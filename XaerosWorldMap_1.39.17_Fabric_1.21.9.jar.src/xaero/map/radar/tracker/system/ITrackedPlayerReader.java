package xaero.map.radar.tracker.system;

import java.util.UUID;
import net.minecraft.class_1937;
import net.minecraft.class_5321;

public interface ITrackedPlayerReader<P> {
  UUID getId(P paramP);
  
  double getX(P paramP);
  
  double getY(P paramP);
  
  double getZ(P paramP);
  
  class_5321<class_1937> getDimension(P paramP);
}


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\radar\tracker\system\ITrackedPlayerReader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */