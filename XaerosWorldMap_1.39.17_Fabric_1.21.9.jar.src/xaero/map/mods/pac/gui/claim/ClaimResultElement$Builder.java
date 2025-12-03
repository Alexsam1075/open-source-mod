/*     */ package xaero.map.mods.pac.gui.claim;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_5250;
/*     */ import xaero.map.gui.CursorBox;
/*     */ import xaero.map.misc.Area;
/*     */ import xaero.pac.common.claims.result.api.AreaClaimResult;
/*     */ import xaero.pac.common.claims.result.api.ClaimResult;
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
/*     */ public final class Builder
/*     */ {
/*     */   private Area key;
/*     */   private AreaClaimResult result;
/*     */   
/*     */   private Builder setDefault() {
/*  94 */     setResult(null);
/*  95 */     setKey(null);
/*  96 */     return this;
/*     */   }
/*     */   
/*     */   public Builder setKey(Area key) {
/* 100 */     this.key = key;
/* 101 */     return this;
/*     */   }
/*     */   
/*     */   public Builder setResult(AreaClaimResult result) {
/* 105 */     this.result = result;
/* 106 */     return this;
/*     */   }
/*     */   
/*     */   public ClaimResultElement build() {
/* 110 */     if (this.result == null || this.key == null)
/* 111 */       throw new IllegalStateException(); 
/* 112 */     long time = System.currentTimeMillis();
/* 113 */     class_5250 tooltipText = class_2561.method_43470("");
/* 114 */     boolean hasPositive = false;
/* 115 */     boolean hasNegative = false;
/* 116 */     for (ClaimResult.Type type : this.result.getResultTypesIterable()) {
/* 117 */       if (type.success)
/* 118 */         hasPositive = true; 
/* 119 */       if (type.fail)
/* 120 */         hasNegative = true; 
/* 121 */       if (hasPositive && hasNegative)
/*     */         break; 
/*     */     } 
/* 124 */     List<ClaimResult.Type> filteredResultTypes = new ArrayList<>();
/* 125 */     boolean first = true;
/* 126 */     boolean filteredHasPositive = false;
/* 127 */     boolean filteredHasNegative = false;
/* 128 */     for (ClaimResult.Type type : this.result.getResultTypesIterable()) {
/* 129 */       if (!hasPositive || type.success || type == ClaimResult.Type.TOO_MANY_CHUNKS || type == ClaimResult.Type.TOO_FAR) {
/* 130 */         if (!first)
/* 131 */           tooltipText.method_10855().add(class_2561.method_43470(" \n ")); 
/* 132 */         tooltipText.method_10855().add(type.message);
/* 133 */         filteredResultTypes.add(type);
/* 134 */         first = false;
/* 135 */         if (type.success)
/* 136 */           filteredHasPositive = true; 
/* 137 */         if (type.fail)
/* 138 */           filteredHasNegative = true; 
/*     */       } 
/*     */     } 
/* 141 */     CursorBox tooltip = new CursorBox((class_2561)tooltipText);
/*     */     
/* 143 */     return new ClaimResultElement(this.key, this.result, Collections.unmodifiableList(filteredResultTypes), tooltip, time, time, filteredHasPositive, filteredHasNegative);
/*     */   }
/*     */   
/*     */   public static Builder begin() {
/* 147 */     return (new Builder()).setDefault();
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\mods\pac\gui\claim\ClaimResultElement$Builder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */