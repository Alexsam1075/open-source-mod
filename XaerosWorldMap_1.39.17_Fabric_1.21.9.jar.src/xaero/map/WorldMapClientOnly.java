/*    */ package xaero.map;
/*    */ 
/*    */ import net.minecraft.class_4587;
/*    */ import xaero.map.graphics.CustomVertexConsumers;
/*    */ import xaero.map.region.texture.BranchTextureRenderer;
/*    */ 
/*    */ 
/*    */ public class WorldMapClientOnly
/*    */ {
/*    */   public BranchTextureRenderer branchTextureRenderer;
/*    */   public CustomVertexConsumers customVertexConsumers;
/*    */   private class_4587 mapScreenPoseStack;
/*    */   
/*    */   public void preInit(String modId) {}
/*    */   
/*    */   public void postInit() {
/* 17 */     this.branchTextureRenderer = new BranchTextureRenderer();
/* 18 */     this.customVertexConsumers = new CustomVertexConsumers();
/* 19 */     this.mapScreenPoseStack = new class_4587();
/*    */   }
/*    */   
/*    */   public class_4587 getMapScreenPoseStack() {
/* 23 */     return this.mapScreenPoseStack;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\WorldMapClientOnly.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */