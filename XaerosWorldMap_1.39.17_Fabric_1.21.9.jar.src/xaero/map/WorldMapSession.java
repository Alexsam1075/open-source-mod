/*     */ package xaero.map;
/*     */ 
/*     */ import java.util.HashSet;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_634;
/*     */ import net.minecraft.class_746;
/*     */ import xaero.map.biome.BiomeColorCalculator;
/*     */ import xaero.map.biome.BiomeGetter;
/*     */ import xaero.map.cache.BlockStateShortShapeCache;
/*     */ import xaero.map.cache.BrokenBlockTintCache;
/*     */ import xaero.map.controls.ControlsHandler;
/*     */ import xaero.map.core.IWorldMapClientPlayNetHandler;
/*     */ import xaero.map.core.XaeroWorldMapCore;
/*     */ import xaero.map.executor.Executor;
/*     */ import xaero.map.file.MapSaveLoad;
/*     */ import xaero.map.file.worldsave.WorldDataHandler;
/*     */ import xaero.map.file.worldsave.WorldDataReader;
/*     */ import xaero.map.file.worldsave.biome.WorldDataBiomeManager;
/*     */ import xaero.map.graphics.TextureUploader;
/*     */ import xaero.map.graphics.renderer.multitexture.MultiTextureRenderTypeRendererProvider;
/*     */ import xaero.map.gui.message.MessageBox;
/*     */ import xaero.map.gui.message.render.MessageBoxRenderer;
/*     */ import xaero.map.highlight.HighlighterRegistry;
/*     */ import xaero.map.highlight.MapRegionHighlightsPreparer;
/*     */ import xaero.map.misc.CaveStartCalculator;
/*     */ import xaero.map.mods.SupportMods;
/*     */ import xaero.map.radar.tracker.synced.ClientSyncedTrackedPlayerManager;
/*     */ 
/*     */ 
/*     */ public class WorldMapSession
/*     */ {
/*     */   private ControlsHandler controlsHandler;
/*     */   private MapProcessor mapProcessor;
/*     */   private MapWriter mapWriter;
/*     */   private MultiTextureRenderTypeRendererProvider multiTextureRenderTypeRenderers;
/*     */   private boolean usable;
/*     */   
/*     */   public void init(class_634 connection, long biomeZoomSeed) throws NoSuchFieldException {
/*  39 */     BlockStateShortShapeCache blockStateShortShapeCache = new BlockStateShortShapeCache();
/*  40 */     MapSaveLoad mapSaveLoad = new MapSaveLoad(WorldMap.overlayManager, WorldMap.pngExporter, WorldMap.oldFormatSupport, blockStateShortShapeCache);
/*  41 */     TextureUploader textureUploader = new TextureUploader(WorldMap.normalTextureUploadPool, WorldMap.branchUpdatePool, WorldMap.branchUpdateAllocatePool, WorldMap.branchDownloadPool, WorldMap.subsequentNormalTextureUploadPool, WorldMap.textureUploadBenchmark);
/*  42 */     BiomeGetter biomeGetter = new BiomeGetter();
/*  43 */     BrokenBlockTintCache brokenBlockTintCache = new BrokenBlockTintCache(new HashSet(32));
/*  44 */     BiomeColorCalculator biomeColorCalculator = new BiomeColorCalculator();
/*  45 */     WorldDataBiomeManager worldDataBiomeManager = new WorldDataBiomeManager();
/*  46 */     WorldDataReader worldDataReader = new WorldDataReader(WorldMap.overlayManager, blockStateShortShapeCache, worldDataBiomeManager, biomeZoomSeed);
/*  47 */     Executor worldDataRenderExecutor = new Executor("world data render executor", Thread.currentThread());
/*  48 */     WorldDataHandler worldDataHandler = new WorldDataHandler(worldDataReader, worldDataRenderExecutor);
/*  49 */     this.multiTextureRenderTypeRenderers = new MultiTextureRenderTypeRendererProvider(2);
/*  50 */     this.mapWriter = WorldMap.INSTANCE.createWriter(WorldMap.overlayManager, blockStateShortShapeCache, biomeGetter);
/*  51 */     HighlighterRegistry highlightRegistry = new HighlighterRegistry();
/*     */     
/*  53 */     if (SupportMods.pac())
/*  54 */       SupportMods.xaeroPac.registerHighlighters(highlightRegistry); 
/*  55 */     highlightRegistry.end();
/*  56 */     MapRegionHighlightsPreparer mapRegionHighlightsPreparer = new MapRegionHighlightsPreparer();
/*  57 */     ClientSyncedTrackedPlayerManager clientSyncedTrackedPlayerManager = new ClientSyncedTrackedPlayerManager();
/*  58 */     this
/*     */ 
/*     */       
/*  61 */       .mapProcessor = new MapProcessor(mapSaveLoad, this.mapWriter, WorldMap.mapLimiter, WorldMap.bufferDeallocator, WorldMap.tilePool, WorldMap.overlayManager, textureUploader, worldDataHandler, WorldMap.worldMapClientOnly.branchTextureRenderer, this.multiTextureRenderTypeRenderers, WorldMap.worldMapClientOnly.customVertexConsumers, biomeColorCalculator, blockStateShortShapeCache, biomeGetter, brokenBlockTintCache, highlightRegistry, mapRegionHighlightsPreparer, MessageBox.Builder.begin().build(), new MessageBoxRenderer(), new CaveStartCalculator(this.mapWriter), clientSyncedTrackedPlayerManager);
/*     */     
/*  63 */     this.mapWriter.setMapProcessor(this.mapProcessor);
/*  64 */     mapSaveLoad.setMapProcessor(this.mapProcessor);
/*  65 */     worldDataReader.setMapProcessor(this.mapProcessor);
/*  66 */     this.controlsHandler = new ControlsHandler(this.mapProcessor);
/*  67 */     this.mapProcessor.onInit(connection);
/*  68 */     this.usable = true;
/*  69 */     WorldMap.LOGGER.info("New world map session initialized!");
/*     */   }
/*     */   
/*     */   public void cleanup() {
/*     */     try {
/*  74 */       if (this.usable) {
/*  75 */         this.mapProcessor.stop();
/*     */         
/*  77 */         WorldMap.LOGGER.info("Finalizing world map session...");
/*  78 */         WorldMap.mapRunnerThread.interrupt();
/*  79 */         while (!this.mapProcessor.isFinished()) {
/*  80 */           this.mapProcessor.waitForLoadingToFinish(() -> { 
/*     */               }); try {
/*  82 */             Thread.sleep(20L);
/*  83 */           } catch (InterruptedException e) {
/*  84 */             WorldMap.LOGGER.error("suppressed exception", e);
/*     */           } 
/*     */         } 
/*     */       } 
/*  88 */       WorldMap.LOGGER.info("World map session finalized.");
/*  89 */       WorldMap.onSessionFinalized();
/*  90 */     } catch (Throwable t) {
/*  91 */       WorldMap.LOGGER.error("World map session failed to finalize properly.", t);
/*     */     } 
/*  93 */     this.usable = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public ControlsHandler getControlsHandler() {
/*  98 */     return this.controlsHandler;
/*     */   }
/*     */   
/*     */   public MapProcessor getMapProcessor() {
/* 102 */     return this.mapProcessor;
/*     */   }
/*     */   
/*     */   public static WorldMapSession getCurrentSession() {
/* 106 */     WorldMapSession session = getForPlayer((class_310.method_1551()).field_1724);
/* 107 */     if (session == null && XaeroWorldMapCore.currentSession != null && XaeroWorldMapCore.currentSession.usable)
/* 108 */       session = XaeroWorldMapCore.currentSession; 
/* 109 */     return session;
/*     */   }
/*     */   
/*     */   public static WorldMapSession getForPlayer(class_746 player) {
/* 113 */     if (player == null || player.field_3944 == null)
/* 114 */       return null; 
/* 115 */     return ((IWorldMapClientPlayNetHandler)player.field_3944).getXaero_worldmapSession();
/*     */   }
/*     */   
/*     */   public boolean isUsable() {
/* 119 */     return this.usable;
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\WorldMapSession.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */