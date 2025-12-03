/*     */ package xaero.map.palette;
/*     */ 
/*     */ import xaero.map.misc.ConsistentBitArray;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Paletted2DFastBitArrayStorage<T>
/*     */ {
/*     */   private final FastPalette<T> palette;
/*     */   private final int width;
/*     */   private final int height;
/*     */   private ConsistentBitArray data;
/*     */   private final T defaultValue;
/*     */   private int defaultValueCount;
/*     */   
/*     */   private Paletted2DFastBitArrayStorage(FastPalette<T> palette, T defaultValue, int width, int height, int defaultValueCount, ConsistentBitArray data) {
/*  20 */     this.palette = palette;
/*  21 */     this.defaultValue = defaultValue;
/*  22 */     this.width = width;
/*  23 */     this.height = height;
/*  24 */     this.data = data;
/*  25 */     this.defaultValueCount = defaultValueCount;
/*     */   }
/*     */   
/*     */   private void checkRange(int x, int y) {
/*  29 */     if (x < 0 || y < 0 || x >= this.width || y >= this.height)
/*  30 */       throw new IllegalArgumentException("out of bounds! (x: " + x + "; y: " + y + ") (w: " + this.width + "; h: " + this.height + ")"); 
/*     */   }
/*     */   
/*     */   private int getIndex(int x, int y) {
/*  34 */     return y * this.width + x;
/*     */   }
/*     */   
/*     */   public synchronized T get(int x, int y) {
/*  38 */     checkRange(x, y);
/*  39 */     int index = getIndex(x, y);
/*  40 */     int paletteIndex = this.data.get(index);
/*  41 */     if (paletteIndex == 0)
/*  42 */       return this.defaultValue; 
/*  43 */     return this.palette.get(paletteIndex - 1);
/*     */   }
/*     */   
/*     */   public synchronized void set(int x, int y, T object) {
/*  47 */     checkRange(x, y);
/*  48 */     int index = getIndex(x, y);
/*  49 */     int currentPaletteIndex = this.data.get(index);
/*  50 */     int newPaletteIndex = 0;
/*  51 */     if (currentPaletteIndex > 0) {
/*  52 */       newPaletteIndex = this.palette.getIndex(object) + 1;
/*  53 */       if (newPaletteIndex == currentPaletteIndex)
/*     */         return; 
/*  55 */       int replacedObjectCount = this.palette.count(currentPaletteIndex - 1, false);
/*  56 */       if (replacedObjectCount == 0)
/*  57 */         this.palette.remove(currentPaletteIndex - 1); 
/*     */     } else {
/*  59 */       this.defaultValueCount--;
/*  60 */     }  if (object != this.defaultValue) {
/*  61 */       if (newPaletteIndex == 0)
/*  62 */         newPaletteIndex = this.palette.add(object) + 1; 
/*  63 */       this.palette.count(newPaletteIndex - 1, true);
/*     */     } else {
/*  65 */       this.defaultValueCount++;
/*  66 */     }  this.data.set(index, newPaletteIndex);
/*     */   }
/*     */   
/*     */   public boolean contains(T object) {
/*  70 */     return (this.palette.getIndex(object) != -1);
/*     */   }
/*     */   
/*     */   public int getPaletteSize() {
/*  74 */     return this.palette.getSize();
/*     */   }
/*     */   
/*     */   public int getPaletteNonNullCount() {
/*  78 */     return this.palette.getNonNullCount();
/*     */   }
/*     */   
/*     */   public T getPaletteElement(int index) {
/*  82 */     return this.palette.get(index);
/*     */   }
/*     */   
/*     */   public int getPaletteElementCount(int index) {
/*  86 */     return this.palette.getCount(index);
/*     */   }
/*     */   
/*     */   public int getDefaultValueCount() {
/*  90 */     return this.defaultValueCount;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class Builder<T>
/*     */   {
/*     */     private int width;
/*     */     
/*     */     private int height;
/*     */     
/*     */     private int maxPaletteElements;
/*     */     private T defaultValue;
/*     */     private FastPalette<T> palette;
/*     */     private ConsistentBitArray data;
/*     */     private int defaultValueCount;
/*     */     
/*     */     public Builder<T> setDefault() {
/* 107 */       setWidth(0);
/* 108 */       setHeight(0);
/* 109 */       setDefaultValue(null);
/* 110 */       setMaxPaletteElements(0);
/* 111 */       setPalette(null);
/* 112 */       setData(null);
/* 113 */       setDefaultValueCount(-2147483648);
/* 114 */       return this;
/*     */     }
/*     */     
/*     */     public Builder<T> setWidth(int width) {
/* 118 */       this.width = width;
/* 119 */       return this;
/*     */     }
/*     */     
/*     */     public Builder<T> setHeight(int height) {
/* 123 */       this.height = height;
/* 124 */       return this;
/*     */     }
/*     */     
/*     */     public Builder<T> setMaxPaletteElements(int maxPaletteElements) {
/* 128 */       this.maxPaletteElements = maxPaletteElements;
/* 129 */       return this;
/*     */     }
/*     */     
/*     */     public Builder<T> setDefaultValue(T defaultValue) {
/* 133 */       this.defaultValue = defaultValue;
/* 134 */       return this;
/*     */     }
/*     */     
/*     */     public Builder<T> setPalette(FastPalette<T> palette) {
/* 138 */       this.palette = palette;
/* 139 */       return this;
/*     */     }
/*     */     
/*     */     public Builder<T> setData(ConsistentBitArray data) {
/* 143 */       this.data = data;
/* 144 */       return this;
/*     */     }
/*     */     
/*     */     public Builder<T> setDefaultValueCount(int defaultValueCount) {
/* 148 */       this.defaultValueCount = defaultValueCount;
/* 149 */       return this;
/*     */     }
/*     */     
/*     */     public Paletted2DFastBitArrayStorage<T> build() {
/* 153 */       if (this.width == 0 || this.height == 0 || this.maxPaletteElements == 0)
/* 154 */         throw new IllegalStateException(); 
/* 155 */       if (this.palette == null)
/* 156 */         this.palette = FastPalette.Builder.<T>begin().setMaxCountPerElement(this.width * this.height).build(); 
/* 157 */       int bitsPerEntry = (int)Math.ceil(Math.log((this.maxPaletteElements + 1)) / Math.log(2.0D));
/* 158 */       if (this.data == null)
/* 159 */         this.data = new ConsistentBitArray(bitsPerEntry, this.width * this.height); 
/* 160 */       if (this.data.getBitsPerEntry() != bitsPerEntry)
/* 161 */         throw new IllegalStateException(); 
/* 162 */       if (this.defaultValueCount == Integer.MIN_VALUE)
/* 163 */         this.defaultValueCount = this.width * this.height; 
/* 164 */       if (this.defaultValueCount < 0)
/* 165 */         throw new IllegalStateException(); 
/* 166 */       return new Paletted2DFastBitArrayStorage<>(this.palette, this.defaultValue, this.width, this.height, this.defaultValueCount, this.data);
/*     */     }
/*     */     
/*     */     public static <T> Builder<T> begin() {
/* 170 */       return (new Builder<>()).setDefault();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\palette\Paletted2DFastBitArrayStorage.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */