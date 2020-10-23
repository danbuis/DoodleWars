package gameComponents.Ships;

import GameEngine.GameItem2d;
import Geometry2d.BBDPolygon;
import OpenGL.Mesh;
import OpenGL.ShaderProgram;

public class PlayerShip extends GameItem2d {
    public PlayerShip(Mesh mesh, ShaderProgram shaderProgram, BBDPolygon shape, int layer, boolean shapeInteracts) {
        super(mesh, shaderProgram, shape, layer, shapeInteracts);
    }
}
