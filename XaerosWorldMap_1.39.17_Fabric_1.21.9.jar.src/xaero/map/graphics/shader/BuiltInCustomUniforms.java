/*    */ package xaero.map.graphics.shader;
/*    */ 
/*    */ import com.mojang.blaze3d.pipeline.RenderPipeline;
/*    */ import net.minecraft.class_10789;
/*    */ 
/*    */ public class BuiltInCustomUniforms
/*    */ {
/*  8 */   public static final CustomUniform<Float> BRIGHTNESS = new CustomUniform<>(new RenderPipeline.UniformDescription("BrightnessBlock", class_10789.field_60031), BuiltInCustomUniformValueTypes.FLOAT, 32);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 14 */   public static final CustomUniform<Integer> WITH_LIGHT = new CustomUniform<>(new RenderPipeline.UniformDescription("WithLightBlock", class_10789.field_60031), BuiltInCustomUniformValueTypes.INT, 32);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 22 */     registerAll();
/*    */   }
/*    */   
/*    */   private static void registerAll() {
/* 26 */     CustomUniforms.register(BRIGHTNESS);
/* 27 */     CustomUniforms.register(WITH_LIGHT);
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\graphics\shader\BuiltInCustomUniforms.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */