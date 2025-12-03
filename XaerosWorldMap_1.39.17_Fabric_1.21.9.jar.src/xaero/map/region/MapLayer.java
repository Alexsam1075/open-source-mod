/*     */ package xaero.map.region;
/*     */ 
/*     */ import java.util.Hashtable;
/*     */ import xaero.map.file.RegionDetection;
/*     */ import xaero.map.highlight.RegionHighlightExistenceTracker;
/*     */ import xaero.map.util.linked.ILinkedChainNode;
/*     */ import xaero.map.util.linked.LinkedChain;
/*     */ import xaero.map.world.MapDimension;
/*     */ 
/*     */ 
/*     */ public class MapLayer
/*     */ {
/*     */   private final MapDimension mapDimension;
/*     */   private final LeveledRegionManager mapRegions;
/*     */   private final RegionHighlightExistenceTracker regionHighlightExistenceTracker;
/*     */   private final Hashtable<Integer, Hashtable<Integer, RegionDetection>> detectedRegions;
/*     */   private final Hashtable<Integer, Hashtable<Integer, RegionDetection>> completeDetectedRegions;
/*     */   private final LinkedChain<RegionDetection> completeDetectedRegionsLinked;
/*     */   private int caveStart;
/*     */   
/*     */   public MapLayer(MapDimension mapDimension, RegionHighlightExistenceTracker regionHighlightExistenceTracker) {
/*  22 */     this.mapDimension = mapDimension;
/*  23 */     this.mapRegions = new LeveledRegionManager();
/*  24 */     this.regionHighlightExistenceTracker = regionHighlightExistenceTracker;
/*  25 */     this.detectedRegions = new Hashtable<>();
/*  26 */     this.completeDetectedRegions = new Hashtable<>();
/*  27 */     this.completeDetectedRegionsLinked = new LinkedChain();
/*     */   }
/*     */   
/*     */   public boolean regionDetectionExists(int x, int z) {
/*  31 */     return (getRegionDetection(x, z) != null);
/*     */   }
/*     */   
/*     */   public void addRegionDetection(RegionDetection regionDetection) {
/*  35 */     synchronized (this.detectedRegions) {
/*  36 */       Hashtable<Integer, RegionDetection> column = this.detectedRegions.get(Integer.valueOf(regionDetection.getRegionX()));
/*  37 */       if (column == null)
/*  38 */         this.detectedRegions.put(Integer.valueOf(regionDetection.getRegionX()), column = new Hashtable<>()); 
/*  39 */       column.put(Integer.valueOf(regionDetection.getRegionZ()), regionDetection);
/*  40 */       tryAddingToCompleteRegionDetection(regionDetection);
/*     */     } 
/*     */   }
/*     */   
/*     */   public RegionDetection getCompleteRegionDetection(int x, int z) {
/*  45 */     if (this.mapDimension.isUsingWorldSave())
/*  46 */       return this.mapDimension.getWorldSaveRegionDetection(x, z); 
/*  47 */     Hashtable<Integer, RegionDetection> column = this.completeDetectedRegions.get(Integer.valueOf(x));
/*  48 */     if (column != null)
/*  49 */       return column.get(Integer.valueOf(z)); 
/*  50 */     return null;
/*     */   }
/*     */   
/*     */   private boolean completeRegionDetectionContains(RegionDetection regionDetection) {
/*  54 */     return (getCompleteRegionDetection(regionDetection.getRegionX(), regionDetection.getRegionZ()) != null);
/*     */   }
/*     */   
/*     */   public void tryAddingToCompleteRegionDetection(RegionDetection regionDetection) {
/*  58 */     if (completeRegionDetectionContains(regionDetection))
/*     */       return; 
/*  60 */     if (this.mapDimension.isUsingWorldSave()) {
/*  61 */       this.mapDimension.addWorldSaveRegionDetection(regionDetection);
/*     */       return;
/*     */     } 
/*  64 */     synchronized (this.completeDetectedRegions) {
/*  65 */       Hashtable<Integer, RegionDetection> column = this.completeDetectedRegions.get(Integer.valueOf(regionDetection.getRegionX()));
/*  66 */       if (column == null)
/*  67 */         this.completeDetectedRegions.put(Integer.valueOf(regionDetection.getRegionX()), column = new Hashtable<>()); 
/*  68 */       column.put(Integer.valueOf(regionDetection.getRegionZ()), regionDetection);
/*  69 */       this.completeDetectedRegionsLinked.add((ILinkedChainNode)regionDetection);
/*     */     } 
/*     */   }
/*     */   
/*     */   public RegionDetection getRegionDetection(int x, int z) {
/*  74 */     Hashtable<Integer, RegionDetection> column = this.detectedRegions.get(Integer.valueOf(x));
/*  75 */     RegionDetection result = null;
/*  76 */     if (column != null)
/*  77 */       result = column.get(Integer.valueOf(z)); 
/*  78 */     if (result == null) {
/*  79 */       RegionDetection worldSaveDetection = this.mapDimension.getWorldSaveRegionDetection(x, z);
/*  80 */       if (worldSaveDetection != null) {
/*     */ 
/*     */         
/*  83 */         result = new RegionDetection(worldSaveDetection.getWorldId(), worldSaveDetection.getDimId(), worldSaveDetection.getMwId(), worldSaveDetection.getRegionX(), worldSaveDetection.getRegionZ(), worldSaveDetection.getRegionFile(), worldSaveDetection.getInitialVersion(), worldSaveDetection.isHasHadTerrain());
/*  84 */         addRegionDetection(result);
/*  85 */         return result;
/*     */       } 
/*  87 */     } else if (result.isRemoved()) {
/*  88 */       return null;
/*  89 */     }  return result;
/*     */   }
/*     */   
/*     */   public void removeRegionDetection(int x, int z) {
/*  93 */     if (this.mapDimension.getWorldSaveRegionDetection(x, z) != null) {
/*  94 */       RegionDetection regionDetection = getRegionDetection(x, z);
/*  95 */       if (regionDetection != null)
/*  96 */         regionDetection.setRemoved(true); 
/*     */       return;
/*     */     } 
/*  99 */     synchronized (this.detectedRegions) {
/* 100 */       Hashtable<Integer, RegionDetection> column = this.detectedRegions.get(Integer.valueOf(x));
/* 101 */       if (column != null) {
/* 102 */         column.remove(Integer.valueOf(z));
/* 103 */         if (column.isEmpty())
/* 104 */           this.detectedRegions.remove(Integer.valueOf(x)); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public RegionHighlightExistenceTracker getRegionHighlightExistenceTracker() {
/* 110 */     return this.regionHighlightExistenceTracker;
/*     */   }
/*     */   
/*     */   public LeveledRegionManager getMapRegions() {
/* 114 */     return this.mapRegions;
/*     */   }
/*     */   
/*     */   public Hashtable<Integer, Hashtable<Integer, RegionDetection>> getDetectedRegions() {
/* 118 */     return this.detectedRegions;
/*     */   }
/*     */   
/*     */   public Iterable<RegionDetection> getLinkedCompleteWorldSaveDetectedRegions() {
/* 122 */     return this.mapDimension.isUsingWorldSave() ? this.mapDimension.getLinkedWorldSaveDetectedRegions() : (Iterable<RegionDetection>)this.completeDetectedRegionsLinked;
/*     */   }
/*     */   
/*     */   public void preDetection() {
/* 126 */     this.detectedRegions.clear();
/* 127 */     this.completeDetectedRegions.clear();
/* 128 */     this.completeDetectedRegionsLinked.reset();
/*     */   }
/*     */   
/*     */   public int getCaveStart() {
/* 132 */     return this.caveStart;
/*     */   }
/*     */   
/*     */   public void setCaveStart(int caveStart) {
/* 136 */     this.caveStart = caveStart;
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\region\MapLayer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */