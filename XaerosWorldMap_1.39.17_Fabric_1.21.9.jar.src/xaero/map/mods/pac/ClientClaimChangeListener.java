/*    */ package xaero.map.mods.pac;
/*    */ 
/*    */ import net.minecraft.class_2960;
/*    */ import net.minecraft.class_5321;
/*    */ import net.minecraft.class_7924;
/*    */ import xaero.map.WorldMapSession;
/*    */ import xaero.map.world.MapDimension;
/*    */ import xaero.pac.common.claims.player.api.IPlayerChunkClaimAPI;
/*    */ import xaero.pac.common.claims.tracker.api.IClaimsManagerListenerAPI;
/*    */ 
/*    */ 
/*    */ public class ClientClaimChangeListener
/*    */   implements IClaimsManagerListenerAPI
/*    */ {
/*    */   public void onWholeRegionChange(class_2960 dimension, int regionX, int regionZ) {
/* 16 */     WorldMapSession session = WorldMapSession.getCurrentSession();
/* 17 */     MapDimension mapDim = session.getMapProcessor().getMapWorld().getDimension(class_5321.method_29179(class_7924.field_41223, dimension));
/* 18 */     if (mapDim != null)
/* 19 */       for (int i = -1; i < 2; i++) {
/* 20 */         for (int j = -1; j < 2; j++) {
/* 21 */           if ((i == 0 && j == 0) || i * i != j * j) {
/* 22 */             mapDim.getHighlightHandler().clearCachedHash(regionX + i, regionZ + j);
/*    */           }
/*    */         } 
/*    */       }  
/*    */   }
/*    */   
/*    */   public void onChunkChange(class_2960 dimension, int chunkX, int chunkZ, IPlayerChunkClaimAPI claim) {
/* 29 */     WorldMapSession session = WorldMapSession.getCurrentSession();
/* 30 */     MapDimension mapDim = session.getMapProcessor().getMapWorld().getDimension(class_5321.method_29179(class_7924.field_41223, dimension));
/* 31 */     if (mapDim != null)
/* 32 */       for (int i = -1; i < 2; i++) {
/* 33 */         for (int j = -1; j < 2; j++) {
/* 34 */           if ((i == 0 && j == 0) || i * i != j * j)
/* 35 */             mapDim.getHighlightHandler().clearCachedHash(chunkX + i >> 5, chunkZ + j >> 5); 
/*    */         } 
/*    */       }  
/*    */   }
/*    */   
/*    */   public void onDimensionChange(class_2960 dimension) {
/* 41 */     WorldMapSession session = WorldMapSession.getCurrentSession();
/* 42 */     MapDimension mapDim = session.getMapProcessor().getMapWorld().getDimension(class_5321.method_29179(class_7924.field_41223, dimension));
/* 43 */     if (mapDim != null)
/* 44 */       mapDim.getHighlightHandler().clearCachedHashes(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\mods\pac\ClientClaimChangeListener.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */