package engine;

import BBDGameLibrary.GameEngine.Camera;
import BBDGameLibrary.GameEngine.GameComponent;
import BBDGameLibrary.GameEngine.GameItem;
import BBDGameLibrary.Geometry2d.BBDPolygon;
import BBDGameLibrary.OpenGL.*;
import gameComponents.Background.Background;
import gameComponents.GameValues;
import gameComponents.NotShips.Asteroid;
import gameComponents.Ships.PlayerShip;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class DoodleWarsGame implements GameComponent {
    private final Renderer renderer;
    private Camera camera;
    private Background background;
    private PlayerShip playerShip;

    public static List<GameItem> bulletList = new ArrayList<GameItem>();
    public static List<GameItem> asteroidList = new ArrayList<GameItem>();
    public static List<GameItem> newAsteroids = new ArrayList<GameItem>();

    public DoodleWarsGame() {
        renderer = new Renderer();
        camera = new Camera();
    }

    @Override
    public void init(Window window) {
        background = new Background(renderer, camera);
        background.init(window);

        playerShip = initializePlayerShip();
        initializeAsteroids();
    }

    //Create a bunch of size 1 asteroids
    private void initializeAsteroids() {
        float min = GameValues.BOARD_EDGE * -1;
        float max = GameValues.BOARD_EDGE;

        for (int i = 0; i < GameValues.INITIAL_ASTEROID_COUNT; i++){
            float speed = (float) ((Math.random() * (GameValues.ASTEROID_START_SPEED_MAX - GameValues.ASTEROID_START_SPEED_MIN)) + GameValues.ASTEROID_START_SPEED_MIN);
            Asteroid asteroid = new Asteroid(1, (float) (Math.PI * 2 * Math.random()), speed);
            DoodleWarsGame.asteroidList.add(asteroid);

            float newX = (float) (Math.random()*(max-min+1)+min);
            float newY = (float) (Math.random()*(max-min+1)+min);

            asteroid.setPosition(newX, newY);
            System.out.println(newX+", "+newY);
        }
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

        for (GameItem bullet: bulletList){
            bullet.update(v);
        }

        for (GameItem asteroid: asteroidList){
            asteroid.update(v);
        }

        for(GameItem asteroid: newAsteroids){
            if(asteroidList.contains(asteroid)){
                asteroidList.remove(asteroid);
                asteroid.cleanup();
            }else{
                System.out.println("adding an asteroid");
                asteroidList.add(asteroid);
            }
        }
        newAsteroids.clear();
    }

    @Override
    public void render(Window window) {
        renderer.resetRenderer(window);
        background.render(window);
        renderer.renderItem(window, playerShip, camera);
        renderer.renderList(window, bulletList, camera);
        renderer.renderList(window, asteroidList, camera);
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
