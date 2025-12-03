/*     */ package xaero.map.gui;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.class_11909;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_339;
/*     */ import net.minecraft.class_364;
/*     */ import net.minecraft.class_3675;
/*     */ import net.minecraft.class_437;
/*     */ import org.lwjgl.glfw.GLFW;
/*     */ import xaero.map.graphics.TextureUtils;
/*     */ import xaero.map.gui.dropdown.DropDownWidget;
/*     */ import xaero.map.misc.Misc;
/*     */ import xaero.map.render.util.GuiRenderUtil;
/*     */ 
/*     */ 
/*     */ public class ScreenBase
/*     */   extends class_437
/*     */   implements IScreenBase
/*     */ {
/*     */   public class_437 parent;
/*     */   public class_437 escape;
/*     */   protected boolean canSkipWorldRender;
/*     */   protected DropDownWidget openDropdown;
/*     */   private List<DropDownWidget> dropdowns;
/*     */   
/*     */   protected ScreenBase(class_437 parent, class_437 escape, class_2561 titleIn) {
/*  31 */     super(titleIn);
/*  32 */     this.parent = parent;
/*  33 */     this.escape = escape;
/*  34 */     this.canSkipWorldRender = true;
/*  35 */     this.dropdowns = new ArrayList<>();
/*     */   }
/*     */   
/*     */   protected void onExit(class_437 screen) {
/*  39 */     this.field_22787.method_1507(screen);
/*     */   }
/*     */   
/*     */   protected void goBack() {
/*  43 */     onExit(this.parent);
/*     */   }
/*     */ 
/*     */   
/*     */   public void method_25419() {
/*  48 */     onExit(this.escape);
/*     */   }
/*     */   
/*     */   public void renderEscapeScreen(class_332 guiGraphics, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
/*  52 */     if (this.escape != null) {
/*  53 */       this.escape.method_47413(guiGraphics, p_230430_2_, p_230430_3_, p_230430_4_);
/*  54 */       GuiRenderUtil.flushGUI();
/*  55 */       TextureUtils.clearRenderTargetDepth(this.field_22787.method_1522(), 1.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void method_25394(class_332 guiGraphics, int mouseX, int mouseY, float partial) {
/*  61 */     super.method_25394(guiGraphics, mouseX, mouseY, partial);
/*  62 */     renderPreDropdown(guiGraphics, mouseX, mouseY, partial);
/*  63 */     for (DropDownWidget dropdown : this.dropdowns)
/*  64 */       dropdown.method_25394(guiGraphics, mouseX, mouseY, partial); 
/*  65 */     if (this.openDropdown != null) {
/*  66 */       this.openDropdown.render(guiGraphics, mouseX, mouseY, this.field_22790, false);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderPreDropdown(class_332 guiGraphics, int mouseX, int mouseY, float partial) {}
/*     */ 
/*     */   
/*     */   protected void method_25426() {
/*  75 */     super.method_25426();
/*  76 */     this.dropdowns.clear();
/*  77 */     this.openDropdown = null;
/*  78 */     if (this.escape != null) {
/*  79 */       this.escape.method_25423(this.field_22787, this.field_22789, this.field_22790);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean method_25402(class_11909 event, boolean doubleClick) {
/*  84 */     if (this.openDropdown != null) {
/*  85 */       if (!this.openDropdown.onDropDown((int)event.comp_4798(), (int)event.comp_4799(), this.field_22790)) {
/*  86 */         this.openDropdown.setClosed(true);
/*  87 */         this.openDropdown = null;
/*     */       } else {
/*  89 */         this.openDropdown.method_25402(event, doubleClick);
/*  90 */         return true;
/*     */       } 
/*     */     }
/*  93 */     return super.method_25402(event, doubleClick);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean method_25401(double mouseX, double mouseY, double g, double wheel) {
/*  98 */     if (this.openDropdown != null) {
/*  99 */       if (this.openDropdown.onDropDown((int)mouseX, (int)mouseY, this.field_22790))
/* 100 */         return this.openDropdown.method_25401(mouseX, mouseY, g, wheel); 
/* 101 */       return true;
/*     */     } 
/* 103 */     return super.method_25401(mouseX, mouseY, g, wheel);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean method_25406(class_11909 event) {
/* 108 */     if (this.openDropdown != null && 
/* 109 */       this.openDropdown.method_25406(event))
/* 110 */       return true; 
/* 111 */     return super.method_25406(event);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldSkipWorldRender() {
/* 116 */     return (this.canSkipWorldRender && Misc.screenShouldSkipWorldRender(this.escape, true));
/*     */   }
/*     */   
/*     */   protected boolean renderTooltips(class_332 guiGraphics, int par1, int par2, float par3) {
/* 120 */     boolean result = false;
/* 121 */     boolean mousePressed = (GLFW.glfwGetMouseButton(this.field_22787.method_22683().method_4490(), 0) == 1);
/* 122 */     for (class_364 el : method_25396()) {
/* 123 */       if (!(el instanceof class_339))
/*     */         continue; 
/* 125 */       class_339 b = (class_339)el;
/* 126 */       if (b instanceof ICanTooltip && (!(b instanceof net.minecraft.class_357) || !mousePressed)) {
/* 127 */         ICanTooltip canTooltip = (ICanTooltip)b;
/* 128 */         if (par1 >= b.method_46426() && par2 >= b.method_46427() && par1 < b.method_46426() + b.method_25368() && par2 < b.method_46427() + b.method_25364() && 
/* 129 */           canTooltip.getXaero_wm_tooltip() != null) {
/* 130 */           CursorBox tooltip = canTooltip.getXaero_wm_tooltip().get();
/* 131 */           if (tooltip != null) {
/* 132 */             tooltip.drawBox(guiGraphics, par1, par2, this.field_22789, this.field_22790);
/* 133 */             result = true;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 140 */     return result;
/*     */   }
/*     */   
/*     */   public class_437 getEscape() {
/* 144 */     return this.escape;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDropdownOpen(DropDownWidget menu) {
/* 149 */     if (this.openDropdown != null && this.openDropdown != menu)
/* 150 */       this.openDropdown.setClosed(true); 
/* 151 */     this.openDropdown = menu;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDropdownClosed(DropDownWidget menu) {
/* 156 */     if (menu != this.openDropdown && this.openDropdown != null)
/* 157 */       this.openDropdown.setClosed(true); 
/* 158 */     this.openDropdown = null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected <T extends class_364 & net.minecraft.class_6379> T method_25429(T guiEventListener) {
/* 163 */     if (guiEventListener instanceof DropDownWidget)
/* 164 */       this.dropdowns.add((DropDownWidget)guiEventListener); 
/* 165 */     return (T)super.method_25429((class_364)guiEventListener);
/*     */   }
/*     */   
/*     */   private void handleDropdownReplacement(class_339 current, class_339 replacement) {
/* 169 */     int dropdownIndex = this.dropdowns.indexOf(current);
/* 170 */     if (dropdownIndex != -1)
/* 171 */       this.dropdowns.set(dropdownIndex, (DropDownWidget)replacement); 
/* 172 */     if (method_25399() == current) {
/* 173 */       method_25395((class_364)replacement);
/*     */     }
/*     */   }
/*     */   
/*     */   private void replaceWidget(class_339 current, class_339 replacement, boolean renderable) {
/* 178 */     int childIndex = method_25396().indexOf(current);
/* 179 */     if (childIndex != -1) {
/* 180 */       super.method_37066((class_364)current);
/* 181 */       if (renderable) {
/* 182 */         method_37063((class_364)replacement);
/*     */       } else {
/* 184 */         super.method_25429((class_364)replacement);
/* 185 */       }  method_25396().remove(replacement);
/* 186 */       method_25396().add(childIndex, replacement);
/*     */     } 
/* 188 */     handleDropdownReplacement(current, replacement);
/*     */   }
/*     */   
/*     */   public void replaceWidget(class_339 current, class_339 replacement) {
/* 192 */     replaceWidget(current, replacement, false);
/*     */   }
/*     */   
/*     */   public void replaceRenderableWidget(class_339 current, class_339 replacement) {
/* 196 */     replaceWidget(current, replacement, true);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void method_37066(class_364 current) {
/* 201 */     this.dropdowns.remove(current);
/* 202 */     super.method_37066(current);
/*     */   }
/*     */   
/*     */   public static boolean hasShiftDown() {
/* 206 */     return (class_3675.method_15987(class_310.method_1551().method_22683(), 340) || 
/* 207 */       class_3675.method_15987(class_310.method_1551().method_22683(), 344));
/*     */   }
/*     */   
/*     */   public static boolean hasControlDown() {
/* 211 */     return (class_3675.method_15987(class_310.method_1551().method_22683(), 341) || 
/* 212 */       class_3675.method_15987(class_310.method_1551().method_22683(), 345));
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\gui\ScreenBase.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */