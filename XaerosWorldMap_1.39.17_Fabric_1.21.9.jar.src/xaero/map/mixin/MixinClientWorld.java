/*    */ package xaero.map.mixin;
/*    */ 
/*    */ import net.minecraft.class_638;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import xaero.map.mcworld.IWorldMapClientWorld;
/*    */ import xaero.map.mcworld.WorldMapClientWorldData;
/*    */ 
/*    */ @Mixin({class_638.class})
/*    */ public class MixinClientWorld
/*    */   implements IWorldMapClientWorld
/*    */ {
/*    */   private WorldMapClientWorldData xaero_worldmapData;
/*    */   
/*    */   public WorldMapClientWorldData getXaero_worldmapData() {
/* 15 */     return this.xaero_worldmapData;
/*    */   }
/*    */   
/*    */   public void setXaero_worldmapData(WorldMapClientWorldData xaero_worldmapData) {
/* 19 */     this.xaero_worldmapData = xaero_worldmapData;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\mixin\MixinClientWorld.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */