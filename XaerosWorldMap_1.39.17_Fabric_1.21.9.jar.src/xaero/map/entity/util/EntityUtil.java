/*    */ package xaero.map.entity.util;
/*    */ 
/*    */ import net.minecraft.class_1297;
/*    */ import net.minecraft.class_243;
/*    */ 
/*    */ public class EntityUtil
/*    */ {
/*    */   public static double getEntityX(class_1297 e, float partial) {
/*  9 */     double xOld = (e.field_6012 > 0) ? e.field_6038 : e.method_23317();
/* 10 */     return xOld + (e.method_23317() - xOld) * partial;
/*    */   }
/*    */   
/*    */   public static double getEntityY(class_1297 e, float partial) {
/* 14 */     double yOld = (e.field_6012 > 0) ? e.field_5971 : e.method_23318();
/* 15 */     return yOld + (e.method_23318() - yOld) * partial;
/*    */   }
/*    */   
/*    */   public static double getEntityZ(class_1297 e, float partial) {
/* 19 */     double zOld = (e.field_6012 > 0) ? e.field_5989 : e.method_23321();
/* 20 */     return zOld + (e.method_23321() - zOld) * partial;
/*    */   }
/*    */   
/*    */   public static class_243 getEntityPos(class_1297 e, float partial) {
/* 24 */     return new class_243(
/* 25 */         getEntityX(e, partial), 
/* 26 */         getEntityY(e, partial), 
/* 27 */         getEntityZ(e, partial));
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\entit\\util\EntityUtil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */