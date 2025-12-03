/*     */ package xaero.map.gui;
/*     */ 
/*     */ import net.minecraft.class_11908;
/*     */ import net.minecraft.class_11909;
/*     */ import net.minecraft.class_11910;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_342;
/*     */ import net.minecraft.class_364;
/*     */ import net.minecraft.class_4185;
/*     */ import net.minecraft.class_437;
/*     */ import xaero.map.WorldMapSession;
/*     */ import xaero.map.mods.SupportMods;
/*     */ import xaero.map.world.MapWorld;
/*     */ 
/*     */ public class GuiMapTpCommand extends ScreenBase {
/*     */   private MySmallButton confirmButton;
/*     */   private class_342 commandFormatTextField;
/*     */   private class_342 dimensionCommandFormatTextField;
/*     */   private String commandFormat;
/*     */   private String dimensionCommandFormat;
/*  22 */   private class_2561 waypointCommandHint = (class_2561)class_2561.method_43471("gui.xaero_wm_teleport_command_waypoints_hint");
/*     */   
/*     */   public GuiMapTpCommand(class_437 parent, class_437 escape) {
/*  25 */     super(parent, escape, (class_2561)class_2561.method_43471("gui.xaero_wm_teleport_command"));
/*     */   }
/*     */ 
/*     */   
/*     */   public void method_25426() {
/*  30 */     super.method_25426();
/*  31 */     WorldMapSession session = WorldMapSession.getCurrentSession();
/*  32 */     MapWorld mapWorld = session.getMapProcessor().getMapWorld();
/*  33 */     if (this.commandFormat == null)
/*  34 */       this.commandFormat = mapWorld.getTeleportCommandFormat(); 
/*  35 */     if (this.dimensionCommandFormat == null)
/*  36 */       this.dimensionCommandFormat = mapWorld.getDimensionTeleportCommandFormat(); 
/*  37 */     this.commandFormatTextField = new class_342(this.field_22793, this.field_22789 / 2 - 100, this.field_22790 / 7 + 20, 200, 20, (class_2561)class_2561.method_43471("gui.xaero_wm_teleport_command"));
/*  38 */     this.commandFormatTextField.method_1880(128);
/*  39 */     this.commandFormatTextField.method_1852(this.commandFormat);
/*  40 */     this.dimensionCommandFormatTextField = new class_342(this.field_22793, this.field_22789 / 2 - 100, this.field_22790 / 7 + 50, 200, 20, (class_2561)class_2561.method_43471("gui.xaero_wm_dimension_teleport_command"));
/*  41 */     this.dimensionCommandFormatTextField.method_1880(256);
/*  42 */     this.dimensionCommandFormatTextField.method_1852(this.dimensionCommandFormat);
/*  43 */     method_25429(this.commandFormatTextField);
/*  44 */     method_25429(this.dimensionCommandFormatTextField);
/*  45 */     if (SupportMods.minimap()) {
/*  46 */       method_37063((class_364)new MySmallButton(this.field_22789 / 2 - 75, this.field_22790 / 7 + 118, 
/*  47 */             (class_2561)class_2561.method_43471("gui.xaero_wm_teleport_command_waypoints"), b -> SupportMods.xaeroMinimap.openWaypointWorldTeleportCommandScreen(this, this.escape)));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  52 */     method_37063(
/*  53 */         (class_364)(this.confirmButton = new MySmallButton(this.field_22789 / 2 - 155, this.field_22790 / 6 + 168, (class_2561)class_2561.method_43471("gui.xaero_confirm"), b -> {
/*     */             updateFormat();
/*     */             
/*     */             if (canConfirm()) {
/*     */               mapWorld.setTeleportCommandFormat(this.commandFormat);
/*     */               mapWorld.setDimensionTeleportCommandFormat(this.dimensionCommandFormat);
/*     */               mapWorld.saveConfig();
/*     */               goBack();
/*     */             } 
/*     */           })));
/*  63 */     method_37063((class_364)new MySmallButton(this.field_22789 / 2 + 5, this.field_22790 / 6 + 168, 
/*  64 */           (class_2561)class_2561.method_43471("gui.xaero_cancel"), b -> goBack()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void method_25420(class_332 guiGraphics, int i, int j, float f) {
/*  72 */     renderEscapeScreen(guiGraphics, 0, 0, f);
/*  73 */     super.method_25420(guiGraphics, i, j, f);
/*  74 */     guiGraphics.method_27534(this.field_22793, this.field_22785, this.field_22789 / 2, 20, -1);
/*  75 */     if (SupportMods.minimap())
/*  76 */       guiGraphics.method_27534(this.field_22793, this.waypointCommandHint, this.field_22789 / 2, this.field_22790 / 7 + 104, -5592406); 
/*  77 */     guiGraphics.method_25300(this.field_22793, "{x} {y} {z} {d}", this.field_22789 / 2, this.field_22790 / 7 + 6, -5592406);
/*     */   }
/*     */ 
/*     */   
/*     */   public void method_25394(class_332 guiGraphics, int mouseX, int mouseY, float partial) {
/*  82 */     super.method_25394(guiGraphics, mouseX, mouseY, partial);
/*  83 */     this.commandFormatTextField.method_25394(guiGraphics, mouseX, mouseY, partial);
/*  84 */     this.dimensionCommandFormatTextField.method_25394(guiGraphics, mouseX, mouseY, partial);
/*     */   }
/*     */   
/*     */   private boolean canConfirm() {
/*  88 */     return (this.commandFormat != null && this.commandFormat.length() > 0 && this.dimensionCommandFormat != null && this.dimensionCommandFormat.length() > 0);
/*     */   }
/*     */   
/*     */   private void updateFormat() {
/*  92 */     this.commandFormat = this.commandFormatTextField.method_1882();
/*  93 */     this.dimensionCommandFormat = this.dimensionCommandFormatTextField.method_1882();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void method_25393() {
/* 100 */     updateFormat();
/* 101 */     this.confirmButton.field_22763 = canConfirm();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean method_25404(class_11908 event) {
/* 106 */     if (event.comp_4795() == 257 && this.commandFormat != null && this.commandFormat.length() > 0) {
/* 107 */       this.confirmButton.method_25348(new class_11909(0.0D, 0.0D, new class_11910(0, 0)), false);
/*     */     }
/* 109 */     return super.method_25404(event);
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\gui\GuiMapTpCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */