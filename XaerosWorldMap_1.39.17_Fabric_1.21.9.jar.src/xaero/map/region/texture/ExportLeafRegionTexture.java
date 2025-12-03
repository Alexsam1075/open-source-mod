/*    */ package xaero.map.region.texture;
/*    */ 
/*    */ import xaero.map.highlight.DimensionHighlighterHandler;
/*    */ import xaero.map.pool.buffer.PoolTextureDirectBufferUnit;
/*    */ import xaero.map.region.MapTileChunk;
/*    */ 
/*    */ public class ExportLeafRegionTexture
/*    */   extends LeafRegionTexture {
/*    */   public ExportLeafRegionTexture(MapTileChunk tileChunk) {
/* 10 */     super(tileChunk);
/*    */   }
/*    */ 
/*    */   
/*    */   public void applyHighlights(DimensionHighlighterHandler highlighterHandler, PoolTextureDirectBufferUnit colorBuffer) {
/* 15 */     applyHighlights(highlighterHandler, colorBuffer, false);
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getMipMapLevels() {
/* 20 */     return 10;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\region\texture\ExportLeafRegionTexture.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */