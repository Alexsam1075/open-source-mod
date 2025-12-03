/*     */ package xaero.map.highlight;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
/*     */ import java.nio.ByteBuffer;
/*     */ import net.minecraft.class_1937;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_5250;
/*     */ import net.minecraft.class_5321;
/*     */ import org.apache.commons.lang3.builder.HashCodeBuilder;
/*     */ import xaero.map.WorldMap;
/*     */ import xaero.map.mods.SupportMods;
/*     */ import xaero.map.pool.buffer.PoolTextureDirectBufferUnit;
/*     */ import xaero.map.world.MapDimension;
/*     */ 
/*     */ public class DimensionHighlighterHandler
/*     */ {
/*     */   private final MapDimension mapDimension;
/*     */   private final class_5321<class_1937> dimension;
/*     */   private final HighlighterRegistry registry;
/*     */   private final Long2ObjectMap<Integer> hashCodeCache;
/*  22 */   private final class_2561 SUBTLE_TOOLTIP_SEPARATOR = (class_2561)class_2561.method_43470(" | ");
/*  23 */   private final class_2561 BLUNT_TOOLTIP_SEPARATOR = (class_2561)class_2561.method_43470(" \n ");
/*     */ 
/*     */   
/*     */   public DimensionHighlighterHandler(MapDimension mapDimension, class_5321<class_1937> dimension, HighlighterRegistry registry) {
/*  27 */     this.mapDimension = mapDimension;
/*  28 */     this.dimension = dimension;
/*  29 */     this.registry = registry;
/*  30 */     this.hashCodeCache = (Long2ObjectMap<Integer>)new Long2ObjectOpenHashMap();
/*     */   }
/*     */   
/*     */   public int getRegionHash(int regionX, int regionZ) {
/*  34 */     synchronized (this) {
/*  35 */       long key = getKey(regionX, regionZ);
/*  36 */       Integer cachedHash = (Integer)this.hashCodeCache.get(key);
/*  37 */       if (cachedHash == null)
/*  38 */         cachedHash = Integer.valueOf(recalculateHash(regionX, regionZ)); 
/*  39 */       return cachedHash.intValue();
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean shouldApplyRegionHighlights(int regionX, int regionZ, boolean discovered) {
/*  44 */     class_5321<class_1937> dimension = this.dimension;
/*  45 */     for (AbstractHighlighter hl : this.registry.getHighlighters()) {
/*  46 */       if ((discovered || hl.isCoveringOutsideDiscovered()) && hl.regionHasHighlights(dimension, regionX, regionZ))
/*  47 */         return true; 
/*  48 */     }  return false;
/*     */   }
/*     */   
/*     */   public boolean shouldApplyTileChunkHighlights(int regionX, int regionZ, int insideTileChunkX, int insideTileChunkZ, boolean discovered) {
/*  52 */     int startChunkX = regionX << 5 | insideTileChunkX << 2;
/*  53 */     int startChunkZ = regionZ << 5 | insideTileChunkZ << 2;
/*  54 */     for (AbstractHighlighter hl : this.registry.getHighlighters()) {
/*  55 */       if (shouldApplyTileChunkHighlightsHelp(hl, regionX, regionZ, startChunkX, startChunkZ, discovered))
/*  56 */         return true; 
/*     */     } 
/*  58 */     return false;
/*     */   }
/*     */   
/*     */   private boolean shouldApplyTileChunkHighlights(AbstractHighlighter hl, int regionX, int regionZ, int insideTileChunkX, int insideTileChunkZ, boolean discovered) {
/*  62 */     int startChunkX = regionX << 5 | insideTileChunkX << 2;
/*  63 */     int startChunkZ = regionZ << 5 | insideTileChunkZ << 2;
/*  64 */     return shouldApplyTileChunkHighlightsHelp(hl, regionX, regionZ, startChunkX, startChunkZ, discovered);
/*     */   }
/*     */   
/*     */   private boolean shouldApplyTileChunkHighlightsHelp(AbstractHighlighter hl, int regionX, int regionZ, int startChunkX, int startChunkZ, boolean discovered) {
/*  68 */     if (!discovered && !hl.isCoveringOutsideDiscovered())
/*  69 */       return false; 
/*  70 */     class_5321<class_1937> dimension = this.dimension;
/*  71 */     if (!hl.regionHasHighlights(dimension, regionX, regionZ))
/*  72 */       return false; 
/*  73 */     for (int i = 0; i < 4; i++) {
/*  74 */       for (int j = 0; j < 4; j++) {
/*  75 */         if (hl.chunkIsHighlit(dimension, startChunkX | i, startChunkZ | j))
/*  76 */           return true; 
/*     */       } 
/*  78 */     }  return false;
/*     */   }
/*     */   
/*     */   public PoolTextureDirectBufferUnit applyChunkHighlightColors(int chunkX, int chunkZ, int innerChunkX, int innerChunkZ, PoolTextureDirectBufferUnit buffer, PoolTextureDirectBufferUnit highlitColorBuffer, boolean highlitBufferPrepared, boolean discovered, boolean separateBuffer) {
/*  82 */     boolean hasSomething = false;
/*  83 */     class_5321<class_1937> dimension = this.dimension;
/*  84 */     if (!separateBuffer) {
/*  85 */       highlitBufferPrepared = true;
/*  86 */       highlitColorBuffer = buffer;
/*     */     } 
/*  88 */     ByteBuffer highlitColorBufferDirect = (highlitColorBuffer == null) ? null : highlitColorBuffer.getDirectBuffer();
/*  89 */     for (AbstractHighlighter hl : this.registry.getHighlighters()) {
/*  90 */       if (!discovered && !hl.isCoveringOutsideDiscovered())
/*     */         continue; 
/*  92 */       int[] highlightColors = hl.getChunkHighlitColor(dimension, chunkX, chunkZ);
/*  93 */       if (highlightColors == null)
/*     */         continue; 
/*  95 */       if (!hasSomething && !highlitBufferPrepared) {
/*  96 */         highlitColorBuffer = WorldMap.textureDirectBufferPool.get((buffer == null));
/*  97 */         highlitColorBufferDirect = highlitColorBuffer.getDirectBuffer();
/*  98 */         if (buffer != null)
/*  99 */           highlitColorBufferDirect.put(buffer.getDirectBuffer()); 
/* 100 */         highlitColorBufferDirect.position(0);
/* 101 */         if (buffer != null)
/* 102 */           buffer.getDirectBuffer().position(0); 
/*     */       } 
/* 104 */       hasSomething = true;
/* 105 */       int textureOffset = innerChunkZ << 4 << 6 | innerChunkX << 4;
/* 106 */       for (int i = 0; i < highlightColors.length; i++) {
/* 107 */         int highlightColor = highlightColors[i];
/* 108 */         int hlAlpha = highlightColor & 0xFF;
/* 109 */         float hlAlphaFloat = hlAlpha / 255.0F;
/* 110 */         float oneMinusHlAlpha = 1.0F - hlAlphaFloat;
/* 111 */         int hlRed = highlightColor >> 8 & 0xFF;
/* 112 */         int hlGreen = highlightColor >> 16 & 0xFF;
/* 113 */         int hlBlue = highlightColor >> 24 & 0xFF;
/*     */         
/* 115 */         int index = textureOffset | i >> 4 << 6 | i & 0xF;
/* 116 */         int originalColor = highlitColorBufferDirect.getInt(index * 4);
/* 117 */         int red = originalColor >> 8 & 0xFF;
/* 118 */         int green = originalColor >> 16 & 0xFF;
/* 119 */         int blue = originalColor >> 24 & 0xFF;
/* 120 */         int alpha = originalColor & 0xFF;
/* 121 */         red = (int)(red * oneMinusHlAlpha + hlRed * hlAlphaFloat);
/* 122 */         green = (int)(green * oneMinusHlAlpha + hlGreen * hlAlphaFloat);
/* 123 */         blue = (int)(blue * oneMinusHlAlpha + hlBlue * hlAlphaFloat);
/* 124 */         if (red > 255)
/* 125 */           red = 255; 
/* 126 */         if (green > 255)
/* 127 */           green = 255; 
/* 128 */         if (blue > 255)
/* 129 */           blue = 255; 
/* 130 */         highlitColorBufferDirect.putInt(index * 4, blue << 24 | green << 16 | red << 8 | alpha);
/*     */       } 
/*     */     } 
/* 133 */     if (!hasSomething)
/* 134 */       return null; 
/* 135 */     return highlitColorBuffer;
/*     */   }
/*     */   
/*     */   private int recalculateHash(int regionX, int regionZ) {
/* 139 */     HashCodeBuilder hashcodeBuilder = new HashCodeBuilder();
/* 140 */     for (AbstractHighlighter hl : this.registry.getHighlighters()) {
/* 141 */       hashcodeBuilder.append(hl.calculateRegionHash(this.dimension, regionX, regionZ));
/* 142 */       hashcodeBuilder.append(hl.isCoveringOutsideDiscovered());
/*     */     } 
/* 144 */     int builtHash = hashcodeBuilder.build().intValue();
/* 145 */     long key = getKey(regionX, regionZ);
/* 146 */     this.hashCodeCache.put(key, Integer.valueOf(builtHash));
/* 147 */     return builtHash;
/*     */   }
/*     */   
/*     */   public void clearCachedHash(int regionX, int regionZ) {
/* 151 */     long key = getKey(regionX, regionZ);
/* 152 */     this.hashCodeCache.remove(key);
/* 153 */     this.mapDimension.onClearCachedHighlightHash(regionX, regionZ);
/* 154 */     if (SupportMods.minimap()) {
/* 155 */       SupportMods.xaeroMinimap.onClearHighlightHash(regionX, regionZ);
/*     */     }
/*     */   }
/*     */   
/*     */   public void clearCachedHashes() {
/* 160 */     this.hashCodeCache.clear();
/* 161 */     this.mapDimension.onClearCachedHighlightHashes();
/* 162 */     if (SupportMods.minimap())
/* 163 */       SupportMods.xaeroMinimap.onClearHighlightHashes(); 
/*     */   }
/*     */   
/*     */   public class_2561 getBlockHighlightSubtleTooltip(int blockX, int blockZ, boolean discovered) {
/* 167 */     return getBlockHighlightTooltip(blockX, blockZ, discovered, true);
/*     */   }
/*     */   
/*     */   public class_2561 getBlockHighlightBluntTooltip(int blockX, int blockZ, boolean discovered) {
/* 171 */     return getBlockHighlightTooltip(blockX, blockZ, discovered, false);
/*     */   }
/*     */   private class_2561 getBlockHighlightTooltip(int blockX, int blockZ, boolean discovered, boolean subtle) {
/*     */     class_5250 class_5250;
/* 175 */     class_5321<class_1937> dimension = this.dimension;
/* 176 */     int tileChunkX = blockX >> 6;
/* 177 */     int tileChunkZ = blockZ >> 6;
/* 178 */     int regionX = tileChunkX >> 3;
/* 179 */     int regionZ = tileChunkZ >> 3;
/* 180 */     if (!shouldApplyRegionHighlights(regionX, regionZ, discovered))
/* 181 */       return null; 
/* 182 */     int localTileChunkX = tileChunkX & 0x7;
/* 183 */     int localTileChunkZ = tileChunkZ & 0x7;
/* 184 */     class_2561 result = null;
/* 185 */     for (AbstractHighlighter hl : this.registry.getHighlighters()) {
/* 186 */       if (!shouldApplyTileChunkHighlights(hl, regionX, regionZ, localTileChunkX, localTileChunkZ, discovered))
/*     */         continue; 
/* 188 */       class_2561 hlTooltip = subtle ? hl.getBlockHighlightSubtleTooltip(dimension, blockX, blockZ) : hl.getBlockHighlightBluntTooltip(dimension, blockX, blockZ);
/* 189 */       if (hlTooltip == null)
/*     */         continue; 
/* 191 */       if (result == null) {
/* 192 */         class_5250 = class_2561.method_43470("");
/*     */       } else {
/* 194 */         class_5250.method_10855().add(subtle ? this.SUBTLE_TOOLTIP_SEPARATOR : this.BLUNT_TOOLTIP_SEPARATOR);
/* 195 */       }  class_5250.method_10855().add(hlTooltip);
/*     */     } 
/* 197 */     return (class_2561)class_5250;
/*     */   }
/*     */   
/*     */   public static long getKey(int regionX, int regionZ) {
/* 201 */     return regionZ << 32L | regionX & 0xFFFFFFFFL;
/*     */   }
/*     */   
/*     */   public static int getXFromKey(long key) {
/* 205 */     return (int)(key & 0xFFFFFFFFFFFFFFFFL);
/*     */   }
/*     */   
/*     */   public static int getZFromKey(long key) {
/* 209 */     return (int)(key >> 32L);
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\highlight\DimensionHighlighterHandler.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */