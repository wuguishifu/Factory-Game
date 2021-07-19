package com.bramerlabs.engine.objects.shapes_2d;

import com.bramerlabs.engine.graphics.Material;
import com.bramerlabs.engine.graphics.Mesh;
import com.bramerlabs.engine.graphics.Vertex;
import com.bramerlabs.engine.math.vector.Vector2f;
import com.bramerlabs.engine.objects.RenderObject;

public class Square extends RenderObject {

    public Square(Material material, Vector2f position, Vector2f rotation, Vector2f scale) {
        super(createMesh(material), position, rotation, scale);
    }

    public static Mesh createMesh(Material material) {
        return new Mesh(
                new Vertex[]{
                        new Vertex(new Vector2f(-0.5f, -0.5f), new Vector2f(0, 1)),
                        new Vertex(new Vector2f( 0.5f, -0.5f), new Vector2f(1, 1)),
                        new Vertex(new Vector2f( 0.5f,  0.5f), new Vector2f(1, 0)),
                        new Vertex(new Vector2f(-0.5f,  0.5f), new Vector2f(0, 0)),
                },
                new int[]{0, 1, 2, 0, 2, 3},
                material
        );
    }

}
