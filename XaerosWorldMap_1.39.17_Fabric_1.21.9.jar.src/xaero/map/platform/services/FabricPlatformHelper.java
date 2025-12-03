/*    */ package xaero.map.platform.services;
/*    */ 
/*    */ import java.nio.file.Path;
/*    */ import net.fabricmc.api.EnvType;
/*    */ import net.fabricmc.loader.api.FabricLoader;
/*    */ import xaero.map.controls.IKeyBindingHelper;
/*    */ import xaero.map.controls.KeyBindingHelperFabric;
/*    */ import xaero.map.misc.IObfuscatedReflection;
/*    */ import xaero.map.misc.ObfuscatedReflectionFabric;
/*    */ import xaero.map.render.util.FabricRenderDeviceUtil;
/*    */ import xaero.map.render.util.FabricRenderUtil;
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
/*    */ public class FabricPlatformHelper
/*    */   implements IPlatformHelper
/*    */ {
/* 25 */   private final IObfuscatedReflection obfuscatedFieldReflection = (IObfuscatedReflection)new ObfuscatedReflectionFabric();
/* 26 */   private final KeyBindingHelperFabric keyBindingHelperFabric = new KeyBindingHelperFabric();
/* 27 */   private final FabricRenderUtil fabricRenderUtil = new FabricRenderUtil();
/* 28 */   private final FabricRenderDeviceUtil fabricRenderDeviceUtil = new FabricRenderDeviceUtil();
/*    */ 
/*    */ 
/*    */   
/*    */   public String getPlatformName() {
/* 33 */     return "Fabric";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isModLoaded(String modId) {
/* 38 */     return FabricLoader.getInstance().isModLoaded(modId);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isDevelopmentEnvironment() {
/* 43 */     return FabricLoader.getInstance().isDevelopmentEnvironment();
/*    */   }
/*    */ 
/*    */   
/*    */   public IObfuscatedReflection getObfuscatedFieldReflection() {
/* 48 */     return this.obfuscatedFieldReflection;
/*    */   }
/*    */ 
/*    */   
/*    */   public IKeyBindingHelper getKeyBindingHelper() {
/* 53 */     return (IKeyBindingHelper)this.keyBindingHelperFabric;
/*    */   }
/*    */ 
/*    */   
/*    */   public IPlatformRenderUtil getPlatformRenderUtil() {
/* 58 */     return (IPlatformRenderUtil)this.fabricRenderUtil;
/*    */   }
/*    */ 
/*    */   
/*    */   public IPlatformRenderDeviceUtil getPlatformRenderDeviceUtil() {
/* 63 */     return (IPlatformRenderDeviceUtil)this.fabricRenderDeviceUtil;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isDedicatedServer() {
/* 68 */     return (FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER);
/*    */   }
/*    */ 
/*    */   
/*    */   public Path getGameDir() {
/* 73 */     return FabricLoader.getInstance().getGameDir().normalize();
/*    */   }
/*    */ 
/*    */   
/*    */   public Path getConfigDir() {
/* 78 */     return FabricLoader.getInstance().getConfigDir();
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\platform\services\FabricPlatformHelper.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */