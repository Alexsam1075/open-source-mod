/*    */ package xaero.map.mods.pac.party;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import xaero.map.mods.pac.SupportOpenPartiesAndClaims;
/*    */ import xaero.map.radar.tracker.system.IPlayerTrackerSystem;
/*    */ import xaero.map.radar.tracker.system.ITrackedPlayerReader;
/*    */ import xaero.pac.common.parties.party.api.IPartyMemberDynamicInfoSyncableAPI;
/*    */ 
/*    */ 
/*    */ public class OPACPlayerTrackerSystem
/*    */   implements IPlayerTrackerSystem<IPartyMemberDynamicInfoSyncableAPI>
/*    */ {
/*    */   private final SupportOpenPartiesAndClaims opac;
/*    */   private final OPACTrackedPlayerReader reader;
/*    */   
/*    */   public OPACPlayerTrackerSystem(SupportOpenPartiesAndClaims opac) {
/* 17 */     this.opac = opac;
/* 18 */     this.reader = new OPACTrackedPlayerReader();
/*    */   }
/*    */ 
/*    */   
/*    */   public ITrackedPlayerReader<IPartyMemberDynamicInfoSyncableAPI> getReader() {
/* 23 */     return this.reader;
/*    */   }
/*    */ 
/*    */   
/*    */   public Iterator<IPartyMemberDynamicInfoSyncableAPI> getTrackedPlayerIterator() {
/* 28 */     return this.opac.getAllyIterator();
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\mods\pac\party\OPACPlayerTrackerSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */