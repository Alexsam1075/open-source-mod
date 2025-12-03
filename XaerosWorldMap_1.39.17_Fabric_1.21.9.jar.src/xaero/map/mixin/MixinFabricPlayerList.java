/*    */ package xaero.map.mixin;
/*    */ 
/*    */ import net.minecraft.class_1657;
/*    */ import net.minecraft.class_2535;
/*    */ import net.minecraft.class_3222;
/*    */ import net.minecraft.class_3324;
/*    */ import net.minecraft.class_8792;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ import xaero.map.WorldMap;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_3324.class})
/*    */ public class MixinFabricPlayerList
/*    */ {
/*    */   @Inject(at = {@At("TAIL")}, method = {"placeNewPlayer"})
/*    */   public void onPlaceNewPlayer(class_2535 connection, class_3222 serverPlayer, class_8792 commonListenerCookie, CallbackInfo info) {
/* 21 */     if (!WorldMap.loaded)
/*    */       return; 
/* 23 */     WorldMap.commonEvents.onPlayerLogIn((class_1657)serverPlayer);
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\mixin\MixinFabricPlayerList.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */