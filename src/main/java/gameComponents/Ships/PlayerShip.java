package gameComponents.Ships;

import BBDGameLibrary.GameEngine.GameItem2d;
import BBDGameLibrary.Geometry2d.BBDPolygon;
import BBDGameLibrary.OpenGL.Mesh;
import BBDGameLibrary.OpenGL.ShaderProgram;
import BBDGameLibrary.OpenGL.Window;
import gameComponents.GameValues;
import org.joml.Matrix4f;

import static org.lwjgl.glfw.GLFW.*;

public class PlayerShip extends GameItem2d {

    private float acceleration = 0;
    private float speed = 0;
    private float shipRotationRate = 0;

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
    }

    @Override
    public void update(float interval){

        this.rotate(interval * this.shipRotationRate);

        speed += this.acceleration * interval;
        speed = Math.max(0, Math.min(speed, GameValues.PLAYER_MAX_SPEED));

        this.translate((float)Math.sin(this.getRotation().z) * speed,
                (float)Math.cos(this.getRotation().z) *speed);
    }


    @Override
    public void setUniforms(Matrix4f projectionMatrix, Matrix4f modelViewMatrix) {
        this.shader.setUniform("projectionMatrix", projectionMatrix);
        this.shader.setUniform("modelViewMatrix", modelViewMatrix);
        this.shader.setUniform("texture_sampler", 0);
    }
}
