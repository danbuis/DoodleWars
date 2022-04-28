package gameComponents.Ships;

import BBDGameLibrary.GameEngine.GameItem2d;
import BBDGameLibrary.Geometry2d.BBDPoint;
import BBDGameLibrary.Geometry2d.BBDPolygon;
import BBDGameLibrary.OpenGL.Mesh;
import BBDGameLibrary.OpenGL.ShaderProgram;
import BBDGameLibrary.OpenGL.Texture;
import engine.Utils;
import gameComponents.GameValues;
import gameComponents.NotShips.Spawner;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class BaseShip extends GameItem2d {
    private float accel;
    private float turn_rate;
    private float max_speed;
    private float current_speed;
    private float bullet_cooldown;
    private float hull_current;
    private float hull_max;
    private float shields_current;
    private float shields_max;
    public BBDPoint targetPoint;

    private static ShaderProgram shader = Utils.buildBasicTexturedShaderProgram();

    public BaseShip(float[] input_values, BBDPolygon quad, Texture texture){
        super(Mesh.buildMeshFromPolygon(quad, texture), shader, quad, GameValues.ENEMY_SHIP_LAYER, false);
        this.accel = input_values[0];
        this.turn_rate = input_values[1];
        this.max_speed = input_values[2];
        this.bullet_cooldown = input_values[3];
        this.hull_current = input_values[4];
        this.hull_max = this.hull_current;
        this.shields_current = input_values[5];
        this.shields_max = shields_current;
        this.current_speed = 0;
    }

    public BBDPoint roombaSearch(){
        Vector2f targetVector = Spawner.getSpawnCoords();
        return new BBDPoint(targetVector.x, targetVector.y);
    }

    public boolean atBoardEdge(){
        if (Math.abs(this.getPosition().x) > GameValues.BOARD_EDGE_BUFFER
                || Math.abs(this.getPosition().y) > GameValues.BOARD_EDGE_BUFFER){
            return true;
        }
        return false;
    }

    public float distanceSquaredToTarget(){
        Vector3f currentLocation = this.getPosition();
        BBDPoint currentPoint = new BBDPoint(currentLocation.x, currentLocation.y);

        return targetPoint.distanceSquaredToPoint(currentPoint);
    }

    public float directionToTarget(){
        Vector3f currentLocation = this.getPosition();
        BBDPoint currentPoint = new BBDPoint(currentLocation.x, currentLocation.y);
        float targetDir = currentPoint.angleToOtherPoint(targetPoint);
        return targetDir;
    }

    public boolean moveTowardTarget(float updateInterval, float desiredSpeed){
        //TODO don't instantaneously pivot

        float targetDirection = directionToTarget();
        this.setRotation(-targetDirection);

        // TODO manage acceleration
        this.current_speed = desiredSpeed;
        float xMult = (float)Math.cos(this.getRotation().z);
        float yMult = (float)Math.sin(this.getRotation().z);
        this.translate(xMult * this.current_speed * updateInterval,
                -yMult * this.current_speed  * updateInterval);
        System.out.println(this.distanceSquaredToTarget());
        return this.distanceSquaredToTarget() < 0.8f;
    }

    @Override
    public void setUniforms(Matrix4f projectionMatrix, Matrix4f modelViewMatrix) {
        this.shader.setUniform("projectionMatrix", projectionMatrix);
        this.shader.setUniform("modelViewMatrix", modelViewMatrix);
        this.shader.setUniform("texture_sampler", 0);
    }

    public float getAccel(){ return accel;}
    public float getTurnRate(){ return turn_rate;}
    public float getMaxSpeed(){ return max_speed;}
    public float getBulletCooldown(){ return bullet_cooldown;}
    public float getCurrentHull(){ return hull_current;}
    public float getCurrentShield(){ return shields_current;}
    public float getMaxHull(){ return hull_max;}
    public float getMaxShield(){ return shields_max;}
    public float getCurrentSpeed() { return current_speed;}
}
