/*    */ package xaero.map.gui;
/*    */ 
/*    */ import net.minecraft.class_11908;
/*    */ import net.minecraft.class_11909;
/*    */ import net.minecraft.class_11910;
/*    */ import net.minecraft.class_2561;
/*    */ import net.minecraft.class_332;
/*    */ import net.minecraft.class_342;
/*    */ import net.minecraft.class_364;
/*    */ import net.minecraft.class_4185;
/*    */ import net.minecraft.class_437;
/*    */ import xaero.map.WorldMapSession;
/*    */ import xaero.map.world.MapWorld;
/*    */ 
/*    */ public class GuiPlayerTpCommand extends ScreenBase {
/*    */   private MySmallButton confirmButton;
/*    */   private class_342 commandFormatTextField;
/*    */   private String commandFormat;
/*    */   
/*    */   public GuiPlayerTpCommand(class_437 parent, class_437 escape) {
/* 21 */     super(parent, escape, (class_2561)class_2561.method_43471("gui.xaero_wm_player_teleport_command"));
/*    */   }
/*    */ 
/*    */   
/*    */   public void method_25426() {
/* 26 */     super.method_25426();
/* 27 */     WorldMapSession session = WorldMapSession.getCurrentSession();
/* 28 */     MapWorld mapWorld = session.getMapProcessor().getMapWorld();
/* 29 */     if (this.commandFormat == null)
/* 30 */       this.commandFormat = mapWorld.getPlayerTeleportCommandFormat(); 
/* 31 */     this.commandFormatTextField = new class_342(this.field_22793, this.field_22789 / 2 - 100, this.field_22790 / 7 + 50, 200, 20, (class_2561)class_2561.method_43471("gui.xaero_wm_player_teleport_command"));
/* 32 */     this.commandFormatTextField.method_1852(this.commandFormat);
/* 33 */     this.commandFormatTextField.method_1880(128);
/* 34 */     method_25429(this.commandFormatTextField);
/* 35 */     method_37063(
/* 36 */         (class_364)(this.confirmButton = new MySmallButton(this.field_22789 / 2 - 155, this.field_22790 / 6 + 168, (class_2561)class_2561.method_43469("gui.xaero_confirm", new Object[0]), b -> {
/*    */             mapWorld.setPlayerTeleportCommandFormat(this.commandFormat);
/*    */             
/*    */             mapWorld.saveConfig();
/*    */             goBack();
/*    */           })));
/* 42 */     method_37063((class_364)new MySmallButton(this.field_22789 / 2 + 5, this.field_22790 / 6 + 168, 
/* 43 */           (class_2561)class_2561.method_43469("gui.xaero_cancel", new Object[0]), b -> goBack()));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void method_25420(class_332 guiGraphics, int i, int j, float f) {
/* 51 */     renderEscapeScreen(guiGraphics, 0, 0, f);
/* 52 */     super.method_25420(guiGraphics, i, j, f);
/* 53 */     guiGraphics.method_27534(this.field_22793, this.field_22785, this.field_22789 / 2, 20, -1);
/* 54 */     guiGraphics.method_25300(this.field_22793, "{x} {y} {z} {name}", this.field_22789 / 2, this.field_22790 / 7 + 36, -5592406);
/*    */   }
/*    */ 
/*    */   
/*    */   public void method_25394(class_332 guiGraphics, int mouseX, int mouseY, float partial) {
/* 59 */     super.method_25394(guiGraphics, mouseX, mouseY, partial);
/* 60 */     this.commandFormatTextField.method_25394(guiGraphics, mouseX, mouseY, partial);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void method_25393() {
/* 66 */     this.commandFormat = this.commandFormatTextField.method_1882();
/* 67 */     this.confirmButton.field_22763 = (this.commandFormat != null && this.commandFormat.length() > 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean method_25404(class_11908 event) {
/* 72 */     if (event.comp_4795() == 257 && this.commandFormat != null && this.commandFormat.length() > 0) {
/* 73 */       this.confirmButton.method_25348(new class_11909(0.0D, 0.0D, new class_11910(0, 0)), false);
/*    */     }
/* 75 */     return super.method_25404(event);
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\gui\GuiPlayerTpCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */