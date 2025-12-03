package xaero.map.radar.tracker.system;

import java.util.Iterator;

public interface IPlayerTrackerSystem<P> {
  ITrackedPlayerReader<P> getReader();
  
  Iterator<P> getTrackedPlayerIterator();
}


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\radar\tracker\system\IPlayerTrackerSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */