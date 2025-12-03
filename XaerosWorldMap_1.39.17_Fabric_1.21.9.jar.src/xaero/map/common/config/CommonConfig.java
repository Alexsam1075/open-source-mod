/*    */ package xaero.map.common.config;
/*    */ 
/*    */ 
/*    */ public final class CommonConfig
/*    */ {
/*    */   public final boolean registerStatusEffects;
/*    */   public boolean allowCaveModeOnServer;
/*    */   public boolean allowNetherCaveModeOnServer;
/*    */   public boolean everyoneTracksEveryone;
/*    */   
/*    */   private CommonConfig(boolean registerStatusEffects) {
/* 12 */     this.registerStatusEffects = registerStatusEffects;
/*    */   }
/*    */ 
/*    */   
/*    */   public static final class Builder
/*    */   {
/*    */     public boolean registerStatusEffects;
/*    */     
/*    */     public boolean allowCaveModeOnServer;
/*    */     
/*    */     public boolean allowNetherCaveModeOnServer;
/*    */     public boolean everyoneTracksEveryone;
/*    */     
/*    */     public Builder setDefault() {
/* 26 */       setRegisterStatusEffects(true);
/* 27 */       setAllowCaveModeOnServer(true);
/* 28 */       setAllowNetherCaveModeOnServer(true);
/* 29 */       setEveryoneTracksEveryone(false);
/* 30 */       return this;
/*    */     }
/*    */     
/*    */     public Builder setRegisterStatusEffects(boolean registerStatusEffects) {
/* 34 */       this.registerStatusEffects = registerStatusEffects;
/* 35 */       return this;
/*    */     }
/*    */     
/*    */     public Builder setAllowCaveModeOnServer(boolean allowCaveModeOnServer) {
/* 39 */       this.allowCaveModeOnServer = allowCaveModeOnServer;
/* 40 */       return this;
/*    */     }
/*    */     
/*    */     public Builder setAllowNetherCaveModeOnServer(boolean allowNetherCaveModeOnServer) {
/* 44 */       this.allowNetherCaveModeOnServer = allowNetherCaveModeOnServer;
/* 45 */       return this;
/*    */     }
/*    */     
/*    */     public Builder setEveryoneTracksEveryone(boolean everyoneTracksEveryone) {
/* 49 */       this.everyoneTracksEveryone = everyoneTracksEveryone;
/* 50 */       return this;
/*    */     }
/*    */     
/*    */     public CommonConfig build() {
/* 54 */       CommonConfig result = new CommonConfig(this.registerStatusEffects);
/* 55 */       result.allowCaveModeOnServer = this.allowCaveModeOnServer;
/* 56 */       result.allowNetherCaveModeOnServer = this.allowNetherCaveModeOnServer;
/* 57 */       result.everyoneTracksEveryone = this.everyoneTracksEveryone;
/* 58 */       return result;
/*    */     }
/*    */     
/*    */     public static Builder begin() {
/* 62 */       return (new Builder()).setDefault();
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\common\config\CommonConfig.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */