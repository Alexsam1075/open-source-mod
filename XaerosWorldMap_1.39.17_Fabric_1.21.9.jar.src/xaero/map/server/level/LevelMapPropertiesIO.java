/*    */ package xaero.map.server.level;
/*    */ import java.io.BufferedOutputStream;
/*    */ import java.io.BufferedReader;
/*    */ import java.io.IOException;
/*    */ import java.io.PrintWriter;
/*    */ import java.nio.file.Path;
/*    */ 
/*    */ public class LevelMapPropertiesIO {
/*    */   public static final String FILE_NAME = "xaeromap.txt";
/*    */   
/*    */   public void load(Path file, LevelMapProperties dest) throws IOException {
/* 12 */     BufferedReader reader = null;
/*    */     try {
/* 14 */       reader = new BufferedReader(new InputStreamReader(new FileInputStream(file.toFile()), "UTF8"));
/* 15 */       dest.read(reader);
/* 16 */     } catch (UnsupportedEncodingException e) {
/* 17 */       throw new RuntimeException(e);
/*    */     } finally {
/* 19 */       if (reader != null)
/* 20 */         reader.close(); 
/*    */     } 
/*    */   }
/*    */   
/*    */   public void save(Path file, LevelMapProperties dest) throws IOException {
/* 25 */     BufferedOutputStream bufferedOutput = new BufferedOutputStream(new FileOutputStream(file.toFile())); try { PrintWriter writer = new PrintWriter(new OutputStreamWriter(bufferedOutput, StandardCharsets.UTF_8)); 
/* 26 */       try { dest.write(writer);
/* 27 */         writer.close(); } catch (Throwable throwable) { try { writer.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  bufferedOutput.close(); }
/*    */     catch (Throwable throwable)
/*    */     { try {
/*    */         bufferedOutput.close();
/*    */       } catch (Throwable throwable1) {
/*    */         throwable.addSuppressed(throwable1);
/*    */       } 
/*    */       throw throwable; }
/*    */   
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\server\level\LevelMapPropertiesIO.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */