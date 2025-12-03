/*      */ package xaero.map.gui;
/*      */ import com.mojang.blaze3d.buffers.GpuBufferSlice;
/*      */ import com.mojang.blaze3d.systems.RenderSystem;
/*      */ import com.mojang.blaze3d.textures.GpuTexture;
/*      */ import java.io.IOException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.List;
/*      */ import net.minecraft.class_10366;
/*      */ import net.minecraft.class_1044;
/*      */ import net.minecraft.class_1074;
/*      */ import net.minecraft.class_10799;
/*      */ import net.minecraft.class_11905;
/*      */ import net.minecraft.class_11908;
/*      */ import net.minecraft.class_11909;
/*      */ import net.minecraft.class_124;
/*      */ import net.minecraft.class_1297;
/*      */ import net.minecraft.class_1657;
/*      */ import net.minecraft.class_1921;
/*      */ import net.minecraft.class_1937;
/*      */ import net.minecraft.class_1959;
/*      */ import net.minecraft.class_2378;
/*      */ import net.minecraft.class_2561;
/*      */ import net.minecraft.class_276;
/*      */ import net.minecraft.class_287;
/*      */ import net.minecraft.class_2874;
/*      */ import net.minecraft.class_2960;
/*      */ import net.minecraft.class_304;
/*      */ import net.minecraft.class_310;
/*      */ import net.minecraft.class_332;
/*      */ import net.minecraft.class_339;
/*      */ import net.minecraft.class_364;
/*      */ import net.minecraft.class_3675;
/*      */ import net.minecraft.class_4185;
/*      */ import net.minecraft.class_437;
/*      */ import net.minecraft.class_4587;
/*      */ import net.minecraft.class_4588;
/*      */ import net.minecraft.class_4597;
/*      */ import net.minecraft.class_5321;
/*      */ import net.minecraft.class_6599;
/*      */ import org.joml.Matrix4f;
/*      */ import org.joml.Vector3fc;
/*      */ import xaero.map.MapProcessor;
/*      */ import xaero.map.WorldMap;
/*      */ import xaero.map.animation.Animation;
/*      */ import xaero.map.animation.SlowingAnimation;
/*      */ import xaero.map.controls.ControlsHandler;
/*      */ import xaero.map.controls.ControlsRegister;
/*      */ import xaero.map.core.IWorldMapMinecraftClient;
/*      */ import xaero.map.effects.Effects;
/*      */ import xaero.map.element.HoveredMapElementHolder;
/*      */ import xaero.map.graphics.CustomRenderTypes;
/*      */ import xaero.map.graphics.GpuTextureAndView;
/*      */ import xaero.map.graphics.ImprovedFramebuffer;
/*      */ import xaero.map.graphics.MapRenderHelper;
/*      */ import xaero.map.graphics.OpenGlHelper;
/*      */ import xaero.map.graphics.TextureUtils;
/*      */ import xaero.map.graphics.renderer.multitexture.MultiTextureRenderTypeRenderer;
/*      */ import xaero.map.graphics.renderer.multitexture.MultiTextureRenderTypeRendererProvider;
/*      */ import xaero.map.graphics.shader.WorldMapShaderHelper;
/*      */ import xaero.map.gui.dropdown.DropDownWidget;
/*      */ import xaero.map.gui.dropdown.rightclick.GuiRightClickMenu;
/*      */ import xaero.map.gui.dropdown.rightclick.RightClickOption;
/*      */ import xaero.map.misc.Misc;
/*      */ import xaero.map.misc.OptimizedMath;
/*      */ import xaero.map.mods.SupportMods;
/*      */ import xaero.map.mods.gui.Waypoint;
/*      */ import xaero.map.radar.tracker.PlayerTeleporter;
/*      */ import xaero.map.radar.tracker.PlayerTrackerMapElement;
/*      */ import xaero.map.region.BranchLeveledRegion;
/*      */ import xaero.map.region.LayeredRegionManager;
/*      */ import xaero.map.region.LeveledRegion;
/*      */ import xaero.map.region.MapBlock;
/*      */ import xaero.map.region.MapRegion;
/*      */ import xaero.map.region.MapTile;
/*      */ import xaero.map.region.MapTileChunk;
/*      */ import xaero.map.region.Overlay;
/*      */ import xaero.map.region.texture.RegionTexture;
/*      */ import xaero.map.render.util.ImmediateRenderUtil;
/*      */ import xaero.map.settings.ModOptions;
/*      */ import xaero.map.settings.ModSettings;
/*      */ import xaero.map.teleport.MapTeleporter;
/*      */ import xaero.map.world.MapDimension;
/*      */ import xaero.map.world.MapWorld;
/*      */ 
/*      */ public class GuiMap extends ScreenBase implements IRightClickableElement {
/*   86 */   private static final class_2561 FULL_RELOAD_IN_PROGRESS = (class_2561)class_2561.method_43471("gui.xaero_full_reload_in_progress");
/*   87 */   private static final class_2561 UNKNOWN_DIMENSION_TYPE1 = (class_2561)class_2561.method_43471("gui.xaero_unknown_dimension_type1");
/*   88 */   private static final class_2561 UNKNOWN_DIMENSION_TYPE2 = (class_2561)class_2561.method_43471("gui.xaero_unknown_dimension_type2");
/*      */   private static final double ZOOM_STEP = 1.2D;
/*      */   private static final int white = -1;
/*      */   private static final int black = -16777216;
/*   92 */   private static int lastAmountOfRegionsViewed = 1;
/*      */   private long loadingAnimationStart;
/*      */   private class_1297 player;
/*   95 */   private double screenScale = 0.0D;
/*      */   
/*   97 */   private int mouseDownPosX = -1;
/*   98 */   private int mouseDownPosY = -1;
/*   99 */   private double mouseDownCameraX = -1.0D;
/*  100 */   private double mouseDownCameraZ = -1.0D;
/*  101 */   private int mouseCheckPosX = -1;
/*  102 */   private int mouseCheckPosY = -1;
/*  103 */   private long mouseCheckTimeNano = -1L;
/*  104 */   private int prevMouseCheckPosX = -1;
/*  105 */   private int prevMouseCheckPosY = -1;
/*  106 */   private long prevMouseCheckTimeNano = -1L;
/*  107 */   private double cameraX = 0.0D;
/*  108 */   private double cameraZ = 0.0D;
/*      */   private boolean shouldResetCameraPos;
/*  110 */   private int[] cameraDestination = null;
/*  111 */   private SlowingAnimation cameraDestinationAnimX = null;
/*  112 */   private SlowingAnimation cameraDestinationAnimZ = null;
/*      */   private double scale;
/*      */   private double userScale;
/*  115 */   private static double destScale = 3.0D;
/*      */   
/*      */   private boolean pauseZoomKeys;
/*      */   
/*      */   private int lastZoomMethod;
/*      */   private double prevPlayerDimDiv;
/*  121 */   private HoveredMapElementHolder<?, ?> viewed = null;
/*      */   private boolean viewedInList;
/*  123 */   private HoveredMapElementHolder<?, ?> viewedOnMousePress = null;
/*      */   private boolean overWaypointsMenu;
/*      */   private Animation zoomAnim;
/*      */   public boolean waypointMenu = false;
/*      */   private boolean overPlayersMenu;
/*      */   public boolean playersMenu = false;
/*  129 */   private static ImprovedFramebuffer primaryScaleFBO = null;
/*  130 */   private float[] colourBuffer = new float[4];
/*  131 */   private ArrayList<MapRegion> regionBuffer = new ArrayList<>();
/*  132 */   private ArrayList<BranchLeveledRegion> branchRegionBuffer = new ArrayList<>();
/*      */   
/*      */   private boolean prevWaitingForBranchCache = true;
/*      */   
/*      */   private boolean prevLoadingLeaves = true;
/*      */   private class_5321<class_1937> lastNonNullViewedDimensionId;
/*      */   private class_5321<class_1937> lastViewedDimensionId;
/*      */   private String lastViewedMultiworldId;
/*      */   private int mouseBlockPosX;
/*      */   private int mouseBlockPosY;
/*      */   private int mouseBlockPosZ;
/*      */   private class_5321<class_1937> mouseBlockDim;
/*  144 */   private double mouseBlockCoordinateScale = 1.0D;
/*      */   
/*      */   private long lastStartTime;
/*      */   
/*      */   private final GuiMapSwitching mapSwitchingGui;
/*      */   
/*      */   private MapMouseButtonPress leftMouseButton;
/*      */   
/*      */   private MapMouseButtonPress rightMouseButton;
/*      */   private MapProcessor mapProcessor;
/*      */   private MapDimension futureDimension;
/*      */   public boolean noUploadingLimits;
/*  156 */   private boolean[] waitingForBranchCache = new boolean[1];
/*      */   
/*      */   private class_4185 settingsButton;
/*      */   
/*      */   private class_4185 exportButton;
/*      */   
/*      */   private class_4185 waypointsButton;
/*      */   
/*      */   private class_4185 playersButton;
/*      */   private class_4185 radarButton;
/*      */   private class_4185 claimsButton;
/*      */   private class_4185 zoomInButton;
/*      */   private class_4185 zoomOutButton;
/*      */   private class_4185 keybindingsButton;
/*      */   private class_4185 caveModeButton;
/*      */   private class_4185 dimensionToggleButton;
/*      */   private class_4185 buttonPressed;
/*      */   private GuiRightClickMenu rightClickMenu;
/*      */   private int rightClickX;
/*      */   private int rightClickY;
/*      */   private int rightClickZ;
/*      */   private class_5321<class_1937> rightClickDim;
/*      */   private double rightClickCoordinateScale;
/*      */   private boolean lastFrameRenderedRootTextures;
/*      */   private MapTileSelection mapTileSelection;
/*      */   private boolean tabPressed;
/*      */   private GuiCaveModeOptions caveModeOptions;
/*  183 */   private static final Matrix4f identityMatrix = new Matrix4f();
/*      */   static {
/*  185 */     identityMatrix.identity();
/*      */   }
/*      */   
/*      */   public GuiMap(class_437 parent, class_437 escape, MapProcessor mapProcessor, class_1297 player) {
/*  189 */     super(parent, escape, (class_2561)class_2561.method_43471("gui.xaero_world_map_screen"));
/*  190 */     this.player = player;
/*  191 */     this.shouldResetCameraPos = true;
/*  192 */     this.leftMouseButton = new MapMouseButtonPress();
/*  193 */     this.rightMouseButton = new MapMouseButtonPress();
/*  194 */     this.mapSwitchingGui = new GuiMapSwitching(mapProcessor);
/*      */     
/*  196 */     this.userScale = destScale * (WorldMap.settings.openMapAnimation ? 1.5F : 1.0F);
/*  197 */     this.zoomAnim = (Animation)new SlowingAnimation(this.userScale, destScale, 0.88D, destScale * 0.001D);
/*  198 */     this.mapProcessor = mapProcessor;
/*  199 */     this.caveModeOptions = new GuiCaveModeOptions();
/*  200 */     if (SupportMods.minimap())
/*  201 */       SupportMods.xaeroMinimap.onMapConstruct(); 
/*      */   }
/*      */   
/*      */   private double getScaleMultiplier(int screenShortSide) {
/*  205 */     return (screenShortSide <= 1080) ? 1.0D : (screenShortSide / 1080.0D);
/*      */   }
/*      */ 
/*      */   
/*      */   public <T extends class_364 & net.minecraft.class_4068 & net.minecraft.class_6379> T method_37063(T guiEventListener) {
/*  210 */     return (T)super.method_37063((class_364)guiEventListener);
/*      */   }
/*      */   
/*      */   public <T extends class_364 & net.minecraft.class_4068 & net.minecraft.class_6379> T addButton(T guiEventListener) {
/*  214 */     return method_37063(guiEventListener);
/*      */   }
/*      */   
/*      */   public <T extends class_364 & net.minecraft.class_6379> T method_25429(T guiEventListener) {
/*  218 */     return super.method_25429(guiEventListener);
/*      */   }
/*      */   
/*      */   public void method_25426() {
/*      */     CursorBox waypointsTooltip, claimsTooltip;
/*  223 */     super.method_25426();
/*  224 */     MapWorld mapWorld = this.mapProcessor.getMapWorld();
/*  225 */     this.futureDimension = (mapWorld == null || mapWorld.getFutureDimensionId() == null) ? null : mapWorld.getFutureDimension();
/*  226 */     this.tabPressed = false;
/*  227 */     boolean waypointsEnabled = (SupportMods.minimap() && WorldMap.settings.waypoints);
/*  228 */     this.waypointMenu = (this.waypointMenu && waypointsEnabled);
/*  229 */     this.mapSwitchingGui.init(this, this.field_22787, this.field_22789, this.field_22790);
/*  230 */     CursorBox caveModeButtonTooltip = new CursorBox((class_2561)class_2561.method_43471(WorldMap.settings.isCaveMapsAllowed() ? "gui.xaero_box_cave_mode" : "gui.xaero_box_cave_mode_not_allowed"));
/*  231 */     this.caveModeButton = new GuiTexturedButton(0, this.field_22790 - 40, 20, 20, 229, 64, 16, 16, WorldMap.guiTextures, this::onCaveModeButton, () -> caveModeButtonTooltip, 256, 256);
/*  232 */     this.caveModeButton.field_22763 = WorldMap.settings.isCaveMapsAllowed();
/*  233 */     addButton(this.caveModeButton);
/*  234 */     this.caveModeOptions.onInit(this, this.mapProcessor);
/*  235 */     CursorBox dimensionToggleButtonTooltip = new CursorBox((class_2561)class_2561.method_43469("gui.xaero_dimension_toggle_button", new Object[] { Misc.getKeyName(ControlsRegister.keyToggleDimension) }));
/*  236 */     this.dimensionToggleButton = new GuiTexturedButton(0, this.field_22790 - 60, 20, 20, 197, 80, 16, 16, WorldMap.guiTextures, this::onDimensionToggleButton, () -> dimensionToggleButtonTooltip, 256, 256);
/*  237 */     addButton(this.dimensionToggleButton);
/*  238 */     this.loadingAnimationStart = System.currentTimeMillis();
/*  239 */     if (SupportMods.minimap()) {
/*  240 */       SupportMods.xaeroMinimap.requestWaypointsRefresh();
/*      */     }
/*  242 */     this.screenScale = class_310.method_1551().method_22683().method_4495();
/*  243 */     this.pauseZoomKeys = false;
/*  244 */     CursorBox openSettingsTooltip = new CursorBox((class_2561)class_2561.method_43469("gui.xaero_box_open_settings", new Object[] { Misc.getKeyName(ControlsRegister.keyOpenSettings) }));
/*  245 */     addButton(this.settingsButton = new GuiTexturedButton(0, 0, 30, 30, 113, 0, 20, 20, WorldMap.guiTextures, this::onSettingsButton, () -> openSettingsTooltip, 256, 256));
/*      */     
/*  247 */     if (waypointsEnabled) {
/*  248 */       waypointsTooltip = new CursorBox(this.waypointMenu ? "gui.xaero_box_close_waypoints" : "gui.xaero_box_open_waypoints");
/*      */     } else {
/*  250 */       waypointsTooltip = new CursorBox(!SupportMods.minimap() ? "gui.xaero_box_waypoints_minimap_required" : "gui.xaero_box_waypoints_disabled");
/*  251 */     }  CursorBox playersTooltip = new CursorBox(this.playersMenu ? "gui.xaero_box_close_players" : "gui.xaero_box_open_players");
/*      */     
/*  253 */     if (SupportMods.pac()) {
/*  254 */       claimsTooltip = new CursorBox((class_2561)class_2561.method_43469(WorldMap.settings.displayClaims ? "gui.xaero_box_pac_displaying_claims" : "gui.xaero_box_pac_not_displaying_claims", new Object[] { class_2561.method_43470(Misc.getKeyName(SupportMods.xaeroPac.getPacClaimsKeyBinding())).method_27692(class_124.field_1077) }));
/*      */     } else {
/*  256 */       claimsTooltip = new CursorBox((class_2561)class_2561.method_43471("gui.xaero_box_claims_pac_required"));
/*      */     } 
/*  258 */     addButton(this.waypointsButton = new GuiTexturedButton(this.field_22789 - 20, this.field_22790 - 20, 20, 20, 213, 0, 16, 16, WorldMap.guiTextures, this::onWaypointsButton, () -> waypointsTooltip, 256, 256));
/*  259 */     this.waypointsButton.field_22763 = waypointsEnabled;
/*  260 */     addButton(this.playersButton = new GuiTexturedButton(this.field_22789 - 20, this.field_22790 - 40, 20, 20, 197, 32, 16, 16, WorldMap.guiTextures, this::onPlayersButton, () -> playersTooltip, 256, 256));
/*  261 */     CursorBox radarButtonTooltip = new CursorBox((class_2561)class_2561.method_43469(WorldMap.settings.minimapRadar ? "gui.xaero_box_minimap_radar" : "gui.xaero_box_no_minimap_radar", new Object[] { class_2561.method_43470(Misc.getKeyName(SupportMods.minimap() ? SupportMods.xaeroMinimap.getToggleRadarKey() : null)).method_27692(class_124.field_1077) }));
/*  262 */     addButton(this.radarButton = new GuiTexturedButton(this.field_22789 - 20, this.field_22790 - 60, 20, 20, WorldMap.settings.minimapRadar ? 213 : 229, 32, 16, 16, WorldMap.guiTextures, this::onRadarButton, () -> radarButtonTooltip, 256, 256));
/*  263 */     (getRadarButton()).field_22763 = SupportMods.minimap();
/*  264 */     addButton(this.claimsButton = new GuiTexturedButton(this.field_22789 - 20, this.field_22790 - 80, 20, 20, WorldMap.settings.displayClaims ? 197 : 213, 64, 16, 16, WorldMap.guiTextures, this::onClaimsButton, () -> claimsTooltip, 256, 256));
/*  265 */     this.claimsButton.field_22763 = SupportMods.pac();
/*  266 */     CursorBox exportButtonTooltip = new CursorBox("gui.xaero_box_export");
/*  267 */     addButton(this.exportButton = new GuiTexturedButton(this.field_22789 - 20, this.field_22790 - 100, 20, 20, 133, 0, 16, 16, WorldMap.guiTextures, this::onExportButton, () -> exportButtonTooltip, 256, 256));
/*  268 */     CursorBox controlsButtonTooltip = new CursorBox(class_1074.method_4662("gui.xaero_box_controls", new Object[] {
/*  269 */             (SupportMods.minimap() ? SupportMods.xaeroMinimap.getControlsTooltip() : "") + (SupportMods.minimap() ? SupportMods.xaeroMinimap.getControlsTooltip() : "")
/*      */           }));
/*  271 */     controlsButtonTooltip.setStartWidth(400);
/*  272 */     addButton(this.keybindingsButton = new GuiTexturedButton(this.field_22789 - 20, this.field_22790 - 120, 20, 20, 197, 0, 16, 16, WorldMap.guiTextures, this::onKeybindingsButton, () -> controlsButtonTooltip, 256, 256));
/*      */ 
/*      */     
/*  275 */     CursorBox zoomInButtonTooltip = new CursorBox((class_2561)class_2561.method_43469("gui.xaero_box_zoom_in", new Object[] { class_2561.method_43470(Misc.getKeyName(ControlsRegister.keyZoomIn)).method_27692(class_124.field_1077) }));
/*  276 */     this.zoomInButton = new GuiTexturedButton(this.field_22789 - 20, this.field_22790 - 160, 20, 20, 165, 0, 16, 16, WorldMap.guiTextures, this::onZoomInButton, () -> zoomInButtonTooltip, 256, 256);
/*  277 */     CursorBox zoomOutButtonTooltip = new CursorBox((class_2561)class_2561.method_43469("gui.xaero_box_zoom_out", new Object[] { class_2561.method_43470(Misc.getKeyName(ControlsRegister.keyZoomOut)).method_27692(class_124.field_1077) }));
/*  278 */     this.zoomOutButton = new GuiTexturedButton(this.field_22789 - 20, this.field_22790 - 140, 20, 20, 181, 0, 16, 16, WorldMap.guiTextures, this::onZoomOutButton, () -> zoomOutButtonTooltip, 256, 256);
/*  279 */     if (WorldMap.settings.zoomButtons) {
/*  280 */       addButton(this.zoomOutButton);
/*  281 */       addButton(this.zoomInButton);
/*      */     } 
/*  283 */     if (this.rightClickMenu != null) {
/*  284 */       this.rightClickMenu.setClosed(true);
/*  285 */       this.rightClickMenu = null;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  293 */     if (SupportMods.minimap() && this.waypointMenu) {
/*  294 */       SupportMods.xaeroMinimap.onMapInit(this, this.field_22787, this.field_22789, this.field_22790);
/*      */     }
/*  296 */     if (this.playersMenu) {
/*  297 */       WorldMap.trackedPlayerMenuRenderer.onMapInit(this, this.field_22787, this.field_22789, this.field_22790);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void method_56131() {}
/*      */ 
/*      */   
/*      */   private void onCaveModeButton(class_4185 b) {
/*  307 */     this.caveModeOptions.toggle(this);
/*  308 */     method_25395((class_364)this.caveModeButton);
/*      */   }
/*      */   
/*      */   private void onDimensionToggleButton(class_4185 b) {
/*  312 */     this.mapProcessor.getMapWorld().toggleDimension(!hasShiftDown());
/*  313 */     String messageType = (this.mapProcessor.getMapWorld().getCustomDimensionId() == null) ? "gui.xaero_switched_to_current_dimension" : "gui.xaero_switched_to_dimension";
/*  314 */     class_2960 messageDimLoc = (this.mapProcessor.getMapWorld().getFutureDimensionId() == null) ? null : this.mapProcessor.getMapWorld().getFutureDimensionId().method_29177();
/*  315 */     this.mapProcessor.getMessageBox().addMessage((class_2561)class_2561.method_43469(messageType, new Object[] { messageDimLoc.toString() }));
/*  316 */     method_25423(this.field_22787, this.field_22789, this.field_22790);
/*  317 */     method_25395((class_364)this.dimensionToggleButton);
/*      */   }
/*      */   
/*      */   private void onSettingsButton(class_4185 b) {
/*  321 */     this.field_22787.method_1507(new GuiWorldMapSettings(this, this));
/*      */   }
/*      */   
/*      */   private void onKeybindingsButton(class_4185 b) {
/*  325 */     this.field_22787.method_1507((class_437)new class_6599(this, this.field_22787.field_1690));
/*      */   }
/*      */   
/*      */   private void onExportButton(class_4185 b) {
/*  329 */     this.field_22787.method_1507(new ExportScreen(this, this, this.mapProcessor, this.mapTileSelection));
/*      */   }
/*      */   
/*      */   private void toggleWaypointMenu() {
/*  333 */     if (this.playersMenu)
/*  334 */       togglePlayerMenu(); 
/*  335 */     this.waypointMenu = !this.waypointMenu;
/*  336 */     if (!this.waypointMenu) {
/*  337 */       SupportMods.xaeroMinimap.getWaypointMenuRenderer().onMenuClosed();
/*  338 */       unfocusAll();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void togglePlayerMenu() {
/*  343 */     if (this.waypointMenu)
/*  344 */       toggleWaypointMenu(); 
/*  345 */     this.playersMenu = !this.playersMenu;
/*  346 */     if (!this.playersMenu) {
/*  347 */       WorldMap.trackedPlayerMenuRenderer.onMenuClosed();
/*  348 */       unfocusAll();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void onPlayersButton(class_4185 b) {
/*  353 */     togglePlayerMenu();
/*  354 */     method_25423(this.field_22787, this.field_22789, this.field_22790);
/*  355 */     method_25395((class_364)this.playersButton);
/*      */   }
/*      */   
/*      */   private void onClaimsButton(class_4185 b) {
/*  359 */     WorldMap.settings.setOptionValue(ModOptions.PAC_CLAIMS, Boolean.valueOf(!((Boolean)WorldMap.settings.getOptionValue(ModOptions.PAC_CLAIMS)).booleanValue()));
/*      */     try {
/*  361 */       WorldMap.settings.saveSettings();
/*  362 */     } catch (IOException e) {
/*  363 */       WorldMap.LOGGER.error("suppressed exception", e);
/*      */     } 
/*  365 */     method_25423(this.field_22787, this.field_22789, this.field_22790);
/*  366 */     method_25395((class_364)this.claimsButton);
/*      */   }
/*      */   
/*      */   private void onWaypointsButton(class_4185 b) {
/*  370 */     toggleWaypointMenu();
/*  371 */     method_25423(this.field_22787, this.field_22789, this.field_22790);
/*  372 */     method_25395((class_364)this.waypointsButton);
/*      */   }
/*      */   
/*      */   public void onRadarButton(class_4185 b) {
/*  376 */     WorldMap.settings.minimapRadar = !WorldMap.settings.minimapRadar;
/*      */     try {
/*  378 */       WorldMap.settings.saveSettings();
/*  379 */     } catch (IOException e) {
/*  380 */       WorldMap.LOGGER.error("suppressed exception", e);
/*      */     } 
/*  382 */     method_25423(this.field_22787, this.field_22789, this.field_22790);
/*  383 */     method_25395((class_364)this.radarButton);
/*      */   }
/*      */   
/*      */   private void onZoomInButton(class_4185 b) {
/*  387 */     this.buttonPressed = (this.buttonPressed == null) ? b : null;
/*      */   }
/*      */   
/*      */   private void onZoomOutButton(class_4185 b) {
/*  391 */     this.buttonPressed = (this.buttonPressed == null) ? b : null;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean method_25402(class_11909 event, boolean doubleClick) {
/*  396 */     boolean toReturn = super.method_25402(event, doubleClick);
/*  397 */     if (!toReturn) {
/*  398 */       if (event.method_74245() == 0) {
/*  399 */         this.leftMouseButton.isDown = this.leftMouseButton.clicked = true;
/*  400 */         this.leftMouseButton.pressedAtX = (int)Misc.getMouseX(this.field_22787, SupportMods.vivecraft);
/*  401 */         this.leftMouseButton.pressedAtY = (int)Misc.getMouseY(this.field_22787, SupportMods.vivecraft);
/*  402 */       } else if (event.method_74245() == 1) {
/*  403 */         this.rightMouseButton.isDown = this.rightMouseButton.clicked = true;
/*  404 */         this.rightMouseButton.pressedAtX = (int)Misc.getMouseX(this.field_22787, SupportMods.vivecraft);
/*  405 */         this.rightMouseButton.pressedAtY = (int)Misc.getMouseY(this.field_22787, SupportMods.vivecraft);
/*  406 */         this.viewedOnMousePress = this.viewed;
/*  407 */         this.rightClickX = this.mouseBlockPosX;
/*  408 */         this.rightClickY = this.mouseBlockPosY;
/*  409 */         this.rightClickZ = this.mouseBlockPosZ;
/*  410 */         this.rightClickDim = this.mouseBlockDim;
/*  411 */         this.rightClickCoordinateScale = this.mouseBlockCoordinateScale;
/*  412 */         if (SupportMods.minimap())
/*  413 */           SupportMods.xaeroMinimap.onRightClick(); 
/*  414 */         if (this.viewedOnMousePress == null || !this.viewedOnMousePress.isRightClickValid())
/*  415 */           this.mapTileSelection = new MapTileSelection(this.rightClickX >> 4, this.rightClickZ >> 4); 
/*      */       } else {
/*  417 */         toReturn = onInputPress(class_3675.class_307.field_1672, event.method_74245());
/*  418 */       }  if (!toReturn && this.caveModeOptions.isEnabled()) {
/*  419 */         this.caveModeOptions.toggle(this);
/*  420 */         toReturn = true;
/*      */       } 
/*      */     } 
/*  423 */     return toReturn;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean method_25406(class_11909 event) {
/*  428 */     this.buttonPressed = null;
/*  429 */     int mouseX = (int)Misc.getMouseX(this.field_22787, SupportMods.vivecraft);
/*  430 */     int mouseY = (int)Misc.getMouseY(this.field_22787, SupportMods.vivecraft);
/*  431 */     if (this.leftMouseButton.isDown && event.method_74245() == 0) {
/*  432 */       this.leftMouseButton.isDown = false;
/*  433 */       if (Math.abs(this.leftMouseButton.pressedAtX - mouseX) < 5 && Math.abs(this.leftMouseButton.pressedAtY - mouseY) < 5)
/*  434 */         mapClicked(0, this.leftMouseButton.pressedAtX, this.leftMouseButton.pressedAtY); 
/*  435 */       this.leftMouseButton.pressedAtX = -1;
/*  436 */       this.leftMouseButton.pressedAtY = -1;
/*      */     } 
/*  438 */     if (this.rightMouseButton.isDown && event.method_74245() == 1) {
/*  439 */       this.rightMouseButton.isDown = false;
/*  440 */       mapClicked(1, mouseX, mouseY);
/*  441 */       this.rightMouseButton.pressedAtX = -1;
/*  442 */       this.rightMouseButton.pressedAtY = -1;
/*      */     } 
/*  444 */     if (this.waypointMenu) {
/*  445 */       SupportMods.xaeroMinimap.onMapMouseRelease(event);
/*      */     }
/*  447 */     if (this.playersMenu) {
/*  448 */       WorldMap.trackedPlayerMenuRenderer.onMapMouseRelease(event);
/*      */     }
/*  450 */     boolean toReturn = super.method_25406(event);
/*  451 */     if (!toReturn)
/*  452 */       toReturn = onInputRelease(class_3675.class_307.field_1672, event.method_74245()); 
/*  453 */     return toReturn;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean method_25401(double par1, double par2, double g, double wheel) {
/*  458 */     int direction = (wheel > 0.0D) ? 1 : -1;
/*  459 */     if (this.waypointMenu && this.overWaypointsMenu) {
/*  460 */       SupportMods.xaeroMinimap.getWaypointMenuRenderer().mouseScrolled(direction);
/*  461 */     } else if (this.playersMenu && this.overPlayersMenu) {
/*  462 */       WorldMap.trackedPlayerMenuRenderer.mouseScrolled(direction);
/*      */     } else {
/*  464 */       changeZoom(wheel, 0);
/*  465 */     }  return super.method_25401(par1, par2, g, wheel);
/*      */   }
/*      */   
/*      */   private void changeZoom(double factor, int zoomMethod) {
/*  469 */     closeDropdowns();
/*  470 */     this.lastZoomMethod = zoomMethod;
/*  471 */     this.cameraDestinationAnimX = null;
/*  472 */     this.cameraDestinationAnimZ = null;
/*  473 */     if (hasControlDown()) {
/*  474 */       double destScaleBefore = destScale;
/*  475 */       if (destScale >= 1.0D) {
/*  476 */         if (factor > 0.0D) {
/*  477 */           destScale = Math.ceil(destScale);
/*      */         } else {
/*  479 */           destScale = Math.floor(destScale);
/*  480 */         }  if (destScaleBefore == destScale)
/*  481 */           destScale += (factor > 0.0D) ? 1.0D : -1.0D; 
/*  482 */         if (destScale == 0.0D)
/*  483 */           destScale = 0.5D; 
/*      */       } else {
/*  485 */         double reversedScale = 1.0D / destScale;
/*  486 */         double log2 = Math.log(reversedScale) / Math.log(2.0D);
/*  487 */         if (factor > 0.0D) {
/*  488 */           log2 = Math.floor(log2);
/*      */         } else {
/*  490 */           log2 = Math.ceil(log2);
/*  491 */         }  destScale = 1.0D / Math.pow(2.0D, log2);
/*  492 */         if (destScaleBefore == destScale)
/*  493 */           destScale = 1.0D / Math.pow(2.0D, log2 + ((factor > 0.0D) ? -1 : true)); 
/*      */       } 
/*      */     } else {
/*  496 */       destScale *= Math.pow(1.2D, factor);
/*  497 */     }  if (destScale < 0.0625D) {
/*  498 */       destScale = 0.0625D;
/*  499 */     } else if (destScale > 50.0D) {
/*  500 */       destScale = 50.0D;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void method_25432() {
/*  505 */     super.method_25432();
/*  506 */     this.leftMouseButton.isDown = false;
/*  507 */     this.rightMouseButton.isDown = false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void method_25394(class_332 guiGraphics, int scaledMouseX, int scaledMouseY, float partialTicks) {
/*  513 */     OpenGlHelper.clearErrors(false, "GuiMap.render");
/*      */     
/*  515 */     ImmediateRenderUtil.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
/*  516 */     class_310 mc = class_310.method_1551();
/*  517 */     long startTime = System.currentTimeMillis();
/*  518 */     MapDimension currentFutureDim = !this.mapProcessor.isMapWorldUsable() ? null : this.mapProcessor.getMapWorld().getFutureDimension();
/*  519 */     if (currentFutureDim != this.futureDimension)
/*      */     {
/*  521 */       method_25423(this.field_22787, this.field_22789, this.field_22790);
/*      */     }
/*  523 */     double playerDimDiv = this.prevPlayerDimDiv;
/*  524 */     synchronized (this.mapProcessor.renderThreadPauseSync) {
/*  525 */       if (!this.mapProcessor.isRenderingPaused()) {
/*  526 */         class_2378<class_2874> dimTypes = this.mapProcessor.getWorldDimensionTypeRegistry();
/*  527 */         if (dimTypes != null)
/*  528 */           playerDimDiv = this.mapProcessor.getMapWorld().getCurrentDimension().calculateDimDiv(dimTypes, this.player.method_73183().method_8597()); 
/*      */       } 
/*      */     } 
/*  531 */     double scaledPlayerX = this.player.method_23317() / playerDimDiv;
/*  532 */     double scaledPlayerZ = this.player.method_23321() / playerDimDiv;
/*  533 */     if (this.shouldResetCameraPos) {
/*  534 */       this.cameraX = (float)scaledPlayerX;
/*  535 */       this.cameraZ = (float)scaledPlayerZ;
/*  536 */       this.shouldResetCameraPos = false;
/*  537 */     } else if (this.prevPlayerDimDiv != 0.0D && playerDimDiv != this.prevPlayerDimDiv) {
/*  538 */       double oldScaledPlayerX = this.player.method_23317() / this.prevPlayerDimDiv;
/*  539 */       double oldScaledPlayerZ = this.player.method_23321() / this.prevPlayerDimDiv;
/*  540 */       this.cameraX = this.cameraX - oldScaledPlayerX + scaledPlayerX;
/*  541 */       this.cameraZ = this.cameraZ - oldScaledPlayerZ + scaledPlayerZ;
/*  542 */       this.cameraDestinationAnimX = null;
/*  543 */       this.cameraDestinationAnimZ = null;
/*  544 */       this.cameraDestination = null;
/*      */     } 
/*  546 */     this.prevPlayerDimDiv = playerDimDiv;
/*  547 */     double cameraXBefore = this.cameraX;
/*  548 */     double cameraZBefore = this.cameraZ;
/*  549 */     double scaleBefore = this.scale;
/*  550 */     this.mapSwitchingGui.preMapRender(this, this.field_22787, this.field_22789, this.field_22790);
/*      */     
/*  552 */     long passed = (this.lastStartTime == 0L) ? 16L : (startTime - this.lastStartTime);
/*  553 */     double passedScrolls = ((float)passed / 64.0F);
/*  554 */     int direction = (this.buttonPressed == this.zoomInButton || ControlsHandler.isDown(ControlsRegister.keyZoomIn)) ? 1 : ((this.buttonPressed == this.zoomOutButton || ControlsHandler.isDown(ControlsRegister.keyZoomOut)) ? -1 : 0);
/*  555 */     if (direction != 0) {
/*  556 */       boolean ctrlKey = hasControlDown();
/*  557 */       if (!ctrlKey || !this.pauseZoomKeys) {
/*  558 */         changeZoom(direction * passedScrolls, (this.buttonPressed == this.zoomInButton || this.buttonPressed == this.zoomOutButton) ? 2 : 1);
/*  559 */         if (ctrlKey)
/*  560 */           this.pauseZoomKeys = true; 
/*      */       } 
/*      */     } else {
/*  563 */       this.pauseZoomKeys = false;
/*      */     } 
/*  565 */     this.lastStartTime = startTime;
/*  566 */     if (this.cameraDestination != null) {
/*  567 */       this.cameraDestinationAnimX = new SlowingAnimation(this.cameraX, this.cameraDestination[0], 0.9D, 0.01D);
/*  568 */       this.cameraDestinationAnimZ = new SlowingAnimation(this.cameraZ, this.cameraDestination[1], 0.9D, 0.01D);
/*  569 */       this.cameraDestination = null;
/*      */     } 
/*  571 */     if (this.cameraDestinationAnimX != null) {
/*  572 */       this.cameraX = this.cameraDestinationAnimX.getCurrent();
/*  573 */       if (this.cameraX == this.cameraDestinationAnimX.getDestination())
/*  574 */         this.cameraDestinationAnimX = null; 
/*      */     } 
/*  576 */     if (this.cameraDestinationAnimZ != null) {
/*  577 */       this.cameraZ = this.cameraDestinationAnimZ.getCurrent();
/*  578 */       if (this.cameraZ == this.cameraDestinationAnimZ.getDestination())
/*  579 */         this.cameraDestinationAnimZ = null; 
/*      */     } 
/*  581 */     this.lastViewedDimensionId = null;
/*  582 */     this.lastViewedMultiworldId = null;
/*  583 */     this.mouseBlockPosY = 32767;
/*  584 */     boolean discoveredForHighlights = false;
/*  585 */     synchronized (this.mapProcessor.renderThreadPauseSync) {
/*  586 */       if (!this.mapProcessor.isRenderingPaused())
/*  587 */       { boolean mapLoaded = (this.mapProcessor.getCurrentWorldId() != null && !this.mapProcessor.isWaitingForWorldUpdate() && this.mapProcessor.getMapSaveLoad().isRegionDetectionComplete());
/*  588 */         boolean noWorldMapEffect = (mc.field_1724 == null || Misc.hasEffect((class_1657)mc.field_1724, Effects.NO_WORLD_MAP) || Misc.hasEffect((class_1657)mc.field_1724, Effects.NO_WORLD_MAP_HARMFUL));
/*  589 */         boolean allowedBasedOnItem = (ModSettings.mapItem == null || (mc.field_1724 != null && Misc.hasItem((class_1657)mc.field_1724, ModSettings.mapItem)));
/*  590 */         boolean isLocked = this.mapProcessor.isCurrentMapLocked();
/*  591 */         if (mapLoaded && !noWorldMapEffect && allowedBasedOnItem && !isLocked) {
/*  592 */           double fboScale, secondaryOffsetX, secondaryOffsetY; GpuBufferSlice projectionBU = RenderSystem.getProjectionMatrixBuffer();
/*  593 */           class_10366 projectionTypeBU = RenderSystem.getProjectionType();
/*  594 */           Misc.minecraftOrtho(this.field_22787, false);
/*  595 */           RenderSystem.getModelViewStack().pushMatrix();
/*  596 */           RenderSystem.getModelViewStack().identity();
/*  597 */           RenderSystem.getModelViewStack().translate(0.0F, 0.0F, -11000.0F);
/*  598 */           if (SupportMods.vivecraft)
/*  599 */             TextureUtils.clearRenderTarget(this.field_22787.method_1522(), -16777216); 
/*  600 */           this.mapProcessor.updateCaveStart();
/*  601 */           this.lastNonNullViewedDimensionId = this.lastViewedDimensionId = this.mapProcessor.getMapWorld().getCurrentDimension().getDimId();
/*  602 */           this.lastViewedMultiworldId = this.mapProcessor.getMapWorld().getCurrentDimension().getCurrentMultiworld();
/*  603 */           if (SupportMods.minimap()) {
/*  604 */             SupportMods.xaeroMinimap.checkWaypoints(this.mapProcessor.getMapWorld().isMultiplayer(), this.lastViewedDimensionId, this.lastViewedMultiworldId, this.field_22789, this.field_22790, this, this.mapProcessor.getMapWorld(), this.mapProcessor.getWorldDimensionTypeRegistry());
/*      */           }
/*  606 */           int mouseXPos = (int)Misc.getMouseX(mc, false);
/*  607 */           int mouseYPos = (int)Misc.getMouseY(mc, false);
/*  608 */           double scaleMultiplier = getScaleMultiplier(Math.min(mc.method_22683().method_4489(), mc.method_22683().method_4506()));
/*  609 */           this.scale = this.userScale * scaleMultiplier;
/*  610 */           if (this.mouseCheckPosX == -1 || System.nanoTime() - this.mouseCheckTimeNano > 30000000L) {
/*  611 */             this.prevMouseCheckPosX = this.mouseCheckPosX;
/*  612 */             this.prevMouseCheckPosY = this.mouseCheckPosY;
/*  613 */             this.prevMouseCheckTimeNano = this.mouseCheckTimeNano;
/*  614 */             this.mouseCheckPosX = mouseXPos;
/*  615 */             this.mouseCheckPosY = mouseYPos;
/*  616 */             this.mouseCheckTimeNano = System.nanoTime();
/*      */           } 
/*  618 */           if (!this.leftMouseButton.isDown) {
/*  619 */             if (this.mouseDownPosX != -1) {
/*  620 */               this.mouseDownPosX = -1;
/*  621 */               this.mouseDownPosY = -1;
/*  622 */               if (this.prevMouseCheckTimeNano != -1L) {
/*  623 */                 double downTime = 0.0D;
/*  624 */                 int draggedX = 0;
/*  625 */                 int draggedY = 0;
/*  626 */                 downTime = (System.nanoTime() - this.prevMouseCheckTimeNano);
/*  627 */                 draggedX = mouseXPos - this.prevMouseCheckPosX;
/*  628 */                 draggedY = mouseYPos - this.prevMouseCheckPosY;
/*  629 */                 double frameTime60FPS = 1.6666666666666666E7D;
/*  630 */                 double speedScale = downTime / frameTime60FPS;
/*  631 */                 double speed_x = -draggedX / this.scale / speedScale;
/*  632 */                 double speed_z = -draggedY / this.scale / speedScale;
/*  633 */                 double speed = Math.sqrt(speed_x * speed_x + speed_z * speed_z);
/*  634 */                 if (speed > 0.0D) {
/*  635 */                   double cos = speed_x / speed;
/*  636 */                   double sin = speed_z / speed;
/*  637 */                   double maxSpeed = 500.0D / this.userScale;
/*  638 */                   speed = (Math.abs(speed) > maxSpeed) ? Math.copySign(maxSpeed, speed) : speed;
/*      */ 
/*      */ 
/*      */                   
/*  642 */                   double speed_factor = 0.9D;
/*  643 */                   double ln = Math.log(speed_factor);
/*  644 */                   double move_distance = -speed / ln;
/*  645 */                   double moveX = cos * move_distance;
/*  646 */                   double moveZ = sin * move_distance;
/*  647 */                   this.cameraDestinationAnimX = new SlowingAnimation(this.cameraX, this.cameraX + moveX, 0.9D, 0.01D);
/*  648 */                   this.cameraDestinationAnimZ = new SlowingAnimation(this.cameraZ, this.cameraZ + moveZ, 0.9D, 0.01D);
/*      */                 } 
/*      */               } 
/*      */             } 
/*  652 */           } else if (this.viewed == null || !this.viewedInList || this.mouseDownPosX != -1) {
/*  653 */             if (this.mouseDownPosX != -1) {
/*  654 */               this.cameraX = (this.mouseDownPosX - mouseXPos) / this.scale + this.mouseDownCameraX;
/*  655 */               this.cameraZ = (this.mouseDownPosY - mouseYPos) / this.scale + this.mouseDownCameraZ;
/*      */             } else {
/*  657 */               this.mouseDownPosX = mouseXPos;
/*  658 */               this.mouseDownPosY = mouseYPos;
/*  659 */               this.mouseDownCameraX = this.cameraX;
/*  660 */               this.mouseDownCameraZ = this.cameraZ;
/*  661 */               this.cameraDestinationAnimX = null;
/*  662 */               this.cameraDestinationAnimZ = null;
/*      */             } 
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  674 */           int mouseFromCentreX = mouseXPos - mc.method_22683().method_4489() / 2;
/*  675 */           int mouseFromCentreY = mouseYPos - mc.method_22683().method_4506() / 2;
/*  676 */           double oldMousePosX = mouseFromCentreX / this.scale + this.cameraX;
/*  677 */           double oldMousePosZ = mouseFromCentreY / this.scale + this.cameraZ;
/*      */           
/*  679 */           double preScale = this.scale;
/*  680 */           if (destScale != this.userScale) {
/*      */             
/*  682 */             if (this.zoomAnim != null) {
/*  683 */               this.userScale = this.zoomAnim.getCurrent();
/*  684 */               this.scale = this.userScale * scaleMultiplier;
/*      */             } 
/*  686 */             if (this.zoomAnim == null || Misc.round(this.zoomAnim.getDestination(), 4) != Misc.round(destScale, 4)) {
/*  687 */               this.zoomAnim = (Animation)new SinAnimation(this.userScale, destScale, 100L);
/*      */             }
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  694 */           if (this.scale > preScale && this.lastZoomMethod != 2) {
/*      */ 
/*      */             
/*  697 */             this.cameraX = oldMousePosX - mouseFromCentreX / this.scale;
/*  698 */             this.cameraZ = oldMousePosZ - mouseFromCentreY / this.scale;
/*      */           } 
/*      */ 
/*      */           
/*  702 */           int textureLevel = 0;
/*  703 */           if (this.scale >= 1.0D) {
/*  704 */             fboScale = Math.max(1.0D, Math.floor(this.scale));
/*      */           } else {
/*  706 */             fboScale = this.scale;
/*  707 */           }  if (this.userScale < 1.0D) {
/*  708 */             double reversedScale = 1.0D / this.userScale;
/*  709 */             double log2 = Math.floor(Math.log(reversedScale) / Math.log(2.0D));
/*  710 */             textureLevel = Math.min((int)log2, 3);
/*      */           } 
/*  712 */           (this.mapProcessor.getMapSaveLoad()).mainTextureLevel = textureLevel;
/*  713 */           int leveledRegionShift = 9 + textureLevel;
/*  714 */           double secondaryScale = this.scale / fboScale;
/*      */           
/*  716 */           class_4587 matrixStack = WorldMap.worldMapClientOnly.getMapScreenPoseStack();
/*  717 */           matrixStack.method_22903();
/*  718 */           matrixStack.method_46416(0.0F, 0.0F, 100.0F);
/*      */           
/*  720 */           double mousePosX = mouseFromCentreX / this.scale + this.cameraX;
/*  721 */           double mousePosZ = mouseFromCentreY / this.scale + this.cameraZ;
/*      */           
/*  723 */           this.mouseBlockPosX = (int)Math.floor(mousePosX);
/*  724 */           this.mouseBlockPosZ = (int)Math.floor(mousePosZ);
/*  725 */           this.mouseBlockDim = this.mapProcessor.getMapWorld().getCurrentDimension().getDimId();
/*  726 */           this.mouseBlockCoordinateScale = getCurrentMapCoordinateScale();
/*  727 */           if (SupportMods.minimap())
/*  728 */             SupportMods.xaeroMinimap.onBlockHover(); 
/*  729 */           int mouseRegX = this.mouseBlockPosX >> leveledRegionShift;
/*  730 */           int mouseRegZ = this.mouseBlockPosZ >> leveledRegionShift;
/*  731 */           int renderedCaveLayer = this.mapProcessor.getCurrentCaveLayer();
/*  732 */           LeveledRegion<?> reg = this.mapProcessor.getLeveledRegion(renderedCaveLayer, mouseRegX, mouseRegZ, textureLevel);
/*  733 */           int maxRegBlockCoord = (1 << leveledRegionShift) - 1;
/*  734 */           int mouseRegPixelX = (this.mouseBlockPosX & maxRegBlockCoord) >> textureLevel;
/*  735 */           int mouseRegPixelZ = (this.mouseBlockPosZ & maxRegBlockCoord) >> textureLevel;
/*      */           
/*  737 */           this.mouseBlockPosX = (mouseRegX << leveledRegionShift) + (mouseRegPixelX << textureLevel);
/*  738 */           this.mouseBlockPosZ = (mouseRegZ << leveledRegionShift) + (mouseRegPixelZ << textureLevel);
/*      */           
/*  740 */           if (this.mapTileSelection != null && this.rightClickMenu == null) {
/*  741 */             this.mapTileSelection.setEnd(this.mouseBlockPosX >> 4, this.mouseBlockPosZ >> 4);
/*      */           }
/*  743 */           MapRegion leafRegion = this.mapProcessor.getLeafMapRegion(renderedCaveLayer, this.mouseBlockPosX >> 9, this.mouseBlockPosZ >> 9, false);
/*  744 */           MapTileChunk chunk = (leafRegion == null) ? null : leafRegion.getChunk(this.mouseBlockPosX >> 6 & 0x7, this.mouseBlockPosZ >> 6 & 0x7);
/*  745 */           int debugTextureX = this.mouseBlockPosX >> leveledRegionShift - 3 & 0x7;
/*  746 */           int debugTextureY = this.mouseBlockPosZ >> leveledRegionShift - 3 & 0x7;
/*      */           
/*  748 */           RegionTexture tex = (reg != null && reg.hasTextures()) ? reg.getTexture(debugTextureX, debugTextureY) : null;
/*  749 */           if (WorldMap.settings.debug) {
/*  750 */             if (reg != null) {
/*  751 */               List<String> debugLines = new ArrayList<>();
/*  752 */               if (tex != null) {
/*  753 */                 tex.addDebugLines(debugLines);
/*      */                 
/*  755 */                 MapTile mouseTile = (chunk == null) ? null : chunk.getTile(this.mouseBlockPosX >> 4 & 0x3, this.mouseBlockPosZ >> 4 & 0x3);
/*  756 */                 if (mouseTile != null) {
/*  757 */                   MapBlock block = mouseTile.getBlock(this.mouseBlockPosX & 0xF, this.mouseBlockPosZ & 0xF);
/*  758 */                   if (block != null) {
/*  759 */                     guiGraphics.method_25300(mc.field_1772, block.toRenderString(leafRegion.getBiomeRegistry()), this.field_22789 / 2, 22, -1);
/*  760 */                     if (block.getNumberOfOverlays() != 0)
/*  761 */                       for (int n = 0; n < block.getOverlays().size(); n++) {
/*  762 */                         guiGraphics.method_25300(mc.field_1772, ((Overlay)block.getOverlays().get(n)).toRenderString(), this.field_22789 / 2, 32 + n * 10, -1);
/*      */                       } 
/*      */                   } 
/*      */                 } 
/*      */               } 
/*  767 */               debugLines.add("");
/*  768 */               debugLines.add(reg.toString());
/*  769 */               reg.addDebugLines(debugLines, this.mapProcessor, debugTextureX, debugTextureY);
/*      */               
/*  771 */               for (int m = 0; m < debugLines.size(); m++)
/*  772 */                 guiGraphics.method_25303(mc.field_1772, debugLines.get(m), 5, 15 + 10 * m, -1); 
/*      */             } 
/*  774 */             class_2874 dimType = this.mapProcessor.getMapWorld().getCurrentDimension().getDimensionType(this.mapProcessor.getWorldDimensionTypeRegistry());
/*  775 */             class_2960 dimTypeId = this.mapProcessor.getMapWorld().getCurrentDimension().getDimensionTypeId();
/*  776 */             guiGraphics.method_25303(mc.field_1772, "MultiWorld ID: " + this.mapProcessor.getMapWorld().getCurrentMultiworld() + " Dim Type: " + String.valueOf((dimType == null) ? "unknown" : dimTypeId), 5, 265, -1);
/*  777 */             LayeredRegionManager regions = this.mapProcessor.getMapWorld().getCurrentDimension().getLayeredMapRegions();
/*  778 */             guiGraphics.method_25303(mc.field_1772, String.format("regions: %d loaded: %d processed: %d viewed: %d benchmarks %s", new Object[] { Integer.valueOf(regions.size()), Integer.valueOf(regions.loadedCount()), Integer.valueOf(this.mapProcessor.getProcessedCount()), Integer.valueOf(lastAmountOfRegionsViewed), WorldMap.textureUploadBenchmark.getTotalsString() }), 5, 275, -1);
/*  779 */             guiGraphics.method_25303(mc.field_1772, String.format("toLoad: %d toSave: %d tile pool: %d overlays: %d toLoadBranchCache: %d buffers: %d", new Object[] { Integer.valueOf(this.mapProcessor.getMapSaveLoad().getSizeOfToLoad()), Integer.valueOf(this.mapProcessor.getMapSaveLoad().getToSave().size()), Integer.valueOf(this.mapProcessor.getTilePool().size()), Integer.valueOf(this.mapProcessor.getOverlayManager().getNumberOfUniqueOverlays()), Integer.valueOf(this.mapProcessor.getMapSaveLoad().getSizeOfToLoadBranchCache()), Integer.valueOf(WorldMap.textureDirectBufferPool.size()) }), 5, 285, -1);
/*  780 */             long i = Runtime.getRuntime().maxMemory();
/*  781 */             long j = Runtime.getRuntime().totalMemory();
/*  782 */             long k = Runtime.getRuntime().freeMemory();
/*  783 */             long l = j - k;
/*  784 */             int debugFPS = ((IWorldMapMinecraftClient)mc).getXaeroWorldMap_fps();
/*      */             
/*  786 */             guiGraphics.method_25303(mc.field_1772, String.format("FPS: %d", new Object[] { Integer.valueOf(debugFPS) }), 5, 295, -1);
/*  787 */             guiGraphics.method_25303(mc.field_1772, String.format("Mem: % 2d%% %03d/%03dMB", new Object[] { Long.valueOf(l * 100L / i), Long.valueOf(bytesToMb(l)), Long.valueOf(bytesToMb(i)) }), 5, 315, -1);
/*  788 */             guiGraphics.method_25303(mc.field_1772, String.format("Allocated: % 2d%% %03dMB", new Object[] { Long.valueOf(j * 100L / i), Long.valueOf(bytesToMb(j)) }), 5, 325, -1);
/*  789 */             guiGraphics.method_25303(mc.field_1772, String.format("Available VRAM: %dMB", new Object[] { Integer.valueOf(this.mapProcessor.getMapLimiter().getAvailableVRAM() / 1024) }), 5, 335, -1);
/*      */           } 
/*      */           
/*  792 */           int pixelInsideTexX = mouseRegPixelX & 0x3F;
/*  793 */           int pixelInsideTexZ = mouseRegPixelZ & 0x3F;
/*  794 */           boolean hasAmbiguousHeight = false;
/*  795 */           int mouseBlockBottomY = 32767;
/*  796 */           int mouseBlockTopY = 32767;
/*  797 */           class_5321<class_1959> pointedAtBiome = null;
/*  798 */           if (tex != null) {
/*  799 */             mouseBlockBottomY = this.mouseBlockPosY = tex.getHeight(pixelInsideTexX, pixelInsideTexZ);
/*  800 */             mouseBlockTopY = tex.getTopHeight(pixelInsideTexX, pixelInsideTexZ);
/*  801 */             hasAmbiguousHeight = (this.mouseBlockPosY != mouseBlockTopY);
/*  802 */             pointedAtBiome = tex.getBiome(pixelInsideTexX, pixelInsideTexZ);
/*      */           } 
/*  804 */           if (hasAmbiguousHeight) {
/*  805 */             if (mouseBlockTopY != 32767) {
/*  806 */               this.mouseBlockPosY = mouseBlockTopY;
/*  807 */             } else if (WorldMap.settings.detectAmbiguousY) {
/*  808 */               this.mouseBlockPosY = 32767;
/*      */             } 
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*  814 */           if (primaryScaleFBO == null || primaryScaleFBO.field_1482 != mc.method_22683().method_4489() || primaryScaleFBO.field_1481 != mc.method_22683().method_4506())
/*  815 */             primaryScaleFBO = new ImprovedFramebuffer(mc.method_22683().method_4489(), mc.method_22683().method_4506(), true); 
/*  816 */           if (primaryScaleFBO.method_30277() == null || primaryScaleFBO.method_30278() == null) {
/*  817 */             matrixStack.method_22909();
/*  818 */             RenderSystem.setProjectionMatrix(projectionBU, projectionTypeBU);
/*  819 */             RenderSystem.getModelViewStack().popMatrix();
/*      */             
/*      */             return;
/*      */           } 
/*  823 */           primaryScaleFBO.bindAsMainTarget(false);
/*  824 */           TextureUtils.clearRenderTarget((class_276)primaryScaleFBO, -16777216, 1.0F);
/*  825 */           matrixStack.method_22905((float)(1.0D / this.screenScale), (float)(1.0D / this.screenScale), 1.0F);
/*  826 */           matrixStack.method_46416((mc.method_22683().method_4489() / 2), (mc.method_22683().method_4506() / 2), 0.0F);
/*  827 */           matrixStack.method_22903();
/*  828 */           int flooredCameraX = (int)Math.floor(this.cameraX);
/*  829 */           int flooredCameraZ = (int)Math.floor(this.cameraZ);
/*  830 */           double primaryOffsetX = 0.0D;
/*  831 */           double primaryOffsetY = 0.0D;
/*      */ 
/*      */           
/*  834 */           if (fboScale < 1.0D) {
/*  835 */             double pixelInBlocks = 1.0D / fboScale;
/*  836 */             int xInFullPixels = (int)Math.floor(this.cameraX / pixelInBlocks);
/*  837 */             int zInFullPixels = (int)Math.floor(this.cameraZ / pixelInBlocks);
/*  838 */             double fboOffsetX = xInFullPixels * pixelInBlocks;
/*  839 */             double fboOffsetZ = zInFullPixels * pixelInBlocks;
/*  840 */             flooredCameraX = (int)Math.floor(fboOffsetX);
/*  841 */             flooredCameraZ = (int)Math.floor(fboOffsetZ);
/*  842 */             primaryOffsetX = fboOffsetX - flooredCameraX;
/*  843 */             primaryOffsetY = fboOffsetZ - flooredCameraZ;
/*  844 */             secondaryOffsetX = (this.cameraX - fboOffsetX) * fboScale;
/*  845 */             secondaryOffsetY = (this.cameraZ - fboOffsetZ) * fboScale;
/*      */           } else {
/*  847 */             secondaryOffsetX = (this.cameraX - flooredCameraX) * fboScale;
/*  848 */             secondaryOffsetY = (this.cameraZ - flooredCameraZ) * fboScale;
/*      */             
/*  850 */             if (secondaryOffsetX >= 1.0D) {
/*  851 */               int offset = (int)secondaryOffsetX;
/*  852 */               matrixStack.method_46416(-offset, 0.0F, 0.0F);
/*  853 */               secondaryOffsetX -= offset;
/*      */             } 
/*  855 */             if (secondaryOffsetY >= 1.0D) {
/*  856 */               int offset = (int)secondaryOffsetY;
/*  857 */               matrixStack.method_46416(0.0F, offset, 0.0F);
/*  858 */               secondaryOffsetY -= offset;
/*      */             } 
/*      */           } 
/*  861 */           matrixStack.method_22905((float)fboScale, (float)-fboScale, 1.0F);
/*  862 */           matrixStack.method_22904(-primaryOffsetX, -primaryOffsetY, 0.0D);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  869 */           double leftBorder = this.cameraX - (mc.method_22683().method_4489() / 2) / this.scale;
/*  870 */           double rightBorder = leftBorder + mc.method_22683().method_4489() / this.scale;
/*  871 */           double topBorder = this.cameraZ - (mc.method_22683().method_4506() / 2) / this.scale;
/*  872 */           double bottomBorder = topBorder + mc.method_22683().method_4506() / this.scale;
/*  873 */           int minRegX = (int)Math.floor(leftBorder) >> leveledRegionShift;
/*  874 */           int maxRegX = (int)Math.floor(rightBorder) >> leveledRegionShift;
/*  875 */           int minRegZ = (int)Math.floor(topBorder) >> leveledRegionShift;
/*  876 */           int maxRegZ = (int)Math.floor(bottomBorder) >> leveledRegionShift;
/*      */           
/*  878 */           int blockToTextureConversion = 6 + textureLevel;
/*  879 */           int minTextureX = (int)Math.floor(leftBorder) >> blockToTextureConversion;
/*  880 */           int maxTextureX = (int)Math.floor(rightBorder) >> blockToTextureConversion;
/*  881 */           int minTextureZ = (int)Math.floor(topBorder) >> blockToTextureConversion;
/*  882 */           int maxTextureZ = (int)Math.floor(bottomBorder) >> blockToTextureConversion;
/*      */ 
/*      */           
/*  885 */           int minLeafRegX = minTextureX << blockToTextureConversion >> 9;
/*  886 */           int maxLeafRegX = (maxTextureX + 1 << blockToTextureConversion) - 1 >> 9;
/*  887 */           int minLeafRegZ = minTextureZ << blockToTextureConversion >> 9;
/*  888 */           int maxLeafRegZ = (maxTextureZ + 1 << blockToTextureConversion) - 1 >> 9;
/*      */           
/*  890 */           lastAmountOfRegionsViewed = (maxRegX - minRegX + 1) * (maxRegZ - minRegZ + 1);
/*  891 */           if (this.mapProcessor.getMapLimiter().getMostRegionsAtATime() < lastAmountOfRegionsViewed)
/*  892 */             this.mapProcessor.getMapLimiter().setMostRegionsAtATime(lastAmountOfRegionsViewed); 
/*  893 */           this.regionBuffer.clear();
/*  894 */           this.branchRegionBuffer.clear();
/*  895 */           float brightness = this.mapProcessor.getBrightness();
/*  896 */           int globalRegionCacheHashCode = WorldMap.settings.getRegionCacheHashCode();
/*  897 */           int globalCaveStart = this.mapProcessor.getMapWorld().getCurrentDimension().getLayeredMapRegions().getLayer(renderedCaveLayer).getCaveStart();
/*  898 */           int globalCaveDepth = WorldMap.settings.caveModeDepth;
/*  899 */           boolean reloadEverything = WorldMap.settings.reloadEverything;
/*  900 */           int globalReloadVersion = WorldMap.settings.reloadVersion;
/*  901 */           int globalVersion = this.mapProcessor.getGlobalVersion();
/*  902 */           boolean prevWaitingForBranchCache = this.prevWaitingForBranchCache;
/*  903 */           this.waitingForBranchCache[0] = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  912 */           Matrix4f matrix = matrixStack.method_23760().method_23761();
/*  913 */           class_4597.class_4598 renderTypeBuffers = this.mapProcessor.getCvc().getRenderTypeBuffers();
/*  914 */           MultiTextureRenderTypeRendererProvider rendererProvider = this.mapProcessor.getMultiTextureRenderTypeRenderers();
/*  915 */           MultiTextureRenderTypeRenderer withLightRenderer = rendererProvider.getRenderer(MultiTextureRenderTypeRendererProvider::defaultTextureBind, CustomRenderTypes.MAP);
/*  916 */           MultiTextureRenderTypeRenderer noLightRenderer = rendererProvider.getRenderer(MultiTextureRenderTypeRendererProvider::defaultTextureBind, CustomRenderTypes.MAP);
/*  917 */           class_4588 overlayBuffer = renderTypeBuffers.method_73477(CustomRenderTypes.MAP_COLOR_OVERLAY);
/*  918 */           LeveledRegion.setComparison(this.mouseBlockPosX >> leveledRegionShift, this.mouseBlockPosZ >> leveledRegionShift, textureLevel, this.mouseBlockPosX >> 9, this.mouseBlockPosZ >> 9);
/*  919 */           LeveledRegion<?> lastUpdatedRootLeveledRegion = null;
/*  920 */           boolean cacheOnlyMode = this.mapProcessor.getMapWorld().isCacheOnlyMode();
/*  921 */           boolean frameRenderedRootTextures = false;
/*  922 */           boolean loadingLeaves = false;
/*  923 */           for (int leveledRegX = minRegX; leveledRegX <= maxRegX; leveledRegX++) {
/*  924 */             for (int leveledRegZ = minRegZ; leveledRegZ <= maxRegZ; leveledRegZ++) {
/*  925 */               int leveledSideInRegions = 1 << textureLevel;
/*  926 */               int leveledSideInBlocks = leveledSideInRegions * 512;
/*  927 */               int leafRegionMinX = leveledRegX * leveledSideInRegions;
/*  928 */               int leafRegionMinZ = leveledRegZ * leveledSideInRegions;
/*  929 */               LeveledRegion<?> leveledRegion = null;
/*  930 */               for (int leafX = 0; leafX < leveledSideInRegions; leafX++) {
/*  931 */                 for (int leafZ = 0; leafZ < leveledSideInRegions; leafZ++) {
/*  932 */                   int regX = leafRegionMinX + leafX;
/*  933 */                   if (regX >= minLeafRegX && regX <= maxLeafRegX) {
/*      */                     
/*  935 */                     int regZ = leafRegionMinZ + leafZ;
/*  936 */                     if (regZ >= minLeafRegZ && regZ <= maxLeafRegZ) {
/*      */                       
/*  938 */                       MapRegion region = this.mapProcessor.getLeafMapRegion(renderedCaveLayer, regX, regZ, false);
/*  939 */                       if (region == null)
/*  940 */                         region = this.mapProcessor.getLeafMapRegion(renderedCaveLayer, regX, regZ, this.mapProcessor.regionExists(renderedCaveLayer, regX, regZ)); 
/*  941 */                       if (region != null) {
/*      */                         
/*  943 */                         if (leveledRegion == null) {
/*  944 */                           leveledRegion = this.mapProcessor.getLeveledRegion(renderedCaveLayer, leveledRegX, leveledRegZ, textureLevel);
/*      */                         }
/*  946 */                         if (!prevWaitingForBranchCache) {
/*  947 */                           synchronized (region) {
/*  948 */                             if (textureLevel != 0 && region.getLoadState() == 0 && region.loadingNeededForBranchLevel != 0 && region.loadingNeededForBranchLevel != textureLevel) {
/*  949 */                               region.loadingNeededForBranchLevel = 0;
/*  950 */                               region.getParent().setShouldCheckForUpdatesRecursive(true);
/*      */                             } 
/*  952 */                             if (region.canRequestReload_unsynced() && ((!cacheOnlyMode && ((reloadEverything && region
/*  953 */                               .getReloadVersion() != globalReloadVersion) || region
/*  954 */                               .getCacheHashCode() != globalRegionCacheHashCode || region
/*  955 */                               .caveStartOutdated(globalCaveStart, globalCaveDepth) || region
/*  956 */                               .getVersion() != globalVersion || (region
/*  957 */                               .getLoadState() != 2 && region.shouldCache()))) || (region
/*  958 */                               .getLoadState() == 0 && (!region.isMetaLoaded() || textureLevel == 0 || region.loadingNeededForBranchLevel == textureLevel)) || ((region
/*  959 */                               .isMetaLoaded() || region.getLoadState() != 0 || !region.hasHadTerrain()) && region.getHighlightsHash() != region.getDim().getHighlightHandler().getRegionHash(region.getRegionX(), region.getRegionZ())))) {
/*      */                               
/*  961 */                               loadingLeaves = true;
/*      */ 
/*      */                               
/*  964 */                               region.calculateSortingDistance();
/*  965 */                               Misc.addToListOfSmallest(10, this.regionBuffer, (Comparable)region);
/*      */                             } 
/*      */                           } 
/*      */                         }
/*      */                       } 
/*      */                     } 
/*      */                   } 
/*      */                 } 
/*      */               } 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  978 */               if (leveledRegion != null) {
/*      */                 
/*  980 */                 LeveledRegion<?> rootLeveledRegion = leveledRegion.getRootRegion();
/*  981 */                 if (rootLeveledRegion == leveledRegion)
/*  982 */                   rootLeveledRegion = null; 
/*  983 */                 if (rootLeveledRegion != null && !rootLeveledRegion.isLoaded()) {
/*  984 */                   if (!rootLeveledRegion.recacheHasBeenRequested() && !rootLeveledRegion.reloadHasBeenRequested()) {
/*  985 */                     rootLeveledRegion.calculateSortingDistance();
/*  986 */                     Misc.addToListOfSmallest(10, this.branchRegionBuffer, (Comparable)rootLeveledRegion);
/*      */                   } 
/*  988 */                   this.waitingForBranchCache[0] = true;
/*  989 */                   rootLeveledRegion = null;
/*      */                 } 
/*  991 */                 if (!this.mapProcessor.isUploadingPaused() && !WorldMap.settings.pauseRequests) {
/*      */ 
/*      */                   
/*  994 */                   if (leveledRegion instanceof BranchLeveledRegion) {
/*  995 */                     BranchLeveledRegion branchRegion = (BranchLeveledRegion)leveledRegion;
/*  996 */                     branchRegion.checkForUpdates(this.mapProcessor, prevWaitingForBranchCache, this.waitingForBranchCache, this.branchRegionBuffer, textureLevel, minLeafRegX, minLeafRegZ, maxLeafRegX, maxLeafRegZ);
/*      */                   } 
/*  998 */                   if (((textureLevel != 0 && !prevWaitingForBranchCache) || (textureLevel == 0 && !this.prevLoadingLeaves)) && this.lastFrameRenderedRootTextures && rootLeveledRegion != null && rootLeveledRegion != lastUpdatedRootLeveledRegion) {
/*      */ 
/*      */                     
/* 1001 */                     BranchLeveledRegion branchRegion = (BranchLeveledRegion)rootLeveledRegion;
/* 1002 */                     branchRegion.checkForUpdates(this.mapProcessor, prevWaitingForBranchCache, this.waitingForBranchCache, this.branchRegionBuffer, textureLevel, minLeafRegX, minLeafRegZ, maxLeafRegX, maxLeafRegZ);
/* 1003 */                     lastUpdatedRootLeveledRegion = rootLeveledRegion;
/*      */                   } 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 1009 */                   this.mapProcessor.getMapWorld().getCurrentDimension().getLayeredMapRegions().bumpLoadedRegion(leveledRegion);
/* 1010 */                   if (rootLeveledRegion != null) {
/* 1011 */                     this.mapProcessor.getMapWorld().getCurrentDimension().getLayeredMapRegions().bumpLoadedRegion(rootLeveledRegion);
/*      */                   }
/*      */                 } else {
/*      */                   
/* 1015 */                   this.waitingForBranchCache[0] = prevWaitingForBranchCache;
/*      */                 } 
/* 1017 */                 int minXBlocks = leveledRegX * leveledSideInBlocks;
/* 1018 */                 int minZBlocks = leveledRegZ * leveledSideInBlocks;
/* 1019 */                 int textureSize = 64 * leveledSideInRegions;
/* 1020 */                 int firstTextureX = leveledRegX << 3;
/* 1021 */                 int firstTextureZ = leveledRegZ << 3;
/* 1022 */                 int levelDiff = 3 - textureLevel;
/* 1023 */                 int rootSize = 1 << levelDiff;
/* 1024 */                 int maxInsideCoord = rootSize - 1;
/* 1025 */                 int firstRootTextureX = firstTextureX >> levelDiff & 0x7;
/* 1026 */                 int firstRootTextureZ = firstTextureZ >> levelDiff & 0x7;
/* 1027 */                 int firstInsideTextureX = firstTextureX & maxInsideCoord;
/* 1028 */                 int firstInsideTextureZ = firstTextureZ & maxInsideCoord;
/* 1029 */                 boolean hasTextures = leveledRegion.hasTextures();
/* 1030 */                 boolean rootHasTextures = (rootLeveledRegion != null && rootLeveledRegion.hasTextures());
/* 1031 */                 if (hasTextures || rootHasTextures) {
/* 1032 */                   for (int o = 0; o < 8; o++) {
/* 1033 */                     int textureX = minXBlocks + o * textureSize;
/* 1034 */                     if (textureX <= rightBorder && (textureX + textureSize) >= leftBorder)
/*      */                     {
/* 1036 */                       for (int p = 0; p < 8; p++) {
/* 1037 */                         int textureZ = minZBlocks + p * textureSize;
/* 1038 */                         if (textureZ <= bottomBorder && (textureZ + textureSize) >= topBorder) {
/*      */                           
/* 1040 */                           RegionTexture<?> regionTexture = hasTextures ? leveledRegion.getTexture(o, p) : null;
/* 1041 */                           if (regionTexture == null || regionTexture.getGlColorTexture() == null)
/* 1042 */                           { if (rootHasTextures) {
/*      */                               
/* 1044 */                               int insideX = firstInsideTextureX + o;
/* 1045 */                               int insideZ = firstInsideTextureZ + p;
/* 1046 */                               int rootTextureX = firstRootTextureX + (insideX >> levelDiff);
/* 1047 */                               int rootTextureZ = firstRootTextureZ + (insideZ >> levelDiff);
/* 1048 */                               regionTexture = rootLeveledRegion.getTexture(rootTextureX, rootTextureZ);
/* 1049 */                               if (regionTexture != null) {
/*      */                                 
/* 1051 */                                 GpuTextureAndView texture = regionTexture.getGlColorTexture();
/* 1052 */                                 if (texture != null)
/* 1053 */                                 { frameRenderedRootTextures = true;
/* 1054 */                                   int insideTextureX = insideX & maxInsideCoord;
/* 1055 */                                   int insideTextureZ = insideZ & maxInsideCoord;
/* 1056 */                                   float textureX1 = insideTextureX / rootSize;
/* 1057 */                                   float textureX2 = (insideTextureX + 1) / rootSize;
/* 1058 */                                   float textureY1 = insideTextureZ / rootSize;
/* 1059 */                                   float textureY2 = (insideTextureZ + 1) / rootSize;
/*      */                                   
/* 1061 */                                   boolean hasLight = regionTexture.getTextureHasLight();
/* 1062 */                                   renderTexturedModalSubRectWithLighting(matrix, (textureX - flooredCameraX), (textureZ - flooredCameraZ), textureX1, textureY1, textureX2, textureY2, textureSize, textureSize, texture.texture, hasLight, hasLight ? withLightRenderer : noLightRenderer); } 
/*      */                               } 
/*      */                             }  }
/* 1065 */                           else { GpuTextureAndView texture = regionTexture.getGlColorTexture();
/* 1066 */                             if (texture != null) {
/* 1067 */                               boolean hasLight = regionTexture.getTextureHasLight();
/* 1068 */                               renderTexturedModalRectWithLighting3(matrix, (textureX - flooredCameraX), (textureZ - flooredCameraZ), textureSize, textureSize, texture.texture, hasLight, hasLight ? withLightRenderer : noLightRenderer);
/*      */                             }  }
/*      */                         
/*      */                         } 
/*      */                       } 
/*      */                     }
/*      */                   } 
/*      */                 }
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 1080 */                 if (leveledRegion.loadingAnimation()) {
/*      */                   
/* 1082 */                   matrixStack.method_22903();
/* 1083 */                   matrixStack.method_22904(leveledSideInBlocks * (leveledRegX + 0.5D) - flooredCameraX, leveledSideInBlocks * (leveledRegZ + 0.5D) - flooredCameraZ, 0.0D);
/* 1084 */                   float loadingAnimationPassed = (float)(System.currentTimeMillis() - this.loadingAnimationStart);
/* 1085 */                   if (loadingAnimationPassed > 0.0F) {
/* 1086 */                     int period = 2000;
/* 1087 */                     int numbersOfActors = 3;
/* 1088 */                     float loadingAnimation = loadingAnimationPassed % period / period * 360.0F;
/* 1089 */                     float step = 360.0F / numbersOfActors;
/* 1090 */                     OptimizedMath.rotatePose(matrixStack, loadingAnimation, (Vector3fc)OptimizedMath.ZP);
/* 1091 */                     int numberOfVisibleActors = 1 + (int)loadingAnimationPassed % 3 * period / period;
/* 1092 */                     matrixStack.method_22905(leveledSideInRegions, leveledSideInRegions, 1.0F);
/* 1093 */                     for (int i = 0; i < numberOfVisibleActors; i++) {
/* 1094 */                       OptimizedMath.rotatePose(matrixStack, step, (Vector3fc)OptimizedMath.ZP);
/* 1095 */                       MapRenderHelper.fillIntoExistingBuffer(matrixStack.method_23760().method_23761(), overlayBuffer, 16, -8, 32, 8, 1.0F, 1.0F, 1.0F, 1.0F);
/*      */                     } 
/*      */                   } 
/* 1098 */                   matrixStack.method_22909();
/*      */                 } 
/*      */                 
/* 1101 */                 if (WorldMap.settings.debug && leveledRegion instanceof MapRegion) {
/* 1102 */                   MapRegion region = (MapRegion)leveledRegion;
/* 1103 */                   matrixStack.method_22903();
/* 1104 */                   matrixStack.method_46416((512 * region.getRegionX() + 32 - flooredCameraX), (512 * region.getRegionZ() + 32 - flooredCameraZ), 0.0F);
/* 1105 */                   matrixStack.method_22905(10.0F, 10.0F, 1.0F);
/* 1106 */                   Misc.drawNormalText(matrixStack, "" + region.getLoadState(), 0.0F, 0.0F, -1, true, renderTypeBuffers);
/* 1107 */                   matrixStack.method_22909();
/*      */                 } 
/*      */                 
/* 1110 */                 if (WorldMap.settings.debug && textureLevel > 0)
/* 1111 */                   for (int i = 0; i < leveledSideInRegions; i++) {
/* 1112 */                     for (int leafZ = 0; leafZ < leveledSideInRegions; leafZ++) {
/* 1113 */                       int regX = leafRegionMinX + i;
/* 1114 */                       int regZ = leafRegionMinZ + leafZ;
/* 1115 */                       MapRegion region = this.mapProcessor.getLeafMapRegion(renderedCaveLayer, regX, regZ, false);
/* 1116 */                       if (region != null) {
/*      */                         
/* 1118 */                         boolean currentlyLoading = (this.mapProcessor.getMapSaveLoad().getNextToLoadByViewing() == region);
/* 1119 */                         if (currentlyLoading || region.isLoaded() || region.isMetaLoaded()) {
/* 1120 */                           matrixStack.method_22903();
/* 1121 */                           matrixStack.method_46416((512 * region.getRegionX() - flooredCameraX), (512 * region.getRegionZ() - flooredCameraZ), 0.0F);
/* 1122 */                           float r = 0.0F;
/* 1123 */                           float g = 0.0F;
/* 1124 */                           float b = 0.0F;
/* 1125 */                           float a = 0.1569F;
/* 1126 */                           if (currentlyLoading) {
/* 1127 */                             r = b = 1.0F;
/* 1128 */                           } else if (region.isLoaded()) {
/* 1129 */                             g = 1.0F;
/*      */                           } else {
/* 1131 */                             r = g = 1.0F;
/* 1132 */                           }  MapRenderHelper.fillIntoExistingBuffer(matrixStack.method_23760().method_23761(), overlayBuffer, 0, 0, 512, 512, r, g, b, a);
/* 1133 */                           matrixStack.method_22909();
/*      */                         } 
/*      */                       } 
/*      */                     } 
/*      */                   }  
/*      */               } 
/*      */             } 
/* 1140 */           }  this.lastFrameRenderedRootTextures = frameRenderedRootTextures;
/* 1141 */           WorldMapShaderHelper.setBrightness(brightness);
/* 1142 */           WorldMapShaderHelper.setWithLight(true);
/* 1143 */           rendererProvider.draw(withLightRenderer);
/* 1144 */           WorldMapShaderHelper.setWithLight(false);
/* 1145 */           rendererProvider.draw(noLightRenderer);
/*      */           
/* 1147 */           LeveledRegion<?> nextToLoad = this.mapProcessor.getMapSaveLoad().getNextToLoadByViewing();
/* 1148 */           boolean shouldRequest = false;
/* 1149 */           if (nextToLoad != null) {
/* 1150 */             shouldRequest = nextToLoad.shouldAllowAnotherRegionToLoad();
/*      */           } else {
/* 1152 */             shouldRequest = true;
/* 1153 */           }  shouldRequest = (shouldRequest && this.mapProcessor.getAffectingLoadingFrequencyCount() < 16);
/* 1154 */           if (shouldRequest && !WorldMap.settings.pauseRequests) {
/*      */             
/* 1156 */             int toRequest = 2;
/* 1157 */             int counter = 0;
/*      */             
/*      */             int i;
/*      */             
/* 1161 */             for (i = 0; i < this.branchRegionBuffer.size() && counter < toRequest; i++) {
/* 1162 */               BranchLeveledRegion region = this.branchRegionBuffer.get(i);
/* 1163 */               if (!region.reloadHasBeenRequested() && !region.recacheHasBeenRequested() && !region.isLoaded()) {
/*      */                 
/* 1165 */                 region.setReloadHasBeenRequested(true, "Gui");
/* 1166 */                 this.mapProcessor.getMapSaveLoad().requestBranchCache(region, "Gui");
/* 1167 */                 if (counter == 0)
/* 1168 */                   this.mapProcessor.getMapSaveLoad().setNextToLoadByViewing((LeveledRegion)region); 
/* 1169 */                 counter++;
/*      */               } 
/* 1171 */             }  toRequest = 1;
/* 1172 */             counter = 0;
/* 1173 */             if (!prevWaitingForBranchCache)
/*      */             {
/* 1175 */               for (i = 0; i < this.regionBuffer.size() && counter < toRequest; i++) {
/* 1176 */                 MapRegion region = this.regionBuffer.get(i);
/* 1177 */                 if (region != nextToLoad || this.regionBuffer.size() <= 1)
/*      */                 {
/* 1179 */                   synchronized (region) {
/* 1180 */                     if (!region.canRequestReload_unsynced()) {  }
/*      */                     else
/* 1182 */                     { if (region.getLoadState() == 2) {
/* 1183 */                         region.requestRefresh(this.mapProcessor);
/*      */                       } else {
/* 1185 */                         this.mapProcessor.getMapSaveLoad().requestLoad(region, "Gui");
/* 1186 */                       }  if (counter == 0)
/* 1187 */                         this.mapProcessor.getMapSaveLoad().setNextToLoadByViewing((LeveledRegion)region); 
/* 1188 */                       counter++;
/* 1189 */                       if (region.getLoadState() == 4)
/*      */                         break;  }
/*      */                   
/*      */                   }  } 
/*      */               } 
/*      */             }
/*      */           } 
/* 1196 */           this.prevWaitingForBranchCache = this.waitingForBranchCache[0];
/* 1197 */           this.prevLoadingLeaves = loadingLeaves;
/* 1198 */           int highlightChunkX = this.mouseBlockPosX >> 4;
/* 1199 */           int highlightChunkZ = this.mouseBlockPosZ >> 4;
/* 1200 */           int chunkHighlightLeftX = highlightChunkX << 4;
/* 1201 */           int chunkHighlightRightX = highlightChunkX + 1 << 4;
/* 1202 */           int chunkHighlightTopZ = highlightChunkZ << 4;
/* 1203 */           int chunkHighlightBottomZ = highlightChunkZ + 1 << 4;
/* 1204 */           MapRenderHelper.renderDynamicHighlight(matrixStack, overlayBuffer, flooredCameraX, flooredCameraZ, chunkHighlightLeftX, chunkHighlightRightX, chunkHighlightTopZ, chunkHighlightBottomZ, 0.0F, 0.0F, 0.0F, 0.2F, 1.0F, 1.0F, 1.0F, 0.1569F);
/*      */           
/* 1206 */           MapTileSelection mapTileSelectionToRender = this.mapTileSelection;
/* 1207 */           if (mapTileSelectionToRender == null && this.field_22787.field_1755 instanceof ExportScreen)
/* 1208 */             mapTileSelectionToRender = ((ExportScreen)this.field_22787.field_1755).getSelection(); 
/* 1209 */           if (mapTileSelectionToRender != null) {
/* 1210 */             MapRenderHelper.renderDynamicHighlight(matrixStack, overlayBuffer, flooredCameraX, flooredCameraZ, mapTileSelectionToRender.getLeft() << 4, mapTileSelectionToRender.getRight() + 1 << 4, mapTileSelectionToRender.getTop() << 4, mapTileSelectionToRender.getBottom() + 1 << 4, 0.0F, 0.0F, 0.0F, 0.2F, 1.0F, 0.5F, 0.5F, 0.4F);
/* 1211 */             if (SupportMods.pac() && !this.mapProcessor.getMapWorld().isUsingCustomDimension()) {
/* 1212 */               int playerX = (int)Math.floor(this.player.method_23317());
/* 1213 */               int playerZ = (int)Math.floor(this.player.method_23321());
/* 1214 */               int playerChunkX = playerX >> 4;
/* 1215 */               int playerChunkZ = playerZ >> 4;
/* 1216 */               int claimDistance = SupportMods.xaeroPac.getClaimDistance();
/* 1217 */               int claimableAreaLeft = playerChunkX - claimDistance;
/* 1218 */               int claimableAreaTop = playerChunkZ - claimDistance;
/* 1219 */               int claimableAreaRight = playerChunkX + claimDistance;
/* 1220 */               int claimableAreaBottom = playerChunkZ + claimDistance;
/*      */               
/* 1222 */               int claimableAreaHighlightLeftX = claimableAreaLeft << 4;
/* 1223 */               int claimableAreaHighlightRightX = claimableAreaRight + 1 << 4;
/* 1224 */               int claimableAreaHighlightTopZ = claimableAreaTop << 4;
/* 1225 */               int claimableAreaHighlightBottomZ = claimableAreaBottom + 1 << 4;
/* 1226 */               MapRenderHelper.renderDynamicHighlight(matrixStack, overlayBuffer, flooredCameraX, flooredCameraZ, claimableAreaHighlightLeftX, claimableAreaHighlightRightX, claimableAreaHighlightTopZ, claimableAreaHighlightBottomZ, 0.0F, 0.0F, 1.0F, 0.3F, 0.0F, 0.0F, 1.0F, 0.15F);
/*      */             } 
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/* 1232 */           renderTypeBuffers.method_22993();
/* 1233 */           primaryScaleFBO.bindDefaultFramebuffer(mc);
/* 1234 */           matrixStack.method_22909();
/*      */           
/* 1236 */           matrixStack.method_22903();
/* 1237 */           matrixStack.method_22905((float)secondaryScale, (float)secondaryScale, 1.0F);
/* 1238 */           primaryScaleFBO.method_30277().setTextureFilter(FilterMode.LINEAR, false);
/* 1239 */           class_4588 colorBackgroundConsumer = renderTypeBuffers.method_73477(CustomRenderTypes.MAP_COLOR_FILLER);
/* 1240 */           int lineX = -mc.method_22683().method_4489() / 2;
/* 1241 */           int lineY = mc.method_22683().method_4506() / 2 - 5;
/* 1242 */           int lineW = mc.method_22683().method_4489();
/* 1243 */           int lineH = 6;
/* 1244 */           MapRenderHelper.fillIntoExistingBuffer(matrixStack.method_23760().method_23761(), colorBackgroundConsumer, lineX, lineY, lineX + lineW, lineY + lineH, 0.0F, 0.0F, 0.0F, 1.0F);
/* 1245 */           lineX = mc.method_22683().method_4489() / 2 - 5;
/* 1246 */           lineY = -mc.method_22683().method_4506() / 2;
/* 1247 */           lineW = 6;
/* 1248 */           lineH = mc.method_22683().method_4506();
/* 1249 */           MapRenderHelper.fillIntoExistingBuffer(matrixStack.method_23760().method_23761(), colorBackgroundConsumer, lineX, lineY, lineX + lineW, lineY + lineH, 0.0F, 0.0F, 0.0F, 1.0F);
/* 1250 */           renderTypeBuffers.method_22993();
/*      */           
/* 1252 */           class_1921 mainFrameRenderType = CustomRenderTypes.MAP_FRAME;
/* 1253 */           MultiTextureRenderTypeRenderer mainFrameRenderer = rendererProvider.getRenderer(MultiTextureRenderTypeRendererProvider::defaultTextureBind, mainFrameRenderType);
/* 1254 */           class_287 class_287 = mainFrameRenderer.begin(primaryScaleFBO.method_30277());
/*      */           
/* 1256 */           renderTexturedModalRect(matrixStack.method_23760().method_23761(), (class_4588)class_287, (-mc.method_22683().method_4489() / 2) - (float)secondaryOffsetX, (-mc.method_22683().method_4506() / 2) - (float)secondaryOffsetY, 0, 0, primaryScaleFBO.field_1482, primaryScaleFBO.field_1481, primaryScaleFBO.field_1482, primaryScaleFBO.field_1481, 1.0F, 1.0F, 1.0F, 1.0F);
/* 1257 */           rendererProvider.draw(mainFrameRenderer);
/* 1258 */           matrixStack.method_22909();
/* 1259 */           matrixStack.method_22905((float)this.scale, (float)this.scale, 1.0F);
/*      */           
/* 1261 */           double screenSizeBasedScale = scaleMultiplier;
/*      */           
/* 1263 */           WorldMap.trackedPlayerRenderer.update(mc);
/*      */ 
/*      */           
/*      */           try {
/* 1267 */             this.viewed = WorldMap.mapElementRenderHandler.render(this, renderTypeBuffers, rendererProvider, this.cameraX, this.cameraZ, mc.method_22683().method_4489(), mc.method_22683().method_4506(), screenSizeBasedScale, this.scale, playerDimDiv, mousePosX, mousePosZ, brightness, (renderedCaveLayer != Integer.MAX_VALUE), this.viewed, mc, partialTicks);
/* 1268 */           } catch (Throwable t) {
/* 1269 */             WorldMap.LOGGER.error("error rendering map elements", t);
/* 1270 */             throw t;
/*      */           } 
/* 1272 */           this.viewedInList = false;
/*      */           
/* 1274 */           matrixStack.method_22903();
/* 1275 */           matrixStack.method_46416(0.0F, 0.0F, 50.0F);
/* 1276 */           class_4588 regularUIObjectConsumer = renderTypeBuffers.method_73477(CustomRenderTypes.GUI);
/* 1277 */           if (WorldMap.settings.footsteps) {
/* 1278 */             ArrayList<Double[]> footprints = this.mapProcessor.getFootprints();
/* 1279 */             synchronized (footprints) {
/* 1280 */               for (int i = 0; i < footprints.size(); i++) {
/* 1281 */                 Double[] coords = footprints.get(i);
/* 1282 */                 setColourBuffer(1.0F, 0.1F, 0.1F, 1.0F);
/* 1283 */                 drawDotOnMap(matrixStack, regularUIObjectConsumer, coords[0].doubleValue() / playerDimDiv - this.cameraX, coords[1].doubleValue() / playerDimDiv - this.cameraZ, 0.0F, 1.0D / this.scale);
/*      */               } 
/*      */             } 
/*      */           } 
/* 1287 */           if (WorldMap.settings.renderArrow) {
/* 1288 */             boolean toTheLeft = (scaledPlayerX < leftBorder);
/* 1289 */             boolean toTheRight = (scaledPlayerX > rightBorder);
/* 1290 */             boolean down = (scaledPlayerZ > bottomBorder);
/* 1291 */             boolean up = (scaledPlayerZ < topBorder);
/*      */             
/* 1293 */             float configuredR = 1.0F;
/* 1294 */             float configuredG = 1.0F;
/* 1295 */             float configuredB = 1.0F;
/* 1296 */             int effectiveArrowColorIndex = WorldMap.settings.arrowColour;
/* 1297 */             if (effectiveArrowColorIndex == -2 && !SupportMods.minimap())
/* 1298 */               effectiveArrowColorIndex = 0; 
/* 1299 */             if (effectiveArrowColorIndex == -2 && SupportMods.xaeroMinimap.getArrowColorIndex() == -1) {
/* 1300 */               effectiveArrowColorIndex = -1;
/*      */             }
/* 1302 */             if (effectiveArrowColorIndex == -1) {
/* 1303 */               int rgb = Misc.getTeamColour((mc.field_1724 == null) ? mc.method_1560() : (class_1297)mc.field_1724);
/* 1304 */               if (rgb == -1) {
/* 1305 */                 effectiveArrowColorIndex = 0;
/*      */               } else {
/* 1307 */                 configuredR = (rgb >> 16 & 0xFF) / 255.0F;
/* 1308 */                 configuredG = (rgb >> 8 & 0xFF) / 255.0F;
/* 1309 */                 configuredB = (rgb & 0xFF) / 255.0F;
/*      */               } 
/* 1311 */             } else if (effectiveArrowColorIndex == -2) {
/* 1312 */               float[] c = SupportMods.xaeroMinimap.getArrowColor();
/* 1313 */               if (c == null) {
/* 1314 */                 effectiveArrowColorIndex = 0;
/*      */               } else {
/* 1316 */                 configuredR = c[0];
/* 1317 */                 configuredG = c[1];
/* 1318 */                 configuredB = c[2];
/*      */               } 
/*      */             } 
/* 1321 */             if (effectiveArrowColorIndex >= 0) {
/* 1322 */               float[] c = ModSettings.arrowColours[effectiveArrowColorIndex];
/* 1323 */               configuredR = c[0];
/* 1324 */               configuredG = c[1];
/* 1325 */               configuredB = c[2];
/*      */             } 
/*      */             
/* 1328 */             if (toTheLeft || toTheRight || up || down) {
/* 1329 */               double arrowX = scaledPlayerX;
/* 1330 */               double arrowZ = scaledPlayerZ;
/* 1331 */               float a = 0.0F;
/* 1332 */               if (toTheLeft) {
/* 1333 */                 a = up ? 1.5F : (down ? 0.5F : 1.0F);
/* 1334 */                 arrowX = leftBorder;
/* 1335 */               } else if (toTheRight) {
/* 1336 */                 a = up ? 2.5F : (down ? 3.5F : 3.0F);
/* 1337 */                 arrowX = rightBorder;
/*      */               } 
/* 1339 */               if (down) {
/* 1340 */                 arrowZ = bottomBorder;
/* 1341 */               } else if (up) {
/* 1342 */                 if (a == 0.0F)
/* 1343 */                   a = 2.0F; 
/* 1344 */                 arrowZ = topBorder;
/*      */               } 
/* 1346 */               setColourBuffer(0.0F, 0.0F, 0.0F, 0.9F);
/* 1347 */               drawFarArrowOnMap(matrixStack, regularUIObjectConsumer, arrowX - this.cameraX, arrowZ + 2.0D * screenSizeBasedScale / this.scale - this.cameraZ, a, screenSizeBasedScale / this.scale);
/* 1348 */               setColourBuffer(configuredR, configuredG, configuredB, 1.0F);
/* 1349 */               drawFarArrowOnMap(matrixStack, regularUIObjectConsumer, arrowX - this.cameraX, arrowZ - this.cameraZ, a, screenSizeBasedScale / this.scale);
/*      */             } else {
/* 1351 */               setColourBuffer(0.0F, 0.0F, 0.0F, 0.9F);
/* 1352 */               drawArrowOnMap(matrixStack, regularUIObjectConsumer, scaledPlayerX - this.cameraX, scaledPlayerZ + 2.0D * screenSizeBasedScale / this.scale - this.cameraZ, this.player.method_36454(), screenSizeBasedScale / this.scale);
/* 1353 */               setColourBuffer(configuredR, configuredG, configuredB, 1.0F);
/* 1354 */               drawArrowOnMap(matrixStack, regularUIObjectConsumer, scaledPlayerX - this.cameraX, scaledPlayerZ - this.cameraZ, this.player.method_36454(), screenSizeBasedScale / this.scale);
/*      */             } 
/*      */           } 
/*      */ 
/*      */           
/* 1359 */           class_1044 guiTextures = this.field_22787.method_1531().method_4619(WorldMap.guiTextures);
/* 1360 */           guiTextures.method_4527(true, false);
/* 1361 */           renderTypeBuffers.method_22993();
/* 1362 */           guiTextures.method_4527(false, false);
/* 1363 */           matrixStack.method_22909();
/*      */           
/* 1365 */           matrixStack.method_22909();
/*      */           
/* 1367 */           TextureUtils.clearRenderTargetDepth(this.field_22787.method_1522(), 1.0F);
/* 1368 */           int cursorDisplayOffset = 0;
/* 1369 */           if (WorldMap.settings.coordinates) {
/* 1370 */             String coordsString = "X: " + this.mouseBlockPosX;
/* 1371 */             if (mouseBlockBottomY != 32767)
/* 1372 */               coordsString = coordsString + " Y: " + coordsString; 
/* 1373 */             if (hasAmbiguousHeight && mouseBlockTopY != 32767)
/* 1374 */               coordsString = coordsString + " (" + coordsString + ")"; 
/* 1375 */             coordsString = coordsString + " Z: " + coordsString;
/* 1376 */             MapRenderHelper.drawCenteredStringWithBackground(guiGraphics, this.field_22793, coordsString, this.field_22789 / 2, 2 + cursorDisplayOffset, -1, 0.0F, 0.0F, 0.0F, 0.4F);
/* 1377 */             cursorDisplayOffset += 10;
/*      */           } 
/* 1379 */           if (WorldMap.settings.hoveredBiome && pointedAtBiome != null) {
/* 1380 */             class_2960 biomeRL = pointedAtBiome.method_29177();
/* 1381 */             String biomeText = (biomeRL == null) ? class_1074.method_4662("gui.xaero_wm_unknown_biome", new Object[0]) : class_1074.method_4662("biome." + biomeRL.method_12836() + "." + biomeRL.method_12832(), new Object[0]);
/* 1382 */             MapRenderHelper.drawCenteredStringWithBackground(guiGraphics, this.field_22793, biomeText, this.field_22789 / 2, 2 + cursorDisplayOffset, -1, 0.0F, 0.0F, 0.0F, 0.4F);
/*      */           } 
/*      */           
/* 1385 */           int subtleTooltipOffset = 12;
/* 1386 */           if (WorldMap.settings.displayZoom) {
/* 1387 */             String zoomString = "" + Math.round(destScale * 1000.0D) / 1000.0D + "x";
/* 1388 */             MapRenderHelper.drawCenteredStringWithBackground(guiGraphics, mc.field_1772, zoomString, this.field_22789 / 2, this.field_22790 - subtleTooltipOffset, -1, 0.0F, 0.0F, 0.0F, 0.4F);
/*      */           } 
/* 1390 */           if (this.mapProcessor.getMapWorld().getCurrentDimension().getFullReloader() != null) {
/* 1391 */             subtleTooltipOffset += 12;
/* 1392 */             MapRenderHelper.drawCenteredStringWithBackground(guiGraphics, mc.field_1772, FULL_RELOAD_IN_PROGRESS, this.field_22789 / 2, this.field_22790 - subtleTooltipOffset, -1, 0.0F, 0.0F, 0.0F, 0.4F);
/*      */           } 
/* 1394 */           if (this.mapProcessor.getMapWorld().isUsingUnknownDimensionType()) {
/* 1395 */             subtleTooltipOffset += 24;
/* 1396 */             MapRenderHelper.drawCenteredStringWithBackground(guiGraphics, mc.field_1772, UNKNOWN_DIMENSION_TYPE2, this.field_22789 / 2, this.field_22790 - subtleTooltipOffset, -1, 0.0F, 0.0F, 0.0F, 0.4F);
/* 1397 */             subtleTooltipOffset += 12;
/* 1398 */             MapRenderHelper.drawCenteredStringWithBackground(guiGraphics, mc.field_1772, UNKNOWN_DIMENSION_TYPE1, this.field_22789 / 2, this.field_22790 - subtleTooltipOffset, -1, 0.0F, 0.0F, 0.0F, 0.4F);
/*      */           } 
/* 1400 */           if (WorldMap.settings.displayCaveModeStart) {
/* 1401 */             subtleTooltipOffset += 12;
/* 1402 */             if (globalCaveStart != Integer.MAX_VALUE && globalCaveStart != Integer.MIN_VALUE) {
/* 1403 */               String caveModeStartString = class_1074.method_4662("gui.xaero_wm_cave_mode_start_display", new Object[] { Integer.valueOf(globalCaveStart) });
/* 1404 */               MapRenderHelper.drawCenteredStringWithBackground(guiGraphics, mc.field_1772, caveModeStartString, this.field_22789 / 2, this.field_22790 - subtleTooltipOffset, -1, 0.0F, 0.0F, 0.0F, 0.4F);
/*      */             } 
/*      */           } 
/* 1407 */           if (SupportMods.minimap()) {
/* 1408 */             String subWorldNameToRender = SupportMods.xaeroMinimap.getSubWorldNameToRender();
/* 1409 */             if (subWorldNameToRender != null) {
/* 1410 */               subtleTooltipOffset += 24;
/* 1411 */               MapRenderHelper.drawCenteredStringWithBackground(guiGraphics, mc.field_1772, subWorldNameToRender, this.field_22789 / 2, this.field_22790 - subtleTooltipOffset, -1, 0.0F, 0.0F, 0.0F, 0.4F);
/*      */             } 
/*      */           } 
/* 1414 */           discoveredForHighlights = (mouseBlockBottomY != 32767);
/* 1415 */           class_2561 subtleHighlightTooltip = this.mapProcessor.getMapWorld().getCurrentDimension().getHighlightHandler().getBlockHighlightSubtleTooltip(this.mouseBlockPosX, this.mouseBlockPosZ, discoveredForHighlights);
/* 1416 */           if (subtleHighlightTooltip != null) {
/* 1417 */             subtleTooltipOffset += 12;
/* 1418 */             MapRenderHelper.drawCenteredStringWithBackground(guiGraphics, mc.field_1772, subtleHighlightTooltip, this.field_22789 / 2, this.field_22790 - subtleTooltipOffset, -1, 0.0F, 0.0F, 0.0F, 0.4F);
/*      */           } 
/* 1420 */           this.overWaypointsMenu = false;
/* 1421 */           this.overPlayersMenu = false;
/*      */           
/* 1423 */           if (this.waypointMenu) {
/* 1424 */             if (SupportMods.xaeroMinimap.getWaypointsSorted() != null) {
/* 1425 */               HoveredMapElementHolder<?, ?> hovered = SupportMods.xaeroMinimap.renderWaypointsMenu(guiGraphics, this, this.scale, this.field_22789, this.field_22790, scaledMouseX, scaledMouseY, this.leftMouseButton.isDown, this.leftMouseButton.clicked, this.viewed, mc);
/* 1426 */               if (hovered != null) {
/* 1427 */                 this.overWaypointsMenu = true;
/* 1428 */                 if (hovered.getElement() instanceof Waypoint) {
/* 1429 */                   this.viewed = hovered;
/* 1430 */                   this.viewedInList = true;
/* 1431 */                   if (this.leftMouseButton.clicked) {
/* 1432 */                     this.cameraDestination = new int[] { (int)((Waypoint)this.viewed.getElement()).getRenderX(), (int)((Waypoint)this.viewed.getElement()).getRenderZ() };
/* 1433 */                     this.leftMouseButton.isDown = false;
/* 1434 */                     if (WorldMap.settings.closeWaypointsWhenHopping)
/* 1435 */                       onWaypointsButton(this.waypointsButton); 
/*      */                   } 
/*      */                 } 
/*      */               } 
/*      */             } 
/* 1440 */           } else if (this.playersMenu) {
/* 1441 */             HoveredMapElementHolder<?, ?> hovered = WorldMap.trackedPlayerMenuRenderer.renderMenu(guiGraphics, this, this.scale, this.field_22789, this.field_22790, scaledMouseX, scaledMouseY, this.leftMouseButton.isDown, this.leftMouseButton.clicked, this.viewed, mc);
/* 1442 */             if (hovered != null) {
/* 1443 */               this.overPlayersMenu = true;
/* 1444 */               if (hovered.getElement() instanceof PlayerTrackerMapElement && WorldMap.trackedPlayerMenuRenderer.canJumpTo((PlayerTrackerMapElement)hovered.getElement())) {
/* 1445 */                 this.viewed = hovered;
/* 1446 */                 this.viewedInList = true;
/* 1447 */                 if (this.leftMouseButton.clicked) {
/* 1448 */                   PlayerTrackerMapElement<?> clickedPlayer = (PlayerTrackerMapElement)this.viewed.getElement();
/* 1449 */                   MapDimension clickedPlayerDim = this.mapProcessor.getMapWorld().getDimension(clickedPlayer.getDimension());
/* 1450 */                   class_2874 clickedPlayerDimType = MapDimension.getDimensionType(clickedPlayerDim, clickedPlayer.getDimension(), this.mapProcessor.getWorldDimensionTypeRegistry());
/* 1451 */                   double clickedPlayerDimDiv = this.mapProcessor.getMapWorld().getCurrentDimension().calculateDimDiv(this.mapProcessor.getWorldDimensionTypeRegistry(), clickedPlayerDimType);
/* 1452 */                   double jumpX = clickedPlayer.getX() / clickedPlayerDimDiv;
/* 1453 */                   double jumpZ = clickedPlayer.getZ() / clickedPlayerDimDiv;
/* 1454 */                   this.cameraDestination = new int[] { (int)jumpX, (int)jumpZ };
/* 1455 */                   this.leftMouseButton.isDown = false;
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */ 
/*      */           
/* 1462 */           if (SupportMods.minimap()) {
/* 1463 */             SupportMods.xaeroMinimap.drawSetChange(guiGraphics);
/*      */           }
/*      */           
/* 1466 */           if (SupportMods.pac()) {
/* 1467 */             SupportMods.xaeroPac.onMapRender(this.field_22787, matrixStack, scaledMouseX, scaledMouseY, partialTicks, this.mapProcessor.getWorld().method_27983().method_29177(), highlightChunkX, highlightChunkZ);
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1475 */           RenderSystem.setProjectionMatrix(projectionBU, projectionTypeBU);
/* 1476 */           RenderSystem.getModelViewStack().popMatrix();
/* 1477 */         } else if (!mapLoaded) {
/* 1478 */           renderLoadingScreen(guiGraphics);
/* 1479 */         } else if (isLocked) {
/* 1480 */           renderMessageScreen(guiGraphics, class_1074.method_4662("gui.xaero_current_map_locked1", new Object[0]), class_1074.method_4662("gui.xaero_current_map_locked2", new Object[0]));
/* 1481 */         } else if (noWorldMapEffect) {
/* 1482 */           renderMessageScreen(guiGraphics, class_1074.method_4662("gui.xaero_no_world_map_message", new Object[0]));
/* 1483 */         } else if (!allowedBasedOnItem) {
/* 1484 */           renderMessageScreen(guiGraphics, class_1074.method_4662("gui.xaero_no_world_map_item_message", new Object[0]), ModSettings.mapItem.method_63680().getString() + " (" + ModSettings.mapItem.method_63680().getString() + ")");
/*      */         }  }
/* 1486 */       else { renderLoadingScreen(guiGraphics); }
/* 1487 */        this.mapSwitchingGui.renderText(guiGraphics, this.field_22787, scaledMouseX, scaledMouseY, this.field_22789, this.field_22790);
/* 1488 */       guiGraphics.method_25290(class_10799.field_56883, WorldMap.guiTextures, this.field_22789 - 34, 2, 0.0F, 37.0F, 32, 32, 256, 256);
/*      */     } 
/*      */     
/* 1491 */     super.method_25394(guiGraphics, scaledMouseX, scaledMouseY, partialTicks);
/* 1492 */     if (this.rightClickMenu != null)
/* 1493 */       this.rightClickMenu.method_25394(guiGraphics, scaledMouseX, scaledMouseY, partialTicks); 
/* 1494 */     if (mc.field_1755 == this) {
/* 1495 */       if (!renderTooltips(guiGraphics, scaledMouseX, scaledMouseY, partialTicks) && !this.leftMouseButton.isDown && !this.rightMouseButton.isDown)
/* 1496 */         if (this.viewed != null) {
/* 1497 */           CursorBox hoveredTooltip = hoveredElementTooltipHelper(this.viewed, this.viewedInList);
/* 1498 */           if (hoveredTooltip != null)
/* 1499 */             hoveredTooltip.drawBox(guiGraphics, scaledMouseX, scaledMouseY, this.field_22789, this.field_22790); 
/*      */         } else {
/* 1501 */           synchronized (this.mapProcessor.renderThreadPauseSync) {
/* 1502 */             if (!this.mapProcessor.isRenderingPaused() && this.mapProcessor.getCurrentWorldId() != null && this.mapProcessor.getMapSaveLoad().isRegionDetectionComplete()) {
/* 1503 */               class_2561 bluntHighlightTooltip = this.mapProcessor.getMapWorld().getCurrentDimension().getHighlightHandler().getBlockHighlightBluntTooltip(this.mouseBlockPosX, this.mouseBlockPosZ, discoveredForHighlights);
/* 1504 */               if (bluntHighlightTooltip != null) {
/* 1505 */                 (new CursorBox(bluntHighlightTooltip)).drawBox(guiGraphics, scaledMouseX, scaledMouseY, this.field_22789, this.field_22790);
/*      */               }
/*      */             } 
/*      */           } 
/*      */         }  
/* 1510 */       this.mapProcessor.getMessageBoxRenderer().render(guiGraphics, this.mapProcessor.getMessageBox(), this.field_22793, 1, this.field_22790 / 2, false);
/*      */     } 
/* 1512 */     this.rightMouseButton.clicked = false;
/* 1513 */     this.noUploadingLimits = (this.cameraX == cameraXBefore && this.cameraZ == cameraZBefore && scaleBefore == this.scale);
/*      */ 
/*      */     
/* 1516 */     MapRenderHelper.restoreDefaultShaderBlendState();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void method_25420(class_332 guiGraphics, int i, int j, float f) {}
/*      */ 
/*      */   
/*      */   protected void renderPreDropdown(class_332 guiGraphics, int scaledMouseX, int scaledMouseY, float partialTicks) {
/* 1525 */     super.renderPreDropdown(guiGraphics, scaledMouseX, scaledMouseY, partialTicks);
/* 1526 */     if (this.waypointMenu) {
/* 1527 */       SupportMods.xaeroMinimap.getWaypointMenuRenderer().postMapRender(guiGraphics, this, scaledMouseX, scaledMouseY, this.field_22789, this.field_22790, partialTicks);
/*      */     }
/* 1529 */     if (this.playersMenu) {
/* 1530 */       WorldMap.trackedPlayerMenuRenderer.postMapRender(guiGraphics, this, scaledMouseX, scaledMouseY, this.field_22789, this.field_22790, partialTicks);
/*      */     }
/* 1532 */     this.mapSwitchingGui.postMapRender(guiGraphics, this.field_22787, scaledMouseX, scaledMouseY, this.field_22789, this.field_22790);
/*      */   }
/*      */   
/*      */   private <E, C> CursorBox hoveredElementTooltipHelper(HoveredMapElementHolder<E, C> hovered, boolean viewedInList) {
/* 1536 */     return hovered.getRenderer().getReader().getTooltip(hovered.getElement(), hovered.getRenderer().getContext(), viewedInList);
/*      */   }
/*      */   
/*      */   private void renderLoadingScreen(class_332 guiGraphics) {
/* 1540 */     renderMessageScreen(guiGraphics, "Preparing World Map...");
/*      */   }
/*      */   
/*      */   private void renderMessageScreen(class_332 guiGraphics, String message) {
/* 1544 */     renderMessageScreen(guiGraphics, message, (String)null);
/*      */   }
/*      */   
/*      */   private void renderMessageScreen(class_332 guiGraphics, String message, String message2) {
/* 1548 */     guiGraphics.method_25294(0, 0, this.field_22787.method_22683().method_4489(), this.field_22787.method_22683().method_4506(), -16777216);
/* 1549 */     guiGraphics.method_25300(this.field_22787.field_1772, message, this.field_22787.method_22683().method_4486() / 2, this.field_22787.method_22683().method_4502() / 2, -1);
/* 1550 */     if (message2 != null)
/* 1551 */       guiGraphics.method_25300(this.field_22787.field_1772, message2, this.field_22787.method_22683().method_4486() / 2, this.field_22787.method_22683().method_4502() / 2 + 10, -1); 
/*      */   }
/*      */   
/*      */   public void drawDotOnMap(class_4587 matrixStack, class_4588 guiLinearBuffer, double x, double z, float angle, double sc) {
/* 1555 */     drawObjectOnMap(matrixStack, guiLinearBuffer, x, z, angle, sc, 2.5F, 2.5F, 0, 69, 5, 5);
/*      */   }
/*      */   
/*      */   public void drawArrowOnMap(class_4587 matrixStack, class_4588 guiLinearBuffer, double x, double z, float angle, double sc) {
/* 1559 */     drawObjectOnMap(matrixStack, guiLinearBuffer, x, z, angle, sc, 13.0F, 5.0F, 0, 0, 26, 28);
/*      */   }
/*      */   
/*      */   public void drawFarArrowOnMap(class_4587 matrixStack, class_4588 guiLinearBuffer, double x, double z, float angle, double sc) {
/* 1563 */     drawObjectOnMap(matrixStack, guiLinearBuffer, x, z, angle * 90.0F, sc, 27.0F, 13.0F, 26, 0, 54, 13);
/*      */   }
/*      */   
/*      */   public void drawObjectOnMap(class_4587 matrixStack, class_4588 guiLinearBuffer, double x, double z, float angle, double sc, float offX, float offY, int textureX, int textureY, int w, int h) {
/* 1567 */     matrixStack.method_22903();
/* 1568 */     matrixStack.method_22904(x, z, 0.0D);
/* 1569 */     matrixStack.method_22905((float)sc, (float)sc, 1.0F);
/* 1570 */     if (angle != 0.0F)
/* 1571 */       OptimizedMath.rotatePose(matrixStack, angle, (Vector3fc)OptimizedMath.ZP); 
/* 1572 */     Matrix4f matrix = matrixStack.method_23760().method_23761();
/* 1573 */     renderTexturedModalRect(matrix, guiLinearBuffer, -offX, -offY, textureX, textureY, w, h, 256.0F, 256.0F, this.colourBuffer[0], this.colourBuffer[1], this.colourBuffer[2], this.colourBuffer[3]);
/* 1574 */     matrixStack.method_22909();
/*      */   }
/*      */   
/*      */   public static void renderTexturedModalRectWithLighting3(Matrix4f matrix, float x, float y, float width, float height, GpuTexture texture, boolean hasLight, MultiTextureRenderTypeRenderer renderer) {
/* 1578 */     buildTexturedModalRectWithLighting(matrix, renderer.begin(texture), x, y, width, height);
/*      */   }
/*      */   
/*      */   public static void renderTexturedModalSubRectWithLighting(Matrix4f matrix, float x, float y, float textureX1, float textureY1, float textureX2, float textureY2, float width, float height, GpuTexture texture, boolean hasLight, MultiTextureRenderTypeRenderer renderer) {
/* 1582 */     buildTexturedModalSubRectWithLighting(matrix, renderer.begin(texture), x, y, textureX1, textureY1, textureX2, textureY2, width, height);
/*      */   }
/*      */   
/*      */   public static void buildTexturedModalRectWithLighting(Matrix4f matrix, class_287 vertexBuffer, float x, float y, float width, float height) {
/* 1586 */     vertexBuffer.method_22918(matrix, x + 0.0F, y + height, 0.0F).method_22913(0.0F, 1.0F);
/* 1587 */     vertexBuffer.method_22918(matrix, x + width, y + height, 0.0F).method_22913(1.0F, 1.0F);
/* 1588 */     vertexBuffer.method_22918(matrix, x + width, y + 0.0F, 0.0F).method_22913(1.0F, 0.0F);
/* 1589 */     vertexBuffer.method_22918(matrix, x + 0.0F, y + 0.0F, 0.0F).method_22913(0.0F, 0.0F);
/*      */   }
/*      */   
/*      */   public static void buildTexturedModalSubRectWithLighting(Matrix4f matrix, class_287 vertexBuffer, float x, float y, float textureX1, float textureY1, float textureX2, float textureY2, float width, float height) {
/* 1593 */     vertexBuffer.method_22918(matrix, x + 0.0F, y + height, 0.0F).method_22913(textureX1, textureY2);
/* 1594 */     vertexBuffer.method_22918(matrix, x + width, y + height, 0.0F).method_22913(textureX2, textureY2);
/* 1595 */     vertexBuffer.method_22918(matrix, x + width, y + 0.0F, 0.0F).method_22913(textureX2, textureY1);
/* 1596 */     vertexBuffer.method_22918(matrix, x + 0.0F, y + 0.0F, 0.0F).method_22913(textureX1, textureY1);
/*      */   }
/*      */   
/*      */   public static void renderTexturedModalRect(Matrix4f matrix, class_4588 vertexBuffer, float x, float y, int textureX, int textureY, float width, float height, float textureWidth, float textureHeight, float r, float g, float b, float a) {
/* 1600 */     float normalizedTextureX = textureX / textureWidth;
/* 1601 */     float normalizedTextureY = textureY / textureHeight;
/* 1602 */     float normalizedTextureX2 = (textureX + width) / textureWidth;
/* 1603 */     float normalizedTextureY2 = (textureY + height) / textureHeight;
/* 1604 */     vertexBuffer.method_22918(matrix, x + 0.0F, y + height, 0.0F)
/* 1605 */       .method_22915(r, g, b, a)
/* 1606 */       .method_22913(normalizedTextureX, normalizedTextureY2);
/* 1607 */     vertexBuffer.method_22918(matrix, x + width, y + height, 0.0F)
/* 1608 */       .method_22915(r, g, b, a)
/* 1609 */       .method_22913(normalizedTextureX2, normalizedTextureY2);
/* 1610 */     vertexBuffer.method_22918(matrix, x + width, y + 0.0F, 0.0F)
/* 1611 */       .method_22915(r, g, b, a)
/* 1612 */       .method_22913(normalizedTextureX2, normalizedTextureY);
/* 1613 */     vertexBuffer.method_22918(matrix, x + 0.0F, y + 0.0F, 0.0F)
/* 1614 */       .method_22915(r, g, b, a)
/* 1615 */       .method_22913(normalizedTextureX, normalizedTextureY);
/*      */   }
/*      */   
/*      */   public void mapClicked(int button, int x, int y) {
/* 1619 */     if (button == 1)
/* 1620 */       if (this.viewedOnMousePress != null && this.viewedOnMousePress.isRightClickValid() && (!(this.viewedOnMousePress.getElement() instanceof Waypoint) || SupportMods.xaeroMinimap.waypointExists((Waypoint)this.viewedOnMousePress.getElement()))) {
/* 1621 */         handleRightClick((IRightClickableElement)this.viewedOnMousePress, (int)(x / this.screenScale), (int)(y / this.screenScale));
/*      */         
/* 1623 */         this.mouseDownPosX = -1;
/* 1624 */         this.mouseDownPosY = -1;
/* 1625 */         this.mapTileSelection = null;
/*      */       } else {
/* 1627 */         handleRightClick(this, (int)(x / this.screenScale), (int)(y / this.screenScale));
/*      */       }  
/*      */   }
/*      */   
/*      */   private void handleRightClick(IRightClickableElement target, int x, int y) {
/* 1632 */     if (this.rightClickMenu != null)
/* 1633 */       this.rightClickMenu.setClosed(true); 
/* 1634 */     this.rightClickMenu = GuiRightClickMenu.getMenu(target, this, x, y, 150);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean method_25400(class_11905 event) {
/* 1639 */     boolean result = super.method_25400(event);
/* 1640 */     if (this.waypointMenu && SupportMods.xaeroMinimap.getWaypointMenuRenderer().charTyped())
/* 1641 */       return true; 
/* 1642 */     if (this.playersMenu && WorldMap.trackedPlayerMenuRenderer.charTyped()) {
/* 1643 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1649 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean method_25404(class_11908 event) {
/* 1654 */     int par1 = event.comp_4795();
/* 1655 */     int par2 = event.comp_4796();
/* 1656 */     if (par1 == 258) {
/* 1657 */       if (this.tabPressed && SupportMods.minimap() && WorldMap.settings.minimapRadar && (class_310.method_1551()).field_1690.field_1907.method_1417(event))
/* 1658 */         return true; 
/* 1659 */       this.tabPressed = true;
/*      */     } 
/* 1661 */     boolean result = super.method_25404(event);
/* 1662 */     if (isUsingTextField()) {
/* 1663 */       if (this.waypointMenu && SupportMods.xaeroMinimap.getWaypointMenuRenderer().keyPressed(this, par1)) {
/* 1664 */         result = true;
/* 1665 */       } else if (this.playersMenu && WorldMap.trackedPlayerMenuRenderer.keyPressed(this, par1)) {
/* 1666 */         result = true;
/*      */       } 
/*      */     } else {
/* 1669 */       result = (onInputPress((par1 != -1) ? class_3675.class_307.field_1668 : class_3675.class_307.field_1671, (par1 != -1) ? par1 : par2) || result);
/* 1670 */     }  return result;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean method_16803(class_11908 event) {
/* 1675 */     int par1 = event.comp_4795();
/* 1676 */     int par2 = event.comp_4796();
/* 1677 */     if (par1 == 258)
/* 1678 */       this.tabPressed = false; 
/* 1679 */     if (onInputRelease((par1 != -1) ? class_3675.class_307.field_1668 : class_3675.class_307.field_1671, (par1 != -1) ? par1 : par2))
/* 1680 */       return true; 
/* 1681 */     return super.method_16803(event);
/*      */   }
/*      */ 
/*      */   
/*      */   private static long bytesToMb(long bytes) {
/* 1686 */     return bytes / 1024L / 1024L;
/*      */   }
/*      */   
/*      */   private void setColourBuffer(float r, float g, float b, float a) {
/* 1690 */     this.colourBuffer[0] = r;
/* 1691 */     this.colourBuffer[1] = g;
/* 1692 */     this.colourBuffer[2] = b;
/* 1693 */     this.colourBuffer[3] = a;
/*      */   }
/*      */   
/*      */   private boolean isUsingTextField() {
/* 1697 */     class_339 currentFocused = (class_339)method_25399();
/* 1698 */     return (currentFocused != null && currentFocused.method_25370() && currentFocused instanceof net.minecraft.class_342);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void method_25393() {
/* 1704 */     super.method_25393();
/* 1705 */     if (this.waypointMenu)
/* 1706 */       SupportMods.xaeroMinimap.getWaypointMenuRenderer().tick(); 
/* 1707 */     if (this.playersMenu)
/* 1708 */       WorldMap.trackedPlayerMenuRenderer.tick(); 
/* 1709 */     this.caveModeOptions.tick(this);
/*      */   }
/*      */   
/*      */   public class_304 getTrackedPlayerKeyBinding() {
/* 1713 */     if (SupportMods.minimap())
/* 1714 */       return SupportMods.xaeroMinimap.getToggleAllyPlayersKey(); 
/* 1715 */     return ControlsRegister.keyToggleTrackedPlayers;
/*      */   }
/*      */   
/*      */   private boolean onInputPress(class_3675.class_307 type, int code) {
/* 1719 */     if (Misc.inputMatchesKeyBinding(type, code, ControlsRegister.keyOpenSettings, 0)) {
/* 1720 */       onSettingsButton(this.settingsButton);
/* 1721 */       return true;
/*      */     } 
/* 1723 */     boolean result = false;
/* 1724 */     if (Misc.inputMatchesKeyBinding(type, code, this.field_22787.field_1690.field_1907, 0)) {
/* 1725 */       this.field_22787.field_1690.field_1907.method_23481(true);
/* 1726 */       result = true;
/*      */     } 
/* 1728 */     if (Misc.inputMatchesKeyBinding(type, code, ControlsRegister.keyOpenMap, 0)) {
/* 1729 */       goBack();
/* 1730 */       result = true;
/*      */     } 
/* 1732 */     if (Misc.inputMatchesKeyBinding(type, code, getTrackedPlayerKeyBinding(), 0)) {
/* 1733 */       WorldMap.trackedPlayerMenuRenderer.onShowPlayersButton(this, this.field_22789, this.field_22790);
/* 1734 */       return true;
/*      */     } 
/* 1736 */     if (((type == class_3675.class_307.field_1668 && code == 257) || Misc.inputMatchesKeyBinding(type, code, ControlsRegister.keyQuickConfirm, 0)) && this.mapSwitchingGui.active) {
/* 1737 */       this.mapSwitchingGui.confirm(this, this.field_22787, this.field_22789, this.field_22790);
/* 1738 */       result = true;
/*      */     } 
/* 1740 */     if (Misc.inputMatchesKeyBinding(type, code, ControlsRegister.keyToggleDimension, 1)) {
/* 1741 */       onDimensionToggleButton(this.dimensionToggleButton);
/* 1742 */       result = true;
/*      */     } 
/* 1744 */     if (SupportMods.minimap()) {
/* 1745 */       SupportMods.xaeroMinimap.onMapKeyPressed(type, code, this);
/* 1746 */       result = true;
/*      */     } 
/* 1748 */     if (SupportMods.pac()) {
/* 1749 */       result = (SupportMods.xaeroPac.onMapKeyPressed(type, code, this) || result);
/*      */     }
/* 1751 */     IRightClickableElement hoverTarget = getHoverTarget();
/* 1752 */     if (hoverTarget != null && 
/* 1753 */       type == class_3675.class_307.field_1668) {
/* 1754 */       boolean isValid = hoverTarget.isRightClickValid();
/* 1755 */       if (isValid) {
/* 1756 */         if (hoverTarget instanceof HoveredMapElementHolder && ((HoveredMapElementHolder)hoverTarget).getElement() instanceof Waypoint) {
/* 1757 */           switch (code) {
/*      */             case 72:
/* 1759 */               SupportMods.xaeroMinimap.disableWaypoint((Waypoint)((HoveredMapElementHolder)hoverTarget).getElement());
/* 1760 */               closeRightClick();
/* 1761 */               result = true;
/*      */               break;
/*      */             case 261:
/* 1764 */               SupportMods.xaeroMinimap.deleteWaypoint((Waypoint)((HoveredMapElementHolder)hoverTarget).getElement());
/* 1765 */               closeRightClick();
/* 1766 */               result = true;
/*      */               break;
/*      */           } 
/* 1769 */         } else if (SupportMods.pac() && hoverTarget instanceof HoveredMapElementHolder && ((HoveredMapElementHolder)hoverTarget).getElement() instanceof PlayerTrackerMapElement) {
/* 1770 */           switch (code) {
/*      */             case 67:
/* 1772 */               SupportMods.xaeroPac.openPlayerConfigScreen(this, this, (PlayerTrackerMapElement)((HoveredMapElementHolder)hoverTarget).getElement());
/* 1773 */               closeRightClick();
/* 1774 */               result = true;
/*      */               break;
/*      */           } 
/*      */         } 
/*      */       } else {
/* 1779 */         closeRightClick();
/*      */       } 
/*      */     } 
/* 1782 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   private double getCurrentMapCoordinateScale() {
/* 1787 */     return this.mapProcessor.getMapWorld().getCurrentDimension().calculateDimScale(this.mapProcessor.getWorldDimensionTypeRegistry());
/*      */   }
/*      */   
/*      */   private boolean onInputRelease(class_3675.class_307 type, int code) {
/* 1791 */     boolean result = false;
/*      */     
/* 1793 */     if (Misc.inputMatchesKeyBinding(type, code, this.field_22787.field_1690.field_1907, 0)) {
/* 1794 */       this.field_22787.field_1690.field_1907.method_23481(false);
/* 1795 */       result = true;
/*      */     } 
/*      */     
/* 1798 */     if (SupportMods.minimap() && SupportMods.xaeroMinimap.onMapKeyReleased(type, code, this))
/* 1799 */       result = true; 
/* 1800 */     if (SupportMods.minimap() && this.lastViewedDimensionId != null && !isUsingTextField()) {
/* 1801 */       int waypointDestinationX = this.mouseBlockPosX;
/* 1802 */       int waypointDestinationY = this.mouseBlockPosY;
/* 1803 */       int waypointDestinationZ = this.mouseBlockPosZ;
/* 1804 */       double waypointDestinationCoordinateScale = this.mouseBlockCoordinateScale;
/* 1805 */       boolean waypointDestinationRightClick = false;
/* 1806 */       if (this.rightClickMenu != null && this.rightClickMenu.getTarget() == this) {
/* 1807 */         waypointDestinationX = this.rightClickX;
/* 1808 */         waypointDestinationY = this.rightClickY;
/* 1809 */         waypointDestinationZ = this.rightClickZ;
/* 1810 */         waypointDestinationCoordinateScale = this.rightClickCoordinateScale;
/* 1811 */         waypointDestinationRightClick = true;
/*      */       } 
/* 1813 */       if (Misc.inputMatchesKeyBinding(type, code, SupportMods.xaeroMinimap.getWaypointKeyBinding(), 0) && WorldMap.settings.waypoints) {
/* 1814 */         SupportMods.xaeroMinimap.createWaypoint(this, waypointDestinationX, (waypointDestinationY == 32767) ? 32767 : (waypointDestinationY + 1), waypointDestinationZ, waypointDestinationCoordinateScale, waypointDestinationRightClick);
/* 1815 */         closeRightClick();
/* 1816 */         result = true;
/*      */       } 
/* 1818 */       if (Misc.inputMatchesKeyBinding(type, code, SupportMods.xaeroMinimap.getTempWaypointKeyBinding(), 0) && WorldMap.settings.waypoints) {
/* 1819 */         closeRightClick();
/* 1820 */         SupportMods.xaeroMinimap.createTempWaypoint(waypointDestinationX, (waypointDestinationY == 32767) ? 32767 : (waypointDestinationY + 1), waypointDestinationZ, waypointDestinationCoordinateScale, waypointDestinationRightClick);
/* 1821 */         result = true;
/*      */       } 
/*      */       
/* 1824 */       IRightClickableElement hoverTarget = getHoverTarget();
/* 1825 */       if (hoverTarget != null && 
/* 1826 */         !Misc.inputMatchesKeyBinding(type, code, ControlsRegister.keyOpenMap, 0) && 
/* 1827 */         type == class_3675.class_307.field_1668) {
/* 1828 */         boolean isValid = hoverTarget.isRightClickValid();
/* 1829 */         if (isValid) {
/* 1830 */           if (hoverTarget instanceof HoveredMapElementHolder && ((HoveredMapElementHolder)hoverTarget).getElement() instanceof Waypoint) {
/* 1831 */             switch (code) {
/*      */               case 84:
/* 1833 */                 SupportMods.xaeroMinimap.teleportToWaypoint(this, (Waypoint)((HoveredMapElementHolder)hoverTarget).getElement());
/* 1834 */                 closeRightClick();
/* 1835 */                 result = true;
/*      */                 break;
/*      */               case 69:
/* 1838 */                 SupportMods.xaeroMinimap.openWaypoint(this, (Waypoint)((HoveredMapElementHolder)hoverTarget).getElement());
/* 1839 */                 closeRightClick();
/* 1840 */                 result = true;
/*      */                 break;
/*      */             } 
/* 1843 */           } else if (hoverTarget instanceof HoveredMapElementHolder && ((HoveredMapElementHolder)hoverTarget).getElement() instanceof PlayerTrackerMapElement) {
/* 1844 */             switch (code) {
/*      */               case 84:
/* 1846 */                 (new PlayerTeleporter()).teleportToPlayer(this, this.mapProcessor.getMapWorld(), (PlayerTrackerMapElement)((HoveredMapElementHolder)hoverTarget).getElement());
/* 1847 */                 closeRightClick();
/* 1848 */                 result = true;
/*      */                 break;
/*      */             } 
/*      */           } 
/*      */         } else {
/* 1853 */           closeRightClick();
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1858 */     return result;
/*      */   }
/*      */   
/*      */   private IRightClickableElement getHoverTarget() {
/* 1862 */     return (this.rightClickMenu != null) ? this.rightClickMenu.getTarget() : (IRightClickableElement)this.viewed;
/*      */   }
/*      */   
/*      */   private void unfocusAll() {
/* 1866 */     if (SupportMods.minimap())
/* 1867 */       SupportMods.xaeroMinimap.getWaypointMenuRenderer().unfocusAll(); 
/* 1868 */     WorldMap.trackedPlayerMenuRenderer.unfocusAll();
/* 1869 */     this.caveModeOptions.unfocusAll();
/* 1870 */     method_25395(null);
/*      */   }
/*      */   
/*      */   public void closeRightClick() {
/* 1874 */     if (this.rightClickMenu != null) {
/* 1875 */       this.rightClickMenu.setClosed(true);
/*      */     }
/*      */   }
/*      */   
/*      */   public void onRightClickClosed() {
/* 1880 */     this.rightClickMenu = null;
/* 1881 */     this.mapTileSelection = null;
/*      */   }
/*      */   
/*      */   private void closeDropdowns() {
/* 1885 */     if (this.openDropdown != null) {
/* 1886 */       this.openDropdown.setClosed(true);
/*      */     }
/*      */   }
/*      */   
/*      */   public ArrayList<RightClickOption> getRightClickOptions() {
/* 1891 */     ArrayList<RightClickOption> options = new ArrayList<>();
/* 1892 */     options.add(new RightClickOption(this, "gui.xaero_right_click_map_title", options.size(), this)
/*      */         {
/*      */           public void onAction(class_437 screen) {}
/*      */         });
/*      */     
/* 1897 */     if (WorldMap.settings.coordinates && (!SupportMods.minimap() || !SupportMods.xaeroMinimap.hidingWaypointCoordinates())) {
/* 1898 */       if (this.mapTileSelection != null) {
/*      */ 
/*      */ 
/*      */         
/* 1902 */         String chunkOption = (this.mapTileSelection.getStartX() != this.mapTileSelection.getEndX() || this.mapTileSelection.getStartZ() != this.mapTileSelection.getEndZ()) ? String.format("C: (%d;%d):(%d;%d)", new Object[] { Integer.valueOf(this.mapTileSelection.getLeft()), Integer.valueOf(this.mapTileSelection.getTop()), Integer.valueOf(this.mapTileSelection.getRight()), Integer.valueOf(this.mapTileSelection.getBottom()) }) : String.format("C: (%d;%d)", new Object[] { Integer.valueOf(this.mapTileSelection.getLeft()), Integer.valueOf(this.mapTileSelection.getTop()) });
/* 1903 */         options.add(new RightClickOption(this, chunkOption, options.size(), this)
/*      */             {
/*      */               public void onAction(class_437 screen) {}
/*      */             });
/*      */       } 
/*      */       
/* 1909 */       options.add(new RightClickOption(this, String.format((this.rightClickY != 32767) ? "X: %1$d, Y: %2$d, Z: %3$d" : "X: %1$d, Z: %3$d", new Object[] { Integer.valueOf(this.rightClickX), Integer.valueOf(this.rightClickY), Integer.valueOf(this.rightClickZ) }), options.size(), this)
/*      */           {
/*      */             public void onAction(class_437 screen) {}
/*      */           });
/*      */     } 
/*      */     
/* 1915 */     if (SupportMods.minimap() && 
/* 1916 */       WorldMap.settings.waypoints) {
/* 1917 */       options.add((new RightClickOption("gui.xaero_right_click_map_create_waypoint", options.size(), this)
/*      */           {
/*      */             public void onAction(class_437 screen) {
/* 1920 */               SupportMods.xaeroMinimap.createWaypoint(GuiMap.this, GuiMap.this.rightClickX, (GuiMap.this.rightClickY == 32767) ? 32767 : (GuiMap.this.rightClickY + 1), GuiMap.this.rightClickZ, GuiMap.this.rightClickCoordinateScale, true);
/*      */             }
/* 1922 */           }).setNameFormatArgs(new Object[] { Misc.getKeyName(SupportMods.xaeroMinimap.getWaypointKeyBinding()) }));
/* 1923 */       options.add((new RightClickOption("gui.xaero_right_click_map_create_temporary_waypoint", options.size(), this)
/*      */           {
/*      */             public void onAction(class_437 screen) {
/* 1926 */               SupportMods.xaeroMinimap.createTempWaypoint(GuiMap.this.rightClickX, (GuiMap.this.rightClickY == 32767) ? 32767 : (GuiMap.this.rightClickY + 1), GuiMap.this.rightClickZ, GuiMap.this.rightClickCoordinateScale, true);
/*      */             }
/* 1928 */           }).setNameFormatArgs(new Object[] { Misc.getKeyName(SupportMods.xaeroMinimap.getTempWaypointKeyBinding()) }));
/*      */     } 
/*      */     
/* 1931 */     MapDimension currentDimension = this.mapProcessor.getMapWorld().getCurrentDimension();
/* 1932 */     if (!this.field_22787.field_1761.method_2908() || currentDimension != null) {
/* 1933 */       if (this.mapProcessor.getMapWorld().isTeleportAllowed() && (this.rightClickY != 32767 || !this.field_22787.field_1761.method_2908())) {
/* 1934 */         options.add(new RightClickOption("gui.xaero_right_click_map_teleport", options.size(), this)
/*      */             {
/*      */               public void onAction(class_437 screen) {
/* 1937 */                 MapDimension currentDimension = GuiMap.this.mapProcessor.getMapWorld().getCurrentDimension();
/* 1938 */                 if ((!GuiMap.this.field_22787.field_1761.method_2908() || currentDimension != null) && (
/* 1939 */                   GuiMap.this.rightClickY != 32767 || !GuiMap.this.field_22787.field_1761.method_2908())) {
/* 1940 */                   class_5321<class_1937> tpDim = (GuiMap.this.rightClickDim != GuiMap.this.field_22787.field_1687.method_27983()) ? GuiMap.this.rightClickDim : null;
/* 1941 */                   (new MapTeleporter()).teleport(GuiMap.this, GuiMap.this.mapProcessor.getMapWorld(), GuiMap.this.rightClickX, (GuiMap.this.rightClickY == 32767) ? 32767 : (GuiMap.this.rightClickY + 1), GuiMap.this.rightClickZ, tpDim);
/*      */                 }
/*      */               
/*      */               }
/*      */             });
/* 1946 */       } else if (!this.mapProcessor.getMapWorld().isTeleportAllowed()) {
/* 1947 */         options.add(new RightClickOption(this, "gui.xaero_wm_right_click_map_teleport_not_allowed", options.size(), this)
/*      */             {
/*      */               
/*      */               public void onAction(class_437 screen) {}
/*      */             });
/*      */       } else {
/* 1953 */         options.add(new RightClickOption(this, "gui.xaero_right_click_map_cant_teleport", options.size(), this)
/*      */             {
/*      */               
/*      */               public void onAction(class_437 screen) {}
/*      */             });
/*      */       } 
/*      */     } else {
/* 1960 */       options.add(new RightClickOption(this, "gui.xaero_right_click_map_cant_teleport_world", options.size(), this)
/*      */           {
/*      */             public void onAction(class_437 screen) {}
/*      */           });
/*      */     } 
/*      */     
/* 1966 */     if (SupportMods.minimap()) {
/* 1967 */       options.add(new RightClickOption("gui.xaero_right_click_map_share_location", options.size(), this)
/*      */           {
/*      */             public void onAction(class_437 screen) {
/* 1970 */               SupportMods.xaeroMinimap.shareLocation(GuiMap.this, GuiMap.this.rightClickX, (GuiMap.this.rightClickY == 32767) ? 32767 : (GuiMap.this.rightClickY + 1), GuiMap.this.rightClickZ);
/*      */             }
/*      */           });
/* 1973 */       if (WorldMap.settings.waypoints) {
/* 1974 */         options.add((new RightClickOption("gui.xaero_right_click_map_waypoints_menu", options.size(), this)
/*      */             {
/*      */               public void onAction(class_437 screen) {
/* 1977 */                 SupportMods.xaeroMinimap.openWaypointsMenu(GuiMap.this.field_22787, GuiMap.this);
/*      */               }
/* 1979 */             }).setNameFormatArgs(new Object[] { Misc.getKeyName(SupportMods.xaeroMinimap.getTempWaypointsMenuKeyBinding()) }));
/*      */       }
/*      */     } 
/* 1982 */     if (SupportMods.pac()) {
/* 1983 */       SupportMods.xaeroPac.addRightClickOptions(this, options, this.mapTileSelection, this.mapProcessor);
/*      */     }
/* 1985 */     options.add(new RightClickOption("gui.xaero_right_click_box_map_export", options.size(), this)
/*      */         {
/*      */           public void onAction(class_437 screen) {
/* 1988 */             GuiMap.this.onExportButton(GuiMap.this.exportButton);
/*      */           }
/*      */         });
/* 1991 */     options.add((new RightClickOption("gui.xaero_right_click_box_map_settings", options.size(), this)
/*      */         {
/*      */           public void onAction(class_437 screen) {
/* 1994 */             GuiMap.this.onSettingsButton(GuiMap.this.settingsButton);
/*      */           }
/* 1996 */         }).setNameFormatArgs(new Object[] { Misc.getKeyName(ControlsRegister.keyOpenSettings) }));
/* 1997 */     return options;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isRightClickValid() {
/* 2002 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getRightClickTitleBackgroundColor() {
/* 2007 */     return -10461088;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean shouldSkipWorldRender() {
/* 2012 */     return true;
/*      */   }
/*      */   
/*      */   public double getUserScale() {
/* 2016 */     return this.userScale;
/*      */   }
/*      */   
/*      */   public class_4185 getRadarButton() {
/* 2020 */     return this.radarButton;
/*      */   }
/*      */ 
/*      */   
/*      */   public void onDropdownOpen(DropDownWidget menu) {
/* 2025 */     super.onDropdownOpen(menu);
/* 2026 */     unfocusAll();
/*      */   }
/*      */ 
/*      */   
/*      */   public void onDropdownClosed(DropDownWidget menu) {
/* 2031 */     super.onDropdownClosed(menu);
/* 2032 */     if (menu == this.rightClickMenu)
/* 2033 */       onRightClickClosed(); 
/*      */   }
/*      */   
/*      */   public void onCaveModeStartSet() {
/* 2037 */     this.caveModeOptions.onCaveModeStartSet(this);
/*      */   }
/*      */   
/*      */   public MapDimension getFutureDimension() {
/* 2041 */     return this.futureDimension;
/*      */   }
/*      */   
/*      */   public MapProcessor getMapProcessor() {
/* 2045 */     return this.mapProcessor;
/*      */   }
/*      */   
/*      */   public void enableCaveModeOptions() {
/* 2049 */     if (!this.caveModeOptions.isEnabled())
/* 2050 */       this.caveModeOptions.toggle(this); 
/*      */   }
/*      */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\gui\GuiMap.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */