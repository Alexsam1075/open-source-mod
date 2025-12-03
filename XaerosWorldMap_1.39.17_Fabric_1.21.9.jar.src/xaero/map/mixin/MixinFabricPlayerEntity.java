/*    */ package xaero.map.mixin;
/*    */ 
/*    */ import net.minecraft.class_1657;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ import xaero.map.WorldMap;
/*    */ 
/*    */ 
/*    */ @Mixin({class_1657.class})
/*    */ public class MixinFabricPlayerEntity
/*    */ {
/*    */   @Inject(at = {@At("HEAD")}, method = {"tick"})
/*    */   public void onTickStart(CallbackInfo info) {
/* 16 */     if (!WorldMap.loaded)
/*    */       return; 
/* 18 */     WorldMap.commonEvents.handlePlayerTickStart((class_1657)this);
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\mixin\MixinFabricPlayerEntity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */