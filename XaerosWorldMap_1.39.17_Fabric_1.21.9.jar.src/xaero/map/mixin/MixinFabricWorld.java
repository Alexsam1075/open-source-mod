/*    */ package xaero.map.mixin;
/*    */ 
/*    */ import net.minecraft.class_1936;
/*    */ import net.minecraft.class_1937;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ import xaero.map.WorldMap;
/*    */ 
/*    */ @Mixin({class_1937.class})
/*    */ public class MixinFabricWorld
/*    */ {
/*    */   @Inject(at = {@At("HEAD")}, method = {"close"})
/*    */   public void onClose(CallbackInfo info) {
/* 16 */     if (this instanceof net.minecraft.class_3218) {
/* 17 */       if (!WorldMap.loaded)
/*    */         return; 
/* 19 */       WorldMap.events.handleWorldUnload((class_1936)this);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\mixin\MixinFabricWorld.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */