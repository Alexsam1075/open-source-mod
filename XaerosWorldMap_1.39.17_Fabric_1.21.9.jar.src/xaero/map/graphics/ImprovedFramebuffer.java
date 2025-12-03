/*    */ package xaero.map.graphics;
/*    */ 
/*    */ import com.mojang.blaze3d.systems.RenderSystem;
/*    */ import com.mojang.blaze3d.textures.GpuTexture;
/*    */ import com.mojang.blaze3d.textures.GpuTextureView;
/*    */ import java.lang.reflect.Field;
/*    */ import net.minecraft.class_276;
/*    */ import net.minecraft.class_310;
/*    */ import net.minecraft.class_6367;
/*    */ import xaero.map.icon.XaeroIconAtlas;
/*    */ import xaero.map.misc.Misc;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ImprovedFramebuffer
/*    */   extends class_6367
/*    */ {
/* 20 */   private static Field MAIN_RENDER_TARGET_FIELD = Misc.getFieldReflection(class_310.class, "mainRenderTarget", "field_1689", "Lnet/minecraft/class_276;", "f_91042_");
/*    */   private static class_276 mainRenderTargetBackup;
/*    */   private static GpuTextureView outputColorTextureOverrideBU;
/*    */   private static GpuTextureView outputDepthTextureOverrideBU;
/*    */   
/*    */   public ImprovedFramebuffer(int width, int height, boolean useDepthIn) {
/* 26 */     super(null, width, height, useDepthIn);
/*    */   }
/*    */   
/*    */   public void bindDefaultFramebuffer(class_310 mc) {
/* 30 */     restoreMainRenderTarget();
/*    */   }
/*    */ 
/*    */   
/*    */   public void bindRead() {
/* 35 */     RenderSystem.setShaderTexture(0, this.field_60567);
/*    */   }
/*    */   
/*    */   public void generateMipmaps() {
/* 39 */     OpenGlHelper.generateMipmaps(this.field_1475);
/*    */   }
/*    */   
/*    */   private void forceAsMainRenderTarget() {
/* 43 */     if (mainRenderTargetBackup == null) {
/* 44 */       mainRenderTargetBackup = (class_276)Misc.getReflectFieldValue(class_310.method_1551(), MAIN_RENDER_TARGET_FIELD);
/* 45 */       outputColorTextureOverrideBU = RenderSystem.outputColorTextureOverride;
/* 46 */       outputDepthTextureOverrideBU = RenderSystem.outputDepthTextureOverride;
/* 47 */       RenderSystem.outputColorTextureOverride = null;
/* 48 */       RenderSystem.outputDepthTextureOverride = null;
/*    */     } 
/* 50 */     Misc.setReflectFieldValue(class_310.method_1551(), MAIN_RENDER_TARGET_FIELD, this);
/*    */   }
/*    */   
/*    */   public static void restoreMainRenderTarget() {
/* 54 */     if (mainRenderTargetBackup != null) {
/* 55 */       Misc.setReflectFieldValue(class_310.method_1551(), MAIN_RENDER_TARGET_FIELD, mainRenderTargetBackup);
/* 56 */       if (RenderSystem.outputColorTextureOverride == null && RenderSystem.outputDepthTextureOverride == null) {
/* 57 */         RenderSystem.outputColorTextureOverride = outputColorTextureOverrideBU;
/* 58 */         RenderSystem.outputDepthTextureOverride = outputDepthTextureOverrideBU;
/*    */       } 
/* 60 */       mainRenderTargetBackup = null;
/* 61 */       outputColorTextureOverrideBU = null;
/* 62 */       outputDepthTextureOverrideBU = null;
/*    */     } 
/*    */   }
/*    */   
/*    */   public void bindAsMainTarget(boolean viewport) {
/* 67 */     forceAsMainRenderTarget();
/*    */   }
/*    */   
/*    */   public void setColorTexture(GpuTexture texture, GpuTextureView textureView) {
/* 71 */     this.field_1475 = texture;
/* 72 */     this.field_60567 = textureView;
/*    */   }
/*    */   
/*    */   public void setColorTexture(XaeroIconAtlas atlas) {
/* 76 */     setColorTexture(atlas.getTextureId(), atlas.getTextureView());
/*    */   }
/*    */   
/*    */   public void setDepthTexture(GpuTexture depthTexture, GpuTextureView textureView) {
/* 80 */     this.field_56739 = depthTexture;
/* 81 */     this.field_60568 = textureView;
/*    */   }
/*    */   
/*    */   public void closeColorTexture() {
/* 85 */     this.field_60567.close();
/* 86 */     this.field_1475.close();
/*    */   }
/*    */   
/*    */   public void closeDepthTexture() {
/* 90 */     this.field_60568.close();
/* 91 */     this.field_56739.close();
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\graphics\ImprovedFramebuffer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */