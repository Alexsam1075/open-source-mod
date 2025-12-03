/*    */ package xaero.map.gui.message;
/*    */ 
/*    */ import net.minecraft.class_2561;
/*    */ 
/*    */ 
/*    */ public class Message
/*    */ {
/*    */   private final class_2561 text;
/*    */   private final long additionTime;
/*    */   
/*    */   public Message(class_2561 text, long additionTime) {
/* 12 */     this.text = text;
/* 13 */     this.additionTime = additionTime;
/*    */   }
/*    */   
/*    */   public class_2561 getText() {
/* 17 */     return this.text;
/*    */   }
/*    */   
/*    */   public long getAdditionTime() {
/* 21 */     return this.additionTime;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\gui\message\Message.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */