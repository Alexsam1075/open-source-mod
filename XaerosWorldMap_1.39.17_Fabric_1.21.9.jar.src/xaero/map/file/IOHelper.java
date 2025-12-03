/*    */ package xaero.map.file;
/*    */ 
/*    */ import java.io.DataInputStream;
/*    */ import java.io.EOFException;
/*    */ import java.io.IOException;
/*    */ 
/*    */ public class IOHelper
/*    */ {
/*    */   public static void readToBuffer(byte[] buffer, int count, DataInputStream input) throws IOException {
/* 10 */     int currentTotal = 0;
/* 11 */     while (currentTotal < count) {
/* 12 */       int readCount = input.read(buffer, currentTotal, count - currentTotal);
/* 13 */       if (readCount == -1)
/* 14 */         throw new EOFException(); 
/* 15 */       currentTotal += readCount;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\file\IOHelper.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */