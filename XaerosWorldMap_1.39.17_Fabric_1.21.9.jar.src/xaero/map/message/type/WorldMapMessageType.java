/*    */ package xaero.map.message.type;
/*    */ 
/*    */ import java.util.function.BiConsumer;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.class_2540;
/*    */ import xaero.map.message.WorldMapMessage;
/*    */ import xaero.map.message.client.ClientMessageConsumer;
/*    */ import xaero.map.message.server.ServerMessageConsumer;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldMapMessageType<T extends WorldMapMessage<T>>
/*    */ {
/*    */   private final int index;
/*    */   private final Class<T> type;
/*    */   private final ClientMessageConsumer<T> clientHandler;
/*    */   private final ServerMessageConsumer<T> serverHandler;
/*    */   private final Function<class_2540, T> decoder;
/*    */   private final BiConsumer<T, class_2540> encoder;
/*    */   
/*    */   public WorldMapMessageType(int index, Class<T> type, ServerMessageConsumer<T> serverHandler, ClientMessageConsumer<T> clientHandler, Function<class_2540, T> decoder, BiConsumer<T, class_2540> encoder) {
/* 22 */     this.index = index;
/* 23 */     this.type = type;
/* 24 */     this.serverHandler = serverHandler;
/* 25 */     this.clientHandler = clientHandler;
/* 26 */     this.decoder = decoder;
/* 27 */     this.encoder = encoder;
/*    */   }
/*    */   
/*    */   public int getIndex() {
/* 31 */     return this.index;
/*    */   }
/*    */   
/*    */   public Class<T> getType() {
/* 35 */     return this.type;
/*    */   }
/*    */   
/*    */   public ClientMessageConsumer<T> getClientHandler() {
/* 39 */     return this.clientHandler;
/*    */   }
/*    */   
/*    */   public ServerMessageConsumer<T> getServerHandler() {
/* 43 */     return this.serverHandler;
/*    */   }
/*    */   
/*    */   public Function<class_2540, T> getDecoder() {
/* 47 */     return this.decoder;
/*    */   }
/*    */   
/*    */   public BiConsumer<T, class_2540> getEncoder() {
/* 51 */     return this.encoder;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\message\type\WorldMapMessageType.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */