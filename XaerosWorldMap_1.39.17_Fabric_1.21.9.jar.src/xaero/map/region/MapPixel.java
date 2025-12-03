/*     */ package xaero.map.region;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.class_1922;
/*     */ import net.minecraft.class_1937;
/*     */ import net.minecraft.class_1959;
/*     */ import net.minecraft.class_2248;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_2378;
/*     */ import net.minecraft.class_2680;
/*     */ import net.minecraft.class_2874;
/*     */ import xaero.map.MapProcessor;
/*     */ import xaero.map.MapWriter;
/*     */ import xaero.map.WorldMap;
/*     */ import xaero.map.biome.BlockTintProvider;
/*     */ import xaero.map.cache.BlockStateShortShapeCache;
/*     */ import xaero.map.world.MapDimension;
/*     */ 
/*     */ public class MapPixel {
/*     */   private static final int VOID_COLOR = -16121833;
/*     */   private static final float DEFAULT_AMBIENT_LIGHT = 0.7F;
/*     */   private static final float DEFAULT_AMBIENT_LIGHT_COLORED = 0.2F;
/*     */   private static final float DEFAULT_AMBIENT_LIGHT_WHITE = 0.5F;
/*     */   private static final float DEFAULT_MAX_DIRECT_LIGHT = 0.6666667F;
/*     */   private static final float GLOWING_MAX_DIRECT_LIGHT = 0.22222224F;
/*     */   protected class_2680 state;
/*  27 */   protected byte light = 0;
/*     */   
/*     */   protected boolean glowing = false;
/*     */   
/*     */   private int getVanillaTransparency(class_2248 b) {
/*  32 */     return (b instanceof net.minecraft.class_2404) ? 191 : ((b instanceof net.minecraft.class_2386) ? 216 : 127);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getPixelColours(int[] result_dest, MapWriter mapWriter, class_1937 world, MapDimension dim, class_2378<class_2248> blockRegistry, MapTileChunk tileChunk, MapTileChunk prevChunk, MapTileChunk prevChunkDiagonal, MapTileChunk prevChunkHorisontal, MapTile mapTile, int x, int z, MapBlock block, int height, int topHeight, int caveStart, int caveDepth, ArrayList<Overlay> overlays, class_2338.class_2339 mutableGlobalPos, class_2378<class_1959> biomeRegistry, class_2378<class_2874> dimensionTypes, float shadowR, float shadowG, float shadowB, BlockTintProvider blockTintProvider, MapProcessor mapProcessor, OverlayManager overlayManager, BlockStateShortShapeCache blockStateShortShapeCache) {
/*  39 */     int colour = (block != null && caveStart != Integer.MAX_VALUE) ? 0 : -16121833;
/*  40 */     int topLightValue = this.light;
/*  41 */     int lightMin = 9;
/*  42 */     float brightnessR = 1.0F;
/*  43 */     float brightnessG = 1.0F;
/*  44 */     float brightnessB = 1.0F;
/*  45 */     mutableGlobalPos.method_10103(mapTile.getChunkX() * 16 + x, height, mapTile.getChunkZ() * 16 + z);
/*  46 */     class_2680 state = this.state;
/*  47 */     boolean isAir = state.method_26204() instanceof net.minecraft.class_2189;
/*  48 */     boolean isFinalBlock = this instanceof MapBlock;
/*  49 */     if (!isAir) {
/*  50 */       if (WorldMap.settings.colours == 0) {
/*  51 */         colour = mapWriter.loadBlockColourFromTexture(state, true, world, blockRegistry, (class_2338)mutableGlobalPos);
/*     */       } else {
/*     */         try {
/*  54 */           class_2248 class_2248 = state.method_26204();
/*  55 */           int a = getVanillaTransparency(class_2248);
/*     */ 
/*     */ 
/*     */           
/*  59 */           colour = (state.method_26205((class_1922)world, (class_2338)mutableGlobalPos)).field_16011;
/*  60 */           if (!isFinalBlock && colour == 0) {
/*  61 */             result_dest[0] = -1;
/*     */             
/*     */             return;
/*     */           } 
/*  65 */           colour = a << 24 | colour & 0xFFFFFF;
/*  66 */         } catch (Exception exception) {}
/*     */       } 
/*     */       
/*  69 */       if (!isFinalBlock && !WorldMap.settings.displayStainedGlass && (state.method_26204() instanceof net.minecraft.class_2506 || state.method_26204() instanceof net.minecraft.class_2504)) {
/*  70 */         result_dest[0] = -1;
/*     */         return;
/*     */       } 
/*     */     } 
/*  74 */     int r = colour >> 16 & 0xFF;
/*  75 */     int g = colour >> 8 & 0xFF;
/*  76 */     int b = colour & 0xFF;
/*  77 */     if (WorldMap.settings.biomeColorsVanillaMode || WorldMap.settings.colours == 0) {
/*  78 */       int c = blockTintProvider.getBiomeColor((class_2338)mutableGlobalPos, state, !isFinalBlock, mapTile, tileChunk.getInRegion().getCaveLayer());
/*  79 */       float rMultiplier = r / 255.0F;
/*  80 */       float gMultiplier = g / 255.0F;
/*  81 */       float bMultiplier = b / 255.0F;
/*  82 */       r = (int)((c >> 16 & 0xFF) * rMultiplier);
/*  83 */       g = (int)((c >> 8 & 0xFF) * gMultiplier);
/*  84 */       b = (int)((c & 0xFF) * bMultiplier);
/*     */     } 
/*  86 */     if (this.glowing) {
/*  87 */       int total = r + g + b;
/*  88 */       float minBrightness = 407.0F;
/*  89 */       float brightener = Math.max(1.0F, minBrightness / total);
/*     */       
/*  91 */       r = (int)(r * brightener);
/*  92 */       g = (int)(g * brightener);
/*  93 */       b = (int)(b * brightener);
/*  94 */       topLightValue = 15;
/*     */     } 
/*  96 */     int overlayRed = 0;
/*  97 */     int overlayGreen = 0;
/*  98 */     int overlayBlue = 0;
/*     */     
/* 100 */     float currentTransparencyMultiplier = 1.0F;
/* 101 */     boolean legibleCaveMaps = (WorldMap.settings.legibleCaveMaps && caveStart != Integer.MAX_VALUE);
/* 102 */     boolean hasValidOverlay = false;
/* 103 */     if (overlays != null && !overlays.isEmpty()) {
/* 104 */       int sun = 15;
/* 105 */       for (int i = 0; i < overlays.size(); i++) {
/* 106 */         Overlay o = overlays.get(i);
/* 107 */         o.getPixelColour(block, result_dest, mapWriter, world, dim, blockRegistry, tileChunk, prevChunk, prevChunkDiagonal, prevChunkHorisontal, mapTile, x, z, caveStart, caveDepth, mutableGlobalPos, biomeRegistry, dimensionTypes, shadowR, shadowG, shadowB, blockTintProvider, mapProcessor, overlayManager);
/* 108 */         if (result_dest[0] != -1) {
/*     */           
/* 110 */           hasValidOverlay = true;
/* 111 */           if (i == 0)
/* 112 */             topLightValue = o.light; 
/* 113 */           float transparency = result_dest[3] / 255.0F;
/* 114 */           float overlayIntensity = getBlockBrightness(lightMin, o.light, sun) * transparency * currentTransparencyMultiplier;
/* 115 */           overlayRed = (int)(overlayRed + result_dest[0] * overlayIntensity);
/* 116 */           overlayGreen = (int)(overlayGreen + result_dest[1] * overlayIntensity);
/* 117 */           overlayBlue = (int)(overlayBlue + result_dest[2] * overlayIntensity);
/* 118 */           sun -= o.getOpacity();
/* 119 */           if (sun < 0)
/* 120 */             sun = 0; 
/* 121 */           currentTransparencyMultiplier *= 1.0F - transparency;
/*     */         } 
/* 123 */       }  if (!legibleCaveMaps && hasValidOverlay && !this.glowing && !isAir)
/* 124 */         brightnessR = brightnessG = brightnessB = getBlockBrightness(lightMin, this.light, sun); 
/*     */     } 
/* 126 */     if (isFinalBlock) {
/* 127 */       if (block.slopeUnknown) {
/* 128 */         if (!isAir) {
/* 129 */           block.fixHeightType(x, z, mapTile, tileChunk, prevChunk, prevChunkDiagonal, prevChunkHorisontal, height, false, blockStateShortShapeCache);
/*     */         } else {
/* 131 */           block.setVerticalSlope((byte)0);
/* 132 */           block.setDiagonalSlope((byte)0);
/* 133 */           block.slopeUnknown = false;
/*     */         } 
/*     */       }
/* 136 */       float depthBrightness = 1.0F;
/* 137 */       int slopes = WorldMap.settings.terrainSlopes;
/* 138 */       if (legibleCaveMaps)
/* 139 */         topLightValue = 15; 
/* 140 */       if (height != 32767)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 149 */         if (legibleCaveMaps && (!isAir || hasValidOverlay)) {
/* 150 */           int depthCalculationBase = 0;
/* 151 */           int depthCalculationHeight = height;
/* 152 */           int depthCalculationBottom = caveStart + 1 - caveDepth;
/* 153 */           int depthCalculationTop = caveStart;
/* 154 */           if (caveStart == Integer.MIN_VALUE) {
/* 155 */             depthCalculationBottom = 0;
/* 156 */             depthCalculationTop = 63;
/* 157 */             int odd = depthCalculationHeight >> 6 & 0x1;
/* 158 */             depthCalculationHeight = 63 * odd + (1 - 2 * odd) * (depthCalculationHeight & 0x3F);
/* 159 */             depthCalculationBase = 16;
/*     */           } 
/* 161 */           int caveRange = 1 + depthCalculationTop - depthCalculationBottom;
/*     */           
/* 163 */           if (!isAir && !this.glowing) {
/* 164 */             float caveBrightness = (1.0F + depthCalculationBase + depthCalculationHeight - depthCalculationBottom) / (depthCalculationBase + caveRange);
/* 165 */             brightnessR *= caveBrightness;
/* 166 */             brightnessG *= caveBrightness;
/* 167 */             brightnessB *= caveBrightness;
/*     */           } 
/* 169 */           if (hasValidOverlay) {
/* 170 */             depthCalculationHeight = topHeight;
/* 171 */             if (caveStart == Integer.MIN_VALUE) {
/* 172 */               int odd = depthCalculationHeight >> 6 & 0x1;
/* 173 */               depthCalculationHeight = 63 * odd + (1 - 2 * odd) * (depthCalculationHeight & 0x3F);
/*     */             } 
/* 175 */             float caveBrightness = (1.0F + depthCalculationBase + depthCalculationHeight - depthCalculationBottom) / (depthCalculationBase + caveRange);
/* 176 */             overlayRed = (int)(overlayRed * caveBrightness);
/* 177 */             overlayGreen = (int)(overlayGreen * caveBrightness);
/* 178 */             overlayBlue = (int)(overlayBlue * caveBrightness);
/*     */           } 
/* 180 */         } else if (!isAir && !this.glowing && WorldMap.settings.terrainDepth) {
/* 181 */           if (caveStart == Integer.MAX_VALUE) {
/* 182 */             depthBrightness = height / 63.0F;
/* 183 */           } else if (caveStart == Integer.MIN_VALUE) {
/* 184 */             depthBrightness = 0.7F + 0.3F * height / dim.getDimensionType(dimensionTypes).comp_653();
/*     */           } else {
/* 186 */             int caveBottom = caveStart - caveDepth;
/* 187 */             depthBrightness = 0.7F + 0.3F * (height - caveBottom) / caveDepth;
/*     */           } 
/* 189 */           float max = (slopes >= 2) ? 1.0F : 1.15F;
/* 190 */           float min = (slopes >= 2) ? 0.9F : 0.7F;
/* 191 */           if (depthBrightness > max) {
/* 192 */             depthBrightness = max;
/* 193 */           } else if (depthBrightness < min) {
/* 194 */             depthBrightness = min;
/*     */           } 
/*     */         }  } 
/* 197 */       if (!isAir && slopes > 0 && !block.slopeUnknown) {
/* 198 */         int verticalSlope = block.getVerticalSlope();
/* 199 */         if (slopes == 1)
/* 200 */         { if (verticalSlope > 0) {
/* 201 */             depthBrightness = (float)(depthBrightness * 1.15D);
/* 202 */           } else if (verticalSlope < 0) {
/* 203 */             depthBrightness = (float)(depthBrightness * 0.85D);
/*     */           }  }
/* 205 */         else { int diagonalSlope = block.getDiagonalSlope();
/* 206 */           float ambientLightColored = 0.2F;
/* 207 */           float ambientLightWhite = 0.5F;
/* 208 */           float maxDirectLight = 0.6666667F;
/* 209 */           if (this.glowing) {
/* 210 */             ambientLightColored = 0.0F;
/* 211 */             ambientLightWhite = 1.0F;
/* 212 */             maxDirectLight = 0.22222224F;
/*     */           } 
/* 214 */           float cos = 0.0F;
/* 215 */           if (slopes == 2) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 221 */             float crossZ = -verticalSlope;
/* 222 */             if (crossZ < 1.0F) {
/* 223 */               if (verticalSlope == 1 && diagonalSlope == 1) {
/* 224 */                 cos = 1.0F;
/*     */               } else {
/*     */                 
/* 227 */                 float crossX = (verticalSlope - diagonalSlope);
/* 228 */                 float cast = 1.0F - crossZ;
/* 229 */                 float crossMagnitude = (float)Math.sqrt((crossX * crossX + 1.0F + crossZ * crossZ));
/* 230 */                 cos = (float)((cast / crossMagnitude) / Math.sqrt(2.0D));
/*     */               } 
/*     */             }
/* 233 */           } else if (verticalSlope >= 0) {
/*     */             
/* 235 */             if (verticalSlope == 1) {
/* 236 */               cos = 1.0F;
/*     */             } else {
/* 238 */               float surfaceDirectionMagnitude = (float)Math.sqrt((verticalSlope * verticalSlope + 1));
/*     */               
/* 240 */               float castToMostLit = (verticalSlope + 1);
/*     */               
/* 242 */               cos = (float)((castToMostLit / surfaceDirectionMagnitude) / Math.sqrt(2.0D));
/*     */             } 
/*     */           } 
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
/* 261 */           float directLightClamped = 0.0F;
/* 262 */           if (cos == 1.0F) {
/* 263 */             directLightClamped = maxDirectLight;
/* 264 */           } else if (cos > 0.0F) {
/* 265 */             directLightClamped = (float)Math.ceil((cos * 10.0F)) / 10.0F * maxDirectLight * 0.88388F;
/*     */           } 
/*     */ 
/*     */           
/* 269 */           float whiteLight = ambientLightWhite + directLightClamped;
/*     */           
/* 271 */           brightnessR *= shadowR * ambientLightColored + whiteLight;
/* 272 */           brightnessG *= shadowG * ambientLightColored + whiteLight;
/* 273 */           brightnessB *= shadowB * ambientLightColored + whiteLight; }
/*     */       
/*     */       } 
/* 276 */       brightnessR *= depthBrightness;
/* 277 */       brightnessG *= depthBrightness;
/* 278 */       brightnessB *= depthBrightness;
/* 279 */       result_dest[3] = (int)(getPixelLight(lightMin, topLightValue) * 255.0F);
/*     */     } else {
/*     */       
/* 282 */       result_dest[3] = colour >> 24 & 0xFF;
/* 283 */       if (result_dest[3] == 0) {
/* 284 */         result_dest[3] = getVanillaTransparency(state.method_26204());
/*     */       }
/*     */     } 
/* 287 */     result_dest[0] = (int)(r * brightnessR * currentTransparencyMultiplier + overlayRed);
/* 288 */     if (result_dest[0] > 255)
/* 289 */       result_dest[0] = 255; 
/* 290 */     result_dest[1] = (int)(g * brightnessG * currentTransparencyMultiplier + overlayGreen);
/* 291 */     if (result_dest[1] > 255)
/* 292 */       result_dest[1] = 255; 
/* 293 */     result_dest[2] = (int)(b * brightnessB * currentTransparencyMultiplier + overlayBlue);
/* 294 */     if (result_dest[2] > 255) {
/* 295 */       result_dest[2] = 255;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getBlockBrightness(float min, int l, int sun) {
/* 305 */     return (min + Math.max(sun, l)) / (15.0F + min);
/*     */   }
/*     */   
/*     */   private float getPixelLight(float min, int topLightValue) {
/* 309 */     return (topLightValue == 0) ? 0.0F : getBlockBrightness(min, topLightValue, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public class_2680 getState() {
/* 314 */     return this.state;
/*     */   }
/*     */   
/*     */   public void setState(class_2680 state) {
/* 318 */     this.state = state;
/*     */   }
/*     */   
/*     */   public void setLight(byte light) {
/* 322 */     this.light = light;
/*     */   }
/*     */   
/*     */   public void setGlowing(boolean glowing) {
/* 326 */     this.glowing = glowing;
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\region\MapPixel.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */