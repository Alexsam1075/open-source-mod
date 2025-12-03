/*     */ package xaero.map.element;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_4587;
/*     */ import net.minecraft.class_4597;
/*     */ import xaero.map.WorldMap;
/*     */ import xaero.map.element.render.ElementRenderLocation;
/*     */ import xaero.map.element.render.ElementRenderer;
/*     */ import xaero.map.mods.SupportMods;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Builder
/*     */ {
/*     */   private class_4587 poseStack;
/*     */   
/*     */   public Builder setDefault() {
/* 230 */     setPoseStack(null);
/* 231 */     return this;
/*     */   }
/*     */   
/*     */   public Builder setPoseStack(class_4587 poseStack) {
/* 235 */     this.poseStack = poseStack;
/* 236 */     return this;
/*     */   }
/*     */   
/*     */   public MapElementRenderHandler build() {
/* 240 */     if (this.poseStack == null)
/* 241 */       throw new IllegalStateException(); 
/* 242 */     List<ElementRenderer<?, ?, ?>> renderers = new ArrayList<>();
/* 243 */     if (SupportMods.minimap())
/* 244 */       renderers.add(SupportMods.xaeroMinimap.getWaypointRenderer()); 
/* 245 */     renderers.add(WorldMap.trackedPlayerRenderer);
/* 246 */     if (SupportMods.pac())
/* 247 */       renderers.add(SupportMods.xaeroPac.getCaimResultElementRenderer()); 
/* 248 */     MapElementGraphics graphics = new MapElementGraphics(this.poseStack, () -> class_310.method_1551().method_22940().method_23000());
/* 249 */     return new MapElementRenderHandler(renderers, ElementRenderLocation.WORLD_MAP, graphics);
/*     */   }
/*     */   
/*     */   public static Builder begin() {
/* 253 */     return new Builder();
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\element\MapElementRenderHandler$Builder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */