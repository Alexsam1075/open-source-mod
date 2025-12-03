/*    */ package xaero.map.events;
/*    */ 
/*    */ import java.nio.file.Path;
/*    */ import net.minecraft.class_1657;
/*    */ import net.minecraft.class_2561;
/*    */ import net.minecraft.class_3222;
/*    */ import net.minecraft.class_5218;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import xaero.map.WorldMap;
/*    */ import xaero.map.common.config.CommonConfig;
/*    */ import xaero.map.message.WorldMapMessage;
/*    */ import xaero.map.message.basic.ClientboundRulesPacket;
/*    */ import xaero.map.message.basic.HandshakePacket;
/*    */ import xaero.map.message.tracker.ClientboundPlayerTrackerResetPacket;
/*    */ import xaero.map.server.MinecraftServerData;
/*    */ import xaero.map.server.MineraftServerDataInitializer;
/*    */ import xaero.map.server.level.LevelMapProperties;
/*    */ import xaero.map.server.player.IServerPlayer;
/*    */ import xaero.map.server.player.ServerPlayerData;
/*    */ 
/*    */ 
/*    */ public class CommonEvents
/*    */ {
/*    */   protected void onPlayerClone(class_1657 oldPlayer, class_1657 newPlayer, boolean alive) {
/* 25 */     if (oldPlayer instanceof class_3222) { class_3222 oldServerPlayer = (class_3222)oldPlayer;
/* 26 */       ((IServerPlayer)newPlayer).setXaeroWorldMapPlayerData(ServerPlayerData.get(oldServerPlayer)); }
/*    */   
/*    */   }
/*    */   
/*    */   public void onServerStarting(MinecraftServer server) {
/* 31 */     (new MineraftServerDataInitializer()).init(server);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onServerStopped(MinecraftServer server) {}
/*    */ 
/*    */   
/*    */   public void onPlayerLogIn(class_1657 player) {
/* 39 */     if (player instanceof class_3222) { class_3222 serverPlayer = (class_3222)player;
/* 40 */       WorldMap.messageHandler.sendToPlayer(serverPlayer, (WorldMapMessage)new ClientboundPlayerTrackerResetPacket()); }
/*    */   
/*    */   }
/*    */   public void onPlayerWorldJoin(class_3222 player) {
/* 44 */     WorldMap.messageHandler.sendToPlayer(player, (WorldMapMessage)new HandshakePacket());
/* 45 */     CommonConfig config = WorldMap.commonConfig;
/* 46 */     WorldMap.messageHandler.sendToPlayer(player, (WorldMapMessage)new ClientboundRulesPacket(config.allowCaveModeOnServer, config.allowNetherCaveModeOnServer));
/*    */     
/* 48 */     Path propertiesPath = player.method_51469().method_8503().method_27050(class_5218.field_24184).getParent().resolve("xaeromap.txt");
/*    */     
/*    */     try {
/* 51 */       MinecraftServerData serverData = MinecraftServerData.get(player.method_51469().method_8503());
/* 52 */       LevelMapProperties properties = serverData.getLevelProperties(propertiesPath);
/* 53 */       if (properties.isUsable())
/* 54 */         WorldMap.messageHandler.sendToPlayer(player, (WorldMapMessage)properties); 
/* 55 */     } catch (Throwable t) {
/* 56 */       WorldMap.LOGGER.error("suppressed exception", t);
/* 57 */       player.field_13987.method_52396((class_2561)class_2561.method_43471("gui.xaero_wm_error_loading_properties"));
/*    */     } 
/*    */   }
/*    */   
/*    */   public void handlePlayerTickStart(class_1657 player) {
/* 62 */     if (player instanceof class_3222) {
/* 63 */       WorldMap.serverPlayerTickHandler.tick((class_3222)player);
/*    */       return;
/*    */     } 
/* 66 */     if (WorldMap.events != null)
/* 67 */       WorldMap.events.handlePlayerTickStart(player); 
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\events\CommonEvents.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */