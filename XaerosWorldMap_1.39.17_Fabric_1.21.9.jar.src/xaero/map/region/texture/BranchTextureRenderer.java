/*    */ package xaero.map.region.texture;
/*    */ 
/*    */ import com.mojang.blaze3d.buffers.GpuBufferSlice;
/*    */ import com.mojang.blaze3d.systems.RenderSystem;
/*    */ import net.minecraft.class_10366;
/*    */ import net.minecraft.class_11278;
/*    */ import net.minecraft.class_276;
/*    */ import net.minecraft.class_310;
/*    */ import net.minecraft.class_4587;
/*    */ import org.joml.Matrix4fStack;
/*    */ import xaero.map.exception.OpenGLException;
/*    */ import xaero.map.graphics.CustomRenderTypes;
/*    */ import xaero.map.graphics.GpuTextureAndView;
/*    */ import xaero.map.graphics.ImprovedFramebuffer;
/*    */ import xaero.map.graphics.MapRenderHelper;
/*    */ import xaero.map.graphics.TextureUtils;
/*    */ import xaero.map.graphics.renderer.multitexture.MultiTextureRenderTypeRenderer;
/*    */ import xaero.map.graphics.renderer.multitexture.MultiTextureRenderTypeRendererProvider;
/*    */ import xaero.map.misc.Misc;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BranchTextureRenderer
/*    */ {
/*    */   private ImprovedFramebuffer renderFBO;
/*    */   private GpuTextureAndView glEmptyTexture;
/* 28 */   private class_4587 matrixStack = new class_4587(); private GpuBufferSlice projectionMatrix; public BranchTextureRenderer() {
/* 29 */     class_11278 orthoProjectionCache = new class_11278("branch renderer", -1.0F, 1.0F, true);
/* 30 */     this.projectionMatrix = orthoProjectionCache.method_71092(64.0F, 64.0F);
/* 31 */     this.rendererProvider = new MultiTextureRenderTypeRendererProvider(1);
/*    */   }
/*    */   private MultiTextureRenderTypeRendererProvider rendererProvider;
/*    */   public GpuTextureAndView getEmptyTexture() {
/* 35 */     ensureRenderTarget();
/* 36 */     return this.glEmptyTexture;
/*    */   }
/*    */   
/*    */   private void ensureRenderTarget() {
/* 40 */     if (this.renderFBO == null) {
/* 41 */       this.renderFBO = new ImprovedFramebuffer(64, 64, false);
/* 42 */       this.glEmptyTexture = new GpuTextureAndView(this.renderFBO.method_30277(), this.renderFBO.method_71639());
/* 43 */       TextureUtils.clearRenderTarget((class_276)this.renderFBO, -16777216);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void render(GpuTextureAndView destTexture, GpuTextureAndView srcTextureTopLeft, GpuTextureAndView srcTextureTopRight, GpuTextureAndView srcTextureBottomLeft, GpuTextureAndView srcTextureBottomRight, class_276 defaultFramebuffer, boolean justAllocated) {
/* 48 */     ensureRenderTarget();
/* 49 */     this.renderFBO.bindAsMainTarget(true);
/* 50 */     this.renderFBO.setColorTexture(destTexture.texture, destTexture.view);
/*    */ 
/*    */ 
/*    */     
/* 54 */     OpenGLException.checkGLError();
/*    */     
/* 56 */     Matrix4fStack shaderMatrixStack = RenderSystem.getModelViewStack();
/* 57 */     shaderMatrixStack.pushMatrix();
/* 58 */     shaderMatrixStack.identity();
/* 59 */     RenderSystem.setProjectionMatrix(this.projectionMatrix, class_10366.field_54954);
/*    */     
/* 61 */     if (justAllocated) {
/* 62 */       TextureUtils.clearRenderTarget((class_276)this.renderFBO, -16777216);
/*    */     }
/* 64 */     MultiTextureRenderTypeRenderer renderer = this.rendererProvider.getRenderer(MultiTextureRenderTypeRendererProvider::defaultTextureBind, CustomRenderTypes.MAP_BRANCH);
/*    */ 
/*    */     
/* 67 */     if (srcTextureTopLeft != null)
/* 68 */       renderCorner(srcTextureTopLeft, 0, 0, renderer); 
/* 69 */     if (srcTextureTopRight != null)
/* 70 */       renderCorner(srcTextureTopRight, 1, 0, renderer); 
/* 71 */     if (srcTextureBottomLeft != null)
/* 72 */       renderCorner(srcTextureBottomLeft, 0, 1, renderer); 
/* 73 */     if (srcTextureBottomRight != null) {
/* 74 */       renderCorner(srcTextureBottomRight, 1, 1, renderer);
/*    */     }
/* 76 */     this.rendererProvider.draw(renderer);
/* 77 */     OpenGLException.checkGLError(false, "updating a map branch texture");
/*    */     
/* 79 */     shaderMatrixStack.popMatrix();
/* 80 */     class_310 mc = class_310.method_1551();
/* 81 */     Misc.minecraftOrtho(mc, false);
/*    */ 
/*    */     
/* 84 */     this.renderFBO.bindDefaultFramebuffer(mc);
/* 85 */     OpenGLException.checkGLError();
/*    */   }
/*    */   
/*    */   private boolean renderCorner(GpuTextureAndView srcTexture, int cornerX, int cornerY, MultiTextureRenderTypeRenderer renderer) {
/* 89 */     int xOffset = cornerX * 32;
/* 90 */     int yOffset = (1 - cornerY) * 32;
/* 91 */     MapRenderHelper.renderBranchUpdate(srcTexture.texture, xOffset, yOffset, 32.0F, 32.0F, 0, 64, 64.0F, -64.0F, 64.0F, 64.0F, renderer);
/* 92 */     return false;
/*    */   }
/*    */   
/*    */   public GpuTextureAndView getGlEmptyTexture() {
/* 96 */     return this.glEmptyTexture;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\region\texture\BranchTextureRenderer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */