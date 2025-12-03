/*    */ package xaero.map.events;
/*    */ 
/*    */ import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
/*    */ import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
/*    */ 
/*    */ public class CommonEventsFabric
/*    */   extends CommonEvents {
/*    */   public void register() {
/*  9 */     ServerPlayerEvents.COPY_FROM.register(this::onPlayerClone);
/* 10 */     ServerLifecycleEvents.SERVER_STARTED.register(this::onServerStarting);
/* 11 */     ServerLifecycleEvents.SERVER_STOPPED.register(this::onServerStopped);
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\events\CommonEventsFabric.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */