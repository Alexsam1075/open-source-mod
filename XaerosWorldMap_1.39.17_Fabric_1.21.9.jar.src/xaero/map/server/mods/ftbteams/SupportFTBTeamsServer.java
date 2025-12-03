/*    */ package xaero.map.server.mods.ftbteams;
/*    */ 
/*    */ import xaero.map.server.radar.tracker.ISyncedPlayerTrackerSystem;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SupportFTBTeamsServer
/*    */ {
/* 11 */   private final ISyncedPlayerTrackerSystem syncedPlayerTrackerSystem = new FTBTeamsSyncedPlayerTrackerSystem();
/*    */ 
/*    */   
/*    */   public ISyncedPlayerTrackerSystem getSyncedPlayerTrackerSystem() {
/* 15 */     return this.syncedPlayerTrackerSystem;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\server\mods\ftbteams\SupportFTBTeamsServer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */