/*    */ package xaero.map.nbt.util;
/*    */ 
/*    */ import java.util.Optional;
/*    */ import java.util.UUID;
/*    */ import net.minecraft.class_2487;
/*    */ import net.minecraft.class_2495;
/*    */ import net.minecraft.class_4844;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class XaeroNbtUtils
/*    */ {
/*    */   public static Optional<UUID> getUUID(class_2487 tag, String id) {
/* 14 */     int[] intArray = tag.method_10561(id).orElse(null);
/* 15 */     if (intArray == null) {
/* 16 */       long[] longArray = tag.method_10565(id).orElse(null);
/* 17 */       if (longArray == null || longArray.length != 2)
/* 18 */         return Optional.empty(); 
/* 19 */       return Optional.of(new UUID(longArray[0], longArray[1]));
/*    */     } 
/* 21 */     if (intArray.length != 4)
/* 22 */       return Optional.empty(); 
/* 23 */     return Optional.of(class_4844.method_26276(intArray));
/*    */   }
/*    */   
/*    */   public static void putUUID(class_2487 tag, String id, UUID uuid) {
/* 27 */     tag.method_10539(id, class_4844.method_26275(uuid));
/*    */   }
/*    */   
/*    */   public static void putUUIDAsLongArray(class_2487 tag, String id, UUID uuid) {
/* 31 */     tag.method_10564(id, new long[] { uuid.getMostSignificantBits(), uuid.getLeastSignificantBits() });
/*    */   }
/*    */   
/*    */   public static class_2495 createUUIDTag(UUID partyId) {
/* 35 */     return new class_2495(class_4844.method_26275(partyId));
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\nb\\util\XaeroNbtUtils.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */