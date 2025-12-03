/*    */ package xaero.map.mixin;
/*    */ 
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.time.Instant;
/*    */ import net.minecraft.class_2556;
/*    */ import net.minecraft.class_2561;
/*    */ import net.minecraft.class_7471;
/*    */ import net.minecraft.class_7594;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ import xaero.map.WorldMap;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_7594.class})
/*    */ public class MixinFabricChatListener
/*    */ {
/*    */   @Inject(method = {"showMessageToPlayer"}, cancellable = true, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/ChatComponent;addMessage(Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/MessageSignature;Lnet/minecraft/client/GuiMessageTag;)V")})
/*    */   public void onShowMessageToPlayer(class_2556.class_7602 bound, class_7471 playerChatMessage, class_2561 component, GameProfile gameProfile, boolean bl, Instant instant, CallbackInfoReturnable<Boolean> info) {
/* 23 */     if (!WorldMap.loaded)
/*    */       return; 
/* 25 */     if (WorldMap.events.handleClientPlayerChatReceivedEvent(bound, component, gameProfile))
/* 26 */       info.setReturnValue(Boolean.valueOf(false)); 
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\mixin\MixinFabricChatListener.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */