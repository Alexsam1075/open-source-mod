/*     */ package xaero.map.element;
/*     */ 
/*     */ import com.mojang.blaze3d.pipeline.RenderPipeline;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import com.mojang.blaze3d.textures.GpuTextureView;
/*     */ import java.util.function.Supplier;
/*     */ import net.minecraft.class_1044;
/*     */ import net.minecraft.class_1058;
/*     */ import net.minecraft.class_10799;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_2960;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_327;
/*     */ import net.minecraft.class_4587;
/*     */ import net.minecraft.class_4597;
/*     */ import net.minecraft.class_5348;
/*     */ import net.minecraft.class_5481;
/*     */ import net.minecraft.class_9848;
/*     */ import xaero.map.render.util.ImmediateRenderUtil;
/*     */ 
/*     */ public class MapElementGraphics
/*     */ {
/*     */   private final class_4587 poseStack;
/*     */   private final Supplier<class_4597.class_4598> vanillaBufferSourceSupplier;
/*     */   
/*     */   public MapElementGraphics(class_4587 poseStack, Supplier<class_4597.class_4598> vanillaBufferSourceSupplier) {
/*  27 */     this.poseStack = poseStack;
/*  28 */     this.vanillaBufferSourceSupplier = vanillaBufferSourceSupplier;
/*     */   }
/*     */   
/*     */   public final void fill(int x1, int y1, int x2, int y2, int color) {
/*  32 */     fill(class_10799.field_56879, x1, y1, x2, y2, color);
/*     */   }
/*     */   
/*     */   public final void fill(RenderPipeline pipeline, int x1, int y1, int x2, int y2, int color) {
/*  36 */     if (x1 < x2) {
/*  37 */       int x1bu = x1;
/*  38 */       x1 = x2;
/*  39 */       x2 = x1bu;
/*     */     } 
/*  41 */     if (y1 < y2) {
/*  42 */       int y1bu = y1;
/*  43 */       y1 = y2;
/*  44 */       y2 = y1bu;
/*     */     } 
/*  46 */     ImmediateRenderUtil.coloredRectangle(pose().method_23760().method_23761(), x1, y1, x2, y2, color, pipeline);
/*     */   }
/*     */   
/*     */   public final void fillGradient(int x1, int y1, int x2, int y2, int color1, int color2) {
/*  50 */     ImmediateRenderUtil.gradientRectangle(pose().method_23760().method_23761(), x1, y1, x2, y2, color1, color2);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void blit(class_1058 sprite, int x, int y, int width, int height, RenderPipeline renderPipeline) {
/*  55 */     class_1044 abstractTexture = class_310.method_1551().method_1531().method_4619(sprite.method_45852());
/*  56 */     if (abstractTexture == null)
/*     */       return; 
/*  58 */     RenderSystem.setShaderTexture(0, abstractTexture.method_71659());
/*  59 */     ImmediateRenderUtil.texturedRect(
/*  60 */         pose(), x, y, sprite.method_4594(), sprite.method_4593(), width, height, sprite
/*  61 */         .method_4577(), sprite.method_4575(), 1.0F, renderPipeline);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void blit(class_2960 texture, int x, int y, int u1, int v1, int width, int height, int textureSize, RenderPipeline renderPipeline) {
/*  67 */     blit(texture, x, y, u1, v1, width, height, u1 + width, v1 + height, textureSize, renderPipeline);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void blit(class_2960 texture, int x, int y, int u1, int v1, int width, int height, int u2, int v2, int textureSize, RenderPipeline renderPipeline) {
/*  72 */     class_1044 abstractTexture = class_310.method_1551().method_1531().method_4619(texture);
/*  73 */     if (abstractTexture == null)
/*     */       return; 
/*  75 */     RenderSystem.setShaderTexture(0, abstractTexture.method_71659());
/*  76 */     ImmediateRenderUtil.texturedRect(
/*  77 */         pose(), x, y, u1, v1, width, height, u2, v2, textureSize, renderPipeline);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void blit(GpuTextureView texture, int x, int y, int u1, int v1, int width, int height, int u2, int v2, int textureSize, RenderPipeline renderPipeline) {
/*  84 */     RenderSystem.setShaderTexture(0, texture);
/*  85 */     ImmediateRenderUtil.texturedRect(
/*  86 */         pose(), x, y, u1, v1, width, height, u2, v2, textureSize, renderPipeline);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void drawCenteredString(class_327 font, String text, int x, int y, int color) {
/*  92 */     drawString(font, text, x - font.method_1727(text) / 2, y, color);
/*     */   }
/*     */   
/*     */   public final void drawCenteredString(class_327 font, class_2561 text, int x, int y, int color) {
/*  96 */     drawString(font, text, x - font.method_27525((class_5348)text) / 2, y, color);
/*     */   }
/*     */   
/*     */   public final void drawCenteredString(class_327 font, class_5481 charSequence, int x, int y, int color) {
/* 100 */     drawString(font, charSequence, x - font.method_30880(charSequence) / 2, y, color);
/*     */   }
/*     */   
/*     */   public final void drawString(class_327 font, String text, int x, int y, int color) {
/* 104 */     drawString(font, text, x, y, color, true);
/*     */   }
/*     */   
/*     */   public final void drawString(class_327 font, String text, int x, int y, int color, boolean shadow) {
/* 108 */     if (class_9848.method_61320(color) == 0)
/*     */       return; 
/* 110 */     class_4597.class_4598 vanillaBufferSource = getBufferSource();
/* 111 */     font.method_27521(text, x, y, color, shadow, pose().method_23760().method_23761(), (class_4597)vanillaBufferSource, class_327.class_6415.field_33993, 0, 15728880);
/* 112 */     vanillaBufferSource.method_37104();
/*     */   }
/*     */   
/*     */   public final void drawString(class_327 font, class_2561 text, int x, int y, int color) {
/* 116 */     drawString(font, text, x, y, color, true);
/*     */   }
/*     */   
/*     */   public final void drawString(class_327 font, class_2561 text, int x, int y, int color, boolean shadow) {
/* 120 */     if (class_9848.method_61320(color) == 0)
/*     */       return; 
/* 122 */     class_4597.class_4598 vanillaBufferSource = getBufferSource();
/* 123 */     font.method_27522(text, x, y, color, shadow, pose().method_23760().method_23761(), (class_4597)vanillaBufferSource, class_327.class_6415.field_33993, 0, 15728880);
/* 124 */     vanillaBufferSource.method_37104();
/*     */   }
/*     */   
/*     */   public final void drawString(class_327 font, class_5481 charSequence, int x, int y, int color) {
/* 128 */     drawString(font, charSequence, x, y, color, true);
/*     */   }
/*     */   
/*     */   public final void drawString(class_327 font, class_5481 charSequence, int x, int y, int color, boolean shadow) {
/* 132 */     if (class_9848.method_61320(color) == 0)
/*     */       return; 
/* 134 */     class_4597.class_4598 vanillaBufferSource = getBufferSource();
/* 135 */     font.method_22942(charSequence, x, y, color, shadow, pose().method_23760().method_23761(), (class_4597)vanillaBufferSource, class_327.class_6415.field_33993, 0, 15728880);
/* 136 */     vanillaBufferSource.method_37104();
/*     */   }
/*     */   
/*     */   public class_4597.class_4598 getBufferSource() {
/* 140 */     return this.vanillaBufferSourceSupplier.get();
/*     */   }
/*     */   
/*     */   public class_4587 pose() {
/* 144 */     return this.poseStack;
/*     */   }
/*     */   
/*     */   public void flush() {
/* 148 */     ((class_4597.class_4598)this.vanillaBufferSourceSupplier.get()).method_22993();
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\element\MapElementGraphics.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */