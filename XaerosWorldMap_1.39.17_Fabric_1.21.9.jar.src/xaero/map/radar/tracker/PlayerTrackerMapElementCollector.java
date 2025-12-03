/*    */ package xaero.map.radar.tracker;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import java.util.UUID;
/*    */ import net.minecraft.class_1657;
/*    */ import net.minecraft.class_310;
/*    */ import xaero.map.radar.tracker.system.IPlayerTrackerSystem;
/*    */ import xaero.map.radar.tracker.system.ITrackedPlayerReader;
/*    */ import xaero.map.radar.tracker.system.PlayerTrackerSystemManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PlayerTrackerMapElementCollector
/*    */ {
/*    */   private Map<UUID, PlayerTrackerMapElement<?>> elements;
/*    */   private final PlayerTrackerSystemManager systemManager;
/*    */   private final Runnable onElementsChange;
/*    */   
/*    */   public PlayerTrackerMapElementCollector(PlayerTrackerSystemManager systemManager, Runnable onElementsChange) {
/* 22 */     this.elements = new HashMap<>();
/* 23 */     this.systemManager = systemManager;
/* 24 */     this.onElementsChange = onElementsChange;
/*    */   }
/*    */   
/*    */   public void update(class_310 mc) {
/* 28 */     if (this.elements == null)
/* 29 */       this.elements = new HashMap<>(); 
/* 30 */     Map<UUID, PlayerTrackerMapElement<?>> updatedMap = new HashMap<>();
/* 31 */     boolean hasNewPlayer = false;
/*    */     
/* 33 */     for (IPlayerTrackerSystem<?> system : (Iterable<IPlayerTrackerSystem<?>>)this.systemManager.getSystems()) {
/* 34 */       hasNewPlayer = (updateForSystem(system, updatedMap, this.elements) || hasNewPlayer);
/*    */     }
/* 36 */     if (hasNewPlayer || updatedMap.size() != this.elements.size()) {
/* 37 */       this.elements = updatedMap;
/* 38 */       this.onElementsChange.run();
/*    */     } 
/*    */   }
/*    */   
/*    */   private <P> boolean updateForSystem(IPlayerTrackerSystem<P> system, Map<UUID, PlayerTrackerMapElement<?>> destination, Map<UUID, PlayerTrackerMapElement<?>> current) {
/* 43 */     Iterator<P> playerIterator = system.getTrackedPlayerIterator();
/* 44 */     if (playerIterator == null)
/* 45 */       return false; 
/* 46 */     ITrackedPlayerReader<P> reader = system.getReader();
/* 47 */     boolean hasNewPlayer = false;
/* 48 */     while (playerIterator.hasNext()) {
/* 49 */       P player = playerIterator.next();
/* 50 */       UUID playerId = reader.getId(player);
/* 51 */       PlayerTrackerMapElement<?> element = current.get(playerId);
/* 52 */       if (destination.containsKey(playerId))
/*    */         continue; 
/* 54 */       if (element == null || element.getPlayer() != player) {
/* 55 */         element = new PlayerTrackerMapElement(player, system);
/* 56 */         hasNewPlayer = true;
/*    */       } 
/* 58 */       destination.put(element.getPlayerId(), element);
/*    */     } 
/* 60 */     return hasNewPlayer;
/*    */   }
/*    */   
/*    */   public boolean playerExists(UUID id) {
/* 64 */     return (this.elements != null && this.elements.containsKey(id));
/*    */   }
/*    */   
/*    */   public Iterable<PlayerTrackerMapElement<?>> getElements() {
/* 68 */     return this.elements.values();
/*    */   }
/*    */   
/*    */   public void resetRenderedOnRadarFlags() {
/* 72 */     for (PlayerTrackerMapElement<?> e : this.elements.values())
/* 73 */       e.setRenderedOnRadar(false); 
/*    */   }
/*    */   
/*    */   public void confirmPlayerRadarRender(class_1657 p) {
/* 77 */     ((PlayerTrackerMapElement)this.elements.get(p.method_5667())).setRenderedOnRadar(true);
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\radar\tracker\PlayerTrackerMapElementCollector.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */