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
/*     */ public final class FastPalette<T>
/*     */ {
/*     */   private final Object2IntMap<T> indexHelper;
/*     */   private final List<Element<T>> elements;
/*     */   private final int maxCountPerElement;
/*     */   
/*     */   private FastPalette(Object2IntMap<T> indexHelper, List<Element<T>> elements, int maxCountPerElement) {
/*  22 */     this.indexHelper = indexHelper;
/*  23 */     this.elements = elements;
/*  24 */     this.maxCountPerElement = maxCountPerElement;
/*     */   }
/*     */   
/*     */   public synchronized T get(int index) {
/*  28 */     if (index < 0 || index >= this.elements.size())
/*  29 */       return null; 
/*  30 */     Element<T> element = this.elements.get(index);
/*  31 */     if (element == null)
/*  32 */       return null; 
/*  33 */     return element.getObject();
/*     */   }
/*     */   
/*     */   public synchronized int add(T elementObject) {
/*  37 */     int existing = this.indexHelper.getOrDefault(elementObject, -1);
/*  38 */     if (existing != -1)
/*  39 */       return existing; 
/*  40 */     int newIndex = this.elements.size();
/*  41 */     boolean add = true;
/*  42 */     for (int i = 0; i < this.elements.size(); i++) {
/*  43 */       if (this.elements.get(i) == null) {
/*  44 */         newIndex = i;
/*  45 */         add = false; break;
/*     */       } 
/*     */     } 
/*  48 */     this.indexHelper.put(elementObject, newIndex);
/*  49 */     Element<T> element = new Element<>(elementObject);
/*  50 */     if (add) {
/*  51 */       this.elements.add(element);
/*     */     } else {
/*  53 */       this.elements.set(newIndex, element);
/*  54 */     }  return newIndex;
/*     */   }
/*     */   
/*     */   public synchronized int add(T elementObject, int count) {
/*  58 */     if (count < 0 || count > this.maxCountPerElement)
/*  59 */       throw new IllegalArgumentException("illegal count!"); 
/*  60 */     int index = add(elementObject);
/*  61 */     ((Element)this.elements.get(index)).count = (short)count;
/*  62 */     return index;
/*     */   }
/*     */   
/*     */   public synchronized int append(T elementObject, int count) {
/*  66 */     if (count < 0 || count > this.maxCountPerElement)
/*  67 */       throw new IllegalArgumentException("illegal count!"); 
/*  68 */     int existing = this.indexHelper.getOrDefault(elementObject, -1);
/*  69 */     if (existing != -1)
/*  70 */       throw new IllegalArgumentException("duplicate palette element!"); 
/*  71 */     int newIndex = this.elements.size();
/*  72 */     this.indexHelper.put(elementObject, newIndex);
/*  73 */     Element<T> element = new Element<>(elementObject);
/*  74 */     element.count = (short)count;
/*  75 */     this.elements.add(element);
/*  76 */     return newIndex;
/*     */   }
/*     */   
/*     */   public synchronized int getIndex(T elementObject) {
/*  80 */     return this.indexHelper.getOrDefault(elementObject, -1);
/*     */   }
/*     */   
/*     */   public synchronized int count(int index, boolean up) {
/*  84 */     Element<T> element = this.elements.get(index);
/*  85 */     element.count(up, this.maxCountPerElement);
/*  86 */     return element.getCount();
/*     */   }
/*     */   
/*     */   public synchronized int getCount(int index) {
/*  90 */     Element<T> element = this.elements.get(index);
/*  91 */     return element.getCount();
/*     */   }
/*     */   
/*     */   public synchronized void remove(int index) {
/*  95 */     Element<T> previous = this.elements.set(index, null);
/*  96 */     if (previous != null)
/*  97 */       this.indexHelper.removeInt(previous.getObject()); 
/*  98 */     if (index == this.elements.size() - 1)
/*  99 */       while (!this.elements.isEmpty() && this.elements.get(this.elements.size() - 1) == null) {
/* 100 */         this.elements.remove(this.elements.size() - 1);
/*     */       } 
/*     */   }
/*     */   
/*     */   public synchronized boolean replace(T elementObject, T newObject) {
/* 105 */     int index = this.indexHelper.getOrDefault(elementObject, -1);
/* 106 */     if (index == -1)
/* 107 */       return false; 
/* 108 */     return replace(index, newObject);
/*     */   }
/*     */   
/*     */   public synchronized boolean replace(int index, T newObject) {
/* 112 */     Element<T> element = this.elements.get(index);
/* 113 */     T elementObject = element.getObject();
/* 114 */     element.setObject(newObject);
/* 115 */     this.indexHelper.removeInt(elementObject);
/* 116 */     this.indexHelper.put(newObject, index);
/* 117 */     return true;
/*     */   }
/*     */   
/*     */   public synchronized void addNull() {
/* 121 */     this.elements.add(null);
/*     */   }
/*     */   
/*     */   public int getSize() {
/* 125 */     return this.elements.size();
/*     */   }
/*     */   
/*     */   public int getNonNullCount() {
/* 129 */     return this.indexHelper.size();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Builder<T>
/*     */   {
/*     */     private int maxCountPerElement;
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder<T> setDefault() {
/* 141 */       setMaxCountPerElement(0);
/* 142 */       return this;
/*     */     }
/*     */     
/*     */     public Builder<T> setMaxCountPerElement(int maxCountPerElement) {
/* 146 */       this.maxCountPerElement = maxCountPerElement;
/* 147 */       return this;
/*     */     }
/*     */     
/*     */     public FastPalette<T> build() {
/* 151 */       if (this.maxCountPerElement == 0)
/* 152 */         throw new IllegalStateException(); 
/* 153 */       if (this.maxCountPerElement > 65535)
/* 154 */         throw new IllegalStateException("the max count must be within 0 - 65535"); 
/* 155 */       Object2IntOpenHashMap object2IntOpenHashMap = new Object2IntOpenHashMap();
/* 156 */       List<FastPalette.Element<T>> elements = new ArrayList<>();
/* 157 */       return new FastPalette<>((Object2IntMap<T>)object2IntOpenHashMap, elements, this.maxCountPerElement);
/*     */     }
/*     */     
/*     */     public static <T> Builder<T> begin() {
/* 161 */       return (new Builder<>()).setDefault();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class Element<T>
/*     */   {
/*     */     private T object;
/*     */     
/*     */     private short count;
/*     */     
/*     */     private Element(T elementObject) {
/* 173 */       this.object = elementObject;
/*     */     }
/*     */     
/*     */     private void setObject(T elementObject) {
/* 177 */       this.object = elementObject;
/*     */     }
/*     */     
/*     */     private T getObject() {
/* 181 */       return this.object;
/*     */     }
/*     */     
/*     */     private int getCount() {
/* 185 */       return this.count & 0xFFFF;
/*     */     }
/*     */     
/*     */     private void count(boolean up, int maxCount) {
/* 189 */       this.count = (short)(this.count + (up ? 1 : -1));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\palette\FastPalette.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */