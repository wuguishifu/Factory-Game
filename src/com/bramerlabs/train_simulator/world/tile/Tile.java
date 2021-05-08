package com.bramerlabs.train_simulator.world.tile;

import com.bramerlabs.engine.math.vector.Vector2f;
import com.bramerlabs.engine.math.vector.Vector3f;

public class Tile {

    protected static final Vector3f X_INC = Vector3f.normalize(new Vector3f((float) Math.cos(Math.toRadians(0)), 0, (float) Math.sin(Math.toRadians(0))), 1);
    protected static final Vector3f Y_INC = Vector3f.normalize(new Vector3f((float) Math.cos(Math.toRadians(60)), 0, (float) Math.sin(Math.toRadians(60))), 1);
    protected static final Vector3f Z_INC = Vector3f.normalize(new Vector3f((float) Math.cos(Math.toRadians(120)), 0, (float) Math.sin(Math.toRadians(120))), 1);

    private Vector3f position;
    private Vector3f renderPosition;

    private Hexagon hexagon;
    private static final float radius = 1.1f;

    /**
     * default constructor
     * @param axial - the axial position of this hexagonal tile
     */
    public Tile(Vector2f axial) {
        // generate hex grid with constraint x + y + z = 0
        // convert from axial coordinates to cube coordinates
        this.position = new Vector3f(axial.x, 0 - axial.x - axial.y, axial.y);

        // convert from cube coordinates to hex coordinates
        this.renderPosition = Vector3f.add(new Vector3f(0, 0, 0), Vector3f.normalize(X_INC, position.x * 2));
        this.renderPosition = Vector3f.add(this.renderPosition, Vector3f.normalize(Y_INC, position.y * 2));
        this.renderPosition = Vector3f.add(this.renderPosition, Vector3f.normalize(Z_INC, position.z * 2));

        this.hexagon = Hexagon.getInstance(renderPosition, radius);
        this.hexagon.createMesh();
    }

    /**
     * getter method
     * @return - the cube coordinate of this hexagonal tile
     */
    public Vector3f getCubePosition() {
        return this.position;
    }

    /**
     * getter method
     * @return - the hex coordinate of this hexagonal tile
     */
    public Vector3f getHexPosition() {
        return this.renderPosition;
    }

    public Hexagon getHexagon() {
        return this.hexagon;
    }

}
