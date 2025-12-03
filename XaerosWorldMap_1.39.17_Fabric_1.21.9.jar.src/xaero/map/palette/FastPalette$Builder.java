/*     */ package xaero.map.palette;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
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
/*     */ 
/*     */ public final class Builder<T>
/*     */ {
/*     */   private int maxCountPerElement;
/*     */   
/*     */   public Builder<T> setDefault() {
/* 141 */     setMaxCountPerElement(0);
/* 142 */     return this;
/*     */   }
/*     */   
/*     */   public Builder<T> setMaxCountPerElement(int maxCountPerElement) {
/* 146 */     this.maxCountPerElement = maxCountPerElement;
/* 147 */     return this;
/*     */   }
/*     */   
/*     */   public FastPalette<T> build() {
/* 151 */     if (this.maxCountPerElement == 0)
/* 152 */       throw new IllegalStateException(); 
/* 153 */     if (this.maxCountPerElement > 65535)
/* 154 */       throw new IllegalStateException("the max count must be within 0 - 65535"); 
/* 155 */     Object2IntOpenHashMap object2IntOpenHashMap = new Object2IntOpenHashMap();
/* 156 */     List<FastPalette.Element<T>> elements = new ArrayList<>();
/* 157 */     return new FastPalette<>((Object2IntMap<T>)object2IntOpenHashMap, elements, this.maxCountPerElement);
/*     */   }
/*     */   
/*     */   public static <T> Builder<T> begin() {
/* 161 */     return (new Builder<>()).setDefault();
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\palette\FastPalette$Builder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */