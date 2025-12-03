/*    */ package xaero.map.element;
/*    */ 
/*    */ import xaero.map.element.render.ElementReader;
/*    */ 
/*    */ public abstract class MenuOnlyElementReader<E>
/*    */   extends ElementReader<E, Object, MenuOnlyElementRenderer<E>>
/*    */ {
/*    */   public boolean isHidden(E element, Object context) {
/*  9 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getRenderX(E element, Object context, float partialTicks) {
/* 14 */     return 0.0D;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getRenderZ(E element, Object context, float partialTicks) {
/* 19 */     return 0.0D;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getInteractionBoxLeft(E element, Object context, float partialTicks) {
/* 24 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getInteractionBoxRight(E element, Object context, float partialTicks) {
/* 29 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getInteractionBoxTop(E element, Object context, float partialTicks) {
/* 34 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getInteractionBoxBottom(E element, Object context, float partialTicks) {
/* 39 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getRenderBoxLeft(E element, Object context, float partialTicks) {
/* 44 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getRenderBoxRight(E element, Object context, float partialTicks) {
/* 49 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getRenderBoxTop(E element, Object context, float partialTicks) {
/* 54 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getRenderBoxBottom(E element, Object context, float partialTicks) {
/* 59 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\element\MenuOnlyElementReader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */