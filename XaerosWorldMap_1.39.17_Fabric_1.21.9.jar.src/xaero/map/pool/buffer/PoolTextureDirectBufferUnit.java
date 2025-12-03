/*    */ package xaero.map.pool.buffer;
/*    */ 
/*    */ import java.nio.ByteBuffer;
/*    */ import org.lwjgl.BufferUtils;
/*    */ import xaero.map.pool.PoolUnit;
/*    */ 
/*    */ 
/*    */ public class PoolTextureDirectBufferUnit
/*    */   implements PoolUnit
/*    */ {
/*    */   private ByteBuffer directBuffer;
/*    */   
/*    */   public PoolTextureDirectBufferUnit(Object... args) {
/* 14 */     create(args);
/*    */   }
/*    */   
/*    */   public ByteBuffer getDirectBuffer() {
/* 18 */     return this.directBuffer;
/*    */   }
/*    */   
/*    */   public void reset() {
/* 22 */     this.directBuffer.clear();
/* 23 */     BufferUtils.zeroBuffer(this.directBuffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public void create(Object... args) {
/* 28 */     if (this.directBuffer == null) {
/* 29 */       this.directBuffer = createBuffer();
/*    */     } else {
/* 31 */       this.directBuffer.clear();
/* 32 */       if (((Boolean)args[0]).booleanValue())
/* 33 */         BufferUtils.zeroBuffer(this.directBuffer); 
/*    */     } 
/*    */   }
/*    */   
/*    */   public static ByteBuffer createBuffer() {
/* 38 */     return BufferUtils.createByteBuffer(16384);
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\pool\buffer\PoolTextureDirectBufferUnit.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */