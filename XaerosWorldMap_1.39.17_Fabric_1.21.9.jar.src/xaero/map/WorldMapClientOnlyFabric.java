/*    */ package xaero.map;
/*    */ 
/*    */ import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
/*    */ import xaero.map.message.client.WorldMapPayloadClientHandler;
/*    */ import xaero.map.message.payload.WorldMapMessagePayload;
/*    */ 
/*    */ public class WorldMapClientOnlyFabric
/*    */   extends WorldMapClientOnly
/*    */ {
/*    */   public void preInit(String modId) {
/* 11 */     super.preInit(modId);
/*    */     
/* 13 */     ClientPlayNetworking.registerGlobalReceiver(WorldMapMessagePayload.TYPE, (ClientPlayNetworking.PlayPayloadHandler)new WorldMapPayloadClientHandler());
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\WorldMapClientOnlyFabric.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */