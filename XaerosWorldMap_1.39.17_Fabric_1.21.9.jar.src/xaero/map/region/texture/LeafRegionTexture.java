/*     */ package xaero.map.region.texture;
/*     */ 
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import net.minecraft.class_310;
/*     */ import xaero.map.MapProcessor;
/*     */ import xaero.map.WorldMap;
/*     */ import xaero.map.biome.BlockTintProvider;
/*     */ import xaero.map.cache.BlockStateShortShapeCache;
/*     */ import xaero.map.exception.OpenGLException;
/*     */ import xaero.map.graphics.TextureUploader;
/*     */ import xaero.map.highlight.DimensionHighlighterHandler;
/*     */ import xaero.map.misc.Misc;
/*     */ import xaero.map.pool.PoolUnit;
/*     */ import xaero.map.pool.buffer.PoolTextureDirectBufferUnit;
/*     */ import xaero.map.region.LeveledRegion;
/*     */ import xaero.map.region.MapRegion;
/*     */ import xaero.map.region.MapTileChunk;
/*     */ import xaero.map.region.OverlayManager;
/*     */ 
/*     */ public class LeafRegionTexture extends RegionTexture<LeafRegionTexture> {
/*     */   private MapTileChunk tileChunk;
/*     */   
/*     */   public LeafRegionTexture(MapTileChunk tileChunk) {
/*  27 */     super((LeveledRegion<LeafRegionTexture>)tileChunk.getInRegion());
/*  28 */     this.tileChunk = tileChunk;
/*     */   }
/*     */   protected PoolTextureDirectBufferUnit highlitColorBuffer;
/*     */   public void postBufferUpdate(boolean hasLight) {
/*  32 */     this.colorBufferFormat = null;
/*  33 */     this.bufferHasLight = hasLight;
/*     */   }
/*     */ 
/*     */   
/*     */   public void preUpload(MapProcessor mapProcessor, BlockTintProvider blockTintProvider, OverlayManager overlayManager, LeveledRegion<LeafRegionTexture> leveledRegion, boolean detailedDebug, BlockStateShortShapeCache blockStateShortShapeCache) {
/*  38 */     MapRegion region = (MapRegion)leveledRegion;
/*  39 */     if (this.tileChunk.getToUpdateBuffers() && !mapProcessor.isWritingPaused()) {
/*  40 */       synchronized (region.writerThreadPauseSync) {
/*  41 */         if (!region.isWritingPaused()) {
/*  42 */           this.tileChunk.updateBuffers(mapProcessor, blockTintProvider, overlayManager, detailedDebug, blockStateShortShapeCache);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void postUpload(MapProcessor mapProcessor, LeveledRegion<LeafRegionTexture> leveledRegion, boolean cleanAndCacheRequestsBlocked) {
/*  49 */     MapRegion region = (MapRegion)leveledRegion;
/*  50 */     if (region.getLoadState() >= 2 && (
/*  51 */       region.getLoadState() == 3 || (!region.isBeingWritten() && (region.getLastVisited() == 0L || region.getTimeSinceVisit() > 1000L))) && !cleanAndCacheRequestsBlocked && !this.tileChunk.getToUpdateBuffers() && this.tileChunk.getLoadState() != 3) {
/*  52 */       region.setLoadState((byte)3);
/*  53 */       this.tileChunk.setLoadState((byte)3);
/*  54 */       this.tileChunk.clean(mapProcessor);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canUpload() {
/*  61 */     return (this.tileChunk.getLoadState() >= 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isUploaded() {
/*  66 */     return (super.isUploaded() && !this.tileChunk.getToUpdateBuffers());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasSourceData() {
/*  71 */     return (this.tileChunk.getLoadState() != 3);
/*     */   }
/*     */ 
/*     */   
/*     */   protected long uploadNonCache(DimensionHighlighterHandler highlighterHandler, TextureUploader textureUploader, BranchTextureRenderer unused) {
/*     */     long totalEstimatedTime;
/*  77 */     PoolTextureDirectBufferUnit colorBufferToUpload = applyHighlights(highlighterHandler, this.colorBuffer, true);
/*  78 */     if (this.textureVersion == -1) {
/*  79 */       updateTextureVersion((this.bufferedTextureVersion != -1) ? (this.bufferedTextureVersion + 1) : (1 + (int)(Math.random() * 1000000.0D)));
/*     */     } else {
/*  81 */       updateTextureVersion(this.textureVersion + 1);
/*  82 */     }  if (colorBufferToUpload == null)
/*  83 */       return 0L; 
/*  84 */     writeToUnpackPBO(0, colorBufferToUpload);
/*     */     
/*  86 */     this.textureHasLight = this.bufferHasLight;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  97 */     this.colorBuffer.getDirectBuffer().position(0);
/*  98 */     this.colorBufferFormat = DEFAULT_INTERNAL_FORMAT;
/*  99 */     this.bufferedTextureVersion = this.textureVersion;
/* 100 */     boolean subsequent = (this.glColorTexture != null);
/* 101 */     bindColorTexture(true);
/* 102 */     OpenGLException.checkGLError();
/* 103 */     if (this.unpackPbo[0] == null) {
/* 104 */       return 0L;
/*     */     }
/* 106 */     if (subsequent) {
/* 107 */       totalEstimatedTime = textureUploader.requestSubsequentNormal(this.glColorTexture, this.unpackPbo[0], 0, 64, 64, 0, 0L, 0, 0);
/*     */     } else {
/* 109 */       totalEstimatedTime = textureUploader.requestNormal(this.glColorTexture, this.unpackPbo[0], 0, DEFAULT_INTERNAL_FORMAT, 64, 64, 0, 0L);
/* 110 */     }  boolean toUploadImmediately = this.tileChunk.getInRegion().isBeingWritten();
/* 111 */     if (toUploadImmediately)
/* 112 */       textureUploader.finishNewestRequestImmediately(); 
/* 113 */     return totalEstimatedTime;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected PoolTextureDirectBufferUnit applyHighlights(DimensionHighlighterHandler highlighterHandler, PoolTextureDirectBufferUnit colorBuffer, boolean separateBuffer) {
/* 119 */     if (!this.tileChunk.hasHighlights())
/* 120 */       return colorBuffer; 
/* 121 */     colorBuffer = super.applyHighlights(highlighterHandler, colorBuffer, separateBuffer);
/* 122 */     int startChunkX = this.tileChunk.getX() << 2;
/* 123 */     int startChunkZ = this.tileChunk.getZ() << 2;
/*     */     
/* 125 */     boolean prepared = false;
/* 126 */     for (int i = 0; i < 4; i++) {
/* 127 */       for (int j = 0; j < 4; j++) {
/* 128 */         boolean discovered = (getHeight(i << 4, j << 4) != 32767);
/* 129 */         int chunkX = startChunkX + i;
/* 130 */         int chunkZ = startChunkZ + j;
/* 131 */         PoolTextureDirectBufferUnit result = highlighterHandler.applyChunkHighlightColors(chunkX, chunkZ, i, j, colorBuffer, this.highlitColorBuffer, prepared, discovered, separateBuffer);
/* 132 */         if (result != null && separateBuffer) {
/* 133 */           this.highlitColorBuffer = result;
/* 134 */           prepared = true;
/*     */         } 
/*     */       } 
/* 137 */     }  if (prepared)
/* 138 */       return this.highlitColorBuffer; 
/* 139 */     return colorBuffer;
/*     */   }
/*     */ 
/*     */   
/*     */   public void postBufferWrite(PoolTextureDirectBufferUnit buffer) {
/* 144 */     super.postBufferWrite(buffer);
/* 145 */     if (buffer == this.highlitColorBuffer) {
/* 146 */       this.highlitColorBuffer = null;
/* 147 */       if (!WorldMap.textureDirectBufferPool.addToPool((PoolUnit)buffer)) {
/* 148 */         WorldMap.bufferDeallocator.deallocate(buffer.getDirectBuffer(), WorldMap.settings.debug);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void updateTextureVersion(int newVersion) {
/* 154 */     super.updateTextureVersion(newVersion);
/* 155 */     this.region.updateLeafTextureVersion(this.tileChunk.getX() & 0x7, this.tileChunk.getZ() & 0x7, newVersion);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addDebugLines(List<String> lines) {
/* 160 */     super.addDebugLines(lines);
/* 161 */     lines.add("" + this.tileChunk.getX() + " " + this.tileChunk.getX());
/* 162 */     lines.add("loadState: " + this.tileChunk.getLoadState());
/* 163 */     lines.add(String.format("changed: %s include: %s terrain: %s highlights: %s toUpdateBuffers: %s", new Object[] { Boolean.valueOf(this.tileChunk.wasChanged()), Boolean.valueOf(this.tileChunk.includeInSave()), Boolean.valueOf(this.tileChunk.hasHadTerrain()), Boolean.valueOf(this.tileChunk.hasHighlights()), Boolean.valueOf(this.tileChunk.getToUpdateBuffers()) }));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeCacheMapData(DataOutputStream output, byte[] usableBuffer, byte[] integerByteBuffer, LeveledRegion<LeafRegionTexture> inRegion) throws IOException {
/* 169 */     super.writeCacheMapData(output, usableBuffer, integerByteBuffer, inRegion);
/* 170 */     this.tileChunk.writeCacheData(output, usableBuffer, integerByteBuffer, inRegion);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void readCacheData(int minorSaveVersion, int majorSaveVersion, DataInputStream input, byte[] usableBuffer, byte[] integerByteBuffer, LeveledRegion<LeafRegionTexture> inRegion, MapProcessor mapProcessor, int x, int y, boolean leafShouldAffectBranches) throws IOException {
/* 176 */     super.readCacheData(minorSaveVersion, majorSaveVersion, input, usableBuffer, integerByteBuffer, inRegion, mapProcessor, x, y, leafShouldAffectBranches);
/* 177 */     this.tileChunk.readCacheData(minorSaveVersion, majorSaveVersion, input, usableBuffer, integerByteBuffer, mapProcessor, x, y);
/* 178 */     if (leafShouldAffectBranches)
/* 179 */       this.colorBufferFormat = null; 
/* 180 */     if (this.colorBuffer != null)
/* 181 */       this.tileChunk.setHasHadTerrain(); 
/*     */   }
/*     */   
/*     */   public void resetHeights() {
/* 185 */     Misc.clearHeightsData1024(this.heightValues.getData());
/* 186 */     Misc.clearHeightsData1024(this.topHeightValues.getData());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldBeUsedForBranchUpdate(int usedVersion) {
/* 191 */     return (this.tileChunk.getLoadState() != 1 && super.shouldBeUsedForBranchUpdate(usedVersion));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldHaveContentForBranchUpdate() {
/* 196 */     return (this.tileChunk.getLoadState() > 0 && super.shouldHaveContentForBranchUpdate());
/*     */   }
/*     */ 
/*     */   
/*     */   public void deleteTexturesAndBuffers() {
/* 201 */     if (!class_310.method_1551().method_18854())
/* 202 */       synchronized ((this.region.getLevel() == 3) ? this.region : this.region.getParent()) {
/*     */         
/* 204 */         synchronized (this.region) {
/* 205 */           this.tileChunk.setLoadState((byte)0);
/*     */         } 
/*     */       }  
/* 208 */     super.deleteTexturesAndBuffers();
/*     */   }
/*     */ 
/*     */   
/*     */   public void prepareBuffer() {
/* 213 */     super.prepareBuffer();
/* 214 */     this.tileChunk.setHasHadTerrain();
/*     */   }
/*     */   
/*     */   public MapTileChunk getTileChunk() {
/* 218 */     return this.tileChunk;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldIncludeInCache() {
/* 223 */     return this.tileChunk.hasHadTerrain();
/*     */   }
/*     */   
/*     */   public void requestHighlightOnlyUpload() {
/* 227 */     resetBiomes();
/* 228 */     this.colorBufferFormat = DEFAULT_INTERNAL_FORMAT;
/* 229 */     this.bufferedTextureVersion = this.tileChunk.getInRegion().getTargetHighlightsHash();
/* 230 */     setToUpload(true);
/* 231 */     if (this.tileChunk.getLoadState() < 2)
/* 232 */       this.tileChunk.setLoadState((byte)2); 
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\region\texture\LeafRegionTexture.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */