/*    */ package xaero.map.server.player;
/*    */ 
/*    */ import net.minecraft.class_3222;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import xaero.map.server.MinecraftServerData;
/*    */ 
/*    */ public class ServerPlayerTickHandler
/*    */ {
/*    */   public void tick(class_3222 player) {
/* 10 */     MinecraftServer server = player.method_51469().method_8503();
/* 11 */     MinecraftServerData serverData = MinecraftServerData.get(server);
/* 12 */     ServerPlayerData playerData = ServerPlayerData.get(player);
/*    */     
/* 14 */     serverData.getSyncedPlayerTracker().onTick(server, player, serverData, playerData);
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\server\player\ServerPlayerTickHandler.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */