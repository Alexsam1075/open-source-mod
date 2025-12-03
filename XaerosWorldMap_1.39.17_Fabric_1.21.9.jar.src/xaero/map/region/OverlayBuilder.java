/*    */ package xaero.map.region;
/*    */ 
/*    */ import net.minecraft.class_1058;
/*    */ import net.minecraft.class_1959;
/*    */ import net.minecraft.class_2246;
/*    */ import net.minecraft.class_2680;
/*    */ import net.minecraft.class_310;
/*    */ import net.minecraft.class_5321;
/*    */ import xaero.map.MapProcessor;
/*    */ 
/*    */ 
/*    */ public class OverlayBuilder
/*    */ {
/*    */   private static final int MAX_OVERLAYS = 10;
/*    */   private Overlay[] overlayBuildingSet;
/*    */   private int currentOverlayIndex;
/*    */   private OverlayManager overlayManager;
/*    */   private class_1058 prevIcon;
/*    */   private class_5321<class_1959> overlayBiome;
/*    */   
/*    */   public OverlayBuilder(OverlayManager overlayManager) {
/* 22 */     this.overlayManager = overlayManager;
/* 23 */     this.overlayBuildingSet = new Overlay[10];
/* 24 */     for (int i = 0; i < this.overlayBuildingSet.length; i++)
/* 25 */       this.overlayBuildingSet[i] = new Overlay(class_2246.field_10124.method_9564(), (byte)0, false); 
/* 26 */     this.currentOverlayIndex = -1;
/*    */   }
/*    */   
/*    */   public void startBuilding() {
/* 30 */     this.currentOverlayIndex = -1;
/* 31 */     setOverlayBiome(null);
/*    */   }
/*    */   
/*    */   public void build(class_2680 state, int opacity, byte light, MapProcessor mapProcessor, class_5321<class_1959> biomeId) {
/* 35 */     Overlay currentOverlay = getCurrentOverlay();
/* 36 */     Overlay nextOverlay = null;
/* 37 */     if (this.currentOverlayIndex < this.overlayBuildingSet.length - 1)
/* 38 */       nextOverlay = this.overlayBuildingSet[this.currentOverlayIndex + 1]; 
/* 39 */     class_1058 icon = null;
/* 40 */     boolean changed = false;
/* 41 */     if (currentOverlay == null || currentOverlay.getState() != state) {
/* 42 */       icon = class_310.method_1551().method_1541().method_3351().method_3339(state);
/* 43 */       changed = (icon != this.prevIcon);
/*    */     } 
/* 45 */     if (nextOverlay != null && (currentOverlay == null || changed)) {
/* 46 */       boolean glowing = false;
/*    */       try {
/* 48 */         glowing = mapProcessor.getMapWriter().isGlowing(state);
/*    */       
/*    */       }
/* 51 */       catch (Exception exception) {}
/*    */ 
/*    */       
/* 54 */       if (getOverlayBiome() == null)
/* 55 */         setOverlayBiome(biomeId); 
/* 56 */       nextOverlay.write(state, light, glowing);
/* 57 */       currentOverlay = nextOverlay;
/* 58 */       this.currentOverlayIndex++;
/*    */     } 
/* 60 */     currentOverlay.increaseOpacity(opacity);
/* 61 */     if (changed)
/* 62 */       this.prevIcon = icon; 
/*    */   }
/*    */   
/*    */   public boolean isEmpty() {
/* 66 */     return (this.currentOverlayIndex < 0);
/*    */   }
/*    */   
/*    */   public Overlay getCurrentOverlay() {
/* 70 */     Overlay currentOverlay = null;
/* 71 */     if (this.currentOverlayIndex >= 0)
/* 72 */       currentOverlay = this.overlayBuildingSet[this.currentOverlayIndex]; 
/* 73 */     return currentOverlay;
/*    */   }
/*    */   
/*    */   public void finishBuilding(MapBlock block) {
/* 77 */     for (int i = 0; i <= this.currentOverlayIndex; i++) {
/* 78 */       Overlay o = this.overlayBuildingSet[i];
/* 79 */       Overlay original = this.overlayManager.getOriginal(o);
/* 80 */       if (o == original)
/* 81 */         this.overlayBuildingSet[i] = new Overlay(class_2246.field_10124.method_9564(), (byte)0, false); 
/* 82 */       block.addOverlay(original);
/*    */     } 
/*    */   }
/*    */   
/*    */   public class_5321<class_1959> getOverlayBiome() {
/* 87 */     return this.overlayBiome;
/*    */   }
/*    */   
/*    */   public void setOverlayBiome(class_5321<class_1959> overlayBiome) {
/* 91 */     this.overlayBiome = overlayBiome;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\region\OverlayBuilder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */