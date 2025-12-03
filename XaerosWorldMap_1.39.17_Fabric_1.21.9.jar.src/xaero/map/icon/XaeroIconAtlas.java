/*     */ package xaero.map.icon;
/*     */ 
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import com.mojang.blaze3d.textures.AddressMode;
/*     */ import com.mojang.blaze3d.textures.FilterMode;
/*     */ import com.mojang.blaze3d.textures.GpuTexture;
/*     */ import com.mojang.blaze3d.textures.GpuTextureView;
/*     */ import com.mojang.blaze3d.textures.TextureFormat;
/*     */ import xaero.map.WorldMap;
/*     */ 
/*     */ 
/*     */ public final class XaeroIconAtlas
/*     */ {
/*     */   private final GpuTexture textureId;
/*     */   private final GpuTextureView textureView;
/*     */   private final int width;
/*     */   
/*     */   private XaeroIconAtlas(GpuTexture textureId, GpuTextureView textureView, int width, int iconWidth) {
/*  19 */     this.textureId = textureId;
/*  20 */     this.textureView = textureView;
/*  21 */     this.width = width;
/*  22 */     this.iconWidth = iconWidth;
/*  23 */     this.sideIconCount = width / iconWidth;
/*  24 */     this.maxIconCount = this.sideIconCount * this.sideIconCount;
/*     */   }
/*     */   private int currentIndex; private final int iconWidth; private final int sideIconCount; private final int maxIconCount;
/*     */   public GpuTexture getTextureId() {
/*  28 */     return this.textureId;
/*     */   }
/*     */   
/*     */   public GpuTextureView getTextureView() {
/*  32 */     return this.textureView;
/*     */   }
/*     */   
/*     */   public void close() {
/*  36 */     this.textureView.close();
/*  37 */     this.textureId.close();
/*     */   }
/*     */   
/*     */   public int getWidth() {
/*  41 */     return this.width;
/*     */   }
/*     */   
/*     */   public int getCurrentIndex() {
/*  45 */     return this.currentIndex;
/*     */   }
/*     */   
/*     */   public boolean isFull() {
/*  49 */     return (this.currentIndex >= this.maxIconCount);
/*     */   }
/*     */   
/*     */   public XaeroIcon createIcon() {
/*  53 */     if (!isFull()) {
/*  54 */       int offsetX = this.currentIndex % this.sideIconCount * this.iconWidth;
/*  55 */       int offsetY = this.currentIndex / this.sideIconCount * this.iconWidth;
/*  56 */       this.currentIndex++;
/*  57 */       return new XaeroIcon(this, offsetX, offsetY);
/*     */     } 
/*  59 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Builder
/*     */   {
/*     */     private int width;
/*     */     
/*     */     private GpuTexture preparedTexture;
/*     */     
/*     */     private int iconWidth;
/*     */     
/*     */     public Builder setDefault() {
/*  72 */       setIconWidth(64);
/*  73 */       return this;
/*     */     }
/*     */     
/*     */     public Builder setPreparedTexture(GpuTexture preparedTexture) {
/*  77 */       this.preparedTexture = preparedTexture;
/*  78 */       return this;
/*     */     }
/*     */     
/*     */     public Builder setWidth(int width) {
/*  82 */       this.width = width;
/*  83 */       return this;
/*     */     }
/*     */     
/*     */     public Builder setIconWidth(int iconWidth) {
/*  87 */       this.iconWidth = iconWidth;
/*  88 */       return this;
/*     */     }
/*     */     
/*     */     private GpuTexture createGlTexture(int actualWidth) {
/*  92 */       GpuTexture texture = RenderSystem.getDevice().createTexture((String)null, 15, TextureFormat.RGBA8, actualWidth, actualWidth, 1, 1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  98 */       if (texture == null)
/*  99 */         return null; 
/* 100 */       texture.setTextureFilter(FilterMode.LINEAR, false);
/* 101 */       texture.setAddressMode(AddressMode.CLAMP_TO_EDGE);
/* 102 */       RenderSystem.getDevice().createCommandEncoder().clearColorTexture(texture, 0);
/* 103 */       return texture;
/*     */     }
/*     */     
/*     */     public XaeroIconAtlas build() {
/* 107 */       if (this.width == 0 || this.iconWidth <= 0)
/* 108 */         throw new IllegalStateException(); 
/* 109 */       if (this.width / this.iconWidth * this.iconWidth != this.width)
/* 110 */         throw new IllegalArgumentException(); 
/* 111 */       GpuTexture texture = (this.preparedTexture == null) ? createGlTexture(this.width) : this.preparedTexture;
/* 112 */       if (texture == null) {
/* 113 */         WorldMap.LOGGER.error("Failed to create a GL texture for a new xaero icon atlas!");
/* 114 */         return null;
/*     */       } 
/* 116 */       GpuTextureView textureView = RenderSystem.getDevice().createTextureView(texture);
/* 117 */       return new XaeroIconAtlas(texture, textureView, this.width, this.iconWidth);
/*     */     }
/*     */     
/*     */     public static Builder begin() {
/* 121 */       return (new Builder()).setDefault();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\icon\XaeroIconAtlas.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */