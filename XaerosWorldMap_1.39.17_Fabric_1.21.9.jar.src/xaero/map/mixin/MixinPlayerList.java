/*    */ package xaero.map.mixin;
/*    */ 
/*    */ import net.minecraft.class_1657;
/*    */ import net.minecraft.class_3218;
/*    */ import net.minecraft.class_3222;
/*    */ import net.minecraft.class_3324;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ import xaero.map.server.core.XaeroWorldMapServerCore;
/*    */ 
/*    */ @Mixin({class_3324.class})
/*    */ public class MixinPlayerList {
/*    */   @Inject(at = {@At("HEAD")}, method = {"sendLevelInfo"})
/*    */   public void onSendWorldInfo(class_3222 player, class_3218 world, CallbackInfo info) {
/* 17 */     XaeroWorldMapServerCore.onServerWorldInfo((class_1657)player);
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\mixin\MixinPlayerList.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */