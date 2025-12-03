/*    */ package xaero.map.mods;
/*    */ 
/*    */ import java.lang.reflect.InvocationTargetException;
/*    */ import java.lang.reflect.Method;
/*    */ import xaero.map.WorldMap;
/*    */ import xaero.map.mods.pac.SupportOpenPartiesAndClaims;
/*    */ 
/*    */ 
/*    */ public class SupportMods
/*    */ {
/* 11 */   public static SupportXaeroMinimap xaeroMinimap = null;
/* 12 */   public static SupportOpenPartiesAndClaims xaeroPac = null;
/*    */   public static boolean optifine;
/*    */   public static boolean vivecraft;
/*    */   public static boolean iris;
/*    */   public static SupportIris supportIris;
/* 17 */   public static SupportFramedBlocks supportFramedBlocks = null;
/*    */   
/*    */   public static boolean minimap() {
/* 20 */     return (xaeroMinimap != null && xaeroMinimap.modMain != null);
/*    */   }
/*    */   
/*    */   public static boolean framedBlocks() {
/* 24 */     return (supportFramedBlocks != null);
/*    */   }
/*    */   
/*    */   public static boolean pac() {
/* 28 */     return (xaeroPac != null);
/*    */   }
/*    */ 
/*    */   
/*    */   public void load() {
/*    */     try {
/* 34 */       Class<?> mmClassTest = Class.forName("xaero.common.IXaeroMinimap");
/* 35 */       xaeroMinimap = new SupportXaeroMinimap();
/* 36 */       xaeroMinimap.register();
/* 37 */     } catch (ClassNotFoundException classNotFoundException) {}
/*    */ 
/*    */     
/*    */     try {
/* 41 */       Class<?> pacClassTest = Class.forName("xaero.pac.OpenPartiesAndClaims");
/* 42 */       xaeroPac = new SupportOpenPartiesAndClaims();
/* 43 */       xaeroPac.register();
/* 44 */       WorldMap.LOGGER.info("Xaero's WorldMap Mod: Open Parties And Claims found!");
/* 45 */     } catch (ClassNotFoundException classNotFoundException) {}
/*    */ 
/*    */     
/*    */     try {
/* 49 */       Class<?> optifineClassTest = Class.forName("optifine.Patcher");
/* 50 */       optifine = true;
/* 51 */       WorldMap.LOGGER.info("Optifine!");
/* 52 */     } catch (ClassNotFoundException e) {
/* 53 */       optifine = false;
/* 54 */       WorldMap.LOGGER.info("No Optifine!");
/*    */     } 
/*    */     
/*    */     try {
/* 58 */       Class<?> vivecraftClassTest = Class.forName("org.vivecraft.api.VRData");
/* 59 */       vivecraft = true;
/*    */       
/* 61 */       try { Class<?> vrStateClass = Class.forName("org.vivecraft.VRState");
/* 62 */         Method checkVRMethod = vrStateClass.getDeclaredMethod("checkVR", new Class[0]);
/* 63 */         vivecraft = ((Boolean)checkVRMethod.invoke(null, new Object[0])).booleanValue(); }
/* 64 */       catch (ClassNotFoundException classNotFoundException) {  }
/* 65 */       catch (NoSuchMethodException noSuchMethodException) {  }
/* 66 */       catch (IllegalAccessException illegalAccessException) {  }
/* 67 */       catch (IllegalArgumentException illegalArgumentException) {  }
/* 68 */       catch (InvocationTargetException invocationTargetException) {}
/*    */     }
/* 70 */     catch (ClassNotFoundException classNotFoundException) {}
/*    */     
/* 72 */     if (vivecraft) {
/* 73 */       WorldMap.LOGGER.info("Xaero's World Map: Vivecraft!");
/*    */     } else {
/* 75 */       WorldMap.LOGGER.info("Xaero's World Map: No Vivecraft!");
/*    */     } 
/*    */     try {
/* 78 */       Class<?> mmClassTest = Class.forName("xfacthd.framedblocks.FramedBlocks");
/* 79 */       supportFramedBlocks = new SupportFramedBlocks();
/* 80 */       WorldMap.LOGGER.info("Xaero's World Map: Framed Blocks found!");
/* 81 */     } catch (ClassNotFoundException classNotFoundException) {}
/*    */     
/*    */     try {
/* 84 */       Class.forName("net.irisshaders.iris.api.v0.IrisApi");
/* 85 */       supportIris = new SupportIris();
/* 86 */       iris = true;
/* 87 */       WorldMap.LOGGER.info("Xaero's World Map: Iris found!");
/* 88 */     } catch (Exception exception) {}
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\mods\SupportMods.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */