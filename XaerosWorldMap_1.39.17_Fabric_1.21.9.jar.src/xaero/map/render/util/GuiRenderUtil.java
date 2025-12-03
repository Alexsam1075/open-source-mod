/*    */ package xaero.map.render.util;
/*    */ 
/*    */ import com.mojang.blaze3d.pipeline.RenderPipeline;
/*    */ import com.mojang.blaze3d.textures.GpuTextureView;
/*    */ import java.lang.reflect.Method;
/*    */ import net.minecraft.class_310;
/*    */ import net.minecraft.class_332;
/*    */ import net.minecraft.class_758;
/*    */ import xaero.map.core.IGameRenderer;
/*    */ import xaero.map.misc.Misc;
/*    */ 
/*    */ 
/*    */ public class GuiRenderUtil
/*    */ {
/*    */   private static Method submitBlitMethod;
/*    */   
/*    */   public static void flushGUI() {
/* 18 */     IGameRenderer gameRenderer = (IGameRenderer)(class_310.method_1551()).field_1773;
/* 19 */     gameRenderer.xaero_wm_getGuiRenderer().method_70890(gameRenderer
/* 20 */         .xaero_wm_getFogRenderer().method_71109(class_758.class_4596.field_60101));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void submitBlit(class_332 guiGraphics, RenderPipeline pipeline, GpuTextureView texture, int x0, int y0, int x1, int y1, float u0, float u1, float v0, float v1, int color) {
/* 28 */     if (submitBlitMethod == null) {
/* 29 */       submitBlitMethod = Misc.getMethodReflection(class_332.class, "submitBlit", "method_70847", "(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lcom/mojang/blaze3d/textures/GpuTextureView;IIIIFFFFI)V", "submitBlit", new Class[] { RenderPipeline.class, GpuTextureView.class, int.class, int.class, int.class, int.class, float.class, float.class, float.class, float.class, int.class });
/*    */     }
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
/* 48 */     Misc.getReflectMethodValue(guiGraphics, submitBlitMethod, new Object[] { pipeline, texture, 
/* 49 */           Integer.valueOf(x0), Integer.valueOf(y0), Integer.valueOf(x1), Integer.valueOf(y1), Float.valueOf(u0), Float.valueOf(u1), Float.valueOf(v0), Float.valueOf(v1), Integer.valueOf(color) });
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\rende\\util\GuiRenderUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */