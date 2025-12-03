/*    */ package xaero.map.mods.minimap.element;
/*    */ 
/*    */ import xaero.common.IXaeroMinimap;
/*    */ import xaero.hud.minimap.element.render.MinimapElementRenderer;
/*    */ import xaero.hud.minimap.radar.render.element.RadarRenderer;
/*    */ import xaero.map.WorldMap;
/*    */ 
/*    */ 
/*    */ public class RadarRendererWrapperHelper
/*    */ {
/*    */   public void createWrapper(IXaeroMinimap modMain, RadarRenderer radarRenderer) {
/* 12 */     WorldMap.mapElementRenderHandler.add(MinimapElementRendererWrapper.Builder.begin((MinimapElementRenderer<?, ?>)radarRenderer).setModMain(modMain).setShouldRenderSupplier(() -> Boolean.valueOf(WorldMap.settings.minimapRadar)).setOrder(100).build());
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\mods\minimap\element\RadarRendererWrapperHelper.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */