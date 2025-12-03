/*    */ package xaero.map.cache;
/*    */ 
/*    */ import java.util.Set;
/*    */ import net.minecraft.class_2680;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BrokenBlockTintCache
/*    */ {
/*    */   private final Set<class_2680> brokenStates;
/*    */   
/*    */   public BrokenBlockTintCache(Set<class_2680> brokenStates) {
/* 13 */     this.brokenStates = brokenStates;
/*    */   }
/*    */   
/*    */   public void setBroken(class_2680 state) {
/* 17 */     this.brokenStates.add(state);
/*    */   }
/*    */   
/*    */   public boolean isBroken(class_2680 state) {
/* 21 */     return this.brokenStates.contains(state);
/*    */   }
/*    */   
/*    */   public int getSize() {
/* 25 */     return this.brokenStates.size();
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\cache\BrokenBlockTintCache.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */