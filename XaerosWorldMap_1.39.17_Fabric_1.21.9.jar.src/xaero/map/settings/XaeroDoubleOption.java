/*    */ package xaero.map.settings;
/*    */ 
/*    */ import java.util.function.Consumer;
/*    */ import java.util.function.Supplier;
/*    */ import net.minecraft.class_2561;
/*    */ import net.minecraft.class_339;
/*    */ import xaero.map.gui.XaeroDoubleOptionWidget;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class XaeroDoubleOption
/*    */   extends Option
/*    */ {
/*    */   private final double min;
/*    */   private final double max;
/*    */   private final float step;
/*    */   private final Supplier<Double> getter;
/*    */   private final Consumer<Double> setter;
/*    */   private final Supplier<class_2561> displayStringGetter;
/*    */   
/*    */   public XaeroDoubleOption(ModOptions option, double min, double max, float step, Supplier<Double> getter, Consumer<Double> setter, Supplier<class_2561> displayStringGetter) {
/* 22 */     super(option);
/* 23 */     this.min = min;
/* 24 */     this.max = max;
/* 25 */     this.step = step;
/* 26 */     this.getter = getter;
/* 27 */     this.setter = setter;
/* 28 */     this.displayStringGetter = displayStringGetter;
/*    */   }
/*    */   
/*    */   public double getMin() {
/* 32 */     return this.min;
/*    */   }
/*    */   
/*    */   public double getMax() {
/* 36 */     return this.max;
/*    */   }
/*    */   
/*    */   public float getStep() {
/* 40 */     return this.step;
/*    */   }
/*    */   
/*    */   public Supplier<Double> getGetter() {
/* 44 */     return this.getter;
/*    */   }
/*    */   
/*    */   public Consumer<Double> getSetter() {
/* 48 */     return this.setter;
/*    */   }
/*    */   
/*    */   public Supplier<class_2561> getDisplayStringGetter() {
/* 52 */     return this.displayStringGetter;
/*    */   }
/*    */   
/*    */   public ModOptions getOption() {
/* 56 */     return this.option;
/*    */   }
/*    */ 
/*    */   
/*    */   public class_339 createButton(int x, int y, int width) {
/* 61 */     return (class_339)new XaeroDoubleOptionWidget(this, x, y, width, 20);
/*    */   }
/*    */   
/*    */   public class_2561 getMessage() {
/* 65 */     return this.displayStringGetter.get();
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\settings\XaeroDoubleOption.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */