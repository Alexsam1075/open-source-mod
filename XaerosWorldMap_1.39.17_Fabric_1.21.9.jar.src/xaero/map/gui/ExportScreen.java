/*    */ package xaero.map.gui;
/*    */ 
/*    */ import net.minecraft.class_2561;
/*    */ import net.minecraft.class_332;
/*    */ import net.minecraft.class_364;
/*    */ import net.minecraft.class_4185;
/*    */ import net.minecraft.class_437;
/*    */ import xaero.map.MapProcessor;
/*    */ import xaero.map.file.export.PNGExportResult;
/*    */ import xaero.map.settings.ModOptions;
/*    */ 
/*    */ public class ExportScreen
/*    */   extends GuiSettings {
/* 14 */   private static final class_2561 EXPORTING_MESSAGE = (class_2561)class_2561.method_43471("gui.xaero_export_screen_exporting");
/*    */   private final MapProcessor mapProcessor;
/*    */   private PNGExportResult result;
/*    */   private int stage;
/*    */   private final MapTileSelection selection;
/*    */   public boolean fullExport;
/*    */   
/*    */   public ExportScreen(class_437 backScreen, class_437 escScreen, MapProcessor mapProcessor, MapTileSelection selection) {
/* 22 */     super((class_2561)class_2561.method_43471("gui.xaero_export_screen"), backScreen, escScreen);
/* 23 */     this.mapProcessor = mapProcessor;
/* 24 */     this.selection = selection;
/* 25 */     this.entries = new ISettingEntry[] { new ConfigSettingEntry(ModOptions.FULL_EXPORT), new ConfigSettingEntry(ModOptions.MULTIPLE_IMAGES_EXPORT), new ConfigSettingEntry(ModOptions.NIGHT_EXPORT), new ConfigSettingEntry(ModOptions.EXPORT_HIGHLIGHTS), new ConfigSettingEntry(ModOptions.EXPORT_SCALE_DOWN_SQUARE) };
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 30 */     this.canSearch = false;
/* 31 */     this.shouldAddBackButton = false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void method_25426() {
/* 36 */     if (this.stage > 0)
/*    */       return; 
/* 38 */     super.method_25426();
/* 39 */     method_37063((class_364)new MySmallButton(this.field_22789 / 2 - 155, this.field_22790 / 6 + 168, (class_2561)class_2561.method_43469("gui.xaero_confirm", new Object[0]), b -> {
/*    */             this.stage = 1;
/*    */             
/*    */             method_25423(this.field_22787, this.field_22789, this.field_22790);
/*    */           }));
/* 44 */     method_37063((class_364)new MySmallButton(this.field_22789 / 2 + 5, this.field_22790 / 6 + 168, (class_2561)class_2561.method_43469("gui.xaero_cancel", new Object[0]), b -> goBack()));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void method_25420(class_332 guiGraphics, int i, int j, float f) {
/* 52 */     renderEscapeScreen(guiGraphics, 0, 0, f);
/* 53 */     super.method_25420(guiGraphics, i, j, f);
/*    */   }
/*    */ 
/*    */   
/*    */   public void method_25394(class_332 guiGraphics, int par1, int par2, float par3) {
/* 58 */     super.method_25394(guiGraphics, par1, par2, par3);
/* 59 */     if (this.result != null)
/* 60 */       guiGraphics.method_27534(this.field_22787.field_1772, this.result.getMessage(), this.field_22789 / 2, 20, -1); 
/* 61 */     if (this.stage > 0) {
/* 62 */       guiGraphics.method_27534(this.field_22787.field_1772, EXPORTING_MESSAGE, this.field_22789 / 2, this.field_22790 / 6 + 68, -1);
/* 63 */       if (this.stage == 1) {
/* 64 */         this.stage = 2;
/*    */         return;
/*    */       } 
/*    */     } 
/* 68 */     if (this.stage != 2) {
/*    */       return;
/*    */     }
/* 71 */     if (this.mapProcessor.getMapSaveLoad().exportPNG(this, this.fullExport ? null : this.selection)) {
/* 72 */       this.stage = 3;
/* 73 */       this.result = null;
/* 74 */       for (class_364 c : method_25396()) {
/* 75 */         if (c instanceof class_4185)
/* 76 */           ((class_4185)c).field_22763 = false; 
/*    */       }  return;
/*    */     } 
/* 79 */     this.stage = 0;
/* 80 */     method_25423(this.field_22787, this.field_22789, this.field_22790);
/*    */   }
/*    */   
/*    */   public void onExportDone(PNGExportResult result) {
/* 84 */     this.result = result;
/* 85 */     this.stage = 0;
/*    */   }
/*    */   
/*    */   public MapTileSelection getSelection() {
/* 89 */     return this.selection;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\gui\ExportScreen.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */