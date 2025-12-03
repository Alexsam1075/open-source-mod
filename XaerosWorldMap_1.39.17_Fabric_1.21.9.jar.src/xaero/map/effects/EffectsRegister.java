/*    */ package xaero.map.effects;
/*    */ 
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.class_1291;
/*    */ import net.minecraft.class_6880;
/*    */ 
/*    */ 
/*    */ public class EffectsRegister
/*    */ {
/*    */   public void registerEffects(Function<WorldMapStatusEffect, class_6880<class_1291>> registry) {
/* 11 */     Effects.init();
/*    */     
/* 13 */     Effects.NO_WORLD_MAP = registry.apply(Effects.NO_WORLD_MAP_UNHELD);
/* 14 */     Effects.NO_WORLD_MAP_HARMFUL = registry.apply(Effects.NO_WORLD_MAP_HARMFUL_UNHELD);
/* 15 */     Effects.NO_CAVE_MAPS = registry.apply(Effects.NO_CAVE_MAPS_UNHELD);
/* 16 */     Effects.NO_CAVE_MAPS_HARMFUL = registry.apply(Effects.NO_CAVE_MAPS_HARMFUL_UNHELD);
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\effects\EffectsRegister.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */