/*     */ package xaero.map.mods.minimap.element;
/*     */ 
/*     */ import java.util.function.Supplier;
/*     */ import xaero.common.IXaeroMinimap;
/*     */ import xaero.hud.minimap.element.render.MinimapElementRenderer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Builder<E, C>
/*     */ {
/*     */   private final MinimapElementRenderer<E, C> renderer;
/*     */   private Supplier<Boolean> shouldRenderSupplier;
/*     */   private IXaeroMinimap modMain;
/*     */   private int order;
/*     */   
/*     */   private Builder(MinimapElementRenderer<E, C> renderer) {
/* 118 */     this.renderer = renderer;
/*     */   }
/*     */   
/*     */   private Builder<E, C> setDefault() {
/* 122 */     setModMain(null);
/* 123 */     setShouldRenderSupplier(() -> Boolean.valueOf(true));
/* 124 */     setOrder(0);
/* 125 */     return this;
/*     */   }
/*     */   
/*     */   public Builder<E, C> setModMain(IXaeroMinimap modMain) {
/* 129 */     this.modMain = modMain;
/* 130 */     return this;
/*     */   }
/*     */   
/*     */   public Builder<E, C> setShouldRenderSupplier(Supplier<Boolean> shouldRenderSupplier) {
/* 134 */     this.shouldRenderSupplier = shouldRenderSupplier;
/* 135 */     return this;
/*     */   }
/*     */   
/*     */   public Builder<E, C> setOrder(int order) {
/* 139 */     this.order = order;
/* 140 */     return this;
/*     */   }
/*     */   
/*     */   public MinimapElementRendererWrapper<E, C> build() {
/* 144 */     if (this.modMain == null || this.shouldRenderSupplier == null)
/* 145 */       throw new IllegalStateException(); 
/* 146 */     MinimapElementRenderProviderWrapper<E, C> providerWrapper = new MinimapElementRenderProviderWrapper<>(this.renderer.getProvider());
/* 147 */     MinimapElementReaderWrapper<E, C> readerWrapper = new MinimapElementReaderWrapper<>(this.renderer.getElementReader());
/* 148 */     C context = (C)this.renderer.getContext();
/* 149 */     return new MinimapElementRendererWrapper<>(this.modMain, context, providerWrapper, readerWrapper, this.renderer, this.shouldRenderSupplier, this.order);
/*     */   }
/*     */   
/*     */   public static <E, C> Builder<E, C> begin(MinimapElementRenderer<E, C> renderer) {
/* 153 */     return (new Builder<>(renderer)).setDefault();
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\mods\minimap\element\MinimapElementRendererWrapper$Builder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */