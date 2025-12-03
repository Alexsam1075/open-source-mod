/*     */ package xaero.map.element;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import net.minecraft.class_1044;
/*     */ import net.minecraft.class_1060;
/*     */ import net.minecraft.class_1657;
/*     */ import net.minecraft.class_243;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_327;
/*     */ import net.minecraft.class_4587;
/*     */ import net.minecraft.class_4597;
/*     */ import xaero.map.MapProcessor;
/*     */ import xaero.map.WorldMap;
/*     */ import xaero.map.WorldMapSession;
/*     */ import xaero.map.element.render.ElementReader;
/*     */ import xaero.map.element.render.ElementRenderInfo;
/*     */ import xaero.map.element.render.ElementRenderLocation;
/*     */ import xaero.map.element.render.ElementRenderProvider;
/*     */ import xaero.map.element.render.ElementRenderer;
/*     */ import xaero.map.graphics.renderer.multitexture.MultiTextureRenderTypeRendererProvider;
/*     */ import xaero.map.gui.GuiMap;
/*     */ import xaero.map.mods.SupportMods;
/*     */ import xaero.map.world.MapDimension;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MapElementRenderHandler
/*     */ {
/*     */   protected final MapElementGraphics guiGraphics;
/*     */   private final List<ElementRenderer<?, ?, ?>> renderers;
/*     */   protected final ElementRenderLocation location;
/*     */   private HoveredMapElementHolder<?, ?> previousHovered;
/*     */   
/*     */   private MapElementRenderHandler(List<ElementRenderer<?, ?, ?>> renderers, ElementRenderLocation location, MapElementGraphics guiGraphics) {
/*  37 */     this.renderers = renderers;
/*  38 */     this.location = location;
/*  39 */     this.guiGraphics = guiGraphics;
/*     */   }
/*     */   private boolean previousHoveredPresent; private boolean renderingHovered; private Object workingHovered; private ElementRenderer<?, ?, ?> workingHoveredRenderer;
/*     */   public void add(ElementRenderer<?, ?, ?> renderer) {
/*  43 */     this.renderers.add(renderer);
/*     */   }
/*     */ 
/*     */   
/*     */   public static <E, C> HoveredMapElementHolder<E, C> createResult(E hovered, ElementRenderer<?, ?, ?> hoveredRenderer) {
/*  48 */     ElementRenderer<?, ?, ?> elementRenderer = hoveredRenderer;
/*  49 */     return new HoveredMapElementHolder<>(hovered, (ElementRenderer)elementRenderer);
/*     */   }
/*     */   
/*     */   private <E> ElementRenderer<E, ?, ?> getRenderer(HoveredMapElementHolder<E, ?> holder) {
/*  53 */     return holder.getRenderer();
/*     */   }
/*     */ 
/*     */   
/*     */   public HoveredMapElementHolder<?, ?> render(GuiMap mapScreen, class_4597.class_4598 renderTypeBuffers, MultiTextureRenderTypeRendererProvider rendererProvider, double cameraX, double cameraZ, int width, int height, double screenSizeBasedScale, double scale, double playerDimDiv, double mouseX, double mouseZ, float brightness, boolean cave, HoveredMapElementHolder<?, ?> oldHovered, class_310 mc, float partialTicks) {
/*  58 */     MapProcessor mapProcessor = WorldMapSession.getCurrentSession().getMapProcessor();
/*  59 */     MapDimension mapDimension = mapProcessor.getMapWorld().getCurrentDimension();
/*  60 */     double mapDimScale = mapDimension.calculateDimScale(mapProcessor.getWorldDimensionTypeRegistry());
/*  61 */     class_4587 matrixStack = this.guiGraphics.pose();
/*  62 */     class_1060 textureManager = mc.method_1531();
/*  63 */     class_327 fontRenderer = mc.field_1772;
/*  64 */     class_1044 guiTextures = textureManager.method_4619(WorldMap.guiTextures);
/*  65 */     guiTextures.method_4527(true, false);
/*  66 */     double baseScale = 1.0D / scale;
/*     */     
/*  68 */     Collections.sort(this.renderers);
/*     */     
/*  70 */     if (this.previousHovered == null)
/*  71 */       this.previousHovered = oldHovered; 
/*  72 */     this.workingHovered = null;
/*  73 */     this.workingHoveredRenderer = null;
/*  74 */     this.previousHoveredPresent = false;
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
/*  90 */     ElementRenderInfo renderInfo = new ElementRenderInfo(this.location, mc.method_1560(), (class_1657)mc.field_1724, new class_243(cameraX, -1.0D, cameraZ), mouseX, mouseZ, scale, cave, partialTicks, brightness, screenSizeBasedScale, null, mapDimScale, mapDimension.getDimId());
/*     */     
/*  92 */     matrixStack.method_22903();
/*  93 */     matrixStack.method_22905((float)baseScale, (float)baseScale, 1.0F);
/*  94 */     for (ElementRenderer<?, ?, ?> renderer : this.renderers) {
/*  95 */       renderWithRenderer(renderer, renderInfo, renderTypeBuffers, rendererProvider, width, height, baseScale, playerDimDiv, true, 0, 0);
/*     */     }
/*  97 */     if (this.previousHoveredPresent) {
/*  98 */       renderHoveredWithRenderer(this.previousHovered, renderTypeBuffers, rendererProvider, renderInfo, baseScale, playerDimDiv, true, 0, 0);
/*     */     }
/* 100 */     this.previousHoveredPresent = false;
/* 101 */     int indexLimit = 19490;
/* 102 */     for (ElementRenderer<?, ?, ?> renderer : this.renderers) {
/* 103 */       int elementIndex = 0;
/* 104 */       elementIndex = renderWithRenderer(renderer, renderInfo, renderTypeBuffers, rendererProvider, width, height, baseScale, playerDimDiv, false, elementIndex, indexLimit);
/* 105 */       matrixStack.method_22904(0.0D, 0.0D, getElementIndexDepth(elementIndex, indexLimit));
/* 106 */       indexLimit -= elementIndex;
/* 107 */       if (indexLimit < 0)
/* 108 */         indexLimit = 0; 
/*     */     } 
/* 110 */     if (this.previousHoveredPresent)
/* 111 */       renderHoveredWithRenderer(this.previousHovered, renderTypeBuffers, rendererProvider, renderInfo, baseScale, playerDimDiv, false, 0, indexLimit); 
/* 112 */     matrixStack.method_22909();
/* 113 */     guiTextures.method_4527(false, false);
/*     */     
/* 115 */     this
/* 116 */       .previousHovered = (this.previousHovered != null && this.previousHovered.is(this.workingHovered)) ? this.previousHovered : ((this.workingHovered == null) ? null : createResult(this.workingHovered, this.workingHoveredRenderer));
/* 117 */     return this.previousHovered;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private <E, C> int renderHoveredWithRenderer(HoveredMapElementHolder<E, C> hoveredHolder, class_4597.class_4598 renderTypeBuffers, MultiTextureRenderTypeRendererProvider rendererProvider, ElementRenderInfo renderInfo, double baseScale, double playerDimDiv, boolean pre, int elementIndex, int indexLimit) {
/* 126 */     ElementRenderer<E, C, ?> renderer = hoveredHolder.getRenderer();
/* 127 */     if (!renderer.shouldRenderHovered(pre))
/* 128 */       return elementIndex; 
/* 129 */     class_4587 matrixStack = this.guiGraphics.pose();
/* 130 */     ElementReader<E, C, ?> reader = renderer.getReader();
/* 131 */     E hoveredCast = hoveredHolder.getElement();
/* 132 */     renderer.preRender(renderInfo, renderTypeBuffers, rendererProvider, pre);
/* 133 */     matrixStack.method_22903();
/* 134 */     if (!pre)
/* 135 */       matrixStack.method_46416(0.0F, 0.0F, 1.0F); 
/* 136 */     double rendererDimDiv = renderer.shouldBeDimScaled() ? playerDimDiv : 1.0D;
/* 137 */     this.renderingHovered = true;
/* 138 */     if (!reader.isHidden(hoveredCast, renderer.getContext()) && transformAndRenderElement(renderer, hoveredCast, true, renderInfo, renderTypeBuffers, rendererProvider, baseScale, rendererDimDiv, pre, elementIndex, indexLimit) && !pre)
/*     */     {
/* 140 */       elementIndex++; } 
/* 141 */     this.renderingHovered = false;
/* 142 */     matrixStack.method_22909();
/* 143 */     renderer.postRender(renderInfo, renderTypeBuffers, rendererProvider, pre);
/* 144 */     return elementIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private <E, C, R extends ElementRenderer<E, C, R>> int renderWithRenderer(ElementRenderer<E, C, R> renderer, ElementRenderInfo renderInfo, class_4597.class_4598 renderTypeBuffers, MultiTextureRenderTypeRendererProvider rendererProvider, int width, int height, double baseScale, double playerDimDiv, boolean pre, int elementIndex, int indexLimit) {
/* 154 */     ElementRenderLocation location = this.location;
/* 155 */     if (!renderer.shouldRender(location, pre))
/* 156 */       return elementIndex; 
/* 157 */     ElementReader<E, C, R> reader = renderer.getReader();
/* 158 */     ElementRenderProvider<E, C> provider = renderer.getProvider();
/* 159 */     C context = (C)renderer.getContext();
/* 160 */     double rendererDimDiv = renderer.shouldBeDimScaled() ? playerDimDiv : 1.0D;
/* 161 */     renderer.preRender(renderInfo, renderTypeBuffers, rendererProvider, pre);
/* 162 */     provider.begin(location, context);
/* 163 */     while (provider.hasNext(location, context)) {
/* 164 */       E e = (E)provider.setupContextAndGetNext(location, context);
/* 165 */       if (e != null && !reader.isHidden(e, context) && 
/* 166 */         reader.isOnScreen(e, renderInfo.renderPos.field_1352, renderInfo.renderPos.field_1350, width, height, renderInfo.scale, renderInfo.screenSizeBasedScale, rendererDimDiv, context, renderInfo.partialTicks) && 
/* 167 */         transformAndRenderElement(renderer, e, false, renderInfo, renderTypeBuffers, rendererProvider, baseScale, rendererDimDiv, pre, elementIndex, indexLimit) && !pre)
/*     */       {
/* 169 */         elementIndex++;
/*     */       }
/*     */     } 
/* 172 */     provider.end(location, context);
/* 173 */     renderer.postRender(renderInfo, renderTypeBuffers, rendererProvider, pre);
/* 174 */     return elementIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private <E, C, R extends ElementRenderer<E, C, R>> boolean transformAndRenderElement(ElementRenderer<E, C, R> renderer, E e, boolean highlighted, ElementRenderInfo renderInfo, class_4597.class_4598 renderTypeBuffers, MultiTextureRenderTypeRendererProvider rendererProvider, double baseScale, double rendererDimDiv, boolean pre, int elementIndex, int indexLimit) {
/* 184 */     class_4587 matrixStack = this.guiGraphics.pose();
/* 185 */     ElementReader<E, C, R> reader = renderer.getReader();
/* 186 */     C context = (C)renderer.getContext();
/* 187 */     if (!this.renderingHovered) {
/* 188 */       if (reader.isInteractable(renderInfo.location, e) && reader.isHoveredOnMap(this.location, e, renderInfo.mouseX, renderInfo.mouseZ, renderInfo.scale, renderInfo.screenSizeBasedScale, rendererDimDiv, context, renderInfo.partialTicks)) {
/* 189 */         this.workingHovered = e;
/* 190 */         this.workingHoveredRenderer = renderer;
/*     */       } 
/* 192 */       if (!this.previousHoveredPresent && this.previousHovered != null && this.previousHovered.is(e)) {
/* 193 */         this.previousHoveredPresent = true;
/* 194 */         return false;
/*     */       } 
/*     */     } 
/* 197 */     matrixStack.method_22903();
/*     */     
/* 199 */     double offX = (reader.getRenderX(e, context, renderInfo.partialTicks) / rendererDimDiv - renderInfo.renderPos.field_1352) / baseScale;
/* 200 */     double offZ = (reader.getRenderZ(e, context, renderInfo.partialTicks) / rendererDimDiv - renderInfo.renderPos.field_1350) / baseScale;
/* 201 */     long roundedOffX = Math.round(offX);
/* 202 */     long roundedOffZ = Math.round(offZ);
/* 203 */     double partialX = offX - roundedOffX;
/* 204 */     double partialY = offZ - roundedOffZ;
/* 205 */     matrixStack.method_46416((float)roundedOffX, (float)roundedOffZ, 0.0F);
/* 206 */     boolean result = false;
/* 207 */     if (pre) {
/* 208 */       renderer.renderElementShadow(e, highlighted, (float)renderInfo.screenSizeBasedScale, partialX, partialY, renderInfo, this.guiGraphics, renderTypeBuffers, rendererProvider);
/*     */     } else {
/* 210 */       double optionalDepth = getElementIndexDepth(elementIndex, indexLimit);
/* 211 */       result = renderer.renderElement(e, highlighted, optionalDepth, (float)renderInfo.screenSizeBasedScale, partialX, partialY, renderInfo, this.guiGraphics, renderTypeBuffers, rendererProvider);
/*     */     } 
/* 213 */     matrixStack.method_22909();
/* 214 */     return result;
/*     */   }
/*     */   
/*     */   private double getElementIndexDepth(int elementIndex, int indexLimit) {
/* 218 */     return ((elementIndex >= indexLimit) ? indexLimit : elementIndex) * 0.1D;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Builder
/*     */   {
/*     */     private class_4587 poseStack;
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder setDefault() {
/* 230 */       setPoseStack(null);
/* 231 */       return this;
/*     */     }
/*     */     
/*     */     public Builder setPoseStack(class_4587 poseStack) {
/* 235 */       this.poseStack = poseStack;
/* 236 */       return this;
/*     */     }
/*     */     
/*     */     public MapElementRenderHandler build() {
/* 240 */       if (this.poseStack == null)
/* 241 */         throw new IllegalStateException(); 
/* 242 */       List<ElementRenderer<?, ?, ?>> renderers = new ArrayList<>();
/* 243 */       if (SupportMods.minimap())
/* 244 */         renderers.add(SupportMods.xaeroMinimap.getWaypointRenderer()); 
/* 245 */       renderers.add(WorldMap.trackedPlayerRenderer);
/* 246 */       if (SupportMods.pac())
/* 247 */         renderers.add(SupportMods.xaeroPac.getCaimResultElementRenderer()); 
/* 248 */       MapElementGraphics graphics = new MapElementGraphics(this.poseStack, () -> class_310.method_1551().method_22940().method_23000());
/* 249 */       return new MapElementRenderHandler(renderers, ElementRenderLocation.WORLD_MAP, graphics);
/*     */     }
/*     */     
/*     */     public static Builder begin() {
/* 253 */       return new Builder();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\element\MapElementRenderHandler.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */