/*    */ package xaero.map.element;
/*    */ 
/*    */ import net.minecraft.class_4597;
/*    */ import xaero.map.element.render.ElementRenderInfo;
/*    */ import xaero.map.element.render.ElementRenderLocation;
/*    */ import xaero.map.element.render.ElementRenderer;
/*    */ import xaero.map.graphics.renderer.multitexture.MultiTextureRenderTypeRendererProvider;
/*    */ 
/*    */ public final class MenuOnlyElementRenderer<E>
/*    */   extends ElementRenderer<E, Object, MenuOnlyElementRenderer<E>> {
/*    */   protected MenuOnlyElementRenderer(MenuOnlyElementReader<E> reader) {
/* 12 */     super(null, null, reader);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldRender(ElementRenderLocation location, boolean shadow) {
/* 17 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void preRender(ElementRenderInfo renderInfo, class_4597.class_4598 vanillaBufferSource, MultiTextureRenderTypeRendererProvider rendererProvider, boolean shadow) {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void postRender(ElementRenderInfo renderInfo, class_4597.class_4598 vanillaBufferSource, MultiTextureRenderTypeRendererProvider rendererProvider, boolean shadow) {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderElementShadow(E element, boolean hovered, float optionalScale, double partialX, double partialY, ElementRenderInfo renderInfo, MapElementGraphics guiGraphics, class_4597.class_4598 vanillaBufferSource, MultiTextureRenderTypeRendererProvider rendererProvider) {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean renderElement(E element, boolean hovered, double optionalDepth, float optionalScale, double partialX, double partialY, ElementRenderInfo renderInfo, MapElementGraphics guiGraphics, class_4597.class_4598 vanillaBufferSource, MultiTextureRenderTypeRendererProvider rendererProvider) {
/* 48 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\element\MenuOnlyElementRenderer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */