/*     */ package xaero.map.graphics;
/*     */ 
/*     */ import com.mojang.blaze3d.buffers.GpuBuffer;
/*     */ import com.mojang.blaze3d.textures.TextureFormat;
/*     */ import net.minecraft.class_310;
/*     */ import xaero.map.exception.OpenGLException;
/*     */ import xaero.map.pool.PoolUnit;
/*     */ import xaero.map.region.texture.BranchTextureRenderer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class TextureUpload
/*     */   implements PoolUnit
/*     */ {
/*     */   protected GpuTextureAndView glTexture;
/*     */   private GpuBuffer glUnpackPbo;
/*     */   private int level;
/*     */   private TextureFormat internalFormat;
/*     */   private int width;
/*     */   private int height;
/*     */   private int border;
/*     */   private long pixels_buffer_offset;
/*     */   private int uploadType;
/*     */   
/*     */   public void create(Object... args) {
/*  30 */     this.glTexture = (GpuTextureAndView)args[0];
/*  31 */     this.glUnpackPbo = (GpuBuffer)args[1];
/*  32 */     this.level = ((Integer)args[2]).intValue();
/*  33 */     this.internalFormat = (TextureFormat)args[3];
/*  34 */     this.width = ((Integer)args[4]).intValue();
/*  35 */     this.height = ((Integer)args[5]).intValue();
/*  36 */     this.border = ((Integer)args[6]).intValue();
/*  37 */     this.pixels_buffer_offset = ((Long)args[7]).longValue();
/*     */   }
/*     */   
/*     */   public void run() throws OpenGLException {
/*  41 */     OpenGLException.checkGLError();
/*  42 */     upload();
/*  43 */     OpenGLException.checkGLError();
/*     */   }
/*     */   
/*     */   abstract void upload() throws OpenGLException;
/*     */   
/*     */   public int getUploadType() {
/*  49 */     return this.uploadType;
/*     */   }
/*     */   
/*     */   public static class Normal
/*     */     extends TextureUpload {
/*     */     public Normal(int uploadType) {
/*  55 */       this.uploadType = uploadType;
/*     */     }
/*     */ 
/*     */     
/*     */     public Normal(Object... args) {
/*  60 */       this(0);
/*  61 */       create(args);
/*     */     }
/*     */ 
/*     */     
/*     */     void upload() throws OpenGLException {
/*  66 */       OpenGlHelper.copyBGRAUnpackBufferToMapTexture(this.glUnpackPbo, this.glTexture.texture, this.level, this.internalFormat, this.width, this.height, 0, this.pixels_buffer_offset);
/*     */ 
/*     */ 
/*     */       
/*  70 */       OpenGLException.checkGLError(false, "uploading a map texture");
/*     */     }
/*     */ 
/*     */     
/*     */     public void create(Object... args) {
/*  75 */       super.create(args);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class SubsequentNormal
/*     */     extends TextureUpload
/*     */   {
/*     */     private int xOffset;
/*     */     private int yOffset;
/*     */     
/*     */     public SubsequentNormal(int uploadType) {
/*  86 */       this.uploadType = uploadType;
/*     */     }
/*     */ 
/*     */     
/*     */     public SubsequentNormal(Object... args) {
/*  91 */       this(6);
/*  92 */       create(args);
/*     */     }
/*     */ 
/*     */     
/*     */     void upload() throws OpenGLException {
/*  97 */       OpenGlHelper.copyBGRAUnpackBufferToSubMapTexture(this.glUnpackPbo, this.glTexture.texture, this.level, this.xOffset, this.yOffset, this.width, this.height, this.pixels_buffer_offset);
/*     */ 
/*     */ 
/*     */       
/* 101 */       OpenGLException.checkGLError();
/*     */     }
/*     */ 
/*     */     
/*     */     public void create(Object... args) {
/* 106 */       super.create(args);
/* 107 */       this.xOffset = ((Integer)args[8]).intValue();
/* 108 */       this.yOffset = ((Integer)args[9]).intValue();
/*     */     }
/*     */   }
/*     */   
/*     */   public static class BranchUpdate
/*     */     extends TextureUpload
/*     */   {
/*     */     private boolean allocate;
/*     */     private GpuTextureAndView srcTextureTopLeft;
/*     */     private GpuTextureAndView srcTextureTopRight;
/*     */     private GpuTextureAndView srcTextureBottomLeft;
/*     */     private GpuTextureAndView srcTextureBottomRight;
/*     */     private BranchTextureRenderer renderer;
/*     */     private GpuBuffer glPackPbo;
/*     */     private long pboOffset;
/*     */     
/*     */     public BranchUpdate(int uploadType) {
/* 125 */       this.uploadType = uploadType;
/*     */     }
/*     */ 
/*     */     
/*     */     public BranchUpdate(Object... args) {
/* 130 */       this(((Boolean)args[8]).booleanValue() ? 4 : 3);
/* 131 */       create(args);
/*     */     }
/*     */ 
/*     */     
/*     */     void upload() throws OpenGLException {
/* 136 */       this.renderer.render(this.glTexture, this.srcTextureTopLeft, this.srcTextureTopRight, this.srcTextureBottomLeft, this.srcTextureBottomRight, class_310.method_1551().method_1522(), this.allocate);
/*     */ 
/*     */       
/* 139 */       if (this.glPackPbo == null)
/*     */         return; 
/* 141 */       OpenGlHelper.copyTextureToBGRAPackBuffer(this.glTexture.texture, this.glPackPbo, this.pboOffset);
/*     */     }
/*     */ 
/*     */     
/*     */     public void create(Object... args) {
/* 146 */       super.create(args);
/* 147 */       this.allocate = ((Boolean)args[8]).booleanValue();
/* 148 */       this.srcTextureTopLeft = (GpuTextureAndView)args[9];
/* 149 */       this.srcTextureTopRight = (GpuTextureAndView)args[10];
/* 150 */       this.srcTextureBottomLeft = (GpuTextureAndView)args[11];
/* 151 */       this.srcTextureBottomRight = (GpuTextureAndView)args[12];
/* 152 */       this.renderer = (BranchTextureRenderer)args[13];
/* 153 */       this.glPackPbo = (GpuBuffer)args[14];
/* 154 */       this.pboOffset = ((Long)args[15]).longValue();
/*     */     }
/*     */   }
/*     */   
/*     */   public static class BranchDownload
/*     */     extends TextureUpload
/*     */   {
/*     */     private GpuBuffer glPackPbo;
/*     */     private long pboOffset;
/*     */     
/*     */     public BranchDownload(int uploadType) {
/* 165 */       this.uploadType = uploadType;
/*     */     }
/*     */ 
/*     */     
/*     */     public BranchDownload(Object... args) {
/* 170 */       this(5);
/* 171 */       create(args);
/*     */     }
/*     */ 
/*     */     
/*     */     void upload() throws OpenGLException {
/* 176 */       if (this.glPackPbo == null) {
/*     */         return;
/*     */       }
/* 179 */       OpenGlHelper.copyTextureToBGRAPackBuffer(this.glTexture.texture, this.glPackPbo, this.pboOffset);
/*     */     }
/*     */ 
/*     */     
/*     */     public void create(Object... args) {
/* 184 */       super.create(args);
/* 185 */       this.glPackPbo = (GpuBuffer)args[8];
/* 186 */       this.pboOffset = ((Long)args[9]).longValue();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\graphics\TextureUpload.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */