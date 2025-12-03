/*     */ package xaero.map.minimap;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.class_1657;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_746;
/*     */ import xaero.map.MapProcessor;
/*     */ import xaero.map.WorldMap;
/*     */ import xaero.map.misc.Misc;
/*     */ import xaero.map.region.LeveledRegion;
/*     */ import xaero.map.region.MapRegion;
/*     */ 
/*     */ public class MinimapRenderListener
/*     */ {
/*  15 */   private ArrayList<MapRegion> regionBuffer = new ArrayList<>();
/*     */   private boolean shouldRequestLoading;
/*     */   private boolean playerMoving;
/*     */   private int renderedCaveLayer;
/*     */   private boolean isCacheOnlyMode;
/*     */   private int globalRegionCacheHashCode;
/*     */   private boolean reloadEverything;
/*     */   private int globalVersion;
/*     */   private int globalReloadVersion;
/*     */   private int globalCaveStart;
/*     */   private int globalCaveDepth;
/*     */   private MapRegion prevRegion;
/*     */   
/*     */   public void init(MapProcessor mapProcessor, int flooredMapCameraX, int flooredMapCameraZ) {
/*  29 */     mapProcessor.updateCaveStart();
/*  30 */     class_746 class_746 = (class_310.method_1551()).field_1724;
/*  31 */     this.playerMoving = (((class_1657)class_746).field_6014 != class_746.method_23317() || ((class_1657)class_746).field_6036 != class_746.method_23318() || ((class_1657)class_746).field_5969 != class_746.method_23321());
/*  32 */     this.renderedCaveLayer = mapProcessor.getCurrentCaveLayer();
/*  33 */     this.isCacheOnlyMode = mapProcessor.getMapWorld().getCurrentDimension().isCacheOnlyMode(mapProcessor.getWorldDimensionTypeRegistry());
/*  34 */     this.globalRegionCacheHashCode = WorldMap.settings.getRegionCacheHashCode();
/*  35 */     this.reloadEverything = WorldMap.settings.reloadEverything;
/*  36 */     this.globalVersion = mapProcessor.getGlobalVersion();
/*  37 */     this.globalReloadVersion = WorldMap.settings.reloadVersion;
/*  38 */     this.globalCaveStart = mapProcessor.getMapWorld().getCurrentDimension().getLayeredMapRegions().getLayer(this.renderedCaveLayer).getCaveStart();
/*  39 */     this.globalCaveDepth = WorldMap.settings.caveModeDepth;
/*  40 */     this.prevRegion = null;
/*     */     
/*  42 */     this.shouldRequestLoading = false;
/*  43 */     LeveledRegion<?> nextToLoad = mapProcessor.getMapSaveLoad().getNextToLoadByViewing();
/*  44 */     this.shouldRequestLoading = (nextToLoad == null || nextToLoad.shouldAllowAnotherRegionToLoad());
/*  45 */     int comparisonChunkX = (flooredMapCameraX >> 4) - 16;
/*  46 */     int comparisonChunkZ = (flooredMapCameraZ >> 4) - 16;
/*  47 */     LeveledRegion.setComparison(comparisonChunkX, comparisonChunkZ, 0, comparisonChunkX, comparisonChunkZ);
/*     */   }
/*     */   
/*     */   public void beforeMinimapRender(MapRegion region) {
/*  51 */     if (!this.shouldRequestLoading)
/*     */       return; 
/*  53 */     if (region != null && region != this.prevRegion)
/*  54 */       synchronized (region) {
/*  55 */         int regionHashCode = region.getCacheHashCode();
/*  56 */         if (region.canRequestReload_unsynced()) if (region
/*     */ 
/*     */             
/*  59 */             .getLoadState() == 0 || ((region
/*     */ 
/*     */             
/*  62 */             .getLoadState() == 4 || (region.getLoadState() == 2 && region.isBeingWritten())) && ((!this.isCacheOnlyMode && ((this.reloadEverything && region
/*     */ 
/*     */             
/*  65 */             .getReloadVersion() != this.globalReloadVersion) || regionHashCode != this.globalRegionCacheHashCode || (!this.playerMoving && region
/*     */             
/*  67 */             .caveStartOutdated(this.globalCaveStart, this.globalCaveDepth)) || region
/*  68 */             .getVersion() != this.globalVersion || (region
/*  69 */             .getLoadState() != 2 && region.shouldCache()))) || ((region
/*     */             
/*  71 */             .isMetaLoaded() || region.getLoadState() != 0 || !region.hasHadTerrain()) && region.getHighlightsHash() != region.getDim().getHighlightHandler().getRegionHash(region.getRegionX(), region.getRegionZ())))))
/*     */           {
/*     */ 
/*     */             
/*  75 */             if (!this.regionBuffer.contains(region)) {
/*  76 */               region.calculateSortingChunkDistance();
/*  77 */               Misc.addToListOfSmallest(10, this.regionBuffer, (Comparable)region);
/*     */             } 
/*     */           } 
/*     */       }  
/*  81 */     this.prevRegion = region;
/*     */   }
/*     */   
/*     */   public void finalize(MapProcessor mapProcessor) {
/*  85 */     int toRequest = 1;
/*  86 */     int counter = 0;
/*  87 */     LeveledRegion<?> nextToLoad = mapProcessor.getMapSaveLoad().getNextToLoadByViewing();
/*  88 */     for (int i = 0; i < this.regionBuffer.size() && counter < toRequest; i++) {
/*  89 */       MapRegion region = this.regionBuffer.get(i);
/*  90 */       if (region != nextToLoad || this.regionBuffer.size() <= 1)
/*     */       {
/*  92 */         synchronized (region) {
/*  93 */           if (!region.canRequestReload_unsynced()) {  }
/*     */           else
/*  95 */           { if (region.getLoadState() == 2) {
/*  96 */               region.requestRefresh(mapProcessor);
/*     */             } else {
/*  98 */               mapProcessor.getMapSaveLoad().requestLoad(region, "Minimap listener", false);
/*  99 */             }  if (counter == 0)
/* 100 */               mapProcessor.getMapSaveLoad().setNextToLoadByViewing((LeveledRegion)region); 
/* 101 */             counter++;
/* 102 */             if (region.getLoadState() == 4)
/*     */               break;  }
/*     */         
/*     */         }  } 
/* 106 */     }  this.regionBuffer.clear();
/*     */   }
/*     */   
/*     */   public int getRenderedCaveLayer() {
/* 110 */     return this.renderedCaveLayer;
/*     */   }
/*     */   
/*     */   public boolean shouldRequestLoading() {
/* 114 */     return this.shouldRequestLoading;
/*     */   }
/*     */   
/*     */   public boolean isPlayerMoving() {
/* 118 */     return this.playerMoving;
/*     */   }
/*     */   
/*     */   public boolean isCacheOnlyMode() {
/* 122 */     return this.isCacheOnlyMode;
/*     */   }
/*     */   
/*     */   public int getGlobalRegionCacheHashCode() {
/* 126 */     return this.globalRegionCacheHashCode;
/*     */   }
/*     */   
/*     */   public boolean isReloadEverything() {
/* 130 */     return this.reloadEverything;
/*     */   }
/*     */   
/*     */   public int getGlobalVersion() {
/* 134 */     return this.globalVersion;
/*     */   }
/*     */   
/*     */   public int getGlobalReloadVersion() {
/* 138 */     return this.globalReloadVersion;
/*     */   }
/*     */   
/*     */   public int getGlobalCaveStart() {
/* 142 */     return this.globalCaveStart;
/*     */   }
/*     */   
/*     */   public int getGlobalCaveDepth() {
/* 146 */     return this.globalCaveDepth;
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\minimap\MinimapRenderListener.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */