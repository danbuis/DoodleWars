package gameComponents.NotShips;

import BBDGameLibrary.GameEngine.GameItem2d;
import BBDGameLibrary.Geometry2d.BBDPoint;
import BBDGameLibrary.Geometry2d.BBDPolygon;
import BBDGameLibrary.OpenGL.Mesh;
import BBDGameLibrary.OpenGL.Texture;
import engine.Utils;
import gameComponents.GameValues;
import org.joml.Matrix4f;

public class Asteroid extends GameItem2d {
    private final float direction;
    private float health = 0;
    private float speed = 0;
    private float rotation = 0;

    private static final Texture texture = new Texture("assets/images/asteroid.jpg");

    public Asteroid(int size_class, float direction, float speed) {
        super(Mesh.buildMeshFromPolygon(Asteroid.buildPolygon(size_class), texture), Utils.buildBasicTexturedShaderProgram(), Asteroid.buildPolygon(size_class), GameValues.ASTEROID_LAYER, false);
        this.direction = direction;
        switch (size_class){
            case 1:
                this.health = GameValues.ASTEROID_HEALTH_1;
                break;
            case 2:
                this.health = GameValues.ASTEROID_HEALTH_2;
                break;
            case 3:
                this.health = GameValues.ASTEROID_HEALTH_3;
                break;
            case 4:
                this.health = GameValues.ASTEROID_HEALTH_4;
                break;
        }
        this.speed = speed;
        this.rotation = (float) (Math.random() * GameValues.ASTEROID_ROTATION_SPEED_MAX);
    }

    @Override
    public void update(float interval){
        this.translate((float)Math.sin(this.direction) * speed * interval,
                (float)Math.cos(this.direction) * speed * interval);

        this.rotate(this.rotation * interval);
    }

    @Override
    public void setUniforms(Matrix4f projectionMatrix, Matrix4f modelViewMatrix) {
        this.shader.setUniform("projectionMatrix", projectionMatrix);
        this.shader.setUniform("modelViewMatrix", modelViewMatrix);
        this.shader.setUniform("texture_sampler", 0);
    }

    public static BBDPolygon buildPolygon(int size_class){
        float width = 0;
        switch (size_class){
            case 1:
                width = GameValues.ASTEROID_SIZE_1;
                break;
            case 2:
                width = GameValues.ASTEROID_SIZE_2;
                break;
            case 3:
                width = GameValues.ASTEROID_SIZE_3;
                break;
            case 4:
                width = GameValues.ASTEROID_SIZE_4;
                break;
        }
        float sqrt = (float) (Math.sqrt(2) * width/4);
        return new BBDPolygon(new BBDPoint[]{
                new BBDPoint(width/2, 0),
                new BBDPoint(sqrt, sqrt),
                new BBDPoint(0, width/2),
                new BBDPoint(-sqrt, sqrt),
                new BBDPoint(-width/2, 0),
                new BBDPoint(-sqrt, -sqrt),
                new BBDPoint(0, -width/2),
                new BBDPoint(sqrt, -sqrt)
        });
    }
}
