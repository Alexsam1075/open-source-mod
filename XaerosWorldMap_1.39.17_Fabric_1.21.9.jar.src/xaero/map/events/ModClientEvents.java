/*    */ package xaero.map.events;
/*    */ 
/*    */ import net.minecraft.class_1059;
/*    */ import xaero.map.MapProcessor;
/*    */ import xaero.map.WorldMap;
/*    */ import xaero.map.WorldMapSession;
/*    */ import xaero.map.mods.SupportMods;
/*    */ 
/*    */ public class ModClientEvents
/*    */ {
/*    */   private boolean listenToTextureStitch;
/*    */   
/*    */   public void handleTextureStitchEventPost(class_1059 atlasTexture) {
/* 14 */     if (atlasTexture.method_24106() != class_1059.field_5275)
/*    */       return; 
/* 16 */     boolean shouldListenToStitch = this.listenToTextureStitch;
/* 17 */     this.listenToTextureStitch = true;
/* 18 */     WorldMapSession worldmapSession = WorldMapSession.getCurrentSession();
/* 19 */     if (worldmapSession != null) {
/* 20 */       MapProcessor mapProcessor = worldmapSession.getMapProcessor();
/* 21 */       if (shouldListenToStitch) {
/* 22 */         mapProcessor.getMapWriter().requestCachedColoursClear();
/* 23 */         mapProcessor.getBlockStateShortShapeCache().reset();
/*    */       } 
/*    */     } 
/* 26 */     if (shouldListenToStitch) {
/* 27 */       if (SupportMods.minimap())
/* 28 */         WorldMap.waypointSymbolCreator.resetChars(); 
/* 29 */       if (WorldMap.settings != null)
/* 30 */         WorldMap.settings.updateRegionCacheHashCode(); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\events\ModClientEvents.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */