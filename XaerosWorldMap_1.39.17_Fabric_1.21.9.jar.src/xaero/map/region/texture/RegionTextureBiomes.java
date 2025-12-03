/*    */ package xaero.map.region.texture;
/*    */ 
/*    */ import net.minecraft.class_1959;
/*    */ import net.minecraft.class_5321;
/*    */ import xaero.map.palette.FastPalette;
/*    */ import xaero.map.palette.Paletted2DFastBitArrayIntStorage;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RegionTextureBiomes
/*    */ {
/*    */   protected final Paletted2DFastBitArrayIntStorage biomeIndexStorage;
/*    */   protected final FastPalette<class_5321<class_1959>> regionBiomePalette;
/*    */   
/*    */   public RegionTextureBiomes(Paletted2DFastBitArrayIntStorage biomeIndexStorage, FastPalette<class_5321<class_1959>> regionBiomePalette) {
/* 16 */     this.biomeIndexStorage = biomeIndexStorage;
/* 17 */     this.regionBiomePalette = regionBiomePalette;
/*    */   }
/*    */   
/*    */   public Paletted2DFastBitArrayIntStorage getBiomeIndexStorage() {
/* 21 */     return this.biomeIndexStorage;
/*    */   }
/*    */   
/*    */   public FastPalette<class_5321<class_1959>> getRegionBiomePalette() {
/* 25 */     return this.regionBiomePalette;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\region\texture\RegionTextureBiomes.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */