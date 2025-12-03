/*    */ package xaero.map.mixin;
/*    */ 
/*    */ import net.minecraft.class_3222;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import xaero.map.server.player.IServerPlayer;
/*    */ import xaero.map.server.player.ServerPlayerData;
/*    */ 
/*    */ 
/*    */ @Mixin({class_3222.class})
/*    */ public class MixinServerPlayer
/*    */   implements IServerPlayer
/*    */ {
/*    */   private ServerPlayerData xaeroWorldMapPlayerData;
/*    */   
/*    */   public ServerPlayerData getXaeroWorldMapPlayerData() {
/* 16 */     return this.xaeroWorldMapPlayerData;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setXaeroWorldMapPlayerData(ServerPlayerData data) {
/* 21 */     this.xaeroWorldMapPlayerData = data;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\mixin\MixinServerPlayer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */