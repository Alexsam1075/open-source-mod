/*    */ package xaero.map.radar.tracker;
/*    */ 
/*    */ import java.util.UUID;
/*    */ import net.minecraft.class_1937;
/*    */ import net.minecraft.class_5321;
/*    */ import xaero.map.animation.SlowingAnimation;
/*    */ import xaero.map.radar.tracker.system.IPlayerTrackerSystem;
/*    */ 
/*    */ 
/*    */ public class PlayerTrackerMapElement<P>
/*    */ {
/*    */   private P player;
/*    */   private IPlayerTrackerSystem<P> system;
/*    */   private SlowingAnimation fadeAnim;
/*    */   private boolean renderedOnRadar;
/*    */   
/*    */   public PlayerTrackerMapElement(P player, IPlayerTrackerSystem<P> system) {
/* 18 */     this.player = player;
/* 19 */     this.system = system;
/*    */   }
/*    */   
/*    */   public UUID getPlayerId() {
/* 23 */     return this.system.getReader().getId(this.player);
/*    */   }
/*    */   
/*    */   public double getX() {
/* 27 */     return this.system.getReader().getX(this.player);
/*    */   }
/*    */   
/*    */   public double getY() {
/* 31 */     return this.system.getReader().getY(this.player);
/*    */   }
/*    */   
/*    */   public double getZ() {
/* 35 */     return this.system.getReader().getZ(this.player);
/*    */   }
/*    */   
/*    */   public class_5321<class_1937> getDimension() {
/* 39 */     return this.system.getReader().getDimension(this.player);
/*    */   }
/*    */   
/*    */   public P getPlayer() {
/* 43 */     return this.player;
/*    */   }
/*    */   
/*    */   public void setRenderedOnRadar(boolean renderedOnRadar) {
/* 47 */     this.renderedOnRadar = renderedOnRadar;
/*    */   }
/*    */   
/*    */   public boolean wasRenderedOnRadar() {
/* 51 */     return this.renderedOnRadar;
/*    */   }
/*    */   
/*    */   public SlowingAnimation getFadeAnim() {
/* 55 */     return this.fadeAnim;
/*    */   }
/*    */   
/*    */   public void setFadeAnim(SlowingAnimation fadeAnim) {
/* 59 */     this.fadeAnim = fadeAnim;
/*    */   }
/*    */   
/*    */   public IPlayerTrackerSystem<P> getSystem() {
/* 63 */     return this.system;
/*    */   }
/*    */ }


/* Location:              C:\Users\PC\Downloads\xaero-map-viewer\XaerosWorldMap_1.39.17_Fabric_1.21.9.jar!\xaero\map\radar\tracker\PlayerTrackerMapElement.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */