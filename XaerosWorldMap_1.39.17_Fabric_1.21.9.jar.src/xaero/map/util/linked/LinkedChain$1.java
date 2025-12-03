/*    */ package xaero.map.util.linked;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class null
/*    */   implements Iterator<V>
/*    */ {
/* 57 */   private V next = LinkedChain.this.head;
/*    */   
/*    */   private V reachValidNext() {
/* 60 */     if (LinkedChain.this.destroyed) {
/* 61 */       this.next = null;
/* 62 */       return null;
/*    */     } 
/* 64 */     while (this.next != null && this.next.isDestroyed())
/* 65 */       this.next = (V)this.next.getNext(); 
/* 66 */     return this.next;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasNext() {
/* 71 */     return (reachValidNext() != null);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public V next() {
/* 77 */     V result = reachValidNext();
/* 78 */     if (result != null)
/* 79 */       this.next = (V)result.getNext(); 
/* 80 */     return result;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\ma\\util\linked\LinkedChain$1.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */