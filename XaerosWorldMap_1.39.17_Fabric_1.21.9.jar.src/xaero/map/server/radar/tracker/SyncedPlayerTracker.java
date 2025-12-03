/*     */ package xaero.map.server.radar.tracker;
/*     */ 
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.class_1657;
/*     */ import net.minecraft.class_3222;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import xaero.map.WorldMap;
/*     */ import xaero.map.message.WorldMapMessage;
/*     */ import xaero.map.message.tracker.ClientboundTrackedPlayerPacket;
/*     */ import xaero.map.server.MinecraftServerData;
/*     */ import xaero.map.server.mods.SupportServerMods;
/*     */ import xaero.map.server.player.ServerPlayerData;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SyncedPlayerTracker
/*     */ {
/*     */   public void onTick(MinecraftServer server, class_3222 player, MinecraftServerData serverData, ServerPlayerData playerData) {
/*  21 */     long currentTime = System.currentTimeMillis();
/*  22 */     if (currentTime - playerData.getLastTrackedPlayerSync() < 250L)
/*     */       return; 
/*  24 */     playerData.setLastTrackedPlayerSync(currentTime);
/*  25 */     boolean playerHasMod = playerData.hasMod();
/*  26 */     boolean shouldSyncToPlayer = playerHasMod;
/*     */     
/*  28 */     if (SupportServerMods.hasMinimap() && SupportServerMods.getMinimap().supportsTrackedPlayers() && SupportServerMods.getMinimap().playerSupportsTrackedPlayers(player)) {
/*  29 */       if (playerData.getCurrentlySyncedPlayers() != null && !playerData.getCurrentlySyncedPlayers().isEmpty()) {
/*  30 */         for (UUID id : playerData.getCurrentlySyncedPlayers())
/*  31 */           sendRemovePacket(player, playerData, id); 
/*  32 */         playerData.getCurrentlySyncedPlayers().clear();
/*     */       } 
/*  34 */       shouldSyncToPlayer = false;
/*     */     } 
/*  36 */     boolean everyoneIsTracked = WorldMap.commonConfig.everyoneTracksEveryone;
/*  37 */     Iterable<ISyncedPlayerTrackerSystem> playerTrackerSystems = serverData.getSyncedPlayerTrackerSystemManager().getSystems();
/*  38 */     Set<UUID> syncedPlayers = playerData.ensureCurrentlySyncedPlayers();
/*  39 */     Set<UUID> leftoverPlayers = new HashSet<>(syncedPlayers);
/*  40 */     SyncedTrackedPlayer toSync = playerData.getLastSyncedData();
/*  41 */     boolean shouldSyncToOthers = (toSync == null || !toSync.matchesEnough((class_1657)player, 0.0D));
/*  42 */     if (shouldSyncToOthers) {
/*  43 */       toSync = playerData.ensureLastSyncedData();
/*  44 */       toSync.update((class_1657)player);
/*     */     } 
/*     */ 
/*     */     
/*  48 */     boolean opacReceiveParty = (SupportServerMods.hasOpac() && SupportServerMods.getOpac().getReceiveLocationsFromPartyConfigValue(player));
/*     */     
/*  50 */     boolean opacReceiveMutualAllies = (SupportServerMods.hasOpac() && SupportServerMods.getOpac().getReceiveLocationsFromMutualAlliesConfigValue(player));
/*  51 */     if (SupportServerMods.hasOpac()) {
/*  52 */       SupportServerMods.getOpac().updateShareLocationConfigValues(player, playerData);
/*     */     }
/*  54 */     for (class_3222 otherPlayer : server.method_3760().method_14571()) {
/*  55 */       if (otherPlayer == player)
/*     */         continue; 
/*  57 */       leftoverPlayers.remove(otherPlayer.method_5667());
/*     */       
/*  59 */       ServerPlayerData otherPlayerData = ServerPlayerData.get(otherPlayer);
/*  60 */       if (shouldSyncToOthers) {
/*  61 */         Set<UUID> otherPlayerSyncedPlayers = otherPlayerData.getCurrentlySyncedPlayers();
/*  62 */         if (otherPlayerSyncedPlayers != null && otherPlayerSyncedPlayers.contains(player.method_5667()))
/*  63 */           sendTrackedPlayerPacket(otherPlayer, otherPlayerData, toSync); 
/*     */       } 
/*  65 */       if (!shouldSyncToPlayer)
/*     */         continue; 
/*  67 */       boolean tracked = everyoneIsTracked;
/*  68 */       if (!tracked) {
/*     */         
/*  70 */         boolean opacConfigsAllowPartySync = (!SupportServerMods.hasOpac() || SupportServerMods.getOpac().isPositionSyncAllowed(2, otherPlayerData, opacReceiveParty));
/*     */ 
/*     */ 
/*     */         
/*  74 */         boolean opacConfigsAllowAllySync = (!SupportServerMods.hasOpac() || SupportServerMods.getOpac().isPositionSyncAllowed(1, otherPlayerData, opacReceiveMutualAllies));
/*     */ 
/*     */         
/*  77 */         for (ISyncedPlayerTrackerSystem system : playerTrackerSystems) {
/*  78 */           int trackingLevel = system.getTrackingLevel((class_1657)player, (class_1657)otherPlayer);
/*  79 */           if (trackingLevel > 0 && (
/*  80 */             !system.isPartySystem() || (trackingLevel == 1 && opacConfigsAllowAllySync) || (trackingLevel > 1 && opacConfigsAllowPartySync))) {
/*  81 */             tracked = true;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/*  87 */       boolean alreadySynced = syncedPlayers.contains(otherPlayer.method_5667());
/*  88 */       if (!tracked) {
/*  89 */         if (alreadySynced) {
/*  90 */           syncedPlayers.remove(otherPlayer.method_5667());
/*  91 */           sendRemovePacket(player, playerData, otherPlayer.method_5667());
/*     */         } 
/*     */         continue;
/*     */       } 
/*  95 */       if (alreadySynced)
/*     */         continue; 
/*  97 */       if (otherPlayerData.getLastSyncedData() != null) {
/*  98 */         syncedPlayers.add(otherPlayer.method_5667());
/*  99 */         sendTrackedPlayerPacket(player, playerData, otherPlayerData.getLastSyncedData());
/*     */       } 
/*     */     } 
/* 102 */     for (UUID offlineId : leftoverPlayers) {
/* 103 */       syncedPlayers.remove(offlineId);
/* 104 */       sendRemovePacket(player, playerData, offlineId);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void sendRemovePacket(class_3222 player, ServerPlayerData playerData, UUID toRemove) {
/* 109 */     WorldMap.messageHandler.sendToPlayer(player, (WorldMapMessage)new ClientboundTrackedPlayerPacket(true, toRemove, 0.0D, 0.0D, 0.0D, null, playerData.getClientModNetworkVersion()));
/*     */   }
/*     */   
/*     */   private void sendTrackedPlayerPacket(class_3222 player, ServerPlayerData playerData, SyncedTrackedPlayer tracked) {
/* 113 */     WorldMap.messageHandler.sendToPlayer(player, (WorldMapMessage)new ClientboundTrackedPlayerPacket(false, tracked.getId(), tracked.getX(), tracked.getY(), tracked.getZ(), tracked.getDimension().method_29177(), playerData.getClientModNetworkVersion()));
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\server\radar\tracker\SyncedPlayerTracker.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */