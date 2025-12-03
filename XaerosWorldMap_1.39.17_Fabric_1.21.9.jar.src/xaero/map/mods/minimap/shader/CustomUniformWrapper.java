/*    */ package xaero.map.mods.minimap.shader;
/*    */ 
/*    */ import com.mojang.blaze3d.buffers.GpuBufferSlice;
/*    */ import com.mojang.blaze3d.pipeline.RenderPipeline;
/*    */ import net.minecraft.class_10789;
/*    */ import xaero.common.graphics.shader.CustomUniform;
/*    */ import xaero.map.graphics.shader.CustomUniform;
/*    */ 
/*    */ public class CustomUniformWrapper<T>
/*    */   extends CustomUniform<T>
/*    */ {
/*    */   private final CustomUniform<T> worldMapUniform;
/*    */   
/*    */   public CustomUniformWrapper(CustomUniform<T> worldMapUniform) {
/* 15 */     super(worldMapUniform.getDescription(), null, 1);
/* 16 */     this.worldMapUniform = worldMapUniform;
/*    */   }
/*    */ 
/*    */   
/*    */   public RenderPipeline.UniformDescription getDescription() {
/* 21 */     return this.worldMapUniform.getDescription();
/*    */   }
/*    */ 
/*    */   
/*    */   public String name() {
/* 26 */     return this.worldMapUniform.name();
/*    */   }
/*    */ 
/*    */   
/*    */   public class_10789 type() {
/* 31 */     return this.worldMapUniform.type();
/*    */   }
/*    */ 
/*    */   
/*    */   public T getValue() {
/* 36 */     return (T)this.worldMapUniform.getValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public void setValue(T value) {
/* 41 */     this.worldMapUniform.setValue(value);
/*    */   }
/*    */ 
/*    */   
/*    */   public GpuBufferSlice getBufferSlice() {
/* 46 */     return this.worldMapUniform.getBufferSlice();
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\mods\minimap\shader\CustomUniformWrapper.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */