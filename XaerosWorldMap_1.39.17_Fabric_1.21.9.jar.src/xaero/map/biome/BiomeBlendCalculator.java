/*    */ package xaero.map.biome;
/*    */ 
/*    */ import net.minecraft.class_1920;
/*    */ import net.minecraft.class_1937;
/*    */ import net.minecraft.class_1959;
/*    */ import net.minecraft.class_2338;
/*    */ import net.minecraft.class_2350;
/*    */ import net.minecraft.class_2586;
/*    */ import net.minecraft.class_2680;
/*    */ import net.minecraft.class_3568;
/*    */ import net.minecraft.class_3610;
/*    */ import net.minecraft.class_6539;
/*    */ import net.minecraft.class_6880;
/*    */ 
/*    */ public class BiomeBlendCalculator
/*    */   implements class_1920 {
/*    */   private class_1937 original;
/*    */   
/*    */   public void setWorld(class_1937 original) {
/* 20 */     this.original = original;
/*    */   }
/*    */ 
/*    */   
/*    */   public class_2586 method_8321(class_2338 blockPos) {
/* 25 */     return this.original.method_8321(blockPos);
/*    */   }
/*    */ 
/*    */   
/*    */   public class_2680 method_8320(class_2338 blockPos) {
/* 30 */     return this.original.method_8320(blockPos);
/*    */   }
/*    */ 
/*    */   
/*    */   public class_3610 method_8316(class_2338 blockPos) {
/* 35 */     return this.original.method_8316(blockPos);
/*    */   }
/*    */ 
/*    */   
/*    */   public float method_24852(class_2350 direction, boolean bl) {
/* 40 */     return this.original.method_24852(direction, bl);
/*    */   }
/*    */ 
/*    */   
/*    */   public class_3568 method_22336() {
/* 45 */     return this.original.method_22336();
/*    */   }
/*    */ 
/*    */   
/*    */   public int method_31605() {
/* 50 */     return this.original.method_31605();
/*    */   }
/*    */ 
/*    */   
/*    */   public int method_31607() {
/* 55 */     return this.original.method_31607();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int method_23752(class_2338 blockPos, class_6539 colorResolver) {
/* 61 */     class_2338.class_2339 mutableBlockPos = new class_2338.class_2339();
/* 62 */     int x = blockPos.method_10263();
/* 63 */     int y = blockPos.method_10264();
/* 64 */     int z = blockPos.method_10260();
/* 65 */     int redAccumulator = 0;
/* 66 */     int greenAccumulator = 0;
/* 67 */     int blueAccumulator = 0;
/* 68 */     int count = 0;
/* 69 */     for (int i = -1; i < 2; i++) {
/* 70 */       for (int j = -1; j < 2; j++) {
/* 71 */         mutableBlockPos.method_10103(x + i, y, z + j);
/* 72 */         class_6880<class_1959> biomeHolder = this.original.method_23753((class_2338)mutableBlockPos);
/* 73 */         class_1959 biome = (biomeHolder == null) ? null : (class_1959)biomeHolder.comp_349();
/* 74 */         if (biome != null)
/*    */         
/* 76 */         { int colorSample = colorResolver.getColor(biome, mutableBlockPos.method_10263(), mutableBlockPos.method_10260());
/* 77 */           redAccumulator += colorSample >> 16 & 0xFF;
/* 78 */           greenAccumulator += colorSample >> 8 & 0xFF;
/* 79 */           blueAccumulator += colorSample & 0xFF;
/* 80 */           count++; } 
/*    */       } 
/* 82 */     }  int red = redAccumulator / count;
/* 83 */     int green = greenAccumulator / count;
/* 84 */     int blue = blueAccumulator / count;
/* 85 */     return 0xFF000000 | red << 16 | green << 8 | blue;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\biome\BiomeBlendCalculator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */