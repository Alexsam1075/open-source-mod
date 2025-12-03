/*    */ package xaero.map.animation;
/*    */ 
/*    */ public class SinAnimation
/*    */   extends Animation {
/*    */   public SinAnimation(double from, double to, long time) {
/*  6 */     super(from, to, time);
/*    */   }
/*    */   
/*    */   public double getCurrent() {
/* 10 */     double passed = Math.min(1.0D, (System.currentTimeMillis() - this.start) / this.time);
/* 11 */     double angle = 1.5707963267948966D * passed;
/* 12 */     return this.from + this.off * Math.sin(angle);
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\animation\SinAnimation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */