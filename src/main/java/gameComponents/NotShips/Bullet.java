package gameComponents.NotShips;

import BBDGameLibrary.GameEngine.GameItem2d;
import BBDGameLibrary.GameEngine.MouseInput;
import BBDGameLibrary.Geometry2d.BBDPolygon;
import BBDGameLibrary.OpenGL.Mesh;
import BBDGameLibrary.OpenGL.Texture;
import BBDGameLibrary.OpenGL.Window;
import engine.DoodleWarsGame;
import engine.Utils;
import gameComponents.GameValues;
import org.joml.Matrix4f;

public class Bullet extends GameItem2d {
    private final float damage;

    private static final BBDPolygon polygon = Utils.buildQuad(GameValues.BULLET_SIZE, GameValues.BULLET_SIZE);
    private static final Texture texture = new Texture("assets/images/bullet.png");

    public Bullet(GameItem2d parent, float damage){
        super(Mesh.buildMeshFromPolygon(polygon, texture), Utils.buildBasicTexturedShaderProgram(), polygon, GameValues.BULLET_LAYER, false);
        this.damage = damage;
        this.setRotation(parent.getRotation().z);
        DoodleWarsGame.bulletList.add(this);
    }

    @Override
    public void update(float interval, MouseInput mouseInput, Window window){
        Utils.translateEntity(this, GameValues.BULLET_SPEED, interval);
    }

    @Override
    public void setUniforms(Matrix4f projectionMatrix, Matrix4f modelViewMatrix) {
        this.shader.setUniform("projectionMatrix", projectionMatrix);
        this.shader.setUniform("modelViewMatrix", modelViewMatrix);
        this.shader.setUniform("texture_sampler", 0);
    }

    public float getDamage(){
        return  this.damage;
    }


}
