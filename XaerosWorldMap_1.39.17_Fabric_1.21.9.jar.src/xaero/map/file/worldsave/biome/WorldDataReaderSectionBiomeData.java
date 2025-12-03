/*    */ package xaero.map.file.worldsave.biome;
/*    */ 
/*    */ import net.minecraft.class_2499;
/*    */ import net.minecraft.class_3508;
/*    */ import net.minecraft.class_3532;
/*    */ import net.minecraft.class_6490;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldDataReaderSectionBiomeData
/*    */ {
/*    */   private final class_2499 paletteTag;
/*    */   private final long[] biomesLongArray;
/*    */   private class_6490 biomesBitArray;
/*    */   private boolean triedReadingData;
/*    */   
/*    */   public WorldDataReaderSectionBiomeData(class_2499 paletteTag, long[] biomesLongArray) {
/* 18 */     this.paletteTag = paletteTag;
/* 19 */     this.biomesLongArray = biomesLongArray;
/*    */   }
/*    */   
/*    */   public boolean hasDifferentBiomes() {
/* 23 */     return (this.biomesLongArray != null);
/*    */   }
/*    */   
/*    */   public String get(int quadX, int sectionQuadY, int quadZ) {
/* 27 */     if (!hasDifferentBiomes())
/* 28 */       return this.paletteTag.isEmpty() ? null : this.paletteTag.method_10608(0).orElse(null); 
/* 29 */     if (!this.triedReadingData && this.biomesBitArray == null && this.biomesLongArray != null) {
/* 30 */       this.triedReadingData = true;
/* 31 */       int bits = class_3532.method_15342(this.paletteTag.size());
/*    */       try {
/* 33 */         this.biomesBitArray = (class_6490)new class_3508(bits, 64, this.biomesLongArray);
/* 34 */       } catch (net.minecraft.class_3508.class_6685 class_6685) {}
/*    */     } 
/* 36 */     if (this.biomesBitArray == null)
/* 37 */       return this.paletteTag.isEmpty() ? null : this.paletteTag.method_10608(0).orElse(null); 
/* 38 */     int pos3D = sectionQuadY << 4 | quadZ << 2 | quadX;
/* 39 */     int biomePaletteIndex = this.biomesBitArray.method_15211(pos3D);
/* 40 */     if (biomePaletteIndex >= this.paletteTag.size())
/* 41 */       return null; 
/* 42 */     return this.paletteTag.method_10608(biomePaletteIndex).orElse(null);
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\file\worldsave\biome\WorldDataReaderSectionBiomeData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */