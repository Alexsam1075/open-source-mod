/*    */ package xaero.map.gui;
/*    */ 
/*    */ import java.util.function.BiFunction;
/*    */ import java.util.function.Supplier;
/*    */ import net.minecraft.class_1074;
/*    */ import net.minecraft.class_2561;
/*    */ import net.minecraft.class_310;
/*    */ import net.minecraft.class_339;
/*    */ import net.minecraft.class_4185;
/*    */ import net.minecraft.class_437;
/*    */ 
/*    */ public class ScreenSwitchSettingEntry
/*    */   implements ISettingEntry {
/*    */   private String name;
/*    */   private BiFunction<class_437, class_437, class_437> screenFactory;
/*    */   private Supplier<CursorBox> tooltipSupplier;
/*    */   private boolean active;
/*    */   
/*    */   public ScreenSwitchSettingEntry(String name, BiFunction<class_437, class_437, class_437> screenFactoryFromCurrentAndEscape, CursorBox tooltip, boolean active) {
/* 20 */     this.name = name;
/* 21 */     this.screenFactory = screenFactoryFromCurrentAndEscape;
/* 22 */     this.tooltipSupplier = (() -> tooltip);
/* 23 */     this.active = active;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getStringForSearch() {
/* 28 */     CursorBox entryTooltip = (this.tooltipSupplier == null) ? null : this.tooltipSupplier.get();
/* 29 */     return class_1074.method_4662(this.name, new Object[0]) + " " + class_1074.method_4662(this.name, new Object[0]) + this.name.replace("gui.xaero", "");
/*    */   }
/*    */ 
/*    */   
/*    */   public class_339 createWidget(int x, int y, int w, boolean canEditIngameSettings) {
/* 34 */     TooltipButton button = new TooltipButton(x, y, w, 20, (class_2561)class_2561.method_43471(this.name), b -> { class_310 mc = class_310.method_1551(); class_437 current = mc.field_1755; class_437 currentEscScreen = (current instanceof ScreenBase) ? ((ScreenBase)current).escape : null; class_437 targetScreen = this.screenFactory.apply(current, currentEscScreen); if (current instanceof ScreenBase) { ((ScreenBase)current).onExit(targetScreen); } else { mc.method_1507(targetScreen); }  }this.tooltipSupplier);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 44 */     button.field_22763 = this.active;
/* 45 */     return (class_339)button;
/*    */   }
/*    */   
/*    */   public BiFunction<class_437, class_437, class_437> getScreenFactory() {
/* 49 */     return this.screenFactory;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\gui\ScreenSwitchSettingEntry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */