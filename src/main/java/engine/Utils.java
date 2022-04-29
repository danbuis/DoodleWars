package engine;

import BBDGameLibrary.GameEngine.GameItem2d;
import BBDGameLibrary.Geometry2d.BBDGeometry;
import BBDGameLibrary.Geometry2d.BBDPoint;
import BBDGameLibrary.Geometry2d.BBDPolygon;
import BBDGameLibrary.OpenGL.ShaderProgram;
import gameComponents.GameValues;

public class Utils {

    public static ShaderProgram buildBasicTexturedShaderProgram(){
        ShaderProgram returnProgram = null;
        try {
            returnProgram = new ShaderProgram();

            //create and attach shaders
            returnProgram.createVertexShader(BBDGameLibrary.OpenGL.Utils.loadShaderScript("/shaders/vertex.vs"));
            returnProgram.createFragmentShader(BBDGameLibrary.OpenGL.Utils.loadShaderScript("/shaders/fragment.fs"));

            //give the shader program an id
            returnProgram.link();

            // Create uniforms for world and projection matrices and texture
            returnProgram.createUniform("projectionMatrix");
            returnProgram.createUniform("modelViewMatrix");
            returnProgram.createUniform("texture_sampler");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnProgram;
    }

    public static BBDPolygon buildQuad(float width, float height){
        return new BBDPolygon(new BBDPoint[]{
                new BBDPoint(width/2, height/2),
                new BBDPoint(-width/2, height/2),
                new BBDPoint(-width/2, -height/2),
                new BBDPoint(width/2, -height/2)
        });
    }

    public static void translateEntity(GameItem2d entity, float speed, float interval){
        entity.translate((float)Math.cos(entity.getRotation().z) * speed * interval,
                -(float)Math.sin(entity.getRotation().z) *speed * interval);
    }
}
