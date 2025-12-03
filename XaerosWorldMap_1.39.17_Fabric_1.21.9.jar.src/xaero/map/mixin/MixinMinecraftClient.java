/*    */ package xaero.map.mixin;
/*    */ 
/*    */ import net.minecraft.class_310;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.ModifyVariable;
/*    */ import xaero.map.WorldMap;
/*    */ import xaero.map.core.IWorldMapMinecraftClient;
/*    */ 
/*    */ @Mixin({class_310.class})
/*    */ public class MixinMinecraftClient
/*    */   implements IWorldMapMinecraftClient
/*    */ {
/*    */   @Shadow
/*    */   private static int field_1738;
/*    */   
/*    */   public int getXaeroWorldMap_fps() {
/* 19 */     return field_1738;
/*    */   }
/*    */   
/*    */   @ModifyVariable(method = {"runTick"}, at = @At("HEAD"), index = 1)
/*    */   public boolean onRunTick(boolean unused) {
/* 24 */     WorldMap.events.onMinecraftRunTickStart();
/* 25 */     return unused;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\mixin\MixinMinecraftClient.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */