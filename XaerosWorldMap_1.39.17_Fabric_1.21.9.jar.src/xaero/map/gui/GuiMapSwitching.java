/*     */ package xaero.map.gui;
/*     */ import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.function.Supplier;
/*     */ import net.minecraft.class_1074;
/*     */ import net.minecraft.class_1937;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_339;
/*     */ import net.minecraft.class_364;
/*     */ import net.minecraft.class_410;
/*     */ import net.minecraft.class_4185;
/*     */ import net.minecraft.class_437;
/*     */ import net.minecraft.class_4588;
/*     */ import net.minecraft.class_4597;
/*     */ import net.minecraft.class_5321;
/*     */ import xaero.map.MapProcessor;
/*     */ import xaero.map.WorldMap;
/*     */ import xaero.map.graphics.CustomRenderTypes;
/*     */ import xaero.map.gui.dropdown.DropDownWidget;
/*     */ import xaero.map.misc.KeySortableByOther;
/*     */ import xaero.map.world.MapConnectionManager;
/*     */ import xaero.map.world.MapConnectionNode;
/*     */ import xaero.map.world.MapDimension;
/*     */ 
/*     */ public class GuiMapSwitching {
/*  30 */   private static final class_2561 CONNECT_MAP = (class_2561)class_2561.method_43471("gui.xaero_connect_map");
/*  31 */   private static final class_2561 DISCONNECT_MAP = (class_2561)class_2561.method_43471("gui.xaero_disconnect_map");
/*     */   
/*     */   private MapProcessor mapProcessor;
/*     */   private MapDimension settingsDimension;
/*     */   private String[] mwDropdownValues;
/*     */   private DropDownWidget createdDimensionDropdown;
/*     */   private DropDownWidget createdMapDropdown;
/*     */   private class_4185 switchingButton;
/*     */   private class_4185 multiworldTypeOptionButton;
/*     */   private class_4185 renameButton;
/*     */   private class_4185 connectButton;
/*     */   private class_4185 deleteButton;
/*     */   private class_4185 confirmButton;
/*  44 */   private CursorBox serverSelectionModeBox = new CursorBox("gui.xaero_mw_server_box");
/*  45 */   private CursorBox mapSelectionBox = new CursorBox("gui.xaero_map_selection_box");
/*     */   public boolean active;
/*     */   private boolean writableOnInit;
/*     */   private boolean uiPausedOnUpdate;
/*     */   private boolean mapSwitchingAllowed;
/*     */   
/*     */   public GuiMapSwitching(MapProcessor mapProcessor) {
/*  52 */     this.mapProcessor = mapProcessor;
/*  53 */     this.mapSelectionBox.setStartWidth(200);
/*  54 */     this.serverSelectionModeBox.setStartWidth(200);
/*     */   }
/*     */   
/*     */   public void init(GuiMap mapScreen, class_310 minecraft, int width, int height) {
/*  58 */     boolean dimensionDDWasOpen = (this.createdDimensionDropdown != null && !this.createdDimensionDropdown.isClosed());
/*  59 */     boolean mapDDWasOpen = (this.createdMapDropdown != null && !this.createdMapDropdown.isClosed());
/*  60 */     this.createdDimensionDropdown = null;
/*  61 */     this.createdMapDropdown = null;
/*  62 */     this.switchingButton = null;
/*  63 */     this.multiworldTypeOptionButton = null;
/*  64 */     this.renameButton = null;
/*  65 */     this.deleteButton = null;
/*  66 */     this.confirmButton = null;
/*  67 */     this.settingsDimension = this.mapProcessor.getMapWorld().getFutureDimension();
/*  68 */     this.mapSwitchingAllowed = (this.settingsDimension != null);
/*  69 */     synchronized (this.mapProcessor.uiPauseSync) {
/*  70 */       this.uiPausedOnUpdate = isUIPaused();
/*  71 */       mapScreen.addButton(this.switchingButton = new GuiMapSwitchingButton(this.active, 0, height - 20, b -> {
/*     */               synchronized (this.mapProcessor.uiPauseSync) {
/*     */                 if (!canToggleThisScreen())
/*     */                   return; 
/*     */                 this.active = !this.active;
/*     */                 mapScreen.method_25423(minecraft, width, height);
/*     */                 mapScreen.method_25395((class_364)this.switchingButton);
/*     */               } 
/*     */             }));
/*  80 */       if (this.mapSwitchingAllowed) {
/*  81 */         this.writableOnInit = this.settingsDimension.futureMultiworldWritable;
/*  82 */         if (this.active) {
/*  83 */           this.createdDimensionDropdown = createDimensionDropdown(this.uiPausedOnUpdate, width, mapScreen, minecraft);
/*  84 */           this.createdMapDropdown = createMapDropdown(this.uiPausedOnUpdate, width, mapScreen, minecraft);
/*  85 */           mapScreen.method_25429(this.createdDimensionDropdown);
/*  86 */           mapScreen.method_25429(this.createdMapDropdown);
/*  87 */           if (dimensionDDWasOpen)
/*  88 */             this.createdDimensionDropdown.setClosed(false); 
/*  89 */           if (mapDDWasOpen) {
/*  90 */             this.createdMapDropdown.setClosed(false);
/*     */           }
/*  92 */           mapScreen.addButton(this
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/*  99 */               .multiworldTypeOptionButton = new TooltipButton(width / 2 - 90, 24, 180, 20, (class_2561)class_2561.method_43470(getMultiworldTypeButtonMessage()), b -> { synchronized (this.mapProcessor.uiPauseSync) { if (isMapSelectionOptionEnabled()) { this.mapProcessor.toggleMultiworldType(this.settingsDimension); b.method_25355((class_2561)class_2561.method_43470(getMultiworldTypeButtonMessage())); }  }  }this.settingsDimension.isFutureMultiworldServerBased() ? (() -> this.serverSelectionModeBox) : (() -> this.mapSelectionBox)));
/* 100 */           mapScreen.addButton(this
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 109 */               .renameButton = class_4185.method_46430((class_2561)class_2561.method_43471("gui.xaero_rename"), b -> { synchronized (this.mapProcessor.uiPauseSync) { if (!canRenameMap()) return;  String currentMultiworld = this.settingsDimension.getFutureMultiworldUnsynced(); if (currentMultiworld == null) return;  minecraft.method_1507(new GuiMapName(this.mapProcessor, mapScreen, mapScreen, this.settingsDimension, currentMultiworld)); }  }).method_46434(width / 2 + 109, 80, 60, 20).method_46431());
/* 110 */           mapScreen.addButton(this
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 144 */               .connectButton = class_4185.method_46430(getConnectButtonLabel(), b -> { if (!canConnectMap()) return;  MapConnectionNode playerMapKey = this.settingsDimension.getMapWorld().getPlayerMapKey(); if (playerMapKey == null) return;  MapConnectionNode destinationMapKey = this.settingsDimension.getSelectedMapKeyUnsynced(); if (destinationMapKey == null) return;  String autoMapName = playerMapKey.getNamedString(this.settingsDimension.getMapWorld()); String selectedMapName = destinationMapKey.getNamedString(this.settingsDimension.getMapWorld()); String connectionDisplayString = autoMapName + "   §e<=>§r   " + autoMapName; MapConnectionManager mapConnections = this.settingsDimension.getMapWorld().getMapConnections(); boolean connected = mapConnections.isConnected(playerMapKey, destinationMapKey); BooleanConsumer confirmationConsumer = (); if (connected) { minecraft.method_1507((class_437)new class_410(confirmationConsumer, (class_2561)class_2561.method_43471("gui.xaero_wm_disconnect_from_auto_msg"), (class_2561)class_2561.method_43470(connectionDisplayString))); } else { minecraft.method_1507((class_437)new class_410(confirmationConsumer, (class_2561)class_2561.method_43471("gui.xaero_wm_connect_with_auto_msg"), (class_2561)class_2561.method_43470(connectionDisplayString))); }  }).method_46434(width / 2 + 109, 102, 60, 20).method_46431());
/* 145 */           mapScreen.addButton(this
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 181 */               .deleteButton = class_4185.method_46430((class_2561)class_2561.method_43471("gui.xaero_delete"), b -> { synchronized (this.mapProcessor.uiPauseSync) { if (!canDeleteMap()) return;  String selectedMWId = this.settingsDimension.getFutureCustomSelectedMultiworld(); minecraft.method_1507((class_437)new class_410((), (class_2561)class_2561.method_43471("gui.xaero_delete_map_msg1"), (class_2561)class_2561.method_43471("gui.xaero_delete_map_msg2"))); }  }).method_46434(width / 2 - 168, 80, 60, 20).method_46431());
/* 182 */           mapScreen.addButton(this
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 188 */               .confirmButton = class_4185.method_46430((class_2561)class_2561.method_43471("gui.xaero_confirm"), b -> { synchronized (this.mapProcessor.uiPauseSync) { if (!canConfirm()) return;  confirm(mapScreen, minecraft, width, height); }  }).method_46434(width / 2 - 50, 104, 100, 20).method_46431());
/* 189 */           updateButtons(mapScreen, width, minecraft);
/*     */         } else {
/* 191 */           this.switchingButton.field_22763 = canToggleThisScreen();
/*     */         } 
/*     */       } else {
/* 194 */         this.switchingButton.field_22763 = false;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   public static GuiDimensionOptions getSortedDimensionOptions(MapDimension dim) {
/* 199 */     int selected = 0;
/* 200 */     class_5321<class_1937> currentDim = dim.getDimId();
/* 201 */     List<KeySortableByOther<class_5321<class_1937>>> sortableList = new ArrayList<>();
/* 202 */     for (MapDimension dimension : dim.getMapWorld().getDimensionsList()) {
/* 203 */       sortableList.add(new KeySortableByOther(dimension.getDimId(), new Comparable[] { dimension.getDimId().method_29177().toString() }));
/* 204 */     }  Collections.sort(sortableList);
/* 205 */     selected = getDropdownSelectionIdFromValue(sortableList, currentDim);
/*     */     
/* 207 */     class_5321[] arrayOfClass_5321 = new class_5321[0];
/* 208 */     arrayOfClass_5321 = (class_5321[])((ArrayList)sortableList.stream().map(KeySortableByOther::getKey).collect(ArrayList::new, ArrayList::add, ArrayList::addAll)).toArray((Object[])arrayOfClass_5321);
/* 209 */     return new GuiDimensionOptions(selected, (class_5321<class_1937>[])arrayOfClass_5321);
/*     */   }
/*     */   
/*     */   private DropDownWidget createDimensionDropdown(boolean paused, int width, GuiMap mapScreen, class_310 minecraft) {
/* 213 */     GuiDimensionOptions dimOptions = getSortedDimensionOptions(this.settingsDimension);
/* 214 */     List<String> dropdownLabels = new ArrayList<>();
/* 215 */     class_5321<class_1937> currentWorldDim = (this.mapProcessor.getWorld() == null) ? null : this.mapProcessor.getWorld().method_27983();
/* 216 */     for (class_5321<class_1937> k : dimOptions.values) {
/* 217 */       String str = k.method_29177().toString();
/* 218 */       if (str.startsWith("minecraft:"))
/* 219 */         str = str.substring(10); 
/* 220 */       if (k == currentWorldDim)
/* 221 */         str = str + " (auto)"; 
/* 222 */       dropdownLabels.add(str);
/*     */     } 
/* 224 */     class_5321<class_1937>[] finalValues = dimOptions.values;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 246 */     DropDownWidget result = DropDownWidget.Builder.begin().setOptions(dropdownLabels.<String>toArray(new String[0])).setX(width / 2 - 100).setY(64).setW(200).setSelected(Integer.valueOf(dimOptions.selected)).setCallback((dd, i) -> { class_5321<class_1937> selectedValue = finalValues[i]; this.settingsDimension = this.settingsDimension.getMapWorld().getDimension(selectedValue); if (selectedValue == currentWorldDim) selectedValue = null;  this.settingsDimension.getMapWorld().setCustomDimensionId(selectedValue); this.mapProcessor.checkForWorldUpdate(); DropDownWidget newDropDown = createMapDropdown(this.uiPausedOnUpdate, width, mapScreen, minecraft); mapScreen.replaceWidget((class_339)this.createdMapDropdown, (class_339)newDropDown); this.createdMapDropdown = newDropDown; updateButtons(mapScreen, width, minecraft); return true; }).setContainer(mapScreen).setNarrationTitle((class_2561)class_2561.method_43471("gui_xaero_wm_dropdown_dimension_select")).build();
/* 247 */     return result;
/*     */   }
/*     */   
/*     */   private DropDownWidget createMapDropdown(boolean paused, int width, GuiMap mapScreen, class_310 minecraft) {
/*     */     List<String> mwDropdownNames;
/* 252 */     int selected = 0;
/* 253 */     if (!paused) {
/* 254 */       String currentMultiworld = this.settingsDimension.getFutureMultiworldUnsynced();
/* 255 */       List<KeySortableByOther<String>> sortableList = new ArrayList<>();
/* 256 */       for (String mwId : this.settingsDimension.getMultiworldIdsCopy()) {
/* 257 */         sortableList.add(new KeySortableByOther(mwId, new Comparable[] { this.settingsDimension.getMultiworldName(mwId).toLowerCase() }));
/* 258 */       }  if (currentMultiworld != null) {
/* 259 */         int currentIndex = getDropdownSelectionIdFromValue(sortableList, currentMultiworld);
/* 260 */         if (currentIndex == -1)
/* 261 */           sortableList.add(new KeySortableByOther(currentMultiworld, new Comparable[] { this.settingsDimension.getMultiworldName(currentMultiworld).toLowerCase() })); 
/*     */       } 
/* 263 */       Collections.sort(sortableList);
/* 264 */       if (currentMultiworld != null)
/* 265 */         selected = getDropdownSelectionIdFromValue(sortableList, currentMultiworld); 
/* 266 */       this.mwDropdownValues = (String[])((ArrayList)sortableList.stream().map(KeySortableByOther::getKey).collect(ArrayList::new, ArrayList::add, ArrayList::addAll)).toArray((Object[])new String[0]);
/* 267 */       Objects.requireNonNull(this.settingsDimension); mwDropdownNames = (List<String>)sortableList.stream().map(KeySortableByOther::getKey).map(this.settingsDimension::getMultiworldName).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
/* 268 */       mwDropdownNames.add("§8" + class_1074.method_4662("gui.xaero_create_new_map", new Object[0]));
/*     */     } else {
/* 270 */       mwDropdownNames = new ArrayList<>();
/* 271 */       this.mwDropdownValues = null;
/* 272 */       mwDropdownNames.add("§7" + class_1074.method_4662("gui.xaero_map_menu_please_wait", new Object[0]));
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 295 */     DropDownWidget result = DropDownWidget.Builder.begin().setOptions(mwDropdownNames.<String>toArray(new String[0])).setX(width / 2 - 100).setY(84).setW(200).setSelected(Integer.valueOf(selected)).setCallback((dd, i) -> { synchronized (this.mapProcessor.uiPauseSync) { if (isUIPaused() || this.uiPausedOnUpdate) return false;  if (i < this.mwDropdownValues.length) { this.mapProcessor.setMultiworld(this.settingsDimension, this.mwDropdownValues[i]); updateButtons(mapScreen, width, minecraft); return true; }  minecraft.method_1507(new GuiMapName(this.mapProcessor, mapScreen, mapScreen, this.settingsDimension, null)); return false; }  }).setContainer(mapScreen).setNarrationTitle((class_2561)class_2561.method_43471("gui_xaero_wm_dropdown_map_select")).build();
/* 296 */     result.setActive(!paused);
/* 297 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isUIPaused() {
/* 304 */     return (this.mapProcessor.isUIPaused() || this.mapProcessor.isWaitingForWorldUpdate());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isMapSelectionOptionEnabled() {
/* 311 */     return (!isUIPaused() && !this.settingsDimension.isFutureMultiworldServerBased() && this.settingsDimension.getMapWorld().isMultiplayer());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean canToggleThisScreen() {
/* 318 */     return (!isUIPaused() && this.settingsDimension != null && this.settingsDimension.futureMultiworldWritable);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean canDeleteMap() {
/* 325 */     return (!isUIPaused() && !this.settingsDimension.isFutureUsingWorldSaveUnsynced() && this.mwDropdownValues != null && this.mwDropdownValues.length > 1 && this.settingsDimension.getFutureCustomSelectedMultiworld() != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean canRenameMap() {
/* 332 */     return (!isUIPaused() && !this.settingsDimension.isFutureUsingWorldSaveUnsynced());
/*     */   }
/*     */   
/*     */   private boolean canConnectMap() {
/* 336 */     if (!this.mapProcessor.getMapWorld().isMultiplayer())
/* 337 */       return false; 
/* 338 */     MapConnectionNode playerMapKey = this.settingsDimension.getMapWorld().getPlayerMapKey();
/* 339 */     if (playerMapKey == null)
/* 340 */       return false; 
/* 341 */     MapConnectionNode destinationMapKey = this.settingsDimension.getSelectedMapKeyUnsynced();
/* 342 */     if (destinationMapKey == null)
/* 343 */       return false; 
/* 344 */     return !destinationMapKey.equals(playerMapKey);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean canConfirm() {
/* 351 */     return !isUIPaused();
/*     */   }
/*     */   
/*     */   private class_2561 getConnectButtonLabel() {
/* 355 */     synchronized (this.mapProcessor.uiPauseSync) {
/* 356 */       if (isUIPaused())
/* 357 */         return CONNECT_MAP; 
/* 358 */       MapConnectionNode playerMapKey = this.settingsDimension.getMapWorld().getPlayerMapKey();
/* 359 */       if (playerMapKey == null)
/* 360 */         return CONNECT_MAP; 
/* 361 */       MapConnectionNode destinationMapKey = this.settingsDimension.getSelectedMapKeyUnsynced();
/* 362 */       if (destinationMapKey == null)
/* 363 */         return CONNECT_MAP; 
/* 364 */       MapConnectionManager mapConnections = this.settingsDimension.getMapWorld().getMapConnections();
/* 365 */       if (mapConnections.isConnected(playerMapKey, destinationMapKey))
/* 366 */         return DISCONNECT_MAP; 
/* 367 */       return CONNECT_MAP;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateButtons(GuiMap mapScreen, int width, class_310 minecraft) {
/* 372 */     synchronized (this.mapProcessor.uiPauseSync) {
/* 373 */       boolean isPaused = isUIPaused();
/* 374 */       if (this.uiPausedOnUpdate != isPaused) {
/* 375 */         DropDownWidget newDropDown = !this.active ? null : createMapDropdown(isPaused, width, mapScreen, minecraft);
/* 376 */         if (newDropDown != null) {
/* 377 */           if (this.createdMapDropdown != null)
/* 378 */           { mapScreen.replaceWidget((class_339)this.createdMapDropdown, (class_339)newDropDown); }
/*     */           else
/* 380 */           { mapScreen.method_25429(newDropDown); } 
/* 381 */         } else if (this.createdMapDropdown != null) {
/* 382 */           mapScreen.method_37066((class_364)this.createdMapDropdown);
/* 383 */         }  this.createdMapDropdown = !this.active ? null : newDropDown;
/*     */         
/* 385 */         this.uiPausedOnUpdate = isPaused;
/*     */       } 
/* 387 */       this.switchingButton.field_22763 = canToggleThisScreen();
/* 388 */       if (this.deleteButton != null)
/* 389 */         this.deleteButton.field_22763 = canDeleteMap(); 
/* 390 */       if (this.renameButton != null)
/* 391 */         this.renameButton.field_22763 = canRenameMap(); 
/* 392 */       if (this.connectButton != null) {
/* 393 */         this.connectButton.field_22763 = canConnectMap();
/* 394 */         this.connectButton.method_25355(getConnectButtonLabel());
/*     */       } 
/* 396 */       if (this.multiworldTypeOptionButton != null)
/* 397 */         this.multiworldTypeOptionButton.field_22763 = isMapSelectionOptionEnabled(); 
/* 398 */       if (this.confirmButton != null)
/* 399 */         this.confirmButton.field_22763 = canConfirm(); 
/*     */     } 
/*     */   }
/*     */   
/*     */   private String getMultiworldTypeButtonMessage() {
/* 404 */     int multiworldType = this.settingsDimension.getMapWorld().getFutureMultiworldType(this.settingsDimension);
/* 405 */     return class_1074.method_4662("gui.xaero_map_selection", new Object[0]) + ": " + class_1074.method_4662("gui.xaero_map_selection", new Object[0]);
/*     */   }
/*     */   
/*     */   public void confirm(GuiMap mapScreen, class_310 minecraft, int width, int height) {
/* 409 */     if (this.mapProcessor.confirmMultiworld(this.settingsDimension)) {
/* 410 */       this.active = false;
/* 411 */       mapScreen.method_25423(minecraft, width, height);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static <S> int getDropdownSelectionIdFromValue(List<KeySortableByOther<S>> values, S value) {
/* 416 */     for (int selected = 0; selected < values.size(); selected++) {
/* 417 */       if (((KeySortableByOther)values.get(selected)).getKey().equals(value))
/* 418 */         return selected; 
/* 419 */     }  return -1;
/*     */   }
/*     */   
/*     */   public void preMapRender(GuiMap mapScreen, class_310 minecraft, int width, int height) {
/* 423 */     if (!this.active && this.settingsDimension != null && !this.settingsDimension.futureMultiworldWritable) {
/* 424 */       this.active = true;
/* 425 */       mapScreen.method_25423(minecraft, width, height);
/*     */     } 
/* 427 */     if (this.mapSwitchingAllowed && (
/* 428 */       this.createdMapDropdown == null || this.createdMapDropdown.isClosed()))
/* 429 */       synchronized (this.mapProcessor.uiPauseSync) {
/* 430 */         if (this.uiPausedOnUpdate != isUIPaused()) {
/* 431 */           updateButtons(mapScreen, width, minecraft);
/*     */         }
/*     */       }  
/* 434 */     if (this.active && 
/* 435 */       this.settingsDimension != null && this.createdMapDropdown.isClosed() && !this.uiPausedOnUpdate) {
/* 436 */       String currentMultiworld = this.settingsDimension.getFutureMultiworldUnsynced();
/* 437 */       if (currentMultiworld != null) {
/* 438 */         String currentDropdownSelection = this.mwDropdownValues[this.createdMapDropdown.getSelected()];
/* 439 */         if (!currentMultiworld.equals(currentDropdownSelection) || this.writableOnInit != this.settingsDimension.futureMultiworldWritable) {
/* 440 */           mapScreen.method_25423(minecraft, width, height);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void renderText(class_332 guiGraphics, class_310 minecraft, int mouseX, int mouseY, int width, int height) {
/* 447 */     if (!this.active)
/*     */       return; 
/* 449 */     String selectMapString = class_1074.method_4662("gui.xaero_select_map", new Object[0]) + ":";
/* 450 */     class_4597.class_4598 renderTypeBuffers = this.mapProcessor.getCvc().getRenderTypeBuffers();
/* 451 */     class_4588 backgroundVertexBuffer = renderTypeBuffers.method_73477(CustomRenderTypes.MAP_COLOR_OVERLAY);
/* 452 */     MapRenderHelper.drawStringWithBackground(guiGraphics, minecraft.field_1772, selectMapString, width / 2 - minecraft.field_1772.method_1727(selectMapString) / 2, 49, -1, 0.0F, 0.0F, 0.0F, 0.4F);
/* 453 */     renderTypeBuffers.method_22993();
/*     */   }
/*     */   
/*     */   public void postMapRender(class_332 guiGraphics, class_310 minecraft, int mouseX, int mouseY, int width, int height) {}
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\gui\GuiMapSwitching.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */