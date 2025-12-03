/*    */ package xaero.map.highlight;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.class_1937;
/*    */ import net.minecraft.class_2561;
/*    */ import net.minecraft.class_5321;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AbstractHighlighter
/*    */ {
/*    */   protected final boolean coveringOutsideDiscovered;
/*    */   protected final int[] resultStore;
/*    */   
/*    */   protected AbstractHighlighter(boolean coveringOutsideDiscovered) {
/* 16 */     this.resultStore = new int[256];
/* 17 */     this.coveringOutsideDiscovered = coveringOutsideDiscovered;
/*    */   }
/*    */ 
/*    */   
/*    */   public abstract int calculateRegionHash(class_5321<class_1937> paramclass_5321, int paramInt1, int paramInt2);
/*    */ 
/*    */   
/*    */   public abstract boolean regionHasHighlights(class_5321<class_1937> paramclass_5321, int paramInt1, int paramInt2);
/*    */   
/*    */   public abstract boolean chunkIsHighlit(class_5321<class_1937> paramclass_5321, int paramInt1, int paramInt2);
/*    */   
/*    */   protected void setResult(int x, int z, int color) {
/* 29 */     this.resultStore[z << 4 | x] = color;
/*    */   } public abstract int[] getChunkHighlitColor(class_5321<class_1937> paramclass_5321, int paramInt1, int paramInt2); public abstract class_2561 getBlockHighlightSubtleTooltip(class_5321<class_1937> paramclass_5321, int paramInt1, int paramInt2); public abstract class_2561 getBlockHighlightBluntTooltip(class_5321<class_1937> paramclass_5321, int paramInt1, int paramInt2);
/*    */   public abstract void addMinimapBlockHighlightTooltips(List<class_2561> paramList, class_5321<class_1937> paramclass_5321, int paramInt1, int paramInt2, int paramInt3);
/*    */   protected int getBlend(int color1, int color2) {
/* 33 */     if (color1 == color2)
/* 34 */       return color1; 
/* 35 */     int red1 = color1 >> 8 & 0xFF;
/* 36 */     int green1 = color1 >> 16 & 0xFF;
/* 37 */     int blue1 = color1 >> 24 & 0xFF;
/* 38 */     int alpha1 = color1 & 0xFF;
/*    */     
/* 40 */     int red2 = color2 >> 8 & 0xFF;
/* 41 */     int green2 = color2 >> 16 & 0xFF;
/* 42 */     int blue2 = color2 >> 24 & 0xFF;
/* 43 */     int alpha2 = color2 & 0xFF;
/*    */     
/* 45 */     int red = red1 + red2 >> 1;
/* 46 */     int green = green1 + green2 >> 1;
/* 47 */     int blue = blue1 + blue2 >> 1;
/* 48 */     int alpha = alpha1 + alpha2 >> 1;
/*    */     
/* 50 */     return blue << 24 | green << 16 | red << 8 | alpha;
/*    */   }
/*    */   
/*    */   public boolean isCoveringOutsideDiscovered() {
/* 54 */     return this.coveringOutsideDiscovered;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\highlight\AbstractHighlighter.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */