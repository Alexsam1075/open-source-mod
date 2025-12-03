/*    */ package xaero.map.mods.minimap.tracker.system;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import net.minecraft.class_310;
/*    */ import xaero.common.XaeroMinimapSession;
/*    */ import xaero.common.minimap.mcworld.MinimapClientWorldData;
/*    */ import xaero.common.minimap.mcworld.MinimapClientWorldDataHelper;
/*    */ import xaero.common.server.radar.tracker.SyncedTrackedPlayer;
/*    */ import xaero.hud.minimap.player.tracker.synced.ClientSyncedTrackedPlayerManager;
/*    */ import xaero.map.mods.SupportXaeroMinimap;
/*    */ import xaero.map.radar.tracker.system.IPlayerTrackerSystem;
/*    */ import xaero.map.radar.tracker.system.ITrackedPlayerReader;
/*    */ 
/*    */ 
/*    */ public class MinimapSyncedPlayerTrackerSystem
/*    */   implements IPlayerTrackerSystem<SyncedTrackedPlayer>
/*    */ {
/*    */   private final MinimapSyncedTrackedPlayerReader reader;
/*    */   
/*    */   public MinimapSyncedPlayerTrackerSystem(SupportXaeroMinimap minimapSupport) {
/* 21 */     this.reader = new MinimapSyncedTrackedPlayerReader(minimapSupport);
/*    */   }
/*    */ 
/*    */   
/*    */   public ITrackedPlayerReader<SyncedTrackedPlayer> getReader() {
/* 26 */     return this.reader;
/*    */   }
/*    */ 
/*    */   
/*    */   public Iterator<SyncedTrackedPlayer> getTrackedPlayerIterator() {
/* 31 */     XaeroMinimapSession session = XaeroMinimapSession.getCurrentSession();
/* 32 */     if (session == null)
/* 33 */       return null; 
/* 34 */     if (class_310.method_1551().method_1576() == null) {
/* 35 */       MinimapClientWorldData worldData = MinimapClientWorldDataHelper.getCurrentWorldData();
/* 36 */       if (worldData.serverLevelId == null)
/* 37 */         return null; 
/*    */     } 
/* 39 */     ClientSyncedTrackedPlayerManager manager = session.getMinimapProcessor().getSyncedTrackedPlayerManager();
/* 40 */     return manager.getPlayers().iterator();
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\mods\minimap\tracker\system\MinimapSyncedPlayerTrackerSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */