/*     */ package xaero.map.world;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.attribute.FileAttribute;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.class_1132;
/*     */ import net.minecraft.class_1937;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_2378;
/*     */ import net.minecraft.class_2874;
/*     */ import net.minecraft.class_2960;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_3218;
/*     */ import net.minecraft.class_3532;
/*     */ import net.minecraft.class_5294;
/*     */ import net.minecraft.class_5321;
/*     */ import net.minecraft.class_638;
/*     */ import net.minecraft.class_7134;
/*     */ import xaero.map.MapFullReloader;
/*     */ import xaero.map.MapProcessor;
/*     */ import xaero.map.WorldMap;
/*     */ import xaero.map.file.RegionDetection;
/*     */ import xaero.map.highlight.DimensionHighlighterHandler;
/*     */ import xaero.map.highlight.HighlighterRegistry;
/*     */ import xaero.map.region.LayeredRegionManager;
/*     */ import xaero.map.region.LeveledRegion;
/*     */ import xaero.map.region.MapLayer;
/*     */ import xaero.map.region.MapRegion;
/*     */ import xaero.map.util.linked.LinkedChain;
/*     */ 
/*     */ public class MapDimension {
/*     */   private final MapWorld mapWorld;
/*     */   private final class_5321<class_1937> dimId;
/*     */   private final List<String> multiworldIds;
/*     */   private final Hashtable<String, String> multiworldNames;
/*  47 */   private float shadowR = 1.0F; private final Hashtable<String, String> autoMultiworldBindings; private final DimensionHighlighterHandler highlightHandler; private class_2960 dimensionTypeId; private class_2874 dimensionType; private class_5294 dimensionEffects;
/*  48 */   private float shadowG = 1.0F;
/*  49 */   private float shadowB = 1.0F;
/*     */   
/*     */   private String futureAutoMultiworldBinding;
/*     */   
/*     */   private String futureCustomSelectedMultiworld;
/*     */   
/*     */   public boolean futureMultiworldWritable;
/*     */   
/*     */   public boolean futureMultiworldServerBased;
/*     */   
/*     */   private String currentMultiworld;
/*     */   public boolean currentMultiworldWritable;
/*     */   private String confirmedMultiworld;
/*     */   private final LayeredRegionManager mapRegions;
/*     */   private List<MapRegion> regionBackCompList;
/*     */   private final Hashtable<Integer, Hashtable<Integer, RegionDetection>> worldSaveDetectedRegions;
/*     */   private final LinkedChain<RegionDetection> worldSaveDetectedRegionsLinked;
/*     */   private boolean doneRegionDetection;
/*     */   public final ArrayList<LeveledRegion<?>> regionsToCache;
/*     */   private MapFullReloader fullReloader;
/*     */   private int caveModeType;
/*     */   private static final int CAVE_MODE_TYPES = 3;
/*     */   
/*     */   public MapDimension(MapWorld mapWorld, class_5321<class_1937> dimId, HighlighterRegistry highlighterRegistry) {
/*  73 */     this.mapWorld = mapWorld;
/*  74 */     this.dimId = dimId;
/*  75 */     this.multiworldIds = new ArrayList<>();
/*  76 */     this.multiworldNames = new Hashtable<>();
/*  77 */     this.mapRegions = new LayeredRegionManager(this);
/*  78 */     this.autoMultiworldBindings = new Hashtable<>();
/*  79 */     this.regionsToCache = new ArrayList<>();
/*  80 */     this.regionBackCompList = new ArrayList<>();
/*  81 */     this.highlightHandler = new DimensionHighlighterHandler(this, dimId, highlighterRegistry);
/*  82 */     this.worldSaveDetectedRegions = new Hashtable<>();
/*  83 */     this.worldSaveDetectedRegionsLinked = new LinkedChain();
/*  84 */     this.caveModeType = WorldMap.settings.defaultCaveModeType;
/*     */   }
/*     */   
/*     */   public String getCurrentMultiworld() {
/*  88 */     return this.currentMultiworld;
/*     */   }
/*     */   
/*     */   public boolean isUsingWorldSave() {
/*  92 */     return (!this.mapWorld.isMultiplayer() && (this.currentMultiworld == null || this.currentMultiworld.isEmpty()));
/*     */   }
/*     */   
/*     */   public boolean isFutureUsingWorldSaveUnsynced() {
/*  96 */     return (!this.mapWorld.isMultiplayer() && (getFutureMultiworldUnsynced() == null || getFutureMultiworldUnsynced().isEmpty()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getMultiworldIdsCopy() {
/* 104 */     synchronized (this.multiworldIds) {
/* 105 */       return new ArrayList<>(this.multiworldIds);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void updateFutureAutomaticUnsynced(class_310 mc, Object baseObject) {
/* 110 */     if (!this.mapWorld.isMultiplayer()) {
/* 111 */       this.futureAutoMultiworldBinding = "";
/* 112 */       this.futureMultiworldServerBased = false;
/* 113 */     } else if (baseObject != null) {
/* 114 */       if (baseObject instanceof class_2338) {
/* 115 */         class_2338 dimSpawn = (class_2338)baseObject;
/* 116 */         this.futureAutoMultiworldBinding = "mw" + (dimSpawn.method_10263() >> 6) + "," + (dimSpawn.method_10264() >> 6) + "," + (dimSpawn.method_10260() >> 6);
/* 117 */         this.futureMultiworldServerBased = false;
/* 118 */       } else if (baseObject instanceof Integer) {
/* 119 */         int levelId = ((Integer)baseObject).intValue();
/* 120 */         this.futureAutoMultiworldBinding = "mw$" + levelId;
/* 121 */         this.futureMultiworldServerBased = true;
/*     */       } 
/*     */     } else {
/* 124 */       this.futureAutoMultiworldBinding = "unknown";
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getFutureCustomSelectedMultiworld() {
/* 129 */     return this.futureCustomSelectedMultiworld;
/*     */   }
/*     */   
/*     */   public String getFutureMultiworldUnsynced() {
/* 133 */     if (this.futureCustomSelectedMultiworld == null) {
/* 134 */       return getFutureAutoMultiworld();
/*     */     }
/* 136 */     return this.futureCustomSelectedMultiworld;
/*     */   }
/*     */   
/*     */   public void switchToFutureUnsynced() {
/* 140 */     this.currentMultiworld = getFutureMultiworldUnsynced();
/* 141 */     addMultiworldChecked(this.currentMultiworld);
/*     */   }
/*     */   
/*     */   public void switchToFutureMultiworldWritableValueUnsynced() {
/* 145 */     this.currentMultiworldWritable = this.futureMultiworldWritable;
/*     */   }
/*     */   
/*     */   public LayeredRegionManager getLayeredMapRegions() {
/* 149 */     return this.mapRegions;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 153 */     this.regionsToCache.clear();
/* 154 */     this.mapRegions.clear();
/* 155 */     this.regionBackCompList.clear();
/* 156 */     this.worldSaveDetectedRegions.clear();
/* 157 */     this.worldSaveDetectedRegionsLinked.reset();
/* 158 */     this.doneRegionDetection = false;
/* 159 */     clearFullMapReload();
/*     */   }
/*     */   
/*     */   public void preDetection() {
/* 163 */     this.doneRegionDetection = true;
/* 164 */     this.mapRegions.preDetection();
/*     */   }
/*     */   
/*     */   public Path getMainFolderPath() {
/* 168 */     return this.mapWorld.getMapProcessor().getMapSaveLoad().getMainFolder(this.mapWorld.getMainId(), this.mapWorld.getMapProcessor().getDimensionName(this.dimId));
/*     */   }
/*     */   
/*     */   public Path getOldFolderPath() {
/* 172 */     return this.mapWorld.getMapProcessor().getMapSaveLoad().getOldFolder(this.mapWorld.getOldUnfixedMainId(), this.mapWorld.getMapProcessor().getDimensionName(this.dimId));
/*     */   }
/*     */   
/*     */   public void saveConfigUnsynced() {
/* 176 */     Path dimensionSavePath = getMainFolderPath(); 
/* 177 */     try { BufferedOutputStream bufferedOutput = new BufferedOutputStream(new FileOutputStream(dimensionSavePath.resolve("dimension_config.txt").toFile())); try { PrintWriter writer = new PrintWriter(new OutputStreamWriter(bufferedOutput, StandardCharsets.UTF_8)); 
/* 178 */         try { if (this.confirmedMultiworld != null)
/* 179 */             writer.println("confirmedMultiworld:" + this.confirmedMultiworld); 
/* 180 */           for (Map.Entry<String, String> bindingEntry : this.autoMultiworldBindings.entrySet())
/* 181 */             writer.println("autoMWBinding:" + (String)bindingEntry.getKey() + ":" + (String)bindingEntry.getValue()); 
/* 182 */           for (Map.Entry<String, String> bindingEntry : this.multiworldNames.entrySet())
/* 183 */             writer.println("MWName:" + (String)bindingEntry.getKey() + ":" + ((String)bindingEntry.getValue()).replace(":", "^col^")); 
/* 184 */           writer.println("caveModeType:" + this.caveModeType);
/* 185 */           if (this.dimensionTypeId != null)
/* 186 */             writer.println("dimensionTypeId:" + String.valueOf(this.dimensionTypeId)); 
/* 187 */           writer.close(); } catch (Throwable throwable) { try { writer.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  bufferedOutput.close(); } catch (Throwable throwable) { try { bufferedOutput.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (IOException e)
/* 188 */     { WorldMap.LOGGER.error("suppressed exception", e); }
/*     */   
/*     */   }
/*     */   
/*     */   private void loadConfigUnsynced(int attempts) {
/* 193 */     Path dimensionSavePath = getMainFolderPath();
/*     */     
/* 195 */     BufferedReader reader = null;
/*     */     
/*     */     try {
/* 198 */       Path oldDimensionSavePath = getOldFolderPath();
/* 199 */       if (!Files.exists(dimensionSavePath, new java.nio.file.LinkOption[0]) && Files.exists(oldDimensionSavePath, new java.nio.file.LinkOption[0])) {
/* 200 */         Files.move(oldDimensionSavePath, dimensionSavePath, new java.nio.file.CopyOption[0]);
/*     */       }
/* 202 */       if (!Files.exists(dimensionSavePath, new java.nio.file.LinkOption[0]))
/* 203 */         Files.createDirectories(dimensionSavePath, (FileAttribute<?>[])new FileAttribute[0]); 
/* 204 */       loadMultiworldsList(dimensionSavePath);
/* 205 */       Path configFile = dimensionSavePath.resolve("dimension_config.txt");
/* 206 */       if (Files.exists(configFile, new java.nio.file.LinkOption[0]))
/* 207 */       { reader = new BufferedReader(new InputStreamReader(new FileInputStream(configFile.toFile()), "UTF8"));
/*     */         String line;
/* 209 */         while ((line = reader.readLine()) != null) {
/* 210 */           String[] args = line.split(":");
/* 211 */           if (args[0].equals("confirmedMultiworld")) {
/* 212 */             String savedMultiworld = (args.length > 1) ? args[1] : "";
/* 213 */             if (this.multiworldIds.contains(savedMultiworld))
/* 214 */               this.confirmedMultiworld = savedMultiworld; 
/* 215 */           } else if (args[0].equals("autoMWBinding")) {
/* 216 */             bindAutoMultiworld(args[1], args[2]);
/* 217 */           } else if (args[0].equals("MWName")) {
/* 218 */             setMultiworldName(args[1], args[2].replace("^col^", ":"));
/* 219 */           } else if (args[0].equals("dimensionTypeId")) {
/* 220 */             this.dimensionTypeId = class_2960.method_60654(line.substring(line.indexOf(':') + 1));
/*     */           } 
/* 222 */           if (args[0].equals("caveModeType"))
/* 223 */             this.caveModeType = Integer.parseInt(args[1]); 
/*     */         }  }
/*     */       else
/* 226 */       { saveConfigUnsynced(); } 
/* 227 */     } catch (IOException e1) {
/* 228 */       if (attempts > 1) {
/* 229 */         if (reader != null)
/*     */           try {
/* 231 */             reader.close();
/* 232 */           } catch (IOException e) {
/* 233 */             throw new RuntimeException(e);
/*     */           }  
/* 235 */         WorldMap.LOGGER.warn("IO exception while loading world map dimension config. Retrying... " + attempts);
/*     */         try {
/* 237 */           Thread.sleep(20L);
/* 238 */         } catch (InterruptedException interruptedException) {}
/*     */         
/* 240 */         loadConfigUnsynced(attempts - 1);
/*     */         return;
/*     */       } 
/* 243 */       throw new RuntimeException(e1);
/*     */     } finally {
/* 245 */       if (reader != null)
/*     */         try {
/* 247 */           reader.close();
/* 248 */         } catch (IOException e) {
/* 249 */           WorldMap.LOGGER.error("suppressed exception", e);
/*     */         }  
/*     */     } 
/*     */   }
/*     */   
/*     */   public void pickDefaultCustomMultiworldUnsynced() {
/* 255 */     if (this.multiworldIds.isEmpty()) {
/* 256 */       this.futureCustomSelectedMultiworld = "mw$default";
/* 257 */       this.multiworldIds.add(this.futureCustomSelectedMultiworld);
/* 258 */       setMultiworldName(this.futureCustomSelectedMultiworld, "Default");
/*     */     } else {
/* 260 */       int indexOfAuto = this.multiworldIds.indexOf(getFutureAutoMultiworld());
/* 261 */       this.futureCustomSelectedMultiworld = this.multiworldIds.get((indexOfAuto != -1) ? indexOfAuto : 0);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void loadMultiworldsList(Path dimensionSavePath) {
/* 267 */     if (!this.mapWorld.isMultiplayer())
/* 268 */       this.multiworldIds.add(""); 
/*     */     try {
/* 270 */       Stream<Path> subFolders = Files.list(dimensionSavePath);
/* 271 */       Iterator<Path> iter = subFolders.iterator();
/* 272 */       while (iter.hasNext()) {
/* 273 */         Path path = iter.next();
/* 274 */         if (path.toFile().isDirectory()) {
/* 275 */           String folderName = path.getFileName().toString();
/* 276 */           boolean autoMultiworldFormat = folderName.matches("^mw(-?\\d+),(-?\\d+),(-?\\d+)$");
/* 277 */           boolean levelIdMultiworldFormat = folderName.startsWith("mw$");
/* 278 */           boolean customMultiworldFormat = folderName.startsWith("cm$");
/* 279 */           if (autoMultiworldFormat || levelIdMultiworldFormat || customMultiworldFormat)
/*     */           {
/* 281 */             this.multiworldIds.add(folderName);
/*     */           }
/*     */         } 
/*     */       } 
/* 285 */       subFolders.close();
/* 286 */     } catch (IOException e) {
/* 287 */       WorldMap.LOGGER.error("suppressed exception", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void confirmMultiworldUnsynced() {
/* 292 */     if (!this.futureMultiworldWritable) {
/* 293 */       this.futureMultiworldWritable = true;
/* 294 */       if (this.mapWorld.getFutureMultiworldType(this) == 2 && this.futureCustomSelectedMultiworld != null)
/* 295 */         makeCustomSelectedMultiworldAutoUnsynced(); 
/* 296 */       this.confirmedMultiworld = getFutureMultiworldUnsynced();
/* 297 */       saveConfigUnsynced();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void makeCustomSelectedMultiworldAutoUnsynced() {
/* 302 */     String currentAutoMultiworld = getFutureAutoMultiworld();
/* 303 */     boolean currentBindingFound = false;
/* 304 */     for (Map.Entry<String, String> bindingEntry : this.autoMultiworldBindings.entrySet()) {
/* 305 */       if (((String)bindingEntry.getValue()).equals(this.futureCustomSelectedMultiworld)) {
/* 306 */         bindAutoMultiworld(bindingEntry.getKey(), currentAutoMultiworld);
/* 307 */         currentBindingFound = true;
/*     */         break;
/*     */       } 
/*     */     } 
/* 311 */     if (!currentBindingFound && !this.futureCustomSelectedMultiworld.startsWith("cm$"))
/* 312 */       bindAutoMultiworld(this.futureCustomSelectedMultiworld, currentAutoMultiworld); 
/* 313 */     bindAutoMultiworld(this.futureAutoMultiworldBinding, this.futureCustomSelectedMultiworld);
/* 314 */     this.futureCustomSelectedMultiworld = null;
/* 315 */     saveConfigUnsynced();
/*     */   }
/*     */   
/*     */   private void bindAutoMultiworld(String binding, String multiworld) {
/* 319 */     if (binding.equals(multiworld)) {
/* 320 */       this.autoMultiworldBindings.remove(binding);
/*     */     } else {
/* 322 */       this.autoMultiworldBindings.put(binding, multiworld);
/*     */     } 
/*     */   }
/*     */   public void resetCustomMultiworldUnsynced() {
/* 326 */     this.futureCustomSelectedMultiworld = (this.mapWorld.getFutureMultiworldType(this) == 2) ? null : this.confirmedMultiworld;
/* 327 */     if (this.futureCustomSelectedMultiworld == null && this.mapWorld.isMultiplayer() && this.mapWorld.getFutureMultiworldType(this) < 2)
/* 328 */       pickDefaultCustomMultiworldUnsynced(); 
/* 329 */     this.futureMultiworldWritable = (this.mapWorld.getFutureMultiworldType(this) != 1 && this.mapWorld.isFutureMultiworldTypeConfirmed(this));
/*     */   }
/*     */   
/*     */   public void setMultiworldUnsynced(String nextMW) {
/* 333 */     String cmw = (this.futureCustomSelectedMultiworld == null) ? getFutureMultiworldUnsynced() : this.futureCustomSelectedMultiworld;
/*     */     
/* 335 */     this.futureCustomSelectedMultiworld = nextMW;
/* 336 */     this.futureMultiworldWritable = false;
/* 337 */     WorldMap.LOGGER.info(cmw + " -> " + cmw);
/*     */   }
/*     */   
/*     */   private boolean multiworldExists(String mw) {
/* 341 */     synchronized (this.multiworldIds) {
/* 342 */       return this.multiworldIds.contains(mw);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean addMultiworldChecked(String mw) {
/* 347 */     synchronized (this.multiworldIds) {
/* 348 */       if (!this.multiworldIds.contains(mw)) {
/* 349 */         this.multiworldIds.add(mw);
/* 350 */         return true;
/*     */       } 
/*     */     } 
/* 353 */     return false;
/*     */   }
/*     */   
/*     */   public String getMultiworldName(String mwId) {
/* 357 */     if (mwId.isEmpty())
/* 358 */       return "gui.xaero_world_save"; 
/* 359 */     String tableName = this.multiworldNames.get(mwId);
/* 360 */     if (tableName == null) {
/* 361 */       if (multiworldExists(mwId)) {
/*     */         
/* 363 */         int index = 1; String automaticName;
/* 364 */         while (this.multiworldNames.containsValue(automaticName = "Map " + index++));
/* 365 */         setMultiworldName(mwId, automaticName);
/* 366 */         synchronized ((this.mapWorld.getMapProcessor()).uiSync) {
/* 367 */           saveConfigUnsynced();
/*     */         } 
/* 369 */         return automaticName;
/*     */       } 
/* 371 */       return mwId;
/*     */     } 
/* 373 */     return tableName;
/*     */   }
/*     */   
/*     */   public void setMultiworldName(String mwId, String mwName) {
/* 377 */     this.multiworldNames.put(mwId, mwName);
/*     */   }
/*     */   
/*     */   private String getFutureAutoMultiworld() {
/* 381 */     String futureAutoMultiworldBinding = this.futureAutoMultiworldBinding;
/* 382 */     if (futureAutoMultiworldBinding == null)
/* 383 */       return null; 
/* 384 */     String boundMultiworld = this.autoMultiworldBindings.get(futureAutoMultiworldBinding);
/* 385 */     if (boundMultiworld == null)
/* 386 */       return futureAutoMultiworldBinding; 
/* 387 */     return boundMultiworld;
/*     */   }
/*     */   
/*     */   public MapWorld getMapWorld() {
/* 391 */     return this.mapWorld;
/*     */   }
/*     */   
/*     */   public void deleteMultiworldMapDataUnsynced(String mwId) {
/*     */     try {
/* 396 */       Path currentDimFolder = getMainFolderPath();
/* 397 */       Path currentMWFolder = currentDimFolder.resolve(mwId);
/* 398 */       Path binFolder = currentDimFolder.resolve("last deleted");
/* 399 */       Path binMWFolder = binFolder.resolve(mwId);
/* 400 */       if (!Files.exists(binFolder, new java.nio.file.LinkOption[0]))
/* 401 */         Files.createDirectories(binFolder, (FileAttribute<?>[])new FileAttribute[0]); 
/* 402 */       FileUtils.cleanDirectory(binFolder.toFile());
/* 403 */       Files.move(currentMWFolder, binMWFolder, new java.nio.file.CopyOption[0]);
/* 404 */     } catch (Exception e) {
/* 405 */       WorldMap.LOGGER.error("suppressed exception", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void deleteMultiworldId(String mwId) {
/* 410 */     synchronized (this.multiworldIds) {
/* 411 */       this.multiworldIds.remove(mwId);
/* 412 */       this.multiworldNames.remove(mwId);
/* 413 */       if (mwId.equals(this.confirmedMultiworld))
/* 414 */         this.confirmedMultiworld = null; 
/*     */     } 
/*     */   }
/*     */   
/*     */   public class_5321<class_1937> getDimId() {
/* 419 */     return this.dimId;
/*     */   }
/*     */   
/*     */   public boolean hasConfirmedMultiworld() {
/* 423 */     return (this.confirmedMultiworld != null);
/*     */   }
/*     */   
/*     */   public boolean isFutureMultiworldServerBased() {
/* 427 */     return this.futureMultiworldServerBased;
/*     */   }
/*     */   
/*     */   public DimensionHighlighterHandler getHighlightHandler() {
/* 431 */     return this.highlightHandler;
/*     */   }
/*     */   
/*     */   public void onClearCachedHighlightHash(int regionX, int regionZ) {
/* 435 */     this.mapRegions.onClearCachedHighlightHash(regionX, regionZ);
/*     */   }
/*     */   
/*     */   public void onClearCachedHighlightHashes() {
/* 439 */     this.mapRegions.onClearCachedHighlightHashes();
/*     */   }
/*     */   
/*     */   public boolean hasDoneRegionDetection() {
/* 443 */     return this.doneRegionDetection;
/*     */   }
/*     */   
/*     */   public void addWorldSaveRegionDetection(RegionDetection regionDetection) {
/* 447 */     synchronized (this.worldSaveDetectedRegions) {
/* 448 */       Hashtable<Integer, RegionDetection> column = this.worldSaveDetectedRegions.get(Integer.valueOf(regionDetection.getRegionX()));
/* 449 */       if (column == null)
/* 450 */         this.worldSaveDetectedRegions.put(Integer.valueOf(regionDetection.getRegionX()), column = new Hashtable<>()); 
/* 451 */       column.put(Integer.valueOf(regionDetection.getRegionZ()), regionDetection);
/* 452 */       this.worldSaveDetectedRegionsLinked.add((ILinkedChainNode)regionDetection);
/*     */     } 
/*     */   }
/*     */   
/*     */   public RegionDetection getWorldSaveRegionDetection(int x, int z) {
/* 457 */     if (this.worldSaveDetectedRegions == null)
/* 458 */       return null; 
/* 459 */     Hashtable<Integer, RegionDetection> column = this.worldSaveDetectedRegions.get(Integer.valueOf(x));
/* 460 */     if (column != null) {
/* 461 */       return column.get(Integer.valueOf(z));
/*     */     }
/* 463 */     return null;
/*     */   }
/*     */   
/*     */   public int getCaveModeType() {
/* 467 */     return this.caveModeType;
/*     */   }
/*     */   
/*     */   public void toggleCaveModeType(boolean forward) {
/* 471 */     this.caveModeType += forward ? 1 : -1;
/* 472 */     if (forward) {
/* 473 */       if (this.caveModeType >= 3)
/* 474 */         this.caveModeType = 0; 
/*     */       return;
/*     */     } 
/* 477 */     if (this.caveModeType < 0)
/* 478 */       this.caveModeType = 2; 
/*     */   }
/*     */   
/*     */   public Iterable<Hashtable<Integer, RegionDetection>> getWorldSaveDetectedRegions() {
/* 482 */     return this.worldSaveDetectedRegions.values();
/*     */   }
/*     */   
/*     */   public Iterable<RegionDetection> getLinkedWorldSaveDetectedRegions() {
/* 486 */     return (Iterable<RegionDetection>)this.worldSaveDetectedRegionsLinked;
/*     */   }
/*     */   
/*     */   public MapFullReloader getFullReloader() {
/* 490 */     return this.fullReloader;
/*     */   }
/*     */   
/*     */   public void startFullMapReload(int caveLayer, boolean resave, MapProcessor mapProcessor) {
/* 494 */     MapLayer layer = this.mapRegions.getLayer(caveLayer);
/* 495 */     this.fullReloader = new MapFullReloader(caveLayer, resave, layer.getLinkedCompleteWorldSaveDetectedRegions().iterator(), this, mapProcessor);
/*     */   }
/*     */   
/*     */   public void clearFullMapReload() {
/* 499 */     this.fullReloader = null;
/*     */   }
/*     */   
/*     */   public class_2874 getDimensionType(class_2378<class_2874> dimensionTypes) {
/* 503 */     if (this.dimensionType != null)
/* 504 */       return this.dimensionType; 
/* 505 */     this.dimensionType = getDimensionType(this.dimId, this.dimensionTypeId, dimensionTypes);
/* 506 */     return this.dimensionType;
/*     */   }
/*     */   
/*     */   private static class_2874 getDimensionType(class_5321<class_1937> dimId, class_2960 dimensionTypeId, class_2378<class_2874> dimensionTypes) {
/* 510 */     if (dimensionTypeId == null)
/*     */     {
/* 512 */       if (dimId == class_1937.field_25180) {
/* 513 */         dimensionTypeId = class_7134.field_37671;
/* 514 */       } else if (dimId == class_1937.field_25179) {
/* 515 */         dimensionTypeId = class_7134.field_37670;
/* 516 */       } else if (dimId == class_1937.field_25181) {
/* 517 */         dimensionTypeId = class_7134.field_37672;
/*     */       } else {
/* 519 */         class_1132 integratedServer = class_310.method_1551().method_1576();
/* 520 */         if (integratedServer == null)
/* 521 */           return null; 
/* 522 */         class_3218 serverLevel = integratedServer.method_3847(dimId);
/* 523 */         if (serverLevel == null)
/* 524 */           return null; 
/* 525 */         return serverLevel.method_8597();
/*     */       } 
/*     */     }
/* 528 */     if (dimensionTypes == null)
/* 529 */       return null; 
/* 530 */     return (class_2874)dimensionTypes.method_63535(dimensionTypeId);
/*     */   }
/*     */   
/*     */   public static class_2874 getDimensionType(MapDimension dim, class_5321<class_1937> dimId, class_2378<class_2874> dimensionTypes) {
/* 534 */     return (dim == null) ? getDimensionType(dimId, (class_2960)null, dimensionTypes) : dim.getDimensionType(dimensionTypes);
/*     */   }
/*     */   
/*     */   public void onWorldChangeUnsynced(class_1937 newWorld) {
/* 538 */     if (newWorld != null && this.dimId.equals(newWorld.method_27983())) {
/* 539 */       class_5321<class_2874> dimTypeId = newWorld.method_40134().method_40230().get();
/* 540 */       if (!dimTypeId.method_29177().equals(this.dimensionTypeId)) {
/* 541 */         this.dimensionTypeId = dimTypeId.method_29177();
/* 542 */         saveConfigUnsynced();
/*     */       } 
/*     */     } 
/* 545 */     this.dimensionType = null;
/* 546 */     this.dimensionEffects = null;
/*     */   }
/*     */   
/*     */   public boolean isUsingUnknownDimensionType(class_2378<class_2874> dimensionTypes) {
/* 550 */     return (getDimensionType(dimensionTypes) == null);
/*     */   }
/*     */   
/*     */   public boolean isCacheOnlyMode(class_2378<class_2874> dimensionTypes) {
/* 554 */     return isUsingUnknownDimensionType(dimensionTypes);
/*     */   }
/*     */   
/*     */   public void onCreationUnsynced() {
/* 558 */     loadConfigUnsynced(10);
/* 559 */     if (this.dimId == class_1937.field_25179 || class_7134.field_37670.equals(this.dimensionTypeId)) {
/*     */ 
/*     */ 
/*     */       
/* 563 */       this.shadowR = 0.518F;
/* 564 */       this.shadowG = 0.678F;
/* 565 */       this.shadowB = 1.0F;
/* 566 */     } else if (this.dimId == class_1937.field_25180 || class_7134.field_37671.equals(this.dimensionTypeId)) {
/* 567 */       this.shadowR = 1.0F;
/* 568 */       this.shadowG = 0.0F;
/* 569 */       this.shadowB = 0.0F;
/*     */     } 
/*     */   }
/*     */   
/*     */   public float getShadowR() {
/* 574 */     return this.shadowR;
/*     */   }
/*     */   
/*     */   public float getShadowG() {
/* 578 */     return this.shadowG;
/*     */   }
/*     */   
/*     */   public float getShadowB() {
/* 582 */     return this.shadowB;
/*     */   }
/*     */   
/*     */   public class_5294 getDimensionEffects(class_2378<class_2874> dimensionTypes) {
/* 586 */     if (this.dimensionEffects == null) {
/* 587 */       class_2874 type = getDimensionType(dimensionTypes);
/* 588 */       if (type == null)
/* 589 */         return null; 
/* 590 */       this.dimensionEffects = class_5294.method_28111(type);
/*     */     } 
/* 592 */     return this.dimensionEffects;
/*     */   }
/*     */   
/*     */   public float getSkyDarken(float partial, class_638 world, class_2378<class_2874> dimensionTypes) {
/* 596 */     if (this.dimId == world.method_27983())
/* 597 */       return world.method_23783(1.0F); 
/* 598 */     class_2874 dimType = getDimensionType(dimensionTypes);
/* 599 */     if (dimType == null)
/* 600 */       return 1.0F; 
/* 601 */     float timeOfDay = dimType.method_28528(world.method_30271());
/* 602 */     float brightness = 1.0F - class_3532.method_15362(timeOfDay * 6.2831855F) * 2.0F + 0.2F;
/* 603 */     brightness = 1.0F - class_3532.method_15363(brightness, 0.0F, 1.0F);
/* 604 */     return brightness * 0.8F + 0.2F;
/*     */   }
/*     */   
/*     */   public double calculateDimScale(class_2378<class_2874> dimensionTypes) {
/* 608 */     class_2874 dimType = getDimensionType(dimensionTypes);
/* 609 */     return (dimType == null) ? 1.0D : dimType.comp_646();
/*     */   }
/*     */   
/*     */   public double calculateDimDiv(class_2378<class_2874> dimensionTypes, class_2874 actualDimension) {
/* 613 */     return calculateDimScale(dimensionTypes) / ((actualDimension == null) ? 1.0D : actualDimension.comp_646());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MapConnectionNode getPlayerMapKey() {
/* 619 */     String playerMW = (this.mapWorld.getFutureMultiworldType(this) == 1) ? null : getFutureAutoMultiworld();
/* 620 */     if (playerMW == null)
/* 621 */       playerMW = this.confirmedMultiworld; 
/* 622 */     if (playerMW == null)
/* 623 */       return null; 
/* 624 */     return new MapConnectionNode(this.dimId, playerMW);
/*     */   }
/*     */ 
/*     */   
/*     */   public MapConnectionNode getSelectedMapKeyUnsynced() {
/* 629 */     String selectedMW = getFutureMultiworldUnsynced();
/* 630 */     if (selectedMW == null)
/* 631 */       selectedMW = getCurrentMultiworld(); 
/* 632 */     if (selectedMW == null)
/* 633 */       return null; 
/* 634 */     return new MapConnectionNode(this.dimId, selectedMW);
/*     */   }
/*     */   
/*     */   public boolean isAutoSelected() {
/* 638 */     String selectedMW = getFutureCustomSelectedMultiworld();
/* 639 */     return (selectedMW == null || selectedMW.equals(getFutureAutoMultiworld()));
/*     */   }
/*     */   
/*     */   public class_2960 getDimensionTypeId() {
/* 643 */     return this.dimensionTypeId;
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\world\MapDimension.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */