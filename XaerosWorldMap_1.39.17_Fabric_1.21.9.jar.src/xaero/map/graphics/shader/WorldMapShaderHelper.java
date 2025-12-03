/*    */ package xaero.map.graphics.shader;
/*    */ 
/*    */ public class WorldMapShaderHelper
/*    */ {
/*  5 */   public static Float cachedBrightness = null;
/*  6 */   public static Integer cachedWithLight = null;
/*    */   
/*    */   public static void setBrightness(float brightness) {
/*  9 */     if (cachedBrightness != null && cachedBrightness.floatValue() == brightness)
/*    */       return; 
/* 11 */     cachedBrightness = Float.valueOf(brightness);
/* 12 */     BuiltInCustomUniforms.BRIGHTNESS.setValue(cachedBrightness);
/*    */   }
/*    */   
/*    */   public static void setWithLight(boolean withLight) {
/* 16 */     int withLightInt = withLight ? 1 : 0;
/* 17 */     if (cachedWithLight != null && cachedWithLight.intValue() == withLightInt)
/*    */       return; 
/* 19 */     cachedWithLight = Integer.valueOf(withLightInt);
/* 20 */     BuiltInCustomUniforms.WITH_LIGHT.setValue(cachedWithLight);
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\graphics\shader\WorldMapShaderHelper.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */