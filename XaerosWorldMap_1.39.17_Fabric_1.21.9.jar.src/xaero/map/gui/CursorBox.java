/*     */ package xaero.map.gui;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_2583;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_5348;
/*     */ import xaero.map.misc.TextSplitter;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CursorBox
/*     */ {
/*     */   private static final int BOX_OFFSET_X = 12;
/*     */   private static final int BOX_OFFSET_Y = 10;
/*     */   private static final int START_WIDTH = 20;
/*     */   private static final int USUAL_WIDTH = 200;
/*     */   private ArrayList<class_2561> strings;
/*     */   private class_2561 directText;
/*     */   private boolean directTextReady;
/*     */   private String language;
/*     */   private String fullCode;
/*     */   private class_2583 codeStyle;
/*     */   private String plainText;
/*  26 */   private int boxWidth = 20;
/*  27 */   private int startWidth = 20;
/*     */   private static final int color = -939524096;
/*     */   private boolean customLines;
/*     */   private boolean flippedByDefault;
/*     */   private boolean autoLinebreak;
/*     */   
/*     */   public CursorBox(String code) {
/*  34 */     this(code, class_2583.field_24360);
/*     */   }
/*     */   
/*     */   public CursorBox(String code, class_2583 codeStyle) {
/*  38 */     this(code, codeStyle, false);
/*     */   }
/*     */   
/*     */   public CursorBox(String code, class_2583 codeStyle, boolean flippedByDefault) {
/*  42 */     this.fullCode = code;
/*  43 */     this.codeStyle = codeStyle;
/*     */ 
/*     */     
/*  46 */     this.flippedByDefault = flippedByDefault;
/*  47 */     this.autoLinebreak = true;
/*     */   }
/*     */   
/*     */   public CursorBox(class_2561 directText) {
/*  51 */     this(directText, false);
/*     */   }
/*     */   
/*     */   public CursorBox(class_2561 directText, boolean flippedByDefault) {
/*  55 */     this.directText = directText;
/*  56 */     this.flippedByDefault = flippedByDefault;
/*  57 */     this.autoLinebreak = true;
/*     */   }
/*     */   
/*     */   public CursorBox(int size) {
/*  61 */     this.strings = new ArrayList<>();
/*  62 */     for (int i = 0; i < size; i++)
/*  63 */       this.strings.add(class_2561.method_43470("")); 
/*  64 */     this.customLines = true;
/*     */   }
/*     */   
/*     */   public void setStartWidth(int startWidth) {
/*  68 */     this.startWidth = startWidth;
/*     */   }
/*     */   
/*     */   private String currentLanguage() {
/*  72 */     return class_310.method_1551().method_1526().method_4669();
/*     */   }
/*     */   
/*     */   public void createLines(class_2561 text) {
/*     */     try {
/*  77 */       this.language = currentLanguage();
/*  78 */     } catch (NullPointerException e) {
/*  79 */       this.language = "en_us";
/*     */     } 
/*  81 */     this.strings = new ArrayList<>();
/*  82 */     splitWords(this.strings, (class_5348)text);
/*     */   }
/*     */   
/*     */   public void splitWords(ArrayList<class_2561> dest, class_5348 formattedText) {
/*  86 */     StringBuilder plainTextBuilder = new StringBuilder();
/*  87 */     this.boxWidth = 20 + TextSplitter.splitTextIntoLines(dest, this.startWidth - 20, (this.autoLinebreak ? 200 : Integer.MAX_VALUE) - 20, formattedText, plainTextBuilder);
/*  88 */     this.plainText = plainTextBuilder.toString().replaceAll("(§[0-9a-gr])+", "");
/*     */   }
/*     */   
/*     */   public class_2561 getLine(int line) {
/*  92 */     return this.strings.get(line);
/*     */   }
/*     */   
/*     */   private void ensure() {
/*     */     try {
/*  97 */       if (!this.customLines && ((this.fullCode == null && !this.directTextReady) || this.language == null || !this.language.equals(currentLanguage()))) {
/*  98 */         if (this.fullCode != null) {
/*  99 */           createLines((class_2561)class_2561.method_43471(this.fullCode).method_27696(this.codeStyle));
/*     */         } else {
/* 101 */           createLines(this.directText);
/* 102 */           this.directTextReady = true;
/*     */         } 
/*     */       }
/* 105 */     } catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawBox(class_332 guiGraphics, int x, int y, int width, int height) {
/* 110 */     ensure();
/*     */     
/* 112 */     int drawX = x + 12;
/* 113 */     int drawY = y + 10;
/* 114 */     int overEdgeX = drawX + this.boxWidth - width;
/* 115 */     if (this.flippedByDefault || overEdgeX > 9) {
/* 116 */       drawX = x - 12 - this.boxWidth;
/* 117 */     } else if (overEdgeX > 0) {
/* 118 */       drawX -= overEdgeX;
/* 119 */     }  if (drawX < 0) {
/* 120 */       drawX = 0;
/*     */     }
/* 122 */     int h = 5 + this.strings.size() * 10 + 5;
/* 123 */     int overEdgeY = drawY + h - height;
/* 124 */     if (overEdgeY > h / 2) {
/* 125 */       drawY = y - 10 - h;
/* 126 */     } else if (overEdgeY > 0) {
/* 127 */       drawY -= overEdgeY;
/*     */     } 
/* 129 */     if (drawY < 0) {
/* 130 */       drawY = 0;
/*     */     }
/* 132 */     guiGraphics.method_25294(drawX, drawY, drawX + this.boxWidth, drawY + h, -939524096);
/* 133 */     for (int i = 0; i < this.strings.size(); i++) {
/* 134 */       class_2561 s = getLine(i);
/* 135 */       guiGraphics.method_27535((class_310.method_1551()).field_1772, s, drawX + 10, drawY + 6 + 10 * i, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public CursorBox withWidth(int boxWidth) {
/* 141 */     this.boxWidth = boxWidth;
/* 142 */     return this;
/*     */   }
/*     */   
/*     */   public void setAutoLinebreak(boolean autoLinebreak) {
/* 146 */     this.autoLinebreak = autoLinebreak;
/*     */   }
/*     */   
/*     */   public String getPlainText() {
/* 150 */     ensure();
/* 151 */     return this.plainText;
/*     */   }
/*     */   
/*     */   public String getFullCode() {
/* 155 */     return this.fullCode;
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\gui\CursorBox.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */