/*    */ package xaero.map.element;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import xaero.map.element.render.ElementRenderer;
/*    */ import xaero.map.gui.IRightClickableElement;
/*    */ import xaero.map.gui.dropdown.rightclick.RightClickOption;
/*    */ 
/*    */ 
/*    */ public class HoveredMapElementHolder<E, C>
/*    */   implements IRightClickableElement
/*    */ {
/*    */   private final E element;
/*    */   private final ElementRenderer<E, C, ?> renderer;
/*    */   
/*    */   public HoveredMapElementHolder(E element, ElementRenderer<E, C, ?> renderer) {
/* 16 */     this.element = element;
/* 17 */     this.renderer = renderer;
/*    */   }
/*    */ 
/*    */   
/*    */   public ArrayList<RightClickOption> getRightClickOptions() {
/* 22 */     return this.renderer.getReader().getRightClickOptions(this.element, this);
/*    */   }
/*    */   
/*    */   public boolean isRightClickValid() {
/* 26 */     return this.renderer.getReader().isRightClickValid(this.element);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getRightClickTitleBackgroundColor() {
/* 31 */     return this.renderer.getReader().getRightClickTitleBackgroundColor(this.element);
/*    */   }
/*    */   
/*    */   public E getElement() {
/* 35 */     return this.element;
/*    */   }
/*    */   
/*    */   public ElementRenderer<E, C, ?> getRenderer() {
/* 39 */     return this.renderer;
/*    */   }
/*    */   
/*    */   public boolean is(Object o) {
/* 43 */     return (this.element == o);
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\element\HoveredMapElementHolder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */