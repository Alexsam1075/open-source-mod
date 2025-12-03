/*    */ package xaero.map.graphics;
/*    */ 
/*    */ import java.nio.ByteBuffer;
/*    */ import java.nio.IntBuffer;
/*    */ import org.lwjgl.opengl.GL15;
/*    */ import xaero.map.WorldMap;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PixelBuffers
/*    */ {
/* 14 */   private static int buffersType = 0;
/*    */ 
/*    */   
/*    */   private static int innerGenBuffers() {
/* 18 */     switch (buffersType) {
/*    */       case 0:
/* 20 */         return GL15.glGenBuffers();
/*    */     } 
/* 22 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public static int glGenBuffers() {
/* 27 */     int result, attempts = 5;
/*    */     do {
/* 29 */       result = innerGenBuffers();
/* 30 */     } while (--attempts > 0 && result == 0);
/* 31 */     if (result == 0)
/* 32 */       WorldMap.LOGGER.error("Failed to generate a PBO after multiple attempts. Likely caused by previous errors from other mods."); 
/* 33 */     return result;
/*    */   }
/*    */   
/*    */   public static void glBindBuffer(int target, int buffer) {
/* 37 */     switch (buffersType) {
/*    */       case 0:
/* 39 */         GL15.glBindBuffer(target, buffer);
/*    */         break;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void glBufferData(int target, long size, int usage) {
/* 45 */     switch (buffersType) {
/*    */       case 0:
/* 47 */         GL15.glBufferData(target, size, usage);
/*    */         break;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static ByteBuffer glMapBuffer(int target, int access, long length, ByteBuffer old_buffer) {
/* 53 */     switch (buffersType) {
/*    */       case 0:
/* 55 */         return GL15.glMapBuffer(target, access, length, old_buffer);
/*    */     } 
/* 57 */     return null;
/*    */   }
/*    */   
/*    */   public static boolean glUnmapBuffer(int target) {
/* 61 */     switch (buffersType) {
/*    */       case 0:
/* 63 */         return GL15.glUnmapBuffer(target);
/*    */     } 
/* 65 */     return false;
/*    */   }
/*    */   
/*    */   public static void glDeleteBuffers(int buffer) {
/* 69 */     switch (buffersType) {
/*    */       case 0:
/* 71 */         GL15.glDeleteBuffers(buffer);
/*    */         break;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void glDeleteBuffers(IntBuffer buffers) {
/* 77 */     switch (buffersType) {
/*    */       case 0:
/* 79 */         GL15.glDeleteBuffers(buffers);
/*    */         break;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static ByteBuffer glMapBuffer(int target, int access) {
/* 85 */     switch (buffersType) {
/*    */       case 0:
/* 87 */         return GL15.glMapBuffer(target, access);
/*    */     } 
/* 89 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\graphics\PixelBuffers.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */