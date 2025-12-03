/*    */ package xaero.map.util.linked;
/*    */ 
/*    */ import com.google.common.collect.Streams;
/*    */ import java.util.Iterator;
/*    */ import java.util.stream.Stream;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class LinkedChain<V extends ILinkedChainNode<V>>
/*    */   implements Iterable<V>
/*    */ {
/*    */   private boolean destroyed;
/*    */   private V head;
/*    */   
/*    */   public void add(V element) {
/* 16 */     if (this.destroyed)
/* 17 */       throw new RuntimeException(new IllegalAccessException("Trying to use a destroyed chain!")); 
/* 18 */     if (element.isDestroyed())
/* 19 */       throw new IllegalArgumentException("Trying to reintroduce a removed chain element!"); 
/* 20 */     if (this.head != null) {
/* 21 */       element.setNext(this.head);
/* 22 */       this.head.setPrevious(element);
/*    */     } 
/* 24 */     this.head = element;
/*    */   }
/*    */   
/*    */   public void remove(V element) {
/* 28 */     if (this.destroyed)
/* 29 */       throw new RuntimeException(new IllegalAccessException("Trying to use a cleared chain!")); 
/* 30 */     if (element.isDestroyed())
/*    */       return; 
/* 32 */     V prev = element.getPrevious();
/* 33 */     V next = element.getNext();
/* 34 */     if (prev != null)
/* 35 */       prev.setNext(next); 
/* 36 */     if (next != null)
/* 37 */       next.setPrevious(prev); 
/* 38 */     if (element == this.head)
/* 39 */       this.head = next; 
/* 40 */     element.onDestroyed();
/*    */   }
/*    */   
/*    */   public void destroy() {
/* 44 */     this.head = null;
/* 45 */     this.destroyed = true;
/*    */   }
/*    */   
/*    */   public void reset() {
/* 49 */     this.head = null;
/* 50 */     this.destroyed = false;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Iterator<V> iterator() {
/* 56 */     return new Iterator<V>() {
/* 57 */         private V next = LinkedChain.this.head;
/*    */         
/*    */         private V reachValidNext() {
/* 60 */           if (LinkedChain.this.destroyed) {
/* 61 */             this.next = null;
/* 62 */             return null;
/*    */           } 
/* 64 */           while (this.next != null && this.next.isDestroyed())
/* 65 */             this.next = this.next.getNext(); 
/* 66 */           return this.next;
/*    */         }
/*    */ 
/*    */         
/*    */         public boolean hasNext() {
/* 71 */           return (reachValidNext() != null);
/*    */         }
/*    */ 
/*    */         
/*    */         @Nullable
/*    */         public V next() {
/* 77 */           V result = reachValidNext();
/* 78 */           if (result != null)
/* 79 */             this.next = result.getNext(); 
/* 80 */           return result;
/*    */         }
/*    */       };
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public Stream<V> stream() {
/* 87 */     return Streams.stream(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\ma\\util\linked\LinkedChain.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */