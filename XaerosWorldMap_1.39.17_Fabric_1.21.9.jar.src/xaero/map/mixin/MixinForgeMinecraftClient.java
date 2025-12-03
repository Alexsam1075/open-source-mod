/*    */ package xaero.map.mixin;
/*    */ 
/*    */ import net.minecraft.class_310;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.ModifyArg;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ import xaero.map.core.XaeroWorldMapCore;
/*    */ 
/*    */ @Mixin({class_310.class})
/*    */ public class MixinForgeMinecraftClient
/*    */ {
/*    */   @Inject(at = {@At("HEAD")}, method = {"runTick"})
/*    */   public void onRunTickStart(CallbackInfo info) {
/* 16 */     XaeroWorldMapCore.onMinecraftRunTick();
/*    */   }
/*    */   
/*    */   @ModifyArg(method = {"runTick"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GameRenderer;render(Lnet/minecraft/client/DeltaTracker;Z)V"), index = 1)
/*    */   public boolean onRenderCall(boolean renderingInGame) {
/* 21 */     return XaeroWorldMapCore.onRenderCall(renderingInGame);
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\mixin\MixinForgeMinecraftClient.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */