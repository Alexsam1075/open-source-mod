/*    */ package xaero.map.misc;
/*    */ 
/*    */ import net.minecraft.class_4587;
/*    */ import org.joml.Vector3f;
/*    */ import org.joml.Vector3fc;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OptimizedMath
/*    */ {
/* 11 */   public static final Vector3f XP = new Vector3f(1.0F, 0.0F, 0.0F);
/* 12 */   public static final Vector3f YP = new Vector3f(0.0F, 1.0F, 0.0F);
/* 13 */   public static final Vector3f ZP = new Vector3f(0.0F, 0.0F, 1.0F);
/*    */   
/*    */   public static int myFloor(double d) {
/* 16 */     int asInt = (int)d;
/* 17 */     if (asInt != d && d < 0.0D)
/* 18 */       asInt--; 
/* 19 */     return asInt;
/*    */   }
/*    */   
/*    */   public static void rotatePose(class_4587 poseStack, float degrees, Vector3fc vector) {
/* 23 */     class_4587.class_4665 pose = poseStack.method_23760();
/* 24 */     pose.method_23761().rotate(degrees * 0.017453292F, vector);
/* 25 */     pose.method_23762().rotate(degrees * 0.017453292F, vector);
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\misc\OptimizedMath.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */