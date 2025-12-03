/*    */ package xaero.map.graphics;
/*    */ 
/*    */ import com.mojang.blaze3d.systems.RenderSystem;
/*    */ import com.mojang.blaze3d.textures.GpuTextureView;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.nio.ByteOrder;
/*    */ import java.nio.IntBuffer;
/*    */ import net.minecraft.class_1044;
/*    */ import net.minecraft.class_276;
/*    */ import net.minecraft.class_2960;
/*    */ import net.minecraft.class_310;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TextureUtils
/*    */ {
/*    */   public static void setTexture(int index, class_2960 textureLocation) {
/* 19 */     class_1044 textureObject = class_310.method_1551().method_1531().method_4619(textureLocation);
/* 20 */     GpuTextureView texture = (textureObject == null) ? null : textureObject.method_71659();
/* 21 */     RenderSystem.setShaderTexture(index, texture);
/*    */   }
/*    */   
/*    */   public static void clearRenderTarget(class_276 renderTarget, int color, float depth) {
/* 25 */     RenderSystem.getDevice().createCommandEncoder().clearColorAndDepthTextures(renderTarget
/* 26 */         .method_30277(), color, renderTarget.method_30278(), depth);
/*    */   }
/*    */ 
/*    */   
/*    */   public static void clearRenderTarget(class_276 renderTarget, int color) {
/* 31 */     RenderSystem.getDevice().createCommandEncoder().clearColorTexture(renderTarget.method_30277(), color);
/*    */   }
/*    */   
/*    */   public static void clearRenderTargetDepth(class_276 renderTarget, float depth) {
/* 35 */     RenderSystem.getDevice().createCommandEncoder().clearDepthTexture(renderTarget.method_30278(), depth);
/*    */   }
/*    */   
/*    */   public static IntBuffer allocateLittleEndianIntBuffer(int capacity) {
/* 39 */     return ByteBuffer.allocateDirect(capacity * 4).order(ByteOrder.LITTLE_ENDIAN).asIntBuffer();
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\graphics\TextureUtils.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */