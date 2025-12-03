/*    */ package xaero.map.element;
/*    */ 
/*    */ public class MapElementRenderLocation
/*    */ {
/*    */   public static final int UNKNOWN = -1;
/*    */   public static final int IN_MINIMAP = 0;
/*    */   public static final int OVER_MINIMAP = 1;
/*    */   public static final int IN_GAME = 2;
/*    */   public static final int WORLD_MAP = 3;
/*    */   public static final int WORLD_MAP_MENU = 4;
/*    */   
/*    */   public static int fromMinimap(int location) {
/* 13 */     if (location > 4 || location < -1)
/* 14 */       return -1; 
/* 15 */     return location;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\element\MapElementRenderLocation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */