package engine;

import OpenGL.ShaderProgram;

public class Utils {

    public static ShaderProgram buildBasicTexturedShaderProgram(){
        ShaderProgram returnProgram = null;
        try {
            returnProgram = new ShaderProgram();

            //create and attach shaders
            returnProgram.createVertexShader(OpenGL.Utils.loadShaderScript("/shaders/vertex.vs"));
            returnProgram.createFragmentShader(OpenGL.Utils.loadShaderScript("/shaders/fragment.fs"));

            //give the shader program an id
            returnProgram.link();

            // Create uniforms for world and projection matrices and texture
            returnProgram.createUniform("projectionMatrix");
            returnProgram.createUniform("worldMatrix");
            returnProgram.createUniform("texture_sampler");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnProgram;
    }
}
