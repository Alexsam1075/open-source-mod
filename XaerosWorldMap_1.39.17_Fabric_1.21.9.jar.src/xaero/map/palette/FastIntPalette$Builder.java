/*     */ package xaero.map.palette;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.ints.Int2IntMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Builder
/*     */ {
/*     */   private int maxCountPerElement;
/*     */   
/*     */   public Builder setDefault() {
/* 140 */     setMaxCountPerElement(0);
/* 141 */     return this;
/*     */   }
/*     */   
/*     */   public Builder setMaxCountPerElement(int maxCountPerElement) {
/* 145 */     this.maxCountPerElement = maxCountPerElement;
/* 146 */     return this;
/*     */   }
/*     */   
/*     */   public FastIntPalette build() {
/* 150 */     if (this.maxCountPerElement == 0)
/* 151 */       throw new IllegalStateException(); 
/* 152 */     if (this.maxCountPerElement > 65535)
/* 153 */       throw new IllegalStateException("the max count must be within 0 - 65535"); 
/* 154 */     Int2IntOpenHashMap int2IntOpenHashMap = new Int2IntOpenHashMap();
/* 155 */     List<FastIntPalette.Element> elements = new ArrayList<>();
/* 156 */     return new FastIntPalette((Int2IntMap)int2IntOpenHashMap, elements, this.maxCountPerElement);
/*     */   }
/*     */   
/*     */   public static Builder begin() {
/* 160 */     return (new Builder()).setDefault();
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\palette\FastIntPalette$Builder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */