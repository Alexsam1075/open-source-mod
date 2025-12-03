/*    */ package xaero.map.server.core;
/*    */ 
/*    */ import net.minecraft.class_1657;
/*    */ import net.minecraft.class_3222;
/*    */ import xaero.map.WorldMap;
/*    */ 
/*    */ public class XaeroWorldMapServerCore
/*    */ {
/*    */   public static void onServerWorldInfo(class_1657 player) {
/* 10 */     if (!WorldMap.loaded)
/*    */       return; 
/* 12 */     WorldMap.commonEvents.onPlayerWorldJoin((class_3222)player);
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\server\core\XaeroWorldMapServerCore.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */