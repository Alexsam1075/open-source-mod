/*    */ package xaero.map.server.mods;
/*    */ 
/*    */ import xaero.map.server.mods.argonauts.SupportArgonautsServer;
/*    */ import xaero.map.server.mods.ftbteams.SupportFTBTeamsServer;
/*    */ import xaero.map.server.mods.opac.SupportOPACServer;
/*    */ 
/*    */ public class SupportServerMods
/*    */ {
/*    */   private static SupportFTBTeamsServer ftbTeams;
/*    */   private static SupportArgonautsServer argonauts;
/*    */   private static SupportOPACServer opac;
/*    */   private static SupportMinimapServer minimap;
/*    */   
/*    */   public static void check() {
/*    */     try {
/* 16 */       Class.forName("dev.ftb.mods.ftbteams.api.FTBTeamsAPI");
/* 17 */       ftbTeams = new SupportFTBTeamsServer();
/* 18 */     } catch (ClassNotFoundException classNotFoundException) {}
/*    */     
/*    */     try {
/* 21 */       Class.forName("earth.terrarium.argonauts.api.ApiHelper");
/* 22 */       argonauts = new SupportArgonautsServer();
/* 23 */     } catch (ClassNotFoundException classNotFoundException) {}
/*    */     
/*    */     try {
/* 26 */       Class.forName("xaero.pac.common.server.api.OpenPACServerAPI");
/* 27 */       opac = new SupportOPACServer();
/* 28 */     } catch (ClassNotFoundException classNotFoundException) {}
/*    */     
/*    */     try {
/* 31 */       Class.forName("xaero.common.XaeroMinimapSession");
/* 32 */       minimap = new SupportMinimapServer();
/* 33 */     } catch (ClassNotFoundException classNotFoundException) {}
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean hasFtbTeams() {
/* 38 */     return (ftbTeams != null);
/*    */   }
/*    */   
/*    */   public static SupportFTBTeamsServer getFtbTeams() {
/* 42 */     return ftbTeams;
/*    */   }
/*    */   
/*    */   public static boolean hasArgonauts() {
/* 46 */     return (argonauts != null);
/*    */   }
/*    */   
/*    */   public static SupportArgonautsServer getArgonauts() {
/* 50 */     return argonauts;
/*    */   }
/*    */   
/*    */   public static boolean hasOpac() {
/* 54 */     return (opac != null);
/*    */   }
/*    */   
/*    */   public static SupportOPACServer getOpac() {
/* 58 */     return opac;
/*    */   }
/*    */   
/*    */   public static boolean hasMinimap() {
/* 62 */     return (minimap != null);
/*    */   }
/*    */   
/*    */   public static SupportMinimapServer getMinimap() {
/* 66 */     return minimap;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\server\mods\SupportServerMods.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */