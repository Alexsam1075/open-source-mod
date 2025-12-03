/*     */ package xaero.map.gui.dropdown;
/*     */ 
/*     */ import javax.annotation.Nonnull;
/*     */ import net.minecraft.class_1074;
/*     */ import net.minecraft.class_1109;
/*     */ import net.minecraft.class_1113;
/*     */ import net.minecraft.class_11905;
/*     */ import net.minecraft.class_11908;
/*     */ import net.minecraft.class_11909;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_339;
/*     */ import net.minecraft.class_3417;
/*     */ import net.minecraft.class_5250;
/*     */ import net.minecraft.class_6381;
/*     */ import net.minecraft.class_6382;
/*     */ import net.minecraft.class_6880;
/*     */ import xaero.map.gui.ScreenBase;
/*     */ 
/*     */ 
/*     */ public class DropDownWidget
/*     */   extends class_339
/*     */ {
/*     */   public static final int DEFAULT_BACKGROUND = -939524096;
/*     */   public static final int SELECTED_DEFAULT_BACKGROUND = -922757376;
/*     */   public static final int SELECTED_DEFAULT_HOVERED_BACKGROUND = -10496;
/*     */   public static final int TRIM = -6250336;
/*     */   public static final int TRIM_OPEN = -1;
/*     */   public static final int TRIM_INSIDE = -13487566;
/*     */   public static final int LINE_HEIGHT = 11;
/*  32 */   private int xOffset = 0;
/*  33 */   private int yOffset = 0;
/*  34 */   private String[] realOptions = new String[0];
/*  35 */   private String[] options = new String[0];
/*  36 */   private int selected = 0;
/*     */   
/*     */   private boolean closed = true;
/*     */   private int scroll;
/*     */   private long scrollTime;
/*     */   private int autoScrolling;
/*     */   protected boolean openingUp;
/*     */   private final IDropDownWidgetCallback callback;
/*     */   private final IDropDownContainer container;
/*     */   private final boolean hasEmptyOption;
/*     */   protected int selectedBackground;
/*     */   protected int selectedHoveredBackground;
/*     */   protected boolean shortenFromTheRight;
/*     */   private boolean wasHovered;
/*     */   
/*     */   protected DropDownWidget(String[] options, int x, int y, int w, Integer selected, boolean openingUp, IDropDownWidgetCallback callback, IDropDownContainer container, boolean hasEmptyOption, class_2561 narrationTitle) {
/*  52 */     super(x, y, w, 11, narrationTitle);
/*  53 */     this.realOptions = options;
/*  54 */     this.callback = callback;
/*  55 */     this.container = container;
/*  56 */     int emptyOptionCount = hasEmptyOption ? 1 : 0;
/*  57 */     this.options = new String[this.realOptions.length + emptyOptionCount];
/*  58 */     System.arraycopy(this.realOptions, 0, this.options, emptyOptionCount, this.realOptions.length);
/*  59 */     selectId(selected.intValue(), false);
/*  60 */     this.openingUp = openingUp;
/*  61 */     this.hasEmptyOption = hasEmptyOption;
/*  62 */     this.selectedBackground = -922757376;
/*  63 */     this.selectedHoveredBackground = -10496;
/*  64 */     this.field_22763 = true;
/*     */   }
/*     */   
/*     */   public int size() {
/*  68 */     return this.realOptions.length;
/*     */   }
/*     */   
/*     */   public int getXWithOffset() {
/*  72 */     return method_46426() + this.xOffset;
/*     */   }
/*     */   
/*     */   public int getRenderY() {
/*  76 */     return method_46427() + (this.openingUp ? 11 : 0);
/*     */   }
/*     */   
/*     */   public int getRenderYWithOffset() {
/*  80 */     return getRenderY() + this.yOffset;
/*     */   }
/*     */ 
/*     */   
/*     */   private void drawSlot(class_332 guiGraphics, String text, int slotIndex, int pos, int mouseX, int mouseY, boolean scrolling, int optionLimit, int xWithOffset, int yWithOffset) {
/*  85 */     int slotBackground, emptyOptionCount = this.hasEmptyOption ? 1 : 0;
/*  86 */     if ((this.closed && method_25367()) || (!this.closed && onDropDownSlot(mouseX, mouseY, slotIndex, scrolling, optionLimit))) {
/*  87 */       slotBackground = (slotIndex - emptyOptionCount == this.selected) ? this.selectedHoveredBackground : -13487566;
/*     */     } else {
/*  89 */       slotBackground = (slotIndex - emptyOptionCount == this.selected) ? this.selectedBackground : -939524096;
/*  90 */     }  if (this.openingUp)
/*  91 */       pos = -pos - 1; 
/*  92 */     guiGraphics.method_25294(xWithOffset, yWithOffset + 11 * pos, xWithOffset + this.field_22758, yWithOffset + 11 + 11 * pos, slotBackground);
/*     */     
/*  94 */     guiGraphics.method_51738(xWithOffset + 1, xWithOffset + this.field_22758 - 1, yWithOffset + 11 * pos, -13487566);
/*  95 */     int textWidth = (class_310.method_1551()).field_1772.method_1727(text);
/*  96 */     boolean shortened = false;
/*  97 */     while (textWidth > this.field_22758 - 2) {
/*  98 */       text = this.shortenFromTheRight ? text.substring(0, text.length() - 1) : text.substring(1);
/*  99 */       textWidth = (class_310.method_1551()).field_1772.method_1727("..." + text);
/* 100 */       shortened = true;
/*     */     } 
/* 102 */     if (shortened)
/* 103 */       if (this.shortenFromTheRight) {
/* 104 */         text = text + "...";
/*     */       } else {
/* 106 */         text = "..." + text;
/* 107 */       }   int textColor = -1;
/* 108 */     guiGraphics.method_25300((class_310.method_1551()).field_1772, text, xWithOffset + 1 + this.field_22758 / 2, yWithOffset + 2 + 11 * pos, textColor);
/*     */   }
/*     */   
/*     */   private void drawMenu(class_332 guiGraphics, int amount, int mouseX, int mouseY, int scaledHeight, int optionLimit) {
/* 112 */     boolean scrolling = scrolling(optionLimit);
/* 113 */     int totalH = 11 * (amount + (scrolling ? 2 : 0));
/* 114 */     int height = scaledHeight;
/* 115 */     int renderY = getRenderY();
/* 116 */     if (!this.openingUp && renderY + totalH + 1 > height) {
/* 117 */       this.yOffset = height - renderY - totalH - 1;
/* 118 */     } else if (this.openingUp && renderY - totalH < 0) {
/* 119 */       this.yOffset = totalH - renderY;
/*     */     } else {
/* 121 */       this.yOffset = 0;
/* 122 */     }  int xWithOffset = getXWithOffset();
/* 123 */     int yWithOffset = getRenderYWithOffset();
/* 124 */     int first = this.closed ? 0 : this.scroll;
/* 125 */     if (scrolling) {
/* 126 */       drawSlot(guiGraphics, ((this.scroll == 0) ? "§8" : "§7") + ((this.scroll == 0) ? "§8" : "§7"), -1, 0, mouseX, mouseY, scrolling, optionLimit, xWithOffset, yWithOffset);
/* 127 */       drawSlot(guiGraphics, ((this.scroll + optionLimit >= this.options.length) ? "§8" : "§7") + ((this.scroll + optionLimit >= this.options.length) ? "§8" : "§7"), -2, amount + 1, mouseX, mouseY, scrolling, optionLimit, xWithOffset, yWithOffset);
/*     */     } 
/* 129 */     for (int i = first; i < first + amount; i++) {
/*     */       String slotText;
/* 131 */       if (this.hasEmptyOption && i == 0) {
/* 132 */         slotText = !this.closed ? "-" : class_1074.method_4662(this.realOptions[this.selected], new Object[0]).replace("§§", ":");
/*     */       } else {
/* 134 */         slotText = class_1074.method_4662(this.options[i], new Object[0]).replace("§§", ":");
/* 135 */       }  drawSlot(guiGraphics, slotText, i, i - first + (scrolling ? 1 : 0), mouseX, mouseY, scrolling, optionLimit, xWithOffset, yWithOffset);
/*     */     } 
/* 137 */     int trimPosY = yWithOffset - (this.openingUp ? totalH : 0);
/* 138 */     int trim = this.closed ? -6250336 : -1;
/* 139 */     guiGraphics.method_51742(xWithOffset, trimPosY, trimPosY + totalH, trim);
/* 140 */     guiGraphics.method_51742(xWithOffset + this.field_22758, trimPosY, trimPosY + totalH, trim);
/* 141 */     guiGraphics.method_51738(xWithOffset, xWithOffset + this.field_22758, trimPosY, trim);
/* 142 */     guiGraphics.method_51738(xWithOffset, xWithOffset + this.field_22758, trimPosY + totalH, trim);
/*     */   }
/*     */   
/*     */   private boolean scrolling(int optionLimit) {
/* 146 */     return (this.options.length > optionLimit && !this.closed);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(int mouseX, int mouseY, int mouseButton, int scaledHeight) {
/* 151 */     if (mouseButton != 0)
/* 152 */       return false; 
/* 153 */     if (!this.closed) {
/* 154 */       int optionLimit = optionLimit(scaledHeight);
/* 155 */       int clickedId = getHoveredId(mouseX, mouseY, scrolling(optionLimit), optionLimit);
/* 156 */       if (clickedId >= 0) {
/* 157 */         selectId(clickedId - (this.hasEmptyOption ? 1 : 0), true);
/*     */       } else {
/* 159 */         this.autoScrolling = (clickedId == -1) ? 1 : -1;
/* 160 */         this.scrollTime = System.currentTimeMillis();
/* 161 */         mouseScrolledInternal(this.autoScrolling, mouseX, mouseY, optionLimit);
/*     */       } 
/* 163 */       class_310.method_1551().method_1483().method_4873((class_1113)class_1109.method_47978((class_6880)class_3417.field_15015, 1.0F));
/* 164 */     } else if (this.options.length > 1 && this.field_22763) {
/* 165 */       setClosed(false);
/* 166 */       this.scroll = 0;
/*     */     } 
/* 168 */     return true;
/*     */   }
/*     */   
/*     */   public void mouseReleased(int mouseX, int mouseY, int mouseButton, int scaledHeight) {
/* 172 */     this.autoScrolling = 0;
/*     */   }
/*     */   
/*     */   private int getHoveredId(int mouseX, int mouseY, boolean scrolling, int optionLimit) {
/* 176 */     int yOnMenu = mouseY - getRenderYWithOffset();
/* 177 */     int visibleSlotIndex = (this.openingUp ? (-yOnMenu - 1) : yOnMenu) / 11;
/* 178 */     if (scrolling && visibleSlotIndex == 0)
/* 179 */       return -1; 
/* 180 */     if (visibleSlotIndex >= optionLimit + (scrolling ? 1 : 0))
/* 181 */       return -2; 
/* 182 */     int slot = this.scroll + visibleSlotIndex - (scrolling ? 1 : 0);
/* 183 */     if (slot >= this.options.length)
/* 184 */       slot = this.options.length - 1; 
/* 185 */     return slot;
/*     */   }
/*     */   
/*     */   public boolean onDropDown(int mouseX, int mouseY, int scaledHeight) {
/* 189 */     int optionLimit = optionLimit(scaledHeight);
/* 190 */     return onDropDown(mouseX, mouseY, scrolling(optionLimit), optionLimit);
/*     */   }
/*     */   
/*     */   public boolean onDropDown(int mouseX, int mouseY, boolean scrolling, int optionLimit) {
/* 194 */     int menuTop = getRenderYWithOffset();
/* 195 */     int menuHeight = this.closed ? 11 : ((Math.min(this.options.length, optionLimit) + (scrolling ? 2 : 0)) * 11);
/* 196 */     if (this.openingUp)
/* 197 */       menuTop -= menuHeight; 
/* 198 */     int xOnMenu = mouseX - getXWithOffset();
/* 199 */     int yOnMenu = mouseY - menuTop;
/* 200 */     if (xOnMenu < 0 || yOnMenu < 0 || xOnMenu > this.field_22758 || yOnMenu >= menuHeight)
/* 201 */       return false; 
/* 202 */     return true;
/*     */   }
/*     */   
/*     */   private boolean onDropDownSlot(int mouseX, int mouseY, int id, boolean scrolling, int optionLimit) {
/* 206 */     if (!onDropDown(mouseX, mouseY, scrolling, optionLimit))
/* 207 */       return false; 
/* 208 */     int hoveredSlot = getHoveredId(mouseX, mouseY, scrolling, optionLimit);
/* 209 */     return (hoveredSlot == id);
/*     */   }
/*     */   
/*     */   public void selectId(int id, boolean callCallback) {
/* 213 */     if (id == -1) {
/* 214 */       setClosed(true);
/*     */       return;
/*     */     } 
/* 217 */     boolean newId = (id != this.selected);
/* 218 */     if (newId && (!callCallback || this.callback.onSelected(this, id)))
/* 219 */       this.selected = id; 
/* 220 */     setClosed(true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void method_48579(@Nonnull class_332 guiGraphics, int mouseX, int mouseY, float partial) {
/* 226 */     int scaledHeight = (class_310.method_1551()).field_1755.field_22790;
/* 227 */     this.field_22762 = (this.field_22764 && onDropDown(mouseX, mouseY, scaledHeight));
/* 228 */     if (!this.field_22764)
/*     */       return; 
/* 230 */     render(guiGraphics, mouseX, mouseY, (class_310.method_1551()).field_1755.field_22790, true);
/*     */   }
/*     */   
/*     */   public void render(class_332 guiGraphics, int mouseX, int mouseY, int scaledHeight, boolean closedOnly) {
/* 234 */     if (!this.closed && closedOnly)
/*     */       return; 
/* 236 */     int optionLimit = optionLimit(scaledHeight);
/* 237 */     if (this.autoScrolling != 0 && System.currentTimeMillis() - this.scrollTime > 100L) {
/* 238 */       this.scrollTime = System.currentTimeMillis();
/* 239 */       mouseScrolledInternal(this.autoScrolling, mouseX, mouseY, optionLimit);
/*     */     } 
/* 241 */     drawMenu(guiGraphics, this.closed ? 1 : Math.min(optionLimit, this.options.length), mouseX, mouseY, scaledHeight, optionLimit);
/*     */   }
/*     */   
/*     */   public boolean isClosed() {
/* 245 */     return this.closed;
/*     */   }
/*     */   
/*     */   public void setClosed(boolean closed) {
/* 249 */     if (this.closed != closed)
/* 250 */       if (!closed) {
/* 251 */         class_310.method_1551().method_1483().method_4873((class_1113)class_1109.method_47978((class_6880)class_3417.field_15015, 1.0F));
/* 252 */         this.container.onDropdownOpen(this);
/*     */       } else {
/* 254 */         this.container.onDropdownClosed(this);
/*     */       }  
/* 256 */     this.closed = closed;
/*     */   }
/*     */   
/*     */   public void mouseScrolled(int wheel, int mouseXScaled, int mouseYScaled, int scaledHeight) {
/* 260 */     mouseScrolledInternal(wheel * (this.openingUp ? -1 : 1), mouseXScaled, mouseYScaled, optionLimit(scaledHeight));
/*     */   }
/*     */   
/*     */   private void mouseScrolledInternal(int wheel, int mouseXScaled, int mouseYScaled, int optionLimit) {
/* 264 */     int newScroll = this.scroll - wheel;
/* 265 */     if (newScroll + optionLimit > this.options.length)
/* 266 */       newScroll = this.options.length - optionLimit; 
/* 267 */     if (newScroll < 0)
/* 268 */       newScroll = 0; 
/* 269 */     this.scroll = newScroll;
/*     */   }
/*     */   
/*     */   private int optionLimit(int scaledHeight) {
/* 273 */     return Math.max(1, scaledHeight / 11 - 2);
/*     */   }
/*     */   
/*     */   public int getSelected() {
/* 277 */     return this.selected;
/*     */   }
/*     */ 
/*     */   
/*     */   public void method_47399(class_6382 narrationElementOutput) {
/* 282 */     if (method_25369() != null) {
/* 283 */       narrationElementOutput.method_37034(class_6381.field_33788, (class_2561)method_25360());
/*     */     }
/*     */   }
/*     */   
/*     */   protected class_5250 method_25360() {
/* 288 */     class_5250 narrationMessage = class_2561.method_43470("");
/* 289 */     narrationMessage.method_10855().add(method_25369());
/* 290 */     narrationMessage.method_10855().add(class_2561.method_43470(". "));
/* 291 */     narrationMessage.method_10855().add(class_2561.method_43469("gui.xaero_dropdown_selected_narration", new Object[] { this.realOptions[this.selected].replaceAll("(§[0-9a-gr])+", "") }));
/* 292 */     return narrationMessage;
/*     */   }
/*     */ 
/*     */   
/*     */   public void method_16014(double $$0, double $$1) {
/* 297 */     super.method_16014($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean method_25401(double mouseXScaled, double mouseYScaled, double g, double wheel) {
/* 302 */     if (!isClosed()) {
/* 303 */       mouseScrolled((int)wheel, (int)mouseXScaled, (int)mouseYScaled, (class_310.method_1551()).field_1755.field_22790);
/* 304 */       return true;
/*     */     } 
/* 306 */     return super.method_25401(mouseXScaled, mouseYScaled, g, wheel);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean method_25404(class_11908 event) {
/* 311 */     if (event.comp_4795() == 257 || event.comp_4795() == 32) {
/*     */       int nextSelection;
/* 313 */       if (ScreenBase.hasShiftDown()) {
/* 314 */         nextSelection = getSelected() - 1;
/* 315 */         if (nextSelection < 0)
/* 316 */           nextSelection = this.realOptions.length - 1; 
/*     */       } else {
/* 318 */         nextSelection = (getSelected() + 1) % this.realOptions.length;
/* 319 */       }  selectId(nextSelection, true);
/* 320 */       return true;
/*     */     } 
/* 322 */     return super.method_25404(event);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean method_16803(class_11908 event) {
/* 327 */     return super.method_16803(event);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean method_25400(class_11905 event) {
/* 332 */     return super.method_25400(event);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean method_25402(class_11909 event, boolean doubleClick) {
/* 337 */     if (this.field_22762) {
/* 338 */       int scaledHeight = (class_310.method_1551()).field_1755.field_22790;
/* 339 */       return mouseClicked((int)event.comp_4798(), (int)event.comp_4799(), event.method_74245(), scaledHeight);
/*     */     } 
/* 341 */     return super.method_25402(event, doubleClick);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean method_25406(class_11909 event) {
/* 346 */     int scaledHeight = (class_310.method_1551()).field_1755.field_22790;
/* 347 */     mouseReleased((int)event.comp_4798(), (int)event.comp_4799(), event.method_74245(), scaledHeight);
/* 348 */     return false;
/*     */   }
/*     */   
/*     */   public void setActive(boolean b) {
/* 352 */     this.field_22763 = b;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class Builder
/*     */   {
/*     */     private String[] options;
/*     */     
/*     */     private int x;
/*     */     private int y;
/*     */     private int w;
/*     */     private Integer selected;
/*     */     private boolean openingUp;
/*     */     private IDropDownWidgetCallback callback;
/*     */     private IDropDownContainer container;
/*     */     private boolean hasEmptyOption;
/*     */     private class_2561 narrationTitle;
/*     */     
/*     */     public Builder setDefault() {
/* 371 */       setOptions(null);
/* 372 */       setX(0);
/* 373 */       setY(0);
/* 374 */       setW(0);
/* 375 */       setSelected(null);
/* 376 */       setOpeningUp(false);
/* 377 */       setCallback(null);
/* 378 */       setHasEmptyOption(true);
/* 379 */       setNarrationTitle(null);
/* 380 */       return this;
/*     */     }
/*     */     
/*     */     public Builder setOptions(String[] options) {
/* 384 */       this.options = options;
/* 385 */       return this;
/*     */     }
/*     */     
/*     */     public Builder setX(int x) {
/* 389 */       this.x = x;
/* 390 */       return this;
/*     */     }
/*     */     
/*     */     public Builder setY(int y) {
/* 394 */       this.y = y;
/* 395 */       return this;
/*     */     }
/*     */     
/*     */     public Builder setW(int w) {
/* 399 */       this.w = w;
/* 400 */       return this;
/*     */     }
/*     */     
/*     */     public Builder setSelected(Integer selected) {
/* 404 */       this.selected = selected;
/* 405 */       return this;
/*     */     }
/*     */     
/*     */     public Builder setOpeningUp(boolean openingUp) {
/* 409 */       this.openingUp = openingUp;
/* 410 */       return this;
/*     */     }
/*     */     
/*     */     public Builder setCallback(IDropDownWidgetCallback callback) {
/* 414 */       this.callback = callback;
/* 415 */       return this;
/*     */     }
/*     */     
/*     */     public Builder setContainer(IDropDownContainer container) {
/* 419 */       this.container = container;
/* 420 */       return this;
/*     */     }
/*     */     
/*     */     public Builder setHasEmptyOption(boolean hasEmptyOption) {
/* 424 */       this.hasEmptyOption = hasEmptyOption;
/* 425 */       return this;
/*     */     }
/*     */     
/*     */     public Builder setNarrationTitle(class_2561 narrationTitle) {
/* 429 */       this.narrationTitle = narrationTitle;
/* 430 */       return this;
/*     */     }
/*     */     
/*     */     public DropDownWidget build() {
/* 434 */       if (this.options == null || this.w == 0 || this.selected == null || this.callback == null || this.narrationTitle == null || this.container == null)
/* 435 */         throw new IllegalStateException(); 
/* 436 */       return new DropDownWidget(this.options, this.x, this.y, this.w, this.selected, this.openingUp, this.callback, this.container, this.hasEmptyOption, this.narrationTitle);
/*     */     }
/*     */     
/*     */     public static Builder begin() {
/* 440 */       return (new Builder()).setDefault();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\gui\dropdown\DropDownWidget.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */