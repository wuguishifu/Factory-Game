package com.bramerlabs.engine.graphics.renderers;

import com.bramerlabs.engine.graphics.Camera;
import com.bramerlabs.engine.graphics.Shader;
import com.bramerlabs.engine.io.window.Window;
import com.bramerlabs.engine.math.matrix.Matrix4f;
import com.bramerlabs.engine.math.vector.Vector3f;
import com.bramerlabs.engine.objects.RenderObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

public class Renderer {

    private final Window window;

    public Renderer(Window window) {
        this.window = window;
    }

    public void renderMesh(RenderObject object, Camera camera, Shader shader) {
        // create MVP matrices
        Matrix4f model = Matrix4f.transform(
                new Vector3f(object.getPosition(), 0.0f),
                new Vector3f(object.getRotation(), 0.0f),
                new Vector3f(object.getScale(), 0.0f)
        );
        Matrix4f view = Matrix4f.view(new Vector3f(camera.getPosition(), 1.0f), new Vector3f(0, 0, 0));

        GL30.glBindVertexArray(object.getMesh().getVAO());

        GL30.glEnableVertexAttribArray(0); // position buffer;
        GL30.glEnableVertexAttribArray(1); // texture coord buffer
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, object.getMesh().getIBO());

        GL13.glActiveTexture(GL13.GL_TEXTURE0); // base texture map
        GL13.glBindTexture(GL11.GL_TEXTURE_2D, object.getMesh().getMaterial().getTextureID());

        shader.bind();
        shader.setUniform("vModel", model);
        shader.setUniform("vView", view);
        shader.setUniform("vProjection", window.getProjectionMatrix());
        GL11.glDrawElements(GL11.GL_TRIANGLES, object.getMesh().getIndices().length, GL11.GL_UNSIGNED_INT, 0);
        shader.unbind();

        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL13.glBindTexture(GL11.GL_TEXTURE_2D, 0);

        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL30.glDisableVertexAttribArray(0);
        GL30.glDisableVertexAttribArray(1);

        GL30.glBindVertexArray(0);
    }
}