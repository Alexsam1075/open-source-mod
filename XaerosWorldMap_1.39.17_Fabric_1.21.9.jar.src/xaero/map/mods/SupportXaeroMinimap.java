/*     */ package xaero.map.mods;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import net.minecraft.class_1074;
/*     */ import net.minecraft.class_11909;
/*     */ import net.minecraft.class_1657;
/*     */ import net.minecraft.class_1937;
/*     */ import net.minecraft.class_2378;
/*     */ import net.minecraft.class_2874;
/*     */ import net.minecraft.class_304;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_3675;
/*     */ import net.minecraft.class_437;
/*     */ import net.minecraft.class_5321;
/*     */ import xaero.common.HudMod;
/*     */ import xaero.common.IXaeroMinimap;
/*     */ import xaero.common.effect.Effects;
/*     */ import xaero.common.graphics.shader.CustomUniform;
/*     */ import xaero.common.graphics.shader.CustomUniforms;
/*     */ import xaero.common.gui.GuiAddWaypoint;
/*     */ import xaero.common.gui.GuiWaypoints;
/*     */ import xaero.common.gui.GuiWorldTpCommand;
/*     */ import xaero.common.minimap.highlight.DimensionHighlighterHandler;
/*     */ import xaero.common.minimap.waypoints.Waypoint;
/*     */ import xaero.common.misc.Misc;
/*     */ import xaero.common.mods.SupportXaeroWorldmap;
/*     */ import xaero.common.settings.ModSettings;
/*     */ import xaero.hud.controls.key.KeyMappingController;
/*     */ import xaero.hud.controls.key.function.KeyMappingFunction;
/*     */ import xaero.hud.minimap.BuiltInHudModules;
/*     */ import xaero.hud.minimap.controls.key.MinimapKeyMappings;
/*     */ import xaero.hud.minimap.module.MinimapSession;
/*     */ import xaero.hud.minimap.radar.render.element.RadarRenderer;
/*     */ import xaero.hud.minimap.waypoint.WaypointSession;
/*     */ import xaero.hud.minimap.waypoint.WaypointTeleport;
/*     */ import xaero.hud.minimap.waypoint.set.WaypointSet;
/*     */ import xaero.hud.minimap.world.MinimapDimensionHelper;
/*     */ import xaero.hud.minimap.world.MinimapWorld;
/*     */ import xaero.hud.minimap.world.MinimapWorldManager;
/*     */ import xaero.hud.minimap.world.container.MinimapWorldRootContainer;
/*     */ import xaero.hud.minimap.world.state.MinimapWorldState;
/*     */ import xaero.hud.minimap.world.state.MinimapWorldStateUpdater;
/*     */ import xaero.hud.path.XaeroPath;
/*     */ import xaero.map.WorldMap;
/*     */ import xaero.map.element.HoveredMapElementHolder;
/*     */ import xaero.map.element.MapElementGraphics;
/*     */ import xaero.map.graphics.shader.BuiltInCustomUniforms;
/*     */ import xaero.map.gui.GuiMap;
/*     */ import xaero.map.misc.KeySortableByOther;
/*     */ import xaero.map.misc.Misc;
/*     */ import xaero.map.mods.gui.Waypoint;
/*     */ import xaero.map.mods.gui.WaypointMenuRenderContext;
/*     */ import xaero.map.mods.gui.WaypointMenuRenderProvider;
/*     */ import xaero.map.mods.gui.WaypointMenuRenderer;
/*     */ import xaero.map.mods.gui.WaypointRenderer;
/*     */ import xaero.map.mods.minimap.element.MinimapElementGraphicsWrapper;
/*     */ import xaero.map.mods.minimap.element.RadarRendererWrapperHelper;
/*     */ import xaero.map.mods.minimap.shader.CustomUniformWrapper;
/*     */ import xaero.map.mods.minimap.tracker.system.MinimapSyncedPlayerTrackerSystem;
/*     */ import xaero.map.radar.tracker.system.IPlayerTrackerSystem;
/*     */ import xaero.map.world.MapDimension;
/*     */ import xaero.map.world.MapWorld;
/*     */ 
/*     */ public class SupportXaeroMinimap {
/*     */   HudMod modMain;
/*     */   public int compatibilityVersion;
/*     */   private boolean deathpoints = true;
/*     */   private boolean refreshWaypoints = true;
/*     */   private MinimapWorld waypointWorld;
/*     */   private MinimapWorld mapWaypointWorld;
/*     */   private class_5321<class_1937> mapDimId;
/*     */   private double dimDiv;
/*     */   private WaypointSet waypointSet;
/*     */   private boolean allSets;
/*     */   private ArrayList<Waypoint> waypoints;
/*     */   private ArrayList<Waypoint> waypointsSorted;
/*     */   private WaypointMenuRenderer waypointMenuRenderer;
/*     */   private final WaypointRenderer waypointRenderer;
/*     */   private IPlayerTrackerSystem<?> minimapSyncedPlayerTrackerSystem;
/*     */   private MinimapWorld mouseBlockWaypointWorld;
/*     */   private MinimapWorld rightClickWaypointWorld;
/*     */   private MinimapElementGraphicsWrapper elementGraphicsWrapper;
/*     */   
/*     */   public SupportXaeroMinimap() {
/*     */     try {
/*  89 */       Class<?> mmClassTest = Class.forName("xaero.pvp.BetterPVP");
/*  90 */       this.modMain = HudMod.INSTANCE;
/*  91 */       WorldMap.LOGGER.info("Xaero's WorldMap Mod: Better PVP found!");
/*  92 */     } catch (ClassNotFoundException e) {
/*     */       
/*     */       try {
/*  95 */         Class<?> mmClassTest = Class.forName("xaero.minimap.XaeroMinimap");
/*  96 */         this.modMain = HudMod.INSTANCE;
/*  97 */         WorldMap.LOGGER.info("Xaero's WorldMap Mod: Xaero's minimap found!");
/*  98 */       } catch (ClassNotFoundException classNotFoundException) {}
/*     */     } 
/*     */     
/* 101 */     if (this.modMain != null) {
/*     */       try {
/* 103 */         this.compatibilityVersion = SupportXaeroWorldmap.WORLDMAP_COMPATIBILITY_VERSION;
/* 104 */       } catch (NoSuchFieldError noSuchFieldError) {}
/*     */       
/* 106 */       if (this.compatibilityVersion < 3)
/* 107 */         throw new RuntimeException("Xaero's Minimap 20.23.0 or newer required!"); 
/* 108 */       this.elementGraphicsWrapper = new MinimapElementGraphicsWrapper();
/*     */     } 
/* 110 */     this.waypointRenderer = WaypointRenderer.Builder.begin().setMinimap(this).setSymbolCreator(WorldMap.waypointSymbolCreator).build();
/*     */   }
/*     */   
/*     */   public void register() {
/* 114 */     WorldMap.playerTrackerSystemManager.register("minimap_synced", getMinimapSyncedPlayerTrackerSystem());
/* 115 */     registerShaderUniforms();
/*     */   }
/*     */   
/*     */   public ArrayList<Waypoint> convertWaypoints(double dimDiv) {
/* 119 */     if (this.waypointSet == null)
/* 120 */       return null; 
/* 121 */     ArrayList<Waypoint> result = new ArrayList<>();
/* 122 */     if (!this.allSets) {
/* 123 */       convertSet(this.waypointSet, result, dimDiv);
/*     */     } else {
/* 125 */       for (WaypointSet set : this.waypointWorld.getIterableWaypointSets())
/* 126 */         convertSet(set, result, dimDiv); 
/*     */     } 
/* 128 */     this.deathpoints = this.modMain.getSettings().getDeathpoints();
/* 129 */     return result;
/*     */   }
/*     */   
/*     */   private void convertSet(WaypointSet set, ArrayList<Waypoint> result, double dimDiv) {
/* 133 */     String setName = set.getName();
/* 134 */     boolean showingDisabled = WorldMap.settings.showDisabledWaypoints;
/* 135 */     for (Waypoint w : set.getWaypoints()) {
/* 136 */       if (!showingDisabled && w.isDisabled())
/*     */         continue; 
/* 138 */       result.add(convertWaypoint(w, true, setName, dimDiv));
/*     */     } 
/*     */   }
/*     */   
/*     */   public Waypoint convertWaypoint(Waypoint w, boolean editable, String setName, double dimDiv) {
/* 143 */     int waypointType = w.getWaypointType();
/* 144 */     Waypoint converted = new Waypoint(w, w.getX(), w.getY(), w.getZ(), w.getName(), w.getSymbol(), ModSettings.COLORS[w.getColor()], waypointType, editable, setName, w.isYIncluded(), dimDiv);
/* 145 */     converted.setDisabled(w.isDisabled());
/* 146 */     converted.setYaw(w.getYaw());
/* 147 */     converted.setRotation(w.isRotation());
/* 148 */     converted.setTemporary(w.isTemporary());
/* 149 */     converted.setGlobal(w.isGlobal());
/* 150 */     return converted;
/*     */   }
/*     */   
/*     */   public void openWaypoint(GuiMap parent, Waypoint waypoint) {
/* 154 */     if (!waypoint.isEditable())
/*     */       return; 
/* 156 */     MinimapSession minimapSession = (MinimapSession)BuiltInHudModules.MINIMAP.getCurrentSession();
/* 157 */     ArrayList<Waypoint> waypointsEdited = new ArrayList<>();
/* 158 */     waypointsEdited.add((Waypoint)waypoint.getOriginal());
/* 159 */     GuiAddWaypoint guiAddWaypoint = new GuiAddWaypoint(this.modMain, minimapSession, (class_437)parent, (class_437)parent, waypointsEdited, this.waypointWorld.getContainer().getRoot().getPath(), this.waypointWorld, waypoint.getSetName(), false);
/* 160 */     class_310.method_1551().method_1507((class_437)guiAddWaypoint);
/*     */   }
/*     */   
/*     */   public void createWaypoint(GuiMap parent, int x, int y, int z, double coordDimensionScale, boolean rightClick) {
/* 164 */     if (this.waypointWorld == null)
/* 165 */       return;  MinimapSession minimapSession = (MinimapSession)BuiltInHudModules.MINIMAP.getCurrentSession();
/* 166 */     MinimapWorld coordSourceWaypointWorld = rightClick ? this.rightClickWaypointWorld : this.mouseBlockWaypointWorld;
/* 167 */     GuiAddWaypoint guiAddWaypoint = new GuiAddWaypoint(this.modMain, minimapSession, (class_437)parent, (class_437)parent, new ArrayList(), this.waypointWorld.getContainer().getRoot().getPath(), this.waypointWorld, this.waypointWorld.getCurrentWaypointSetId(), true, true, x, y, z, coordDimensionScale, coordSourceWaypointWorld);
/* 168 */     class_310.method_1551().method_1507((class_437)guiAddWaypoint);
/*     */   }
/*     */   
/*     */   public void createTempWaypoint(int x, int y, int z, double mapDimensionScale, boolean rightClick) {
/* 172 */     if (this.waypointWorld == null)
/* 173 */       return;  MinimapSession minimapSession = (MinimapSession)BuiltInHudModules.MINIMAP.getCurrentSession();
/* 174 */     MinimapWorld coordSourceWaypointWorld = rightClick ? this.rightClickWaypointWorld : this.mouseBlockWaypointWorld;
/* 175 */     minimapSession.getWaypointSession().getTemporaryHandler()
/* 176 */       .createTemporaryWaypoint(this.waypointWorld, x, y, z, (y != 32767 && coordSourceWaypointWorld == this.waypointWorld), mapDimensionScale);
/* 177 */     requestWaypointsRefresh();
/*     */   }
/*     */   
/*     */   public boolean canTeleport(MinimapWorld world) {
/* 181 */     MinimapSession minimapSession = (MinimapSession)BuiltInHudModules.MINIMAP.getCurrentSession();
/* 182 */     WaypointSession waypointSession = minimapSession.getWaypointSession();
/* 183 */     WaypointTeleport waypointTeleport = waypointSession.getTeleport();
/* 184 */     return (world != null && waypointTeleport.canTeleport(waypointTeleport.isWorldTeleportable(world), world));
/*     */   }
/*     */   
/*     */   public void teleportToWaypoint(class_437 screen, Waypoint w) {
/* 188 */     teleportToWaypoint(screen, w, this.waypointWorld);
/*     */   }
/*     */   
/*     */   public void teleportToWaypoint(class_437 screen, Waypoint w, MinimapWorld world) {
/* 192 */     if (world == null)
/* 193 */       return;  MinimapSession minimapSession = (MinimapSession)BuiltInHudModules.MINIMAP.getCurrentSession();
/* 194 */     WaypointSession waypointSession = minimapSession.getWaypointSession();
/* 195 */     WaypointTeleport waypointTeleport = waypointSession.getTeleport();
/* 196 */     waypointTeleport.teleportToWaypoint((Waypoint)w.getOriginal(), world, screen);
/*     */   }
/*     */   
/*     */   public void disableWaypoint(Waypoint waypoint) {
/* 200 */     ((Waypoint)waypoint.getOriginal()).setDisabled(!((Waypoint)waypoint.getOriginal()).isDisabled());
/*     */     
/* 202 */     MinimapSession minimapSession = (MinimapSession)BuiltInHudModules.MINIMAP.getCurrentSession();
/*     */     try {
/* 204 */       minimapSession.getWorldManagerIO().saveWorld(this.waypointWorld);
/* 205 */     } catch (IOException e) {
/* 206 */       WorldMap.LOGGER.error("suppressed exception", e);
/*     */     } 
/* 208 */     waypoint.setDisabled(((Waypoint)waypoint.getOriginal()).isDisabled());
/* 209 */     waypoint.setTemporary(((Waypoint)waypoint.getOriginal()).isTemporary());
/*     */   }
/*     */   
/*     */   public void deleteWaypoint(Waypoint waypoint) {
/* 213 */     if (!this.allSets) {
/* 214 */       this.waypointSet.remove((Waypoint)waypoint.getOriginal());
/*     */     } else {
/* 216 */       for (WaypointSet set : this.waypointWorld.getIterableWaypointSets())
/* 217 */         set.remove((Waypoint)waypoint.getOriginal()); 
/*     */     } 
/* 219 */     MinimapSession minimapSession = (MinimapSession)BuiltInHudModules.MINIMAP.getCurrentSession();
/*     */     try {
/* 221 */       minimapSession.getWorldManagerIO().saveWorld(this.waypointWorld);
/* 222 */     } catch (IOException e) {
/* 223 */       WorldMap.LOGGER.error("suppressed exception", e);
/*     */     } 
/* 225 */     this.waypoints.remove(waypoint);
/* 226 */     this.waypointsSorted.remove(waypoint);
/* 227 */     this.waypointMenuRenderer.updateFilteredList();
/*     */   }
/*     */   public void checkWaypoints(boolean multiplayer, class_5321<class_1937> dimId, String multiworldId, int width, int height, GuiMap screen, MapWorld mapWorld, class_2378<class_2874> dimensionTypes) {
/*     */     MinimapWorld minimapWorld;
/* 231 */     MinimapSession minimapSession = (MinimapSession)BuiltInHudModules.MINIMAP.getCurrentSession();
/* 232 */     MinimapWorldManager worldManager = minimapSession.getWorldManager();
/* 233 */     MinimapWorldState worldState = minimapSession.getWorldState();
/* 234 */     MinimapWorldStateUpdater worldStateUpdater = minimapSession.getWorldStateUpdater();
/* 235 */     MinimapDimensionHelper dimensionHelper = minimapSession.getDimensionHelper();
/*     */ 
/*     */     
/* 238 */     XaeroPath containerPath = worldState.getAutoRootContainerPath().resolve(dimensionHelper.getDimensionDirectoryName(dimId));
/* 239 */     XaeroPath mapBasedWorldPath = containerPath.resolve(!multiplayer ? "waypoints" : multiworldId);
/* 240 */     this.mapWaypointWorld = worldManager.getWorld(mapBasedWorldPath);
/*     */ 
/*     */     
/* 243 */     if (WorldMap.settings.onlyCurrentMapWaypoints) {
/* 244 */       minimapWorld = this.mapWaypointWorld;
/*     */     } else {
/*     */       
/* 247 */       minimapWorld = worldManager.getCurrentWorld();
/*     */     } 
/* 249 */     class_310 mc = class_310.method_1551();
/* 250 */     if (Misc.hasEffect((class_1657)mc.field_1724, Effects.NO_WAYPOINTS) || Misc.hasEffect((class_1657)mc.field_1724, Effects.NO_WAYPOINTS_HARMFUL))
/* 251 */       minimapWorld = null; 
/* 252 */     boolean shouldRefresh = this.refreshWaypoints;
/* 253 */     if (dimId != this.mapDimId) {
/* 254 */       shouldRefresh = true;
/* 255 */       this.mapDimId = dimId;
/*     */     } 
/* 257 */     if (minimapWorld != this.waypointWorld) {
/* 258 */       this.waypointWorld = minimapWorld;
/*     */       
/* 260 */       screen.closeRightClick();
/* 261 */       if (screen.waypointMenu) {
/* 262 */         screen.method_25423(class_310.method_1551(), width, height);
/*     */       }
/* 264 */       shouldRefresh = true;
/*     */     } 
/* 266 */     WaypointSet checkingSet = (minimapWorld == null) ? null : minimapWorld.getCurrentWaypointSet();
/* 267 */     if (checkingSet != this.waypointSet) {
/* 268 */       this.waypointSet = checkingSet;
/* 269 */       shouldRefresh = true;
/*     */     } 
/* 271 */     if (this.allSets != (this.modMain.getSettings()).renderAllSets) {
/* 272 */       this.allSets = (this.modMain.getSettings()).renderAllSets;
/* 273 */       shouldRefresh = true;
/*     */     } 
/* 275 */     if (shouldRefresh) {
/* 276 */       this.dimDiv = (this.waypointWorld == null) ? 1.0D : getDimensionDivision(mapWorld, dimensionTypes, dimensionHelper, this.waypointWorld.getContainer().getPath(), dimId);
/* 277 */       this.waypoints = convertWaypoints(this.dimDiv);
/* 278 */       if (this.waypoints != null) {
/* 279 */         Collections.sort(this.waypoints);
/* 280 */         this.waypointsSorted = new ArrayList<>();
/*     */         
/* 282 */         ArrayList<KeySortableByOther<Waypoint>> sortingList = new ArrayList<>();
/* 283 */         for (Waypoint w : this.waypoints) {
/* 284 */           sortingList.add(new KeySortableByOther(w, new Comparable[] { w.getComparisonName(), w.getName() }));
/* 285 */         }  Collections.sort(sortingList);
/* 286 */         for (KeySortableByOther<Waypoint> e : sortingList)
/* 287 */           this.waypointsSorted.add((Waypoint)e.getKey()); 
/*     */       } else {
/* 289 */         this.waypointsSorted = null;
/*     */       } 
/* 291 */       this.waypointMenuRenderer.updateFilteredList();
/*     */     } 
/* 293 */     this.refreshWaypoints = false;
/*     */   }
/*     */   
/*     */   private double getDimensionDivision(MapWorld mapWorld, class_2378<class_2874> dimensionTypes, MinimapDimensionHelper dimensionHelper, XaeroPath worldContainerID, class_5321<class_1937> mapDimId) {
/* 297 */     if (worldContainerID == null || (class_310.method_1551()).field_1687 == null)
/* 298 */       return 1.0D; 
/* 299 */     String dimPart = worldContainerID.getLastNode();
/* 300 */     class_5321<class_1937> waypointDimId = dimensionHelper.getDimensionKeyForDirectoryName(dimPart);
/* 301 */     MapDimension waypointMapDimension = mapWorld.getDimension(waypointDimId);
/* 302 */     MapDimension mapDimension = mapWorld.getDimension(mapDimId);
/* 303 */     class_2874 waypointDimType = MapDimension.getDimensionType(waypointMapDimension, waypointDimId, dimensionTypes);
/* 304 */     class_2874 mapDimType = MapDimension.getDimensionType(mapDimension, mapDimId, dimensionTypes);
/* 305 */     double waypointDimScale = (waypointDimType == null) ? 1.0D : waypointDimType.comp_646();
/* 306 */     double mapDimScale = (mapDimType == null) ? 1.0D : mapDimType.comp_646();
/* 307 */     return mapDimScale / waypointDimScale;
/*     */   }
/*     */   
/*     */   public HoveredMapElementHolder<?, ?> renderWaypointsMenu(class_332 guiGraphics, GuiMap gui, double scale, int width, int height, int mouseX, int mouseY, boolean leftMousePressed, boolean leftMouseClicked, HoveredMapElementHolder<?, ?> hovered, class_310 mc) {
/* 311 */     return this.waypointMenuRenderer.renderMenu(guiGraphics, gui, scale, width, height, mouseX, mouseY, leftMousePressed, leftMouseClicked, hovered, mc);
/*     */   }
/*     */   
/*     */   public void requestWaypointsRefresh() {
/* 315 */     this.refreshWaypoints = true;
/*     */   }
/*     */   
/*     */   public class_304 getWaypointKeyBinding() {
/* 319 */     return MinimapKeyMappings.ADD_WAYPOINT;
/*     */   }
/*     */   
/*     */   public class_304 getTempWaypointKeyBinding() {
/* 323 */     return MinimapKeyMappings.TEMPORARY_WAYPOINT;
/*     */   }
/*     */   
/*     */   public class_304 getTempWaypointsMenuKeyBinding() {
/* 327 */     return MinimapKeyMappings.WAYPOINT_MENU;
/*     */   }
/*     */   
/*     */   public void onMapKeyPressed(class_3675.class_307 type, int code, GuiMap screen) {
/* 331 */     class_304 kb = null;
/* 332 */     if (Misc.inputMatchesKeyBinding(type, code, getToggleRadarKey(), 0))
/* 333 */       screen.onRadarButton(screen.getRadarButton()); 
/* 334 */     if (Misc.inputMatchesKeyBinding(type, code, MinimapKeyMappings.TOGGLE_MAP_WAYPOINTS, 0))
/* 335 */       getWaypointMenuRenderer().onRenderWaypointsButton(screen, screen.field_22789, screen.field_22790); 
/* 336 */     if (Misc.inputMatchesKeyBinding(type, code, MinimapKeyMappings.REVERSE_ENTITY_RADAR, 0))
/* 337 */       MinimapKeyMappings.REVERSE_ENTITY_RADAR.method_23481(true); 
/* 338 */     if (Misc.inputMatchesKeyBinding(type, code, MinimapKeyMappings.SWITCH_WAYPOINT_SET, 0))
/* 339 */       kb = MinimapKeyMappings.SWITCH_WAYPOINT_SET; 
/* 340 */     if (Misc.inputMatchesKeyBinding(type, code, MinimapKeyMappings.RENDER_ALL_SETS, 0))
/* 341 */       kb = MinimapKeyMappings.RENDER_ALL_SETS; 
/* 342 */     if (Misc.inputMatchesKeyBinding(type, code, MinimapKeyMappings.WAYPOINT_MENU, 0))
/* 343 */       kb = MinimapKeyMappings.WAYPOINT_MENU; 
/* 344 */     class_304 minimapSettingsKB = (class_304)this.modMain.getSettingsKey();
/* 345 */     if (Misc.inputMatchesKeyBinding(type, code, minimapSettingsKB, 0))
/* 346 */       kb = minimapSettingsKB; 
/* 347 */     class_304 listPlayerAlternative = getMinimapListPlayersAlternative();
/* 348 */     if (listPlayerAlternative != null && Misc.inputMatchesKeyBinding(type, code, listPlayerAlternative, 0))
/* 349 */       listPlayerAlternative.method_23481(true); 
/* 350 */     class_310 mc = class_310.method_1551();
/* 351 */     if (kb != null) {
/* 352 */       if (kb == MinimapKeyMappings.WAYPOINT_MENU) {
/* 353 */         openWaypointsMenu(mc, screen); return;
/*     */       } 
/* 355 */       if (minimapSettingsKB != null && kb == minimapSettingsKB) {
/* 356 */         openSettingsScreen(mc, (class_437)screen, (class_437)screen);
/*     */         return;
/*     */       } 
/* 359 */       handleMinimapKeyBinding(kb, screen);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean onMapKeyReleased(class_3675.class_307 type, int code, GuiMap screen) {
/* 364 */     boolean result = false;
/* 365 */     if (Misc.inputMatchesKeyBinding(type, code, MinimapKeyMappings.REVERSE_ENTITY_RADAR, 0)) {
/* 366 */       MinimapKeyMappings.REVERSE_ENTITY_RADAR.method_23481(false);
/* 367 */       result = true;
/*     */     } 
/* 369 */     class_304 listPlayerAlternative = getMinimapListPlayersAlternative();
/* 370 */     if (listPlayerAlternative != null && Misc.inputMatchesKeyBinding(type, code, listPlayerAlternative, 0)) {
/* 371 */       listPlayerAlternative.method_23481(false);
/* 372 */       result = true;
/*     */     } 
/* 374 */     return result;
/*     */   }
/*     */   
/*     */   public void handleMinimapKeyBinding(class_304 kb, GuiMap screen) {
/* 378 */     KeyMappingController controller = this.modMain.getKeyMappingControllers().getController(kb);
/* 379 */     for (KeyMappingFunction keyFunction : controller) {
/* 380 */       if (keyFunction.isHeld())
/*     */         continue; 
/* 382 */       keyFunction.onPress();
/*     */     } 
/* 384 */     for (KeyMappingFunction keyFunction : controller) {
/* 385 */       if (keyFunction.isHeld())
/*     */         continue; 
/* 387 */       keyFunction.onRelease();
/*     */     } 
/* 389 */     if ((kb == MinimapKeyMappings.SWITCH_WAYPOINT_SET || kb == MinimapKeyMappings.RENDER_ALL_SETS) && screen.waypointMenu)
/* 390 */       screen.method_25423(class_310.method_1551(), screen.field_22789, screen.field_22790); 
/*     */   }
/*     */   
/*     */   public void drawSetChange(class_332 guiGraphics) {
/* 394 */     MinimapSession minimapSession = (MinimapSession)BuiltInHudModules.MINIMAP.getCurrentSession();
/* 395 */     this.modMain.getMinimap().getWaypointMapRenderer().drawSetChange(minimapSession, guiGraphics, class_310.method_1551().method_22683());
/*     */   }
/*     */   
/*     */   public class_437 openSettingsScreen(class_310 mc, class_437 current, class_437 escape) {
/* 399 */     this.modMain.getGuiHelper().openMinimapSettingsFromScreen(current, escape);
/* 400 */     return (class_310.method_1551()).field_1755;
/*     */   }
/*     */   
/*     */   public String getControlsTooltip() {
/* 404 */     return class_1074.method_4662("gui.xaero_box_controls_minimap", new Object[] { Misc.getKeyName(MinimapKeyMappings.ADD_WAYPOINT), Misc.getKeyName(MinimapKeyMappings.TEMPORARY_WAYPOINT), Misc.getKeyName(MinimapKeyMappings.SWITCH_WAYPOINT_SET), Misc.getKeyName(MinimapKeyMappings.RENDER_ALL_SETS), Misc.getKeyName(MinimapKeyMappings.WAYPOINT_MENU) });
/*     */   }
/*     */   
/*     */   public void onMapMouseRelease(class_11909 event) {
/* 408 */     this.waypointMenuRenderer.onMapMouseRelease(event);
/*     */   }
/*     */   
/*     */   public void onMapConstruct() {
/* 412 */     this.waypointMenuRenderer = new WaypointMenuRenderer(new WaypointMenuRenderContext(), new WaypointMenuRenderProvider(this), this.waypointRenderer);
/*     */   }
/*     */   
/*     */   public void onMapInit(GuiMap mapScreen, class_310 mc, int width, int height) {
/* 416 */     this.waypointMenuRenderer.onMapInit(mapScreen, mc, width, height, this.waypointWorld, (IXaeroMinimap)this.modMain, (MinimapSession)BuiltInHudModules.MINIMAP.getCurrentSession());
/*     */   }
/*     */   
/*     */   public ArrayList<Waypoint> getWaypointsSorted() {
/* 420 */     return this.waypointsSorted;
/*     */   }
/*     */   
/*     */   public boolean waypointExists(Waypoint w) {
/* 424 */     return (this.waypoints != null && this.waypoints.contains(w));
/*     */   }
/*     */   
/*     */   public void toggleTemporaryWaypoint(Waypoint waypoint) {
/* 428 */     MinimapSession minimapSession = (MinimapSession)BuiltInHudModules.MINIMAP.getCurrentSession();
/* 429 */     ((Waypoint)waypoint.getOriginal()).setTemporary(!((Waypoint)waypoint.getOriginal()).isTemporary());
/*     */     try {
/* 431 */       minimapSession.getWorldManagerIO().saveWorld(this.waypointWorld);
/* 432 */     } catch (IOException e) {
/* 433 */       WorldMap.LOGGER.error("suppressed exception", e);
/*     */     } 
/* 435 */     waypoint.setDisabled(((Waypoint)waypoint.getOriginal()).isDisabled());
/* 436 */     waypoint.setTemporary(((Waypoint)waypoint.getOriginal()).isTemporary());
/*     */   }
/*     */   
/*     */   public void openWaypointsMenu(class_310 mc, GuiMap screen) {
/* 440 */     MinimapSession minimapSession = (MinimapSession)BuiltInHudModules.MINIMAP.getCurrentSession();
/* 441 */     mc.method_1507((class_437)new GuiWaypoints(this.modMain, minimapSession, (class_437)screen, (class_437)screen));
/*     */   }
/*     */   
/*     */   public boolean screenShouldSkipWorldRender(class_437 currentScreen) {
/* 445 */     return Misc.screenShouldSkipWorldRender((IXaeroMinimap)this.modMain, currentScreen, false);
/*     */   }
/*     */   
/*     */   public boolean hidingWaypointCoordinates() {
/* 449 */     return (this.modMain.getSettings()).hideWaypointCoordinates;
/*     */   }
/*     */   
/*     */   public void shareWaypoint(Waypoint waypoint, GuiMap screen, MinimapWorld world) {
/* 453 */     MinimapSession minimapSession = (MinimapSession)BuiltInHudModules.MINIMAP.getCurrentSession();
/* 454 */     minimapSession.getWaypointSession().getSharing().shareWaypoint((class_437)screen, (Waypoint)waypoint.getOriginal(), world);
/*     */   }
/*     */   
/*     */   public void shareLocation(GuiMap guiMap, int rightClickX, int rightClickY, int rightClickZ) {
/* 458 */     int wpColor = (int)(ModSettings.COLORS.length * Math.random());
/* 459 */     Waypoint minimapLocationWaypoint = new Waypoint(rightClickX, (rightClickY == 32767) ? 0 : rightClickY, rightClickZ, "Shared Location", "S", wpColor, 0, false, (rightClickY != 32767));
/* 460 */     Waypoint locationWaypoint = convertWaypoint(minimapLocationWaypoint, false, "", 1.0D);
/* 461 */     shareWaypoint(locationWaypoint, guiMap, this.rightClickWaypointWorld);
/*     */   }
/*     */   
/*     */   public MinimapWorld getMapWaypointWorld() {
/* 465 */     return this.mapWaypointWorld;
/*     */   }
/*     */   
/*     */   public MinimapWorld getWaypointWorld() {
/* 469 */     return this.waypointWorld;
/*     */   }
/*     */   
/*     */   public double getDimDiv() {
/* 473 */     return this.dimDiv;
/*     */   }
/*     */   
/*     */   public int getArrowColorIndex() {
/* 477 */     return (this.modMain.getSettings()).arrowColour;
/*     */   }
/*     */   
/*     */   public float[] getArrowColor() {
/* 481 */     this.modMain.getSettings(); if ((this.modMain.getSettings()).arrowColour < 0 || (this.modMain.getSettings()).arrowColour >= ModSettings.arrowColours.length) {
/* 482 */       return null;
/*     */     }
/* 484 */     this.modMain.getSettings(); return ModSettings.arrowColours[(this.modMain.getSettings()).arrowColour];
/*     */   }
/*     */   
/*     */   public String getSubWorldNameToRender() {
/* 488 */     if (WorldMap.settings.onlyCurrentMapWaypoints || this.waypointWorld == null)
/* 489 */       return null; 
/* 490 */     if (this.waypointWorld != this.mapWaypointWorld)
/* 491 */       return class_1074.method_4662("gui.xaero_wm_using_custom_subworld", new Object[] { this.waypointWorld.getContainer().getSubName() }); 
/* 492 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerMinimapHighlighters(Object highlighterRegistry) {}
/*     */ 
/*     */   
/*     */   public ArrayList<Waypoint> getWaypoints() {
/* 500 */     return this.waypoints;
/*     */   }
/*     */   
/*     */   public boolean getDeathpoints() {
/* 504 */     return this.deathpoints;
/*     */   }
/*     */   
/*     */   public WaypointRenderer getWaypointRenderer() {
/* 508 */     return this.waypointRenderer;
/*     */   }
/*     */   
/*     */   public WaypointMenuRenderer getWaypointMenuRenderer() {
/* 512 */     return this.waypointMenuRenderer;
/*     */   }
/*     */   
/*     */   public void onClearHighlightHash(int regionX, int regionZ) {
/* 516 */     MinimapSession minimapSession = (MinimapSession)BuiltInHudModules.MINIMAP.getCurrentSession();
/* 517 */     if (minimapSession != null) {
/* 518 */       DimensionHighlighterHandler highlightHandler = minimapSession.getProcessor().getMinimapWriter().getDimensionHighlightHandler();
/* 519 */       if (highlightHandler != null)
/* 520 */         highlightHandler.requestRefresh(regionX, regionZ); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void createRadarRendererWrapper(Object radarRenderer) {
/* 525 */     (new RadarRendererWrapperHelper()).createWrapper((IXaeroMinimap)this.modMain, (RadarRenderer)radarRenderer);
/*     */   }
/*     */   
/*     */   public class_304 getToggleRadarKey() {
/* 529 */     return MinimapKeyMappings.TOGGLE_RADAR;
/*     */   }
/*     */   
/*     */   public void onClearHighlightHashes() {
/* 533 */     MinimapSession minimapSession = (MinimapSession)BuiltInHudModules.MINIMAP.getCurrentSession();
/* 534 */     if (minimapSession != null) {
/* 535 */       DimensionHighlighterHandler highlightHandler = minimapSession.getProcessor().getMinimapWriter().getDimensionHighlightHandler();
/* 536 */       if (highlightHandler != null)
/* 537 */         highlightHandler.requestRefresh(); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public class_304 getToggleAllyPlayersKey() {
/* 542 */     return MinimapKeyMappings.TOGGLE_TRACKED_PLAYERS_MAP;
/*     */   }
/*     */   
/*     */   public class_304 getToggleClaimsKey() {
/* 546 */     return MinimapKeyMappings.TOGGLE_OPAC_CLAIMS;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onSessionFinalized() {
/* 551 */     this.waypointWorld = null;
/* 552 */     this.mapWaypointWorld = null;
/*     */   }
/*     */   
/*     */   public void openWaypointWorldTeleportCommandScreen(class_437 parent, class_437 escape) {
/* 556 */     MinimapSession minimapSession = (MinimapSession)BuiltInHudModules.MINIMAP.getCurrentSession();
/* 557 */     if (minimapSession == null)
/*     */       return; 
/* 559 */     XaeroPath containerId = minimapSession.getWorldState().getAutoRootContainerPath();
/* 560 */     MinimapWorldRootContainer container = minimapSession.getWorldManager().getWorldContainerNullable(containerId).getRoot();
/* 561 */     if (container != null) {
/* 562 */       class_310.method_1551().method_1507((class_437)new GuiWorldTpCommand((IXaeroMinimap)this.modMain, parent, escape, container));
/*     */     }
/*     */   }
/*     */   
/*     */   public class_304 getMinimapListPlayersAlternative() {
/* 567 */     return MinimapKeyMappings.ALTERNATIVE_LIST_PLAYERS;
/*     */   }
/*     */   
/*     */   public int getCaveStart(int defaultWorldMapStart, boolean isMapScreen) {
/* 571 */     if (!this.modMain.getSettings().getMinimap() || isFairPlay())
/* 572 */       return defaultWorldMapStart; 
/* 573 */     if (Misc.hasEffect(Effects.NO_CAVE_MAPS) || 
/* 574 */       Misc.hasEffect(Effects.NO_CAVE_MAPS_HARMFUL) || this.modMain
/* 575 */       .getSettings().caveMapsDisabled())
/*     */     {
/* 577 */       return isMapScreen ? defaultWorldMapStart : Integer.MAX_VALUE; } 
/* 578 */     int usedCaving = getUsedCaving();
/* 579 */     if (usedCaving == Integer.MAX_VALUE)
/* 580 */       return WorldMap.settings.caveModeStart; 
/* 581 */     return usedCaving;
/*     */   }
/*     */   
/*     */   public int getUsedCaving() {
/* 585 */     MinimapSession minimapSession = (MinimapSession)BuiltInHudModules.MINIMAP.getCurrentSession();
/* 586 */     if (minimapSession != null)
/* 587 */       return minimapSession.getProcessor().getMinimapWriter().getLoadedCaving(); 
/* 588 */     return Integer.MAX_VALUE;
/*     */   }
/*     */   
/*     */   public boolean isFairPlay() {
/* 592 */     return this.modMain.isFairPlay();
/*     */   }
/*     */   
/*     */   public IPlayerTrackerSystem<?> getMinimapSyncedPlayerTrackerSystem() {
/* 596 */     if (this.minimapSyncedPlayerTrackerSystem == null)
/* 597 */       this.minimapSyncedPlayerTrackerSystem = (IPlayerTrackerSystem<?>)new MinimapSyncedPlayerTrackerSystem(this); 
/* 598 */     return this.minimapSyncedPlayerTrackerSystem;
/*     */   }
/*     */   
/*     */   public void onBlockHover() {
/* 602 */     this.mouseBlockWaypointWorld = this.mapWaypointWorld;
/*     */   }
/*     */   
/*     */   public void onRightClick() {
/* 606 */     this.rightClickWaypointWorld = this.mouseBlockWaypointWorld;
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerShaderUniforms() {
/* 611 */     CustomUniforms.register((CustomUniform)new CustomUniformWrapper(BuiltInCustomUniforms.BRIGHTNESS));
/* 612 */     CustomUniforms.register((CustomUniform)new CustomUniformWrapper(BuiltInCustomUniforms.WITH_LIGHT));
/*     */   }
/*     */   
/*     */   public MinimapElementGraphicsWrapper wrapElementGraphics(MapElementGraphics graphics) {
/* 616 */     return this.elementGraphicsWrapper.setGraphics(graphics);
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\mods\SupportXaeroMinimap.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */