/*     */ package xaero.map.graphics.renderer.multitexture;
/*     */ 
/*     */ import com.mojang.blaze3d.buffers.GpuBuffer;
/*     */ import com.mojang.blaze3d.pipeline.RenderPipeline;
/*     */ import com.mojang.blaze3d.systems.GpuDevice;
/*     */ import com.mojang.blaze3d.systems.RenderPass;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import com.mojang.blaze3d.textures.GpuTexture;
/*     */ import com.mojang.blaze3d.textures.GpuTextureView;
/*     */ import com.mojang.blaze3d.vertex.VertexFormat;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.function.Consumer;
/*     */ import net.minecraft.class_1921;
/*     */ import net.minecraft.class_276;
/*     */ import net.minecraft.class_287;
/*     */ import net.minecraft.class_9799;
/*     */ import net.minecraft.class_9801;
/*     */ import xaero.map.core.ICompositeRenderType;
/*     */ import xaero.map.core.ICompositeState;
/*     */ import xaero.map.graphics.CustomRenderTypes;
/*     */ import xaero.map.render.util.ImmediateRenderUtil;
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
/*     */ public class MultiTextureRenderTypeRenderer
/*     */ {
/*  44 */   private class_9799 sharedBuffer = new class_9799(16384);
/*  45 */   private List<class_9801> buffersForDrawCalls = new ArrayList<>();
/*  46 */   private ArrayList<GpuTexture> texturesForDrawCalls = new ArrayList<>();
/*  47 */   private List<RenderPass.class_10884<MultiTextureRenderTypeRenderer>> drawCallBuilder = new ArrayList<>();
/*  48 */   private List<GpuBuffer> immediateVertexBuffers = new ArrayList<>(); private static final String RENDER_PASS_NAME = "xaero wm multitexture render pass"; private boolean used; private class_287 currentBufferBuilder; private Consumer<GpuTexture> textureBinder; private Consumer<GpuTexture> textureFinalizer; private GpuTexture prevTexture;
/*     */   private class_1921 renderType;
/*     */   
/*     */   void init(Consumer<GpuTexture> textureBinder, Consumer<GpuTexture> textureFinalizer, class_1921 renderType) {
/*  52 */     if (this.used)
/*  53 */       throw new IllegalStateException("Multi-texture renderer already in use!"); 
/*  54 */     if (!(renderType instanceof ICompositeRenderType))
/*  55 */       throw new IllegalArgumentException("Not a usable render type!"); 
/*  56 */     this.used = true;
/*  57 */     this.textureBinder = textureBinder;
/*  58 */     this.textureFinalizer = textureFinalizer;
/*  59 */     this.prevTexture = null;
/*  60 */     this.renderType = renderType;
/*     */   }
/*     */   
/*     */   void draw() {
/*  64 */     if (!this.used)
/*  65 */       throw new IllegalStateException("Multi-texture renderer is not in use!"); 
/*  66 */     if (!this.texturesForDrawCalls.isEmpty()) {
/*  67 */       Consumer<GpuTexture> textureBinder = this.textureBinder;
/*  68 */       Consumer<GpuTexture> textureFinalizer = this.textureFinalizer;
/*  69 */       boolean hasTextureFinalizer = (textureFinalizer != null);
/*  70 */       endBuffer(this.currentBufferBuilder);
/*     */       
/*  72 */       RenderPipeline renderPipeline = this.renderType.method_73243();
/*  73 */       ICompositeState compositeState = ((ICompositeRenderType)this.renderType).xaero_wm_getState();
/*  74 */       class_276 target = CustomRenderTypes.getOutputStateTarget(compositeState.xaero_wm_getOutputState());
/*     */       
/*  76 */       for (int i = 0; i < this.texturesForDrawCalls.size(); i++) {
/*  77 */         GpuTexture texture = this.texturesForDrawCalls.get(i);
/*  78 */         class_9801 meshData = this.buffersForDrawCalls.get(i);
/*  79 */         this.drawCallBuilder.add(createDrawCall(i, meshData, texture, renderPipeline));
/*  80 */         meshData.close();
/*     */       } 
/*  82 */       this.renderType.method_23516();
/*     */ 
/*     */       
/*  85 */       GpuTextureView colorTarget = (RenderSystem.outputColorTextureOverride != null) ? RenderSystem.outputColorTextureOverride : target.method_71639();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  92 */       GpuTextureView depthTarget = target.field_1478 ? ((RenderSystem.outputDepthTextureOverride != null) ? RenderSystem.outputDepthTextureOverride : target.method_71640()) : null;
/*     */       
/*  94 */       RenderPass renderPass = ImmediateRenderUtil.createRenderPass("xaero wm multitexture render pass", renderPipeline, colorTarget, depthTarget);
/*     */       
/*  96 */       try { renderPass.drawMultipleIndexed(this.drawCallBuilder, null, null, Collections.emptyList(), this);
/*  97 */         if (renderPass != null) renderPass.close();  } catch (Throwable throwable) { if (renderPass != null)
/*  98 */           try { renderPass.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  if (hasTextureFinalizer) {
/*  99 */         for (int j = 0; j < this.texturesForDrawCalls.size(); j++) {
/* 100 */           GpuTexture texture = this.texturesForDrawCalls.get(j);
/* 101 */           textureFinalizer.accept(texture);
/*     */         } 
/*     */       }
/*     */       
/* 105 */       textureBinder.accept(null);
/* 106 */       this.renderType.method_23518();
/*     */     } 
/* 108 */     this.drawCallBuilder.clear();
/* 109 */     this.texturesForDrawCalls.clear();
/* 110 */     this.buffersForDrawCalls.clear();
/* 111 */     this.used = false;
/* 112 */     this.renderType = null;
/*     */   }
/*     */   
/*     */   private RenderPass.class_10884<MultiTextureRenderTypeRenderer> createDrawCall(int index, class_9801 meshData, GpuTexture texture, RenderPipeline renderPipeline) {
/* 116 */     ByteBuffer indexBuffer = meshData.method_60821();
/* 117 */     if (indexBuffer != null) {
/* 118 */       throw new IllegalArgumentException();
/*     */     }
/* 120 */     RenderSystem.class_5590 sequentialBuffer = RenderSystem.getSequentialBuffer(meshData.method_60822().comp_752());
/* 121 */     GpuBuffer gpuIndexBuffer = sequentialBuffer.method_68274(meshData.method_60822().comp_751());
/* 122 */     VertexFormat.class_5595 gpuIndexType = sequentialBuffer.method_31924();
/* 123 */     GpuBuffer gpuVertexBuffer = uploadImmediateVertexBuffer(index, meshData.method_60818());
/* 124 */     return new RenderPass.class_10884(0, gpuVertexBuffer, gpuIndexBuffer, gpuIndexType, 0, meshData
/* 125 */         .method_60822().comp_751(), (context, uu) -> this.textureBinder.accept(texture));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private GpuBuffer uploadImmediateVertexBuffer(int index, ByteBuffer vertexBuffer) {
/* 135 */     GpuDevice gpuDevice = RenderSystem.getDevice();
/* 136 */     while (index >= this.immediateVertexBuffers.size())
/* 137 */       this.immediateVertexBuffers.add(null); 
/* 138 */     GpuBuffer currentImmediateBuffer = this.immediateVertexBuffers.get(index);
/* 139 */     if (currentImmediateBuffer != null && currentImmediateBuffer.size() < vertexBuffer.remaining()) {
/* 140 */       currentImmediateBuffer.close();
/* 141 */       currentImmediateBuffer = null;
/* 142 */       this.immediateVertexBuffers.set(index, null);
/*     */     } 
/* 144 */     if (currentImmediateBuffer == null) {
/*     */ 
/*     */       
/* 147 */       currentImmediateBuffer = gpuDevice.createBuffer(null, 46, vertexBuffer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 153 */       this.immediateVertexBuffers.set(index, currentImmediateBuffer);
/* 154 */       return currentImmediateBuffer;
/*     */     } 
/* 156 */     gpuDevice.createCommandEncoder().writeToBuffer(currentImmediateBuffer.slice(), vertexBuffer);
/* 157 */     return currentImmediateBuffer;
/*     */   }
/*     */   
/*     */   private void endBuffer(class_287 builder) {
/* 161 */     this.buffersForDrawCalls.add(builder.method_60794());
/*     */   }
/*     */   
/*     */   public class_287 begin(GpuTexture texture) {
/* 165 */     if (!this.used)
/* 166 */       throw new IllegalStateException("Multi-texture renderer is not in use!"); 
/* 167 */     if (texture == null)
/* 168 */       throw new IllegalStateException("Attempted to use the multi-texture renderer with texture null!"); 
/* 169 */     if (texture != this.prevTexture) {
/* 170 */       if (this.prevTexture != null)
/* 171 */         endBuffer(this.currentBufferBuilder); 
/* 172 */       this.currentBufferBuilder = new class_287(this.sharedBuffer, this.renderType.method_23033(), this.renderType.method_23031());
/* 173 */       this.prevTexture = texture;
/* 174 */       this.texturesForDrawCalls.add(texture);
/*     */     } 
/* 176 */     return this.currentBufferBuilder;
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\graphics\renderer\multitexture\MultiTextureRenderTypeRenderer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */