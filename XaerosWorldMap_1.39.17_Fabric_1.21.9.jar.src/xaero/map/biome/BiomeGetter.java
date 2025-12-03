/*    */ package xaero.map.biome;
/*    */ 
/*    */ import net.minecraft.class_1937;
/*    */ import net.minecraft.class_1959;
/*    */ import net.minecraft.class_2338;
/*    */ import net.minecraft.class_2378;
/*    */ import net.minecraft.class_2960;
/*    */ import net.minecraft.class_5321;
/*    */ import net.minecraft.class_6880;
/*    */ import net.minecraft.class_7924;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BiomeGetter
/*    */ {
/* 19 */   public final class_5321<class_1959> UNKNOWN_BIOME = class_5321.method_29179(class_7924.field_41236, class_2960.method_60654("xaeroworldmap:unknown_biome"));
/* 20 */   public final class_5321<class_1959> PLACEHOLDER_BIOME = class_5321.method_29179(class_7924.field_41236, class_2960.method_60654("xaeroworldmap:placeholder_biome"));
/*    */ 
/*    */   
/*    */   public class_5321<class_1959> getBiome(class_1937 world, class_2338 pos, class_2378<class_1959> biomeRegistry) {
/* 24 */     class_6880<class_1959> biomeHolder = world.method_23753(pos);
/* 25 */     class_1959 biome = (biomeHolder == null) ? null : (class_1959)biomeHolder.comp_349();
/* 26 */     return biomeRegistry.method_29113(biome).orElse(this.UNKNOWN_BIOME);
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\biome\BiomeGetter.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */