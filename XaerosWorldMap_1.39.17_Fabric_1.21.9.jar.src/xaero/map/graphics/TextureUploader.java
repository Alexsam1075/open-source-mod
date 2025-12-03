/*     */ package xaero.map.graphics;
/*     */ 
/*     */ import com.mojang.blaze3d.buffers.GpuBuffer;
/*     */ import com.mojang.blaze3d.textures.TextureFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import xaero.map.exception.OpenGLException;
/*     */ import xaero.map.pool.TextureUploadPool;
/*     */ import xaero.map.region.texture.BranchTextureRenderer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TextureUploader
/*     */ {
/*     */   public static final int NORMAL = 0;
/*     */   public static final int NORMALDOWNLOAD = 1;
/*     */   public static final int BRANCHUPDATE = 3;
/*     */   public static final int BRANCHUPDATE_ALLOCATE = 4;
/*     */   public static final int BRANCHDOWNLOAD = 5;
/*     */   public static final int SUBSEQUENT_NORMAL = 6;
/*     */   private static final int DEFAULT_NORMAL_TIME = 1000000;
/*     */   private static final int DEFAULT_COMPRESSED_TIME = 1000000;
/*     */   private static final int DEFAULT_BRANCHUPDATED_TIME = 3000000;
/*     */   private static final int DEFAULT_BRANCHUPDATE_ALLOCATE_TIME = 4000000;
/*     */   private static final int DEFAULT_BRANCHDOWNLOAD_TIME = 1000000;
/*     */   private static final int DEFAULT_SUBSEQUENT_NORMAL_TIME = 1000000;
/*     */   private List<TextureUpload> textureUploadRequests;
/*     */   private TextureUploadBenchmark textureUploadBenchmark;
/*     */   private final TextureUploadPool.Normal normalTextureUploadPool;
/*     */   private final TextureUploadPool.BranchUpdate branchUpdatePool;
/*     */   private final TextureUploadPool.BranchUpdate branchUpdateAllocatePool;
/*     */   private final TextureUploadPool.BranchDownload branchDownloadPool;
/*     */   private final TextureUploadPool.SubsequentNormal subsequentNormalTextureUploadPool;
/*     */   
/*     */   public TextureUploader(TextureUploadPool.Normal normalTextureUploadPool, TextureUploadPool.BranchUpdate branchUpdatePool, TextureUploadPool.BranchUpdate branchUpdateAllocatePool, TextureUploadPool.BranchDownload branchDownloadPool, TextureUploadPool.SubsequentNormal subsequentNormalTextureUploadPool, TextureUploadBenchmark textureUploadBenchmark) {
/*  41 */     this.textureUploadRequests = new ArrayList<>();
/*  42 */     this.normalTextureUploadPool = normalTextureUploadPool;
/*     */     
/*  44 */     this.textureUploadBenchmark = textureUploadBenchmark;
/*  45 */     this.branchUpdatePool = branchUpdatePool;
/*  46 */     this.branchUpdateAllocatePool = branchUpdateAllocatePool;
/*  47 */     this.branchDownloadPool = branchDownloadPool;
/*  48 */     this.subsequentNormalTextureUploadPool = subsequentNormalTextureUploadPool;
/*     */   }
/*     */   
/*     */   public long requestUpload(TextureUpload upload) {
/*  52 */     this.textureUploadRequests.add(upload);
/*     */ 
/*     */     
/*  55 */     if (upload.getUploadType() == 0)
/*  56 */       return this.textureUploadBenchmark.isFinished(0) ? Math.min(this.textureUploadBenchmark.getAverage(0), 1000000L) : 1000000L; 
/*  57 */     if (upload.getUploadType() == 3)
/*  58 */       return this.textureUploadBenchmark.isFinished(3) ? Math.min(this.textureUploadBenchmark.getAverage(3), 3000000L) : 3000000L; 
/*  59 */     if (upload.getUploadType() == 4)
/*  60 */       return this.textureUploadBenchmark.isFinished(4) ? Math.min(this.textureUploadBenchmark.getAverage(4), 4000000L) : 4000000L; 
/*  61 */     if (upload.getUploadType() == 5)
/*  62 */       return this.textureUploadBenchmark.isFinished(5) ? Math.min(this.textureUploadBenchmark.getAverage(5), 1000000L) : 1000000L; 
/*  63 */     if (upload.getUploadType() == 6)
/*  64 */       return this.textureUploadBenchmark.isFinished(6) ? Math.min(this.textureUploadBenchmark.getAverage(6), 1000000L) : 1000000L; 
/*  65 */     return 0L;
/*     */   }
/*     */   
/*     */   public long requestNormal(GpuTextureAndView glTexture, GpuBuffer glPbo, int level, TextureFormat internalFormat, int width, int height, int border, long pixels_buffer_offset) {
/*  69 */     TextureUpload upload = this.normalTextureUploadPool.get(glTexture, glPbo, level, internalFormat, width, height, border, pixels_buffer_offset);
/*  70 */     return requestUpload(upload);
/*     */   }
/*     */   
/*     */   public long requestSubsequentNormal(GpuTextureAndView glTexture, GpuBuffer glPbo, int level, int width, int height, int border, long pixels_buffer_offset, int xOffset, int yOffset) {
/*  74 */     TextureUpload upload = this.subsequentNormalTextureUploadPool.get(glTexture, glPbo, level, width, height, border, pixels_buffer_offset, xOffset, yOffset);
/*  75 */     return requestUpload(upload);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long requestBranchUpdate(boolean allocate, GpuTextureAndView gpuTextureView, GpuBuffer glPbo, int level, TextureFormat internalFormat, int width, int height, int border, long pixels_buffer_offset, GpuTextureAndView srcTextureTopLeft, GpuTextureAndView srcTextureTopRight, GpuTextureAndView srcTextureBottomLeft, GpuTextureAndView srcTextureBottomRight, BranchTextureRenderer renderer, GpuBuffer glPackPbo, long pboOffset) {
/*     */     TextureUpload upload;
/*  85 */     if (!allocate) {
/*  86 */       upload = this.branchUpdatePool.get(gpuTextureView, glPbo, level, internalFormat, width, height, border, pixels_buffer_offset, srcTextureTopLeft, srcTextureTopRight, srcTextureBottomLeft, srcTextureBottomRight, renderer, glPackPbo, pboOffset);
/*     */     } else {
/*  88 */       upload = this.branchUpdateAllocatePool.get(gpuTextureView, glPbo, level, internalFormat, width, height, border, pixels_buffer_offset, srcTextureTopLeft, srcTextureTopRight, srcTextureBottomLeft, srcTextureBottomRight, renderer, glPackPbo, pboOffset);
/*  89 */     }  return requestUpload(upload);
/*     */   }
/*     */   
/*     */   public long requestBranchDownload(GpuTextureAndView glTexture, GpuBuffer glPackPbo, long pboOffset) {
/*  93 */     TextureUpload upload = this.branchDownloadPool.get(glTexture, glPackPbo, pboOffset);
/*  94 */     return requestUpload(upload);
/*     */   }
/*     */   
/*     */   public void finishNewestRequestImmediately() {
/*  98 */     TextureUpload newestRequest = this.textureUploadRequests.remove(this.textureUploadRequests.size() - 1);
/*  99 */     newestRequest.run();
/* 100 */     addToPool(newestRequest);
/*     */   }
/*     */   
/*     */   public void uploadTextures() throws OpenGLException {
/* 104 */     if (!this.textureUploadRequests.isEmpty()) {
/* 105 */       boolean prepared = false;
/* 106 */       for (int i = 0; i < this.textureUploadRequests.size(); i++) {
/* 107 */         TextureUpload tu = this.textureUploadRequests.get(i);
/* 108 */         int type = tu.getUploadType();
/* 109 */         if (!this.textureUploadBenchmark.isFinished(type)) {
/* 110 */           if (!prepared) {
/* 111 */             GL11.glFinish();
/* 112 */             prepared = true;
/*     */           } 
/* 114 */           this.textureUploadBenchmark.pre();
/*     */         } 
/* 116 */         tu.run();
/* 117 */         if (!this.textureUploadBenchmark.isFinished(type)) {
/* 118 */           this.textureUploadBenchmark.post(type);
/* 119 */           prepared = true;
/*     */         } 
/* 121 */         addToPool(tu);
/*     */       } 
/* 123 */       this.textureUploadRequests.clear();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void addToPool(TextureUpload tu) {
/* 128 */     switch (tu.getUploadType()) {
/*     */       case 0:
/* 130 */         this.normalTextureUploadPool.addToPool(tu);
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case 3:
/* 136 */         this.branchUpdatePool.addToPool(tu);
/*     */         break;
/*     */       case 4:
/* 139 */         this.branchUpdateAllocatePool.addToPool(tu);
/*     */         break;
/*     */       case 5:
/* 142 */         this.branchDownloadPool.addToPool(tu);
/*     */         break;
/*     */       case 6:
/* 145 */         this.subsequentNormalTextureUploadPool.addToPool(tu);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\graphics\TextureUploader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */