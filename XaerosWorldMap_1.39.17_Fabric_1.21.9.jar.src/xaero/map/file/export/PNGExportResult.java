/*    */ package xaero.map.file.export;
/*    */ 
/*    */ import java.nio.file.Path;
/*    */ import net.minecraft.class_2561;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PNGExportResult
/*    */ {
/*    */   private final PNGExportResultType type;
/*    */   private final Path folderToOpen;
/*    */   
/*    */   public PNGExportResult(PNGExportResultType type, Path folderToOpen) {
/* 14 */     this.type = type;
/* 15 */     this.folderToOpen = folderToOpen;
/*    */   }
/*    */   
/*    */   public PNGExportResultType getType() {
/* 19 */     return this.type;
/*    */   }
/*    */   
/*    */   public Path getFolderToOpen() {
/* 23 */     return this.folderToOpen;
/*    */   }
/*    */   
/*    */   public class_2561 getMessage() {
/* 27 */     return this.type.getMessage();
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\file\export\PNGExportResult.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */