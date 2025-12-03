/*     */ package xaero.map.graphics;
/*     */ 
/*     */ import net.minecraft.class_1921;
/*     */ import net.minecraft.class_4668;
/*     */ import xaero.map.misc.Misc;
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
/*     */ final class CustomStateBuilder
/*     */ {
/*     */   private final class_1921.class_4688.class_4689 original;
/* 350 */   private class_4668.class_4678 outputState = class_4668.field_21358;
/*     */   
/*     */   private CustomStateBuilder(class_1921.class_4688.class_4689 original) {
/* 353 */     this.original = original;
/*     */   }
/*     */   
/*     */   private CustomStateBuilder setTextureState(class_4668.class_5939 textureState) {
/* 357 */     Misc.getReflectMethodValue(this.original, CustomRenderTypes.compositeStateBuilderSetTextureStateMethod, new Object[] { textureState });
/* 358 */     return this;
/*     */   }
/*     */   
/*     */   private CustomStateBuilder setLightmapState(class_4668.class_4676 lightmapState) {
/* 362 */     Misc.getReflectMethodValue(this.original, CustomRenderTypes.compositeStateBuilderSetLightmapStateMethod, new Object[] { lightmapState });
/* 363 */     return this;
/*     */   }
/*     */   
/*     */   private CustomStateBuilder setOverlayState(class_4668.class_4679 overlayState) {
/* 367 */     Misc.getReflectMethodValue(this.original, CustomRenderTypes.compositeStateBuilderSetOverlayStateMethod, new Object[] { overlayState });
/* 368 */     return this;
/*     */   }
/*     */   
/*     */   private CustomStateBuilder setLayeringState(class_4668.class_4675 layeringState) {
/* 372 */     Misc.getReflectMethodValue(this.original, CustomRenderTypes.compositeStateBuilderSetLayeringStateMethod, new Object[] { layeringState });
/* 373 */     return this;
/*     */   }
/*     */   
/*     */   private CustomStateBuilder setOutputState(class_4668.class_4678 outputState) {
/* 377 */     Misc.getReflectMethodValue(this.original, CustomRenderTypes.compositeStateBuilderSetOutputStateMethod, new Object[] { outputState });
/* 378 */     this.outputState = outputState;
/* 379 */     return this;
/*     */   }
/*     */   
/*     */   private CustomStateBuilder setTexturingState(class_4668.class_4684 texturingState) {
/* 383 */     Misc.getReflectMethodValue(this.original, CustomRenderTypes.compositeStateBuilderSetTexturingStateMethod, new Object[] { texturingState });
/* 384 */     return this;
/*     */   }
/*     */   
/*     */   private CustomStateBuilder setLineState(class_4668.class_4677 lineState) {
/* 388 */     Misc.getReflectMethodValue(this.original, CustomRenderTypes.compositeStateBuilderSetLineStateMethod, new Object[] { lineState });
/* 389 */     return this;
/*     */   }
/*     */   
/*     */   public class_4668.class_4678 getOutputState() {
/* 393 */     return this.outputState;
/*     */   }
/*     */   
/*     */   private class_1921.class_4688 createCompositeState(class_1921.class_4750 outlineProperty) {
/* 397 */     return (class_1921.class_4688)Misc.getReflectMethodValue(this.original, CustomRenderTypes.compositeStateBuilderCreateCompositeStateMethod, new Object[] { outlineProperty });
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\graphics\CustomRenderTypes$CustomStateBuilder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */