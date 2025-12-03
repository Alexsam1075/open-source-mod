/*     */ package xaero.map.mods;
/*     */ 
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.minecraft.class_1937;
/*     */ import net.minecraft.class_2248;
/*     */ import net.minecraft.class_2378;
/*     */ import net.minecraft.class_2586;
/*     */ import net.minecraft.class_2680;
/*     */ import net.minecraft.class_5321;
/*     */ import net.minecraft.class_7924;
/*     */ import xaero.map.WorldMap;
/*     */ import xaero.map.misc.Misc;
/*     */ 
/*     */ public class SupportFramedBlocks
/*     */ {
/*     */   private Class<?> framedTileBlockClass;
/*     */   private Method framedTileEntityCamoStateMethod;
/*     */   private Method framedTileEntityCamoMethod;
/*     */   private Method camoContainerStateMethod;
/*     */   private Method camoContainerContentMethod;
/*     */   private Method camoContentStateMethod;
/*     */   private boolean usable;
/*     */   private Set<class_2248> framedBlocks;
/*     */   
/*     */   public SupportFramedBlocks() {
/*     */     try {
/*  30 */       this.framedTileBlockClass = Class.forName("xfacthd.framedblocks.api.block.blockentity.FramedBlockEntity");
/*  31 */     } catch (ClassNotFoundException cnfe) {
/*     */       try {
/*  33 */         this.framedTileBlockClass = Class.forName("xfacthd.framedblocks.common.tileentity.FramedTileEntity");
/*  34 */       } catch (ClassNotFoundException cnfe2) {
/*     */         try {
/*  36 */           this.framedTileBlockClass = Class.forName("xfacthd.framedblocks.api.block.FramedBlockEntity");
/*  37 */         } catch (ClassNotFoundException cnfe3) {
/*  38 */           WorldMap.LOGGER.info("Failed to init Framed Blocks support!", cnfe3);
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     try {
/*  44 */       this.framedTileEntityCamoStateMethod = this.framedTileBlockClass.getDeclaredMethod("getCamoState", new Class[0]);
/*  45 */     } catch (NoSuchMethodException|SecurityException e1) {
/*     */       try {
/*     */         Class<?> camoContainerClass;
/*     */         try {
/*  49 */           camoContainerClass = Class.forName("xfacthd.framedblocks.api.data.CamoContainer");
/*  50 */         } catch (ClassNotFoundException cnfe) {
/*  51 */           camoContainerClass = Class.forName("xfacthd.framedblocks.api.camo.CamoContainer");
/*     */         } 
/*  53 */         this.framedTileEntityCamoMethod = this.framedTileBlockClass.getDeclaredMethod("getCamo", new Class[0]);
/*     */         try {
/*  55 */           this.camoContainerStateMethod = camoContainerClass.getDeclaredMethod("getState", new Class[0]);
/*  56 */         } catch (NoSuchMethodException|SecurityException e2) {
/*  57 */           this.camoContainerContentMethod = camoContainerClass.getDeclaredMethod("getContent", new Class[0]);
/*  58 */           Class<?> camoContentClass = Class.forName("xfacthd.framedblocks.api.camo.CamoContent");
/*  59 */           this.camoContentStateMethod = camoContentClass.getDeclaredMethod("getAppearanceState", new Class[0]);
/*     */         } 
/*  61 */       } catch (ClassNotFoundException|NoSuchMethodException|SecurityException e2) {
/*  62 */         WorldMap.LOGGER.info("Failed to init Framed Blocks support!", e1);
/*  63 */         WorldMap.LOGGER.info("Failed to init Framed Blocks support!", e2);
/*     */       } 
/*     */     } 
/*  66 */     this.usable = (this.framedTileBlockClass != null && (this.framedTileEntityCamoStateMethod != null || (this.framedTileEntityCamoMethod != null && (this.camoContainerStateMethod != null || (this.camoContainerContentMethod != null && this.camoContentStateMethod != null)))));
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
/*     */   public void onWorldChange() {
/*  78 */     this.framedBlocks = null;
/*     */   }
/*     */   
/*     */   private void findFramedBlocks(class_1937 world, class_2378<class_2248> registry) {
/*  82 */     if (this.framedBlocks == null) {
/*  83 */       this.framedBlocks = new HashSet<>();
/*  84 */       if (registry == null)
/*  85 */         registry = world.method_30349().method_30530(class_7924.field_41254); 
/*  86 */       registry.method_29722().forEach(entry -> {
/*     */             class_5321<class_2248> key = (class_5321<class_2248>)entry.getKey();
/*     */             if (key.method_29177().method_12836().equals("framedblocks") && key.method_29177().method_12832().startsWith("framed_")) {
/*     */               this.framedBlocks.add((class_2248)entry.getValue());
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isFrameBlock(class_1937 world, class_2378<class_2248> registry, class_2680 state) {
/*  96 */     if (!this.usable)
/*  97 */       return false; 
/*  98 */     findFramedBlocks(world, registry);
/*  99 */     return this.framedBlocks.contains(state.method_26204());
/*     */   }
/*     */   
/*     */   public class_2680 unpackFramedBlock(class_1937 world, class_2378<class_2248> registry, class_2680 original, class_2586 tileEntity) {
/* 103 */     if (!this.usable)
/* 104 */       return original; 
/* 105 */     if (this.framedTileBlockClass.isAssignableFrom(tileEntity.getClass())) {
/* 106 */       if (this.framedTileEntityCamoStateMethod != null)
/* 107 */         return (class_2680)Misc.getReflectMethodValue(tileEntity, this.framedTileEntityCamoStateMethod, new Object[0]); 
/* 108 */       Object camoContainer = Misc.getReflectMethodValue(tileEntity, this.framedTileEntityCamoMethod, new Object[0]);
/* 109 */       if (this.camoContainerStateMethod != null)
/* 110 */         return (class_2680)Misc.getReflectMethodValue(camoContainer, this.camoContainerStateMethod, new Object[0]); 
/* 111 */       Object camoContent = Misc.getReflectMethodValue(camoContainer, this.camoContainerContentMethod, new Object[0]);
/* 112 */       if (camoContent == null)
/* 113 */         return original; 
/* 114 */       class_2680 state = (class_2680)Misc.getReflectMethodValue(camoContent, this.camoContentStateMethod, new Object[0]);
/* 115 */       if (state == null)
/* 116 */         return original; 
/* 117 */       return state;
/*     */     } 
/* 119 */     return original;
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\mods\SupportFramedBlocks.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */