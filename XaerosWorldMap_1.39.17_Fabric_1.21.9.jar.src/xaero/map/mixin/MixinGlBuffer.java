/*    */ package xaero.map.mixin;
/*    */ 
/*    */ import net.minecraft.class_10859;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import xaero.map.core.IWorldMapGlBuffer;
/*    */ 
/*    */ 
/*    */ @Mixin({class_10859.class})
/*    */ public class MixinGlBuffer
/*    */   implements IWorldMapGlBuffer
/*    */ {
/*    */   @Shadow
/*    */   private int field_57842;
/*    */   
/*    */   public int xaero_wm_getHandle() {
/* 17 */     return this.field_57842;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\mixin\MixinGlBuffer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */