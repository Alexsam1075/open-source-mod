/*    */ package xaero.map.server.mods.ftbteams;
/*    */ 
/*    */ import dev.ftb.mods.ftbteams.api.FTBTeamsAPI;
/*    */ import dev.ftb.mods.ftbteams.api.Team;
/*    */ import dev.ftb.mods.ftbteams.api.TeamRank;
/*    */ import net.minecraft.class_1657;
/*    */ import xaero.map.server.radar.tracker.ISyncedPlayerTrackerSystem;
/*    */ 
/*    */ public class FTBTeamsSyncedPlayerTrackerSystem
/*    */   implements ISyncedPlayerTrackerSystem
/*    */ {
/*    */   public int getTrackingLevel(class_1657 tracker, class_1657 tracked) {
/* 13 */     if (FTBTeamsAPI.api().getManager().arePlayersInSameTeam(tracker.method_5667(), tracked.method_5667()))
/* 14 */       return 2; 
/* 15 */     Team trackerTeam = FTBTeamsAPI.api().getManager().getTeamForPlayerID(tracker.method_5667()).orElse(null);
/* 16 */     if (trackerTeam == null)
/* 17 */       return 0; 
/* 18 */     Team trackedTeam = FTBTeamsAPI.api().getManager().getTeamForPlayerID(tracked.method_5667()).orElse(null);
/* 19 */     if (trackedTeam == null)
/* 20 */       return 0; 
/* 21 */     if (trackerTeam.getRankForPlayer(tracked.method_5667()) == TeamRank.ALLY && trackedTeam.getRankForPlayer(tracker.method_5667()) == TeamRank.ALLY)
/* 22 */       return 1; 
/* 23 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isPartySystem() {
/* 28 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\server\mods\ftbteams\FTBTeamsSyncedPlayerTrackerSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */