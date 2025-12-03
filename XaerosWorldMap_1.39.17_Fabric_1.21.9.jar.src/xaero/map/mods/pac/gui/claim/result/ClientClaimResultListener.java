/*    */ package xaero.map.mods.pac.gui.claim.result;
/*    */ 
/*    */ import net.minecraft.class_2561;
/*    */ import xaero.map.MapProcessor;
/*    */ import xaero.map.WorldMapSession;
/*    */ import xaero.map.mods.SupportMods;
/*    */ import xaero.map.mods.pac.gui.claim.ClaimResultElement;
/*    */ import xaero.pac.client.claims.tracker.result.api.IClaimsManagerClaimResultListenerAPI;
/*    */ import xaero.pac.common.claims.result.api.AreaClaimResult;
/*    */ import xaero.pac.common.claims.result.api.ClaimResult;
/*    */ 
/*    */ public class ClientClaimResultListener
/*    */   implements IClaimsManagerClaimResultListenerAPI {
/*    */   public void onClaimResult(AreaClaimResult result) {
/* 15 */     SupportMods.xaeroPac.getClaimResultElementManager().clear();
/* 16 */     ClaimResultElement resultElement = SupportMods.xaeroPac.getClaimResultElementManager().add(result);
/* 17 */     WorldMapSession session = WorldMapSession.getCurrentSession();
/* 18 */     if (session != null) {
/* 19 */       MapProcessor mapProcessor = session.getMapProcessor();
/* 20 */       resultElement.getFilteredResultTypeIterator().forEachRemaining(type -> mapProcessor.getMessageBox().addMessageWithSource((class_2561)class_2561.method_43470("Claims"), type.message));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\mods\pac\gui\claim\result\ClientClaimResultListener.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */