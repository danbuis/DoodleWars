package gameComponents.NotShips;

import engine.DoodleWarsGame;
import gameComponents.GameValues;

public class Spawner {
    private float elapsedTime = 0;
    public static final int SPAWN_ASTEROID = 0;

    public void spawnItem(int itemToSpawn){
        float lateralValue = GameValues.BOARD_EDGE_BUFFER - (float) (Math.random() * 2 * GameValues.BOARD_EDGE_BUFFER);
        int side = (int) Math.ceil(Math.random() * 4);

        float x;
        float y;

        switch (side){
            case 1: //top edge
                x = lateralValue;
                y = GameValues.BOARD_EDGE_BUFFER;
                break;
            case 2: //right edge
                x = GameValues.BOARD_EDGE_BUFFER;
                y = lateralValue;
                break;
            case 3: //bottom edge
                x = lateralValue;
                y = -GameValues.BOARD_EDGE_BUFFER;
                break;
            case 4: //left edge
                x = -GameValues.BOARD_EDGE_BUFFER;
                y = lateralValue;
                break;
            default:
                x = 0;
                y = 0;
        }

        switch (itemToSpawn){
            case SPAWN_ASTEROID:
                float speed = (float) ((Math.random() * (GameValues.ASTEROID_START_SPEED_MAX - GameValues.ASTEROID_START_SPEED_MIN)) + GameValues.ASTEROID_START_SPEED_MIN);
                Asteroid asteroid = new Asteroid(1, (float) ((Math.PI * 4 * Math.random()) - Math.PI*2), speed);
                DoodleWarsGame.asteroidList.add(asteroid);
                asteroid.setPosition(x, y);
                System.out.println(x+", "+y);
        }
    }

    public void update(float interval){
        elapsedTime += interval;
    }

}
