/*    */ package xaero.map.graphics;
/*    */ 
/*    */ import xaero.map.exception.OpenGLException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Normal
/*    */   extends TextureUpload
/*    */ {
/*    */   public Normal(int uploadType) {
/* 55 */     this.uploadType = uploadType;
/*    */   }
/*    */ 
/*    */   
/*    */   public Normal(Object... args) {
/* 60 */     this(0);
/* 61 */     create(args);
/*    */   }
/*    */ 
/*    */   
/*    */   void upload() throws OpenGLException {
/* 66 */     OpenGlHelper.copyBGRAUnpackBufferToMapTexture(this.glUnpackPbo, this.glTexture.texture, this.level, this.internalFormat, this.width, this.height, 0, this.pixels_buffer_offset);
/*    */ 
/*    */ 
/*    */     
/* 70 */     OpenGLException.checkGLError(false, "uploading a map texture");
/*    */   }
/*    */ 
/*    */   
/*    */   public void create(Object... args) {
/* 75 */     super.create(args);
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\graphics\TextureUpload$Normal.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */