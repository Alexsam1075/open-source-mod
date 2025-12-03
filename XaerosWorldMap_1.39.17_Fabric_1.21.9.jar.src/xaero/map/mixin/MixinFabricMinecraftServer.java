/*    */ package xaero.map.mixin;
/*    */ 
/*    */ import java.util.function.BooleanSupplier;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ import xaero.map.WorldMap;
/*    */ import xaero.map.WorldMapFabric;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({MinecraftServer.class})
/*    */ public class MixinFabricMinecraftServer
/*    */ {
/*    */   @Inject(at = {@At("HEAD")}, method = {"tickServer"})
/*    */   public void onTick(BooleanSupplier shouldKeepTicking, CallbackInfo info) {
/* 20 */     if (!(this instanceof net.minecraft.class_3176))
/*    */       return; 
/* 22 */     if (WorldMap.INSTANCE != null)
/* 23 */       ((WorldMapFabric)WorldMap.INSTANCE).tryLoadLaterServer(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\mixin\MixinFabricMinecraftServer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */