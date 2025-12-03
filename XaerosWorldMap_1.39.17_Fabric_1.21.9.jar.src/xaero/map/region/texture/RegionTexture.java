/*     */ package xaero.map.region.texture;
/*     */ import com.mojang.blaze3d.buffers.GpuBuffer;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import com.mojang.blaze3d.textures.AddressMode;
/*     */ import com.mojang.blaze3d.textures.FilterMode;
/*     */ import com.mojang.blaze3d.textures.GpuTexture;
/*     */ import com.mojang.blaze3d.textures.TextureFormat;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.class_1959;
/*     */ import net.minecraft.class_5321;
/*     */ import xaero.map.MapProcessor;
/*     */ import xaero.map.WorldMap;
/*     */ import xaero.map.biome.BlockTintProvider;
/*     */ import xaero.map.cache.BlockStateShortShapeCache;
/*     */ import xaero.map.exception.OpenGLException;
/*     */ import xaero.map.file.IOHelper;
/*     */ import xaero.map.graphics.GpuTextureAndView;
/*     */ import xaero.map.graphics.OpenGlHelper;
/*     */ import xaero.map.graphics.TextureUploader;
/*     */ import xaero.map.highlight.DimensionHighlighterHandler;
/*     */ import xaero.map.misc.ConsistentBitArray;
/*     */ import xaero.map.palette.FastIntPalette;
/*     */ import xaero.map.palette.Paletted2DFastBitArrayIntStorage;
/*     */ import xaero.map.pool.PoolUnit;
/*     */ import xaero.map.pool.buffer.PoolTextureDirectBufferUnit;
/*     */ import xaero.map.region.BranchLeveledRegion;
/*     */ import xaero.map.region.LeveledRegion;
/*     */ import xaero.map.region.MapRegion;
/*     */ import xaero.map.region.OverlayManager;
/*     */ 
/*     */ public abstract class RegionTexture<T extends RegionTexture<T>> {
/*  38 */   public static final TextureFormat DEFAULT_INTERNAL_FORMAT = TextureFormat.RGBA8;
/*     */   public static final int PBO_UNPACK_LENGTH = 16384;
/*     */   public static final int PBO_PACK_LENGTH = 16384;
/*     */   private static final long[] ONE_BIOME_PALETTE_DATA;
/*     */   
/*     */   static {
/*  44 */     ConsistentBitArray dataStorage = new ConsistentBitArray(13, 4096);
/*  45 */     for (int i = 0; i < 4096; i++)
/*  46 */       dataStorage.set(i, 1); 
/*  47 */     ONE_BIOME_PALETTE_DATA = dataStorage.getData();
/*     */   }
/*     */   
/*  50 */   private static final ThreadLocal<ConsistentBitArray> OLD_HEIGHT_VALUES_SUPPORT = ThreadLocal.withInitial(() -> new ConsistentBitArray(9, 4096));
/*     */   
/*     */   protected int textureVersion;
/*     */   protected GpuTextureAndView glColorTexture;
/*     */   protected boolean textureHasLight;
/*     */   protected PoolTextureDirectBufferUnit colorBuffer;
/*     */   protected boolean bufferHasLight;
/*     */   protected TextureFormat colorBufferFormat;
/*     */   protected int bufferedTextureVersion;
/*     */   protected GpuBuffer packPbo;
/*     */   protected GpuBuffer[] unpackPbo;
/*     */   protected boolean shouldDownloadFromPBO;
/*     */   protected int timer;
/*     */   private boolean cachePrepared;
/*     */   protected boolean toUpload;
/*     */   protected LeveledRegion<T> region;
/*     */   protected ConsistentBitArray heightValues;
/*     */   protected ConsistentBitArray topHeightValues;
/*     */   protected RegionTextureBiomes biomes;
/*     */   
/*     */   public RegionTexture(LeveledRegion<T> region) {
/*  71 */     this.glColorTexture = null;
/*  72 */     this.unpackPbo = new GpuBuffer[2];
/*  73 */     this.colorBufferFormat = null;
/*  74 */     this.region = region;
/*  75 */     this.bufferedTextureVersion = this.textureVersion = -1;
/*  76 */     this.heightValues = new ConsistentBitArray(13, 4096);
/*  77 */     this.topHeightValues = new ConsistentBitArray(13, 4096);
/*     */   }
/*     */   
/*     */   private void setupTextureParameters() {
/*  81 */     OpenGlHelper.fixMaxLod(this.glColorTexture.texture, getMipMapLevels());
/*  82 */     this.glColorTexture.texture.setTextureFilter(FilterMode.LINEAR, FilterMode.NEAREST, false);
/*  83 */     this.glColorTexture.texture.setAddressMode(AddressMode.CLAMP_TO_EDGE);
/*     */   }
/*     */   
/*     */   public void prepareBuffer() {
/*  87 */     if (this.colorBuffer != null) {
/*  88 */       this.colorBuffer.reset();
/*     */     } else {
/*  90 */       this.colorBuffer = WorldMap.textureDirectBufferPool.get(true);
/*     */     } 
/*     */   }
/*     */   protected int getMipMapLevels() {
/*  94 */     return 1;
/*     */   }
/*     */   
/*     */   public GpuTextureAndView bindColorTexture(boolean create) {
/*  98 */     boolean result = false;
/*  99 */     GpuTextureAndView texture = this.glColorTexture;
/* 100 */     if (texture == null)
/* 101 */       if (create) {
/* 102 */         int levels = getMipMapLevels();
/* 103 */         GpuTexture createdTexture = RenderSystem.getDevice().createTexture((String)null, 15, DEFAULT_INTERNAL_FORMAT, 64, 64, 1, levels);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 110 */         texture = this.glColorTexture = new GpuTextureAndView(createdTexture, RenderSystem.getDevice().createTextureView(createdTexture));
/* 111 */         result = true;
/*     */       } else {
/* 113 */         return null;
/*     */       }  
/* 115 */     if (result)
/* 116 */       setupTextureParameters(); 
/* 117 */     RenderSystem.setShaderTexture(0, texture.view);
/* 118 */     return texture;
/*     */   }
/*     */   
/*     */   public long uploadBuffer(DimensionHighlighterHandler highlighterHandler, TextureUploader textureUploader, LeveledRegion<T> inRegion, BranchTextureRenderer branchTextureRenderer, int x, int y) throws OpenGLException, IllegalArgumentException, IllegalAccessException {
/* 122 */     long result = uploadBufferHelper(highlighterHandler, textureUploader, inRegion, branchTextureRenderer);
/* 123 */     if (!shouldDownloadFromPBO()) {
/* 124 */       setToUpload(false);
/* 125 */       if (getColorBufferFormat() == null) {
/* 126 */         deleteColorBuffer();
/*     */       } else {
/* 128 */         setCachePrepared(true);
/*     */       } 
/* 130 */     }  return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public void postBufferWrite(PoolTextureDirectBufferUnit buffer) {}
/*     */   
/*     */   private long uploadBufferHelper(DimensionHighlighterHandler highlighterHandler, TextureUploader textureUploader, LeveledRegion<T> inRegion, BranchTextureRenderer branchTextureRenderer) throws OpenGLException, IllegalArgumentException, IllegalAccessException {
/* 137 */     return uploadBufferHelper(highlighterHandler, textureUploader, inRegion, branchTextureRenderer, false);
/*     */   }
/*     */   private long uploadBufferHelper(DimensionHighlighterHandler highlighterHandler, TextureUploader textureUploader, LeveledRegion<T> inRegion, BranchTextureRenderer branchTextureRenderer, boolean retrying) throws OpenGLException, IllegalArgumentException, IllegalAccessException {
/*     */     GpuBuffer.MappedView mappedPBO;
/* 141 */     if (this.colorBufferFormat != null) {
/*     */ 
/*     */       
/* 144 */       PoolTextureDirectBufferUnit colorBufferToUpload = applyHighlights(highlighterHandler, this.colorBuffer, true);
/* 145 */       updateTextureVersion(this.bufferedTextureVersion);
/* 146 */       if (colorBufferToUpload == null)
/* 147 */         return 0L; 
/* 148 */       writeToUnpackPBO(0, colorBufferToUpload);
/* 149 */       TextureFormat internalFormat = this.colorBufferFormat;
/* 150 */       this.textureHasLight = this.bufferHasLight;
/* 151 */       this.colorBufferFormat = null;
/* 152 */       this.bufferedTextureVersion = -1;
/* 153 */       boolean subsequent = (this.glColorTexture != null);
/* 154 */       bindColorTexture(true);
/* 155 */       long totalEstimatedTime = 0L;
/* 156 */       if (this.unpackPbo[0] == null)
/* 157 */         return 0L; 
/* 158 */       if (subsequent) {
/* 159 */         totalEstimatedTime = textureUploader.requestSubsequentNormal(this.glColorTexture, this.unpackPbo[0], 0, 64, 64, 0, 0L, 0, 0);
/*     */       } else {
/* 161 */         totalEstimatedTime = textureUploader.requestNormal(this.glColorTexture, this.unpackPbo[0], 0, internalFormat, 64, 64, 0, 0L);
/* 162 */       }  onCacheUploadRequested();
/* 163 */       return totalEstimatedTime;
/*     */     } 
/* 165 */     if (!this.shouldDownloadFromPBO) {
/* 166 */       return uploadNonCache(highlighterHandler, textureUploader, branchTextureRenderer);
/*     */     }
/* 168 */     ensurePackPBO();
/* 169 */     if (this.packPbo == null) {
/* 170 */       onDownloadedBuffer(null);
/* 171 */       endPBODownload(DEFAULT_INTERNAL_FORMAT, false);
/* 172 */       return 0L;
/*     */     } 
/*     */     
/*     */     try {
/* 176 */       mappedPBO = RenderSystem.getDevice().createCommandEncoder().mapBuffer(this.packPbo, true, false);
/* 177 */     } catch (Throwable t) {
/* 178 */       WorldMap.LOGGER.warn("Exception trying to map PBO", t);
/* 179 */       mappedPBO = null;
/*     */     } 
/* 181 */     if (mappedPBO == null) {
/* 182 */       unbindPackPBO();
/* 183 */       WorldMap.LOGGER.warn("Failed to map PBO {} {} (uploadBufferHelper).", this.packPbo, Boolean.valueOf(retrying));
/* 184 */       this.packPbo.close();
/* 185 */       OpenGlHelper.clearErrors(true, "uploadBufferHelper");
/* 186 */       this.packPbo = null;
/* 187 */       if (retrying) {
/* 188 */         onDownloadedBuffer(null);
/* 189 */         endPBODownload(DEFAULT_INTERNAL_FORMAT, false);
/* 190 */         return 0L;
/*     */       } 
/* 192 */       return uploadBufferHelper(highlighterHandler, textureUploader, inRegion, branchTextureRenderer, true);
/*     */     } 
/* 194 */     OpenGLException.checkGLError();
/*     */     
/* 196 */     onDownloadedBuffer(mappedPBO.data());
/*     */ 
/*     */     
/* 199 */     mappedPBO.close();
/* 200 */     OpenGLException.checkGLError();
/* 201 */     unbindPackPBO();
/* 202 */     OpenGLException.checkGLError();
/* 203 */     endPBODownload(DEFAULT_INTERNAL_FORMAT, true);
/* 204 */     return 0L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PoolTextureDirectBufferUnit applyHighlights(DimensionHighlighterHandler highlighterHandler, PoolTextureDirectBufferUnit colorBuffer, boolean separateBuffer) {
/* 211 */     return colorBuffer;
/*     */   }
/*     */   
/*     */   protected void onDownloadedBuffer(ByteBuffer mappedPBO) {
/* 215 */     ByteBuffer directBuffer = this.colorBuffer.getDirectBuffer();
/* 216 */     directBuffer.clear();
/* 217 */     if (mappedPBO != null) {
/* 218 */       directBuffer.put(mappedPBO);
/* 219 */       directBuffer.flip();
/*     */     } else {
/* 221 */       directBuffer.limit(16384);
/*     */     } 
/*     */   }
/*     */   protected void endPBODownload(TextureFormat format, boolean success) {
/* 225 */     this.bufferHasLight = this.textureHasLight;
/* 226 */     this.colorBufferFormat = format;
/* 227 */     this.shouldDownloadFromPBO = false;
/* 228 */     this.bufferedTextureVersion = this.textureVersion;
/* 229 */     if (format == null)
/* 230 */       throw new RuntimeException("Invalid texture internal format returned by the driver."); 
/*     */   }
/*     */   
/*     */   protected void ensurePackPBO() {
/* 234 */     if (this.packPbo != null)
/*     */       return; 
/* 236 */     this.packPbo = RenderSystem.getDevice().createBuffer(null, 29, 16384);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void ensureUnpackPBO(int index) {
/* 244 */     if (this.unpackPbo[index] != null)
/*     */       return; 
/* 246 */     this.unpackPbo[index] = RenderSystem.getDevice().createBuffer(null, 30, 16384);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void unbindPackPBO() {
/* 254 */     OpenGlHelper.unbindPackBuffer();
/*     */   }
/*     */   
/*     */   private void unbindUnpackPBO() {
/* 258 */     OpenGlHelper.unbindUnpackBuffer();
/*     */   }
/*     */   
/*     */   protected void writeToUnpackPBO(int pboIndex, PoolTextureDirectBufferUnit buffer) throws OpenGLException {
/* 262 */     ensureUnpackPBO(pboIndex);
/* 263 */     if (this.unpackPbo[pboIndex] == null) {
/* 264 */       postBufferWrite(buffer);
/*     */       return;
/*     */     } 
/* 267 */     OpenGlHelper.clearErrors(false, null);
/* 268 */     RenderSystem.getDevice().createCommandEncoder().writeToBuffer(this.unpackPbo[pboIndex].slice(), buffer.getDirectBuffer());
/* 269 */     OpenGlHelper.clearErrors(true, "writeToUnpackPBO");
/* 270 */     postBufferWrite(buffer);
/*     */   }
/*     */   
/*     */   public void deleteColorBuffer() {
/* 274 */     if (this.colorBuffer != null) {
/* 275 */       if (!WorldMap.textureDirectBufferPool.addToPool((PoolUnit)this.colorBuffer))
/* 276 */         WorldMap.bufferDeallocator.deallocate(this.colorBuffer.getDirectBuffer(), WorldMap.settings.debug); 
/* 277 */       this.colorBuffer = null;
/*     */     } 
/* 279 */     this.colorBufferFormat = null;
/* 280 */     this.bufferedTextureVersion = -1;
/*     */   }
/*     */   
/*     */   public void deletePBOs() {
/* 284 */     if (this.packPbo != null)
/* 285 */       WorldMap.gpuObjectDeleter.requestBufferToDelete(this.packPbo); 
/* 286 */     this.packPbo = null;
/* 287 */     for (int i = 0; i < this.unpackPbo.length; i++) {
/* 288 */       if (this.unpackPbo[i] != null) {
/* 289 */         WorldMap.gpuObjectDeleter.requestBufferToDelete(this.unpackPbo[i]);
/* 290 */         this.unpackPbo[i] = null;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeCacheMapData(DataOutputStream output, byte[] usableBuffer, byte[] integerByteBuffer, LeveledRegion<T> inRegion) throws IOException {
/* 296 */     ByteBuffer directBuffer = this.colorBuffer.getDirectBuffer();
/*     */     
/* 298 */     directBuffer.get(usableBuffer, 0, 16384);
/* 299 */     directBuffer.position(0);
/* 300 */     output.write(usableBuffer, 0, 16384);
/* 301 */     output.writeBoolean(this.bufferHasLight);
/* 302 */     long[] heightData = this.heightValues.getData();
/* 303 */     for (int i = 0; i < heightData.length; i++)
/* 304 */       output.writeLong(heightData[i]); 
/* 305 */     long[] topHeightData = this.topHeightValues.getData();
/* 306 */     for (int j = 0; j < topHeightData.length; j++)
/* 307 */       output.writeLong(topHeightData[j]); 
/* 308 */     saveBiomeIndexStorage(output);
/*     */   }
/*     */   
/*     */   public void readCacheData(int minorSaveVersion, int majorSaveVersion, DataInputStream input, byte[] usableBuffer, byte[] integerByteBuffer, LeveledRegion<T> inRegion, MapProcessor mapProcessor, int x, int y, boolean leafShouldAffectBranches) throws IOException {
/* 312 */     if (minorSaveVersion < 7 || (minorSaveVersion >= 9 && minorSaveVersion <= 11)) {
/* 313 */       this.bufferedTextureVersion = 1;
/*     */     } else {
/* 315 */       this.bufferedTextureVersion = inRegion.getAndResetCachedTextureVersion(x, y);
/* 316 */     }  if (minorSaveVersion == 6) {
/* 317 */       input.readInt();
/*     */     }
/* 319 */     int lightLevelsInCache = (minorSaveVersion < 3) ? 4 : 1;
/* 320 */     for (int i = 0; i < lightLevelsInCache; i++) {
/* 321 */       boolean colorBufferCompressed = false;
/* 322 */       if (i == 0) {
/* 323 */         if (majorSaveVersion < 2) {
/* 324 */           colorBufferCompressed = true;
/* 325 */           if (minorSaveVersion > 1)
/* 326 */             colorBufferCompressed = (input.read() == 1); 
/* 327 */           input.readInt();
/*     */         } 
/*     */       } else {
/* 330 */         if (minorSaveVersion > 1)
/* 331 */           input.read(); 
/* 332 */         input.readInt();
/*     */       } 
/* 334 */       int length = 16384;
/* 335 */       if (majorSaveVersion < 2) {
/* 336 */         length = input.readInt();
/*     */       }
/* 338 */       IOHelper.readToBuffer(usableBuffer, length, input);
/* 339 */       if (i == 0) {
/* 340 */         this.colorBufferFormat = DEFAULT_INTERNAL_FORMAT;
/* 341 */         if (inRegion.getLevel() == 0 && colorBufferCompressed) {
/* 342 */           if (this.colorBuffer == null)
/* 343 */             this.colorBuffer = WorldMap.textureDirectBufferPool.get(true); 
/* 344 */           inRegion.setShouldCache(true, "ignoring compressed textures");
/* 345 */           this.colorBuffer.getDirectBuffer().limit(16384);
/*     */         } else {
/* 347 */           if (this.colorBuffer == null)
/* 348 */             this.colorBuffer = WorldMap.textureDirectBufferPool.get(false); 
/* 349 */           ByteBuffer directBuffer = this.colorBuffer.getDirectBuffer();
/* 350 */           directBuffer.put(usableBuffer, 0, length);
/* 351 */           directBuffer.flip();
/*     */         } 
/*     */       } 
/*     */     } 
/* 355 */     if (minorSaveVersion >= 14) {
/* 356 */       this.bufferHasLight = input.readBoolean();
/* 357 */     } else if (minorSaveVersion > 2) {
/* 358 */       int lightLength = input.readInt();
/* 359 */       if (lightLength > 0)
/* 360 */         IOHelper.readToBuffer(usableBuffer, lightLength, input); 
/* 361 */       this.bufferHasLight = false;
/*     */     } 
/* 363 */     if (minorSaveVersion >= 13) {
/* 364 */       long[] heightData = new long[(majorSaveVersion == 0) ? 586 : 1024]; int j;
/* 365 */       for (j = 0; j < heightData.length; j++)
/* 366 */         heightData[j] = input.readLong(); 
/* 367 */       if (majorSaveVersion == 0) {
/* 368 */         ConsistentBitArray oldHeightArray = OLD_HEIGHT_VALUES_SUPPORT.get();
/* 369 */         oldHeightArray.setData(heightData);
/* 370 */         for (int k = 0; k < 4096; k++) {
/* 371 */           int oldValue = oldHeightArray.get(k);
/* 372 */           if (oldValue >> 8 != 0)
/* 373 */             putHeight(k, oldValue & 0xFF); 
/*     */         } 
/*     */       } else {
/* 376 */         this.heightValues.setData(heightData);
/* 377 */       }  if (minorSaveVersion >= 17) {
/* 378 */         long[] topHeightData = new long[(majorSaveVersion == 0) ? 586 : 1024];
/* 379 */         for (int k = 0; k < topHeightData.length; k++)
/* 380 */           topHeightData[k] = input.readLong(); 
/* 381 */         if (majorSaveVersion == 0) {
/* 382 */           ConsistentBitArray oldHeightArray = OLD_HEIGHT_VALUES_SUPPORT.get();
/* 383 */           oldHeightArray.setData(topHeightData);
/* 384 */           for (int m = 0; m < 4096; m++) {
/* 385 */             int oldValue = oldHeightArray.get(m);
/* 386 */             if (oldValue >> 8 != 0)
/* 387 */               putTopHeight(m, oldValue & 0xFF); 
/*     */           } 
/*     */         } else {
/* 390 */           this.topHeightValues.setData(topHeightData);
/*     */         } 
/*     */       } else {
/* 393 */         long[] copyFrom = this.heightValues.getData();
/* 394 */         long[] topHeightData = new long[(this.topHeightValues.getData()).length];
/* 395 */         System.arraycopy(copyFrom, 0, topHeightData, 0, copyFrom.length);
/* 396 */         this.topHeightValues.setData(topHeightData);
/*     */       } 
/* 398 */       loadBiomeIndexStorage(input, minorSaveVersion, majorSaveVersion);
/* 399 */       if (minorSaveVersion == 16)
/* 400 */         for (j = 0; j < 64; j++) {
/* 401 */           input.readLong();
/*     */         } 
/*     */     } 
/* 404 */     this.toUpload = true;
/*     */   }
/*     */   
/*     */   private void saveBiomeIndexStorage(DataOutputStream output) throws IOException {
/* 408 */     Paletted2DFastBitArrayIntStorage biomeIndexStorage = (this.biomes == null) ? null : this.biomes.getBiomeIndexStorage();
/* 409 */     int paletteSize = (biomeIndexStorage == null) ? 0 : biomeIndexStorage.getPaletteSize();
/* 410 */     if (paletteSize > 0) {
/* 411 */       if (this.region.getBiomePalette() == null)
/* 412 */         throw new RuntimeException("saving biomes for a texture in a biomeless region"); 
/* 413 */       if (biomeIndexStorage.getPaletteNonNullCount() > 1 || biomeIndexStorage.getDefaultValueCount() != 0) {
/* 414 */         output.writeInt(paletteSize);
/* 415 */         for (int i = 0; i < paletteSize; i++) {
/* 416 */           int paletteElement = biomeIndexStorage.getPaletteElement(i);
/* 417 */           output.writeInt(paletteElement);
/* 418 */           if (paletteElement != -1)
/* 419 */             output.writeShort(biomeIndexStorage.getPaletteElementCount(i)); 
/*     */         } 
/* 421 */         output.write(1);
/* 422 */         biomeIndexStorage.writeData(output);
/*     */       } else {
/*     */         
/* 425 */         int paletteElement = biomeIndexStorage.getPaletteElement(paletteSize - 1);
/* 426 */         int paletteElementCount = biomeIndexStorage.getPaletteElementCount(paletteSize - 1);
/* 427 */         output.writeInt(1);
/* 428 */         output.writeInt(paletteElement);
/* 429 */         output.writeShort(paletteElementCount);
/* 430 */         output.write(0);
/*     */       } 
/*     */     } else {
/* 433 */       output.writeInt(0);
/*     */     } 
/*     */   }
/*     */   private void loadBiomeIndexStorage(DataInputStream input, int minorSaveVersion, int majorSaveVersion) throws IOException {
/* 437 */     if (minorSaveVersion >= 19) {
/* 438 */       int paletteSize = input.readInt();
/* 439 */       if (paletteSize > 0) {
/* 440 */         int defaultValueCount = 4096;
/* 441 */         FastIntPalette fastIntPalette = FastIntPalette.Builder.begin().setMaxCountPerElement(4096).build();
/* 442 */         for (int i = 0; i < paletteSize; i++) {
/* 443 */           int paletteElementValue = input.readInt();
/* 444 */           if (paletteElementValue == -1) {
/* 445 */             fastIntPalette.addNull();
/*     */           } else {
/* 447 */             int count = input.readShort() & 0xFFFF;
/* 448 */             fastIntPalette.append(paletteElementValue, count);
/* 449 */             defaultValueCount -= count;
/*     */           } 
/*     */         } 
/*     */         
/* 453 */         long[] data = new long[1024];
/* 454 */         if (minorSaveVersion == 19 || input.read() == 1) {
/* 455 */           for (int j = 0; j < data.length; j++)
/* 456 */             data[j] = input.readLong(); 
/*     */         } else {
/* 458 */           System.arraycopy(ONE_BIOME_PALETTE_DATA, 0, data, 0, data.length);
/* 459 */         }  ConsistentBitArray dataStorage = new ConsistentBitArray(13, 4096, data);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 466 */         Paletted2DFastBitArrayIntStorage biomeIndexStorage = Paletted2DFastBitArrayIntStorage.Builder.begin().setPalette(fastIntPalette).setData(dataStorage).setWidth(64).setHeight(64).setDefaultValueCount(defaultValueCount).setMaxPaletteElements(4096).build();
/* 467 */         if (this.region.getBiomePalette() != null) {
/* 468 */           for (int j = 0; j < fastIntPalette.getSize(); j++) {
/* 469 */             int paletteElement = fastIntPalette.get(j, -1);
/* 470 */             if (paletteElement != -1)
/* 471 */               this.region.getBiomePalette().count(paletteElement, true); 
/*     */           } 
/* 473 */           this.biomes = new RegionTextureBiomes(biomeIndexStorage, this.region.getBiomePalette());
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void deleteTexturesAndBuffers() {
/* 481 */     GpuTextureAndView textureToDelete = getGlColorTexture();
/* 482 */     this.glColorTexture = null;
/* 483 */     if (textureToDelete != null)
/* 484 */       WorldMap.gpuObjectDeleter.requestTextureDeletion(textureToDelete); 
/* 485 */     onTextureDeletion();
/* 486 */     if (getColorBuffer() != null)
/* 487 */       deleteColorBuffer(); 
/* 488 */     deletePBOs();
/*     */   }
/*     */   
/*     */   public PoolTextureDirectBufferUnit getColorBuffer() {
/* 492 */     return this.colorBuffer;
/*     */   }
/*     */   
/*     */   public ByteBuffer getDirectColorBuffer() {
/* 496 */     return (this.colorBuffer == null) ? null : this.colorBuffer.getDirectBuffer();
/*     */   }
/*     */   
/*     */   public void setShouldDownloadFromPBO(boolean shouldDownloadFromPBO) {
/* 500 */     this.shouldDownloadFromPBO = shouldDownloadFromPBO;
/*     */   }
/*     */   
/*     */   public TextureFormat getColorBufferFormat() {
/* 504 */     return this.colorBufferFormat;
/*     */   }
/*     */   
/*     */   public boolean shouldDownloadFromPBO() {
/* 508 */     return this.shouldDownloadFromPBO;
/*     */   }
/*     */   
/*     */   public int getTimer() {
/* 512 */     return this.timer;
/*     */   }
/*     */   
/*     */   public void decTimer() {
/* 516 */     this.timer--;
/*     */   }
/*     */   
/*     */   public void resetTimer() {
/* 520 */     this.timer = 0;
/*     */   }
/*     */   
/*     */   public final GpuTextureAndView getGlColorTexture() {
/* 524 */     return this.glColorTexture;
/*     */   }
/*     */   
/*     */   public void onTextureDeletion() {
/* 528 */     updateTextureVersion(0);
/*     */   }
/*     */   
/*     */   public boolean shouldUpload() {
/* 532 */     return this.toUpload;
/*     */   }
/*     */   
/*     */   public void setToUpload(boolean value) {
/* 536 */     this.toUpload = value;
/*     */   }
/*     */   
/*     */   public boolean isCachePrepared() {
/* 540 */     return this.cachePrepared;
/*     */   }
/*     */   
/*     */   public void setCachePrepared(boolean cachePrepared) {
/* 544 */     this.cachePrepared = cachePrepared;
/*     */   }
/*     */   
/*     */   public boolean canUpload() {
/* 548 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isUploaded() {
/* 552 */     return !shouldUpload();
/*     */   }
/*     */   
/*     */   public int getTextureVersion() {
/* 556 */     return this.textureVersion;
/*     */   }
/*     */   
/*     */   public int getBufferedTextureVersion() {
/* 560 */     return this.bufferedTextureVersion;
/*     */   }
/*     */   
/*     */   public void setBufferedTextureVersion(int bufferedTextureVersion) {
/* 564 */     this.bufferedTextureVersion = bufferedTextureVersion;
/*     */   }
/*     */   
/*     */   public LeveledRegion<T> getRegion() {
/* 568 */     return this.region;
/*     */   }
/*     */   
/*     */   protected void updateTextureVersion(int newVersion) {
/* 572 */     this.textureVersion = newVersion;
/*     */   }
/*     */   
/*     */   public int getHeight(int x, int z) {
/* 576 */     int index = (z << 6) + x;
/* 577 */     int value = this.heightValues.get(index);
/* 578 */     if (value >> 12 == 0)
/* 579 */       return 32767; 
/* 580 */     return (value & 0xFFF) << 20 >> 20;
/*     */   }
/*     */   
/*     */   public void putHeight(int x, int z, int height) {
/* 584 */     int index = (z << 6) + x;
/* 585 */     putHeight(index, height);
/*     */   }
/*     */   
/*     */   public void putHeight(int index, int height) {
/* 589 */     int value = 0x1000 | height & 0xFFF;
/* 590 */     this.heightValues.set(index, value);
/*     */   }
/*     */   
/*     */   public void removeHeight(int x, int z) {
/* 594 */     int index = (z << 6) + x;
/* 595 */     this.heightValues.set(index, 0);
/*     */   }
/*     */   
/*     */   public int getTopHeight(int x, int z) {
/* 599 */     int index = (z << 6) + x;
/* 600 */     int value = this.topHeightValues.get(index);
/* 601 */     if (value >> 12 == 0)
/* 602 */       return 32767; 
/* 603 */     return (value & 0xFFF) << 20 >> 20;
/*     */   }
/*     */   
/*     */   public void putTopHeight(int x, int z, int height) {
/* 607 */     int index = (z << 6) + x;
/* 608 */     putTopHeight(index, height);
/*     */   }
/*     */   
/*     */   public void putTopHeight(int index, int height) {
/* 612 */     int value = 0x1000 | height & 0xFFF;
/* 613 */     this.topHeightValues.set(index, value);
/*     */   }
/*     */   
/*     */   public void removeTopHeight(int x, int z) {
/* 617 */     int index = (z << 6) + x;
/* 618 */     this.topHeightValues.set(index, 0);
/*     */   }
/*     */   
/*     */   public void ensureBiomeIndexStorage() {
/* 622 */     if (this.biomes == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 628 */       Paletted2DFastBitArrayIntStorage biomeIndexStorage = Paletted2DFastBitArrayIntStorage.Builder.begin().setMaxPaletteElements(4096).setDefaultValue(-1).setWidth(64).setHeight(64).build();
/* 629 */       this.region.ensureBiomePalette();
/* 630 */       this.biomes = new RegionTextureBiomes(biomeIndexStorage, this.region.getBiomePalette());
/*     */     } 
/*     */   }
/*     */   
/*     */   public class_5321<class_1959> getBiome(int x, int z) {
/* 635 */     RegionTextureBiomes biomes = this.biomes;
/* 636 */     if (biomes == null)
/* 637 */       return null; 
/* 638 */     int biomePaletteIndex = biomes.getBiomeIndexStorage().get(x, z);
/* 639 */     if (biomePaletteIndex == -1)
/* 640 */       return null; 
/* 641 */     return (class_5321<class_1959>)biomes.getRegionBiomePalette().get(biomePaletteIndex);
/*     */   }
/*     */   
/*     */   public void setBiome(int x, int z, class_5321<class_1959> biome) {
/* 645 */     ensureBiomeIndexStorage();
/* 646 */     Paletted2DFastBitArrayIntStorage biomeIndexStorage = this.biomes.getBiomeIndexStorage();
/* 647 */     int currentBiomePaletteIndex = biomeIndexStorage.get(x, z);
/* 648 */     int biomePaletteIndex = (biome == null) ? -1 : this.region.getBiomePaletteIndex(biome);
/* 649 */     if (biome != null && (biomePaletteIndex == -1 || !biomeIndexStorage.contains(biomePaletteIndex))) {
/* 650 */       biomePaletteIndex = this.region.onBiomeAddedToTexture(biome);
/* 651 */     } else if (biomePaletteIndex == currentBiomePaletteIndex) {
/*     */       return;
/*     */     }  try {
/* 654 */       biomeIndexStorage.set(x, z, biomePaletteIndex);
/* 655 */     } catch (Throwable t) {
/*     */       
/* 657 */       WorldMap.LOGGER.error("weird biomes " + String.valueOf(this.region) + " pixel x:" + x + " z:" + z + " " + currentBiomePaletteIndex + " " + biomePaletteIndex, t);
/* 658 */       for (int i = 0; i < 8; i++) {
/* 659 */         for (int j = 0; j < 8; j++) {
/* 660 */           if (this.region.getTexture(i, j) == this)
/* 661 */             WorldMap.LOGGER.info("texture " + i + " " + j); 
/*     */         } 
/*     */       } 
/* 664 */       WorldMap.LOGGER.error(biomeIndexStorage.getBiomePaletteDebug());
/* 665 */       int[] realCounts = new int[biomeIndexStorage.getPaletteSize()];
/* 666 */       for (int p = 0; p < 64; p++) {
/* 667 */         String line = "";
/* 668 */         for (int o = 0; o < 64; o++) {
/* 669 */           int rawIndex = biomeIndexStorage.getRaw(o, p) - 1;
/* 670 */           line = line + " " + line;
/* 671 */           if (rawIndex >= 0 && rawIndex < realCounts.length)
/* 672 */             realCounts[rawIndex] = realCounts[rawIndex] + 1; 
/*     */         } 
/* 674 */         WorldMap.LOGGER.error(line);
/*     */       } 
/* 676 */       WorldMap.LOGGER.error("real counts: " + Arrays.toString(realCounts));
/*     */ 
/*     */       
/* 679 */       WorldMap.LOGGER.error("suppressed exception", t);
/* 680 */       this.region.setShouldCache(true, "broken cache biome data");
/* 681 */       if (this.region.getLevel() > 0) {
/* 682 */         this.textureVersion = (new Random()).nextInt();
/* 683 */         ((BranchLeveledRegion)this.region).setShouldCheckForUpdatesRecursive(true);
/*     */       } else {
/* 685 */         ((MapRegion)this.region).setCacheHashCode(0);
/* 686 */       }  this.biomes = null;
/*     */     } 
/* 688 */     if (currentBiomePaletteIndex != -1 && !biomeIndexStorage.contains(currentBiomePaletteIndex))
/* 689 */       this.region.onBiomeRemovedFromTexture(currentBiomePaletteIndex); 
/*     */   }
/*     */   
/*     */   public boolean getTextureHasLight() {
/* 693 */     return this.textureHasLight;
/*     */   }
/*     */   
/*     */   public void addDebugLines(List<String> debugLines) {
/* 697 */     debugLines.add("shouldUpload: " + shouldUpload() + " timer: " + getTimer());
/* 698 */     debugLines.add(String.format("buffer exists: %s", new Object[] { Boolean.valueOf((getColorBuffer() != null)) }));
/* 699 */     debugLines.add("glColorTexture: " + String.valueOf(getGlColorTexture()) + " textureHasLight: " + this.textureHasLight);
/* 700 */     debugLines.add("cachePrepared: " + isCachePrepared());
/* 701 */     debugLines.add("textureVersion: " + this.textureVersion);
/* 702 */     debugLines.add("colorBufferFormat: " + String.valueOf(this.colorBufferFormat));
/* 703 */     if (this.biomes != null)
/* 704 */       debugLines.add(this.biomes.getBiomeIndexStorage().getBiomePaletteDebug()); 
/*     */   }
/*     */   
/*     */   protected void onCacheUploadRequested() {}
/*     */   
/*     */   public boolean shouldBeUsedForBranchUpdate(int usedVersion) {
/* 710 */     return ((shouldHaveContentForBranchUpdate() ? this.textureVersion : 0) != usedVersion);
/*     */   }
/*     */   
/*     */   public boolean shouldHaveContentForBranchUpdate() {
/* 714 */     return true;
/*     */   }
/*     */   
/*     */   public boolean shouldIncludeInCache() {
/* 718 */     return true;
/*     */   }
/*     */   
/*     */   public RegionTextureBiomes getBiomes() {
/* 722 */     return this.biomes;
/*     */   }
/*     */   
/*     */   public void resetBiomes() {
/* 726 */     this.biomes = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getBufferHasLight() {
/* 735 */     return this.bufferHasLight;
/*     */   }
/*     */   
/*     */   public abstract boolean hasSourceData();
/*     */   
/*     */   public abstract void preUpload(MapProcessor paramMapProcessor, BlockTintProvider paramBlockTintProvider, OverlayManager paramOverlayManager, LeveledRegion<T> paramLeveledRegion, boolean paramBoolean, BlockStateShortShapeCache paramBlockStateShortShapeCache);
/*     */   
/*     */   public abstract void postUpload(MapProcessor paramMapProcessor, LeveledRegion<T> paramLeveledRegion, boolean paramBoolean);
/*     */   
/*     */   protected abstract long uploadNonCache(DimensionHighlighterHandler paramDimensionHighlighterHandler, TextureUploader paramTextureUploader, BranchTextureRenderer paramBranchTextureRenderer);
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\region\texture\RegionTexture.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */