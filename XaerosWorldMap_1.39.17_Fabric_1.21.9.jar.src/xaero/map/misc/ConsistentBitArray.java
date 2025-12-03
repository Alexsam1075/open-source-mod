/*    */ package xaero.map.misc;
/*    */ 
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ public class ConsistentBitArray
/*    */ {
/*    */   private int insideALong;
/*    */   private int bitsPerEntry;
/*    */   private int entries;
/*    */   private long[] data;
/*    */   private int entryMask;
/*    */   
/*    */   public ConsistentBitArray(int bitsPerEntry, int entries) {
/* 16 */     this(bitsPerEntry, entries, null);
/*    */   }
/*    */   
/*    */   public ConsistentBitArray(int bitsPerEntry, int entries, long[] data) {
/* 20 */     if (bitsPerEntry > 32) {
/* 21 */       throw new RuntimeException("Entry size too big for int! " + bitsPerEntry);
/*    */     }
/* 23 */     this.insideALong = 64 / bitsPerEntry;
/* 24 */     int longs = (entries + this.insideALong - 1) / this.insideALong;
/* 25 */     if (data != null) {
/* 26 */       if (data.length != longs)
/* 27 */         throw new RuntimeException("Incorrect data length: " + data.length + " VS " + longs); 
/* 28 */       this.data = data;
/*    */     } else {
/* 30 */       this.data = new long[longs];
/*    */     } 
/* 32 */     this.bitsPerEntry = bitsPerEntry;
/* 33 */     this.entries = entries;
/* 34 */     this.entryMask = (1 << bitsPerEntry) - 1;
/*    */   }
/*    */   
/*    */   public int get(int index) {
/* 38 */     if (index >= this.entries)
/* 39 */       throw new ArrayIndexOutOfBoundsException(index); 
/* 40 */     int longIndex = index / this.insideALong;
/* 41 */     int insideIndex = index % this.insideALong;
/* 42 */     return (int)(this.data[longIndex] >> insideIndex * this.bitsPerEntry & this.entryMask);
/*    */   }
/*    */   
/*    */   public void set(int index, int value) {
/* 46 */     if (index >= this.entries)
/* 47 */       throw new ArrayIndexOutOfBoundsException(index); 
/* 48 */     int longIndex = index / this.insideALong;
/* 49 */     int insideIndex = index % this.insideALong;
/* 50 */     long currentLong = this.data[longIndex];
/* 51 */     int offset = insideIndex * this.bitsPerEntry;
/* 52 */     long shiftedMask = this.entryMask << offset;
/* 53 */     long shiftedValue = (value & this.entryMask) << offset;
/* 54 */     this.data[longIndex] = currentLong & (shiftedMask ^ 0xFFFFFFFFFFFFFFFFL) | shiftedValue;
/*    */   }
/*    */   
/*    */   public void write(DataOutputStream output) throws IOException {
/* 58 */     for (int i = 0; i < this.data.length; i++)
/* 59 */       output.writeLong(this.data[i]); 
/*    */   }
/*    */   
/*    */   public long[] getData() {
/* 63 */     return this.data;
/*    */   }
/*    */   
/*    */   public void setData(long[] data) {
/* 67 */     this.data = data;
/*    */   }
/*    */   
/*    */   public int getBitsPerEntry() {
/* 71 */     return this.bitsPerEntry;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\misc\ConsistentBitArray.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */