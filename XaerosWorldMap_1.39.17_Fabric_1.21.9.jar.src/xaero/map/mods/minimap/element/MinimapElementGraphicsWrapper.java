/*    */ package xaero.map.mods.minimap.element;
/*    */ 
/*    */ import net.minecraft.class_4587;
/*    */ import net.minecraft.class_4597;
/*    */ import xaero.hud.minimap.element.render.MinimapElementGraphics;
/*    */ import xaero.map.element.MapElementGraphics;
/*    */ 
/*    */ public class MinimapElementGraphicsWrapper
/*    */   extends MinimapElementGraphics {
/*    */   private MapElementGraphics graphics;
/*    */   
/*    */   public MinimapElementGraphicsWrapper() {
/* 13 */     super(null, null);
/*    */   }
/*    */   
/*    */   public MinimapElementGraphicsWrapper setGraphics(MapElementGraphics graphics) {
/* 17 */     this.graphics = graphics;
/* 18 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public class_4587 pose() {
/* 23 */     return this.graphics.pose();
/*    */   }
/*    */ 
/*    */   
/*    */   public class_4597.class_4598 getBufferSource() {
/* 28 */     return this.graphics.getBufferSource();
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\mods\minimap\element\MinimapElementGraphicsWrapper.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */