/*     */ package xaero.map.gui;
/*     */ 
/*     */ import com.google.common.base.Objects;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.function.Supplier;
/*     */ import net.minecraft.class_1074;
/*     */ import net.minecraft.class_11905;
/*     */ import net.minecraft.class_11908;
/*     */ import net.minecraft.class_11909;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_339;
/*     */ import net.minecraft.class_342;
/*     */ import net.minecraft.class_364;
/*     */ import net.minecraft.class_3675;
/*     */ import net.minecraft.class_4185;
/*     */ import net.minecraft.class_437;
/*     */ import xaero.map.WorldMap;
/*     */ import xaero.map.WorldMapSession;
/*     */ import xaero.map.controls.ControlsRegister;
/*     */ import xaero.map.misc.KeySortableByOther;
/*     */ import xaero.map.misc.Misc;
/*     */ import xaero.map.settings.ModOptions;
/*     */ import xaero.map.settings.ModSettings;
/*     */ 
/*     */ public abstract class GuiSettings
/*     */   extends ScreenBase
/*     */ {
/*  31 */   protected int entriesPerPage = 12;
/*     */   
/*     */   protected ISettingEntry[] entries;
/*     */   protected String entryFilter;
/*     */   private boolean foundSomething;
/*     */   protected class_2561 screenTitle;
/*     */   protected int page;
/*     */   protected int maxPage;
/*     */   private MyTinyButton nextButton;
/*     */   private MyTinyButton prevButton;
/*     */   private class_342 searchField;
/*     */   protected boolean canSearch;
/*     */   protected boolean shouldAddBackButton;
/*     */   private boolean shouldRefocusSearch;
/*     */   
/*     */   public GuiSettings(class_2561 title, class_437 backScreen, class_437 escScreen) {
/*  47 */     super(backScreen, escScreen, title);
/*  48 */     this.entryFilter = "";
/*  49 */     this.canSearch = true;
/*  50 */     this.shouldAddBackButton = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void method_25426() {
/*  58 */     super.method_25426();
/*  59 */     this.screenTitle = this.field_22785;
/*  60 */     if (this.shouldAddBackButton) {
/*  61 */       method_37063((class_364)class_4185.method_46430((class_2561)class_2561.method_43469("gui.xaero_back", new Object[0]), b -> goBack())
/*     */ 
/*     */           
/*  64 */           .method_46434(this.field_22789 / 2 - 100, this.field_22790 / 6 + 168, 200, 20).method_46431());
/*     */     }
/*  66 */     int verticalOffset = this.canSearch ? 24 : 0;
/*     */     
/*  68 */     if (this.entries != null) {
/*  69 */       boolean canEditIngameSettings = ModSettings.canEditIngameSettings();
/*  70 */       ArrayList<KeySortableByOther<ISettingEntry>> sortingList = new ArrayList<>();
/*  71 */       String comparisonFilter = this.entryFilter.toLowerCase();
/*  72 */       for (int i = 0; i < this.entries.length; i++) {
/*  73 */         ISettingEntry entry = this.entries[i];
/*  74 */         String entrySearchString = entry.getStringForSearch().toLowerCase();
/*  75 */         if (entrySearchString != null) {
/*  76 */           int positionInEntryString = entrySearchString.indexOf(comparisonFilter);
/*  77 */           if (positionInEntryString != -1) {
/*  78 */             KeySortableByOther<ISettingEntry> sortableEntry = new KeySortableByOther(entry, new Comparable[] { Integer.valueOf(positionInEntryString) });
/*  79 */             sortingList.add(sortableEntry);
/*     */           } 
/*     */         } 
/*     */       } 
/*  83 */       ArrayList<ISettingEntry> filteredEntries = (ArrayList<ISettingEntry>)sortingList.stream().sorted().map(KeySortableByOther::getKey).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
/*     */       
/*  85 */       if (!filteredEntries.isEmpty()) {
/*  86 */         this.foundSomething = true;
/*  87 */         this.maxPage = (int)Math.ceil(filteredEntries.size() / this.entriesPerPage) - 1;
/*  88 */         if (this.page > this.maxPage)
/*  89 */           this.page = this.maxPage; 
/*  90 */         int firstEntry = this.entriesPerPage * this.page;
/*  91 */         int entryCount = Math.min(filteredEntries.size() - firstEntry, this.entriesPerPage);
/*     */         
/*  93 */         for (int j = 0; j < entryCount; j++) {
/*  94 */           ISettingEntry entry = filteredEntries.get(firstEntry + j);
/*  95 */           class_339 optionWidget = entry.createWidget(this.field_22789 / 2 - 205 + j % 2 * 210, this.field_22790 / 7 + verticalOffset + 24 * (j >> 1), 200, canEditIngameSettings);
/*  96 */           method_37063((class_364)optionWidget);
/*     */         } 
/*     */       } else {
/*  99 */         this.foundSomething = false;
/* 100 */         this.page = 0;
/* 101 */         this.maxPage = 0;
/*     */       } 
/*     */     } 
/* 104 */     this.screenTitle = (class_2561)this.screenTitle.method_27662().method_27693(" (" + this.page + 1 + "/" + this.maxPage + 1 + ")");
/*     */     
/* 106 */     this.nextButton = new MyTinyButton(this.field_22789 / 2 + 131, this.field_22790 / 7 + 144 + verticalOffset, (class_2561)class_2561.method_43469("gui.xaero_wm_next", new Object[0]), b -> onNextButton());
/* 107 */     this.prevButton = new MyTinyButton(this.field_22789 / 2 - 205, this.field_22790 / 7 + 144 + verticalOffset, (class_2561)class_2561.method_43469("gui.xaero_wm_previous", new Object[0]), b -> onPrevButton());
/* 108 */     if (this.maxPage > 0) {
/* 109 */       method_37063((class_364)this.nextButton);
/* 110 */       method_37063((class_364)this.prevButton);
/* 111 */       this.nextButton.field_22763 = (this.page < this.maxPage);
/* 112 */       this.prevButton.field_22763 = (this.page > 0);
/*     */     } 
/*     */     
/* 115 */     if (this.canSearch) {
/* 116 */       boolean shouldFocusSearch = this.shouldRefocusSearch;
/* 117 */       int cursorPos = 0;
/* 118 */       if (shouldFocusSearch)
/* 119 */         cursorPos = this.searchField.method_1881(); 
/* 120 */       this.searchField = new class_342(this.field_22793, this.field_22789 / 2 - 100, this.field_22790 / 7, 200, 20, (class_2561)class_2561.method_43471("gui.xaero_wm_settings_search"));
/* 121 */       this.searchField.method_1852(this.entryFilter);
/* 122 */       if (shouldFocusSearch) {
/* 123 */         method_25395((class_364)this.searchField);
/* 124 */         this.searchField.method_1875(cursorPos);
/* 125 */         this.searchField.method_1884(cursorPos);
/*     */       } 
/* 127 */       this.searchField.method_1863(s -> {
/*     */             if (this.canSearch)
/*     */               updateSearch(); 
/*     */           });
/* 131 */       method_25429(this.searchField);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void method_56131() {
/* 137 */     if (this.canSearch && this.shouldRefocusSearch) {
/* 138 */       this.shouldRefocusSearch = false;
/*     */       return;
/*     */     } 
/* 141 */     super.method_56131();
/*     */   }
/*     */ 
/*     */   
/*     */   public void method_25393() {
/* 146 */     super.method_25393();
/* 147 */     if (this.canSearch);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void method_25420(class_332 guiGraphics, int i, int j, float f) {
/* 154 */     super.method_25420(guiGraphics, i, j, f);
/* 155 */     guiGraphics.method_27534(this.field_22787.field_1772, this.screenTitle, this.field_22789 / 2, 5, -1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void method_25394(class_332 guiGraphics, int par1, int par2, float par3) {
/* 163 */     super.method_25394(guiGraphics, par1, par2, par3);
/* 164 */     if (this.canSearch) {
/* 165 */       if (!this.foundSomething)
/* 166 */         guiGraphics.method_25300(this.field_22787.field_1772, class_1074.method_4662("gui.xaero_wm_settings_not_found", new Object[0]), this.field_22789 / 2, this.field_22790 / 7 + 29, -1); 
/* 167 */       if (!this.searchField.method_25370() && this.searchField.method_1882().isEmpty()) {
/* 168 */         Misc.setFieldText(this.searchField, class_1074.method_4662("gui.xaero_wm_settings_search_placeholder", new Object[0]), -11184811);
/* 169 */         this.searchField.method_1883(0, false);
/*     */       } 
/* 171 */       this.searchField.method_25394(guiGraphics, par1, par2, par3);
/* 172 */       if (!this.searchField.method_25370()) {
/* 173 */         Misc.setFieldText(this.searchField, this.entryFilter);
/*     */       }
/*     */     } 
/* 176 */     renderTooltips(guiGraphics, par1, par2, par3);
/*     */   }
/*     */   
/*     */   public void restoreFocus(int index) {
/* 180 */     if (index != -1) {
/*     */       try {
/* 182 */         class_364 child = method_25396().get(index);
/* 183 */         method_25395(child);
/* 184 */       } catch (IndexOutOfBoundsException indexOutOfBoundsException) {}
/*     */     }
/*     */   }
/*     */   
/*     */   public int getIndex(class_364 child) {
/* 189 */     for (int i = 0; i < method_25396().size(); i++) {
/* 190 */       if (method_25396().get(i) == child)
/* 191 */         return i; 
/* 192 */     }  return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onExit(class_437 screen) {
/*     */     try {
/* 198 */       WorldMap.settings.saveSettings();
/* 199 */     } catch (IOException e) {
/* 200 */       WorldMap.LOGGER.error("suppressed exception", e);
/*     */     } 
/* 202 */     super.onExit(screen);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean method_25404(class_11908 event) {
/* 207 */     int par1 = event.comp_4795();
/* 208 */     int par2 = event.comp_4796();
/* 209 */     if (!super.method_25404(event) && (!(method_25399() instanceof class_342) || !((class_342)method_25399()).method_25370())) {
/* 210 */       if (Misc.inputMatchesKeyBinding((par1 != -1) ? class_3675.class_307.field_1668 : class_3675.class_307.field_1671, (par1 != -1) ? par1 : par2, ControlsRegister.keyOpenSettings, 0)) {
/* 211 */         method_25419();
/* 212 */         return true;
/*     */       } 
/* 214 */       return false;
/*     */     } 
/* 216 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean method_25400(class_11905 event) {
/* 221 */     boolean result = super.method_25400(event);
/* 222 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean method_25402(class_11909 event, boolean doubleClick) {
/* 227 */     if (!super.method_25402(event, doubleClick)) {
/* 228 */       if (Misc.inputMatchesKeyBinding(class_3675.class_307.field_1672, event.method_74245(), ControlsRegister.keyOpenSettings, 0)) {
/* 229 */         goBack();
/* 230 */         return true;
/*     */       } 
/* 232 */       return false;
/*     */     } 
/* 234 */     return true;
/*     */   }
/*     */   
/*     */   protected void onNextButton() {
/* 238 */     this.page++;
/* 239 */     method_25423(this.field_22787, this.field_22789, this.field_22790);
/*     */   }
/*     */   
/*     */   protected void onPrevButton() {
/* 243 */     this.page--;
/* 244 */     method_25423(this.field_22787, this.field_22789, this.field_22790);
/*     */   }
/*     */   
/*     */   protected static ISettingEntry[] entriesFromOptions(ModOptions[] options) {
/* 248 */     ISettingEntry[] result = new ISettingEntry[options.length];
/* 249 */     for (int i = 0; i < options.length; i++)
/* 250 */       result[i] = new ConfigSettingEntry(options[i]); 
/* 251 */     return result;
/*     */   }
/*     */   
/*     */   protected void resetConfirmResult(boolean result, class_437 parent, class_437 escScreen) {
/* 255 */     if (result) {
/* 256 */       WorldMapSession minimapSession = WorldMapSession.getCurrentSession();
/* 257 */       if (minimapSession != null);
/*     */ 
/*     */       
/*     */       try {
/* 261 */         WorldMap.settings = new ModSettings();
/* 262 */         WorldMap.settings.saveSettings();
/* 263 */       } catch (IOException e) {
/* 264 */         WorldMap.LOGGER.error("suppressed exception", e);
/*     */       } 
/* 266 */       WorldMap.settings.updateRegionCacheHashCode();
/*     */     } 
/* 268 */     class_310.method_1551().method_1507(parent);
/*     */   }
/*     */   
/*     */   public ISettingEntry[] getEntriesCopy() {
/* 272 */     if (this.entries == null)
/* 273 */       return null; 
/* 274 */     ISettingEntry[] result = new ISettingEntry[this.entries.length];
/* 275 */     System.arraycopy(this.entries, 0, result, 0, this.entries.length);
/* 276 */     return result;
/*     */   }
/*     */   
/*     */   private void updateSearch() {
/* 280 */     if (this.searchField.method_25370()) {
/* 281 */       String newValue = this.searchField.method_1882();
/* 282 */       if (!Objects.equal(this.entryFilter, newValue)) {
/* 283 */         this.entryFilter = this.searchField.method_1882();
/* 284 */         this.shouldRefocusSearch = true;
/* 285 */         this.page = 0;
/* 286 */         method_25423(this.field_22787, this.field_22789, this.field_22790);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\gui\GuiSettings.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */