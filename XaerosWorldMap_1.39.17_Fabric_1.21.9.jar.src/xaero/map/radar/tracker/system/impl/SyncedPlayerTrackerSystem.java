/*    */ package xaero.map.radar.tracker.system.impl;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import net.minecraft.class_310;
/*    */ import xaero.map.WorldMapSession;
/*    */ import xaero.map.mcworld.WorldMapClientWorldData;
/*    */ import xaero.map.mcworld.WorldMapClientWorldDataHelper;
/*    */ import xaero.map.radar.tracker.synced.ClientSyncedTrackedPlayerManager;
/*    */ import xaero.map.radar.tracker.system.IPlayerTrackerSystem;
/*    */ import xaero.map.radar.tracker.system.ITrackedPlayerReader;
/*    */ import xaero.map.server.radar.tracker.SyncedTrackedPlayer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SyncedPlayerTrackerSystem
/*    */   implements IPlayerTrackerSystem<SyncedTrackedPlayer>
/*    */ {
/* 20 */   private final SyncedTrackedPlayerReader reader = new SyncedTrackedPlayerReader();
/*    */ 
/*    */ 
/*    */   
/*    */   public ITrackedPlayerReader<SyncedTrackedPlayer> getReader() {
/* 25 */     return this.reader;
/*    */   }
/*    */ 
/*    */   
/*    */   public Iterator<SyncedTrackedPlayer> getTrackedPlayerIterator() {
/* 30 */     WorldMapSession session = WorldMapSession.getCurrentSession();
/* 31 */     if (session == null)
/* 32 */       return null; 
/* 33 */     if (class_310.method_1551().method_1576() == null) {
/* 34 */       WorldMapClientWorldData worldData = WorldMapClientWorldDataHelper.getCurrentWorldData();
/* 35 */       if (worldData.serverLevelId == null)
/* 36 */         return null; 
/*    */     } 
/* 38 */     ClientSyncedTrackedPlayerManager manager = session.getMapProcessor().getClientSyncedTrackedPlayerManager();
/* 39 */     return manager.getPlayers().iterator();
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\radar\tracker\system\impl\SyncedPlayerTrackerSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */