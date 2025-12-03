/*    */ package xaero.map.message;
/*    */ 
/*    */ import java.util.function.BiConsumer;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.class_2540;
/*    */ import xaero.map.message.client.ClientMessageConsumer;
/*    */ import xaero.map.message.server.ServerMessageConsumer;
/*    */ import xaero.map.message.type.WorldMapMessageType;
/*    */ import xaero.map.message.type.WorldMapMessageTypeManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class WorldMapMessageHandlerFull
/*    */   extends WorldMapMessageHandler
/*    */ {
/* 17 */   protected final WorldMapMessageTypeManager messageTypes = new WorldMapMessageTypeManager();
/*    */ 
/*    */ 
/*    */   
/*    */   public <T extends WorldMapMessage<T>> void register(int index, Class<T> type, ServerMessageConsumer<T> serverHandler, ClientMessageConsumer<T> clientHandler, Function<class_2540, T> decoder, BiConsumer<T, class_2540> encoder) {
/* 22 */     this.messageTypes.register(index, type, serverHandler, clientHandler, decoder, encoder);
/*    */   }
/*    */   
/*    */   public <T extends WorldMapMessage<T>> void encodeMessage(WorldMapMessageType<T> type, WorldMapMessage<?> message, class_2540 buf) {
/* 26 */     buf.method_52997(type.getIndex());
/*    */     
/* 28 */     WorldMapMessage<?> worldMapMessage = message;
/* 29 */     type.getEncoder().accept(worldMapMessage, buf);
/*    */   }
/*    */   
/*    */   public WorldMapMessageType<?> getByIndex(int index) {
/* 33 */     return this.messageTypes.getByIndex(index);
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\message\WorldMapMessageHandlerFull.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */