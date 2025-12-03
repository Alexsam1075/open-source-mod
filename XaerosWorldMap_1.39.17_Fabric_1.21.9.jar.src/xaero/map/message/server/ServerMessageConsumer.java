package xaero.map.message.server;

import net.minecraft.class_3222;
import net.minecraft.server.MinecraftServer;

public interface ServerMessageConsumer<T extends xaero.map.message.WorldMapMessage<T>> {
  void handle(MinecraftServer paramMinecraftServer, class_3222 paramclass_3222, T paramT);
}


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\message\server\ServerMessageConsumer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */