/*    */ package xaero.map.gui;
/*    */ 
/*    */ import java.util.function.Supplier;
/*    */ import net.minecraft.class_10799;
/*    */ import net.minecraft.class_2561;
/*    */ import net.minecraft.class_2960;
/*    */ import net.minecraft.class_332;
/*    */ import net.minecraft.class_4185;
/*    */ 
/*    */ 
/*    */ public class GuiTexturedButton
/*    */   extends TooltipButton
/*    */ {
/*    */   protected int textureX;
/*    */   protected int textureY;
/*    */   protected int textureW;
/*    */   protected int textureH;
/*    */   protected final int factorW;
/*    */   protected final int factorH;
/*    */   protected class_2960 texture;
/*    */   
/*    */   public GuiTexturedButton(int x, int y, int w, int h, int textureX, int textureY, int textureW, int textureH, class_2960 texture, class_4185.class_4241 onPress, Supplier<CursorBox> tooltip, int factorW, int factorH) {
/* 23 */     super(x, y, w, h, (class_2561)class_2561.method_43470(""), onPress, tooltip);
/* 24 */     this.textureX = textureX;
/* 25 */     this.textureY = textureY;
/* 26 */     this.textureW = textureW;
/* 27 */     this.textureH = textureH;
/* 28 */     this.texture = texture;
/* 29 */     this.factorW = factorW;
/* 30 */     this.factorH = factorH;
/*    */   }
/*    */ 
/*    */   
/*    */   public class_2561 method_25369() {
/* 35 */     if (this.tooltipSupplier != null)
/* 36 */       return (class_2561)class_2561.method_43470(((CursorBox)this.tooltipSupplier.get()).getPlainText()); 
/* 37 */     return super.method_25369();
/*    */   }
/*    */ 
/*    */   
/*    */   public void method_48579(class_332 guiGraphics, int p_renderButton_1_, int p_renderButton_2_, float p_renderButton_3_) {
/* 42 */     int iconX = method_46426() + this.field_22758 / 2 - this.textureW / 2;
/* 43 */     int iconY = method_46427() + this.field_22759 / 2 - this.textureH / 2;
/* 44 */     int color = -12566464;
/* 45 */     if (this.field_22763)
/* 46 */       if (this.field_22762) {
/* 47 */         iconY--;
/* 48 */         color = -1644826;
/*    */       } else {
/* 50 */         color = -197380;
/*    */       }  
/* 52 */     if (method_25370())
/* 53 */       guiGraphics.method_25294(iconX, iconY, iconX + this.textureW, iconY + this.textureH, 1442840575); 
/* 54 */     guiGraphics.method_25291(class_10799.field_56883, this.texture, iconX, iconY, this.textureX, this.textureY, this.textureW, this.textureH, this.factorW, this.factorH, color);
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\gui\GuiTexturedButton.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */