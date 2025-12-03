/*     */ package xaero.map.misc;
/*     */ 
/*     */ import net.minecraft.class_1937;
/*     */ import net.minecraft.class_1944;
/*     */ import net.minecraft.class_2246;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_2680;
/*     */ import net.minecraft.class_2688;
/*     */ import net.minecraft.class_2791;
/*     */ import net.minecraft.class_2818;
/*     */ import net.minecraft.class_2826;
/*     */ import net.minecraft.class_2902;
/*     */ import net.minecraft.class_3481;
/*     */ import net.minecraft.class_3532;
/*     */ import net.minecraft.class_3619;
/*     */ import xaero.map.MapWriter;
/*     */ import xaero.map.WorldMap;
/*     */ 
/*     */ 
/*     */ public class CaveStartCalculator
/*     */ {
/*     */   private final class_2338.class_2339 mutableBlockPos;
/*     */   private final CachedFunction<class_2688<?, ?>, Boolean> transparentCache;
/*     */   
/*     */   public CaveStartCalculator(MapWriter mapWriter) {
/*  26 */     this.mutableBlockPos = new class_2338.class_2339();
/*  27 */     this.transparentCache = new CachedFunction<>(state -> Boolean.valueOf(mapWriter.shouldOverlay(state)));
/*     */   }
/*     */   
/*     */   public int getCaving(double playerX, double playerY, double playerZ, class_1937 world) {
/*  31 */     if (WorldMap.settings.autoCaveMode == 0)
/*  32 */       return Integer.MAX_VALUE; 
/*  33 */     int worldBottomY = world.method_31607();
/*  34 */     int worldTopY = world.method_31600();
/*  35 */     int y = (int)playerY + 1;
/*  36 */     int defaultCaveStart = y + 3;
/*  37 */     int defaultResult = Integer.MAX_VALUE;
/*  38 */     if (y > worldTopY || y < worldBottomY)
/*  39 */       return defaultResult; 
/*  40 */     int x = class_3532.method_15357(playerX);
/*  41 */     int z = class_3532.method_15357(playerZ);
/*  42 */     int roofRadius = (WorldMap.settings.autoCaveMode < 0) ? 1 : (WorldMap.settings.autoCaveMode - 1);
/*  43 */     int roofDiameter = 1 + roofRadius * 2;
/*  44 */     int startX = x - roofRadius;
/*  45 */     int startZ = z - roofRadius;
/*  46 */     boolean ignoringHeightmaps = WorldMap.settings.ignoreHeightmaps;
/*  47 */     int bottom = y;
/*  48 */     int top = Integer.MAX_VALUE;
/*  49 */     class_2791 prevBChunk = null;
/*  50 */     int potentialResult = defaultCaveStart;
/*  51 */     for (int o = 0; o < roofDiameter; o++) {
/*     */       
/*  53 */       for (int p = 0; p < roofDiameter; p++) {
/*  54 */         int skyLight, currentX = startX + o;
/*  55 */         int currentZ = startZ + p;
/*  56 */         this.mutableBlockPos.method_10103(currentX, y, currentZ);
/*  57 */         class_2818 class_2818 = world.method_8497(currentX >> 4, currentZ >> 4);
/*     */         
/*  59 */         if (class_2818 != null) {
/*  60 */           skyLight = world.method_8314(class_1944.field_9284, (class_2338)this.mutableBlockPos);
/*     */         } else {
/*  62 */           return defaultResult;
/*  63 */         }  if (!ignoringHeightmaps) {
/*  64 */           if (skyLight < 15)
/*  65 */           { int insideX = currentX & 0xF;
/*  66 */             int insideZ = currentZ & 0xF;
/*  67 */             top = class_2818.method_12005(class_2902.class_2903.field_13202, insideX, insideZ); }
/*     */           else
/*  69 */           { return defaultResult; } 
/*  70 */         } else if (class_2818 != prevBChunk) {
/*  71 */           class_2826[] sections = class_2818.method_12006();
/*  72 */           if (sections.length == 0)
/*  73 */             return defaultResult; 
/*  74 */           int playerSection = y - worldBottomY >> 4;
/*  75 */           boolean foundSomething = false;
/*  76 */           for (int j = playerSection; j < sections.length; j++) {
/*  77 */             class_2826 searchedSection = sections[j];
/*  78 */             if (!searchedSection.method_38292()) {
/*  79 */               if (!foundSomething) {
/*  80 */                 bottom = Math.max(bottom, worldBottomY + (j << 4));
/*  81 */                 foundSomething = true;
/*     */               } 
/*  83 */               top = worldBottomY + (j << 4) + 15;
/*     */             } 
/*     */           } 
/*  86 */           if (!foundSomething)
/*  87 */             return defaultResult; 
/*  88 */           class_2818 class_28181 = class_2818;
/*     */         } 
/*  90 */         if (top < worldBottomY)
/*  91 */           return defaultResult; 
/*  92 */         if (top > worldTopY)
/*  93 */           top = worldTopY; 
/*  94 */         int i = bottom; while (true) { if (i <= top) {
/*  95 */             this.mutableBlockPos.method_33098(i);
/*  96 */             class_2680 state = world.method_8320((class_2338)this.mutableBlockPos);
/*  97 */             if (!state.method_26215() && state.method_26223() != class_3619.field_15971 && !(state.method_26204() instanceof net.minecraft.class_2404) && !state.method_26164(class_3481.field_15503) && !((Boolean)this.transparentCache.apply(state)).booleanValue() && state.method_26204() != class_2246.field_10499) {
/*  98 */               if (o == p && o == roofRadius)
/*  99 */                 potentialResult = Math.min(i, defaultCaveStart);  break;
/*     */             }  i++;
/*     */             continue;
/*     */           } 
/* 103 */           return defaultResult; }
/*     */       
/*     */       } 
/* 106 */     }  return potentialResult;
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\misc\CaveStartCalculator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */