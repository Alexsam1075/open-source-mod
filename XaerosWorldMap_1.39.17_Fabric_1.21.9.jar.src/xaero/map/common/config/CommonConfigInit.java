/*    */ package xaero.map.common.config;
/*    */ 
/*    */ import java.nio.file.Files;
/*    */ import java.nio.file.Path;
/*    */ import xaero.map.WorldMap;
/*    */ import xaero.map.platform.Services;
/*    */ 
/*    */ 
/*    */ public class CommonConfigInit
/*    */ {
/*    */   public void init(String configFileName) {
/* 12 */     Path configDestinationPath = Services.PLATFORM.getConfigDir();
/* 13 */     Path configPath = configDestinationPath.resolve(configFileName);
/* 14 */     if (Services.PLATFORM.isDedicatedServer() && !Files.exists(configPath, new java.nio.file.LinkOption[0])) {
/* 15 */       Path oldConfigPath = Services.PLATFORM.getGameDir().resolve(configFileName);
/* 16 */       if (Files.exists(oldConfigPath, new java.nio.file.LinkOption[0]))
/* 17 */         configPath = oldConfigPath; 
/*    */     } 
/* 19 */     CommonConfigIO io = new CommonConfigIO(configPath);
/* 20 */     WorldMap.commonConfigIO = io;
/* 21 */     if (Files.exists(configPath, new java.nio.file.LinkOption[0])) {
/* 22 */       WorldMap.commonConfig = io.load();
/*    */     } else {
/* 24 */       WorldMap.commonConfig = CommonConfig.Builder.begin().build();
/* 25 */     }  io.save(WorldMap.commonConfig);
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\common\config\CommonConfigInit.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */