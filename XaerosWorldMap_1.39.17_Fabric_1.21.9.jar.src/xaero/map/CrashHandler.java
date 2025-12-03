/*    */ package xaero.map;
/*    */ 
/*    */ public class CrashHandler
/*    */ {
/*    */   private Throwable crashedBy;
/*    */   
/*    */   public void checkForCrashes() throws RuntimeException {
/*  8 */     if (this.crashedBy != null) {
/*  9 */       Throwable crash = this.crashedBy;
/* 10 */       this.crashedBy = null;
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 15 */       throw new RuntimeException("Xaero's World Map (" + WorldMap.INSTANCE.getVersionID() + ") has crashed! Please report here: bit.ly/XaeroWMIssues", crash);
/*    */     } 
/*    */   }
/*    */   
/*    */   public Throwable getCrashedBy() {
/* 20 */     return this.crashedBy;
/*    */   }
/*    */   
/*    */   public void setCrashedBy(Throwable crashedBy) {
/* 24 */     if (this.crashedBy == null)
/* 25 */       this.crashedBy = crashedBy; 
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\CrashHandler.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */