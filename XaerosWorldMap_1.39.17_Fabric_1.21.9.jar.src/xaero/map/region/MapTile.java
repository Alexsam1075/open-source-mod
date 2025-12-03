/*    */ package xaero.map.region;
/*    */ 
/*    */ import xaero.map.pool.PoolUnit;
/*    */ 
/*    */ public class MapTile implements PoolUnit {
/*    */   public static final int CURRENT_WORLD_INTERPRETATION_VERSION = 1;
/*    */   private boolean loaded;
/*    */   private byte signed_worldInterpretationVersion;
/*    */   private int chunkX;
/*    */   private int chunkZ;
/*    */   private MapBlock[][] blocks;
/*    */   private boolean writtenOnce;
/*    */   private int writtenCaveStart;
/*    */   private byte writtenCaveDepth;
/*    */   
/*    */   public MapTile(Object... args) {
/* 17 */     this.blocks = new MapBlock[16][16];
/* 18 */     create(args);
/*    */   }
/*    */ 
/*    */   
/*    */   public void create(Object... args) {
/* 23 */     this.chunkX = ((Integer)args[1]).intValue();
/* 24 */     this.chunkZ = ((Integer)args[2]).intValue();
/* 25 */     this.loaded = false;
/* 26 */     this.signed_worldInterpretationVersion = 0;
/* 27 */     this.writtenOnce = false;
/* 28 */     this.writtenCaveStart = Integer.MAX_VALUE;
/* 29 */     this.writtenCaveDepth = 0;
/*    */   }
/*    */   
/*    */   public boolean isLoaded() {
/* 33 */     return this.loaded;
/*    */   }
/*    */   
/*    */   public void setLoaded(boolean loaded) {
/* 37 */     this.loaded = loaded;
/*    */   }
/*    */   
/*    */   public MapBlock getBlock(int x, int z) {
/* 41 */     return this.blocks[x][z];
/*    */   }
/*    */   
/*    */   public MapBlock[] getBlockColumn(int x) {
/* 45 */     return this.blocks[x];
/*    */   }
/*    */   
/*    */   public void setBlock(int x, int z, MapBlock block) {
/* 49 */     this.blocks[x][z] = block;
/*    */   }
/*    */   
/*    */   public int getChunkX() {
/* 53 */     return this.chunkX;
/*    */   }
/*    */   
/*    */   public int getChunkZ() {
/* 57 */     return this.chunkZ;
/*    */   }
/*    */   
/*    */   public boolean wasWrittenOnce() {
/* 61 */     return this.writtenOnce;
/*    */   }
/*    */   
/*    */   public void setWrittenOnce(boolean writtenOnce) {
/* 65 */     this.writtenOnce = writtenOnce;
/*    */   }
/*    */   
/*    */   public int getWorldInterpretationVersion() {
/* 69 */     return this.signed_worldInterpretationVersion & 0xFF;
/*    */   }
/*    */   
/*    */   public void setWorldInterpretationVersion(int version) {
/* 73 */     this.signed_worldInterpretationVersion = (byte)version;
/*    */   }
/*    */   
/*    */   public int getWrittenCaveStart() {
/* 77 */     return this.writtenCaveStart;
/*    */   }
/*    */   
/*    */   public void setWrittenCave(int writtenCaveStart, int writtenCaveDepth) {
/* 81 */     this.writtenCaveStart = writtenCaveStart;
/* 82 */     this.writtenCaveDepth = (byte)writtenCaveDepth;
/*    */   }
/*    */   
/*    */   public int getWrittenCaveDepth() {
/* 86 */     return this.writtenCaveDepth & 0xFF;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\region\MapTile.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */