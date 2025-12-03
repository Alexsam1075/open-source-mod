/*    */ package xaero.map.server.radar.tracker;
/*    */ 
/*    */ import java.util.UUID;
/*    */ import net.minecraft.class_1657;
/*    */ import net.minecraft.class_1937;
/*    */ import net.minecraft.class_5321;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SyncedTrackedPlayer
/*    */ {
/*    */   private final UUID id;
/*    */   private double x;
/*    */   private double y;
/*    */   private double z;
/*    */   private class_5321<class_1937> dimension;
/*    */   
/*    */   public SyncedTrackedPlayer(UUID id, double x, double y, double z, class_5321<class_1937> dimension) {
/* 19 */     this.id = id;
/* 20 */     this.x = x;
/* 21 */     this.y = y;
/* 22 */     this.z = z;
/* 23 */     this.dimension = dimension;
/*    */   }
/*    */   
/*    */   public SyncedTrackedPlayer setPos(double x, double y, double z) {
/* 27 */     this.x = x;
/* 28 */     this.y = y;
/* 29 */     this.z = z;
/* 30 */     return this;
/*    */   }
/*    */   
/*    */   public SyncedTrackedPlayer setDimension(class_5321<class_1937> dimension) {
/* 34 */     this.dimension = dimension;
/* 35 */     return this;
/*    */   }
/*    */   
/*    */   public UUID getId() {
/* 39 */     return this.id;
/*    */   }
/*    */   
/*    */   public double getX() {
/* 43 */     return this.x;
/*    */   }
/*    */   
/*    */   public double getY() {
/* 47 */     return this.y;
/*    */   }
/*    */   
/*    */   public double getZ() {
/* 51 */     return this.z;
/*    */   }
/*    */   
/*    */   public class_5321<class_1937> getDimension() {
/* 55 */     return this.dimension;
/*    */   }
/*    */   
/*    */   public boolean matchesEnough(class_1657 player, double maxAxisDistance) {
/* 59 */     return (Math.abs(player.method_23317() - this.x) <= maxAxisDistance && Math.abs(player.method_23318() - this.y) <= maxAxisDistance && Math.abs(player.method_23321() - this.z) <= maxAxisDistance && player.method_73183().method_27983().method_29177().equals(this.dimension));
/*    */   }
/*    */   
/*    */   public void update(class_1657 player) {
/* 63 */     setPos(player.method_23317(), player.method_23318(), player.method_23321()).setDimension(player.method_73183().method_27983());
/*    */   }
/*    */   
/*    */   public void copyFrom(SyncedTrackedPlayer trackedPlayer) {
/* 67 */     setPos(trackedPlayer.getX(), trackedPlayer.getY(), trackedPlayer.getZ()).setDimension(trackedPlayer.getDimension());
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\server\radar\tracker\SyncedTrackedPlayer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */