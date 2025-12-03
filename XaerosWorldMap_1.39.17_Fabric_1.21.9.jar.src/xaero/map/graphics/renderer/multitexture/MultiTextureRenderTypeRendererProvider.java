/*    */ package xaero.map.graphics.renderer.multitexture;
/*    */ 
/*    */ import com.mojang.blaze3d.textures.GpuTexture;
/*    */ import java.util.ArrayDeque;
/*    */ import java.util.Deque;
/*    */ import java.util.HashSet;
/*    */ import java.util.function.Consumer;
/*    */ import net.minecraft.class_10868;
/*    */ import net.minecraft.class_1921;
/*    */ import xaero.map.graphics.OpenGlHelper;
/*    */ import xaero.map.platform.Services;
/*    */ import xaero.map.render.util.IPlatformRenderDeviceUtil;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MultiTextureRenderTypeRendererProvider
/*    */ {
/* 23 */   private Deque<MultiTextureRenderTypeRenderer> availableRenderers = new ArrayDeque<>(); public MultiTextureRenderTypeRendererProvider(int rendererCount) {
/* 24 */     for (int i = 0; i < rendererCount; i++)
/* 25 */       this.availableRenderers.add(new MultiTextureRenderTypeRenderer()); 
/* 26 */     this.usedRenderers = new HashSet<>();
/*    */   }
/*    */   private HashSet<MultiTextureRenderTypeRenderer> usedRenderers;
/*    */   public MultiTextureRenderTypeRenderer getRenderer(Consumer<GpuTexture> textureBinder, class_1921 renderType) {
/* 30 */     return getRenderer(textureBinder, null, renderType);
/*    */   }
/*    */   
/*    */   public MultiTextureRenderTypeRenderer getRenderer(Consumer<GpuTexture> textureBinder, Consumer<GpuTexture> textureFinalizer, class_1921 renderType) {
/* 34 */     if (this.availableRenderers.isEmpty())
/* 35 */       throw new RuntimeException("No renderers available!"); 
/* 36 */     MultiTextureRenderTypeRenderer renderer = this.availableRenderers.removeFirst();
/* 37 */     renderer.init(textureBinder, textureFinalizer, renderType);
/* 38 */     this.usedRenderers.add(renderer);
/* 39 */     return renderer;
/*    */   }
/*    */   
/*    */   public void draw(MultiTextureRenderTypeRenderer renderer) {
/* 43 */     if (this.usedRenderers.remove(renderer)) {
/* 44 */       renderer.draw();
/* 45 */       this.availableRenderers.add(renderer);
/*    */     } else {
/* 47 */       throw new RuntimeException("The renderer requested for drawing was not provided by this provider!");
/*    */     } 
/*    */   }
/*    */   public static void defaultTextureBind(GpuTexture texture) {
/* 51 */     IPlatformRenderDeviceUtil renderDeviceUtil = Services.PLATFORM.getPlatformRenderDeviceUtil();
/* 52 */     if (!(renderDeviceUtil.getRealDevice() instanceof net.minecraft.class_10865))
/* 53 */       throw new IllegalStateException("Unsupported non-OpenGL rendering detected!"); 
/* 54 */     GpuTexture realTexture = renderDeviceUtil.getRealTexture(texture);
/* 55 */     OpenGlHelper.bindTexture(0, realTexture);
/* 56 */     if (realTexture != null)
/* 57 */       ((class_10868)realTexture).method_68424(3553); 
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\graphics\renderer\multitexture\MultiTextureRenderTypeRendererProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */