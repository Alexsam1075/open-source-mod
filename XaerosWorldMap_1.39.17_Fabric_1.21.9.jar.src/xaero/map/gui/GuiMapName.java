/*     */ package xaero.map.gui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.attribute.FileAttribute;
/*     */ import net.minecraft.class_11908;
/*     */ import net.minecraft.class_11909;
/*     */ import net.minecraft.class_11910;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_342;
/*     */ import net.minecraft.class_364;
/*     */ import net.minecraft.class_4185;
/*     */ import net.minecraft.class_437;
/*     */ import xaero.map.MapProcessor;
/*     */ import xaero.map.WorldMap;
/*     */ import xaero.map.world.MapDimension;
/*     */ 
/*     */ public class GuiMapName
/*     */   extends ScreenBase {
/*     */   private class_342 nameTextField;
/*     */   private MapDimension mapDimension;
/*     */   private String editingMWId;
/*     */   private String currentNameFieldContent;
/*     */   private MapProcessor mapProcessor;
/*     */   private class_4185 confirmButton;
/*     */   
/*     */   public GuiMapName(MapProcessor mapProcessor, class_437 par1GuiScreen, class_437 escape, MapDimension mapDimension, String editingMWId) {
/*  30 */     super(par1GuiScreen, escape, (class_2561)class_2561.method_43471("gui.xaero_map_name"));
/*  31 */     this.mapDimension = mapDimension;
/*  32 */     this.editingMWId = editingMWId;
/*  33 */     this.currentNameFieldContent = (editingMWId == null) ? "" : mapDimension.getMultiworldName(editingMWId);
/*  34 */     this.mapProcessor = mapProcessor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void method_25426() {
/*  43 */     super.method_25426();
/*  44 */     if (this.nameTextField != null)
/*  45 */       this.currentNameFieldContent = this.nameTextField.method_1882(); 
/*  46 */     this.nameTextField = new class_342(this.field_22793, this.field_22789 / 2 - 100, 60, 200, 20, (class_2561)class_2561.method_43471("gui.xaero_map_name"));
/*  47 */     this.nameTextField.method_1852(this.currentNameFieldContent);
/*  48 */     method_25395((class_364)this.nameTextField);
/*  49 */     method_37063((class_364)this.nameTextField);
/*  50 */     method_37063((class_364)(this.confirmButton = new MySmallButton(this.field_22789 / 2 - 155, this.field_22790 / 6 + 168, (class_2561)class_2561.method_43469("gui.xaero_confirm", new Object[0]), b -> {
/*     */             if (canConfirm()) {
/*     */               synchronized (this.mapProcessor.uiSync) {
/*     */                 if (this.mapProcessor.getMapWorld() == this.mapDimension.getMapWorld()) {
/*     */                   String mwIdFixed;
/*     */                   String unfilteredName = this.nameTextField.method_1882();
/*     */                   if (this.editingMWId == null) {
/*     */                     String mwId = unfilteredName.toLowerCase().replaceAll("[^a-z0-9]+", "");
/*     */                     if (mwId.isEmpty()) {
/*     */                       mwId = "map";
/*     */                     }
/*     */                     mwId = "cm$" + mwId;
/*     */                     boolean mwAdded = false;
/*     */                     mwIdFixed = mwId;
/*     */                     int fix = 1;
/*     */                     while (!mwAdded) {
/*     */                       mwAdded = this.mapDimension.addMultiworldChecked(mwIdFixed);
/*     */                       if (!mwAdded) {
/*     */                         mwIdFixed = mwId + mwId;
/*     */                       }
/*     */                     } 
/*     */                     Path dimensionFolderPath = this.mapDimension.getMainFolderPath();
/*     */                     Path multiworldFolderPath = dimensionFolderPath.resolve(mwIdFixed);
/*     */                     try {
/*     */                       Files.createDirectories(multiworldFolderPath, (FileAttribute<?>[])new FileAttribute[0]);
/*  75 */                     } catch (IOException e) {
/*     */                       WorldMap.LOGGER.error("suppressed exception", e);
/*     */                     } 
/*     */                     
/*     */                     this.mapDimension.setMultiworldUnsynced(mwIdFixed);
/*     */                   } else {
/*     */                     mwIdFixed = this.editingMWId;
/*     */                   } 
/*     */                   this.mapDimension.setMultiworldName(mwIdFixed, unfilteredName);
/*     */                   this.mapDimension.saveConfigUnsynced();
/*     */                   goBack();
/*     */                 } 
/*     */               } 
/*     */             }
/*     */           })));
/*  90 */     method_37063((class_364)new MySmallButton(this.field_22789 / 2 + 5, this.field_22790 / 6 + 168, (class_2561)class_2561.method_43469("gui.xaero_cancel", new Object[0]), b -> goBack()));
/*     */ 
/*     */ 
/*     */     
/*  94 */     updateConfirmButton();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void method_56131() {}
/*     */ 
/*     */   
/*     */   private boolean canConfirm() {
/* 103 */     return (this.nameTextField.method_1882().length() > 0);
/*     */   }
/*     */   
/*     */   private void updateConfirmButton() {
/* 107 */     this.confirmButton.field_22763 = canConfirm();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean method_25404(class_11908 event) {
/* 114 */     boolean result = super.method_25404(event);
/* 115 */     if (event.comp_4795() == 257 && canConfirm()) {
/*     */       
/* 117 */       this.confirmButton.method_25348(new class_11909(0.0D, 0.0D, new class_11910(0, 0)), false);
/* 118 */       return true;
/*     */     } 
/*     */     
/* 121 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void method_25393() {
/* 127 */     updateConfirmButton();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void method_25420(class_332 guiGraphics, int par1, int par2, float par3) {
/* 133 */     renderEscapeScreen(guiGraphics, 0, 0, par3);
/* 134 */     super.method_25420(guiGraphics, par1, par2, par3);
/* 135 */     guiGraphics.method_27534(this.field_22793, this.field_22785, this.field_22789 / 2, 20, -1);
/* 136 */     this.nameTextField.method_25394(guiGraphics, par1, par2, par3);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void method_25394(class_332 guiGraphics, int par1, int par2, float par3) {
/* 142 */     super.method_25394(guiGraphics, par1, par2, par3);
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\gui\GuiMapName.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */