/*     */ package xaero.map;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.nio.file.Path;
/*     */ import java.util.List;
/*     */ import net.fabricmc.api.ClientModInitializer;
/*     */ import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
/*     */ import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
/*     */ import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
/*     */ import net.fabricmc.loader.api.FabricLoader;
/*     */ import net.fabricmc.loader.api.ModContainer;
/*     */ import net.fabricmc.loader.api.metadata.ModOrigin;
/*     */ import net.minecraft.class_2378;
/*     */ import net.minecraft.class_304;
/*     */ import net.minecraft.class_6880;
/*     */ import net.minecraft.class_7923;
/*     */ import net.minecraft.class_9139;
/*     */ import xaero.map.biome.BiomeGetter;
/*     */ import xaero.map.cache.BlockStateShortShapeCache;
/*     */ import xaero.map.effects.EffectsRegister;
/*     */ import xaero.map.effects.WorldMapStatusEffect;
/*     */ import xaero.map.events.ClientEvents;
/*     */ import xaero.map.events.ClientEventsFabric;
/*     */ import xaero.map.events.CommonEventsFabric;
/*     */ import xaero.map.events.ModClientEvents;
/*     */ import xaero.map.events.ModClientEventsFabric;
/*     */ import xaero.map.events.ModCommonEventsFabric;
/*     */ import xaero.map.message.WorldMapMessageHandler;
/*     */ import xaero.map.message.WorldMapMessageHandlerFabric;
/*     */ import xaero.map.message.WorldMapMessageHandlerFull;
/*     */ import xaero.map.message.payload.WorldMapMessagePayload;
/*     */ import xaero.map.message.payload.WorldMapMessagePayloadCodec;
/*     */ import xaero.map.mods.SupportMods;
/*     */ import xaero.map.mods.SupportModsFabric;
/*     */ import xaero.map.region.OverlayManager;
/*     */ import xaero.map.server.WorldMapServer;
/*     */ 
/*     */ public class WorldMapFabric extends WorldMap implements ClientModInitializer, DedicatedServerModInitializer {
/*  39 */   private final String fileLayoutID = "worldmap_fabric";
/*     */   
/*     */   private Throwable firstStageError;
/*     */   
/*     */   private boolean loadLaterNeeded;
/*     */   private boolean loadLaterDone;
/*     */   
/*     */   public void onInitializeClient() {
/*     */     try {
/*  48 */       loadCommon();
/*  49 */       loadClient();
/*  50 */     } catch (Throwable e) {
/*  51 */       this.firstStageError = e;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onInitializeServer() {
/*     */     try {
/*  58 */       loadCommon();
/*  59 */       loadServer();
/*  60 */     } catch (Throwable e) {
/*  61 */       this.firstStageError = e;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void registerClientEvents() {
/*  66 */     events = (ClientEvents)new ClientEventsFabric();
/*  67 */     modEvents = (ModClientEvents)new ModClientEventsFabric();
/*     */   }
/*     */   
/*     */   private void registerCommonEvents() {
/*  71 */     CommonEventsFabric commonEventsFabric = new CommonEventsFabric();
/*  72 */     commonEvents = (CommonEvents)commonEventsFabric;
/*  73 */     modCommonEvents = (ModCommonEvents)new ModCommonEventsFabric();
/*  74 */     commonEventsFabric.register();
/*     */   }
/*     */ 
/*     */   
/*     */   void loadCommon() {
/*  79 */     messageHandler = (WorldMapMessageHandler)new WorldMapMessageHandlerFabric();
/*  80 */     super.loadCommon();
/*  81 */     if (commonConfig.registerStatusEffects)
/*  82 */       (new EffectsRegister()).registerEffects(effect -> class_2378.method_47985(class_7923.field_41174, effect.getRegistryName(), effect)); 
/*  83 */     WorldMapMessagePayloadCodec worldMapMessagePayloadCodec = new WorldMapMessagePayloadCodec((WorldMapMessageHandlerFull)messageHandler);
/*  84 */     PayloadTypeRegistry.playS2C().register(WorldMapMessagePayload.TYPE, (class_9139)worldMapMessagePayloadCodec);
/*  85 */     PayloadTypeRegistry.playC2S().register(WorldMapMessagePayload.TYPE, (class_9139)worldMapMessagePayloadCodec);
/*  86 */     ServerPlayNetworking.registerGlobalReceiver(WorldMapMessagePayload.TYPE, (ServerPlayNetworking.PlayPayloadHandler)new WorldMapPayloadServerHandler());
/*  87 */     registerCommonEvents();
/*     */   }
/*     */ 
/*     */   
/*     */   void loadClient() throws IOException, ClassNotFoundException, NoSuchMethodException, SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
/*  92 */     registerClientEvents();
/*  93 */     super.loadClient();
/*  94 */     controlsRegister.register(KeyBindingHelper::registerKeyBinding, c -> { 
/*  95 */         }); this.loadLaterNeeded = true;
/*     */   }
/*     */ 
/*     */   
/*     */   void loadServer() {
/* 100 */     super.loadServer();
/* 101 */     this.loadLaterNeeded = true;
/*     */   }
/*     */   
/*     */   public void tryLoadLater() {
/* 105 */     if (this.loadLaterDone)
/*     */       return; 
/* 107 */     if (this.firstStageError != null)
/* 108 */       throw new RuntimeException(this.firstStageError); 
/* 109 */     if (!this.loadLaterNeeded)
/*     */       return; 
/* 111 */     this.loadLaterDone = true;
/* 112 */     loadLater();
/*     */   }
/*     */   
/*     */   public void tryLoadLaterServer() {
/* 116 */     if (this.loadLaterDone)
/*     */       return; 
/* 118 */     if (this.firstStageError != null)
/* 119 */       throw new RuntimeException(this.firstStageError); 
/* 120 */     if (!this.loadLaterNeeded)
/*     */       return; 
/* 122 */     this.loadLaterDone = true;
/* 123 */     loadLaterServer();
/*     */   }
/*     */ 
/*     */   
/*     */   protected Path fetchModFile() {
/* 128 */     ModContainer modContainer = FabricLoader.getInstance().getModContainer("xaeroworldmap").orElse(null);
/* 129 */     ModOrigin origin = modContainer.getOrigin();
/* 130 */     Path modFile = (origin.getKind() == ModOrigin.Kind.PATH) ? origin.getPaths().get(0) : null;
/* 131 */     if (modFile == null) {
/*     */       try {
/* 133 */         Class<?> quiltLoaderClass = Class.forName("org.quiltmc.loader.api.QuiltLoader");
/* 134 */         Method quiltGetModContainerMethod = quiltLoaderClass.getDeclaredMethod("getModContainer", new Class[] { String.class });
/* 135 */         Class<?> quiltModContainerAPIClass = Class.forName("org.quiltmc.loader.api.ModContainer");
/* 136 */         Method quiltGetSourcePathsMethod = quiltModContainerAPIClass.getDeclaredMethod("getSourcePaths", new Class[0]);
/* 137 */         Object quiltModContainer = ((Optional)quiltGetModContainerMethod.invoke(null, new Object[] { "xaeroworldmap" })).orElse(null);
/*     */         
/* 139 */         List<List<Path>> paths = (List<List<Path>>)quiltGetSourcePathsMethod.invoke(quiltModContainer, new Object[0]);
/* 140 */         if (!paths.isEmpty() && !((List)paths.get(0)).isEmpty())
/* 141 */           modFile = ((List<Path>)paths.get(0)).get(0); 
/* 142 */       } catch (ClassNotFoundException|NoSuchMethodException|SecurityException|IllegalAccessException|IllegalArgumentException|java.lang.reflect.InvocationTargetException classNotFoundException) {}
/*     */     }
/*     */ 
/*     */     
/* 146 */     return modFile;
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getFileLayoutID() {
/* 151 */     return "worldmap_fabric";
/*     */   }
/*     */ 
/*     */   
/*     */   protected SupportMods createSupportMods() {
/* 156 */     return (SupportMods)new SupportModsFabric();
/*     */   }
/*     */   
/*     */   protected WorldMapClientOnly createClientLoad() {
/* 160 */     return new WorldMapClientOnlyFabric();
/*     */   }
/*     */ 
/*     */   
/*     */   protected WorldMapServer createServerLoad() {
/* 165 */     return (WorldMapServer)new WorldMapServerFabric();
/*     */   }
/*     */ 
/*     */   
/*     */   public MapWriter createWriter(OverlayManager overlayManager, BlockStateShortShapeCache blockStateShortShapeCache, BiomeGetter biomeGetter) {
/* 170 */     return new MapWriterFabric(overlayManager, blockStateShortShapeCache, biomeGetter);
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getModInfoVersion() {
/* 175 */     ModContainer modContainer = FabricLoader.getInstance().getModContainer("xaeroworldmap").get();
/* 176 */     return modContainer.getMetadata().getVersion().getFriendlyString() + "_fabric";
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\WorldMapFabric.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */