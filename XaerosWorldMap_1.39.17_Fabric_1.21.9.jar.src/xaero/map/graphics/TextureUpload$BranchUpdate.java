/*     */ package xaero.map.graphics;
/*     */ 
/*     */ import com.mojang.blaze3d.buffers.GpuBuffer;
/*     */ import net.minecraft.class_310;
/*     */ import xaero.map.exception.OpenGLException;
/*     */ import xaero.map.region.texture.BranchTextureRenderer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BranchUpdate
/*     */   extends TextureUpload
/*     */ {
/*     */   private boolean allocate;
/*     */   private GpuTextureAndView srcTextureTopLeft;
/*     */   private GpuTextureAndView srcTextureTopRight;
/*     */   private GpuTextureAndView srcTextureBottomLeft;
/*     */   private GpuTextureAndView srcTextureBottomRight;
/*     */   private BranchTextureRenderer renderer;
/*     */   private GpuBuffer glPackPbo;
/*     */   private long pboOffset;
/*     */   
/*     */   public BranchUpdate(int uploadType) {
/* 125 */     this.uploadType = uploadType;
/*     */   }
/*     */ 
/*     */   
/*     */   public BranchUpdate(Object... args) {
/* 130 */     this(((Boolean)args[8]).booleanValue() ? 4 : 3);
/* 131 */     create(args);
/*     */   }
/*     */ 
/*     */   
/*     */   void upload() throws OpenGLException {
/* 136 */     this.renderer.render(this.glTexture, this.srcTextureTopLeft, this.srcTextureTopRight, this.srcTextureBottomLeft, this.srcTextureBottomRight, class_310.method_1551().method_1522(), this.allocate);
/*     */ 
/*     */     
/* 139 */     if (this.glPackPbo == null)
/*     */       return; 
/* 141 */     OpenGlHelper.copyTextureToBGRAPackBuffer(this.glTexture.texture, this.glPackPbo, this.pboOffset);
/*     */   }
/*     */ 
/*     */   
/*     */   public void create(Object... args) {
/* 146 */     super.create(args);
/* 147 */     this.allocate = ((Boolean)args[8]).booleanValue();
/* 148 */     this.srcTextureTopLeft = (GpuTextureAndView)args[9];
/* 149 */     this.srcTextureTopRight = (GpuTextureAndView)args[10];
/* 150 */     this.srcTextureBottomLeft = (GpuTextureAndView)args[11];
/* 151 */     this.srcTextureBottomRight = (GpuTextureAndView)args[12];
/* 152 */     this.renderer = (BranchTextureRenderer)args[13];
/* 153 */     this.glPackPbo = (GpuBuffer)args[14];
/* 154 */     this.pboOffset = ((Long)args[15]).longValue();
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\graphics\TextureUpload$BranchUpdate.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */