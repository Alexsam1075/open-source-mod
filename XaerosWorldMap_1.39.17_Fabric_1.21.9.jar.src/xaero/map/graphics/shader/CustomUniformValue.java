/*    */ package xaero.map.graphics.shader;
/*    */ 
/*    */ import java.nio.ByteBuffer;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nonnull;
/*    */ import net.minecraft.class_11280;
/*    */ 
/*    */ public class CustomUniformValue<T>
/*    */   implements class_11280.class_11281
/*    */ {
/*    */   private final T value;
/*    */   private final CustomUniformValueType<T> valueType;
/*    */   
/*    */   public CustomUniformValue(T value, CustomUniformValueType<T> valueType) {
/* 15 */     this.value = value;
/* 16 */     this.valueType = valueType;
/*    */   }
/*    */   
/*    */   public T getValue() {
/* 20 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 25 */     if (this == o) return true; 
/* 26 */     if (o == null || getClass() != o.getClass()) return false; 
/* 27 */     CustomUniformValue<?> that = (CustomUniformValue)o;
/* 28 */     return Objects.equals(this.value, that.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 33 */     return Objects.hashCode(this.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public void method_71104(@Nonnull ByteBuffer buffer) {
/* 38 */     this.valueType.getWriter().accept(buffer, this.value);
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\graphics\shader\CustomUniformValue.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */