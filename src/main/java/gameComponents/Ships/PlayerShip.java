package gameComponents.Ships;

import BBDGameLibrary.GameEngine.GameItem;
import BBDGameLibrary.GameEngine.GameItem2d;
import BBDGameLibrary.GameEngine.MouseInput;
import BBDGameLibrary.Geometry2d.BBDPolygon;
import BBDGameLibrary.OpenGL.Mesh;
import BBDGameLibrary.OpenGL.ShaderProgram;
import BBDGameLibrary.OpenGL.Window;
import engine.DoodleWarsGame;
import engine.Utils;
import gameComponents.Equipment.Shooter;
import gameComponents.NotShips.Asteroid;
import gameComponents.NotShips.Bullet;
import gameComponents.GameValues;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;

public class PlayerShip extends GameItem2d {

    private float acceleration = 0;
    private float speed = 0;
    private float shipRotationRate = 0;
    private float cooldown = 0;
    private boolean fireBulletsBool = false;
    private float maxHull = GameValues.PLAYER_HULL_BASE;
    private float maxShields = GameValues.PLAYER_SHIELDS_BASE;
    private float currentHull = GameValues.PLAYER_HULL_BASE;
    private float currentShields = GameValues.PLAYER_SHIELDS_BASE;
    private ArrayList<Shooter> guns = new ArrayList<Shooter>();

    public PlayerShip(Mesh mesh, ShaderProgram shaderProgram, BBDPolygon shape, int layer, boolean shapeInteracts) {
        super(mesh, shaderProgram, shape, layer, shapeInteracts);
        guns.add(new Shooter(GameValues.PLAYER_BULLET_COOLDOWN, 0, 1, this, new Vector2f(GameValues.PLAYER_WIDTH/2,0)));
    }

    public void takeDamage(float damage){
        if (damage < currentShields){
            currentShields -= damage;
        }else{
            damage -= currentShields;
            currentShields = 0;
            currentHull -= damage;
        }
    }

    @Override
    public void input(Window window, MouseInput mouseInput){
        if(window.isKeyPressed(GLFW_KEY_UP)){
            this.acceleration = GameValues.PLAYER_ACCELERATION;
        }else if (window.isKeyPressed(GLFW_KEY_DOWN)){
            this.acceleration = -GameValues.PLAYER_ACCELERATION;
        }else{
            this.acceleration = 0;
        }

        if(window.isKeyPressed(GLFW_KEY_LEFT)){
            this.shipRotationRate = GameValues.PLAYER_TURN_RATE;
        }else if (window.isKeyPressed(GLFW_KEY_RIGHT)){
            this.shipRotationRate = -GameValues.PLAYER_TURN_RATE;
        }else{
            this.shipRotationRate = 0;
        }

        fireBulletsBool = window.isKeyPressed(GLFW_KEY_SPACE);
    }

    @Override
    public void update(float interval, MouseInput mouseInput, Window window){
        //handle movement updates
        this.rotate(interval * -this.shipRotationRate);

        speed += this.acceleration * interval;
        speed = Math.max(0, Math.min(speed, GameValues.PLAYER_MAX_SPEED));

        Utils.translateEntity(this, speed, interval);

        for (Shooter gun: guns){
            gun.update(interval);

            if(fireBulletsBool){
                gun.shoot();
            }
        }

        currentShields += GameValues.PLAYER_SHIELD_REGEN * interval;
        currentShields = Math.min(currentShields, this.maxShields);
    }

    @Override
    public void setUniforms(Matrix4f projectionMatrix, Matrix4f modelViewMatrix) {
        this.shader.setUniform("projectionMatrix", projectionMatrix);
        this.shader.setUniform("modelViewMatrix", modelViewMatrix);
        this.shader.setUniform("texture_sampler", 0);
    }
}
