/*     */ package xaero.map.misc;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.security.KeyFactory;
/*     */ import java.security.PublicKey;
/*     */ import java.security.spec.X509EncodedKeySpec;
/*     */ import java.util.Base64;
/*     */ import javax.crypto.Cipher;
/*     */ import xaero.map.WorldMap;
/*     */ import xaero.map.patreon.Patreon;
/*     */ import xaero.map.patreon.decrypt.DecryptInputStream;
/*     */ import xaero.map.settings.ModSettings;
/*     */ 
/*     */ public class Internet
/*     */ {
/*  21 */   public static Cipher cipher = null;
/*     */   
/*     */   static {
/*     */     try {
/*  25 */       cipher = Cipher.getInstance("RSA");
/*  26 */       KeyFactory factory = KeyFactory.getInstance("RSA");
/*  27 */       byte[] byteKey = Base64.getDecoder().decode(Patreon.getPublicKeyString().getBytes());
/*  28 */       X509EncodedKeySpec X509publicKey = new X509EncodedKeySpec(byteKey);
/*  29 */       PublicKey publicKey = factory.generatePublic(X509publicKey);
/*  30 */       cipher.init(2, publicKey);
/*  31 */     } catch (Exception e) {
/*  32 */       cipher = null;
/*  33 */       WorldMap.LOGGER.error("suppressed exception", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void checkModVersion() {
/*  38 */     if (!WorldMap.settings.allowInternetAccess)
/*     */       return; 
/*  40 */     int keyVersion = Patreon.getKEY_VERSION();
/*  41 */     String s = "http://data.chocolateminecraft.com/Versions_" + keyVersion + "/WorldMap" + ((keyVersion >= 4) ? ".dat" : ".txt");
/*  42 */     s = s.replaceAll(" ", "%20");
/*     */     
/*     */     try {
/*  45 */       if (cipher == null)
/*  46 */         throw new Exception("Cipher instance is null!"); 
/*  47 */       URL url = new URL(s);
/*     */       
/*  49 */       URLConnection conn = url.openConnection();
/*  50 */       conn.setReadTimeout(900);
/*  51 */       conn.setConnectTimeout(900);
/*  52 */       if (conn.getContentLengthLong() > 524288L)
/*  53 */         throw new IOException("Input too long to trust!"); 
/*  54 */       BufferedReader reader = new BufferedReader(new InputStreamReader((InputStream)new DecryptInputStream(conn.getInputStream(), cipher), "UTF8"));
/*  55 */       WorldMap.isOutdated = true;
/*  56 */       String line = reader.readLine();
/*  57 */       if (line != null) {
/*  58 */         WorldMap.newestUpdateID = Integer.parseInt(line);
/*  59 */         if (!ModSettings.updateNotification || WorldMap.newestUpdateID == ModSettings.ignoreUpdate) {
/*  60 */           WorldMap.isOutdated = false;
/*  61 */           reader.close();
/*     */           return;
/*     */         } 
/*     */       } 
/*  65 */       boolean versionFound = false;
/*  66 */       String[] current = WorldMap.INSTANCE.getVersionID().split("_");
/*  67 */       while ((line = reader.readLine()) != null) {
/*  68 */         if (line.equals(WorldMap.INSTANCE.getVersionID())) {
/*  69 */           WorldMap.isOutdated = false; break;
/*     */         } 
/*  71 */         if (Patreon.getHasAutoUpdates()) {
/*  72 */           if (versionFound) {
/*  73 */             if (line.startsWith("meta;")) {
/*  74 */               String[] metadata = line.substring(5).split(";");
/*  75 */               WorldMap.latestVersionMD5 = metadata[0];
/*     */             } 
/*  77 */             versionFound = false;
/*     */           } 
/*  79 */           if (line.startsWith(current[0] + "_")) {
/*  80 */             String[] args = line.split("_");
/*  81 */             if (args.length == current.length) {
/*  82 */               boolean sameType = true;
/*  83 */               if (current.length > 2)
/*  84 */                 for (int i = 2; i < current.length && sameType; i++) {
/*  85 */                   if (!args[i].equals(current[i]))
/*  86 */                     sameType = false; 
/*  87 */                 }   if (sameType) {
/*  88 */                 WorldMap.latestVersion = args[1];
/*  89 */                 versionFound = true;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*  95 */       reader.close();
/*  96 */     } catch (IOException ioe) {
/*  97 */       WorldMap.LOGGER.error("io exception while checking versions: {}", ioe.getMessage());
/*  98 */       WorldMap.isOutdated = false;
/*  99 */     } catch (Throwable e) {
/* 100 */       WorldMap.LOGGER.error("suppressed exception", e);
/* 101 */       WorldMap.isOutdated = false;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\misc\Internet.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */