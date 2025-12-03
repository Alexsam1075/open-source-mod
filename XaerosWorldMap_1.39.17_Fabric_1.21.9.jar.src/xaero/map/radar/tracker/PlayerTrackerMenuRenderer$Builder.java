/*     */ package xaero.map.radar.tracker;
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
/*     */   private PlayerTrackerMapElementRenderer renderer;
/*     */   
/*     */   private Builder setDefault() {
/* 107 */     setRenderer(null);
/* 108 */     return this;
/*     */   }
/*     */   
/*     */   public Builder setRenderer(PlayerTrackerMapElementRenderer renderer) {
/* 112 */     this.renderer = renderer;
/* 113 */     return this;
/*     */   }
/*     */   
/*     */   public PlayerTrackerMenuRenderer build() {
/* 117 */     if (this.renderer == null)
/* 118 */       throw new IllegalStateException(); 
/* 119 */     return new PlayerTrackerMenuRenderer(this.renderer, new PlayerTrackerIconRenderer(), new PlayerTrackerMenuRenderContext(), new PlayerTrackerMapElementRenderProvider<>(this.renderer.getCollector()));
/*     */   }
/*     */   
/*     */   public static Builder begin() {
/* 123 */     return (new Builder()).setDefault();
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\radar\tracker\PlayerTrackerMenuRenderer$Builder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */