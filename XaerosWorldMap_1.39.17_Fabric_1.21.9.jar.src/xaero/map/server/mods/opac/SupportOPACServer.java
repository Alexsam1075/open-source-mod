/*    */ package xaero.map.server.mods.opac;
/*    */ 
/*    */ import net.minecraft.class_3222;
/*    */ import xaero.map.server.player.ServerPlayerData;
/*    */ import xaero.pac.common.server.api.OpenPACServerAPI;
/*    */ import xaero.pac.common.server.player.config.api.IPlayerConfigAPI;
/*    */ import xaero.pac.common.server.player.config.api.IPlayerConfigManagerAPI;
/*    */ import xaero.pac.common.server.player.config.api.PlayerConfigOptions;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SupportOPACServer
/*    */ {
/*    */   public boolean isPositionSyncAllowed(int relationship, ServerPlayerData fromPlayerData, boolean receive) {
/* 17 */     if (!receive)
/* 18 */       return false; 
/* 19 */     if (relationship <= 0)
/* 20 */       return false; 
/* 21 */     ServerPlayerOpacData fromPlayerOpacData = getPlayerOpacData(fromPlayerData);
/* 22 */     if (relationship == 1 && !fromPlayerOpacData.shareLocationWithMutualAllies)
/* 23 */       return false; 
/* 24 */     if (relationship > 1 && !fromPlayerOpacData.shareLocationWithParty)
/* 25 */       return false; 
/* 26 */     return true;
/*    */   }
/*    */   
/*    */   public boolean getReceiveLocationsFromMutualAlliesConfigValue(class_3222 player) {
/* 30 */     IPlayerConfigManagerAPI configManager = OpenPACServerAPI.get(player.method_51469().method_8503()).getPlayerConfigs();
/* 31 */     IPlayerConfigAPI config = configManager.getLoadedConfig(player.method_5667());
/* 32 */     return ((Boolean)config.getEffective(PlayerConfigOptions.RECEIVE_LOCATIONS_FROM_PARTY_MUTUAL_ALLIES)).booleanValue();
/*    */   }
/*    */   
/*    */   public boolean getReceiveLocationsFromPartyConfigValue(class_3222 player) {
/* 36 */     IPlayerConfigManagerAPI configManager = OpenPACServerAPI.get(player.method_51469().method_8503()).getPlayerConfigs();
/* 37 */     IPlayerConfigAPI config = configManager.getLoadedConfig(player.method_5667());
/* 38 */     return ((Boolean)config.getEffective(PlayerConfigOptions.RECEIVE_LOCATIONS_FROM_PARTY)).booleanValue();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateShareLocationConfigValues(class_3222 player, ServerPlayerData playerData) {
/* 44 */     ServerPlayerOpacData opacData = getPlayerOpacData(playerData);
/* 45 */     IPlayerConfigManagerAPI configManager = OpenPACServerAPI.get(player.method_51469().method_8503()).getPlayerConfigs();
/* 46 */     IPlayerConfigAPI config = configManager.getLoadedConfig(player.method_5667());
/* 47 */     opacData.shareLocationWithParty = ((Boolean)config.getEffective(PlayerConfigOptions.SHARE_LOCATION_WITH_PARTY)).booleanValue();
/* 48 */     opacData.shareLocationWithMutualAllies = ((Boolean)config.getEffective(PlayerConfigOptions.SHARE_LOCATION_WITH_PARTY_MUTUAL_ALLIES)).booleanValue();
/*    */   }
/*    */   
/*    */   private ServerPlayerOpacData getPlayerOpacData(ServerPlayerData playerData) {
/* 52 */     ServerPlayerOpacData opacData = (ServerPlayerOpacData)playerData.getOpacData();
/* 53 */     if (opacData == null)
/* 54 */       playerData.setOpacData(opacData = new ServerPlayerOpacData()); 
/* 55 */     return opacData;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\server\mods\opac\SupportOPACServer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */