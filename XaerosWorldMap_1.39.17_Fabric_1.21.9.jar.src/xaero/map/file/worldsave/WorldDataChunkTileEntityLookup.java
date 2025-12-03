/*    */ package xaero.map.file.worldsave;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*    */ import net.minecraft.class_2487;
/*    */ import net.minecraft.class_2499;
/*    */ import net.minecraft.class_2520;
/*    */ 
/*    */ public class WorldDataChunkTileEntityLookup {
/*    */   private class_2499 tileEntitiesNbt;
/*    */   private Int2ObjectMap<Int2ObjectMap<Int2ObjectMap<class_2487>>> tileEntities;
/*    */   
/*    */   public WorldDataChunkTileEntityLookup(class_2499 tileEntitiesNbt) {
/* 14 */     this.tileEntitiesNbt = tileEntitiesNbt;
/*    */   }
/*    */   
/*    */   private void loadIfNeeded() {
/* 18 */     if (this.tileEntities == null) {
/* 19 */       this.tileEntities = (Int2ObjectMap<Int2ObjectMap<Int2ObjectMap<class_2487>>>)new Int2ObjectOpenHashMap();
/* 20 */       this.tileEntitiesNbt.forEach(tag -> {
/*    */             if (tag instanceof class_2487) {
/*    */               Int2ObjectOpenHashMap int2ObjectOpenHashMap1; Int2ObjectOpenHashMap int2ObjectOpenHashMap2; class_2487 compoundNbt = (class_2487)tag;
/*    */               if (!compoundNbt.method_10545("x"))
/*    */                 return; 
/*    */               int x = ((Integer)compoundNbt.method_10550("x").orElse(Integer.valueOf(0))).intValue() & 0xF;
/*    */               if (!compoundNbt.method_10545("y"))
/*    */                 return; 
/*    */               int y = ((Integer)compoundNbt.method_10550("y").orElse(Integer.valueOf(0))).intValue();
/*    */               if (!compoundNbt.method_10545("z"))
/*    */                 return; 
/*    */               int z = ((Integer)compoundNbt.method_10550("z").orElse(Integer.valueOf(0))).intValue() & 0xF;
/*    */               Int2ObjectMap<Int2ObjectMap<class_2487>> byX = (Int2ObjectMap<Int2ObjectMap<class_2487>>)this.tileEntities.get(x);
/*    */               if (byX == null)
/*    */                 this.tileEntities.put(x, int2ObjectOpenHashMap1 = new Int2ObjectOpenHashMap()); 
/*    */               Int2ObjectMap<class_2487> byY = (Int2ObjectMap<class_2487>)int2ObjectOpenHashMap1.get(y);
/*    */               if (byY == null)
/*    */                 int2ObjectOpenHashMap1.put(y, int2ObjectOpenHashMap2 = new Int2ObjectOpenHashMap()); 
/*    */               int2ObjectOpenHashMap2.put(z, compoundNbt);
/*    */             } 
/*    */           });
/* 41 */       this.tileEntitiesNbt = null;
/*    */     } 
/*    */   }
/*    */   
/*    */   public class_2487 getTileEntityNbt(int x, int y, int z) {
/* 46 */     loadIfNeeded();
/* 47 */     Int2ObjectMap<Int2ObjectMap<class_2487>> byX = (Int2ObjectMap<Int2ObjectMap<class_2487>>)this.tileEntities.get(x);
/* 48 */     if (byX == null)
/* 49 */       return null; 
/* 50 */     Int2ObjectMap<class_2487> byY = (Int2ObjectMap<class_2487>)byX.get(y);
/* 51 */     if (byY == null)
/* 52 */       return null; 
/* 53 */     return (class_2487)byY.get(z);
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\file\worldsave\WorldDataChunkTileEntityLookup.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */