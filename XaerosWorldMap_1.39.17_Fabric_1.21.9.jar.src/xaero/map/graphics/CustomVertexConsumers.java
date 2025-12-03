/*    */ package xaero.map.graphics;
/*    */ import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
/*    */ import java.util.SortedMap;
/*    */ import net.minecraft.class_156;
/*    */ import net.minecraft.class_1921;
/*    */ import net.minecraft.class_4597;
/*    */ import net.minecraft.class_9799;
/*    */ 
/*    */ public class CustomVertexConsumers {
/*    */   private final SortedMap<class_1921, class_9799> builders;
/*    */   
/*    */   public CustomVertexConsumers() {
/* 13 */     this.builders = (SortedMap<class_1921, class_9799>)class_156.method_654(new Object2ObjectLinkedOpenHashMap(), map -> {
/*    */           checkedAddToMap(map, CustomRenderTypes.MAP_COLOR_FILLER, new class_9799(256));
/*    */           
/*    */           checkedAddToMap(map, CustomRenderTypes.MAP_FRAME, new class_9799(256));
/*    */           
/*    */           checkedAddToMap(map, CustomRenderTypes.GUI, new class_9799(256));
/*    */           
/*    */           checkedAddToMap(map, CustomRenderTypes.GUI_PREMULTIPLIED, new class_9799(256));
/*    */           
/*    */           checkedAddToMap(map, CustomRenderTypes.MAP_COLOR_OVERLAY, new class_9799(256));
/*    */           checkedAddToMap(map, CustomRenderTypes.MAP, new class_9799(256));
/*    */           checkedAddToMap(map, CustomRenderTypes.MAP_ELEMENT_TEXT_BG, new class_9799(42));
/*    */           checkedAddToMap(map, CustomRenderTypes.MAP_BRANCH, new class_9799(42));
/*    */         });
/* 27 */     this.renderTypeBuffers = class_4597.method_22992(this.builders, new class_9799(256));
/*    */   }
/*    */   private class_4597.class_4598 renderTypeBuffers;
/*    */   public class_4597.class_4598 getRenderTypeBuffers() {
/* 31 */     return this.renderTypeBuffers;
/*    */   }
/*    */   
/*    */   private static void checkedAddToMap(Object2ObjectLinkedOpenHashMap<class_1921, class_9799> map, class_1921 layer, class_9799 bb) {
/* 35 */     if (map.containsKey(layer))
/* 36 */       throw new RuntimeException("Duplicate render layers!"); 
/* 37 */     map.put(layer, bb);
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\graphics\CustomVertexConsumers.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */