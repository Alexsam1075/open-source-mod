package xaero.map.message;

import java.util.function.BiConsumer;
import java.util.function.Function;
import net.minecraft.class_2540;
import net.minecraft.class_3222;
import xaero.map.message.client.ClientMessageConsumer;
import xaero.map.message.server.ServerMessageConsumer;

public abstract class WorldMapMessageHandler {
  public static final int NETWORK_COMPATIBILITY = 3;
  
  public abstract <T extends WorldMapMessage<T>> void register(int paramInt, Class<T> paramClass, ServerMessageConsumer<T> paramServerMessageConsumer, ClientMessageConsumer<T> paramClientMessageConsumer, Function<class_2540, T> paramFunction, BiConsumer<T, class_2540> paramBiConsumer);
  
  public abstract <T extends WorldMapMessage<T>> void sendToPlayer(class_3222 paramclass_3222, T paramT);
  
  public abstract <T extends WorldMapMessage<T>> void sendToServer(T paramT);
}


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\message\WorldMapMessageHandler.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */