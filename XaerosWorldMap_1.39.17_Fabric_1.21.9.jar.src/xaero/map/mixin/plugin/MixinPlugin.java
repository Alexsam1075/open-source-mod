/*    */ package xaero.map.mixin.plugin;
/*    */ 
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import org.objectweb.asm.tree.ClassNode;
/*    */ import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
/*    */ import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
/*    */ import xaero.map.platform.Services;
/*    */ 
/*    */ public class MixinPlugin
/*    */   implements IMixinConfigPlugin
/*    */ {
/* 15 */   private static final Map<String, String> MIXIN_MOD_ID_MAP = (Map<String, String>)ImmutableMap.of();
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
/* 20 */     String modId = MIXIN_MOD_ID_MAP.get(mixinClassName);
/* 21 */     if (modId == null)
/* 22 */       return true; 
/* 23 */     return Services.PLATFORM.checkModForMixin(modId);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onLoad(String mixinPackage) {}
/*    */ 
/*    */   
/*    */   public String getRefMapperConfig() {
/* 32 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public List<String> getMixins() {
/* 42 */     return null;
/*    */   }
/*    */   
/*    */   public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}
/*    */   
/*    */   public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\mixin\plugin\MixinPlugin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */