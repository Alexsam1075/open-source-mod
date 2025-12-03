/*    */ package xaero.map.common.config;
/*    */ import java.io.BufferedInputStream;
/*    */ import java.io.BufferedOutputStream;
/*    */ import java.io.BufferedReader;
/*    */ import java.io.FileOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.PrintWriter;
/*    */ import java.nio.file.Path;
/*    */ import xaero.map.WorldMap;
/*    */ 
/*    */ public class CommonConfigIO {
/*    */   private final Path configFilePath;
/*    */   
/*    */   public CommonConfigIO(Path configFilePath) {
/* 15 */     this.configFilePath = configFilePath;
/*    */   }
/*    */   
/*    */   public void save(CommonConfig config) {
/*    */     try {
/* 20 */       Path parentFolder = this.configFilePath.getParent();
/* 21 */       if (parentFolder != null)
/* 22 */         Files.createDirectories(parentFolder, (FileAttribute<?>[])new FileAttribute[0]); 
/* 23 */     } catch (IOException e) {
/* 24 */       WorldMap.LOGGER.error("suppressed exception", e);
/*    */       return;
/*    */     } 
/*    */     
/* 28 */     try { BufferedOutputStream bufferedOutput = new BufferedOutputStream(new FileOutputStream(this.configFilePath.toFile())); 
/* 29 */       try { PrintWriter writer = new PrintWriter(bufferedOutput);
/*    */         
/* 31 */         try { write(config, writer);
/* 32 */           writer.close(); } catch (Throwable throwable) { try { writer.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  bufferedOutput.close(); } catch (Throwable throwable) { try { bufferedOutput.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (IOException e)
/* 33 */     { WorldMap.LOGGER.error("suppressed exception", e); }
/*    */   
/*    */   }
/*    */   
/*    */   public CommonConfig load() {
/*    */     
/* 39 */     try { BufferedInputStream bufferedOutput = new BufferedInputStream(new FileInputStream(this.configFilePath.toFile())); 
/* 40 */       try { BufferedReader reader = new BufferedReader(new InputStreamReader(bufferedOutput));
/*    */         
/* 42 */         try { CommonConfig.Builder builder = CommonConfig.Builder.begin();
/*    */           String line;
/* 44 */           while ((line = reader.readLine()) != null)
/* 45 */             readLine(builder, line.split(":")); 
/* 46 */           CommonConfig commonConfig = builder.build();
/* 47 */           reader.close(); bufferedOutput.close(); return commonConfig; } catch (Throwable throwable) { try { reader.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (Throwable throwable) { try { bufferedOutput.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (IOException e)
/* 48 */     { throw new RuntimeException(e); }
/*    */   
/*    */   }
/*    */   
/*    */   private void write(CommonConfig config, PrintWriter writer) {
/* 53 */     writer.println("allowCaveModeOnServer:" + config.allowCaveModeOnServer);
/* 54 */     writer.println("allowNetherCaveModeOnServer:" + config.allowNetherCaveModeOnServer);
/* 55 */     writer.println("registerStatusEffects:" + config.registerStatusEffects);
/* 56 */     writer.println("everyoneTracksEveryone:" + config.everyoneTracksEveryone);
/*    */   }
/*    */   
/*    */   private boolean readLine(CommonConfig.Builder configBuilder, String[] args) {
/* 60 */     if (args[0].equals("allowCaveModeOnServer")) {
/* 61 */       configBuilder.setAllowCaveModeOnServer(args[1].equals("true"));
/* 62 */       return true;
/*    */     } 
/* 64 */     if (args[0].equals("allowNetherCaveModeOnServer")) {
/* 65 */       configBuilder.setAllowNetherCaveModeOnServer(args[1].equals("true"));
/* 66 */       return true;
/*    */     } 
/* 68 */     if (args[0].equals("registerStatusEffects")) {
/* 69 */       configBuilder.setRegisterStatusEffects(args[1].equals("true"));
/* 70 */       return true;
/*    */     } 
/* 72 */     if (args[0].equals("everyoneTracksEveryone")) {
/* 73 */       configBuilder.setEveryoneTracksEveryone(args[1].equals("true"));
/* 74 */       return true;
/*    */     } 
/* 76 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\common\config\CommonConfigIO.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */