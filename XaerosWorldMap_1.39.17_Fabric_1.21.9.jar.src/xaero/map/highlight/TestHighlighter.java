/*    */ package xaero.map.highlight;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.class_1937;
/*    */ import net.minecraft.class_2561;
/*    */ import net.minecraft.class_5321;
/*    */ 
/*    */ public class TestHighlighter
/*    */   extends ChunkHighlighter
/*    */ {
/*    */   public TestHighlighter() {
/* 12 */     super(true);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean regionHasHighlights(class_5321<class_1937> dimension, int regionX, int regionZ) {
/* 17 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   protected int[] getColors(class_5321<class_1937> dimension, int chunkX, int chunkZ) {
/* 22 */     if (!chunkIsHighlit(dimension, chunkX, chunkZ))
/* 23 */       return null; 
/* 24 */     int centerColor = 1442796919;
/* 25 */     int sideColor = 1442797004;
/* 26 */     this.resultStore[0] = centerColor;
/* 27 */     this.resultStore[1] = ((chunkZ & 0x3) == 0) ? sideColor : centerColor;
/* 28 */     this.resultStore[2] = ((chunkX & 0x3) == 3) ? sideColor : centerColor;
/* 29 */     this.resultStore[3] = ((chunkZ & 0x3) == 3) ? sideColor : centerColor;
/* 30 */     this.resultStore[4] = ((chunkX & 0x3) == 0) ? sideColor : centerColor;
/* 31 */     return this.resultStore;
/*    */   }
/*    */ 
/*    */   
/*    */   public int calculateRegionHash(class_5321<class_1937> dimension, int regionX, int regionZ) {
/* 36 */     return 51;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean chunkIsHighlit(class_5321<class_1937> dimension, int chunkX, int chunkZ) {
/* 41 */     return ((chunkX >> 2 & 0x1) == (chunkZ >> 2 & 0x1));
/*    */   }
/*    */ 
/*    */   
/*    */   public class_2561 getChunkHighlightSubtleTooltip(class_5321<class_1937> dimension, int chunkX, int chunkZ) {
/* 46 */     return (class_2561)class_2561.method_43470("subtle!");
/*    */   }
/*    */ 
/*    */   
/*    */   public class_2561 getChunkHighlightBluntTooltip(class_5321<class_1937> dimension, int chunkX, int chunkZ) {
/* 51 */     return (class_2561)class_2561.method_43470("blunt!");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void addMinimapBlockHighlightTooltips(List<class_2561> list, class_5321<class_1937> dimension, int blockX, int blockZ, int width) {
/* 57 */     list.add(class_2561.method_43470("minimap tooltip!"));
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\highlight\TestHighlighter.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */