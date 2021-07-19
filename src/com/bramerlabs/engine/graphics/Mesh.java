package com.bramerlabs.engine.graphics;

import org.lwjgl.opengl.*;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Mesh {

    private final Vertex[] vertices;
    private final int[] indices;

    private final Material material;

    private int vao, pbo, tbo, ibo;

    public Mesh(Vertex[] vertices, int[] indices, Material material) {
        this.vertices = vertices;
        this.indices = indices;
        this.material = material;
        this.create();
    }

    private void create() {
        vao = GL46.glGenVertexArrays();
        GL46.glBindVertexArray(vao);

        makePositionBuffer();
        makeTextureBuffer();
        makeIndexBuffer();
    }

    private void makePositionBuffer() {
        FloatBuffer positionBuffer = MemoryUtil.memAllocFloat(vertices.length * 2);
        float[] positionData = new float[vertices.length * 2];
        for (int i = 0; i < vertices.length; i++) {
            positionData[i * 2] = vertices[i].position.x;
            positionData[i * 2 + 1] = vertices[i].position.y;
        }
        positionBuffer.put(positionData).flip();
        pbo = storeData(positionBuffer, 0, 2);
    }

    private void makeTextureBuffer() {
        FloatBuffer textureBuffer = MemoryUtil.memAllocFloat(vertices.length * 2);
        float[] textureData = new float[vertices.length * 2];
        for (int i = 0; i < vertices.length; i++) {
            textureData[i * 2] = vertices[i].textureCoord.x;
            textureData[i * 2 + 1] = vertices[i].textureCoord.y;
        }
        textureBuffer.put(textureData).flip();
        tbo = storeData(textureBuffer, 1, 2);
    }

    private void makeIndexBuffer() {
        IntBuffer indicesBuffer = MemoryUtil.memAllocInt(indices.length);
        indicesBuffer.put(indices).flip();
        ibo = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    private int storeData(FloatBuffer buffer, int index, int size) {
        int bufferID = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, bufferID);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(index, size, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        return bufferID;
    }

    public Vertex[] getVertices() {
        return vertices;
    }

    public int[] getIndices() {
        return indices;
    }

    public int getVAO() {
        return this.vao;
    }

    public int getIBO() {
        return this.ibo;
    }

    public Material getMaterial() {
        return this.material;
    }

    public void destroy() {
        GL15.glDeleteBuffers(pbo);
        GL15.glDeleteBuffers(tbo);
        GL30.glDeleteVertexArrays(vao);
    }
}
