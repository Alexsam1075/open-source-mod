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
/*     */ 
/*     */ 
/*     */ public class Builder
/*     */ {
/*     */   private int width;
/*     */   private GpuTexture preparedTexture;
/*     */   private int iconWidth;
/*     */   
/*     */   public Builder setDefault() {
/*  72 */     setIconWidth(64);
/*  73 */     return this;
/*     */   }
/*     */   
/*     */   public Builder setPreparedTexture(GpuTexture preparedTexture) {
/*  77 */     this.preparedTexture = preparedTexture;
/*  78 */     return this;
/*     */   }
/*     */   
/*     */   public Builder setWidth(int width) {
/*  82 */     this.width = width;
/*  83 */     return this;
/*     */   }
/*     */   
/*     */   public Builder setIconWidth(int iconWidth) {
/*  87 */     this.iconWidth = iconWidth;
/*  88 */     return this;
/*     */   }
/*     */   
/*     */   private GpuTexture createGlTexture(int actualWidth) {
/*  92 */     GpuTexture texture = RenderSystem.getDevice().createTexture((String)null, 15, TextureFormat.RGBA8, actualWidth, actualWidth, 1, 1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  98 */     if (texture == null)
/*  99 */       return null; 
/* 100 */     texture.setTextureFilter(FilterMode.LINEAR, false);
/* 101 */     texture.setAddressMode(AddressMode.CLAMP_TO_EDGE);
/* 102 */     RenderSystem.getDevice().createCommandEncoder().clearColorTexture(texture, 0);
/* 103 */     return texture;
/*     */   }
/*     */   
/*     */   public XaeroIconAtlas build() {
/* 107 */     if (this.width == 0 || this.iconWidth <= 0)
/* 108 */       throw new IllegalStateException(); 
/* 109 */     if (this.width / this.iconWidth * this.iconWidth != this.width)
/* 110 */       throw new IllegalArgumentException(); 
/* 111 */     GpuTexture texture = (this.preparedTexture == null) ? createGlTexture(this.width) : this.preparedTexture;
/* 112 */     if (texture == null) {
/* 113 */       WorldMap.LOGGER.error("Failed to create a GL texture for a new xaero icon atlas!");
/* 114 */       return null;
/*     */     } 
/* 116 */     GpuTextureView textureView = RenderSystem.getDevice().createTextureView(texture);
/* 117 */     return new XaeroIconAtlas(texture, textureView, this.width, this.iconWidth);
/*     */   }
/*     */   
/*     */   public static Builder begin() {
/* 121 */     return (new Builder()).setDefault();
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\icon\XaeroIconAtlas$Builder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */