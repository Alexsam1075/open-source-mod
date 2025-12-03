/*    */ package xaero.map.mixin;
/*    */ 
/*    */ import java.util.function.Supplier;
/*    */ import net.minecraft.class_5676;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import xaero.map.gui.CursorBox;
/*    */ import xaero.map.gui.IXaeroClickableWidget;
/*    */ 
/*    */ @Mixin({class_5676.class})
/*    */ public class MixinCyclingButtonWidget
/*    */   implements IXaeroClickableWidget
/*    */ {
/*    */   private Supplier<CursorBox> xaero_wm_tooltip;
/*    */   
/*    */   public Supplier<CursorBox> getXaero_wm_tooltip() {
/* 16 */     return this.xaero_wm_tooltip;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setXaero_wm_tooltip(Supplier<CursorBox> tooltip) {
/* 21 */     this.xaero_wm_tooltip = tooltip;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\mixin\MixinCyclingButtonWidget.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */