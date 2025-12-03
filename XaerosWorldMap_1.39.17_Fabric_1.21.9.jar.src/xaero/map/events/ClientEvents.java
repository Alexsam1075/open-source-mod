/*     */ package xaero.map.events;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.List;
/*     */ import net.minecraft.class_1041;
/*     */ import net.minecraft.class_1074;
/*     */ import net.minecraft.class_1657;
/*     */ import net.minecraft.class_1936;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_2556;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_3218;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_4358;
/*     */ import net.minecraft.class_437;
/*     */ import net.minecraft.class_4398;
/*     */ import net.minecraft.class_4439;
/*     */ import net.minecraft.class_4877;
/*     */ import net.minecraft.class_638;
/*     */ import xaero.map.MapProcessor;
/*     */ import xaero.map.WorldMap;
/*     */ import xaero.map.WorldMapSession;
/*     */ import xaero.map.file.worldsave.WorldDataHandler;
/*     */ import xaero.map.graphics.shader.CustomUniforms;
/*     */ import xaero.map.misc.Misc;
/*     */ import xaero.map.mods.SupportMods;
/*     */ import xaero.map.patreon.GuiUpdateAll;
/*     */ import xaero.map.patreon.Patreon;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClientEvents
/*     */ {
/*     */   private class_4877 latestRealm;
/*     */   private Field realmsTaskField;
/*     */   private Field realmsTaskServerField;
/*     */   
/*     */   public class_437 handleGuiOpen(class_437 gui) {
/*  42 */     if (gui instanceof class_4398) {
/*     */       try {
/*  44 */         if (this.realmsTaskField == null) {
/*  45 */           this.realmsTaskField = Misc.getFieldReflection(class_4398.class, "queuedTasks", "field_46707", "Ljava/util/List;", "f_302752_");
/*  46 */           this.realmsTaskField.setAccessible(true);
/*     */         } 
/*  48 */         if (this.realmsTaskServerField == null) {
/*  49 */           this.realmsTaskServerField = Misc.getFieldReflection(class_4439.class, "server", "field_20224", "Lnet/minecraft/class_4877;", "f_90327_");
/*  50 */           this.realmsTaskServerField.setAccessible(true);
/*     */         } 
/*  52 */         class_4398 realmsTaskScreen = (class_4398)gui;
/*     */         
/*  54 */         List<class_4358> tasks = (List<class_4358>)this.realmsTaskField.get(realmsTaskScreen);
/*  55 */         for (class_4358 task : tasks) {
/*  56 */           if (task instanceof class_4439) {
/*  57 */             class_4439 realmsTask = (class_4439)task;
/*  58 */             class_4877 realm = (class_4877)this.realmsTaskServerField.get(realmsTask);
/*  59 */             if (realm != null && (this.latestRealm == null || realm.field_22599 != this.latestRealm.field_22599))
/*     */             {
/*  61 */               this.latestRealm = realm; } 
/*     */           } 
/*     */         } 
/*  64 */       } catch (Exception e) {
/*  65 */         WorldMap.LOGGER.error("suppressed exception", e);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*  70 */     return gui;
/*     */   }
/*     */   
/*     */   public void onMinecraftRunTickStart() {
/*  74 */     WorldMapSession worldmapSession = WorldMapSession.getCurrentSession();
/*  75 */     if (worldmapSession == null)
/*     */       return; 
/*  77 */     MapProcessor mapProcessor = worldmapSession.getMapProcessor();
/*  78 */     mapProcessor.getRenderStartTimeUpdater().run();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean handleRenderTick(boolean start) {
/*  83 */     if (!WorldMap.loaded)
/*  84 */       return false; 
/*  85 */     class_310 mc = class_310.method_1551();
/*  86 */     if (!start) {
/*  87 */       WorldMap.gpuObjectDeleter.work();
/*  88 */       CustomUniforms.endFrame();
/*     */     } 
/*  90 */     boolean shouldCancelGameRender = false;
/*  91 */     if (mc.field_1724 != null) {
/*  92 */       WorldMapSession worldmapSession = WorldMapSession.getCurrentSession();
/*  93 */       if (worldmapSession != null) {
/*  94 */         MapProcessor mapProcessor = worldmapSession.getMapProcessor();
/*  95 */         if (!start) {
/*  96 */           mapProcessor.onRenderProcess(mc);
/*  97 */           mapProcessor.resetRenderStartTime();
/*     */         } else {
/*     */           
/* 100 */           if (!SupportMods.vivecraft && Misc.screenShouldSkipWorldRender(mc.field_1755, true)) {
/* 101 */             mc.field_1687.method_38534();
/* 102 */             mc.field_1687.method_2935().method_12130().method_15516();
/* 103 */             shouldCancelGameRender = true;
/*     */           } 
/* 105 */           if (mapProcessor != null)
/* 106 */             mapProcessor.setMainValues(); 
/*     */         } 
/*     */       } 
/*     */     } 
/* 110 */     return shouldCancelGameRender;
/*     */   }
/*     */   
/*     */   public void handleDrawScreen(class_437 gui) {
/* 114 */     if (Patreon.needsNotification() && gui instanceof net.minecraft.class_442 && !SupportMods.minimap()) {
/* 115 */       class_310.method_1551().method_1507((class_437)new GuiUpdateAll());
/* 116 */     } else if (WorldMap.isOutdated) {
/* 117 */       WorldMap.isOutdated = false;
/*     */     } 
/*     */   }
/*     */   public void handlePlayerSetSpawnEvent(class_2338 spawn, class_638 world) {
/* 121 */     WorldMapSession worldmapSession = WorldMapSession.getCurrentSession();
/* 122 */     if (worldmapSession != null) {
/* 123 */       MapProcessor mapProcessor = worldmapSession.getMapProcessor();
/* 124 */       mapProcessor.updateWorldSpawn(spawn, world);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleWorldUnload(class_1936 world) {
/* 135 */     if ((class_310.method_1551()).field_1724 != null) {
/* 136 */       WorldMapSession worldmapSession = WorldMapSession.getCurrentSession();
/* 137 */       if (worldmapSession != null) {
/* 138 */         MapProcessor mapProcessor = worldmapSession.getMapProcessor();
/* 139 */         if (world == mapProcessor.mainWorld)
/* 140 */           mapProcessor.onWorldUnload(); 
/*     */       } 
/*     */     } 
/* 143 */     if (world instanceof class_3218) {
/* 144 */       class_3218 sw = (class_3218)world;
/* 145 */       WorldDataHandler.onServerWorldUnload(sw);
/*     */     } 
/*     */   }
/*     */   
/*     */   public class_4877 getLatestRealm() {
/* 150 */     return this.latestRealm;
/*     */   }
/*     */   
/*     */   public boolean handleRenderCrosshairOverlay(class_332 guiGraphics) {
/* 154 */     if ((class_310.method_1551()).field_1690.field_1842)
/* 155 */       return false; 
/* 156 */     WorldMapSession worldmapSession = WorldMapSession.getCurrentSession();
/* 157 */     MapProcessor mapProcessor = (worldmapSession == null) ? null : worldmapSession.getMapProcessor();
/* 158 */     String crosshairMessage = (mapProcessor == null) ? null : mapProcessor.getCrosshairMessage();
/* 159 */     if (crosshairMessage != null) {
/* 160 */       int messageWidth = (class_310.method_1551()).field_1772.method_1727(crosshairMessage);
/* 161 */       class_1041 window = class_310.method_1551().method_22683();
/* 162 */       guiGraphics.method_25303((class_310.method_1551()).field_1772, crosshairMessage, window.method_4486() / 2 - messageWidth / 2, window.method_4502() / 2 + 60, -1);
/*     */     } 
/*     */     
/* 165 */     return false;
/*     */   }
/*     */   
/*     */   public boolean handleClientPlayerChatReceivedEvent(class_2556.class_7602 chatType, class_2561 component, GameProfile gameProfile) {
/* 169 */     if (component == null)
/* 170 */       return false; 
/* 171 */     return handleChatMessage((gameProfile == null) ? null : gameProfile.name(), component);
/*     */   }
/*     */   
/*     */   public boolean handleClientSystemChatReceivedEvent(class_2561 component) {
/* 175 */     if (component == null)
/* 176 */       return false; 
/* 177 */     String textString = component.getString();
/* 178 */     if (textString.contains("§r§e§s§e§t§x§a§e§r§o")) {
/* 179 */       WorldMapSession worldmapSession = WorldMapSession.getCurrentSession();
/* 180 */       worldmapSession.getMapProcessor().setConsideringNetherFairPlayMessage(false);
/*     */     } 
/* 182 */     if (textString.contains("§x§a§e§r§o§w§m§n§e§t§h§e§r§i§s§f§a§i§r")) {
/* 183 */       WorldMapSession worldmapSession = WorldMapSession.getCurrentSession();
/* 184 */       worldmapSession.getMapProcessor().setConsideringNetherFairPlayMessage(true);
/*     */     } 
/* 186 */     return handleChatMessage(class_1074.method_4662("gui.xaero_waypoint_server_shared", new Object[0]), component);
/*     */   }
/*     */   
/*     */   private boolean handleChatMessage(String playerName, class_2561 text) {
/* 190 */     return false;
/*     */   }
/*     */   
/*     */   public void handlePlayerTickStart(class_1657 player) {
/* 194 */     if (player == (class_310.method_1551()).field_1724) {
/* 195 */       WorldMapSession worldmapSession = WorldMapSession.getCurrentSession();
/* 196 */       if (worldmapSession != null)
/* 197 */         worldmapSession.getControlsHandler().handleKeyEvents(); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void handleClientTickStart() {
/* 202 */     if ((class_310.method_1551()).field_1724 != null) {
/* 203 */       if (!WorldMap.loaded)
/*     */         return; 
/* 205 */       WorldMap.crashHandler.checkForCrashes();
/* 206 */       WorldMapSession worldmapSession = WorldMapSession.getCurrentSession();
/* 207 */       if (worldmapSession != null) {
/* 208 */         MapProcessor mapProcessor = worldmapSession.getMapProcessor();
/* 209 */         mapProcessor.onClientTickStart();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void handleClientRunTickStart() {
/* 215 */     if ((class_310.method_1551()).field_1724 != null) {
/* 216 */       if (!WorldMap.loaded)
/*     */         return; 
/* 218 */       WorldMap.crashHandler.checkForCrashes();
/* 219 */       WorldMapSession worldmapSession = WorldMapSession.getCurrentSession();
/* 220 */       if (worldmapSession != null)
/* 221 */         worldmapSession.getMapProcessor().getWorldDataHandler().handleRenderExecutor(); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\events\ClientEvents.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */