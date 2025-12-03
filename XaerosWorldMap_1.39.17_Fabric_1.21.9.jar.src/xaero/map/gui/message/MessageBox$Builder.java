/*    */ package xaero.map.gui.message;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Builder
/*    */ {
/*    */   private int width;
/*    */   private int capacity;
/*    */   
/*    */   public Builder setDefault() {
/* 62 */     setWidth(250);
/* 63 */     setCapacity(5);
/* 64 */     return this;
/*    */   }
/*    */   
/*    */   public Builder setWidth(int width) {
/* 68 */     this.width = width;
/* 69 */     return this;
/*    */   }
/*    */   
/*    */   public Builder setCapacity(int capacity) {
/* 73 */     this.capacity = capacity;
/* 74 */     return this;
/*    */   }
/*    */   
/*    */   public MessageBox build() {
/* 78 */     return new MessageBox(new ArrayList<>(this.capacity), this.width, this.capacity);
/*    */   }
/*    */   
/*    */   public static Builder begin() {
/* 82 */     return (new Builder()).setDefault();
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\gui\message\MessageBox$Builder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */