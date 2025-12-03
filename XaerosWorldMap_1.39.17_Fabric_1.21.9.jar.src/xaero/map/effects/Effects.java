/*    */ package xaero.map.effects;
/*    */ 
/*    */ import net.minecraft.class_1291;
/*    */ import net.minecraft.class_4081;
/*    */ import net.minecraft.class_6880;
/*    */ 
/*    */ public class Effects
/*    */ {
/*  9 */   public static WorldMapStatusEffect NO_WORLD_MAP_UNHELD = null;
/* 10 */   public static WorldMapStatusEffect NO_WORLD_MAP_HARMFUL_UNHELD = null;
/* 11 */   public static WorldMapStatusEffect NO_CAVE_MAPS_UNHELD = null;
/* 12 */   public static WorldMapStatusEffect NO_CAVE_MAPS_HARMFUL_UNHELD = null;
/*    */   
/* 14 */   public static class_6880<class_1291> NO_WORLD_MAP = null;
/* 15 */   public static class_6880<class_1291> NO_WORLD_MAP_HARMFUL = null;
/* 16 */   public static class_6880<class_1291> NO_CAVE_MAPS = null;
/* 17 */   public static class_6880<class_1291> NO_CAVE_MAPS_HARMFUL = null;
/*    */   
/*    */   public static void init() {
/* 20 */     if (NO_WORLD_MAP != null) {
/*    */       return;
/*    */     }
/*    */ 
/*    */     
/* 25 */     NO_WORLD_MAP_UNHELD = new NoWorldMapEffect(class_4081.field_18273);
/* 26 */     NO_WORLD_MAP_HARMFUL_UNHELD = new NoWorldMapEffect(class_4081.field_18272);
/* 27 */     NO_CAVE_MAPS_UNHELD = new NoCaveMapsEffect(class_4081.field_18273);
/* 28 */     NO_CAVE_MAPS_HARMFUL_UNHELD = new NoCaveMapsEffect(class_4081.field_18272);
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\effects\Effects.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */