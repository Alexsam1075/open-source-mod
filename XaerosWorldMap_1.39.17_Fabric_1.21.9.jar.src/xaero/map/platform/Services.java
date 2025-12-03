/*    */ package xaero.map.platform;
/*    */ 
/*    */ import java.util.ServiceLoader;
/*    */ import xaero.map.WorldMap;
/*    */ import xaero.map.platform.services.IPlatformHelper;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Services
/*    */ {
/* 16 */   public static final IPlatformHelper PLATFORM = load(IPlatformHelper.class);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static <T> T load(Class<T> clazz) {
/* 26 */     T loadedService = (T)ServiceLoader.<T>load(clazz).findFirst().orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
/* 27 */     WorldMap.LOGGER.debug("Loaded {} for service {}", loadedService, clazz);
/* 28 */     return loadedService;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\platform\Services.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */