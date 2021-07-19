package com.bramerlabs.engine.graphics;

import com.bramerlabs.engine.math.vector.Vector2f;

public class Vertex {

    // position of this vertex
    public final Vector2f position;

    // texture coordinate
    public final Vector2f textureCoord;

    public Vertex(Vector2f position, Vector2f textureCoord) {
        this.position = position;
        this.textureCoord = textureCoord;
    }
}
