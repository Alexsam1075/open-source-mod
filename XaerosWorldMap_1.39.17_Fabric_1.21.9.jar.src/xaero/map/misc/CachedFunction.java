/*    */ package xaero.map.misc;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.function.Function;
/*    */ 
/*    */ public class CachedFunction<F, T>
/*    */ {
/*    */   private final HashMap<F, T> cache;
/*    */   private F prevFrom;
/*    */   private T prevTo;
/*    */   private Function<F, T> function;
/*    */   
/*    */   public CachedFunction(Function<F, T> function) {
/* 14 */     this.cache = new HashMap<>();
/* 15 */     this.function = function;
/*    */   }
/*    */   
/*    */   public T apply(F from) {
/* 19 */     if (this.prevFrom == from && from != null)
/* 20 */       return this.prevTo; 
/* 21 */     T cached = this.cache.get(from);
/* 22 */     if (cached == null) {
/* 23 */       cached = this.function.apply(from);
/* 24 */       this.cache.put(from, cached);
/*    */     } 
/* 26 */     this.prevFrom = from;
/* 27 */     this.prevTo = cached;
/* 28 */     return cached;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\misc\CachedFunction.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */