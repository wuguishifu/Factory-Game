package com.bramerlabs.engine.graphics;

import com.bramerlabs.engine.io.file_util.FileUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.IOException;

public class Material {

    // the path to the texture
    private String texturePath;

    // the format of the texture files
    private static final String FORMAT = "PNG";

    // dimension of the texture
    private float width, height;

    // the texture id
    private int textureID;

    // the texture interface
    private Texture texture;

    public Material(String texturePath) {
        this.texturePath = texturePath;
        this.create();
    }

    public void create() {
        // attempt to load textures
        try {
            texture = TextureLoader.getTexture(FORMAT,
                    FileUtils.class.getModule().getResourceAsStream(texturePath), GL11.GL_NEAREST);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error: could not load texture at " + texturePath);
        }

        // get the size
        width = texture.getWidth();
        height = texture.getHeight();

        // pointers
        textureID = texture.getTextureID();
    }

    public void destroy() {
        GL20.glDeleteTextures(textureID);
    }

    public float getWidth() {
        return this.width;
    }

    public float getHeight() {
        return this.height;
    }

    public int getTextureID() {
        return this.textureID;
    }

}
