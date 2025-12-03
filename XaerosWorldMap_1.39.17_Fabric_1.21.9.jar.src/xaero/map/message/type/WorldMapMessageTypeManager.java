/*    */ package xaero.map.message.type;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.function.BiConsumer;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.class_2540;
/*    */ import xaero.map.message.client.ClientMessageConsumer;
/*    */ import xaero.map.message.server.ServerMessageConsumer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldMapMessageTypeManager
/*    */ {
/* 19 */   private final Map<Integer, WorldMapMessageType<?>> typeByIndex = new HashMap<>();
/* 20 */   private final Map<Class<?>, WorldMapMessageType<?>> typeByClass = new HashMap<>();
/*    */ 
/*    */   
/*    */   public <T extends xaero.map.message.WorldMapMessage<T>> void register(int index, Class<T> type, ServerMessageConsumer<T> serverHandler, ClientMessageConsumer<T> clientHandler, Function<class_2540, T> decoder, BiConsumer<T, class_2540> encoder) {
/* 24 */     WorldMapMessageType<T> messageType = new WorldMapMessageType<>(index, type, serverHandler, clientHandler, decoder, encoder);
/* 25 */     this.typeByIndex.put(Integer.valueOf(index), messageType);
/* 26 */     this.typeByClass.put(type, messageType);
/*    */   }
/*    */   
/*    */   public WorldMapMessageType<?> getByIndex(int index) {
/* 30 */     return this.typeByIndex.get(Integer.valueOf(index));
/*    */   }
/*    */   
/*    */   public WorldMapMessageType<?> getByClass(Class<?> clazz) {
/* 34 */     return this.typeByClass.get(clazz);
/*    */   }
/*    */ 
/*    */   
/*    */   public <T extends xaero.map.message.WorldMapMessage<T>> WorldMapMessageType<T> getType(T message) {
/* 39 */     return (WorldMapMessageType<T>)this.typeByClass.get(message.getClass());
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\message\type\WorldMapMessageTypeManager.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */