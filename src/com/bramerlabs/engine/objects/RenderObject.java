package com.bramerlabs.engine.objects;

import com.bramerlabs.engine.graphics.Mesh;
import com.bramerlabs.engine.math.vector.Vector2f;

public class RenderObject {

    protected Vector2f position, scale;
    protected float rotation;
    protected final Mesh mesh;

    public RenderObject(Mesh mesh, Vector2f position, float rotation, Vector2f scale) {
        this.mesh = mesh;
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    public Vector2f getPosition() {
        return this.position;
    }

    public float getRotation() {
        return this.rotation;
    }

    public Vector2f getScale() {
        return this.scale;
    }

    public Mesh getMesh() {
        return this.mesh;
    }

    public void destroy() {
        mesh.destroy();
    }

}
