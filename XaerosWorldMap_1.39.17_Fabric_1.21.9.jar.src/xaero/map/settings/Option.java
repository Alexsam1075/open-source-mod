/*    */ package xaero.map.settings;
/*    */ 
/*    */ import net.minecraft.class_2561;
/*    */ import net.minecraft.class_339;
/*    */ 
/*    */ 
/*    */ public abstract class Option
/*    */ {
/*    */   protected final ModOptions option;
/*    */   private final class_2561 caption;
/*    */   
/*    */   public Option(ModOptions option) {
/* 13 */     this.option = option;
/* 14 */     this.caption = (class_2561)class_2561.method_43471(option.getEnumStringRaw());
/*    */   }
/*    */   
/*    */   public class_2561 getCaption() {
/* 18 */     return this.caption;
/*    */   }
/*    */   
/*    */   public abstract class_339 createButton(int paramInt1, int paramInt2, int paramInt3);
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\settings\Option.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */