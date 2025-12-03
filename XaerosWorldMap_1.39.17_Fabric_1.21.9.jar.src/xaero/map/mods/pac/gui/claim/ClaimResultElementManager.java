/*    */ package xaero.map.mods.pac.gui.claim;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import xaero.map.misc.Area;
/*    */ import xaero.map.mods.pac.SupportOpenPartiesAndClaims;
/*    */ import xaero.pac.common.claims.result.api.AreaClaimResult;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ClaimResultElementManager
/*    */ {
/*    */   private Map<Area, ClaimResultElement> claimResults;
/*    */   
/*    */   private ClaimResultElementManager(Map<Area, ClaimResultElement> claimResults) {
/* 17 */     this.claimResults = claimResults;
/*    */   }
/*    */   
/*    */   public static long getLongCoordinatesFor(int x, int z) {
/* 21 */     return x << 32L | z & 0xFFFFFFFFL;
/*    */   }
/*    */   
/*    */   public static int getXFromLongCoordinates(long key) {
/* 25 */     return (int)(key >> 32L);
/*    */   }
/*    */   
/*    */   public static int getZFromLongCoordinates(long key) {
/* 29 */     return (int)(key & 0xFFFFFFFFFFFFFFFFL);
/*    */   }
/*    */   
/*    */   public ClaimResultElement add(AreaClaimResult result) {
/* 33 */     Area key = new Area(result.getLeft(), result.getTop(), result.getRight(), result.getBottom());
/*    */     ClaimResultElement toReturn;
/* 35 */     this.claimResults.put(key, toReturn = ClaimResultElement.Builder.begin().setKey(key).setResult(result).build());
/* 36 */     return toReturn;
/*    */   }
/*    */   
/*    */   public void remove(ClaimResultElement element) {
/* 40 */     this.claimResults.remove(element.getKey());
/*    */   }
/*    */   
/*    */   public Iterator<ClaimResultElement> getIterator() {
/* 44 */     return this.claimResults.values().iterator();
/*    */   }
/*    */   
/*    */   public void clear() {
/* 48 */     this.claimResults.clear();
/*    */   }
/*    */ 
/*    */   
/*    */   public static final class Builder
/*    */   {
/*    */     private SupportOpenPartiesAndClaims pac;
/*    */ 
/*    */     
/*    */     private Builder setDefault() {
/* 58 */       return this;
/*    */     }
/*    */     
/*    */     public Builder setPac(SupportOpenPartiesAndClaims pac) {
/* 62 */       this.pac = pac;
/* 63 */       return this;
/*    */     }
/*    */     
/*    */     public ClaimResultElementManager build() {
/* 67 */       if (this.pac == null)
/* 68 */         throw new IllegalStateException(); 
/* 69 */       ClaimResultElementManager result = new ClaimResultElementManager(new HashMap<>());
/* 70 */       return result;
/*    */     }
/*    */     
/*    */     public static Builder begin() {
/* 74 */       return (new Builder()).setDefault();
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\mods\pac\gui\claim\ClaimResultElementManager.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */