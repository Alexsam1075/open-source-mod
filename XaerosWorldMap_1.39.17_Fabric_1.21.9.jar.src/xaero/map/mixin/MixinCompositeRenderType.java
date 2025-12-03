/*    */ package xaero.map.mixin;
/*    */ 
/*    */ import net.minecraft.class_1921;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import xaero.map.core.ICompositeRenderType;
/*    */ import xaero.map.core.ICompositeState;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin(targets = {"net.minecraft.client.renderer.RenderType$CompositeRenderType"})
/*    */ public class MixinCompositeRenderType
/*    */   implements ICompositeRenderType
/*    */ {
/*    */   @Shadow
/*    */   private class_1921.class_4688 field_21403;
/*    */   
/*    */   public ICompositeState xaero_wm_getState() {
/* 21 */     return (ICompositeState)this.field_21403;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\mixin\MixinCompositeRenderType.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */