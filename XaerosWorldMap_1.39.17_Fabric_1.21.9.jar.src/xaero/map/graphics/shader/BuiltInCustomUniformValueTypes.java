/*    */ package xaero.map.graphics.shader;
/*    */ 
/*    */ import java.nio.ByteBuffer;
/*    */ import org.joml.Vector2f;
/*    */ 
/*    */ 
/*    */ public class BuiltInCustomUniformValueTypes
/*    */ {
/*  9 */   public static final CustomUniformValueType<Float> FLOAT = new CustomUniformValueType<>(4, ByteBuffer::putFloat); static {
/* 10 */     VEC_2F = new CustomUniformValueType<>(8, (buffer, value) -> {
/*    */           buffer.putFloat(value.x);
/*    */           buffer.putFloat(value.y);
/*    */         });
/*    */   }
/*    */   public static final CustomUniformValueType<Vector2f> VEC_2F;
/* 16 */   public static final CustomUniformValueType<Integer> INT = new CustomUniformValueType<>(4, ByteBuffer::putInt);
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\graphics\shader\BuiltInCustomUniformValueTypes.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */