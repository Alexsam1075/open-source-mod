/*    */ package xaero.map.gui;
/*    */ 
/*    */ import java.util.function.Supplier;
/*    */ import net.minecraft.class_357;
/*    */ import xaero.map.settings.XaeroDoubleOption;
/*    */ 
/*    */ public class XaeroDoubleOptionWidget
/*    */   extends class_357
/*    */   implements IXaeroClickableWidget {
/*    */   private XaeroDoubleOption option;
/*    */   
/*    */   public XaeroDoubleOptionWidget(XaeroDoubleOption option, int x, int y, int width, int height) {
/* 13 */     super(x, y, width, height, option.getMessage(), option.getOption().normalizeValue(((Double)option.getGetter().get()).doubleValue()));
/* 14 */     this.option = option;
/*    */   }
/*    */ 
/*    */   
/*    */   public Supplier<CursorBox> getXaero_wm_tooltip() {
/* 19 */     return () -> this.option.getOption().getTooltip();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setXaero_wm_tooltip(Supplier<CursorBox> tooltip) {}
/*    */ 
/*    */ 
/*    */   
/*    */   protected void method_25346() {
/* 29 */     method_25355(this.option.getMessage());
/*    */   }
/*    */ 
/*    */   
/*    */   protected void method_25344() {
/* 34 */     this.option.getSetter().accept(Double.valueOf(this.option.getOption().denormalizeValue(this.field_22753)));
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\gui\XaeroDoubleOptionWidget.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */