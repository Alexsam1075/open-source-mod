/*    */ package xaero.map.gui;
/*    */ 
/*    */ import net.minecraft.class_4185;
/*    */ import xaero.map.WorldMap;
/*    */ 
/*    */ public class GuiMapSwitchingButton
/*    */   extends GuiTexturedButton {
/*  8 */   public static final CursorBox TOOLTIP = new CursorBox("gui.xaero_box_map_switching");
/*    */   
/*    */   public GuiMapSwitchingButton(boolean menuActive, int x, int y, class_4185.class_4241 onPress) {
/* 11 */     super(x, y, 20, 20, menuActive ? 97 : 81, 0, 16, 16, WorldMap.guiTextures, onPress, () -> TOOLTIP, 256, 256);
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\gui\GuiMapSwitchingButton.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */