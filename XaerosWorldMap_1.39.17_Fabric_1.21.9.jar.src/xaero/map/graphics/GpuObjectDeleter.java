/*    */ package xaero.map.graphics;
/*    */ 
/*    */ import com.mojang.blaze3d.buffers.GpuBuffer;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GpuObjectDeleter
/*    */ {
/*    */   private static final int DELETE_PER_FRAME = 5;
/* 12 */   private ArrayList<GpuTextureAndView> texturesToDelete = new ArrayList<>();
/* 13 */   private ArrayList<GpuBuffer> buffersToDelete = new ArrayList<>();
/*    */   
/*    */   public void work() {
/* 16 */     if (!this.texturesToDelete.isEmpty()) {
/*    */       do {
/* 18 */         synchronized (this.texturesToDelete) {
/* 19 */           OpenGlHelper.deleteTextures(this.texturesToDelete, 5);
/*    */         } 
/* 21 */       } while (this.texturesToDelete.size() > 640);
/*    */     }
/* 23 */     if (!this.buffersToDelete.isEmpty()) {
/*    */       do {
/* 25 */         synchronized (this.buffersToDelete) {
/* 26 */           OpenGlHelper.deleteBuffers(this.buffersToDelete, 5);
/*    */         } 
/* 28 */       } while (this.buffersToDelete.size() > 640);
/*    */     }
/*    */   }
/*    */   
/*    */   public void requestTextureDeletion(GpuTextureAndView texture) {
/* 33 */     synchronized (this.texturesToDelete) {
/* 34 */       this.texturesToDelete.add(texture);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void requestBufferToDelete(GpuBuffer bufferId) {
/* 39 */     synchronized (this.buffersToDelete) {
/* 40 */       this.buffersToDelete.add(bufferId);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\graphics\GpuObjectDeleter.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */