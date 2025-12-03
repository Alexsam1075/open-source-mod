/*    */ package xaero.map.server.level;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.IOException;
/*    */ import java.io.PrintWriter;
/*    */ import java.util.Random;
/*    */ import net.minecraft.class_2540;
/*    */ import xaero.map.message.WorldMapMessage;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LevelMapProperties
/*    */   extends WorldMapMessage<LevelMapProperties>
/*    */ {
/* 17 */   private int id = (new Random()).nextInt();
/*    */   
/*    */   private boolean usable = true;
/*    */   
/*    */   public void write(PrintWriter writer) {
/* 22 */     writer.print("id:" + this.id);
/*    */   }
/*    */   
/*    */   public void read(BufferedReader reader) throws IOException {
/*    */     String line;
/* 27 */     while ((line = reader.readLine()) != null) {
/* 28 */       String[] args = line.split(":");
/* 29 */       if (args[0].equals("id")) {
/*    */         try {
/* 31 */           this.id = Integer.parseInt(args[1]);
/* 32 */         } catch (NumberFormatException numberFormatException) {}
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean isUsable() {
/* 38 */     return this.usable;
/*    */   }
/*    */   
/*    */   public void setUsable(boolean usable) {
/* 42 */     this.usable = usable;
/*    */   }
/*    */   
/*    */   public int getId() {
/* 46 */     return this.id;
/*    */   }
/*    */   
/*    */   public static LevelMapProperties read(class_2540 input) {
/* 50 */     LevelMapProperties result = new LevelMapProperties();
/* 51 */     result.id = input.readInt();
/* 52 */     return result;
/*    */   }
/*    */   
/*    */   public void write(class_2540 u) {
/* 56 */     u.method_53002(this.id);
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\server\level\LevelMapProperties.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */