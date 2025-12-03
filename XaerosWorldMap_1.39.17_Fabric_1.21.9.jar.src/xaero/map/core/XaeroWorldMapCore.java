/*     */ package xaero.map.core;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.Path;
/*     */ import net.minecraft.class_2556;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_2626;
/*     */ import net.minecraft.class_2637;
/*     */ import net.minecraft.class_2666;
/*     */ import net.minecraft.class_2672;
/*     */ import net.minecraft.class_2676;
/*     */ import net.minecraft.class_2678;
/*     */ import net.minecraft.class_2759;
/*     */ import net.minecraft.class_2818;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_32;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_634;
/*     */ import net.minecraft.class_638;
/*     */ import xaero.map.WorldMap;
/*     */ import xaero.map.WorldMapSession;
/*     */ import xaero.map.file.MapSaveLoad;
/*     */ import xaero.map.misc.Misc;
/*     */ import xaero.map.world.MapWorld;
/*     */ 
/*     */ public class XaeroWorldMapCore {
/*  26 */   public static Field chunkCleanField = null;
/*     */   public static WorldMapSession currentSession;
/*     */   
/*     */   public static void ensureField() {
/*  30 */     if (chunkCleanField == null) {
/*     */       try {
/*  32 */         chunkCleanField = class_2818.class.getDeclaredField("xaero_wm_chunkClean");
/*  33 */       } catch (NoSuchFieldException|SecurityException e) {
/*  34 */         throw new RuntimeException(e);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static void chunkUpdateCallback(int chunkX, int chunkZ) {
/*  40 */     ensureField();
/*  41 */     class_638 class_638 = (class_310.method_1551()).field_1687;
/*  42 */     if (class_638 != null) {
/*     */       try {
/*  44 */         for (int x = chunkX - 1; x < chunkX + 2; x++) {
/*  45 */           for (int z = chunkZ - 1; z < chunkZ + 2; z++) {
/*  46 */             class_2818 chunk = class_638.method_8497(x, z);
/*  47 */             if (chunk != null)
/*     */             {
/*     */               
/*  50 */               chunkCleanField.set(chunk, Boolean.valueOf(false)); } 
/*     */           } 
/*     */         } 
/*  53 */       } catch (IllegalArgumentException|IllegalAccessException e) {
/*  54 */         throw new RuntimeException(e);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static void onChunkData(int x, int z, class_6603 packetIn) {
/*  60 */     chunkUpdateCallback(x, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void onChunkLightData(int x, int z) {
/*  65 */     chunkUpdateCallback(x, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void onHandleLevelChunkWithLight(class_2672 packet) {
/*  70 */     onChunkLightData(packet.method_11523(), packet.method_11524());
/*     */   }
/*     */   
/*     */   public static void onHandleLightUpdatePacket(class_2676 packet) {
/*  74 */     onChunkLightData(packet.method_11558(), packet.method_11554());
/*     */   }
/*     */   
/*     */   public static void onQueueLightRemoval(class_2666 packet) {
/*  78 */     onChunkLightData((packet.comp_1726()).field_9181, (packet.comp_1726()).field_9180);
/*     */   }
/*     */   
/*     */   public static void onBlockChange(class_2626 packetIn) {
/*  82 */     chunkUpdateCallback(packetIn.method_11309().method_10263() >> 4, packetIn.method_11309().method_10260() >> 4);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void onMultiBlockChange(class_2637 packetIn) {
/*  87 */     IWorldMapSMultiBlockChangePacket packetAccess = (IWorldMapSMultiBlockChangePacket)packetIn;
/*  88 */     chunkUpdateCallback(packetAccess.xaero_wm_getSectionPos().method_10263(), packetAccess.xaero_wm_getSectionPos().method_10260());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void onPlayNetHandler(class_634 netHandler, class_2678 packet) {
/* 108 */     if (!WorldMap.loaded)
/*     */       return; 
/*     */     try {
/* 111 */       IWorldMapClientPlayNetHandler netHandlerAccess = (IWorldMapClientPlayNetHandler)netHandler;
/* 112 */       if (netHandlerAccess.getXaero_worldmapSession() != null)
/*     */         return; 
/* 114 */       if (currentSession != null) {
/* 115 */         WorldMap.LOGGER.info("Previous world map session still active. Probably using MenuMobs. Forcing it to end...");
/* 116 */         cleanupCurrentSession();
/*     */       } 
/* 118 */       WorldMapSession worldmapSession = new WorldMapSession();
/* 119 */       currentSession = worldmapSession;
/* 120 */       worldmapSession.init(netHandler, packet.comp_1727().comp_1555());
/* 121 */       netHandlerAccess.setXaero_worldmapSession(worldmapSession);
/* 122 */       WorldMap.settings.updateRegionCacheHashCode();
/* 123 */     } catch (Throwable e) {
/* 124 */       if (currentSession != null)
/* 125 */         cleanupCurrentSession(); 
/* 126 */       RuntimeException wrappedException = new RuntimeException("Exception initializing Xaero's World Map! ", e);
/* 127 */       WorldMap.crashHandler.setCrashedBy(wrappedException);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void cleanupCurrentSession() {
/*     */     try {
/* 133 */       currentSession.cleanup();
/* 134 */     } catch (Throwable supressed) {
/* 135 */       WorldMap.LOGGER.error("suppressed exception", supressed);
/*     */     } finally {
/* 137 */       currentSession = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void onPlayNetHandlerCleanup(class_634 netHandler) {
/* 142 */     if (!WorldMap.loaded)
/*     */       return; 
/*     */     try {
/* 145 */       WorldMapSession netHandlerSession = ((IWorldMapClientPlayNetHandler)netHandler).getXaero_worldmapSession();
/* 146 */       if (netHandlerSession == null)
/*     */         return; 
/*     */       try {
/* 149 */         netHandlerSession.cleanup();
/*     */       } finally {
/* 151 */         if (netHandlerSession == currentSession)
/* 152 */           currentSession = null; 
/* 153 */         ((IWorldMapClientPlayNetHandler)netHandler).setXaero_worldmapSession(null);
/*     */       } 
/* 155 */     } catch (Throwable e) {
/* 156 */       RuntimeException wrappedException = new RuntimeException("Exception finalizing Xaero's World Map! ", e);
/* 157 */       WorldMap.crashHandler.setCrashedBy(wrappedException);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void onDeleteWorld(class_32.class_5143 levelStorageAccess) {
/* 162 */     if (!WorldMap.loaded)
/*     */       return; 
/* 164 */     String folderName = levelStorageAccess.method_27010(class_5218.field_24188).getParent().getFileName().toString();
/* 165 */     String worldRootId = MapWorld.convertWorldFolderToRootId(4, folderName);
/* 166 */     if (!worldRootId.isEmpty()) {
/* 167 */       Path worldMapCacheFolder = MapSaveLoad.getRootFolder(worldRootId);
/* 168 */       if (worldMapCacheFolder.toFile().exists()) {
/*     */         try {
/* 170 */           Misc.deleteFileIf(worldMapCacheFolder, path -> {
/*     */                 String pathString = worldMapCacheFolder.relativize(path).toString().replace('\\', '/');
/*     */                 
/* 173 */                 return (pathString.contains("/cache/") || pathString.endsWith("/cache") || pathString.contains("/cache_"));
/*     */               }20);
/*     */           
/* 176 */           WorldMap.LOGGER.info(String.format("Deleted world map cache at %s", new Object[] { worldMapCacheFolder }));
/* 177 */         } catch (IOException e) {
/* 178 */           WorldMap.LOGGER.error(String.format("Failed to delete world map cache at %s!", new Object[] { worldMapCacheFolder }), e);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void onMinecraftRunTick() {
/* 185 */     if (WorldMap.events != null)
/* 186 */       WorldMap.events.handleClientRunTickStart(); 
/*     */   }
/*     */   
/*     */   public static boolean onSystemChat(class_2561 component) {
/* 190 */     if (!WorldMap.loaded)
/* 191 */       return false; 
/* 192 */     return WorldMap.events.handleClientSystemChatReceivedEvent(component);
/*     */   }
/*     */   
/*     */   public static boolean onHandleDisguisedChatMessage(class_2556.class_7602 chatType, class_2561 component) {
/* 196 */     if (!WorldMap.loaded)
/* 197 */       return true; 
/* 198 */     return !WorldMap.events.handleClientPlayerChatReceivedEvent(chatType, component, null);
/*     */   }
/*     */   
/*     */   public static boolean onRenderCall(boolean renderingInGame) {
/* 202 */     if (!WorldMap.loaded)
/* 203 */       return renderingInGame; 
/* 204 */     if (WorldMap.events.handleRenderTick(true))
/* 205 */       return false; 
/* 206 */     return renderingInGame;
/*     */   }
/*     */   
/*     */   public static void handlePlayerSetSpawnPacket(class_2759 packet) {
/* 210 */     if (!WorldMap.loaded)
/*     */       return; 
/* 212 */     WorldMap.events.handlePlayerSetSpawnEvent(packet.comp_4904().method_74897(), (class_310.method_1551()).field_1687);
/*     */   }
/*     */   
/*     */   public static boolean onRenderCrosshair(class_332 guiGraphics) {
/* 216 */     if (!WorldMap.loaded)
/* 217 */       return false; 
/* 218 */     return WorldMap.events.handleRenderCrosshairOverlay(guiGraphics);
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\core\XaeroWorldMapCore.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */