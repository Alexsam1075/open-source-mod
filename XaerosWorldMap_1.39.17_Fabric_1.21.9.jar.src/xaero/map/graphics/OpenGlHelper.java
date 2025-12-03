/*     */ package xaero.map.graphics;
/*     */ import com.mojang.blaze3d.buffers.GpuBuffer;
/*     */ import com.mojang.blaze3d.opengl.GlConst;
/*     */ import com.mojang.blaze3d.opengl.GlStateManager;
/*     */ import com.mojang.blaze3d.textures.GpuTexture;
/*     */ import com.mojang.blaze3d.textures.TextureFormat;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.List;
/*     */ import net.minecraft.class_10859;
/*     */ import net.minecraft.class_10868;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.opengl.GL30;
/*     */ import xaero.map.WorldMap;
/*     */ import xaero.map.core.IWorldMapGlBuffer;
/*     */ import xaero.map.exception.OpenGLException;
/*     */ import xaero.map.platform.Services;
/*     */ 
/*     */ public class OpenGlHelper {
/*     */   public static boolean isUsingOpenGL() {
/*  20 */     return Services.PLATFORM.getPlatformRenderDeviceUtil().getRealDevice() instanceof net.minecraft.class_10865;
/*     */   }
/*     */   
/*     */   public static void resetPixelStore() {
/*  24 */     if (!isUsingOpenGL()) {
/*     */       return;
/*     */     }
/*  27 */     GlStateManager._pixelStore(3333, 4);
/*  28 */     GlStateManager._pixelStore(3330, 0);
/*     */     
/*  30 */     GlStateManager._pixelStore(3317, 4);
/*  31 */     GlStateManager._pixelStore(3316, 0);
/*  32 */     GlStateManager._pixelStore(3315, 0);
/*  33 */     GlStateManager._pixelStore(3314, 0);
/*     */   }
/*     */   
/*     */   public static void bindTexture(int index, GpuTexture texture) {
/*  37 */     if (!isUsingOpenGL())
/*     */       return; 
/*  39 */     texture = Services.PLATFORM.getPlatformRenderDeviceUtil().getRealTexture(texture);
/*  40 */     class_10868 glTexture = (class_10868)texture;
/*  41 */     GlStateManager._activeTexture(33984 + index);
/*  42 */     GlStateManager._bindTexture((glTexture == null) ? 0 : glTexture.method_68427());
/*     */   }
/*     */   
/*     */   public static void generateMipmaps(GpuTexture texture) {
/*  46 */     if (!isUsingOpenGL())
/*     */       return; 
/*  48 */     texture = Services.PLATFORM.getPlatformRenderDeviceUtil().getRealTexture(texture);
/*  49 */     class_10868 glTexture = (class_10868)texture;
/*  50 */     GlStateManager._activeTexture(33984);
/*  51 */     GlStateManager._bindTexture(glTexture.method_68427());
/*  52 */     glTexture.method_68424(3553);
/*  53 */     GL30.glGenerateMipmap(3553);
/*     */   }
/*     */   
/*     */   public static void clearErrors(boolean loud, String where) {
/*  57 */     if (!isUsingOpenGL())
/*     */       return; 
/*     */     int error;
/*  60 */     while ((error = GL11.glGetError()) != 0) {
/*  61 */       if (loud) {
/*  62 */         WorldMap.LOGGER.warn("OpenGL error ({}): {}", where, Integer.valueOf(error));
/*     */       }
/*     */     } 
/*     */   }
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
/*     */   public static void deleteTextures(List<GpuTextureAndView> textures, int count) {
/*  78 */     if (!isUsingOpenGL())
/*     */       return; 
/*  80 */     if (textures == null || textures.isEmpty())
/*     */       return; 
/*  82 */     for (int i = 0; i < count && !textures.isEmpty(); i++) {
/*  83 */       GpuTextureAndView glTexture = textures.remove(textures.size() - 1);
/*  84 */       glTexture.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void deleteBuffers(List<GpuBuffer> buffers, int count) {
/*  97 */     if (!isUsingOpenGL())
/*     */       return; 
/*  99 */     if (buffers == null || buffers.isEmpty())
/*     */       return; 
/* 101 */     for (int i = 0; i < count && !buffers.isEmpty(); i++) {
/* 102 */       class_10859 glBuffer = (class_10859)buffers.remove(buffers.size() - 1);
/* 103 */       glBuffer.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void unbindUnpackBuffer() {
/* 116 */     PixelBuffers.glBindBuffer(35052, 0);
/*     */   }
/*     */   
/*     */   public static void unbindPackBuffer() {
/* 120 */     PixelBuffers.glBindBuffer(35051, 0);
/*     */   }
/*     */   public static void uploadBGRABufferToMapTexture(ByteBuffer colorBuffer, GpuTexture texture, TextureFormat internalFormat, int width, int height) {
/*     */     class_10868 glTexture;
/* 124 */     texture = Services.PLATFORM.getPlatformRenderDeviceUtil().getRealTexture(texture);
/* 125 */     if (texture instanceof class_10868) { glTexture = (class_10868)texture; }
/*     */     else { return; }
/* 127 */      GlStateManager._activeTexture(33984);
/* 128 */     GlStateManager._bindTexture(glTexture.method_68427());
/* 129 */     GL11.glTexImage2D(3553, 0, 
/* 130 */         GlConst.toGlInternalId(internalFormat), width, height, 0, 32993, 32821, colorBuffer);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void downloadMapTextureToBGRABuffer(GpuTexture texture, ByteBuffer colorBuffer) {
/*     */     class_10868 glTexture;
/* 136 */     texture = Services.PLATFORM.getPlatformRenderDeviceUtil().getRealTexture(texture);
/* 137 */     if (texture instanceof class_10868) { glTexture = (class_10868)texture; }
/*     */     else { return; }
/* 139 */      GlStateManager._activeTexture(33984);
/* 140 */     GlStateManager._bindTexture(glTexture.method_68427());
/* 141 */     GL11.glGetTexImage(3553, 0, 32993, 33639, colorBuffer);
/*     */   }
/*     */   
/*     */   public static void copyTextureToBGRAPackBuffer(GpuTexture texture, GpuBuffer packBuffer, long packBufferOffset) {
/*     */     class_10859 glBuffer;
/* 146 */     if (packBuffer instanceof class_10859) { glBuffer = (class_10859)packBuffer; }
/*     */     else { return; }
/* 148 */      texture = Services.PLATFORM.getPlatformRenderDeviceUtil().getRealTexture(texture);
/* 149 */     class_10868 glTexture = (class_10868)texture;
/* 150 */     OpenGLException.checkGLError();
/* 151 */     GlStateManager._activeTexture(33984);
/* 152 */     GlStateManager._bindTexture(glTexture.method_68427());
/* 153 */     PixelBuffers.glBindBuffer(35051, ((IWorldMapGlBuffer)glBuffer).xaero_wm_getHandle());
/* 154 */     GL11.glGetTexImage(3553, 0, 32993, 32821, packBufferOffset);
/* 155 */     PixelBuffers.glBindBuffer(35051, 0);
/* 156 */     OpenGLException.checkGLError();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void copyBGRAUnpackBufferToMapTexture(GpuBuffer unpackBuffer, GpuTexture texture, int level, TextureFormat internalFormat, int width, int height, int border, long pixels_buffer_offset) {
/*     */     class_10859 glBuffer;
/* 163 */     if (unpackBuffer instanceof class_10859) { glBuffer = (class_10859)unpackBuffer; }
/*     */     else { return; }
/* 165 */      texture = Services.PLATFORM.getPlatformRenderDeviceUtil().getRealTexture(texture);
/* 166 */     class_10868 glTexture = (class_10868)texture;
/* 167 */     GlStateManager._activeTexture(33984);
/* 168 */     GlStateManager._bindTexture(glTexture.method_68427());
/* 169 */     PixelBuffers.glBindBuffer(35052, ((IWorldMapGlBuffer)glBuffer).xaero_wm_getHandle());
/* 170 */     GL11.glTexImage2D(3553, level, 
/* 171 */         GlConst.toGlInternalId(internalFormat), width, height, border, 32993, 32821, pixels_buffer_offset);
/*     */ 
/*     */     
/* 174 */     PixelBuffers.glBindBuffer(35052, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void copyBGRAUnpackBufferToSubMapTexture(GpuBuffer unpackBuffer, GpuTexture texture, int level, int xOffset, int yOffset, int width, int height, long pixels_buffer_offset) {
/*     */     class_10859 glBuffer;
/* 181 */     if (unpackBuffer instanceof class_10859) { glBuffer = (class_10859)unpackBuffer; }
/*     */     else { return; }
/* 183 */      texture = Services.PLATFORM.getPlatformRenderDeviceUtil().getRealTexture(texture);
/* 184 */     class_10868 glTexture = (class_10868)texture;
/* 185 */     GlStateManager._activeTexture(33984);
/* 186 */     GlStateManager._bindTexture(glTexture.method_68427());
/* 187 */     PixelBuffers.glBindBuffer(35052, ((IWorldMapGlBuffer)glBuffer).xaero_wm_getHandle());
/* 188 */     GL11.glTexSubImage2D(3553, level, xOffset, yOffset, width, height, 32993, 32821, pixels_buffer_offset);
/*     */ 
/*     */ 
/*     */     
/* 192 */     PixelBuffers.glBindBuffer(35052, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void fixMaxLod(GpuTexture glColorTexture, int levels) {
/* 198 */     bindTexture(0, glColorTexture);
/* 199 */     GL11.glTexParameterf(3553, 33083, levels);
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\graphics\OpenGlHelper.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */