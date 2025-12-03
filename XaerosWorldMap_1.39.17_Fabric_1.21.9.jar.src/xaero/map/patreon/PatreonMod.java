/*    */ package xaero.map.patreon;
/*    */ 
/*    */ import java.io.File;
/*    */ 
/*    */ public class PatreonMod
/*    */ {
/*    */   public PatreonMod(String fileLayoutID, String latestVersionLayout, String changelogLink, String modName) {
/*  8 */     this.fileLayoutID = fileLayoutID;
/*  9 */     this.latestVersionLayout = latestVersionLayout;
/* 10 */     this.changelogLink = changelogLink;
/* 11 */     this.modName = modName;
/*    */   }
/*    */   
/*    */   public String fileLayoutID;
/*    */   public String latestVersionLayout;
/*    */   public String changelogLink;
/*    */   public String modName;
/*    */   public File modJar;
/*    */   public String currentVersion;
/*    */   public String latestVersion;
/*    */   public String md5;
/*    */   public Runnable onVersionIgnore;
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\patreon\PatreonMod.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */