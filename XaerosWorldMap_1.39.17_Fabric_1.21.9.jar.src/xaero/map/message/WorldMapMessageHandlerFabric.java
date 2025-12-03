/*    */ package xaero.map.message;
/*    */ 
/*    */ import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
/*    */ import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
/*    */ import net.minecraft.class_3222;
/*    */ import net.minecraft.class_8710;
/*    */ import xaero.map.message.payload.WorldMapMessagePayload;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldMapMessageHandlerFabric
/*    */   extends WorldMapMessageHandlerFull
/*    */ {
/*    */   public static final int NETWORK_COMPATIBILITY = 2;
/*    */   
/*    */   public <T extends WorldMapMessage<T>> void sendToPlayer(class_3222 player, T message) {
/* 18 */     ServerPlayNetworking.send(player, (class_8710)new WorldMapMessagePayload(this.messageTypes.getType((WorldMapMessage)message), (WorldMapMessage)message));
/*    */   }
/*    */ 
/*    */   
/*    */   public <T extends WorldMapMessage<T>> void sendToServer(T message) {
/* 23 */     ClientPlayNetworking.send((class_8710)new WorldMapMessagePayload(this.messageTypes.getType((WorldMapMessage)message), (WorldMapMessage)message));
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\message\WorldMapMessageHandlerFabric.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */