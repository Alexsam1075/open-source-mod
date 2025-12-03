/*    */ package xaero.map.mixin;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import net.minecraft.class_1921;
/*    */ import net.minecraft.class_4668;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import xaero.map.core.ICompositeState;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_1921.class_4688.class})
/*    */ public class MixinCompositeState
/*    */   implements ICompositeState
/*    */ {
/*    */   @Shadow
/*    */   private class_4668.class_4678 field_57931;
/*    */   @Shadow
/*    */   private ImmutableList<class_4668> field_21422;
/*    */   
/*    */   public ImmutableList<class_4668> xaero_wm_getStates() {
/* 25 */     return this.field_21422;
/*    */   }
/*    */ 
/*    */   
/*    */   public Object xaero_wm_getOutputState() {
/* 30 */     return this.field_57931;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\mixin\MixinCompositeState.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */