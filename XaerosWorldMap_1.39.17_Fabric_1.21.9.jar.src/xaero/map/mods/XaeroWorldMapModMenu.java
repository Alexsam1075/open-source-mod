/*    */ package xaero.map.mods;
/*    */ 
/*    */ import com.terraformersmc.modmenu.api.ConfigScreenFactory;
/*    */ import com.terraformersmc.modmenu.api.ModMenuApi;
/*    */ import xaero.map.gui.GuiSettings;
/*    */ 
/*    */ 
/*    */ public class XaeroWorldMapModMenu
/*    */   implements ModMenuApi
/*    */ {
/*    */   public ConfigScreenFactory<GuiSettings> getModConfigScreenFactory() {
/* 12 */     return xaero.map.gui.GuiWorldMapSettings::new;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\mods\XaeroWorldMapModMenu.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */