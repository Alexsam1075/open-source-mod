/*     */ package xaero.map.gui.dropdown;
/*     */ 
/*     */ import net.minecraft.class_2561;
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
/*     */   private String[] options;
/*     */   private int x;
/*     */   private int y;
/*     */   private int w;
/*     */   private Integer selected;
/*     */   private boolean openingUp;
/*     */   private IDropDownWidgetCallback callback;
/*     */   private IDropDownContainer container;
/*     */   private boolean hasEmptyOption;
/*     */   private class_2561 narrationTitle;
/*     */   
/*     */   public Builder setDefault() {
/* 371 */     setOptions(null);
/* 372 */     setX(0);
/* 373 */     setY(0);
/* 374 */     setW(0);
/* 375 */     setSelected(null);
/* 376 */     setOpeningUp(false);
/* 377 */     setCallback(null);
/* 378 */     setHasEmptyOption(true);
/* 379 */     setNarrationTitle(null);
/* 380 */     return this;
/*     */   }
/*     */   
/*     */   public Builder setOptions(String[] options) {
/* 384 */     this.options = options;
/* 385 */     return this;
/*     */   }
/*     */   
/*     */   public Builder setX(int x) {
/* 389 */     this.x = x;
/* 390 */     return this;
/*     */   }
/*     */   
/*     */   public Builder setY(int y) {
/* 394 */     this.y = y;
/* 395 */     return this;
/*     */   }
/*     */   
/*     */   public Builder setW(int w) {
/* 399 */     this.w = w;
/* 400 */     return this;
/*     */   }
/*     */   
/*     */   public Builder setSelected(Integer selected) {
/* 404 */     this.selected = selected;
/* 405 */     return this;
/*     */   }
/*     */   
/*     */   public Builder setOpeningUp(boolean openingUp) {
/* 409 */     this.openingUp = openingUp;
/* 410 */     return this;
/*     */   }
/*     */   
/*     */   public Builder setCallback(IDropDownWidgetCallback callback) {
/* 414 */     this.callback = callback;
/* 415 */     return this;
/*     */   }
/*     */   
/*     */   public Builder setContainer(IDropDownContainer container) {
/* 419 */     this.container = container;
/* 420 */     return this;
/*     */   }
/*     */   
/*     */   public Builder setHasEmptyOption(boolean hasEmptyOption) {
/* 424 */     this.hasEmptyOption = hasEmptyOption;
/* 425 */     return this;
/*     */   }
/*     */   
/*     */   public Builder setNarrationTitle(class_2561 narrationTitle) {
/* 429 */     this.narrationTitle = narrationTitle;
/* 430 */     return this;
/*     */   }
/*     */   
/*     */   public DropDownWidget build() {
/* 434 */     if (this.options == null || this.w == 0 || this.selected == null || this.callback == null || this.narrationTitle == null || this.container == null)
/* 435 */       throw new IllegalStateException(); 
/* 436 */     return new DropDownWidget(this.options, this.x, this.y, this.w, this.selected, this.openingUp, this.callback, this.container, this.hasEmptyOption, this.narrationTitle);
/*     */   }
/*     */   
/*     */   public static Builder begin() {
/* 440 */     return (new Builder()).setDefault();
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\gui\dropdown\DropDownWidget$Builder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */