/*    */ package xaero.map.mixin;
/*    */ 
/*    */ import net.minecraft.class_329;
/*    */ import net.minecraft.class_332;
/*    */ import net.minecraft.class_9779;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ import xaero.map.core.XaeroWorldMapCore;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_329.class})
/*    */ public class MixinOptionalGui
/*    */ {
/*    */   @Inject(at = {@At("HEAD")}, method = {"renderCrosshair"}, cancellable = true)
/*    */   public void onRenderCrosshair(class_332 guiGraphics, class_9779 deltaTracker, CallbackInfo info) {
/* 22 */     if (XaeroWorldMapCore.onRenderCrosshair(guiGraphics))
/* 23 */       info.cancel(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\mixin\MixinOptionalGui.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */