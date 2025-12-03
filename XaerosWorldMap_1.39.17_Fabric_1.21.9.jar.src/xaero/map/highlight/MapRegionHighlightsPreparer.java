/*    */ package xaero.map.highlight;
/*    */ 
/*    */ import net.minecraft.class_310;
/*    */ import xaero.map.region.MapRegion;
/*    */ import xaero.map.region.MapTileChunk;
/*    */ 
/*    */ public class MapRegionHighlightsPreparer
/*    */ {
/*    */   public void prepare(MapRegion region, boolean tileChunkDiscoveryKnown) {
/* 10 */     if (!class_310.method_1551().method_18854())
/* 11 */       throw new RuntimeException(new IllegalAccessException()); 
/* 12 */     region.updateTargetHighlightsHash();
/* 13 */     DimensionHighlighterHandler highlighterHandler = region.getDim().getHighlightHandler();
/* 14 */     boolean regionHasHighlights = highlighterHandler.shouldApplyRegionHighlights(region.getRegionX(), region.getRegionZ(), region.hasHadTerrain());
/* 15 */     for (int i = 0; i < 8; i++) {
/* 16 */       for (int j = 0; j < 8; j++)
/* 17 */         prepare(region, i, j, regionHasHighlights, tileChunkDiscoveryKnown); 
/*    */     } 
/*    */   }
/*    */   
/*    */   private void prepare(MapRegion region, int x, int z, boolean regionHasHighlights, boolean tileChunkDiscoveryKnown) {
/* 22 */     DimensionHighlighterHandler highlighterHandler = region.getDim().getHighlightHandler();
/* 23 */     MapTileChunk tileChunk = region.getChunk(x, z);
/*    */     
/* 25 */     boolean tileChunkHasHighlights = !regionHasHighlights ? false : highlighterHandler.shouldApplyTileChunkHighlights(region.getRegionX(), region.getRegionZ(), x, z, !tileChunkDiscoveryKnown ? true : ((tileChunk != null)));
/*    */     
/* 27 */     boolean tileChunkHasHighlightsUndiscovered = !regionHasHighlights ? false : highlighterHandler.shouldApplyTileChunkHighlights(region.getRegionX(), region.getRegionZ(), x, z, false);
/* 28 */     if (tileChunk == null) {
/* 29 */       if (!tileChunkHasHighlights)
/*    */         return; 
/* 31 */       tileChunk = region.createTexture(x, z).getTileChunk();
/*    */     } 
/* 33 */     tileChunk.setHasHighlights(tileChunkHasHighlights);
/* 34 */     tileChunk.setHasHighlightsIfUndiscovered(tileChunkHasHighlightsUndiscovered);
/*    */   }
/*    */   
/*    */   public void prepare(MapRegion region, int x, int z, boolean tileChunkDiscoveryKnown) {
/* 38 */     if (!class_310.method_1551().method_18854())
/* 39 */       throw new RuntimeException(new IllegalAccessException()); 
/* 40 */     DimensionHighlighterHandler highlighterHandler = region.getDim().getHighlightHandler();
/* 41 */     prepare(region, x, z, highlighterHandler.shouldApplyRegionHighlights(region.getRegionX(), region.getRegionZ(), region.hasHadTerrain()), tileChunkDiscoveryKnown);
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\highlight\MapRegionHighlightsPreparer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */