/*    */ package xaero.map.element.render;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*    */ 
/*    */ public class ElementRenderLocation
/*    */ {
/*  8 */   private static final Int2ObjectMap<ElementRenderLocation> ALL = (Int2ObjectMap<ElementRenderLocation>)new Int2ObjectOpenHashMap();
/*  9 */   public static final ElementRenderLocation UNKNOWN = new ElementRenderLocation(-1);
/* 10 */   public static final ElementRenderLocation IN_MINIMAP = new ElementRenderLocation(0);
/* 11 */   public static final ElementRenderLocation OVER_MINIMAP = new ElementRenderLocation(1);
/* 12 */   public static final ElementRenderLocation IN_WORLD = new ElementRenderLocation(2);
/* 13 */   public static final ElementRenderLocation WORLD_MAP = new ElementRenderLocation(3);
/* 14 */   public static final ElementRenderLocation WORLD_MAP_MENU = new ElementRenderLocation(4);
/*    */   
/*    */   private final int index;
/*    */   
/*    */   public ElementRenderLocation(int index) {
/* 19 */     this.index = index;
/* 20 */     ALL.put(index, this);
/*    */   }
/*    */   
/*    */   public int getIndex() {
/* 24 */     return this.index;
/*    */   }
/*    */   
/*    */   public static ElementRenderLocation fromIndex(int location) {
/* 28 */     ElementRenderLocation result = (ElementRenderLocation)ALL.get(location);
/* 29 */     if (result == null)
/* 30 */       return UNKNOWN; 
/* 31 */     return result;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\element\render\ElementRenderLocation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */