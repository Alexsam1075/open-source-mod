/*    */ package xaero.map.radar.tracker;
/*    */ 
/*    */ import com.mojang.blaze3d.systems.RenderSystem;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import net.minecraft.class_2960;
/*    */ import xaero.map.icon.XaeroIcon;
/*    */ import xaero.map.icon.XaeroIconAtlasManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Builder
/*    */ {
/*    */   public TrackedPlayerIconManager build() {
/* 57 */     int maxTextureSize = RenderSystem.getDevice().getMaxTextureSize();
/* 58 */     int atlasTextureSize = Math.min(maxTextureSize, 1024) / 32 * 32;
/* 59 */     return new TrackedPlayerIconManager(new TrackedPlayerIconPrerenderer(), new XaeroIconAtlasManager(32, atlasTextureSize, new ArrayList()), new HashMap<>(), 32);
/*    */   }
/*    */   
/*    */   public static Builder begin() {
/* 63 */     return new Builder();
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\radar\tracker\TrackedPlayerIconManager$Builder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */