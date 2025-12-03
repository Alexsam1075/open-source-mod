/*    */ package xaero.map.gui;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import xaero.map.gui.dropdown.rightclick.RightClickOption;
/*    */ 
/*    */ public interface IRightClickableElement {
/*    */   ArrayList<RightClickOption> getRightClickOptions();
/*    */   
/*    */   boolean isRightClickValid();
/*    */   
/*    */   default int getRightClickTitleBackgroundColor() {
/* 12 */     return -10496;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\gui\IRightClickableElement.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */