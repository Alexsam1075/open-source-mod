/*    */ package xaero.map.radar.tracker;
/*    */ 
/*    */ import com.mojang.blaze3d.systems.RenderSystem;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import net.minecraft.class_1068;
/*    */ import net.minecraft.class_1657;
/*    */ import net.minecraft.class_2960;
/*    */ import net.minecraft.class_640;
/*    */ import net.minecraft.class_742;
/*    */ import xaero.map.element.MapElementGraphics;
/*    */ import xaero.map.icon.XaeroIcon;
/*    */ import xaero.map.icon.XaeroIconAtlasManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class TrackedPlayerIconManager
/*    */ {
/*    */   private static final int ICON_WIDTH = 32;
/*    */   private static final int PREFERRED_ATLAS_WIDTH = 1024;
/*    */   private final TrackedPlayerIconPrerenderer prerenderer;
/*    */   private final XaeroIconAtlasManager iconAtlasManager;
/*    */   private final Map<class_2960, XaeroIcon> icons;
/*    */   private final int iconWidth;
/*    */   
/*    */   private TrackedPlayerIconManager(TrackedPlayerIconPrerenderer prerenderer, XaeroIconAtlasManager iconAtlasManager, Map<class_2960, XaeroIcon> icons, int iconWidth) {
/* 30 */     this.prerenderer = prerenderer;
/* 31 */     this.iconAtlasManager = iconAtlasManager;
/* 32 */     this.icons = icons;
/* 33 */     this.iconWidth = iconWidth;
/*    */   }
/*    */ 
/*    */   
/*    */   public class_2960 getPlayerSkin(class_1657 player, class_640 info) {
/* 38 */     class_2960 skinTextureLocation = (player instanceof class_742) ? ((class_742)player).method_52814().comp_1626().comp_3627() : info.method_52810().comp_1626().comp_3627();
/* 39 */     if (skinTextureLocation == null)
/* 40 */       skinTextureLocation = class_1068.method_4648(player.method_5667()).comp_1626().comp_3627(); 
/* 41 */     return skinTextureLocation;
/*    */   }
/*    */   
/*    */   public XaeroIcon getIcon(MapElementGraphics guiGraphics, class_1657 player, class_640 info, PlayerTrackerMapElement<?> element) {
/* 45 */     class_2960 skinTextureLocation = getPlayerSkin(player, info);
/* 46 */     XaeroIcon result = this.icons.get(skinTextureLocation);
/* 47 */     if (result == null) {
/* 48 */       this.icons.put(skinTextureLocation, result = this.iconAtlasManager.getCurrentAtlas().createIcon());
/* 49 */       this.prerenderer.prerender(guiGraphics, result, player, this.iconWidth, skinTextureLocation, element);
/*    */     } 
/* 51 */     return result;
/*    */   }
/*    */   
/*    */   public static final class Builder
/*    */   {
/*    */     public TrackedPlayerIconManager build() {
/* 57 */       int maxTextureSize = RenderSystem.getDevice().getMaxTextureSize();
/* 58 */       int atlasTextureSize = Math.min(maxTextureSize, 1024) / 32 * 32;
/* 59 */       return new TrackedPlayerIconManager(new TrackedPlayerIconPrerenderer(), new XaeroIconAtlasManager(32, atlasTextureSize, new ArrayList()), new HashMap<>(), 32);
/*    */     }
/*    */     
/*    */     public static Builder begin() {
/* 63 */       return new Builder();
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\radar\tracker\TrackedPlayerIconManager.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */