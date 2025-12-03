/*    */ package xaero.map.server.mods.argonauts;
/*    */ 
/*    */ import xaero.map.server.radar.tracker.ISyncedPlayerTrackerSystem;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SupportArgonautsServer
/*    */ {
/* 11 */   private final ISyncedPlayerTrackerSystem syncedPlayerTrackerSystem = new ArgonautsSyncedPlayerTrackerSystem();
/*    */ 
/*    */   
/*    */   public ISyncedPlayerTrackerSystem getSyncedPlayerTrackerSystem() {
/* 15 */     return this.syncedPlayerTrackerSystem;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\server\mods\argonauts\SupportArgonautsServer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */