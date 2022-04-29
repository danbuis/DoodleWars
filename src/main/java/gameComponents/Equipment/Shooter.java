package gameComponents.Equipment;

import BBDGameLibrary.GameEngine.GameItem2d;
import BBDGameLibrary.Geometry2d.BBDPoint;
import gameComponents.GameValues;
import gameComponents.NotShips.Bullet;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Shooter {
    private float cooldown = 0;
    private float cooldownRemaining = 0;
    private float angleOffset =0;
    private float damageMultiplier =1;
    private boolean ready = true;
    private GameItem2d parent;
    private Vector2f offset;

    public Shooter(float cooldown, float angleOffset, float damageMultiplier, GameItem2d parent, Vector2f offset){
        this.cooldown = cooldown;
        this.angleOffset = angleOffset;
        this.damageMultiplier = damageMultiplier;
        this.parent = parent;
        this.offset = offset;
    }

    public void update(float interval){
        if (this.cooldownRemaining > 0){
            this.cooldownRemaining -= interval;
            ready = true;
        }
    }

    public void shoot(){
        if (this.isReady()){
            this.cooldownRemaining = this.cooldown;
            this.ready = false;
            Bullet bullet = new Bullet(this.parent, GameValues.PLAYER_BULLET_BASE_DAMAGE * this.damageMultiplier);
            Vector3f bulletPosition = parent.getPosition();
            BBDPoint offsetPoint = new BBDPoint(offset.x, offset.y);
            offsetPoint.rotateAroundPoint(new BBDPoint(0,0), -parent.getRotation().z);
            bullet.setPosition(bulletPosition.x + offsetPoint.getXLoc(), bulletPosition.y + offsetPoint.getYLoc());
        }
    }

    public boolean isReady() {return ready;}
}
