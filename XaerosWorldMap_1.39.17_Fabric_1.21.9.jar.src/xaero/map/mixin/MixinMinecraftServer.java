/*    */ package xaero.map.mixin;
/*    */ 
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import xaero.map.server.IMinecraftServer;
/*    */ import xaero.map.server.MinecraftServerData;
/*    */ 
/*    */ @Mixin({MinecraftServer.class})
/*    */ public class MixinMinecraftServer
/*    */   implements IMinecraftServer
/*    */ {
/*    */   private MinecraftServerData xaeroWorldMapServerData;
/*    */   
/*    */   public MinecraftServerData getXaeroWorldMapServerData() {
/* 15 */     return this.xaeroWorldMapServerData;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setXaeroWorldMapServerData(MinecraftServerData data) {
/* 20 */     this.xaeroWorldMapServerData = data;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\mixin\MixinMinecraftServer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */