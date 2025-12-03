/*    */ package xaero.map.server.mods.argonauts;
/*    */ 
/*    */ import earth.terrarium.argonauts.api.guild.Guild;
/*    */ import earth.terrarium.argonauts.api.guild.GuildApi;
/*    */ import earth.terrarium.argonauts.api.party.Party;
/*    */ import earth.terrarium.argonauts.api.party.PartyApi;
/*    */ import net.minecraft.class_1657;
/*    */ import xaero.map.server.radar.tracker.ISyncedPlayerTrackerSystem;
/*    */ 
/*    */ public class ArgonautsSyncedPlayerTrackerSystem
/*    */   implements ISyncedPlayerTrackerSystem
/*    */ {
/*    */   public int getTrackingLevel(class_1657 tracker, class_1657 tracked) {
/* 14 */     int partyTrackingLevel = getPartyTrackingLevel(tracker, tracked);
/* 15 */     int guildTrackingLevel = getGuildTrackingLevel(tracker, tracked);
/* 16 */     return Math.max(partyTrackingLevel, guildTrackingLevel);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isPartySystem() {
/* 21 */     return true;
/*    */   }
/*    */   
/*    */   private int getPartyTrackingLevel(class_1657 tracker, class_1657 tracked) {
/* 25 */     Party trackerParty = PartyApi.API.get(tracker);
/* 26 */     if (trackerParty == null)
/* 27 */       return 0; 
/* 28 */     Party trackedParty = PartyApi.API.get(tracked);
/* 29 */     if (trackerParty == trackedParty)
/* 30 */       return 2; 
/* 31 */     return 0;
/*    */   }
/*    */   
/*    */   private int getGuildTrackingLevel(class_1657 tracker, class_1657 tracked) {
/* 35 */     Guild trackerGuild = GuildApi.API.getPlayerGuild(tracker.method_73183().method_8503(), tracker.method_5667());
/* 36 */     if (trackerGuild == null)
/* 37 */       return 0; 
/* 38 */     Guild trackedGuild = GuildApi.API.getPlayerGuild(tracked.method_73183().method_8503(), tracked.method_5667());
/* 39 */     if (trackerGuild == trackedGuild)
/* 40 */       return 2; 
/* 41 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\server\mods\argonauts\ArgonautsSyncedPlayerTrackerSystem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */