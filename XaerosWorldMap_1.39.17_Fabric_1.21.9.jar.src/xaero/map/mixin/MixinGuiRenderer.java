/*    */ package xaero.map.mixin;
/*    */ 
/*    */ import net.minecraft.class_11228;
/*    */ import net.minecraft.class_11278;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import xaero.map.core.IGuiRenderer;
/*    */ 
/*    */ @Mixin({class_11228.class})
/*    */ public class MixinGuiRenderer
/*    */   implements IGuiRenderer
/*    */ {
/*    */   @Shadow
/*    */   private class_11278 field_60040;
/*    */   
/*    */   public class_11278 xaero_wm_getGuiProjectionMatrixBuffer() {
/* 17 */     return this.field_60040;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\mixin\MixinGuiRenderer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */