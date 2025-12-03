/*    */ package xaero.map.mixin;
/*    */ 
/*    */ import net.minecraft.class_32;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ import xaero.map.core.XaeroWorldMapCore;
/*    */ 
/*    */ 
/*    */ @Mixin({class_32.class_5143.class})
/*    */ public class MixinLevelStorageAccess
/*    */ {
/*    */   @Inject(at = {@At("RETURN")}, method = {"deleteLevel"}, cancellable = false)
/*    */   public void onDeleteLevel(CallbackInfo info) {
/* 16 */     XaeroWorldMapCore.onDeleteWorld((class_32.class_5143)this);
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\mixin\MixinLevelStorageAccess.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */