/*    */ package xaero.map.mods.pac.gui.claim;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import xaero.map.misc.Area;
/*    */ import xaero.map.mods.pac.SupportOpenPartiesAndClaims;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Builder
/*    */ {
/*    */   private SupportOpenPartiesAndClaims pac;
/*    */   
/*    */   private Builder setDefault() {
/* 58 */     return this;
/*    */   }
/*    */   
/*    */   public Builder setPac(SupportOpenPartiesAndClaims pac) {
/* 62 */     this.pac = pac;
/* 63 */     return this;
/*    */   }
/*    */   
/*    */   public ClaimResultElementManager build() {
/* 67 */     if (this.pac == null)
/* 68 */       throw new IllegalStateException(); 
/* 69 */     ClaimResultElementManager result = new ClaimResultElementManager(new HashMap<>());
/* 70 */     return result;
/*    */   }
/*    */   
/*    */   public static Builder begin() {
/* 74 */     return (new Builder()).setDefault();
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\mods\pac\gui\claim\ClaimResultElementManager$Builder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */