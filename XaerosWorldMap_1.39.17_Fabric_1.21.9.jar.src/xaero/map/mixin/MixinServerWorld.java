/*    */ package xaero.map.mixin;
/*    */ 
/*    */ import net.minecraft.class_3218;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import xaero.map.capabilities.ServerWorldCapabilities;
/*    */ import xaero.map.core.IWorldMapServerLevel;
/*    */ 
/*    */ @Mixin({class_3218.class})
/*    */ public class MixinServerWorld
/*    */   implements IWorldMapServerLevel
/*    */ {
/*    */   public ServerWorldCapabilities xaero_wm_capabilities;
/*    */   
/*    */   public ServerWorldCapabilities getXaero_wm_capabilities() {
/* 15 */     return this.xaero_wm_capabilities;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setXaero_wm_capabilities(ServerWorldCapabilities capabilities) {
/* 20 */     this.xaero_wm_capabilities = capabilities;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\mixin\MixinServerWorld.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */