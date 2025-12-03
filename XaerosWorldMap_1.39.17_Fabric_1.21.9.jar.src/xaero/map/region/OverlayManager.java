/*    */ package xaero.map.region;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import net.minecraft.class_2680;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OverlayManager
/*    */ {
/*    */   private HashMap<class_2680, HashMap<Byte, HashMap<Short, Overlay>>> overlayMap;
/* 11 */   private int numberOfUniques = 0;
/*    */   private Object[] keyHolder;
/*    */   
/*    */   public OverlayManager() {
/* 15 */     this.overlayMap = new HashMap<>();
/* 16 */     this.keyHolder = new Object[5];
/*    */   }
/*    */   
/*    */   public synchronized Overlay getOriginal(Overlay o) {
/* 20 */     o.fillManagerKeyHolder(this.keyHolder);
/* 21 */     return getOriginal(this.overlayMap, o, 0);
/*    */   }
/*    */ 
/*    */   
/*    */   private Overlay getOriginal(HashMap<Object, Overlay> map, Overlay o, int index) {
/* 26 */     Object<Object, Object> byKey = (Object<Object, Object>)map.get(this.keyHolder[index]);
/* 27 */     if (index == this.keyHolder.length - 1) {
/* 28 */       if (byKey == null) {
/* 29 */         this.numberOfUniques++;
/*    */         
/* 31 */         map.put(this.keyHolder[index], o);
/* 32 */         return o;
/*    */       } 
/* 34 */       return (Overlay)byKey;
/*    */     } 
/* 36 */     if (byKey == null) {
/* 37 */       byKey = (Object<Object, Object>)new HashMap<>();
/* 38 */       map.put(this.keyHolder[index], byKey);
/*    */     } 
/* 40 */     return getOriginal((HashMap)byKey, o, ++index);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getNumberOfUniqueOverlays() {
/* 45 */     return this.numberOfUniques;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\region\OverlayManager.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */