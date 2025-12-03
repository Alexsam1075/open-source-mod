/*     */ package xaero.map.palette;
/*     */ 
/*     */ import xaero.map.misc.ConsistentBitArray;
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
/*     */   private int width;
/*     */   private int height;
/*     */   private int maxPaletteElements;
/*     */   private T defaultValue;
/*     */   private FastPalette<T> palette;
/*     */   private ConsistentBitArray data;
/*     */   private int defaultValueCount;
/*     */   
/*     */   public Builder<T> setDefault() {
/* 107 */     setWidth(0);
/* 108 */     setHeight(0);
/* 109 */     setDefaultValue(null);
/* 110 */     setMaxPaletteElements(0);
/* 111 */     setPalette(null);
/* 112 */     setData(null);
/* 113 */     setDefaultValueCount(-2147483648);
/* 114 */     return this;
/*     */   }
/*     */   
/*     */   public Builder<T> setWidth(int width) {
/* 118 */     this.width = width;
/* 119 */     return this;
/*     */   }
/*     */   
/*     */   public Builder<T> setHeight(int height) {
/* 123 */     this.height = height;
/* 124 */     return this;
/*     */   }
/*     */   
/*     */   public Builder<T> setMaxPaletteElements(int maxPaletteElements) {
/* 128 */     this.maxPaletteElements = maxPaletteElements;
/* 129 */     return this;
/*     */   }
/*     */   
/*     */   public Builder<T> setDefaultValue(T defaultValue) {
/* 133 */     this.defaultValue = defaultValue;
/* 134 */     return this;
/*     */   }
/*     */   
/*     */   public Builder<T> setPalette(FastPalette<T> palette) {
/* 138 */     this.palette = palette;
/* 139 */     return this;
/*     */   }
/*     */   
/*     */   public Builder<T> setData(ConsistentBitArray data) {
/* 143 */     this.data = data;
/* 144 */     return this;
/*     */   }
/*     */   
/*     */   public Builder<T> setDefaultValueCount(int defaultValueCount) {
/* 148 */     this.defaultValueCount = defaultValueCount;
/* 149 */     return this;
/*     */   }
/*     */   
/*     */   public Paletted2DFastBitArrayStorage<T> build() {
/* 153 */     if (this.width == 0 || this.height == 0 || this.maxPaletteElements == 0)
/* 154 */       throw new IllegalStateException(); 
/* 155 */     if (this.palette == null)
/* 156 */       this.palette = FastPalette.Builder.<T>begin().setMaxCountPerElement(this.width * this.height).build(); 
/* 157 */     int bitsPerEntry = (int)Math.ceil(Math.log((this.maxPaletteElements + 1)) / Math.log(2.0D));
/* 158 */     if (this.data == null)
/* 159 */       this.data = new ConsistentBitArray(bitsPerEntry, this.width * this.height); 
/* 160 */     if (this.data.getBitsPerEntry() != bitsPerEntry)
/* 161 */       throw new IllegalStateException(); 
/* 162 */     if (this.defaultValueCount == Integer.MIN_VALUE)
/* 163 */       this.defaultValueCount = this.width * this.height; 
/* 164 */     if (this.defaultValueCount < 0)
/* 165 */       throw new IllegalStateException(); 
/* 166 */     return new Paletted2DFastBitArrayStorage<>(this.palette, this.defaultValue, this.width, this.height, this.defaultValueCount, this.data);
/*     */   }
/*     */   
/*     */   public static <T> Builder<T> begin() {
/* 170 */     return (new Builder<>()).setDefault();
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\palette\Paletted2DFastBitArrayStorage$Builder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */