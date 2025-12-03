/*     */ package xaero.map.region;
/*     */ 
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import net.minecraft.class_1937;
/*     */ import net.minecraft.class_1959;
/*     */ import net.minecraft.class_2248;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_2378;
/*     */ import net.minecraft.class_2874;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_638;
/*     */ import xaero.map.MapProcessor;
/*     */ import xaero.map.WorldMap;
/*     */ import xaero.map.biome.BlockTintProvider;
/*     */ import xaero.map.cache.BlockStateShortShapeCache;
/*     */ import xaero.map.file.IOHelper;
/*     */ import xaero.map.region.texture.LeafRegionTexture;
/*     */ import xaero.map.world.MapDimension;
/*     */ 
/*     */ public class MapTileChunk
/*     */ {
/*     */   public static final int SIDE_LENGTH = 4;
/*     */   private MapRegion inRegion;
/*  27 */   private byte loadState = 0;
/*     */   private int X;
/*     */   private int Z;
/*  30 */   private MapTile[][] tiles = new MapTile[4][4];
/*  31 */   private byte[][] tileGridsCache = new byte[this.tiles.length][this.tiles.length];
/*     */   private LeafRegionTexture leafTexture;
/*     */   private boolean toUpdateBuffers;
/*     */   private boolean changed;
/*     */   private boolean includeInSave;
/*     */   private boolean hasHadTerrain;
/*     */   private boolean hasHighlights;
/*     */   private boolean hasHighlightsIfUndiscovered;
/*     */   
/*     */   public MapTileChunk(MapRegion r, int x, int z) {
/*  41 */     this.X = x;
/*  42 */     this.Z = z;
/*  43 */     this.inRegion = r;
/*  44 */     this.leafTexture = createLeafTexture();
/*     */   }
/*     */   
/*     */   protected LeafRegionTexture createLeafTexture() {
/*  48 */     return new LeafRegionTexture(this);
/*     */   }
/*     */   
/*     */   public void updateBuffers(MapProcessor mapProcessor, BlockTintProvider blockTintProvider, OverlayManager overlayManager, boolean detailedDebug, BlockStateShortShapeCache blockStateShortShapeCache) {
/*  52 */     if (!class_310.method_1551().method_18854())
/*  53 */       throw new RuntimeException("Wrong thread!"); 
/*  54 */     if (detailedDebug)
/*  55 */       WorldMap.LOGGER.info("Updating buffers: " + this.X + " " + this.Z + " " + this.loadState); 
/*  56 */     class_638 class_638 = mapProcessor.getWorld();
/*  57 */     class_2378<class_2248> blockRegistry = mapProcessor.getWorldBlockRegistry();
/*  58 */     class_2378<class_1959> biomeRegistry = mapProcessor.worldBiomeRegistry;
/*  59 */     class_2378<class_2874> dimensionTypes = mapProcessor.getWorldDimensionTypeRegistry();
/*  60 */     LeafRegionTexture leafTexture = getLeafTexture();
/*  61 */     leafTexture.resetTimer();
/*  62 */     synchronized (this.inRegion) {
/*  63 */       leafTexture.setCachePrepared(false);
/*  64 */       leafTexture.setShouldDownloadFromPBO(false);
/*  65 */       this.inRegion.setAllCachePrepared(false);
/*     */     } 
/*  67 */     leafTexture.prepareBuffer();
/*  68 */     int[] result = this.inRegion.getPixelResultBuffer();
/*  69 */     boolean hasLight = false;
/*  70 */     class_2338.class_2339 mutableGlobalPos = this.inRegion.getMutableGlobalPos();
/*  71 */     MapTileChunk prevTileChunk = getNeighbourTileChunk(0, -1, mapProcessor, false);
/*  72 */     MapTileChunk prevTileChunkDiagonal = getNeighbourTileChunk(-1, -1, mapProcessor, false);
/*  73 */     MapTileChunk prevTileChunkHorisontal = getNeighbourTileChunk(-1, 0, mapProcessor, false);
/*  74 */     MapDimension dim = mapProcessor.getMapWorld().getCurrentDimension();
/*  75 */     float shadowR = dim.getShadowR();
/*  76 */     float shadowG = dim.getShadowG();
/*  77 */     float shadowB = dim.getShadowB();
/*  78 */     ByteBuffer colorBuffer = leafTexture.getDirectColorBuffer();
/*     */     
/*  80 */     mapProcessor.getBiomeColorCalculator().prepare(WorldMap.settings.biomeBlending);
/*  81 */     for (int o = 0; o < this.tiles.length; o++) {
/*  82 */       int offX = o * 16;
/*  83 */       for (int p = 0; p < this.tiles.length; p++) {
/*  84 */         MapTile tile = this.tiles[o][p];
/*  85 */         if (tile != null && tile.isLoaded()) {
/*     */           
/*  87 */           int caveStart = tile.getWrittenCaveStart();
/*  88 */           int caveDepth = tile.getWrittenCaveDepth();
/*  89 */           int offZ = p * 16;
/*  90 */           for (int z = 0; z < 16; z++) {
/*  91 */             int pixelZ = offZ + z;
/*  92 */             for (int x = 0; x < 16; x++) {
/*  93 */               int pixelX = offX + x;
/*  94 */               int effectiveHeight = leafTexture.getHeight(pixelX, pixelZ);
/*  95 */               int effectiveTopHeight = leafTexture.getTopHeight(pixelX, pixelZ);
/*     */               
/*  97 */               tile.getBlock(x, z).getPixelColour(result, mapProcessor.getMapWriter(), (class_1937)class_638, dim, blockRegistry, this, prevTileChunk, prevTileChunkDiagonal, prevTileChunkHorisontal, tile, x, z, caveStart, caveDepth, mutableGlobalPos, biomeRegistry, dimensionTypes, shadowR, shadowG, shadowB, blockTintProvider, mapProcessor, overlayManager, effectiveHeight, effectiveTopHeight, blockStateShortShapeCache);
/*  98 */               putColour(pixelX, pixelZ, result[0], result[1], result[2], result[3], colorBuffer, 64);
/*  99 */               if (result[3] != 0) {
/* 100 */                 hasLight = true;
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 109 */     leafTexture.postBufferUpdate(hasLight);
/* 110 */     this.toUpdateBuffers = false;
/* 111 */     leafTexture.setToUpload(true);
/*     */   }
/*     */   
/*     */   public void putColour(int x, int y, int red, int green, int blue, int alpha, ByteBuffer buffer, int size) {
/* 115 */     int pos = (y * size + x) * 4;
/*     */     
/* 117 */     buffer.putInt(pos, blue << 24 | green << 16 | red << 8 | alpha);
/*     */   }
/*     */ 
/*     */   
/*     */   public MapTileChunk getNeighbourTileChunk(int directionX, int directionZ, MapProcessor mapProcessor, boolean crossRegion) {
/*     */     MapRegion prevTileChunkSrc;
/* 123 */     int maxCoord = 7;
/* 124 */     int chunkXInsideRegion = this.X & maxCoord;
/* 125 */     int chunkZInsideRegion = this.Z & maxCoord;
/* 126 */     MapTileChunk prevTileChunk = null;
/* 127 */     int chunkXInsideRegionPrev = chunkXInsideRegion + directionX;
/* 128 */     int chunkZInsideRegionPrev = chunkZInsideRegion + directionZ;
/*     */     
/* 130 */     int regDirectionX = 0;
/* 131 */     int regDirectionZ = 0;
/* 132 */     if (chunkXInsideRegionPrev < 0 || chunkXInsideRegionPrev > maxCoord) {
/* 133 */       regDirectionX = directionX;
/* 134 */       chunkXInsideRegionPrev &= maxCoord;
/*     */     } 
/* 136 */     if (chunkZInsideRegionPrev < 0 || chunkZInsideRegionPrev > maxCoord) {
/* 137 */       regDirectionZ = directionZ;
/* 138 */       chunkZInsideRegionPrev &= maxCoord;
/*     */     } 
/*     */     
/* 141 */     if (regDirectionX != 0 || regDirectionZ != 0) {
/* 142 */       prevTileChunkSrc = !crossRegion ? null : mapProcessor.getLeafMapRegion(this.inRegion.getCaveLayer(), this.inRegion.getRegionX() + regDirectionX, this.inRegion.getRegionZ() + regDirectionZ, false);
/*     */     } else {
/* 144 */       prevTileChunkSrc = this.inRegion;
/*     */     } 
/* 146 */     if (prevTileChunkSrc != null)
/* 147 */       prevTileChunk = prevTileChunkSrc.getChunk(chunkXInsideRegionPrev, chunkZInsideRegionPrev); 
/* 148 */     return prevTileChunk;
/*     */   }
/*     */   
/*     */   public void clean(MapProcessor mapProcessor) {
/* 152 */     for (int o = 0; o < 4; o++) {
/* 153 */       for (int p = 0; p < 4; p++) {
/* 154 */         MapTile tile = this.tiles[o][p];
/* 155 */         if (tile != null)
/*     */         
/* 157 */         { mapProcessor.getTilePool().addToPool(tile);
/* 158 */           this.tiles[o][p] = null; } 
/*     */       } 
/* 160 */     }  this.toUpdateBuffers = false;
/* 161 */     this.includeInSave = false;
/*     */   }
/*     */   
/*     */   public int getX() {
/* 165 */     return this.X;
/*     */   }
/*     */   
/*     */   public int getZ() {
/* 169 */     return this.Z;
/*     */   }
/*     */   
/*     */   public byte[][] getTileGridsCache() {
/* 173 */     return this.tileGridsCache;
/*     */   }
/*     */   
/*     */   public int getLoadState() {
/* 177 */     return this.loadState;
/*     */   }
/*     */   
/*     */   public void setLoadState(byte loadState) {
/* 181 */     this.loadState = loadState;
/*     */   }
/*     */   
/*     */   public MapTile getTile(int x, int z) {
/* 185 */     return this.tiles[x][z];
/*     */   }
/*     */   
/*     */   public void setTile(int x, int z, MapTile tile, BlockStateShortShapeCache blockStateShortShapeCache) {
/* 189 */     LeafRegionTexture leafTexture = this.leafTexture;
/* 190 */     if (tile != null) {
/* 191 */       boolean tileWasLoadedWithTopHeightValues = (tile.getWorldInterpretationVersion() > 0);
/* 192 */       this.includeInSave = true;
/* 193 */       for (int i = 0; i < 16; i++) {
/* 194 */         for (int j = 0; j < 16; j++)
/* 195 */         { int destX = x * 16 + i;
/* 196 */           int destZ = z * 16 + j;
/* 197 */           MapBlock mapBlock = tile.getBlock(i, j);
/* 198 */           boolean subtractOneFromHeight = (WorldMap.settings.adjustHeightForCarpetLikeBlocks && blockStateShortShapeCache.isShort(mapBlock.getState()));
/* 199 */           leafTexture.putHeight(destX, destZ, mapBlock.getEffectiveHeight(subtractOneFromHeight));
/* 200 */           if (mapBlock.getState() == null || (mapBlock.getState().method_26215() && mapBlock.getNumberOfOverlays() == 0) || (!tileWasLoadedWithTopHeightValues && !mapBlock.getState().method_26215() && mapBlock.getNumberOfOverlays() > 0)) {
/* 201 */             leafTexture.removeTopHeight(destX, destZ);
/*     */           } else {
/* 203 */             leafTexture.putTopHeight(destX, destZ, mapBlock.getEffectiveTopHeight(subtractOneFromHeight));
/* 204 */           }  leafTexture.setBiome(destX, destZ, mapBlock.getBiome()); } 
/*     */       } 
/* 206 */     } else if (this.tiles[x][z] != null) {
/* 207 */       for (int i = 0; i < 16; i++) {
/* 208 */         for (int j = 0; j < 16; j++) {
/* 209 */           int destX = x * 16 + i;
/* 210 */           int destZ = z * 16 + j;
/* 211 */           leafTexture.removeHeight(destX, destZ);
/* 212 */           leafTexture.removeTopHeight(destX, destZ);
/* 213 */           leafTexture.setBiome(destX, destZ, null);
/*     */         } 
/*     */       } 
/* 216 */     }  this.tiles[x][z] = tile;
/*     */   }
/*     */   
/*     */   public MapRegion getInRegion() {
/* 220 */     return this.inRegion;
/*     */   }
/*     */   
/*     */   public boolean wasChanged() {
/* 224 */     return this.changed;
/*     */   }
/*     */   
/*     */   public void setChanged(boolean changed) {
/* 228 */     this.changed = changed;
/*     */   }
/*     */   
/*     */   public int getTimer() {
/* 232 */     return this.leafTexture.getTimer();
/*     */   }
/*     */   
/*     */   public void decTimer() {
/* 236 */     this.leafTexture.decTimer();
/*     */   }
/*     */   
/*     */   public boolean includeInSave() {
/* 240 */     return this.includeInSave;
/*     */   }
/*     */   
/*     */   public void unincludeInSave() {
/* 244 */     this.includeInSave = false;
/*     */   }
/*     */   
/*     */   public void resetHeights() {
/* 248 */     this.leafTexture.resetHeights();
/*     */   }
/*     */   
/*     */   public boolean getToUpdateBuffers() {
/* 252 */     return this.toUpdateBuffers;
/*     */   }
/*     */   
/*     */   public void setToUpdateBuffers(boolean toUpdateBuffers) {
/* 256 */     this.toUpdateBuffers = toUpdateBuffers;
/*     */   }
/*     */   
/*     */   public LeafRegionTexture getLeafTexture() {
/* 260 */     return this.leafTexture;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeCacheData(DataOutputStream output, byte[] usableBuffer, byte[] integerByteBuffer, LeveledRegion<LeafRegionTexture> inRegion2) throws IOException {}
/*     */ 
/*     */   
/*     */   public void readCacheData(int minorSaveVersion, int majorSaveVersion, DataInputStream input, byte[] usableBuffer, byte[] integerByteBuffer, MapProcessor mapProcessor, int x, int y) throws IOException {
/* 269 */     if (minorSaveVersion == 4) {
/* 270 */       boolean hasBottomHeightValues = (input.read() == 1);
/* 271 */       if (hasBottomHeightValues) {
/*     */         
/* 273 */         input.readByte();
/* 274 */         byte[] bottomHeights = new byte[64];
/* 275 */         IOHelper.readToBuffer(bottomHeights, 64, input);
/* 276 */         LeafRegionTexture leafTexture = this.leafTexture;
/* 277 */         for (int i = 0; i < 64; i++) {
/* 278 */           leafTexture.putHeight(i, 63, bottomHeights[i]);
/*     */         }
/*     */       } 
/* 281 */     } else if (minorSaveVersion >= 5 && minorSaveVersion < 13) {
/* 282 */       input.readInt();
/* 283 */       byte[] heights = new byte[64];
/* 284 */       LeafRegionTexture leafTexture = this.leafTexture;
/* 285 */       for (int hx = 0; hx < 64; hx++) {
/* 286 */         IOHelper.readToBuffer(heights, 64, input);
/* 287 */         for (int hz = 0; hz < 64; hz++)
/* 288 */           leafTexture.putHeight(hx, hz, heights[hz]); 
/*     */       } 
/*     */     } 
/* 291 */     if (minorSaveVersion >= 4 && minorSaveVersion < 10 && (
/* 292 */       this.Z & 0x7) == 0) {
/* 293 */       input.readByte();
/*     */     }
/* 295 */     this.loadState = 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 300 */     return "" + getX() + " " + getX();
/*     */   }
/*     */   
/*     */   public boolean hasHadTerrain() {
/* 304 */     return this.hasHadTerrain;
/*     */   }
/*     */   
/*     */   public void setHasHadTerrain() {
/* 308 */     this.hasHadTerrain = true;
/* 309 */     this.inRegion.setHasHadTerrain();
/*     */   }
/*     */   
/*     */   public void unsetHasHadTerrain() {
/* 313 */     this.hasHadTerrain = false;
/*     */   }
/*     */   
/*     */   public boolean hasHighlights() {
/* 317 */     return this.hasHighlights;
/*     */   }
/*     */   
/*     */   public void setHasHighlights(boolean hasHighlights) {
/* 321 */     this.hasHighlights = hasHighlights;
/*     */   }
/*     */   
/*     */   public boolean hasHighlightsIfUndiscovered() {
/* 325 */     return this.hasHighlightsIfUndiscovered;
/*     */   }
/*     */   
/*     */   public void setHasHighlightsIfUndiscovered(boolean hasHighlightsIfUndiscovered) {
/* 329 */     this.hasHighlightsIfUndiscovered = hasHighlightsIfUndiscovered;
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\region\MapTileChunk.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */