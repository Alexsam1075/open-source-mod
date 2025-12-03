/*    */ package xaero.map.highlight;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HighlighterRegistry
/*    */ {
/* 12 */   private List<AbstractHighlighter> highlighters = new ArrayList<>();
/*    */ 
/*    */   
/*    */   public void register(AbstractHighlighter highlighter) {
/* 16 */     this.highlighters.add(highlighter);
/*    */   }
/*    */   
/*    */   public void end() {
/* 20 */     this.highlighters = Collections.unmodifiableList(this.highlighters);
/*    */   }
/*    */   
/*    */   public List<AbstractHighlighter> getHighlighters() {
/* 24 */     return this.highlighters;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\highlight\HighlighterRegistry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */