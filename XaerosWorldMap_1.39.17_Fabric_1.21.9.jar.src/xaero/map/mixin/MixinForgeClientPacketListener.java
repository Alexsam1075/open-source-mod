/*    */ package xaero.map.mixin;
/*    */ 
/*    */ import net.minecraft.class_2678;
/*    */ import net.minecraft.class_634;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ import xaero.map.core.XaeroWorldMapCore;
/*    */ 
/*    */ @Mixin({class_634.class})
/*    */ public class MixinForgeClientPacketListener
/*    */ {
/*    */   @Inject(at = {@At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/network/protocol/PacketUtils;ensureRunningOnSameThread(Lnet/minecraft/network/protocol/Packet;Lnet/minecraft/network/PacketListener;Lnet/minecraft/network/PacketProcessor;)V")}, method = {"handleLogin"})
/*    */   public void onOnGameJoin(class_2678 packet, CallbackInfo info) {
/* 16 */     XaeroWorldMapCore.onPlayNetHandler((class_634)this, packet);
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\mixin\MixinForgeClientPacketListener.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */