/*     */ package xaero.map.gui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.class_1074;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_339;
/*     */ import net.minecraft.class_342;
/*     */ import net.minecraft.class_4185;
/*     */ import xaero.map.MapProcessor;
/*     */ import xaero.map.WorldMap;
/*     */ import xaero.map.settings.ModOptions;
/*     */ import xaero.map.world.MapDimension;
/*     */ 
/*     */ 
/*     */ public class GuiCaveModeOptions
/*     */ {
/*     */   private MapDimension dimension;
/*     */   private boolean enabled;
/*     */   private class_339 caveModeStartSlider;
/*     */   private class_342 caveModeStartField;
/*     */   private boolean shouldUpdateSlider;
/*     */   
/*     */   public void onInit(GuiMap screen, MapProcessor mapProcessor) {
/*  25 */     this.caveModeStartSlider = null;
/*  26 */     this.caveModeStartField = null;
/*  27 */     this.dimension = mapProcessor.getMapWorld().getFutureDimension();
/*  28 */     this.enabled = (this.enabled && this.dimension != null);
/*  29 */     if (this.enabled && this.dimension != null) {
/*  30 */       updateSlider(screen);
/*  31 */       updateField(screen);
/*  32 */       CursorBox caveModeTypeButtonTooltip = new CursorBox("gui.xaero_wm_box_cave_mode_type");
/*  33 */       screen.addButton(new TooltipButton(20, screen.field_22790 - 62, 150, 20, getCaveModeTypeButtonMessage(), b -> onCaveModeTypeButton(b, screen), () -> caveModeTypeButtonTooltip));
/*     */     } 
/*     */   }
/*     */   
/*     */   private void onCaveModeTypeButton(class_4185 b, GuiMap screen) {
/*  38 */     this.dimension.toggleCaveModeType(true);
/*  39 */     synchronized ((screen.getMapProcessor()).uiSync) {
/*  40 */       this.dimension.saveConfigUnsynced();
/*     */     } 
/*  42 */     b.method_25355(getCaveModeTypeButtonMessage());
/*     */   }
/*     */   
/*     */   private class_342 createField(GuiMap screen) {
/*  46 */     class_342 field = new class_342((class_310.method_1551()).field_1772, 172, screen.field_22790 - 40, 50, 20, (class_2561)class_2561.method_43471("gui.xaero_wm_cave_mode_start"));
/*  47 */     field.method_1880(7);
/*  48 */     field.method_1852((WorldMap.settings.caveModeStart == Integer.MAX_VALUE) ? "" : ("" + WorldMap.settings.caveModeStart));
/*  49 */     field.method_1863(text -> {
/*     */           try {
/*  51 */             WorldMap.settings.caveModeStart = (text.isEmpty() || text.equalsIgnoreCase("auto")) ? Integer.MAX_VALUE : Integer.parseInt(text);
/*     */             this.shouldUpdateSlider = true;
/*  53 */           } catch (NumberFormatException numberFormatException) {}
/*     */           
/*     */           try {
/*     */             WorldMap.settings.saveSettings();
/*  57 */           } catch (IOException e) {
/*     */             WorldMap.LOGGER.error("suppressed exception", e);
/*     */           } 
/*     */         });
/*  61 */     return field;
/*     */   }
/*     */   
/*     */   private class_339 createSlider(GuiMap screen) {
/*  65 */     return ModOptions.CAVE_MODE_START.getXOption().createButton(20, screen.field_22790 - 40, 150);
/*     */   }
/*     */   
/*     */   private void updateField(GuiMap screen) {
/*  69 */     if (this.caveModeStartField == null) {
/*  70 */       screen.addButton(this.caveModeStartField = createField(screen));
/*     */     } else {
/*  72 */       screen.replaceRenderableWidget((class_339)this.caveModeStartField, (class_339)(this.caveModeStartField = createField(screen)));
/*     */     } 
/*     */   }
/*     */   private void updateSlider(GuiMap screen) {
/*  76 */     if (this.caveModeStartSlider == null) {
/*  77 */       screen.addButton(this.caveModeStartSlider = createSlider(screen));
/*     */     } else {
/*  79 */       screen.replaceRenderableWidget(this.caveModeStartSlider, this.caveModeStartSlider = createSlider(screen));
/*     */     } 
/*     */   }
/*     */   public void toggle(GuiMap screen) {
/*  83 */     this.enabled = (WorldMap.settings.isCaveMapsAllowed() && !this.enabled);
/*  84 */     screen.method_25423(class_310.method_1551(), screen.field_22789, screen.field_22790);
/*     */   }
/*     */   
/*     */   public void onCaveModeStartSet(GuiMap screen) {
/*  88 */     if (this.enabled)
/*  89 */       updateField(screen); 
/*     */   }
/*     */   
/*     */   public void tick(GuiMap screen) {
/*  93 */     if (this.shouldUpdateSlider) {
/*  94 */       updateSlider(screen);
/*  95 */       this.shouldUpdateSlider = false;
/*     */     } 
/*  97 */     if (this.enabled) {
/*  98 */       this.caveModeStartField.method_1887(this.caveModeStartField.method_1882().isEmpty() ? class_1074.method_4662("gui.xaero_wm_cave_mode_start_auto", new Object[0]) : "");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void unfocusAll() {
/* 104 */     if (this.caveModeStartField != null)
/* 105 */       this.caveModeStartField.method_25365(false); 
/* 106 */     if (this.caveModeStartSlider != null)
/* 107 */       this.caveModeStartSlider.method_25365(false); 
/*     */   }
/*     */   
/*     */   private class_2561 getCaveModeTypeButtonMessage() {
/* 111 */     return (class_2561)class_2561.method_43470(class_1074.method_4662("gui.xaero_wm_cave_mode_type", new Object[0]) + ": " + class_1074.method_4662("gui.xaero_wm_cave_mode_type", new Object[0]));
/*     */   }
/*     */   
/*     */   public boolean isEnabled() {
/* 115 */     return this.enabled;
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\gui\GuiCaveModeOptions.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */