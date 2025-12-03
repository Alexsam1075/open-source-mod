/*    */ package xaero.map.exception;
/*    */ 
/*    */ import org.lwjgl.opengl.GL11;
/*    */ import xaero.map.WorldMap;
/*    */ import xaero.map.graphics.OpenGlHelper;
/*    */ 
/*    */ public class OpenGLException
/*    */   extends RuntimeException {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public OpenGLException(int error) {
/* 12 */     super("OpenGL error: " + error);
/*    */   }
/*    */   
/*    */   public static void checkGLError() throws OpenGLException {
/* 16 */     checkGLError(true, null);
/*    */   }
/*    */   
/*    */   public static void checkGLError(boolean crash, String where) throws OpenGLException {
/* 20 */     if (!OpenGlHelper.isUsingOpenGL())
/*    */       return; 
/* 22 */     int error = GL11.glGetError();
/* 23 */     if (error != 0) {
/* 24 */       if (crash)
/* 25 */         throw new OpenGLException(error); 
/* 26 */       WorldMap.LOGGER.warn("Ignoring OpenGL error " + error + " when " + where + ". Most likely caused by another mod.");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\exception\OpenGLException.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */