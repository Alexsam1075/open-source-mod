/*    */ package xaero.map;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import xaero.map.task.MapRunnerTask;
/*    */ 
/*    */ public class MapRunner
/*    */   implements Runnable
/*    */ {
/*    */   private boolean stopped;
/* 10 */   private ArrayList<MapRunnerTask> tasks = new ArrayList<>();
/*    */ 
/*    */   
/*    */   public void run() {
/* 14 */     while (!this.stopped) {
/* 15 */       WorldMapSession worldmapSession = WorldMapSession.getCurrentSession();
/* 16 */       if (worldmapSession != null && worldmapSession.isUsable()) {
/* 17 */         MapProcessor mapProcessor = worldmapSession.getMapProcessor();
/* 18 */         mapProcessor.run(this);
/*    */       } else {
/* 20 */         doTasks(null);
/*    */       }  try {
/* 22 */         Thread.sleep(100L);
/* 23 */       } catch (InterruptedException interruptedException) {}
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void doTasks(MapProcessor mapProcessor) {
/* 29 */     while (!this.tasks.isEmpty()) {
/*    */       MapRunnerTask task;
/* 31 */       synchronized (this.tasks) {
/* 32 */         if (this.tasks.isEmpty())
/*    */           break; 
/* 34 */         task = this.tasks.remove(0);
/*    */       } 
/* 36 */       task.run(mapProcessor);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void addTask(MapRunnerTask task) {
/* 41 */     synchronized (this.tasks) {
/* 42 */       this.tasks.add(task);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void stop() {
/* 47 */     this.stopped = true;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\MapRunner.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */