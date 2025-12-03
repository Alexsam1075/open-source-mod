/*    */ package xaero.map.biome;
/*    */ 
/*    */ import net.minecraft.class_1920;
/*    */ import net.minecraft.class_1959;
/*    */ import net.minecraft.class_2338;
/*    */ import net.minecraft.class_2350;
/*    */ import net.minecraft.class_2378;
/*    */ import net.minecraft.class_2382;
/*    */ import net.minecraft.class_2586;
/*    */ import net.minecraft.class_2680;
/*    */ import net.minecraft.class_310;
/*    */ import net.minecraft.class_3568;
/*    */ import net.minecraft.class_3610;
/*    */ import net.minecraft.class_6539;
/*    */ import xaero.map.MapProcessor;
/*    */ import xaero.map.MapWriter;
/*    */ import xaero.map.WorldMap;
/*    */ import xaero.map.cache.BrokenBlockTintCache;
/*    */ import xaero.map.region.MapTile;
/*    */ 
/*    */ public class BlockTintProvider
/*    */   implements class_1920
/*    */ {
/*    */   private final class_2378<class_1959> biomeRegistry;
/*    */   private final BiomeColorCalculator calculator;
/*    */   private final class_2338.class_2339 mutablePos;
/*    */   private final MapProcessor mapProcessor;
/*    */   private final BrokenBlockTintCache brokenBlockTintCache;
/*    */   private final MapWriter mapWriter;
/*    */   private class_2680 state;
/*    */   private boolean overlay;
/*    */   private MapTile tile;
/*    */   private int caveLayer;
/*    */   
/*    */   public BlockTintProvider(class_2378<class_1959> biomeRegistry, BiomeColorCalculator calculator, MapProcessor mapProcessor, BrokenBlockTintCache brokenBlockTintCache, MapWriter mapWriter) {
/* 36 */     this.biomeRegistry = biomeRegistry;
/* 37 */     this.calculator = calculator;
/* 38 */     this.mutablePos = new class_2338.class_2339();
/* 39 */     this.mapProcessor = mapProcessor;
/* 40 */     this.brokenBlockTintCache = brokenBlockTintCache;
/* 41 */     this.mapWriter = mapWriter;
/*    */   }
/*    */ 
/*    */   
/*    */   public int method_31605() {
/* 46 */     return 16;
/*    */   }
/*    */ 
/*    */   
/*    */   public int method_31607() {
/* 51 */     return this.mutablePos.method_10264() >> 4 << 4;
/*    */   }
/*    */ 
/*    */   
/*    */   public class_2586 method_8321(class_2338 blockPos) {
/* 56 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public class_2680 method_8320(class_2338 blockPos) {
/* 61 */     return this.state;
/*    */   }
/*    */ 
/*    */   
/*    */   public class_3610 method_8316(class_2338 blockPos) {
/* 66 */     return (this.state == null) ? null : this.state.method_26227();
/*    */   }
/*    */ 
/*    */   
/*    */   public float method_24852(class_2350 direction, boolean bl) {
/* 71 */     return 1.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   public class_3568 method_22336() {
/* 76 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public int method_23752(class_2338 blockPos, class_6539 colorResolver) {
/* 81 */     this.mutablePos.method_10101((class_2382)blockPos);
/* 82 */     return this.calculator.getBiomeColor(colorResolver, this.overlay, this.mutablePos, this.tile, this.caveLayer, this.biomeRegistry, this.mapProcessor);
/*    */   }
/*    */   
/*    */   public int getBiomeColor(class_2338 blockPos, class_2680 state, boolean overlay, MapTile tile, int caveLayer) {
/* 86 */     if (this.brokenBlockTintCache.isBroken(state))
/* 87 */       return -1; 
/* 88 */     this.mutablePos.method_10101((class_2382)blockPos);
/* 89 */     this.state = state;
/* 90 */     this.overlay = overlay;
/* 91 */     this.tile = tile;
/* 92 */     this.caveLayer = caveLayer;
/*    */     try {
/* 94 */       return class_310.method_1551().method_1505().method_1697(state, this, blockPos, this.mapWriter.getBlockTintIndex(state));
/* 95 */     } catch (Throwable t) {
/* 96 */       this.brokenBlockTintCache.setBroken(state);
/* 97 */       WorldMap.LOGGER.error("Error calculating block tint for block state " + String.valueOf(state) + "! Total: " + this.brokenBlockTintCache.getSize(), t);
/* 98 */       return -1;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\biome\BlockTintProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */