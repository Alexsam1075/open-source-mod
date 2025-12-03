/*     */ package xaero.map.graphics;
/*     */ 
/*     */ import com.mojang.blaze3d.pipeline.BlendFunction;
/*     */ import com.mojang.blaze3d.pipeline.RenderPipeline;
/*     */ import com.mojang.blaze3d.platform.DepthTestFunction;
/*     */ import com.mojang.blaze3d.platform.DestFactor;
/*     */ import com.mojang.blaze3d.platform.SourceFactor;
/*     */ import com.mojang.blaze3d.vertex.VertexFormat;
/*     */ import com.mojang.blaze3d.vertex.VertexFormatElement;
/*     */ import java.lang.reflect.Method;
/*     */ import net.minecraft.class_10789;
/*     */ import net.minecraft.class_1921;
/*     */ import net.minecraft.class_276;
/*     */ import net.minecraft.class_290;
/*     */ import net.minecraft.class_2960;
/*     */ import net.minecraft.class_4668;
/*     */ import net.minecraft.class_9801;
/*     */ import xaero.map.WorldMap;
/*     */ import xaero.map.graphics.shader.BuiltInCustomUniforms;
/*     */ import xaero.map.graphics.shader.MapShaders;
/*     */ import xaero.map.misc.Misc;
/*     */ 
/*     */ 
/*     */ public class CustomRenderTypes
/*     */   extends class_1921
/*     */ {
/*  27 */   public static final VertexFormat POSITION_COLOR_TEX = VertexFormat.builder()
/*  28 */     .add("Position", VertexFormatElement.POSITION)
/*  29 */     .add("Color", VertexFormatElement.COLOR)
/*  30 */     .add("UV0", VertexFormatElement.UV0)
/*  31 */     .build();
/*     */   
/*  33 */   protected static final BlendFunction TRANSLUCENT_TRANSPARENCY = new BlendFunction(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ONE_MINUS_SRC_ALPHA);
/*     */ 
/*     */ 
/*     */   
/*  37 */   protected static final BlendFunction DEST_TRANSPARENCY = new BlendFunction(SourceFactor.ONE, DestFactor.ZERO, SourceFactor.ZERO, DestFactor.ONE);
/*     */ 
/*     */ 
/*     */   
/*  41 */   protected static final BlendFunction PREMULTIPLIED_TRANSPARENCY = new BlendFunction(SourceFactor.ONE, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ONE_MINUS_SRC_ALPHA);
/*     */ 
/*     */ 
/*     */   
/*  45 */   protected static final BlendFunction ADD_ALPHA_TRANSPARENCY = new BlendFunction(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ONE);
/*     */ 
/*     */ 
/*     */   
/*     */   public static final RenderPipeline RP_POSITION_COLOR_TEX;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final RenderPipeline RP_POSITION_COLOR_TEX_NO_BLEND;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final RenderPipeline RP_POSITION_COLOR_TEX_NO_CULL;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final RenderPipeline RP_POSITION_COLOR;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final RenderPipeline RP_POSITION_COLOR_NO_BLEND;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final RenderPipeline RP_POSITION_COLOR_NO_CULL;
/*     */ 
/*     */   
/*     */   public static final RenderPipeline RP_MAP_BRANCH;
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*     */     try {
/*  78 */       Class<?> compositeStateClass = Misc.getClassForName("net.minecraft.class_1921$class_4688", "net.minecraft.client.renderer.RenderType$CompositeState");
/*     */ 
/*     */ 
/*     */       
/*  82 */       compositeStateBuilderMethod = Misc.getMethodReflection(compositeStateClass, "builder", "method_23598", "()Lnet/minecraft/class_1921$class_4688$class_4689;", "m_110628_", new Class[0]);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  89 */       compositeStateBuilderCreateCompositeStateMethod = Misc.getMethodReflection(class_1921.class_4688.class_4689.class, "createCompositeState", "method_24297", "(Lnet/minecraft/class_1921$class_4750;)Lnet/minecraft/class_1921$class_4688;", "m_110689_", new Class[] { class_1921.class_4750.class });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  97 */       compositeStateBuilderSetTextureStateMethod = Misc.getMethodReflection(class_1921.class_4688.class_4689.class, "setTextureState", "method_34577", "(Lnet/minecraft/class_4668$class_5939;)Lnet/minecraft/class_1921$class_4688$class_4689;", "m_173290_", new Class[] { class_4668.class_5939.class });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 105 */       compositeStateBuilderSetLightmapStateMethod = Misc.getMethodReflection(class_1921.class_4688.class_4689.class, "setLightmapState", "method_23608", "(Lnet/minecraft/class_4668$class_4676;)Lnet/minecraft/class_1921$class_4688$class_4689;", "m_110671_", new Class[] { class_4668.class_4676.class });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 113 */       compositeStateBuilderSetOverlayStateMethod = Misc.getMethodReflection(class_1921.class_4688.class_4689.class, "setOverlayState", "method_23611", "(Lnet/minecraft/class_4668$class_4679;)Lnet/minecraft/class_1921$class_4688$class_4689;", "m_110677_", new Class[] { class_4668.class_4679.class });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 121 */       compositeStateBuilderSetLayeringStateMethod = Misc.getMethodReflection(class_1921.class_4688.class_4689.class, "setLayeringState", "method_23607", "(Lnet/minecraft/class_4668$class_4675;)Lnet/minecraft/class_1921$class_4688$class_4689;", "m_110669_", new Class[] { class_4668.class_4675.class });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 129 */       compositeStateBuilderSetOutputStateMethod = Misc.getMethodReflection(class_1921.class_4688.class_4689.class, "setOutputState", "method_23610", "(Lnet/minecraft/class_4668$class_4678;)Lnet/minecraft/class_1921$class_4688$class_4689;", "m_110675_", new Class[] { class_4668.class_4678.class });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 137 */       compositeStateBuilderSetTexturingStateMethod = Misc.getMethodReflection(class_1921.class_4688.class_4689.class, "setTexturingState", "method_23614", "(Lnet/minecraft/class_4668$class_4684;)Lnet/minecraft/class_1921$class_4688$class_4689;", "m_110683_", new Class[] { class_4668.class_4684.class });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 145 */       compositeStateBuilderSetLineStateMethod = Misc.getMethodReflection(class_1921.class_4688.class_4689.class, "setLineState", "method_23609", "(Lnet/minecraft/class_4668$class_4677;)Lnet/minecraft/class_1921$class_4688$class_4689;", "m_110673_", new Class[] { class_4668.class_4677.class });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 153 */       renderTypeCreateMethod = Misc.getMethodReflection(class_1921.class, "create", "method_24049", "(Ljava/lang/String;IZZLcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/class_1921$class_4688;)Lnet/minecraft/class_1921$class_4687;", "m_173215_", new Class[] { String.class, int.class, boolean.class, boolean.class, RenderPipeline.class, class_1921.class_4688.class });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 166 */     catch (ClassNotFoundException e) {
/* 167 */       throw new RuntimeException(e);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 173 */     RenderPipeline.Snippet MATRICES_SNIPPET = RenderPipeline.builder(new RenderPipeline.Snippet[0]).withUniform("DynamicTransforms", class_10789.field_60031).withUniform("Projection", class_10789.field_60031).buildSnippet();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 180 */     RenderPipeline.Snippet POSITION_COLOR_TEX_SNIPPET = RenderPipeline.builder(new RenderPipeline.Snippet[] { MATRICES_SNIPPET }).withVertexShader(MapShaders.POSITION_COLOR_TEX).withFragmentShader(MapShaders.POSITION_COLOR_TEX).withVertexFormat(POSITION_COLOR_TEX, VertexFormat.class_5596.field_27382).withSampler("Sampler0").buildSnippet();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 186 */     RenderPipeline.Snippet POSITION_COLOR_SNIPPET = RenderPipeline.builder(new RenderPipeline.Snippet[] { MATRICES_SNIPPET }).withVertexShader(MapShaders.POSITION_COLOR).withFragmentShader(MapShaders.POSITION_COLOR).withVertexFormat(class_290.field_1576, VertexFormat.class_5596.field_27382).buildSnippet();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 191 */     RP_POSITION_COLOR_TEX = RenderPipeline.builder(new RenderPipeline.Snippet[] { POSITION_COLOR_TEX_SNIPPET }).withLocation(class_2960.method_60655("xaeroworldmap", "pipeline/pos_col_tex")).withBlend(TRANSLUCENT_TRANSPARENCY).build();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 196 */     RP_POSITION_COLOR_TEX_NO_BLEND = RenderPipeline.builder(new RenderPipeline.Snippet[] { POSITION_COLOR_TEX_SNIPPET }).withLocation(class_2960.method_60655("xaeroworldmap", "pipeline/pos_col_tex_no_blend")).withoutBlend().build();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 202 */     RP_POSITION_COLOR_TEX_NO_CULL = RenderPipeline.builder(new RenderPipeline.Snippet[] { POSITION_COLOR_TEX_SNIPPET }).withLocation(class_2960.method_60655("xaeroworldmap", "pipeline/pos_col_tex_no_cull")).withBlend(TRANSLUCENT_TRANSPARENCY).withCull(false).build();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 207 */     RP_POSITION_COLOR = RenderPipeline.builder(new RenderPipeline.Snippet[] { POSITION_COLOR_SNIPPET }).withLocation(class_2960.method_60655("xaeroworldmap", "pipeline/pos_col")).withBlend(TRANSLUCENT_TRANSPARENCY).build();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 212 */     RP_POSITION_COLOR_NO_BLEND = RenderPipeline.builder(new RenderPipeline.Snippet[] { POSITION_COLOR_SNIPPET }).withLocation(class_2960.method_60655("xaeroworldmap", "pipeline/pos_col_no_blend")).withoutBlend().build();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 218 */     RP_POSITION_COLOR_NO_CULL = RenderPipeline.builder(new RenderPipeline.Snippet[] { POSITION_COLOR_SNIPPET }).withLocation(class_2960.method_60655("xaeroworldmap", "pipeline/pos_col_no_cull")).withBlend(TRANSLUCENT_TRANSPARENCY).withCull(false).build();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 230 */     RP_MAP_BRANCH = RenderPipeline.builder(new RenderPipeline.Snippet[] { MATRICES_SNIPPET }).withLocation(class_2960.method_60655("xaeroworldmap", "pipeline/map_branch")).withVertexShader(MapShaders.WORLD_MAP_BRANCH).withFragmentShader(MapShaders.WORLD_MAP_BRANCH).withVertexFormat(class_290.field_1585, VertexFormat.class_5596.field_27382).withSampler("Sampler0").withoutBlend().withCull(false).withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST).withDepthWrite(false).build();
/*     */   }
/* 232 */   public static final class_1921 GUI = createRenderType("xaero_wm_gui", 786432, false, false, RP_POSITION_COLOR_TEX_NO_CULL, 
/* 233 */       getStateBuilder()
/* 234 */       .setTextureState((class_4668.class_5939)new class_4668.class_4683(WorldMap.guiTextures, false))
/* 235 */       .setOutputState(class_4668.field_21358), class_1921.class_4750.field_21853);
/*     */   
/*     */   public static final class_1921 GUI_PREMULTIPLIED;
/*     */   public static final class_1921 MAP;
/*     */   
/*     */   static {
/* 241 */     RenderPipeline POSITION_COLOR_TEX_PREMULTIPLIED_PL = RenderPipeline.builder(new RenderPipeline.Snippet[] { POSITION_COLOR_TEX_SNIPPET }).withLocation(class_2960.method_60655("xaeroworldmap", "pipeline/pos_col_tex_premultiplied")).withBlend(PREMULTIPLIED_TRANSPARENCY).withCull(false).build();
/*     */     
/* 243 */     GUI_PREMULTIPLIED = createRenderType("xaero_wm_gui_pre", 786432, false, false, POSITION_COLOR_TEX_PREMULTIPLIED_PL, 
/* 244 */         getStateBuilder()
/* 245 */         .setTextureState((class_4668.class_5939)new class_4668.class_4683(WorldMap.guiTextures, false))
/* 246 */         .setOutputState(class_4668.field_21358), class_1921.class_4750.field_21853);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 258 */     RenderPipeline MAP_PL = RenderPipeline.builder(new RenderPipeline.Snippet[] { MATRICES_SNIPPET }).withLocation(class_2960.method_60655("xaeroworldmap", "pipeline/map")).withVertexShader(MapShaders.WORLD_MAP).withFragmentShader(MapShaders.WORLD_MAP).withVertexFormat(class_290.field_1585, VertexFormat.class_5596.field_27382).withSampler("Sampler0").withUniform(BuiltInCustomUniforms.BRIGHTNESS.name(), BuiltInCustomUniforms.BRIGHTNESS.type()).withUniform(BuiltInCustomUniforms.WITH_LIGHT.name(), BuiltInCustomUniforms.WITH_LIGHT.type()).withBlend(DEST_TRANSPARENCY).withCull(false).build();
/*     */     
/* 260 */     MAP = createRenderType("xaero_wm_map_with_light", 786432, false, false, MAP_PL, 
/* 261 */         getStateBuilder()
/* 262 */         .setTextureState((class_4668.class_5939)new class_4668.class_4683(WorldMap.guiTextures, false))
/* 263 */         .setOutputState(class_4668.field_21358), class_1921.class_4750.field_21853);
/*     */   }
/* 265 */   public static final class_1921 MAP_BRANCH = createRenderType("xaero_wm_map_branch", 1536, false, false, RP_MAP_BRANCH, 
/* 266 */       getStateBuilder()
/* 267 */       .setOutputState(class_4668.field_21358), class_1921.class_4750.field_21853);
/*     */   
/* 269 */   public static final class_1921 MAP_COLOR_OVERLAY = createRenderType("xaero_wm_world_map_overlay", 786432, false, false, RP_POSITION_COLOR_NO_CULL, 
/* 270 */       getStateBuilder()
/* 271 */       .setOutputState(class_4668.field_21358), class_1921.class_4750.field_21853);
/*     */   
/*     */   public static final class_1921 MAP_FRAME;
/*     */   
/*     */   public static final class_1921 MAP_COLOR_FILLER;
/*     */   
/*     */   static {
/* 278 */     RenderPipeline MAP_FRAME_PL = RenderPipeline.builder(new RenderPipeline.Snippet[] { POSITION_COLOR_TEX_SNIPPET }).withLocation(class_2960.method_60655("xaeroworldmap", "pipeline/map_frame")).withBlend(DEST_TRANSPARENCY).withCull(false).withDepthWrite(false).build();
/*     */     
/* 280 */     MAP_FRAME = createRenderType("xaero_wm_frame_texture", 1536, false, false, MAP_FRAME_PL, 
/* 281 */         getStateBuilder()
/* 282 */         .setTextureState((class_4668.class_5939)new class_4668.class_4683(WorldMap.guiTextures, false))
/* 283 */         .setOutputState(class_4668.field_21358), class_1921.class_4750.field_21853);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 289 */     RenderPipeline COLOR_FILLER_PL = RenderPipeline.builder(new RenderPipeline.Snippet[] { POSITION_COLOR_SNIPPET }).withLocation(class_2960.method_60655("xaeroworldmap", "pipeline/color_filler")).withBlend(TRANSLUCENT_TRANSPARENCY).withDepthWrite(false).build();
/*     */     
/* 291 */     MAP_COLOR_FILLER = createRenderType("xaero_wm_world_map_filler", 1536, false, false, COLOR_FILLER_PL, 
/* 292 */         getStateBuilder()
/* 293 */         .setOutputState(class_4668.field_21358), class_1921.class_4750.field_21853);
/*     */   }
/* 295 */   public static final class_1921 MAP_ELEMENT_TEXT_BG = createRenderType("xaero_wm_world_map_waypoint_name_bg", 786432, false, false, RP_POSITION_COLOR, 
/* 296 */       getStateBuilder()
/* 297 */       .setOutputState(class_4668.field_21358), class_1921.class_4750.field_21853); private static final Method compositeStateBuilderMethod; private static final Method compositeStateBuilderCreateCompositeStateMethod; private static final Method compositeStateBuilderSetTextureStateMethod;
/*     */   private static final Method compositeStateBuilderSetLightmapStateMethod;
/*     */   private static final Method compositeStateBuilderSetOverlayStateMethod;
/*     */   
/*     */   private CustomRenderTypes(String name, int bufferSize, boolean affectsCrumbling, boolean sortOnUpload, Runnable startAction, Runnable endAction) {
/* 302 */     super(name, bufferSize, affectsCrumbling, sortOnUpload, startAction, endAction);
/*     */   }
/*     */   private static final Method compositeStateBuilderSetLayeringStateMethod; private static final Method compositeStateBuilderSetOutputStateMethod; private static final Method compositeStateBuilderSetTexturingStateMethod; private static final Method compositeStateBuilderSetLineStateMethod; private static final Method renderTypeCreateMethod;
/*     */   
/*     */   public void method_60895(class_9801 var1) {
/* 307 */     throw new IllegalAccessError();
/*     */   }
/*     */ 
/*     */   
/*     */   public VertexFormat method_23031() {
/* 312 */     throw new IllegalAccessError();
/*     */   }
/*     */ 
/*     */   
/*     */   public VertexFormat.class_5596 method_23033() {
/* 317 */     throw new IllegalAccessError();
/*     */   }
/*     */ 
/*     */   
/*     */   public RenderPipeline method_73243() {
/* 322 */     throw new IllegalAccessError();
/*     */   }
/*     */   
/*     */   private static CustomStateBuilder getStateBuilder() {
/* 326 */     return new CustomStateBuilder((class_1921.class_4688.class_4689)Misc.getReflectMethodValue(null, compositeStateBuilderMethod, new Object[0]));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class_1921 createRenderType(String name, int bufferSize, boolean affectsCrumbling, boolean sortOnUpload, RenderPipeline renderPipeline, CustomStateBuilder stateBuilder, class_1921.class_4750 outlineProperty) {
/* 338 */     class_1921.class_4688 compositeState = stateBuilder.createCompositeState(outlineProperty);
/* 339 */     class_1921 normalRenderType = (class_1921)Misc.getReflectMethodValue(null, renderTypeCreateMethod, new Object[] { name, 
/* 340 */           Integer.valueOf(bufferSize), 
/* 341 */           Boolean.valueOf(affectsCrumbling), Boolean.valueOf(sortOnUpload), renderPipeline, compositeState });
/*     */     
/* 343 */     return new ImprovedCompositeRenderType(name, bufferSize, affectsCrumbling, sortOnUpload, renderPipeline, stateBuilder
/* 344 */         .getOutputState(), compositeState, normalRenderType);
/*     */   }
/*     */   
/*     */   private static final class CustomStateBuilder
/*     */   {
/*     */     private final class_1921.class_4688.class_4689 original;
/* 350 */     private class_4668.class_4678 outputState = class_4668.field_21358;
/*     */     
/*     */     private CustomStateBuilder(class_1921.class_4688.class_4689 original) {
/* 353 */       this.original = original;
/*     */     }
/*     */     
/*     */     private CustomStateBuilder setTextureState(class_4668.class_5939 textureState) {
/* 357 */       Misc.getReflectMethodValue(this.original, CustomRenderTypes.compositeStateBuilderSetTextureStateMethod, new Object[] { textureState });
/* 358 */       return this;
/*     */     }
/*     */     
/*     */     private CustomStateBuilder setLightmapState(class_4668.class_4676 lightmapState) {
/* 362 */       Misc.getReflectMethodValue(this.original, CustomRenderTypes.compositeStateBuilderSetLightmapStateMethod, new Object[] { lightmapState });
/* 363 */       return this;
/*     */     }
/*     */     
/*     */     private CustomStateBuilder setOverlayState(class_4668.class_4679 overlayState) {
/* 367 */       Misc.getReflectMethodValue(this.original, CustomRenderTypes.compositeStateBuilderSetOverlayStateMethod, new Object[] { overlayState });
/* 368 */       return this;
/*     */     }
/*     */     
/*     */     private CustomStateBuilder setLayeringState(class_4668.class_4675 layeringState) {
/* 372 */       Misc.getReflectMethodValue(this.original, CustomRenderTypes.compositeStateBuilderSetLayeringStateMethod, new Object[] { layeringState });
/* 373 */       return this;
/*     */     }
/*     */     
/*     */     private CustomStateBuilder setOutputState(class_4668.class_4678 outputState) {
/* 377 */       Misc.getReflectMethodValue(this.original, CustomRenderTypes.compositeStateBuilderSetOutputStateMethod, new Object[] { outputState });
/* 378 */       this.outputState = outputState;
/* 379 */       return this;
/*     */     }
/*     */     
/*     */     private CustomStateBuilder setTexturingState(class_4668.class_4684 texturingState) {
/* 383 */       Misc.getReflectMethodValue(this.original, CustomRenderTypes.compositeStateBuilderSetTexturingStateMethod, new Object[] { texturingState });
/* 384 */       return this;
/*     */     }
/*     */     
/*     */     private CustomStateBuilder setLineState(class_4668.class_4677 lineState) {
/* 388 */       Misc.getReflectMethodValue(this.original, CustomRenderTypes.compositeStateBuilderSetLineStateMethod, new Object[] { lineState });
/* 389 */       return this;
/*     */     }
/*     */     
/*     */     public class_4668.class_4678 getOutputState() {
/* 393 */       return this.outputState;
/*     */     }
/*     */     
/*     */     private class_1921.class_4688 createCompositeState(class_1921.class_4750 outlineProperty) {
/* 397 */       return (class_1921.class_4688)Misc.getReflectMethodValue(this.original, CustomRenderTypes.compositeStateBuilderCreateCompositeStateMethod, new Object[] { outlineProperty });
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class_276 getOutputStateTarget(Object outputStateShard) {
/* 403 */     return ((class_4668.class_4678)outputStateShard).method_68491();
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\graphics\CustomRenderTypes.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */