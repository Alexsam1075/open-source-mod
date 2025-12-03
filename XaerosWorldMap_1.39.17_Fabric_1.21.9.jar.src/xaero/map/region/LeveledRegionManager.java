/*    */ package xaero.map.region;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LeveledRegionManager
/*    */ {
/*    */   public static final int MAX_LEVEL = 3;
/* 11 */   private HashMap<Integer, HashMap<Integer, LeveledRegion<?>>> regionTextureMap = new HashMap<>();
/*    */   
/*    */   public void putLeaf(int X, int Z, MapRegion leaf) {
/*    */     HashMap<Integer, LeveledRegion<?>> column;
/*    */     LeveledRegion<?> rootBranch;
/* 16 */     int maxLevelX = X >> 3;
/* 17 */     int maxLevelZ = Z >> 3;
/*    */     
/* 19 */     synchronized (this.regionTextureMap) {
/* 20 */       column = this.regionTextureMap.get(Integer.valueOf(maxLevelX));
/* 21 */       if (column == null) {
/* 22 */         column = new HashMap<>();
/* 23 */         this.regionTextureMap.put(Integer.valueOf(maxLevelX), column);
/*    */       } 
/*    */     } 
/*    */     
/* 27 */     synchronized (column) {
/* 28 */       rootBranch = column.get(Integer.valueOf(maxLevelZ));
/* 29 */       if (rootBranch == null) {
/* 30 */         rootBranch = new BranchLeveledRegion(leaf.getWorldId(), leaf.getDimId(), leaf.getMwId(), leaf.getDim(), 3, maxLevelX, maxLevelZ, leaf.caveLayer, null);
/* 31 */         column.put(Integer.valueOf(maxLevelZ), rootBranch);
/*    */         
/* 33 */         leaf.getDim().getLayeredMapRegions().addListRegion(rootBranch);
/*    */       } 
/*    */     } 
/* 36 */     if (!(rootBranch instanceof MapRegion))
/* 37 */       rootBranch.putLeaf(X, Z, leaf); 
/*    */   }
/*    */   
/*    */   public MapRegion getLeaf(int X, int Z) {
/* 41 */     return (MapRegion)get(X, Z, 0);
/*    */   } public LeveledRegion<?> get(int leveledX, int leveledZ, int level) {
/*    */     HashMap<Integer, LeveledRegion<?>> column;
/*    */     LeveledRegion<?> rootBranch;
/* 45 */     if (level > 3)
/* 46 */       throw new RuntimeException(new IllegalArgumentException()); 
/* 47 */     int maxLevelX = leveledX >> 3 - level;
/* 48 */     int maxLevelZ = leveledZ >> 3 - level;
/*    */     
/* 50 */     synchronized (this.regionTextureMap) {
/* 51 */       column = this.regionTextureMap.get(Integer.valueOf(maxLevelX));
/*    */     } 
/* 53 */     if (column == null) {
/* 54 */       return null;
/*    */     }
/* 56 */     synchronized (column) {
/* 57 */       rootBranch = column.get(Integer.valueOf(maxLevelZ));
/*    */     } 
/* 59 */     if (rootBranch == null)
/* 60 */       return null; 
/* 61 */     if (level == 3)
/* 62 */       return rootBranch; 
/* 63 */     return rootBranch.get(leveledX, leveledZ, level);
/*    */   } public boolean remove(int leveledX, int leveledZ, int level) {
/*    */     HashMap<Integer, LeveledRegion<?>> column;
/*    */     LeveledRegion<?> rootBranch;
/* 67 */     if (level > 3)
/* 68 */       throw new RuntimeException(new IllegalArgumentException()); 
/* 69 */     int maxLevelX = leveledX >> 3 - level;
/* 70 */     int maxLevelZ = leveledZ >> 3 - level;
/*    */     
/* 72 */     synchronized (this.regionTextureMap) {
/* 73 */       column = this.regionTextureMap.get(Integer.valueOf(maxLevelX));
/*    */     } 
/* 75 */     if (column == null) {
/* 76 */       return false;
/*    */     }
/* 78 */     synchronized (column) {
/* 79 */       rootBranch = column.get(Integer.valueOf(maxLevelZ));
/*    */     } 
/* 81 */     if (rootBranch == null)
/* 82 */       return false; 
/* 83 */     if (!(rootBranch instanceof MapRegion)) {
/* 84 */       return rootBranch.remove(leveledX, leveledZ, level);
/*    */     }
/* 86 */     synchronized (column) {
/* 87 */       column.remove(Integer.valueOf(maxLevelZ));
/*    */     } 
/* 89 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\region\LeveledRegionManager.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */