/*     */ package xaero.map.file.export;
/*     */ 
/*     */ import com.mojang.blaze3d.buffers.GpuBufferSlice;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import com.mojang.blaze3d.textures.FilterMode;
/*     */ import com.mojang.blaze3d.textures.TextureFormat;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.attribute.FileAttribute;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Hashtable;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.class_11278;
/*     */ import net.minecraft.class_1959;
/*     */ import net.minecraft.class_2378;
/*     */ import net.minecraft.class_2874;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_4587;
/*     */ import org.joml.Matrix4f;
/*     */ import org.joml.Matrix4fStack;
/*     */ import xaero.map.MapProcessor;
/*     */ import xaero.map.WorldMap;
/*     */ import xaero.map.biome.BlockTintProvider;
/*     */ import xaero.map.cache.BlockStateShortShapeCache;
/*     */ import xaero.map.exception.OpenGLException;
/*     */ import xaero.map.file.MapRegionInfo;
/*     */ import xaero.map.file.MapSaveLoad;
/*     */ import xaero.map.file.OldFormatSupport;
/*     */ import xaero.map.file.RegionDetection;
/*     */ import xaero.map.graphics.CustomRenderTypes;
/*     */ import xaero.map.graphics.GpuTextureAndView;
/*     */ import xaero.map.graphics.ImprovedFramebuffer;
/*     */ import xaero.map.graphics.OpenGlHelper;
/*     */ import xaero.map.graphics.renderer.multitexture.MultiTextureRenderTypeRenderer;
/*     */ import xaero.map.graphics.renderer.multitexture.MultiTextureRenderTypeRendererProvider;
/*     */ import xaero.map.graphics.shader.WorldMapShaderHelper;
/*     */ import xaero.map.gui.MapTileSelection;
/*     */ import xaero.map.misc.Misc;
/*     */ import xaero.map.region.ExportMapRegion;
/*     */ import xaero.map.region.ExportMapTileChunk;
/*     */ import xaero.map.region.LeveledRegion;
/*     */ import xaero.map.region.MapLayer;
/*     */ import xaero.map.region.MapRegion;
/*     */ import xaero.map.region.MapTileChunk;
/*     */ import xaero.map.region.OverlayManager;
/*     */ import xaero.map.region.texture.ExportLeafRegionTexture;
/*     */ import xaero.map.world.MapDimension;
/*     */ 
/*     */ public class PNGExporter {
/*     */   private final Calendar calendar;
/*     */   private Path destinationPath;
/*     */   
/*  59 */   public PNGExporter(Path destinationPath) { this.calendar = Calendar.getInstance();
/*  60 */     this.destinationPath = destinationPath;
/*  61 */     this.matrixStack = new class_4587(); } private class_4587 matrixStack; private class_11278 projectionCache; public PNGExportResult export(MapProcessor mapProcessor, class_2378<class_1959> biomeRegistry, class_2378<class_2874> dimensionTypes, MapTileSelection selection, OldFormatSupport oldFormatSupport) throws IllegalArgumentException, IllegalAccessException, OpenGLException { BufferedImage image;
/*     */     ImprovedFramebuffer exportFrameBuffer;
/*     */     ByteBuffer frameDataBuffer;
/*     */     int[] bufferArray;
/*  65 */     if (!mapProcessor.getMapSaveLoad().isRegionDetectionComplete()) {
/*  66 */       return new PNGExportResult(PNGExportResultType.NOT_PREPARED, null);
/*     */     }
/*  68 */     int exportedLayer = mapProcessor.getCurrentCaveLayer();
/*  69 */     MapDimension dim = mapProcessor.getMapWorld().getCurrentDimension();
/*  70 */     Set<LeveledRegion<?>> list = dim.getLayeredMapRegions().getUnsyncedSet();
/*  71 */     if (list.isEmpty())
/*  72 */       return new PNGExportResult(PNGExportResultType.EMPTY, null); 
/*  73 */     boolean multipleImagesSetting = WorldMap.settings.multipleImagesExport;
/*  74 */     boolean nightExportSetting = WorldMap.settings.nightExport;
/*  75 */     int exportScaleDownSquareSetting = WorldMap.settings.exportScaleDownSquare;
/*  76 */     boolean includingHighlights = WorldMap.settings.highlightsExport;
/*  77 */     boolean full = (selection == null);
/*  78 */     Integer minX = null;
/*  79 */     Integer maxX = null;
/*  80 */     Integer minZ = null;
/*  81 */     Integer maxZ = null;
/*  82 */     MapLayer mapLayer = dim.getLayeredMapRegions().getLayer(exportedLayer);
/*  83 */     if (full)
/*  84 */     { for (LeveledRegion<?> region : list) {
/*  85 */         if (region.getLevel() != 0 || !((MapRegion)region).hasHadTerrain() || region.getCaveLayer() != exportedLayer)
/*     */           continue; 
/*  87 */         if (minX == null || region.getRegionX() < minX.intValue())
/*  88 */           minX = Integer.valueOf(region.getRegionX()); 
/*  89 */         if (maxX == null || region.getRegionX() > maxX.intValue())
/*  90 */           maxX = Integer.valueOf(region.getRegionX()); 
/*  91 */         if (minZ == null || region.getRegionZ() < minZ.intValue())
/*  92 */           minZ = Integer.valueOf(region.getRegionZ()); 
/*  93 */         if (maxZ == null || region.getRegionZ() > maxZ.intValue())
/*  94 */           maxZ = Integer.valueOf(region.getRegionZ()); 
/*     */       } 
/*  96 */       Iterable<Hashtable<Integer, RegionDetection>> regionDetectionIterable = !dim.isUsingWorldSave() ? mapLayer.getDetectedRegions().values() : dim.getWorldSaveDetectedRegions();
/*  97 */       for (Hashtable<Integer, RegionDetection> column : regionDetectionIterable) {
/*  98 */         for (RegionDetection regionDetection : column.values()) {
/*  99 */           if (!regionDetection.isHasHadTerrain())
/*     */             continue; 
/* 101 */           if (minX == null || regionDetection.getRegionX() < minX.intValue())
/* 102 */             minX = Integer.valueOf(regionDetection.getRegionX()); 
/* 103 */           if (maxX == null || regionDetection.getRegionX() > maxX.intValue())
/* 104 */             maxX = Integer.valueOf(regionDetection.getRegionX()); 
/* 105 */           if (minZ == null || regionDetection.getRegionZ() < minZ.intValue())
/* 106 */             minZ = Integer.valueOf(regionDetection.getRegionZ()); 
/* 107 */           if (maxZ == null || regionDetection.getRegionZ() > maxZ.intValue())
/* 108 */             maxZ = Integer.valueOf(regionDetection.getRegionZ()); 
/*     */         } 
/*     */       }  }
/* 111 */     else { minX = Integer.valueOf(selection.getLeft() >> 5);
/* 112 */       minZ = Integer.valueOf(selection.getTop() >> 5);
/* 113 */       maxX = Integer.valueOf(selection.getRight() >> 5);
/* 114 */       maxZ = Integer.valueOf(selection.getBottom() >> 5); }
/*     */     
/* 116 */     int minBlockX = minX.intValue() * 512;
/* 117 */     int minBlockZ = minZ.intValue() * 512;
/* 118 */     int maxBlockX = (maxX.intValue() + 1) * 512 - 1;
/* 119 */     int maxBlockZ = (maxZ.intValue() + 1) * 512 - 1;
/* 120 */     if (!full) {
/* 121 */       minBlockX = Math.max(minBlockX, selection.getLeft() << 4);
/* 122 */       minBlockZ = Math.max(minBlockZ, selection.getTop() << 4);
/* 123 */       maxBlockX = Math.min(maxBlockX, (selection.getRight() << 4) + 15);
/* 124 */       maxBlockZ = Math.min(maxBlockZ, (selection.getBottom() << 4) + 15);
/*     */     } 
/* 126 */     int exportAreaWidthInRegions = maxX.intValue() - minX.intValue() + 1;
/* 127 */     int exportAreaHeightInRegions = maxZ.intValue() - minZ.intValue() + 1;
/* 128 */     long exportAreaSizeInRegions = exportAreaWidthInRegions * exportAreaHeightInRegions;
/*     */     
/* 130 */     int exportAreaWidth = exportAreaWidthInRegions * 512;
/* 131 */     int exportAreaHeight = exportAreaHeightInRegions * 512;
/* 132 */     if (!full) {
/* 133 */       exportAreaWidth = maxBlockX - minBlockX + 1;
/* 134 */       exportAreaHeight = maxBlockZ - minBlockZ + 1;
/*     */     } 
/* 136 */     int scaleDownSquareSquared = exportScaleDownSquareSetting * exportScaleDownSquareSetting;
/* 137 */     float scale = (exportAreaSizeInRegions < scaleDownSquareSquared || multipleImagesSetting || scaleDownSquareSquared <= 0) ? 1.0F : (float)(exportScaleDownSquareSetting / Math.sqrt(exportAreaSizeInRegions));
/* 138 */     int exportImageWidth = (int)(exportAreaWidth * scale);
/* 139 */     int exportImageHeight = (int)(exportAreaHeight * scale);
/* 140 */     if (!multipleImagesSetting && scaleDownSquareSquared > 0) {
/* 141 */       long maxExportAreaSizeInRegions = scaleDownSquareSquared * 262144L;
/* 142 */       if (exportAreaWidth * exportAreaHeight / 512L / 512L > maxExportAreaSizeInRegions)
/* 143 */         return new PNGExportResult(PNGExportResultType.TOO_BIG, null); 
/*     */     } 
/* 145 */     int maxTextureSize = GL11.glGetInteger(3379);
/* 146 */     OpenGLException.checkGLError();
/*     */     
/* 148 */     int frameWidth = Math.min(1024, Math.min(maxTextureSize, exportImageWidth));
/* 149 */     int frameHeight = Math.min(1024, Math.min(maxTextureSize, exportImageHeight));
/*     */     
/* 151 */     int horizontalFrames = (int)Math.ceil(exportImageWidth / frameWidth);
/* 152 */     int verticalFrames = (int)Math.ceil(exportImageHeight / frameHeight);
/* 153 */     boolean multipleImages = (multipleImagesSetting && horizontalFrames * verticalFrames > 1);
/* 154 */     if (multipleImages) {
/* 155 */       exportImageWidth = frameWidth;
/* 156 */       exportImageHeight = frameHeight;
/*     */     } 
/* 158 */     int pixelCount = exportImageWidth * exportImageHeight;
/* 159 */     if (pixelCount == Integer.MAX_VALUE || pixelCount / exportImageHeight != exportImageWidth)
/* 160 */       return new PNGExportResult(PNGExportResultType.IMAGE_TOO_BIG, null); 
/* 161 */     if (WorldMap.settings.debug) {
/* 162 */       WorldMap.LOGGER.info(String.format("Exporting PNG of size %dx%d using a framebuffer of size %dx%d.", new Object[] { Integer.valueOf(exportImageWidth), Integer.valueOf(exportImageHeight), Integer.valueOf(frameWidth), Integer.valueOf(frameHeight) }));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 168 */       image = new BufferedImage(exportImageWidth, exportImageHeight, 1);
/* 169 */       exportFrameBuffer = new ImprovedFramebuffer(frameWidth, frameHeight, true);
/* 170 */       frameDataBuffer = BufferUtils.createByteBuffer(frameWidth * frameHeight * 4);
/* 171 */       bufferArray = new int[frameWidth * frameHeight];
/* 172 */     } catch (OutOfMemoryError oome) {
/* 173 */       return new PNGExportResult(PNGExportResultType.OUT_OF_MEMORY, null);
/*     */     } 
/* 175 */     if (exportFrameBuffer.method_30277() == null || exportFrameBuffer.method_30278() == null)
/* 176 */       return new PNGExportResult(PNGExportResultType.BAD_FBO, null); 
/* 177 */     (class_310.method_1551()).field_1773.method_71114().method_71034(class_308.class_11274.field_60026);
/* 178 */     if (this.projectionCache == null)
/* 179 */       this.projectionCache = new class_11278("png export render", 0.0F, 1000.0F, false); 
/* 180 */     GpuBufferSlice ortho = this.projectionCache.method_71092(frameWidth, frameHeight);
/* 181 */     RenderSystem.setProjectionMatrix(ortho, class_10366.field_54954);
/* 182 */     class_4587 matrixStack = this.matrixStack;
/* 183 */     BlockStateShortShapeCache shortShapeCache = mapProcessor.getBlockStateShortShapeCache();
/* 184 */     BlockTintProvider blockTintProvider = mapProcessor.getWorldBlockTintProvider();
/* 185 */     OverlayManager overlayManager = mapProcessor.getOverlayManager();
/* 186 */     MapSaveLoad mapSaveLoad = mapProcessor.getMapSaveLoad();
/* 187 */     MultiTextureRenderTypeRendererProvider rendererProvider = mapProcessor.getMultiTextureRenderTypeRenderers();
/* 188 */     Matrix4fStack shaderMatrixStack = RenderSystem.getModelViewStack();
/* 189 */     shaderMatrixStack.pushMatrix();
/* 190 */     shaderMatrixStack.identity();
/* 191 */     matrixStack.method_22903();
/*     */     
/* 193 */     exportFrameBuffer.bindAsMainTarget(true);
/*     */     
/* 195 */     matrixStack.method_22905(scale, scale, 1.0F);
/* 196 */     boolean[] justMetaDest = new boolean[1];
/* 197 */     Path imageDestination = this.destinationPath;
/* 198 */     if (multipleImages)
/* 199 */       imageDestination = this.destinationPath.resolve(getExportBaseName()); 
/* 200 */     boolean empty = true;
/* 201 */     PNGExportResultType resultType = PNGExportResultType.SUCCESS;
/* 202 */     for (int i = 0; i < horizontalFrames; i++) {
/* 203 */       for (int j = 0; j < verticalFrames; j++) {
/* 204 */         boolean renderedSomething = false;
/* 205 */         TextureUtils.clearRenderTarget((class_276)exportFrameBuffer, -16777216, 1.0F);
/* 206 */         matrixStack.method_22903();
/*     */         
/* 208 */         float frameLeft = minBlockX + (i * frameWidth) / scale;
/* 209 */         float frameRight = minBlockX + ((i + 1) * frameWidth) / scale - 1.0F;
/* 210 */         float frameTop = minBlockZ + (j * frameHeight) / scale;
/* 211 */         float frameBottom = minBlockZ + ((j + 1) * frameHeight) / scale - 1.0F;
/*     */         
/* 213 */         if (!full) {
/* 214 */           if (maxBlockX < frameRight)
/* 215 */             frameRight = maxBlockX; 
/* 216 */           if (maxBlockZ < frameBottom) {
/* 217 */             frameBottom = maxBlockZ;
/*     */           }
/*     */         } 
/* 220 */         int minTileChunkX = (int)Math.floor(frameLeft) >> 6;
/* 221 */         int maxTileChunkX = (int)Math.floor(frameRight) >> 6;
/* 222 */         int minTileChunkZ = (int)Math.floor(frameTop) >> 6;
/* 223 */         int maxTileChunkZ = (int)Math.floor(frameBottom) >> 6;
/* 224 */         int minRegionX = minTileChunkX >> 3;
/* 225 */         int minRegionZ = minTileChunkZ >> 3;
/* 226 */         int maxRegionX = maxTileChunkX >> 3;
/* 227 */         int maxRegionZ = maxTileChunkZ >> 3;
/*     */         
/* 229 */         matrixStack.method_22904(0.1D, 0.0D, 0.0D);
/*     */         
/* 231 */         Matrix4f matrix = matrixStack.method_23760().method_23761();
/* 232 */         for (int regionX = minRegionX; regionX <= maxRegionX; regionX++) {
/* 233 */           for (int regionZ = minRegionZ; regionZ <= maxRegionZ; regionZ++) {
/* 234 */             RegionDetection regionDetection; MapRegion originalRegion = mapProcessor.getLeafMapRegion(exportedLayer, regionX, regionZ, false);
/* 235 */             MapRegion mapRegion1 = originalRegion;
/* 236 */             if (originalRegion == null && mapLayer.regionDetectionExists(regionX, regionZ))
/* 237 */               regionDetection = mapLayer.getRegionDetection(regionX, regionZ); 
/* 238 */             boolean regionHasHighlightsIfUndiscovered = (includingHighlights && dim.getHighlightHandler().shouldApplyRegionHighlights(regionX, regionZ, false));
/* 239 */             if (regionDetection == null && !regionHasHighlightsIfUndiscovered)
/*     */               continue; 
/* 241 */             File cacheFile = null;
/* 242 */             boolean loadingFromCache = (regionDetection != null && (originalRegion == null || !originalRegion.isBeingWritten() || originalRegion.getLoadState() != 2));
/* 243 */             if (loadingFromCache) {
/* 244 */               cacheFile = regionDetection.getCacheFile();
/* 245 */               if (cacheFile == null && !regionDetection.hasLookedForCache()) {
/*     */                 try {
/* 247 */                   cacheFile = mapSaveLoad.getCacheFile((MapRegionInfo)regionDetection, exportedLayer, true, false);
/* 248 */                 } catch (IOException iOException) {}
/*     */               }
/* 250 */               if (cacheFile == null) {
/* 251 */                 if (!regionHasHighlightsIfUndiscovered)
/*     */                   continue; 
/* 253 */                 loadingFromCache = false;
/*     */               } 
/*     */             } 
/* 256 */             ExportMapRegion region = new ExportMapRegion(dim, regionX, regionZ, exportedLayer, biomeRegistry);
/* 257 */             if (loadingFromCache) {
/* 258 */               region.setShouldCache(true, "png");
/* 259 */               region.setHasHadTerrain();
/* 260 */               region.setCacheFile(cacheFile);
/* 261 */               region.loadCacheTextures(mapProcessor, biomeRegistry, false, null, 0, null, justMetaDest, 1, oldFormatSupport);
/* 262 */             } else if (originalRegion != null) {
/* 263 */               for (int o = 0; o < 8; o++) {
/* 264 */                 for (int p = 0; p < 8; p++) {
/* 265 */                   MapTileChunk originalTileChunk = originalRegion.getChunk(o, p);
/* 266 */                   if (originalTileChunk != null && originalTileChunk.hasHadTerrain()) {
/* 267 */                     MapTileChunk tileChunk = region.createTexture(o, p).getTileChunk();
/* 268 */                     for (int tx = 0; tx < 4; tx++) {
/* 269 */                       for (int tz = 0; tz < 4; tz++)
/* 270 */                         tileChunk.setTile(tx, tz, originalTileChunk.getTile(tx, tz), shortShapeCache); 
/* 271 */                     }  tileChunk.setLoadState((byte)2);
/* 272 */                     tileChunk.updateBuffers(mapProcessor, blockTintProvider, overlayManager, WorldMap.settings.detailed_debug, shortShapeCache);
/*     */                   } 
/*     */                 } 
/*     */               } 
/* 276 */             }  if (includingHighlights) {
/* 277 */               mapProcessor.getMapRegionHighlightsPreparer().prepare((MapRegion)region, true);
/*     */             }
/* 279 */             MultiTextureRenderTypeRenderer rendererLight = rendererProvider.getRenderer(MultiTextureRenderTypeRendererProvider::defaultTextureBind, CustomRenderTypes.MAP);
/* 280 */             MultiTextureRenderTypeRenderer rendererNoLight = rendererProvider.getRenderer(MultiTextureRenderTypeRendererProvider::defaultTextureBind, CustomRenderTypes.MAP);
/* 281 */             List<GpuTextureAndView> texturesToDelete = new ArrayList<>();
/* 282 */             for (int localChunkX = 0; localChunkX < 8; localChunkX++) {
/* 283 */               for (int localChunkZ = 0; localChunkZ < 8; localChunkZ++) {
/* 284 */                 ExportMapTileChunk tileChunk = region.getChunk(localChunkX, localChunkZ);
/* 285 */                 if (tileChunk != null)
/*     */                 
/* 287 */                 { ExportLeafRegionTexture tileChunkTexture = tileChunk.getLeafTexture();
/* 288 */                   if (tileChunkTexture != null)
/*     */                   {
/* 290 */                     if (tileChunk.getX() < minTileChunkX || tileChunk.getX() > maxTileChunkX || tileChunk.getZ() < minTileChunkZ || tileChunk.getZ() > maxTileChunkZ)
/* 291 */                     { tileChunkTexture.deleteColorBuffer(); }
/*     */                     else
/*     */                     
/* 294 */                     { GpuTextureAndView textureId = tileChunkTexture.bindColorTexture(true);
/* 295 */                       if (tileChunkTexture.getColorBuffer() == null)
/* 296 */                         tileChunkTexture.prepareBuffer(); 
/* 297 */                       ByteBuffer colorBuffer = tileChunkTexture.getDirectColorBuffer();
/* 298 */                       if (includingHighlights) {
/* 299 */                         tileChunkTexture.applyHighlights(dim.getHighlightHandler(), tileChunkTexture.getColorBuffer());
/*     */                       }
/*     */                       
/* 302 */                       TextureFormat internalFormat = (tileChunkTexture.getColorBufferFormat() == null) ? ExportLeafRegionTexture.DEFAULT_INTERNAL_FORMAT : tileChunkTexture.getColorBufferFormat();
/* 303 */                       OpenGlHelper.uploadBGRABufferToMapTexture(colorBuffer, textureId.texture, internalFormat, 64, 64);
/* 304 */                       tileChunkTexture.deleteColorBuffer();
/*     */                       
/* 306 */                       if (textureId != null)
/*     */                       
/* 308 */                       { textureId.texture.setTextureFilter(FilterMode.LINEAR, FilterMode.NEAREST, true);
/* 309 */                         OpenGlHelper.generateMipmaps(textureId.texture);
/*     */                         
/* 311 */                         GuiMap.renderTexturedModalRectWithLighting3(matrix, (tileChunk.getX() * 64) - frameLeft, (tileChunk.getZ() * 64) - frameTop, 64.0F, 64.0F, textureId.texture, tileChunkTexture.getBufferHasLight(), tileChunkTexture.getBufferHasLight() ? rendererLight : rendererNoLight);
/* 312 */                         renderedSomething = true;
/*     */                         
/* 314 */                         texturesToDelete.add(textureId); }  }  }  } 
/*     */               } 
/* 316 */             }  float brightness = nightExportSetting ? mapProcessor.getAmbientBrightness(dim.getDimensionType(dimensionTypes)) : mapProcessor.getBrightness(exportedLayer, mapProcessor.getWorld(), (WorldMap.settings.lighting && exportedLayer != Integer.MAX_VALUE));
/* 317 */             WorldMapShaderHelper.setBrightness(brightness);
/* 318 */             WorldMapShaderHelper.setWithLight(true);
/* 319 */             rendererProvider.draw(rendererLight);
/* 320 */             WorldMapShaderHelper.setWithLight(false);
/* 321 */             rendererProvider.draw(rendererNoLight);
/* 322 */             OpenGlHelper.deleteTextures(texturesToDelete, texturesToDelete.size()); continue;
/*     */           } 
/* 324 */         }  matrixStack.method_22909();
/*     */ 
/*     */         
/* 327 */         ImmediateRenderUtil.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
/*     */         
/* 329 */         if (renderedSomething) {
/*     */           
/* 331 */           empty = false;
/* 332 */           frameDataBuffer.clear();
/* 333 */           OpenGlHelper.downloadMapTextureToBGRABuffer(exportFrameBuffer.method_30277(), frameDataBuffer);
/*     */           
/* 335 */           frameDataBuffer.asIntBuffer().get(bufferArray);
/*     */           
/* 337 */           int insertOffsetX = i * frameWidth;
/* 338 */           int insertOffsetZ = j * frameHeight;
/* 339 */           if (multipleImages) {
/* 340 */             insertOffsetX = 0;
/* 341 */             insertOffsetZ = 0;
/*     */           } 
/* 343 */           int actualFrameWidth = Math.min(frameWidth, exportImageWidth - insertOffsetX);
/* 344 */           int actualFrameHeight = Math.min(frameHeight, exportImageHeight - insertOffsetZ);
/* 345 */           image.setRGB(insertOffsetX, insertOffsetZ, actualFrameWidth, actualFrameHeight, bufferArray, 0, frameWidth);
/* 346 */           if (multipleImages) {
/* 347 */             PNGExportResultType saveResult = saveImage(image, imageDestination, "" + i + "_" + i, "_x" + (int)frameLeft + "_z" + (int)frameTop);
/* 348 */             if (saveResult != PNGExportResultType.SUCCESS)
/* 349 */               resultType = saveResult; 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 354 */     class_310 mc = class_310.method_1551();
/* 355 */     exportFrameBuffer.bindDefaultFramebuffer(mc);
/* 356 */     matrixStack.method_22909();
/* 357 */     shaderMatrixStack.popMatrix();
/* 358 */     Misc.minecraftOrtho(mc, SupportMods.vivecraft);
/* 359 */     exportFrameBuffer.method_1238();
/* 360 */     mapProcessor.getBufferDeallocator().deallocate(frameDataBuffer, WorldMap.settings.debug);
/* 361 */     if (empty)
/* 362 */       return new PNGExportResult(PNGExportResultType.EMPTY, null); 
/* 363 */     if (multipleImages) {
/* 364 */       image.flush();
/* 365 */       return new PNGExportResult(resultType, imageDestination);
/*     */     } 
/* 367 */     resultType = saveImage(image, imageDestination, null, "_x" + minBlockX + "_z" + minBlockZ);
/* 368 */     image.flush();
/* 369 */     return new PNGExportResult(resultType, imageDestination); }
/*     */ 
/*     */   
/*     */   private PNGExportResultType saveImage(BufferedImage image, Path destinationPath, String baseName, String suffix) {
/* 373 */     if (baseName == null)
/* 374 */       baseName = getExportBaseName(); 
/* 375 */     baseName = baseName + baseName;
/* 376 */     int additionalIndex = 1;
/*     */     try {
/* 378 */       if (!Files.exists(destinationPath, new java.nio.file.LinkOption[0])) {
/* 379 */         Files.createDirectories(destinationPath, (FileAttribute<?>[])new FileAttribute[0]);
/*     */       }
/* 381 */       Path imagePath = destinationPath.resolve(baseName + ".png");
/* 382 */       while (Files.exists(imagePath, new java.nio.file.LinkOption[0])) {
/* 383 */         additionalIndex++;
/* 384 */         imagePath = destinationPath.resolve(baseName + "_" + baseName + ".png");
/*     */       } 
/* 386 */       ImageIO.write(image, "png", imagePath.toFile());
/* 387 */       return PNGExportResultType.SUCCESS;
/* 388 */     } catch (IOException e1) {
/* 389 */       WorldMap.LOGGER.error("IO exception while exporting PNG: ", e1);
/* 390 */       return PNGExportResultType.IO_EXCEPTION;
/*     */     } 
/*     */   }
/*     */   
/*     */   private String getExportBaseName() {
/* 395 */     this.calendar.setTimeInMillis(System.currentTimeMillis());
/* 396 */     int year = this.calendar.get(1);
/* 397 */     int month = 1 + this.calendar.get(2);
/* 398 */     int day = this.calendar.get(5);
/* 399 */     int hours = this.calendar.get(11);
/* 400 */     int minutes = this.calendar.get(12);
/* 401 */     int seconds = this.calendar.get(13);
/* 402 */     return String.format("%d-%02d-%02d_%02d.%02d.%02d", new Object[] { Integer.valueOf(year), Integer.valueOf(month), Integer.valueOf(day), Integer.valueOf(hours), Integer.valueOf(minutes), Integer.valueOf(seconds) });
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\file\export\PNGExporter.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */