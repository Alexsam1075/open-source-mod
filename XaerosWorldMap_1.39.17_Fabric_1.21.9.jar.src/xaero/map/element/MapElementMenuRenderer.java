/*     */ package xaero.map.element;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.regex.Pattern;
/*     */ import java.util.regex.PatternSyntaxException;
/*     */ import net.minecraft.class_1074;
/*     */ import net.minecraft.class_1109;
/*     */ import net.minecraft.class_1113;
/*     */ import net.minecraft.class_11909;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_3417;
/*     */ import net.minecraft.class_342;
/*     */ import net.minecraft.class_364;
/*     */ import net.minecraft.class_437;
/*     */ import net.minecraft.class_6880;
/*     */ import org.joml.Matrix3x2fStack;
/*     */ import xaero.map.element.render.ElementReader;
/*     */ import xaero.map.element.render.ElementRenderLocation;
/*     */ import xaero.map.element.render.ElementRenderProvider;
/*     */ import xaero.map.element.render.ElementRenderer;
/*     */ import xaero.map.gui.GuiMap;
/*     */ import xaero.map.misc.Misc;
/*     */ 
/*     */ public abstract class MapElementMenuRenderer<E, C>
/*     */ {
/*     */   private static final int MENU_RIGHT_PADDING = 27;
/*     */   public static final int MAX_MENU_SIZE = 10;
/*     */   public static final int MIN_MENU_SIZE = 2;
/*     */   protected final MapElementMenuScroll scrollUp;
/*     */   protected final MapElementMenuScroll scrollDown;
/*     */   protected final MapElementMenuHitbox extraHitbox;
/*     */   protected final MenuScrollReader scrollReader;
/*     */   protected final MenuHitboxReader hitboxReader;
/*     */   protected final C context;
/*     */   protected final ElementRenderProvider<E, C> provider;
/*     */   protected ArrayList<E> filteredElements;
/*     */   private class_342 filterField;
/*  40 */   public int menuOffset = 0;
/*  41 */   protected Pattern searchPattern = null;
/*  42 */   protected Pattern searchStartPattern = null;
/*     */   protected final class_310 mc;
/*     */   
/*     */   protected MapElementMenuRenderer(C context, ElementRenderProvider<E, C> provider) {
/*  46 */     this.scrollUp = new MapElementMenuScroll("gui.xaero_wm_up", "△", 1);
/*  47 */     this.scrollDown = new MapElementMenuScroll("gui.xaero_wm_down", "▽", -1);
/*  48 */     this.scrollReader = new MenuScrollReader();
/*  49 */     this.extraHitbox = new MapElementMenuHitbox(-150, 0, 177, 0);
/*  50 */     this.hitboxReader = new MenuHitboxReader();
/*  51 */     this.mc = class_310.method_1551();
/*  52 */     this.context = context;
/*  53 */     this.provider = provider;
/*     */   }
/*     */   
/*     */   public void onMapInit(GuiMap screen, class_310 mc, int width, int height) {
/*  57 */     String searchText = (this.filterField == null) ? "" : this.filterField.method_1882();
/*  58 */     this.filterField = new class_342(mc.field_1772, screen.field_22789 - 172, menuStartPos(height) + 3 + menuSearchPadding(), 150, 20, (class_2561)class_2561.method_43471(getFilterPlaceholder()));
/*  59 */     this.filterField.method_1852(searchText);
/*  60 */     this.filterField.method_1863(s -> updateSearch());
/*  61 */     screen.method_25429((class_364)this.filterField);
/*     */   }
/*     */   
/*     */   public HoveredMapElementHolder<?, ?> renderMenu(class_332 guiGraphics, GuiMap gui, double scale, int width, int height, int mouseX, int mouseY, boolean leftMousePressed, boolean leftMouseClicked, HoveredMapElementHolder<?, ?> oldHovered, class_310 mc) {
/*  65 */     if (this.filteredElements == null)
/*  66 */       updateFilteredList(); 
/*  67 */     ArrayList<? extends E> elements = this.filteredElements;
/*     */     
/*  69 */     int menuElementCount = getMenuElementCount(menuStartPos(height));
/*  70 */     if (this.menuOffset + menuElementCount > elements.size()) {
/*  71 */       this.menuOffset = elements.size() - menuElementCount;
/*     */     }
/*  73 */     if (this.menuOffset < 0) {
/*  74 */       this.menuOffset = 0;
/*     */     }
/*  76 */     int offset = this.menuOffset;
/*     */     
/*  78 */     Object viewed = null;
/*  79 */     int menuStartPos = menuStartPos(height);
/*  80 */     int yPos = menuStartPos;
/*     */     
/*  82 */     int elementCount = getMenuElementCount(yPos);
/*     */     
/*  84 */     beforeMenuRender();
/*     */     
/*  86 */     yPos -= 8;
/*  87 */     viewed = renderMenuElement(guiGraphics, this.scrollDown, width, yPos, mouseX, mouseY, viewed, leftMousePressed, (class_437)gui, (offset > 0), mc);
/*  88 */     yPos -= 8;
/*     */     
/*  90 */     for (int i = offset; i < elements.size(); i++) {
/*  91 */       yPos -= 8;
/*  92 */       viewed = renderMenuElement(guiGraphics, elements.get(i), width, yPos, mouseX, mouseY, viewed, leftMousePressed, (class_437)gui, true, mc);
/*  93 */       yPos -= 8;
/*  94 */       if (i - offset == elementCount - 1)
/*     */         break; 
/*     */     } 
/*  97 */     yPos -= 8;
/*  98 */     viewed = renderMenuElement(guiGraphics, this.scrollUp, width, yPos, mouseX, mouseY, viewed, leftMousePressed, (class_437)gui, (offset < elements.size() - elementCount), mc);
/*  99 */     yPos -= 8;
/* 100 */     if (viewed != null && leftMouseClicked)
/* 101 */       class_310.method_1551().method_1483().method_4873((class_1113)class_1109.method_47978((class_6880)class_3417.field_15015, 1.0F)); 
/* 102 */     if (leftMousePressed && viewed instanceof MapElementMenuScroll) {
/* 103 */       int direction = ((MapElementMenuScroll)viewed).scroll();
/* 104 */       this.menuOffset += direction;
/*     */     } 
/*     */     
/* 107 */     if (viewed == null) {
/* 108 */       this.extraHitbox.setH(menuStartPos - yPos);
/* 109 */       this.extraHitbox.setY(yPos - menuStartPos);
/* 110 */       viewed = renderMenuElement(guiGraphics, this.extraHitbox, width, menuStartPos, mouseX, mouseY, viewed, leftMousePressed, (class_437)gui, true, mc);
/*     */     } 
/* 112 */     afterMenuRender();
/* 113 */     return (oldHovered != null && oldHovered.equals(viewed)) ? oldHovered : ((viewed == null) ? null : MapElementRenderHandler.createResult(viewed, getAnyRenderer(viewed)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void postMapRender(class_332 guiGraphics, GuiMap gui, int scaledMouseX, int scaledMouseY, int width, int height, float partialTicks) {
/* 121 */     String searchText = this.filterField.method_1882();
/* 122 */     boolean searchFieldPlaceHolder = (searchText.isEmpty() && !this.filterField.method_25370());
/* 123 */     boolean invalidRegex = false;
/* 124 */     if (searchFieldPlaceHolder) {
/* 125 */       Misc.setFieldText(this.filterField, class_1074.method_4662(getFilterPlaceholder(), new Object[0]), -11184811);
/* 126 */     } else if (!searchText.isEmpty() && this.searchPattern == null) {
/* 127 */       invalidRegex = true;
/* 128 */     }  this.filterField.method_25394(guiGraphics, scaledMouseX, scaledMouseY, partialTicks);
/* 129 */     if (searchFieldPlaceHolder) {
/* 130 */       Misc.setFieldText(this.filterField, "");
/* 131 */     } else if (invalidRegex) {
/* 132 */       String errorMessage = class_1074.method_4662("gui.xaero_wm_search_invalid_regex", new Object[0]);
/* 133 */       guiGraphics.method_25303(this.mc.field_1772, errorMessage, width - 176 - this.mc.field_1772.method_1727(errorMessage), this.filterField.method_46427() + 6, -43691);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int getMenuElementCount(int menuStartPos) {
/* 138 */     return Math.min(10, Math.max(2, (menuStartPos - 34) / 16 - 2));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private <O> Object renderMenuElement(class_332 guiGraphics, O element, int width, int yPos, int mouseX, int mouseY, Object viewed, boolean leftMousePressed, class_437 gui, boolean enabled, class_310 mc) {
/* 148 */     ElementReader<? super O, ?, ?> reader = (element == this.scrollDown || element == this.scrollUp) ? this.scrollReader : ((element == this.extraHitbox) ? this.hitboxReader : getAnyRenderer(element).getReader());
/*     */     
/* 150 */     int xPos = width - 27;
/* 151 */     boolean hovered = (viewed == null && reader.isMouseOverMenuElement(element, xPos, yPos, mouseX, mouseY, mc));
/* 152 */     if (hovered)
/* 153 */       viewed = element; 
/* 154 */     if (element != this.extraHitbox) {
/* 155 */       renderMenuElement(reader, element, guiGraphics, gui, xPos, yPos, mouseX, mouseY, 1.0D, enabled, hovered, mc, leftMousePressed);
/*     */     }
/* 157 */     return viewed;
/*     */   }
/*     */   
/*     */   public <O> void renderMenuElement(ElementReader<O, ?, ?> reader, O element, class_332 guiGraphics, class_437 gui, int x, int y, int mouseX, int mouseY, double scale, boolean enabled, boolean hovered, class_310 mc, boolean pressed) {
/* 161 */     Matrix3x2fStack matrixStack = guiGraphics.method_51448();
/* 162 */     matrixStack.pushMatrix();
/* 163 */     if (hovered)
/* 164 */       matrixStack.translate(pressed ? 1.0F : 2.0F, 0.0F); 
/* 165 */     matrixStack.translate(x, y);
/* 166 */     matrixStack.scale((float)scale, (float)scale);
/*     */     
/* 168 */     matrixStack.translate(-4.0F, -4.0F);
/* 169 */     String name = reader.getMenuName(element);
/* 170 */     int len = mc.field_1772.method_1727(name);
/* 171 */     int textX = -3 - len;
/* 172 */     guiGraphics.method_25294(textX - 2 - reader.getMenuTextFillLeftPadding(element), -2, textX + len + 2, 11, 1996488704);
/* 173 */     guiGraphics.method_25303(mc.field_1772, name, textX, 0, enabled ? -1 : -11184811);
/* 174 */     matrixStack.translate(4.0F, 4.0F);
/*     */     
/* 176 */     if (element == this.scrollUp || element == this.scrollDown) {
/* 177 */       renderScroll((MapElementMenuScroll)element, guiGraphics, gui, mouseX, mouseY, scale, enabled, hovered, mc, pressed);
/*     */     } else {
/*     */       
/* 180 */       O o = element;
/* 181 */       renderInMenu((E)o, guiGraphics, gui, mouseX, mouseY, scale, enabled, hovered, mc, pressed, textX);
/*     */     } 
/*     */     
/* 184 */     matrixStack.popMatrix();
/*     */   }
/*     */   
/*     */   public void onMapMouseRelease(class_11909 event) {
/* 188 */     releaseScroll();
/*     */   }
/*     */   
/*     */   private void releaseScroll() {
/* 192 */     this.scrollUp.onMouseRelease();
/* 193 */     this.scrollDown.onMouseRelease();
/*     */   }
/*     */   
/*     */   private void renderScroll(MapElementMenuScroll scroll, class_332 guiGraphics, class_437 gui, int mouseX, int mouseY, double scale, boolean enabled, boolean hovered, class_310 mc, boolean pressed) {
/* 197 */     Matrix3x2fStack matrixStack = guiGraphics.method_51448();
/* 198 */     if (enabled && hovered)
/* 199 */       matrixStack.translate(pressed ? 1.0F : 2.0F, 0.0F); 
/* 200 */     matrixStack.translate(-4.0F, -4.0F);
/* 201 */     int color = enabled ? -1 : -11184811;
/* 202 */     guiGraphics.method_25303(mc.field_1772, scroll.getIcon(), 5 - mc.field_1772.method_1727(scroll.getIcon()) / 2, 1, color);
/*     */   }
/*     */   
/*     */   private void updateSearch() {
/* 206 */     String search = this.filterField.method_1882();
/*     */     try {
/* 208 */       this.searchPattern = Pattern.compile(search.toLowerCase());
/* 209 */       if (search.length() > 0)
/* 210 */       { if (search.charAt(0) == '^') {
/* 211 */           this.searchStartPattern = this.searchPattern;
/*     */         } else {
/* 213 */           this.searchStartPattern = Pattern.compile("^" + search.toString().toLowerCase());
/*     */         }  }
/* 215 */       else { this.searchPattern = this.searchStartPattern = null; }
/*     */     
/* 217 */     } catch (PatternSyntaxException e) {
/* 218 */       this.searchPattern = this.searchStartPattern = null;
/*     */     } 
/* 220 */     updateFilteredList();
/*     */   }
/*     */   
/*     */   public boolean charTyped() {
/* 224 */     if (this.filterField.method_25370())
/* 225 */       return true; 
/* 226 */     return false;
/*     */   }
/*     */   
/*     */   public boolean keyPressed(GuiMap screen, int keyCode) {
/* 230 */     if (screen.method_25399() == this.filterField) {
/* 231 */       if (keyCode == 257)
/* 232 */         this.filterField.method_1852(""); 
/* 233 */       return true;
/*     */     } 
/* 235 */     return false;
/*     */   }
/*     */   
/*     */   public void mouseScrolled(int direction) {
/* 239 */     scroll(direction);
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {}
/*     */ 
/*     */   
/*     */   public void unfocusAll() {
/* 247 */     if (this.filterField != null)
/* 248 */       this.filterField.method_25365(false); 
/*     */   }
/*     */   
/*     */   public void onMenuClosed() {
/* 252 */     this.menuOffset = 0;
/* 253 */     this.searchPattern = null;
/* 254 */     this.searchStartPattern = null;
/* 255 */     updateFilteredList();
/* 256 */     this.filterField = null;
/*     */   }
/*     */   
/*     */   private void scroll(int direction) {
/* 260 */     this.menuOffset += direction;
/*     */   }
/*     */   
/*     */   public Pattern getSearchPattern() {
/* 264 */     return this.searchPattern;
/*     */   }
/*     */   
/*     */   public Pattern getSearchStartPattern() {
/* 268 */     return this.searchStartPattern;
/*     */   }
/*     */   
/*     */   public void updateFilteredList() {
/* 272 */     ElementRenderProvider<E, C> provider = this.provider;
/* 273 */     if (provider == null) {
/* 274 */       this.filteredElements = null;
/*     */       return;
/*     */     } 
/* 277 */     if (this.filteredElements == null) {
/* 278 */       this.filteredElements = new ArrayList<>();
/*     */     } else {
/* 280 */       this.filteredElements.clear();
/* 281 */     }  Pattern regex = this.searchPattern;
/* 282 */     Pattern regexStartsWith = this.searchStartPattern;
/* 283 */     beforeFiltering();
/* 284 */     provider.begin(ElementRenderLocation.WORLD_MAP_MENU, this.context);
/*     */     try {
/* 286 */       while (provider.hasNext(ElementRenderLocation.WORLD_MAP_MENU, this.context)) {
/* 287 */         E e = (E)provider.getNext(ElementRenderLocation.WORLD_MAP_MENU, this.context);
/* 288 */         if (regex == null) {
/* 289 */           this.filteredElements.add(e); continue;
/*     */         } 
/* 291 */         String filterName = getRenderer(e).getReader().getFilterName(e).toLowerCase();
/* 292 */         if (regexStartsWith.matcher(filterName).find()) {
/* 293 */           this.filteredElements.add(0, e); continue;
/* 294 */         }  if (regex.matcher(filterName).find()) {
/* 295 */           this.filteredElements.add(e);
/*     */         }
/*     */       } 
/*     */     } finally {
/* 299 */       provider.end(ElementRenderLocation.WORLD_MAP_MENU, this.context);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected <O> ElementRenderer<? super O, ?, ?> getAnyRenderer(O element) {
/* 305 */     if (element == this.scrollDown || element == this.scrollUp || element == this.extraHitbox)
/* 306 */       return null; 
/* 307 */     return getRenderer((E)element);
/*     */   }
/*     */   
/*     */   protected abstract void beforeMenuRender();
/*     */   
/*     */   protected abstract void afterMenuRender();
/*     */   
/*     */   protected abstract ElementRenderer<? super E, ?, ?> getRenderer(E paramE);
/*     */   
/*     */   public abstract int menuStartPos(int paramInt);
/*     */   
/*     */   public abstract int menuSearchPadding();
/*     */   
/*     */   public abstract void renderInMenu(E paramE, class_332 paramclass_332, class_437 paramclass_437, int paramInt1, int paramInt2, double paramDouble, boolean paramBoolean1, boolean paramBoolean2, class_310 paramclass_310, boolean paramBoolean3, int paramInt3);
/*     */   
/*     */   protected abstract String getFilterPlaceholder();
/*     */   
/*     */   protected abstract void beforeFiltering();
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\element\MapElementMenuRenderer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */