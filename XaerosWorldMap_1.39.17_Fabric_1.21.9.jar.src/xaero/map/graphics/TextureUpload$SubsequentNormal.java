/*     */ package xaero.map.graphics;
/*     */ 
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
/*     */ public class SubsequentNormal
/*     */   extends TextureUpload
/*     */ {
/*     */   private int xOffset;
/*     */   private int yOffset;
/*     */   
/*     */   public SubsequentNormal(int uploadType) {
/*  86 */     this.uploadType = uploadType;
/*     */   }
/*     */ 
/*     */   
/*     */   public SubsequentNormal(Object... args) {
/*  91 */     this(6);
/*  92 */     create(args);
/*     */   }
/*     */ 
/*     */   
/*     */   void upload() throws OpenGLException {
/*  97 */     OpenGlHelper.copyBGRAUnpackBufferToSubMapTexture(this.glUnpackPbo, this.glTexture.texture, this.level, this.xOffset, this.yOffset, this.width, this.height, this.pixels_buffer_offset);
/*     */ 
/*     */ 
/*     */     
/* 101 */     OpenGLException.checkGLError();
/*     */   }
/*     */ 
/*     */   
/*     */   public void create(Object... args) {
/* 106 */     super.create(args);
/* 107 */     this.xOffset = ((Integer)args[8]).intValue();
/* 108 */     this.yOffset = ((Integer)args[9]).intValue();
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\graphics\TextureUpload$SubsequentNormal.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */