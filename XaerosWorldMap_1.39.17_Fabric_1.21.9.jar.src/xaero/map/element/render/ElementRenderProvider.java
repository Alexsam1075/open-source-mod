/*   */ package xaero.map.element.render;
/*   */ 
/*   */ 
/*   */ public abstract class ElementRenderProvider<E, C>
/*   */ {
/*   */   public abstract void begin(ElementRenderLocation paramElementRenderLocation, C paramC);
/*   */   
/*   */   public E setupContextAndGetNext(ElementRenderLocation location, C context) {
/* 9 */     return getNext(location, context);
/*   */   }
/*   */   
/*   */   public abstract boolean hasNext(ElementRenderLocation paramElementRenderLocation, C paramC);
/*   */   
/*   */   public abstract E getNext(ElementRenderLocation paramElementRenderLocation, C paramC);
/*   */   
/*   */   public abstract void end(ElementRenderLocation paramElementRenderLocation, C paramC);
/*   */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\element\render\ElementRenderProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */