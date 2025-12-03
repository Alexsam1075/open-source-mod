/*     */ package xaero.map.mods.gui;
/*     */ 
/*     */ import com.mojang.blaze3d.buffers.GpuBufferSlice;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.class_10366;
/*     */ import net.minecraft.class_10799;
/*     */ import net.minecraft.class_11278;
/*     */ import net.minecraft.class_276;
/*     */ import net.minecraft.class_2960;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_4587;
/*     */ import org.joml.Matrix4fStack;
/*     */ import xaero.map.element.MapElementGraphics;
/*     */ import xaero.map.exception.OpenGLException;
/*     */ import xaero.map.graphics.ImprovedFramebuffer;
/*     */ import xaero.map.graphics.TextureUtils;
/*     */ import xaero.map.icon.XaeroIcon;
/*     */ import xaero.map.icon.XaeroIconAtlas;
/*     */ import xaero.map.icon.XaeroIconAtlasManager;
/*     */ import xaero.map.misc.Misc;
/*     */ import xaero.map.render.util.ImmediateRenderUtil;
/*     */ 
/*     */ 
/*     */ public class WaypointSymbolCreator
/*     */ {
/*     */   private static final int PREFERRED_ATLAS_WIDTH = 1024;
/*     */   private static final int ICON_WIDTH = 64;
/*  31 */   public static final class_2960 minimapTextures = class_2960.method_60655("xaerobetterpvp", "gui/guis.png");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int white = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  42 */   private class_310 mc = class_310.method_1551(); private XaeroIcon deathSymbolTexture;
/*  43 */   private final Map<String, XaeroIcon> charSymbols = new HashMap<>(); private XaeroIconAtlasManager iconManager;
/*     */   private ImprovedFramebuffer atlasRenderFramebuffer;
/*     */   
/*     */   public XaeroIcon getDeathSymbolTexture(MapElementGraphics guiGraphics) {
/*  47 */     if (this.deathSymbolTexture == null)
/*  48 */       createDeathSymbolTexture(guiGraphics); 
/*  49 */     return this.deathSymbolTexture;
/*     */   }
/*     */   private XaeroIconAtlas lastAtlas; private class_11278 orthoProjectionCache;
/*     */   private void createDeathSymbolTexture(MapElementGraphics guiGraphics) {
/*  53 */     this.deathSymbolTexture = createCharSymbol(guiGraphics, true, null);
/*     */   }
/*     */   
/*     */   public XaeroIcon getSymbolTexture(MapElementGraphics guiGraphics, String c) {
/*     */     XaeroIcon icon;
/*  58 */     synchronized (this.charSymbols) {
/*  59 */       icon = this.charSymbols.get(c);
/*     */     } 
/*  61 */     if (icon == null)
/*  62 */       icon = createCharSymbol(guiGraphics, false, c); 
/*  63 */     return icon;
/*     */   }
/*     */   
/*     */   private XaeroIcon createCharSymbol(MapElementGraphics guiGraphics, boolean death, String c) {
/*  67 */     if (this.iconManager == null) {
/*  68 */       int maxTextureSize = RenderSystem.getDevice().getMaxTextureSize();
/*  69 */       int atlasTextureSize = Math.min(maxTextureSize, 1024) / 64 * 64;
/*  70 */       this.atlasRenderFramebuffer = new ImprovedFramebuffer(atlasTextureSize, atlasTextureSize, true);
/*  71 */       this.atlasRenderFramebuffer.closeColorTexture();
/*  72 */       OpenGLException.checkGLError();
/*  73 */       this.atlasRenderFramebuffer.setColorTexture(null, null);
/*  74 */       this.iconManager = new XaeroIconAtlasManager(64, atlasTextureSize, new ArrayList());
/*  75 */       this.orthoProjectionCache = new class_11278("waypoint symbol creator", -1.0F, 1000.0F, true);
/*     */     } 
/*  77 */     XaeroIconAtlas atlas = this.iconManager.getCurrentAtlas();
/*  78 */     XaeroIcon icon = atlas.createIcon();
/*     */     
/*  80 */     guiGraphics.flush();
/*  81 */     this.atlasRenderFramebuffer.bindAsMainTarget(false);
/*  82 */     this.atlasRenderFramebuffer.setColorTexture(atlas);
/*     */     
/*  84 */     if (this.lastAtlas != atlas) {
/*  85 */       TextureUtils.clearRenderTarget((class_276)this.atlasRenderFramebuffer, 0, 1.0F);
/*  86 */       this.lastAtlas = atlas;
/*     */     } 
/*     */     
/*  89 */     GpuBufferSlice ortho = this.orthoProjectionCache.method_71092(this.atlasRenderFramebuffer.field_1482, this.atlasRenderFramebuffer.field_1481);
/*  90 */     RenderSystem.setProjectionMatrix(ortho, class_10366.field_54954);
/*  91 */     Matrix4fStack shaderMatrixStack = RenderSystem.getModelViewStack();
/*  92 */     shaderMatrixStack.pushMatrix();
/*  93 */     shaderMatrixStack.identity();
/*  94 */     class_4587 matrixStack = guiGraphics.pose();
/*  95 */     matrixStack.method_22903();
/*  96 */     matrixStack.method_34426();
/*  97 */     matrixStack.method_46416(icon.getOffsetX(), (this.atlasRenderFramebuffer.field_1481 - 64 - icon.getOffsetY()), 0.0F);
/*     */     
/*  99 */     matrixStack.method_46416(2.0F, 2.0F, 0.0F);
/* 100 */     if (!death) {
/* 101 */       matrixStack.method_22905(3.0F, 3.0F, 1.0F);
/* 102 */       guiGraphics.drawString(this.mc.field_1772, c, 0, 0, -1);
/*     */     } else {
/* 104 */       matrixStack.method_22905(3.0F, 3.0F, 1.0F);
/* 105 */       ImmediateRenderUtil.setShaderColor(0.243F, 0.243F, 0.243F, 1.0F);
/* 106 */       guiGraphics.blit(minimapTextures, 1, 1, 0, 78, 9, 9, 256, class_10799.field_56883);
/* 107 */       ImmediateRenderUtil.setShaderColor(0.988F, 0.988F, 0.988F, 1.0F);
/* 108 */       guiGraphics.blit(minimapTextures, 0, 0, 0, 78, 9, 9, 256, class_10799.field_56883);
/* 109 */       ImmediateRenderUtil.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     } 
/* 111 */     matrixStack.method_22909();
/* 112 */     guiGraphics.flush();
/*     */     
/* 114 */     Misc.minecraftOrtho(this.mc, false);
/* 115 */     shaderMatrixStack.popMatrix();
/* 116 */     this.atlasRenderFramebuffer.bindDefaultFramebuffer(this.mc);
/* 117 */     if (death) {
/* 118 */       this.deathSymbolTexture = icon;
/*     */     } else {
/* 120 */       synchronized (this.charSymbols) {
/* 121 */         this.charSymbols.put(c, icon);
/*     */       } 
/*     */     } 
/* 124 */     return icon;
/*     */   }
/*     */   
/*     */   public void resetChars() {
/* 128 */     synchronized (this.charSymbols) {
/* 129 */       this.charSymbols.clear();
/*     */     } 
/* 131 */     this.lastAtlas = null;
/* 132 */     this.deathSymbolTexture = null;
/* 133 */     if (this.iconManager != null) {
/* 134 */       this.iconManager.clearAtlases();
/* 135 */       this.atlasRenderFramebuffer.setColorTexture(null, null);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\mods\gui\WaypointSymbolCreator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */