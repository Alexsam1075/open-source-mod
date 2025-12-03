/*    */ package xaero.map.gui;
/*    */ 
/*    */ import net.minecraft.class_2561;
/*    */ import net.minecraft.class_4185;
/*    */ import xaero.map.settings.ModOptions;
/*    */ 
/*    */ public class MyTinyButton
/*    */   extends class_4185
/*    */ {
/*    */   private final ModOptions modOptions;
/*    */   
/*    */   public MyTinyButton(int par1, int par2, class_2561 par4Str, class_4185.class_4241 onPress) {
/* 13 */     this(par1, par2, (ModOptions)null, par4Str, onPress);
/*    */   }
/*    */ 
/*    */   
/*    */   public MyTinyButton(int par1, int par2, int par3, int par4, class_2561 par6Str, class_4185.class_4241 onPress) {
/* 18 */     super(par1, par2, par3, par4, par6Str, onPress, field_40754);
/* 19 */     this.modOptions = null;
/*    */   }
/*    */ 
/*    */   
/*    */   public MyTinyButton(int par1, int par2, ModOptions par4EnumOptions, class_2561 par5Str, class_4185.class_4241 onPress) {
/* 24 */     super(par1, par2, 75, 20, par5Str, onPress, field_40754);
/* 25 */     this.modOptions = par4EnumOptions;
/*    */   }
/*    */ 
/*    */   
/*    */   public ModOptions returnModOptions() {
/* 30 */     return this.modOptions;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\gui\MyTinyButton.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */