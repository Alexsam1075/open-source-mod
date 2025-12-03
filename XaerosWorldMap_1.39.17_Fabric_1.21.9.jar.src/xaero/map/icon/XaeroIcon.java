/*    */ package xaero.map.icon;
/*    */ 
/*    */ 
/*    */ public class XaeroIcon
/*    */ {
/*    */   private final XaeroIconAtlas textureAtlas;
/*    */   private final int offsetX;
/*    */   private final int offsetY;
/*    */   
/*    */   public XaeroIcon(XaeroIconAtlas textureAtlas, int offsetX, int offsetY) {
/* 11 */     this.textureAtlas = textureAtlas;
/* 12 */     this.offsetX = offsetX;
/* 13 */     this.offsetY = offsetY;
/*    */   }
/*    */   
/*    */   public XaeroIconAtlas getTextureAtlas() {
/* 17 */     return this.textureAtlas;
/*    */   }
/*    */   
/*    */   public int getOffsetX() {
/* 21 */     return this.offsetX;
/*    */   }
/*    */   
/*    */   public int getOffsetY() {
/* 25 */     return this.offsetY;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\icon\XaeroIcon.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */