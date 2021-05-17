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
    private Hexagon selectionHexagon;
    private static final float radius = 1.1f;

    private boolean selected = false;

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

        this.hexagon = Hexagon.getInstance(renderPosition, radius, Hexagon.defaultHeight);
        this.hexagon.createMesh();
        this.selectionHexagon = Hexagon.getInstance(renderPosition, radius + 0.05f, Hexagon.selectionHeight);
        this.selectionHexagon.createMesh();
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

    /**
     * getter method
     * @return - the hexagon used to render this tile
     */
    public Hexagon getHexagon() {
        return this.hexagon;
    }

    /**
     * getter method
     * @return - the hexagon used to render the selection of this tile
     */
    public Hexagon getSelectionHexagon() {
        return this.selectionHexagon;
    }

    /**
     * toggles if this tile is selected or not
     */
    public void toggleSelected() {
        this.selected = !this.selected;
    }

    /**
     * getter method
     * @return - true if the tile is currently selected
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * determines if a point is within the bounding box of the hexagon
     * @param point - the point
     * @return - true if the point is within the bounding box of the hexagon
     */
    public boolean containsPoint(Vector3f point) {
        if (point.y > position.y + Hexagon.defaultHeight/2 || point.y < position.y - Hexagon.defaultHeight/2) {
            return false;
        }
        return (Vector2f.distance(point.xz(), position.xz()) < Math.sqrt(3) * 0.5f * hexagon.radius);
    }

}
