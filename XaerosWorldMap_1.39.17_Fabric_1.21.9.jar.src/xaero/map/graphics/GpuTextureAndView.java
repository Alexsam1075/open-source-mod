/*    */ package xaero.map.graphics;
/*    */ 
/*    */ import com.mojang.blaze3d.textures.GpuTexture;
/*    */ import com.mojang.blaze3d.textures.GpuTextureView;
/*    */ 
/*    */ 
/*    */ public class GpuTextureAndView
/*    */ {
/*    */   public final GpuTexture texture;
/*    */   public final GpuTextureView view;
/*    */   
/*    */   public GpuTextureAndView(GpuTexture texture, GpuTextureView view) {
/* 13 */     this.texture = texture;
/* 14 */     this.view = view;
/*    */   }
/*    */   
/*    */   public void close() {
/* 18 */     this.view.close();
/* 19 */     this.texture.close();
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\graphics\GpuTextureAndView.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */