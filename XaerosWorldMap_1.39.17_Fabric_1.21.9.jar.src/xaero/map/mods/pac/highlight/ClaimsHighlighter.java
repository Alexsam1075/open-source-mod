/*     */ package xaero.map.mods.pac.highlight;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.class_1074;
/*     */ import net.minecraft.class_124;
/*     */ import net.minecraft.class_1937;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_2583;
/*     */ import net.minecraft.class_5321;
/*     */ import xaero.map.WorldMap;
/*     */ import xaero.map.highlight.ChunkHighlighter;
/*     */ import xaero.pac.client.claims.api.IClientClaimsManagerAPI;
/*     */ import xaero.pac.client.claims.api.IClientDimensionClaimsManagerAPI;
/*     */ import xaero.pac.client.claims.api.IClientRegionClaimsAPI;
/*     */ import xaero.pac.client.claims.player.api.IClientPlayerClaimInfoAPI;
/*     */ import xaero.pac.common.claims.player.api.IPlayerChunkClaimAPI;
/*     */ import xaero.pac.common.claims.player.api.IPlayerClaimPosListAPI;
/*     */ import xaero.pac.common.claims.player.api.IPlayerDimensionClaimsAPI;
/*     */ import xaero.pac.common.server.player.config.PlayerConfig;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClaimsHighlighter
/*     */   extends ChunkHighlighter
/*     */ {
/*     */   private final IClientClaimsManagerAPI<IClientPlayerClaimInfoAPI<IPlayerDimensionClaimsAPI<IPlayerClaimPosListAPI>>, IClientDimensionClaimsManagerAPI<IClientRegionClaimsAPI>> claimsManager;
/*     */   private class_2561 cachedTooltip;
/*     */   private IPlayerChunkClaimAPI cachedTooltipFor;
/*     */   private String cachedForCustomName;
/*     */   private int cachedForClaimsColor;
/*     */   
/*     */   public ClaimsHighlighter(IClientClaimsManagerAPI<IClientPlayerClaimInfoAPI<IPlayerDimensionClaimsAPI<IPlayerClaimPosListAPI>>, IClientDimensionClaimsManagerAPI<IClientRegionClaimsAPI>> claimsManager) {
/*  35 */     super(true);
/*  36 */     this.claimsManager = claimsManager;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean regionHasHighlights(class_5321<class_1937> dimension, int regionX, int regionZ) {
/*  41 */     if (!WorldMap.settings.displayClaims)
/*  42 */       return false; 
/*  43 */     IClientDimensionClaimsManagerAPI<IClientRegionClaimsAPI> claimsDimension = this.claimsManager.getDimension(dimension.method_29177());
/*  44 */     if (claimsDimension == null)
/*  45 */       return false; 
/*  46 */     return (claimsDimension.getRegion(regionX, regionZ) != null);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int[] getColors(class_5321<class_1937> dimension, int chunkX, int chunkZ) {
/*  51 */     if (!WorldMap.settings.displayClaims)
/*  52 */       return null; 
/*  53 */     IPlayerChunkClaimAPI currentClaim = this.claimsManager.get(dimension.method_29177(), chunkX, chunkZ);
/*  54 */     if (currentClaim == null)
/*  55 */       return null; 
/*  56 */     IPlayerChunkClaimAPI topClaim = this.claimsManager.get(dimension.method_29177(), chunkX, chunkZ - 1);
/*  57 */     IPlayerChunkClaimAPI rightClaim = this.claimsManager.get(dimension.method_29177(), chunkX + 1, chunkZ);
/*  58 */     IPlayerChunkClaimAPI bottomClaim = this.claimsManager.get(dimension.method_29177(), chunkX, chunkZ + 1);
/*  59 */     IPlayerChunkClaimAPI leftClaim = this.claimsManager.get(dimension.method_29177(), chunkX - 1, chunkZ);
/*  60 */     IClientPlayerClaimInfoAPI<IPlayerDimensionClaimsAPI<IPlayerClaimPosListAPI>> claimInfo = this.claimsManager.getPlayerInfo(currentClaim.getPlayerId());
/*  61 */     int claimColor = getClaimsColor(currentClaim, claimInfo);
/*  62 */     int claimColorFormatted = (claimColor & 0xFF) << 24 | (claimColor >> 8 & 0xFF) << 16 | (claimColor >> 16 & 0xFF) << 8;
/*  63 */     int fillOpacity = WorldMap.settings.claimsFillOpacity;
/*  64 */     int borderOpacity = WorldMap.settings.claimsBorderOpacity;
/*  65 */     int centerColor = claimColorFormatted | 255 * fillOpacity / 100;
/*  66 */     int sideColor = claimColorFormatted | 255 * borderOpacity / 100;
/*  67 */     this.resultStore[0] = centerColor;
/*  68 */     this.resultStore[1] = (topClaim != currentClaim) ? sideColor : centerColor;
/*  69 */     this.resultStore[2] = (rightClaim != currentClaim) ? sideColor : centerColor;
/*  70 */     this.resultStore[3] = (bottomClaim != currentClaim) ? sideColor : centerColor;
/*  71 */     this.resultStore[4] = (leftClaim != currentClaim) ? sideColor : centerColor;
/*  72 */     return this.resultStore;
/*     */   }
/*     */ 
/*     */   
/*     */   public int calculateRegionHash(class_5321<class_1937> dimension, int regionX, int regionZ) {
/*  77 */     if (!WorldMap.settings.displayClaims)
/*  78 */       return 0; 
/*  79 */     IClientDimensionClaimsManagerAPI<IClientRegionClaimsAPI> claimsDimension = this.claimsManager.getDimension(dimension.method_29177());
/*  80 */     if (claimsDimension == null)
/*  81 */       return 0; 
/*  82 */     IClientRegionClaimsAPI claimsRegion = claimsDimension.getRegion(regionX, regionZ);
/*  83 */     if (claimsRegion == null)
/*  84 */       return 0; 
/*  85 */     IClientRegionClaimsAPI topRegion = claimsDimension.getRegion(regionX, regionZ - 1);
/*  86 */     IClientRegionClaimsAPI rightRegion = claimsDimension.getRegion(regionX + 1, regionZ);
/*  87 */     IClientRegionClaimsAPI bottomRegion = claimsDimension.getRegion(regionX, regionZ + 1);
/*  88 */     IClientRegionClaimsAPI leftRegion = claimsDimension.getRegion(regionX - 1, regionZ);
/*  89 */     long accumulator = 0L;
/*  90 */     accumulator = accumulator * 37L + WorldMap.settings.claimsBorderOpacity;
/*  91 */     accumulator = accumulator * 37L + WorldMap.settings.claimsFillOpacity;
/*  92 */     for (int i = 0; i < 32; i++) {
/*  93 */       accumulator = accountClaim(accumulator, (topRegion != null) ? topRegion.get(i, 31) : null);
/*  94 */       accumulator = accountClaim(accumulator, (rightRegion != null) ? rightRegion.get(0, i) : null);
/*  95 */       accumulator = accountClaim(accumulator, (bottomRegion != null) ? bottomRegion.get(i, 0) : null);
/*  96 */       accumulator = accountClaim(accumulator, (leftRegion != null) ? leftRegion.get(31, i) : null);
/*  97 */       for (int j = 0; j < 32; j++) {
/*  98 */         IPlayerChunkClaimAPI claim = claimsRegion.get(i, j);
/*  99 */         accumulator = accountClaim(accumulator, claim);
/*     */       } 
/*     */     } 
/* 102 */     return (int)(accumulator >> 32L) * 37 + (int)(accumulator & 0xFFFFFFFFFFFFFFFFL);
/*     */   }
/*     */   
/*     */   private long accountClaim(long accumulator, IPlayerChunkClaimAPI claim) {
/* 106 */     if (claim != null) {
/* 107 */       UUID playerId = claim.getPlayerId();
/* 108 */       accumulator += playerId.getLeastSignificantBits();
/* 109 */       accumulator *= 37L;
/* 110 */       accumulator += claim.getPlayerId().getMostSignificantBits();
/* 111 */       accumulator *= 37L;
/* 112 */       IClientPlayerClaimInfoAPI<IPlayerDimensionClaimsAPI<IPlayerClaimPosListAPI>> claimInfo = this.claimsManager.getPlayerInfo(playerId);
/* 113 */       accumulator += getClaimsColor(claim, claimInfo);
/* 114 */       accumulator *= 37L;
/* 115 */       accumulator += claim.isForceloadable() ? 1L : 0L;
/* 116 */       accumulator *= 37L;
/* 117 */       accumulator += claim.getSubConfigIndex();
/*     */     } 
/* 119 */     accumulator *= 37L;
/* 120 */     return accumulator;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean chunkIsHighlit(class_5321<class_1937> dimension, int chunkX, int chunkZ) {
/* 125 */     return (this.claimsManager.get(dimension.method_29177(), chunkX, chunkZ) != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public class_2561 getChunkHighlightSubtleTooltip(class_5321<class_1937> dimension, int chunkX, int chunkZ) {
/* 130 */     IPlayerChunkClaimAPI currentClaim = this.claimsManager.get(dimension.method_29177(), chunkX, chunkZ);
/* 131 */     if (currentClaim == null)
/* 132 */       return null; 
/* 133 */     UUID currentClaimId = currentClaim.getPlayerId();
/* 134 */     IClientPlayerClaimInfoAPI<IPlayerDimensionClaimsAPI<IPlayerClaimPosListAPI>> claimInfo = this.claimsManager.getPlayerInfo(currentClaimId);
/* 135 */     String customName = getClaimsName(currentClaim, claimInfo);
/* 136 */     int actualClaimsColor = getClaimsColor(currentClaim, claimInfo);
/* 137 */     int claimsColor = actualClaimsColor | 0xFF000000;
/* 138 */     if (!Objects.equals(currentClaim, this.cachedTooltipFor) || this.cachedForClaimsColor != claimsColor || !Objects.equals(customName, this.cachedForCustomName)) {
/* 139 */       this.cachedTooltip = (class_2561)class_2561.method_43470("□ ").method_27694(s -> s.method_36139(claimsColor));
/* 140 */       if (Objects.equals(currentClaimId, PlayerConfig.SERVER_CLAIM_UUID)) {
/* 141 */         this.cachedTooltip.method_10855().add(class_2561.method_43469("gui.xaero_wm_pac_server_claim_tooltip", new Object[] { currentClaim.isForceloadable() ? class_2561.method_43471("gui.xaero_wm_pac_marked_for_forceload") : "" }).method_27692(class_124.field_1068));
/* 142 */       } else if (Objects.equals(currentClaimId, PlayerConfig.EXPIRED_CLAIM_UUID)) {
/* 143 */         this.cachedTooltip.method_10855().add(class_2561.method_43469("gui.xaero_wm_pac_expired_claim_tooltip", new Object[] { currentClaim.isForceloadable() ? class_2561.method_43471("gui.xaero_wm_pac_marked_for_forceload") : "" }).method_27692(class_124.field_1068));
/*     */       } else {
/* 145 */         this.cachedTooltip.method_10855().add(class_2561.method_43469("gui.xaero_wm_pac_claim_tooltip", new Object[] { claimInfo.getPlayerUsername(), currentClaim.isForceloadable() ? class_2561.method_43471("gui.xaero_wm_pac_marked_for_forceload") : "" }).method_27692(class_124.field_1068));
/* 146 */       }  if (!customName.isEmpty()) {
/* 147 */         this.cachedTooltip.method_10855().add(0, class_2561.method_43470(class_1074.method_4662(customName, new Object[0]) + " - ").method_27692(class_124.field_1068));
/*     */       }
/* 149 */       this.cachedTooltipFor = currentClaim;
/* 150 */       this.cachedForCustomName = customName;
/* 151 */       this.cachedForClaimsColor = claimsColor;
/*     */     } 
/* 153 */     return this.cachedTooltip;
/*     */   }
/*     */ 
/*     */   
/*     */   public class_2561 getChunkHighlightBluntTooltip(class_5321<class_1937> dimension, int chunkX, int chunkZ) {
/* 158 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addMinimapBlockHighlightTooltips(List<class_2561> list, class_5321<class_1937> dimension, int blockX, int blockZ, int width) {}
/*     */ 
/*     */   
/*     */   private String getClaimsName(IPlayerChunkClaimAPI currentClaim, IClientPlayerClaimInfoAPI<IPlayerDimensionClaimsAPI<IPlayerClaimPosListAPI>> claimInfo) {
/* 167 */     int subConfigIndex = currentClaim.getSubConfigIndex();
/* 168 */     String customName = claimInfo.getClaimsName(subConfigIndex);
/* 169 */     if (subConfigIndex != -1 && customName == null)
/* 170 */       customName = claimInfo.getClaimsName(); 
/* 171 */     return customName;
/*     */   }
/*     */   
/*     */   private int getClaimsColor(IPlayerChunkClaimAPI currentClaim, IClientPlayerClaimInfoAPI<IPlayerDimensionClaimsAPI<IPlayerClaimPosListAPI>> claimInfo) {
/* 175 */     int subConfigIndex = currentClaim.getSubConfigIndex();
/* 176 */     Integer actualClaimsColor = claimInfo.getClaimsColor(subConfigIndex);
/* 177 */     if (subConfigIndex != -1 && actualClaimsColor == null)
/* 178 */       actualClaimsColor = Integer.valueOf(claimInfo.getClaimsColor()); 
/* 179 */     return actualClaimsColor.intValue();
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\mods\pac\highlight\ClaimsHighlighter.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */