/*     */ package xaero.map.mods.pac.gui.claim.element;
/*     */ 
/*     */ import xaero.map.mods.pac.gui.claim.ClaimResultElementManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Builder
/*     */ {
/*     */   private ClaimResultElementManager manager;
/*     */   
/*     */   private Builder setDefault() {
/* 127 */     setManager(null);
/* 128 */     return this;
/*     */   }
/*     */   
/*     */   public Builder setManager(ClaimResultElementManager manager) {
/* 132 */     this.manager = manager;
/* 133 */     return this;
/*     */   }
/*     */   
/*     */   public ClaimResultElementRenderer build() {
/* 137 */     if (this.manager == null)
/* 138 */       throw new IllegalStateException(); 
/* 139 */     return new ClaimResultElementRenderer(this.manager, new ClaimResultElementRenderContext(), new ClaimResultElementRenderProvider(this.manager), new ClaimResultElementRenderReader());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Builder begin() {
/* 148 */     return (new Builder()).setDefault();
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\mods\pac\gui\claim\element\ClaimResultElementRenderer$Builder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */