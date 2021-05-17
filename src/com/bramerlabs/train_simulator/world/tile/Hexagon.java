package com.bramerlabs.train_simulator.world.tile;

import com.bramerlabs.engine.graphics.Mesh;
import com.bramerlabs.engine.graphics.Vertex;
import com.bramerlabs.engine.math.vector.Vector3f;
import com.bramerlabs.engine.math.vector.Vector4f;
import com.bramerlabs.engine.objects.RenderObject;
import com.bramerlabs.engine.objects.shapes.shapes_2d.Triangle;

import java.awt.*;
import java.util.ArrayList;

public class Hexagon extends RenderObject {

    protected static final double[] hexPointAngles = new double[]{Math.toRadians(30), Math.toRadians(90), Math.toRadians(150), Math.toRadians(210), Math.toRadians(270), Math.toRadians(330), Math.toRadians(30)};
    protected static final double[] hexFaceAngles = new double[]{-Math.toRadians(0), -Math.toRadians(60), -Math.toRadians(120), -Math.toRadians(180), -Math.toRadians(240), -Math.toRadians(300), -Math.toRadians(0)};
    protected static final float defaultHeight = 0.2f;
    protected static final float selectionHeight = 0.3f;

    protected float radius;

    protected static Vector3f[] hexCornerPoints = new Vector3f[]{
            new Vector3f((float) Math.cos(hexPointAngles[6]), 0, (float) Math.sin(hexPointAngles[6])),
            new Vector3f((float) Math.cos(hexPointAngles[5]), 0, (float) Math.sin(hexPointAngles[5])),
            new Vector3f((float) Math.cos(hexPointAngles[4]), 0, (float) Math.sin(hexPointAngles[4])),
            new Vector3f((float) Math.cos(hexPointAngles[3]), 0, (float) Math.sin(hexPointAngles[3])),
            new Vector3f((float) Math.cos(hexPointAngles[2]), 0, (float) Math.sin(hexPointAngles[2])),
            new Vector3f((float) Math.cos(hexPointAngles[1]), 0, (float) Math.sin(hexPointAngles[1])),
            new Vector3f((float) Math.cos(hexPointAngles[0]), 0, (float) Math.sin(hexPointAngles[0])),
    };

    /**
     * default constructor for specified values
     *
     * @param mesh     - the mesh that this object is made of
     * @param position - the position of this object
     * @param rotation - the rotation of this object
     * @param scale    - the scale of this object
     */
    public Hexagon(Mesh mesh, Vector3f position, Vector3f rotation, Vector3f scale) {
        super(mesh, position, rotation, scale);
    }

    public static Hexagon getInstance(Vector3f position, float radius, float height) {
        Hexagon hexagon = new Hexagon(generateMesh(radius, height), position, new Vector3f(0), new Vector3f(1));
        hexagon.radius = radius;
        return hexagon;
    }

    public static Mesh generateMesh(float radius, float height) {
        Vector4f color = new Vector4f(0.5f, 0.5f, 0.5f, 1.0f);
        if (height == selectionHeight) {
            color = new Vector4f(Vector3f.divide(new Vector3f(new Color(247, 255, 121)), new Vector3f(255)), 0.3f);
        }

        ArrayList<Triangle> triangles = generateTriangles(radius, height);

        Vertex[] vertices = new Vertex[triangles.size() * 3];
        for (int i = 0; i < triangles.size(); i++) {
            Triangle t = triangles.get(i);
            vertices[3 * i]     = new Vertex(t.getV1(), color, t.getNormal());
            vertices[3 * i + 1] = new Vertex(t.getV2(), color, t.getNormal());
            vertices[3 * i + 2] = new Vertex(t.getV3(), color, t.getNormal());
        }

        int[] indices = new int[triangles.size() * 3];
        for (int i = 0; i < triangles.size() * 3; i++) {
            indices[i] = i;
        }

        return new Mesh(vertices, indices, null);
    }

    public static ArrayList<Triangle> generateTriangles(float radius, float height) {
        Vector3f p1 = new Vector3f(0, height/2, 0);
        Vector3f p2 = new Vector3f(0, -height/2, 0);
        ArrayList<Triangle> triangles = new ArrayList<>();

        Vector3f[] hexPoints = new Vector3f[7];
        for (int i = 0; i < 7; i++) {
            hexPoints[i] = Vector3f.normalize(hexCornerPoints[i], radius);
        }

        for (int i = 0; i < 6; i++) {
            // hex face
            triangles.add(new Triangle(p1, Vector3f.add(p1, hexPoints[i]), Vector3f.add(p1, hexPoints[i + 1]), Vector3f.e2));
            triangles.add(new Triangle(p2, Vector3f.add(p2, hexPoints[5 - i + 1]), Vector3f.add(p2, hexPoints[5 - i]), Vector3f.flip(Vector3f.e2)));

            // rectangle face
            triangles.add(new Triangle(Vector3f.add(p1, hexPoints[i]), Vector3f.add(p2, hexPoints[i]), Vector3f.add(p1, hexPoints[i + 1]), new Vector3f((float) Math.cos(hexFaceAngles[i]), 0, (float) Math.sin(hexFaceAngles[i]))));
            triangles.add(new Triangle(Vector3f.add(p1, hexPoints[i + 1]), Vector3f.add(p2, hexPoints[i]), Vector3f.add(p2, hexPoints[i + 1]), new Vector3f((float) Math.cos(hexFaceAngles[i]), 0, (float) Math.sin(hexFaceAngles[i]))));
        }
        return triangles;
    }
}