/*      */ package xaero.map;
/*      */ 
/*      */ import com.google.common.collect.Sets;
/*      */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.lang.reflect.Field;
/*      */ import java.nio.channels.FileChannel;
/*      */ import java.nio.channels.FileLock;
/*      */ import java.nio.file.Files;
/*      */ import java.nio.file.InvalidPathException;
/*      */ import java.nio.file.OpenOption;
/*      */ import java.nio.file.Path;
/*      */ import java.nio.file.StandardOpenOption;
/*      */ import java.nio.file.attribute.FileAttribute;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashSet;
/*      */ import net.minecraft.class_1074;
/*      */ import net.minecraft.class_11278;
/*      */ import net.minecraft.class_1297;
/*      */ import net.minecraft.class_151;
/*      */ import net.minecraft.class_1937;
/*      */ import net.minecraft.class_1959;
/*      */ import net.minecraft.class_2248;
/*      */ import net.minecraft.class_2338;
/*      */ import net.minecraft.class_2378;
/*      */ import net.minecraft.class_2874;
/*      */ import net.minecraft.class_2960;
/*      */ import net.minecraft.class_310;
/*      */ import net.minecraft.class_3532;
/*      */ import net.minecraft.class_3611;
/*      */ import net.minecraft.class_5218;
/*      */ import net.minecraft.class_5321;
/*      */ import net.minecraft.class_634;
/*      */ import net.minecraft.class_638;
/*      */ import net.minecraft.class_642;
/*      */ import net.minecraft.class_7134;
/*      */ import net.minecraft.class_7225;
/*      */ import net.minecraft.class_746;
/*      */ import net.minecraft.class_7924;
/*      */ import xaero.map.biome.BiomeColorCalculator;
/*      */ import xaero.map.biome.BiomeGetter;
/*      */ import xaero.map.biome.BlockTintProvider;
/*      */ import xaero.map.cache.BlockStateShortShapeCache;
/*      */ import xaero.map.cache.BrokenBlockTintCache;
/*      */ import xaero.map.controls.ControlsRegister;
/*      */ import xaero.map.deallocator.ByteBufferDeallocator;
/*      */ import xaero.map.exception.OpenGLException;
/*      */ import xaero.map.file.MapRegionInfo;
/*      */ import xaero.map.file.MapSaveLoad;
/*      */ import xaero.map.file.RegionDetection;
/*      */ import xaero.map.file.worldsave.WorldDataHandler;
/*      */ import xaero.map.graphics.CustomVertexConsumers;
/*      */ import xaero.map.graphics.MapRenderHelper;
/*      */ import xaero.map.graphics.OpenGlHelper;
/*      */ import xaero.map.graphics.TextureUploader;
/*      */ import xaero.map.graphics.renderer.multitexture.MultiTextureRenderTypeRendererProvider;
/*      */ import xaero.map.gui.GuiMap;
/*      */ import xaero.map.gui.message.MessageBox;
/*      */ import xaero.map.gui.message.render.MessageBoxRenderer;
/*      */ import xaero.map.highlight.DimensionHighlighterHandler;
/*      */ import xaero.map.highlight.HighlighterRegistry;
/*      */ import xaero.map.highlight.MapRegionHighlightsPreparer;
/*      */ import xaero.map.mcworld.WorldMapClientWorldData;
/*      */ import xaero.map.mcworld.WorldMapClientWorldDataHelper;
/*      */ import xaero.map.minimap.MinimapRenderListener;
/*      */ import xaero.map.misc.CaveStartCalculator;
/*      */ import xaero.map.misc.Misc;
/*      */ import xaero.map.mods.SupportMods;
/*      */ import xaero.map.pool.MapTilePool;
/*      */ import xaero.map.radar.tracker.synced.ClientSyncedTrackedPlayerManager;
/*      */ import xaero.map.region.LayeredRegionManager;
/*      */ import xaero.map.region.LeveledRegion;
/*      */ import xaero.map.region.MapBlock;
/*      */ import xaero.map.region.MapLayer;
/*      */ import xaero.map.region.MapRegion;
/*      */ import xaero.map.region.MapTile;
/*      */ import xaero.map.region.MapTileChunk;
/*      */ import xaero.map.region.OverlayManager;
/*      */ import xaero.map.region.texture.BranchTextureRenderer;
/*      */ import xaero.map.region.texture.RegionTexture;
/*      */ import xaero.map.render.util.ImmediateRenderUtil;
/*      */ import xaero.map.task.MapRunnerTask;
/*      */ import xaero.map.world.MapDimension;
/*      */ import xaero.map.world.MapWorld;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class MapProcessor
/*      */ {
/*      */   public static final int ROOT_FOLDER_FORMAT = 4;
/*      */   public static final int DEFAULT_LIGHT_LEVELS = 4;
/*      */   private MapSaveLoad mapSaveLoad;
/*      */   private MapWriter mapWriter;
/*      */   private MapLimiter mapLimiter;
/*      */   private WorldDataHandler worldDataHandler;
/*      */   private ByteBufferDeallocator bufferDeallocator;
/*      */   private TextureUploader textureUploader;
/*      */   private BranchTextureRenderer branchTextureRenderer;
/*      */   private BiomeColorCalculator biomeColorCalculator;
/*      */   private final BlockStateShortShapeCache blockStateShortShapeCache;
/*      */   private final BiomeGetter biomeGetter;
/*      */   private final BrokenBlockTintCache brokenBlockTintCache;
/*      */   private final MapRegionHighlightsPreparer mapRegionHighlightsPreparer;
/*      */   private final CaveStartCalculator caveStartCalculator;
/*      */   private final ClientSyncedTrackedPlayerManager clientSyncedTrackedPlayerManager;
/*      */   private class_638 world;
/*      */   private class_7225<class_2248> worldBlockLookup;
/*      */   private class_2378<class_2248> worldBlockRegistry;
/*      */   private class_2378<class_3611> worldFluidRegistry;
/*      */   public class_2378<class_1959> worldBiomeRegistry;
/*      */   private class_2378<class_2874> worldDimensionTypeRegistry;
/*      */   private BlockTintProvider worldBlockTintProvider;
/*      */   private class_638 newWorld;
/*      */   private class_7225<class_2248> newWorldBlockLookup;
/*      */   public class_2378<class_2248> newWorldBlockRegistry;
/*      */   private class_2378<class_3611> newWorldFluidRegistry;
/*      */   public class_2378<class_1959> newWorldBiomeRegistry;
/*      */   public class_2378<class_2874> newWorldDimensionTypeRegistry;
/*      */   public final Object mainStuffSync;
/*      */   public class_638 mainWorld;
/*      */   private class_7225<class_2248> mainWorldBlockLookup;
/*      */   public class_2378<class_2248> mainWorldBlockRegistry;
/*      */   private class_2378<class_3611> mainWorldFluidRegistry;
/*      */   public class_2378<class_1959> mainWorldBiomeRegistry;
/*      */   public class_2378<class_2874> mainWorldDimensionTypeRegistry;
/*      */   public double mainPlayerX;
/*      */   public double mainPlayerY;
/*      */   public double mainPlayerZ;
/*      */   private boolean mainWorldUnloaded;
/*  139 */   private ArrayList<Double[]> footprints = (ArrayList)new ArrayList<>();
/*      */   
/*      */   private int footprintsTimer;
/*      */   
/*      */   private boolean mapWorldUsable;
/*      */   
/*      */   private MapWorld mapWorld;
/*      */   
/*      */   private String currentWorldId;
/*      */   
/*      */   private String currentDimId;
/*      */   
/*      */   private String currentMWId;
/*      */   
/*      */   private FileLock mapLockToRelease;
/*      */   
/*      */   private FileChannel mapLockChannelToClose;
/*      */   
/*      */   private FileChannel currentMapLockChannel;
/*      */   
/*      */   private FileLock currentMapLock;
/*      */   private boolean mapWorldUsableRequest;
/*  161 */   public final Object renderThreadPauseSync = new Object();
/*      */   
/*      */   private int pauseUploading;
/*      */   private int pauseRendering;
/*      */   private int pauseWriting;
/*  166 */   public final Object processorThreadPauseSync = new Object();
/*      */   
/*      */   private int pauseProcessing;
/*  169 */   private final Object loadingSync = new Object();
/*      */   
/*      */   private boolean isLoading;
/*  172 */   public final Object uiSync = new Object();
/*      */   
/*      */   private boolean waitingForWorldUpdate;
/*      */   
/*  176 */   public final Object uiPauseSync = new Object();
/*      */ 
/*      */   
/*      */   private boolean isUIPaused;
/*      */ 
/*      */   
/*      */   private ArrayList<LeveledRegion<?>>[] toProcessLevels;
/*      */   
/*  184 */   private ArrayList<MapRegion> toRefresh = new ArrayList<>();
/*      */ 
/*      */   
/*      */   private static final int SPAWNPOINT_TIMEOUT = 3000;
/*      */ 
/*      */   
/*      */   private class_2338 spawnToRestore;
/*      */   
/*  192 */   private long mainWorldChangedTime = -1L;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private MapTilePool tilePool;
/*      */ 
/*      */ 
/*      */   
/*      */   private int firstBranchLevel;
/*      */ 
/*      */ 
/*      */   
/*  205 */   private long lastRenderProcessTime = -1L;
/*      */   private int workingFramesCount;
/*  207 */   public long freeFramePeriod = -1L;
/*  208 */   private int testingFreeFrame = 1;
/*      */ 
/*      */   
/*      */   private MultiTextureRenderTypeRendererProvider multiTextureRenderTypeRenderers;
/*      */   
/*      */   private CustomVertexConsumers cvc;
/*      */   
/*      */   private class_11278 mapProjectionCache;
/*      */   
/*      */   private final MessageBox messageBox;
/*      */   
/*      */   private final MessageBoxRenderer messageBoxRenderer;
/*      */   
/*      */   private MinimapRenderListener minimapRenderListener;
/*      */   
/*      */   private boolean currentMapNeedsDeletion;
/*      */   
/*      */   private OverlayManager overlayManager;
/*      */   
/*      */   private long renderStartTime;
/*      */   
/*      */   private Runnable renderStartTimeUpdaterRunnable;
/*      */   
/*      */   private boolean finalizing;
/*      */   
/*      */   private int state;
/*      */   
/*      */   private HashSet<class_2960> hardcodedNetherlike;
/*      */   
/*      */   private final HighlighterRegistry highlighterRegistry;
/*      */   
/*  239 */   private int currentCaveLayer = Integer.MAX_VALUE;
/*      */   private long lastLocalCaveModeToggle;
/*  241 */   private int nextLocalCaveMode = Integer.MAX_VALUE;
/*  242 */   private int localCaveMode = Integer.MAX_VALUE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean consideringNetherFairPlayMessage;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String[] dimensionsToIgnore;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Field selectedField;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onInit(class_634 connection) {
/*  290 */     String mainId = getMainId(4, connection);
/*  291 */     fixRootFolder(mainId, connection);
/*  292 */     this.mapWorld = new MapWorld(mainId, getMainId(0, connection), this);
/*  293 */     this.mapWorld.load();
/*      */   }
/*      */   
/*      */   public void run(MapRunner runner) {
/*  297 */     if (this.state < 2) {
/*      */       try {
/*  299 */         while (this.state < 2 && WorldMap.crashHandler.getCrashedBy() == null)
/*      */         {
/*  301 */           synchronized (this.processorThreadPauseSync) {
/*  302 */             if (!isProcessingPaused()) {
/*      */               
/*  304 */               updateWorld();
/*  305 */               if (this.world != null)
/*  306 */                 updateFootprints(((class_310.method_1551()).field_1755 instanceof GuiMap) ? 1 : 10); 
/*  307 */               if (this.mapWorldUsable) {
/*  308 */                 this.mapLimiter.applyLimit(this.mapWorld, this);
/*  309 */                 long currentTime = System.currentTimeMillis();
/*  310 */                 for (int l = 0; l < this.toProcessLevels.length; l++) {
/*  311 */                   ArrayList<LeveledRegion<?>> regionsToProcess = this.toProcessLevels[l];
/*  312 */                   for (int i = 0; i < regionsToProcess.size(); i++) {
/*      */                     LeveledRegion<?> leveledRegion;
/*  314 */                     synchronized (regionsToProcess) {
/*  315 */                       if (i >= regionsToProcess.size())
/*      */                         break; 
/*  317 */                       leveledRegion = regionsToProcess.get(i);
/*      */                     } 
/*  319 */                     this.mapSaveLoad.updateSave(leveledRegion, currentTime, this.currentCaveLayer);
/*      */                   } 
/*      */                 } 
/*      */               } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  345 */               this.mapSaveLoad.run(this.worldBlockLookup, this.worldBlockRegistry, this.worldFluidRegistry, this.biomeGetter, this.worldBiomeRegistry);
/*  346 */               handleRefresh();
/*      */               
/*  348 */               runner.doTasks(this);
/*  349 */               releaseLocksIfNeeded();
/*      */             } 
/*      */           } 
/*      */           try {
/*  353 */             Thread.sleep((this.world == null || Misc.screenShouldSkipWorldRender((class_310.method_1551()).field_1755, true) || this.state > 0) ? 40L : 100L);
/*  354 */           } catch (InterruptedException interruptedException) {}
/*      */         }
/*      */       
/*  357 */       } catch (Throwable e) {
/*  358 */         WorldMap.crashHandler.setCrashedBy(e);
/*      */       } 
/*  360 */       if (this.state < 2)
/*  361 */         forceClean(); 
/*      */     } 
/*  363 */     if (this.state == 2)
/*  364 */       this.state = 3; 
/*      */   }
/*      */   
/*      */   public void onRenderProcess(class_310 mc) throws RuntimeException {
/*      */     try {
/*  369 */       this.mapWriter.onRender(this.biomeColorCalculator, this.overlayManager);
/*  370 */       long renderProcessTime = System.nanoTime();
/*  371 */       if (this.testingFreeFrame == 1) {
/*  372 */         this.testingFreeFrame = 2;
/*      */       } else {
/*  374 */         synchronized (this.renderThreadPauseSync) {
/*  375 */           if (this.lastRenderProcessTime == -1L)
/*  376 */             this.lastRenderProcessTime = renderProcessTime; 
/*  377 */           long sinceLastProcessTime = renderProcessTime - this.lastRenderProcessTime;
/*  378 */           if (this.testingFreeFrame == 2) {
/*  379 */             this.freeFramePeriod = sinceLastProcessTime;
/*  380 */             this.testingFreeFrame = 0;
/*      */           } 
/*  382 */           if (this.pauseUploading == 0 && this.mapWorldUsable && this.currentWorldId != null) {
/*  383 */             mc.method_22940().method_23000().method_22993();
/*  384 */             OpenGlHelper.clearErrors(false, "onRenderProcess");
/*      */             
/*  386 */             OpenGlHelper.resetPixelStore();
/*  387 */             ImmediateRenderUtil.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
/*  388 */             OpenGLException.checkGLError();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  403 */             updateCaveStart();
/*      */             
/*  405 */             MapDimension currentDim = this.mapWorld.getCurrentDimension();
/*  406 */             if (currentDim.getFullReloader() != null)
/*  407 */               currentDim.getFullReloader().onRenderProcess(); 
/*  408 */             DimensionHighlighterHandler highlighterHandler = currentDim.getHighlightHandler();
/*      */             
/*  410 */             int globalRegionCacheHashCode = WorldMap.settings.getRegionCacheHashCode();
/*  411 */             boolean detailedDebug = WorldMap.settings.detailed_debug;
/*  412 */             long uploadStart = System.nanoTime();
/*  413 */             long totalTime = Math.min(sinceLastProcessTime, this.freeFramePeriod);
/*      */             
/*  415 */             long passed = uploadStart - this.renderStartTime;
/*  416 */             long timeAvailable = Math.max(3000000L, totalTime - passed);
/*      */             
/*  418 */             long uploadUntil = uploadStart + timeAvailable / 4L;
/*  419 */             long gpuLimit = Math.max(1000000L, ((class_310.method_1551()).field_1755 instanceof GuiMap) ? (totalTime * 5L / 12L) : Math.min(totalTime / 5L, timeAvailable));
/*  420 */             boolean noLimits = false;
/*  421 */             if ((class_310.method_1551()).field_1755 instanceof GuiMap) {
/*  422 */               GuiMap guiMap = (GuiMap)(class_310.method_1551()).field_1755;
/*  423 */               noLimits = guiMap.noUploadingLimits;
/*  424 */               guiMap.noUploadingLimits = false;
/*      */             } 
/*  426 */             int firstLevel = 0;
/*  427 */             boolean branchesCatchup = ((int)(Math.random() * 5.0D) == 0);
/*  428 */             if (branchesCatchup)
/*  429 */               firstLevel = 1 + this.firstBranchLevel; 
/*  430 */             this.firstBranchLevel = (this.firstBranchLevel + 1) % (this.toProcessLevels.length - 1);
/*  431 */             for (int j = 0; j < this.toProcessLevels.length; j++) {
/*  432 */               int level = (firstLevel + j) % this.toProcessLevels.length;
/*  433 */               ArrayList<LeveledRegion<?>> toProcess = this.toProcessLevels[level];
/*  434 */               for (int i = 0; i < toProcess.size(); i++) {
/*      */                 LeveledRegion<? extends RegionTexture<?>> region;
/*  436 */                 synchronized (toProcess) {
/*  437 */                   if (i >= toProcess.size())
/*      */                     break; 
/*  439 */                   region = (LeveledRegion<? extends RegionTexture<?>>)toProcess.get(i);
/*      */                 } 
/*      */                 
/*  442 */                 if (region != null)
/*      */                 {
/*  444 */                   synchronized (region) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                     
/*  454 */                     if (region.shouldBeProcessed()) {
/*      */                       
/*  456 */                       boolean cleanAndCacheRequestsBlocked = region.cleanAndCacheRequestsBlocked();
/*  457 */                       boolean allCleaned = true;
/*  458 */                       boolean allCached = true;
/*  459 */                       boolean allUploaded = true;
/*  460 */                       boolean hasLoadedTextures = false;
/*      */                       
/*  462 */                       for (int x = 0; x < 8; x++) {
/*  463 */                         for (int z = 0; z < 8; z++) {
/*      */                           
/*  465 */                           RegionTexture texture = region.getTexture(x, z);
/*  466 */                           if (texture != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                             
/*  473 */                             if (texture.canUpload()) {
/*  474 */                               hasLoadedTextures = true;
/*  475 */                               if (noLimits || (gpuLimit > 0L && System.nanoTime() < uploadUntil)) {
/*  476 */                                 texture.preUpload(this, this.worldBlockTintProvider, this.overlayManager, region, detailedDebug, this.blockStateShortShapeCache);
/*  477 */                                 if (texture.shouldUpload())
/*  478 */                                   if (texture.getTimer() == 0) {
/*  479 */                                     gpuLimit -= texture.uploadBuffer(highlighterHandler, this.textureUploader, region, this.branchTextureRenderer, x, z);
/*      */                                   } else {
/*  481 */                                     texture.decTimer();
/*      */                                   }  
/*      */                               } 
/*  484 */                               texture.postUpload(this, region, cleanAndCacheRequestsBlocked);
/*      */                             } 
/*  486 */                             if (texture.hasSourceData())
/*  487 */                               allCleaned = false; 
/*  488 */                             if (texture.shouldIncludeInCache() && !texture.isCachePrepared())
/*  489 */                               allCached = false; 
/*  490 */                             if (!texture.isUploaded())
/*  491 */                               allUploaded = false; 
/*      */                           } 
/*      */                         } 
/*  494 */                       }  if (hasLoadedTextures)
/*  495 */                         region.processWhenLoadedChunksExist(globalRegionCacheHashCode); 
/*  496 */                       allUploaded = (allUploaded && region.isLoaded() && !cleanAndCacheRequestsBlocked);
/*  497 */                       allCached = (allCached && allUploaded);
/*  498 */                       if ((!region.shouldCache() || !region.recacheHasBeenRequested()) && region.shouldEndProcessingAfterUpload() && allCleaned && allUploaded) {
/*  499 */                         region.onProcessingEnd();
/*  500 */                         region.deleteGLBuffers();
/*  501 */                         synchronized (toProcess) {
/*  502 */                           if (i < toProcess.size()) {
/*  503 */                             toProcess.remove(i);
/*      */                             
/*  505 */                             i--;
/*      */                           } 
/*      */                         } 
/*      */ 
/*      */                         
/*  510 */                         if (WorldMap.settings.debug) {
/*  511 */                           WorldMap.LOGGER.info("Region freed: " + String.valueOf(region) + " " + this.mapWriter.getUpdateCounter() + " " + this.currentWorldId + " " + this.currentDimId);
/*      */                         }
/*      */                       } 
/*  514 */                       if (allCached && !region.isAllCachePrepared()) {
/*  515 */                         region.setAllCachePrepared(true);
/*      */                       }
/*  517 */                       if (region.shouldCache() && region.recacheHasBeenRequested() && region.isAllCachePrepared() && !cleanAndCacheRequestsBlocked)
/*  518 */                         getMapSaveLoad().requestCache(region); 
/*      */                     } 
/*      */                   } 
/*      */                 }
/*      */               } 
/*      */             } 
/*  524 */             this.workingFramesCount++;
/*  525 */             if (this.workingFramesCount >= 30) {
/*  526 */               this.testingFreeFrame = 1;
/*  527 */               this.workingFramesCount = 0;
/*      */             } 
/*      */ 
/*      */             
/*  531 */             this.textureUploader.uploadTextures();
/*      */           } 
/*      */         } 
/*  534 */       }  this.mapLimiter.updateAvailableVRAM();
/*  535 */       this.lastRenderProcessTime = renderProcessTime;
/*      */     
/*      */     }
/*  538 */     catch (Throwable e) {
/*  539 */       WorldMap.crashHandler.setCrashedBy(e);
/*      */     } 
/*  541 */     WorldMap.crashHandler.checkForCrashes();
/*  542 */     MapRenderHelper.restoreDefaultShaderBlendState();
/*      */   }
/*      */   public void updateCaveStart() {
/*      */     int newCaveStart;
/*  546 */     class_310 mc = class_310.method_1551();
/*  547 */     MapDimension dimension = this.mapWorld.getCurrentDimension();
/*      */     
/*  549 */     if (!WorldMap.settings.isCaveMapsAllowed() || dimension.getCaveModeType() == 0) {
/*  550 */       newCaveStart = Integer.MAX_VALUE;
/*      */     } else {
/*  552 */       if (WorldMap.settings.caveModeStart == Integer.MAX_VALUE) {
/*  553 */         newCaveStart = Integer.MIN_VALUE;
/*      */       } else {
/*  555 */         newCaveStart = WorldMap.settings.caveModeStart;
/*  556 */       }  boolean customDim = (dimension.getDimId() != mc.field_1687.method_27983());
/*  557 */       boolean isMapScreen = (mc.field_1755 instanceof GuiMap || Misc.screenShouldSkipWorldRender(mc.field_1755, true));
/*  558 */       if (SupportMods.minimap() && ((!customDim && WorldMap.settings.autoCaveMode < 0 && newCaveStart == Integer.MIN_VALUE) || !isMapScreen))
/*  559 */         newCaveStart = SupportMods.xaeroMinimap.getCaveStart(newCaveStart, isMapScreen); 
/*  560 */       if (newCaveStart == Integer.MIN_VALUE) {
/*  561 */         long currentTime = System.currentTimeMillis();
/*      */         
/*  563 */         int nextLocalCaveMode = customDim ? Integer.MAX_VALUE : this.caveStartCalculator.getCaving(mc.field_1724.method_23317(), mc.field_1724.method_23318(), mc.field_1724.method_23321(), (class_1937)mc.field_1687);
/*  564 */         boolean toggling = (((this.localCaveMode == Integer.MAX_VALUE) ? true : false) != ((nextLocalCaveMode == Integer.MAX_VALUE) ? true : false));
/*  565 */         if (!toggling || currentTime - this.lastLocalCaveModeToggle > WorldMap.settings.caveModeToggleTimer) {
/*  566 */           if (toggling)
/*  567 */             this.lastLocalCaveModeToggle = currentTime; 
/*  568 */           this.localCaveMode = nextLocalCaveMode;
/*      */         } 
/*  570 */         newCaveStart = this.localCaveMode;
/*      */       } 
/*  572 */       if (newCaveStart != Integer.MAX_VALUE)
/*  573 */         if (dimension.getCaveModeType() == 2) {
/*  574 */           newCaveStart = Integer.MIN_VALUE;
/*      */         } else {
/*  576 */           newCaveStart = class_3532.method_15340(newCaveStart, this.world.method_31607(), this.world.method_31600());
/*      */         }  
/*      */     } 
/*  579 */     int newCaveLayer = getCaveLayer(newCaveStart);
/*  580 */     dimension.getLayeredMapRegions().getLayer(newCaveLayer).setCaveStart(newCaveStart);
/*  581 */     this.currentCaveLayer = newCaveLayer;
/*      */   }
/*      */   
/*  584 */   public MapProcessor(MapSaveLoad mapSaveLoad, MapWriter mapWriter, MapLimiter mapLimiter, ByteBufferDeallocator bufferDeallocator, MapTilePool tilePool, OverlayManager overlayManager, TextureUploader textureUploader, WorldDataHandler worldDataHandler, BranchTextureRenderer branchTextureRenderer, MultiTextureRenderTypeRendererProvider mtrtrs, CustomVertexConsumers cvc, BiomeColorCalculator biomeColorCalculator, BlockStateShortShapeCache blockStateShortShapeCache, BiomeGetter biomeGetter, BrokenBlockTintCache brokenBlockTintCache, HighlighterRegistry highlighterRegistry, MapRegionHighlightsPreparer mapRegionHighlightsPreparer, MessageBox messageBox, MessageBoxRenderer messageBoxRenderer, CaveStartCalculator caveStartCalculator, ClientSyncedTrackedPlayerManager clientSyncedTrackedPlayerManager) throws NoSuchFieldException { this.dimensionsToIgnore = new String[] { "FZHammer" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  623 */     this.selectedField = null; this.branchTextureRenderer = branchTextureRenderer; this.mapSaveLoad = mapSaveLoad; this.mapWriter = mapWriter; this.mapLimiter = mapLimiter; this.bufferDeallocator = bufferDeallocator; this.tilePool = tilePool; this.overlayManager = overlayManager; this.textureUploader = textureUploader; this.worldDataHandler = worldDataHandler; this.renderStartTimeUpdaterRunnable = new Runnable() {
/*      */         public void run() { MapProcessor.this.updateRenderStartTime(); }
/*      */       }; this.mainStuffSync = new Object(); this.toProcessLevels = (ArrayList<LeveledRegion<?>>[])new ArrayList[4]; for (int i = 0; i < this.toProcessLevels.length; i++)
/*      */       this.toProcessLevels[i] = new ArrayList<>();  this.multiTextureRenderTypeRenderers = mtrtrs; this.cvc = cvc; this.biomeColorCalculator = biomeColorCalculator; this.blockStateShortShapeCache = blockStateShortShapeCache; this.hardcodedNetherlike = Sets.newHashSet((Object[])new class_2960[] { class_7134.field_37671, class_2960.method_60655("undergarden", "undergarden") }); this.biomeGetter = biomeGetter; this.brokenBlockTintCache = brokenBlockTintCache; this.highlighterRegistry = highlighterRegistry; this.mapRegionHighlightsPreparer = mapRegionHighlightsPreparer; this.messageBox = messageBox; this.messageBoxRenderer = messageBoxRenderer; this.caveStartCalculator = caveStartCalculator; this.clientSyncedTrackedPlayerManager = clientSyncedTrackedPlayerManager; this.minimapRenderListener = new MinimapRenderListener(); } public boolean ignoreWorld(class_1937 world) { for (int i = 0; i < this.dimensionsToIgnore.length; i++) { if (this.dimensionsToIgnore[i].equals(world.method_27983().method_29177().method_12832()))
/*      */         return true;  }
/*  628 */      return false; } public void waitForLoadingToFinish(Runnable onFinish) { while (true) { synchronized (this.loadingSync)
/*  629 */       { if (this.isLoading)
/*  630 */         { this.blockStateShortShapeCache.supplyForIOThread();
/*  631 */           this.worldDataHandler.handleRenderExecutor(); }
/*      */         else
/*      */         
/*  634 */         { onFinish.run(); break; }  }  }  }
/*      */   public String getDimensionName(class_5321<class_1937> id) { if (id == class_1937.field_25179) return "null";  if (id == class_1937.field_25180)
/*      */       return "DIM-1";  if (id == class_1937.field_25181)
/*      */       return "DIM1";  class_2960 identifier = id.method_29177(); return identifier.method_12836() + "$" + identifier.method_12836(); }
/*      */   public class_5321<class_1937> getDimensionIdForFolder(String folderName) { if (folderName.equals("null"))
/*      */       return class_1937.field_25179;  if (folderName.equals("DIM-1"))
/*      */       return class_1937.field_25180;  if (folderName.equals("DIM1"))
/*      */       return class_1937.field_25181;  int separatorIndex = folderName.indexOf('$'); if (separatorIndex == -1)
/*  642 */       return null;  String namespace = folderName.substring(0, separatorIndex); String path = folderName.substring(separatorIndex + 1).replace('%', '/'); try { class_2960 dimensionId = class_2960.method_60655(namespace, path); return class_5321.method_29179(class_7924.field_41223, dimensionId); } catch (class_151 rse) { return null; }  } public synchronized void changeWorld(class_638 world, class_7225<class_2248> blockLookup, class_2378<class_2248> blockRegistry, class_2378<class_3611> fluidRegistry, class_2378<class_1959> biomeRegistry, class_2378<class_2874> dimensionTypeRegistry) { pushWriterPause();
/*  643 */     if (world != this.newWorld)
/*  644 */       waitForLoadingToFinish(() -> this.waitingForWorldUpdate = true); 
/*  645 */     this.newWorld = world;
/*  646 */     this.newWorldBlockLookup = blockLookup;
/*  647 */     this.newWorldBlockRegistry = blockRegistry;
/*  648 */     this.newWorldFluidRegistry = fluidRegistry;
/*  649 */     this.newWorldBiomeRegistry = biomeRegistry;
/*  650 */     this.newWorldDimensionTypeRegistry = dimensionTypeRegistry;
/*  651 */     if (world == null) {
/*  652 */       this.mapWorldUsableRequest = false;
/*      */     } else {
/*      */       
/*  655 */       this.mapWorldUsableRequest = true;
/*  656 */       class_5321<class_1937> dimId = this.mapWorld.getPotentialDimId();
/*  657 */       this.mapWorld.setFutureDimensionId(dimId);
/*  658 */       updateDimension(world, dimId);
/*  659 */       this.mapWorld.getFutureDimension().resetCustomMultiworldUnsynced();
/*      */     } 
/*  661 */     popWriterPause(); }
/*      */ 
/*      */   
/*      */   public void updateVisitedDimension(class_638 world) {
/*  665 */     updateDimension(world, world.method_27983());
/*      */   }
/*      */   
/*      */   public synchronized void updateDimension(class_638 world, class_5321<class_1937> dimId) {
/*  669 */     if (world == null)
/*      */       return; 
/*  671 */     Object autoIdBase = getAutoIdBase(world);
/*  672 */     MapDimension mapDimension = this.mapWorld.getDimension(dimId);
/*  673 */     if (mapDimension == null)
/*  674 */       mapDimension = this.mapWorld.createDimensionUnsynced(dimId); 
/*  675 */     mapDimension.updateFutureAutomaticUnsynced(class_310.method_1551(), autoIdBase);
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   private String getMainId(boolean rootFolderFormat, boolean preIP6Fix, class_634 connection) {
/*  680 */     if (!rootFolderFormat)
/*  681 */       return getMainId(0, connection); 
/*  682 */     return getMainId(preIP6Fix ? 1 : 2, connection);
/*      */   }
/*      */   
/*      */   private String getMainId(int version, class_634 connection) {
/*  686 */     class_310 mc = class_310.method_1551();
/*  687 */     String result = null;
/*  688 */     if (mc.method_1576() != null) {
/*  689 */       result = MapWorld.convertWorldFolderToRootId(version, mc.method_1576().method_27050(class_5218.field_24188).getParent().getFileName().toString());
/*      */     } else {
/*      */       
/*  692 */       class_642 serverData = connection.method_45734();
/*  693 */       if (serverData != null && serverData.method_52811() && WorldMap.events.getLatestRealm() != null) {
/*  694 */         result = "Realms_" + String.valueOf((WorldMap.events.getLatestRealm()).field_22605) + "." + (WorldMap.events.getLatestRealm()).field_22599;
/*  695 */       } else if (serverData != null) {
/*  696 */         int portDivider; String serverIP = WorldMap.settings.differentiateByServerAddress ? serverData.field_3761 : "Any Address";
/*      */         
/*  698 */         if (version >= 2 && serverIP.indexOf(":") != serverIP.lastIndexOf(":")) {
/*  699 */           portDivider = serverIP.lastIndexOf("]:") + 1;
/*      */         } else {
/*  701 */           portDivider = serverIP.indexOf(":");
/*  702 */         }  if (portDivider > 0)
/*  703 */           serverIP = serverIP.substring(0, portDivider); 
/*  704 */         while (version >= 1 && serverIP.endsWith("."))
/*  705 */           serverIP = serverIP.substring(0, serverIP.length() - 1); 
/*  706 */         if (version >= 3) {
/*  707 */           serverIP = serverIP.replace("[", "").replace("]", "");
/*      */         }
/*  709 */         result = "Multiplayer_" + serverIP.replaceAll(":", (version < 4) ? "§" : ".");
/*      */       }
/*      */       else {
/*      */         
/*  713 */         result = "Multiplayer_Unknown";
/*      */       } 
/*      */     } 
/*  716 */     return result;
/*      */   }
/*      */   
/*      */   public synchronized void toggleMultiworldType(MapDimension dim) {
/*  720 */     if (this.mapWorldUsable && !this.waitingForWorldUpdate && this.mapWorld.isMultiplayer() && this.mapWorld.getCurrentDimension() == dim) {
/*  721 */       this.mapWorld.toggleMultiworldTypeUnsynced();
/*      */     }
/*      */   }
/*      */   
/*      */   public synchronized void quickConfirmMultiworld() {
/*  726 */     if (canQuickConfirmUnsynced() && this.mapWorld.getCurrentDimension().hasConfirmedMultiworld()) {
/*  727 */       confirmMultiworld(this.mapWorld.getCurrentDimension());
/*      */     }
/*      */   }
/*      */   
/*      */   public synchronized boolean confirmMultiworld(MapDimension dim) {
/*  732 */     if (this.mapWorldUsable && this.mainWorld != null && this.mapWorld.getPotentialDimId() == this.mapWorld.getCurrentDimensionId() && this.mapWorld.getCurrentDimension() == dim) {
/*  733 */       this.mapWorld.confirmMultiworldTypeUnsynced();
/*  734 */       this.mapWorld.getCurrentDimension().confirmMultiworldUnsynced();
/*      */       
/*  736 */       return true;
/*      */     } 
/*  738 */     return false;
/*      */   }
/*      */   
/*      */   public synchronized void setMultiworld(MapDimension dimToCompare, String customMW) {
/*  742 */     if (this.mapWorldUsable && dimToCompare.getMapWorld() == this.mapWorld)
/*  743 */       dimToCompare.setMultiworldUnsynced(customMW); 
/*      */   }
/*      */   
/*      */   public boolean canQuickConfirmUnsynced() {
/*  747 */     return (this.mapWorldUsable && !(this.mapWorld.getCurrentDimension()).futureMultiworldWritable && this.mapWorld.getPotentialDimId() == this.mapWorld.getCurrentDimensionId());
/*      */   }
/*      */   
/*      */   public String getCrosshairMessage() {
/*  751 */     synchronized (this.uiPauseSync) {
/*  752 */       if (this.isUIPaused)
/*  753 */         return null; 
/*  754 */       if (canQuickConfirmUnsynced()) {
/*  755 */         String selectedMWName = this.mapWorld.getCurrentDimension().getMultiworldName(this.mapWorld.getCurrentDimension().getFutureMultiworldUnsynced());
/*  756 */         String message = "§2(" + ControlsRegister.keyOpenMap.method_16007().getString().toUpperCase() + ")§r " + class_1074.method_4662("gui.xaero_map_unconfirmed", new Object[0]);
/*  757 */         if (this.mapWorld.getCurrentDimension().hasConfirmedMultiworld())
/*  758 */           message = message + " §2" + message + "§r for map \"" + ControlsRegister.keyQuickConfirm.method_16007().getString().toUpperCase() + "\""; 
/*  759 */         return message;
/*      */       } 
/*      */     } 
/*  762 */     return null;
/*      */   }
/*      */   
/*      */   public synchronized void checkForWorldUpdate() {
/*  766 */     if (this.mainWorld != null) {
/*  767 */       Object autoIdBase = getAutoIdBase(this.mainWorld);
/*  768 */       if (autoIdBase != null) {
/*  769 */         boolean baseChanged = !autoIdBase.equals(getUsedAutoIdBase(this.mainWorld));
/*  770 */         class_5321<class_1937> potentialDimId = this.mapWorld.getPotentialDimId();
/*  771 */         if (baseChanged && this.mapWorldUsableRequest) {
/*  772 */           MapDimension mapDimension = this.mapWorld.getDimension(potentialDimId);
/*  773 */           if (mapDimension != null) {
/*  774 */             boolean serverBasedBefore = mapDimension.isFutureMultiworldServerBased();
/*  775 */             mapDimension.updateFutureAutomaticUnsynced(class_310.method_1551(), autoIdBase);
/*  776 */             if (serverBasedBefore != mapDimension.isFutureMultiworldServerBased())
/*  777 */               mapDimension.resetCustomMultiworldUnsynced(); 
/*      */           } 
/*      */         } 
/*  780 */         if (this.mainWorld != this.world || potentialDimId != this.mapWorld.getFutureDimensionId())
/*  781 */           changeWorld(this.mainWorld, this.mainWorldBlockLookup, this.mainWorldBlockRegistry, this.mainWorldFluidRegistry, this.mainWorldBiomeRegistry, this.mainWorldDimensionTypeRegistry); 
/*  782 */         Object updatedAutoIdBase = getAutoIdBase(this.mainWorld);
/*  783 */         if (updatedAutoIdBase != null) {
/*  784 */           setUsedAutoIdBase(this.mainWorld, updatedAutoIdBase);
/*      */         } else {
/*  786 */           removeUsedAutoIdBase(this.mainWorld);
/*      */         } 
/*  788 */         if (potentialDimId != this.mainWorld.method_27983())
/*  789 */           updateVisitedDimension(this.mainWorld); 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void updateWorld() throws IOException, CommandSyntaxException {
/*  795 */     pushUIPause();
/*  796 */     updateWorldSynced();
/*  797 */     popUIPause();
/*  798 */     if (this.mapWorldUsable && !this.mapSaveLoad.isRegionDetectionComplete()) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  804 */       this.mapSaveLoad.detectRegions(10);
/*  805 */       this.mapSaveLoad.setRegionDetectionComplete(true);
/*      */     } 
/*      */   }
/*      */   
/*      */   private synchronized void updateWorldSynced() throws IOException, CommandSyntaxException {
/*  810 */     synchronized (this.uiSync) {
/*  811 */       boolean changedDimension = (this.mapWorldUsable != this.mapWorldUsableRequest || !this.mapWorldUsableRequest || this.mapWorld.getFutureDimension() != this.mapWorld.getCurrentDimension());
/*  812 */       if (this.mapWorldUsable != this.mapWorldUsableRequest || (this.mapWorldUsableRequest && (changedDimension || 
/*      */ 
/*      */         
/*  815 */         !this.mapWorld.getFutureDimension().getFutureMultiworldUnsynced().equals(this.mapWorld.getFutureDimension().getCurrentMultiworld())))) {
/*  816 */         String newMWId = !this.mapWorldUsableRequest ? null : this.mapWorld.getFutureMultiworldUnsynced();
/*  817 */         pushRenderPause(true, true);
/*  818 */         pushWriterPause();
/*  819 */         String newWorldId = !this.mapWorldUsableRequest ? null : this.mapWorld.getMainId();
/*  820 */         boolean shouldClearAllDimensions = (this.state == 1);
/*  821 */         boolean shouldClearNewDimension = (this.mapWorldUsableRequest && !this.mapWorld.getFutureMultiworldUnsynced().equals(this.mapWorld.getFutureDimension().getCurrentMultiworld()));
/*  822 */         this.mapSaveLoad.getToSave().clear();
/*      */         
/*  824 */         if (this.currentMapLock != null) {
/*  825 */           this.mapLockToRelease = this.currentMapLock;
/*  826 */           this.mapLockChannelToClose = this.currentMapLockChannel;
/*  827 */           this.currentMapLock = null;
/*  828 */           this.currentMapLockChannel = null;
/*      */         } 
/*  830 */         releaseLocksIfNeeded();
/*  831 */         if (this.mapWorld.getCurrentDimensionId() != null) {
/*  832 */           MapDimension currentDim = this.mapWorld.getCurrentDimension();
/*  833 */           MapDimension reqDim = !this.mapWorldUsableRequest ? null : this.mapWorld.getFutureDimension();
/*  834 */           boolean shouldFinishCurrentDim = (this.mapWorldUsable && !this.currentMapNeedsDeletion);
/*  835 */           boolean currentDimChecked = false;
/*  836 */           if (shouldFinishCurrentDim)
/*  837 */             this.mapSaveLoad.saveAll = true; 
/*  838 */           if (shouldFinishCurrentDim || (shouldClearNewDimension && reqDim == currentDim)) {
/*  839 */             for (LeveledRegion<?> region : (Iterable<LeveledRegion<?>>)currentDim.getLayeredMapRegions().getUnsyncedSet()) {
/*  840 */               if (shouldFinishCurrentDim) {
/*  841 */                 if (region.getLevel() == 0) {
/*  842 */                   MapRegion leafRegion = (MapRegion)region;
/*  843 */                   if (!leafRegion.isNormalMapData() && 
/*  844 */                     !leafRegion.hasLookedForCache() && leafRegion.isOutdatedWithOtherLayers()) {
/*  845 */                     File potentialCacheFile = this.mapSaveLoad.getCacheFile((MapRegionInfo)leafRegion, leafRegion.getCaveLayer(), false, false);
/*  846 */                     if (potentialCacheFile.exists()) {
/*  847 */                       leafRegion.setCacheFile(potentialCacheFile);
/*  848 */                       leafRegion.setLookedForCache(true);
/*      */                     } 
/*      */                   } 
/*      */                   
/*  852 */                   if (leafRegion.shouldConvertCacheToOutdatedOnFinishDim() && leafRegion.getCacheFile() != null) {
/*      */ 
/*      */                     
/*  855 */                     leafRegion.convertCacheToOutdated(this.mapSaveLoad, "might be outdated");
/*  856 */                     if (WorldMap.settings.debug) {
/*  857 */                       WorldMap.LOGGER.info(String.format("Converting cache for region %s because it might be outdated.", new Object[] { leafRegion }));
/*      */                     }
/*      */                   } 
/*      */                 } 
/*  861 */                 region.setReloadHasBeenRequested(false, "world/dim change");
/*  862 */                 region.onCurrentDimFinish(this.mapSaveLoad, this);
/*      */               } 
/*  864 */               if (shouldClearAllDimensions || (shouldClearNewDimension && reqDim == currentDim)) {
/*  865 */                 region.onDimensionClear(this);
/*      */               }
/*      */             } 
/*      */             
/*  869 */             currentDimChecked = true;
/*      */           } 
/*  871 */           if (reqDim != currentDim && shouldClearNewDimension) {
/*  872 */             for (LeveledRegion<?> region : (Iterable<LeveledRegion<?>>)reqDim.getLayeredMapRegions().getUnsyncedSet()) {
/*  873 */               region.onDimensionClear(this);
/*      */             }
/*      */           }
/*      */           
/*  877 */           if (shouldClearAllDimensions) {
/*  878 */             for (MapDimension dim : this.mapWorld.getDimensionsList()) {
/*  879 */               if (!currentDimChecked || dim != currentDim) {
/*  880 */                 for (LeveledRegion<?> region : (Iterable<LeveledRegion<?>>)dim.getLayeredMapRegions().getUnsyncedSet()) {
/*  881 */                   region.onDimensionClear(this);
/*      */                 }
/*      */               }
/*      */             } 
/*      */           }
/*  886 */           if (this.currentMapNeedsDeletion)
/*  887 */             this.mapWorld.getCurrentDimension().deleteMultiworldMapDataUnsynced(this.mapWorld.getCurrentDimension().getCurrentMultiworld()); 
/*      */         } 
/*  889 */         this.currentMapNeedsDeletion = false;
/*  890 */         if (shouldClearAllDimensions) {
/*  891 */           if (this.mapWorld.getCurrentDimensionId() != null)
/*  892 */             for (MapDimension dim : this.mapWorld.getDimensionsList()) {
/*  893 */               dim.clear();
/*      */             } 
/*  895 */           if (WorldMap.settings.debug)
/*  896 */             WorldMap.LOGGER.info("All map data cleared!"); 
/*  897 */           if (this.state == 1) {
/*  898 */             WorldMap.LOGGER.info("World map cleaned normally!");
/*  899 */             this.state = 2;
/*      */           } 
/*  901 */         } else if (shouldClearNewDimension) {
/*  902 */           this.mapWorld.getFutureDimension().clear();
/*  903 */           if (WorldMap.settings.debug) {
/*  904 */             WorldMap.LOGGER.info("Dimension map data cleared!");
/*      */           }
/*      */         } 
/*      */         
/*  908 */         if (WorldMap.settings.debug) {
/*  909 */           WorldMap.LOGGER.info("World changed!");
/*      */         }
/*  911 */         this.mapWorldUsable = this.mapWorldUsableRequest;
/*  912 */         if (this.mapWorldUsableRequest)
/*  913 */           this.mapWorld.switchToFutureUnsynced(); 
/*  914 */         this.currentWorldId = newWorldId;
/*  915 */         this.currentDimId = !this.mapWorldUsableRequest ? null : getDimensionName(this.mapWorld.getFutureDimensionId());
/*  916 */         this.currentMWId = newMWId;
/*  917 */         Path mapPath = this.mapSaveLoad.getMWSubFolder(this.currentWorldId, this.currentDimId, this.currentMWId);
/*  918 */         if (this.mapWorldUsable) {
/*  919 */           Files.createDirectories(mapPath, (FileAttribute<?>[])new FileAttribute[0]);
/*  920 */           Path mapLockPath = mapPath.resolve(".lock");
/*  921 */           int totalLockAttempts = 10;
/*  922 */           int lockAttempts = 10;
/*  923 */           while (lockAttempts-- > 0) {
/*  924 */             if (lockAttempts < 9) {
/*  925 */               WorldMap.LOGGER.info("Failed attempt to lock the current world map! Retrying in 50 ms... " + lockAttempts);
/*      */               try {
/*  927 */                 Thread.sleep(50L);
/*  928 */               } catch (InterruptedException interruptedException) {}
/*      */             } 
/*      */             
/*      */             try {
/*  932 */               FileChannel lockChannel = FileChannel.open(mapLockPath, new OpenOption[] { StandardOpenOption.APPEND, StandardOpenOption.CREATE });
/*  933 */               this.currentMapLock = lockChannel.tryLock();
/*  934 */               if (this.currentMapLock == null)
/*      */                 continue; 
/*  936 */               this.currentMapLockChannel = lockChannel;
/*      */               break;
/*  938 */             } catch (Exception e) {
/*  939 */               WorldMap.LOGGER.error("suppressed exception", e);
/*      */             } 
/*      */           } 
/*      */         } 
/*  943 */         checkFootstepsReset((class_1937)this.world, (class_1937)this.newWorld);
/*  944 */         this.mapSaveLoad.clearToLoad();
/*  945 */         this.mapSaveLoad.setNextToLoadByViewing((LeveledRegion)null);
/*  946 */         clearToRefresh();
/*  947 */         for (int i = 0; i < this.toProcessLevels.length; i++)
/*  948 */           this.toProcessLevels[i].clear(); 
/*  949 */         if (this.mapWorldUsable && !isCurrentMapLocked()) {
/*  950 */           for (LeveledRegion<?> region : (Iterable<LeveledRegion<?>>)this.mapWorld.getCurrentDimension().getLayeredMapRegions().getUnsyncedSet()) {
/*  951 */             if (region.shouldBeProcessed()) {
/*  952 */               addToProcess(region);
/*      */             }
/*      */           } 
/*      */         }
/*      */         
/*  957 */         this.mapWriter.resetPosition();
/*  958 */         this.world = this.newWorld;
/*  959 */         this.worldBlockLookup = this.newWorldBlockLookup;
/*  960 */         this.worldBlockRegistry = this.newWorldBlockRegistry;
/*  961 */         this.worldFluidRegistry = this.newWorldFluidRegistry;
/*  962 */         this.worldBiomeRegistry = this.newWorldBiomeRegistry;
/*  963 */         this.worldDimensionTypeRegistry = this.newWorldDimensionTypeRegistry;
/*  964 */         this.worldBlockTintProvider = (this.world == null) ? null : new BlockTintProvider(this.worldBiomeRegistry, this.biomeColorCalculator, this, this.brokenBlockTintCache, this.mapWriter);
/*  965 */         if (SupportMods.framedBlocks())
/*  966 */           SupportMods.supportFramedBlocks.onWorldChange(); 
/*  967 */         if (SupportMods.pac()) {
/*  968 */           SupportMods.xaeroPac.onMapChange(changedDimension);
/*  969 */           SupportMods.xaeroPac.resetDetection();
/*      */         } 
/*  971 */         this.mapWorld.onWorldChangeUnsynced(this.world);
/*  972 */         if (WorldMap.settings.debug)
/*  973 */           WorldMap.LOGGER.info("World/dimension changed to: " + this.currentWorldId + " " + this.currentDimId + " " + this.currentMWId); 
/*  974 */         this.worldDataHandler.prepareSingleplayer((class_1937)this.world, this);
/*  975 */         if (this.worldDataHandler.getWorldDir() == null && this.currentWorldId != null && this.mapWorld.getCurrentDimension().isUsingWorldSave())
/*  976 */           this.currentWorldId = this.currentDimId = null; 
/*  977 */         boolean shouldDetect = (this.mapWorldUsable && !this.mapWorld.getCurrentDimension().hasDoneRegionDetection());
/*      */         
/*  979 */         this.mapSaveLoad.setRegionDetectionComplete(!shouldDetect);
/*  980 */         popRenderPause(true, true);
/*  981 */         popWriterPause();
/*  982 */       } else if (this.newWorld != this.world) {
/*  983 */         pushRenderPause(false, true);
/*  984 */         pushWriterPause();
/*  985 */         checkFootstepsReset((class_1937)this.world, (class_1937)this.newWorld);
/*  986 */         this.world = this.newWorld;
/*  987 */         this.worldBlockLookup = this.newWorldBlockLookup;
/*  988 */         this.worldBlockRegistry = this.newWorldBlockRegistry;
/*  989 */         this.worldFluidRegistry = this.newWorldFluidRegistry;
/*  990 */         this.worldBiomeRegistry = this.newWorldBiomeRegistry;
/*  991 */         this.worldDimensionTypeRegistry = this.newWorldDimensionTypeRegistry;
/*  992 */         this.worldBlockTintProvider = (this.world == null) ? null : new BlockTintProvider(this.worldBiomeRegistry, this.biomeColorCalculator, this, this.brokenBlockTintCache, this.mapWriter);
/*  993 */         if (SupportMods.framedBlocks())
/*  994 */           SupportMods.supportFramedBlocks.onWorldChange(); 
/*  995 */         if (SupportMods.pac())
/*  996 */           SupportMods.xaeroPac.resetDetection(); 
/*  997 */         this.mapWorld.onWorldChangeUnsynced(this.world);
/*  998 */         popRenderPause(false, true);
/*  999 */         popWriterPause();
/*      */       } 
/* 1001 */       if (this.mapWorldUsable) {
/* 1002 */         this.mapWorld.getCurrentDimension().switchToFutureMultiworldWritableValueUnsynced();
/* 1003 */         this.mapWorld.switchToFutureMultiworldTypeUnsynced();
/*      */       } 
/* 1005 */       this.waitingForWorldUpdate = false;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void updateFootprints(int step) {
/* 1010 */     if (WorldMap.settings.footsteps) {
/* 1011 */       if (this.footprintsTimer > 0) {
/* 1012 */         this.footprintsTimer -= step;
/*      */       } else {
/* 1014 */         Double[] coords = { Double.valueOf(this.mainPlayerX), Double.valueOf(this.mainPlayerZ) };
/* 1015 */         synchronized (this.footprints) {
/* 1016 */           this.footprints.add(coords);
/* 1017 */           if (this.footprints.size() > 32)
/* 1018 */             this.footprints.remove(0); 
/*      */         } 
/* 1020 */         this.footprintsTimer = 20;
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public void addToRefresh(MapRegion region, boolean prepareHighlights) {
/* 1026 */     synchronized (this.toRefresh) {
/* 1027 */       if (!this.toRefresh.contains(region))
/* 1028 */         this.toRefresh.add(0, region); 
/*      */     } 
/* 1030 */     if (prepareHighlights)
/* 1031 */       this.mapRegionHighlightsPreparer.prepare(region, false); 
/*      */   }
/*      */   
/*      */   public void removeToRefresh(MapRegion region) {
/* 1035 */     synchronized (this.toRefresh) {
/* 1036 */       this.toRefresh.remove(region);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void clearToRefresh() {
/* 1041 */     synchronized (this.toRefresh) {
/* 1042 */       this.toRefresh.clear();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void handleRefresh() throws RuntimeException {
/* 1047 */     pushIsLoading();
/* 1048 */     if (!this.waitingForWorldUpdate && !this.toRefresh.isEmpty()) {
/* 1049 */       MapRegion region = this.toRefresh.get(0);
/* 1050 */       if (region.isRefreshing()) {
/*      */         boolean regionLoaded;
/* 1052 */         int globalReloadVersion = WorldMap.settings.reloadVersion;
/* 1053 */         int globalCacheHashCode = WorldMap.settings.getRegionCacheHashCode();
/* 1054 */         synchronized (region) {
/* 1055 */           regionLoaded = (region.getLoadState() == 2);
/* 1056 */           if (regionLoaded) {
/* 1057 */             region.setRecacheHasBeenRequested(true, "refresh handle");
/* 1058 */             region.setShouldCache(true, "refresh handle");
/*      */             
/* 1060 */             region.setVersion(WorldMap.globalVersion);
/* 1061 */             region.setCacheHashCode(globalCacheHashCode);
/* 1062 */             region.setReloadVersion(globalReloadVersion);
/* 1063 */             region.setHighlightsHash(region.getTargetHighlightsHash());
/*      */           } 
/*      */         } 
/* 1066 */         boolean isEmpty = true;
/* 1067 */         if (regionLoaded) {
/* 1068 */           synchronized (region) {
/* 1069 */             region.setAllCachePrepared(false);
/*      */           } 
/*      */           
/* 1072 */           boolean skipRegularRefresh = false;
/* 1073 */           int upToDateCaveStart = region.getUpToDateCaveStart();
/* 1074 */           if (region.isBeingWritten() && region.caveStartOutdated(upToDateCaveStart, WorldMap.settings.caveModeDepth)) {
/*      */             try {
/* 1076 */               getWorldDataHandler().buildRegion(region, this.worldBlockLookup, this.worldBlockRegistry, this.worldFluidRegistry, false, null);
/* 1077 */               skipRegularRefresh = true;
/* 1078 */             } catch (Throwable e) {
/* 1079 */               WorldMap.LOGGER.info("Region failed to refresh from world save: " + String.valueOf(region) + " " + region.getWorldId() + " " + region.getDimId() + " " + region.getMwId());
/*      */             } 
/*      */           }
/* 1082 */           for (int i = 0; i < 8; i++) {
/* 1083 */             for (int j = 0; j < 8; j++) {
/* 1084 */               MapTileChunk chunk = region.getChunk(i, j);
/* 1085 */               if (chunk != null) {
/* 1086 */                 if (chunk.hasHadTerrain()) {
/* 1087 */                   if (!skipRegularRefresh && chunk.getLoadState() == 2) {
/* 1088 */                     for (int tileX = 0; tileX < 4; tileX++) {
/* 1089 */                       for (int tileZ = 0; tileZ < 4; tileZ++) {
/* 1090 */                         region.pushWriterPause();
/* 1091 */                         MapTile tile = chunk.getTile(tileX, tileZ);
/* 1092 */                         if (tile != null && tile.isLoaded())
/* 1093 */                           for (int o = 0; o < 16; o++) {
/* 1094 */                             MapBlock[] column = tile.getBlockColumn(o);
/* 1095 */                             for (int p = 0; p < 16; p++)
/* 1096 */                               column[p].setSlopeUnknown(true); 
/*      */                           }  
/* 1098 */                         chunk.setTile(tileX, tileZ, tile, this.blockStateShortShapeCache);
/* 1099 */                         region.popWriterPause();
/*      */                       } 
/* 1101 */                     }  chunk.setToUpdateBuffers(true);
/*      */                   } 
/*      */                 } else {
/* 1104 */                   region.pushWriterPause();
/* 1105 */                   if (!chunk.hasHadTerrain() && !chunk.wasChanged() && !chunk.getToUpdateBuffers()) {
/* 1106 */                     region.uncountTextureBiomes((RegionTexture)chunk.getLeafTexture());
/* 1107 */                     chunk.getLeafTexture().resetBiomes();
/* 1108 */                     if (chunk.hasHighlightsIfUndiscovered()) {
/* 1109 */                       chunk.getLeafTexture().requestHighlightOnlyUpload();
/*      */                     } else {
/* 1111 */                       region.setChunk(i, j, null);
/* 1112 */                       chunk.getLeafTexture().deleteTexturesAndBuffers();
/*      */                     } 
/*      */                   } 
/* 1115 */                   region.popWriterPause();
/*      */                 } 
/* 1117 */                 isEmpty = false;
/*      */               } 
/*      */             } 
/* 1120 */           }  if (WorldMap.settings.debug)
/* 1121 */             WorldMap.LOGGER.info("Region refreshed: " + String.valueOf(region) + " " + String.valueOf(region) + " " + this.mapWriter.getUpdateCounter()); 
/*      */         } 
/* 1123 */         synchronized (region) {
/* 1124 */           region.setRefreshing(false);
/* 1125 */           if (isEmpty) {
/* 1126 */             region.setShouldCache(false, "refresh handle");
/* 1127 */             region.setRecacheHasBeenRequested(false, "refresh handle");
/*      */           } 
/*      */         } 
/* 1130 */         if (region.isResaving())
/* 1131 */           region.setLastSaveTime(-60000L); 
/*      */       } else {
/* 1133 */         throw new RuntimeException(String.format("Trying to refresh region %s, which is not marked as being refreshed!", new Object[] { region }));
/* 1134 */       }  removeToRefresh(region);
/*      */     } 
/* 1136 */     popIsLoading();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean regionExists(int caveLayer, int x, int z) {
/* 1141 */     return (regionDetectionExists(caveLayer, x, z) || this.mapWorld.getCurrentDimension().getHighlightHandler().shouldApplyRegionHighlights(x, z, false));
/*      */   }
/*      */   
/*      */   public boolean regionDetectionExists(int caveLayer, int x, int z) {
/* 1145 */     if (!this.mapSaveLoad.isRegionDetectionComplete())
/* 1146 */       return false; 
/* 1147 */     return this.mapWorld.getCurrentDimension().getLayeredMapRegions().getLayer(caveLayer).regionDetectionExists(x, z);
/*      */   }
/*      */   
/*      */   public void removeMapRegion(LeveledRegion<?> region) {
/* 1151 */     MapDimension regionDim = region.getDim();
/* 1152 */     LayeredRegionManager regions = regionDim.getLayeredMapRegions();
/* 1153 */     if (region.getLevel() == 0) {
/* 1154 */       regions.remove(region.getCaveLayer(), region.getRegionX(), region.getRegionZ(), region.getLevel());
/* 1155 */       regions.removeListRegion(region);
/*      */     } 
/* 1157 */     regions.removeLoadedRegion(region);
/* 1158 */     removeToProcess(region);
/*      */   }
/*      */   
/*      */   public LeveledRegion<?> getLeveledRegion(int caveLayer, int leveledRegX, int leveledRegZ, int level) {
/* 1162 */     MapDimension mapDimension = this.mapWorld.getCurrentDimension();
/* 1163 */     LayeredRegionManager regions = mapDimension.getLayeredMapRegions();
/* 1164 */     return regions.get(caveLayer, leveledRegX, leveledRegZ, level);
/*      */   }
/*      */   
/*      */   public void initMinimapRender(int flooredMapCameraX, int flooredMapCameraZ) {
/* 1168 */     this.minimapRenderListener.init(this, flooredMapCameraX, flooredMapCameraZ);
/*      */   }
/*      */   
/*      */   public void beforeMinimapRegionRender(MapRegion region) {
/* 1172 */     this.minimapRenderListener.beforeMinimapRender(region);
/*      */   }
/*      */   
/*      */   public void finalizeMinimapRender() {
/* 1176 */     this.minimapRenderListener.finalize(this);
/*      */   }
/*      */   
/*      */   public MapRegion getLeafMapRegion(int caveLayer, int regX, int regZ, boolean create) {
/* 1180 */     if (!this.mapSaveLoad.isRegionDetectionComplete())
/* 1181 */       return null; 
/* 1182 */     MapDimension mapDimension = this.mapWorld.getCurrentDimension();
/* 1183 */     LayeredRegionManager regions = mapDimension.getLayeredMapRegions();
/* 1184 */     MapRegion region = regions.getLeaf(caveLayer, regX, regZ);
/* 1185 */     if (region == null)
/* 1186 */       if (create) {
/* 1187 */         if (!class_310.method_1551().method_18854())
/* 1188 */           throw new IllegalAccessError(); 
/* 1189 */         region = new MapRegion(this.currentWorldId, this.currentDimId, this.currentMWId, mapDimension, regX, regZ, caveLayer, getGlobalVersion(), !mapDimension.isUsingWorldSave(), this.worldBiomeRegistry);
/* 1190 */         MapLayer mapLayer = regions.getLayer(caveLayer);
/* 1191 */         region.updateCaveMode();
/* 1192 */         RegionDetection regionDetection = mapLayer.getRegionDetection(regX, regZ);
/* 1193 */         if (regionDetection != null) {
/* 1194 */           regionDetection.transferInfoTo(region);
/* 1195 */           mapLayer.removeRegionDetection(regX, regZ);
/* 1196 */         } else if (mapLayer.getCompleteRegionDetection(regX, regZ) == null) {
/*      */ 
/*      */           
/* 1199 */           RegionDetection perpetualRegionDetection = new RegionDetection(region.getWorldId(), region.getDimId(), region.getMwId(), region.getRegionX(), region.getRegionZ(), region.getRegionFile(), getGlobalVersion(), true);
/* 1200 */           mapLayer.tryAddingToCompleteRegionDetection(perpetualRegionDetection);
/* 1201 */           if (!region.isNormalMapData())
/* 1202 */             mapLayer.removeRegionDetection(regX, regZ); 
/*      */         } 
/* 1204 */         if (!region.hasHadTerrain()) {
/* 1205 */           regions.getLayer(caveLayer).getRegionHighlightExistenceTracker().stopTracking(regX, regZ);
/* 1206 */           region.setVersion(getGlobalVersion());
/* 1207 */           region.setCacheHashCode(WorldMap.settings.getRegionCacheHashCode());
/* 1208 */           region.setReloadVersion(WorldMap.settings.reloadVersion);
/*      */         } 
/* 1210 */         regions.putLeaf(regX, regZ, region);
/* 1211 */         regions.addListRegion((LeveledRegion)region);
/* 1212 */         if (regionDetection != null)
/* 1213 */           regionDetection.transferInfoPostAddTo(region, this); 
/*      */       } else {
/* 1215 */         return null;
/*      */       }  
/* 1217 */     return region;
/*      */   }
/*      */   
/*      */   public MapRegion getMinimapMapRegion(int regX, int regZ) {
/* 1221 */     int renderedCaveLayer = this.minimapRenderListener.getRenderedCaveLayer();
/* 1222 */     return getLeafMapRegion(renderedCaveLayer, regX, regZ, regionExists(renderedCaveLayer, regX, regZ));
/*      */   }
/*      */   
/*      */   public MapTileChunk getMapChunk(int caveLayer, int chunkX, int chunkZ) {
/* 1226 */     int regionX = chunkX >> 3;
/* 1227 */     int regionZ = chunkZ >> 3;
/* 1228 */     MapRegion region = getLeafMapRegion(caveLayer, regionX, regionZ, false);
/* 1229 */     if (region == null)
/* 1230 */       return null; 
/* 1231 */     int localChunkX = chunkX & 0x7;
/* 1232 */     int localChunkZ = chunkZ & 0x7;
/* 1233 */     return region.getChunk(localChunkX, localChunkZ);
/*      */   }
/*      */   
/*      */   public MapTile getMapTile(int caveLayer, int x, int z) {
/* 1237 */     MapTileChunk tileChunk = getMapChunk(caveLayer, x >> 2, z >> 2);
/* 1238 */     if (tileChunk == null)
/* 1239 */       return null; 
/* 1240 */     int tileX = x & 0x3;
/* 1241 */     int tileZ = z & 0x3;
/* 1242 */     return tileChunk.getTile(tileX, tileZ);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateWorldSpawn(class_2338 newSpawn, class_638 world) {
/* 1248 */     class_5321<class_1937> dimId = world.method_27983();
/* 1249 */     WorldMapClientWorldData worldData = WorldMapClientWorldDataHelper.getWorldData(world);
/* 1250 */     worldData.latestSpawn = newSpawn;
/* 1251 */     if (WorldMap.settings.debug)
/* 1252 */       WorldMap.LOGGER.info("Updated spawn for dimension " + String.valueOf(dimId) + " " + String.valueOf(newSpawn)); 
/* 1253 */     this.spawnToRestore = newSpawn;
/* 1254 */     if (world == this.mainWorld) {
/* 1255 */       this.mainWorldChangedTime = -1L;
/*      */       
/* 1257 */       if (WorldMap.settings.debug) {
/* 1258 */         WorldMap.LOGGER.info("Done waiting for main spawn.");
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1266 */     checkForWorldUpdate();
/*      */   }
/*      */   
/*      */   public void onServerLevelId(int serverLevelId) {
/* 1270 */     WorldMapClientWorldData worldData = WorldMapClientWorldDataHelper.getCurrentWorldData();
/* 1271 */     worldData.serverLevelId = Integer.valueOf(serverLevelId);
/* 1272 */     if (WorldMap.settings.debug)
/* 1273 */       WorldMap.LOGGER.info("Updated server level id " + serverLevelId); 
/* 1274 */     checkForWorldUpdate();
/*      */   }
/*      */   
/*      */   public void onWorldUnload() {
/* 1278 */     if (this.mainWorldUnloaded)
/*      */       return; 
/* 1280 */     if (WorldMap.settings.debug)
/* 1281 */       WorldMap.LOGGER.info("Changing worlds, pausing the world map..."); 
/* 1282 */     this.mainWorldUnloaded = true;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1287 */     this.mapWorld.clearAllCachedHighlightHashes();
/* 1288 */     this.mainWorldChangedTime = -1L;
/* 1289 */     changeWorld(null, null, null, null, null, null);
/*      */   }
/*      */ 
/*      */   
/*      */   public void onClientTickStart() throws RuntimeException {
/* 1294 */     if (this.mainWorld != null && this.spawnToRestore != null && this.mainWorldChangedTime != -1L && System.currentTimeMillis() - this.mainWorldChangedTime >= 3000L) {
/* 1295 */       if (WorldMap.settings.debug)
/* 1296 */         WorldMap.LOGGER.info("SPAWN SET TIME OUT"); 
/* 1297 */       updateWorldSpawn(this.spawnToRestore, this.mainWorld);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void updateRenderStartTime() {
/* 1302 */     if (this.renderStartTime == -1L)
/*      */     {
/* 1304 */       this.renderStartTime = System.nanoTime();
/*      */     }
/*      */   }
/*      */   
/*      */   public void pushWriterPause() {
/* 1309 */     synchronized (this.renderThreadPauseSync) {
/* 1310 */       this.pauseWriting++;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void popWriterPause() {
/* 1315 */     synchronized (this.renderThreadPauseSync) {
/* 1316 */       this.pauseWriting--;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void pushRenderPause(boolean rendering, boolean uploading) {
/* 1321 */     synchronized (this.renderThreadPauseSync) {
/* 1322 */       if (rendering)
/* 1323 */         this.pauseRendering++; 
/* 1324 */       if (uploading)
/* 1325 */         this.pauseUploading++; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void popRenderPause(boolean rendering, boolean uploading) {
/* 1330 */     synchronized (this.renderThreadPauseSync) {
/* 1331 */       if (rendering)
/* 1332 */         this.pauseRendering--; 
/* 1333 */       if (uploading)
/* 1334 */         this.pauseUploading--; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void pushIsLoading() {
/* 1339 */     synchronized (this.loadingSync) {
/* 1340 */       this.isLoading = true;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void popIsLoading() {
/* 1345 */     synchronized (this.loadingSync) {
/* 1346 */       this.isLoading = false;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void pushUIPause() {
/* 1351 */     synchronized (this.uiPauseSync) {
/* 1352 */       this.isUIPaused = true;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void popUIPause() {
/* 1357 */     synchronized (this.uiPauseSync) {
/* 1358 */       this.isUIPaused = false;
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean isUIPaused() {
/* 1363 */     return this.isUIPaused;
/*      */   }
/*      */   
/*      */   public boolean isWritingPaused() {
/* 1367 */     return (this.pauseWriting > 0);
/*      */   }
/*      */   
/*      */   public boolean isRenderingPaused() {
/* 1371 */     return (this.pauseRendering > 0);
/*      */   }
/*      */   
/*      */   public boolean isUploadingPaused() {
/* 1375 */     return (this.pauseUploading > 0);
/*      */   }
/*      */   
/*      */   public boolean isProcessingPaused() {
/* 1379 */     return (this.pauseProcessing > 0);
/*      */   }
/*      */   
/*      */   public boolean isProcessed(LeveledRegion<?> region) {
/* 1383 */     ArrayList<LeveledRegion<?>> toProcess = this.toProcessLevels[region.getLevel()];
/* 1384 */     synchronized (toProcess) {
/* 1385 */       return toProcess.contains(region);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void addToProcess(LeveledRegion<?> region) {
/* 1390 */     ArrayList<LeveledRegion<?>> toProcess = this.toProcessLevels[region.getLevel()];
/* 1391 */     synchronized (toProcess) {
/* 1392 */       toProcess.add(region);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void removeToProcess(LeveledRegion<?> region) {
/* 1397 */     ArrayList<LeveledRegion<?>> toProcess = this.toProcessLevels[region.getLevel()];
/* 1398 */     synchronized (toProcess) {
/* 1399 */       toProcess.remove(region);
/*      */     } 
/*      */   }
/*      */   
/*      */   public int getProcessedCount() {
/* 1404 */     int total = 0;
/* 1405 */     for (int i = 0; i < this.toProcessLevels.length; i++)
/* 1406 */       total += this.toProcessLevels[i].size(); 
/* 1407 */     return total;
/*      */   }
/*      */   
/*      */   public int getAffectingLoadingFrequencyCount() {
/* 1411 */     int total = 0;
/* 1412 */     for (int i = 0; i < this.toProcessLevels.length; i++) {
/* 1413 */       ArrayList<LeveledRegion<?>> processed = this.toProcessLevels[i];
/* 1414 */       for (int j = 0; j < processed.size(); j++) {
/* 1415 */         synchronized (processed) {
/* 1416 */           if (j >= processed.size())
/*      */             break; 
/* 1418 */           if (((LeveledRegion)processed.get(j)).shouldAffectLoadingRequestFrequency())
/* 1419 */             total++; 
/*      */         } 
/*      */       } 
/*      */     } 
/* 1423 */     return total;
/*      */   }
/*      */   
/*      */   public MapSaveLoad getMapSaveLoad() {
/* 1427 */     return this.mapSaveLoad;
/*      */   }
/*      */   
/*      */   public class_638 getWorld() {
/* 1431 */     return this.world;
/*      */   }
/*      */   
/*      */   public class_638 getNewWorld() {
/* 1435 */     return this.newWorld;
/*      */   }
/*      */   
/*      */   public String getCurrentWorldId() {
/* 1439 */     return this.currentWorldId;
/*      */   }
/*      */   
/*      */   public String getCurrentDimId() {
/* 1443 */     return this.currentDimId;
/*      */   }
/*      */   
/*      */   public String getCurrentMWId() {
/* 1447 */     return this.currentMWId;
/*      */   }
/*      */   
/*      */   public MapWriter getMapWriter() {
/* 1451 */     return this.mapWriter;
/*      */   }
/*      */   
/*      */   public MapLimiter getMapLimiter() {
/* 1455 */     return this.mapLimiter;
/*      */   }
/*      */   
/*      */   public ArrayList<Double[]> getFootprints() {
/* 1459 */     return this.footprints;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBufferDeallocator getBufferDeallocator() {
/* 1467 */     return this.bufferDeallocator;
/*      */   }
/*      */   
/*      */   public MapTilePool getTilePool() {
/* 1471 */     return this.tilePool;
/*      */   }
/*      */   
/*      */   public OverlayManager getOverlayManager() {
/* 1475 */     return this.overlayManager;
/*      */   }
/*      */   
/*      */   public int getGlobalVersion() {
/* 1479 */     return WorldMap.globalVersion;
/*      */   }
/*      */   
/*      */   public void setGlobalVersion(int globalVersion) {
/* 1483 */     WorldMap.globalVersion = globalVersion;
/*      */   }
/*      */   
/*      */   public long getRenderStartTime() {
/* 1487 */     return this.renderStartTime;
/*      */   }
/*      */   
/*      */   public void resetRenderStartTime() {
/* 1491 */     this.renderStartTime = -1L;
/*      */   }
/*      */   
/*      */   public Runnable getRenderStartTimeUpdater() {
/* 1495 */     return this.renderStartTimeUpdaterRunnable;
/*      */   }
/*      */   
/*      */   public boolean isWaitingForWorldUpdate() {
/* 1499 */     return this.waitingForWorldUpdate;
/*      */   }
/*      */   
/*      */   public WorldDataHandler getWorldDataHandler() {
/* 1503 */     return this.worldDataHandler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMainValues() {
/* 1511 */     synchronized (this.mainStuffSync) {
/* 1512 */       class_746 class_746; class_1297 player = class_310.method_1551().method_1560();
/* 1513 */       if (player == null)
/* 1514 */         class_746 = (class_310.method_1551()).field_1724; 
/* 1515 */       if (class_746 != null) {
/* 1516 */         class_638 worldToChangeTo = (ignoreWorld(class_746.method_73183()) || !(class_746.method_73183() instanceof class_638)) ? this.mainWorld : (class_638)class_746.method_73183();
/* 1517 */         boolean worldChanging = (worldToChangeTo != this.mainWorld);
/* 1518 */         if (worldChanging) {
/* 1519 */           this.mainWorldChangedTime = -1L;
/* 1520 */           if (this.spawnToRestore != null) {
/* 1521 */             WorldMapClientWorldData worldData = WorldMapClientWorldDataHelper.getWorldData(worldToChangeTo);
/* 1522 */             if (worldData.latestSpawn == null)
/* 1523 */               this.mainWorldChangedTime = System.currentTimeMillis(); 
/*      */           } 
/* 1525 */           this.mainWorldUnloaded = false;
/*      */           
/* 1527 */           this.mainWorldBlockLookup = (worldToChangeTo == null) ? null : worldToChangeTo.method_45448(class_7924.field_41254);
/* 1528 */           this.mainWorldBlockRegistry = (worldToChangeTo == null) ? null : worldToChangeTo.method_30349().method_30530(class_7924.field_41254);
/* 1529 */           this.mainWorldFluidRegistry = (worldToChangeTo == null) ? null : worldToChangeTo.method_30349().method_30530(class_7924.field_41270);
/* 1530 */           this.mainWorldBiomeRegistry = (worldToChangeTo == null) ? null : worldToChangeTo.method_30349().method_30530(class_7924.field_41236);
/* 1531 */           this.mainWorldDimensionTypeRegistry = (worldToChangeTo == null) ? null : worldToChangeTo.method_30349().method_30530(class_7924.field_41241);
/*      */         } 
/* 1533 */         this.mainWorld = worldToChangeTo;
/* 1534 */         this.mainPlayerX = class_746.method_23317();
/* 1535 */         this.mainPlayerY = class_746.method_23318();
/* 1536 */         this.mainPlayerZ = class_746.method_23321();
/* 1537 */         if (worldChanging)
/* 1538 */           checkForWorldUpdate(); 
/*      */       } else {
/* 1540 */         if (this.mainWorld != null && !this.mainWorldUnloaded)
/* 1541 */           onWorldUnload(); 
/* 1542 */         this.mainWorld = null;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public float getBrightness() {
/* 1548 */     return getBrightness(WorldMap.settings.lighting);
/*      */   }
/*      */   
/*      */   public float getBrightness(boolean lighting) {
/* 1552 */     return getBrightness(this.currentCaveLayer, this.world, lighting);
/*      */   }
/*      */   public float getBrightness(int layer, class_638 world, boolean lighting) {
/*      */     float sunBrightness;
/* 1556 */     if (world == null || world != this.world) {
/* 1557 */       return 1.0F;
/*      */     }
/* 1559 */     MapDimension dim = this.mapWorld.getCurrentDimension();
/* 1560 */     class_2874 dimType = dim.getDimensionType(this.worldDimensionTypeRegistry);
/* 1561 */     if (layer == Integer.MAX_VALUE || (dimType != null && !dimType.comp_642())) {
/* 1562 */       if (lighting)
/*      */       
/* 1564 */       { boolean isEndLike = (dimType != null && (dimType.comp_656() >= 0.25F || dim.getDimensionEffects(this.worldDimensionTypeRegistry).method_73235()));
/* 1565 */         if (isEndLike)
/* 1566 */           return 1.0F; 
/* 1567 */         sunBrightness = (dim.getSkyDarken(1.0F, world, this.worldDimensionTypeRegistry) - 0.2F) / 0.8F; }
/*      */       else
/* 1569 */       { return 1.0F; } 
/* 1570 */     } else if (lighting) {
/* 1571 */       sunBrightness = 0.0F;
/*      */     } else {
/* 1573 */       return 1.0F;
/* 1574 */     }  float ambient = getAmbientBrightness(dimType);
/* 1575 */     return ambient + (1.0F - ambient) * class_3532.method_15363(sunBrightness, 0.0F, 1.0F);
/*      */   }
/*      */   
/*      */   public float getAmbientBrightness(class_2874 dimType) {
/* 1579 */     float result = 0.375F + ((dimType == null) ? 0.0F : dimType.comp_656());
/* 1580 */     if (result > 1.0F)
/* 1581 */       result = 1.0F; 
/* 1582 */     return result;
/*      */   }
/*      */   
/*      */   public static boolean isWorldRealms(String world) {
/* 1586 */     return world.startsWith("Realms_");
/*      */   }
/*      */   
/*      */   public static boolean isWorldMultiplayer(boolean realms, String world) {
/* 1590 */     return (realms || world.startsWith("Multiplayer_"));
/*      */   }
/*      */   
/*      */   public MapWorld getMapWorld() {
/* 1594 */     return this.mapWorld;
/*      */   }
/*      */   
/*      */   public boolean isCurrentMultiworldWritable() {
/* 1598 */     return (this.mapWorldUsable && (this.mapWorld.getCurrentDimension()).currentMultiworldWritable);
/*      */   }
/*      */   
/*      */   public String getCurrentDimension() {
/* 1602 */     return "placeholder";
/*      */   }
/*      */   
/*      */   public void requestCurrentMapDeletion() {
/* 1606 */     if (this.currentMapNeedsDeletion)
/* 1607 */       throw new RuntimeException("Requesting map deletion at a weird time!"); 
/* 1608 */     this.currentMapNeedsDeletion = true;
/*      */   }
/*      */   
/*      */   public boolean isFinalizing() {
/* 1612 */     return this.finalizing;
/*      */   }
/*      */   
/*      */   public void stop() {
/* 1616 */     this.finalizing = true;
/* 1617 */     WorldMap.mapRunner.addTask(new MapRunnerTask()
/*      */         {
/*      */           public void run(MapProcessor doNotUse) {
/* 1620 */             if (MapProcessor.this.state == 0) {
/* 1621 */               MapProcessor.this.state = 1;
/* 1622 */               if (!MapProcessor.this.mapWorldUsable) {
/* 1623 */                 MapProcessor.this.forceClean();
/*      */               } else {
/* 1625 */                 MapProcessor.this.changeWorld(null, null, null, null, null, null);
/*      */               } 
/*      */             } 
/*      */           }
/*      */         });
/*      */   }
/*      */   private synchronized void forceClean() {
/* 1632 */     pushRenderPause(true, true);
/* 1633 */     pushWriterPause();
/* 1634 */     if (this.mapWorld != null)
/* 1635 */       for (MapDimension dim : this.mapWorld.getDimensionsList()) {
/* 1636 */         for (LeveledRegion<?> region : (Iterable<LeveledRegion<?>>)dim.getLayeredMapRegions().getUnsyncedSet())
/* 1637 */           region.onDimensionClear(this); 
/* 1638 */       }   popRenderPause(true, true);
/* 1639 */     popWriterPause();
/* 1640 */     if (this.currentMapLock != null) {
/* 1641 */       if (this.mapLockToRelease != null)
/* 1642 */         releaseLocksIfNeeded(); 
/* 1643 */       this.mapLockToRelease = this.currentMapLock;
/* 1644 */       this.mapLockChannelToClose = this.currentMapLockChannel;
/* 1645 */       releaseLocksIfNeeded();
/*      */     } 
/* 1647 */     this.state = 2;
/* 1648 */     WorldMap.LOGGER.info("World map force-cleaned!");
/*      */   }
/*      */   
/*      */   public boolean isMapWorldUsable() {
/* 1652 */     return this.mapWorldUsable;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private Object getAutoIdBase(class_638 world) {
/* 1658 */     return hasServerLevelId() ? (WorldMapClientWorldDataHelper.getCurrentWorldData()).serverLevelId : (WorldMapClientWorldDataHelper.getWorldData(world)).latestSpawn;
/*      */   }
/*      */   
/*      */   private Object getUsedAutoIdBase(class_638 world) {
/* 1662 */     WorldMapClientWorldData worldData = WorldMapClientWorldDataHelper.getWorldData(world);
/* 1663 */     return hasServerLevelId() ? (WorldMapClientWorldDataHelper.getCurrentWorldData()).usedServerLevelId : worldData.usedSpawn;
/*      */   }
/*      */   
/*      */   private void setUsedAutoIdBase(class_638 world, Object autoIdBase) {
/* 1667 */     WorldMapClientWorldData worldData = WorldMapClientWorldDataHelper.getWorldData(world);
/* 1668 */     if (hasServerLevelId()) {
/* 1669 */       (WorldMapClientWorldDataHelper.getCurrentWorldData()).usedServerLevelId = (Integer)autoIdBase;
/*      */     } else {
/* 1671 */       worldData.usedSpawn = (class_2338)autoIdBase;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void removeUsedAutoIdBase(class_638 world) {
/* 1676 */     WorldMapClientWorldData worldData = WorldMapClientWorldDataHelper.getWorldData(world);
/* 1677 */     if (hasServerLevelId()) {
/* 1678 */       (WorldMapClientWorldDataHelper.getCurrentWorldData()).usedServerLevelId = null;
/*      */     } else {
/* 1680 */       worldData.usedSpawn = null;
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean hasServerLevelId() {
/* 1685 */     WorldMapClientWorldData worldData = WorldMapClientWorldDataHelper.getCurrentWorldData();
/* 1686 */     if (worldData == null)
/* 1687 */       return false; 
/* 1688 */     return (worldData.serverLevelId != null && !this.mapWorld.isIgnoreServerLevelId());
/*      */   }
/*      */   
/*      */   public boolean isEqual(String worldId, String dimId, String mwId) {
/* 1692 */     return (worldId.equals(this.currentWorldId) && dimId
/* 1693 */       .equals(this.currentDimId) && (mwId == this.currentMWId || (mwId != null && mwId
/* 1694 */       .equals(this.currentMWId))));
/*      */   }
/*      */   
/*      */   public boolean isFinished() {
/* 1698 */     return (this.state == 3);
/*      */   }
/*      */   
/*      */   public MultiTextureRenderTypeRendererProvider getMultiTextureRenderTypeRenderers() {
/* 1702 */     return this.multiTextureRenderTypeRenderers;
/*      */   }
/*      */   
/*      */   public CustomVertexConsumers getCvc() {
/* 1706 */     return this.cvc;
/*      */   }
/*      */   
/*      */   public boolean isCurrentMapLocked() {
/* 1710 */     return (this.currentMapLock == null);
/*      */   }
/*      */   
/*      */   private void releaseLocksIfNeeded() {
/* 1714 */     if (this.mapLockToRelease != null) {
/* 1715 */       int lockAttempts = 10;
/* 1716 */       while (lockAttempts-- > 0) {
/*      */         try {
/* 1718 */           if (this.mapLockToRelease.isValid())
/* 1719 */             this.mapLockToRelease.release(); 
/* 1720 */           this.mapLockChannelToClose.close();
/*      */           break;
/* 1722 */         } catch (Exception e) {
/* 1723 */           WorldMap.LOGGER.error("Failed attempt to release the lock for the world map! Retrying in 50 ms... " + lockAttempts, e);
/*      */           
/*      */           try {
/* 1726 */             Thread.sleep(50L);
/* 1727 */           } catch (InterruptedException interruptedException) {}
/*      */         } 
/*      */       } 
/* 1730 */       this.mapLockToRelease = null;
/* 1731 */       this.mapLockChannelToClose = null;
/*      */     } 
/*      */   }
/*      */   
/*      */   private int getCaveLayer(int caveStart) {
/* 1736 */     if (caveStart == Integer.MAX_VALUE || caveStart == Integer.MIN_VALUE)
/* 1737 */       return caveStart; 
/* 1738 */     return caveStart >> 4;
/*      */   }
/*      */   
/*      */   public int getCurrentCaveLayer() {
/* 1742 */     return this.currentCaveLayer;
/*      */   }
/*      */   
/*      */   public BlockStateShortShapeCache getBlockStateShortShapeCache() {
/* 1746 */     return this.blockStateShortShapeCache;
/*      */   }
/*      */   
/*      */   public BlockTintProvider getWorldBlockTintProvider() {
/* 1750 */     return this.worldBlockTintProvider;
/*      */   }
/*      */   
/*      */   public HighlighterRegistry getHighlighterRegistry() {
/* 1754 */     return this.highlighterRegistry;
/*      */   }
/*      */   
/*      */   public MapRegionHighlightsPreparer getMapRegionHighlightsPreparer() {
/* 1758 */     return this.mapRegionHighlightsPreparer;
/*      */   }
/*      */   
/*      */   public MessageBox getMessageBox() {
/* 1762 */     return this.messageBox;
/*      */   }
/*      */   
/*      */   public MessageBoxRenderer getMessageBoxRenderer() {
/* 1766 */     return this.messageBoxRenderer;
/*      */   }
/*      */   
/*      */   public class_2378<class_2248> getWorldBlockRegistry() {
/* 1770 */     return this.worldBlockRegistry;
/*      */   }
/*      */   
/*      */   public class_7225<class_2248> getWorldBlockLookup() {
/* 1774 */     return this.worldBlockLookup;
/*      */   }
/*      */   
/*      */   public boolean isConsideringNetherFairPlay() {
/* 1778 */     return this.consideringNetherFairPlayMessage;
/*      */   }
/*      */   
/*      */   public void setConsideringNetherFairPlayMessage(boolean consideringNetherFairPlay) {
/* 1782 */     this.consideringNetherFairPlayMessage = consideringNetherFairPlay;
/*      */   }
/*      */   
/*      */   public BiomeColorCalculator getBiomeColorCalculator() {
/* 1786 */     return this.biomeColorCalculator;
/*      */   }
/*      */   
/*      */   public ClientSyncedTrackedPlayerManager getClientSyncedTrackedPlayerManager() {
/* 1790 */     return this.clientSyncedTrackedPlayerManager;
/*      */   }
/*      */   
/*      */   public boolean serverHasMod() {
/* 1794 */     WorldMapClientWorldData worldData = WorldMapClientWorldDataHelper.getCurrentWorldData();
/* 1795 */     return (worldData != null && worldData.serverLevelId != null);
/*      */   }
/*      */   
/*      */   public void setServerModNetworkVersion(int networkVersion) {
/* 1799 */     WorldMapClientWorldData worldData = WorldMapClientWorldDataHelper.getCurrentWorldData();
/* 1800 */     if (worldData == null)
/*      */       return; 
/* 1802 */     worldData.setServerModNetworkVersion(networkVersion);
/*      */   }
/*      */   
/*      */   public int getServerModNetworkVersion() {
/* 1806 */     WorldMapClientWorldData worldData = WorldMapClientWorldDataHelper.getCurrentWorldData();
/* 1807 */     if (worldData == null)
/* 1808 */       return 0; 
/* 1809 */     return worldData.getServerModNetworkVersion();
/*      */   }
/*      */   
/*      */   public class_2378<class_2874> getWorldDimensionTypeRegistry() {
/* 1813 */     return this.worldDimensionTypeRegistry;
/*      */   }
/*      */   
/*      */   private void checkFootstepsReset(class_1937 oldWorld, class_1937 newWorld) {
/* 1817 */     class_5321<class_1937> oldDimId = (oldWorld == null) ? null : oldWorld.method_27983();
/* 1818 */     class_5321<class_1937> newDimId = (newWorld == null) ? null : newWorld.method_27983();
/* 1819 */     if (oldDimId != newDimId)
/* 1820 */       this.footprints.clear(); 
/*      */   }
/*      */   
/*      */   private void fixRootFolder(String mainId, class_634 connection) {
/* 1824 */     for (int format = 3; format >= 1; format--)
/* 1825 */       fixRootFolder(mainId, getMainId(format, connection)); 
/*      */   }
/*      */   
/*      */   private void fixRootFolder(String mainId, String oldMainId) {
/* 1829 */     if (!mainId.equals(oldMainId)) {
/*      */       Path oldFolder;
/*      */       try {
/* 1832 */         oldFolder = WorldMap.saveFolder.toPath().resolve(oldMainId);
/* 1833 */       } catch (InvalidPathException ipe) {
/*      */         return;
/*      */       } 
/*      */       
/* 1837 */       if (Files.exists(oldFolder, new java.nio.file.LinkOption[0])) {
/* 1838 */         Path fixedFolder = WorldMap.saveFolder.toPath().resolve(mainId);
/* 1839 */         if (!Files.exists(fixedFolder, new java.nio.file.LinkOption[0]))
/*      */           try {
/* 1841 */             Files.move(oldFolder, fixedFolder, new java.nio.file.CopyOption[0]);
/* 1842 */           } catch (IOException e) {
/* 1843 */             throw new RuntimeException("failed to auto-restore old world map folder", e);
/*      */           }  
/*      */       } 
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\MapProcessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */