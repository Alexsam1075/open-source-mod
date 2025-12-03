/*    */ package xaero.map.world;
/*    */ 
/*    */ import net.minecraft.class_1937;
/*    */ import net.minecraft.class_2960;
/*    */ import net.minecraft.class_5321;
/*    */ import net.minecraft.class_7924;
/*    */ 
/*    */ 
/*    */ public class MapConnectionNode
/*    */ {
/*    */   private final class_5321<class_1937> dimId;
/*    */   private final String mw;
/*    */   private String cachedString;
/*    */   
/*    */   public MapConnectionNode(class_5321<class_1937> dimId, String mw) {
/* 16 */     this.dimId = dimId;
/* 17 */     this.mw = mw;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 22 */     if (this.cachedString == null)
/* 23 */       this.cachedString = this.dimId.method_29177().toString().replace(':', '$') + "/" + this.dimId.method_29177().toString().replace(':', '$'); 
/* 24 */     return this.cachedString;
/*    */   }
/*    */   
/*    */   public String getNamedString(MapWorld mapWorld) {
/* 28 */     MapDimension dim = mapWorld.getDimension(this.dimId);
/* 29 */     return String.valueOf(this.dimId.method_29177()) + "/" + String.valueOf(this.dimId.method_29177());
/*    */   }
/*    */   public static MapConnectionNode fromString(String s) {
/*    */     class_2960 dimLocation;
/* 33 */     int dividerIndex = s.lastIndexOf('/');
/* 34 */     if (dividerIndex == -1)
/* 35 */       return null; 
/* 36 */     String dimString = s.substring(0, dividerIndex);
/*    */     
/*    */     try {
/* 39 */       if (dimString.equals("0"))
/* 40 */       { dimLocation = class_1937.field_25179.method_29177(); }
/* 41 */       else if (dimString.equals("-1"))
/* 42 */       { dimLocation = class_1937.field_25180.method_29177(); }
/* 43 */       else if (dimString.equals("1"))
/* 44 */       { dimLocation = class_1937.field_25181.method_29177(); }
/*    */       else
/* 46 */       { dimLocation = class_2960.method_60654(dimString.replace('$', ':')); } 
/* 47 */     } catch (Throwable t) {
/* 48 */       return null;
/*    */     } 
/* 50 */     String mwString = s.substring(dividerIndex + 1);
/* 51 */     return new MapConnectionNode(class_5321.method_29179(class_7924.field_41223, dimLocation), mwString);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object another) {
/* 56 */     if (this == another)
/* 57 */       return true; 
/* 58 */     if (another == null || !(another instanceof MapConnectionNode))
/* 59 */       return false; 
/* 60 */     MapConnectionNode anotherNode = (MapConnectionNode)another;
/* 61 */     return (this.dimId.equals(anotherNode.dimId) && this.mw.equals(anotherNode.mw));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 66 */     return toString().hashCode();
/*    */   }
/*    */   
/*    */   public class_5321<class_1937> getDimId() {
/* 70 */     return this.dimId;
/*    */   }
/*    */   
/*    */   public String getMw() {
/* 74 */     return this.mw;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\world\MapConnectionNode.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */