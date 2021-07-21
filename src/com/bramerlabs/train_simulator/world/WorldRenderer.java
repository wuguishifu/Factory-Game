package com.bramerlabs.train_simulator.world;

import com.bramerlabs.engine.graphics.Camera;
import com.bramerlabs.engine.graphics.Material;
import com.bramerlabs.engine.graphics.Shader;
import com.bramerlabs.engine.graphics.renderers.Renderer;
import com.bramerlabs.engine.io.window.Window;
import com.bramerlabs.engine.math.matrix.Matrix4f;
import com.bramerlabs.engine.math.vector.Vector2f;
import com.bramerlabs.engine.math.vector.Vector3f;
import com.bramerlabs.engine.objects.RenderObject;
import com.bramerlabs.engine.objects.shapes_2d.Square;
import com.bramerlabs.train_simulator.world.chunk.Chunk;
import com.bramerlabs.train_simulator.world.title.Tile;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

public class WorldRenderer extends Renderer {

    public Square square;

    public WorldRenderer(Window window) {
        super(window);
        square = new Square(new Material("textures/test.png"), new Vector2f(0, 0), 0, new Vector2f(1, 1));
    }

    public void renderWorld(World world, Camera camera, Shader shader) {
        Matrix4f view = Matrix4f.view(new Vector3f(camera.getPosition(), camera.getDistance()), new Vector3f(0));

        GL30.glBindVertexArray(square.getMesh().getVAO());
        GL30.glEnableVertexAttribArray(0);
        GL30.glEnableVertexAttribArray(1);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, square.getMesh().getIBO());
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL13.glBindTexture(GL11.GL_TEXTURE_2D, Tile.tileSet.get(-1).getTextureID());
        shader.bind();
        shader.setUniform("vView", view);
        shader.setUniform("vProjection", window.getProjectionMatrix());

        for (Chunk chunk : world.visibleChunks.values()) {
            Vector2f chunkPosition = Vector2f.scale(chunk.getPosition(), (Chunk.TILE_SIZE * Chunk.SIZE));
            Tile[][] tiles = chunk.getTiles();
            for (int x = 0; x < Chunk.SIZE; x++) {
                for (int y = 0; y < Chunk.SIZE; y++) {
                    if (tiles[x][y].getType() != 0) {
                        Vector3f position = new Vector3f(Vector2f.add(chunkPosition,
                                Vector2f.scale(new Vector2f(x, y), Chunk.TILE_SIZE)), 0);
                        Vector3f rotation = new Vector3f(0);
                        Vector3f scale = new Vector3f(Chunk.TILE_SIZE, Chunk.TILE_SIZE, 0);
                        Matrix4f model = Matrix4f.transform(position, rotation, scale);
                        shader.setUniform("vModel", model);
                        GL11.glDrawElements(GL11.GL_TRIANGLES, square.getMesh().getIndices().length, GL11.GL_UNSIGNED_INT, 0);
                    }
                }
            }
        }

        shader.unbind();
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL13.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL30.glDisableVertexAttribArray(0);
        GL30.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);
    }

    public void renderWorldNaive(World world, Camera camera, Shader shader) {
        Matrix4f view = Matrix4f.view(new Vector3f(camera.getPosition(), camera.getDistance()), new Vector3f(0));
        GL30.glBindVertexArray(square.getMesh().getVAO());
        GL30.glEnableVertexAttribArray(0);
        GL30.glEnableVertexAttribArray(1);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, square.getMesh().getIBO());

        shader.bind();
        shader.setUniform("vView", view);
        shader.setUniform("vProjection", window.getProjectionMatrix());

        for (int i = 0; i < Tile.numTextures; i++) {
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL13.glBindTexture(GL11.GL_TEXTURE_2D, Tile.tileSet.get(i).getTextureID());

            for (Chunk chunk : world.visibleChunks.values()) {
                Vector2f chunkPosition = Vector2f.scale(chunk.getPosition(), (Chunk.TILE_SIZE * Chunk.SIZE));
                for (int x = 0; x < Chunk.SIZE; x++) {
                    for (int y = 0; y < Chunk.SIZE; y++) {
                        if (chunk.getTiles()[x][y].getType() != i) {
                            continue;
                        }
                        Vector3f position = new Vector3f(Vector2f.add(chunkPosition,
                                Vector2f.scale(new Vector2f(x, y), Chunk.TILE_SIZE)), 0);
                        Vector3f rotation = new Vector3f(0, 0, 0);
                        Vector3f scale = new Vector3f(Chunk.TILE_SIZE, Chunk.TILE_SIZE, 0);
                        Matrix4f model = Matrix4f.transform(position, rotation, scale);
                        shader.setUniform("vModel", model);
                        GL11.glDrawElements(GL11.GL_TRIANGLES, square.getMesh().getIndices().length,
                                GL11.GL_UNSIGNED_INT, 0);
                    }
                }
            }
        }

        shader.unbind();
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL13.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL30.glDisableVertexAttribArray(0);
        GL30.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);
    }

    public void destroy() {
        square.destroy();
    }
}