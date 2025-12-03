/*    */ package xaero.map.gui.dropdown.rightclick;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.function.Supplier;
/*    */ import net.minecraft.class_437;
/*    */ import xaero.map.gui.GuiMap;
/*    */ import xaero.map.gui.IRightClickableElement;
/*    */ import xaero.map.gui.dropdown.DropDownWidget;
/*    */ import xaero.map.gui.dropdown.IDropDownContainer;
/*    */ 
/*    */ public class GuiRightClickMenu
/*    */   extends DropDownWidget {
/*    */   private IRightClickableElement target;
/*    */   private ArrayList<RightClickOption> actionOptions;
/*    */   private class_437 screen;
/*    */   private boolean removed;
/*    */   
/*    */   private GuiRightClickMenu(IRightClickableElement target, ArrayList<RightClickOption> options, class_437 screen, int x, int y, int w, int titleBackgroundColor, IDropDownContainer container) {
/* 19 */     super((String[])((ArrayList)options.stream().map(o -> o.getDisplayName()).collect(ArrayList::new, ArrayList::add, ArrayList::addAll)).toArray((Object[])new String[0]), x - (shouldOpenLeft(options.size(), x, w, screen.field_22789) ? w : 0), y, w, 
/* 20 */         Integer.valueOf(-1), false, null, container, false, null);
/* 21 */     this.openingUp = false;
/* 22 */     this.target = target;
/* 23 */     this.screen = screen;
/* 24 */     setClosed(false);
/* 25 */     this.actionOptions = options;
/* 26 */     this.selectedBackground = this.selectedHoveredBackground = titleBackgroundColor;
/* 27 */     this.shortenFromTheRight = true;
/*    */   }
/*    */ 
/*    */   
/*    */   private static boolean shouldOpenLeft(int optionCount, int x, int w, int screenWidth) {
/* 32 */     return (x + w - screenWidth > 0);
/*    */   }
/*    */   
/*    */   private static boolean shouldOpenUp(int optionCount, int y, int screenHeight) {
/* 36 */     int potentialHeight = 11 * optionCount;
/* 37 */     return (y + potentialHeight - screenHeight > potentialHeight / 2);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setClosed(boolean closed) {
/* 49 */     if (!isClosed() && closed)
/* 50 */       this.removed = true; 
/* 51 */     super.setClosed(closed);
/*    */   }
/*    */ 
/*    */   
/*    */   public void selectId(int id, boolean callCallback) {
/* 56 */     if (id == -1)
/*    */       return; 
/* 58 */     if (this.removed)
/*    */       return; 
/* 60 */     ((RightClickOption)this.actionOptions.get(id)).onSelected(this.screen);
/* 61 */     setClosed(true);
/*    */   }
/*    */   
/*    */   public static GuiRightClickMenu getMenu(IRightClickableElement rightClickable, GuiMap screen, int x, int y, int w) {
/* 65 */     return new GuiRightClickMenu(rightClickable, rightClickable.getRightClickOptions(), (class_437)screen, x, y, w, rightClickable.getRightClickTitleBackgroundColor(), (IDropDownContainer)screen);
/*    */   }
/*    */   
/*    */   public IRightClickableElement getTarget() {
/* 69 */     return this.target;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\gui\dropdown\rightclick\GuiRightClickMenu.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */