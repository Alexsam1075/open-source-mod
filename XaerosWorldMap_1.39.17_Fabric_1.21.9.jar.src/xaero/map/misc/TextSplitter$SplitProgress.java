/*     */ package xaero.map.misc;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_2583;
/*     */ import net.minecraft.class_5250;
/*     */ import net.minecraft.class_8828;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SplitProgress
/*     */ {
/*     */   int multiwordWidth;
/*  74 */   List<class_5250> multiword = null;
/*     */   boolean firstWord = true;
/*  76 */   class_2561 line = null;
/*  77 */   StringBuilder stringBuilder = new StringBuilder();
/*     */   int lineWidth;
/*     */   class_2583 lastStyle;
/*     */   int resultWidth;
/*     */   
/*     */   public void buildMultiword(String wordPart, int width, class_2583 style) {
/*  83 */     class_5250 wordPartComponent = class_2561.method_43470(wordPart).method_27696(style);
/*  84 */     if (this.multiword == null)
/*  85 */       this.multiword = new ArrayList<>(); 
/*  86 */     this.multiword.add(wordPartComponent);
/*  87 */     this.multiwordWidth += width;
/*     */   }
/*     */   
/*     */   private void confirmWordPart(String part, class_2583 style) {
/*  91 */     if (this.lastStyle != null && !Objects.equals(style, this.lastStyle))
/*  92 */       confirmComponent(); 
/*  93 */     this.stringBuilder.append(part);
/*  94 */     this.lastStyle = style;
/*     */   }
/*     */   
/*     */   public void confirmWord(String lastPart, class_2583 lastPartStyle, int width) {
/*  98 */     if (!this.firstWord)
/*  99 */       this.stringBuilder.append(" "); 
/* 100 */     if (this.multiword != null) {
/* 101 */       for (class_2561 component : this.multiword) {
/* 102 */         String text = ((class_8828)component.method_10851()).comp_737();
/* 103 */         class_2583 style = component.method_10866();
/* 104 */         confirmWordPart(text, style);
/*     */       } 
/* 106 */       this.multiword = null;
/* 107 */       this.multiwordWidth = 0;
/*     */     } 
/* 109 */     confirmWordPart(lastPart, lastPartStyle);
/* 110 */     this.lineWidth += width;
/* 111 */     this.firstWord = false;
/*     */   }
/*     */   
/*     */   public void confirmComponent() {
/* 115 */     class_5250 class_5250 = class_2561.method_43470(this.stringBuilder.toString()).method_27696((this.lastStyle == null) ? class_2583.field_24360 : this.lastStyle);
/* 116 */     if (this.line != null) {
/* 117 */       if (this.stringBuilder.length() > 0)
/* 118 */         this.line.method_10855().add(class_5250); 
/*     */     } else {
/* 120 */       this.line = (class_2561)class_5250;
/* 121 */     }  this.stringBuilder.delete(0, this.stringBuilder.length());
/*     */   }
/*     */   
/*     */   public void nextLine() {
/* 125 */     this.firstWord = true;
/* 126 */     this.line = null;
/* 127 */     this.lastStyle = null;
/* 128 */     this.lineWidth = 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\misc\TextSplitter$SplitProgress.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */