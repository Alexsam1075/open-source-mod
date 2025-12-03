/*    */ package xaero.map.settings;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.function.Consumer;
/*    */ import java.util.function.Supplier;
/*    */ import net.minecraft.class_2561;
/*    */ import net.minecraft.class_339;
/*    */ import net.minecraft.class_5676;
/*    */ import xaero.map.gui.CursorBox;
/*    */ import xaero.map.gui.IXaeroClickableWidget;
/*    */ 
/*    */ public class XaeroCyclingOption<T>
/*    */   extends Option {
/*    */   private List<T> values;
/*    */   private Supplier<T> getter;
/*    */   private Consumer<T> setter;
/*    */   private Supplier<class_2561> buttonNameSupplier;
/*    */   
/*    */   public XaeroCyclingOption(ModOptions option, List<T> values, Supplier<T> getter, Consumer<T> setter, Supplier<class_2561> buttonNameSupplier) {
/* 20 */     super(option);
/* 21 */     this.values = values;
/* 22 */     this.getter = getter;
/* 23 */     this.setter = setter;
/* 24 */     this.buttonNameSupplier = buttonNameSupplier;
/*    */   }
/*    */ 
/*    */   
/*    */   public class_339 createButton(int x, int y, int width) {
/* 29 */     class_5676<T> resultButton = class_5676.method_32606(v -> (class_2561)this.buttonNameSupplier.get()).method_32620(this.values).method_32619(this.getter.get()).method_32617(x, y, width, 20, getCaption(), (button, value) -> {
/*    */           this.setter.accept((T)value);
/*    */           button.method_32605(value);
/*    */         });
/* 33 */     ((IXaeroClickableWidget)resultButton).setXaero_wm_tooltip(() -> this.option.getTooltip());
/* 34 */     return (class_339)resultButton;
/*    */   }
/*    */   
/*    */   public String getSearchText() {
/* 38 */     return this.option.getEnumString() + ": " + this.option.getEnumString();
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\settings\XaeroCyclingOption.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */