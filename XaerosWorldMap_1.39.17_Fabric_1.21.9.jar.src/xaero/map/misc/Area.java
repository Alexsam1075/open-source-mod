/*    */ package xaero.map.misc;
/*    */ 
/*    */ 
/*    */ public class Area
/*    */ {
/*    */   private final int left;
/*    */   private final int top;
/*    */   private final int right;
/*    */   private final int bottom;
/*    */   
/*    */   public Area(int left, int top, int right, int bottom) {
/* 12 */     this.left = left;
/* 13 */     this.top = top;
/* 14 */     this.right = right;
/* 15 */     this.bottom = bottom;
/*    */   }
/*    */   
/*    */   public int getLeft() {
/* 19 */     return this.left;
/*    */   }
/*    */   
/*    */   public int getTop() {
/* 23 */     return this.top;
/*    */   }
/*    */   public int getRight() {
/* 26 */     return this.right;
/*    */   }
/*    */   
/*    */   public int getBottom() {
/* 30 */     return this.bottom;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 35 */     int hash = this.left;
/* 36 */     hash = hash * 37 + this.top;
/* 37 */     hash = hash * 37 + this.right;
/* 38 */     hash = hash * 37 + this.bottom;
/* 39 */     hash = hash * 37 + this.right;
/* 40 */     hash = hash * 37 + this.top;
/* 41 */     hash = hash * 37 + this.left;
/* 42 */     return hash;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 47 */     if (!(obj instanceof Area))
/* 48 */       return false; 
/* 49 */     Area other = (Area)obj;
/* 50 */     return (this.left == other.left && this.top == other.top && this.right == other.right && this.bottom == other.bottom);
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\misc\Area.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */