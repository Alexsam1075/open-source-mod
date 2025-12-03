/*     */ package xaero.map.file.worldsave;
/*     */ 
/*     */ import com.mojang.datafixers.DataFixer;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.ExecutionException;
/*     */ import net.minecraft.class_1923;
/*     */ import net.minecraft.class_1937;
/*     */ import net.minecraft.class_1959;
/*     */ import net.minecraft.class_1972;
/*     */ import net.minecraft.class_2246;
/*     */ import net.minecraft.class_2248;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_2378;
/*     */ import net.minecraft.class_2487;
/*     */ import net.minecraft.class_2499;
/*     */ import net.minecraft.class_2507;
/*     */ import net.minecraft.class_2512;
/*     */ import net.minecraft.class_2520;
/*     */ import net.minecraft.class_2680;
/*     */ import net.minecraft.class_2688;
/*     */ import net.minecraft.class_2806;
/*     */ import net.minecraft.class_2861;
/*     */ import net.minecraft.class_2960;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_3218;
/*     */ import net.minecraft.class_3508;
/*     */ import net.minecraft.class_3532;
/*     */ import net.minecraft.class_3610;
/*     */ import net.minecraft.class_3611;
/*     */ import net.minecraft.class_3619;
/*     */ import net.minecraft.class_3898;
/*     */ import net.minecraft.class_3977;
/*     */ import net.minecraft.class_4284;
/*     */ import net.minecraft.class_4543;
/*     */ import net.minecraft.class_5321;
/*     */ import net.minecraft.class_6490;
/*     */ import net.minecraft.class_7225;
/*     */ import net.minecraft.class_7871;
/*     */ import xaero.map.MapProcessor;
/*     */ import xaero.map.WorldMap;
/*     */ import xaero.map.cache.BlockStateShortShapeCache;
/*     */ import xaero.map.executor.Executor;
/*     */ import xaero.map.file.worldsave.biome.WorldDataBiomeManager;
/*     */ import xaero.map.file.worldsave.biome.WorldDataReaderSectionBiomeData;
/*     */ import xaero.map.misc.CachedFunction;
/*     */ import xaero.map.mods.SupportMods;
/*     */ import xaero.map.pool.PoolUnit;
/*     */ import xaero.map.region.MapBlock;
/*     */ import xaero.map.region.MapRegion;
/*     */ import xaero.map.region.MapTile;
/*     */ import xaero.map.region.MapTileChunk;
/*     */ import xaero.map.region.OverlayBuilder;
/*     */ import xaero.map.region.OverlayManager;
/*     */ import xaero.map.region.texture.RegionTexture;
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
/*     */ public class WorldDataReader
/*     */ {
/*  89 */   private MapBlock buildingObject = new MapBlock();
/*  90 */   private boolean[] underair = new boolean[256];
/*  91 */   private boolean[] shouldEnterGround = new boolean[256];
/*  92 */   private boolean[] blockFound = new boolean[256];
/*  93 */   private byte[] lightLevels = new byte[256];
/*  94 */   private byte[] skyLightLevels = new byte[256];
/*  95 */   private OverlayBuilder[] overlayBuilders = new OverlayBuilder[256];
/*  96 */   private class_2338.class_2339 mutableBlockPos = new class_2338.class_2339();
/*  97 */   private List<class_2680> blockStatePalette = new ArrayList<>();
/*     */   
/*  99 */   private class_6490 heightMapBitArray = (class_6490)new class_3508(9, 256);
/*     */   
/* 101 */   public Object taskCreationSync = new Object(); private MapProcessor mapProcessor; private int[] topH; private class_6490 blockStatesBitArray; private CompletableFuture<Optional<class_2487>>[] chunkNBTCompounds; private BlockStateShortShapeCache blockStateShortShapeCache; private class_5321<class_1959> defaultBiomeKey; public WorldDataReader(OverlayManager overlayManager, BlockStateShortShapeCache blockStateShortShapeCache, WorldDataBiomeManager biomeManager, long biomeZoomSeed) {
/* 102 */     for (int i = 0; i < this.overlayBuilders.length; i++) {
/* 103 */       this.overlayBuilders[i] = new OverlayBuilder(overlayManager);
/*     */     }
/* 105 */     CompletableFuture[] arrayOfCompletableFuture = new CompletableFuture[16];
/* 106 */     this.chunkNBTCompounds = (CompletableFuture<Optional<class_2487>>[])arrayOfCompletableFuture;
/* 107 */     this.topH = new int[256];
/* 108 */     this.blockStateShortShapeCache = blockStateShortShapeCache;
/* 109 */     this.defaultBiomeKey = class_1972.field_9473;
/* 110 */     this.transparentCache = new CachedFunction(state -> Boolean.valueOf(this.mapProcessor.getMapWriter().shouldOverlay(state)));
/* 111 */     this.shouldExtendTillTheBottom = new boolean[256];
/* 112 */     this.firstTransparentStateY = new int[256];
/* 113 */     this.fluidToBlock = new CachedFunction(class_3610::method_15759);
/* 114 */     this.biomeManager = biomeManager;
/* 115 */     this.biomeZoomer = new class_4543((class_4543.class_4544)biomeManager, biomeZoomSeed);
/*     */   }
/*     */   private final CachedFunction<class_2688<?, ?>, Boolean> transparentCache; private int[] firstTransparentStateY; private boolean[] shouldExtendTillTheBottom; private CachedFunction<class_3610, class_2680> fluidToBlock; private WorldDataBiomeManager biomeManager; private final class_4543 biomeZoomer;
/*     */   public void setMapProcessor(MapProcessor mapProcessor) {
/* 119 */     this.mapProcessor = mapProcessor;
/*     */   }
/*     */   
/*     */   private void updateHeightArray(int bitsPerHeight) {
/* 123 */     if (this.heightMapBitArray.method_34896() != bitsPerHeight) {
/* 124 */       this.heightMapBitArray = (class_6490)new class_3508(bitsPerHeight, 256);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean buildRegion(MapRegion region, class_3218 serverWorld, class_7225<class_2248> blockLookup, class_2378<class_2248> blockRegistry, class_2378<class_3611> fluidRegistry, boolean loading, int[] chunkCountDest, Executor renderExecutor) {
/* 129 */     if (!loading)
/* 130 */       region.pushWriterPause(); 
/* 131 */     boolean result = true;
/* 132 */     int prevRegX = region.getRegionX();
/* 133 */     int prevRegZ = region.getRegionZ() - 1;
/* 134 */     MapRegion prevRegion = this.mapProcessor.getLeafMapRegion(region.getCaveLayer(), prevRegX, prevRegZ, false);
/*     */     
/* 136 */     region.updateCaveMode();
/* 137 */     int caveStart = region.getCaveStart();
/* 138 */     int caveDepth = region.getCaveDepth();
/* 139 */     boolean worldHasSkylight = serverWorld.method_8597().comp_642();
/* 140 */     boolean ignoreHeightmaps = this.mapProcessor.getMapWorld().isIgnoreHeightmaps();
/* 141 */     boolean flowers = WorldMap.settings.flowers;
/* 142 */     if (loading || region.getLoadState() == 2) {
/* 143 */       serverWorld.method_8503().method_20493(() -> serverWorld.method_14178().method_17298(false)).join();
/* 144 */       int worldBottomY = serverWorld.method_31607();
/* 145 */       int worldTopY = serverWorld.method_31600() + 1;
/* 146 */       class_3898 chunkManager = (serverWorld.method_14178()).field_17254;
/*     */ 
/*     */ 
/*     */       
/* 150 */       class_2378<class_1959> biomeRegistry = region.getBiomeRegistry();
/* 151 */       class_1959 theVoid = (class_1959)biomeRegistry.method_29107(class_1972.field_9473);
/* 152 */       this.biomeManager.resetChunkBiomeData(region.getRegionX(), region.getRegionZ(), theVoid, biomeRegistry);
/* 153 */       CompletableFuture<?> lastFuture = null;
/* 154 */       for (int i = -1; i < 9; i++) {
/* 155 */         for (int j = -1; j < 9; j++) {
/* 156 */           if (i < 0 || j < 0 || i >= 8 || j >= 8) {
/* 157 */             handleTileChunkOutsideRegion(i, j, (region.getRegionX() << 3) + i, (region.getRegionZ() << 3) + j, caveStart, ignoreHeightmaps, biomeRegistry, flowers, (class_3977)chunkManager);
/*     */           } else {
/* 159 */             MapTileChunk tileChunk = region.getChunk(i, j);
/* 160 */             if (tileChunk == null) {
/* 161 */               region.setChunk(i, j, tileChunk = new MapTileChunk(region, (region.getRegionX() << 3) + i, (region.getRegionZ() << 3) + j));
/* 162 */               synchronized (region) {
/* 163 */                 region.setAllCachePrepared(false);
/*     */               } 
/*     */             } 
/* 166 */             if (region.isMetaLoaded())
/* 167 */               tileChunk.getLeafTexture().setBufferedTextureVersion(region.getAndResetCachedTextureVersion(i, j)); 
/* 168 */             readChunkNBTCompounds((class_3977)chunkManager, tileChunk);
/* 169 */             buildTileChunk(tileChunk, caveStart, caveDepth, worldHasSkylight, ignoreHeightmaps, prevRegion, serverWorld, blockLookup, blockRegistry, fluidRegistry, biomeRegistry, flowers, worldBottomY, worldTopY);
/* 170 */             if (!tileChunk.includeInSave() && !tileChunk.hasHighlightsIfUndiscovered()) {
/* 171 */               region.uncountTextureBiomes((RegionTexture)tileChunk.getLeafTexture());
/* 172 */               region.setChunk(i, j, null);
/* 173 */               tileChunk.getLeafTexture().deleteTexturesAndBuffers();
/* 174 */               tileChunk = null;
/*     */             } else {
/* 176 */               if (!loading && !tileChunk.includeInSave() && tileChunk.hasHadTerrain()) {
/* 177 */                 tileChunk.getLeafTexture().deleteColorBuffer();
/* 178 */                 tileChunk.unsetHasHadTerrain();
/* 179 */                 tileChunk.setChanged(false);
/*     */               } 
/* 181 */               if (chunkCountDest != null)
/* 182 */                 chunkCountDest[0] = chunkCountDest[0] + 1; 
/*     */             } 
/*     */           } 
/* 185 */           if (i > 0 && j > 0) {
/* 186 */             MapTileChunk topLeftTileChunk = region.getChunk(i - 1, j - 1);
/* 187 */             if (topLeftTileChunk != null && topLeftTileChunk.includeInSave()) {
/* 188 */               fillBiomes(topLeftTileChunk, this.biomeZoomer, biomeRegistry);
/* 189 */               lastFuture = renderExecutor.method_20493(() -> {
/*     */                     transferFilledBiomes(topLeftTileChunk, this.biomeZoomer, biomeRegistry);
/*     */                     topLeftTileChunk.setToUpdateBuffers(true);
/*     */                     topLeftTileChunk.setChanged(false);
/*     */                     topLeftTileChunk.setLoadState((byte)2);
/*     */                   });
/*     */             } 
/* 196 */             if (lastFuture != null && i == 8 && j == 8)
/* 197 */               lastFuture.join(); 
/*     */           } 
/*     */         } 
/* 200 */       }  this.biomeManager.clear();
/* 201 */       if (region.isNormalMapData()) {
/* 202 */         region.setLastSaveTime(System.currentTimeMillis() - 60000L + 1500L);
/*     */       }
/*     */     } else {
/*     */       
/* 206 */       result = false;
/* 207 */     }  if (!loading)
/* 208 */       region.popWriterPause(); 
/* 209 */     return result;
/*     */   }
/*     */   
/*     */   private void readChunkNBTCompounds(class_3977 chunkLoader, MapTileChunk tileChunk) {
/* 213 */     for (int xl = 0; xl < 4; xl++) {
/* 214 */       for (int zl = 0; zl < 4; zl++) {
/* 215 */         int i = zl << 2 | xl;
/* 216 */         this.chunkNBTCompounds[i] = chunkLoader.method_23696(new class_1923(tileChunk.getX() * 4 + xl, tileChunk.getZ() * 4 + zl));
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
/*     */   public class_2487 readChunk(class_2861 regionFile, class_1923 pos) throws IOException
/*     */   {
/* 253 */     DataInputStream datainputstream = regionFile.method_21873(pos); 
/* 254 */     try { if (datainputstream != null)
/* 255 */       { class_2487 class_24871 = class_2507.method_10627(datainputstream);
/*     */         
/* 257 */         if (datainputstream != null) datainputstream.close();  return class_24871; }  class_2487 class_2487 = null; if (datainputstream != null) datainputstream.close();  return class_2487; } catch (Throwable throwable) { if (datainputstream != null)
/*     */         try { datainputstream.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/* 261 */      } private void buildTileChunk(MapTileChunk tileChunk, int caveStart, int caveDepth, boolean worldHasSkylight, boolean ignoreHeightmaps, MapRegion prevRegion, class_3218 serverWorld, class_7225<class_2248> blockLookup, class_2378<class_2248> blockRegistry, class_2378<class_3611> fluidRegistry, class_2378<class_1959> biomeRegistry, boolean flowers, int worldBottomY, int worldTopY) { tileChunk.unincludeInSave();
/* 262 */     tileChunk.resetHeights();
/* 263 */     for (int insideX = 0; insideX < 4; insideX++) {
/* 264 */       for (int insideZ = 0; insideZ < 4; insideZ++) {
/* 265 */         MapTile tile = tileChunk.getTile(insideX, insideZ);
/* 266 */         int chunkX = (tileChunk.getX() << 2) + insideX;
/* 267 */         int chunkZ = (tileChunk.getZ() << 2) + insideZ;
/* 268 */         class_2487 nbttagcompound = null;
/*     */ 
/*     */         
/* 271 */         try { Optional<class_2487> optional = this.chunkNBTCompounds[insideZ << 2 | insideX].get();
/* 272 */           if (optional.isPresent())
/* 273 */             nbttagcompound = optional.get();  }
/* 274 */         catch (InterruptedException interruptedException) {  }
/* 275 */         catch (ExecutionException e)
/* 276 */         { e.printStackTrace(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 283 */         if (nbttagcompound == null) {
/* 284 */           if (tile != null) {
/* 285 */             tileChunk.setChanged(true);
/* 286 */             tileChunk.setTile(insideX, insideZ, null, this.blockStateShortShapeCache);
/* 287 */             this.mapProcessor.getTilePool().addToPool((PoolUnit)tile);
/*     */           } 
/*     */         } else {
/*     */           
/* 291 */           boolean createdTile = false;
/* 292 */           if (tile == null) {
/* 293 */             tile = this.mapProcessor.getTilePool().get(this.mapProcessor.getCurrentDimension(), chunkX, chunkZ);
/* 294 */             createdTile = true;
/*     */           } 
/*     */ 
/*     */           
/* 298 */           DataFixer fixer = class_310.method_1551().method_1543();
/* 299 */           int i = ((Integer)nbttagcompound.method_10550("DataVersion").orElse(Integer.valueOf(-1))).intValue();
/* 300 */           nbttagcompound = class_4284.field_19214.method_48130(fixer, nbttagcompound, i);
/* 301 */           if (buildTile(nbttagcompound, tile, tileChunk, chunkX, chunkZ, chunkX & 0x1F, chunkZ & 0x1F, caveStart, caveDepth, worldHasSkylight, ignoreHeightmaps, serverWorld, blockLookup, blockRegistry, fluidRegistry, biomeRegistry, flowers, worldBottomY, worldTopY)) {
/* 302 */             tile.setWrittenCave(caveStart, caveDepth);
/* 303 */             tileChunk.setTile(insideX, insideZ, tile, this.blockStateShortShapeCache);
/* 304 */             if (createdTile)
/* 305 */               tileChunk.setChanged(true); 
/*     */           } else {
/* 307 */             tileChunk.setTile(insideX, insideZ, null, this.blockStateShortShapeCache);
/* 308 */             this.mapProcessor.getTilePool().addToPool((PoolUnit)tile);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }  } private boolean buildTile(class_2487 nbttagcompound, MapTile tile, MapTileChunk tileChunk, int chunkX, int chunkZ, int insideRegionX, int insideRegionZ, int caveStart, int caveDepth, boolean worldHasSkylight, boolean ignoreHeightmaps, class_3218 serverWorld, class_7225<class_2248> blockLookup, class_2378<class_2248> blockRegistry, class_2378<class_3611> fluidRegistry, class_2378<class_1959> biomeRegistry, boolean flowers, int worldBottomY, int worldTopY) {
/*     */     boolean heightMapExists;
/* 314 */     class_2487 levelCompound = nbttagcompound;
/* 315 */     boolean oldOptimizedChunk = levelCompound.method_10545("below_zero_retrogen");
/* 316 */     String status = !oldOptimizedChunk ? levelCompound.method_68564("Status", null) : levelCompound.method_68568("below_zero_retrogen").method_68564("target_status", null);
/* 317 */     int chunkStatusIndex = class_2806.method_12168(status).method_16559();
/* 318 */     if (chunkStatusIndex < class_2806.field_12794.method_16559())
/* 319 */       return false; 
/* 320 */     handleChunkBiomes(levelCompound, insideRegionX, insideRegionZ);
/* 321 */     if (chunkStatusIndex < class_2806.field_12795.method_16559())
/* 322 */       return false; 
/* 323 */     class_2499 sectionsList = levelCompound.method_68569("sections");
/* 324 */     int fillCounter = 256;
/* 325 */     int[] topH = this.topH;
/* 326 */     int chunkBottomY = ((Integer)levelCompound.method_10550("yPos").orElse(Integer.valueOf(0))).intValue() * 16;
/* 327 */     boolean[] shouldExtendTillTheBottom = this.shouldExtendTillTheBottom;
/* 328 */     boolean cave = (caveStart != Integer.MAX_VALUE);
/* 329 */     boolean fullCave = (caveStart == Integer.MIN_VALUE);
/* 330 */     for (int i = 0; i < this.blockFound.length; i++) {
/* 331 */       this.overlayBuilders[i].startBuilding();
/* 332 */       this.blockFound[i] = false;
/* 333 */       this.shouldEnterGround[i] = fullCave; this.underair[i] = fullCave;
/* 334 */       this.lightLevels[i] = 0;
/* 335 */       this.skyLightLevels[i] = worldHasSkylight ? 15 : 0;
/* 336 */       topH[i] = worldBottomY;
/* 337 */       shouldExtendTillTheBottom[i] = false;
/*     */     } 
/* 339 */     boolean oldHeightMap = !levelCompound.method_10545("Heightmaps");
/* 340 */     int[] oldHeightMapArray = null;
/*     */     
/* 342 */     if (oldHeightMap) {
/* 343 */       oldHeightMapArray = levelCompound.method_10561("HeightMap").orElse(null);
/* 344 */       heightMapExists = (oldHeightMapArray != null && oldHeightMapArray.length == 256);
/*     */     } else {
/* 346 */       long[] heightMapArray = levelCompound.method_68568("Heightmaps").method_10565("WORLD_SURFACE").orElse(null);
/* 347 */       int potentialBitsPerHeight = (heightMapArray == null) ? 0 : (heightMapArray.length / 4);
/* 348 */       heightMapExists = (potentialBitsPerHeight > 0 && potentialBitsPerHeight <= 10);
/* 349 */       if (heightMapExists) {
/* 350 */         updateHeightArray(potentialBitsPerHeight);
/* 351 */         System.arraycopy(heightMapArray, 0, this.heightMapBitArray.method_15212(), 0, heightMapArray.length);
/*     */       } 
/*     */     } 
/* 354 */     boolean lightIsOn = ((Boolean)levelCompound.method_10577("isLightOn").orElse(Boolean.valueOf(true))).booleanValue();
/*     */     
/* 356 */     int caveStartSectionHeight = (fullCave ? serverWorld.method_31600() : caveStart) >> 4 << 4;
/* 357 */     int lowH = worldBottomY;
/* 358 */     if (cave && !fullCave) {
/* 359 */       lowH = caveStart + 1 - caveDepth;
/* 360 */       if (lowH < worldBottomY)
/* 361 */         lowH = worldBottomY; 
/*     */     } 
/* 363 */     int lowHSection = lowH >> 4 << 4;
/* 364 */     boolean transparency = true;
/* 365 */     if (sectionsList.size() == 0)
/* 366 */     { for (int j = 0; j < 16; j++) {
/* 367 */         for (int k = 0; k < 16; k++) {
/* 368 */           MapBlock currentPixel = tile.getBlock(j, k);
/* 369 */           this.buildingObject.prepareForWriting(worldBottomY);
/* 370 */           this.buildingObject.write(class_2246.field_10124.method_9564(), worldBottomY, worldBottomY, null, (byte)0, false, cave);
/* 371 */           tile.setBlock(j, k, this.buildingObject);
/* 372 */           if (currentPixel != null)
/* 373 */           { this.buildingObject = currentPixel; }
/*     */           else
/* 375 */           { this.buildingObject = new MapBlock(); } 
/*     */         } 
/*     */       }  }
/* 378 */     else { class_2499 tileEntitiesNbt = levelCompound.method_68569("block_entities");
/* 379 */       WorldDataChunkTileEntityLookup tileEntityLookup = null;
/* 380 */       if (!tileEntitiesNbt.isEmpty())
/* 381 */         tileEntityLookup = new WorldDataChunkTileEntityLookup(tileEntitiesNbt); 
/* 382 */       int prevSectionHeight = Integer.MAX_VALUE;
/* 383 */       int sectionHeight = Integer.MAX_VALUE;
/* 384 */       for (int j = sectionsList.size() - 1; j >= 0 && fillCounter > 0; j--) {
/* 385 */         class_2487 sectionCompound = sectionsList.method_68582(j);
/*     */         
/* 387 */         sectionHeight = ((Byte)sectionCompound.method_10571("Y").orElse(Byte.valueOf((byte)0))).byteValue() * 16;
/* 388 */         boolean hasBlocks = false;
/* 389 */         class_2487 blockStatesCompound = null;
/* 390 */         if (sectionCompound.method_10545("block_states")) {
/* 391 */           blockStatesCompound = sectionCompound.method_68568("block_states");
/* 392 */           hasBlocks = (sectionHeight >= lowHSection);
/* 393 */           if (hasBlocks) {
/* 394 */             hasBlocks = blockStatesCompound.method_10545("data");
/* 395 */             if (!hasBlocks && blockStatesCompound.method_10545("palette")) {
/* 396 */               class_2499 paletteList = blockStatesCompound.method_68569("palette");
/*     */               
/* 398 */               hasBlocks = (paletteList.size() == 1 && !((class_2487)paletteList.method_10534(0)).method_68564("Name", "").equals("minecraft:air"));
/*     */             } 
/*     */           } 
/*     */         } 
/* 402 */         if (j <= 0 || hasBlocks || sectionCompound.method_10545("BlockLight") || (cave && sectionCompound.method_10545("SkyLight"))) {
/*     */           
/* 404 */           boolean previousSectionExists = (prevSectionHeight - sectionHeight == 16);
/* 405 */           boolean underAirByDefault = (cave && !previousSectionExists && caveStartSectionHeight > sectionHeight);
/* 406 */           int sectionBasedHeight = sectionHeight + 15;
/* 407 */           boolean preparedSectionData = false;
/* 408 */           boolean hasDifferentBlockStates = false;
/* 409 */           byte[] lightMap = null;
/* 410 */           byte[] skyLightMap = null;
/* 411 */           prevSectionHeight = sectionHeight;
/* 412 */           for (int z = 0; z < 16; z++) {
/* 413 */             for (int x = 0; x < 16; x++) {
/* 414 */               int pos_2d = (z << 4) + x;
/* 415 */               if (!this.blockFound[pos_2d]) {
/*     */ 
/*     */                 
/* 418 */                 int startHeight, heightMapValue = heightMapExists ? (oldHeightMap ? oldHeightMapArray[pos_2d] : (chunkBottomY + this.heightMapBitArray.method_15211(pos_2d))) : Integer.MIN_VALUE;
/* 419 */                 if (cave && !fullCave) {
/* 420 */                   startHeight = caveStart;
/*     */                 }
/* 422 */                 else if (ignoreHeightmaps || heightMapValue < chunkBottomY) {
/* 423 */                   startHeight = sectionBasedHeight;
/*     */                 } else {
/* 425 */                   startHeight = heightMapValue + 3;
/*     */                 } 
/* 427 */                 if (startHeight >= worldTopY)
/* 428 */                   startHeight = worldTopY - 1; 
/* 429 */                 startHeight++;
/* 430 */                 if (j <= 0 || startHeight >= sectionHeight) {
/*     */                   
/* 432 */                   int localStartHeight = 15;
/* 433 */                   if (startHeight >> 4 << 4 == sectionHeight)
/* 434 */                     localStartHeight = startHeight & 0xF; 
/* 435 */                   if (!preparedSectionData) {
/*     */                     
/* 437 */                     if (hasBlocks) {
/* 438 */                       class_2499 paletteList = blockStatesCompound.method_68569("palette");
/* 439 */                       hasDifferentBlockStates = (blockStatesCompound.method_10545("data") && paletteList.size() > 1);
/* 440 */                       boolean shouldReadPalette = true;
/* 441 */                       if (hasDifferentBlockStates) {
/* 442 */                         long[] blockStatesArray = blockStatesCompound.method_10565("data").orElse(null);
/* 443 */                         int bits = blockStatesArray.length * 64 / 4096;
/* 444 */                         int bitsOther = Math.max(4, class_3532.method_15342(paletteList.size()));
/* 445 */                         if (bitsOther > 8)
/* 446 */                           bits = bitsOther; 
/* 447 */                         if (this.blockStatesBitArray == null || this.blockStatesBitArray.method_34896() != bits)
/* 448 */                           this.blockStatesBitArray = (class_6490)new class_3508(bits, 4096); 
/* 449 */                         if (blockStatesArray.length == (this.blockStatesBitArray.method_15212()).length) {
/* 450 */                           System.arraycopy(blockStatesArray, 0, this.blockStatesBitArray.method_15212(), 0, blockStatesArray.length);
/*     */                         } else {
/* 452 */                           hasDifferentBlockStates = false;
/* 453 */                           shouldReadPalette = false;
/*     */                         } 
/*     */                       } 
/* 456 */                       this.blockStatePalette.clear();
/* 457 */                       if (shouldReadPalette) {
/* 458 */                         paletteList.forEach(stateTag -> {
/*     */                               class_2680 state = class_2512.method_10681((class_7871)blockLookup, (class_2487)stateTag);
/*     */                               this.blockStatePalette.add(state);
/*     */                             });
/*     */                       }
/*     */                     } 
/* 464 */                     lightMap = sectionCompound.method_10547("BlockLight").orElse(null);
/* 465 */                     if (lightMap != null && lightMap.length != 2048)
/* 466 */                       lightMap = null; 
/* 467 */                     if (cave) {
/* 468 */                       skyLightMap = sectionCompound.method_10547("SkyLight").orElse(null);
/* 469 */                       if (skyLightMap != null && skyLightMap.length != 2048)
/* 470 */                         skyLightMap = null; 
/*     */                     } 
/* 472 */                     preparedSectionData = true;
/*     */                   } 
/* 474 */                   if (underAirByDefault)
/* 475 */                     this.underair[pos_2d] = true; 
/* 476 */                   for (int y = localStartHeight; y >= 0; y--)
/* 477 */                   { int h = sectionHeight | y;
/* 478 */                     int pos = y << 8 | pos_2d;
/* 479 */                     class_2680 state = null;
/* 480 */                     if (hasBlocks) {
/* 481 */                       int indexInPalette = hasDifferentBlockStates ? this.blockStatesBitArray.method_15211(pos) : 0;
/* 482 */                       if (indexInPalette < this.blockStatePalette.size())
/* 483 */                         state = this.blockStatePalette.get(indexInPalette); 
/*     */                     } 
/* 485 */                     if (state != null && tileEntityLookup != null && !(state.method_26204() instanceof net.minecraft.class_2189) && SupportMods.framedBlocks() && SupportMods.supportFramedBlocks.isFrameBlock((class_1937)serverWorld, null, state)) {
/* 486 */                       class_2487 tileEntityNbt = tileEntityLookup.getTileEntityNbt(x, h, z);
/* 487 */                       if (tileEntityNbt != null) {
/* 488 */                         if (tileEntityNbt.method_10545("camo_state")) {
/*     */                           try {
/* 490 */                             state = class_2512.method_10681((class_7871)blockLookup, tileEntityNbt.method_68568("camo_state"));
/* 491 */                           } catch (IllegalArgumentException iae) {
/* 492 */                             state = null;
/*     */                           } 
/* 494 */                         } else if (tileEntityNbt.method_10545("camo")) {
/* 495 */                           class_2487 camoNbt = tileEntityNbt.method_68568("camo");
/* 496 */                           if (camoNbt.method_10545("state")) {
/*     */                             try {
/* 498 */                               state = class_2512.method_10681((class_7871)blockLookup, camoNbt.method_68568("state"));
/* 499 */                             } catch (IllegalArgumentException iae) {
/* 500 */                               state = null;
/*     */                             } 
/* 502 */                           } else if (camoNbt.method_10545("fluid")) {
/* 503 */                             class_2487 fluidTag = camoNbt.method_68568("fluid");
/* 504 */                             if (fluidTag.method_10545("Name")) {
/* 505 */                               String fluidId = fluidTag.method_68564("Name", null);
/* 506 */                               class_3611 fluid = (class_3611)fluidRegistry.method_63535(class_2960.method_60654(fluidId));
/* 507 */                               state = (fluid == null) ? null : (class_2680)this.fluidToBlock.apply(fluid.method_15785());
/*     */                             } 
/*     */                           } 
/*     */                         } 
/*     */                       }
/*     */                     } 
/* 513 */                     if (state == null) {
/* 514 */                       state = class_2246.field_10124.method_9564();
/*     */                     }
/* 516 */                     this.mutableBlockPos.method_10103(chunkX << 4 | x, h, chunkZ << 4 | z);
/* 517 */                     OverlayBuilder overlayBuilder = this.overlayBuilders[pos_2d];
/* 518 */                     if (!shouldExtendTillTheBottom[pos_2d] && !overlayBuilder.isEmpty() && this.firstTransparentStateY[pos_2d] - h >= 5)
/* 519 */                       shouldExtendTillTheBottom[pos_2d] = true; 
/* 520 */                     boolean buildResult = (h >= lowH && h < startHeight && buildPixel(this.buildingObject, state, x, h, z, pos_2d, this.lightLevels[pos_2d], this.skyLightLevels[pos_2d], null, cave, fullCave, overlayBuilder, serverWorld, blockRegistry, this.mutableBlockPos, biomeRegistry, topH, shouldExtendTillTheBottom[pos_2d], flowers, transparency));
/* 521 */                     if (!buildResult && ((y == 0 && j == 0) || h <= lowH)) {
/* 522 */                       this.lightLevels[pos_2d] = 0;
/* 523 */                       if (cave)
/* 524 */                         this.skyLightLevels[pos_2d] = 0; 
/* 525 */                       h = worldBottomY;
/* 526 */                       state = class_2246.field_10124.method_9564();
/* 527 */                       buildResult = true;
/*     */                     } 
/*     */                     
/* 530 */                     if (buildResult) {
/* 531 */                       this.buildingObject.prepareForWriting(worldBottomY);
/* 532 */                       overlayBuilder.finishBuilding(this.buildingObject);
/* 533 */                       boolean glowing = this.mapProcessor.getMapWriter().isGlowing(state);
/*     */                       
/* 535 */                       byte light = this.lightLevels[pos_2d];
/* 536 */                       if (cave && light < 15 && this.buildingObject.getNumberOfOverlays() == 0) {
/* 537 */                         byte skyLight = this.skyLightLevels[pos_2d];
/* 538 */                         if (skyLight > light)
/* 539 */                           light = skyLight; 
/*     */                       } 
/* 541 */                       this.buildingObject.write(state, h, topH[pos_2d], null, light, glowing, cave);
/*     */                       
/* 543 */                       MapBlock currentPixel = tile.getBlock(x, z);
/* 544 */                       boolean equalsSlopesExcluded = this.buildingObject.equalsSlopesExcluded(currentPixel);
/* 545 */                       boolean fullyEqual = this.buildingObject.equals(currentPixel, equalsSlopesExcluded);
/* 546 */                       if (!fullyEqual) {
/* 547 */                         tile.setBlock(x, z, this.buildingObject);
/* 548 */                         if (currentPixel != null) {
/* 549 */                           this.buildingObject = currentPixel;
/*     */                         } else {
/* 551 */                           this.buildingObject = new MapBlock();
/* 552 */                         }  if (!equalsSlopesExcluded)
/* 553 */                           tileChunk.setChanged(true); 
/*     */                       } 
/* 555 */                       this.blockFound[pos_2d] = true;
/* 556 */                       fillCounter--;
/*     */                       break;
/*     */                     } 
/* 559 */                     byte dataLight = (lightMap == null) ? 0 : nibbleValue(lightMap, pos);
/* 560 */                     if (cave && dataLight < 15 && worldHasSkylight) {
/*     */                       byte dataSkyLight;
/* 562 */                       if (!ignoreHeightmaps && !fullCave && startHeight > heightMapValue) {
/* 563 */                         dataSkyLight = 15;
/*     */                       } else {
/* 565 */                         dataSkyLight = (skyLightMap == null) ? 0 : nibbleValue(skyLightMap, pos);
/* 566 */                       }  this.skyLightLevels[pos_2d] = dataSkyLight;
/*     */                     } 
/* 568 */                     this.lightLevels[pos_2d] = dataLight; } 
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }  }
/* 575 */      tile.setWorldInterpretationVersion(1);
/* 576 */     return true;
/*     */   }
/*     */   
/*     */   private boolean buildPixel(MapBlock pixel, class_2680 state, int x, int h, int z, int pos_2d, byte light, byte skyLight, class_5321<class_1959> biome, boolean cave, boolean fullCave, OverlayBuilder overlayBuilder, class_3218 serverWorld, class_2378<class_2248> blockRegistry, class_2338.class_2339 mutableBlockPos, class_2378<class_1959> biomeRegistry, int[] topH, boolean shouldExtendTillTheBottom, boolean flowers, boolean transparency) {
/* 580 */     class_3610 fluidFluidState = state.method_26227();
/* 581 */     class_2248 b = state.method_26204();
/* 582 */     if (!fluidFluidState.method_15769() && (!cave || !this.shouldEnterGround[pos_2d])) {
/* 583 */       this.underair[pos_2d] = true;
/* 584 */       class_2680 fluidState = (class_2680)this.fluidToBlock.apply(fluidFluidState);
/* 585 */       if (buildPixelHelp(pixel, fluidState, fluidState.method_26204(), fluidFluidState, pos_2d, h, cave, light, skyLight, biome, overlayBuilder, serverWorld, blockRegistry, biomeRegistry, topH, shouldExtendTillTheBottom, flowers, transparency))
/* 586 */         return true; 
/*     */     } 
/* 588 */     if (b instanceof net.minecraft.class_2189) {
/* 589 */       this.underair[pos_2d] = true;
/* 590 */       return false;
/*     */     } 
/* 592 */     if (!this.underair[pos_2d] && cave)
/* 593 */       return false; 
/* 594 */     if (b == ((class_2680)this.fluidToBlock.apply(fluidFluidState)).method_26204())
/* 595 */       return false; 
/* 596 */     if (cave && this.shouldEnterGround[pos_2d]) {
/* 597 */       if (!state.method_50011() && !state.method_45474() && state.method_26223() != class_3619.field_15971 && !shouldOverlayCached((class_2688<?, ?>)state)) {
/* 598 */         this.underair[pos_2d] = false;
/* 599 */         this.shouldEnterGround[pos_2d] = false;
/*     */       } 
/* 601 */       return false;
/*     */     } 
/* 603 */     return buildPixelHelp(pixel, state, state.method_26204(), null, pos_2d, h, cave, light, skyLight, biome, overlayBuilder, serverWorld, blockRegistry, biomeRegistry, topH, shouldExtendTillTheBottom, flowers, transparency);
/*     */   }
/*     */   
/*     */   private boolean buildPixelHelp(MapBlock pixel, class_2680 state, class_2248 b, class_3610 fluidFluidState, int pos_2d, int h, boolean cave, byte light, byte skyLight, class_5321<class_1959> dataBiome, OverlayBuilder overlayBuilder, class_3218 serverWorld, class_2378<class_2248> blockRegistry, class_2378<class_1959> biomeRegistry, int[] topH, boolean shouldExtendTillTheBottom, boolean flowers, boolean transparency) {
/* 607 */     if (this.mapProcessor.getMapWriter().isInvisible(state, b, flowers))
/* 608 */       return false; 
/* 609 */     if (shouldOverlayCached((fluidFluidState == null) ? (class_2688<?, ?>)state : (class_2688<?, ?>)fluidFluidState)) {
/* 610 */       if (cave && !this.underair[pos_2d])
/* 611 */         return !transparency; 
/* 612 */       if (h > topH[pos_2d])
/* 613 */         topH[pos_2d] = h; 
/* 614 */       byte overlayLight = light;
/* 615 */       if (overlayBuilder.isEmpty()) {
/* 616 */         this.firstTransparentStateY[pos_2d] = h;
/* 617 */         if (cave && skyLight > overlayLight)
/* 618 */           overlayLight = skyLight; 
/*     */       } 
/* 620 */       if (shouldExtendTillTheBottom) {
/* 621 */         overlayBuilder.getCurrentOverlay().increaseOpacity(overlayBuilder.getCurrentOverlay().getState().method_26193());
/*     */       } else {
/* 623 */         overlayBuilder.build(state, state.method_26193(), overlayLight, this.mapProcessor, dataBiome);
/* 624 */       }  return !transparency;
/*     */     } 
/* 626 */     if (!this.mapProcessor.getMapWriter().hasVanillaColor(state, (class_1937)serverWorld, blockRegistry, (class_2338)this.mutableBlockPos))
/* 627 */       return false; 
/* 628 */     if (cave && !this.underair[pos_2d])
/* 629 */       return true; 
/* 630 */     if (h > topH[pos_2d])
/* 631 */       topH[pos_2d] = h; 
/* 632 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private void handleTileChunkOutsideRegion(int relativeX, int relativeZ, int actualX, int actualZ, int caveStart, boolean ignoreHeightmaps, class_2378<class_1959> biomeRegistry, boolean flowers, class_3977 chunkLoader) {
/* 637 */     int minInsideX = (relativeX < 0) ? 3 : 0;
/* 638 */     int maxInsideX = (relativeX > 7) ? 0 : 3;
/* 639 */     int minInsideZ = (relativeZ < 0) ? 3 : 0;
/* 640 */     int maxInsideZ = (relativeZ > 7) ? 0 : 3; int insideX;
/* 641 */     for (insideX = minInsideX; insideX <= maxInsideX; insideX++) {
/* 642 */       for (int insideZ = minInsideZ; insideZ <= maxInsideZ; insideZ++)
/* 643 */         this.chunkNBTCompounds[insideZ << 2 | insideX] = chunkLoader.method_23696(new class_1923(actualX << 2 | insideX, actualZ << 2 | insideZ)); 
/*     */     } 
/* 645 */     for (insideX = minInsideX; insideX <= maxInsideX; insideX++) {
/* 646 */       for (int insideZ = minInsideZ; insideZ <= maxInsideZ; insideZ++) {
/* 647 */         class_2487 nbt = null;
/*     */         try {
/* 649 */           nbt = ((Optional<class_2487>)this.chunkNBTCompounds[insideZ << 2 | insideX].get()).orElse(null);
/* 650 */         } catch (InterruptedException|ExecutionException e) {
/* 651 */           e.printStackTrace();
/*     */         } 
/* 653 */         int insideRegionX = relativeX << 2 | insideX;
/* 654 */         int insideRegionZ = relativeZ << 2 | insideZ;
/* 655 */         if (nbt != null) {
/*     */           
/* 657 */           DataFixer fixer = class_310.method_1551().method_1543();
/* 658 */           int i = ((Integer)nbt.method_10550("DataVersion").orElse(Integer.valueOf(-1))).intValue();
/* 659 */           nbt = class_4284.field_19214.method_48130(fixer, nbt, i);
/* 660 */           handleTileOutsideRegion(nbt, insideRegionX, insideRegionZ);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   } private void handleTileOutsideRegion(class_2487 nbt, int insideRegionX, int insideRegionZ) {
/* 665 */     class_2487 levelCompound = nbt.method_68568("Level");
/* 666 */     String status = levelCompound.method_68564("Status", null);
/* 667 */     if (status == null || class_2806.method_12168(status).method_16559() < class_2806.field_12794.method_16559())
/*     */       return; 
/* 669 */     handleChunkBiomes(levelCompound, insideRegionX, insideRegionZ);
/*     */   }
/*     */   
/*     */   private void handleChunkBiomes(class_2487 levelCompound, int insideRegionX, int insideRegionZ) {
/* 673 */     class_2499 sectionsList = levelCompound.method_68569("sections");
/* 674 */     for (int i = 0; i < sectionsList.size(); i++) {
/* 675 */       class_2487 sectionCompound = sectionsList.method_68582(i);
/* 676 */       class_2487 biomesCompound = sectionCompound.method_68568("biomes");
/* 677 */       if (!biomesCompound.method_33133()) {
/*     */         
/* 679 */         class_2499 biomePaletteList = biomesCompound.method_68569("palette");
/* 680 */         if (!biomePaletteList.isEmpty()) {
/*     */ 
/*     */           
/* 683 */           long[] biomesLongArray = (biomePaletteList.size() == 1) ? null : biomesCompound.method_10565("data").orElse(null);
/* 684 */           WorldDataReaderSectionBiomeData biomeSection = new WorldDataReaderSectionBiomeData(biomePaletteList, biomesLongArray);
/* 685 */           int sectionIndex = ((Byte)sectionCompound.method_10571("Y").get()).byteValue();
/* 686 */           this.biomeManager.addBiomeSectionForRegionChunk(insideRegionX, insideRegionZ, sectionIndex, biomeSection);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   } private void fillBiomes(MapTileChunk tileChunk, class_4543 biomeZoomer, class_2378<class_1959> biomeRegistry) {
/*     */     try {
/* 692 */       for (int insideX = 0; insideX < 4; insideX++) {
/* 693 */         for (int insideZ = 0; insideZ < 4; insideZ++) {
/* 694 */           MapTile mapTile = tileChunk.getTile(insideX, insideZ);
/* 695 */           if (mapTile != null) {
/* 696 */             mapTile.setLoaded(true);
/* 697 */             for (int x = 0; x < 16; x++) {
/* 698 */               for (int z = 0; z < 16; z++)
/* 699 */               { MapBlock mapBlock = mapTile.getBlock(x, z);
/* 700 */                 int topHeight = mapBlock.getTopHeight();
/* 701 */                 if (topHeight == 32767)
/* 702 */                   topHeight = mapBlock.getHeight(); 
/* 703 */                 class_1959 biome = this.biomeManager.getBiome(biomeZoomer, mapTile.getChunkX() << 4 | x, topHeight, mapTile.getChunkZ() << 4 | z);
/* 704 */                 class_5321<class_1959> biomeKey = biomeRegistry.method_29113(biome).orElse(null);
/* 705 */                 if (biomeKey != null)
/*     */                 {
/*     */ 
/*     */ 
/*     */ 
/*     */                   
/* 711 */                   mapBlock.setBiome(biomeKey); }  } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 716 */     } catch (Throwable t) {
/* 717 */       WorldMap.LOGGER.error("Error filling tile chunk with zoomed biomes", t);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void transferFilledBiomes(MapTileChunk tileChunk, class_4543 biomeZoomer, class_2378<class_1959> biomeRegistry) {
/*     */     try {
/* 723 */       for (int insideX = 0; insideX < 4; insideX++) {
/* 724 */         for (int insideZ = 0; insideZ < 4; insideZ++) {
/* 725 */           MapTile mapTile = tileChunk.getTile(insideX, insideZ);
/* 726 */           if (mapTile != null && mapTile.isLoaded())
/* 727 */             for (int x = 0; x < 16; x++) {
/* 728 */               for (int z = 0; z < 16; z++) {
/* 729 */                 MapBlock mapBlock = mapTile.getBlock(x, z);
/* 730 */                 tileChunk.getLeafTexture().setBiome(insideX << 4 | x, insideZ << 4 | z, mapBlock.getBiome());
/*     */               } 
/*     */             }  
/*     */         } 
/*     */       } 
/* 735 */     } catch (Throwable t) {
/* 736 */       WorldMap.LOGGER.error("Error transferring filled tile chunk with zoomed biomes", t);
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean shouldOverlayCached(class_2688<?, ?> state) {
/* 741 */     return ((Boolean)this.transparentCache.apply(state)).booleanValue();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private byte nibbleValue(byte[] array, int index) {
/* 761 */     byte b = array[index >> 1];
/* 762 */     if ((index & 0x1) == 0) {
/* 763 */       return (byte)(b & 0xF);
/*     */     }
/* 765 */     return (byte)(b >> 4 & 0xF);
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\file\worldsave\WorldDataReader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */