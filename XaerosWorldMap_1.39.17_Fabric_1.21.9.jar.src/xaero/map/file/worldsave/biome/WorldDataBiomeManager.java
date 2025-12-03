/*    */ package xaero.map.file.worldsave.biome;
/*    */ 
/*    */ import net.minecraft.class_1959;
/*    */ import net.minecraft.class_1972;
/*    */ import net.minecraft.class_2338;
/*    */ import net.minecraft.class_2378;
/*    */ import net.minecraft.class_2960;
/*    */ import net.minecraft.class_4543;
/*    */ import net.minecraft.class_6880;
/*    */ import xaero.map.misc.CachedFunction;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldDataBiomeManager
/*    */   implements class_4543.class_4544
/*    */ {
/* 26 */   private final WorldDataReaderChunkBiomeData[] chunkBiomeData = new WorldDataReaderChunkBiomeData[1156]; private int regionX; private int regionZ; private class_1959 theVoid; public WorldDataBiomeManager() {
/* 27 */     for (int i = 0; i < this.chunkBiomeData.length; i++)
/* 28 */       this.chunkBiomeData[i] = new WorldDataReaderChunkBiomeData(); 
/* 29 */     this.mutableBlockPos = new class_2338.class_2339();
/* 30 */     this.resourceLocationCache = new CachedFunction(class_2960::method_60654);
/*    */   }
/*    */   private class_1959 defaultBiome; private class_2378<class_1959> biomeRegistry; private class_2338.class_2339 mutableBlockPos; private CachedFunction<String, class_2960> resourceLocationCache;
/*    */   public void resetChunkBiomeData(int regionX, int regionZ, class_1959 defaultBiome, class_2378<class_1959> biomeRegistry) {
/* 34 */     this.regionX = regionX;
/* 35 */     this.regionZ = regionZ;
/* 36 */     this.biomeRegistry = biomeRegistry;
/* 37 */     this.theVoid = (class_1959)biomeRegistry.method_29107(class_1972.field_9473);
/*    */   }
/*    */   
/*    */   public void clear() {
/* 41 */     for (int i = 0; i < this.chunkBiomeData.length; i++)
/* 42 */       this.chunkBiomeData[i].clear(); 
/*    */   }
/*    */   
/*    */   private WorldDataReaderChunkBiomeData getChunkBiomeData(int chunkX, int chunkZ) {
/* 46 */     if (chunkX < -1 || chunkZ < -1 || chunkX > 32 || chunkZ > 32)
/* 47 */       return null; 
/* 48 */     return this.chunkBiomeData[(chunkZ + 1) * 34 + chunkX + 1];
/*    */   }
/*    */   
/*    */   public void addBiomeSectionForRegionChunk(int chunkX, int chunkZ, int sectionIndex, WorldDataReaderSectionBiomeData section) {
/* 52 */     getChunkBiomeData(chunkX, chunkZ).addSection(sectionIndex, section);
/*    */   }
/*    */   
/*    */   public class_1959 getBiome(class_4543 biomeZoomer, int x, int y, int z) {
/* 56 */     this.defaultBiome = null;
/* 57 */     this.defaultBiome = (class_1959)method_16359(x >> 2, y >> 2, z >> 2).comp_349();
/* 58 */     if (this.defaultBiome == null)
/* 59 */       this.defaultBiome = this.theVoid; 
/* 60 */     this.mutableBlockPos.method_10103(x, y, z);
/* 61 */     return (class_1959)biomeZoomer.method_22393((class_2338)this.mutableBlockPos).comp_349();
/*    */   }
/*    */ 
/*    */   
/*    */   public class_6880<class_1959> method_16359(int x, int y, int z) {
/* 66 */     int relativeX = x - this.regionX * 128;
/* 67 */     int relativeZ = z - this.regionZ * 128;
/* 68 */     int chunkX = relativeX >> 2;
/* 69 */     int chunkZ = relativeZ >> 2;
/* 70 */     int quadX = relativeX & 0x3;
/* 71 */     int quadZ = relativeZ & 0x3;
/* 72 */     WorldDataReaderChunkBiomeData chunkBiomeData = getChunkBiomeData(chunkX, chunkZ);
/* 73 */     if (chunkBiomeData == null)
/* 74 */       return class_6880.method_40223(this.defaultBiome); 
/* 75 */     class_1959 biome = chunkBiomeData.getNoiseBiome(quadX, y, quadZ, this.biomeRegistry, this.resourceLocationCache);
/* 76 */     if (biome == null)
/* 77 */       return class_6880.method_40223(this.defaultBiome); 
/* 78 */     return class_6880.method_40223(biome);
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\file\worldsave\biome\WorldDataBiomeManager.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */