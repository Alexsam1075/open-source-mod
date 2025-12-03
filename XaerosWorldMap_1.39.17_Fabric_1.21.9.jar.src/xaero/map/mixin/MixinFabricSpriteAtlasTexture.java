/*    */ package xaero.map.mixin;
/*    */ 
/*    */ import net.minecraft.class_1059;
/*    */ import net.minecraft.class_7766;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ import xaero.map.WorldMap;
/*    */ 
/*    */ @Mixin({class_1059.class})
/*    */ public class MixinFabricSpriteAtlasTexture
/*    */ {
/*    */   @Inject(at = {@At("RETURN")}, method = {"upload"})
/*    */   public void onUpload(class_7766.class_7767 spriteAtlasTexture$Data_1, CallbackInfo info) {
/* 16 */     if (!WorldMap.loaded)
/*    */       return; 
/* 18 */     WorldMap.modEvents.handleTextureStitchEventPost((class_1059)this);
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\mixin\MixinFabricSpriteAtlasTexture.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */