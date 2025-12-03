/*     */ package xaero.map.graphics;
/*     */ 
/*     */ import com.mojang.blaze3d.textures.GpuTexture;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_287;
/*     */ import net.minecraft.class_327;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_4587;
/*     */ import net.minecraft.class_4588;
/*     */ import net.minecraft.class_5348;
/*     */ import net.minecraft.class_9848;
/*     */ import org.joml.Matrix4f;
/*     */ import xaero.map.graphics.renderer.multitexture.MultiTextureRenderTypeRenderer;
/*     */ 
/*     */ public class MapRenderHelper {
/*     */   public static void renderBranchUpdate(GpuTexture texture, float x, float y, float width, float height, int textureX, int textureY, float textureW, float textureH, float fullTextureWidth, float fullTextureHeight, MultiTextureRenderTypeRenderer renderer) {
/*  17 */     class_287 vertexBuffer = renderer.begin(texture);
/*  18 */     float normalizedTextureX = textureX / fullTextureWidth;
/*  19 */     float normalizedTextureY = textureY / fullTextureHeight;
/*  20 */     float normalizedTextureX2 = (textureX + textureW) / fullTextureWidth;
/*  21 */     float normalizedTextureY2 = (textureY + textureH) / fullTextureHeight;
/*  22 */     vertexBuffer.method_22912(x + 0.0F, y + height, 0.0F)
/*  23 */       .method_22913(normalizedTextureX, normalizedTextureY2);
/*  24 */     vertexBuffer.method_22912(x + width, y + height, 0.0F)
/*  25 */       .method_22913(normalizedTextureX2, normalizedTextureY2);
/*  26 */     vertexBuffer.method_22912(x + width, y + 0.0F, 0.0F)
/*  27 */       .method_22913(normalizedTextureX2, normalizedTextureY);
/*  28 */     vertexBuffer.method_22912(x + 0.0F, y + 0.0F, 0.0F)
/*  29 */       .method_22913(normalizedTextureX, normalizedTextureY);
/*     */   }
/*     */   
/*     */   public static void fillIntoExistingBuffer(Matrix4f matrix, class_4588 bufferBuilder, int x1, int y1, int x2, int y2, float r, float g, float b, float a) {
/*  33 */     bufferBuilder.method_22918(matrix, x1, y2, 0.0F).method_22915(r, g, b, a);
/*  34 */     bufferBuilder.method_22918(matrix, x2, y2, 0.0F).method_22915(r, g, b, a);
/*  35 */     bufferBuilder.method_22918(matrix, x2, y1, 0.0F).method_22915(r, g, b, a);
/*  36 */     bufferBuilder.method_22918(matrix, x1, y1, 0.0F).method_22915(r, g, b, a);
/*     */   }
/*     */   
/*     */   public static void blitIntoExistingBuffer(Matrix4f matrix, class_4588 bufferBuilder, float x, float y, int u, int v, int width, int height, int tW, int tH, float r, float g, float b, float a, int textureWidth, int textureHeight) {
/*  40 */     float factorX = 1.0F / textureWidth;
/*  41 */     float factorY = 1.0F / textureHeight;
/*  42 */     float textureX1 = u * factorX;
/*  43 */     float textureX2 = (u + tW) * factorX;
/*  44 */     float textureY1 = v * factorY;
/*  45 */     float textureY2 = (v + tH) * factorY;
/*  46 */     bufferBuilder.method_22918(matrix, x, y + height, 0.0F).method_22915(r, g, b, a).method_22913(textureX1, textureY2);
/*  47 */     bufferBuilder.method_22918(matrix, x + width, y + height, 0.0F).method_22915(r, g, b, a).method_22913(textureX2, textureY2);
/*  48 */     bufferBuilder.method_22918(matrix, x + width, y, 0.0F).method_22915(r, g, b, a).method_22913(textureX2, textureY1);
/*  49 */     bufferBuilder.method_22918(matrix, x, y, 0.0F).method_22915(r, g, b, a).method_22913(textureX1, textureY1);
/*     */   }
/*     */   
/*     */   public static void blitIntoExistingBuffer(Matrix4f matrix, class_4588 bufferBuilder, int x, int y, int u, int v, int width, int height, float r, float g, float b, float a) {
/*  53 */     blitIntoExistingBuffer(matrix, bufferBuilder, x, y, u, v, width, height, width, height, r, g, b, a, 256, 256);
/*     */   }
/*     */   
/*     */   public static void blitIntoMultiTextureRenderer(Matrix4f matrix, MultiTextureRenderTypeRenderer renderer, float x, float y, int u, int v, int width, int height, int tW, int tH, float r, float g, float b, float a, int textureWidth, int textureHeight, GpuTexture texture) {
/*  57 */     class_287 class_287 = renderer.begin(texture);
/*  58 */     blitIntoExistingBuffer(matrix, (class_4588)class_287, x, y, u, v, width, height, tW, tH, r, g, b, a, textureWidth, textureHeight);
/*     */   }
/*     */   
/*     */   public static void blitIntoMultiTextureRenderer(Matrix4f matrix, MultiTextureRenderTypeRenderer renderer, float x, float y, int u, int v, int width, int height, float r, float g, float b, float a, int textureWidth, int textureHeight, GpuTexture texture) {
/*  62 */     blitIntoMultiTextureRenderer(matrix, renderer, x, y, u, v, width, height, width, height, r, g, b, a, textureWidth, textureHeight, texture);
/*     */   }
/*     */   
/*     */   public static void blitIntoMultiTextureRenderer(Matrix4f matrix, MultiTextureRenderTypeRenderer renderer, float x, float y, int u, int v, int width, int height, float r, float g, float b, float a, GpuTexture texture) {
/*  66 */     blitIntoMultiTextureRenderer(matrix, renderer, x, y, u, v, width, height, r, g, b, a, 256, 256, texture);
/*     */   }
/*     */   
/*     */   public static void renderDynamicHighlight(class_4587 matrixStack, class_4588 overlayBuffer, int flooredCameraX, int flooredCameraZ, int leftX, int rightX, int topZ, int bottomZ, float sideR, float sideG, float sideB, float sideA, float centerR, float centerG, float centerB, float centerA) {
/*  70 */     fillIntoExistingBuffer(matrixStack.method_23760().method_23761(), overlayBuffer, leftX - 1 - flooredCameraX, topZ - 1 - flooredCameraZ, leftX - flooredCameraX, bottomZ + 1 - flooredCameraZ, sideR, sideG, sideB, sideA);
/*  71 */     fillIntoExistingBuffer(matrixStack.method_23760().method_23761(), overlayBuffer, leftX - flooredCameraX, topZ - 1 - flooredCameraZ, rightX - flooredCameraX, topZ - flooredCameraZ, sideR, sideG, sideB, sideA);
/*  72 */     fillIntoExistingBuffer(matrixStack.method_23760().method_23761(), overlayBuffer, rightX - flooredCameraX, topZ - 1 - flooredCameraZ, rightX + 1 - flooredCameraX, bottomZ + 1 - flooredCameraZ, sideR, sideG, sideB, sideA);
/*  73 */     fillIntoExistingBuffer(matrixStack.method_23760().method_23761(), overlayBuffer, leftX - flooredCameraX, bottomZ - flooredCameraZ, rightX - flooredCameraX, bottomZ + 1 - flooredCameraZ, sideR, sideG, sideB, sideA);
/*     */     
/*  75 */     fillIntoExistingBuffer(matrixStack.method_23760().method_23761(), overlayBuffer, leftX - flooredCameraX, topZ - flooredCameraZ, rightX - flooredCameraX, bottomZ - flooredCameraZ, centerR, centerG, centerB, centerA);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void drawCenteredStringWithBackground(class_332 guiGraphics, class_327 font, String string, int x, int y, int color, float bgRed, float bgGreen, float bgBlue, float bgAlpha) {
/*  80 */     int stringWidth = font.method_1727(string);
/*  81 */     drawStringWithBackground(guiGraphics, font, string, x - stringWidth / 2, y, color, bgRed, bgGreen, bgBlue, bgAlpha);
/*     */   }
/*     */   
/*     */   public static void drawStringWithBackground(class_332 guiGraphics, class_327 font, String string, int x, int y, int color, float bgRed, float bgGreen, float bgBlue, float bgAlpha) {
/*  85 */     int stringWidth = font.method_1727(string);
/*  86 */     int bgColor = class_9848.method_61318(bgAlpha, bgRed, bgGreen, bgBlue);
/*  87 */     guiGraphics.method_25294(x - 1, y - 1, x + stringWidth + 1, y + 9, bgColor);
/*  88 */     guiGraphics.method_25303(font, string, x, y, color);
/*     */   }
/*     */   
/*     */   public static void drawCenteredStringWithBackground(class_332 guiGraphics, class_327 font, class_2561 text, int x, int y, int color, float bgRed, float bgGreen, float bgBlue, float bgAlpha) {
/*  92 */     int stringWidth = font.method_27525((class_5348)text);
/*  93 */     drawStringWithBackground(guiGraphics, font, text, x - stringWidth / 2, y, color, bgRed, bgGreen, bgBlue, bgAlpha);
/*     */   }
/*     */   
/*     */   public static void drawStringWithBackground(class_332 guiGraphics, class_327 font, class_2561 text, int x, int y, int color, float bgRed, float bgGreen, float bgBlue, float bgAlpha) {
/*  97 */     int stringWidth = font.method_27525((class_5348)text);
/*  98 */     int bgColor = class_9848.method_61318(bgAlpha, bgRed, bgGreen, bgBlue);
/*  99 */     guiGraphics.method_25294(x - 1, y - 1, x + stringWidth + 1, y + 9, bgColor);
/* 100 */     guiGraphics.method_27535(font, text, x, y, color);
/*     */   }
/*     */   
/*     */   public static void blitIntoExistingBuffer(Matrix4f matrix, class_4588 bufferBuilder, float x, float y, int u, int v, int width, int height, int textureWidth, int textureHeight) {
/* 104 */     float factorX = 1.0F / textureWidth;
/* 105 */     float factorY = 1.0F / textureHeight;
/* 106 */     float textureX1 = u * factorX;
/* 107 */     float textureX2 = (u + width) * factorX;
/* 108 */     float textureY1 = v * factorY;
/* 109 */     float textureY2 = (v + height) * factorY;
/* 110 */     bufferBuilder.method_22918(matrix, x, y + height, 0.0F).method_22913(textureX1, textureY2);
/* 111 */     bufferBuilder.method_22918(matrix, x + width, y + height, 0.0F).method_22913(textureX2, textureY2);
/* 112 */     bufferBuilder.method_22918(matrix, x + width, y, 0.0F).method_22913(textureX2, textureY1);
/* 113 */     bufferBuilder.method_22918(matrix, x, y, 0.0F).method_22913(textureX1, textureY1);
/*     */   }
/*     */   
/*     */   public static void blitIntoExistingBuffer(Matrix4f matrix, class_4588 bufferBuilder, int x, int y, int u, int v, int width, int height) {
/* 117 */     blitIntoExistingBuffer(matrix, bufferBuilder, x, y, u, v, width, height, 256, 256);
/*     */   }
/*     */   
/*     */   public static void blitIntoMultiTextureRenderer(Matrix4f matrix, MultiTextureRenderTypeRenderer renderer, int x, int y, int u, int v, int width, int height, int textureWidth, int textureHeight, GpuTexture texture) {
/* 121 */     class_287 class_287 = renderer.begin(texture);
/* 122 */     blitIntoExistingBuffer(matrix, (class_4588)class_287, x, y, u, v, width, height, textureWidth, textureHeight);
/*     */   }
/*     */   
/*     */   public static void blitIntoMultiTextureRenderer(Matrix4f matrix, MultiTextureRenderTypeRenderer renderer, int x, int y, int u, int v, int width, int height, GpuTexture texture) {
/* 126 */     blitIntoMultiTextureRenderer(matrix, renderer, x, y, u, v, width, height, 256, 256, texture);
/*     */   }
/*     */   
/*     */   public static void restoreDefaultShaderBlendState() {}
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\graphics\MapRenderHelper.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */