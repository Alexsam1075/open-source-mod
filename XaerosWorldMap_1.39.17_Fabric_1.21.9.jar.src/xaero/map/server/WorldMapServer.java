/*    */ package xaero.map.server;
/*    */ 
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ import xaero.map.server.events.ServerEvents;
/*    */ 
/*    */ public abstract class WorldMapServer
/*    */ {
/*  9 */   public static Logger LOGGER = LogManager.getLogger();
/*    */ 
/*    */   
/*    */   protected ServerEvents serverEvents;
/*    */ 
/*    */   
/*    */   public void load() {
/* 16 */     LOGGER.info("Loading Xaero's World Map - Stage 1/2 (Server)");
/*    */   }
/*    */   
/*    */   public void loadLater() {
/* 20 */     LOGGER.info("Loading Xaero's World Map - Stage 2/2 (Server)");
/*    */   }
/*    */   
/*    */   public ServerEvents getServerEvents() {
/* 24 */     return this.serverEvents;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\server\WorldMapServer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */