/*    */ package xaero.map;
/*    */ 
/*    */ import java.util.Deque;
/*    */ import java.util.Iterator;
/*    */ import java.util.concurrent.LinkedBlockingDeque;
/*    */ import net.minecraft.class_310;
/*    */ import xaero.map.file.RegionDetection;
/*    */ import xaero.map.region.LeveledRegion;
/*    */ import xaero.map.region.MapRegion;
/*    */ import xaero.map.world.MapDimension;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MapFullReloader
/*    */ {
/*    */   public static final String CONVERTED_WORLD_SAVE_MW = "cm$converted";
/*    */   private final int caveLayer;
/*    */   private final boolean resave;
/*    */   private final Iterator<RegionDetection> regionDetectionIterator;
/*    */   private final Deque<RegionDetection> retryLaterDeque;
/*    */   private final MapDimension mapDimension;
/*    */   private final MapProcessor mapProcessor;
/*    */   private MapRegion lastRequestedRegion;
/*    */   
/*    */   public MapFullReloader(int caveLayer, boolean resave, Iterator<RegionDetection> regionDetectionIterator, MapDimension mapDimension, MapProcessor mapProcessor) {
/* 30 */     this.caveLayer = caveLayer;
/* 31 */     this.resave = resave;
/* 32 */     this.regionDetectionIterator = regionDetectionIterator;
/* 33 */     this.retryLaterDeque = new LinkedBlockingDeque<>();
/* 34 */     this.mapDimension = mapDimension;
/* 35 */     this.mapProcessor = mapProcessor;
/*    */   }
/*    */   
/*    */   public void onRenderProcess() {
/* 39 */     LeveledRegion<?> nextToLoad = this.mapProcessor.getMapSaveLoad().getNextToLoadByViewing();
/* 40 */     if (nextToLoad == null || nextToLoad.shouldAllowAnotherRegionToLoad()) {
/*    */       RegionDetection next;
/* 42 */       if (!this.regionDetectionIterator.hasNext()) {
/* 43 */         next = this.retryLaterDeque.isEmpty() ? null : this.retryLaterDeque.removeFirst();
/*    */       } else {
/* 45 */         next = this.regionDetectionIterator.next();
/* 46 */       }  if (next != null) {
/* 47 */         MapRegion nextRegionToReload = this.mapProcessor.getLeafMapRegion(this.caveLayer, next.getRegionX(), next.getRegionZ(), true);
/* 48 */         if (nextRegionToReload == null) {
/* 49 */           this.retryLaterDeque.add(next);
/*    */           return;
/*    */         } 
/* 52 */         nextRegionToReload.setHasHadTerrain();
/* 53 */         synchronized (nextRegionToReload) {
/* 54 */           if (!nextRegionToReload.canRequestReload_unsynced()) {
/* 55 */             this.retryLaterDeque.add(next);
/*    */             return;
/*    */           } 
/* 58 */           if (this.resave) {
/* 59 */             nextRegionToReload.setResaving(true);
/* 60 */             nextRegionToReload.setBeingWritten(true);
/*    */           } 
/* 62 */           if (nextRegionToReload.getLoadState() == 2) {
/* 63 */             nextRegionToReload.requestRefresh(this.mapProcessor);
/*    */           } else {
/* 65 */             this.mapProcessor.getMapSaveLoad().requestLoad(nextRegionToReload, "full reload");
/* 66 */           }  this.mapProcessor.getMapSaveLoad().setNextToLoadByViewing((LeveledRegion)nextRegionToReload);
/* 67 */           this.lastRequestedRegion = nextRegionToReload;
/*    */         } 
/*    */         return;
/*    */       } 
/*    */     } 
/* 72 */     if (!this.regionDetectionIterator.hasNext() && this.retryLaterDeque.isEmpty() && (this.lastRequestedRegion == null || this.lastRequestedRegion
/* 73 */       .shouldAllowAnotherRegionToLoad())) {
/* 74 */       this.mapDimension.clearFullMapReload();
/* 75 */       if (this.resave && this.mapDimension.isUsingWorldSave()) {
/* 76 */         this.mapDimension.addMultiworldChecked("cm$converted");
/* 77 */         this.mapDimension.setMultiworldName("cm$converted", "gui.xaero_converted_world_save");
/* 78 */         this.mapDimension.saveConfigUnsynced();
/*    */       } 
/* 80 */       if ((class_310.method_1551()).field_1755 instanceof xaero.map.gui.GuiWorldMapSettings || (class_310.method_1551()).field_1755 instanceof xaero.map.gui.GuiMap)
/* 81 */         class_310.method_1551().method_1507((class_310.method_1551()).field_1755); 
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean isPartOfReload(MapRegion region) {
/* 86 */     return (region.getDim() == this.mapDimension && region.getCaveLayer() == this.caveLayer);
/*    */   }
/*    */   
/*    */   public boolean isResave() {
/* 90 */     return this.resave;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\MapFullReloader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */