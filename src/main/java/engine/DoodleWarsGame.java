package engine;

import GameEngine.GameComponent;
import Geometry2d.BBDPoint;
import Geometry2d.BBDPolygon;
import OpenGL.*;
import gameComponents.Background.Background;
import gameComponents.GameValues;
import gameComponents.Ships.PlayerShip;

public class DoodleWarsGame implements GameComponent {
    private final Renderer renderer;
    private Background background;
    private PlayerShip playerShip;

    public DoodleWarsGame() {
        renderer = new Renderer();
    }

    @Override
    public void init(Window window) {
        background = new Background(renderer);
        background.init(window);

        playerShip = initializePlayerShip();
    }

    @Override
    public void input(Window window) {

    }

    @Override
    public void update(float v) {

    }

    @Override
    public void render(Window window) {
        renderer.resetRenderer(window);
        background.render(window);
        renderer.renderItem(window, playerShip);
    }

    @Override
    public void cleanup() {

    }

    private PlayerShip initializePlayerShip(){
        BBDPolygon poly = new BBDPolygon(new BBDPoint[]{
                new BBDPoint(GameValues.PLAYER_WIDTH/2, GameValues.PLAYER_HEIGHT/2),
                new BBDPoint(-GameValues.PLAYER_WIDTH/2, GameValues.PLAYER_HEIGHT/2),
                new BBDPoint(-GameValues.PLAYER_WIDTH/2, -GameValues.PLAYER_HEIGHT/2),
                new BBDPoint(GameValues.PLAYER_WIDTH/2, -GameValues.PLAYER_HEIGHT/2)
        });
        ShaderProgram shader = Utils.buildBasicTexturedShaderProgram();
        Texture texture = new Texture("assets/images/Player.png");

        return new PlayerShip(Mesh.buildMeshFromPolygon(poly, texture), shader, poly, GameValues.PLAYER_SHIP_LAYER, false);
    }
}
