/*    */ package xaero.map.patreon.decrypt;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.security.GeneralSecurityException;
/*    */ import java.util.Date;
/*    */ import javax.crypto.Cipher;
/*    */ 
/*    */ public class DecryptInputStream
/*    */   extends InputStream {
/*    */   private InputStream src;
/*    */   private Cipher cipher;
/* 13 */   private byte[] encryptedBuffer = new byte[256];
/*    */   private byte[] currentBlock;
/*    */   private int blockCount;
/*    */   private int blockOffset;
/*    */   private boolean endReached;
/* 18 */   private long prevExpirationTime = -1L;
/*    */   
/*    */   public DecryptInputStream(InputStream src, Cipher cipher) {
/* 21 */     this.src = src;
/* 22 */     this.cipher = cipher;
/*    */   }
/*    */ 
/*    */   
/*    */   public int read() throws IOException {
/* 27 */     if (this.endReached) {
/* 28 */       return -1;
/*    */     }
/* 30 */     if (this.currentBlock == null || this.currentBlock.length == this.blockOffset) {
/* 31 */       int offset = 0;
/* 32 */       while (offset < 256) {
/* 33 */         int read = this.src.read(this.encryptedBuffer, offset, 256 - offset);
/* 34 */         if (read == -1) {
/* 35 */           this.endReached = true;
/* 36 */           if (offset == 0) {
/* 37 */             throw new IOException("Online mod data missing confirmation block!");
/*    */           }
/* 39 */           throw new IOException("Encrypted block too short!");
/*    */         } 
/* 41 */         offset += read;
/*    */       } 
/*    */       try {
/* 44 */         this.currentBlock = this.cipher.doFinal(this.encryptedBuffer);
/*    */ 
/*    */         
/* 47 */         long expirationTime = 0L;
/* 48 */         int blockIndex = 0;
/* 49 */         for (this.blockOffset = 0; this.blockOffset < 8; this.blockOffset++)
/* 50 */           expirationTime |= (this.currentBlock[this.blockOffset] & 0xFF) << 8 * this.blockOffset; 
/* 51 */         for (int i = 0; i < 2; i++) {
/* 52 */           blockIndex |= (this.currentBlock[this.blockOffset] & 0xFF) << 8 * i;
/* 53 */           this.blockOffset++;
/*    */         } 
/*    */         
/* 56 */         if (System.currentTimeMillis() > expirationTime) {
/* 57 */           this.endReached = true;
/* 58 */           throw new IOException("Online mod data expired! Date: " + String.valueOf(new Date(expirationTime)));
/*    */         } 
/* 60 */         if (this.prevExpirationTime != -1L && expirationTime != this.prevExpirationTime) {
/* 61 */           this.endReached = true;
/* 62 */           throw new IOException("Online mod data expiration date mismatch! Dates: " + String.valueOf(new Date(expirationTime)) + " VS " + String.valueOf(new Date(this.prevExpirationTime)));
/*    */         } 
/*    */         
/* 65 */         if (blockIndex != this.blockCount) {
/* 66 */           this.endReached = true;
/* 67 */           throw new IOException("Online mod data block index mismatch! " + blockIndex + " VS " + this.blockCount);
/*    */         } 
/* 69 */         this.prevExpirationTime = expirationTime;
/* 70 */         this.blockCount++;
/* 71 */         if (this.blockOffset == this.currentBlock.length) {
/* 72 */           this.endReached = true;
/* 73 */           return -1;
/*    */         } 
/* 75 */       } catch (GeneralSecurityException e) {
/* 76 */         throw new RuntimeException(e);
/*    */       } 
/*    */     } 
/* 79 */     return this.currentBlock[this.blockOffset++];
/*    */   }
/*    */ 
/*    */   
/*    */   public void close() throws IOException {
/* 84 */     super.close();
/* 85 */     this.src.close();
/* 86 */     this.encryptedBuffer = null;
/* 87 */     this.currentBlock = null;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\patreon\decrypt\DecryptInputStream.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */