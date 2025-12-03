/*    */ package xaero.map.gui;
/*    */ 
/*    */ 
/*    */ public class MapTileSelection
/*    */ {
/*    */   private final int startX;
/*    */   private final int startZ;
/*    */   private int endX;
/*    */   private int endZ;
/*    */   
/*    */   public MapTileSelection(int startX, int startZ) {
/* 12 */     this.startX = startX;
/* 13 */     this.startZ = startZ;
/*    */   }
/*    */   
/*    */   public void setEnd(int endX, int endZ) {
/* 17 */     this.endX = endX;
/* 18 */     this.endZ = endZ;
/*    */   }
/*    */   
/*    */   public int getStartX() {
/* 22 */     return this.startX;
/*    */   }
/*    */   
/*    */   public int getStartZ() {
/* 26 */     return this.startZ;
/*    */   }
/*    */   
/*    */   public int getEndX() {
/* 30 */     return this.endX;
/*    */   }
/*    */   
/*    */   public int getEndZ() {
/* 34 */     return this.endZ;
/*    */   }
/*    */   
/*    */   public int getLeft() {
/* 38 */     return Math.min(this.startX, this.endX);
/*    */   }
/*    */   
/*    */   public int getRight() {
/* 42 */     return Math.max(this.startX, this.endX);
/*    */   }
/*    */   
/*    */   public int getTop() {
/* 46 */     return Math.min(this.startZ, this.endZ);
/*    */   }
/*    */   
/*    */   public int getBottom() {
/* 50 */     return Math.max(this.startZ, this.endZ);
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\gui\MapTileSelection.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */