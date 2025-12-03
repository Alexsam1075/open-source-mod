/*    */ package xaero.map.server;
/*    */ 
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.IOException;
/*    */ import java.nio.file.Path;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import xaero.map.WorldMap;
/*    */ import xaero.map.server.level.LevelMapProperties;
/*    */ import xaero.map.server.level.LevelMapPropertiesIO;
/*    */ import xaero.map.server.radar.tracker.SyncedPlayerTracker;
/*    */ import xaero.map.server.radar.tracker.SyncedPlayerTrackerSystemManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MinecraftServerData
/*    */ {
/*    */   private final SyncedPlayerTrackerSystemManager syncedPlayerTrackerSystemManager;
/*    */   private final SyncedPlayerTracker syncedPlayerTracker;
/*    */   private final Map<Path, LevelMapProperties> levelProperties;
/*    */   private final LevelMapPropertiesIO propertiesIO;
/*    */   
/*    */   public MinecraftServerData(SyncedPlayerTrackerSystemManager syncedPlayerTrackerSystemManager, SyncedPlayerTracker syncedPlayerTracker) {
/* 26 */     this.syncedPlayerTrackerSystemManager = syncedPlayerTrackerSystemManager;
/* 27 */     this.syncedPlayerTracker = syncedPlayerTracker;
/* 28 */     this.levelProperties = new HashMap<>();
/* 29 */     this.propertiesIO = new LevelMapPropertiesIO();
/*    */   }
/*    */   
/*    */   public LevelMapProperties getLevelProperties(Path path) {
/* 33 */     LevelMapProperties properties = this.levelProperties.get(path);
/* 34 */     if (properties == null) {
/* 35 */       properties = new LevelMapProperties();
/*    */       try {
/* 37 */         this.propertiesIO.load(path, properties);
/* 38 */       } catch (FileNotFoundException fnfe) {
/*    */         try {
/* 40 */           this.propertiesIO.save(path, properties);
/* 41 */         } catch (IOException e) {
/* 42 */           properties.setUsable(false);
/* 43 */           WorldMap.LOGGER.warn("Failed to initialize map properties for a world due to an IO exception. This shouldn't be a problem if it's not a \"real\" world. Message: {}", e.getMessage());
/* 44 */           if (WorldMap.settings.debug)
/* 45 */             WorldMap.LOGGER.warn("Full exception: ", e); 
/*    */         } 
/* 47 */       } catch (IOException e) {
/* 48 */         throw new RuntimeException(e);
/*    */       } 
/* 50 */       this.levelProperties.put(path, properties);
/*    */     } 
/* 52 */     return properties;
/*    */   }
/*    */   
/*    */   public SyncedPlayerTrackerSystemManager getSyncedPlayerTrackerSystemManager() {
/* 56 */     return this.syncedPlayerTrackerSystemManager;
/*    */   }
/*    */   
/*    */   public SyncedPlayerTracker getSyncedPlayerTracker() {
/* 60 */     return this.syncedPlayerTracker;
/*    */   }
/*    */   
/*    */   public static MinecraftServerData get(MinecraftServer server) {
/* 64 */     return ((IMinecraftServer)server).getXaeroWorldMapServerData();
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\server\MinecraftServerData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */