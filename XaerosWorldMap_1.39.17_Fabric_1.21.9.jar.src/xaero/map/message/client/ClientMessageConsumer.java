package xaero.map.message.client;

public interface ClientMessageConsumer<T extends xaero.map.message.WorldMapMessage<T>> {
  void handle(T paramT);
}


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\message\client\ClientMessageConsumer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */