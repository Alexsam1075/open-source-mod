/*     */ package xaero.map.region;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.function.BiConsumer;
/*     */ import xaero.map.highlight.RegionHighlightExistenceTracker;
/*     */ import xaero.map.world.MapDimension;
/*     */ 
/*     */ 
/*     */ public class LayeredRegionManager
/*     */ {
/*     */   private final MapDimension mapDimension;
/*     */   private final Int2ObjectMap<MapLayer> mapLayers;
/*     */   private Set<LeveledRegion<?>> regionsListAll;
/*     */   private List<LeveledRegion<?>> regionsListLoaded;
/*     */   
/*     */   public LayeredRegionManager(MapDimension mapDimension) {
/*  23 */     this.mapDimension = mapDimension;
/*  24 */     this.mapLayers = (Int2ObjectMap<MapLayer>)new Int2ObjectOpenHashMap();
/*  25 */     this.regionsListAll = new HashSet<>();
/*  26 */     this.regionsListLoaded = new ArrayList<>();
/*     */   }
/*     */   
/*     */   public void putLeaf(int X, int Z, MapRegion leaf) {
/*  30 */     getLayer(leaf.caveLayer).getMapRegions().putLeaf(X, Z, leaf);
/*     */   }
/*     */   
/*     */   public MapRegion getLeaf(int caveLayer, int X, int Z) {
/*  34 */     return getLayer(caveLayer).getMapRegions().getLeaf(X, Z);
/*     */   }
/*     */   
/*     */   public LeveledRegion<?> get(int caveLayer, int leveledX, int leveledZ, int level) {
/*  38 */     return getLayer(caveLayer).getMapRegions().get(leveledX, leveledZ, level);
/*     */   }
/*     */   
/*     */   public boolean remove(int caveLayer, int leveledX, int leveledZ, int level) {
/*  42 */     return getLayer(caveLayer).getMapRegions().remove(leveledX, leveledZ, level);
/*     */   }
/*     */   
/*     */   public MapLayer getLayer(int caveLayer) {
/*     */     MapLayer mapLayer;
/*  47 */     synchronized (this.mapLayers) {
/*  48 */       mapLayer = (MapLayer)this.mapLayers.get(caveLayer);
/*  49 */       if (mapLayer == null) {
/*  50 */         this.mapLayers.put(caveLayer, mapLayer = new MapLayer(this.mapDimension, new RegionHighlightExistenceTracker(this.mapDimension, caveLayer)));
/*     */       }
/*     */     } 
/*     */     
/*  54 */     return mapLayer;
/*     */   }
/*     */   
/*     */   public void clear() {
/*  58 */     synchronized (this.mapLayers) {
/*  59 */       this.mapLayers.clear();
/*     */     } 
/*  61 */     synchronized (this.regionsListAll) {
/*  62 */       this.regionsListAll.clear();
/*     */     } 
/*  64 */     synchronized (this.regionsListLoaded) {
/*  65 */       this.regionsListLoaded.clear();
/*     */     } 
/*     */   }
/*     */   
/*     */   public int loadedCount() {
/*  70 */     return this.regionsListLoaded.size();
/*     */   }
/*     */   
/*     */   public void removeListRegion(LeveledRegion<?> reg) {
/*  74 */     synchronized (this.regionsListAll) {
/*  75 */       this.regionsListAll.remove(reg);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addListRegion(LeveledRegion<?> reg) {
/*  80 */     synchronized (this.regionsListAll) {
/*  81 */       this.regionsListAll.add(reg);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void bumpLoadedRegion(MapRegion reg) {
/*  87 */     bumpLoadedRegion(reg);
/*     */   }
/*     */   
/*     */   public void bumpLoadedRegion(LeveledRegion<?> reg) {
/*  91 */     synchronized (this.regionsListLoaded) {
/*  92 */       if (this.regionsListLoaded.remove(reg))
/*  93 */         this.regionsListLoaded.add(reg); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public List<LeveledRegion<?>> getLoadedListUnsynced() {
/*  98 */     return this.regionsListLoaded;
/*     */   }
/*     */   
/*     */   public LeveledRegion<?> getLoadedRegion(int index) {
/* 102 */     synchronized (this.regionsListLoaded) {
/* 103 */       return this.regionsListLoaded.get(index);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addLoadedRegion(LeveledRegion<?> reg) {
/* 108 */     synchronized (this.regionsListLoaded) {
/* 109 */       this.regionsListLoaded.add(reg);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void removeLoadedRegion(LeveledRegion<?> reg) {
/* 114 */     synchronized (this.regionsListLoaded) {
/* 115 */       this.regionsListLoaded.remove(reg);
/*     */     } 
/*     */   }
/*     */   
/*     */   public int size() {
/* 120 */     return this.regionsListAll.size();
/*     */   }
/*     */   
/*     */   public Set<LeveledRegion<?>> getUnsyncedSet() {
/* 124 */     return this.regionsListAll;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onClearCachedHighlightHash(int regionX, int regionZ) {
/* 129 */     synchronized (this.mapLayers) {
/* 130 */       this.mapLayers.forEach((i, layer) -> layer.getRegionHighlightExistenceTracker().onClearCachedHash(regionX, regionZ));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onClearCachedHighlightHashes() {
/* 136 */     synchronized (this.mapLayers) {
/* 137 */       this.mapLayers.forEach((i, layer) -> layer.getRegionHighlightExistenceTracker().onClearCachedHashes());
/*     */     } 
/*     */   }
/*     */   
/*     */   public void applyToEachLoadedLayer(BiConsumer<Integer, MapLayer> consumer) {
/* 142 */     synchronized (this.mapLayers) {
/* 143 */       Objects.requireNonNull(consumer); this.mapLayers.forEach(consumer::accept);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void preDetection() {
/* 148 */     synchronized (this.mapLayers) {
/* 149 */       this.mapLayers.forEach((i, layer) -> layer.preDetection());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\region\LayeredRegionManager.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */