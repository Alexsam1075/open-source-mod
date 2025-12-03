/*    */ package xaero.map.highlight;
/*    */ 
/*    */ import net.minecraft.class_1937;
/*    */ import net.minecraft.class_2561;
/*    */ import net.minecraft.class_5321;
/*    */ 
/*    */ public abstract class ChunkHighlighter
/*    */   extends AbstractHighlighter {
/*    */   protected ChunkHighlighter(boolean coveringOutsideDiscovered) {
/* 10 */     super(coveringOutsideDiscovered);
/*    */   }
/*    */ 
/*    */   
/*    */   protected abstract int[] getColors(class_5321<class_1937> paramclass_5321, int paramInt1, int paramInt2);
/*    */   
/*    */   public int[] getChunkHighlitColor(class_5321<class_1937> dimension, int chunkX, int chunkZ) {
/* 17 */     int[] colors = getColors(dimension, chunkX, chunkZ);
/* 18 */     if (colors == null)
/* 19 */       return null; 
/* 20 */     int centerColor = colors[0];
/* 21 */     int topColor = colors[1];
/* 22 */     int rightColor = colors[2];
/* 23 */     int bottomColor = colors[3];
/* 24 */     int leftColor = colors[4];
/* 25 */     int topLeftColor = getSideBlend(topColor, leftColor, centerColor);
/* 26 */     int topRightColor = getSideBlend(topColor, rightColor, centerColor);
/* 27 */     int bottomRightColor = getSideBlend(bottomColor, rightColor, centerColor);
/* 28 */     int bottomLeftColor = getSideBlend(bottomColor, leftColor, centerColor);
/* 29 */     setResult(0, 0, topLeftColor);
/* 30 */     setResult(15, 0, topRightColor);
/* 31 */     setResult(15, 15, bottomRightColor);
/* 32 */     setResult(0, 15, bottomLeftColor);
/* 33 */     for (int i = 1; i < 15; i++) {
/* 34 */       setResult(i, 0, topColor);
/* 35 */       setResult(15, i, rightColor);
/* 36 */       setResult(i, 15, bottomColor);
/* 37 */       setResult(0, i, leftColor);
/* 38 */       for (int j = 1; j < 15; j++)
/* 39 */         setResult(i, j, centerColor); 
/*    */     } 
/* 41 */     return this.resultStore;
/*    */   }
/*    */   
/*    */   private int getSideBlend(int color1, int color2, int centerColor) {
/* 45 */     return getBlend((color1 == centerColor) ? color2 : color1, (color2 == centerColor) ? color1 : color2);
/*    */   }
/*    */ 
/*    */   
/*    */   public class_2561 getBlockHighlightBluntTooltip(class_5321<class_1937> dimension, int blockX, int blockZ) {
/* 50 */     if (!chunkIsHighlit(dimension, blockX >> 4, blockZ >> 4))
/* 51 */       return null; 
/* 52 */     return getChunkHighlightBluntTooltip(dimension, blockX >> 4, blockZ >> 4);
/*    */   }
/*    */ 
/*    */   
/*    */   public class_2561 getBlockHighlightSubtleTooltip(class_5321<class_1937> dimension, int blockX, int blockZ) {
/* 57 */     if (!chunkIsHighlit(dimension, blockX >> 4, blockZ >> 4))
/* 58 */       return null; 
/* 59 */     return getChunkHighlightSubtleTooltip(dimension, blockX >> 4, blockZ >> 4);
/*    */   }
/*    */   
/*    */   public abstract class_2561 getChunkHighlightSubtleTooltip(class_5321<class_1937> paramclass_5321, int paramInt1, int paramInt2);
/*    */   
/*    */   public abstract class_2561 getChunkHighlightBluntTooltip(class_5321<class_1937> paramclass_5321, int paramInt1, int paramInt2);
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\highlight\ChunkHighlighter.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */