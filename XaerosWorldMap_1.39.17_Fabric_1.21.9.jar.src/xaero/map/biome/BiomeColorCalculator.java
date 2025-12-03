/*    */ package xaero.map.biome;
/*    */ 
/*    */ import net.minecraft.class_1959;
/*    */ import net.minecraft.class_1972;
/*    */ import net.minecraft.class_2338;
/*    */ import net.minecraft.class_2378;
/*    */ import net.minecraft.class_2960;
/*    */ import net.minecraft.class_5321;
/*    */ import net.minecraft.class_6539;
/*    */ import net.minecraft.class_7924;
/*    */ import xaero.map.MapProcessor;
/*    */ import xaero.map.region.MapTile;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BiomeColorCalculator
/*    */ {
/* 24 */   public final class_5321<class_1959> UNREACHABLE_BIOME = class_5321.method_29179(class_7924.field_41236, class_2960.method_60654("xaeroworldmap:unreachable_biome"));
/* 25 */   public final class_5321<class_1959> RIVER_BIOME = class_1972.field_9438; private int startO;
/*    */   private int endO;
/*    */   
/*    */   public void prepare(boolean biomeBlending) {
/* 29 */     this.startO = this.endO = this.startP = this.endP = 0;
/* 30 */     if (biomeBlending) {
/* 31 */       this.startO = -1;
/* 32 */       this.endO = 1;
/* 33 */       this.startP = -1;
/* 34 */       this.endP = 1;
/*    */     } 
/*    */   }
/*    */   
/*    */   private int startP;
/*    */   private int endP;
/*    */   
/*    */   public int getBiomeColor(class_6539 stateColorResolver, boolean overlay, class_2338.class_2339 pos, MapTile tile, int caveLayer, class_2378<class_1959> biomeRegistry, MapProcessor mapProcessor) {
/* 42 */     if (stateColorResolver == null)
/* 43 */       return -1; 
/* 44 */     int i = 0;
/* 45 */     int j = 0;
/* 46 */     int k = 0;
/* 47 */     int total = 0;
/* 48 */     int initX = pos.method_10263();
/* 49 */     int initZ = pos.method_10260();
/* 50 */     for (int o = this.startO; o <= this.endO; o++) {
/* 51 */       for (int p = this.startP; p <= this.endP; p++) {
/* 52 */         if (o == 0 || p == 0) {
/*    */           
/* 54 */           pos.method_10103(initX + o, pos.method_10264(), initZ + p);
/* 55 */           class_5321<class_1959> b = getBiomeAtPos((class_2338)pos, tile, caveLayer, mapProcessor);
/* 56 */           if (b != this.UNREACHABLE_BIOME) {
/* 57 */             if (b == null && overlay)
/* 58 */               b = this.RIVER_BIOME; 
/* 59 */             if (b != null) {
/* 60 */               int l = 0;
/* 61 */               class_1959 gen = (class_1959)biomeRegistry.method_29107(b);
/* 62 */               if (gen != null)
/* 63 */               { l = stateColorResolver.getColor(gen, pos.method_10263(), pos.method_10260());
/* 64 */                 i += l & 0xFF0000;
/* 65 */                 j += l & 0xFF00;
/* 66 */                 k += l & 0xFF;
/* 67 */                 total++; } 
/*    */             } 
/*    */           } 
/*    */         } 
/*    */       } 
/* 72 */     }  pos.method_10103(initX, pos.method_10264(), initZ);
/* 73 */     if (total == 0) {
/* 74 */       class_1959 defaultBiome = (class_1959)biomeRegistry.method_29107(class_1972.field_9438);
/* 75 */       if (defaultBiome == null)
/* 76 */         return -1; 
/* 77 */       return stateColorResolver.getColor(defaultBiome, pos.method_10263(), pos.method_10260());
/*    */     } 
/* 79 */     return i / total & 0xFF0000 | j / total & 0xFF00 | k / total;
/*    */   }
/*    */   
/*    */   public class_5321<class_1959> getBiomeAtPos(class_2338 pos, MapTile centerTile, int caveLayer, MapProcessor mapProcessor) {
/* 83 */     int tileX = pos.method_10263() >> 4;
/* 84 */     int tileZ = pos.method_10260() >> 4;
/* 85 */     MapTile tile = (tileX == centerTile.getChunkX() && tileZ == centerTile.getChunkZ()) ? centerTile : mapProcessor.getMapTile(caveLayer, tileX, tileZ);
/* 86 */     if (tile != null && tile.isLoaded())
/* 87 */       return tile.getBlock(pos.method_10263() & 0xF, pos.method_10260() & 0xF).getBiome(); 
/* 88 */     return this.UNREACHABLE_BIOME;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\biome\BiomeColorCalculator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */