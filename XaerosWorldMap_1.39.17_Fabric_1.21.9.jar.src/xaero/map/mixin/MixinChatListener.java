/*    */ package xaero.map.mixin;
/*    */ 
/*    */ import net.minecraft.class_2556;
/*    */ import net.minecraft.class_2561;
/*    */ import net.minecraft.class_7594;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ import xaero.map.core.XaeroWorldMapCore;
/*    */ 
/*    */ @Mixin({class_7594.class})
/*    */ public class MixinChatListener
/*    */ {
/*    */   @Inject(method = {"handleDisguisedChatMessage"}, cancellable = true, at = {@At("HEAD")})
/*    */   public void onHandleDisguisedChatMessag(class_2561 component, class_2556.class_7602 bound, CallbackInfo info) {
/* 17 */     if (!XaeroWorldMapCore.onHandleDisguisedChatMessage(bound, component))
/* 18 */       info.cancel(); 
/*    */   }
/*    */   
/*    */   @Inject(method = {"handleSystemMessage"}, cancellable = true, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/ChatComponent;addMessage(Lnet/minecraft/network/chat/Component;)V")})
/*    */   public void onHandleSystemChat(class_2561 component, boolean bl, CallbackInfo info) {
/* 23 */     if (XaeroWorldMapCore.onSystemChat(component))
/* 24 */       info.cancel(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\mixin\MixinChatListener.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */