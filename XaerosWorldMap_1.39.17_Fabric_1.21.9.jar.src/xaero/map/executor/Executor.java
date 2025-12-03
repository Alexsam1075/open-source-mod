/*    */ package xaero.map.executor;
/*    */ 
/*    */ import net.minecraft.class_1255;
/*    */ 
/*    */ public class Executor
/*    */   extends class_1255<Runnable> {
/*    */   private final Thread thread;
/*    */   
/*    */   public Executor(String name, Thread thread) {
/* 10 */     super(name);
/* 11 */     this.thread = thread;
/*    */   }
/*    */ 
/*    */   
/*    */   public Runnable method_16211(Runnable runnable) {
/* 16 */     return runnable;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean method_18856(Runnable runnable) {
/* 21 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   protected Thread method_3777() {
/* 26 */     return this.thread;
/*    */   }
/*    */ 
/*    */   
/*    */   public void method_5383() {
/* 31 */     if (!method_18854())
/* 32 */       throw new RuntimeException("wrong thread!"); 
/* 33 */     super.method_5383();
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\executor\Executor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */