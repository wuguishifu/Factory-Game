package com.bramerlabs.engine.io.window;

import com.bramerlabs.engine.math.vector.Vector2f;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.GL46;

public class Input {

    private final GLFWKeyCallback keyboard;
    private final GLFWMouseButtonCallback mouseButtons;
    private final GLFWCursorPosCallback cursorPosition;
    private final GLFWWindowSizeCallback windowSize;
    private final GLFWWindowPosCallback windowPosition;
    private final GLFWScrollCallback scrollWheel;

    private final boolean[] keys = new boolean[GLFW.GLFW_KEY_LAST];
    private final boolean[] buttons = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];

    private double mouseX, mouseY;
    private double prevMouseX, prevMouseY;
    private double scrollX, scrollY;

    private int windowWidth, windowHeight;
    private boolean resized;
    private int windowX, windowY;

    /**
     * default constructor
     */
    public Input() {
        keyboard = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                keys[key] = (action != GLFW.GLFW_RELEASE);
            }
        };

        mouseButtons = new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                buttons[button] = (action != GLFW.GLFW_RELEASE);
            }
        };

        cursorPosition = new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
                prevMouseX = mouseX;
                prevMouseY = mouseY;
                mouseX = xpos;
                mouseY = ypos;
            }
        };

        windowSize = new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long window, int width, int height) {
                windowWidth = width;
                windowHeight = height;
                GL46.glViewport(0, 0, width, height);
                resized = true;
            }
        };

        windowPosition = new GLFWWindowPosCallback() {
            @Override
            public void invoke(long window, int xpos, int ypos) {
                windowX = xpos;
                windowY = ypos;
            }
        };

        scrollWheel = new GLFWScrollCallback() {
            @Override
            public void invoke(long window, double xoffset, double yoffset) {
                scrollX += xoffset;
                scrollY += yoffset;
            }
        };
    }

    public void destroy() {
        keyboard.free();
        mouseButtons.free();
        cursorPosition.free();
        scrollWheel.free();
        windowSize.free();
        windowPosition.free();
    }

    public GLFWKeyCallback getKeyboardCallback() {
        return keyboard;
    }

    public GLFWMouseButtonCallback getMouseButtons() {
        return mouseButtons;
    }

    public GLFWCursorPosCallback getCursorPosition() {
        return cursorPosition;
    }

    public GLFWWindowSizeCallback getWindowSize() {
        return windowSize;
    }

    public GLFWWindowPosCallback getWindowPosition() {
        return windowPosition;
    }

    public GLFWScrollCallback getScrollWheel() {
        return scrollWheel;
    }

    public float getMouseX() {
        return (float) mouseX;
    }

    public float getMouseY() {
        return (float) mouseY;
    }

    public double getPrevMouseX() {
        return prevMouseX;
    }

    public double getPrevMouseY() {
        return prevMouseY;
    }

    public double getScrollX() {
        return scrollX;
    }

    public double getScrollY() {
        return scrollY;
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public boolean isResized() {
        return resized;
    }

    public int getWindowX() {
        return windowX;
    }

    public int getWindowY() {
        return windowY;
    }

    public boolean isKeyDown(int key) {
        return keys[key];
    }

    public boolean[] getKeysDown() {
        return keys;
    }

    public boolean isMouseButtonDown(int button) {
        return buttons[button];
    }

    public boolean[] getButtonsDown() {
        return buttons;
    }

    public void setKeyDown(int key, boolean bool) {
        keys[key] = bool;
    }

    public void setButtonDown(int button, boolean bool) {
        buttons[button] = bool;
    }

    public void setWindowX(int windowX) {
        this.windowX = windowX;
    }

    public void setWindowY(int windowY) {
        this.windowY = windowY;
    }

    public void setMouseX(double mouseX) {
        this.mouseX = mouseX;
    }

    public void setMouseY(double mouseY) {
        this.mouseY = mouseY;
    }

    public void setPrevMouseX(double prevMouseX) {
        this.prevMouseX = prevMouseX;
    }

    public void setPrevMouseY(double prevMouseY) {
        this.prevMouseY = prevMouseY;
    }

    public void setScrollX(double scrollX) {
        this.scrollX = scrollX;
    }

    public void setScrollY(double scrollY) {
        this.scrollY = scrollY;
    }

    public void setWindowWidth(int windowWidth) {
        this.windowWidth = windowWidth;
    }

    public void setWindowHeight(int windowHeight) {
        this.windowHeight = windowHeight;
    }

    public void setResized(boolean resized) {
        this.resized = resized;
    }
}
