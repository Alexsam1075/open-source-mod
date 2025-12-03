/*     */ package xaero.map.patreon;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.net.URL;
/*     */ import java.nio.file.Path;
/*     */ import java.security.DigestInputStream;
/*     */ import java.security.MessageDigest;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.class_156;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_364;
/*     */ import net.minecraft.class_410;
/*     */ import net.minecraft.class_4185;
/*     */ import net.minecraft.class_8021;
/*     */ import net.minecraft.class_8667;
/*     */ import xaero.map.WorldMap;
/*     */ import xaero.map.platform.Services;
/*     */ 
/*     */ public class GuiUpdateAll extends class_410 {
/*     */   public GuiUpdateAll() {
/*  29 */     super(GuiUpdateAll::confirmResult, (class_2561)class_2561.method_43470("These mods are out-of-date: " + modListToNames(Patreon.getOutdatedMods())), 
/*  30 */         (class_2561)class_2561.method_43470(Patreon.getHasAutoUpdates() ? "Would you like to automatically update them?" : "Would you like to update them (open the mod pages)?"));
/*  31 */     Patreon.setNotificationDisplayed(true);
/*     */   }
/*     */   
/*     */   private static String modListToNames(List<Object> list) {
/*  35 */     StringBuilder builder = new StringBuilder();
/*  36 */     for (int i = 0; i < list.size(); i++) {
/*  37 */       if (i != 0)
/*  38 */         builder.append(", "); 
/*  39 */       builder.append(((PatreonMod)list.get(i)).modName);
/*     */     } 
/*  41 */     return builder.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void method_37051(class_8667 $$0) {
/*  46 */     super.method_37051($$0);
/*  47 */     if (Patreon.getHasAutoUpdates()) {
/*  48 */       this.field_61001.method_52736((class_8021)class_4185.method_46430((class_2561)class_2561.method_43469("Changelogs", new Object[0]), b -> {
/*     */               for (int i = 0; i < Patreon.getOutdatedMods().size(); i++) {
/*     */                 PatreonMod mod = (PatreonMod)Patreon.getOutdatedMods().get(i);
/*     */                 try {
/*     */                   class_156.method_668().method_673(new URI(mod.changelogLink));
/*  53 */                 } catch (URISyntaxException e) {
/*     */                   
/*     */                   WorldMap.LOGGER.error("suppressed exception", e);
/*     */                 } 
/*     */               } 
/*  58 */             }).method_46434(this.field_22789 / 2 - 100, this.field_22790 / 6 + 120, 200, 20).method_46431());
/*     */     }
/*  60 */     this.field_61001.method_52736((class_8021)class_4185.method_46430(
/*  61 */           (class_2561)class_2561.method_43469("Don't show again for these updates", new Object[0]), b -> {
/*     */             for (int i = 0; i < Patreon.getOutdatedMods().size(); i++) {
/*     */               PatreonMod mod = (PatreonMod)Patreon.getOutdatedMods().get(i);
/*     */               if (mod.onVersionIgnore != null) {
/*     */                 mod.onVersionIgnore.run();
/*     */               }
/*     */             } 
/*     */             this.field_22787.method_1507(null);
/*  69 */           }).method_46434(this.field_22789 / 2 - 100, this.field_22790 / 6 + 144, 200, 20).method_46431());
/*     */   }
/*     */   
/*     */   private static void confirmResult(boolean p_confirmResult_1_) {
/*  73 */     if (p_confirmResult_1_) {
/*  74 */       boolean shouldExit = false;
/*  75 */       if (Patreon.getHasAutoUpdates()) {
/*  76 */         for (class_364 b : (class_310.method_1551()).field_1755.method_25396()) {
/*  77 */           if (b instanceof class_4185)
/*  78 */             ((class_4185)b).field_22763 = false; 
/*     */         } 
/*  80 */         shouldExit = autoUpdate();
/*     */       } else {
/*  82 */         shouldExit = true;
/*  83 */         for (int i = 0; i < Patreon.getOutdatedMods().size(); i++) {
/*  84 */           PatreonMod m = (PatreonMod)Patreon.getOutdatedMods().get(i);
/*     */           try {
/*  86 */             class_156.method_668().method_673(new URI(m.changelogLink));
/*  87 */             if (m.modJar != null)
/*     */             {
/*  89 */               class_156.method_668().method_672(m.modJar.getParentFile()); } 
/*  90 */           } catch (Exception e) {
/*  91 */             WorldMap.LOGGER.error("suppressed exception", e);
/*  92 */             shouldExit = false;
/*     */           } 
/*     */         } 
/*     */       } 
/*  96 */       if (shouldExit) {
/*  97 */         class_310.method_1551().method_1592();
/*     */       } else {
/*  99 */         class_310.method_1551().method_1507(null);
/* 100 */       }  class_310.method_1551().method_1592();
/*     */     } else {
/* 102 */       class_310.method_1551().method_1507(null);
/*     */     } 
/*     */   }
/*     */   private static void download(BufferedOutputStream output, InputStream input, boolean closeInput) throws IOException {
/* 106 */     byte[] buffer = new byte[256];
/*     */     while (true) {
/* 108 */       int read = input.read(buffer, 0, buffer.length);
/* 109 */       if (read < 0)
/*     */         break; 
/* 111 */       output.write(buffer, 0, read);
/*     */     } 
/* 113 */     output.flush();
/* 114 */     if (closeInput)
/* 115 */       input.close(); 
/* 116 */     output.close();
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
/*     */   private static boolean autoUpdate() {
/*     */     try {
/*     */       MessageDigest digestMD5;
/*     */       try {
/* 145 */         digestMD5 = MessageDigest.getInstance("MD5");
/* 146 */       } catch (NoSuchAlgorithmException e1) {
/* 147 */         WorldMap.LOGGER.info("No algorithm for MD5.");
/* 148 */         return false;
/*     */       } 
/*     */       
/* 151 */       PatreonMod autoupdater = (PatreonMod)Patreon.getMods().get("autoupdater30");
/*     */ 
/*     */       
/* 154 */       String jarLink = autoupdater.changelogLink;
/* 155 */       String jarMD5 = autoupdater.latestVersionLayout;
/* 156 */       URL url = new URL(jarLink);
/*     */       
/* 158 */       HttpURLConnection conn = (HttpURLConnection)url.openConnection();
/* 159 */       conn.setReadTimeout(900);
/* 160 */       conn.setConnectTimeout(900);
/* 161 */       conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.56 Safari/535.11");
/*     */       
/* 163 */       if (conn.getContentLengthLong() > 2097152L)
/* 164 */         throw new IOException("Input too long to trust!"); 
/* 165 */       InputStream input = conn.getInputStream();
/* 166 */       input = new BufferedInputStream(input);
/* 167 */       DigestInputStream digestInput = new DigestInputStream(input, digestMD5);
/* 168 */       BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(Services.PLATFORM.getGameDir().resolve("xaero_autoupdater.jar").toFile()));
/* 169 */       download(output, digestInput, true);
/* 170 */       byte[] digest = digestMD5.digest();
/* 171 */       String fileMD5 = Hex.encodeHexString(digest);
/* 172 */       if (!jarMD5.equals(fileMD5)) {
/* 173 */         WorldMap.LOGGER.info("Invalid autoupdater MD5: " + fileMD5);
/* 174 */         return false;
/*     */       } 
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
/* 189 */       ArrayList<String> command = new ArrayList<>();
/*     */       
/* 191 */       Path javaPath = (new File(System.getProperty("java.home"))).toPath().resolve("bin").resolve("java");
/* 192 */       command.add(javaPath.toString());
/* 193 */       command.add("-jar");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 201 */       command.add("./xaero_autoupdater.jar");
/* 202 */       command.add("6");
/* 203 */       command.add(Patreon.getUpdateLocation());
/* 204 */       for (int i = 0; i < Patreon.getOutdatedMods().size(); i++) {
/* 205 */         PatreonMod m = (PatreonMod)Patreon.getOutdatedMods().get(i);
/* 206 */         if (m.modJar != null) {
/*     */           
/* 208 */           int canonicalPathAttempts = 10;
/* 209 */           String jarPath = null;
/* 210 */           while (canonicalPathAttempts-- > 0) {
/*     */             try {
/* 212 */               jarPath = m.modJar.getCanonicalPath();
/*     */               break;
/* 214 */             } catch (IOException ioe) {
/* 215 */               WorldMap.LOGGER.info("IO exception fetching the canonical path to the mod jar!");
/* 216 */               if (canonicalPathAttempts == 0) {
/* 217 */                 throw ioe;
/*     */               }
/* 219 */               WorldMap.LOGGER.error("suppressed exception", ioe);
/* 220 */               WorldMap.LOGGER.info("Retrying... (" + canonicalPathAttempts + ")");
/*     */               try {
/* 222 */                 Thread.sleep(25L);
/* 223 */               } catch (InterruptedException interruptedException) {}
/*     */             } 
/*     */           } 
/*     */ 
/*     */           
/* 228 */           command.add(jarPath);
/* 229 */           command.add(m.latestVersionLayout);
/* 230 */           command.add(m.currentVersion.split("_")[1]);
/* 231 */           command.add(m.latestVersion);
/* 232 */           command.add(m.currentVersion.split("_")[0]);
/* 233 */           command.add((m.md5 == null) ? "null" : m.md5);
/*     */         } 
/* 235 */       }  WorldMap.LOGGER.info(String.join(", ", (Iterable)command));
/* 236 */       Runtime.getRuntime().exec(command.<String>toArray(new String[0]));
/* 237 */     } catch (IOException e) {
/* 238 */       MessageDigest digestMD5; WorldMap.LOGGER.error("suppressed exception", (Throwable)digestMD5);
/* 239 */       return false;
/*     */     } 
/* 241 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\patreon\GuiUpdateAll.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */