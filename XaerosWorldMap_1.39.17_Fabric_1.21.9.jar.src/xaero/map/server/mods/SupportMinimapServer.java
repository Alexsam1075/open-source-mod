/*    */ package xaero.map.server.mods;
/*    */ 
/*    */ import net.minecraft.class_3222;
/*    */ import xaero.common.server.XaeroMinimapServer;
/*    */ import xaero.common.server.player.ServerPlayerData;
/*    */ 
/*    */ public class SupportMinimapServer
/*    */ {
/*    */   private final int compatibilityVersion;
/*    */   
/*    */   public SupportMinimapServer() {
/* 12 */     int compatibilityVersion = 0;
/*    */     try {
/* 14 */       compatibilityVersion = XaeroMinimapServer.SERVER_COMPATIBILITY;
/* 15 */     } catch (Throwable throwable) {}
/*    */     
/* 17 */     this.compatibilityVersion = compatibilityVersion;
/*    */   }
/*    */   
/*    */   public boolean supportsTrackedPlayers() {
/* 21 */     return (this.compatibilityVersion >= 1);
/*    */   }
/*    */   
/*    */   public boolean playerSupportsTrackedPlayers(class_3222 player) {
/* 25 */     ServerPlayerData playerData = ServerPlayerData.get(player);
/* 26 */     return playerData.hasMod();
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\server\mods\SupportMinimapServer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */