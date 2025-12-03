/*    */ package xaero.map.radar.tracker;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.VertexFormat;
/*    */ import net.minecraft.class_10799;
/*    */ import net.minecraft.class_1657;
/*    */ import net.minecraft.class_1664;
/*    */ import net.minecraft.class_2561;
/*    */ import net.minecraft.class_287;
/*    */ import net.minecraft.class_289;
/*    */ import net.minecraft.class_2960;
/*    */ import net.minecraft.class_332;
/*    */ import net.minecraft.class_4587;
/*    */ import net.minecraft.class_4588;
/*    */ import xaero.map.element.MapElementGraphics;
/*    */ import xaero.map.graphics.CustomRenderTypes;
/*    */ import xaero.map.graphics.MapRenderHelper;
/*    */ import xaero.map.graphics.TextureUtils;
/*    */ import xaero.map.render.util.ImmediateRenderUtil;
/*    */ 
/*    */ 
/*    */ public class PlayerTrackerIconRenderer
/*    */ {
/*    */   public boolean isPlayerUpsideDown(class_1657 player) {
/* 24 */     class_2561 playerCustomName = player.method_5797();
/* 25 */     return (playerCustomName != null && isUpsideDownName(playerCustomName.getString()));
/*    */   }
/*    */   
/*    */   protected static boolean isUpsideDownName(String playerName) {
/* 29 */     return ("Dinnerbone".equals(playerName) || "Grumm".equals(playerName));
/*    */   }
/*    */   
/*    */   public void renderIcon(MapElementGraphics guiGraphics, class_1657 player, class_2960 skinTextureLocation) {
/* 33 */     class_4587 matrixStack = guiGraphics.pose();
/* 34 */     boolean upsideDown = (player != null && isPlayerUpsideDown(player));
/*    */     
/* 36 */     int textureY = 8 + (upsideDown ? 8 : 0);
/* 37 */     int textureH = 8 * (upsideDown ? -1 : 1);
/*    */     
/* 39 */     TextureUtils.setTexture(0, skinTextureLocation);
/* 40 */     class_287 bufferbuilder = class_289.method_1348().method_60827(VertexFormat.class_5596.field_27382, CustomRenderTypes.POSITION_COLOR_TEX);
/* 41 */     MapRenderHelper.blitIntoExistingBuffer(matrixStack.method_23760().method_23761(), (class_4588)bufferbuilder, -4.0F, -4.0F, 8, textureY, 8, 8, 8, textureH, 1.0F, 1.0F, 1.0F, 1.0F, 64, 64);
/* 42 */     if (player != null && player.method_74091(class_1664.field_7563)) {
/* 43 */       textureY = 8 + (upsideDown ? 8 : 0);
/* 44 */       textureH = 8 * (upsideDown ? -1 : 1);
/* 45 */       MapRenderHelper.blitIntoExistingBuffer(matrixStack.method_23760().method_23761(), (class_4588)bufferbuilder, -4.0F, -4.0F, 40, textureY, 8, 8, 8, textureH, 1.0F, 1.0F, 1.0F, 1.0F, 64, 64);
/*    */     } 
/* 47 */     ImmediateRenderUtil.drawImmediateMeshData(bufferbuilder.method_60794(), CustomRenderTypes.RP_POSITION_COLOR_TEX);
/*    */   }
/*    */   
/*    */   public void renderIconGUI(class_332 guiGraphics, class_1657 player, class_2960 skinTextureLocation) {
/* 51 */     boolean upsideDown = (player != null && isPlayerUpsideDown(player));
/*    */     
/* 53 */     int textureY = 8 + (upsideDown ? 8 : 0);
/* 54 */     int textureH = 8 * (upsideDown ? -1 : 1);
/*    */     
/* 56 */     guiGraphics.method_25293(class_10799.field_56883, skinTextureLocation, -4, -4, 8.0F, textureY, 8, 8, 8, textureH, 64, 64, -1);
/* 57 */     if (player != null && player.method_74091(class_1664.field_7563)) {
/* 58 */       textureY = 8 + (upsideDown ? 8 : 0);
/* 59 */       textureH = 8 * (upsideDown ? -1 : 1);
/* 60 */       guiGraphics.method_25293(class_10799.field_56883, skinTextureLocation, -4, -4, 40.0F, textureY, 8, 8, 8, textureH, 64, 64, -1);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\radar\tracker\PlayerTrackerIconRenderer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */