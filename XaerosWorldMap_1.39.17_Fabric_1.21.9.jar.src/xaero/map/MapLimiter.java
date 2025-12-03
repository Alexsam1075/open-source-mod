/*     */ package xaero.map;
/*     */ 
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.ArrayList;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.lwjgl.opengl.GL;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import xaero.map.region.LayeredRegionManager;
/*     */ import xaero.map.region.LeveledRegion;
/*     */ import xaero.map.world.MapDimension;
/*     */ import xaero.map.world.MapWorld;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MapLimiter
/*     */ {
/*     */   private static final int MIN_LIMIT = 53;
/*     */   private static final int DEFAULT_LIMIT = 203;
/*     */   private static final int MAX_LIMIT = 403;
/*  22 */   private int availableVRAM = -1;
/*     */   private int mostRegionsAtATime;
/*  24 */   private IntBuffer vramBuffer = BufferUtils.createByteBuffer(64).asIntBuffer();
/*  25 */   private int driverType = -1;
/*  26 */   private ArrayList<MapDimension> workingDimList = new ArrayList<>();
/*     */   
/*     */   public int getAvailableVRAM() {
/*  29 */     return this.availableVRAM;
/*     */   }
/*     */   
/*     */   private void determineDriverType() {
/*  33 */     if ((GL.getCapabilities()).GL_NVX_gpu_memory_info) {
/*  34 */       this.driverType = 0;
/*  35 */     } else if ((GL.getCapabilities()).GL_ATI_meminfo) {
/*  36 */       this.driverType = 1;
/*     */     } else {
/*  38 */       this.driverType = 2;
/*     */     } 
/*     */   }
/*     */   public void updateAvailableVRAM() {
/*  42 */     if (this.driverType == -1)
/*  43 */       determineDriverType(); 
/*  44 */     switch (this.driverType) {
/*     */       case 0:
/*  46 */         this.vramBuffer.clear();
/*  47 */         GL11.glGetIntegerv(36937, this.vramBuffer);
/*  48 */         this.availableVRAM = this.vramBuffer.get(0);
/*     */         break;
/*     */       case 1:
/*  51 */         this.vramBuffer.clear();
/*  52 */         GL11.glGetIntegerv(34812, this.vramBuffer);
/*  53 */         this.availableVRAM = this.vramBuffer.get(0);
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getMostRegionsAtATime() {
/*  59 */     return this.mostRegionsAtATime;
/*     */   }
/*     */   
/*     */   public void setMostRegionsAtATime(int mostRegionsAtATime) {
/*  63 */     this.mostRegionsAtATime = mostRegionsAtATime;
/*     */   }
/*     */   
/*     */   public void applyLimit(MapWorld mapWorld, MapProcessor mapProcessor) {
/*  67 */     int limit = Math.max(this.mostRegionsAtATime, 53);
/*  68 */     int vramDetermined = 0;
/*  69 */     int loadedCount = 0;
/*  70 */     this.workingDimList.clear();
/*  71 */     mapWorld.getDimensions(this.workingDimList);
/*  72 */     for (MapDimension dim : this.workingDimList)
/*  73 */       loadedCount += dim.getLayeredMapRegions().loadedCount(); 
/*  74 */     if (this.availableVRAM != -1) {
/*  75 */       if (this.availableVRAM < 204800)
/*  76 */       { vramDetermined = Math.min(403, loadedCount) - 6; }
/*  77 */       else if (loadedCount > 403)
/*  78 */       { vramDetermined = 397; }
/*     */       else { return; }
/*     */     
/*     */     } else {
/*  82 */       vramDetermined = (loadedCount > 203) ? 197 : loadedCount;
/*  83 */     }  if (vramDetermined > limit) {
/*  84 */       limit = vramDetermined;
/*     */     }
/*  86 */     int count = 0;
/*  87 */     mapProcessor.pushRenderPause(false, true);
/*  88 */     LeveledRegion<?> nextToLoad = mapProcessor.getMapSaveLoad().getNextToLoadByViewing();
/*  89 */     int currentDimIndex = this.workingDimList.indexOf(mapWorld.getCurrentDimension());
/*  90 */     int dimCount = 0;
/*  91 */     int dimTotal = this.workingDimList.size(); int d;
/*  92 */     for (d = (currentDimIndex + 1) % dimTotal; dimCount < dimTotal && loadedCount > limit; d = (d + 1) % dimTotal) {
/*  93 */       MapDimension dimension = this.workingDimList.get(d);
/*  94 */       LayeredRegionManager regions = dimension.getLayeredMapRegions();
/*  95 */       for (int i = 0; i < regions.loadedCount() && loadedCount > limit; i++) {
/*  96 */         LeveledRegion<?> region = regions.getLoadedRegion(i);
/*  97 */         if (region.isLoaded() && !region.shouldBeProcessed() && region.activeBranchUpdateReferences == 0) {
/*  98 */           region.onLimiterRemoval(mapProcessor);
/*  99 */           region.deleteTexturesAndBuffers();
/* 100 */           mapProcessor.getMapSaveLoad().removeToCache(region);
/* 101 */           region.afterLimiterRemoval(mapProcessor);
/* 102 */           if (region == nextToLoad)
/* 103 */             mapProcessor.getMapSaveLoad().setNextToLoadByViewing(null); 
/* 104 */           count++;
/* 105 */           i--;
/* 106 */           loadedCount--;
/*     */         } 
/*     */       } 
/* 109 */       dimCount++;
/*     */     } 
/* 111 */     if (WorldMap.settings.debug && count > 0)
/* 112 */       WorldMap.LOGGER.info("Unloaded " + count + " world map regions!"); 
/* 113 */     mapProcessor.popRenderPause(false, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onSessionFinalized() {
/* 118 */     this.workingDimList.clear();
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\MapLimiter.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */