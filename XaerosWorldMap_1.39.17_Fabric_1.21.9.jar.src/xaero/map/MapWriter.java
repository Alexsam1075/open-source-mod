/*      */ package xaero.map;
/*      */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*      */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.awt.image.Raster;
/*      */ import java.io.InputStream;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import javax.imageio.ImageIO;
/*      */ import net.minecraft.class_1047;
/*      */ import net.minecraft.class_1058;
/*      */ import net.minecraft.class_10725;
/*      */ import net.minecraft.class_1087;
/*      */ import net.minecraft.class_10889;
/*      */ import net.minecraft.class_11515;
/*      */ import net.minecraft.class_1657;
/*      */ import net.minecraft.class_1922;
/*      */ import net.minecraft.class_1937;
/*      */ import net.minecraft.class_1944;
/*      */ import net.minecraft.class_1959;
/*      */ import net.minecraft.class_2246;
/*      */ import net.minecraft.class_2248;
/*      */ import net.minecraft.class_2338;
/*      */ import net.minecraft.class_2350;
/*      */ import net.minecraft.class_2378;
/*      */ import net.minecraft.class_2586;
/*      */ import net.minecraft.class_2680;
/*      */ import net.minecraft.class_2688;
/*      */ import net.minecraft.class_2806;
/*      */ import net.minecraft.class_2818;
/*      */ import net.minecraft.class_2826;
/*      */ import net.minecraft.class_2902;
/*      */ import net.minecraft.class_2960;
/*      */ import net.minecraft.class_310;
/*      */ import net.minecraft.class_3298;
/*      */ import net.minecraft.class_3481;
/*      */ import net.minecraft.class_3610;
/*      */ import net.minecraft.class_3619;
/*      */ import net.minecraft.class_3620;
/*      */ import net.minecraft.class_4696;
/*      */ import net.minecraft.class_5321;
/*      */ import net.minecraft.class_5819;
/*      */ import net.minecraft.class_638;
/*      */ import net.minecraft.class_773;
/*      */ import net.minecraft.class_777;
/*      */ import xaero.map.biome.BiomeColorCalculator;
/*      */ import xaero.map.biome.BiomeGetter;
/*      */ import xaero.map.biome.BlockTintProvider;
/*      */ import xaero.map.cache.BlockStateShortShapeCache;
/*      */ import xaero.map.core.XaeroWorldMapCore;
/*      */ import xaero.map.exception.SilentException;
/*      */ import xaero.map.misc.CachedFunction;
/*      */ import xaero.map.misc.Misc;
/*      */ import xaero.map.mods.SupportMods;
/*      */ import xaero.map.region.LeveledRegion;
/*      */ import xaero.map.region.MapBlock;
/*      */ import xaero.map.region.MapLayer;
/*      */ import xaero.map.region.MapRegion;
/*      */ import xaero.map.region.MapTile;
/*      */ import xaero.map.region.MapTileChunk;
/*      */ import xaero.map.region.OverlayBuilder;
/*      */ import xaero.map.region.OverlayManager;
/*      */ 
/*      */ public abstract class MapWriter {
/*      */   public static final int NO_Y_VALUE = 32767;
/*      */   public static final int MAX_TRANSPARENCY_BLEND_DEPTH = 5;
/*   68 */   public static final String[] DEFAULT_RESOURCE = new String[] { "minecraft", "" };
/*      */   private int X;
/*      */   private int Z;
/*      */   private int playerChunkX;
/*      */   private int playerChunkZ;
/*      */   private int loadDistance;
/*      */   private int startTileChunkX;
/*      */   private int startTileChunkZ;
/*      */   private int endTileChunkX;
/*      */   private int endTileChunkZ;
/*      */   private int insideX;
/*      */   private int insideZ;
/*      */   private long updateCounter;
/*      */   private int caveStart;
/*   82 */   private int writingLayer = Integer.MAX_VALUE;
/*   83 */   private int writtenCaveStart = Integer.MAX_VALUE;
/*      */   private boolean clearCachedColours;
/*      */   private MapBlock loadingObject;
/*      */   private OverlayBuilder overlayBuilder;
/*      */   private final class_2338.class_2339 mutableLocalPos;
/*      */   private final class_2338.class_2339 mutableGlobalPos;
/*   89 */   protected final class_5819 usedRandom = class_5819.method_43049(0L);
/*   90 */   private long lastWrite = -1L;
/*   91 */   private long lastWriteTry = -1L;
/*      */   private int workingFrameCount;
/*   93 */   private long framesFreedTime = -1L;
/*   94 */   public long writeFreeSinceLastWrite = -1L;
/*      */   private int writeFreeSizeTiles;
/*      */   private int writeFreeFullUpdateTargetTime;
/*      */   private MapProcessor mapProcessor;
/*      */   private ArrayList<class_2680> buggedStates;
/*      */   private BlockStateShortShapeCache blockStateShortShapeCache;
/*      */   private int topH;
/*      */   private final CachedFunction<class_2688<?, ?>, Boolean> transparentCache;
/*      */   private int firstTransparentStateY;
/*      */   private final class_2338.class_2339 mutableBlockPos3;
/*      */   private CachedFunction<class_3610, class_2680> fluidToBlock;
/*      */   private BiomeGetter biomeGetter;
/*  106 */   private ArrayList<MapRegion> regionBuffer = new ArrayList<>();
/*  107 */   private MapTileChunk rightChunk = null;
/*  108 */   private MapTileChunk bottomRightChunk = null;
/*      */ 
/*      */ 
/*      */   
/*      */   private HashMap<String, Integer> textureColours;
/*      */ 
/*      */ 
/*      */   
/*      */   private HashMap<class_2680, Integer> blockColours;
/*      */ 
/*      */   
/*      */   private final Object2IntMap<class_2680> blockTintIndices;
/*      */ 
/*      */   
/*      */   private long lastLayerSwitch;
/*      */ 
/*      */   
/*      */   protected List<class_10889> reusableBlockModelPartList;
/*      */ 
/*      */   
/*      */   private class_2680 lastBlockStateForTextureColor;
/*      */ 
/*      */   
/*      */   private int lastBlockStateForTextureColorResult;
/*      */ 
/*      */ 
/*      */   
/*      */   public void onRender(BiomeColorCalculator biomeColorCalculator, OverlayManager overlayManager) {
/*  136 */     long before = System.nanoTime();
/*      */     
/*      */     try {
/*  139 */       if (WorldMap.crashHandler.getCrashedBy() == null)
/*  140 */         synchronized (this.mapProcessor.renderThreadPauseSync) {
/*  141 */           if (!this.mapProcessor.isWritingPaused() && !this.mapProcessor.isWaitingForWorldUpdate() && this.mapProcessor.getMapSaveLoad().isRegionDetectionComplete() && this.mapProcessor.isCurrentMultiworldWritable()) {
/*  142 */             if (this.mapProcessor.getWorld() == null || this.mapProcessor.isCurrentMapLocked() || this.mapProcessor.getMapWorld().isCacheOnlyMode())
/*      */               return; 
/*  144 */             if (this.mapProcessor.getCurrentWorldId() != null && !this.mapProcessor.ignoreWorld((class_1937)this.mapProcessor.getWorld()) && (WorldMap.settings.updateChunks || WorldMap.settings.loadChunks || this.mapProcessor.getMapWorld().getCurrentDimension().isUsingWorldSave())) {
/*      */               double playerX, playerY, playerZ;
/*      */ 
/*      */               
/*  148 */               synchronized (this.mapProcessor.mainStuffSync) {
/*  149 */                 if (this.mapProcessor.mainWorld != this.mapProcessor.getWorld())
/*      */                   return; 
/*  151 */                 if (this.mapProcessor.getWorld().method_27983() != this.mapProcessor.getMapWorld().getCurrentDimensionId())
/*      */                   return; 
/*  153 */                 playerX = this.mapProcessor.mainPlayerX;
/*  154 */                 playerY = this.mapProcessor.mainPlayerY;
/*  155 */                 playerZ = this.mapProcessor.mainPlayerZ;
/*      */               } 
/*  157 */               XaeroWorldMapCore.ensureField();
/*      */               
/*  159 */               int lengthX = this.endTileChunkX - this.startTileChunkX + 1;
/*  160 */               int lengthZ = this.endTileChunkZ - this.startTileChunkZ + 1;
/*  161 */               if (this.lastWriteTry == -1L) {
/*  162 */                 lengthX = 3;
/*  163 */                 lengthZ = 3;
/*      */               } 
/*  165 */               int sizeTileChunks = lengthX * lengthZ;
/*  166 */               int sizeTiles = sizeTileChunks * 4 * 4;
/*  167 */               int sizeBasedTargetTime = sizeTiles * 1000 / 1500;
/*  168 */               int fullUpdateTargetTime = Math.max(100, sizeBasedTargetTime);
/*      */ 
/*      */               
/*  171 */               long time = System.currentTimeMillis();
/*  172 */               long passed = (this.lastWrite == -1L) ? 0L : (time - this.lastWrite);
/*  173 */               if (this.lastWriteTry == -1L || this.writeFreeSizeTiles != sizeTiles || this.writeFreeFullUpdateTargetTime != fullUpdateTargetTime || this.workingFrameCount > 30) {
/*  174 */                 this.framesFreedTime = time;
/*  175 */                 this.writeFreeSizeTiles = sizeTiles;
/*  176 */                 this.writeFreeFullUpdateTargetTime = fullUpdateTargetTime;
/*  177 */                 this.workingFrameCount = 0;
/*      */               } 
/*      */               
/*  180 */               long sinceLastWrite = Math.min(passed, this.writeFreeSinceLastWrite);
/*  181 */               if (this.framesFreedTime != -1L)
/*  182 */                 sinceLastWrite = time - this.framesFreedTime; 
/*  183 */               long tilesToUpdate = Math.min(sinceLastWrite * sizeTiles / fullUpdateTargetTime, 100L);
/*  184 */               if (this.lastWrite == -1L || tilesToUpdate != 0L)
/*  185 */                 this.lastWrite = time; 
/*  186 */               if (tilesToUpdate != 0L) {
/*  187 */                 if (this.framesFreedTime == -1L) {
/*      */                   
/*  189 */                   int timeLimit = (int)(Math.min(sinceLastWrite, 50L) * 86960L);
/*      */                   
/*  191 */                   long writeStartNano = System.nanoTime();
/*  192 */                   class_2378<class_1959> biomeRegistry = this.mapProcessor.worldBiomeRegistry;
/*  193 */                   boolean loadChunks = (WorldMap.settings.loadChunks || this.mapProcessor.getMapWorld().getCurrentDimension().isUsingWorldSave());
/*  194 */                   boolean updateChunks = (WorldMap.settings.updateChunks || this.mapProcessor.getMapWorld().getCurrentDimension().isUsingWorldSave());
/*  195 */                   boolean ignoreHeightmaps = this.mapProcessor.getMapWorld().isIgnoreHeightmaps();
/*  196 */                   boolean flowers = WorldMap.settings.flowers;
/*  197 */                   boolean detailedDebug = WorldMap.settings.detailed_debug;
/*  198 */                   int caveDepth = WorldMap.settings.caveModeDepth;
/*  199 */                   class_2338.class_2339 mutableBlockPos3 = this.mutableBlockPos3;
/*  200 */                   BlockTintProvider blockTintProvider = this.mapProcessor.getWorldBlockTintProvider();
/*  201 */                   class_638 world = this.mapProcessor.getWorld();
/*  202 */                   class_2378<class_2248> blockRegistry = this.mapProcessor.getWorldBlockRegistry();
/*  203 */                   for (int j = 0; j < tilesToUpdate; j++) {
/*  204 */                     if (writeMap((class_1937)world, blockRegistry, playerX, playerY, playerZ, biomeRegistry, biomeColorCalculator, overlayManager, loadChunks, updateChunks, ignoreHeightmaps, flowers, detailedDebug, mutableBlockPos3, blockTintProvider, caveDepth))
/*      */                     {
/*      */                       
/*  207 */                       j--; } 
/*  208 */                     if (System.nanoTime() - writeStartNano >= timeLimit)
/*      */                       break; 
/*      */                   } 
/*  211 */                   this.workingFrameCount++;
/*      */                 } else {
/*  213 */                   this.writeFreeSinceLastWrite = sinceLastWrite;
/*  214 */                   this.framesFreedTime = -1L;
/*      */                 } 
/*      */               }
/*  217 */               this.lastWriteTry = time;
/*      */ 
/*      */               
/*  220 */               int startRegionX = this.startTileChunkX >> 3;
/*  221 */               int startRegionZ = this.startTileChunkZ >> 3;
/*  222 */               int endRegionX = this.endTileChunkX >> 3;
/*  223 */               int endRegionZ = this.endTileChunkZ >> 3;
/*  224 */               boolean shouldRequestLoading = false;
/*  225 */               LeveledRegion<?> nextToLoad = this.mapProcessor.getMapSaveLoad().getNextToLoadByViewing();
/*  226 */               if (nextToLoad != null) {
/*  227 */                 shouldRequestLoading = nextToLoad.shouldAllowAnotherRegionToLoad();
/*      */               } else {
/*  229 */                 shouldRequestLoading = true;
/*      */               } 
/*  231 */               this.regionBuffer.clear();
/*  232 */               int comparisonChunkX = this.playerChunkX - 16;
/*  233 */               int comparisonChunkZ = this.playerChunkZ - 16;
/*  234 */               LeveledRegion.setComparison(comparisonChunkX, comparisonChunkZ, 0, comparisonChunkX, comparisonChunkZ);
/*  235 */               for (int visitRegionX = startRegionX; visitRegionX <= endRegionX; visitRegionX++) {
/*  236 */                 for (int visitRegionZ = startRegionZ; visitRegionZ <= endRegionZ; visitRegionZ++) {
/*  237 */                   MapRegion visitRegion = this.mapProcessor.getLeafMapRegion(this.writingLayer, visitRegionX, visitRegionZ, true);
/*  238 */                   if (visitRegion != null && visitRegion.getLoadState() == 2)
/*  239 */                     visitRegion.registerVisit(); 
/*  240 */                   synchronized (visitRegion) {
/*  241 */                     if (visitRegion.isResting() && 
/*  242 */                       shouldRequestLoading && visitRegion.canRequestReload_unsynced() && visitRegion.getLoadState() != 2) {
/*  243 */                       visitRegion.calculateSortingChunkDistance();
/*  244 */                       Misc.addToListOfSmallest(10, this.regionBuffer, (Comparable)visitRegion);
/*      */                     } 
/*      */                   } 
/*      */                 } 
/*      */               } 
/*      */               
/*  250 */               int toRequest = 1;
/*  251 */               int counter = 0;
/*  252 */               for (int i = 0; i < this.regionBuffer.size() && counter < toRequest; i++) {
/*  253 */                 MapRegion region = this.regionBuffer.get(i);
/*  254 */                 if (region != nextToLoad || this.regionBuffer.size() <= 1)
/*      */                 {
/*  256 */                   synchronized (region) {
/*  257 */                     if (!region.canRequestReload_unsynced() || region.getLoadState() == 2) {  }
/*      */                     else
/*  259 */                     { region.setBeingWritten(true);
/*  260 */                       this.mapProcessor.getMapSaveLoad().requestLoad(region, "writing");
/*  261 */                       if (counter == 0)
/*  262 */                         this.mapProcessor.getMapSaveLoad().setNextToLoadByViewing((LeveledRegion)region); 
/*  263 */                       counter++;
/*  264 */                       if (region.getLoadState() == 4)
/*      */                         break;  }
/*      */                   
/*      */                   }  } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         }  
/*  272 */     } catch (Throwable e) {
/*  273 */       WorldMap.crashHandler.setCrashedBy(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   private int getWriteDistance() {
/*  278 */     int limit = this.mapProcessor.getMapWorld().getCurrentDimension().isUsingWorldSave() ? Integer.MAX_VALUE : WorldMap.settings.mapWritingDistance;
/*  279 */     if (limit < 0)
/*  280 */       limit = Integer.MAX_VALUE; 
/*  281 */     return Math.min(limit, Math.min(32, ((Integer)(class_310.method_1551()).field_1690.method_42503().method_41753()).intValue()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean writeMap(class_1937 world, class_2378<class_2248> blockRegistry, double playerX, double playerY, double playerZ, class_2378<class_1959> biomeRegistry, BiomeColorCalculator biomeColorCalculator, OverlayManager overlayManager, boolean loadChunks, boolean updateChunks, boolean ignoreHeightmaps, boolean flowers, boolean detailedDebug, class_2338.class_2339 mutableBlockPos3, BlockTintProvider blockTintProvider, int caveDepth) {
/*  288 */     boolean onlyLoad = (loadChunks && (!updateChunks || this.updateCounter % 5L != 0L));
/*  289 */     synchronized (world) {
/*  290 */       if (this.insideX == 0 && this.insideZ == 0) {
/*  291 */         if (this.X == 0 && this.Z == 0)
/*  292 */           this.writtenCaveStart = this.caveStart; 
/*  293 */         this.mapProcessor.updateCaveStart();
/*  294 */         int newWritingLayer = this.mapProcessor.getCurrentCaveLayer();
/*  295 */         if (this.writingLayer != newWritingLayer && System.currentTimeMillis() - this.lastLayerSwitch > 300L) {
/*  296 */           this.writingLayer = newWritingLayer;
/*  297 */           this.lastLayerSwitch = System.currentTimeMillis();
/*      */         } 
/*  299 */         this.loadDistance = getWriteDistance();
/*  300 */         if (this.writingLayer != Integer.MAX_VALUE && 
/*  301 */           !((class_310.method_1551()).field_1755 instanceof xaero.map.gui.GuiMap)) {
/*  302 */           this.loadDistance = Math.min(16, this.loadDistance);
/*      */         }
/*  304 */         this.caveStart = this.mapProcessor.getMapWorld().getCurrentDimension().getLayeredMapRegions().getLayer(this.writingLayer).getCaveStart();
/*  305 */         if (this.caveStart != this.writtenCaveStart)
/*  306 */           this.loadDistance = Math.min(4, this.loadDistance); 
/*  307 */         this.playerChunkX = (int)Math.floor(playerX) >> 4;
/*  308 */         this.playerChunkZ = (int)Math.floor(playerZ) >> 4;
/*  309 */         this.startTileChunkX = this.playerChunkX - this.loadDistance >> 2;
/*  310 */         this.startTileChunkZ = this.playerChunkZ - this.loadDistance >> 2;
/*  311 */         this.endTileChunkX = this.playerChunkX + this.loadDistance >> 2;
/*  312 */         this.endTileChunkZ = this.playerChunkZ + this.loadDistance >> 2;
/*      */       } 
/*  314 */       int tileChunkX = this.startTileChunkX + this.X;
/*  315 */       int tileChunkZ = this.startTileChunkZ + this.Z;
/*  316 */       int tileChunkLocalX = tileChunkX & 0x7;
/*  317 */       int tileChunkLocalZ = tileChunkZ & 0x7;
/*  318 */       int chunkX = tileChunkX * 4 + this.insideX;
/*  319 */       int chunkZ = tileChunkZ * 4 + this.insideZ;
/*  320 */       boolean wasSkipped = writeChunk(world, blockRegistry, this.loadDistance, onlyLoad, biomeRegistry, overlayManager, loadChunks, updateChunks, ignoreHeightmaps, flowers, detailedDebug, mutableBlockPos3, blockTintProvider, caveDepth, this.caveStart, this.writingLayer, tileChunkX, tileChunkZ, tileChunkLocalX, tileChunkLocalZ, chunkX, chunkZ);
/*      */ 
/*      */ 
/*      */       
/*  324 */       return (wasSkipped && (Math.abs(chunkX - this.playerChunkX) > 8 || Math.abs(chunkZ - this.playerChunkZ) > 8));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean writeChunk(class_1937 world, class_2378<class_2248> blockRegistry, int distance, boolean onlyLoad, class_2378<class_1959> biomeRegistry, OverlayManager overlayManager, boolean loadChunks, boolean updateChunks, boolean ignoreHeightmaps, boolean flowers, boolean detailedDebug, class_2338.class_2339 mutableBlockPos3, BlockTintProvider blockTintProvider, int caveDepth, int caveStart, int layerToWrite, int tileChunkX, int tileChunkZ, int tileChunkLocalX, int tileChunkLocalZ, int chunkX, int chunkZ) {
/*  333 */     int regionX = tileChunkX >> 3;
/*  334 */     int regionZ = tileChunkZ >> 3;
/*  335 */     MapTileChunk tileChunk = null;
/*  336 */     this.rightChunk = null;
/*  337 */     MapTileChunk bottomChunk = null;
/*  338 */     this.bottomRightChunk = null;
/*  339 */     int worldBottomY = world.method_31607();
/*  340 */     int worldTopY = world.method_31600() + 1;
/*  341 */     MapRegion region = this.mapProcessor.getLeafMapRegion(layerToWrite, regionX, regionZ, true);
/*  342 */     boolean wasSkipped = true;
/*  343 */     synchronized (region.writerThreadPauseSync) {
/*  344 */       if (!region.isWritingPaused()) {
/*      */ 
/*      */         
/*  347 */         boolean regionIsResting, isProperLoadState, createdTileChunk = false;
/*  348 */         synchronized (region) {
/*  349 */           isProperLoadState = (region.getLoadState() == 2);
/*  350 */           if (isProperLoadState)
/*  351 */             region.registerVisit(); 
/*  352 */           regionIsResting = region.isResting();
/*  353 */           if (regionIsResting) {
/*  354 */             region.setBeingWritten(true);
/*      */             
/*  356 */             tileChunk = region.getChunk(tileChunkLocalX, tileChunkLocalZ);
/*  357 */             if (isProperLoadState && tileChunk == null) {
/*  358 */               region.setChunk(tileChunkLocalX, tileChunkLocalZ, tileChunk = new MapTileChunk(region, tileChunkX, tileChunkZ));
/*  359 */               tileChunk.setLoadState((byte)2);
/*  360 */               region.setAllCachePrepared(false);
/*  361 */               createdTileChunk = true;
/*      */             } 
/*  363 */             if (!region.isNormalMapData()) {
/*  364 */               region.getDim().getLayeredMapRegions().applyToEachLoadedLayer((i, layer) -> {
/*      */                     if (i.intValue() != region.getCaveLayer()) {
/*      */                       MapRegion sameRegionAnotherLayer = this.mapProcessor.getLeafMapRegion(i.intValue(), regionX, regionZ, true);
/*      */                       sameRegionAnotherLayer.setOutdatedWithOtherLayers(true);
/*      */                       sameRegionAnotherLayer.setHasHadTerrain();
/*      */                     } 
/*      */                   });
/*      */             }
/*      */           } 
/*      */         } 
/*  374 */         if (regionIsResting && isProperLoadState) {
/*      */ 
/*      */           
/*  377 */           if (tileChunk != null && tileChunk.getLoadState() == 2) {
/*  378 */             if (!tileChunk.getLeafTexture().shouldUpload()) {
/*  379 */               boolean cave = (caveStart != Integer.MAX_VALUE);
/*  380 */               boolean fullCave = (caveStart == Integer.MIN_VALUE);
/*  381 */               int lowH = worldBottomY;
/*  382 */               if (cave && !fullCave) {
/*  383 */                 lowH = caveStart + 1 - caveDepth;
/*  384 */                 if (lowH < worldBottomY)
/*  385 */                   lowH = worldBottomY; 
/*      */               } 
/*  387 */               if (chunkX >= this.playerChunkX - distance && chunkX <= this.playerChunkX + distance && chunkZ >= this.playerChunkZ - distance && chunkZ <= this.playerChunkZ + distance) {
/*      */                 
/*  389 */                 class_2818 chunk = (class_2818)world.method_8402(chunkX, chunkZ, class_2806.field_12803, false);
/*  390 */                 MapTile mapTile = tileChunk.getTile(this.insideX, this.insideZ);
/*  391 */                 boolean chunkUpdated = false;
/*      */                 try {
/*  393 */                   chunkUpdated = (chunk != null && (mapTile == null || mapTile.getWrittenCaveStart() != caveStart || mapTile.getWrittenCaveDepth() != caveDepth || !((Boolean)XaeroWorldMapCore.chunkCleanField.get(chunk)).booleanValue()));
/*  394 */                 } catch (IllegalArgumentException|IllegalAccessException e) {
/*  395 */                   throw new RuntimeException(e);
/*      */                 } 
/*  397 */                 if (chunkUpdated && !(chunk instanceof net.minecraft.class_2812)) {
/*      */ 
/*      */ 
/*      */                   
/*  401 */                   boolean edgeChunk = false;
/*      */                   int i;
/*  403 */                   label226: for (i = -1; i < 2; i++) {
/*  404 */                     for (int j = -1; j < 2; j++) {
/*  405 */                       if (i != 0 || j != 0) {
/*      */                         
/*  407 */                         class_2818 neighbor = world.method_8497(chunkX + i, chunkZ + j);
/*  408 */                         if (neighbor == null || neighbor instanceof net.minecraft.class_2812) {
/*  409 */                           edgeChunk = true; break label226;
/*      */                         } 
/*      */                       } 
/*      */                     } 
/*  413 */                   }  if (!edgeChunk && ((
/*  414 */                     mapTile == null && loadChunks) || (mapTile != null && updateChunks && (!onlyLoad || mapTile.getWrittenCaveStart() != caveStart || mapTile.getWrittenCaveDepth() != caveDepth)))) {
/*  415 */                     wasSkipped = false;
/*  416 */                     if (mapTile == null) {
/*  417 */                       mapTile = this.mapProcessor.getTilePool().get(this.mapProcessor.getCurrentDimension(), chunkX, chunkZ);
/*  418 */                       tileChunk.setChanged(true);
/*      */                     } 
/*  420 */                     MapTileChunk prevTileChunk = tileChunk.getNeighbourTileChunk(0, -1, this.mapProcessor, false);
/*  421 */                     MapTileChunk prevTileChunkDiagonal = tileChunk.getNeighbourTileChunk(-1, -1, this.mapProcessor, false);
/*  422 */                     MapTileChunk prevTileChunkHorisontal = tileChunk.getNeighbourTileChunk(-1, 0, this.mapProcessor, false);
/*  423 */                     int sectionBasedHeight = getSectionBasedHeight(chunk, 64);
/*  424 */                     class_2902.class_2903 typeWorldSurface = class_2902.class_2903.field_13202;
/*  425 */                     MapTile bottomTile = (this.insideZ < 3) ? tileChunk.getTile(this.insideX, this.insideZ + 1) : null;
/*  426 */                     MapTile rightTile = (this.insideX < 3) ? tileChunk.getTile(this.insideX + 1, this.insideZ) : null;
/*  427 */                     boolean triedFetchingBottomChunk = false;
/*  428 */                     boolean triedFetchingRightChunk = false;
/*  429 */                     for (int x = 0; x < 16; x++) {
/*  430 */                       for (int z = 0; z < 16; z++) {
/*  431 */                         int startHeight, mappedHeight = chunk.method_12005(typeWorldSurface, x, z);
/*      */                         
/*  433 */                         if (cave && !fullCave) {
/*  434 */                           startHeight = caveStart;
/*  435 */                         } else if (ignoreHeightmaps || mappedHeight < worldBottomY) {
/*  436 */                           startHeight = sectionBasedHeight;
/*      */                         } else {
/*  438 */                           startHeight = mappedHeight;
/*  439 */                         }  if (startHeight >= worldTopY)
/*  440 */                           startHeight = worldTopY - 1; 
/*  441 */                         MapBlock currentPixel = mapTile.isLoaded() ? mapTile.getBlock(x, z) : null;
/*  442 */                         loadPixel(world, blockRegistry, this.loadingObject, currentPixel, chunk, x, z, startHeight, lowH, cave, fullCave, mappedHeight, mapTile.wasWrittenOnce(), ignoreHeightmaps, biomeRegistry, flowers, worldBottomY, mutableBlockPos3);
/*  443 */                         this.loadingObject.fixHeightType(x, z, mapTile, tileChunk, prevTileChunk, prevTileChunkDiagonal, prevTileChunkHorisontal, this.loadingObject.getEffectiveHeight(this.blockStateShortShapeCache), true, this.blockStateShortShapeCache);
/*  444 */                         boolean equalsSlopesExcluded = this.loadingObject.equalsSlopesExcluded(currentPixel);
/*  445 */                         boolean fullyEqual = this.loadingObject.equals(currentPixel, equalsSlopesExcluded);
/*  446 */                         if (!fullyEqual) {
/*  447 */                           MapBlock loadedBlock = this.loadingObject;
/*  448 */                           mapTile.setBlock(x, z, loadedBlock);
/*  449 */                           if (currentPixel != null) {
/*  450 */                             this.loadingObject = currentPixel;
/*      */                           } else {
/*  452 */                             this.loadingObject = new MapBlock();
/*  453 */                           }  if (!equalsSlopesExcluded) {
/*  454 */                             tileChunk.setChanged(true);
/*  455 */                             boolean zEdge = (z == 15);
/*  456 */                             boolean xEdge = (x == 15);
/*  457 */                             if ((zEdge || xEdge) && (currentPixel == null || currentPixel.getEffectiveHeight(this.blockStateShortShapeCache) != loadedBlock.getEffectiveHeight(this.blockStateShortShapeCache)))
/*  458 */                               if (zEdge) {
/*  459 */                                 if (!triedFetchingBottomChunk && bottomTile == null && this.insideZ == 3 && tileChunkLocalZ < 7) {
/*  460 */                                   bottomChunk = region.getChunk(tileChunkLocalX, tileChunkLocalZ + 1);
/*  461 */                                   triedFetchingBottomChunk = true;
/*  462 */                                   bottomTile = (bottomChunk != null) ? bottomChunk.getTile(this.insideX, 0) : null;
/*  463 */                                   if (bottomTile != null)
/*  464 */                                     bottomChunk.setChanged(true); 
/*      */                                 } 
/*  466 */                                 if (bottomTile != null && bottomTile.isLoaded()) {
/*  467 */                                   bottomTile.getBlock(x, 0).setSlopeUnknown(true);
/*  468 */                                   if (!xEdge)
/*  469 */                                     bottomTile.getBlock(x + 1, 0).setSlopeUnknown(true); 
/*      */                                 } 
/*  471 */                                 if (xEdge)
/*  472 */                                   updateBottomRightTile(region, tileChunk, bottomChunk, tileChunkLocalX, tileChunkLocalZ); 
/*  473 */                               } else if (xEdge) {
/*  474 */                                 if (!triedFetchingRightChunk && rightTile == null && this.insideX == 3 && tileChunkLocalX < 7) {
/*  475 */                                   this.rightChunk = region.getChunk(tileChunkLocalX + 1, tileChunkLocalZ);
/*  476 */                                   triedFetchingRightChunk = true;
/*  477 */                                   rightTile = (this.rightChunk != null) ? this.rightChunk.getTile(0, this.insideZ) : null;
/*  478 */                                   if (rightTile != null)
/*  479 */                                     this.rightChunk.setChanged(true); 
/*      */                                 } 
/*  481 */                                 if (rightTile != null && rightTile.isLoaded())
/*  482 */                                   rightTile.getBlock(0, z + 1).setSlopeUnknown(true); 
/*      */                               }  
/*      */                           } 
/*      */                         } 
/*      */                       } 
/*      */                     } 
/*  488 */                     mapTile.setWorldInterpretationVersion(1);
/*  489 */                     if (mapTile.getWrittenCaveStart() != caveStart)
/*  490 */                       tileChunk.setChanged(true); 
/*  491 */                     mapTile.setWrittenCave(caveStart, caveDepth);
/*  492 */                     tileChunk.setTile(this.insideX, this.insideZ, mapTile, this.blockStateShortShapeCache);
/*  493 */                     mapTile.setWrittenOnce(true);
/*  494 */                     mapTile.setLoaded(true);
/*  495 */                     Misc.setReflectFieldValue(chunk, XaeroWorldMapCore.chunkCleanField, Boolean.valueOf(true));
/*      */                   } 
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */             
/*  501 */             if (createdTileChunk) {
/*  502 */               if (tileChunk.includeInSave())
/*  503 */                 tileChunk.setHasHadTerrain(); 
/*  504 */               this.mapProcessor.getMapRegionHighlightsPreparer().prepare(region, tileChunkLocalX, tileChunkLocalZ, false);
/*  505 */               if (!tileChunk.includeInSave() && !tileChunk.hasHighlightsIfUndiscovered()) {
/*  506 */                 region.setChunk(tileChunkLocalX, tileChunkLocalZ, null);
/*  507 */                 tileChunk = null;
/*      */               } 
/*      */             } 
/*      */           } 
/*  511 */           if (tileChunk != null && this.insideX == 3 && this.insideZ == 3 && tileChunk.wasChanged()) {
/*  512 */             tileChunk.updateBuffers(this.mapProcessor, blockTintProvider, overlayManager, detailedDebug, this.blockStateShortShapeCache);
/*  513 */             if (bottomChunk == null && tileChunkLocalZ < 7)
/*  514 */               bottomChunk = region.getChunk(tileChunkLocalX, tileChunkLocalZ + 1); 
/*  515 */             if (this.rightChunk == null && tileChunkLocalX < 7)
/*  516 */               this.rightChunk = region.getChunk(tileChunkLocalX + 1, tileChunkLocalZ); 
/*  517 */             if (this.bottomRightChunk == null && tileChunkLocalX < 7 && tileChunkLocalZ < 7)
/*  518 */               this.bottomRightChunk = region.getChunk(tileChunkLocalX + 1, tileChunkLocalZ + 1); 
/*  519 */             if (bottomChunk != null && bottomChunk.wasChanged()) {
/*  520 */               bottomChunk.updateBuffers(this.mapProcessor, blockTintProvider, overlayManager, detailedDebug, this.blockStateShortShapeCache);
/*  521 */               bottomChunk.setChanged(false);
/*      */             } 
/*  523 */             if (this.rightChunk != null && this.rightChunk.wasChanged()) {
/*  524 */               this.rightChunk.setToUpdateBuffers(true);
/*  525 */               this.rightChunk.setChanged(false);
/*      */             } 
/*  527 */             if (this.bottomRightChunk != null && this.bottomRightChunk.wasChanged()) {
/*  528 */               this.bottomRightChunk.setToUpdateBuffers(true);
/*  529 */               this.bottomRightChunk.setChanged(false);
/*      */             } 
/*      */             
/*  532 */             tileChunk.setChanged(false);
/*      */           } 
/*      */         } 
/*      */       } else {
/*  536 */         this.insideX = 3;
/*  537 */         this.insideZ = 3;
/*      */       } 
/*      */     } 
/*      */     
/*  541 */     this.insideZ++;
/*  542 */     if (this.insideZ > 3) {
/*  543 */       this.insideZ = 0;
/*  544 */       this.insideX++;
/*  545 */       if (this.insideX > 3) {
/*  546 */         this.insideX = 0;
/*  547 */         this.Z++;
/*  548 */         if (this.Z > this.endTileChunkZ - this.startTileChunkZ) {
/*  549 */           this.Z = 0;
/*  550 */           this.X++;
/*  551 */           if (this.X > this.endTileChunkX - this.startTileChunkX) {
/*  552 */             this.X = 0;
/*  553 */             this.updateCounter++;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*  558 */     return wasSkipped;
/*      */   }
/*      */   
/*      */   public void updateBottomRightTile(MapRegion region, MapTileChunk tileChunk, MapTileChunk bottomChunk, int tileChunkLocalX, int tileChunkLocalZ) {
/*  562 */     MapTile bottomRightTile = (this.insideX < 3 && this.insideZ < 3) ? tileChunk.getTile(this.insideX + 1, this.insideZ + 1) : null;
/*  563 */     if (bottomRightTile == null)
/*  564 */       if (this.insideX == 3 && tileChunkLocalX < 7) {
/*  565 */         if (this.insideZ == 3) {
/*  566 */           if (tileChunkLocalZ < 7)
/*  567 */             this.bottomRightChunk = region.getChunk(tileChunkLocalX + 1, tileChunkLocalZ + 1); 
/*  568 */           bottomRightTile = (this.bottomRightChunk != null) ? this.bottomRightChunk.getTile(0, 0) : null;
/*  569 */           if (bottomRightTile != null)
/*  570 */             this.bottomRightChunk.setChanged(true); 
/*      */         } else {
/*  572 */           if (this.rightChunk == null)
/*  573 */             this.rightChunk = region.getChunk(tileChunkLocalX + 1, tileChunkLocalZ); 
/*  574 */           bottomRightTile = (this.rightChunk != null) ? this.rightChunk.getTile(0, this.insideZ + 1) : null;
/*  575 */           if (bottomRightTile != null)
/*  576 */             this.rightChunk.setChanged(true); 
/*      */         } 
/*  578 */       } else if (this.insideX != 3 && this.insideZ == 3 && tileChunkLocalZ < 7) {
/*      */         
/*  580 */         bottomRightTile = (bottomChunk != null) ? bottomChunk.getTile(this.insideX + 1, 0) : null;
/*  581 */         if (bottomRightTile != null) {
/*  582 */           bottomChunk.setChanged(true);
/*      */         }
/*      */       }  
/*  585 */     if (bottomRightTile != null && bottomRightTile.isLoaded())
/*  586 */       bottomRightTile.getBlock(0, 0).setSlopeUnknown(true); 
/*      */   }
/*      */   
/*      */   public int getSectionBasedHeight(class_2818 bchunk, int startY) {
/*  590 */     class_2826[] sections = bchunk.method_12006();
/*  591 */     if (sections.length == 0)
/*  592 */       return 0; 
/*  593 */     int chunkBottomY = bchunk.method_31607();
/*  594 */     int playerSection = Math.min(startY - chunkBottomY >> 4, sections.length - 1);
/*  595 */     if (playerSection < 0) {
/*  596 */       playerSection = 0;
/*      */     }
/*  598 */     int result = 0; int i;
/*  599 */     for (i = playerSection; i < sections.length; i++) {
/*  600 */       class_2826 searchedSection = sections[i];
/*  601 */       if (!searchedSection.method_38292())
/*  602 */         result = chunkBottomY + (i << 4) + 15; 
/*      */     } 
/*  604 */     if (playerSection > 0 && result == 0)
/*  605 */       for (i = playerSection - 1; i >= 0; i--) {
/*  606 */         class_2826 searchedSection = sections[i];
/*  607 */         if (!searchedSection.method_38292()) {
/*  608 */           result = chunkBottomY + (i << 4) + 15;
/*      */           break;
/*      */         } 
/*      */       }  
/*  612 */     return result;
/*      */   }
/*      */   
/*      */   public boolean isGlowing(class_2680 state) {
/*  616 */     return (state.method_26213() >= 0.5D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean shouldOverlayCached(class_2688<?, ?> state) {
/*  623 */     return ((Boolean)this.transparentCache.apply(state)).booleanValue();
/*      */   }
/*      */   
/*      */   public boolean shouldOverlay(class_2688<?, ?> state) {
/*  627 */     if (state instanceof class_2680) {
/*  628 */       class_2680 blockState = (class_2680)state;
/*  629 */       if (blockState.method_26204() instanceof net.minecraft.class_2189 || blockState.method_26204() instanceof net.minecraft.class_8923)
/*  630 */         return true; 
/*  631 */       return blockStateHasTranslucentRenderType(blockState);
/*      */     } 
/*  633 */     class_3610 fluidState = (class_3610)state;
/*  634 */     return (class_4696.method_23680(fluidState) == class_11515.field_60926);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isInvisible(class_2680 state, class_2248 b, boolean flowers) {
/*  639 */     if (!(b instanceof net.minecraft.class_2404) && state.method_26217() == class_2464.field_11455)
/*  640 */       return true; 
/*  641 */     if (b == class_2246.field_10336)
/*  642 */       return true; 
/*  643 */     if (b == class_2246.field_10479)
/*  644 */       return true; 
/*  645 */     if (b == class_2246.field_10033 || b == class_2246.field_10285)
/*  646 */       return true; 
/*  647 */     boolean isFlower = (b instanceof net.minecraft.class_2521 || b instanceof net.minecraft.class_2356 || (b instanceof net.minecraft.class_10735 && state.method_26164(class_3481.field_20339)));
/*  648 */     if (b instanceof net.minecraft.class_2320 && !isFlower)
/*  649 */       return true; 
/*  650 */     if (isFlower && !flowers)
/*  651 */       return true; 
/*  652 */     synchronized (this.buggedStates) {
/*  653 */       if (this.buggedStates.contains(state))
/*  654 */         return true; 
/*      */     } 
/*  656 */     return false;
/*      */   }
/*      */   
/*      */   public boolean hasVanillaColor(class_2680 state, class_1937 world, class_2378<class_2248> blockRegistry, class_2338 pos) {
/*  660 */     class_3620 materialColor = null;
/*      */     try {
/*  662 */       materialColor = state.method_26205((class_1922)world, pos);
/*  663 */     } catch (Throwable t) {
/*  664 */       synchronized (this.buggedStates) {
/*  665 */         this.buggedStates.add(state);
/*      */       } 
/*  667 */       WorldMap.LOGGER.info("Broken vanilla map color definition found: " + String.valueOf(blockRegistry.method_10221(state.method_26204())));
/*      */     } 
/*  669 */     return (materialColor != null && materialColor.field_16011 != 0);
/*      */   }
/*      */   
/*      */   private class_2680 unpackFramedBlocks(class_2680 original, class_1937 world, class_2338 globalPos) {
/*  673 */     if (original.method_26204() instanceof net.minecraft.class_2189)
/*  674 */       return original; 
/*  675 */     class_2680 result = original;
/*  676 */     if (SupportMods.framedBlocks() && SupportMods.supportFramedBlocks.isFrameBlock(world, null, original)) {
/*  677 */       class_2586 tileEntity = world.method_8321(globalPos);
/*  678 */       if (tileEntity != null) {
/*  679 */         result = SupportMods.supportFramedBlocks.unpackFramedBlock(world, null, original, tileEntity);
/*  680 */         if (result == null || result.method_26204() instanceof net.minecraft.class_2189)
/*  681 */           result = original; 
/*      */       } 
/*      */     } 
/*  684 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void loadPixel(class_1937 world, class_2378<class_2248> blockRegistry, MapBlock pixel, MapBlock currentPixel, class_2818 bchunk, int insideX, int insideZ, int highY, int lowY, boolean cave, boolean fullCave, int mappedHeight, boolean canReuseBiomeColours, boolean ignoreHeightmaps, class_2378<class_1959> biomeRegistry, boolean flowers, int worldBottomY, class_2338.class_2339 mutableBlockPos3) {
/*  690 */     pixel.prepareForWriting(worldBottomY);
/*  691 */     this.overlayBuilder.startBuilding();
/*  692 */     boolean underair = (!cave || fullCave);
/*  693 */     boolean shouldEnterGround = fullCave;
/*  694 */     class_2680 opaqueState = null;
/*  695 */     byte workingLight = -1;
/*  696 */     boolean worldHasSkyLight = world.method_8597().comp_642();
/*  697 */     byte workingSkyLight = worldHasSkyLight ? 15 : 0;
/*  698 */     this.topH = lowY;
/*  699 */     this.mutableGlobalPos.method_10103(((bchunk.method_12004()).field_9181 << 4) + insideX, lowY - 1, ((bchunk.method_12004()).field_9180 << 4) + insideZ);
/*  700 */     boolean shouldExtendTillTheBottom = false;
/*  701 */     int transparentSkipY = 0;
/*  702 */     int h = highY;
/*  703 */     for (; h >= lowY; 
/*  704 */       h = shouldExtendTillTheBottom ? transparentSkipY : (h - 1)) {
/*      */       
/*  706 */       this.mutableLocalPos.method_10103(insideX, h, insideZ);
/*  707 */       class_2680 class_26801 = bchunk.method_8320((class_2338)this.mutableLocalPos);
/*  708 */       if (class_26801 == null)
/*  709 */         class_26801 = class_2246.field_10124.method_9564(); 
/*  710 */       this.mutableGlobalPos.method_33098(h);
/*  711 */       class_26801 = unpackFramedBlocks(class_26801, world, (class_2338)this.mutableGlobalPos);
/*  712 */       class_3610 fluidFluidState = class_26801.method_26227();
/*  713 */       shouldExtendTillTheBottom = (!shouldExtendTillTheBottom && !this.overlayBuilder.isEmpty() && this.firstTransparentStateY - h >= 5);
/*  714 */       if (shouldExtendTillTheBottom) {
/*      */         
/*  716 */         transparentSkipY = h - 1;
/*  717 */         for (; transparentSkipY >= lowY; transparentSkipY--) {
/*  718 */           class_2680 traceState = bchunk.method_8320((class_2338)mutableBlockPos3.method_10103(insideX, transparentSkipY, insideZ));
/*  719 */           if (traceState == null)
/*  720 */             traceState = class_2246.field_10124.method_9564(); 
/*  721 */           class_3610 traceFluidState = traceState.method_26227();
/*  722 */           if (!traceFluidState.method_15769()) {
/*  723 */             if (!shouldOverlayCached((class_2688<?, ?>)traceFluidState))
/*      */               break; 
/*  725 */             if (!(traceState.method_26204() instanceof net.minecraft.class_2189) && traceState.method_26204() == ((class_2680)this.fluidToBlock.apply(traceFluidState)).method_26204())
/*      */               continue; 
/*      */           } 
/*  728 */           if (!shouldOverlayCached((class_2688<?, ?>)traceState))
/*      */             break;  continue;
/*      */         } 
/*      */       } 
/*  732 */       this.mutableGlobalPos.method_33098(h + 1);
/*  733 */       workingLight = (byte)world.method_8314(class_1944.field_9282, (class_2338)this.mutableGlobalPos);
/*  734 */       if (cave && workingLight < 15 && worldHasSkyLight)
/*  735 */         if (!ignoreHeightmaps && !fullCave && highY >= mappedHeight) {
/*  736 */           workingSkyLight = 15;
/*      */         } else {
/*  738 */           workingSkyLight = (byte)world.method_8314(class_1944.field_9284, (class_2338)this.mutableGlobalPos);
/*      */         }  
/*  740 */       this.mutableGlobalPos.method_33098(h);
/*  741 */       if (!fluidFluidState.method_15769() && (!cave || !shouldEnterGround)) {
/*  742 */         underair = true;
/*  743 */         class_2680 fluidState = (class_2680)this.fluidToBlock.apply(fluidFluidState);
/*  744 */         if (loadPixelHelp(pixel, currentPixel, world, blockRegistry, fluidState, workingLight, workingSkyLight, bchunk, insideX, insideZ, h, canReuseBiomeColours, cave, fluidFluidState, biomeRegistry, transparentSkipY, shouldExtendTillTheBottom, flowers, underair)) {
/*  745 */           opaqueState = class_26801;
/*      */           break;
/*      */         } 
/*      */       } 
/*  749 */       class_2248 b = class_26801.method_26204();
/*  750 */       if (b instanceof net.minecraft.class_2189) {
/*  751 */         underair = true;
/*      */       
/*      */       }
/*  754 */       else if (underair) {
/*      */         
/*  756 */         if (class_26801.method_26204() != ((class_2680)this.fluidToBlock.apply(fluidFluidState)).method_26204())
/*      */         {
/*  758 */           if (cave && shouldEnterGround) {
/*  759 */             if (!class_26801.method_50011() && !class_26801.method_45474() && class_26801.method_26223() != class_3619.field_15971 && !shouldOverlayCached((class_2688<?, ?>)class_26801)) {
/*  760 */               underair = false;
/*  761 */               shouldEnterGround = false;
/*      */             }
/*      */           
/*      */           }
/*  765 */           else if (loadPixelHelp(pixel, currentPixel, world, blockRegistry, class_26801, workingLight, workingSkyLight, bchunk, insideX, insideZ, h, canReuseBiomeColours, cave, null, biomeRegistry, transparentSkipY, shouldExtendTillTheBottom, flowers, underair)) {
/*  766 */             opaqueState = class_26801; break;
/*      */           }  } 
/*      */       } 
/*      */     } 
/*  770 */     if (h < lowY)
/*  771 */       h = lowY; 
/*  772 */     class_5321<class_1959> blockBiome = null;
/*  773 */     class_2680 state = (opaqueState == null) ? class_2246.field_10124.method_9564() : opaqueState;
/*      */     
/*  775 */     this.overlayBuilder.finishBuilding(pixel);
/*  776 */     byte light = 0;
/*  777 */     if (opaqueState != null) {
/*  778 */       light = workingLight;
/*  779 */       if (cave && light < 15 && pixel.getNumberOfOverlays() == 0 && 
/*  780 */         workingSkyLight > light) {
/*  781 */         light = workingSkyLight;
/*      */       }
/*      */     } else {
/*  784 */       h = worldBottomY;
/*  785 */     }  if (!canReuseBiomeColours || currentPixel == null || currentPixel.getState() != state || currentPixel.getTopHeight() != this.topH) {
/*  786 */       this.mutableGlobalPos.method_33098(this.topH);
/*  787 */       blockBiome = this.biomeGetter.getBiome(world, (class_2338)this.mutableGlobalPos, biomeRegistry);
/*  788 */       this.mutableGlobalPos.method_33098(h);
/*      */     } else {
/*  790 */       blockBiome = currentPixel.getBiome();
/*  791 */     }  if (this.overlayBuilder.getOverlayBiome() != null)
/*  792 */       blockBiome = this.overlayBuilder.getOverlayBiome(); 
/*  793 */     boolean glowing = isGlowing(state);
/*  794 */     pixel.write(state, h, this.topH, blockBiome, light, glowing, cave);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean loadPixelHelp(MapBlock pixel, MapBlock currentPixel, class_1937 world, class_2378<class_2248> blockRegistry, class_2680 state, byte light, byte skyLight, class_2818 bchunk, int insideX, int insideZ, int h, boolean canReuseBiomeColours, boolean cave, class_3610 fluidFluidState, class_2378<class_1959> biomeRegistry, int transparentSkipY, boolean shouldExtendTillTheBottom, boolean flowers, boolean underair) {
/*  800 */     class_2248 b = state.method_26204();
/*  801 */     if (isInvisible(state, b, flowers))
/*  802 */       return false; 
/*  803 */     if (shouldOverlayCached((fluidFluidState == null) ? (class_2688<?, ?>)state : (class_2688<?, ?>)fluidFluidState)) {
/*  804 */       if (cave && !underair)
/*  805 */         return false; 
/*  806 */       if (h > this.topH)
/*  807 */         this.topH = h; 
/*  808 */       byte overlayLight = light;
/*  809 */       if (this.overlayBuilder.isEmpty()) {
/*  810 */         this.firstTransparentStateY = h;
/*  811 */         if (cave && skyLight > overlayLight)
/*  812 */           overlayLight = skyLight; 
/*      */       } 
/*  814 */       if (shouldExtendTillTheBottom) {
/*  815 */         this.overlayBuilder.getCurrentOverlay().increaseOpacity(this.overlayBuilder.getCurrentOverlay().getState().method_26193() * (h - transparentSkipY));
/*      */       } else {
/*  817 */         class_5321<class_1959> overlayBiome = this.overlayBuilder.getOverlayBiome();
/*  818 */         if (overlayBiome == null)
/*  819 */           if (canReuseBiomeColours && currentPixel != null && currentPixel.getNumberOfOverlays() > 0 && ((Overlay)currentPixel.getOverlays().get(0)).getState() == state) {
/*  820 */             overlayBiome = currentPixel.getBiome();
/*      */           } else {
/*  822 */             overlayBiome = this.biomeGetter.getBiome(world, (class_2338)this.mutableGlobalPos, biomeRegistry);
/*      */           }  
/*  824 */         this.overlayBuilder.build(state, state.method_26193(), overlayLight, this.mapProcessor, overlayBiome);
/*      */       } 
/*  826 */       return false;
/*      */     } 
/*  828 */     if (!hasVanillaColor(state, world, blockRegistry, (class_2338)this.mutableGlobalPos))
/*  829 */       return false; 
/*  830 */     if (cave && !underair)
/*  831 */       return true; 
/*  832 */     if (h > this.topH)
/*  833 */       this.topH = h; 
/*  834 */     return true;
/*      */   }
/*      */   
/*  837 */   public MapWriter(OverlayManager overlayManager, BlockStateShortShapeCache blockStateShortShapeCache, BiomeGetter biomeGetter) { this.lastBlockStateForTextureColor = null;
/*  838 */     this.lastBlockStateForTextureColorResult = -1; this.loadingObject = new MapBlock(); this.textureColours = new HashMap<>(); this.blockColours = new HashMap<>(); this.overlayBuilder = new OverlayBuilder(overlayManager); this.mutableLocalPos = new class_2338.class_2339(); this.mutableGlobalPos = new class_2338.class_2339(); this.buggedStates = new ArrayList<>(); this.blockStateShortShapeCache = blockStateShortShapeCache;
/*      */     this.transparentCache = new CachedFunction(state -> Boolean.valueOf(shouldOverlay(state)));
/*      */     this.mutableBlockPos3 = new class_2338.class_2339();
/*      */     this.fluidToBlock = new CachedFunction(class_3610::method_15759);
/*      */     this.biomeGetter = biomeGetter;
/*      */     this.blockTintIndices = (Object2IntMap<class_2680>)new Object2IntOpenHashMap();
/*  844 */     this.reusableBlockModelPartList = new ArrayList<>(); } public int loadBlockColourFromTexture(class_2680 state, boolean convert, class_1937 world, class_2378<class_2248> blockRegistry, class_2338 globalPos) { if (this.clearCachedColours) {
/*  845 */       this.textureColours.clear();
/*  846 */       this.blockColours.clear();
/*  847 */       this.blockTintIndices.clear();
/*  848 */       this.lastBlockStateForTextureColor = null;
/*  849 */       this.lastBlockStateForTextureColorResult = -1;
/*  850 */       this.clearCachedColours = false;
/*  851 */       if (WorldMap.settings.debug)
/*  852 */         WorldMap.LOGGER.info("Xaero's World Map cache cleared!"); 
/*      */     } 
/*  854 */     if (state == this.lastBlockStateForTextureColor) {
/*  855 */       return this.lastBlockStateForTextureColorResult;
/*      */     }
/*  857 */     Integer c = this.blockColours.get(state);
/*  858 */     int red = 0;
/*  859 */     int green = 0;
/*  860 */     int blue = 0;
/*  861 */     int alpha = 0;
/*  862 */     class_2248 b = state.method_26204();
/*  863 */     if (c == null) {
/*  864 */       String name = null;
/*  865 */       int tintIndex = -1; try {
/*      */         class_1058 texture;
/*  867 */         List<class_777> upQuads = null;
/*  868 */         class_773 bms = class_310.method_1551().method_1541().method_3351();
/*  869 */         class_1087 model = bms.method_3335(state);
/*  870 */         if (convert) {
/*  871 */           upQuads = getQuads(model, world, globalPos, state, class_2350.field_11036);
/*      */         }
/*  873 */         class_1058 missingTexture = class_310.method_1551().method_72703().method_73025(class_10725.field_56382).method_4608(class_1047.method_4539());
/*  874 */         if (upQuads == null || upQuads.isEmpty() || ((class_777)upQuads.get(0)).comp_3724() == missingTexture) {
/*  875 */           texture = getParticleIcon(bms, model, world, globalPos, state);
/*  876 */           tintIndex = 0;
/*      */         } else {
/*  878 */           texture = ((class_777)upQuads.get(0)).comp_3724();
/*  879 */           tintIndex = ((class_777)upQuads.get(0)).comp_3722();
/*      */         } 
/*  881 */         if (texture == null)
/*  882 */           throw new SilentException("No texture for " + String.valueOf(state)); 
/*  883 */         c = Integer.valueOf(-1);
/*  884 */         name = String.valueOf(texture.method_45851().method_45816()) + ".png";
/*  885 */         String[] args = name.split(":");
/*  886 */         if (args.length < 2) {
/*  887 */           DEFAULT_RESOURCE[1] = args[0];
/*  888 */           args = DEFAULT_RESOURCE;
/*      */         } 
/*  890 */         Integer cachedColour = this.textureColours.get(name);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  897 */         if (cachedColour == null)
/*      */         
/*      */         { 
/*  900 */           class_2960 location = class_2960.method_60655(args[0], "textures/" + args[1]);
/*  901 */           class_3298 resource = class_310.method_1551().method_1478().method_14486(location).orElse(null);
/*  902 */           if (resource == null) {
/*  903 */             throw new SilentException("No texture " + String.valueOf(location));
/*      */           }
/*  905 */           InputStream input = resource.method_14482();
/*  906 */           BufferedImage img = ImageIO.read(input);
/*  907 */           red = 0;
/*  908 */           green = 0;
/*  909 */           blue = 0;
/*  910 */           int total = 0;
/*  911 */           int ts = Math.min(img.getWidth(), img.getHeight());
/*  912 */           if (ts > 0) {
/*  913 */             int diff = Math.max(1, Math.min(4, ts / 8));
/*  914 */             int parts = ts / diff;
/*      */             
/*  916 */             Raster raster = img.getData();
/*  917 */             int[] colorHolder = null;
/*  918 */             for (int i = 0; i < parts; i++) {
/*  919 */               for (int j = 0; j < parts; j++) {
/*      */                 int rgb;
/*  921 */                 if (img.getColorModel().getNumComponents() < 3) {
/*  922 */                   colorHolder = raster.getPixel(i * diff, j * diff, colorHolder);
/*  923 */                   int sample = colorHolder[0] & 0xFF;
/*  924 */                   int k = 255;
/*  925 */                   if (colorHolder.length > 1)
/*  926 */                     k = colorHolder[1]; 
/*  927 */                   rgb = k << 24 | sample << 16 | sample << 8 | sample;
/*      */                 } else {
/*  929 */                   rgb = img.getRGB(i * diff, j * diff);
/*  930 */                 }  int a = rgb >> 24 & 0xFF;
/*  931 */                 if (rgb != 0 && a != 0) {
/*  932 */                   red += rgb >> 16 & 0xFF;
/*  933 */                   green += rgb >> 8 & 0xFF;
/*  934 */                   blue += rgb & 0xFF;
/*  935 */                   alpha += a;
/*  936 */                   total++;
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*  941 */           input.close();
/*  942 */           if (total == 0)
/*  943 */             total = 1; 
/*  944 */           red /= total;
/*  945 */           green /= total;
/*  946 */           blue /= total;
/*  947 */           alpha /= total;
/*  948 */           if (convert && red == 0 && green == 0 && blue == 0) {
/*  949 */             throw new SilentException("Black texture " + ts);
/*      */           }
/*  951 */           c = Integer.valueOf(alpha << 24 | red << 16 | green << 8 | blue);
/*  952 */           this.textureColours.put(name, c); }
/*      */         else
/*  954 */         { c = cachedColour; } 
/*  955 */       } catch (FileNotFoundException e) {
/*  956 */         if (convert) {
/*  957 */           return loadBlockColourFromTexture(state, false, world, blockRegistry, globalPos);
/*      */         }
/*  959 */         WorldMap.LOGGER.info("Block file not found: " + String.valueOf(blockRegistry.method_10221(b)));
/*  960 */         c = Integer.valueOf(0);
/*  961 */         if (state != null && state.method_26205((class_1922)world, globalPos) != null)
/*  962 */           c = Integer.valueOf((state.method_26205((class_1922)world, globalPos)).field_16011); 
/*  963 */         if (name != null) {
/*  964 */           this.textureColours.put(name, c);
/*      */         }
/*  966 */       } catch (Exception e) {
/*  967 */         WorldMap.LOGGER.info("Exception when loading " + String.valueOf(blockRegistry.method_10221(b)) + " texture, using material colour.");
/*  968 */         c = Integer.valueOf(0);
/*  969 */         if (state.method_26205((class_1922)world, globalPos) != null)
/*  970 */           c = Integer.valueOf((state.method_26205((class_1922)world, globalPos)).field_16011); 
/*  971 */         if (name != null)
/*  972 */           this.textureColours.put(name, c); 
/*  973 */         if (e instanceof SilentException) {
/*  974 */           WorldMap.LOGGER.info(e.getMessage());
/*      */         } else {
/*  976 */           WorldMap.LOGGER.error("suppressed exception", e);
/*      */         } 
/*  978 */       }  if (c != null) {
/*  979 */         this.blockColours.put(state, c);
/*  980 */         this.blockTintIndices.put(state, tintIndex);
/*      */       } 
/*      */     } 
/*  983 */     this.lastBlockStateForTextureColor = state;
/*  984 */     this.lastBlockStateForTextureColorResult = c.intValue();
/*  985 */     return c.intValue(); }
/*      */ 
/*      */   
/*      */   public long getUpdateCounter() {
/*  989 */     return this.updateCounter;
/*      */   }
/*      */   
/*      */   public void resetPosition() {
/*  993 */     this.X = 0;
/*  994 */     this.Z = 0;
/*  995 */     this.insideX = 0;
/*  996 */     this.insideZ = 0;
/*      */   }
/*      */   
/*      */   public void requestCachedColoursClear() {
/* 1000 */     this.clearCachedColours = true;
/*      */   }
/*      */   
/*      */   public void setMapProcessor(MapProcessor mapProcessor) {
/* 1004 */     this.mapProcessor = mapProcessor;
/*      */   }
/*      */   
/*      */   public void setDirtyInWriteDistance(class_1657 player, class_1937 level) {
/* 1008 */     int writeDistance = getWriteDistance();
/* 1009 */     int playerChunkX = player.method_24515().method_10263() >> 4;
/* 1010 */     int playerChunkZ = player.method_24515().method_10260() >> 4;
/* 1011 */     int startChunkX = playerChunkX - writeDistance;
/* 1012 */     int startChunkZ = playerChunkZ - writeDistance;
/* 1013 */     int endChunkX = playerChunkX + writeDistance;
/* 1014 */     int endChunkZ = playerChunkZ + writeDistance;
/* 1015 */     for (int x = startChunkX; x < endChunkX; x++) {
/* 1016 */       for (int z = startChunkZ; z < endChunkZ; z++) {
/* 1017 */         class_2818 chunk = level.method_8497(x, z);
/* 1018 */         if (chunk != null) {
/*      */           try {
/* 1020 */             XaeroWorldMapCore.chunkCleanField.set(chunk, Boolean.valueOf(false));
/* 1021 */           } catch (IllegalArgumentException|IllegalAccessException e) {
/* 1022 */             throw new RuntimeException(e);
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public int getBlockTintIndex(class_2680 state) {
/* 1030 */     return this.blockTintIndices.getInt(state);
/*      */   }
/*      */   
/*      */   protected abstract boolean blockStateHasTranslucentRenderType(class_2680 paramclass_2680);
/*      */   
/*      */   protected abstract List<class_777> getQuads(class_1087 paramclass_1087, class_1937 paramclass_1937, class_2338 paramclass_2338, class_2680 paramclass_2680, class_2350 paramclass_2350);
/*      */   
/*      */   protected abstract class_1058 getParticleIcon(class_773 paramclass_773, class_1087 paramclass_1087, class_1937 paramclass_1937, class_2338 paramclass_2338, class_2680 paramclass_2680);
/*      */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\MapWriter.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */