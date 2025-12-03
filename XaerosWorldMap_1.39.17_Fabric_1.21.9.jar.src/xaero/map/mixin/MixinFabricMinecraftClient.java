/*    */ package xaero.map.mixin;
/*    */ 
/*    */ import net.minecraft.class_1936;
/*    */ import net.minecraft.class_310;
/*    */ import net.minecraft.class_437;
/*    */ import net.minecraft.class_638;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.ModifyArg;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ import xaero.map.WorldMap;
/*    */ import xaero.map.WorldMapFabric;
/*    */ import xaero.map.core.XaeroWorldMapCore;
/*    */ import xaero.map.core.XaeroWorldMapCoreFabric;
/*    */ 
/*    */ 
/*    */ @Mixin({class_310.class})
/*    */ public class MixinFabricMinecraftClient
/*    */ {
/*    */   @Shadow
/*    */   public class_437 field_1755;
/*    */   @Shadow
/*    */   public class_638 field_1687;
/*    */   
/*    */   @Shadow
/*    */   public void method_1507(class_437 screen) {}
/*    */   
/*    */   @Inject(at = {@At("HEAD")}, method = {"tick"})
/*    */   public void onTickStart(CallbackInfo info) {
/* 32 */     if (WorldMap.loaded)
/* 33 */       WorldMap.events.handleClientTickStart(); 
/*    */   }
/*    */   
/*    */   @Inject(at = {@At("HEAD")}, method = {"runTick"})
/*    */   public void onRunTickStart(CallbackInfo info) {
/* 38 */     XaeroWorldMapCoreFabric.onMinecraftRunTick();
/*    */   }
/*    */   
/*    */   @Inject(at = {@At("HEAD")}, method = {"setScreen"}, cancellable = true)
/*    */   public void onOpenScreen(class_437 screen_1, CallbackInfo info) {
/* 43 */     if (WorldMap.loaded) {
/* 44 */       class_437 resultScreen = WorldMap.events.handleGuiOpen(screen_1);
/* 45 */       if (screen_1 != resultScreen) {
/* 46 */         method_1507(resultScreen);
/* 47 */         info.cancel();
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   @Inject(at = {@At("HEAD")}, method = {"disconnect(Lnet/minecraft/client/gui/screens/Screen;Z)V"})
/*    */   public void onDisconnect(class_437 screen_1, boolean b, CallbackInfo info) {
/* 54 */     if (this.field_1687 != null) {
/* 55 */       if (!WorldMap.loaded)
/*    */         return; 
/* 57 */       WorldMap.events.handleWorldUnload((class_1936)this.field_1687);
/*    */     } 
/*    */   }
/*    */   
/*    */   @Inject(at = {@At("HEAD")}, method = {"setLevel"})
/*    */   public void onJoinWorld(class_638 newWorld, CallbackInfo info) {
/* 63 */     if (this.field_1687 != null) {
/* 64 */       if (!WorldMap.loaded)
/*    */         return; 
/* 66 */       WorldMap.events.handleWorldUnload((class_1936)this.field_1687);
/*    */     } 
/*    */   }
/*    */   
/*    */   @ModifyArg(method = {"runTick"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GameRenderer;render(Lnet/minecraft/client/DeltaTracker;Z)V"), index = 1)
/*    */   public boolean onRenderCall(boolean renderingInGame) {
/* 72 */     if (WorldMap.INSTANCE != null)
/* 73 */       ((WorldMapFabric)WorldMap.INSTANCE).tryLoadLater(); 
/* 74 */     return XaeroWorldMapCore.onRenderCall(renderingInGame);
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\mixin\MixinFabricMinecraftClient.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */