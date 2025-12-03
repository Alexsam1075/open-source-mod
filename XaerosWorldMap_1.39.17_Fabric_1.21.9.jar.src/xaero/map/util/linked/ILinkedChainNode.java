package xaero.map.util.linked;

public interface ILinkedChainNode<V extends ILinkedChainNode<V>> {
  void setNext(V paramV);
  
  void setPrevious(V paramV);
  
  V getNext();
  
  V getPrevious();
  
  boolean isDestroyed();
  
  void onDestroyed();
}


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\ma\\util\linked\ILinkedChainNode.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */