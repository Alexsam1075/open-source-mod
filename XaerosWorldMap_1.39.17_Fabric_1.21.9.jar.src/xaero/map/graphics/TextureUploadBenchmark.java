/*    */ package xaero.map.graphics;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ import xaero.map.misc.Misc;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TextureUploadBenchmark
/*    */ {
/*    */   private long[] accumulators;
/*    */   private long[] results;
/*    */   private int[] totals;
/*    */   private boolean[] finished;
/*    */   private int[] nOfElements;
/*    */   private int nOfFinished;
/*    */   private boolean allFinished;
/*    */   
/*    */   public TextureUploadBenchmark(int... nOfElements) {
/* 23 */     int nOfTypes = nOfElements.length;
/* 24 */     this.accumulators = new long[nOfTypes];
/* 25 */     this.totals = new int[nOfTypes];
/* 26 */     this.results = new long[nOfTypes];
/* 27 */     this.finished = new boolean[nOfTypes];
/* 28 */     this.nOfElements = nOfElements;
/*    */   }
/*    */   
/*    */   public void pre() {
/* 32 */     Misc.timerPre();
/*    */   }
/*    */   
/*    */   public void post(int type) {
/* 36 */     GL11.glFinish();
/* 37 */     int passed = Misc.timerResult();
/* 38 */     this.accumulators[type] = this.accumulators[type] + passed;
/* 39 */     this.totals[type] = this.totals[type] + 1;
/* 40 */     if (this.totals[type] == this.nOfElements[type])
/*    */     {
/* 42 */       finish(type);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   private void finish(int type) {
/* 48 */     this.results[type] = this.accumulators[type] / this.totals[type];
/* 49 */     this.finished[type] = true;
/* 50 */     this.nOfFinished++;
/* 51 */     if (this.nOfFinished == this.finished.length)
/* 52 */       this.allFinished = true; 
/*    */   }
/*    */   
/*    */   public boolean isFinished() {
/* 56 */     return this.allFinished;
/*    */   }
/*    */   
/*    */   public boolean isFinished(int type) {
/* 60 */     return this.finished[type];
/*    */   }
/*    */   
/*    */   public long getAverage(int type) {
/* 64 */     if (this.finished[type]) {
/* 65 */       return this.results[type];
/*    */     }
/* 67 */     return this.accumulators[type] / this.totals[type];
/*    */   }
/*    */   
/*    */   public String getTotalsString() {
/* 71 */     return Arrays.toString(this.totals);
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\graphics\TextureUploadBenchmark.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */