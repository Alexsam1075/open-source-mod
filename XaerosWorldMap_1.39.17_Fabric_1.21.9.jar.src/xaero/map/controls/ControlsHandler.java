/*     */ package xaero.map.controls;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_2960;
/*     */ import net.minecraft.class_304;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_3675;
/*     */ import net.minecraft.class_437;
/*     */ import org.lwjgl.glfw.GLFW;
/*     */ import xaero.map.MapProcessor;
/*     */ import xaero.map.WorldMap;
/*     */ import xaero.map.WorldMapSession;
/*     */ import xaero.map.gui.GuiMap;
/*     */ import xaero.map.gui.GuiWorldMapSettings;
/*     */ import xaero.map.gui.ScreenBase;
/*     */ import xaero.map.platform.Services;
/*     */ 
/*     */ 
/*     */ public class ControlsHandler
/*     */ {
/*     */   private MapProcessor mapProcessor;
/*  24 */   private ArrayList<KeyEvent> keyEvents = new ArrayList<>();
/*  25 */   private ArrayList<KeyEvent> oldKeyEvents = new ArrayList<>();
/*     */   
/*     */   public ControlsHandler(MapProcessor mapProcessor) {
/*  28 */     this.mapProcessor = mapProcessor;
/*     */   }
/*     */   
/*     */   private boolean eventExists(class_304 kb) {
/*  32 */     for (KeyEvent o : this.keyEvents) {
/*  33 */       if (o.getKb() == kb)
/*  34 */         return true; 
/*  35 */     }  return oldEventExists(kb);
/*     */   }
/*     */   
/*     */   private boolean oldEventExists(class_304 kb) {
/*  39 */     for (KeyEvent o : this.oldKeyEvents) {
/*  40 */       if (o.getKb() == kb)
/*  41 */         return true; 
/*  42 */     }  return false;
/*     */   }
/*     */   
/*     */   public static void setKeyState(class_304 kb, boolean pressed) {
/*  46 */     if (kb.method_1434() != pressed)
/*  47 */       class_304.method_1416(Services.PLATFORM.getKeyBindingHelper().getBoundKeyOf(kb), pressed); 
/*     */   }
/*     */   
/*     */   public static boolean isDown(class_304 kb) {
/*  51 */     IKeyBindingHelper keyBindingHelper = Services.PLATFORM.getKeyBindingHelper();
/*  52 */     if (keyBindingHelper.getBoundKeyOf(kb).method_1444() == -1)
/*  53 */       return false; 
/*  54 */     if (keyBindingHelper.getBoundKeyOf(kb).method_1442() == class_3675.class_307.field_1672)
/*  55 */       return (GLFW.glfwGetMouseButton(class_310.method_1551().method_22683().method_4490(), keyBindingHelper.getBoundKeyOf(kb).method_1444()) == 1); 
/*  56 */     if (keyBindingHelper.getBoundKeyOf(kb).method_1442() == class_3675.class_307.field_1668)
/*  57 */       return class_3675.method_15987(class_310.method_1551().method_22683(), keyBindingHelper.getBoundKeyOf(kb).method_1444()); 
/*  58 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean isKeyRepeat(class_304 kb) {
/*  62 */     if (kb == ControlsRegister.keyOpenMap || kb == ControlsRegister.keyOpenSettings || kb == ControlsRegister.keyToggleDimension)
/*  63 */       return false; 
/*  64 */     return true;
/*     */   }
/*     */   
/*     */   public void keyDown(class_304 kb, boolean tickEnd, boolean isRepeat) {
/*  68 */     class_310 mc = class_310.method_1551();
/*  69 */     if (!tickEnd) {
/*  70 */       if (kb == ControlsRegister.keyOpenMap) {
/*  71 */         mc.method_1507((class_437)new GuiMap(null, null, this.mapProcessor, mc.method_1560()));
/*  72 */       } else if (kb == ControlsRegister.keyOpenSettings) {
/*  73 */         mc.method_1507((class_437)new GuiWorldMapSettings());
/*  74 */       } else if (kb == ControlsRegister.keyQuickConfirm) {
/*  75 */         WorldMapSession worldmapSession = WorldMapSession.getCurrentSession();
/*  76 */         MapProcessor mapProcessor = worldmapSession.getMapProcessor();
/*  77 */         synchronized (mapProcessor.uiPauseSync) {
/*  78 */           if (!mapProcessor.isUIPaused())
/*  79 */             mapProcessor.quickConfirmMultiworld(); 
/*     */         } 
/*  81 */       } else if (kb == ControlsRegister.keyToggleDimension) {
/*  82 */         this.mapProcessor.getMapWorld().toggleDimension(!ScreenBase.hasShiftDown());
/*  83 */         String messageType = (this.mapProcessor.getMapWorld().getCustomDimensionId() == null) ? "gui.xaero_switched_to_current_dimension" : "gui.xaero_switched_to_dimension";
/*  84 */         class_2960 messageDimLoc = (this.mapProcessor.getMapWorld().getFutureDimensionId() == null) ? null : this.mapProcessor.getMapWorld().getFutureDimensionId().method_29177();
/*  85 */         mc.field_1705.method_1743().method_1812((class_2561)class_2561.method_43469(messageType, new Object[] { messageDimLoc.toString() }));
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void keyUp(class_304 kb, boolean tickEnd) {
/*  93 */     if (!tickEnd);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleKeyEvents() {
/*  99 */     class_310 mc = class_310.method_1551();
/* 100 */     onKeyInput(mc);
/*     */     
/*     */     int i;
/* 103 */     for (i = 0; i < this.keyEvents.size(); i++) {
/* 104 */       KeyEvent ke = this.keyEvents.get(i);
/* 105 */       if (mc.field_1755 == null) {
/* 106 */         keyDown(ke.getKb(), ke.isTickEnd(), ke.isRepeat());
/*     */       }
/*     */ 
/*     */       
/* 110 */       if (!ke.isRepeat()) {
/* 111 */         if (!oldEventExists(ke.getKb()))
/* 112 */           this.oldKeyEvents.add(ke); 
/* 113 */         this.keyEvents.remove(i);
/* 114 */         i--;
/* 115 */       } else if (!isDown(ke.getKb())) {
/* 116 */         keyUp(ke.getKb(), ke.isTickEnd());
/*     */ 
/*     */         
/* 119 */         this.keyEvents.remove(i);
/* 120 */         i--;
/*     */       } 
/*     */     } 
/* 123 */     for (i = 0; i < this.oldKeyEvents.size(); i++) {
/* 124 */       KeyEvent ke = this.oldKeyEvents.get(i);
/* 125 */       if (!isDown(ke.getKb())) {
/* 126 */         keyUp(ke.getKb(), ke.isTickEnd());
/*     */ 
/*     */         
/* 129 */         this.oldKeyEvents.remove(i);
/* 130 */         i--;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void onKeyInput(class_310 mc) {
/* 136 */     List<class_304> kbs = WorldMap.controlsRegister.keybindings;
/* 137 */     for (int i = 0; i < kbs.size(); i++) {
/* 138 */       class_304 kb = kbs.get(i);
/*     */       try {
/* 140 */         boolean pressed = kb.method_1436();
/* 141 */         while (kb.method_1436());
/* 142 */         if (mc.field_1755 == null && !eventExists(kb) && pressed)
/*     */         {
/* 144 */           this.keyEvents.add(new KeyEvent(kb, false, 
/* 145 */                 isKeyRepeat(kb), true));
/*     */         }
/* 147 */       } catch (Exception exception) {}
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\controls\ControlsHandler.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */