/*    */ package xaero.map;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.class_1058;
/*    */ import net.minecraft.class_1087;
/*    */ import net.minecraft.class_10889;
/*    */ import net.minecraft.class_11515;
/*    */ import net.minecraft.class_1937;
/*    */ import net.minecraft.class_2338;
/*    */ import net.minecraft.class_2350;
/*    */ import net.minecraft.class_2680;
/*    */ import net.minecraft.class_4696;
/*    */ import net.minecraft.class_773;
/*    */ import net.minecraft.class_777;
/*    */ import xaero.map.biome.BiomeGetter;
/*    */ import xaero.map.cache.BlockStateShortShapeCache;
/*    */ import xaero.map.region.OverlayManager;
/*    */ 
/*    */ public class MapWriterFabric
/*    */   extends MapWriter {
/*    */   public MapWriterFabric(OverlayManager overlayManager, BlockStateShortShapeCache blockStateShortShapeCache, BiomeGetter biomeGetter) {
/* 22 */     super(overlayManager, blockStateShortShapeCache, biomeGetter);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean blockStateHasTranslucentRenderType(class_2680 blockState) {
/* 27 */     return (class_4696.method_23679(blockState) == class_11515.field_60926);
/*    */   }
/*    */ 
/*    */   
/*    */   protected List<class_777> getQuads(class_1087 model, class_1937 level, class_2338 pos, class_2680 state, class_2350 direction) {
/* 32 */     this.reusableBlockModelPartList.clear();
/* 33 */     model.method_68513(this.usedRandom, this.reusableBlockModelPartList);
/* 34 */     if (this.reusableBlockModelPartList.isEmpty())
/* 35 */       return null; 
/* 36 */     return ((class_10889)this.reusableBlockModelPartList.getFirst()).method_68509(direction);
/*    */   }
/*    */ 
/*    */   
/*    */   protected class_1058 getParticleIcon(class_773 bms, class_1087 model, class_1937 level, class_2338 pos, class_2680 state) {
/* 41 */     return bms.method_3339(state);
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\MapWriterFabric.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */