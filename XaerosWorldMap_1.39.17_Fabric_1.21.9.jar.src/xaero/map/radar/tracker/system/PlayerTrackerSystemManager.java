/*    */ package xaero.map.radar.tracker.system;
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
/*    */ public class PlayerTrackerSystemManager
/*    */ {
/* 14 */   private final Map<String, IPlayerTrackerSystem<?>> systems = new HashMap<>();
/*    */ 
/*    */   
/*    */   public void register(String name, IPlayerTrackerSystem<?> system) {
/* 18 */     if (this.systems.containsKey(name)) {
/* 19 */       WorldMap.LOGGER.error("Player tracker system with the name " + name + " has already been registered!");
/*    */       return;
/*    */     } 
/* 22 */     this.systems.put(name, system);
/* 23 */     WorldMap.LOGGER.info("Registered player tracker system: " + name);
/*    */   }
/*    */   
/*    */   public Iterable<IPlayerTrackerSystem<?>> getSystems() {
/* 27 */     return this.systems.values();
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\radar\tracker\system\PlayerTrackerSystemManager.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */