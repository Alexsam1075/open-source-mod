/*    */ package xaero.map.element;
/*    */ 
/*    */ public class MapElementMenuScroll
/*    */ {
/*    */   private String name;
/*    */   private String icon;
/*    */   private int direction;
/*    */   private long lastScroll;
/*    */   
/*    */   public MapElementMenuScroll(String name, String icon, int direction) {
/* 11 */     this.name = name;
/* 12 */     this.icon = icon;
/* 13 */     this.direction = direction;
/*    */   }
/*    */   
/*    */   public int getDirection() {
/* 17 */     return this.direction;
/*    */   }
/*    */   
/*    */   public int scroll() {
/* 21 */     long currentTime = System.currentTimeMillis();
/* 22 */     if (this.lastScroll == 0L || currentTime - this.lastScroll > 100L) {
/* 23 */       this.lastScroll = currentTime;
/* 24 */       return this.direction;
/*    */     } 
/* 26 */     return 0;
/*    */   }
/*    */   
/*    */   public void onMouseRelease() {
/* 30 */     this.lastScroll = 0L;
/*    */   }
/*    */   
/*    */   public String getIcon() {
/* 34 */     return this.icon;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 38 */     return this.name;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\element\MapElementMenuScroll.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */