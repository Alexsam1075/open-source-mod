/*    */ package xaero.map.file.worldsave.biome;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*    */ import net.minecraft.class_1959;
/*    */ import net.minecraft.class_2378;
/*    */ import net.minecraft.class_2960;
/*    */ import xaero.map.misc.CachedFunction;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldDataReaderChunkBiomeData
/*    */ {
/*    */   private Int2ObjectMap<WorldDataReaderSectionBiomeData> sections;
/*    */   
/*    */   public void addSection(int sectionIndex, WorldDataReaderSectionBiomeData section) {
/* 18 */     if (this.sections == null)
/* 19 */       this.sections = (Int2ObjectMap<WorldDataReaderSectionBiomeData>)new Int2ObjectOpenHashMap(); 
/* 20 */     this.sections.put(sectionIndex, section);
/*    */   }
/*    */   
/*    */   public class_1959 getNoiseBiome(int quadX, int quadY, int quadZ, class_2378<class_1959> biomeRegistry, CachedFunction<String, class_2960> resourceLocationCache) {
/* 24 */     if (this.sections == null)
/* 25 */       return null; 
/* 26 */     int sectionIndex = quadY >> 2;
/* 27 */     WorldDataReaderSectionBiomeData section = (WorldDataReaderSectionBiomeData)this.sections.get(sectionIndex);
/* 28 */     if (section == null)
/* 29 */       return null; 
/* 30 */     int sectionQuadY = quadY & 0x3;
/* 31 */     class_2960 biomeLocation = (class_2960)resourceLocationCache.apply(section.get(quadX, sectionQuadY, quadZ));
/* 32 */     if (biomeLocation == null)
/* 33 */       return null; 
/* 34 */     return (class_1959)biomeRegistry.method_63535(biomeLocation);
/*    */   }
/*    */   
/*    */   public void clear() {
/* 38 */     this.sections = null;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\file\worldsave\biome\WorldDataReaderChunkBiomeData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */