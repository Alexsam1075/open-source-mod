package xaero.map.file;

import java.io.File;

public interface MapRegionInfo {
  boolean shouldCache();
  
  File getRegionFile();
  
  File getCacheFile();
  
  String getWorldId();
  
  String getDimId();
  
  String getMwId();
  
  int getRegionX();
  
  int getRegionZ();
  
  void setShouldCache(boolean paramBoolean, String paramString);
  
  void setCacheFile(File paramFile);
  
  boolean hasLookedForCache();
}


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\file\MapRegionInfo.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */