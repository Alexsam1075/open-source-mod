/*    */ package xaero.map.region.state;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.objects.Reference2ObjectArrayMap;
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.IOException;
/*    */ import net.minecraft.class_2246;
/*    */ import net.minecraft.class_2487;
/*    */ import net.minecraft.class_2507;
/*    */ import net.minecraft.class_2680;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UnknownBlockState
/*    */   extends class_2680
/*    */ {
/*    */   private class_2487 nbt;
/*    */   private String stringRepresentation;
/*    */   
/*    */   public UnknownBlockState(class_2487 nbt) {
/* 22 */     super(class_2246.field_10124, new Reference2ObjectArrayMap(), null);
/* 23 */     this.nbt = nbt;
/* 24 */     this.stringRepresentation = "Unknown: " + String.valueOf(nbt);
/*    */   }
/*    */   
/*    */   public void write(DataOutputStream out) throws IOException {
/* 28 */     class_2507.method_10628(this.nbt, out);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 33 */     return this.stringRepresentation;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\region\state\UnknownBlockState.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */