/*    */ package xaero.map.capabilities;
/*    */ 
/*    */ import net.minecraft.class_3218;
/*    */ import xaero.map.core.IWorldMapServerLevel;
/*    */ 
/*    */ 
/*    */ public class CapabilityGetter
/*    */ {
/*    */   public static ServerWorldCapabilities getServerWorldCapabilities(class_3218 level) {
/* 10 */     IWorldMapServerLevel serverLevel = (IWorldMapServerLevel)level;
/* 11 */     ServerWorldCapabilities result = serverLevel.getXaero_wm_capabilities();
/* 12 */     if (result == null)
/* 13 */       serverLevel.setXaero_wm_capabilities(result = new ServerWorldCapabilities()); 
/* 14 */     return result;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\capabilities\CapabilityGetter.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */