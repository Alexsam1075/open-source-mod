/*    */ package xaero.map.message;
/*    */ import xaero.map.message.basic.ClientboundRulesPacket;
/*    */ import xaero.map.message.basic.HandshakePacket;
/*    */ import xaero.map.message.client.ClientMessageConsumer;
/*    */ import xaero.map.message.server.ServerMessageConsumer;
/*    */ import xaero.map.message.tracker.ClientboundPlayerTrackerResetPacket;
/*    */ import xaero.map.message.tracker.ClientboundTrackedPlayerPacket;
/*    */ import xaero.map.server.level.LevelMapProperties;
/*    */ 
/*    */ public class WorldMapMessageRegister {
/*    */   public void register(WorldMapMessageHandler messageHandler) {
/* 12 */     messageHandler.register(0, LevelMapProperties.class, null, new LevelMapPropertiesConsumer(), LevelMapProperties::read, LevelMapProperties::write);
/* 13 */     messageHandler.register(1, HandshakePacket.class, (ServerMessageConsumer<HandshakePacket>)new HandshakePacket.ServerHandler(), (ClientMessageConsumer<HandshakePacket>)new HandshakePacket.ClientHandler(), HandshakePacket::read, HandshakePacket::write);
/* 14 */     messageHandler.register(2, ClientboundTrackedPlayerPacket.class, null, (ClientMessageConsumer<ClientboundTrackedPlayerPacket>)new ClientboundTrackedPlayerPacket.Handler(), ClientboundTrackedPlayerPacket::read, ClientboundTrackedPlayerPacket::write);
/* 15 */     messageHandler.register(3, ClientboundPlayerTrackerResetPacket.class, null, (ClientMessageConsumer<ClientboundPlayerTrackerResetPacket>)new ClientboundPlayerTrackerResetPacket.Handler(), ClientboundPlayerTrackerResetPacket::read, ClientboundPlayerTrackerResetPacket::write);
/* 16 */     messageHandler.register(4, ClientboundRulesPacket.class, null, (ClientMessageConsumer<ClientboundRulesPacket>)new ClientboundRulesPacket.ClientHandler(), ClientboundRulesPacket::read, ClientboundRulesPacket::write);
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\message\WorldMapMessageRegister.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */