/*     */ package xaero.map;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.attribute.FileAttribute;
/*     */ import net.minecraft.class_2960;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import xaero.map.biome.BiomeGetter;
/*     */ import xaero.map.cache.BlockStateShortShapeCache;
/*     */ import xaero.map.cache.UnknownBlockStateCache;
/*     */ import xaero.map.common.config.CommonConfig;
/*     */ import xaero.map.common.config.CommonConfigIO;
/*     */ import xaero.map.common.config.CommonConfigInit;
/*     */ import xaero.map.controls.ControlsRegister;
/*     */ import xaero.map.deallocator.ByteBufferDeallocator;
/*     */ import xaero.map.element.MapElementRenderHandler;
/*     */ import xaero.map.events.ClientEvents;
/*     */ import xaero.map.events.CommonEvents;
/*     */ import xaero.map.events.ModClientEvents;
/*     */ import xaero.map.events.ModCommonEvents;
/*     */ import xaero.map.file.OldFormatSupport;
/*     */ import xaero.map.file.export.PNGExporter;
/*     */ import xaero.map.graphics.GpuObjectDeleter;
/*     */ import xaero.map.graphics.TextureUploadBenchmark;
/*     */ import xaero.map.message.WorldMapMessageHandler;
/*     */ import xaero.map.message.WorldMapMessageRegister;
/*     */ import xaero.map.misc.Internet;
/*     */ import xaero.map.mods.SupportMods;
/*     */ import xaero.map.mods.gui.WaypointSymbolCreator;
/*     */ import xaero.map.patreon.Patreon;
/*     */ import xaero.map.platform.Services;
/*     */ import xaero.map.pool.MapTilePool;
/*     */ import xaero.map.pool.TextureUploadPool;
/*     */ import xaero.map.pool.buffer.TextureDirectBufferPool;
/*     */ import xaero.map.radar.tracker.PlayerTrackerMapElementRenderer;
/*     */ import xaero.map.radar.tracker.PlayerTrackerMenuRenderer;
/*     */ import xaero.map.radar.tracker.system.IPlayerTrackerSystem;
/*     */ import xaero.map.radar.tracker.system.PlayerTrackerSystemManager;
/*     */ import xaero.map.radar.tracker.system.impl.SyncedPlayerTrackerSystem;
/*     */ import xaero.map.region.OverlayManager;
/*     */ import xaero.map.server.WorldMapServer;
/*     */ import xaero.map.server.mods.SupportServerMods;
/*     */ import xaero.map.server.player.ServerPlayerTickHandler;
/*     */ import xaero.map.settings.ModOptions;
/*     */ import xaero.map.settings.ModSettings;
/*     */ 
/*     */ public abstract class WorldMap {
/*     */   public static final String MOD_ID = "xaeroworldmap";
/*     */   
/*     */   public WorldMap() {
/*  54 */     INSTANCE = this;
/*  55 */     (new CommonConfigInit()).init("xaeroworldmap-common.txt");
/*     */   }
/*     */   
/*     */   public static boolean loaded = false;
/*     */   public static WorldMap INSTANCE;
/*  60 */   public static int MINIMAP_COMPATIBILITY_VERSION = 26;
/*  61 */   public static Logger LOGGER = LogManager.getLogger();
/*     */   
/*     */   static final String versionID_minecraft = "1.21.9";
/*     */   
/*     */   private String versionID;
/*     */   
/*     */   public static int newestUpdateID;
/*     */   
/*     */   public static boolean isOutdated;
/*     */   
/*     */   public static String latestVersion;
/*     */   
/*     */   public static String latestVersionMD5;
/*     */   public static ClientEvents events;
/*     */   public static ModClientEvents modEvents;
/*     */   public static ControlsRegister controlsRegister;
/*     */   public static WaypointSymbolCreator waypointSymbolCreator;
/*     */   public static ByteBufferDeallocator bufferDeallocator;
/*     */   public static TextureUploadBenchmark textureUploadBenchmark;
/*     */   public static OverlayManager overlayManager;
/*     */   public static OldFormatSupport oldFormatSupport;
/*     */   public static PNGExporter pngExporter;
/*     */   public static TextureUploadPool.Normal normalTextureUploadPool;
/*     */   public static TextureUploadPool.BranchUpdate branchUpdatePool;
/*     */   public static TextureUploadPool.BranchUpdate branchUpdateAllocatePool;
/*     */   public static TextureUploadPool.BranchDownload branchDownloadPool;
/*     */   public static TextureUploadPool.SubsequentNormal subsequentNormalTextureUploadPool;
/*     */   public static TextureDirectBufferPool textureDirectBufferPool;
/*     */   public static MapTilePool tilePool;
/*     */   public static MapLimiter mapLimiter;
/*     */   public static UnknownBlockStateCache unknownBlockStateCache;
/*     */   public static GpuObjectDeleter gpuObjectDeleter;
/*     */   public static MapRunner mapRunner;
/*     */   public static Thread mapRunnerThread;
/*     */   public static CrashHandler crashHandler;
/*  96 */   public static final class_2960 guiTextures = class_2960.method_60655("xaeroworldmap", "gui/gui.png");
/*     */   public static ModSettings settings;
/*  98 */   public static int globalVersion = 1;
/*     */   public static WorldMapClientOnly worldMapClientOnly;
/*     */   public static WorldMapServer worldmapServer;
/*     */   public static MapElementRenderHandler mapElementRenderHandler;
/*     */   public static ServerPlayerTickHandler serverPlayerTickHandler;
/* 103 */   public static PlayerTrackerSystemManager playerTrackerSystemManager = new PlayerTrackerSystemManager();
/*     */   
/*     */   public static PlayerTrackerMapElementRenderer trackedPlayerRenderer;
/*     */   
/*     */   public static PlayerTrackerMenuRenderer trackedPlayerMenuRenderer;
/*     */   
/*     */   public static WorldMapMessageHandler messageHandler;
/*     */   
/*     */   public static CommonEvents commonEvents;
/*     */   public static ModCommonEvents modCommonEvents;
/*     */   
/*     */   void loadClient() throws IOException, ClassNotFoundException, NoSuchMethodException, SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
/* 115 */     LOGGER.info("Loading Xaero's World Map - Stage 1/2");
/*     */ 
/*     */     
/* 118 */     trackedPlayerRenderer = PlayerTrackerMapElementRenderer.Builder.begin().build();
/* 119 */     trackedPlayerMenuRenderer = PlayerTrackerMenuRenderer.Builder.begin().setRenderer(trackedPlayerRenderer).build();
/* 120 */     ModOptions.init();
/* 121 */     Path modFile = fetchModFile();
/* 122 */     (worldMapClientOnly = createClientLoad()).preInit("xaeroworldmap");
/* 123 */     String fileName = modFile.getFileName().toString();
/* 124 */     if (fileName.endsWith(".jar"))
/* 125 */       modJAR = modFile.toFile(); 
/* 126 */     Path gameDir = Services.PLATFORM.getGameDir();
/* 127 */     Path config = Services.PLATFORM.getConfigDir();
/* 128 */     configFolder = config.toFile();
/* 129 */     optionsFile = config.resolve("xaeroworldmap.txt").toFile();
/* 130 */     Path oldSaveFolder4 = gameDir.resolve("XaeroWorldMap");
/* 131 */     Path xaeroFolder = gameDir.resolve("xaero");
/* 132 */     if (!Files.exists(xaeroFolder, new java.nio.file.LinkOption[0]))
/* 133 */       Files.createDirectories(xaeroFolder, (FileAttribute<?>[])new FileAttribute[0]); 
/* 134 */     saveFolder = xaeroFolder.resolve("world-map").toFile();
/* 135 */     if (oldSaveFolder4.toFile().exists() && !saveFolder.exists()) {
/* 136 */       Files.move(oldSaveFolder4, saveFolder.toPath(), new java.nio.file.CopyOption[0]);
/*     */     }
/* 138 */     Path oldSaveFolder3 = config.getParent().resolve("XaeroWorldMap");
/* 139 */     File oldOptionsFile = gameDir.resolve("xaeroworldmap.txt").toFile();
/* 140 */     File oldSaveFolder = gameDir.resolve("mods").resolve("XaeroWorldMap").toFile();
/* 141 */     File oldSaveFolder2 = gameDir.resolve("config").resolve("XaeroWorldMap").toFile();
/* 142 */     if (oldOptionsFile.exists() && !optionsFile.exists())
/* 143 */       Files.move(oldOptionsFile.toPath(), optionsFile.toPath(), new java.nio.file.CopyOption[0]); 
/* 144 */     if (oldSaveFolder.exists() && !saveFolder.exists())
/* 145 */       Files.move(oldSaveFolder.toPath(), saveFolder.toPath(), new java.nio.file.CopyOption[0]); 
/* 146 */     if (oldSaveFolder2.exists() && !saveFolder.exists())
/* 147 */       Files.move(oldSaveFolder2.toPath(), saveFolder.toPath(), new java.nio.file.CopyOption[0]); 
/* 148 */     if (oldSaveFolder3.toFile().exists() && !saveFolder.exists())
/* 149 */       Files.move(oldSaveFolder3, saveFolder.toPath(), new java.nio.file.CopyOption[0]); 
/* 150 */     if (!saveFolder.exists()) {
/* 151 */       Files.createDirectories(saveFolder.toPath(), (FileAttribute<?>[])new FileAttribute[0]);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 156 */     settings = new ModSettings();
/* 157 */     settings.loadSettings();
/* 158 */     Patreon.checkPatreon();
/* 159 */     Internet.checkModVersion();
/* 160 */     if (isOutdated) {
/* 161 */       Object patreonEntry = Patreon.getMods().get(getFileLayoutID());
/* 162 */       if (patreonEntry != null) {
/* 163 */         Patreon.setModInfo(patreonEntry, modJAR, getVersionID(), latestVersion, latestVersionMD5, () -> {
/*     */               ModSettings.ignoreUpdate = newestUpdateID;
/*     */               try {
/*     */                 settings.saveSettings();
/* 167 */               } catch (IOException e) {
/*     */                 LOGGER.error("suppressed exception", e);
/*     */               } 
/*     */             });
/* 171 */         Patreon.addOutdatedMod(patreonEntry);
/*     */       } 
/*     */     } 
/* 174 */     waypointSymbolCreator = new WaypointSymbolCreator();
/* 175 */     if (controlsRegister == null)
/* 176 */       controlsRegister = new ControlsRegister(); 
/* 177 */     bufferDeallocator = new ByteBufferDeallocator();
/* 178 */     tilePool = new MapTilePool();
/* 179 */     overlayManager = new OverlayManager();
/* 180 */     oldFormatSupport = new OldFormatSupport();
/* 181 */     pngExporter = new PNGExporter(configFolder.toPath().getParent().resolve("map exports"));
/* 182 */     mapLimiter = new MapLimiter();
/* 183 */     normalTextureUploadPool = new TextureUploadPool.Normal(256);
/*     */     
/* 185 */     branchUpdatePool = new TextureUploadPool.BranchUpdate(256, false);
/* 186 */     branchUpdateAllocatePool = new TextureUploadPool.BranchUpdate(256, true);
/* 187 */     branchDownloadPool = new TextureUploadPool.BranchDownload(256);
/* 188 */     textureDirectBufferPool = new TextureDirectBufferPool();
/* 189 */     subsequentNormalTextureUploadPool = new TextureUploadPool.SubsequentNormal(256);
/* 190 */     textureUploadBenchmark = new TextureUploadBenchmark(new int[] { 512, 512, 512, 256, 256, 256, 256 });
/* 191 */     unknownBlockStateCache = new UnknownBlockStateCache();
/* 192 */     gpuObjectDeleter = new GpuObjectDeleter();
/* 193 */     crashHandler = new CrashHandler();
/* 194 */     (mapRunnerThread = new Thread(mapRunner = new MapRunner())).start();
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadLater() {
/* 199 */     LOGGER.info("Loading Xaero's World Map - Stage 2/2");
/*     */     try {
/* 201 */       settings.findMapItem();
/* 202 */       worldMapClientOnly.postInit();
/* 203 */       settings.updateRegionCacheHashCode();
/* 204 */       playerTrackerSystemManager.register("map_synced", (IPlayerTrackerSystem)new SyncedPlayerTrackerSystem());
/* 205 */       createSupportMods().load();
/*     */ 
/*     */       
/* 208 */       mapElementRenderHandler = MapElementRenderHandler.Builder.begin().setPoseStack(worldMapClientOnly.getMapScreenPoseStack()).build();
/* 209 */       oldFormatSupport.loadStates();
/* 210 */       loaded = true;
/* 211 */     } catch (Throwable e) {
/* 212 */       LOGGER.error("error", e);
/* 213 */       crashHandler.setCrashedBy(e);
/*     */     } 
/*     */   }
/*     */   
/* 217 */   public static File modJAR = null;
/*     */   public static File configFolder;
/*     */   public static File optionsFile;
/*     */   public static File saveFolder;
/*     */   public static CommonConfigIO commonConfigIO;
/*     */   public static CommonConfig commonConfig;
/*     */   
/*     */   void loadServer() {
/* 225 */     worldmapServer = createServerLoad();
/* 226 */     worldmapServer.load();
/*     */   }
/*     */   
/*     */   void loadLaterServer() {
/* 230 */     worldmapServer.loadLater();
/* 231 */     loaded = true;
/*     */   }
/*     */   
/*     */   void loadCommon() {
/* 235 */     this.versionID = "1.21.9_" + getModInfoVersion();
/* 236 */     (new WorldMapMessageRegister()).register(messageHandler);
/* 237 */     serverPlayerTickHandler = new ServerPlayerTickHandler();
/* 238 */     SupportServerMods.check();
/*     */   }
/*     */   
/*     */   public String getVersionID() {
/* 242 */     return this.versionID;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void onSessionFinalized() {
/* 247 */     mapLimiter.onSessionFinalized();
/* 248 */     if (SupportMods.minimap())
/* 249 */       SupportMods.xaeroMinimap.onSessionFinalized(); 
/*     */   }
/*     */   
/*     */   protected abstract Path fetchModFile();
/*     */   
/*     */   protected abstract String getFileLayoutID();
/*     */   
/*     */   protected abstract SupportMods createSupportMods();
/*     */   
/*     */   protected abstract WorldMapClientOnly createClientLoad();
/*     */   
/*     */   protected abstract WorldMapServer createServerLoad();
/*     */   
/*     */   public abstract MapWriter createWriter(OverlayManager paramOverlayManager, BlockStateShortShapeCache paramBlockStateShortShapeCache, BiomeGetter paramBiomeGetter);
/*     */   
/*     */   protected abstract String getModInfoVersion();
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\WorldMap.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */