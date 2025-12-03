/*    */ package xaero.map.gui;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
/*    */ import net.minecraft.class_2561;
/*    */ import net.minecraft.class_332;
/*    */ import net.minecraft.class_410;
/*    */ import net.minecraft.class_437;
/*    */ import xaero.map.graphics.TextureUtils;
/*    */ import xaero.map.gui.dropdown.DropDownWidget;
/*    */ import xaero.map.misc.Misc;
/*    */ import xaero.map.render.util.GuiRenderUtil;
/*    */ 
/*    */ public class ConfirmScreenBase
/*    */   extends class_410 implements IScreenBase {
/*    */   public class_437 parent;
/*    */   public class_437 escape;
/*    */   private boolean renderEscapeInBackground;
/*    */   protected boolean canSkipWorldRender;
/*    */   
/*    */   public ConfirmScreenBase(class_437 parent, class_437 escape, boolean renderEscapeInBackground, BooleanConsumer _callbackFunction, class_2561 _title, class_2561 _messageLine2) {
/* 21 */     super(_callbackFunction, _title, _messageLine2);
/* 22 */     this.parent = parent;
/* 23 */     this.escape = escape;
/* 24 */     this.renderEscapeInBackground = renderEscapeInBackground;
/* 25 */     this.canSkipWorldRender = true;
/*    */   }
/*    */   
/*    */   public ConfirmScreenBase(class_437 parent, class_437 escape, boolean renderEscapeInBackground, BooleanConsumer p_i232270_1_, class_2561 p_i232270_2_, class_2561 p_i232270_3_, class_2561 p_i232270_4_, class_2561 p_i232270_5_) {
/* 29 */     super(p_i232270_1_, p_i232270_2_, p_i232270_3_, p_i232270_4_, p_i232270_5_);
/* 30 */     this.parent = parent;
/* 31 */     this.escape = escape;
/* 32 */     this.renderEscapeInBackground = renderEscapeInBackground;
/* 33 */     this.canSkipWorldRender = true;
/*    */   }
/*    */   
/*    */   protected void onExit(class_437 screen) {
/* 37 */     this.field_22787.method_1507(screen);
/*    */   }
/*    */   
/*    */   protected void goBack() {
/* 41 */     onExit(this.parent);
/*    */   }
/*    */ 
/*    */   
/*    */   public void method_25419() {
/* 46 */     onExit(this.escape);
/*    */   }
/*    */   
/*    */   public void renderEscapeScreen(class_332 guiGraphics, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
/* 50 */     if (this.escape != null) {
/* 51 */       this.escape.method_47413(guiGraphics, p_230430_2_, p_230430_3_, p_230430_4_);
/* 52 */       GuiRenderUtil.flushGUI();
/* 53 */       TextureUtils.clearRenderTargetDepth(this.field_22787.method_1522(), 1.0F);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void method_25420(class_332 guiGraphics, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
/* 59 */     if (this.renderEscapeInBackground)
/* 60 */       renderEscapeScreen(guiGraphics, 0, 0, p_230430_4_); 
/* 61 */     super.method_25420(guiGraphics, p_230430_2_, p_230430_3_, p_230430_4_);
/*    */   }
/*    */ 
/*    */   
/*    */   public void method_25426() {
/* 66 */     super.method_25426();
/* 67 */     if (this.escape != null) {
/* 68 */       this.escape.method_25423(this.field_22787, this.field_22789, this.field_22790);
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean shouldSkipWorldRender() {
/* 73 */     return (this.canSkipWorldRender && this.renderEscapeInBackground && Misc.screenShouldSkipWorldRender(this.escape, true));
/*    */   }
/*    */   
/*    */   public void onDropdownOpen(DropDownWidget menu) {}
/*    */   
/*    */   public void onDropdownClosed(DropDownWidget menu) {}
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\gui\ConfirmScreenBase.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */