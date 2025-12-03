/*    */ package xaero.map.profile;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.objects.Object2LongMap;
/*    */ import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
/*    */ import xaero.map.WorldMap;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SimpleProfiler
/*    */ {
/* 15 */   private final Object2LongMap<String> sections = (Object2LongMap<String>)new Object2LongOpenHashMap();
/*    */   private String currentSection;
/*    */   
/*    */   public void reset() {
/* 19 */     this.sections.clear();
/* 20 */     this.currentSection = null;
/*    */   }
/*    */   private long previousNanoTime;
/*    */   private void addTime(String sectionName, long time) {
/* 24 */     long current = this.sections.getOrDefault(sectionName, 0L);
/* 25 */     this.sections.put(sectionName, current + time);
/*    */   }
/*    */   
/*    */   public void section(String sectionName) {
/* 29 */     long currentTime = System.nanoTime();
/* 30 */     if (this.currentSection != null) {
/* 31 */       long passed = currentTime - this.previousNanoTime;
/* 32 */       addTime(this.currentSection, passed);
/*    */     } 
/* 34 */     this.previousNanoTime = currentTime;
/* 35 */     this.currentSection = sectionName;
/*    */   }
/*    */   
/*    */   public void end() {
/* 39 */     section(null);
/*    */   }
/*    */   
/*    */   public void debug() {
/* 43 */     this.sections.forEach((sectionName, time) -> WorldMap.LOGGER.info(sectionName + " : " + sectionName + " (" + time + " ms)"));
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\profile\SimpleProfiler.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */