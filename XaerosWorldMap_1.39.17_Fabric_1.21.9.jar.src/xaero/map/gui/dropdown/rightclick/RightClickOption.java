/*    */ package xaero.map.gui.dropdown.rightclick;
/*    */ 
/*    */ import net.minecraft.class_437;
/*    */ import xaero.map.gui.IRightClickableElement;
/*    */ 
/*    */ 
/*    */ public abstract class RightClickOption
/*    */ {
/*    */   protected String name;
/*    */   protected int index;
/*    */   protected boolean active;
/*    */   protected IRightClickableElement target;
/*    */   protected Object[] nameFormatArgs;
/*    */   
/*    */   public RightClickOption(String name, int index, IRightClickableElement target) {
/* 16 */     this.name = name;
/* 17 */     this.index = index;
/* 18 */     this.active = true;
/* 19 */     this.target = target;
/* 20 */     this.nameFormatArgs = new Object[0];
/*    */   }
/*    */   
/*    */   public abstract void onAction(class_437 paramclass_437);
/*    */   
/*    */   public boolean onSelected(class_437 screen) {
/* 26 */     boolean active = isActive();
/* 27 */     if (active && this.target.isRightClickValid())
/* 28 */       onAction(screen); 
/* 29 */     return active;
/*    */   }
/*    */   
/*    */   protected String getName() {
/* 33 */     return this.name;
/*    */   }
/*    */   
/*    */   public String getDisplayName() {
/* 37 */     return (isActive() ? "" : "§8") + (isActive() ? "" : "§8");
/*    */   }
/*    */   
/*    */   public boolean isActive() {
/* 41 */     return this.active;
/*    */   }
/*    */   
/*    */   public RightClickOption setActive(boolean isActive) {
/* 45 */     this.active = isActive;
/* 46 */     return this;
/*    */   }
/*    */   
/*    */   public RightClickOption setNameFormatArgs(Object... nameFormatArgs) {
/* 50 */     this.nameFormatArgs = nameFormatArgs;
/* 51 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\gui\dropdown\rightclick\RightClickOption.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */