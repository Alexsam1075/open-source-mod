/*    */ package xaero.map.server.player;
/*    */ 
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ import java.util.UUID;
/*    */ import net.minecraft.class_3222;
/*    */ import xaero.map.server.radar.tracker.SyncedTrackedPlayer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ServerPlayerData
/*    */ {
/*    */   private final UUID playerId;
/*    */   private SyncedTrackedPlayer lastSyncedData;
/*    */   private Set<UUID> currentlySyncedPlayers;
/*    */   private long lastTrackedPlayerSync;
/*    */   private int clientModNetworkVersion;
/*    */   private Object opacData;
/*    */   
/*    */   public ServerPlayerData(UUID playerId) {
/* 22 */     this.playerId = playerId;
/*    */   }
/*    */   
/*    */   public SyncedTrackedPlayer getLastSyncedData() {
/* 26 */     return this.lastSyncedData;
/*    */   }
/*    */   
/*    */   public SyncedTrackedPlayer ensureLastSyncedData() {
/* 30 */     if (this.lastSyncedData == null)
/* 31 */       this.lastSyncedData = new SyncedTrackedPlayer(this.playerId, 0.0D, 0.0D, 0.0D, null); 
/* 32 */     return this.lastSyncedData;
/*    */   }
/*    */   
/*    */   public Set<UUID> getCurrentlySyncedPlayers() {
/* 36 */     return this.currentlySyncedPlayers;
/*    */   }
/*    */   
/*    */   public Set<UUID> ensureCurrentlySyncedPlayers() {
/* 40 */     if (this.currentlySyncedPlayers == null)
/* 41 */       this.currentlySyncedPlayers = new HashSet<>(); 
/* 42 */     return this.currentlySyncedPlayers;
/*    */   }
/*    */   
/*    */   public long getLastTrackedPlayerSync() {
/* 46 */     return this.lastTrackedPlayerSync;
/*    */   }
/*    */   
/*    */   public void setLastTrackedPlayerSync(long lastTrackedPlayerSync) {
/* 50 */     this.lastTrackedPlayerSync = lastTrackedPlayerSync;
/*    */   }
/*    */   
/*    */   public static ServerPlayerData get(class_3222 player) {
/* 54 */     ServerPlayerData result = ((IServerPlayer)player).getXaeroWorldMapPlayerData();
/* 55 */     if (result == null)
/* 56 */       ((IServerPlayer)player).setXaeroWorldMapPlayerData(result = new ServerPlayerData(player.method_5667())); 
/* 57 */     return result;
/*    */   }
/*    */   
/*    */   public boolean hasMod() {
/* 61 */     return (this.clientModNetworkVersion != 0);
/*    */   }
/*    */   
/*    */   public void setClientModNetworkVersion(int clientModNetworkVersion) {
/* 65 */     this.clientModNetworkVersion = clientModNetworkVersion;
/*    */   }
/*    */   
/*    */   public int getClientModNetworkVersion() {
/* 69 */     return this.clientModNetworkVersion;
/*    */   }
/*    */   
/*    */   public void setOpacData(Object opacData) {
/* 73 */     this.opacData = opacData;
/*    */   }
/*    */   
/*    */   public Object getOpacData() {
/* 77 */     return this.opacData;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\server\player\ServerPlayerData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */