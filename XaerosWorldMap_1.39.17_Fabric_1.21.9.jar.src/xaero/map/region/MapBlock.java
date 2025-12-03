/*     */ package xaero.map.region;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.class_1937;
/*     */ import net.minecraft.class_1959;
/*     */ import net.minecraft.class_2246;
/*     */ import net.minecraft.class_2248;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_2378;
/*     */ import net.minecraft.class_2680;
/*     */ import net.minecraft.class_2874;
/*     */ import net.minecraft.class_5321;
/*     */ import xaero.map.MapProcessor;
/*     */ import xaero.map.MapWriter;
/*     */ import xaero.map.WorldMap;
/*     */ import xaero.map.biome.BlockTintProvider;
/*     */ import xaero.map.cache.BlockStateShortShapeCache;
/*     */ import xaero.map.world.MapDimension;
/*     */ 
/*     */ public class MapBlock
/*     */   extends MapPixel {
/*     */   protected boolean slopeUnknown = true;
/*     */   private byte verticalSlope;
/*     */   private byte diagonalSlope;
/*     */   private short height;
/*     */   private short topHeight;
/*     */   private ArrayList<Overlay> overlays;
/*  28 */   private class_5321<class_1959> biome = null;
/*     */   
/*     */   public boolean isGrass() {
/*  31 */     return (this.state.method_26204() == class_2246.field_10219);
/*     */   }
/*     */   
/*     */   public int getParametres() {
/*  35 */     int parametres = 0;
/*  36 */     parametres |= !isGrass() ? 1 : 0;
/*  37 */     parametres |= (getNumberOfOverlays() != 0) ? 2 : 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  43 */     parametres |= this.light << 8;
/*  44 */     parametres |= (getHeight() & 0xFF) << 12;
/*  45 */     parametres |= (this.biome != null) ? 1048576 : 0;
/*     */ 
/*     */ 
/*     */     
/*  49 */     parametres |= (this.height != this.topHeight) ? 16777216 : 0;
/*  50 */     parametres |= (getHeight() >> 8 & 0xF) << 25;
/*  51 */     return parametres;
/*     */   }
/*     */   
/*     */   public void getPixelColour(int[] result_dest, MapWriter mapWriter, class_1937 world, MapDimension dim, class_2378<class_2248> blockRegistry, MapTileChunk tileChunk, MapTileChunk prevChunk, MapTileChunk prevChunkDiagonal, MapTileChunk prevChunkHorisontal, MapTile mapTile, int x, int z, int caveStart, int caveDepth, class_2338.class_2339 mutableGlobalPos, class_2378<class_1959> biomeRegistry, class_2378<class_2874> dimensionTypes, float shadowR, float shadowG, float shadowB, BlockTintProvider blockTintProvider, MapProcessor mapProcessor, OverlayManager overlayManager, int effectiveHeight, int effectiveTopHeight, BlockStateShortShapeCache blockStateShortShapeCache) {
/*  55 */     getPixelColours(result_dest, mapWriter, world, dim, blockRegistry, tileChunk, prevChunk, prevChunkDiagonal, prevChunkHorisontal, mapTile, x, z, this, effectiveHeight, effectiveTopHeight, caveStart, caveDepth, this.overlays, mutableGlobalPos, biomeRegistry, dimensionTypes, shadowR, shadowG, shadowB, blockTintProvider, mapProcessor, overlayManager, blockStateShortShapeCache);
/*     */   }
/*     */   
/*     */   public String toRenderString(class_2378<class_1959> biomeRegistry) {
/*  59 */     return "" + ((class_2248.field_10651.method_10200(class_2248.field_10651.method_10206(getState())) == getState()) ? 1 : 0) + " S: " + ((class_2248.field_10651.method_10200(class_2248.field_10651.method_10206(getState())) == getState()) ? 1 : 0) + ", VS: " + String.valueOf(getState()) + ", DS: " + this.verticalSlope + ", SU: " + this.diagonalSlope + ", H: " + this.slopeUnknown + ", B: " + getHeight() + ", L: " + String.valueOf((this.biome == null) ? "null" : this.biome.method_29177()) + ", G: " + this.light + ", O: " + this.glowing;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equalsSlopesExcluded(MapBlock p) {
/*  68 */     boolean equal = (p != null && this.state == p.state && this.light == p.light && this.height == p.height && this.topHeight == p.topHeight && getNumberOfOverlays() == p.getNumberOfOverlays() && this.biome == p.biome);
/*  69 */     if (equal && getNumberOfOverlays() != 0)
/*  70 */       for (int i = 0; i < this.overlays.size(); i++) {
/*  71 */         if (!((Overlay)this.overlays.get(i)).equals(p.overlays.get(i)))
/*  72 */           return false; 
/*     */       }  
/*  74 */     return equal;
/*     */   }
/*     */   
/*     */   public boolean equals(MapBlock p, boolean equalsSlopesExcluded) {
/*  78 */     return (p != null && this.verticalSlope == p.verticalSlope && this.diagonalSlope == p.diagonalSlope && this.slopeUnknown == p.slopeUnknown && equalsSlopesExcluded);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fixHeightType(int x, int z, MapTile mapTile, MapTileChunk tileChunk, MapTileChunk prevChunk, MapTileChunk prevChunkDiagonal, MapTileChunk prevChunkHorisontal, int height, boolean useSourceData, BlockStateShortShapeCache blockStateShortShapeCache) {
/*  86 */     int prevHeight = 32767;
/*  87 */     int prevHeightDiagonal = 32767;
/*  88 */     if (useSourceData && z > 0) {
/*  89 */       prevHeight = mapTile.getBlock(x, z - 1).getEffectiveHeight(blockStateShortShapeCache);
/*  90 */       if (x > 0)
/*  91 */         prevHeightDiagonal = mapTile.getBlock(x - 1, z - 1).getEffectiveHeight(blockStateShortShapeCache); 
/*     */     } 
/*  93 */     if (prevHeight == 32767 || prevHeightDiagonal == 32767) {
/*  94 */       int inTileChunkX = ((mapTile.getChunkX() & 0x3) << 4) + x;
/*  95 */       int inTileChunkZ = ((mapTile.getChunkZ() & 0x3) << 4) + z;
/*  96 */       int inTileChunkXPrev = inTileChunkX - 1;
/*  97 */       int inTileChunkZPrev = inTileChunkZ - 1;
/*  98 */       MapTileChunk verticalSlopeSrc = tileChunk;
/*  99 */       MapTileChunk diagonalSlopeSrc = tileChunk;
/* 100 */       boolean verticalEdge = (inTileChunkZPrev < 0);
/* 101 */       boolean horisontalEdge = (inTileChunkXPrev < 0);
/* 102 */       if (verticalEdge) {
/* 103 */         verticalSlopeSrc = diagonalSlopeSrc = prevChunk;
/* 104 */         inTileChunkZPrev = 63;
/*     */       } 
/* 106 */       if (horisontalEdge) {
/* 107 */         inTileChunkXPrev = 63;
/* 108 */         diagonalSlopeSrc = verticalEdge ? prevChunkDiagonal : prevChunkHorisontal;
/*     */       } 
/* 110 */       if (prevHeight == 32767 && verticalSlopeSrc != null && verticalSlopeSrc.getLoadState() >= 2)
/* 111 */         prevHeight = verticalSlopeSrc.getLeafTexture().getHeight(inTileChunkX, inTileChunkZPrev); 
/* 112 */       if (prevHeightDiagonal == 32767 && diagonalSlopeSrc != null && diagonalSlopeSrc.getLoadState() >= 2)
/* 113 */         prevHeightDiagonal = diagonalSlopeSrc.getLeafTexture().getHeight(inTileChunkXPrev, inTileChunkZPrev); 
/* 114 */       if (prevHeight == 32767 || prevHeightDiagonal == 32767) {
/* 115 */         if (useSourceData)
/*     */           return; 
/* 117 */         int reX = (x < 15) ? (x + 1) : x;
/* 118 */         int reZ = (z < 15) ? (z + 1) : z;
/* 119 */         if (reX == x && reZ == z) {
/* 120 */           this.verticalSlope = 0;
/* 121 */           this.diagonalSlope = 0;
/* 122 */           this.slopeUnknown = false;
/*     */           
/*     */           return;
/*     */         } 
/* 126 */         int inTileChunkReX = ((mapTile.getChunkX() & 0x3) << 4) + reX;
/* 127 */         int inTileChunkReZ = ((mapTile.getChunkZ() & 0x3) << 4) + reZ;
/* 128 */         int reHeight = tileChunk.getLeafTexture().getHeight(inTileChunkReX, inTileChunkReZ);
/* 129 */         if (reHeight != 32767) {
/* 130 */           fixHeightType(reX, reZ, mapTile, tileChunk, prevChunk, prevChunkDiagonal, prevChunkHorisontal, reHeight, useSourceData, blockStateShortShapeCache);
/*     */         }
/*     */         return;
/*     */       } 
/*     */     } 
/* 135 */     this.verticalSlope = (byte)Math.max(-128, Math.min(127, height - prevHeight));
/* 136 */     this.diagonalSlope = (byte)Math.max(-128, Math.min(127, height - prevHeightDiagonal));
/* 137 */     this.slopeUnknown = false;
/*     */   }
/*     */   
/*     */   public void prepareForWriting(int defaultHeight) {
/* 141 */     if (this.overlays != null)
/* 142 */       this.overlays.clear(); 
/* 143 */     this.biome = null;
/* 144 */     this.state = class_2246.field_10124.method_9564();
/* 145 */     this.slopeUnknown = true;
/* 146 */     this.light = 0;
/* 147 */     this.glowing = false;
/* 148 */     this.height = (short)defaultHeight;
/* 149 */     this.topHeight = (short)defaultHeight;
/*     */   }
/*     */   
/*     */   public void write(class_2680 state, int height, int topHeight, class_5321<class_1959> biomeIn, byte light, boolean glowing, boolean cave) {
/* 153 */     this.state = state;
/* 154 */     setHeight(height);
/* 155 */     setTopHeight(topHeight);
/* 156 */     if (biomeIn != null)
/* 157 */       this.biome = biomeIn; 
/* 158 */     this.light = light;
/* 159 */     this.glowing = glowing;
/* 160 */     if (this.overlays != null && this.overlays.isEmpty())
/* 161 */       this.overlays = null; 
/*     */   }
/*     */   
/*     */   public void addOverlay(Overlay o) {
/* 165 */     if (this.overlays == null)
/* 166 */       this.overlays = new ArrayList<>(); 
/* 167 */     this.overlays.add(o);
/*     */   }
/*     */   
/*     */   public int getHeight() {
/* 171 */     return this.height;
/*     */   }
/*     */   
/*     */   public void setHeight(int h) {
/* 175 */     this.height = (short)h;
/*     */   }
/*     */   
/*     */   public int getTopHeight() {
/* 179 */     return this.topHeight;
/*     */   }
/*     */   
/*     */   public void setTopHeight(int h) {
/* 183 */     this.topHeight = (short)h;
/*     */   }
/*     */   
/*     */   public int getEffectiveHeight(BlockStateShortShapeCache blockStateShortShapeCache) {
/* 187 */     return getEffectiveHeight((WorldMap.settings.adjustHeightForCarpetLikeBlocks && blockStateShortShapeCache.isShort(this.state)));
/*     */   }
/*     */   
/*     */   public int getEffectiveHeight(boolean subtractOne) {
/* 191 */     int height = getHeight();
/* 192 */     if (subtractOne)
/* 193 */       height--; 
/* 194 */     return height;
/*     */   }
/*     */   
/*     */   public int getEffectiveTopHeight(boolean subtractOne) {
/* 198 */     int topHeight = getTopHeight();
/* 199 */     if (subtractOne && topHeight == getHeight())
/* 200 */       topHeight--; 
/* 201 */     return topHeight;
/*     */   }
/*     */   
/*     */   public class_5321<class_1959> getBiome() {
/* 205 */     return this.biome;
/*     */   }
/*     */   
/*     */   public void setBiome(class_5321<class_1959> biome) {
/* 209 */     this.biome = biome;
/*     */   }
/*     */   
/*     */   public ArrayList<Overlay> getOverlays() {
/* 213 */     return this.overlays;
/*     */   }
/*     */   
/*     */   public byte getVerticalSlope() {
/* 217 */     return this.verticalSlope;
/*     */   }
/*     */   
/*     */   public void setVerticalSlope(byte slope) {
/* 221 */     this.verticalSlope = slope;
/*     */   }
/*     */   
/*     */   public byte getDiagonalSlope() {
/* 225 */     return this.diagonalSlope;
/*     */   }
/*     */   
/*     */   public void setDiagonalSlope(byte slope) {
/* 229 */     this.diagonalSlope = slope;
/*     */   }
/*     */   
/*     */   public void setSlopeUnknown(boolean slopeUnknown) {
/* 233 */     this.slopeUnknown = slopeUnknown;
/*     */   }
/*     */   
/*     */   public int getNumberOfOverlays() {
/* 237 */     return (this.overlays == null) ? 0 : this.overlays.size();
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\region\MapBlock.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */