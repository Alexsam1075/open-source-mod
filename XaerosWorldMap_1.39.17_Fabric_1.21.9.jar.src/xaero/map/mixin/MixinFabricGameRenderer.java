/*    */ package xaero.map.mixin;
/*    */ 
/*    */ import net.minecraft.class_310;
/*    */ import net.minecraft.class_757;
/*    */ import net.minecraft.class_9779;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ import xaero.map.WorldMap;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin(value = {class_757.class}, priority = 1000001)
/*    */ public class MixinFabricGameRenderer
/*    */ {
/*    */   @Shadow
/*    */   private class_310 field_4015;
/*    */   
/*    */   @Inject(at = {@At("RETURN")}, method = {"render"})
/*    */   public void onRenderEnd(class_9779 deltaTracker, boolean boolean_1, CallbackInfo info) {
/* 25 */     if (!WorldMap.loaded)
/*    */       return; 
/* 27 */     WorldMap.events.handleRenderTick(false);
/* 28 */     if (!this.field_4015.field_1743 && this.field_4015.method_18506() == null && this.field_4015.field_1755 != null)
/* 29 */       WorldMap.events.handleDrawScreen(this.field_4015.field_1755); 
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\mixin\MixinFabricGameRenderer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */