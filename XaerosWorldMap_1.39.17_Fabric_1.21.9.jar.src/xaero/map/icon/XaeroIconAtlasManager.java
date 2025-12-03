/*    */ package xaero.map.icon;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ public class XaeroIconAtlasManager
/*    */ {
/*    */   private final int iconWidth;
/*    */   private final int atlasTextureSize;
/*    */   private final List<XaeroIconAtlas> atlases;
/*    */   private int currentAtlasIndex;
/*    */   
/*    */   public XaeroIconAtlasManager(int iconWidth, int atlasTextureSize, List<XaeroIconAtlas> atlases) {
/* 14 */     this.iconWidth = iconWidth;
/* 15 */     this.atlasTextureSize = atlasTextureSize;
/* 16 */     this.atlases = atlases;
/* 17 */     this.currentAtlasIndex = -1;
/*    */   }
/*    */   
/*    */   public void clearAtlases() {
/* 21 */     for (XaeroIconAtlas entityIconAtlas : this.atlases) {
/* 22 */       entityIconAtlas.close();
/*    */     }
/* 24 */     this.currentAtlasIndex = -1;
/* 25 */     this.atlases.clear();
/*    */   }
/*    */   
/*    */   public XaeroIconAtlas getCurrentAtlas() {
/* 29 */     if (this.currentAtlasIndex < 0 || ((XaeroIconAtlas)this.atlases.get(this.currentAtlasIndex)).isFull()) {
/* 30 */       this.atlases.add(XaeroIconAtlas.Builder.begin().setWidth(this.atlasTextureSize).setIconWidth(this.iconWidth).build());
/* 31 */       this.currentAtlasIndex = this.atlases.size() - 1;
/*    */     } 
/* 33 */     return this.atlases.get(this.currentAtlasIndex);
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\icon\XaeroIconAtlasManager.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */