/*    */ package xaero.map.gui.message.render;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import net.minecraft.class_327;
/*    */ import net.minecraft.class_332;
/*    */ import net.minecraft.class_5348;
/*    */ import org.joml.Matrix3x2fStack;
/*    */ import xaero.map.gui.message.Message;
/*    */ import xaero.map.gui.message.MessageBox;
/*    */ 
/*    */ public class MessageBoxRenderer
/*    */ {
/* 13 */   private final int OPAQUE_FOR = 5000;
/* 14 */   private final int FADE_FOR = 3000;
/*    */   
/*    */   public void render(class_332 guiGraphics, MessageBox messageBox, class_327 font, int x, int y, boolean rightAlign) {
/* 17 */     Matrix3x2fStack matrixStack = guiGraphics.method_51448();
/* 18 */     long time = System.currentTimeMillis();
/* 19 */     matrixStack.pushMatrix();
/* 20 */     matrixStack.translate(x, y);
/* 21 */     int index = 0;
/* 22 */     Iterator<Message> iterator = messageBox.getIterator();
/* 23 */     while (iterator.hasNext()) {
/* 24 */       Message message = iterator.next();
/* 25 */       int passed = (int)(time - message.getAdditionTime());
/* 26 */       float opacity = (passed < 5000) ? 1.0F : ((3000 - passed - 5000) / 3000.0F);
/* 27 */       int alphaInt = (int)(opacity * 255.0F);
/* 28 */       if (alphaInt <= 3)
/*    */         break; 
/* 30 */       int textColor = 0xFFFFFF | alphaInt << 24;
/* 31 */       int bgColor = (int)(0.5F * alphaInt) << 24;
/* 32 */       int textWidth = font.method_27525((class_5348)message.getText());
/* 33 */       int textX = rightAlign ? (-textWidth - 1) : 2;
/* 34 */       int textY = -index * 10 - 4;
/* 35 */       int bgWidth = textWidth + 3;
/* 36 */       guiGraphics.method_25294(textX - 2, textY - 1, textX - 2 + bgWidth, textY + 9, bgColor);
/* 37 */       guiGraphics.method_27535(font, message.getText(), textX, textY, textColor);
/* 38 */       index++;
/*    */     } 
/* 40 */     matrixStack.popMatrix();
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\gui\message\render\MessageBoxRenderer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */