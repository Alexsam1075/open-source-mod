/*    */ package xaero.map.radar.tracker;
/*    */ 
/*    */ import net.minecraft.class_310;
/*    */ import net.minecraft.class_437;
/*    */ import xaero.map.WorldMap;
/*    */ import xaero.map.world.MapWorld;
/*    */ 
/*    */ public class PlayerTeleporter
/*    */ {
/*    */   public void teleport(class_437 screen, MapWorld mapWorld, String name, int x, int y, int z) {
/* 11 */     class_310.method_1551().method_1507(null);
/* 12 */     String tpCommand = mapWorld.getPlayerTeleportCommandFormat();
/* 13 */     tpCommand = tpCommand.replace("{name}", name).replace("{x}", "" + x).replace("{y}", "" + y).replace("{z}", "" + z);
/* 14 */     class_310 mc = class_310.method_1551();
/* 15 */     if (tpCommand.startsWith("/")) {
/* 16 */       tpCommand = tpCommand.substring(1);
/* 17 */       mc.field_1724.field_3944.method_45730(tpCommand);
/*    */     } else {
/* 19 */       mc.field_1724.field_3944.method_45729(tpCommand);
/*    */     } 
/*    */   }
/*    */   public void teleportToPlayer(class_437 screen, MapWorld mapWorld, PlayerTrackerMapElement<?> target) {
/* 23 */     teleport(screen, mapWorld, WorldMap.trackedPlayerRenderer.getReader().getMenuName(target), (int)Math.floor(target.getX()), (int)Math.floor(target.getY()), (int)Math.floor(target.getZ()));
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\radar\tracker\PlayerTeleporter.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */