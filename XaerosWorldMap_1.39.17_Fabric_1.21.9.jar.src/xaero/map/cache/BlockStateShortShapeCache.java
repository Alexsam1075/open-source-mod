/*    */ package xaero.map.cache;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import java.util.function.Supplier;
/*    */ import net.minecraft.class_1922;
/*    */ import net.minecraft.class_2338;
/*    */ import net.minecraft.class_2350;
/*    */ import net.minecraft.class_265;
/*    */ import net.minecraft.class_2680;
/*    */ import net.minecraft.class_310;
/*    */ import xaero.map.WorldMap;
/*    */ import xaero.map.cache.placeholder.PlaceholderBlockGetter;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockStateShortShapeCache
/*    */ {
/*    */   private Map<class_2680, Boolean> shortBlockStates;
/* 21 */   private class_2680 lastShortChecked = null;
/*    */   
/*    */   private boolean lastShortCheckedResult = false;
/*    */   private PlaceholderBlockGetter placeholderBlockGetter;
/*    */   private CompletableFuture<Boolean> ioThreadWaitingFor;
/*    */   private Supplier<Boolean> ioThreadWaitingForSupplier;
/*    */   
/*    */   public BlockStateShortShapeCache() {
/* 29 */     this.shortBlockStates = new HashMap<>();
/* 30 */     this.placeholderBlockGetter = new PlaceholderBlockGetter();
/*    */   }
/*    */   public boolean isShort(class_2680 state) {
/*    */     Boolean cached;
/* 34 */     if (state == null || state.method_26204() instanceof net.minecraft.class_2189 || state.method_26204() instanceof net.minecraft.class_2404) {
/* 35 */       return false;
/*    */     }
/* 37 */     synchronized (this.shortBlockStates) {
/* 38 */       if (state == this.lastShortChecked)
/* 39 */         return this.lastShortCheckedResult; 
/* 40 */       cached = this.shortBlockStates.get(state);
/*    */     } 
/* 42 */     if (cached == null) {
/* 43 */       if (!class_310.method_1551().method_18854()) {
/* 44 */         Supplier<Boolean> supplier = () -> Boolean.valueOf(isShort(state));
/* 45 */         CompletableFuture<Boolean> future = class_310.method_1551().method_5385(supplier);
/*    */         
/* 47 */         boolean isIOThread = (Thread.currentThread() == WorldMap.mapRunnerThread);
/* 48 */         if (isIOThread) {
/* 49 */           this.ioThreadWaitingForSupplier = supplier;
/* 50 */           this.ioThreadWaitingFor = future;
/*    */         } 
/*    */         
/* 53 */         Boolean result = future.join();
/*    */         
/* 55 */         if (isIOThread) {
/* 56 */           this.ioThreadWaitingForSupplier = null;
/* 57 */           this.ioThreadWaitingFor = null;
/*    */         } 
/*    */         
/* 60 */         return result.booleanValue();
/*    */       } 
/*    */       
/*    */       try {
/* 64 */         this.placeholderBlockGetter.setPlaceholderState(state);
/* 65 */         class_265 shape = state.method_26218((class_1922)this.placeholderBlockGetter, class_2338.field_10980);
/* 66 */         cached = Boolean.valueOf((shape.method_1105(class_2350.class_2351.field_11052) < 0.25D));
/* 67 */       } catch (Throwable t) {
/* 68 */         WorldMap.LOGGER.info("Defaulting world-dependent block state shape to not short: " + String.valueOf(state));
/* 69 */         cached = Boolean.valueOf(false);
/*    */       } 
/* 71 */       synchronized (this.shortBlockStates) {
/* 72 */         this.shortBlockStates.put(state, cached);
/* 73 */         this.lastShortChecked = state;
/* 74 */         this.lastShortCheckedResult = cached.booleanValue();
/*    */       } 
/*    */     } 
/* 77 */     return cached.booleanValue();
/*    */   }
/*    */   
/*    */   public void reset() {
/* 81 */     synchronized (this.shortBlockStates) {
/* 82 */       this.shortBlockStates.clear();
/* 83 */       this.lastShortChecked = null;
/* 84 */       this.lastShortCheckedResult = false;
/*    */     } 
/*    */   }
/*    */   
/*    */   public void supplyForIOThread() {
/* 89 */     if (!class_310.method_1551().method_18854())
/* 90 */       throw new RuntimeException(new IllegalAccessException("Supplying from the wrong thread!")); 
/* 91 */     CompletableFuture<Boolean> waitingFor = this.ioThreadWaitingFor;
/* 92 */     if (waitingFor != null && !waitingFor.isDone())
/*    */     {
/* 94 */       waitingFor.complete(this.ioThreadWaitingForSupplier.get());
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\cache\BlockStateShortShapeCache.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */