package gameComponents.Ships;

import BBDGameLibrary.GameEngine.GameItem2d;
import BBDGameLibrary.GameEngine.MouseInput;
import BBDGameLibrary.Geometry2d.BBDPoint;
import BBDGameLibrary.Geometry2d.BBDPolygon;
import BBDGameLibrary.OpenGL.Mesh;
import BBDGameLibrary.OpenGL.ShaderProgram;
import BBDGameLibrary.OpenGL.Texture;
import BBDGameLibrary.OpenGL.Window;
import engine.Utils;
import gameComponents.GameValues;
import org.joml.Vector3f;

public class EasyBulletShip extends BaseShip{
    private static BBDPolygon quad = Utils.buildQuad(GameValues.PLAYER_WIDTH, GameValues.PLAYER_HEIGHT);
    private static Texture texture = new Texture("assets/images/BasicBullet.png");

    private PlayerShip player;
    private boolean playerFound = false;

    public EasyBulletShip(PlayerShip player){
        super(GameValues.EASY_BULLET, quad, texture);
        this.player = player;
        this.targetPoint = this.roombaSearch();
        System.out.println("Initial target : " + this.targetPoint);
    }

    @Override
    public void update(float interval,MouseInput mouseInput, Window window){
        //if not close to player, move forward in roomba search
        Vector3f playerPos = player.getPosition();
        Vector3f myPos = this.getPosition();

        float dist = playerPos.distanceSquared(myPos);

        //logic to handle if player in detection range
        if (dist < 64){
            this.targetPoint = new BBDPoint(playerPos.x, playerPos.y);
            if (!playerFound){
                playerFound=true;
            }
        }else{
            if (playerFound){
                playerFound=false;
            }
        }

        boolean arrived = this.moveTowardTarget(interval, 0.6f * this.getMaxSpeed());

        // TODO logic for firing

        if (arrived && !playerFound){
            this.targetPoint = this.roombaSearch();
        }

    }
}
