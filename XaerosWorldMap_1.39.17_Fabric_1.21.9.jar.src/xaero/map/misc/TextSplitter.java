/*     */ package xaero.map.misc;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import net.minecraft.class_124;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_2583;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_5250;
/*     */ import net.minecraft.class_5348;
/*     */ import net.minecraft.class_8828;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TextSplitter
/*     */ {
/*     */   public static int splitTextIntoLines(List<class_2561> dest, int minWidth, int widthLimit, class_5348 formattedText, StringBuilder plainTextBuilder) {
/*  20 */     SplitProgress progress = new SplitProgress();
/*  21 */     int spaceWidth = (class_310.method_1551()).field_1772.method_1727(" ");
/*  22 */     progress.resultWidth = minWidth;
/*  23 */     class_5348.class_5246<Object> consumer = (style, text) -> {
/*     */         boolean isEnd = (style == null);
/*     */         if (!isEnd && plainTextBuilder != null)
/*     */           plainTextBuilder.append(text); 
/*     */         boolean endsWithSpace = text.endsWith(" ");
/*     */         if (endsWithSpace)
/*     */           text = text + "."; 
/*     */         String[] parts = text.split(" ");
/*     */         for (int i = 0; i < parts.length; i++) {
/*  32 */           boolean canAddMultiword = (isEnd || i < parts.length - 1);
/*  33 */           String part = (isEnd || (endsWithSpace && i == parts.length - 1)) ? "" : parts[i];
/*     */           int partWidth = (class_310.method_1551()).field_1772.method_1727(part);
/*     */           if (!canAddMultiword) {
/*     */             progress.buildMultiword(part, partWidth, style);
/*     */           } else {
/*     */             int wordWidth = partWidth + progress.multiwordWidth;
/*     */             int wordTakesWidth = wordWidth + (!progress.firstWord ? spaceWidth : 0);
/*     */             if (progress.lineWidth + wordTakesWidth <= widthLimit) {
/*     */               progress.resultWidth = Math.max(progress.resultWidth, Math.min(widthLimit, progress.lineWidth + wordTakesWidth));
/*     */             }
/*     */             if (progress.firstWord && progress.lineWidth + wordTakesWidth > progress.resultWidth) {
/*     */               progress.resultWidth = progress.lineWidth + wordTakesWidth;
/*     */             }
/*  46 */             boolean isNewLine = (progress.multiword == null && part.equals("\n")); if (!isNewLine && progress.lineWidth + wordTakesWidth <= progress.resultWidth) {
/*     */               progress.confirmWord(part, style, wordTakesWidth);
/*     */             } else {
/*     */               progress.confirmComponent();
/*     */               dest.add(progress.line);
/*     */               progress.nextLine();
/*     */               if (!isNewLine)
/*     */                 i--; 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         return Optional.empty();
/*     */       };
/*  59 */     formattedText.method_27658(consumer, class_2583.field_24360.method_10977(class_124.field_1068));
/*     */     
/*  61 */     if (progress.multiword != null) {
/*  62 */       consumer.accept(null, "end");
/*  63 */     } else if (progress.stringBuilder.length() > 0) {
/*  64 */       progress.confirmComponent();
/*  65 */     }  if (progress.line != null)
/*  66 */       dest.add(progress.line); 
/*  67 */     if (progress.resultWidth > minWidth)
/*  68 */       progress.resultWidth--; 
/*  69 */     return progress.resultWidth;
/*     */   }
/*     */   
/*     */   public static class SplitProgress {
/*     */     int multiwordWidth;
/*  74 */     List<class_5250> multiword = null;
/*     */     boolean firstWord = true;
/*  76 */     class_2561 line = null;
/*  77 */     StringBuilder stringBuilder = new StringBuilder();
/*     */     int lineWidth;
/*     */     class_2583 lastStyle;
/*     */     int resultWidth;
/*     */     
/*     */     public void buildMultiword(String wordPart, int width, class_2583 style) {
/*  83 */       class_5250 wordPartComponent = class_2561.method_43470(wordPart).method_27696(style);
/*  84 */       if (this.multiword == null)
/*  85 */         this.multiword = new ArrayList<>(); 
/*  86 */       this.multiword.add(wordPartComponent);
/*  87 */       this.multiwordWidth += width;
/*     */     }
/*     */     
/*     */     private void confirmWordPart(String part, class_2583 style) {
/*  91 */       if (this.lastStyle != null && !Objects.equals(style, this.lastStyle))
/*  92 */         confirmComponent(); 
/*  93 */       this.stringBuilder.append(part);
/*  94 */       this.lastStyle = style;
/*     */     }
/*     */     
/*     */     public void confirmWord(String lastPart, class_2583 lastPartStyle, int width) {
/*  98 */       if (!this.firstWord)
/*  99 */         this.stringBuilder.append(" "); 
/* 100 */       if (this.multiword != null) {
/* 101 */         for (class_2561 component : this.multiword) {
/* 102 */           String text = ((class_8828)component.method_10851()).comp_737();
/* 103 */           class_2583 style = component.method_10866();
/* 104 */           confirmWordPart(text, style);
/*     */         } 
/* 106 */         this.multiword = null;
/* 107 */         this.multiwordWidth = 0;
/*     */       } 
/* 109 */       confirmWordPart(lastPart, lastPartStyle);
/* 110 */       this.lineWidth += width;
/* 111 */       this.firstWord = false;
/*     */     }
/*     */     
/*     */     public void confirmComponent() {
/* 115 */       class_5250 class_5250 = class_2561.method_43470(this.stringBuilder.toString()).method_27696((this.lastStyle == null) ? class_2583.field_24360 : this.lastStyle);
/* 116 */       if (this.line != null) {
/* 117 */         if (this.stringBuilder.length() > 0)
/* 118 */           this.line.method_10855().add(class_5250); 
/*     */       } else {
/* 120 */         this.line = (class_2561)class_5250;
/* 121 */       }  this.stringBuilder.delete(0, this.stringBuilder.length());
/*     */     }
/*     */     
/*     */     public void nextLine() {
/* 125 */       this.firstWord = true;
/* 126 */       this.line = null;
/* 127 */       this.lastStyle = null;
/* 128 */       this.lineWidth = 0;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\misc\TextSplitter.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */