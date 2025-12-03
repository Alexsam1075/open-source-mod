/*     */ package xaero.map.graphics;
/*     */ 
/*     */ import com.mojang.blaze3d.buffers.GpuBuffer;
/*     */ import xaero.map.exception.OpenGLException;
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
/*     */ public class BranchDownload
/*     */   extends TextureUpload
/*     */ {
/*     */   private GpuBuffer glPackPbo;
/*     */   private long pboOffset;
/*     */   
/*     */   public BranchDownload(int uploadType) {
/* 165 */     this.uploadType = uploadType;
/*     */   }
/*     */ 
/*     */   
/*     */   public BranchDownload(Object... args) {
/* 170 */     this(5);
/* 171 */     create(args);
/*     */   }
/*     */ 
/*     */   
/*     */   void upload() throws OpenGLException {
/* 176 */     if (this.glPackPbo == null) {
/*     */       return;
/*     */     }
/* 179 */     OpenGlHelper.copyTextureToBGRAPackBuffer(this.glTexture.texture, this.glPackPbo, this.pboOffset);
/*     */   }
/*     */ 
/*     */   
/*     */   public void create(Object... args) {
/* 184 */     super.create(args);
/* 185 */     this.glPackPbo = (GpuBuffer)args[8];
/* 186 */     this.pboOffset = ((Long)args[9]).longValue();
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\graphics\TextureUpload$BranchDownload.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */