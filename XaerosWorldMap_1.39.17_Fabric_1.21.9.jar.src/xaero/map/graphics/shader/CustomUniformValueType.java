/*    */ package xaero.map.graphics.shader;
/*    */ 
/*    */ import java.nio.ByteBuffer;
/*    */ import java.util.Objects;
/*    */ import java.util.function.BiConsumer;
/*    */ import java.util.function.BiPredicate;
/*    */ 
/*    */ public class CustomUniformValueType<T>
/*    */ {
/*    */   private final int size;
/*    */   private final BiConsumer<ByteBuffer, T> writer;
/*    */   private final BiPredicate<T, T> equationChecker;
/*    */   
/*    */   public CustomUniformValueType(int size, BiConsumer<ByteBuffer, T> writer) {
/* 15 */     this(size, writer, Objects::equals);
/*    */   }
/*    */   
/*    */   public CustomUniformValueType(int size, BiConsumer<ByteBuffer, T> writer, BiPredicate<T, T> equationChecker) {
/* 19 */     this.size = size;
/* 20 */     this.writer = writer;
/* 21 */     this.equationChecker = equationChecker;
/*    */   }
/*    */   
/*    */   public int getSize() {
/* 25 */     return this.size;
/*    */   }
/*    */   
/*    */   public BiConsumer<ByteBuffer, T> getWriter() {
/* 29 */     return this.writer;
/*    */   }
/*    */   
/*    */   public boolean checkEquation(T currentValue, T value) {
/* 33 */     return this.equationChecker.test(currentValue, value);
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\graphics\shader\CustomUniformValueType.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */