/*     */ package xaero.map.settings;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileReader;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.util.Collection;
/*     */ import net.minecraft.class_1074;
/*     */ import net.minecraft.class_1657;
/*     */ import net.minecraft.class_1792;
/*     */ import net.minecraft.class_1937;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_3288;
/*     */ import net.minecraft.class_7923;
/*     */ import org.apache.commons.lang3.builder.HashCodeBuilder;
/*     */ import xaero.map.MapFullReloader;
/*     */ import xaero.map.MapProcessor;
/*     */ import xaero.map.WorldMap;
/*     */ import xaero.map.WorldMapSession;
/*     */ import xaero.map.effects.Effects;
/*     */ import xaero.map.gui.ExportScreen;
/*     */ import xaero.map.mcworld.WorldMapClientWorldData;
/*     */ import xaero.map.misc.Misc;
/*     */ import xaero.map.mods.SupportMods;
/*     */ import xaero.map.world.MapDimension;
/*     */ import xaero.map.world.MapWorld;
/*     */ 
/*     */ public class ModSettings {
/*  32 */   public String[] arrowColourNames = new String[] { "gui.xaero_wm_red", "gui.xaero_wm_green", "gui.xaero_wm_blue", "gui.xaero_wm_yellow", "gui.xaero_wm_purple", "gui.xaero_wm_white", "gui.xaero_wm_black", "gui.xaero_wm_legacy_color" };
/*     */   
/*  34 */   public static float[][] arrowColours = new float[][] { { 0.8F, 0.1F, 0.1F, 1.0F }, { 0.09F, 0.57F, 0.0F, 1.0F }, { 0.0F, 0.55F, 1.0F, 1.0F }, { 1.0F, 0.93F, 0.0F, 1.0F }, { 0.73F, 0.33F, 0.83F, 1.0F }, { 1.0F, 1.0F, 1.0F, 1.0F }, { 0.0F, 0.0F, 0.0F, 1.0F }, { 0.4588F, 0.0F, 0.0F, 1.0F } };
/*     */   
/*     */   public static int ignoreUpdate;
/*     */   
/*     */   public static final String format = "§";
/*     */   
/*     */   public static boolean updateNotification = true;
/*     */   
/*     */   private int regionCacheHashCode;
/*     */   public boolean debug = false;
/*     */   public boolean detailed_debug = false;
/*     */   public boolean lighting = true;
/*     */   public boolean loadChunks = true;
/*     */   public boolean updateChunks = true;
/*  48 */   public int terrainSlopes = 2;
/*  49 */   public String[] slopeNames = new String[] { "gui.xaero_off", "gui.xaero_wm_slopes_legacy", "gui.xaero_wm_slopes_default_3d", "gui.xaero_wm_slopes_default_2d" };
/*     */   public boolean terrainDepth = true;
/*     */   public boolean footsteps = true;
/*     */   public boolean flowers = true;
/*     */   public boolean coordinates = true;
/*     */   public boolean hoveredBiome = true;
/*  55 */   public int colours = 0;
/*  56 */   public String[] colourNames = new String[] { "gui.xaero_accurate", "gui.xaero_vanilla" };
/*     */   
/*     */   public boolean biomeColorsVanillaMode = false;
/*     */   public boolean differentiateByServerAddress = true;
/*     */   public boolean waypoints = true;
/*     */   public boolean renderArrow = true;
/*     */   public boolean displayZoom = true;
/*     */   public boolean openMapAnimation = true;
/*  64 */   public float worldmapWaypointsScale = 1.0F;
/*  65 */   public int reloadVersion = 0;
/*     */   public boolean reloadEverything = false;
/*     */   public boolean zoomButtons = true;
/*     */   public boolean waypointBackgrounds = true;
/*     */   private boolean caveMapsAllowed = true;
/*     */   public boolean pauseRequests = false;
/*     */   public boolean extraDebug = false;
/*     */   public boolean ignoreHeightmaps;
/*  73 */   public static String mapItemId = null;
/*  74 */   public static class_1792 mapItem = null;
/*     */   public boolean detectAmbiguousY = true;
/*     */   public boolean showDisabledWaypoints;
/*     */   public boolean closeWaypointsWhenHopping = true;
/*     */   public boolean adjustHeightForCarpetLikeBlocks = true;
/*     */   public boolean onlyCurrentMapWaypoints = false;
/*  80 */   public double minZoomForLocalWaypoints = 0.0D;
/*  81 */   public int arrowColour = -2;
/*     */   public boolean minimapRadar = true;
/*     */   public boolean renderWaypoints = true;
/*     */   public boolean partialYTeleportation = true;
/*     */   public boolean displayStainedGlass = true;
/*  86 */   public int caveModeDepth = 30;
/*  87 */   public int autoCaveMode = -1;
/*     */   public boolean legibleCaveMaps;
/*  89 */   public int caveModeStart = Integer.MAX_VALUE;
/*     */   public boolean displayCaveModeStart = true;
/*  91 */   public int caveModeToggleTimer = 1000;
/*  92 */   public int defaultCaveModeType = 1;
/*     */   public boolean biomeBlending = true;
/*     */   public boolean multipleImagesExport;
/*     */   public boolean nightExport;
/*     */   public boolean highlightsExport;
/*  97 */   public int exportScaleDownSquare = 20;
/*     */   public boolean allowInternetAccess = true;
/*  99 */   public int mapWritingDistance = -1;
/*     */   
/*     */   public boolean isCaveMapsAllowed() {
/* 102 */     if (!this.caveMapsAllowed)
/* 103 */       return false; 
/* 104 */     WorldMapSession worldmapSession = WorldMapSession.getCurrentSession();
/* 105 */     if (SupportMods.minimap() && SupportMods.xaeroMinimap.isFairPlay() && (worldmapSession == null || 
/* 106 */       !worldmapSession.getMapProcessor().isConsideringNetherFairPlay() || worldmapSession.getMapProcessor().getMapWorld().getCurrentDimensionId() != class_1937.field_25180))
/* 107 */       return false; 
/* 108 */     if ((class_310.method_1551()).field_1687 == null)
/* 109 */       return true; 
/* 110 */     if (Misc.hasEffect(Effects.NO_CAVE_MAPS) || Misc.hasEffect(Effects.NO_CAVE_MAPS_HARMFUL))
/* 111 */       return false; 
/* 112 */     WorldMapClientWorldData clientData = WorldMapClientWorldDataHelper.getCurrentWorldData();
/* 113 */     return (((clientData.getSyncedRules()).allowCaveModeOnServer && (class_310.method_1551()).field_1687.method_27983() != class_1937.field_25180) || (
/* 114 */       (clientData.getSyncedRules()).allowNetherCaveModeOnServer && (class_310.method_1551()).field_1687.method_27983() == class_1937.field_25180));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean trackedPlayers = true;
/*     */   
/*     */   public boolean displayClaims = true;
/* 121 */   public int claimsFillOpacity = 46;
/* 122 */   public int claimsBorderOpacity = 80;
/* 123 */   public int claimsOpacity = this.claimsBorderOpacity;
/*     */   
/*     */   public void saveSettings() throws IOException {
/* 126 */     PrintWriter writer = new PrintWriter(new FileWriter(WorldMap.optionsFile));
/* 127 */     writer.println("ignoreUpdate:" + ignoreUpdate);
/* 128 */     writer.println("updateNotification:" + updateNotification);
/* 129 */     writer.println("allowInternetAccess:" + this.allowInternetAccess);
/* 130 */     writer.println("differentiateByServerAddress:" + this.differentiateByServerAddress);
/* 131 */     writer.println("caveMapsAllowed:" + this.caveMapsAllowed);
/* 132 */     writer.println("debug:" + this.debug);
/* 133 */     writer.println("lighting:" + this.lighting);
/* 134 */     writer.println("colours:" + this.colours);
/* 135 */     writer.println("loadChunks:" + this.loadChunks);
/* 136 */     writer.println("updateChunks:" + this.updateChunks);
/* 137 */     writer.println("terrainSlopes:" + this.terrainSlopes);
/* 138 */     writer.println("terrainDepth:" + this.terrainDepth);
/* 139 */     writer.println("footsteps:" + this.footsteps);
/*     */     
/* 141 */     writer.println("flowers:" + this.flowers);
/* 142 */     writer.println("coordinates:" + this.coordinates);
/* 143 */     writer.println("hoveredBiome:" + this.hoveredBiome);
/* 144 */     writer.println("biomeColorsVanillaMode:" + this.biomeColorsVanillaMode);
/* 145 */     writer.println("waypoints:" + this.waypoints);
/* 146 */     writer.println("renderArrow:" + this.renderArrow);
/* 147 */     writer.println("displayZoom:" + this.displayZoom);
/* 148 */     writer.println("worldmapWaypointsScale:" + this.worldmapWaypointsScale);
/* 149 */     writer.println("openMapAnimation:" + this.openMapAnimation);
/* 150 */     writer.println("reloadVersion:" + this.reloadVersion);
/* 151 */     writer.println("reloadEverything:" + this.reloadEverything);
/* 152 */     writer.println("zoomButtons:" + this.zoomButtons);
/* 153 */     writer.println("waypointBackgrounds:" + this.waypointBackgrounds);
/* 154 */     if (mapItemId != null)
/* 155 */       writer.println("mapItemId:" + mapItemId); 
/* 156 */     writer.println("detectAmbiguousY:" + this.detectAmbiguousY);
/* 157 */     writer.println("showDisabledWaypoints:" + this.showDisabledWaypoints);
/* 158 */     writer.println("closeWaypointsWhenHopping:" + this.closeWaypointsWhenHopping);
/* 159 */     writer.println("adjustHeightForCarpetLikeBlocks:" + this.adjustHeightForCarpetLikeBlocks);
/* 160 */     writer.println("onlyCurrentMapWaypoints:" + this.onlyCurrentMapWaypoints);
/* 161 */     writer.println("minZoomForLocalWaypoints:" + this.minZoomForLocalWaypoints);
/* 162 */     writer.println("arrowColour:" + this.arrowColour);
/* 163 */     writer.println("minimapRadar:" + this.minimapRadar);
/* 164 */     writer.println("renderWaypoints:" + this.renderWaypoints);
/* 165 */     writer.println("partialYTeleportation:" + this.partialYTeleportation);
/* 166 */     writer.println("displayStainedGlass:" + this.displayStainedGlass);
/* 167 */     writer.println("caveModeDepth:" + this.caveModeDepth);
/* 168 */     writer.println("caveModeStart:" + this.caveModeStart);
/* 169 */     writer.println("autoCaveMode:" + this.autoCaveMode);
/* 170 */     writer.println("legibleCaveMaps:" + this.legibleCaveMaps);
/* 171 */     writer.println("displayCaveModeStart:" + this.displayCaveModeStart);
/* 172 */     writer.println("caveModeToggleTimer:" + this.caveModeToggleTimer);
/* 173 */     writer.println("defaultCaveModeType:" + this.defaultCaveModeType);
/* 174 */     writer.println("biomeBlending:" + this.biomeBlending);
/* 175 */     writer.println("trackedPlayers:" + this.trackedPlayers);
/* 176 */     writer.println("multipleImagesExport:" + this.multipleImagesExport);
/* 177 */     writer.println("nightExport:" + this.nightExport);
/* 178 */     writer.println("highlightsExport:" + this.highlightsExport);
/* 179 */     writer.println("exportScaleDownSquare:" + this.exportScaleDownSquare);
/* 180 */     writer.println("mapWritingDistance:" + this.mapWritingDistance);
/*     */ 
/*     */ 
/*     */     
/* 184 */     writer.println("displayClaims:" + this.displayClaims);
/* 185 */     writer.println("claimsFillOpacity:" + this.claimsFillOpacity);
/* 186 */     writer.println("claimsBorderOpacity:" + this.claimsBorderOpacity);
/*     */     
/* 188 */     writer.println("globalVersion:" + WorldMap.globalVersion);
/* 189 */     writer.close();
/*     */   }
/*     */   
/*     */   private void loadDefaultSettings() throws IOException {
/* 193 */     File mainConfigFile = WorldMap.optionsFile;
/* 194 */     File defaultConfigFile = mainConfigFile.toPath().getParent().resolveSibling("defaultconfigs").resolve(mainConfigFile.getName()).toFile();
/* 195 */     if (defaultConfigFile.exists())
/* 196 */       loadSettingsFile(defaultConfigFile); 
/*     */   }
/*     */   
/*     */   public void loadSettings() throws IOException {
/* 200 */     loadDefaultSettings();
/* 201 */     File mainConfigFile = WorldMap.optionsFile;
/* 202 */     Path configFolderPath = mainConfigFile.toPath().getParent();
/* 203 */     if (!Files.exists(configFolderPath, new java.nio.file.LinkOption[0]))
/* 204 */       Files.createDirectories(configFolderPath, (FileAttribute<?>[])new FileAttribute[0]); 
/* 205 */     if (mainConfigFile.exists())
/* 206 */       loadSettingsFile(mainConfigFile); 
/* 207 */     saveSettings();
/*     */   }
/*     */   
/*     */   private void loadSettingsFile(File file) throws IOException {
/* 211 */     BufferedReader reader = null;
/*     */     try {
/* 213 */       reader = new BufferedReader(new FileReader(file));
/*     */       String s;
/* 215 */       while ((s = reader.readLine()) != null) {
/* 216 */         String[] args = s.split(":");
/*     */         try {
/* 218 */           if (args[0].equalsIgnoreCase("ignoreUpdate")) {
/* 219 */             ignoreUpdate = Integer.parseInt(args[1]); continue;
/* 220 */           }  if (args[0].equalsIgnoreCase("updateNotification")) {
/* 221 */             updateNotification = args[1].equals("true"); continue;
/* 222 */           }  if (args[0].equalsIgnoreCase("allowInternetAccess")) {
/* 223 */             this.allowInternetAccess = args[1].equals("true"); continue;
/* 224 */           }  if (args[0].equalsIgnoreCase("differentiateByServerAddress")) {
/* 225 */             this.differentiateByServerAddress = args[1].equals("true"); continue;
/* 226 */           }  if (args[0].equalsIgnoreCase("caveMapsAllowed")) {
/* 227 */             this.caveMapsAllowed = args[1].equals("true"); continue;
/* 228 */           }  if (args[0].equalsIgnoreCase("debug")) {
/* 229 */             this.debug = args[1].equals("true"); continue;
/* 230 */           }  if (args[0].equalsIgnoreCase("lighting")) {
/* 231 */             this.lighting = args[1].equals("true"); continue;
/* 232 */           }  if (args[0].equalsIgnoreCase("colours")) {
/* 233 */             this.colours = Integer.parseInt(args[1]); continue;
/* 234 */           }  if (args[0].equalsIgnoreCase("loadChunks")) {
/* 235 */             this.loadChunks = args[1].equals("true"); continue;
/* 236 */           }  if (args[0].equalsIgnoreCase("updateChunks")) {
/* 237 */             this.updateChunks = args[1].equals("true"); continue;
/* 238 */           }  if (args[0].equalsIgnoreCase("terrainSlopes")) {
/* 239 */             this.terrainSlopes = args[1].equals("true") ? 2 : (args[1].equals("false") ? 0 : Integer.parseInt(args[1])); continue;
/* 240 */           }  if (args[0].equalsIgnoreCase("terrainDepth")) {
/* 241 */             this.terrainDepth = args[1].equals("true"); continue;
/* 242 */           }  if (args[0].equalsIgnoreCase("footsteps")) {
/* 243 */             this.footsteps = args[1].equals("true");
/*     */             continue;
/*     */           } 
/* 246 */           if (args[0].equalsIgnoreCase("flowers")) {
/* 247 */             this.flowers = args[1].equals("true"); continue;
/* 248 */           }  if (args[0].equalsIgnoreCase("coordinates")) {
/* 249 */             this.coordinates = args[1].equals("true"); continue;
/* 250 */           }  if (args[0].equalsIgnoreCase("hoveredBiome")) {
/* 251 */             this.hoveredBiome = args[1].equals("true"); continue;
/* 252 */           }  if (args[0].equalsIgnoreCase("biomeColorsVanillaMode")) {
/* 253 */             this.biomeColorsVanillaMode = args[1].equals("true"); continue;
/* 254 */           }  if (args[0].equalsIgnoreCase("waypoints")) {
/* 255 */             this.waypoints = args[1].equals("true"); continue;
/* 256 */           }  if (args[0].equalsIgnoreCase("renderArrow")) {
/* 257 */             this.renderArrow = args[1].equals("true"); continue;
/* 258 */           }  if (args[0].equalsIgnoreCase("displayZoom")) {
/* 259 */             this.displayZoom = args[1].equals("true"); continue;
/* 260 */           }  if (args[0].equalsIgnoreCase("worldmapWaypointsScale")) {
/* 261 */             this.worldmapWaypointsScale = Float.parseFloat(args[1]); continue;
/* 262 */           }  if (args[0].equalsIgnoreCase("openMapAnimation")) {
/* 263 */             this.openMapAnimation = args[1].equals("true"); continue;
/* 264 */           }  if (args[0].equalsIgnoreCase("reloadVersion")) {
/* 265 */             this.reloadVersion = Integer.parseInt(args[1]); continue;
/* 266 */           }  if (args[0].equalsIgnoreCase("reloadEverything")) {
/* 267 */             this.reloadEverything = args[1].equals("true"); continue;
/* 268 */           }  if (args[0].equalsIgnoreCase("zoomButtons")) {
/* 269 */             this.zoomButtons = args[1].equals("true"); continue;
/* 270 */           }  if (args[0].equalsIgnoreCase("waypointBackgrounds")) {
/* 271 */             this.waypointBackgrounds = args[1].equals("true"); continue;
/* 272 */           }  if (args[0].equalsIgnoreCase("mapItemId")) {
/* 273 */             mapItemId = args[1] + ":" + args[1]; continue;
/* 274 */           }  if (args[0].equalsIgnoreCase("detectAmbiguousY")) {
/* 275 */             this.detectAmbiguousY = args[1].equals("true"); continue;
/* 276 */           }  if (args[0].equalsIgnoreCase("showDisabledWaypoints")) {
/* 277 */             this.showDisabledWaypoints = args[1].equals("true"); continue;
/* 278 */           }  if (args[0].equalsIgnoreCase("closeWaypointsWhenHopping")) {
/* 279 */             this.closeWaypointsWhenHopping = args[1].equals("true"); continue;
/* 280 */           }  if (args[0].equalsIgnoreCase("adjustHeightForCarpetLikeBlocks")) {
/* 281 */             this.adjustHeightForCarpetLikeBlocks = args[1].equals("true"); continue;
/* 282 */           }  if (args[0].equalsIgnoreCase("onlyCurrentMapWaypoints")) {
/* 283 */             this.onlyCurrentMapWaypoints = args[1].equals("true"); continue;
/* 284 */           }  if (args[0].equalsIgnoreCase("minZoomForLocalWaypoints")) {
/* 285 */             this.minZoomForLocalWaypoints = Double.parseDouble(args[1]); continue;
/* 286 */           }  if (args[0].equalsIgnoreCase("arrowColour")) {
/* 287 */             this.arrowColour = Integer.parseInt(args[1]); continue;
/* 288 */           }  if (args[0].equalsIgnoreCase("minimapRadar")) {
/* 289 */             this.minimapRadar = args[1].equals("true"); continue;
/* 290 */           }  if (args[0].equalsIgnoreCase("renderWaypoints")) {
/* 291 */             this.renderWaypoints = args[1].equals("true"); continue;
/* 292 */           }  if (args[0].equalsIgnoreCase("partialYTeleportation")) {
/* 293 */             this.partialYTeleportation = args[1].equals("true"); continue;
/* 294 */           }  if (args[0].equalsIgnoreCase("displayStainedGlass")) {
/* 295 */             this.displayStainedGlass = args[1].equals("true"); continue;
/* 296 */           }  if (args[0].equalsIgnoreCase("caveModeDepth")) {
/* 297 */             this.caveModeDepth = Integer.parseInt(args[1]); continue;
/* 298 */           }  if (args[0].equalsIgnoreCase("caveModeStart")) {
/* 299 */             this.caveModeStart = Integer.parseInt(args[1]); continue;
/* 300 */           }  if (args[0].equalsIgnoreCase("autoCaveMode")) {
/* 301 */             this.autoCaveMode = Integer.parseInt(args[1]); continue;
/* 302 */           }  if (args[0].equalsIgnoreCase("legibleCaveMaps")) {
/* 303 */             this.legibleCaveMaps = args[1].equals("true"); continue;
/* 304 */           }  if (args[0].equalsIgnoreCase("displayCaveModeStart")) {
/* 305 */             this.displayCaveModeStart = args[1].equals("true"); continue;
/* 306 */           }  if (args[0].equalsIgnoreCase("caveModeToggleTimer")) {
/* 307 */             this.caveModeToggleTimer = Integer.parseInt(args[1]); continue;
/* 308 */           }  if (args[0].equalsIgnoreCase("defaultCaveModeType")) {
/* 309 */             this.defaultCaveModeType = Integer.parseInt(args[1]); continue;
/* 310 */           }  if (args[0].equalsIgnoreCase("biomeBlending")) {
/* 311 */             this.biomeBlending = args[1].equals("true"); continue;
/* 312 */           }  if (args[0].equalsIgnoreCase("trackedPlayers") || args[0].equalsIgnoreCase("pacPlayers")) {
/* 313 */             this.trackedPlayers = args[1].equals("true"); continue;
/* 314 */           }  if (args[0].equalsIgnoreCase("multipleImagesExport")) {
/* 315 */             this.multipleImagesExport = args[1].equals("true"); continue;
/* 316 */           }  if (args[0].equalsIgnoreCase("nightExport")) {
/* 317 */             this.nightExport = args[1].equals("true"); continue;
/* 318 */           }  if (args[0].equalsIgnoreCase("highlightsExport")) {
/* 319 */             this.highlightsExport = args[1].equals("true"); continue;
/* 320 */           }  if (args[0].equalsIgnoreCase("exportScaleDownSquare")) {
/* 321 */             this.exportScaleDownSquare = Integer.parseInt(args[1]); continue;
/* 322 */           }  if (args[0].equalsIgnoreCase("mapWritingDistance")) {
/* 323 */             this.mapWritingDistance = Integer.parseInt(args[1]);
/*     */             
/*     */             continue;
/*     */           } 
/* 327 */           if (args[0].equalsIgnoreCase("displayClaims")) {
/* 328 */             this.displayClaims = args[1].equals("true"); continue;
/* 329 */           }  if (args[0].equalsIgnoreCase("claimsOpacity")) {
/* 330 */             this.claimsOpacity = this.claimsBorderOpacity = Math.min(100, Math.max(0, Integer.parseInt(args[1])));
/* 331 */             this.claimsFillOpacity = this.claimsBorderOpacity * 58 / 100; continue;
/*     */           } 
/* 333 */           if (args[0].equalsIgnoreCase("claimsBorderOpacity")) {
/* 334 */             this.claimsBorderOpacity = Integer.parseInt(args[1]); continue;
/* 335 */           }  if (args[0].equalsIgnoreCase("claimsFillOpacity")) {
/* 336 */             this.claimsFillOpacity = Integer.parseInt(args[1]); continue;
/*     */           } 
/* 338 */           if (args[0].equalsIgnoreCase("globalVersion"))
/* 339 */             WorldMap.globalVersion = Integer.parseInt(args[1]); 
/* 340 */         } catch (Exception e) {
/* 341 */           WorldMap.LOGGER.info("Skipping setting:" + args[0]);
/*     */         } 
/*     */       } 
/*     */     } finally {
/* 345 */       if (reader != null) {
/* 346 */         reader.close();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getOptionValueName(ModOptions par1EnumOptions) {
/* 352 */     if (par1EnumOptions.isDisabledBecauseNotIngame() || par1EnumOptions.isDisabledBecauseMinimap() || par1EnumOptions.isDisabledBecausePac())
/* 353 */       return getTranslation(false); 
/* 354 */     if (par1EnumOptions.enumBoolean)
/* 355 */       return getTranslation(getClientBooleanValue(par1EnumOptions)); 
/* 356 */     if (par1EnumOptions == ModOptions.COLOURS)
/* 357 */       return class_1074.method_4662(this.colourNames[this.colours], new Object[0]); 
/* 358 */     if (par1EnumOptions == ModOptions.SLOPES)
/* 359 */       return class_1074.method_4662(this.slopeNames[this.terrainSlopes], new Object[0]); 
/* 360 */     if (par1EnumOptions == ModOptions.ARROW_COLOUR) {
/* 361 */       String colourName = "gui.xaero_wm_team_color";
/* 362 */       if (this.arrowColour >= 0) {
/* 363 */         colourName = this.arrowColourNames[this.arrowColour];
/* 364 */       } else if (this.arrowColour == -2) {
/* 365 */         colourName = "gui.xaero_wm_color_minimap";
/* 366 */       }  return class_1074.method_4662(colourName, new Object[0]);
/* 367 */     }  if (par1EnumOptions == ModOptions.AUTO_CAVE_MODE) {
/* 368 */       if (this.autoCaveMode == 0)
/* 369 */         return class_1074.method_4662("gui.xaero_off", new Object[0]); 
/* 370 */       if (this.autoCaveMode < 0) {
/* 371 */         return class_1074.method_4662("gui.xaero_auto_cave_mode_minimap", new Object[0]);
/*     */       }
/* 373 */       int roofSideSize = this.autoCaveMode * 2 - 1;
/* 374 */       return "" + roofSideSize + "x" + roofSideSize + " " + roofSideSize;
/*     */     } 
/* 376 */     if (par1EnumOptions == ModOptions.DEFAULT_CAVE_MODE_TYPE)
/* 377 */       return class_1074.method_4662((this.defaultCaveModeType == 0) ? "gui.xaero_off" : ((this.defaultCaveModeType == 1) ? "gui.xaero_wm_cave_mode_type_layered" : "gui.xaero_wm_cave_mode_type_full"), new Object[0]); 
/* 378 */     if (par1EnumOptions == ModOptions.PAC_CLAIMS_FILL_OPACITY)
/* 379 */       return "" + this.claimsFillOpacity; 
/* 380 */     if (par1EnumOptions == ModOptions.PAC_CLAIMS_BORDER_OPACITY) {
/* 381 */       return "" + this.claimsBorderOpacity;
/*     */     }
/* 383 */     return "";
/*     */   }
/*     */   
/*     */   public boolean getClientBooleanValue(ModOptions o) {
/* 387 */     if (o.isDisabledBecauseNotIngame() || o.isDisabledBecauseMinimap() || o.isDisabledBecausePac())
/* 388 */       return false; 
/* 389 */     if (o == ModOptions.IGNORE_HEIGHTMAPS)
/* 390 */       return WorldMapSession.getCurrentSession().getMapProcessor().getMapWorld().isIgnoreHeightmaps(); 
/* 391 */     if (o == ModOptions.DEBUG)
/* 392 */       return this.debug; 
/* 393 */     if (o == ModOptions.LIGHTING)
/* 394 */       return this.lighting; 
/* 395 */     if (o == ModOptions.LOAD)
/* 396 */       return this.loadChunks; 
/* 397 */     if (o == ModOptions.UPDATE)
/* 398 */       return this.updateChunks; 
/* 399 */     if (o == ModOptions.DEPTH)
/* 400 */       return this.terrainDepth; 
/* 401 */     if (o == ModOptions.STEPS)
/* 402 */       return this.footsteps; 
/* 403 */     if (o == ModOptions.FLOWERS)
/* 404 */       return this.flowers; 
/* 405 */     if (o == ModOptions.COORDINATES)
/* 406 */       return this.coordinates; 
/* 407 */     if (o == ModOptions.HOVERED_BIOME)
/* 408 */       return this.hoveredBiome; 
/* 409 */     if (o == ModOptions.BIOMES)
/* 410 */       return this.biomeColorsVanillaMode; 
/* 411 */     if (o == ModOptions.WAYPOINTS)
/* 412 */       return this.waypoints; 
/* 413 */     if (o == ModOptions.ARROW)
/* 414 */       return this.renderArrow; 
/* 415 */     if (o == ModOptions.DISPLAY_ZOOM)
/* 416 */       return this.displayZoom; 
/* 417 */     if (o == ModOptions.OPEN_ANIMATION)
/* 418 */       return this.openMapAnimation; 
/* 419 */     if (o == ModOptions.RELOAD)
/* 420 */       return this.reloadEverything; 
/* 421 */     if (o == ModOptions.ZOOM_BUTTONS)
/* 422 */       return this.zoomButtons; 
/* 423 */     if (o == ModOptions.WAYPOINT_BACKGROUNDS)
/* 424 */       return this.waypointBackgrounds; 
/* 425 */     if (o == ModOptions.DETECT_AMBIGUOUS_Y)
/* 426 */       return this.detectAmbiguousY; 
/* 427 */     if (o == ModOptions.PAUSE_REQUESTS)
/* 428 */       return this.pauseRequests; 
/* 429 */     if (o == ModOptions.EXTRA_DEBUG)
/* 430 */       return this.extraDebug; 
/* 431 */     if (o == ModOptions.UPDATE_NOTIFICATION)
/* 432 */       return updateNotification; 
/* 433 */     if (o == ModOptions.ADJUST_HEIGHT_FOR_SHORT_BLOCKS)
/* 434 */       return this.adjustHeightForCarpetLikeBlocks; 
/* 435 */     if (o == ModOptions.PARTIAL_Y_TELEPORTATION)
/* 436 */       return this.partialYTeleportation; 
/* 437 */     if (o == ModOptions.DISPLAY_STAINED_GLASS)
/* 438 */       return this.displayStainedGlass; 
/* 439 */     if (o == ModOptions.MAP_TELEPORT_ALLOWED)
/* 440 */       return WorldMapSession.getCurrentSession().getMapProcessor().getMapWorld().isTeleportAllowed(); 
/* 441 */     if (o == ModOptions.LEGIBLE_CAVE_MAPS)
/* 442 */       return this.legibleCaveMaps; 
/* 443 */     if (o == ModOptions.DISPLAY_CAVE_MODE_START)
/* 444 */       return this.displayCaveModeStart; 
/* 445 */     if (o == ModOptions.BIOME_BLENDING)
/* 446 */       return this.biomeBlending; 
/* 447 */     if (o == ModOptions.FULL_EXPORT)
/* 448 */       return ((class_310.method_1551()).field_1755 instanceof ExportScreen) ? ((ExportScreen)(class_310.method_1551()).field_1755).fullExport : false; 
/* 449 */     if (o == ModOptions.MULTIPLE_IMAGES_EXPORT)
/* 450 */       return this.multipleImagesExport; 
/* 451 */     if (o == ModOptions.NIGHT_EXPORT)
/* 452 */       return this.nightExport; 
/* 453 */     if (o == ModOptions.EXPORT_HIGHLIGHTS)
/* 454 */       return this.highlightsExport; 
/* 455 */     if (o == ModOptions.FULL_RELOAD || o == ModOptions.FULL_RESAVE) {
/* 456 */       MapDimension mapDimension = WorldMapSession.getCurrentSession().getMapProcessor().getMapWorld().getCurrentDimension();
/* 457 */       MapFullReloader reloader = (mapDimension == null) ? null : mapDimension.getFullReloader();
/* 458 */       return (reloader != null && (o == ModOptions.FULL_RELOAD || reloader.isResave()));
/*     */     } 
/* 460 */     if (o == ModOptions.PAC_CLAIMS)
/* 461 */       return this.displayClaims; 
/* 462 */     return false;
/*     */   }
/*     */   
/*     */   private static String getTranslation(boolean o) {
/* 466 */     return class_1074.method_4662("gui.xaero_" + (o ? "on" : "off"), new Object[0]);
/*     */   }
/*     */   
/*     */   public void setOptionValue(ModOptions par1EnumOptions, Object value) {
/* 470 */     if (par1EnumOptions.isDisabledBecauseNotIngame() || par1EnumOptions.isDisabledBecauseMinimap() || par1EnumOptions.isDisabledBecausePac())
/*     */       return; 
/* 472 */     if (par1EnumOptions == ModOptions.DEBUG) {
/* 473 */       this.debug = ((Boolean)value).booleanValue();
/*     */     }
/* 475 */     if (par1EnumOptions == ModOptions.COLOURS) {
/* 476 */       this.colours = ((Integer)value).intValue();
/*     */     }
/* 478 */     if (par1EnumOptions == ModOptions.LIGHTING) {
/* 479 */       this.lighting = ((Boolean)value).booleanValue();
/*     */     }
/*     */     
/* 482 */     if (par1EnumOptions == ModOptions.LOAD) {
/* 483 */       this.loadChunks = ((Boolean)value).booleanValue();
/*     */     }
/* 485 */     if (par1EnumOptions == ModOptions.UPDATE) {
/* 486 */       this.updateChunks = ((Boolean)value).booleanValue();
/*     */     }
/* 488 */     if (par1EnumOptions == ModOptions.DEPTH) {
/* 489 */       this.terrainDepth = ((Boolean)value).booleanValue();
/*     */     }
/* 491 */     if (par1EnumOptions == ModOptions.SLOPES) {
/* 492 */       this.terrainSlopes = ((Integer)value).intValue();
/*     */     }
/* 494 */     if (par1EnumOptions == ModOptions.STEPS) {
/* 495 */       this.footsteps = ((Boolean)value).booleanValue();
/*     */     }
/* 497 */     if (par1EnumOptions == ModOptions.FLOWERS) {
/* 498 */       this.flowers = ((Boolean)value).booleanValue();
/* 499 */       class_310 mc = class_310.method_1551();
/* 500 */       if (mc.field_1687 != null && mc.field_1724 != null) {
/* 501 */         WorldMapSession session = WorldMapSession.getCurrentSession();
/* 502 */         if (session != null)
/* 503 */           session.getMapProcessor().getMapWriter().setDirtyInWriteDistance((class_1657)mc.field_1724, (class_1937)mc.field_1687); 
/*     */       } 
/*     */     } 
/* 506 */     if (par1EnumOptions == ModOptions.COORDINATES) {
/* 507 */       this.coordinates = ((Boolean)value).booleanValue();
/*     */     }
/* 509 */     if (par1EnumOptions == ModOptions.HOVERED_BIOME) {
/* 510 */       this.hoveredBiome = !this.hoveredBiome;
/*     */     }
/* 512 */     if (par1EnumOptions == ModOptions.BIOMES) {
/* 513 */       this.biomeColorsVanillaMode = ((Boolean)value).booleanValue();
/*     */     }
/* 515 */     if (par1EnumOptions == ModOptions.WAYPOINTS) {
/* 516 */       this.waypoints = ((Boolean)value).booleanValue();
/*     */     }
/* 518 */     if (par1EnumOptions == ModOptions.ARROW) {
/* 519 */       this.renderArrow = ((Boolean)value).booleanValue();
/*     */     }
/* 521 */     if (par1EnumOptions == ModOptions.DISPLAY_ZOOM) {
/* 522 */       this.displayZoom = ((Boolean)value).booleanValue();
/*     */     }
/* 524 */     if (par1EnumOptions == ModOptions.IGNORE_HEIGHTMAPS) {
/* 525 */       WorldMapSession worldmapSession = WorldMapSession.getCurrentSession();
/* 526 */       MapWorld mapWorld = worldmapSession.getMapProcessor().getMapWorld();
/* 527 */       mapWorld.setIgnoreHeightmaps(((Boolean)value).booleanValue());
/* 528 */       mapWorld.saveConfig();
/*     */     } 
/* 530 */     if (par1EnumOptions == ModOptions.OPEN_ANIMATION) {
/* 531 */       this.openMapAnimation = ((Boolean)value).booleanValue();
/*     */     }
/* 533 */     if (par1EnumOptions == ModOptions.RELOAD) {
/* 534 */       this.reloadEverything = ((Boolean)value).booleanValue();
/* 535 */       if (this.reloadEverything)
/* 536 */         this.reloadVersion++; 
/*     */     } 
/* 538 */     if (par1EnumOptions == ModOptions.ZOOM_BUTTONS) {
/* 539 */       this.zoomButtons = ((Boolean)value).booleanValue();
/*     */     }
/* 541 */     if (par1EnumOptions == ModOptions.WAYPOINT_BACKGROUNDS) {
/* 542 */       this.waypointBackgrounds = ((Boolean)value).booleanValue();
/*     */     }
/* 544 */     if (par1EnumOptions == ModOptions.DETECT_AMBIGUOUS_Y) {
/* 545 */       this.detectAmbiguousY = ((Boolean)value).booleanValue();
/*     */     }
/* 547 */     if (par1EnumOptions == ModOptions.PAUSE_REQUESTS) {
/* 548 */       this.pauseRequests = ((Boolean)value).booleanValue();
/*     */     }
/* 550 */     if (par1EnumOptions == ModOptions.EXTRA_DEBUG) {
/* 551 */       this.extraDebug = ((Boolean)value).booleanValue();
/*     */     }
/* 553 */     if (par1EnumOptions == ModOptions.UPDATE_NOTIFICATION) {
/* 554 */       updateNotification = !updateNotification;
/*     */     }
/* 556 */     if (par1EnumOptions == ModOptions.ADJUST_HEIGHT_FOR_SHORT_BLOCKS)
/* 557 */     { this.adjustHeightForCarpetLikeBlocks = !this.adjustHeightForCarpetLikeBlocks; }
/* 558 */     else if (par1EnumOptions == ModOptions.ARROW_COLOUR)
/* 559 */     { this.arrowColour = -2 + ((Integer)value).intValue(); }
/* 560 */     else { if (par1EnumOptions == ModOptions.MAP_TELEPORT_ALLOWED) {
/* 561 */         WorldMapSession worldmapSession = WorldMapSession.getCurrentSession();
/* 562 */         MapWorld mapWorld = worldmapSession.getMapProcessor().getMapWorld();
/* 563 */         mapWorld.setTeleportAllowed(!mapWorld.isTeleportAllowed());
/* 564 */         mapWorld.saveConfig(); return;
/*     */       } 
/* 566 */       if (par1EnumOptions == ModOptions.PARTIAL_Y_TELEPORTATION)
/* 567 */       { this.partialYTeleportation = ((Boolean)value).booleanValue(); }
/* 568 */       else if (par1EnumOptions == ModOptions.DISPLAY_STAINED_GLASS)
/* 569 */       { this.displayStainedGlass = ((Boolean)value).booleanValue(); }
/* 570 */       else if (par1EnumOptions == ModOptions.LEGIBLE_CAVE_MAPS)
/* 571 */       { this.legibleCaveMaps = ((Boolean)value).booleanValue(); }
/* 572 */       else if (par1EnumOptions == ModOptions.AUTO_CAVE_MODE)
/* 573 */       { this.autoCaveMode = -1 + ((Integer)value).intValue(); }
/* 574 */       else if (par1EnumOptions == ModOptions.DISPLAY_CAVE_MODE_START)
/* 575 */       { this.displayCaveModeStart = ((Boolean)value).booleanValue(); }
/* 576 */       else if (par1EnumOptions == ModOptions.DEFAULT_CAVE_MODE_TYPE)
/* 577 */       { this.defaultCaveModeType = ((Integer)value).intValue(); }
/* 578 */       else if (par1EnumOptions == ModOptions.BIOME_BLENDING)
/* 579 */       { this.biomeBlending = ((Boolean)value).booleanValue(); }
/* 580 */       else if (par1EnumOptions == ModOptions.FULL_EXPORT)
/* 581 */       { if ((class_310.method_1551()).field_1755 instanceof ExportScreen)
/* 582 */           ((ExportScreen)(class_310.method_1551()).field_1755).fullExport = ((Boolean)value).booleanValue();  }
/* 583 */       else if (par1EnumOptions == ModOptions.MULTIPLE_IMAGES_EXPORT)
/* 584 */       { this.multipleImagesExport = ((Boolean)value).booleanValue(); }
/* 585 */       else if (par1EnumOptions == ModOptions.NIGHT_EXPORT)
/* 586 */       { this.nightExport = ((Boolean)value).booleanValue(); }
/* 587 */       else if (par1EnumOptions == ModOptions.EXPORT_HIGHLIGHTS)
/* 588 */       { this.highlightsExport = ((Boolean)value).booleanValue(); }
/* 589 */       else { if (par1EnumOptions == ModOptions.FULL_RELOAD || par1EnumOptions == ModOptions.FULL_RESAVE) {
/* 590 */           WorldMapSession worldmapSession = WorldMapSession.getCurrentSession();
/* 591 */           MapProcessor mapProcessor = worldmapSession.getMapProcessor();
/* 592 */           MapDimension mapDimension = mapProcessor.getMapWorld().getCurrentDimension();
/* 593 */           if (mapDimension == null)
/*     */             return; 
/* 595 */           if (((Boolean)value).booleanValue() && (mapDimension.getFullReloader() == null || (!mapDimension.getFullReloader().isResave() && par1EnumOptions == ModOptions.FULL_RESAVE))) {
/* 596 */             mapDimension.startFullMapReload(mapProcessor.getCurrentCaveLayer(), (par1EnumOptions == ModOptions.FULL_RESAVE), mapProcessor);
/* 597 */           } else if (!((Boolean)value).booleanValue()) {
/* 598 */             mapDimension.clearFullMapReload();
/*     */           } 
/*     */           
/*     */           return;
/*     */         } 
/*     */         
/* 604 */         if (par1EnumOptions == ModOptions.PAC_CLAIMS) {
/* 605 */           this.displayClaims = ((Boolean)value).booleanValue();
/* 606 */           WorldMapSession worldmapSession = WorldMapSession.getCurrentSession();
/* 607 */           if (worldmapSession != null)
/* 608 */             synchronized ((worldmapSession.getMapProcessor()).uiSync) {
/* 609 */               worldmapSession.getMapProcessor().getMapWorld().clearAllCachedHighlightHashes();
/*     */             }  
/*     */         }  }
/*     */        }
/*     */     
/* 614 */     updateRegionCacheHashCode();
/*     */     try {
/* 616 */       saveSettings();
/* 617 */     } catch (IOException e) {
/* 618 */       WorldMap.LOGGER.error("suppressed exception", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOptionDoubleValue(ModOptions options, double f) {
/* 625 */     if (options.isDisabledBecauseNotIngame() || options.isDisabledBecauseMinimap() || options.isDisabledBecausePac())
/*     */       return; 
/* 627 */     if (options == ModOptions.WAYPOINT_SCALE) {
/* 628 */       this.worldmapWaypointsScale = (float)f;
/* 629 */     } else if (options == ModOptions.MIN_ZOOM_LOCAL_WAYPOINTS) {
/* 630 */       this.minZoomForLocalWaypoints = f;
/* 631 */     } else if (options == ModOptions.CAVE_MODE_DEPTH) {
/* 632 */       this.caveModeDepth = (int)f;
/* 633 */     } else if (options == ModOptions.CAVE_MODE_START) {
/* 634 */       this.caveModeStart = (f == ModOptions.CAVE_MODE_START.getValueMin()) ? Integer.MAX_VALUE : (int)f;
/* 635 */       if ((class_310.method_1551()).field_1755 instanceof GuiMap)
/* 636 */         ((GuiMap)(class_310.method_1551()).field_1755).onCaveModeStartSet(); 
/* 637 */     } else if (options == ModOptions.CAVE_MODE_TOGGLE_TIMER) {
/* 638 */       this.caveModeToggleTimer = (int)f;
/* 639 */     } else if (options == ModOptions.EXPORT_SCALE_DOWN_SQUARE) {
/* 640 */       this.exportScaleDownSquare = (int)f;
/* 641 */     } else if (options == ModOptions.MAP_WRITING_DISTANCE) {
/* 642 */       this.mapWritingDistance = (int)f;
/* 643 */     } else if (options == ModOptions.PAC_CLAIMS_BORDER_OPACITY) {
/* 644 */       this.claimsOpacity = this.claimsBorderOpacity = (int)f;
/* 645 */       if (this.displayClaims) {
/* 646 */         WorldMapSession worldmapSession = WorldMapSession.getCurrentSession();
/* 647 */         if (worldmapSession != null) {
/* 648 */           synchronized ((worldmapSession.getMapProcessor()).uiSync) {
/* 649 */             if (worldmapSession.getMapProcessor().getMapWorld().getCurrentDimensionId() != null) {
/* 650 */               worldmapSession.getMapProcessor().getMapWorld().getCurrentDimension().getHighlightHandler().clearCachedHashes();
/*     */             }
/*     */           } 
/*     */         }
/*     */       } 
/* 655 */     } else if (options == ModOptions.PAC_CLAIMS_FILL_OPACITY) {
/* 656 */       this.claimsFillOpacity = (int)f;
/* 657 */       if (this.displayClaims) {
/* 658 */         WorldMapSession worldmapSession = WorldMapSession.getCurrentSession();
/* 659 */         if (worldmapSession != null)
/* 660 */           synchronized ((worldmapSession.getMapProcessor()).uiSync) {
/* 661 */             if (worldmapSession.getMapProcessor().getMapWorld().getCurrentDimensionId() != null) {
/* 662 */               worldmapSession.getMapProcessor().getMapWorld().getCurrentDimension().getHighlightHandler().clearCachedHashes();
/*     */             }
/*     */           }  
/*     */       } 
/*     */     } 
/*     */     try {
/* 668 */       saveSettings();
/* 669 */     } catch (IOException e) {
/* 670 */       WorldMap.LOGGER.error("suppressed exception", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public double getOptionDoubleValue(ModOptions options) {
/* 675 */     if (options.isDisabledBecauseNotIngame() || options.isDisabledBecauseMinimap() || options.isDisabledBecausePac())
/* 676 */       return 0.0D; 
/* 677 */     if (options == ModOptions.WAYPOINT_SCALE)
/* 678 */       return this.worldmapWaypointsScale; 
/* 679 */     if (options == ModOptions.MIN_ZOOM_LOCAL_WAYPOINTS)
/* 680 */       return this.minZoomForLocalWaypoints; 
/* 681 */     if (options == ModOptions.CAVE_MODE_DEPTH)
/* 682 */       return this.caveModeDepth; 
/* 683 */     if (options == ModOptions.CAVE_MODE_START)
/* 684 */       return (this.caveModeStart == Integer.MAX_VALUE) ? ModOptions.CAVE_MODE_START.getValueMin() : this.caveModeStart; 
/* 685 */     if (options == ModOptions.CAVE_MODE_TOGGLE_TIMER)
/* 686 */       return this.caveModeToggleTimer; 
/* 687 */     if (options == ModOptions.EXPORT_SCALE_DOWN_SQUARE)
/* 688 */       return this.exportScaleDownSquare; 
/* 689 */     if (options == ModOptions.MAP_WRITING_DISTANCE)
/* 690 */       return this.mapWritingDistance; 
/* 691 */     if (options == ModOptions.PAC_CLAIMS_BORDER_OPACITY)
/* 692 */       return this.claimsBorderOpacity; 
/* 693 */     if (options == ModOptions.PAC_CLAIMS_FILL_OPACITY)
/* 694 */       return this.claimsFillOpacity; 
/* 695 */     return 1.0D;
/*     */   }
/*     */   
/*     */   public int getRegionCacheHashCode() {
/* 699 */     return this.regionCacheHashCode;
/*     */   }
/*     */   
/*     */   public void updateRegionCacheHashCode() {
/* 703 */     int currentRegionCacheHashCode = this.regionCacheHashCode;
/* 704 */     if (!class_310.method_1551().method_18854())
/* 705 */       throw new RuntimeException("Wrong thread!"); 
/* 706 */     HashCodeBuilder hcb = new HashCodeBuilder();
/* 707 */     hcb.append(this.colours).append(this.terrainDepth).append(this.terrainSlopes).append(false)
/* 708 */       .append((this.colours == 1 && this.biomeColorsVanillaMode)).append(getClientBooleanValue(ModOptions.IGNORE_HEIGHTMAPS))
/* 709 */       .append(this.adjustHeightForCarpetLikeBlocks).append(this.displayStainedGlass).append(this.legibleCaveMaps).append(this.biomeBlending);
/* 710 */     Collection<class_3288> enabledResourcePacks = class_310.method_1551().method_1520().method_14444();
/* 711 */     for (class_3288 resourcePack : enabledResourcePacks)
/* 712 */       hcb.append(resourcePack.method_14463()); 
/* 713 */     this.regionCacheHashCode = hcb.toHashCode();
/* 714 */     if (currentRegionCacheHashCode != this.regionCacheHashCode)
/* 715 */       WorldMap.LOGGER.info("New world map region cache hash code: " + this.regionCacheHashCode); 
/*     */   }
/*     */   
/*     */   public Object getOptionValue(ModOptions par1EnumOptions) {
/* 719 */     if (par1EnumOptions.enumBoolean)
/* 720 */       return Boolean.valueOf(getClientBooleanValue(par1EnumOptions)); 
/* 721 */     if (par1EnumOptions.isIngameOnly() && !canEditIngameSettings())
/* 722 */       return Integer.valueOf(0); 
/* 723 */     if (par1EnumOptions == ModOptions.COLOURS)
/* 724 */       return Integer.valueOf(this.colours); 
/* 725 */     if (par1EnumOptions == ModOptions.SLOPES)
/* 726 */       return Integer.valueOf(this.terrainSlopes); 
/* 727 */     if (par1EnumOptions == ModOptions.ARROW_COLOUR)
/* 728 */       return Integer.valueOf(2 + this.arrowColour); 
/* 729 */     if (par1EnumOptions == ModOptions.AUTO_CAVE_MODE)
/* 730 */       return Integer.valueOf(1 + this.autoCaveMode); 
/* 731 */     if (par1EnumOptions == ModOptions.DEFAULT_CAVE_MODE_TYPE)
/* 732 */       return Integer.valueOf(this.defaultCaveModeType); 
/* 733 */     return Boolean.valueOf(false);
/*     */   }
/*     */   
/*     */   public String getSliderOptionText(ModOptions par1EnumOptions) {
/* 737 */     String s = par1EnumOptions.getEnumString() + ": ";
/* 738 */     if (par1EnumOptions == ModOptions.CAVE_MODE_DEPTH) {
/* 739 */       s = s + s;
/* 740 */     } else if (par1EnumOptions == ModOptions.CAVE_MODE_TOGGLE_TIMER) {
/* 741 */       s = s + s + " s";
/* 742 */     } else if (par1EnumOptions == ModOptions.EXPORT_SCALE_DOWN_SQUARE) {
/* 743 */       s = s + s;
/* 744 */     } else if (par1EnumOptions == ModOptions.MAP_WRITING_DISTANCE) {
/* 745 */       s = s + s;
/* 746 */     } else if (par1EnumOptions == ModOptions.CAVE_MODE_START) {
/* 747 */       s = par1EnumOptions.getEnumString();
/*     */     } else {
/* 749 */       return getEnumFloatSliderText(s, "%.2f", par1EnumOptions);
/* 750 */     }  return s;
/*     */   }
/*     */   
/*     */   protected String getEnumFloatSliderText(String s, String f, ModOptions par1EnumOptions) {
/* 754 */     String f1 = String.format(f, new Object[] { Double.valueOf(getOptionDoubleValue(par1EnumOptions)) });
/*     */     
/* 756 */     return s + s;
/*     */   }
/*     */   
/*     */   public static boolean canEditIngameSettings() {
/* 760 */     WorldMapSession worldmapSession = WorldMapSession.getCurrentSession();
/* 761 */     return (worldmapSession != null && worldmapSession.getMapProcessor().getMapWorld() != null);
/*     */   }
/*     */   
/*     */   public void findMapItem() {
/* 765 */     mapItem = (mapItemId != null) ? (class_1792)class_7923.field_41178.method_63535(class_2960.method_60654(mapItemId)) : null;
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\settings\ModSettings.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */