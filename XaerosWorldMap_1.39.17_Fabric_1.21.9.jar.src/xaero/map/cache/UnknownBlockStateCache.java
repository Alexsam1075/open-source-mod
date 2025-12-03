/*    */ package xaero.map.cache;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import net.minecraft.class_2246;
/*    */ import net.minecraft.class_2248;
/*    */ import net.minecraft.class_2487;
/*    */ import net.minecraft.class_2512;
/*    */ import net.minecraft.class_2680;
/*    */ import net.minecraft.class_7225;
/*    */ import net.minecraft.class_7871;
/*    */ import xaero.map.region.state.UnknownBlockState;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UnknownBlockStateCache
/*    */ {
/* 18 */   private HashMap<String, class_2680> unknownBlockStates = new HashMap<>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public class_2680 getBlockStateFromNBT(class_7225<class_2248> blockLookup, class_2487 nbt) {
/*    */     class_2680 blockState;
/*    */     UnknownBlockState unknownBlockState;
/*    */     try {
/* 30 */       blockState = class_2512.method_10681((class_7871)blockLookup, nbt);
/* 31 */     } catch (IllegalArgumentException iae) {
/* 32 */       blockState = class_2246.field_10124.method_9564();
/*    */     } 
/*    */ 
/*    */     
/* 36 */     if (!nbt.method_10558("Name").equals("minecraft:air") && blockState.method_26204() == class_2246.field_10124) {
/* 37 */       String nbtString = nbt.toString();
/* 38 */       blockState = this.unknownBlockStates.get(nbtString);
/* 39 */       if (blockState == null) {
/* 40 */         unknownBlockState = new UnknownBlockState(nbt);
/* 41 */         this.unknownBlockStates.put(nbtString, unknownBlockState);
/*    */       } 
/*    */     } 
/*    */     
/* 45 */     return (class_2680)unknownBlockState;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\cache\UnknownBlockStateCache.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */