/*    */ package xaero.map.mods.minimap.element;
/*    */ 
/*    */ import xaero.hud.minimap.element.render.MinimapElementRenderProvider;
/*    */ import xaero.map.element.render.ElementRenderLocation;
/*    */ import xaero.map.element.render.ElementRenderProvider;
/*    */ 
/*    */ public class MinimapElementRenderProviderWrapper<E, C>
/*    */   extends ElementRenderProvider<E, C> {
/*    */   private final MinimapElementRenderProvider<E, C> provider;
/*    */   
/*    */   public MinimapElementRenderProviderWrapper(MinimapElementRenderProvider<E, C> provider) {
/* 12 */     this.provider = provider;
/*    */   }
/*    */ 
/*    */   
/*    */   public void begin(ElementRenderLocation location, C context) {
/* 17 */     this.provider.begin(MinimapElementRendererWrapper.getRenderLocation(location), context);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasNext(ElementRenderLocation location, C context) {
/* 22 */     return this.provider.hasNext(MinimapElementRendererWrapper.getRenderLocation(location), context);
/*    */   }
/*    */ 
/*    */   
/*    */   public E setupContextAndGetNext(ElementRenderLocation location, C context) {
/* 27 */     return (E)this.provider.setupContextAndGetNext(MinimapElementRendererWrapper.getRenderLocation(location), context);
/*    */   }
/*    */ 
/*    */   
/*    */   public E getNext(ElementRenderLocation location, C context) {
/* 32 */     return (E)this.provider.getNext(MinimapElementRendererWrapper.getRenderLocation(location), context);
/*    */   }
/*    */ 
/*    */   
/*    */   public void end(ElementRenderLocation location, C context) {
/* 37 */     this.provider.end(MinimapElementRendererWrapper.getRenderLocation(location), context);
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\mods\minimap\element\MinimapElementRenderProviderWrapper.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */