/*    */ package xaero.map.mixin;
/*    */ 
/*    */ import net.minecraft.class_2626;
/*    */ import net.minecraft.class_2637;
/*    */ import net.minecraft.class_2666;
/*    */ import net.minecraft.class_2672;
/*    */ import net.minecraft.class_2676;
/*    */ import net.minecraft.class_2759;
/*    */ import net.minecraft.class_634;
/*    */ import net.minecraft.class_6603;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ import xaero.map.WorldMapSession;
/*    */ import xaero.map.core.IWorldMapClientPlayNetHandler;
/*    */ import xaero.map.core.XaeroWorldMapCore;
/*    */ 
/*    */ @Mixin({class_634.class})
/*    */ public class MixinClientPlayNetworkHandler
/*    */   implements IWorldMapClientPlayNetHandler
/*    */ {
/*    */   WorldMapSession xaero_worldmapSession;
/*    */   
/*    */   @Inject(at = {@At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/network/protocol/PacketUtils;ensureRunningOnSameThread(Lnet/minecraft/network/protocol/Packet;Lnet/minecraft/network/PacketListener;Lnet/minecraft/network/PacketProcessor;)V")}, method = {"handleChunkBlocksUpdate"})
/*    */   public void onOnChunkDeltaUpdate(class_2637 packet, CallbackInfo info) {
/* 27 */     XaeroWorldMapCore.onMultiBlockChange(packet);
/*    */   }
/*    */   
/*    */   @Inject(at = {@At("HEAD")}, method = {"updateLevelChunk"})
/*    */   public void onOnChunkData(int x, int z, class_6603 packet, CallbackInfo info) {
/* 32 */     XaeroWorldMapCore.onChunkData(x, z, packet);
/*    */   }
/*    */   
/*    */   @Inject(at = {@At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/network/protocol/PacketUtils;ensureRunningOnSameThread(Lnet/minecraft/network/protocol/Packet;Lnet/minecraft/network/PacketListener;Lnet/minecraft/network/PacketProcessor;)V")}, method = {"handleLevelChunkWithLight"})
/*    */   public void onHandleLevelChunkWithLight(class_2672 packet, CallbackInfo info) {
/* 37 */     XaeroWorldMapCore.onHandleLevelChunkWithLight(packet);
/*    */   }
/*    */   
/*    */   @Inject(at = {@At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/network/protocol/PacketUtils;ensureRunningOnSameThread(Lnet/minecraft/network/protocol/Packet;Lnet/minecraft/network/PacketListener;Lnet/minecraft/network/PacketProcessor;)V")}, method = {"handleLightUpdatePacket"})
/*    */   public void onHandleLightUpdatePacket(class_2676 packet, CallbackInfo info) {
/* 42 */     XaeroWorldMapCore.onHandleLightUpdatePacket(packet);
/*    */   }
/*    */   
/*    */   @Inject(at = {@At("HEAD")}, method = {"queueLightRemoval"})
/*    */   public void onQueueLightRemoval(class_2666 packet, CallbackInfo info) {
/* 47 */     XaeroWorldMapCore.onQueueLightRemoval(packet);
/*    */   }
/*    */   
/*    */   @Inject(at = {@At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/network/protocol/PacketUtils;ensureRunningOnSameThread(Lnet/minecraft/network/protocol/Packet;Lnet/minecraft/network/PacketListener;Lnet/minecraft/network/PacketProcessor;)V")}, method = {"handleBlockUpdate"})
/*    */   public void onOnBlockUpdate(class_2626 packet, CallbackInfo info) {
/* 52 */     XaeroWorldMapCore.onBlockChange(packet);
/*    */   }
/*    */   
/*    */   @Inject(at = {@At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/network/protocol/PacketUtils;ensureRunningOnSameThread(Lnet/minecraft/network/protocol/Packet;Lnet/minecraft/network/PacketListener;Lnet/minecraft/network/PacketProcessor;)V")}, method = {"handleSetSpawn"})
/*    */   public void onOnPlayerSpawnPosition(class_2759 packet, CallbackInfo info) {
/* 57 */     XaeroWorldMapCore.handlePlayerSetSpawnPacket(packet);
/*    */   }
/*    */ 
/*    */   
/*    */   public WorldMapSession getXaero_worldmapSession() {
/* 62 */     return this.xaero_worldmapSession;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setXaero_worldmapSession(WorldMapSession session) {
/* 67 */     this.xaero_worldmapSession = session;
/*    */   }
/*    */   
/*    */   @Inject(at = {@At("HEAD")}, method = {"close"})
/*    */   public void onCleanup(CallbackInfo info) {
/* 72 */     XaeroWorldMapCore.onPlayNetHandlerCleanup((class_634)this);
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\mixin\MixinClientPlayNetworkHandler.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */