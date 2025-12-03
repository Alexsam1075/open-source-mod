/*     */ package xaero.map.element.render;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.class_310;
/*     */ import xaero.map.gui.CursorBox;
/*     */ import xaero.map.gui.IRightClickableElement;
/*     */ import xaero.map.gui.dropdown.rightclick.RightClickOption;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ElementReader<E, C, R extends ElementRenderer<E, ?, R>>
/*     */ {
/*     */   public abstract boolean isHidden(E paramE, C paramC);
/*     */   
/*     */   public abstract double getRenderX(E paramE, C paramC, float paramFloat);
/*     */   
/*     */   public abstract double getRenderZ(E paramE, C paramC, float paramFloat);
/*     */   
/*     */   public abstract int getInteractionBoxLeft(E paramE, C paramC, float paramFloat);
/*     */   
/*     */   public abstract int getInteractionBoxRight(E paramE, C paramC, float paramFloat);
/*     */   
/*     */   public abstract int getInteractionBoxTop(E paramE, C paramC, float paramFloat);
/*     */   
/*     */   public abstract int getInteractionBoxBottom(E paramE, C paramC, float paramFloat);
/*     */   
/*     */   public abstract int getRenderBoxLeft(E paramE, C paramC, float paramFloat);
/*     */   
/*     */   public boolean isInteractable(ElementRenderLocation location, E element) {
/*  31 */     return false;
/*     */   } public abstract int getRenderBoxRight(E paramE, C paramC, float paramFloat); public abstract int getRenderBoxTop(E paramE, C paramC, float paramFloat); public abstract int getRenderBoxBottom(E paramE, C paramC, float paramFloat); public abstract int getLeftSideLength(E paramE, class_310 paramclass_310); public abstract String getMenuName(E paramE); public abstract String getFilterName(E paramE); public abstract int getMenuTextFillLeftPadding(E paramE); public abstract int getRightClickTitleBackgroundColor(E paramE);
/*     */   public abstract boolean shouldScaleBoxWithOptionalScale();
/*     */   public float getBoxScale(ElementRenderLocation location, E element, C context) {
/*  35 */     return 1.0F;
/*     */   }
/*     */   
/*     */   public boolean isMouseOverMenuElement(E element, int x, int y, int mouseX, int mouseY, class_310 mc) {
/*  39 */     int topEdge = y - 8;
/*  40 */     if (mouseY < topEdge)
/*  41 */       return false; 
/*  42 */     int bottomEdge = y + 8;
/*  43 */     if (mouseY >= bottomEdge) {
/*  44 */       return false;
/*     */     }
/*  46 */     int rightEdge = x + 5;
/*  47 */     if (mouseX >= rightEdge)
/*  48 */       return false; 
/*  49 */     int leftEdge = x - getLeftSideLength(element, mc);
/*  50 */     if (mouseX < leftEdge)
/*  51 */       return false; 
/*  52 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isHoveredOnMap(ElementRenderLocation location, E element, double mouseX, double mouseZ, double scale, double screenSizeBasedScale, double rendererDimDiv, C context, float partialTicks) {
/*  56 */     double fullScale = getBoxScale(location, element, context);
/*  57 */     if (shouldScaleBoxWithOptionalScale())
/*  58 */       fullScale *= screenSizeBasedScale; 
/*  59 */     double left = getInteractionBoxLeft(element, context, partialTicks) * fullScale;
/*  60 */     double right = getInteractionBoxRight(element, context, partialTicks) * fullScale;
/*  61 */     double top = getInteractionBoxTop(element, context, partialTicks) * fullScale;
/*  62 */     double bottom = getInteractionBoxBottom(element, context, partialTicks) * fullScale;
/*  63 */     double screenOffX = (mouseX - getRenderX(element, context, partialTicks) / rendererDimDiv) * scale;
/*  64 */     if (screenOffX < left || screenOffX >= right)
/*  65 */       return false; 
/*  66 */     double screenOffY = (mouseZ - getRenderZ(element, context, partialTicks) / rendererDimDiv) * scale;
/*  67 */     if (screenOffY < top || screenOffY >= bottom)
/*  68 */       return false; 
/*  69 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isOnScreen(E element, double cameraX, double cameraZ, int width, int height, double scale, double screenSizeBasedScale, double rendererDimDiv, C context, float partialTicks) {
/*  73 */     double xOnScreen = (getRenderX(element, context, partialTicks) / rendererDimDiv - cameraX) * scale + (width / 2);
/*  74 */     double zOnScreen = (getRenderZ(element, context, partialTicks) / rendererDimDiv - cameraZ) * scale + (height / 2);
/*  75 */     float boxScale = getBoxScale(ElementRenderLocation.WORLD_MAP, element, context);
/*  76 */     if (shouldScaleBoxWithOptionalScale())
/*  77 */       boxScale = (float)(boxScale * screenSizeBasedScale); 
/*  78 */     double left = xOnScreen + (getRenderBoxLeft(element, context, partialTicks) * boxScale);
/*  79 */     if (left >= width)
/*  80 */       return false; 
/*  81 */     double right = xOnScreen + (getRenderBoxRight(element, context, partialTicks) * boxScale);
/*  82 */     if (right <= 0.0D)
/*  83 */       return false; 
/*  84 */     double top = zOnScreen + (getRenderBoxTop(element, context, partialTicks) * boxScale);
/*  85 */     if (top >= height)
/*  86 */       return false; 
/*  87 */     double bottom = zOnScreen + (getRenderBoxBottom(element, context, partialTicks) * boxScale);
/*  88 */     if (bottom <= 0.0D)
/*  89 */       return false; 
/*  90 */     return true;
/*     */   }
/*     */   
/*     */   public ArrayList<RightClickOption> getRightClickOptions(E element, IRightClickableElement target) {
/*  94 */     return null;
/*     */   }
/*     */   
/*     */   public boolean isRightClickValid(E element) {
/*  98 */     return false;
/*     */   }
/*     */   
/*     */   public CursorBox getTooltip(E element, C context, boolean overMenu) {
/* 102 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\element\render\ElementReader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */