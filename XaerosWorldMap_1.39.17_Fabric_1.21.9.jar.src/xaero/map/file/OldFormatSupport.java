/*     */ package xaero.map.file;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.class_2246;
/*     */ import net.minecraft.class_2487;
/*     */ import net.minecraft.class_2507;
/*     */ import net.minecraft.class_2519;
/*     */ import net.minecraft.class_2520;
/*     */ import net.minecraft.class_2680;
/*     */ import net.minecraft.class_2960;
/*     */ import net.minecraft.class_7225;
/*     */ import net.minecraft.class_7923;
/*     */ import xaero.map.MapProcessor;
/*     */ import xaero.map.WorldMap;
/*     */ 
/*     */ public class OldFormatSupport {
/*     */   private class_2960 vanillaStatesResource;
/*     */   private boolean vanillaStatesLoaded;
/*     */   private HashMap<Integer, HashMap<Integer, class_2680>> vanilla_states;
/*     */   private ImmutableMap<String, String> blockRename1314;
/*     */   private ImmutableMap<String, Fixer> blockFixes1516;
/*     */   private ImmutableMap<String, Fixer> blockFixes1617;
/*     */   private ImmutableMap<String, Fixer> blockFixes1_21_5;
/*  34 */   private Map<String, String> JIGSAW_ORIENTATION_UPDATES16 = (Map<String, String>)ImmutableMap.builder().put("", "north_up").put("down", "down_south").put("up", "up_north").put("north", "north_up").put("south", "south_up").put("west", "west_up").put("east", "east_up").build(); private Fixer wallFix; public ImmutableMap<String, String> cc2BiomeRenames; private Int2ObjectMap<String> biomesById; public OldFormatSupport() {
/*  35 */     this.wallFix = (nbt -> {
/*     */         class_2487 properties = nbt.method_68568("Properties");
/*     */         properties.method_10582("east", properties.method_10558("east").equals("true") ? "low" : "none");
/*     */         properties.method_10582("west", properties.method_10558("west").equals("true") ? "low" : "none");
/*     */         properties.method_10582("north", properties.method_10558("north").equals("true") ? "low" : "none");
/*     */         properties.method_10582("south", properties.method_10558("south").equals("true") ? "low" : "none");
/*     */       });
/*  42 */     this.cc2BiomeRenames = ImmutableMap.builder().put("minecraft:badlands_plateau", "minecraft:badlands").put("minecraft:bamboo_jungle_hills", "minecraft:bamboo_jungle").put("minecraft:birch_forest_hills", "minecraft:birch_forest").put("minecraft:dark_forest_hills", "minecraft:dark_forest").put("minecraft:desert_hills", "minecraft:desert").put("minecraft:desert_lakes", "minecraft:desert").put("minecraft:giant_spruce_taiga_hills", "minecraft:old_growth_spruce_taiga").put("minecraft:giant_spruce_taiga", "minecraft:old_growth_spruce_taiga").put("minecraft:giant_tree_taiga_hills", "minecraft:old_growth_pine_taiga").put("minecraft:giant_tree_taiga", "minecraft:old_growth_pine_taiga").put("minecraft:gravelly_mountains", "minecraft:windswept_gravelly_hills").put("minecraft:jungle_edge", "minecraft:sparse_jungle").put("minecraft:jungle_hills", "minecraft:jungle").put("minecraft:modified_badlands_plateau", "minecraft:badlands").put("minecraft:modified_gravelly_mountains", "minecraft:windswept_gravelly_hills").put("minecraft:modified_jungle_edge", "minecraft:sparse_jungle").put("minecraft:modified_jungle", "minecraft:jungle").put("minecraft:modified_wooded_badlands_plateau", "minecraft:wooded_badlands").put("minecraft:mountain_edge", "minecraft:windswept_hills").put("minecraft:mountains", "minecraft:windswept_hills").put("minecraft:mushroom_field_shore", "minecraft:mushroom_fields").put("minecraft:shattered_savanna", "minecraft:windswept_savanna").put("minecraft:shattered_savanna_plateau", "minecraft:windswept_savanna").put("minecraft:snowy_mountains", "minecraft:snowy_plains").put("minecraft:snowy_taiga_hills", "minecraft:snowy_taiga").put("minecraft:snowy_taiga_mountains", "minecraft:snowy_taiga").put("minecraft:snowy_tundra", "minecraft:snowy_plains").put("minecraft:stone_shore", "minecraft:stony_shore").put("minecraft:swamp_hills", "minecraft:swamp").put("minecraft:taiga_hills", "minecraft:taiga").put("minecraft:taiga_mountains", "minecraft:taiga").put("minecraft:tall_birch_forest", "minecraft:old_growth_birch_forest").put("minecraft:tall_birch_hills", "minecraft:old_growth_birch_forest").put("minecraft:wooded_badlands_plateau", "minecraft:wooded_badlands").put("minecraft:wooded_hills", "minecraft:forest").put("minecraft:wooded_mountains", "minecraft:windswept_forest").put("minecraft:lofty_peaks", "minecraft:jagged_peaks").put("minecraft:snowcapped_peaks", "minecraft:frozen_peaks").build();
/*     */ 
/*     */     
/*  45 */     this.biomesById = (Int2ObjectMap<String>)new Int2ObjectOpenHashMap();
/*     */ 
/*     */     
/*  48 */     this.vanillaStatesResource = class_2960.method_60655("xaeroworldmap", "vanilla_states.dat");
/*  49 */     this.vanilla_states = new HashMap<>();
/*  50 */     this.blockRename1314 = ImmutableMap.of("minecraft:stone_slab", "minecraft:smooth_stone_slab", "minecraft:sign", "minecraft:oak_sign", "minecraft:wall_sign", "minecraft:oak_wall_sign");
/*  51 */     this
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
/* 101 */       .blockFixes1516 = ImmutableMap.builder().put("minecraft:jigsaw", nbt -> { class_2487 properties = nbt.method_68568("Properties"); String facing = properties.method_10558("facing").orElse(null); properties.method_10551("facing"); properties.method_10582("orientation", this.JIGSAW_ORIENTATION_UPDATES16.get(facing)); }).put("minecraft:redstone_wire", nbt -> { class_2487 properties = nbt.method_68568("Properties"); String east = properties.method_10558("east").orElse(null); String west = properties.method_10558("west").orElse(null); String north = properties.method_10558("north").orElse(null); String south = properties.method_10558("south").orElse(null); if (east.equals("")) east = "none";  if (west.equals("")) west = "none";  if (north.equals("")) north = "none";  if (south.equals("")) south = "none";  boolean hasEast = !east.equals("none"); boolean hasWest = !west.equals("none"); boolean hasNorth = !north.equals("none"); boolean hasSouth = !south.equals("none"); boolean hasHorizontal = (hasWest || hasEast); boolean hasVertical = (hasNorth || hasSouth); east = (!hasEast && !hasVertical) ? "side" : east; west = (!hasWest && !hasVertical) ? "side" : west; north = (!hasNorth && !hasHorizontal) ? "side" : north; south = (!hasSouth && !hasHorizontal) ? "side" : south; properties.method_10582("east", east); properties.method_10582("west", west); properties.method_10582("north", north); properties.method_10582("south", south); }).put("minecraft:andesite_wall", this.wallFix).put("minecraft:brick_wall", this.wallFix).put("minecraft:cobblestone_wall", this.wallFix).put("minecraft:diorite_wall", this.wallFix).put("minecraft:end_stone_brick_wall", this.wallFix).put("minecraft:granite_wall", this.wallFix).put("minecraft:mossy_cobblestone_wall", this.wallFix).put("minecraft:mossy_stone_brick_wall", this.wallFix).put("minecraft:nether_brick_wall", this.wallFix).put("minecraft:prismarine_wall", this.wallFix).put("minecraft:red_nether_brick_wall", this.wallFix).put("minecraft:red_sandstone_wall", this.wallFix).put("minecraft:sandstone_wall", this.wallFix).put("minecraft:stone_brick_wall", this.wallFix).build();
/* 102 */     this
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
/* 116 */       .blockFixes1617 = ImmutableMap.builder().put("minecraft:cauldron", nbt -> { class_2487 properties = nbt.method_68568("Properties"); if (!properties.method_33133()) { class_2520 level = properties.method_10580("level"); if (level == null || level.toString().equals("0") || level.toString().equals("0b")) { nbt.method_10551("Properties"); } else { nbt.method_10566("Name", (class_2520)class_2519.method_23256("minecraft:water_cauldron")); }  }  }).put("minecraft:grass_path", nbt -> nbt.method_10566("Name", (class_2520)class_2519.method_23256("minecraft:dirt_path"))).build();
/* 117 */     this
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
/* 128 */       .blockFixes1_21_5 = ImmutableMap.builder().put("minecraft:creaking_heart", nbt -> { class_2487 properties = nbt.method_68568("Properties"); if (properties.method_33133()) return;  String active = properties.method_10558("active").orElse(null); if (active == null) return;  properties.method_10551("active"); properties.method_10582("creaking_heart_state", active.equals("true") ? "awake" : "uprooted"); }).build();
/*     */ 
/*     */     
/* 131 */     this.biomesById.put(0, "minecraft:ocean");
/* 132 */     this.biomesById.put(1, "minecraft:plains");
/* 133 */     this.biomesById.put(2, "minecraft:desert");
/* 134 */     this.biomesById.put(3, "minecraft:mountains");
/* 135 */     this.biomesById.put(4, "minecraft:forest");
/* 136 */     this.biomesById.put(5, "minecraft:taiga");
/* 137 */     this.biomesById.put(6, "minecraft:swamp");
/* 138 */     this.biomesById.put(7, "minecraft:river");
/* 139 */     this.biomesById.put(8, "minecraft:nether_wastes");
/* 140 */     this.biomesById.put(9, "minecraft:the_end");
/* 141 */     this.biomesById.put(10, "minecraft:frozen_ocean");
/* 142 */     this.biomesById.put(11, "minecraft:frozen_river");
/* 143 */     this.biomesById.put(12, "minecraft:snowy_tundra");
/* 144 */     this.biomesById.put(13, "minecraft:snowy_mountains");
/* 145 */     this.biomesById.put(14, "minecraft:mushroom_fields");
/* 146 */     this.biomesById.put(15, "minecraft:mushroom_field_shore");
/* 147 */     this.biomesById.put(16, "minecraft:beach");
/* 148 */     this.biomesById.put(17, "minecraft:desert_hills");
/* 149 */     this.biomesById.put(18, "minecraft:wooded_hills");
/* 150 */     this.biomesById.put(19, "minecraft:taiga_hills");
/* 151 */     this.biomesById.put(20, "minecraft:mountain_edge");
/* 152 */     this.biomesById.put(21, "minecraft:jungle");
/* 153 */     this.biomesById.put(22, "minecraft:jungle_hills");
/* 154 */     this.biomesById.put(23, "minecraft:jungle_edge");
/* 155 */     this.biomesById.put(24, "minecraft:deep_ocean");
/* 156 */     this.biomesById.put(25, "minecraft:stone_shore");
/* 157 */     this.biomesById.put(26, "minecraft:snowy_beach");
/* 158 */     this.biomesById.put(27, "minecraft:birch_forest");
/* 159 */     this.biomesById.put(28, "minecraft:birch_forest_hills");
/* 160 */     this.biomesById.put(29, "minecraft:dark_forest");
/* 161 */     this.biomesById.put(30, "minecraft:snowy_taiga");
/* 162 */     this.biomesById.put(31, "minecraft:snowy_taiga_hills");
/* 163 */     this.biomesById.put(32, "minecraft:giant_tree_taiga");
/* 164 */     this.biomesById.put(33, "minecraft:giant_tree_taiga_hills");
/* 165 */     this.biomesById.put(34, "minecraft:wooded_mountains");
/* 166 */     this.biomesById.put(35, "minecraft:savanna");
/* 167 */     this.biomesById.put(36, "minecraft:savanna_plateau");
/* 168 */     this.biomesById.put(37, "minecraft:badlands");
/* 169 */     this.biomesById.put(38, "minecraft:wooded_badlands_plateau");
/* 170 */     this.biomesById.put(39, "minecraft:badlands_plateau");
/* 171 */     this.biomesById.put(40, "minecraft:small_end_islands");
/* 172 */     this.biomesById.put(41, "minecraft:end_midlands");
/* 173 */     this.biomesById.put(42, "minecraft:end_highlands");
/* 174 */     this.biomesById.put(43, "minecraft:end_barrens");
/* 175 */     this.biomesById.put(44, "minecraft:warm_ocean");
/* 176 */     this.biomesById.put(45, "minecraft:lukewarm_ocean");
/* 177 */     this.biomesById.put(46, "minecraft:cold_ocean");
/* 178 */     this.biomesById.put(47, "minecraft:deep_warm_ocean");
/* 179 */     this.biomesById.put(48, "minecraft:deep_lukewarm_ocean");
/* 180 */     this.biomesById.put(49, "minecraft:deep_cold_ocean");
/* 181 */     this.biomesById.put(50, "minecraft:deep_frozen_ocean");
/* 182 */     this.biomesById.put(127, "minecraft:the_void");
/* 183 */     this.biomesById.put(129, "minecraft:sunflower_plains");
/* 184 */     this.biomesById.put(130, "minecraft:desert_lakes");
/* 185 */     this.biomesById.put(131, "minecraft:gravelly_mountains");
/* 186 */     this.biomesById.put(132, "minecraft:flower_forest");
/* 187 */     this.biomesById.put(133, "minecraft:taiga_mountains");
/* 188 */     this.biomesById.put(134, "minecraft:swamp_hills");
/* 189 */     this.biomesById.put(140, "minecraft:ice_spikes");
/* 190 */     this.biomesById.put(149, "minecraft:modified_jungle");
/* 191 */     this.biomesById.put(151, "minecraft:modified_jungle_edge");
/* 192 */     this.biomesById.put(155, "minecraft:tall_birch_forest");
/* 193 */     this.biomesById.put(156, "minecraft:tall_birch_hills");
/* 194 */     this.biomesById.put(157, "minecraft:dark_forest_hills");
/* 195 */     this.biomesById.put(158, "minecraft:snowy_taiga_mountains");
/* 196 */     this.biomesById.put(160, "minecraft:giant_spruce_taiga");
/* 197 */     this.biomesById.put(161, "minecraft:giant_spruce_taiga_hills");
/* 198 */     this.biomesById.put(162, "minecraft:modified_gravelly_mountains");
/* 199 */     this.biomesById.put(163, "minecraft:shattered_savanna");
/* 200 */     this.biomesById.put(164, "minecraft:shattered_savanna_plateau");
/* 201 */     this.biomesById.put(165, "minecraft:eroded_badlands");
/* 202 */     this.biomesById.put(166, "minecraft:modified_wooded_badlands_plateau");
/* 203 */     this.biomesById.put(167, "minecraft:modified_badlands_plateau");
/* 204 */     this.biomesById.put(168, "minecraft:bamboo_jungle");
/* 205 */     this.biomesById.put(169, "minecraft:bamboo_jungle_hills");
/* 206 */     this.biomesById.put(170, "minecraft:soul_sand_valley");
/* 207 */     this.biomesById.put(171, "minecraft:crimson_forest");
/* 208 */     this.biomesById.put(172, "minecraft:warped_forest");
/* 209 */     this.biomesById.put(173, "minecraft:basalt_deltas");
/* 210 */     this.biomesById.put(174, "minecraft:dripstone_caves");
/* 211 */     this.biomesById.put(175, "minecraft:lush_caves");
/* 212 */     this.biomesById.put(177, "minecraft:meadow");
/* 213 */     this.biomesById.put(178, "minecraft:grove");
/* 214 */     this.biomesById.put(179, "minecraft:snowy_slopes");
/* 215 */     this.biomesById.put(180, "minecraft:snowcapped_peaks");
/* 216 */     this.biomesById.put(181, "minecraft:lofty_peaks");
/* 217 */     this.biomesById.put(182, "minecraft:stony_peaks");
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadVanillaStates() throws IOException, CommandSyntaxException {
/* 222 */     if (WorldMap.settings.debug) {
/* 223 */       WorldMap.LOGGER.info("Loading vanilla states...");
/*     */     }
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
/* 247 */     loadStates(this.vanilla_states, ((class_3298)class_310.method_1551().method_1478().method_14486(this.vanillaStatesResource).get()).method_14482());
/* 248 */     this.vanillaStatesLoaded = true;
/*     */   }
/*     */   
/*     */   public void loadModdedStates(MapProcessor mapProcessor, String worldId, String dimId, String mwId) throws FileNotFoundException, IOException, CommandSyntaxException {
/* 252 */     if (worldId == null)
/*     */       return; 
/* 254 */     if (WorldMap.settings.debug) {
/* 255 */       WorldMap.LOGGER.info("Loading modded states for the world...");
/*     */     }
/*     */     
/* 258 */     Path mainFolder = mapProcessor.getMapSaveLoad().getMainFolder(worldId, dimId);
/* 259 */     Path subFolder = mapProcessor.getMapSaveLoad().getMWSubFolder(worldId, mainFolder, mwId);
/* 260 */     Path inputPath = subFolder.resolve("states.dat");
/* 261 */     if (!Files.exists(subFolder, new java.nio.file.LinkOption[0])) {
/* 262 */       Files.createDirectories(subFolder, (FileAttribute<?>[])new FileAttribute[0]);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void loadStates(HashMap<Integer, HashMap<Integer, class_2680>> stateMap, InputStream inputStream) throws IOException, CommandSyntaxException {
/* 328 */     DataInputStream input = new DataInputStream(new BufferedInputStream(inputStream));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*     */       while (true) {
/* 336 */         int stateId = input.readInt();
/* 337 */         int blockId = stateId & 0xFFF;
/* 338 */         int meta = stateId >> 12 & 0xFFFFF;
/* 339 */         class_2487 nbtCompound = class_2507.method_10627(input);
/* 340 */         fixBlock(nbtCompound, 1);
/* 341 */         class_2680 blockState = WorldMap.unknownBlockStateCache.getBlockStateFromNBT((class_7225)class_7923.field_41175, nbtCompound);
/* 342 */         putState(stateMap, blockId, meta, blockState);
/*     */       }
/*     */     
/* 345 */     } catch (EOFException eOFException) {
/* 346 */       if (WorldMap.settings.debug)
/* 347 */         WorldMap.LOGGER.info("Done."); 
/* 348 */       input.close();
/*     */       return;
/*     */     } 
/*     */   } public void loadStates() throws IOException, CommandSyntaxException {
/* 352 */     if (!this.vanillaStatesLoaded) {
/* 353 */       loadVanillaStates();
/*     */     }
/*     */   }
/*     */   
/*     */   private void putState(HashMap<Integer, HashMap<Integer, class_2680>> stateMap, int blockId, int meta, class_2680 blockState) {
/* 358 */     HashMap<Integer, class_2680> blockStates = stateMap.get(Integer.valueOf(blockId));
/* 359 */     if (blockStates == null)
/* 360 */       stateMap.put(Integer.valueOf(blockId), blockStates = new HashMap<>()); 
/* 361 */     blockStates.put(Integer.valueOf(meta), blockState);
/*     */   }
/*     */   
/*     */   private class_2680 getStateForId(int stateId, HashMap<Integer, HashMap<Integer, class_2680>> stateMap) {
/* 365 */     int blockId = stateId & 0xFFF;
/* 366 */     HashMap<Integer, class_2680> blockStates = stateMap.get(Integer.valueOf(blockId));
/* 367 */     if (blockStates == null) {
/* 368 */       return null;
/*     */     }
/* 370 */     int meta = stateId >> 12 & 0xFFFFF;
/* 371 */     return blockStates.getOrDefault(Integer.valueOf(meta), null);
/*     */   }
/*     */ 
/*     */   
/*     */   public class_2680 getStateForId(int stateId) {
/* 376 */     class_2680 vanillaState = getStateForId(stateId, this.vanilla_states);
/* 377 */     if (vanillaState != null) {
/* 378 */       return vanillaState;
/*     */     }
/*     */     
/* 381 */     return class_2246.field_10124.method_9564();
/*     */   }
/*     */ 
/*     */   
/*     */   public void fixBlock(class_2487 nbt, int version) {
/* 386 */     if (version == 1)
/* 387 */       fixBlockName1314(nbt); 
/* 388 */     if (version < 3)
/* 389 */       fixBlock1516(nbt); 
/* 390 */     if (version < 5)
/* 391 */       fixBlock1617(nbt); 
/* 392 */     if (version < 7)
/* 393 */       fixBlock1215(nbt); 
/*     */   }
/*     */   
/*     */   private void fixBlockName1314(class_2487 nbt) {
/* 397 */     String name = nbt.method_10558("Name").orElse(null);
/* 398 */     nbt.method_10582("Name", (String)this.blockRename1314.getOrDefault(name, name));
/*     */   }
/*     */   
/*     */   private void fixBlock1516(class_2487 nbt) {
/* 402 */     String name = nbt.method_10558("Name").orElse(null);
/* 403 */     Fixer fixer = (Fixer)this.blockFixes1516.get(name);
/* 404 */     if (fixer != null)
/* 405 */       fixer.fix(nbt); 
/*     */   }
/*     */   
/*     */   private void fixBlock1617(class_2487 nbt) {
/* 409 */     String name = nbt.method_10558("Name").orElse(null);
/* 410 */     Fixer fixer = (Fixer)this.blockFixes1617.get(name);
/* 411 */     if (fixer != null)
/* 412 */       fixer.fix(nbt); 
/*     */   }
/*     */   
/*     */   private void fixBlock1215(class_2487 nbt) {
/* 416 */     String name = nbt.method_10558("Name").orElse(null);
/* 417 */     Fixer fixer = (Fixer)this.blockFixes1_21_5.get(name);
/* 418 */     if (fixer != null)
/* 419 */       fixer.fix(nbt); 
/*     */   }
/*     */   
/*     */   public String fixBiome(int id, int version) {
/* 423 */     return fixBiome(id, version, "minecraft:plains");
/*     */   }
/*     */   
/*     */   public String fixBiome(int id, int version, String defaultValue) {
/* 427 */     String biomeStringId = (String)this.biomesById.get(id);
/* 428 */     if (biomeStringId == null)
/* 429 */       biomeStringId = defaultValue; 
/* 430 */     if (biomeStringId == null)
/* 431 */       return null; 
/* 432 */     return fixBiome(biomeStringId, version);
/*     */   }
/*     */   
/*     */   public String fixBiome(String id, int version) {
/* 436 */     if (version < 6)
/* 437 */       return fixBiome1718(id); 
/* 438 */     return id;
/*     */   }
/*     */   
/*     */   private String fixBiome1718(String id) {
/* 442 */     if ("minecraft:deep_warm_ocean".equals(id))
/* 443 */       return "minecraft:warm_ocean"; 
/* 444 */     return (String)this.cc2BiomeRenames.getOrDefault(id, id);
/*     */   }
/*     */   
/*     */   public static interface Fixer {
/*     */     void fix(class_2487 param1class_2487);
/*     */   }
/*     */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\file\OldFormatSupport.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */