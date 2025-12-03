/*     */ package xaero.map.world;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.attribute.FileAttribute;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Hashtable;
/*     */ import java.util.List;
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.class_1937;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_5321;
/*     */ import net.minecraft.class_638;
/*     */ import xaero.map.MapProcessor;
/*     */ import xaero.map.WorldMap;
/*     */ import xaero.map.file.MapSaveLoad;
/*     */ import xaero.map.gui.GuiDimensionOptions;
/*     */ 
/*     */ public class MapWorld {
/*     */   private MapProcessor mapProcessor;
/*     */   private boolean isMultiplayer;
/*     */   private String mainId;
/*     */   private String oldUnfixedMainId;
/*     */   private Hashtable<class_5321<class_1937>, MapDimension> dimensions;
/*     */   private class_5321<class_1937> currentDimensionId;
/*     */   private class_5321<class_1937> futureDimensionId;
/*     */   private class_5321<class_1937> customDimensionId;
/*     */   private int futureMultiworldType;
/*     */   private int currentMultiworldType;
/*     */   private boolean futureMultiworldTypeConfirmed = true;
/*     */   private boolean currentMultiworldTypeConfirmed = false;
/*     */   private boolean ignoreServerLevelId;
/*     */   private boolean ignoreHeightmaps;
/*  37 */   private String playerTeleportCommandFormat = "/tp @s {name}";
/*  38 */   private String normalTeleportCommandFormat = "/tp @s {x} {y} {z}";
/*  39 */   private String dimensionTeleportCommandFormat = "/execute as @s in {d} run tp {x} {y} {z}";
/*     */   
/*     */   private boolean teleportAllowed = true;
/*     */   private MapConnectionManager mapConnections;
/*     */   
/*     */   public MapWorld(String mainId, String oldUnfixedMainId, MapProcessor mapProcessor) {
/*  45 */     this.mainId = mainId;
/*  46 */     this.oldUnfixedMainId = oldUnfixedMainId;
/*  47 */     this.mapProcessor = mapProcessor;
/*  48 */     this.isMultiplayer = MapProcessor.isWorldMultiplayer(MapProcessor.isWorldRealms(mainId), mainId);
/*  49 */     this.dimensions = new Hashtable<>();
/*  50 */     this.futureMultiworldType = this.currentMultiworldType = 0;
/*     */   }
/*     */   
/*     */   public MapDimension getDimension(class_5321<class_1937> dimId) {
/*  54 */     if (dimId == null)
/*  55 */       return null; 
/*  56 */     synchronized (this.dimensions) {
/*  57 */       return this.dimensions.get(dimId);
/*     */     } 
/*     */   }
/*     */   
/*     */   public MapDimension createDimensionUnsynced(class_5321<class_1937> dimId) {
/*  62 */     synchronized (this.dimensions) {
/*  63 */       MapDimension result = this.dimensions.get(dimId);
/*  64 */       if (result == null) {
/*  65 */         this.dimensions.put(dimId, result = new MapDimension(this, dimId, this.mapProcessor.getHighlighterRegistry()));
/*  66 */         result.onCreationUnsynced();
/*     */       } 
/*  68 */       return result;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getMainId() {
/*  73 */     return this.mainId;
/*     */   }
/*     */   
/*     */   public String getOldUnfixedMainId() {
/*  77 */     return this.oldUnfixedMainId;
/*     */   }
/*     */   
/*     */   public String getCurrentMultiworld() {
/*  81 */     MapDimension container = getDimension(this.currentDimensionId);
/*  82 */     return container.getCurrentMultiworld();
/*     */   }
/*     */   
/*     */   public String getFutureMultiworldUnsynced() {
/*  86 */     MapDimension container = getDimension(this.futureDimensionId);
/*  87 */     return container.getFutureMultiworldUnsynced();
/*     */   }
/*     */   
/*     */   public MapDimension getCurrentDimension() {
/*  91 */     class_5321<class_1937> dimId = this.currentDimensionId;
/*  92 */     if (dimId == null)
/*  93 */       return null; 
/*  94 */     return getDimension(dimId);
/*     */   }
/*     */   
/*     */   public MapDimension getFutureDimension() {
/*  98 */     class_5321<class_1937> dimId = this.futureDimensionId;
/*  99 */     if (dimId == null)
/* 100 */       return null; 
/* 101 */     return getDimension(dimId);
/*     */   }
/*     */   
/*     */   public class_5321<class_1937> getCurrentDimensionId() {
/* 105 */     return this.currentDimensionId;
/*     */   }
/*     */   
/*     */   public class_5321<class_1937> getFutureDimensionId() {
/* 109 */     return this.futureDimensionId;
/*     */   }
/*     */   
/*     */   public void setFutureDimensionId(class_5321<class_1937> dimension) {
/* 113 */     this.futureDimensionId = dimension;
/*     */   }
/*     */   
/*     */   public class_5321<class_1937> getCustomDimensionId() {
/* 117 */     return this.customDimensionId;
/*     */   }
/*     */   
/*     */   public void setCustomDimensionId(class_5321<class_1937> dimension) {
/* 121 */     this.customDimensionId = dimension;
/*     */   }
/*     */   
/*     */   public void switchToFutureUnsynced() {
/* 125 */     this.currentDimensionId = this.futureDimensionId;
/* 126 */     getDimension(this.currentDimensionId).switchToFutureUnsynced();
/*     */   }
/*     */   
/*     */   public List<MapDimension> getDimensionsList() {
/* 130 */     List<MapDimension> destList = new ArrayList<>();
/* 131 */     getDimensions(destList);
/* 132 */     return destList;
/*     */   }
/*     */   
/*     */   public void getDimensions(List<MapDimension> dest) {
/* 136 */     synchronized (this.dimensions) {
/* 137 */       dest.addAll(this.dimensions.values());
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getCurrentMultiworldType() {
/* 142 */     return this.currentMultiworldType;
/*     */   }
/*     */   
/*     */   public boolean isMultiplayer() {
/* 146 */     return this.isMultiplayer;
/*     */   }
/*     */   
/*     */   public boolean isCurrentMultiworldTypeConfirmed() {
/* 150 */     return this.currentMultiworldTypeConfirmed;
/*     */   }
/*     */   
/*     */   public int getFutureMultiworldType(MapDimension dim) {
/* 154 */     return dim.isFutureMultiworldServerBased() ? 2 : this.futureMultiworldType;
/*     */   }
/*     */   
/*     */   public void toggleMultiworldTypeUnsynced() {
/* 158 */     unconfirmMultiworldTypeUnsynced();
/* 159 */     this.futureMultiworldType = (this.futureMultiworldType + 1) % 3;
/* 160 */     getCurrentDimension().resetCustomMultiworldUnsynced();
/* 161 */     saveConfig();
/*     */   }
/*     */   
/*     */   public void unconfirmMultiworldTypeUnsynced() {
/* 165 */     this.futureMultiworldTypeConfirmed = false;
/*     */   }
/*     */   
/*     */   public void confirmMultiworldTypeUnsynced() {
/* 169 */     this.futureMultiworldTypeConfirmed = true;
/*     */   }
/*     */   
/*     */   public boolean isFutureMultiworldTypeConfirmed(MapDimension dim) {
/* 173 */     return dim.isFutureMultiworldServerBased() ? true : this.futureMultiworldTypeConfirmed;
/*     */   }
/*     */   
/*     */   public void switchToFutureMultiworldTypeUnsynced() {
/* 177 */     MapDimension futureDim = getFutureDimension();
/* 178 */     this.currentMultiworldType = getFutureMultiworldType(getFutureDimension());
/* 179 */     this.currentMultiworldTypeConfirmed = isFutureMultiworldTypeConfirmed(futureDim);
/*     */   }
/*     */   
/*     */   public void load() {
/* 183 */     this.mapConnections = this.isMultiplayer ? new MapConnectionManager() : new MapConnectionManager(this)
/*     */       {
/*     */         public boolean isConnected(MapConnectionNode mapKey1, MapConnectionNode mapKey2) {
/* 186 */           return true;
/*     */         }
/*     */ 
/*     */         
/*     */         public void save(PrintWriter writer) {}
/*     */       };
/* 192 */     Path rootSavePath = MapSaveLoad.getRootFolder(this.mainId);
/* 193 */     loadConfig(rootSavePath, 10); 
/* 194 */     try { Stream<Path> stream = Files.list(rootSavePath); 
/* 195 */       try { stream.forEach(folder -> {
/*     */               if (!Files.isDirectory(folder, new java.nio.file.LinkOption[0]))
/*     */                 return; 
/*     */               String folderName = folder.getFileName().toString();
/*     */               class_5321<class_1937> folderDimensionId = this.mapProcessor.getDimensionIdForFolder(folderName);
/*     */               if (folderDimensionId == null)
/*     */                 return; 
/*     */               createDimensionUnsynced(folderDimensionId);
/*     */             });
/* 204 */         if (stream != null) stream.close();  } catch (Throwable throwable) { if (stream != null) try { stream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException e)
/* 205 */     { WorldMap.LOGGER.error("suppressed exception", e); }
/*     */   
/*     */   }
/*     */   
/*     */   private void loadConfig(Path rootSavePath, int attempts) {
/* 210 */     MapProcessor mp = this.mapProcessor;
/* 211 */     BufferedReader reader = null;
/*     */     try {
/* 213 */       if (!Files.exists(rootSavePath, new java.nio.file.LinkOption[0]))
/* 214 */         Files.createDirectories(rootSavePath, (FileAttribute<?>[])new FileAttribute[0]); 
/* 215 */       Path configFile = rootSavePath.resolve("server_config.txt");
/*     */ 
/*     */       
/* 218 */       Path oldOverworldSavePath = mp.getMapSaveLoad().getOldFolder(this.oldUnfixedMainId, "null");
/* 219 */       Path oldConfigFile = oldOverworldSavePath.resolve("server_config.txt");
/* 220 */       if (!Files.exists(configFile, new java.nio.file.LinkOption[0]) && Files.exists(oldConfigFile, new java.nio.file.LinkOption[0])) {
/* 221 */         Files.move(oldConfigFile, configFile, new java.nio.file.CopyOption[0]);
/*     */       }
/* 223 */       if (Files.exists(configFile, new java.nio.file.LinkOption[0])) {
/* 224 */         reader = new BufferedReader(new FileReader(configFile.toFile()));
/*     */         String line;
/* 226 */         while ((line = reader.readLine()) != null) {
/* 227 */           String[] args = line.split(":");
/* 228 */           if (this.isMultiplayer)
/* 229 */             if (args[0].equals("multiworldType")) {
/* 230 */               this.futureMultiworldType = Integer.parseInt(args[1]);
/* 231 */             } else if (args[0].equals("ignoreServerLevelId")) {
/* 232 */               this.ignoreServerLevelId = args[1].equals("true");
/*     */             }  
/* 234 */           if (args[0].equals("ignoreHeightmaps")) {
/* 235 */             this.ignoreHeightmaps = args[1].equals("true"); continue;
/* 236 */           }  if (args[0].equals("playerTeleportCommandFormat")) {
/* 237 */             this.playerTeleportCommandFormat = line.substring(line.indexOf(':') + 1); continue;
/* 238 */           }  if (args[0].equals("teleportCommandFormat")) {
/* 239 */             this.normalTeleportCommandFormat = line.substring(line.indexOf(':') + 1);
/* 240 */             this.dimensionTeleportCommandFormat = "/execute as @s in {d} run " + this.normalTeleportCommandFormat.substring(1); continue;
/* 241 */           }  if (args[0].equals("dimensionTeleportCommandFormat")) {
/* 242 */             this.dimensionTeleportCommandFormat = line.substring(line.indexOf(':') + 1); continue;
/* 243 */           }  if (args[0].equals("normalTeleportCommandFormat")) {
/* 244 */             this.normalTeleportCommandFormat = line.substring(line.indexOf(':') + 1); continue;
/* 245 */           }  if (args[0].equals("teleportAllowed")) {
/* 246 */             this.teleportAllowed = args[1].equals("true"); continue;
/* 247 */           }  if (this.isMultiplayer && args[0].equals("connection")) {
/* 248 */             String mapKey1 = args[1];
/* 249 */             if (args.length > 2) {
/* 250 */               String mapKey2 = args[2];
/* 251 */               MapConnectionNode connectionNode1 = MapConnectionNode.fromString(mapKey1);
/* 252 */               MapConnectionNode connectionNode2 = MapConnectionNode.fromString(mapKey2);
/* 253 */               if (connectionNode1 != null && connectionNode2 != null)
/* 254 */                 this.mapConnections.addConnection(connectionNode1, connectionNode2); 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } else {
/* 259 */         saveConfig();
/*     */       } 
/* 261 */     } catch (IOException e1) {
/* 262 */       if (attempts > 1) {
/* 263 */         if (reader != null)
/*     */           try {
/* 265 */             reader.close();
/* 266 */           } catch (IOException e) {
/* 267 */             throw new RuntimeException(e);
/*     */           }  
/* 269 */         WorldMap.LOGGER.warn("IO exception while loading world map config. Retrying... " + attempts);
/*     */         try {
/* 271 */           Thread.sleep(20L);
/* 272 */         } catch (InterruptedException interruptedException) {}
/*     */         
/* 274 */         loadConfig(rootSavePath, attempts - 1);
/*     */         return;
/*     */       } 
/* 277 */       throw new RuntimeException(e1);
/*     */     } finally {
/* 279 */       if (reader != null) {
/*     */         try {
/* 281 */           reader.close();
/* 282 */         } catch (IOException e) {
/* 283 */           WorldMap.LOGGER.error("suppressed exception", e);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void saveConfig() {
/* 290 */     Path rootSavePath = MapSaveLoad.getRootFolder(this.mainId);
/* 291 */     PrintWriter writer = null;
/*     */     try {
/* 293 */       writer = new PrintWriter(new FileWriter(rootSavePath.resolve("server_config.txt").toFile()));
/* 294 */       if (this.isMultiplayer) {
/* 295 */         writer.println("multiworldType:" + this.futureMultiworldType);
/* 296 */         writer.println("ignoreServerLevelId:" + this.ignoreServerLevelId);
/*     */       } 
/* 298 */       writer.println("ignoreHeightmaps:" + this.ignoreHeightmaps);
/* 299 */       writer.println("playerTeleportCommandFormat:" + this.playerTeleportCommandFormat);
/* 300 */       writer.println("normalTeleportCommandFormat:" + this.normalTeleportCommandFormat);
/* 301 */       writer.println("dimensionTeleportCommandFormat:" + this.dimensionTeleportCommandFormat);
/* 302 */       writer.println("teleportAllowed:" + this.teleportAllowed);
/* 303 */       if (this.isMultiplayer)
/* 304 */         this.mapConnections.save(writer); 
/* 305 */     } catch (IOException e) {
/* 306 */       WorldMap.LOGGER.error("suppressed exception", e);
/*     */     } finally {
/* 308 */       if (writer != null)
/* 309 */         writer.close(); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public MapProcessor getMapProcessor() {
/* 314 */     return this.mapProcessor;
/*     */   }
/*     */   
/*     */   public boolean isIgnoreServerLevelId() {
/* 318 */     return this.ignoreServerLevelId;
/*     */   }
/*     */   
/*     */   public boolean isIgnoreHeightmaps() {
/* 322 */     return this.ignoreHeightmaps;
/*     */   }
/*     */   
/*     */   public void setIgnoreHeightmaps(boolean ignoreHeightmaps) {
/* 326 */     this.ignoreHeightmaps = ignoreHeightmaps;
/*     */   }
/*     */   
/*     */   public String getPlayerTeleportCommandFormat() {
/* 330 */     return this.playerTeleportCommandFormat;
/*     */   }
/*     */   
/*     */   public void setPlayerTeleportCommandFormat(String playerTeleportCommandFormat) {
/* 334 */     this.playerTeleportCommandFormat = playerTeleportCommandFormat;
/*     */   }
/*     */   
/*     */   public String getTeleportCommandFormat() {
/* 338 */     return this.normalTeleportCommandFormat;
/*     */   }
/*     */   
/*     */   public void setTeleportCommandFormat(String teleportCommandFormat) {
/* 342 */     this.normalTeleportCommandFormat = teleportCommandFormat;
/*     */   }
/*     */   
/*     */   public String getDimensionTeleportCommandFormat() {
/* 346 */     return this.dimensionTeleportCommandFormat;
/*     */   }
/*     */   
/*     */   public void setDimensionTeleportCommandFormat(String dimensionTeleportCommandFormat) {
/* 350 */     this.dimensionTeleportCommandFormat = dimensionTeleportCommandFormat;
/*     */   }
/*     */   
/*     */   public boolean isTeleportAllowed() {
/* 354 */     return this.teleportAllowed;
/*     */   }
/*     */   
/*     */   public void setTeleportAllowed(boolean teleportAllowed) {
/* 358 */     this.teleportAllowed = teleportAllowed;
/*     */   }
/*     */   
/*     */   public void clearAllCachedHighlightHashes() {
/* 362 */     synchronized (this.dimensions) {
/* 363 */       for (MapDimension dim : this.dimensions.values())
/* 364 */         dim.getHighlightHandler().clearCachedHashes(); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isUsingCustomDimension() {
/* 369 */     class_638 class_638 = this.mapProcessor.getWorld();
/* 370 */     return (class_638 != null && class_638.method_27983() != getCurrentDimensionId());
/*     */   }
/*     */   
/*     */   public boolean isUsingUnknownDimensionType() {
/* 374 */     return getCurrentDimension().isUsingUnknownDimensionType(this.mapProcessor.getWorldDimensionTypeRegistry());
/*     */   }
/*     */   
/*     */   public boolean isCacheOnlyMode() {
/* 378 */     return getCurrentDimension().isCacheOnlyMode(this.mapProcessor.getWorldDimensionTypeRegistry());
/*     */   }
/*     */   
/*     */   public void onWorldChangeUnsynced(class_638 world) {
/* 382 */     synchronized (this.dimensions) {
/* 383 */       for (MapDimension dim : this.dimensions.values())
/* 384 */         dim.onWorldChangeUnsynced((class_1937)world); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public class_5321<class_1937> getPotentialDimId() {
/* 389 */     class_5321<class_1937> customDimId = getCustomDimensionId();
/* 390 */     return (customDimId == null) ? this.mapProcessor.mainWorld.method_27983() : customDimId;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MapConnectionNode getPlayerMapKey() {
/* 396 */     this.mapProcessor.updateVisitedDimension(this.mapProcessor.mainWorld);
/* 397 */     class_5321<class_1937> dimId = this.mapProcessor.mainWorld.method_27983();
/* 398 */     MapDimension dim = getDimension(dimId);
/* 399 */     return (dim == null) ? null : dim.getPlayerMapKey();
/*     */   }
/*     */   
/*     */   public MapConnectionManager getMapConnections() {
/* 403 */     return this.mapConnections;
/*     */   }
/*     */   
/*     */   public void toggleDimension(boolean forward) {
/* 407 */     MapDimension futureDimension = getFutureDimension();
/* 408 */     if (futureDimension == null)
/*     */       return; 
/* 410 */     GuiDimensionOptions dimOptions = GuiMapSwitching.getSortedDimensionOptions(futureDimension);
/* 411 */     int step = forward ? 1 : (dimOptions.values.length - 1);
/* 412 */     int nextIndex = (dimOptions.selected == -1) ? 0 : ((dimOptions.selected + step) % dimOptions.values.length);
/* 413 */     class_5321<class_1937> nextDimId = dimOptions.values[nextIndex];
/* 414 */     if (nextDimId == (class_310.method_1551()).field_1687.method_27983())
/* 415 */       nextDimId = null; 
/* 416 */     setCustomDimensionId(nextDimId);
/* 417 */     this.mapProcessor.checkForWorldUpdate();
/*     */   }
/*     */   
/*     */   public static String convertWorldFolderToRootId(int version, String worldFolder) {
/* 421 */     String rootId = worldFolder.replaceAll("_", "^us^");
/* 422 */     if (MapProcessor.isWorldMultiplayer(MapProcessor.isWorldRealms(rootId), rootId))
/* 423 */       rootId = "^e^" + rootId; 
/* 424 */     if (version >= 3)
/*     */     {
/*     */       
/* 427 */       rootId = rootId.replace("[", "%lb%").replace("]", "%rb%"); } 
/* 428 */     return rootId;
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\world\MapWorld.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */