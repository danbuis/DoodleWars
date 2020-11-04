package gameComponents.Ships;

import BBDGameLibrary.GameEngine.GameItem2d;
import BBDGameLibrary.Geometry2d.BBDPolygon;
import BBDGameLibrary.OpenGL.Mesh;
import BBDGameLibrary.OpenGL.ShaderProgram;
import BBDGameLibrary.OpenGL.Window;
import gameComponents.NotShips.Bullet;
import gameComponents.GameValues;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class PlayerShip extends GameItem2d {

    private float acceleration = 0;
    private float speed = 0;
    private float shipRotationRate = 0;
    private float cooldown = 0;
    private boolean fireBulletsBool = false;

    public PlayerShip(Mesh mesh, ShaderProgram shaderProgram, BBDPolygon shape, int layer, boolean shapeInteracts) {
        super(mesh, shaderProgram, shape, layer, shapeInteracts);
    }

    @Override
    public void input(Window window){
        if(window.isKeyPressed(GLFW_KEY_UP)){
            this.acceleration = GameValues.PLAYER_ACCELERATION;
        }else if (window.isKeyPressed(GLFW_KEY_DOWN)){
            this.acceleration = -GameValues.PLAYER_ACCELERATION;
        }else{
            this.acceleration = 0;
        }

        if(window.isKeyPressed(GLFW_KEY_LEFT)){
            this.shipRotationRate = -GameValues.PLAYER_TURN_RATE;
        }else if (window.isKeyPressed(GLFW_KEY_RIGHT)){
            this.shipRotationRate = GameValues.PLAYER_TURN_RATE;
        }else{
            this.shipRotationRate = 0;
        }

        fireBulletsBool = window.isKeyPressed(GLFW_KEY_SPACE);
    }

    @Override
    public void update(float interval){

        //handle movement updates
        this.rotate(interval * this.shipRotationRate);

        speed += this.acceleration * interval;
        speed = Math.max(0, Math.min(speed, GameValues.PLAYER_MAX_SPEED));

        this.translate((float)Math.sin(this.getRotation().z) * speed * interval,
                (float)Math.cos(this.getRotation().z) *speed * interval);

        //handle shooting bullet updates
        if (this.cooldown > 0){
            this.cooldown -= interval;
        }
        if(fireBulletsBool && this.cooldown <= 0){
            this.cooldown = GameValues.PLAYER_BULLET_COOLDOWN;

            Bullet bullet = new Bullet(this, GameValues.PLAYER_BULLET_BASE_DAMAGE);
            Vector3f thisPosition = this.getPosition();
            bullet.setPosition(thisPosition.x, thisPosition.y);
        }
    }

    @Override
    public void setUniforms(Matrix4f projectionMatrix, Matrix4f modelViewMatrix) {
        this.shader.setUniform("projectionMatrix", projectionMatrix);
        this.shader.setUniform("modelViewMatrix", modelViewMatrix);
        this.shader.setUniform("texture_sampler", 0);
    }
}
