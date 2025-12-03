/*    */ package xaero.map.gui.message;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import net.minecraft.class_2561;
/*    */ import net.minecraft.class_5250;
/*    */ import net.minecraft.class_5348;
/*    */ import xaero.map.misc.TextSplitter;
/*    */ 
/*    */ public class MessageBox
/*    */ {
/*    */   private final List<Message> messages;
/*    */   private final int width;
/*    */   private final int capacity;
/*    */   
/*    */   private MessageBox(List<Message> messages, int width, int capacity) {
/* 18 */     this.messages = messages;
/* 19 */     this.width = width;
/* 20 */     this.capacity = capacity;
/*    */   }
/*    */   
/*    */   private void addMessageLine(class_2561 text) {
/* 24 */     Message msg = new Message(text, System.currentTimeMillis());
/* 25 */     this.messages.add(0, msg);
/* 26 */     if (this.messages.size() > this.capacity)
/* 27 */       this.messages.remove(this.messages.size() - 1); 
/*    */   }
/*    */   
/*    */   public void addMessage(class_2561 text) {
/* 31 */     List<class_2561> splitDest = new ArrayList<>();
/* 32 */     TextSplitter.splitTextIntoLines(splitDest, this.width, this.width, (class_5348)text, null);
/* 33 */     for (class_2561 line : splitDest)
/* 34 */       addMessageLine(line); 
/*    */   }
/*    */   
/*    */   public void addMessageWithSource(class_2561 source, class_2561 text) {
/* 38 */     class_5250 class_5250 = class_2561.method_43470("<");
/* 39 */     class_5250.method_10855().add(source);
/* 40 */     class_5250.method_10855().add(class_2561.method_43470("> "));
/* 41 */     class_5250.method_10855().add(text);
/*    */     
/* 43 */     addMessage((class_2561)class_5250);
/*    */   }
/*    */   
/*    */   public int getCapacity() {
/* 47 */     return this.capacity;
/*    */   }
/*    */   
/*    */   public Iterator<Message> getIterator() {
/* 51 */     return this.messages.iterator();
/*    */   }
/*    */ 
/*    */   
/*    */   public static class Builder
/*    */   {
/*    */     private int width;
/*    */     
/*    */     private int capacity;
/*    */     
/*    */     public Builder setDefault() {
/* 62 */       setWidth(250);
/* 63 */       setCapacity(5);
/* 64 */       return this;
/*    */     }
/*    */     
/*    */     public Builder setWidth(int width) {
/* 68 */       this.width = width;
/* 69 */       return this;
/*    */     }
/*    */     
/*    */     public Builder setCapacity(int capacity) {
/* 73 */       this.capacity = capacity;
/* 74 */       return this;
/*    */     }
/*    */     
/*    */     public MessageBox build() {
/* 78 */       return new MessageBox(new ArrayList<>(this.capacity), this.width, this.capacity);
/*    */     }
/*    */     
/*    */     public static Builder begin() {
/* 82 */       return (new Builder()).setDefault();
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\gui\message\MessageBox.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */