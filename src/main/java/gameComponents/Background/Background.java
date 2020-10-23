package gameComponents.Background;

import GameEngine.GameComponent;
import Geometry2d.BBDPoint;
import Geometry2d.BBDPolygon;
import OpenGL.*;
import com.sun.prism.ps.Shader;
import engine.Utils;
import gameComponents.GameValues;

import java.util.ArrayList;
import java.util.List;

public class Background implements GameComponent {
    private BackgroundBase[] base = new BackgroundBase[GameValues.TILES_PER_SIDE * GameValues.TILES_PER_SIDE];
    private final Renderer renderer;

    public Background(Renderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void init(Window window) {
        //build the assets for a background tile
        BBDPolygon poly = new BBDPolygon(new BBDPoint[]{
                new BBDPoint(GameValues.TILE_SIZE, GameValues.TILE_SIZE),
                new BBDPoint(0, GameValues.TILE_SIZE),
                new BBDPoint(0, 0),
                new BBDPoint(GameValues.TILE_SIZE, 0)
        });

        ShaderProgram shader = Utils.buildBasicTexturedShaderProgram();
        Texture texture = new Texture("assets/images/Background.png");

        //build an array of notebook backgrounds
        int offset = GameValues.TILE_SIZE * GameValues.TILES_PER_SIDE / 2;
        for(int i = 0; i < GameValues.TILES_PER_SIDE; i++){
            for(int j = 0; j < GameValues.TILES_PER_SIDE; j++){
                BackgroundBase temp = new BackgroundBase(Mesh.buildMeshFromPolygon(poly, texture), shader, poly, GameValues.BACKGROUND_BASE_LAYER, false);
                base[GameValues.TILES_PER_SIDE * i + j] = temp;
                temp.translate(i*GameValues.TILE_SIZE-offset, j*GameValues.TILE_SIZE-offset);
            }
        }
    }

    @Override
    public void input(Window window) {

    }

    @Override
    public void update(float v) {

    }

    @Override
    public void render(Window window) {
        renderer.renderArray(window, base);
    }

    @Override
    public void cleanup() {

    }
}
