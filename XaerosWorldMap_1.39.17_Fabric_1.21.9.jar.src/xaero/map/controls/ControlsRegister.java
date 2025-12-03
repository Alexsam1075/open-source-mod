/*    */ package xaero.map.controls;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.List;
/*    */ import java.util.function.Consumer;
/*    */ import net.minecraft.class_2960;
/*    */ import net.minecraft.class_304;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ControlsRegister
/*    */ {
/* 14 */   public static final class_304.class_11900 CATEGORY = class_304.class_11900.method_74698(class_2960.method_60655("xaeroworldmap", "controls"));
/*    */   
/* 16 */   public static final class_304 keyOpenMap = new class_304("gui.xaero_open_map", 77, CATEGORY);
/* 17 */   public static final class_304 keyOpenSettings = new class_304("gui.xaero_open_settings", 93, CATEGORY);
/* 18 */   public static final class_304 keyZoomIn = new class_304("gui.xaero_map_zoom_in", -1, CATEGORY);
/* 19 */   public static final class_304 keyZoomOut = new class_304("gui.xaero_map_zoom_out", -1, CATEGORY);
/* 20 */   public static final class_304 keyQuickConfirm = new class_304("gui.xaero_quick_confirm", 344, CATEGORY);
/* 21 */   public static final class_304 keyToggleDimension = new class_304("gui.xaero_toggle_dimension", -1, CATEGORY);
/*    */   
/*    */   public static class_304 keyToggleTrackedPlayers;
/*    */   
/*    */   public static class_304 keyTogglePacChunkClaims;
/*    */   
/* 27 */   public final List<class_304> keybindings = Lists.newArrayList((Object[])new class_304[] { keyOpenMap, keyOpenSettings, keyZoomIn, keyZoomOut, keyQuickConfirm, keyToggleDimension });
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void register(Consumer<class_304> registry, Consumer<class_304.class_11900> categoryRegistry) {
/* 34 */     categoryRegistry.accept(CATEGORY);
/* 35 */     for (class_304 kb : this.keybindings)
/* 36 */       registry.accept(kb); 
/*    */     try {
/* 38 */       Class.forName("xaero.common.IXaeroMinimap");
/* 39 */     } catch (ClassNotFoundException cnfe) {
/*    */       
/* 41 */       keyToggleTrackedPlayers = new class_304("gui.xaero_toggle_tracked_players", -1, CATEGORY);
/* 42 */       registry.accept(keyToggleTrackedPlayers);
/* 43 */       keyTogglePacChunkClaims = new class_304("gui.xaero_toggle_pac_chunk_claims", -1, CATEGORY);
/* 44 */       registry.accept(keyTogglePacChunkClaims);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\controls\ControlsRegister.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */