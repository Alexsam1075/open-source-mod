/*    */ package xaero.map.controls;
/*    */ 
/*    */ import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
/*    */ import net.minecraft.class_304;
/*    */ import net.minecraft.class_3675;
/*    */ import xaero.map.mods.SupportModsFabric;
/*    */ 
/*    */ 
/*    */ public class KeyBindingHelperFabric
/*    */   implements IKeyBindingHelper
/*    */ {
/*    */   public class_3675.class_306 getBoundKeyOf(class_304 kb) {
/* 13 */     return KeyBindingHelper.getBoundKeyOf(kb);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean modifiersAreActive(class_304 kb, int keyConflictContext) {
/* 18 */     return (!SupportModsFabric.amecs() || SupportModsFabric.amecs.modifiersArePressed(kb));
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\controls\KeyBindingHelperFabric.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */