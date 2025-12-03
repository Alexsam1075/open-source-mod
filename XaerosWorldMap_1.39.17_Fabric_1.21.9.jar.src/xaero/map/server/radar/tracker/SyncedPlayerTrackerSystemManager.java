/*    */ package xaero.map.server.radar.tracker;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import xaero.map.WorldMap;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SyncedPlayerTrackerSystemManager
/*    */ {
/* 14 */   private final Map<String, ISyncedPlayerTrackerSystem> systems = new HashMap<>();
/*    */ 
/*    */   
/*    */   public void register(String name, ISyncedPlayerTrackerSystem system) {
/* 18 */     if (this.systems.containsKey(name)) {
/* 19 */       WorldMap.LOGGER.error("Synced player tracker system with the name " + name + " has already been registered!");
/*    */       return;
/*    */     } 
/* 22 */     this.systems.put(name, system);
/* 23 */     WorldMap.LOGGER.info("Registered synced player tracker system: " + name);
/*    */   }
/*    */   
/*    */   public Iterable<ISyncedPlayerTrackerSystem> getSystems() {
/* 27 */     return this.systems.values();
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\server\radar\tracker\SyncedPlayerTrackerSystemManager.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */