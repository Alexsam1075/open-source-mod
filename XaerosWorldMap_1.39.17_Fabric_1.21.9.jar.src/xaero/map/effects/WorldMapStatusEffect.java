/*    */ package xaero.map.effects;
/*    */ 
/*    */ import net.minecraft.class_1291;
/*    */ import net.minecraft.class_2960;
/*    */ import net.minecraft.class_4081;
/*    */ 
/*    */ public class WorldMapStatusEffect
/*    */   extends class_1291 {
/*    */   private class_2960 id;
/*    */   
/*    */   protected WorldMapStatusEffect(class_4081 type, int color, String idPrefix) {
/* 12 */     super(type, color);
/* 13 */     String suffix = (type == class_4081.field_18272) ? "_harmful" : ((type == class_4081.field_18271) ? "_beneficial" : "");
/* 14 */     setRegistryName(class_2960.method_60655("xaeroworldmap", idPrefix + idPrefix));
/*    */   }
/*    */   
/*    */   protected void setRegistryName(class_2960 id) {
/* 18 */     this.id = id;
/*    */   }
/*    */   
/*    */   public class_2960 getRegistryName() {
/* 22 */     return this.id;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\effects\WorldMapStatusEffect.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */