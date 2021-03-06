package engine;

import BBDGameLibrary.GameEngine.Camera;
import BBDGameLibrary.GameEngine.GameComponent;
import BBDGameLibrary.Geometry2d.BBDPoint;
import BBDGameLibrary.Geometry2d.BBDPolygon;
import BBDGameLibrary.OpenGL.*;
import gameComponents.Background.Background;
import gameComponents.GameValues;
import gameComponents.Ships.PlayerShip;
import org.joml.Matrix3f;
import org.joml.Vector3f;

public class DoodleWarsGame implements GameComponent {
    private final Renderer renderer;
    private Camera camera;
    private Background background;
    private PlayerShip playerShip;

    public DoodleWarsGame() {
        renderer = new Renderer();
        camera = new Camera();
    }

    @Override
    public void init(Window window) {
        background = new Background(renderer, camera);
        background.init(window);

        playerShip = initializePlayerShip();
    }

    @Override
    public void input(Window window) {
        playerShip.input(window);
    }

    @Override
    public void update(float v) {
        playerShip.update(v);

        Vector3f playerPosition = playerShip.getPosition();
        camera.setPosition(playerPosition.x, playerPosition.y, 0);
    }

    @Override
    public void render(Window window) {
        renderer.resetRenderer(window);
        background.render(window);
        renderer.renderItem(window, playerShip, camera);
    }

    @Override
    public void cleanup() {

    }

    private PlayerShip initializePlayerShip(){
        BBDPolygon poly = Utils.buildQuad(GameValues.PLAYER_WIDTH, GameValues.PLAYER_HEIGHT);
        ShaderProgram shader = Utils.buildBasicTexturedShaderProgram();
        Texture texture = new Texture("assets/images/Player.png");

        return new PlayerShip(Mesh.buildMeshFromPolygon(poly, texture), shader, poly, GameValues.PLAYER_SHIP_LAYER, false);
    }
}
