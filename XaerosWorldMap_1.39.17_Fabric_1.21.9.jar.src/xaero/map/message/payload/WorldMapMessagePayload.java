/*    */ package xaero.map.message.payload;
/*    */ 
/*    */ import net.minecraft.class_8710;
/*    */ import xaero.map.message.WorldMapMessage;
/*    */ import xaero.map.message.type.WorldMapMessageType;
/*    */ 
/*    */ public class WorldMapMessagePayload<T extends WorldMapMessage<T>>
/*    */   implements class_8710 {
/*  9 */   public static class_8710.class_9154<WorldMapMessagePayload<?>> TYPE = new class_8710.class_9154(WorldMapMessage.MAIN_CHANNEL);
/*    */   
/*    */   private final WorldMapMessageType<T> type;
/*    */   private final T msg;
/*    */   
/*    */   public WorldMapMessagePayload(WorldMapMessageType<T> type, T msg) {
/* 15 */     this.type = type;
/* 16 */     this.msg = msg;
/*    */   }
/*    */   
/*    */   public WorldMapMessageType<T> getType() {
/* 20 */     return this.type;
/*    */   }
/*    */   
/*    */   public T getMsg() {
/* 24 */     return this.msg;
/*    */   }
/*    */   
/*    */   public class_8710.class_9154<? extends class_8710> method_56479() {
/* 28 */     return (class_8710.class_9154)TYPE;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\message\payload\WorldMapMessagePayload.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */