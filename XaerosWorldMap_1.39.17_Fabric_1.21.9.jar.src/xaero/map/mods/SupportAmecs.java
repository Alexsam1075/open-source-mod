/*    */ package xaero.map.mods;
/*    */ 
/*    */ import de.siphalor.amecs.api.KeyBindingUtils;
/*    */ import de.siphalor.amecs.api.KeyModifiers;
/*    */ import net.minecraft.class_304;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ 
/*    */ public class SupportAmecs
/*    */ {
/*    */   public SupportAmecs(Logger logger) {}
/*    */   
/*    */   public boolean modifiersArePressed(class_304 keyBinding) {
/* 14 */     KeyModifiers modifiers = KeyBindingUtils.getBoundModifiers(keyBinding);
/* 15 */     return KeyModifiers.getCurrentlyPressed().contains(modifiers);
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\mods\SupportAmecs.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */