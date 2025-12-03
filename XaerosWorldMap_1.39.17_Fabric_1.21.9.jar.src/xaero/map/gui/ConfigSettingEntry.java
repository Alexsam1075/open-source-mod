/*    */ package xaero.map.gui;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ import net.minecraft.class_339;
/*    */ import xaero.map.WorldMap;
/*    */ import xaero.map.settings.ModOptions;
/*    */ import xaero.map.settings.Option;
/*    */ import xaero.map.settings.XaeroCyclingOption;
/*    */ import xaero.map.settings.XaeroDoubleOption;
/*    */ 
/*    */ public class ConfigSettingEntry
/*    */   implements ISettingEntry
/*    */ {
/* 16 */   public static final Set<ModOptions> FILE_ONLY_ENABLE = new HashSet<>(Lists.newArrayList((Object[])new ModOptions[] { ModOptions.MAP_TELEPORT_ALLOWED }));
/*    */   
/*    */   private ModOptions option;
/*    */   
/*    */   public ConfigSettingEntry(ModOptions option) {
/* 21 */     this.option = option;
/*    */   }
/*    */ 
/*    */   
/*    */   public class_339 createWidget(int x, int y, int w, boolean canEditIngameSettings) {
/* 26 */     class_339 widget = this.option.getXOption().createButton(x, y, w);
/* 27 */     widget.field_22763 = (!this.option.isDisabledBecauseNotIngame() && !this.option.isDisabledBecauseMinimap() && !this.option.isDisabledBecausePac());
/*    */     
/* 29 */     if (widget.field_22763 && FILE_ONLY_ENABLE.contains(this.option) && !WorldMap.settings.getClientBooleanValue(this.option))
/* 30 */       widget.field_22763 = false; 
/* 31 */     return widget;
/*    */   }
/*    */   
/*    */   public String getStringForSearch() {
/*    */     String tooltipPart;
/* 36 */     Option mcOption = this.option.getXOption();
/* 37 */     CursorBox optionTooltip = this.option.getTooltip();
/* 38 */     String mainText = (mcOption instanceof XaeroCyclingOption) ? ((XaeroCyclingOption)mcOption).getSearchText() : ((mcOption instanceof XaeroDoubleOption) ? ((XaeroDoubleOption)mcOption).getMessage().getString() : "");
/*    */     
/* 40 */     if (optionTooltip != null) {
/* 41 */       tooltipPart = " " + optionTooltip.getPlainText();
/* 42 */       if (optionTooltip.getFullCode() != null)
/* 43 */         tooltipPart = tooltipPart + " " + tooltipPart; 
/*    */     } else {
/* 45 */       tooltipPart = "";
/* 46 */     }  return mainText + " " + mainText + this.option.getEnumStringRaw().replace("gui.xaero", "");
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 51 */     return this.option.hashCode();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 56 */     return (obj instanceof ConfigSettingEntry && ((ConfigSettingEntry)obj).option == this.option);
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\gui\ConfigSettingEntry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */