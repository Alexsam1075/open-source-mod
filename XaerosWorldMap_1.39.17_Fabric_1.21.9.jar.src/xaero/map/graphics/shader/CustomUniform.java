/*    */ package xaero.map.graphics.shader;
/*    */ 
/*    */ import com.mojang.blaze3d.buffers.GpuBufferSlice;
/*    */ import com.mojang.blaze3d.pipeline.RenderPipeline;
/*    */ import net.minecraft.class_10789;
/*    */ import net.minecraft.class_11280;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CustomUniform<T>
/*    */ {
/*    */   private final RenderPipeline.UniformDescription description;
/*    */   private final CustomUniformValueType<T> valueType;
/*    */   private final int initialBlockCount;
/*    */   private class_11280<CustomUniformValue<T>> storage;
/*    */   private CustomUniformValue<T> value;
/*    */   
/*    */   public CustomUniform(RenderPipeline.UniformDescription description, CustomUniformValueType<T> valueType, int initialBlockCount) {
/* 21 */     this.description = description;
/* 22 */     this.valueType = valueType;
/* 23 */     this.initialBlockCount = initialBlockCount;
/*    */   }
/*    */   
/*    */   public RenderPipeline.UniformDescription getDescription() {
/* 27 */     return this.description;
/*    */   }
/*    */   
/*    */   public String name() {
/* 31 */     return this.description.name();
/*    */   }
/*    */   
/*    */   public class_10789 type() {
/* 35 */     return this.description.type();
/*    */   }
/*    */   
/*    */   public T getValue() {
/* 39 */     return (this.value == null) ? null : this.value.getValue();
/*    */   }
/*    */   
/*    */   public void setValue(T value) {
/* 43 */     if (value == null)
/* 44 */       throw new IllegalArgumentException(); 
/* 45 */     T currentValue = (this.value == null) ? null : this.value.getValue();
/* 46 */     if (currentValue != null && this.valueType.checkEquation(currentValue, value))
/*    */       return; 
/* 48 */     this.value = new CustomUniformValue<>(value, this.valueType);
/*    */   }
/*    */   
/*    */   public GpuBufferSlice getBufferSlice() {
/* 52 */     if (this.storage == null)
/* 53 */       this.storage = new class_11280(this.description.name(), this.valueType.getSize(), this.initialBlockCount); 
/* 54 */     return this.storage.method_71102(this.value);
/*    */   }
/*    */   
/*    */   public void endFrame() {
/* 58 */     if (this.storage != null)
/* 59 */       this.storage.method_71100(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\graphics\shader\CustomUniform.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */