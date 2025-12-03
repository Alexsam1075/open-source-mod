/*    */ package xaero.map.mixin;
/*    */ 
/*    */ import net.minecraft.class_11228;
/*    */ import net.minecraft.class_757;
/*    */ import net.minecraft.class_758;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import xaero.map.core.IGameRenderer;
/*    */ 
/*    */ 
/*    */ @Mixin(value = {class_757.class}, priority = 1000001)
/*    */ public class MixinGameRenderer
/*    */   implements IGameRenderer
/*    */ {
/*    */   @Shadow
/*    */   private class_11228 field_59965;
/*    */   @Shadow
/*    */   private class_758 field_60793;
/*    */   
/*    */   public class_11228 xaero_wm_getGuiRenderer() {
/* 21 */     return this.field_59965;
/*    */   }
/*    */ 
/*    */   
/*    */   public class_758 xaero_wm_getFogRenderer() {
/* 26 */     return this.field_60793;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\mixin\MixinGameRenderer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */