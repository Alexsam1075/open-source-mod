/*     */ package xaero.map.palette;
/*     */ 
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import xaero.map.misc.ConsistentBitArray;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Paletted2DFastBitArrayIntStorage
/*     */ {
/*     */   private final FastIntPalette palette;
/*     */   private final int width;
/*     */   private final int height;
/*     */   private ConsistentBitArray data;
/*     */   private final int defaultValue;
/*     */   private int defaultValueCount;
/*     */   
/*     */   private Paletted2DFastBitArrayIntStorage(FastIntPalette palette, int defaultValue, int width, int height, int defaultValueCount, ConsistentBitArray data) {
/*  22 */     this.palette = palette;
/*  23 */     this.defaultValue = defaultValue;
/*  24 */     this.width = width;
/*  25 */     this.height = height;
/*  26 */     this.data = data;
/*  27 */     this.defaultValueCount = defaultValueCount;
/*     */   }
/*     */   
/*     */   private void checkRange(int x, int y) {
/*  31 */     if (x < 0 || y < 0 || x >= this.width || y >= this.height)
/*  32 */       throw new IllegalArgumentException("out of bounds! (x: " + x + "; y: " + y + ") (w: " + this.width + "; h: " + this.height + ")"); 
/*     */   }
/*     */   
/*     */   private int getIndex(int x, int y) {
/*  36 */     return y * this.width + x;
/*     */   }
/*     */   
/*     */   public synchronized int get(int x, int y) {
/*  40 */     checkRange(x, y);
/*  41 */     int index = getIndex(x, y);
/*  42 */     int paletteIndex = this.data.get(index);
/*  43 */     if (paletteIndex == 0)
/*  44 */       return this.defaultValue; 
/*  45 */     return this.palette.get(paletteIndex - 1, this.defaultValue);
/*     */   }
/*     */   
/*     */   public synchronized int getRaw(int x, int y) {
/*  49 */     checkRange(x, y);
/*  50 */     int index = getIndex(x, y);
/*  51 */     return this.data.get(index);
/*     */   }
/*     */   
/*     */   public synchronized void set(int x, int y, int value) {
/*  55 */     checkRange(x, y);
/*  56 */     int index = getIndex(x, y);
/*  57 */     int currentPaletteIndex = this.data.get(index);
/*  58 */     int newPaletteIndex = 0;
/*  59 */     if (currentPaletteIndex > 0) {
/*  60 */       newPaletteIndex = this.palette.getIndex(value) + 1;
/*  61 */       if (newPaletteIndex == currentPaletteIndex)
/*     */         return; 
/*  63 */       int replacedValueCount = this.palette.count(currentPaletteIndex - 1, false);
/*  64 */       if (replacedValueCount == 0)
/*  65 */         this.palette.remove(currentPaletteIndex - 1); 
/*     */     } else {
/*  67 */       this.defaultValueCount--;
/*  68 */     }  if (value != this.defaultValue) {
/*  69 */       if (newPaletteIndex == 0)
/*  70 */         newPaletteIndex = this.palette.add(value) + 1; 
/*  71 */       this.palette.count(newPaletteIndex - 1, true);
/*     */     } else {
/*  73 */       this.defaultValueCount++;
/*  74 */     }  this.data.set(index, newPaletteIndex);
/*     */   }
/*     */   
/*     */   public void writeData(DataOutputStream output) throws IOException {
/*  78 */     this.data.write(output);
/*     */   }
/*     */   
/*     */   public boolean contains(int value) {
/*  82 */     return (this.palette.getIndex(value) != -1);
/*     */   }
/*     */   
/*     */   public int getPaletteSize() {
/*  86 */     return this.palette.getSize();
/*     */   }
/*     */   
/*     */   public int getPaletteNonNullCount() {
/*  90 */     return this.palette.getNonNullCount();
/*     */   }
/*     */   
/*     */   public int getPaletteElement(int index) {
/*  94 */     return this.palette.get(index, this.defaultValue);
/*     */   }
/*     */   
/*     */   public int getPaletteElementCount(int index) {
/*  98 */     return this.palette.getCount(index);
/*     */   }
/*     */   
/*     */   public int getDefaultValueCount() {
/* 102 */     return this.defaultValueCount;
/*     */   }
/*     */   
/*     */   public String getBiomePaletteDebug() {
/* 106 */     String biomePaletteLine = "" + this.defaultValueCount + " / ";
/* 107 */     for (int i = 0; i < this.palette.getSize(); i++) {
/* 108 */       if (i > 0)
/* 109 */         biomePaletteLine = biomePaletteLine + ", "; 
/* 110 */       int paletteElement = this.palette.get(i, -1);
/* 111 */       biomePaletteLine = biomePaletteLine + biomePaletteLine + ":" + paletteElement;
/*     */     } 
/* 113 */     return biomePaletteLine;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class Builder
/*     */   {
/*     */     private int width;
/*     */     
/*     */     private int height;
/*     */     
/*     */     private int maxPaletteElements;
/*     */     private int defaultValue;
/*     */     private FastIntPalette palette;
/*     */     private ConsistentBitArray data;
/*     */     private int defaultValueCount;
/*     */     
/*     */     public Builder setDefault() {
/* 130 */       setWidth(0);
/* 131 */       setHeight(0);
/* 132 */       setDefaultValue(-1);
/* 133 */       setMaxPaletteElements(0);
/* 134 */       setPalette(null);
/* 135 */       setData(null);
/* 136 */       setDefaultValueCount(-2147483648);
/* 137 */       return this;
/*     */     }
/*     */     
/*     */     public Builder setWidth(int width) {
/* 141 */       this.width = width;
/* 142 */       return this;
/*     */     }
/*     */     
/*     */     public Builder setHeight(int height) {
/* 146 */       this.height = height;
/* 147 */       return this;
/*     */     }
/*     */     
/*     */     public Builder setMaxPaletteElements(int maxPaletteElements) {
/* 151 */       this.maxPaletteElements = maxPaletteElements;
/* 152 */       return this;
/*     */     }
/*     */     
/*     */     public Builder setDefaultValue(int defaultValue) {
/* 156 */       this.defaultValue = defaultValue;
/* 157 */       return this;
/*     */     }
/*     */     
/*     */     public Builder setPalette(FastIntPalette palette) {
/* 161 */       this.palette = palette;
/* 162 */       return this;
/*     */     }
/*     */     
/*     */     public Builder setData(ConsistentBitArray data) {
/* 166 */       this.data = data;
/* 167 */       return this;
/*     */     }
/*     */     
/*     */     public Builder setDefaultValueCount(int defaultValueCount) {
/* 171 */       this.defaultValueCount = defaultValueCount;
/* 172 */       return this;
/*     */     }
/*     */     
/*     */     public Paletted2DFastBitArrayIntStorage build() {
/* 176 */       if (this.width == 0 || this.height == 0 || this.maxPaletteElements == 0)
/* 177 */         throw new IllegalStateException(); 
/* 178 */       if (this.palette == null)
/* 179 */         this.palette = FastIntPalette.Builder.begin().setMaxCountPerElement(this.width * this.height).build(); 
/* 180 */       int bitsPerEntry = (int)Math.ceil(Math.log((this.maxPaletteElements + 1)) / Math.log(2.0D));
/* 181 */       if (this.data == null)
/* 182 */         this.data = new ConsistentBitArray(bitsPerEntry, this.width * this.height); 
/* 183 */       if (this.data.getBitsPerEntry() != bitsPerEntry)
/* 184 */         throw new IllegalStateException(); 
/* 185 */       if (this.defaultValueCount == Integer.MIN_VALUE)
/* 186 */         this.defaultValueCount = this.width * this.height; 
/* 187 */       if (this.defaultValueCount < 0)
/* 188 */         throw new IllegalStateException(); 
/* 189 */       return new Paletted2DFastBitArrayIntStorage(this.palette, this.defaultValue, this.width, this.height, this.defaultValueCount, this.data);
/*     */     }
/*     */     
/*     */     public static <T> Builder begin() {
/* 193 */       return (new Builder()).setDefault();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\palette\Paletted2DFastBitArrayIntStorage.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */