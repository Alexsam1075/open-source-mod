/*    */ package xaero.map.radar.tracker;
/*    */ 
/*    */ import com.mojang.blaze3d.buffers.GpuBufferSlice;
/*    */ import com.mojang.blaze3d.systems.RenderSystem;
/*    */ import net.minecraft.class_10366;
/*    */ import net.minecraft.class_11278;
/*    */ import net.minecraft.class_1657;
/*    */ import net.minecraft.class_276;
/*    */ import net.minecraft.class_2960;
/*    */ import net.minecraft.class_310;
/*    */ import net.minecraft.class_4587;
/*    */ import org.joml.Matrix4fStack;
/*    */ import xaero.map.element.MapElementGraphics;
/*    */ import xaero.map.graphics.ImprovedFramebuffer;
/*    */ import xaero.map.graphics.TextureUtils;
/*    */ import xaero.map.icon.XaeroIcon;
/*    */ import xaero.map.icon.XaeroIconAtlas;
/*    */ import xaero.map.misc.Misc;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TrackedPlayerIconPrerenderer
/*    */ {
/*    */   private ImprovedFramebuffer renderFramebuffer;
/*    */   private XaeroIconAtlas lastAtlas;
/* 30 */   private final PlayerTrackerIconRenderer renderer = new PlayerTrackerIconRenderer();
/*    */   private GpuBufferSlice orthoProjection;
/*    */   
/*    */   public void prerender(MapElementGraphics guiGraphics, XaeroIcon icon, class_1657 player, int iconWidth, class_2960 skinTextureLocation, PlayerTrackerMapElement<?> mapElement) {
/* 34 */     if (this.renderFramebuffer == null) {
/* 35 */       this.renderFramebuffer = new ImprovedFramebuffer(icon.getTextureAtlas().getWidth(), icon.getTextureAtlas().getWidth(), true);
/* 36 */       this.renderFramebuffer.closeColorTexture();
/* 37 */       this.renderFramebuffer.setColorTexture(null, null);
/* 38 */       class_11278 orthoProjectionCache = new class_11278("tracked player icon prerender", -1.0F, 1000.0F, true);
/*    */       
/* 40 */       this.orthoProjection = orthoProjectionCache.method_71092(this.renderFramebuffer.field_1482, this.renderFramebuffer.field_1481);
/*    */     } 
/*    */     
/* 43 */     guiGraphics.flush();
/* 44 */     this.renderFramebuffer.bindAsMainTarget(false);
/* 45 */     this.renderFramebuffer.setColorTexture(icon.getTextureAtlas());
/*    */     
/* 47 */     if (this.lastAtlas != icon.getTextureAtlas()) {
/* 48 */       TextureUtils.clearRenderTarget((class_276)this.renderFramebuffer, 0, 1.0F);
/* 49 */       this.lastAtlas = icon.getTextureAtlas();
/*    */     } 
/*    */     
/* 52 */     RenderSystem.setProjectionMatrix(this.orthoProjection, class_10366.field_54954);
/* 53 */     Matrix4fStack shaderMatrixStack = RenderSystem.getModelViewStack();
/* 54 */     shaderMatrixStack.pushMatrix();
/* 55 */     shaderMatrixStack.identity();
/*    */     
/* 57 */     class_4587 matrixStack = guiGraphics.pose();
/* 58 */     matrixStack.method_22903();
/* 59 */     matrixStack.method_34426();
/* 60 */     matrixStack.method_46416(icon.getOffsetX(), (this.renderFramebuffer.field_1481 - iconWidth - icon.getOffsetY()), 0.0F);
/*    */     
/* 62 */     matrixStack.method_46416((iconWidth / 2), (iconWidth / 2), 0.0F);
/* 63 */     matrixStack.method_22905(3.0F, 3.0F, 1.0F);
/*    */     
/* 65 */     guiGraphics.fill(-5, -5, 5, 5, -1);
/* 66 */     this.renderer.renderIcon(guiGraphics, player, skinTextureLocation);
/*    */     
/* 68 */     matrixStack.method_22909();
/* 69 */     guiGraphics.flush();
/*    */     
/* 71 */     class_310 mc = class_310.method_1551();
/* 72 */     Misc.minecraftOrtho(mc, false);
/* 73 */     shaderMatrixStack.popMatrix();
/*    */     
/* 75 */     this.renderFramebuffer.bindDefaultFramebuffer(mc);
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\radar\tracker\TrackedPlayerIconPrerenderer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */