/*    */ package xaero.map.region;
/*    */ 
/*    */ import net.minecraft.class_1937;
/*    */ import net.minecraft.class_1959;
/*    */ import net.minecraft.class_2246;
/*    */ import net.minecraft.class_2248;
/*    */ import net.minecraft.class_2338;
/*    */ import net.minecraft.class_2378;
/*    */ import net.minecraft.class_2680;
/*    */ import net.minecraft.class_2874;
/*    */ import xaero.map.MapProcessor;
/*    */ import xaero.map.MapWriter;
/*    */ import xaero.map.biome.BlockTintProvider;
/*    */ import xaero.map.world.MapDimension;
/*    */ 
/*    */ public class Overlay
/*    */   extends MapPixel {
/*    */   private byte opacity;
/*    */   
/*    */   public Overlay(class_2680 state, byte light, boolean glowing) {
/* 21 */     write(state, light, glowing);
/*    */   }
/*    */   
/*    */   public void write(class_2680 state, byte light, boolean glowing) {
/* 25 */     this.opacity = 0;
/* 26 */     this.state = state;
/* 27 */     this.light = light;
/* 28 */     this.glowing = glowing;
/*    */   }
/*    */   
/*    */   public boolean isWater() {
/* 32 */     return (this.state.method_26204() == class_2246.field_10382);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getParametres() {
/* 37 */     int parametres = 0;
/* 38 */     parametres |= !isWater() ? 1 : 0;
/*    */ 
/*    */ 
/*    */     
/* 42 */     parametres |= this.light << 4;
/*    */ 
/*    */     
/* 45 */     parametres |= (this.opacity & 0xF) << 11;
/* 46 */     return parametres;
/*    */   }
/*    */   
/*    */   public void getPixelColour(MapBlock block, int[] result_dest, MapWriter mapWriter, class_1937 world, MapDimension dim, class_2378<class_2248> blockRegistry, MapTileChunk tileChunk, MapTileChunk prevChunk, MapTileChunk prevChunkDiagonal, MapTileChunk prevChunkHorisontal, MapTile mapTile, int x, int z, int caveStart, int caveDepth, class_2338.class_2339 mutableGlobalPos, class_2378<class_1959> biomeRegistry, class_2378<class_2874> dimensionTypes, float shadowR, float shadowG, float shadowB, BlockTintProvider blockTintProvider, MapProcessor mapProcessor, OverlayManager overlayManager) {
/* 50 */     getPixelColours(result_dest, mapWriter, world, dim, blockRegistry, tileChunk, prevChunk, prevChunkDiagonal, prevChunkHorisontal, mapTile, x, z, block, -1, -1, caveStart, caveDepth, null, mutableGlobalPos, biomeRegistry, dimensionTypes, shadowR, shadowG, shadowB, blockTintProvider, mapProcessor, overlayManager, null);
/*    */   }
/*    */   
/*    */   public String toRenderString() {
/* 54 */     return "(S: " + String.valueOf(getState()) + ", O: " + this.opacity + ", L: " + this.light + ")";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Overlay p) {
/* 59 */     return (p != null && this.opacity == p.opacity && this.light == p.light && getState() == p.getState());
/*    */   }
/*    */   
/*    */   void fillManagerKeyHolder(Object[] keyHolder) {
/* 63 */     keyHolder[0] = this.state;
/* 64 */     keyHolder[1] = Byte.valueOf(this.light);
/* 65 */     keyHolder[2] = Byte.valueOf(this.opacity);
/*    */   }
/*    */   
/*    */   public void increaseOpacity(int toAdd) {
/* 69 */     if (toAdd > 15)
/* 70 */       toAdd = 15; 
/* 71 */     this.opacity = (byte)(this.opacity + toAdd);
/* 72 */     if (this.opacity > 15)
/* 73 */       this.opacity = 15; 
/*    */   }
/*    */   
/*    */   public int getOpacity() {
/* 77 */     return this.opacity;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\region\Overlay.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */