/*     */ package xaero.map.settings;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.stream.Collector;
/*     */ import java.util.stream.Collectors;
/*     */ import java.util.stream.IntStream;
/*     */ import net.minecraft.class_1074;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_3532;
/*     */ import xaero.map.WorldMap;
/*     */ import xaero.map.gui.CursorBox;
/*     */ import xaero.map.mods.SupportMods;
/*     */ 
/*     */ public class ModOptions
/*     */ {
/*  19 */   public static final CursorBox REQUIRES_MINIMAP = new CursorBox("gui.xaero_wm_option_requires_minimap");
/*  20 */   public static final CursorBox REQUIRES_INGAME = new CursorBox("gui.xaero_wm_option_requires_ingame");
/*     */   
/*     */   public static ModOptions DEBUG;
/*     */   
/*     */   public static ModOptions COLOURS;
/*     */   
/*     */   public static ModOptions LIGHTING;
/*     */   
/*     */   public static ModOptions UPDATE;
/*     */   
/*     */   public static ModOptions LOAD;
/*     */   
/*     */   public static ModOptions DEPTH;
/*     */   
/*     */   public static ModOptions SLOPES;
/*     */   
/*     */   public static ModOptions STEPS;
/*     */   
/*     */   public static ModOptions FLOWERS;
/*     */   
/*     */   public static ModOptions COORDINATES;
/*     */   
/*     */   public static ModOptions HOVERED_BIOME;
/*     */   
/*     */   public static ModOptions BIOMES;
/*     */   
/*     */   public static ModOptions WAYPOINTS;
/*     */   
/*     */   public static ModOptions ARROW;
/*     */   
/*     */   public static ModOptions DISPLAY_ZOOM;
/*     */   
/*     */   public static ModOptions IGNORE_HEIGHTMAPS;
/*     */   
/*     */   public static ModOptions WAYPOINT_SCALE;
/*     */   
/*     */   public static ModOptions OPEN_ANIMATION;
/*     */   
/*     */   public static ModOptions RELOAD;
/*     */   
/*     */   public static ModOptions ZOOM_BUTTONS;
/*     */   
/*     */   public static ModOptions WAYPOINT_BACKGROUNDS;
/*     */   
/*     */   public static ModOptions PAUSE_REQUESTS;
/*     */   public static ModOptions EXTRA_DEBUG;
/*     */   public static ModOptions DETECT_AMBIGUOUS_Y;
/*     */   public static ModOptions UPDATE_NOTIFICATION;
/*     */   public static ModOptions ADJUST_HEIGHT_FOR_SHORT_BLOCKS;
/*     */   public static ModOptions MIN_ZOOM_LOCAL_WAYPOINTS;
/*     */   public static ModOptions ARROW_COLOUR;
/*     */   public static ModOptions PAC_CLAIMS;
/*     */   public static ModOptions PAC_CLAIMS_BORDER_OPACITY;
/*     */   
/*     */   public static void init() {
/*  75 */     DEBUG = new ModOptions("gui.xaero_debug", false, false, false);
/*  76 */     COLOURS = new ModOptions("gui.xaero_block_colours", 2, false, false, false);
/*  77 */     LIGHTING = new ModOptions("gui.xaero_lighting", false, false, false);
/*  78 */     UPDATE = new ModOptions("gui.xaero_update_chunks", false, false, false);
/*  79 */     LOAD = new ModOptions("gui.xaero_load_chunks", false, false, false);
/*  80 */     DEPTH = new ModOptions("gui.xaero_terrain_depth", false, false, false);
/*  81 */     SLOPES = new ModOptions("gui.xaero_terrain_slopes", 4, false, false, false);
/*  82 */     STEPS = new ModOptions("gui.xaero_footsteps", false, false, false);
/*  83 */     FLOWERS = new ModOptions("gui.xaero_flowers", false, false, false);
/*  84 */     COORDINATES = new ModOptions("gui.xaero_wm_coordinates", false, false, false);
/*  85 */     HOVERED_BIOME = new ModOptions("gui.xaero_wm_hovered_biome", false, false, false);
/*  86 */     BIOMES = new ModOptions("gui.xaero_biome_colors", false, false, false);
/*  87 */     WAYPOINTS = new ModOptions("gui.xaero_worldmap_waypoints", false, true, false);
/*  88 */     ARROW = new ModOptions("gui.xaero_render_arrow", false, false, false);
/*  89 */     DISPLAY_ZOOM = new ModOptions("gui.xaero_display_zoom", false, false, false);
/*  90 */     IGNORE_HEIGHTMAPS = new ModOptions("gui.xaero_wm_ignore_heightmaps", new CursorBox("gui.xaero_wm_box_ignore_heightmaps"), true, false, false);
/*  91 */     WAYPOINT_SCALE = new ModOptions("gui.xaero_wm_waypoint_scale", 0.5D, 5.0D, 0.5D, false, true, false);
/*  92 */     OPEN_ANIMATION = new ModOptions("gui.xaero_open_map_animation", false, false, false);
/*  93 */     RELOAD = new ModOptions("gui.xaero_reload_viewed_regions", new CursorBox("gui.xaero_box_reload_viewed_regions"), false, false, false);
/*  94 */     ZOOM_BUTTONS = new ModOptions("gui.xaero_zoom_buttons", false, false, false);
/*  95 */     WAYPOINT_BACKGROUNDS = new ModOptions("gui.xaero_waypoint_backgrounds", false, true, false);
/*  96 */     PAUSE_REQUESTS = new ModOptions("pause_requests", false, false, false);
/*  97 */     EXTRA_DEBUG = new ModOptions("extra_debug", false, false, false);
/*  98 */     DETECT_AMBIGUOUS_Y = new ModOptions("gui.xaero_wm_detect_ambiguous_y", new CursorBox("gui.xaero_wm_box_detect_ambiguous_y"), false, false, false);
/*  99 */     UPDATE_NOTIFICATION = new ModOptions("gui.xaero_wm_update_notification", false, false, false);
/* 100 */     ADJUST_HEIGHT_FOR_SHORT_BLOCKS = new ModOptions("gui.xaero_wm_adjust_height_for_carpetlike_blocks", new CursorBox("gui.xaero_wm_box_adjust_height_for_carpetlike_blocks"), false, false, false);
/* 101 */     MIN_ZOOM_LOCAL_WAYPOINTS = new ModOptions("gui.xaero_wm_min_zoom_local_waypoints", 0.0D, 3.0D, 0.01D, false, true, false);
/* 102 */     ARROW_COLOUR = new ModOptions("gui.xaero_wm_arrow_colour", ModSettings.arrowColours.length + 2, new CursorBox("gui.xaero_wm_box_arrow_color"), false, false, false);
/* 103 */     PAC_CLAIMS = new ModOptions("gui.xaero_wm_pac_claims", new CursorBox("gui.xaero_wm_box_pac_claims"), false, false, true);
/* 104 */     PAC_CLAIMS_FILL_OPACITY = new ModOptions("gui.xaero_wm_pac_claims_fill_opacity", 1.0D, 100.0D, 1.0D, new CursorBox("gui.xaero_wm_box_pac_claims_fill_opacity"), false, false, true);
/* 105 */     PAC_CLAIMS_BORDER_OPACITY = new ModOptions("gui.xaero_wm_pac_claims_border_opacity", 1.0D, 100.0D, 1.0D, new CursorBox("gui.xaero_wm_box_pac_claims_border_opacity"), false, false, true);
/* 106 */     MAP_TELEPORT_ALLOWED = new ModOptions("gui.xaero_wm_teleport_allowed", new CursorBox("gui.xaero_wm_teleport_allowed_tooltip"), true, false, false);
/* 107 */     PARTIAL_Y_TELEPORTATION = new ModOptions("gui.xaero_wm_partial_y_teleportation", new CursorBox("gui.xaero_wm_box_partial_y_teleportation"), false, false, false);
/* 108 */     DISPLAY_STAINED_GLASS = new ModOptions("gui.xaero_wm_display_stained_glass", false, false, false);
/* 109 */     CAVE_MODE_DEPTH = new ModOptions("gui.xaero_wm_cave_mode_depth", 1.0D, 64.0D, 1.0D, false, false, false);
/* 110 */     CAVE_MODE_START = new ModOptions("gui.xaero_wm_cave_mode_start", -65.0D, 319.0D, 1.0D, false, false, false);
/* 111 */     LEGIBLE_CAVE_MAPS = new ModOptions("gui.xaero_wm_legible_cave_maps", new CursorBox("gui.xaero_wm_box_legible_cave_maps"), false, false, false);
/* 112 */     AUTO_CAVE_MODE = new ModOptions("gui.xaero_auto_cave_mode", 5, new CursorBox("gui.xaero_box_auto_cave_mode"), false, false, false);
/* 113 */     DISPLAY_CAVE_MODE_START = new ModOptions("gui.xaero_wm_display_cave_mode_start", false, false, false);
/* 114 */     CAVE_MODE_TOGGLE_TIMER = new ModOptions("gui.xaero_wm_cave_mode_toggle_timer", 0.0D, 10000.0D, 100.0D, new CursorBox("gui.xaero_wm_box_cave_mode_toggle_timer"), false, false, false);
/* 115 */     DEFAULT_CAVE_MODE_TYPE = new ModOptions("gui.xaero_wm_default_cave_mode_type", 3, new CursorBox("gui.xaero_wm_box_default_cave_mode_type"), false, false, false);
/* 116 */     BIOME_BLENDING = new ModOptions("gui.xaero_wm_biome_blending", new CursorBox("gui.xaero_wm_box_biome_blending"), false, false, false);
/* 117 */     FULL_EXPORT = new ModOptions("gui.xaero_export_option_full", new CursorBox("gui.xaero_box_export_option_full"), false, false, false);
/* 118 */     MULTIPLE_IMAGES_EXPORT = new ModOptions("gui.xaero_export_option_multiple_images", new CursorBox("gui.xaero_box_export_option_multiple_images"), false, false, false);
/* 119 */     NIGHT_EXPORT = new ModOptions("gui.xaero_export_option_nighttime", new CursorBox("gui.xaero_box_export_option_nighttime"), false, false, false);
/* 120 */     EXPORT_SCALE_DOWN_SQUARE = new ModOptions("gui.xaero_export_option_scale_down_square", 0.0D, 90.0D, 1.0D, new CursorBox("gui.xaero_box_export_option_scale_down_square"), false, false, false);
/* 121 */     EXPORT_HIGHLIGHTS = new ModOptions("gui.xaero_export_option_highlights", new CursorBox("gui.xaero_box_export_option_highlights"), false, false, false);
/* 122 */     MAP_WRITING_DISTANCE = new ModOptions("gui.xaero_map_writing_distance", -1.0D, 32.0D, 1.0D, new CursorBox("gui.xaero_box_map_writing_distance"), false, false, false);
/* 123 */     FULL_RELOAD = new ModOptions("gui.xaero_full_reload", new CursorBox("gui.xaero_box_full_reload"), true, false, false);
/* 124 */     FULL_RESAVE = new ModOptions("gui.xaero_full_resave", new CursorBox("gui.xaero_box_full_resave"), true, false, false);
/*     */   }
/*     */   public static ModOptions PAC_CLAIMS_FILL_OPACITY; public static ModOptions MAP_TELEPORT_ALLOWED; public static ModOptions PARTIAL_Y_TELEPORTATION; public static ModOptions DISPLAY_STAINED_GLASS; public static ModOptions CAVE_MODE_DEPTH; public static ModOptions CAVE_MODE_START; public static ModOptions LEGIBLE_CAVE_MAPS; public static ModOptions AUTO_CAVE_MODE; public static ModOptions DISPLAY_CAVE_MODE_START; public static ModOptions CAVE_MODE_TOGGLE_TIMER; public static ModOptions DEFAULT_CAVE_MODE_TYPE; public static ModOptions BIOME_BLENDING; public static ModOptions FULL_EXPORT; public static ModOptions MULTIPLE_IMAGES_EXPORT; public static ModOptions NIGHT_EXPORT; public static ModOptions EXPORT_SCALE_DOWN_SQUARE; public static ModOptions EXPORT_HIGHLIGHTS; public static ModOptions MAP_WRITING_DISTANCE; public static ModOptions FULL_RELOAD;
/*     */   public static ModOptions FULL_RESAVE;
/*     */   private final boolean enumDouble;
/*     */   final boolean enumBoolean;
/*     */   private final String enumString;
/*     */   private double valueMin;
/*     */   private double valueMax;
/*     */   private double valueStep;
/*     */   private Option xOption;
/*     */   private CursorBox tooltip;
/*     */   private boolean ingameOnly;
/*     */   private boolean requiresMinimap;
/*     */   private boolean requiresPac;
/*     */   
/*     */   private ModOptions(String par3Str, boolean ingameOnly, boolean requiresMinimap, boolean requiresPac) {
/* 141 */     this(par3Str, (CursorBox)null, ingameOnly, requiresMinimap, requiresPac);
/*     */   }
/*     */   
/*     */   private ModOptions(String par3Str, CursorBox tooltip, boolean ingameOnly, boolean requiresMinimap, boolean requiresPac) {
/* 145 */     this(par3Str, true, () -> Lists.newArrayList((Object[])new Boolean[] { Boolean.valueOf(false), Boolean.valueOf(true) }, ), tooltip, ingameOnly, requiresMinimap, requiresPac);
/*     */   }
/*     */   
/*     */   private ModOptions(String par3Str, int optionCount, boolean ingameOnly, boolean requiresMinimap, boolean requiresPac) {
/* 149 */     this(par3Str, optionCount, null, ingameOnly, requiresMinimap, requiresPac);
/*     */   }
/*     */   
/*     */   private ModOptions(String par3Str, int optionCount, CursorBox tooltip, boolean ingameOnly, boolean requiresMinimap, boolean requiresPac) {
/* 153 */     this(par3Str, false, () -> (List<Integer>)IntStream.rangeClosed(0, optionCount - 1).boxed().collect((Collector)Collectors.toList()), tooltip, ingameOnly, requiresMinimap, requiresPac);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private <T> ModOptions(String par3Str, boolean isBoolean, Supplier<List<T>> optionsListSupplier, CursorBox tooltip, boolean ingameOnly, boolean requiresMinimap, boolean requiresPac) {
/* 160 */     this.enumString = par3Str;
/* 161 */     this.enumBoolean = isBoolean;
/* 162 */     this.enumDouble = false;
/*     */     
/* 164 */     Supplier<T> valueGetter = () -> WorldMap.settings.getOptionValue(this);
/* 165 */     Consumer<T> valueSetter = v -> WorldMap.settings.setOptionValue(this, v);
/* 166 */     this.xOption = new XaeroCyclingOption<>(this, optionsListSupplier.get(), valueGetter, valueSetter, () -> class_2561.method_43470(WorldMap.settings.getOptionValueName(this)));
/* 167 */     this.tooltip = tooltip;
/* 168 */     this.ingameOnly = ingameOnly;
/* 169 */     this.requiresMinimap = requiresMinimap;
/*     */   }
/*     */ 
/*     */   
/*     */   private ModOptions(String p_i45004_3_, double p_i45004_6_, double p_i45004_7_, double p_i45004_8_, boolean ingameOnly, boolean requiresMinimap, boolean requiresPac) {
/* 174 */     this(p_i45004_3_, p_i45004_6_, p_i45004_7_, p_i45004_8_, null, ingameOnly, requiresMinimap, requiresPac);
/*     */   }
/*     */ 
/*     */   
/*     */   private ModOptions(String p_i45004_3_, double p_i45004_6_, double p_i45004_7_, double p_i45004_8_, CursorBox tooltip, boolean ingameOnly, boolean requiresMinimap, boolean requiresPac) {
/* 179 */     this.enumString = p_i45004_3_;
/* 180 */     this.enumBoolean = false;
/* 181 */     this.enumDouble = true;
/* 182 */     this.valueMin = p_i45004_6_;
/* 183 */     this.valueMax = p_i45004_7_;
/* 184 */     this.valueStep = p_i45004_8_;
/* 185 */     this.xOption = new XaeroDoubleOption(this, p_i45004_6_, p_i45004_7_, (float)p_i45004_8_, () -> Double.valueOf(WorldMap.settings.getOptionDoubleValue(this)), value -> WorldMap.settings.setOptionDoubleValue(this, value.doubleValue()), () -> class_2561.method_43470(WorldMap.settings.getSliderOptionText(this)));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 192 */     this.tooltip = tooltip;
/* 193 */     this.ingameOnly = ingameOnly;
/* 194 */     this.requiresMinimap = requiresMinimap;
/* 195 */     this.requiresPac = requiresPac;
/*     */   }
/*     */   
/*     */   public boolean getEnumDouble() {
/* 199 */     return this.enumDouble;
/*     */   }
/*     */   
/*     */   public boolean getEnumBoolean() {
/* 203 */     return this.enumBoolean;
/*     */   }
/*     */   
/*     */   public double getValueMax() {
/* 207 */     return this.valueMax;
/*     */   }
/*     */   
/*     */   public void setValueMax(float p_148263_1_) {
/* 211 */     this.valueMax = p_148263_1_;
/*     */   }
/*     */   
/*     */   public double normalizeValue(double p_148266_1_) {
/* 215 */     return class_3532.method_15350((
/* 216 */         snapToStepClamp(p_148266_1_) - this.valueMin) / (this.valueMax - this.valueMin), 0.0D, 1.0D);
/*     */   }
/*     */   
/*     */   public double denormalizeValue(double p_148262_1_) {
/* 220 */     return snapToStepClamp(this.valueMin + (this.valueMax - this.valueMin) * 
/* 221 */         class_3532.method_15350(p_148262_1_, 0.0D, 1.0D));
/*     */   }
/*     */   
/*     */   public double snapToStepClamp(double p_148268_1_) {
/* 225 */     p_148268_1_ = snapToStep(p_148268_1_);
/* 226 */     return class_3532.method_15350(p_148268_1_, this.valueMin, this.valueMax);
/*     */   }
/*     */   
/*     */   protected double snapToStep(double p_148264_1_) {
/* 230 */     if (this.valueStep > 0.0D) {
/* 231 */       p_148264_1_ = this.valueStep * (float)Math.round(p_148264_1_ / this.valueStep);
/*     */     }
/*     */     
/* 234 */     return p_148264_1_;
/*     */   }
/*     */   
/*     */   public String getEnumString() {
/* 238 */     return class_1074.method_4662(this.enumString, new Object[0]);
/*     */   }
/*     */   
/*     */   public String getEnumStringRaw() {
/* 242 */     return this.enumString;
/*     */   }
/*     */   
/*     */   public Option getXOption() {
/* 246 */     return this.xOption;
/*     */   }
/*     */   
/*     */   public CursorBox getTooltip() {
/* 250 */     if (isDisabledBecauseNotIngame())
/* 251 */       return REQUIRES_INGAME; 
/* 252 */     if (isDisabledBecauseMinimap())
/* 253 */       return REQUIRES_MINIMAP; 
/* 254 */     return this.tooltip;
/*     */   }
/*     */   
/*     */   public boolean isIngameOnly() {
/* 258 */     return this.ingameOnly;
/*     */   }
/*     */   
/*     */   public boolean requiresMinimap() {
/* 262 */     return this.requiresMinimap;
/*     */   }
/*     */   
/*     */   public boolean requiresPac() {
/* 266 */     return this.requiresPac;
/*     */   }
/*     */   
/*     */   public boolean isDisabledBecauseNotIngame() {
/* 270 */     return (isIngameOnly() && !ModSettings.canEditIngameSettings());
/*     */   }
/*     */   
/*     */   public boolean isDisabledBecauseMinimap() {
/* 274 */     return (requiresMinimap() && !SupportMods.minimap());
/*     */   }
/*     */   
/*     */   public double getValueMin() {
/* 278 */     return this.valueMin;
/*     */   }
/*     */   
/*     */   public boolean isDisabledBecausePac() {
/* 282 */     return (requiresPac() && !SupportMods.pac());
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\settings\ModOptions.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */