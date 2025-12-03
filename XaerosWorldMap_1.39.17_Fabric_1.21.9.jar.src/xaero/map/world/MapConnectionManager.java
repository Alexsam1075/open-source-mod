/*    */ package xaero.map.world;
/*    */ 
/*    */ import java.io.PrintWriter;
/*    */ import java.util.HashMap;
/*    */ import java.util.HashSet;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import net.minecraft.class_2960;
/*    */ import net.minecraft.class_5321;
/*    */ import net.minecraft.class_7924;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MapConnectionManager
/*    */ {
/* 19 */   private Map<MapConnectionNode, Set<MapConnectionNode>> allConnections = new HashMap<>();
/*    */ 
/*    */   
/*    */   public void addConnection(MapConnectionNode mapKey1, MapConnectionNode mapKey2) {
/* 23 */     addOneWayConnection(mapKey1, mapKey2);
/* 24 */     addOneWayConnection(mapKey2, mapKey1);
/*    */   }
/*    */   
/*    */   private void addOneWayConnection(MapConnectionNode mapKey1, MapConnectionNode mapKey2) {
/* 28 */     Set<MapConnectionNode> connections = this.allConnections.get(mapKey1);
/* 29 */     if (connections == null)
/* 30 */       this.allConnections.put(mapKey1, connections = new HashSet<>()); 
/* 31 */     connections.add(mapKey2);
/*    */   }
/*    */   
/*    */   public void removeConnection(MapConnectionNode mapKey1, MapConnectionNode mapKey2) {
/* 35 */     removeOneWayConnection(mapKey1, mapKey2);
/* 36 */     removeOneWayConnection(mapKey2, mapKey1);
/*    */   }
/*    */   
/*    */   private void removeOneWayConnection(MapConnectionNode mapKey1, MapConnectionNode mapKey2) {
/* 40 */     Set<MapConnectionNode> connections = this.allConnections.get(mapKey1);
/* 41 */     if (connections == null)
/*    */       return; 
/* 43 */     connections.remove(mapKey2);
/*    */   }
/*    */   
/*    */   public boolean isConnected(MapConnectionNode mapKey1, MapConnectionNode mapKey2) {
/* 47 */     if (mapKey1 == null || mapKey2 == null)
/* 48 */       return false; 
/* 49 */     if (mapKey1.equals(mapKey2))
/* 50 */       return true; 
/* 51 */     Set<MapConnectionNode> connections = this.allConnections.get(mapKey1);
/* 52 */     if (connections == null)
/* 53 */       return false; 
/* 54 */     return connections.contains(mapKey2);
/*    */   }
/*    */   
/*    */   public boolean isEmpty() {
/* 58 */     return this.allConnections.isEmpty();
/*    */   }
/*    */   
/*    */   public void save(PrintWriter writer) {
/* 62 */     if (!this.allConnections.isEmpty()) {
/* 63 */       Set<String> redundantConnections = new HashSet<>();
/* 64 */       for (Map.Entry<MapConnectionNode, Set<MapConnectionNode>> entry : this.allConnections.entrySet()) {
/* 65 */         MapConnectionNode mapKey = entry.getKey();
/* 66 */         Set<MapConnectionNode> connections = entry.getValue();
/* 67 */         for (MapConnectionNode c : connections) {
/* 68 */           String fullConnection = String.valueOf(mapKey) + ":" + String.valueOf(mapKey);
/* 69 */           if (redundantConnections.contains(fullConnection))
/*    */             continue; 
/* 71 */           writer.println("connection:" + fullConnection);
/* 72 */           redundantConnections.add(String.valueOf(c) + ":" + String.valueOf(c));
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   private void swapConnections(MapConnectionNode mapKey1, MapConnectionNode mapKey2) {
/* 79 */     Set<MapConnectionNode> connections1 = new HashSet<>(this.allConnections.getOrDefault(mapKey1, new HashSet<>()));
/* 80 */     Set<MapConnectionNode> connections2 = new HashSet<>(this.allConnections.getOrDefault(mapKey2, new HashSet<>()));
/* 81 */     for (MapConnectionNode c : connections1)
/* 82 */       removeConnection(mapKey1, c); 
/* 83 */     for (MapConnectionNode c : connections2)
/* 84 */       addConnection(mapKey1, c); 
/* 85 */     for (MapConnectionNode c : connections2)
/* 86 */       removeConnection(mapKey2, c); 
/* 87 */     for (MapConnectionNode c : connections1)
/* 88 */       addConnection(mapKey2, c); 
/*    */   }
/*    */   
/*    */   public void renameDimension(String oldName, String newName) {
/* 92 */     Set<MapConnectionNode> keysCopy = new HashSet<>(this.allConnections.keySet());
/* 93 */     for (MapConnectionNode mapKey : keysCopy) {
/* 94 */       if (mapKey.getDimId().method_29177().toString().equals(oldName)) {
/* 95 */         String mwPart = mapKey.getMw();
/* 96 */         swapConnections(mapKey, new MapConnectionNode(class_5321.method_29179(class_7924.field_41223, class_2960.method_60654(newName)), mwPart));
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\world\MapConnectionManager.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */