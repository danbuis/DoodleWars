package gameComponents;

public class GameValues {
    //layer values
    public static final int PLAYER_SHIP_LAYER = 9900;
    public static final int BACKGROUND_BASE_LAYER = 10000;
    public static final int BULLET_LAYER = 9500;
    public static final int ASTEROID_LAYER = 9501;

    //background values
    public static final int TILES_PER_SIDE = 10;
    public static final int TILE_SIZE = 3;

    public static final float BOARD_EDGE = TILES_PER_SIDE / 2 * TILE_SIZE * 0.95f;
    public static final float BOARD_EDGE_BUFFER = BOARD_EDGE + 3;

    //bullet values
    public static final float BULLET_SPEED = 4.2f;
    public static final float BULLET_SIZE = 0.15f;

    //ship values
    public static final float PLAYER_WIDTH = 1.2f;
    public static final float PLAYER_HEIGHT = 1.6f;
    public static final float PLAYER_ACCELERATION = 0.9f;
    public static final float PLAYER_TURN_RATE = 1.5f;
    public static final float PLAYER_MAX_SPEED = 1.9f;
    public static final float PLAYER_BULLET_COOLDOWN = 0.01f;
    public static final float PLAYER_BULLET_BASE_DAMAGE = 50;
    public static final float PLAYER_HULL_BASE = 1000;
    public static final float PLAYER_SHIELDS_BASE = 1000;
    public static final float PLAYER_SHIELD_REGEN = 30;

    //asteroid values
    public static final int INITIAL_ASTEROID_COUNT = 4;
    public static final float ASTEROID_SIZE_1 = 3.0f;
    public static final float ASTEROID_SIZE_2 = 1.8f;
    public static final float ASTEROID_SIZE_3 = 1.0f;
    public static final float ASTEROID_SIZE_4 = 0.5f;
    public static final float ASTEROID_HEALTH_1 = 1000;
    public static final float ASTEROID_HEALTH_2 = 600;
    public static final float ASTEROID_HEALTH_3 = 360;
    public static final float ASTEROID_HEALTH_4 = 210;
    public static final float ASTEROID_DAMAGE_1 = 200;
    public static final float ASTEROID_DAMAGE_2 = 100;
    public static final float ASTEROID_DAMAGE_3 = 50;
    public static final float ASTEROID_DAMAGE_4 = 25;
    public static final float ASTEROID_START_SPEED_MIN = 0.2f;
    public static final float ASTEROID_START_SPEED_MAX = 0.4f;
    public static final float ASTEROID_SPEED_MODIFIER_MIN = 1.2f;
    public static final float ASTEROID_SPEED_MODIFIER_MAX = 1.7f;
    public static final float ASTEROID_ROTATION_SPEED_MAX = 0.7f;
    public static final int ASTEROID_SPLIT_QUANTITY = 3;
}
