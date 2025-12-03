/*    */ package xaero.map.mcworld;
/*    */ 
/*    */ import net.minecraft.class_310;
/*    */ import net.minecraft.class_638;
/*    */ 
/*    */ public class WorldMapClientWorldDataHelper
/*    */ {
/*    */   public static WorldMapClientWorldData getCurrentWorldData() {
/*  9 */     return getWorldData((class_310.method_1551()).field_1687);
/*    */   }
/*    */   
/*    */   public static synchronized WorldMapClientWorldData getWorldData(class_638 clientWorld) {
/* 13 */     if (clientWorld == null)
/* 14 */       return null; 
/* 15 */     IWorldMapClientWorld inter = (IWorldMapClientWorld)clientWorld;
/* 16 */     WorldMapClientWorldData worldmapWorldData = inter.getXaero_worldmapData();
/* 17 */     if (worldmapWorldData == null)
/* 18 */       inter.setXaero_worldmapData(worldmapWorldData = new WorldMapClientWorldData(clientWorld)); 
/* 19 */     return worldmapWorldData;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\mcworld\WorldMapClientWorldDataHelper.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */