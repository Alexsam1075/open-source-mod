/*    */ package xaero.map.mcworld;
/*    */ 
/*    */ import net.minecraft.class_2338;
/*    */ import net.minecraft.class_638;
/*    */ import xaero.map.message.basic.ClientboundRulesPacket;
/*    */ 
/*    */ 
/*    */ public class WorldMapClientWorldData
/*    */ {
/*    */   private int serverModNetworkVersion;
/*    */   public Integer serverLevelId;
/*    */   public Integer usedServerLevelId;
/*    */   public class_2338 latestSpawn;
/*    */   public class_2338 usedSpawn;
/*    */   private ClientboundRulesPacket syncedRules;
/*    */   
/*    */   public WorldMapClientWorldData(class_638 world) {}
/*    */   
/*    */   public void setServerModNetworkVersion(int serverModNetworkVersion) {
/* 20 */     this.serverModNetworkVersion = serverModNetworkVersion;
/*    */   }
/*    */   
/*    */   public int getServerModNetworkVersion() {
/* 24 */     return this.serverModNetworkVersion;
/*    */   }
/*    */   
/*    */   public void setSyncedRules(ClientboundRulesPacket syncedRules) {
/* 28 */     this.syncedRules = syncedRules;
/*    */   }
/*    */   
/*    */   public ClientboundRulesPacket getSyncedRules() {
/* 32 */     if (this.syncedRules == null)
/* 33 */       this.syncedRules = new ClientboundRulesPacket(true, true); 
/* 34 */     return this.syncedRules;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\mcworld\WorldMapClientWorldData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */