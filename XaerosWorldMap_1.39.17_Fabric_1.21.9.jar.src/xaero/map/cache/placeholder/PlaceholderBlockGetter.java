/*    */ package xaero.map.cache.placeholder;
/*    */ 
/*    */ import net.minecraft.class_1922;
/*    */ import net.minecraft.class_2338;
/*    */ import net.minecraft.class_2586;
/*    */ import net.minecraft.class_2680;
/*    */ import net.minecraft.class_3610;
/*    */ 
/*    */ public class PlaceholderBlockGetter
/*    */   implements class_1922 {
/*    */   private class_2680 placeholderState;
/*    */   
/*    */   public void setPlaceholderState(class_2680 placeholderState) {
/* 14 */     this.placeholderState = placeholderState;
/*    */   }
/*    */ 
/*    */   
/*    */   public class_2586 method_8321(class_2338 blockPos) {
/* 19 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public class_2680 method_8320(class_2338 blockPos) {
/* 24 */     return this.placeholderState;
/*    */   }
/*    */ 
/*    */   
/*    */   public class_3610 method_8316(class_2338 blockPos) {
/* 29 */     return (this.placeholderState == null) ? null : this.placeholderState.method_26227();
/*    */   }
/*    */ 
/*    */   
/*    */   public int method_31605() {
/* 34 */     return 16;
/*    */   }
/*    */ 
/*    */   
/*    */   public int method_31607() {
/* 39 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\cache\placeholder\PlaceholderBlockGetter.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */