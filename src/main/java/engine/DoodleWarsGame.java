package engine;

import GameEngine.GameComponent;
import OpenGL.Renderer;
import OpenGL.Window;
import gameComponents.Background.Background;

public class DoodleWarsGame implements GameComponent {
    private final Renderer renderer;
    private Background background;

    public DoodleWarsGame() {
        renderer = new Renderer();
    }

    @Override
    public void init(Window window) {
        background = new Background(renderer);
        background.init(window);
    }

    @Override
    public void input(Window window) {

    }

    @Override
    public void update(float v) {

    }

    @Override
    public void render(Window window) {
        background.render(window);
    }

    @Override
    public void cleanup() {

    }
}
