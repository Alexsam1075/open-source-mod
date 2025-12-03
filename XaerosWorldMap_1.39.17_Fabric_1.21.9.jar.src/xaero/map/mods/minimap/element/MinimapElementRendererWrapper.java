/*     */ package xaero.map.mods.minimap.element;
/*     */ 
/*     */ import java.util.function.Supplier;
/*     */ import net.minecraft.class_4597;
/*     */ import xaero.common.IXaeroMinimap;
/*     */ import xaero.common.graphics.renderer.multitexture.MultiTextureRenderTypeRendererProvider;
/*     */ import xaero.hud.minimap.BuiltInHudModules;
/*     */ import xaero.hud.minimap.element.render.MinimapElementRenderInfo;
/*     */ import xaero.hud.minimap.element.render.MinimapElementRenderLocation;
/*     */ import xaero.hud.minimap.element.render.MinimapElementRenderer;
/*     */ import xaero.hud.minimap.module.MinimapSession;
/*     */ import xaero.map.element.MapElementGraphics;
/*     */ import xaero.map.element.render.ElementRenderInfo;
/*     */ import xaero.map.element.render.ElementRenderLocation;
/*     */ import xaero.map.element.render.ElementRenderer;
/*     */ import xaero.map.graphics.renderer.multitexture.MultiTextureRenderTypeRendererProvider;
/*     */ import xaero.map.mods.SupportMods;
/*     */ 
/*     */ 
/*     */ public final class MinimapElementRendererWrapper<E, C>
/*     */   extends ElementRenderer<E, C, MinimapElementRendererWrapper<E, C>>
/*     */ {
/*     */   private final int order;
/*     */   private final IXaeroMinimap modMain;
/*     */   private final MinimapElementRenderer<E, C> renderer;
/*     */   private final Supplier<Boolean> shouldRenderSupplier;
/*     */   private MinimapElementRenderInfo convertedRenderInfo;
/*     */   
/*     */   private MinimapElementRendererWrapper(IXaeroMinimap modMain, C context, MinimapElementRenderProviderWrapper<E, C> provider, MinimapElementReaderWrapper<E, C> reader, MinimapElementRenderer<E, C> renderer, Supplier<Boolean> shouldRenderSupplier, int order) {
/*  30 */     super(context, provider, reader);
/*  31 */     this.order = order;
/*  32 */     this.renderer = renderer;
/*  33 */     this.modMain = modMain;
/*  34 */     this.shouldRenderSupplier = shouldRenderSupplier;
/*     */   }
/*     */   
/*     */   public static MinimapElementRenderLocation getRenderLocation(ElementRenderLocation worldMapLocation) {
/*  38 */     if (worldMapLocation == ElementRenderLocation.IN_MINIMAP)
/*  39 */       return MinimapElementRenderLocation.IN_MINIMAP; 
/*  40 */     if (worldMapLocation == ElementRenderLocation.OVER_MINIMAP)
/*  41 */       return MinimapElementRenderLocation.OVER_MINIMAP; 
/*  42 */     if (worldMapLocation == ElementRenderLocation.IN_WORLD)
/*  43 */       return MinimapElementRenderLocation.IN_WORLD; 
/*  44 */     if (worldMapLocation == ElementRenderLocation.WORLD_MAP)
/*  45 */       return MinimapElementRenderLocation.WORLD_MAP; 
/*  46 */     if (worldMapLocation == ElementRenderLocation.WORLD_MAP_MENU)
/*  47 */       return MinimapElementRenderLocation.WORLD_MAP_MENU; 
/*  48 */     return MinimapElementRenderLocation.UNKNOWN;
/*     */   }
/*     */   
/*     */   private MinimapElementRenderInfo convertRenderInfo(ElementRenderInfo renderInfo) {
/*  52 */     return new MinimapElementRenderInfo(
/*  53 */         getRenderLocation(renderInfo.location), renderInfo.renderEntity, renderInfo.player, renderInfo.renderPos, renderInfo.cave, renderInfo.partialTicks, renderInfo.framebuffer, renderInfo.backgroundCoordinateScale, renderInfo.mapDimension);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void preRender(ElementRenderInfo renderInfo, class_4597.class_4598 vanillaBufferSource, MultiTextureRenderTypeRendererProvider rendererProvider, boolean shadow) {
/*  64 */     MinimapSession minimapSession = (MinimapSession)BuiltInHudModules.MINIMAP.getCurrentSession();
/*  65 */     MultiTextureRenderTypeRendererProvider minimapMultiTextureRender = minimapSession.getMultiTextureRenderTypeRenderers();
/*  66 */     this.convertedRenderInfo = convertRenderInfo(renderInfo);
/*  67 */     this.renderer.preRender(this.convertedRenderInfo, vanillaBufferSource, minimapMultiTextureRender);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void postRender(ElementRenderInfo renderInfo, class_4597.class_4598 vanillaBufferSource, MultiTextureRenderTypeRendererProvider rendererProvider, boolean shadow) {
/*  76 */     MinimapSession minimapSession = (MinimapSession)BuiltInHudModules.MINIMAP.getCurrentSession();
/*  77 */     MultiTextureRenderTypeRendererProvider minimapMultiTextureRender = minimapSession.getMultiTextureRenderTypeRenderers();
/*  78 */     this.renderer.postRender(this.convertedRenderInfo, vanillaBufferSource, minimapMultiTextureRender);
/*  79 */     this.convertedRenderInfo = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean renderElement(E element, boolean hovered, double optionalDepth, float optionalScale, double partialX, double partialY, ElementRenderInfo renderInfo, MapElementGraphics guiGraphics, class_4597.class_4598 vanillaBufferSource, MultiTextureRenderTypeRendererProvider rendererProvider) {
/*  88 */     return this.renderer.renderElement(element, hovered, false, optionalDepth, optionalScale, partialX, partialY, this.convertedRenderInfo, SupportMods.xaeroMinimap
/*  89 */         .wrapElementGraphics(guiGraphics), vanillaBufferSource);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderElementShadow(E element, boolean hovered, float optionalScale, double partialX, double partialY, ElementRenderInfo renderInfo, MapElementGraphics guiGraphics, class_4597.class_4598 vanillaBufferSource, MultiTextureRenderTypeRendererProvider rendererProvider) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldRender(ElementRenderLocation location, boolean shadow) {
/* 102 */     return (!shadow && ((Boolean)this.shouldRenderSupplier.get()).booleanValue() && this.renderer.shouldRender(getRenderLocation(location)));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getOrder() {
/* 107 */     return this.order;
/*     */   }
/*     */   
/*     */   public static final class Builder<E, C>
/*     */   {
/*     */     private final MinimapElementRenderer<E, C> renderer;
/*     */     private Supplier<Boolean> shouldRenderSupplier;
/*     */     private IXaeroMinimap modMain;
/*     */     private int order;
/*     */     
/*     */     private Builder(MinimapElementRenderer<E, C> renderer) {
/* 118 */       this.renderer = renderer;
/*     */     }
/*     */     
/*     */     private Builder<E, C> setDefault() {
/* 122 */       setModMain(null);
/* 123 */       setShouldRenderSupplier(() -> Boolean.valueOf(true));
/* 124 */       setOrder(0);
/* 125 */       return this;
/*     */     }
/*     */     
/*     */     public Builder<E, C> setModMain(IXaeroMinimap modMain) {
/* 129 */       this.modMain = modMain;
/* 130 */       return this;
/*     */     }
/*     */     
/*     */     public Builder<E, C> setShouldRenderSupplier(Supplier<Boolean> shouldRenderSupplier) {
/* 134 */       this.shouldRenderSupplier = shouldRenderSupplier;
/* 135 */       return this;
/*     */     }
/*     */     
/*     */     public Builder<E, C> setOrder(int order) {
/* 139 */       this.order = order;
/* 140 */       return this;
/*     */     }
/*     */     
/*     */     public MinimapElementRendererWrapper<E, C> build() {
/* 144 */       if (this.modMain == null || this.shouldRenderSupplier == null)
/* 145 */         throw new IllegalStateException(); 
/* 146 */       MinimapElementRenderProviderWrapper<E, C> providerWrapper = new MinimapElementRenderProviderWrapper<>(this.renderer.getProvider());
/* 147 */       MinimapElementReaderWrapper<E, C> readerWrapper = new MinimapElementReaderWrapper<>(this.renderer.getElementReader());
/* 148 */       C context = (C)this.renderer.getContext();
/* 149 */       return new MinimapElementRendererWrapper<>(this.modMain, context, providerWrapper, readerWrapper, this.renderer, this.shouldRenderSupplier, this.order);
/*     */     }
/*     */     
/*     */     public static <E, C> Builder<E, C> begin(MinimapElementRenderer<E, C> renderer) {
/* 153 */       return (new Builder<>(renderer)).setDefault();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\mods\minimap\element\MinimapElementRendererWrapper.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */