package gameComponents.NotShips;

import BBDGameLibrary.GameEngine.GameItem;
import engine.DoodleWarsGame;
import gameComponents.GameValues;
import gameComponents.Ships.EasyBulletShip;
import org.joml.Vector2f;

public class Spawner {
    private DoodleWarsGame game;
    private float elapsedTime = 0;
    public static final int SPAWN_ASTEROID = 0;
    public static final int EASYBULLET = 1;

    public Spawner(DoodleWarsGame game){
        this.game = game;
    }

    public void spawnItem(int itemToSpawn){
        switch (itemToSpawn){
            case SPAWN_ASTEROID: {
                Vector2f coords = this.getSpawnCoords();
                boolean validSpot = false;
                int count = 0;
                while (!validSpot && count < 10) {
                    validSpot = true;
                    count++;

                    for (GameItem asteroid : DoodleWarsGame.asteroidList) {
                        float deltaX = asteroid.getPosition().x - coords.x;
                        float deltaY = asteroid.getPosition().y - coords.y;

                        float distance = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);

                        if (distance > GameValues.ASTEROID_SIZE_1 * 1.1) {
                            validSpot = false;
                            coords = this.getSpawnCoords();
                        }
                    }
                }
                float speed = (float) ((Math.random() * (GameValues.ASTEROID_START_SPEED_MAX - GameValues.ASTEROID_START_SPEED_MIN)) + GameValues.ASTEROID_START_SPEED_MIN);
                Asteroid asteroid = new Asteroid(1, (float) ((Math.PI * 4 * Math.random()) - Math.PI * 2), speed);
                DoodleWarsGame.asteroidList.add(asteroid);
                asteroid.setPosition(coords.x, coords.y);
                break;
            }
            case EASYBULLET: {
                Vector2f coords = this.getSpawnCoords();
                EasyBulletShip new_ship = new EasyBulletShip(game.getPlayerShip());
                DoodleWarsGame.enemyList.add(new_ship);
                new_ship.setPosition(coords.x, coords.y);
                System.out.println(coords);
                break;
            }
        }
    }

    public static Vector2f getSpawnCoords(){
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

        return new Vector2f(x,y);
    }

    public void update(float interval){
        elapsedTime += interval;
        if (elapsedTime > 5.0){
            elapsedTime = 4;
            spawnItem(EASYBULLET);
        }
    }

}
