/*     */ package xaero.map.region.texture;
/*     */ 
/*     */ import xaero.map.graphics.GpuTextureAndView;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChildTextureInfo
/*     */ {
/*     */   private int usedTextureVersion;
/*     */   private RegionTexture<?> temporaryReference;
/*     */   
/*     */   public ChildTextureInfo(BranchRegionTexture this$0) {}
/*     */   
/*     */   private GpuTextureAndView getColorTextureForUpdate(GpuTextureAndView emptyTexture) {
/* 246 */     if ((this.temporaryReference == null && this.usedTextureVersion == 0) || (this.temporaryReference != null && !this.temporaryReference.shouldBeUsedForBranchUpdate(this.usedTextureVersion)))
/* 247 */       return null; 
/* 248 */     if (this.temporaryReference == null || !this.temporaryReference.shouldHaveContentForBranchUpdate())
/* 249 */       return emptyTexture; 
/* 250 */     return this.temporaryReference.glColorTexture;
/*     */   }
/*     */   
/*     */   private int getTextureVersion() {
/* 254 */     if (this.temporaryReference == null || !this.temporaryReference.shouldHaveContentForBranchUpdate())
/* 255 */       return 0; 
/* 256 */     return this.temporaryReference.textureVersion;
/*     */   }
/*     */   
/*     */   private boolean hasLight() {
/* 260 */     return (this.temporaryReference != null && this.temporaryReference.textureHasLight && this.temporaryReference.shouldHaveContentForBranchUpdate());
/*     */   }
/*     */   
/*     */   public void onUpdate(int newVersion) {
/* 264 */     this.usedTextureVersion = newVersion;
/* 265 */     if (this.temporaryReference != null) {
/* 266 */       this.temporaryReference = null;
/*     */     }
/*     */   }
/*     */   
/*     */   public void onParentDeletion() {
/* 271 */     if (this.temporaryReference != null) {
/* 272 */       this.temporaryReference = null;
/*     */     }
/*     */   }
/*     */   
/*     */   public GpuTextureAndView getReferenceColorTexture() {
/* 277 */     return (this.temporaryReference == null) ? null : this.temporaryReference.glColorTexture;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 282 */     return "tv " + this.usedTextureVersion + ", ct " + String.valueOf(getReferenceColorTexture());
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\region\texture\BranchRegionTexture$ChildTextureInfo.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */