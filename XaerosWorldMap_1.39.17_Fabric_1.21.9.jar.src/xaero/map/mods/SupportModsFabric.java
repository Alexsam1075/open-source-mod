/*    */ package xaero.map.mods;
/*    */ 
/*    */ import xaero.map.WorldMap;
/*    */ 
/*    */ public class SupportModsFabric
/*    */   extends SupportMods {
/*  7 */   public static SupportAmecs amecs = null;
/*    */ 
/*    */   
/*    */   public void load() {
/* 11 */     super.load();
/*    */     
/*    */     try {
/* 14 */       Class<?> mmClassTest = Class.forName("de.siphalor.amecs.api.KeyModifiers");
/* 15 */       amecs = new SupportAmecs(WorldMap.LOGGER);
/* 16 */     } catch (ClassNotFoundException classNotFoundException) {}
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean amecs() {
/* 21 */     return (amecs != null);
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\mods\SupportModsFabric.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */