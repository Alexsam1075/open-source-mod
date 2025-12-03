/*    */ package xaero.map.core;
/*    */ 
/*    */ import net.minecraft.class_2678;
/*    */ import net.minecraft.class_634;
/*    */ import xaero.map.WorldMap;
/*    */ import xaero.map.WorldMapFabric;
/*    */ 
/*    */ public class XaeroWorldMapCoreFabric
/*    */ {
/*    */   public static void onPlayNetHandler(class_634 netHandler, class_2678 packet) {
/* 11 */     if (WorldMap.INSTANCE != null)
/* 12 */       ((WorldMapFabric)WorldMap.INSTANCE).tryLoadLater(); 
/* 13 */     if (!WorldMap.loaded)
/*    */       return; 
/* 15 */     if (WorldMap.crashHandler.getCrashedBy() != null)
/*    */       return; 
/* 17 */     XaeroWorldMapCore.onPlayNetHandler(netHandler, packet);
/*    */   }
/*    */ 
/*    */   
/*    */   public static void onMinecraftRunTick() {
/* 22 */     if (WorldMap.INSTANCE != null)
/* 23 */       ((WorldMapFabric)WorldMap.INSTANCE).tryLoadLater(); 
/* 24 */     XaeroWorldMapCore.onMinecraftRunTick();
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\core\XaeroWorldMapCoreFabric.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */