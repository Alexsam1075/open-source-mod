/*    */ package xaero.map.deallocator;
/*    */ 
/*    */ import java.lang.reflect.Field;
/*    */ import java.lang.reflect.InvocationTargetException;
/*    */ import java.lang.reflect.Method;
/*    */ import java.nio.ByteBuffer;
/*    */ import xaero.map.WorldMap;
/*    */ 
/*    */ 
/*    */ public class ByteBufferDeallocator
/*    */ {
/*    */   private boolean usingInvokeCleanerMethod;
/* 13 */   private final String directBufferClassName = "java.nio.DirectByteBuffer";
/*    */   
/*    */   private Object theUnsafe;
/*    */   private Method invokeCleanerMethod;
/*    */   private Method directBufferCleanerMethod;
/*    */   private Method cleanerCleanMethod;
/*    */   
/*    */   public ByteBufferDeallocator() throws ClassNotFoundException, NoSuchMethodException, SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
/*    */     try {
/* 22 */       Class<?> unsafeClass = Class.forName("sun.misc.Unsafe");
/* 23 */       Field theUnsafeField = unsafeClass.getDeclaredField("theUnsafe");
/* 24 */       theUnsafeField.setAccessible(true);
/* 25 */       this.theUnsafe = theUnsafeField.get(null);
/* 26 */       theUnsafeField.setAccessible(false);
/* 27 */       this.invokeCleanerMethod = unsafeClass.getDeclaredMethod("invokeCleaner", new Class[] { ByteBuffer.class });
/* 28 */       this.usingInvokeCleanerMethod = true;
/* 29 */     } catch (NoSuchMethodException|NoSuchFieldException nse) {
/* 30 */       Class<?> directByteBufferClass = Class.forName("java.nio.DirectByteBuffer");
/* 31 */       this.directBufferCleanerMethod = directByteBufferClass.getDeclaredMethod("cleaner", new Class[0]);
/* 32 */       Class<?> cleanerClass = this.directBufferCleanerMethod.getReturnType();
/* 33 */       if (Runnable.class.isAssignableFrom(cleanerClass)) {
/* 34 */         this.cleanerCleanMethod = Runnable.class.getDeclaredMethod("run", new Class[0]);
/*    */       } else {
/* 36 */         this.cleanerCleanMethod = cleanerClass.getDeclaredMethod("clean", new Class[0]);
/*    */       } 
/*    */     } 
/*    */   }
/*    */   public synchronized void deallocate(ByteBuffer buffer, boolean debug) {
/* 41 */     if (buffer == null || !buffer.isDirect())
/*    */       return; 
/* 43 */     if (this.usingInvokeCleanerMethod) {
/*    */       try {
/* 45 */         this.invokeCleanerMethod.invoke(this.theUnsafe, new Object[] { buffer });
/*    */       }
/* 47 */       catch (IllegalAccessException e) {
/* 48 */         reportException(e);
/* 49 */       } catch (IllegalArgumentException e) {
/* 50 */         reportException(e);
/* 51 */       } catch (InvocationTargetException e) {
/* 52 */         reportException(e);
/*    */       } 
/*    */     } else {
/* 55 */       boolean cleanerAccessibleBU = this.directBufferCleanerMethod.isAccessible();
/* 56 */       boolean cleanAccessibleBU = this.cleanerCleanMethod.isAccessible();
/*    */       try {
/* 58 */         this.directBufferCleanerMethod.setAccessible(true);
/* 59 */         Object cleaner = this.directBufferCleanerMethod.invoke(buffer, new Object[0]);
/* 60 */         if (cleaner != null)
/* 61 */         { this.cleanerCleanMethod.setAccessible(true);
/* 62 */           this.cleanerCleanMethod.invoke(cleaner, new Object[0]); }
/* 63 */         else if (debug)
/* 64 */         { WorldMap.LOGGER.info("No cleaner to deallocate a buffer!"); } 
/* 65 */       } catch (IllegalAccessException e) {
/* 66 */         reportException(e);
/* 67 */       } catch (IllegalArgumentException e) {
/* 68 */         reportException(e);
/* 69 */       } catch (InvocationTargetException e) {
/* 70 */         reportException(e);
/*    */       } 
/* 72 */       this.directBufferCleanerMethod.setAccessible(cleanerAccessibleBU);
/* 73 */       this.cleanerCleanMethod.setAccessible(cleanAccessibleBU);
/*    */     } 
/*    */   }
/*    */   
/*    */   private void reportException(Exception e) {
/* 78 */     WorldMap.LOGGER.error("Failed to deallocate a direct byte buffer: ", e);
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\deallocator\ByteBufferDeallocator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */