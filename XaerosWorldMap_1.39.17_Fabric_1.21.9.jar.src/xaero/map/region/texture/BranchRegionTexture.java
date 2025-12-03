/*     */ package xaero.map.region.texture;
/*     */ 
/*     */ import com.mojang.blaze3d.textures.TextureFormat;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.List;
/*     */ import net.minecraft.class_1959;
/*     */ import net.minecraft.class_5321;
/*     */ import xaero.map.MapProcessor;
/*     */ import xaero.map.biome.BlockTintProvider;
/*     */ import xaero.map.cache.BlockStateShortShapeCache;
/*     */ import xaero.map.exception.OpenGLException;
/*     */ import xaero.map.graphics.GpuTextureAndView;
/*     */ import xaero.map.graphics.TextureUploader;
/*     */ import xaero.map.highlight.DimensionHighlighterHandler;
/*     */ import xaero.map.region.BranchLeveledRegion;
/*     */ import xaero.map.region.LeveledRegion;
/*     */ import xaero.map.region.OverlayManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BranchRegionTexture
/*     */   extends RegionTexture<BranchRegionTexture>
/*     */ {
/*     */   private boolean updating;
/*     */   private boolean colorAllocationRequested;
/*     */   private ChildTextureInfo topLeftInfo;
/*     */   private ChildTextureInfo topRightInfo;
/*     */   private ChildTextureInfo bottomLeftInfo;
/*     */   private ChildTextureInfo bottomRightInfo;
/*     */   private LeveledRegion<?> branchUpdateChildRegion;
/*     */   private boolean checkForUpdatesAfterDownload;
/*     */   
/*     */   public BranchRegionTexture(LeveledRegion<BranchRegionTexture> region) {
/*  37 */     super(region);
/*  38 */     reset();
/*     */   }
/*     */   
/*     */   private void reset() {
/*  42 */     this.updating = false;
/*  43 */     this.colorAllocationRequested = false;
/*  44 */     this.topLeftInfo = new ChildTextureInfo(this);
/*  45 */     this.topRightInfo = new ChildTextureInfo(this);
/*  46 */     this.bottomLeftInfo = new ChildTextureInfo(this);
/*  47 */     this.bottomRightInfo = new ChildTextureInfo(this);
/*  48 */     this.checkForUpdatesAfterDownload = false;
/*     */   }
/*     */   
/*     */   public boolean checkForUpdates(RegionTexture<?> topLeft, RegionTexture<?> topRight, RegionTexture<?> bottomLeft, RegionTexture<?> bottomRight, LeveledRegion<?> childRegion) {
/*  52 */     boolean needsUpdating = false;
/*  53 */     if ((topLeft != null && topLeft.glColorTexture == null) || (topRight != null && topRight.glColorTexture == null) || (bottomLeft != null && bottomLeft.glColorTexture == null) || (bottomRight != null && bottomRight.glColorTexture == null))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  60 */       return false; } 
/*  61 */     needsUpdating = (needsUpdating || isChildUpdated(this.topLeftInfo, topLeft, childRegion));
/*  62 */     needsUpdating = (needsUpdating || isChildUpdated(this.topRightInfo, topRight, childRegion));
/*  63 */     needsUpdating = (needsUpdating || isChildUpdated(this.bottomLeftInfo, bottomLeft, childRegion));
/*  64 */     needsUpdating = (needsUpdating || isChildUpdated(this.bottomRightInfo, bottomRight, childRegion));
/*  65 */     if (needsUpdating) {
/*  66 */       if (this.toUpload) {
/*  67 */         if (this.shouldDownloadFromPBO) {
/*  68 */           this.checkForUpdatesAfterDownload = true;
/*  69 */           return false;
/*     */         } 
/*  71 */         if (this.topLeftInfo.temporaryReference == topLeft && this.topRightInfo.temporaryReference == topRight && this.bottomLeftInfo.temporaryReference == bottomLeft && this.bottomRightInfo.temporaryReference == bottomRight) {
/*  72 */           return false;
/*     */         }
/*     */       } else {
/*  75 */         childRegion.activeBranchUpdateReferences++;
/*  76 */       }  setCachePrepared(false);
/*  77 */       this.region.setAllCachePrepared(false);
/*  78 */       this.colorBufferFormat = null;
/*  79 */       this.toUpload = true;
/*  80 */       this.updating = true;
/*  81 */       this.topLeftInfo.temporaryReference = topLeft;
/*  82 */       this.topRightInfo.temporaryReference = topRight;
/*  83 */       this.bottomLeftInfo.temporaryReference = bottomLeft;
/*  84 */       this.bottomRightInfo.temporaryReference = bottomRight;
/*  85 */       this.branchUpdateChildRegion = childRegion;
/*     */     } 
/*  87 */     return needsUpdating;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isChildUpdated(ChildTextureInfo info, RegionTexture<?> texture, LeveledRegion<?> region) {
/*  92 */     if (region.isLoaded()) {
/*  93 */       if (texture == null && info.usedTextureVersion != 0) {
/*  94 */         return true;
/*     */       }
/*  96 */       if (texture != null && texture.glColorTexture != null && texture.shouldBeUsedForBranchUpdate(info.usedTextureVersion))
/*  97 */         return true; 
/*     */     } 
/*  99 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void preUpload(MapProcessor mapProcessor, BlockTintProvider blockTintProvider, OverlayManager overlayManager, LeveledRegion<BranchRegionTexture> region, boolean detailedDebug, BlockStateShortShapeCache blockStateShortShapeCache) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void postUpload(MapProcessor mapProcessor, LeveledRegion<BranchRegionTexture> leveledRegion, boolean cleanAndCacheRequestsBlocked) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public long uploadBuffer(DimensionHighlighterHandler highlighterHandler, TextureUploader textureUploader, LeveledRegion<BranchRegionTexture> inRegion, BranchTextureRenderer branchTextureRenderer, int x, int y) throws OpenGLException, IllegalArgumentException, IllegalAccessException {
/* 115 */     return super.uploadBuffer(highlighterHandler, textureUploader, inRegion, branchTextureRenderer, x, y);
/*     */   }
/*     */   
/*     */   private void copyNonColorData(RegionTexture<?> childTexture, int offX, int offZ) {
/* 119 */     boolean resetting = (childTexture == null);
/* 120 */     for (int i = 0; i < 32; i++) {
/* 121 */       for (int j = 0; j < 32; j++) {
/* 122 */         int childHeight = resetting ? 32767 : childTexture.getHeight(i << 1, j << 1);
/* 123 */         int childTopHeight = resetting ? 32767 : childTexture.getTopHeight(i << 1, j << 1);
/* 124 */         class_5321<class_1959> childBiome = resetting ? null : childTexture.getBiome(i << 1, j << 1);
/* 125 */         int destX = offX | i;
/* 126 */         int destZ = offZ | j;
/* 127 */         if (childHeight != 32767) {
/* 128 */           putHeight(destX, destZ, childHeight);
/*     */         } else {
/* 130 */           removeHeight(destX, destZ);
/* 131 */         }  if (childTopHeight != 32767) {
/* 132 */           putTopHeight(destX, destZ, childTopHeight);
/*     */         } else {
/* 134 */           removeTopHeight(destX, destZ);
/* 135 */         }  setBiome(destX, destZ, childBiome);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected long uploadNonCache(DimensionHighlighterHandler highlighterHandler, TextureUploader textureUploader, BranchTextureRenderer renderer) {
/* 141 */     this.timer = 5;
/* 142 */     prepareBuffer();
/* 143 */     this.shouldDownloadFromPBO = true;
/* 144 */     if (this.updating) {
/* 145 */       ensurePackPBO();
/* 146 */       unbindPackPBO();
/* 147 */       bindColorTexture(true);
/* 148 */       OpenGLException.checkGLError();
/* 149 */       ChildTextureInfo topLeftInfo = this.topLeftInfo;
/* 150 */       ChildTextureInfo topRightInfo = this.topRightInfo;
/* 151 */       ChildTextureInfo bottomLeftInfo = this.bottomLeftInfo;
/* 152 */       ChildTextureInfo bottomRightInfo = this.bottomRightInfo;
/* 153 */       GpuTextureAndView emptyTexture = renderer.getEmptyTexture();
/* 154 */       GpuTextureAndView topLeftColor = topLeftInfo.getColorTextureForUpdate(emptyTexture);
/* 155 */       GpuTextureAndView topRightColor = topRightInfo.getColorTextureForUpdate(emptyTexture);
/* 156 */       GpuTextureAndView bottomLeftColor = bottomLeftInfo.getColorTextureForUpdate(emptyTexture);
/* 157 */       GpuTextureAndView bottomRightColor = bottomRightInfo.getColorTextureForUpdate(emptyTexture);
/* 158 */       long estimatedTime = textureUploader.requestBranchUpdate(!this.colorAllocationRequested, this.glColorTexture, this.unpackPbo[0], 0, DEFAULT_INTERNAL_FORMAT, 64, 64, 0, 0L, topLeftColor, topRightColor, bottomLeftColor, bottomRightColor, renderer, this.packPbo, 0L);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 164 */       if (topLeftColor != null)
/* 165 */         copyNonColorData(topLeftInfo.temporaryReference, 0, 0); 
/* 166 */       if (topRightColor != null)
/* 167 */         copyNonColorData(topRightInfo.temporaryReference, 32, 0); 
/* 168 */       if (bottomLeftColor != null)
/* 169 */         copyNonColorData(bottomLeftInfo.temporaryReference, 0, 32); 
/* 170 */       if (bottomRightColor != null) {
/* 171 */         copyNonColorData(bottomRightInfo.temporaryReference, 32, 32);
/*     */       }
/* 173 */       int textureVersionSum = 0;
/*     */ 
/*     */       
/*     */       int topLeftVersion;
/*     */       
/* 178 */       textureVersionSum += topLeftVersion = topLeftInfo.getTextureVersion(); int topRightVersion;
/* 179 */       textureVersionSum += topRightVersion = topRightInfo.getTextureVersion(); int bottomLeftVersion;
/* 180 */       textureVersionSum += bottomLeftVersion = bottomLeftInfo.getTextureVersion(); int bottomRightVersion;
/* 181 */       textureVersionSum += bottomRightVersion = bottomRightInfo.getTextureVersion();
/* 182 */       updateTextureVersion(textureVersionSum);
/* 183 */       this.colorAllocationRequested = true;
/* 184 */       this.textureHasLight = (topLeftInfo.hasLight() || topRightInfo.hasLight() || bottomLeftInfo.hasLight() || bottomRightInfo.hasLight());
/*     */       
/* 186 */       this.branchUpdateChildRegion.activeBranchUpdateReferences--;
/* 187 */       this.branchUpdateChildRegion = null;
/*     */       
/* 189 */       topLeftInfo.onUpdate(topLeftVersion);
/* 190 */       topRightInfo.onUpdate(topRightVersion);
/* 191 */       bottomLeftInfo.onUpdate(bottomLeftVersion);
/* 192 */       bottomRightInfo.onUpdate(bottomRightVersion);
/* 193 */       BranchLeveledRegion branchRegion = (BranchLeveledRegion)this.region;
/* 194 */       branchRegion.postTextureUpdate();
/* 195 */       return estimatedTime;
/*     */     } 
/* 197 */     ensurePackPBO();
/* 198 */     unbindPackPBO();
/* 199 */     return textureUploader.requestBranchDownload(this.glColorTexture, this.packPbo, 0L);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onCacheUploadRequested() {
/* 205 */     super.onCacheUploadRequested();
/* 206 */     this.colorAllocationRequested = true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onDownloadedBuffer(ByteBuffer mappedPBO) {
/* 211 */     super.onDownloadedBuffer(mappedPBO);
/*     */     
/* 213 */     if (this.checkForUpdatesAfterDownload) {
/* 214 */       ((BranchLeveledRegion)this.region).setShouldCheckForUpdatesRecursive(true);
/* 215 */       this.checkForUpdatesAfterDownload = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void endPBODownload(TextureFormat format, boolean success) {
/* 221 */     if (!success) {
/* 222 */       this.topLeftInfo.usedTextureVersion--;
/* 223 */       this.topRightInfo.usedTextureVersion--;
/* 224 */       this.bottomLeftInfo.usedTextureVersion--;
/* 225 */       this.bottomRightInfo.usedTextureVersion--;
/* 226 */       updateTextureVersion(this.topLeftInfo.usedTextureVersion + this.topRightInfo.usedTextureVersion + this.bottomLeftInfo.usedTextureVersion + this.bottomRightInfo.usedTextureVersion);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 233 */     super.endPBODownload(format, success);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasSourceData() {
/* 238 */     return false;
/*     */   }
/*     */   
/*     */   public class ChildTextureInfo
/*     */   {
/*     */     private int usedTextureVersion;
/*     */     
/*     */     private GpuTextureAndView getColorTextureForUpdate(GpuTextureAndView emptyTexture) {
/* 246 */       if ((this.temporaryReference == null && this.usedTextureVersion == 0) || (this.temporaryReference != null && !this.temporaryReference.shouldBeUsedForBranchUpdate(this.usedTextureVersion)))
/* 247 */         return null; 
/* 248 */       if (this.temporaryReference == null || !this.temporaryReference.shouldHaveContentForBranchUpdate())
/* 249 */         return emptyTexture; 
/* 250 */       return this.temporaryReference.glColorTexture;
/*     */     } private RegionTexture<?> temporaryReference;
/*     */     public ChildTextureInfo(BranchRegionTexture this$0) {}
/*     */     private int getTextureVersion() {
/* 254 */       if (this.temporaryReference == null || !this.temporaryReference.shouldHaveContentForBranchUpdate())
/* 255 */         return 0; 
/* 256 */       return this.temporaryReference.textureVersion;
/*     */     }
/*     */     
/*     */     private boolean hasLight() {
/* 260 */       return (this.temporaryReference != null && this.temporaryReference.textureHasLight && this.temporaryReference.shouldHaveContentForBranchUpdate());
/*     */     }
/*     */     
/*     */     public void onUpdate(int newVersion) {
/* 264 */       this.usedTextureVersion = newVersion;
/* 265 */       if (this.temporaryReference != null) {
/* 266 */         this.temporaryReference = null;
/*     */       }
/*     */     }
/*     */     
/*     */     public void onParentDeletion() {
/* 271 */       if (this.temporaryReference != null) {
/* 272 */         this.temporaryReference = null;
/*     */       }
/*     */     }
/*     */     
/*     */     public GpuTextureAndView getReferenceColorTexture() {
/* 277 */       return (this.temporaryReference == null) ? null : this.temporaryReference.glColorTexture;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 282 */       return "tv " + this.usedTextureVersion + ", ct " + String.valueOf(getReferenceColorTexture());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void addDebugLines(List<String> lines) {
/* 288 */     super.addDebugLines(lines);
/* 289 */     lines.add("updating: " + this.updating);
/* 290 */     lines.add("colorAllocationRequested: " + this.colorAllocationRequested);
/* 291 */     lines.add("topLeftInfo: " + String.valueOf(this.topLeftInfo));
/* 292 */     lines.add("topRightInfo: " + String.valueOf(this.topRightInfo));
/* 293 */     lines.add("bottomLeftInfo: " + String.valueOf(this.bottomLeftInfo));
/* 294 */     lines.add("bottomRightInfo: " + String.valueOf(this.bottomRightInfo));
/*     */   }
/*     */ 
/*     */   
/*     */   public void onTextureDeletion() {
/* 299 */     super.onTextureDeletion();
/*     */     
/* 301 */     if (this.branchUpdateChildRegion != null)
/*     */     {
/* 303 */       this.branchUpdateChildRegion.activeBranchUpdateReferences--;
/*     */     }
/*     */     
/* 306 */     this.topLeftInfo.onParentDeletion();
/* 307 */     this.topRightInfo.onParentDeletion();
/* 308 */     this.bottomLeftInfo.onParentDeletion();
/* 309 */     this.bottomRightInfo.onParentDeletion();
/* 310 */     reset();
/*     */   }
/*     */   
/*     */   public void requestDownload() {
/* 314 */     this.toUpload = true;
/* 315 */     this.updating = false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeCacheMapData(DataOutputStream output, byte[] usableBuffer, byte[] integerByteBuffer, LeveledRegion<BranchRegionTexture> inRegion) throws IOException {
/* 321 */     super.writeCacheMapData(output, usableBuffer, integerByteBuffer, inRegion);
/* 322 */     output.writeInt(this.topLeftInfo.usedTextureVersion);
/* 323 */     output.writeInt(this.topRightInfo.usedTextureVersion);
/* 324 */     output.writeInt(this.bottomLeftInfo.usedTextureVersion);
/* 325 */     output.writeInt(this.bottomRightInfo.usedTextureVersion);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void readCacheData(int minorSaveVersion, int majorSaveVersion, DataInputStream input, byte[] usableBuffer, byte[] integerByteBuffer, LeveledRegion<BranchRegionTexture> inRegion, MapProcessor mapProcessor, int x, int y, boolean leafShouldAffectBranches) throws IOException {
/* 331 */     super.readCacheData(minorSaveVersion, majorSaveVersion, input, usableBuffer, integerByteBuffer, inRegion, mapProcessor, x, y, leafShouldAffectBranches);
/* 332 */     if (minorSaveVersion >= 15) {
/* 333 */       this.topLeftInfo.usedTextureVersion = input.readInt();
/* 334 */       this.topRightInfo.usedTextureVersion = input.readInt();
/* 335 */       this.bottomLeftInfo.usedTextureVersion = input.readInt();
/* 336 */       this.bottomRightInfo.usedTextureVersion = input.readInt();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\region\texture\BranchRegionTexture.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */