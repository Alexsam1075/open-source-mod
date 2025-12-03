/*    */ package xaero.map.animation;
/*    */ 
/*    */ public class Animation
/*    */ {
/*    */   protected long start;
/*    */   protected long time;
/*    */   protected double from;
/*    */   protected double off;
/*    */   
/*    */   public Animation(double from, double to, long time) {
/* 11 */     this.from = from;
/* 12 */     this.off = to - from;
/* 13 */     this.time = time;
/* 14 */     this.start = System.currentTimeMillis();
/*    */   }
/*    */   
/*    */   public double getCurrent() {
/* 18 */     return this.from + Math.min(1.0D, (System.currentTimeMillis() - this.start) / this.time) * this.off;
/*    */   }
/*    */   
/*    */   public double getDestination() {
/* 22 */     return this.from + this.off;
/*    */   }
/*    */   
/*    */   public long getPassed() {
/* 26 */     return System.currentTimeMillis() - this.start;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\animation\Animation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */