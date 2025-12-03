/*     */ package xaero.map.render.util;
/*     */ import com.mojang.blaze3d.buffers.GpuBuffer;
/*     */ import com.mojang.blaze3d.buffers.GpuBufferSlice;
/*     */ import com.mojang.blaze3d.pipeline.RenderPipeline;
/*     */ import com.mojang.blaze3d.systems.RenderPass;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import com.mojang.blaze3d.textures.GpuTextureView;
/*     */ import com.mojang.blaze3d.vertex.VertexFormat;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.OptionalDouble;
/*     */ import net.minecraft.class_10799;
/*     */ import net.minecraft.class_11219;
/*     */ import net.minecraft.class_276;
/*     */ import net.minecraft.class_287;
/*     */ import net.minecraft.class_289;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_4587;
/*     */ import net.minecraft.class_9801;
/*     */ import org.joml.Matrix4f;
/*     */ import org.joml.Matrix4fc;
/*     */ import org.joml.Vector3f;
/*     */ import org.joml.Vector4f;
/*     */ import xaero.map.graphics.shader.CustomUniforms;
/*     */ import xaero.map.platform.Services;
/*     */ 
/*     */ public class ImmediateRenderUtil {
/*  27 */   private static Vector4f SHADER_COLOR = new Vector4f(1.0F, 1.0F, 1.0F, 1.0F);
/*     */   
/*     */   private static final String RENDER_PASS_NAME = "xaero wm render pass";
/*     */   
/*     */   public static void coloredRectangle(class_4587 matrices, float x1, float y1, float x2, float y2, int color) {
/*  32 */     coloredRectangle(matrices.method_23760().method_23761(), x1, y1, x2, y2, color);
/*     */   }
/*     */   
/*     */   public static void coloredRectangle(Matrix4f matrix, float x1, float y1, float x2, float y2, int color) {
/*  36 */     coloredRectangle(matrix, x1, y1, x2, y2, color, class_10799.field_56879);
/*     */   }
/*     */   
/*     */   public static void coloredRectangle(Matrix4f matrix, float x1, float y1, float x2, float y2, int color, RenderPipeline renderPipeline) {
/*  40 */     float a = (color >> 24 & 0xFF) / 255.0F;
/*  41 */     float r = (color >> 16 & 0xFF) / 255.0F;
/*  42 */     float g = (color >> 8 & 0xFF) / 255.0F;
/*  43 */     float b = (color & 0xFF) / 255.0F;
/*  44 */     class_289 tessellator = class_289.method_1348();
/*  45 */     class_287 vertexBuffer = tessellator.method_60827(VertexFormat.class_5596.field_27382, renderPipeline.getVertexFormat());
/*  46 */     vertexBuffer.method_22918(matrix, x1, y2, 0.0F).method_22915(r, g, b, a);
/*  47 */     vertexBuffer.method_22918(matrix, x2, y2, 0.0F).method_22915(r, g, b, a);
/*  48 */     vertexBuffer.method_22918(matrix, x2, y1, 0.0F).method_22915(r, g, b, a);
/*  49 */     vertexBuffer.method_22918(matrix, x1, y1, 0.0F).method_22915(r, g, b, a);
/*  50 */     drawImmediateMeshData(vertexBuffer.method_60794(), renderPipeline);
/*     */   }
/*     */   
/*     */   public static void gradientRectangle(Matrix4f matrix, float x1, float y1, float x2, float y2, int color1, int color2) {
/*  54 */     float a1 = (color1 >> 24 & 0xFF) / 255.0F;
/*  55 */     float r1 = (color1 >> 16 & 0xFF) / 255.0F;
/*  56 */     float g1 = (color1 >> 8 & 0xFF) / 255.0F;
/*  57 */     float b1 = (color1 & 0xFF) / 255.0F;
/*  58 */     float a2 = (color2 >> 24 & 0xFF) / 255.0F;
/*  59 */     float r2 = (color2 >> 16 & 0xFF) / 255.0F;
/*  60 */     float g2 = (color2 >> 8 & 0xFF) / 255.0F;
/*  61 */     float b2 = (color2 & 0xFF) / 255.0F;
/*  62 */     class_289 tessellator = class_289.method_1348();
/*  63 */     class_287 vertexBuffer = tessellator.method_60827(VertexFormat.class_5596.field_27382, class_290.field_1576);
/*  64 */     vertexBuffer.method_22918(matrix, x1, y2, 0.0F).method_22915(r2, g2, b2, a2);
/*  65 */     vertexBuffer.method_22918(matrix, x2, y2, 0.0F).method_22915(r2, g2, b2, a2);
/*  66 */     vertexBuffer.method_22918(matrix, x2, y1, 0.0F).method_22915(r1, g1, b1, a1);
/*  67 */     vertexBuffer.method_22918(matrix, x1, y1, 0.0F).method_22915(r1, g1, b1, a1);
/*  68 */     drawImmediateMeshData(vertexBuffer.method_60794(), class_10799.field_56879);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void texturedRect(class_4587 matrixStack, float x, float y, int textureX, int textureY, float width, float height, float theight, float factor) {
/*  73 */     texturedRect(matrixStack, x, y, textureX, textureY, width, height, theight, factor, class_10799.field_56883);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void texturedRect(class_4587 matrixStack, float x, float y, int textureX, int textureY, float width, float height, float textureH, float factor, RenderPipeline renderPipeline) {
/*  78 */     texturedRect(matrixStack, x, y, textureX, textureY, width, height, textureX + width, textureY + textureH, factor, renderPipeline);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void texturedRect(class_4587 matrixStack, float x, float y, float textureX1, float textureY1, float width, float height, float textureX2, float textureY2, float factor, RenderPipeline renderPipeline) {
/*  83 */     float f = 1.0F / factor;
/*  84 */     float f1 = f;
/*  85 */     Matrix4f matrix = matrixStack.method_23760().method_23761();
/*  86 */     class_289 tessellator = class_289.method_1348();
/*  87 */     class_287 vertexBuffer = tessellator.method_60827(VertexFormat.class_5596.field_27382, renderPipeline.getVertexFormat());
/*  88 */     vertexBuffer.method_22918(matrix, x + 0.0F, y + height, 0.0F).method_22915(1.0F, 1.0F, 1.0F, 1.0F)
/*  89 */       .method_22913(textureX1 * f, textureY2 * f1);
/*  90 */     vertexBuffer.method_22918(matrix, x + width, y + height, 0.0F).method_22915(1.0F, 1.0F, 1.0F, 1.0F)
/*  91 */       .method_22913(textureX2 * f, textureY2 * f1);
/*  92 */     vertexBuffer.method_22918(matrix, x + width, y + 0.0F, 0.0F).method_22915(1.0F, 1.0F, 1.0F, 1.0F)
/*  93 */       .method_22913(textureX2 * f, textureY1 * f1);
/*  94 */     vertexBuffer.method_22918(matrix, x + 0.0F, y + 0.0F, 0.0F).method_22915(1.0F, 1.0F, 1.0F, 1.0F)
/*  95 */       .method_22913(textureX1 * f, textureY1 * f1);
/*  96 */     drawImmediateMeshData(vertexBuffer.method_60794(), renderPipeline);
/*     */   }
/*     */   
/*     */   public static void drawImmediateMeshData(class_9801 meshData, RenderPipeline renderPipeline) {
/* 100 */     drawImmediateMeshData(meshData, renderPipeline, class_310.method_1551().method_1522());
/*     */   }
/*     */   
/*     */   public static void drawImmediateMeshData(class_9801 meshData, RenderPipeline renderPipeline, class_276 target) {
/*     */     GpuBuffer gpuIndexBuffer;
/*     */     VertexFormat.class_5595 gpuIndexType;
/* 106 */     ByteBuffer indexBuffer = meshData.method_60821();
/* 107 */     if (indexBuffer == null) {
/*     */       
/* 109 */       RenderSystem.class_5590 sequentialBuffer = RenderSystem.getSequentialBuffer(meshData.method_60822().comp_752());
/* 110 */       gpuIndexBuffer = sequentialBuffer.method_68274(meshData.method_60822().comp_751());
/* 111 */       gpuIndexType = sequentialBuffer.method_31924();
/*     */     } else {
/*     */       
/* 114 */       gpuIndexBuffer = renderPipeline.getVertexFormat().uploadImmediateIndexBuffer(indexBuffer);
/* 115 */       gpuIndexType = meshData.method_60822().comp_753();
/*     */     } 
/*     */     
/* 118 */     GpuBuffer gpuVertexBuffer = renderPipeline.getVertexFormat().uploadImmediateVertexBuffer(meshData.method_60818());
/*     */ 
/*     */     
/* 121 */     GpuTextureView colorTarget = (RenderSystem.outputColorTextureOverride != null) ? RenderSystem.outputColorTextureOverride : target.method_71639();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 128 */     GpuTextureView depthTarget = target.field_1478 ? ((RenderSystem.outputDepthTextureOverride != null) ? RenderSystem.outputDepthTextureOverride : target.method_71640()) : null;
/* 129 */     class_9801 class_98011 = meshData; 
/* 130 */     try { RenderPass renderPass = createRenderPass("xaero wm render pass", renderPipeline, colorTarget, depthTarget);
/*     */       
/* 132 */       try { renderPass.setIndexBuffer(gpuIndexBuffer, gpuIndexType);
/* 133 */         renderPass.setVertexBuffer(0, gpuVertexBuffer);
/* 134 */         renderPass.drawIndexed(0, 0, meshData.method_60822().comp_751(), 1);
/* 135 */         if (renderPass != null) renderPass.close();  } catch (Throwable throwable) { if (renderPass != null) try { renderPass.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  if (class_98011 != null) class_98011.close();  } catch (Throwable throwable) { if (class_98011 != null)
/*     */         try { class_98011.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/* 139 */      } private static GpuBufferSlice getUpdatedDynamicUniforms() { return RenderSystem.getDynamicUniforms()
/* 140 */       .method_71106(
/* 141 */         (Matrix4fc)RenderSystem.getModelViewMatrix(), (Vector4fc)SHADER_COLOR, (Vector3fc)new Vector3f(), 
/*     */ 
/*     */         
/* 144 */         (Matrix4fc)RenderSystem.getTextureMatrix(), 
/* 145 */         RenderSystem.getShaderLineWidth()); }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void prepareRenderPass(RenderPass renderPass, RenderPipeline renderPipeline, GpuBufferSlice dynamicUniformsBuffer) {
/* 150 */     renderPass.setPipeline(renderPipeline);
/* 151 */     class_11219 scissorState = RenderSystem.getScissorStateForRenderTypeDraws();
/* 152 */     if (scissorState.method_72091())
/* 153 */       renderPass.enableScissor(scissorState.method_72092(), scissorState.method_72093(), scissorState.method_72094(), scissorState.method_72095()); 
/* 154 */     renderPass.setUniform("DynamicTransforms", dynamicUniformsBuffer);
/* 155 */     RenderSystem.bindDefaultUniforms(renderPass);
/* 156 */     Services.PLATFORM.getPlatformRenderUtil().onPrepareRenderPass(renderPass);
/* 157 */     for (int textureIndex = 0; textureIndex < 12; textureIndex++) {
/* 158 */       GpuTextureView gpuTexture = RenderSystem.getShaderTexture(textureIndex);
/* 159 */       if (gpuTexture != null)
/* 160 */         renderPass.bindSampler("Sampler" + textureIndex, gpuTexture); 
/*     */     } 
/* 162 */     updateCustomUniforms(renderPipeline, renderPass);
/*     */   }
/*     */   
/*     */   private static void updateCustomUniforms(RenderPipeline renderPipeline) {
/* 166 */     updateCustomUniforms(renderPipeline, null);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void updateCustomUniforms(RenderPipeline renderPipeline, RenderPass renderPass) {
/* 171 */     for (RenderPipeline.UniformDescription uniform : renderPipeline.getUniforms()) {
/* 172 */       if (!CustomUniforms.isCustom(uniform))
/*     */         continue; 
/* 174 */       GpuBufferSlice valueBuffer = CustomUniforms.getUpdatedUniformBuffer(uniform);
/* 175 */       if (valueBuffer == null)
/*     */         continue; 
/* 177 */       if (renderPass == null)
/*     */         continue; 
/* 179 */       renderPass.setUniform(uniform.name(), valueBuffer);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static RenderPass createRenderPass(String name, RenderPipeline renderPipeline, GpuTextureView colorTarget, GpuTextureView depthTarget) {
/* 184 */     GpuBufferSlice dynamicUniforms = getUpdatedDynamicUniforms();
/* 185 */     updateCustomUniforms(renderPipeline);
/*     */ 
/*     */     
/* 188 */     RenderPass renderPass = RenderSystem.getDevice().createCommandEncoder().createRenderPass(() -> name, colorTarget, 
/*     */         
/* 190 */         OptionalInt.empty(), depthTarget, 
/* 191 */         OptionalDouble.empty());
/*     */     
/* 193 */     prepareRenderPass(renderPass, renderPipeline, dynamicUniforms);
/* 194 */     return renderPass;
/*     */   }
/*     */   
/*     */   public static void setShaderColor(float red, float green, float blue, float alpha) {
/* 198 */     SHADER_COLOR.set(red, green, blue, alpha);
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\rende\\util\ImmediateRenderUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */