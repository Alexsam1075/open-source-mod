/*    */ package xaero.map.platform.services;
/*    */ 
/*    */ import java.nio.file.Path;
/*    */ import xaero.map.controls.IKeyBindingHelper;
/*    */ import xaero.map.misc.IObfuscatedReflection;
/*    */ import xaero.map.render.util.IPlatformRenderDeviceUtil;
/*    */ import xaero.map.render.util.IPlatformRenderUtil;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface IPlatformHelper
/*    */ {
/*    */   String getPlatformName();
/*    */   
/*    */   boolean isModLoaded(String paramString);
/*    */   
/*    */   default boolean checkModForMixin(String modId) {
/* 28 */     return isModLoaded(modId);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   boolean isDevelopmentEnvironment();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default String getEnvironmentName() {
/* 45 */     return isDevelopmentEnvironment() ? "development" : "production";
/*    */   }
/*    */   
/*    */   IObfuscatedReflection getObfuscatedFieldReflection();
/*    */   
/*    */   IKeyBindingHelper getKeyBindingHelper();
/*    */   
/*    */   IPlatformRenderUtil getPlatformRenderUtil();
/*    */   
/*    */   IPlatformRenderDeviceUtil getPlatformRenderDeviceUtil();
/*    */   
/*    */   boolean isDedicatedServer();
/*    */   
/*    */   Path getGameDir();
/*    */   
/*    */   Path getConfigDir();
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\platform\services\IPlatformHelper.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */