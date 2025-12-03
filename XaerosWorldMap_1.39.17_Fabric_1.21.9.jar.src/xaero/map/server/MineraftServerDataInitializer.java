/*    */ package xaero.map.server;
/*    */ 
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import xaero.map.server.mods.SupportServerMods;
/*    */ import xaero.map.server.radar.tracker.SyncedPlayerTracker;
/*    */ import xaero.map.server.radar.tracker.SyncedPlayerTrackerSystemManager;
/*    */ 
/*    */ public class MineraftServerDataInitializer
/*    */ {
/*    */   public void init(MinecraftServer server) {
/* 11 */     SyncedPlayerTrackerSystemManager syncedPlayerTrackerSystemManager = new SyncedPlayerTrackerSystemManager();
/* 12 */     if (SupportServerMods.hasFtbTeams())
/* 13 */       syncedPlayerTrackerSystemManager.register("ftb_teams", SupportServerMods.getFtbTeams().getSyncedPlayerTrackerSystem()); 
/* 14 */     if (SupportServerMods.hasArgonauts())
/* 15 */       syncedPlayerTrackerSystemManager.register("argonauts", SupportServerMods.getArgonauts().getSyncedPlayerTrackerSystem()); 
/* 16 */     SyncedPlayerTracker syncedPlayerTracker = new SyncedPlayerTracker();
/*    */     
/* 18 */     MinecraftServerData data = new MinecraftServerData(syncedPlayerTrackerSystemManager, syncedPlayerTracker);
/* 19 */     ((IMinecraftServer)server).setXaeroWorldMapServerData(data);
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\server\MineraftServerDataInitializer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */