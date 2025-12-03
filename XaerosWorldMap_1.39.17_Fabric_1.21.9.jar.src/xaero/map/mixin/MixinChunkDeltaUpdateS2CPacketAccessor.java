/*    */ package xaero.map.mixin;
/*    */ 
/*    */ import net.minecraft.class_2637;
/*    */ import net.minecraft.class_4076;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import xaero.map.core.IWorldMapSMultiBlockChangePacket;
/*    */ 
/*    */ @Mixin({class_2637.class})
/*    */ public class MixinChunkDeltaUpdateS2CPacketAccessor
/*    */   implements IWorldMapSMultiBlockChangePacket
/*    */ {
/*    */   @Shadow
/*    */   private class_4076 field_26345;
/*    */   
/*    */   public class_4076 xaero_wm_getSectionPos() {
/* 17 */     return this.field_26345;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\mixin\MixinChunkDeltaUpdateS2CPacketAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */