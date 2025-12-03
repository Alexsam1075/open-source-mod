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
/*     */ public final class FastIntPalette
/*     */ {
/*     */   private final Int2IntMap indexHelper;
/*     */   private final List<Element> elements;
/*     */   private final int maxCountPerElement;
/*     */   
/*     */   private FastIntPalette(Int2IntMap indexHelper, List<Element> elements, int maxCountPerElement) {
/*  21 */     this.indexHelper = indexHelper;
/*  22 */     this.elements = elements;
/*  23 */     this.maxCountPerElement = maxCountPerElement;
/*     */   }
/*     */   
/*     */   public synchronized int get(int index, int defaultValue) {
/*  27 */     if (index < 0 || index >= this.elements.size())
/*  28 */       return defaultValue; 
/*  29 */     Element element = this.elements.get(index);
/*  30 */     if (element == null)
/*  31 */       return defaultValue; 
/*  32 */     return element.getValue();
/*     */   }
/*     */   
/*     */   public synchronized int add(int elementValue) {
/*  36 */     int existing = this.indexHelper.getOrDefault(elementValue, -1);
/*  37 */     if (existing != -1)
/*  38 */       return existing; 
/*  39 */     int newIndex = this.elements.size();
/*  40 */     boolean add = true;
/*  41 */     for (int i = 0; i < this.elements.size(); i++) {
/*  42 */       if (this.elements.get(i) == null) {
/*  43 */         newIndex = i;
/*  44 */         add = false; break;
/*     */       } 
/*     */     } 
/*  47 */     this.indexHelper.put(elementValue, newIndex);
/*  48 */     Element element = new Element(elementValue);
/*  49 */     if (add) {
/*  50 */       this.elements.add(element);
/*     */     } else {
/*  52 */       this.elements.set(newIndex, element);
/*  53 */     }  return newIndex;
/*     */   }
/*     */   
/*     */   public synchronized int add(int elementValue, int count) {
/*  57 */     if (count < 0 || count > this.maxCountPerElement)
/*  58 */       throw new IllegalArgumentException("illegal count!"); 
/*  59 */     int index = add(elementValue);
/*  60 */     ((Element)this.elements.get(index)).count = (short)count;
/*  61 */     return index;
/*     */   }
/*     */   
/*     */   public synchronized int append(int elementValue, int count) {
/*  65 */     if (count < 0 || count > this.maxCountPerElement)
/*  66 */       throw new IllegalArgumentException("illegal count!"); 
/*  67 */     int existing = this.indexHelper.getOrDefault(elementValue, -1);
/*  68 */     if (existing != -1)
/*  69 */       throw new IllegalArgumentException("duplicate palette element!"); 
/*  70 */     int newIndex = this.elements.size();
/*  71 */     this.indexHelper.put(elementValue, newIndex);
/*  72 */     Element element = new Element(elementValue);
/*  73 */     element.count = (short)count;
/*  74 */     this.elements.add(element);
/*  75 */     return newIndex;
/*     */   }
/*     */   
/*     */   public synchronized int getIndex(int elementValue) {
/*  79 */     return this.indexHelper.getOrDefault(elementValue, -1);
/*     */   }
/*     */   
/*     */   public synchronized int count(int index, boolean up) {
/*  83 */     Element element = this.elements.get(index);
/*  84 */     element.count(up, this.maxCountPerElement);
/*  85 */     return element.getCount();
/*     */   }
/*     */   
/*     */   public synchronized int getCount(int index) {
/*  89 */     Element element = this.elements.get(index);
/*  90 */     return element.getCount();
/*     */   }
/*     */   
/*     */   public synchronized void remove(int index) {
/*  94 */     Element previous = this.elements.set(index, null);
/*  95 */     if (previous != null)
/*  96 */       this.indexHelper.remove(previous.getValue()); 
/*  97 */     if (index == this.elements.size() - 1)
/*  98 */       while (!this.elements.isEmpty() && this.elements.get(this.elements.size() - 1) == null) {
/*  99 */         this.elements.remove(this.elements.size() - 1);
/*     */       } 
/*     */   }
/*     */   
/*     */   public synchronized boolean replace(int elementValue, int newValue) {
/* 104 */     int index = this.indexHelper.getOrDefault(elementValue, -1);
/* 105 */     if (index == -1)
/* 106 */       return false; 
/* 107 */     return replaceAtIndex(index, newValue);
/*     */   }
/*     */   
/*     */   public synchronized boolean replaceAtIndex(int index, int newValue) {
/* 111 */     Element element = this.elements.get(index);
/* 112 */     int elementValue = element.getValue();
/* 113 */     element.setValue(newValue);
/* 114 */     this.indexHelper.remove(elementValue);
/* 115 */     this.indexHelper.put(newValue, index);
/* 116 */     return true;
/*     */   }
/*     */   
/*     */   public synchronized void addNull() {
/* 120 */     this.elements.add(null);
/*     */   }
/*     */   
/*     */   public int getSize() {
/* 124 */     return this.elements.size();
/*     */   }
/*     */   
/*     */   public int getNonNullCount() {
/* 128 */     return this.indexHelper.size();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Builder
/*     */   {
/*     */     private int maxCountPerElement;
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setDefault() {
/* 140 */       setMaxCountPerElement(0);
/* 141 */       return this;
/*     */     }
/*     */     
/*     */     public Builder setMaxCountPerElement(int maxCountPerElement) {
/* 145 */       this.maxCountPerElement = maxCountPerElement;
/* 146 */       return this;
/*     */     }
/*     */     
/*     */     public FastIntPalette build() {
/* 150 */       if (this.maxCountPerElement == 0)
/* 151 */         throw new IllegalStateException(); 
/* 152 */       if (this.maxCountPerElement > 65535)
/* 153 */         throw new IllegalStateException("the max count must be within 0 - 65535"); 
/* 154 */       Int2IntOpenHashMap int2IntOpenHashMap = new Int2IntOpenHashMap();
/* 155 */       List<FastIntPalette.Element> elements = new ArrayList<>();
/* 156 */       return new FastIntPalette((Int2IntMap)int2IntOpenHashMap, elements, this.maxCountPerElement);
/*     */     }
/*     */     
/*     */     public static Builder begin() {
/* 160 */       return (new Builder()).setDefault();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class Element
/*     */   {
/*     */     private int value;
/*     */     
/*     */     private short count;
/*     */     
/*     */     private Element(int elementValue) {
/* 172 */       this.value = elementValue;
/*     */     }
/*     */     
/*     */     private void setValue(int elementValue) {
/* 176 */       this.value = elementValue;
/*     */     }
/*     */     
/*     */     private int getValue() {
/* 180 */       return this.value;
/*     */     }
/*     */     
/*     */     private int getCount() {
/* 184 */       return this.count & 0xFFFF;
/*     */     }
/*     */     
/*     */     private void count(boolean up, int maxCount) {
/* 188 */       if ((up && this.count == maxCount) || (!up && this.count == 0))
/* 189 */         throw new IllegalStateException(); 
/* 190 */       this.count = (short)(this.count + (up ? 1 : -1));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\palette\FastIntPalette.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */