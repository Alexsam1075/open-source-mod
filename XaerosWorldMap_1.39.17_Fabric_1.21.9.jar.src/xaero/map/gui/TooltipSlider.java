/*    */ package xaero.map.gui;
/*    */ 
/*    */ import java.util.function.Consumer;
/*    */ import java.util.function.Function;
/*    */ import java.util.function.Supplier;
/*    */ import net.minecraft.class_2561;
/*    */ import net.minecraft.class_357;
/*    */ 
/*    */ public class TooltipSlider
/*    */   extends class_357
/*    */   implements ICanTooltip {
/*    */   protected final Supplier<CursorBox> tooltipSupplier;
/*    */   protected final Consumer<Double> onValue;
/*    */   protected final Function<TooltipSlider, class_2561> messageUpdater;
/*    */   
/*    */   public TooltipSlider(int x, int y, int w, int h, class_2561 message, double value, Consumer<Double> onValue, Function<TooltipSlider, class_2561> messageUpdater, Supplier<CursorBox> tooltipSupplier) {
/* 17 */     super(x, y, w, h, message, value);
/* 18 */     this.tooltipSupplier = tooltipSupplier;
/* 19 */     this.onValue = onValue;
/* 20 */     this.messageUpdater = messageUpdater;
/*    */   }
/*    */ 
/*    */   
/*    */   public Supplier<CursorBox> getXaero_wm_tooltip() {
/* 25 */     return this.tooltipSupplier;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void method_25346() {
/* 30 */     method_25355(this.messageUpdater.apply(this));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void method_25344() {
/* 35 */     this.onValue.accept(Double.valueOf(this.field_22753));
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\gui\TooltipSlider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */