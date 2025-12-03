/*     */ package xaero.map.patreon;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.PrintWriter;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.security.KeyFactory;
/*     */ import java.security.PublicKey;
/*     */ import java.security.spec.X509EncodedKeySpec;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import javax.crypto.Cipher;
/*     */ import net.minecraft.class_310;
/*     */ import xaero.common.patreon.PatreonMod;
/*     */ import xaero.map.WorldMap;
/*     */ import xaero.map.patreon.decrypt.DecryptInputStream;
/*     */ import xaero.map.platform.Services;
/*     */ 
/*     */ public class Patreon {
/*     */   private static boolean hasAutoUpdates;
/*     */   private static int onlineWidgetLevel;
/*  26 */   private static HashMap<String, Object> mods = new HashMap<>(); private static boolean notificationDisplayed; private static boolean loaded = false; private static String updateLocation;
/*  27 */   private static ArrayList<Object> outdatedMods = new ArrayList();
/*     */   
/*  29 */   private static Cipher cipher = null;
/*  30 */   private static int KEY_VERSION = 4;
/*  31 */   private static String publicKeyString = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAoBeELcruvAEIeLF/UsWF/v5rxyRXIpCs+eORLCbDw5cz9jHsnoypQKx0RTk5rcXIeA0HbEfY0eREB25quHjhZKul7MnzotQT+F2Qb1bPfHa6+SPie+pj79GGGAFP3npki6RqoU/wyYkd1tOomuD8v5ytEkOPC4U42kxxvx23A7vH6w46dew/E/HvfbBvZF2KrqdJtwKAunk847C3FgyhVq8/vzQc6mqAW6Mmn4zlwFvyCnTOWjIRw/I93WIM/uvhE3lt6pmtrWA2yIbKIj1z4pgG/K72EqHfYLGkBFTh7fV1wwCbpNTXZX2JnTfmvMGqzHjq7FijwVfCpFB/dWR3wQIDAQAB";
/*     */   private static boolean shouldRedirectToMinimap = false;
/*     */   
/*     */   static {
/*     */     try {
/*  36 */       Class.forName("xaero.common.patreon.Patreon");
/*  37 */       shouldRedirectToMinimap = true;
/*  38 */     } catch (ClassNotFoundException classNotFoundException) {}
/*     */     
/*  40 */     if (!shouldRedirectToMinimap)
/*     */       try {
/*  42 */         cipher = Cipher.getInstance("RSA");
/*  43 */         KeyFactory factory = KeyFactory.getInstance("RSA");
/*  44 */         byte[] byteKey = Base64.getDecoder().decode(getPublicKeyString().getBytes());
/*  45 */         X509EncodedKeySpec X509publicKey = new X509EncodedKeySpec(byteKey);
/*  46 */         PublicKey publicKey = factory.generatePublic(X509publicKey);
/*  47 */         cipher.init(2, publicKey);
/*  48 */       } catch (Exception e) {
/*  49 */         cipher = null;
/*  50 */         WorldMap.LOGGER.error("suppressed exception", e);
/*     */       }  
/*     */   }
/*     */   
/*     */   public static void checkPatreon() {
/*  55 */     if (!WorldMap.settings.allowInternetAccess)
/*     */       return; 
/*  57 */     if (shouldRedirectToMinimap) {
/*  58 */       xaero.common.patreon.Patreon.checkPatreon();
/*     */       return;
/*     */     } 
/*  61 */     synchronized (mods) {
/*  62 */       if (loaded)
/*     */         return; 
/*  64 */       loadSettings();
/*  65 */       String s = "http://data.chocolateminecraft.com/Versions_" + KEY_VERSION + "/Patreon2.dat";
/*  66 */       s = s.replaceAll(" ", "%20");
/*     */       
/*     */       try {
/*  69 */         URL url = new URL(s);
/*     */         
/*  71 */         URLConnection conn = url.openConnection();
/*  72 */         conn.setReadTimeout(900);
/*  73 */         conn.setConnectTimeout(900);
/*  74 */         if (conn.getContentLengthLong() > 524288L)
/*  75 */           throw new IOException("Input too long to trust!"); 
/*  76 */         BufferedReader reader = new BufferedReader(new InputStreamReader((InputStream)new DecryptInputStream(conn.getInputStream(), cipher)));
/*     */         
/*  78 */         boolean parsingPatrons = false;
/*  79 */         String localPlayerName = class_310.method_1551().method_1548().method_1676(); String line;
/*  80 */         while ((line = reader.readLine()) != null && !line.equals("LAYOUTS")) {
/*  81 */           if (line.startsWith("PATREON")) {
/*  82 */             parsingPatrons = true;
/*     */             continue;
/*     */           } 
/*  85 */           if (!parsingPatrons)
/*     */             continue; 
/*  87 */           String[] rewards = line.split(";");
/*  88 */           if (rewards.length <= 1 || !rewards[0].equalsIgnoreCase(localPlayerName))
/*     */             continue; 
/*  90 */           for (int i = 1; i < rewards.length; i++) {
/*  91 */             String rewardString = rewards[i].trim();
/*  92 */             if ("updates".equals(rewardString)) {
/*  93 */               hasAutoUpdates = true;
/*     */             } else {
/*     */               
/*  96 */               String[] keyAndValue = rewardString.split(":");
/*  97 */               if (keyAndValue.length >= 2)
/*     */               {
/*  99 */                 if (keyAndValue[0].equals("widget_level")) {
/*     */                   try {
/* 101 */                     onlineWidgetLevel = Integer.parseInt(keyAndValue[1]);
/* 102 */                   } catch (NumberFormatException numberFormatException) {}
/*     */                 }
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/* 108 */         updateLocation = reader.readLine();
/* 109 */         while ((line = reader.readLine()) != null) {
/* 110 */           String[] args = line.split("\\t");
/* 111 */           mods.put(args[0], new PatreonMod(args[0], args[1], args[2], args[3]));
/*     */         } 
/*     */         
/* 114 */         reader.close();
/* 115 */       } catch (IOException ioe) {
/* 116 */         WorldMap.LOGGER.error("io exception while checking patreon: {}", ioe.getMessage());
/* 117 */         mods.clear();
/* 118 */       } catch (Throwable e) {
/* 119 */         WorldMap.LOGGER.error("suppressed exception", e);
/* 120 */         mods.clear();
/*     */       } finally {
/* 122 */         loaded = true;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void addOutdatedMod(Object mod) {
/* 128 */     if (shouldRedirectToMinimap) {
/* 129 */       xaero.common.patreon.Patreon.addOutdatedMod(mod);
/*     */       return;
/*     */     } 
/* 132 */     synchronized (getOutdatedMods()) {
/* 133 */       getOutdatedMods().add(mod);
/*     */     } 
/*     */   }
/*     */   
/* 137 */   private static File optionsFile = Services.PLATFORM.getGameDir().resolve("config").resolve("xaeropatreon.txt").toFile();
/*     */   public static void saveSettings() {
/* 139 */     if (shouldRedirectToMinimap) {
/* 140 */       xaero.common.patreon.Patreon.saveSettings();
/*     */       
/*     */       return;
/*     */     } 
/*     */     try {
/* 145 */       PrintWriter writer = new PrintWriter(new FileWriter(optionsFile));
/*     */       
/* 147 */       writer.close();
/* 148 */     } catch (IOException e) {
/* 149 */       WorldMap.LOGGER.error("suppressed exception", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void loadSettings() {
/* 154 */     if (shouldRedirectToMinimap) {
/* 155 */       xaero.common.patreon.Patreon.loadSettings();
/*     */       return;
/*     */     } 
/*     */     try {
/* 159 */       if (!optionsFile.exists()) {
/* 160 */         saveSettings();
/*     */         
/*     */         return;
/*     */       } 
/* 164 */       BufferedReader reader = new BufferedReader(new FileReader(optionsFile));
/*     */       String line;
/* 166 */       while ((line = reader.readLine()) != null) {
/* 167 */         String[] arrayOfString = line.split(":");
/*     */       }
/*     */ 
/*     */       
/* 171 */       reader.close();
/* 172 */     } catch (IOException e) {
/* 173 */       WorldMap.LOGGER.error("suppressed exception", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ArrayList<Object> getOutdatedMods() {
/* 233 */     if (shouldRedirectToMinimap) {
/* 234 */       return xaero.common.patreon.Patreon.getOutdatedMods();
/*     */     }
/* 236 */     return outdatedMods;
/*     */   }
/*     */   
/*     */   public static boolean needsNotification() {
/* 240 */     if (shouldRedirectToMinimap) {
/* 241 */       return false;
/*     */     }
/* 243 */     return (!isNotificationDisplayed() && !outdatedMods.isEmpty());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isNotificationDisplayed() {
/* 263 */     if (shouldRedirectToMinimap) {
/* 264 */       return xaero.common.patreon.Patreon.isNotificationDisplayed();
/*     */     }
/* 266 */     return notificationDisplayed;
/*     */   }
/*     */   
/*     */   public static void setNotificationDisplayed(boolean notificationDisplayed) {
/* 270 */     if (shouldRedirectToMinimap) {
/* 271 */       xaero.common.patreon.Patreon.setNotificationDisplayed(notificationDisplayed);
/*     */       return;
/*     */     } 
/* 274 */     Patreon.notificationDisplayed = notificationDisplayed;
/*     */   }
/*     */   
/*     */   public static HashMap<String, Object> getMods() {
/* 278 */     if (shouldRedirectToMinimap) {
/* 279 */       return xaero.common.patreon.Patreon.getMods();
/*     */     }
/* 281 */     return mods;
/*     */   }
/*     */   
/*     */   public static String getUpdateLocation() {
/* 285 */     if (shouldRedirectToMinimap) {
/* 286 */       return xaero.common.patreon.Patreon.getUpdateLocation();
/*     */     }
/* 288 */     return updateLocation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setModInfo(Object mod, File modJAR, String versionID, String latestVersion, String latestVersionMD5, Runnable onVersionIgnore) {
/* 308 */     if (shouldRedirectToMinimap) {
/* 309 */       PatreonMod patreonMod = (PatreonMod)mod;
/* 310 */       patreonMod.modJar = modJAR;
/* 311 */       patreonMod.currentVersion = versionID;
/* 312 */       patreonMod.latestVersion = latestVersion;
/* 313 */       patreonMod.md5 = latestVersionMD5;
/* 314 */       patreonMod.onVersionIgnore = onVersionIgnore;
/*     */       return;
/*     */     } 
/* 317 */     PatreonMod patreonEntry = (PatreonMod)mod;
/* 318 */     patreonEntry.modJar = modJAR;
/* 319 */     patreonEntry.currentVersion = versionID;
/* 320 */     patreonEntry.latestVersion = latestVersion;
/* 321 */     patreonEntry.md5 = latestVersionMD5;
/* 322 */     patreonEntry.onVersionIgnore = onVersionIgnore;
/*     */   }
/*     */   
/*     */   public static String getPublicKeyString() {
/* 326 */     if (shouldRedirectToMinimap) {
/* 327 */       return xaero.common.patreon.Patreon.getPublicKeyString2();
/*     */     }
/* 329 */     return publicKeyString;
/*     */   }
/*     */   
/*     */   public static int getKEY_VERSION() {
/* 333 */     if (shouldRedirectToMinimap) {
/* 334 */       return xaero.common.patreon.Patreon.getKEY_VERSION2();
/*     */     }
/* 336 */     return KEY_VERSION;
/*     */   }
/*     */   
/*     */   public static boolean getHasAutoUpdates() {
/* 340 */     if (shouldRedirectToMinimap)
/* 341 */       return xaero.common.patreon.Patreon.getHasAutoUpdates(); 
/* 342 */     return hasAutoUpdates;
/*     */   }
/*     */   
/*     */   public static int getOnlineWidgetLevel() {
/* 346 */     if (shouldRedirectToMinimap)
/* 347 */       return xaero.common.patreon.Patreon.getOnlineWidgetLevel(); 
/* 348 */     return onlineWidgetLevel;
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\patreon\Patreon.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */