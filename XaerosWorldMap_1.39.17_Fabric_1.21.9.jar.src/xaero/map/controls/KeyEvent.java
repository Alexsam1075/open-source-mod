/*    */ package xaero.map.controls;
/*    */ 
/*    */ import net.minecraft.class_304;
/*    */ 
/*    */ public class KeyEvent {
/*    */   private class_304 kb;
/*    */   private boolean tickEnd;
/*    */   private boolean isRepeat;
/*    */   private boolean keyDown;
/*    */   
/*    */   public KeyEvent(class_304 kb, boolean tickEnd, boolean isRepeat, boolean keyDown) {
/* 12 */     this.kb = kb;
/* 13 */     this.tickEnd = tickEnd;
/* 14 */     this.isRepeat = isRepeat;
/* 15 */     this.keyDown = keyDown;
/*    */   }
/*    */   
/*    */   public class_304 getKb() {
/* 19 */     return this.kb;
/*    */   }
/*    */   
/*    */   public boolean isTickEnd() {
/* 23 */     return this.tickEnd;
/*    */   }
/*    */   
/*    */   public boolean isRepeat() {
/* 27 */     return this.isRepeat;
/*    */   }
/*    */   
/*    */   public boolean isKeyDown() {
/* 31 */     return this.keyDown;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\controls\KeyEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */