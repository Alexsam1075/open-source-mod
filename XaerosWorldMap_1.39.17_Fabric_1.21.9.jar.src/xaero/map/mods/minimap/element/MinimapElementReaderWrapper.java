/*     */ package xaero.map.mods.minimap.element;
/*     */ 
/*     */ import net.minecraft.class_310;
/*     */ import xaero.hud.minimap.element.render.MinimapElementReader;
/*     */ import xaero.map.element.render.ElementReader;
/*     */ import xaero.map.element.render.ElementRenderLocation;
/*     */ 
/*     */ public class MinimapElementReaderWrapper<E, C>
/*     */   extends ElementReader<E, C, MinimapElementRendererWrapper<E, C>> {
/*     */   private final MinimapElementReader<E, C> reader;
/*     */   
/*     */   public MinimapElementReaderWrapper(MinimapElementReader<E, C> reader) {
/*  13 */     this.reader = reader;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isHidden(E element, C context) {
/*  18 */     return this.reader.isHidden(element, context);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getBoxScale(ElementRenderLocation location, E element, C context) {
/*  23 */     return this.reader.getBoxScale(MinimapElementRendererWrapper.getRenderLocation(location), element, context);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getRenderX(E element, C context, float partialTicks) {
/*  28 */     return this.reader.getRenderX(element, context, partialTicks);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getRenderZ(E element, C context, float partialTicks) {
/*  33 */     return this.reader.getRenderZ(element, context, partialTicks);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getInteractionBoxLeft(E element, C context, float partialTicks) {
/*  38 */     return this.reader.getInteractionBoxLeft(element, context, partialTicks);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getInteractionBoxRight(E element, C context, float partialTicks) {
/*  43 */     return this.reader.getInteractionBoxRight(element, context, partialTicks);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getInteractionBoxTop(E element, C context, float partialTicks) {
/*  48 */     return this.reader.getInteractionBoxTop(element, context, partialTicks);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getInteractionBoxBottom(E element, C context, float partialTicks) {
/*  53 */     return this.reader.getInteractionBoxBottom(element, context, partialTicks);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLeftSideLength(E element, class_310 mc) {
/*  58 */     return this.reader.getLeftSideLength(element, mc);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getMenuName(E element) {
/*  63 */     return this.reader.getMenuName(element);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getFilterName(E element) {
/*  68 */     return this.reader.getFilterName(element);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMenuTextFillLeftPadding(E element) {
/*  73 */     return this.reader.getMenuTextFillLeftPadding(element);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRightClickTitleBackgroundColor(E element) {
/*  78 */     return this.reader.getRightClickTitleBackgroundColor(element);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRenderBoxLeft(E element, C context, float partialTicks) {
/*  83 */     return this.reader.getRenderBoxLeft(element, context, partialTicks);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRenderBoxRight(E element, C context, float partialTicks) {
/*  88 */     return this.reader.getRenderBoxRight(element, context, partialTicks);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRenderBoxTop(E element, C context, float partialTicks) {
/*  93 */     return this.reader.getRenderBoxTop(element, context, partialTicks);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRenderBoxBottom(E element, C context, float partialTicks) {
/*  98 */     return this.reader.getRenderBoxBottom(element, context, partialTicks);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInteractable(ElementRenderLocation location, E element) {
/* 103 */     return this.reader.isInteractable(MinimapElementRendererWrapper.getRenderLocation(location), element);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldScaleBoxWithOptionalScale() {
/* 108 */     return this.reader.shouldScaleBoxWithOptionalScale();
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\mods\minimap\element\MinimapElementReaderWrapper.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */