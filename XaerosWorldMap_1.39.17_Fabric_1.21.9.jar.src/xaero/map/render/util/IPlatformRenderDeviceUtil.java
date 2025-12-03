/*    */ package xaero.map.render.util;
/*    */ 
/*    */ import com.mojang.blaze3d.systems.GpuDevice;
/*    */ import com.mojang.blaze3d.systems.RenderSystem;
/*    */ import com.mojang.blaze3d.textures.GpuTexture;
/*    */ 
/*    */ public interface IPlatformRenderDeviceUtil
/*    */ {
/*    */   default GpuDevice getRealDevice() {
/* 10 */     return RenderSystem.getDevice();
/*    */   }
/*    */   
/*    */   default GpuTexture getRealTexture(GpuTexture texture) {
/* 14 */     return texture;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\rende\\util\IPlatformRenderDeviceUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */