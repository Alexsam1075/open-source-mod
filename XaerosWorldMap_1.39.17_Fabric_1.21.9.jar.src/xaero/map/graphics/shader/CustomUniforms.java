/*    */ package xaero.map.graphics.shader;
/*    */ 
/*    */ import com.mojang.blaze3d.buffers.GpuBufferSlice;
/*    */ import com.mojang.blaze3d.pipeline.RenderPipeline;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ public class CustomUniforms
/*    */ {
/* 11 */   private static Map<RenderPipeline.UniformDescription, CustomUniform<?>> UNIFORMS = new HashMap<>();
/*    */   
/*    */   public static void register(CustomUniform<?> uniform) {
/* 14 */     if (UNIFORMS.containsKey(uniform.getDescription()))
/* 15 */       throw new IllegalArgumentException("Custom uniform already registered: " + String.valueOf(uniform.getDescription())); 
/* 16 */     UNIFORMS.put(uniform.getDescription(), uniform);
/*    */   }
/*    */   
/*    */   public static GpuBufferSlice getUpdatedUniformBuffer(RenderPipeline.UniformDescription uniformDescription) {
/* 20 */     CustomUniform<?> uniform = UNIFORMS.get(uniformDescription);
/* 21 */     if (uniform == null)
/* 22 */       return null; 
/* 23 */     return uniform.getBufferSlice();
/*    */   }
/*    */   
/*    */   public static boolean isCustom(RenderPipeline.UniformDescription uniform) {
/* 27 */     return UNIFORMS.containsKey(uniform);
/*    */   }
/*    */   
/*    */   public static void endFrame() {
/* 31 */     for (CustomUniform<?> uniform : UNIFORMS.values())
/* 32 */       uniform.endFrame(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\graphics\shader\CustomUniforms.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */