package gameComponents.Ships;

import BBDGameLibrary.GameEngine.MouseInput;
import BBDGameLibrary.Geometry2d.BBDPoint;
import BBDGameLibrary.Geometry2d.BBDPolygon;
import BBDGameLibrary.OpenGL.Texture;
import BBDGameLibrary.OpenGL.Window;
import engine.DoodleWarsGame;
import engine.Utils;
import gameComponents.GameValues;
import gameComponents.NotShips.Flock;
import org.joml.Vector3f;
import org.lwjgl.system.CallbackI;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MediumBulletShip extends BaseShip{
    private static BBDPolygon quad = Utils.buildQuad(GameValues.PLAYER_WIDTH, GameValues.PLAYER_HEIGHT);
    private static Texture texture = new Texture("assets/images/Player.png");

    private PlayerShip player;
    private ArrayList<MediumBulletShip> otherMediums;
    public Flock myFlock;
    public boolean arrived = false;

    public MediumBulletShip(PlayerShip player, Flock flock){
        super(GameValues.EASY_BULLET, quad, texture);
        this.player = player;
        myFlock = flock;
    }

    public float distanceToPlayer(){
        Vector3f playerPos = player.getPosition();
        Vector3f myPos = this.getPosition();
        float dist = playerPos.distanceSquared(myPos);
        return dist;
    }

    public float directionToPlayer(){
        Vector3f playerPos = player.getPosition();
        Vector3f myPos = this.getPosition();
        BBDPoint playerPoint = new BBDPoint(playerPos.x, playerPos.y);
        BBDPoint myPoint = new BBDPoint(myPos.x, myPos.y);
        return myPoint.angleToOtherPoint(playerPoint);
    }

    @Override
    public void update(float interval,MouseInput mouseInput, Window window){

        arrived = this.moveTowardTarget(interval, 0.6f * this.getMaxSpeed());

        // TODO logic for firing

    }
}
