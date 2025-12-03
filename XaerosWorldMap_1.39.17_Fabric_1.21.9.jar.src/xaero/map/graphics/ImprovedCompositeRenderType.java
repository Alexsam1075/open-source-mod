/*    */ package xaero.map.graphics;
/*    */ 
/*    */ import com.mojang.blaze3d.pipeline.RenderPipeline;
/*    */ import com.mojang.blaze3d.vertex.VertexFormat;
/*    */ import javax.annotation.Nonnull;
/*    */ import net.minecraft.class_1921;
/*    */ import net.minecraft.class_4668;
/*    */ import net.minecraft.class_9801;
/*    */ import xaero.map.core.ICompositeRenderType;
/*    */ import xaero.map.core.ICompositeState;
/*    */ import xaero.map.render.util.ImmediateRenderUtil;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ImprovedCompositeRenderType
/*    */   extends class_1921
/*    */   implements ICompositeRenderType
/*    */ {
/*    */   private final class_1921 vanillaCompositeRenderType;
/*    */   private final RenderPipeline renderPipeline;
/*    */   private final class_1921.class_4688 compositeState;
/*    */   private final class_4668.class_4678 outputStateShard;
/*    */   
/*    */   public ImprovedCompositeRenderType(String name, int bufferSize, boolean affectsCrumbling, boolean sortOnUpload, RenderPipeline renderPipeline, class_4668.class_4678 outputStateShard, class_1921.class_4688 compositeState, class_1921 vanillaCompositeRenderType) {
/* 31 */     super(name, bufferSize, affectsCrumbling, sortOnUpload, vanillaCompositeRenderType
/*    */         
/* 33 */         ::method_23516, vanillaCompositeRenderType
/* 34 */         ::method_23518);
/*    */     
/* 36 */     this.renderPipeline = renderPipeline;
/* 37 */     this.outputStateShard = outputStateShard;
/* 38 */     this.compositeState = compositeState;
/* 39 */     this.vanillaCompositeRenderType = vanillaCompositeRenderType;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void method_60895(@Nonnull class_9801 meshData) {
/* 47 */     method_23516();
/* 48 */     ImmediateRenderUtil.drawImmediateMeshData(meshData, this.renderPipeline, this.outputStateShard
/* 49 */         .method_68491());
/*    */     
/* 51 */     method_23518();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public VertexFormat method_23031() {
/* 57 */     return this.vanillaCompositeRenderType.method_23031();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public VertexFormat.class_5596 method_23033() {
/* 63 */     return this.vanillaCompositeRenderType.method_23033();
/*    */   }
/*    */ 
/*    */   
/*    */   public RenderPipeline method_73243() {
/* 68 */     return this.renderPipeline;
/*    */   }
/*    */ 
/*    */   
/*    */   public ICompositeState xaero_wm_getState() {
/* 73 */     return (ICompositeState)this.compositeState;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\graphics\ImprovedCompositeRenderType.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */