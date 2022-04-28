package engine;

import BBDGameLibrary.GameEngine.Camera;
import BBDGameLibrary.GameEngine.GameComponent;
import BBDGameLibrary.GameEngine.GameItem;
import BBDGameLibrary.GameEngine.MouseInput;
import BBDGameLibrary.Geometry2d.BBDPolygon;
import BBDGameLibrary.OpenGL.*;
import gameComponents.Background.Background;
import gameComponents.GameValues;
import gameComponents.NotShips.Spawner;
import gameComponents.Ships.PlayerShip;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class DoodleWarsGame implements GameComponent {
    private final Renderer renderer;
    private final Camera camera;
    private Background background;
    private PlayerShip playerShip;
    private final Spawner spawner = new Spawner(this);

    public static List<GameItem> bulletList = new ArrayList<>();
    public static List<GameItem> asteroidList = new ArrayList<>();
    public static List<GameItem> newAsteroids = new ArrayList<>();
    public static List<GameItem> enemyList = new ArrayList<>();

    private int frame = 0;

    public DoodleWarsGame() {
        renderer = new Renderer();
        camera = new Camera();
    }

    @Override
    public void init(Window window) {
        background = new Background(renderer, camera);
        background.init(window);

        playerShip = initializePlayerShip();

        for (int i = 0; i < GameValues.INITIAL_ASTEROID_COUNT; i++){
            spawner.spawnItem(Spawner.SPAWN_ASTEROID);
        }
    }

    @Override
    public void input(Window window, MouseInput mouseInput) {
        playerShip.input(window, mouseInput);
    }

    @Override
    public void update(float interval, MouseInput mouseInput, Window window) {
        spawner.update(interval);

        playerShip.update(interval, mouseInput, window);

        Vector3f playerPosition = playerShip.getPosition();
        camera.setPosition(playerPosition.x, playerPosition.y, 15);

        for (GameItem bullet: bulletList){
            bullet.update(interval, mouseInput, window);
        }

        for (GameItem enemy: enemyList){
            enemy.update(interval, mouseInput, window);
        }

        for (GameItem asteroid: asteroidList){
            asteroid.update(interval, mouseInput, window);
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
        if (enemyList.size() != 0) {
            renderer.renderList(window, enemyList, camera);
        }
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

    public PlayerShip getPlayerShip(){
        return this.playerShip;
    }
}
