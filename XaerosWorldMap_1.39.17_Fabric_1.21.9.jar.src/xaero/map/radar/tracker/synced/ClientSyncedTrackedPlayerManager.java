/*    */ package xaero.map.radar.tracker.synced;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.UUID;
/*    */ import net.minecraft.class_1937;
/*    */ import net.minecraft.class_5321;
/*    */ import xaero.map.server.radar.tracker.SyncedTrackedPlayer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ClientSyncedTrackedPlayerManager
/*    */ {
/* 17 */   private final Map<UUID, SyncedTrackedPlayer> trackedPlayers = new HashMap<>();
/*    */ 
/*    */   
/*    */   public void remove(UUID id) {
/* 21 */     this.trackedPlayers.remove(id);
/*    */   }
/*    */   
/*    */   public void update(UUID id, double x, double y, double z, class_5321<class_1937> dim) {
/* 25 */     SyncedTrackedPlayer current = this.trackedPlayers.get(id);
/* 26 */     if (current != null) {
/* 27 */       current.setPos(x, y, z).setDimension(dim);
/*    */       return;
/*    */     } 
/* 30 */     this.trackedPlayers.put(id, new SyncedTrackedPlayer(id, x, y, z, dim));
/*    */   }
/*    */   
/*    */   public Iterable<SyncedTrackedPlayer> getPlayers() {
/* 34 */     return this.trackedPlayers.values();
/*    */   }
/*    */   
/*    */   public void reset() {
/* 38 */     this.trackedPlayers.clear();
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\radar\tracker\synced\ClientSyncedTrackedPlayerManager.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */