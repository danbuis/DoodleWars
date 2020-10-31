package gameComponents.Background;

import BBDGameLibrary.GameEngine.GameItem2d;
import BBDGameLibrary.Geometry2d.BBDPolygon;
import BBDGameLibrary.OpenGL.Mesh;
import BBDGameLibrary.OpenGL.ShaderProgram;
import org.joml.Matrix4f;

public class BackgroundBase extends GameItem2d {
    public BackgroundBase(Mesh mesh, ShaderProgram shaderProgram, BBDPolygon shape, int layer, boolean shapeInteracts) {
        super(mesh, shaderProgram, shape, layer, shapeInteracts);
    }

    @Override
    public void setUniforms(Matrix4f projectionMatrix, Matrix4f viewModelMatrix) {
        this.shader.setUniform("projectionMatrix", projectionMatrix);
        this.shader.setUniform("modelViewMatrix", viewModelMatrix);
        this.shader.setUniform("texture_sampler", 0);
    }
}
