/*    */ package xaero.map.teleport;
/*    */ 
/*    */ import net.minecraft.class_1074;
/*    */ import net.minecraft.class_124;
/*    */ import net.minecraft.class_1937;
/*    */ import net.minecraft.class_2561;
/*    */ import net.minecraft.class_310;
/*    */ import net.minecraft.class_437;
/*    */ import net.minecraft.class_5250;
/*    */ import net.minecraft.class_5321;
/*    */ import xaero.map.WorldMap;
/*    */ import xaero.map.world.MapConnectionNode;
/*    */ import xaero.map.world.MapDimension;
/*    */ import xaero.map.world.MapWorld;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MapTeleporter
/*    */ {
/*    */   public void teleport(class_437 screen, MapWorld mapWorld, int x, int y, int z, class_5321<class_1937> d) {
/* 21 */     class_310.method_1551().method_1507(null);
/* 22 */     if ((class_310.method_1551()).field_1761.method_2908()) {
/* 23 */       MapDimension destinationDim = mapWorld.getDimension((d != null) ? d : (class_310.method_1551()).field_1687.method_27983());
/* 24 */       MapConnectionNode playerMapKey = mapWorld.getPlayerMapKey();
/* 25 */       if (playerMapKey == null) {
/* 26 */         class_5250 messageComponent = class_2561.method_43470(class_1074.method_4662("gui.xaero_wm_teleport_never_confirmed", new Object[0]));
/* 27 */         messageComponent.method_10862(messageComponent.method_10866().method_10977(class_124.field_1061));
/* 28 */         (class_310.method_1551()).field_1705.method_1743().method_1812((class_2561)messageComponent);
/*    */         return;
/*    */       } 
/* 31 */       MapConnectionNode destinationMapKey = (destinationDim == null) ? null : destinationDim.getSelectedMapKeyUnsynced();
/* 32 */       if (!mapWorld.getMapConnections().isConnected(playerMapKey, destinationMapKey)) {
/* 33 */         class_5250 messageComponent = class_2561.method_43470(class_1074.method_4662("gui.xaero_wm_teleport_not_connected", new Object[0]));
/* 34 */         messageComponent.method_10862(messageComponent.method_10866().method_10977(class_124.field_1061));
/* 35 */         (class_310.method_1551()).field_1705.method_1743().method_1812((class_2561)messageComponent);
/*    */         return;
/*    */       } 
/*    */     } 
/* 39 */     String tpCommand = (d == null) ? mapWorld.getTeleportCommandFormat() : mapWorld.getDimensionTeleportCommandFormat();
/* 40 */     String yString = (y == 32767) ? "~" : (WorldMap.settings.partialYTeleportation ? ("" + y + 0.5D) : ("" + y));
/* 41 */     tpCommand = tpCommand.replace("{x}", "" + x).replace("{y}", yString).replace("{z}", "" + z);
/* 42 */     if (d != null)
/* 43 */       tpCommand = tpCommand.replace("{d}", d.method_29177().toString()); 
/* 44 */     class_310 mc = class_310.method_1551();
/* 45 */     if (tpCommand.startsWith("/")) {
/* 46 */       tpCommand = tpCommand.substring(1);
/* 47 */       mc.field_1724.field_3944.method_45730(tpCommand);
/*    */     } else {
/* 49 */       mc.field_1724.field_3944.method_45729(tpCommand);
/* 50 */     }  mapWorld.setCustomDimensionId(null);
/* 51 */     mapWorld.getMapProcessor().checkForWorldUpdate();
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\teleport\MapTeleporter.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */