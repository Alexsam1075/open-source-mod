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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   private int width;
/*     */   private int height;
/*     */   private int maxPaletteElements;
/*     */   private int defaultValue;
/*     */   private FastIntPalette palette;
/*     */   private ConsistentBitArray data;
/*     */   private int defaultValueCount;
/*     */   
/*     */   public Builder setDefault() {
/* 130 */     setWidth(0);
/* 131 */     setHeight(0);
/* 132 */     setDefaultValue(-1);
/* 133 */     setMaxPaletteElements(0);
/* 134 */     setPalette(null);
/* 135 */     setData(null);
/* 136 */     setDefaultValueCount(-2147483648);
/* 137 */     return this;
/*     */   }
/*     */   
/*     */   public Builder setWidth(int width) {
/* 141 */     this.width = width;
/* 142 */     return this;
/*     */   }
/*     */   
/*     */   public Builder setHeight(int height) {
/* 146 */     this.height = height;
/* 147 */     return this;
/*     */   }
/*     */   
/*     */   public Builder setMaxPaletteElements(int maxPaletteElements) {
/* 151 */     this.maxPaletteElements = maxPaletteElements;
/* 152 */     return this;
/*     */   }
/*     */   
/*     */   public Builder setDefaultValue(int defaultValue) {
/* 156 */     this.defaultValue = defaultValue;
/* 157 */     return this;
/*     */   }
/*     */   
/*     */   public Builder setPalette(FastIntPalette palette) {
/* 161 */     this.palette = palette;
/* 162 */     return this;
/*     */   }
/*     */   
/*     */   public Builder setData(ConsistentBitArray data) {
/* 166 */     this.data = data;
/* 167 */     return this;
/*     */   }
/*     */   
/*     */   public Builder setDefaultValueCount(int defaultValueCount) {
/* 171 */     this.defaultValueCount = defaultValueCount;
/* 172 */     return this;
/*     */   }
/*     */   
/*     */   public Paletted2DFastBitArrayIntStorage build() {
/* 176 */     if (this.width == 0 || this.height == 0 || this.maxPaletteElements == 0)
/* 177 */       throw new IllegalStateException(); 
/* 178 */     if (this.palette == null)
/* 179 */       this.palette = FastIntPalette.Builder.begin().setMaxCountPerElement(this.width * this.height).build(); 
/* 180 */     int bitsPerEntry = (int)Math.ceil(Math.log((this.maxPaletteElements + 1)) / Math.log(2.0D));
/* 181 */     if (this.data == null)
/* 182 */       this.data = new ConsistentBitArray(bitsPerEntry, this.width * this.height); 
/* 183 */     if (this.data.getBitsPerEntry() != bitsPerEntry)
/* 184 */       throw new IllegalStateException(); 
/* 185 */     if (this.defaultValueCount == Integer.MIN_VALUE)
/* 186 */       this.defaultValueCount = this.width * this.height; 
/* 187 */     if (this.defaultValueCount < 0)
/* 188 */       throw new IllegalStateException(); 
/* 189 */     return new Paletted2DFastBitArrayIntStorage(this.palette, this.defaultValue, this.width, this.height, this.defaultValueCount, this.data);
/*     */   }
/*     */   
/*     */   public static <T> Builder begin() {
/* 193 */     return (new Builder()).setDefault();
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\palette\Paletted2DFastBitArrayIntStorage$Builder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */