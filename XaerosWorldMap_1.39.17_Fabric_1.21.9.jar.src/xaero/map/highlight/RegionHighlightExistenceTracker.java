/*    */ package xaero.map.highlight;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
/*    */ import it.unimi.dsi.fastutil.longs.LongSet;
/*    */ import xaero.map.region.BranchLeveledRegion;
/*    */ import xaero.map.region.LeveledRegion;
/*    */ import xaero.map.world.MapDimension;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RegionHighlightExistenceTracker
/*    */ {
/*    */   private final MapDimension mapDimension;
/*    */   private final int caveLayer;
/*    */   private final LongSet regionsToTrackExistenceOf;
/*    */   
/*    */   public RegionHighlightExistenceTracker(MapDimension mapDimension, int caveLayer) {
/* 19 */     this.mapDimension = mapDimension;
/* 20 */     this.caveLayer = caveLayer;
/* 21 */     this.regionsToTrackExistenceOf = (LongSet)new LongOpenHashSet();
/*    */   }
/*    */ 
/*    */   
/*    */   private void requestBranchUpdates(int regionX, int regionZ) {
/* 26 */     for (int i = 1; i <= 3; i++) {
/* 27 */       int leveledRegionX = regionX >> i;
/* 28 */       int leveledRegionZ = regionZ >> i;
/* 29 */       LeveledRegion<?> leveledParent = this.mapDimension.getLayeredMapRegions().get(this.caveLayer, leveledRegionX, leveledRegionZ, i);
/* 30 */       if (leveledParent != null) {
/* 31 */         ((BranchLeveledRegion)leveledParent).setShouldCheckForUpdatesRecursive(true);
/*    */         break;
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public void onClearCachedHash(int regionX, int regionZ) {
/* 38 */     long key = DimensionHighlighterHandler.getKey(regionX, regionZ);
/* 39 */     if (this.regionsToTrackExistenceOf.remove(key))
/* 40 */       requestBranchUpdates(regionX, regionZ); 
/*    */   }
/*    */   
/*    */   public void onClearCachedHashes() {
/* 44 */     this.regionsToTrackExistenceOf.forEach(key -> requestBranchUpdates(DimensionHighlighterHandler.getXFromKey(key), DimensionHighlighterHandler.getZFromKey(key)));
/* 45 */     this.regionsToTrackExistenceOf.clear();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void track(int regionX, int regionZ) {
/* 52 */     this.regionsToTrackExistenceOf.add(DimensionHighlighterHandler.getKey(regionX, regionZ));
/*    */   }
/*    */   
/*    */   public void stopTracking(int regionX, int regionZ) {
/* 56 */     this.regionsToTrackExistenceOf.remove(DimensionHighlighterHandler.getKey(regionX, regionZ));
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\highlight\RegionHighlightExistenceTracker.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */