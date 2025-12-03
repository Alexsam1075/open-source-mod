/*    */ package xaero.map.misc;
/*    */ 
/*    */ import java.lang.reflect.Field;
/*    */ import java.lang.reflect.Method;
/*    */ import net.fabricmc.loader.api.FabricLoader;
/*    */ import net.fabricmc.loader.api.MappingResolver;
/*    */ 
/*    */ public class ObfuscatedReflectionFabric
/*    */   implements IObfuscatedReflection
/*    */ {
/*    */   private static String fixFabricFieldMapping(Class<?> clazz, String name, String descriptor) {
/* 12 */     MappingResolver mappingResolver = FabricLoader.getInstance().getMappingResolver();
/* 13 */     String owner = mappingResolver.unmapClassName("intermediary", clazz.getName());
/* 14 */     return mappingResolver.mapFieldName("intermediary", owner, name, descriptor);
/*    */   }
/*    */   
/*    */   private static String fixFabricMethodMapping(Class<?> clazz, String name, String descriptor) {
/* 18 */     MappingResolver mappingResolver = FabricLoader.getInstance().getMappingResolver();
/* 19 */     String owner = mappingResolver.unmapClassName("intermediary", clazz.getName());
/* 20 */     return mappingResolver.mapMethodName("intermediary", owner, name, descriptor);
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<?> getClassForName(String obfuscatedName, String deobfName) throws ClassNotFoundException {
/* 25 */     MappingResolver mappingResolver = FabricLoader.getInstance().getMappingResolver();
/* 26 */     String name = mappingResolver.mapClassName("intermediary", obfuscatedName);
/*    */     try {
/* 28 */       return Class.forName(name);
/* 29 */     } catch (ClassNotFoundException cnfe) {
/* 30 */       return Class.forName(deobfName);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Field getFieldReflection(Class<?> c, String deobfName, String obfuscatedNameFabric, String descriptor, String obfuscatedNameForge) {
/* 36 */     String name = fixFabricFieldMapping(c, obfuscatedNameFabric, descriptor);
/* 37 */     Field field = null;
/*    */     try {
/* 39 */       field = c.getDeclaredField(name);
/* 40 */     } catch (NoSuchFieldException e) {
/* 41 */       throw new RuntimeException(e);
/*    */     } 
/* 43 */     return field;
/*    */   }
/*    */ 
/*    */   
/*    */   public Method getMethodReflection(Class<?> c, String deobfName, String obfuscatedNameFabric, String descriptor, String obfuscatedNameForge, Class<?>... parameters) {
/* 48 */     String name = fixFabricMethodMapping(c, obfuscatedNameFabric, descriptor);
/* 49 */     Method method = null;
/*    */     try {
/* 51 */       method = c.getDeclaredMethod(name, parameters);
/* 52 */     } catch (NoSuchMethodException e) {
/* 53 */       throw new RuntimeException(e);
/*    */     } 
/* 55 */     return method;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\misc\ObfuscatedReflectionFabric.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */