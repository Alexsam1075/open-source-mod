/*    */ package xaero.map.gui;
/*    */ 
/*    */ import java.util.function.Supplier;
/*    */ import net.minecraft.class_2561;
/*    */ import net.minecraft.class_4185;
/*    */ 
/*    */ public class TooltipButton
/*    */   extends class_4185
/*    */   implements ICanTooltip
/*    */ {
/*    */   protected Supplier<CursorBox> tooltipSupplier;
/*    */   
/*    */   public TooltipButton(int x, int y, int w, int h, class_2561 message, class_4185.class_4241 action, Supplier<CursorBox> tooltipSupplier) {
/* 14 */     super(x, y, w, h, message, action, field_40754);
/* 15 */     this.tooltipSupplier = tooltipSupplier;
/*    */   }
/*    */ 
/*    */   
/*    */   public Supplier<CursorBox> getXaero_wm_tooltip() {
/* 20 */     return this.tooltipSupplier;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\gui\TooltipButton.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */