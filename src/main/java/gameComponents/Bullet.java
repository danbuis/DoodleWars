package gameComponents;

import BBDGameLibrary.GameEngine.GameItem2d;
import BBDGameLibrary.Geometry2d.BBDPolygon;
import BBDGameLibrary.OpenGL.Mesh;
import BBDGameLibrary.OpenGL.Texture;
import engine.Utils;
import org.joml.Vector3f;

public class Bullet extends GameItem2d {
    private final GameItem2d parent;
    private final Vector3f direction;

    private static final BBDPolygon polygon = Utils.buildQuad(GameValues.BULLET_SIZE, GameValues.BULLET_SIZE);
    private static final Texture texture = new Texture("assets/images/bullet.png");

    public Bullet(GameItem2d parent){
        super(Mesh.buildMeshFromPolygon(polygon, texture), Utils.buildBasicTexturedShaderProgram(), polygon, GameValues.BULLET_LAYER, false);
        this.parent = parent;
        this.direction = parent.getRotation();
    }

    @Override
    public void update(float interval){
        
    }
}
