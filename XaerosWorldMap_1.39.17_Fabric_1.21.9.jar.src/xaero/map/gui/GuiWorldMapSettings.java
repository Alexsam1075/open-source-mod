/*    */ package xaero.map.gui;
/*    */ 
/*    */ import net.minecraft.class_2561;
/*    */ import net.minecraft.class_332;
/*    */ import net.minecraft.class_339;
/*    */ import net.minecraft.class_364;
/*    */ import net.minecraft.class_437;
/*    */ import xaero.map.WorldMap;
/*    */ import xaero.map.controls.ControlsRegister;
/*    */ import xaero.map.misc.Misc;
/*    */ import xaero.map.mods.SupportMods;
/*    */ import xaero.map.settings.ModOptions;
/*    */ import xaero.map.settings.ModSettings;
/*    */ 
/*    */ public class GuiWorldMapSettings extends GuiSettings {
/* 16 */   public static final CursorBox PLAYER_TELEPORT_COMMAND_TOOLTIP = new CursorBox("gui.xaero_wm_box_player_teleport_command");
/*    */ 
/*    */   
/*    */   public GuiWorldMapSettings() {
/* 20 */     this((class_437)null);
/*    */   }
/*    */   
/*    */   public GuiWorldMapSettings(class_437 parent) {
/* 24 */     this(parent, (class_437)null);
/*    */   }
/*    */   
/*    */   public GuiWorldMapSettings(class_437 parent, class_437 escapeScreen) {
/* 28 */     super((class_2561)class_2561.method_43471("gui.xaero_world_map_settings"), parent, escapeScreen);
/*    */ 
/*    */     
/* 31 */     ScreenSwitchSettingEntry minimapEntry = new ScreenSwitchSettingEntry("gui.xaero_wm_minimap_settings", (current, escape) -> SupportMods.xaeroMinimap.openSettingsScreen(this.field_22787, this, escape), !SupportMods.minimap() ? ModOptions.REQUIRES_MINIMAP : null, SupportMods.minimap());
/* 32 */     ScreenSwitchSettingEntry resetEntry = new ScreenSwitchSettingEntry("gui.xaero_wm_reset_defaults", (current, escape) -> new ConfirmScreenBase(current, escape, true, (), (class_2561)class_2561.method_43471("gui.xaero_wm_reset_message"), (class_2561)class_2561.method_43471("gui.xaero_wm_reset_message2")), null, true);
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 37 */     ScreenSwitchSettingEntry mapTeleportCommandEntry = new ScreenSwitchSettingEntry("gui.xaero_wm_teleport_command", (current, escape) -> new GuiMapTpCommand(current, escape), !ModSettings.canEditIngameSettings() ? ModOptions.REQUIRES_INGAME : null, ModSettings.canEditIngameSettings());
/*    */     
/* 39 */     ScreenSwitchSettingEntry pacTeleportCommandEntry = new ScreenSwitchSettingEntry("gui.xaero_wm_player_teleport_command", (current, escape) -> new GuiPlayerTpCommand(current, escape), !ModSettings.canEditIngameSettings() ? ModOptions.REQUIRES_INGAME : PLAYER_TELEPORT_COMMAND_TOOLTIP, ModSettings.canEditIngameSettings());
/* 40 */     this.entries = new ISettingEntry[] { new ConfigSettingEntry(ModOptions.LIGHTING), new ConfigSettingEntry(ModOptions.COLOURS), new ConfigSettingEntry(ModOptions.LOAD), new ConfigSettingEntry(ModOptions.UPDATE), new ConfigSettingEntry(ModOptions.DEPTH), new ConfigSettingEntry(ModOptions.SLOPES), new ConfigSettingEntry(ModOptions.STEPS), new ConfigSettingEntry(ModOptions.COORDINATES), new ConfigSettingEntry(ModOptions.WAYPOINTS), new ConfigSettingEntry(ModOptions.WAYPOINT_BACKGROUNDS), new ConfigSettingEntry(ModOptions.WAYPOINT_SCALE), minimapEntry, new ConfigSettingEntry(ModOptions.BIOME_BLENDING), new ConfigSettingEntry(ModOptions.BIOMES), new ConfigSettingEntry(ModOptions.MIN_ZOOM_LOCAL_WAYPOINTS), new ConfigSettingEntry(ModOptions.ADJUST_HEIGHT_FOR_SHORT_BLOCKS), new ConfigSettingEntry(ModOptions.FLOWERS), new ConfigSettingEntry(ModOptions.DISPLAY_STAINED_GLASS), new ConfigSettingEntry(ModOptions.IGNORE_HEIGHTMAPS), new ConfigSettingEntry(ModOptions.CAVE_MODE_DEPTH), new ConfigSettingEntry(ModOptions.AUTO_CAVE_MODE), new ConfigSettingEntry(ModOptions.LEGIBLE_CAVE_MAPS), new ConfigSettingEntry(ModOptions.CAVE_MODE_TOGGLE_TIMER), new ConfigSettingEntry(ModOptions.DEFAULT_CAVE_MODE_TYPE), new ConfigSettingEntry(ModOptions.DISPLAY_CAVE_MODE_START), new ConfigSettingEntry(ModOptions.MAP_WRITING_DISTANCE), new ConfigSettingEntry(ModOptions.ARROW), new ConfigSettingEntry(ModOptions.OPEN_ANIMATION), new ConfigSettingEntry(ModOptions.ARROW_COLOUR), new ConfigSettingEntry(ModOptions.DISPLAY_ZOOM), new ConfigSettingEntry(ModOptions.HOVERED_BIOME), new ConfigSettingEntry(ModOptions.ZOOM_BUTTONS), new ConfigSettingEntry(ModOptions.DETECT_AMBIGUOUS_Y), mapTeleportCommandEntry, new ConfigSettingEntry(ModOptions.MAP_TELEPORT_ALLOWED), pacTeleportCommandEntry, new ConfigSettingEntry(ModOptions.PARTIAL_Y_TELEPORTATION), new ConfigSettingEntry(ModOptions.PAC_CLAIMS), new ConfigSettingEntry(ModOptions.PAC_CLAIMS_BORDER_OPACITY), new ConfigSettingEntry(ModOptions.PAC_CLAIMS_FILL_OPACITY), new ConfigSettingEntry(ModOptions.UPDATE_NOTIFICATION), new ConfigSettingEntry(ModOptions.RELOAD), new ConfigSettingEntry(ModOptions.FULL_RELOAD), new ConfigSettingEntry(ModOptions.FULL_RESAVE), new ConfigSettingEntry(ModOptions.DEBUG), resetEntry };
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void method_25426() {
/* 69 */     super.method_25426();
/* 70 */     CursorBox closeSettingsTooltip = new CursorBox((class_2561)class_2561.method_43469("gui.xaero_box_close_settings", new Object[] { Misc.getKeyName(ControlsRegister.keyOpenSettings) }));
/* 71 */     if (this.parent instanceof GuiMap) {
/* 72 */       method_37063((class_364)new GuiTexturedButton(0, 0, 30, 30, 113, 0, 20, 20, WorldMap.guiTextures, this::onSettingsButton, () -> closeSettingsTooltip, 256, 256));
/*    */     }
/*    */   }
/*    */   
/*    */   public void method_25420(class_332 guiGraphics, int i, int j, float f) {
/* 77 */     renderEscapeScreen(guiGraphics, 0, 0, f);
/* 78 */     super.method_25420(guiGraphics, i, j, f);
/*    */   }
/*    */   
/*    */   private void onSettingsButton(class_339 button) {
/* 82 */     goBack();
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\gui\GuiWorldMapSettings.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */