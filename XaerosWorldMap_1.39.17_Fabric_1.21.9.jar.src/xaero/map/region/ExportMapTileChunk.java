/*    */ package xaero.map.region;
/*    */ 
/*    */ import xaero.map.region.texture.ExportLeafRegionTexture;
/*    */ import xaero.map.region.texture.LeafRegionTexture;
/*    */ 
/*    */ public class ExportMapTileChunk
/*    */   extends MapTileChunk {
/*    */   public ExportMapTileChunk(MapRegion r, int x, int z) {
/*  9 */     super(r, x, z);
/*    */   }
/*    */ 
/*    */   
/*    */   protected LeafRegionTexture createLeafTexture() {
/* 14 */     return (LeafRegionTexture)new ExportLeafRegionTexture(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public ExportLeafRegionTexture getLeafTexture() {
/* 19 */     return (ExportLeafRegionTexture)super.getLeafTexture();
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\region\ExportMapTileChunk.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */