package gameComponents.NotShips;

import BBDGameLibrary.GameEngine.GameItem;
import BBDGameLibrary.GameEngine.GameItem2d;
import BBDGameLibrary.GameEngine.MouseInput;
import BBDGameLibrary.Geometry2d.BBDPoint;
import BBDGameLibrary.Geometry2d.BBDPolygon;
import BBDGameLibrary.OpenGL.Mesh;
import BBDGameLibrary.OpenGL.Texture;
import BBDGameLibrary.OpenGL.Window;
import engine.DoodleWarsGame;
import engine.Utils;
import gameComponents.GameValues;
import org.joml.Matrix4f;

import java.util.ArrayList;

public class Asteroid extends GameItem2d {
    private final float direction;
    private float health;
    private final float speed ;
    private final float rotation;
    private float hitRadius;
    private int size;
    private float damage;

    private static final Texture texture = new Texture("assets/images/asteroid.jpg");

    public Asteroid(int size_class, float direction, float speed) {
        super(Mesh.buildMeshFromPolygon(Asteroid.buildPolygon(size_class), texture), Utils.buildBasicTexturedShaderProgram(), Asteroid.buildPolygon(size_class), GameValues.ASTEROID_LAYER, false);
        this.direction = direction;
        this.size = size_class;
        switch (size_class){
            case 1:
                this.health = GameValues.ASTEROID_HEALTH_1;
                this.hitRadius = 0.5f * GameValues.ASTEROID_SIZE_1;
                this.damage = GameValues.ASTEROID_DAMAGE_1;
                break;
            case 2:
                this.health = GameValues.ASTEROID_HEALTH_2;
                this.hitRadius = 0.5f * GameValues.ASTEROID_SIZE_2;
                this.damage = GameValues.ASTEROID_DAMAGE_2;
                break;
            case 3:
                this.health = GameValues.ASTEROID_HEALTH_3;
                this.hitRadius = 0.5f * GameValues.ASTEROID_SIZE_3;
                this.damage = GameValues.ASTEROID_DAMAGE_3;
                break;
            case 4:
                this.health = GameValues.ASTEROID_HEALTH_4;
                this.hitRadius = 0.5f * GameValues.ASTEROID_SIZE_4;
                this.damage = GameValues.ASTEROID_DAMAGE_4;
                break;
        }
        this.speed = speed;
        this.rotation = (float) (Math.random() * GameValues.ASTEROID_ROTATION_SPEED_MAX);
    }

    @Override
    public void update(float interval, MouseInput mouseInput, Window window){
        Utils.translateEntity(this, speed, interval);

        this.rotate(this.rotation * interval);

        //teleport to the other side
        float currentX = this.getPosition().x;
        float currentY = this.getPosition().y;
        if (Math.abs(currentX) > GameValues.BOARD_EDGE_BUFFER){
            this.setPosition(-currentX, currentY);
        }
        if (Math.abs(currentY) > GameValues.BOARD_EDGE_BUFFER){
            this.setPosition(currentX, -currentY);
        }

        //check for bullet hit
        float distSquared = hitRadius*hitRadius;
        ArrayList<GameItem> bulletsToRemove = new ArrayList<>();
        for (GameItem bllt: DoodleWarsGame.bulletList) {
            float deltaX = bllt.getPosition().x - this.getPosition().x;
            float deltaY = bllt.getPosition().y - this.getPosition().y;

            if (distSquared > deltaX * deltaX + deltaY * deltaY) {
                Bullet bullet = (Bullet) bllt;
                this.health -= bullet.getDamage();
                System.out.println(this.health);
                bullet.cleanup();
                bulletsToRemove.add(bullet);
            }
        }
        for(GameItem bullet:bulletsToRemove){
            DoodleWarsGame.bulletList.remove(bullet);
        }

        //check if this asteroid is toast
        if (this.health <= 0){
            DoodleWarsGame.newAsteroids.add(this);
            if(this.size != 4) {
                for (int i = 0; i < GameValues.ASTEROID_SPLIT_QUANTITY; i++) {
                    float newRotation = (float) (Math.PI * 2 * Math.random());
                    float newSpeed = this.speed * (float) ((Math.random() * (GameValues.ASTEROID_SPEED_MODIFIER_MAX - GameValues.ASTEROID_SPEED_MODIFIER_MIN)) + GameValues.ASTEROID_SPEED_MODIFIER_MIN);

                    Asteroid newAsteroid = new Asteroid(this.size + 1, newRotation, newSpeed);
                    newAsteroid.setPosition(this.getPosition().x, this.getPosition().y);
                    DoodleWarsGame.newAsteroids.add(newAsteroid);
                }
            }
        }
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

    public float getHitRadius(){
        return this.hitRadius;
    }

    public float getDamage(){
        return this.damage;
    }
}
