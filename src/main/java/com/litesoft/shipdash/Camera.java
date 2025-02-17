package com.litesoft.shipdash;

public class Camera {
    Main app;
    float x, y;
    float scale = 1;
    float minScale = 0.25f, maxScale = 4f;
    boolean isDragging = false;
    float dragStartX, dragStartY;
    float cursorX;
    float cursorY;

    public Camera(Main app) {
        this.app = app;
    }

    public void scaleBy(float s) {
        float prevScale = scale;
        scale += s;
        scale = app.constrain(scale, minScale, maxScale);

        x = cursorX - (cursorX - x) * (scale / prevScale);
        y = cursorY - (cursorY - y) * (scale / prevScale);
    }

    public void drag() {
        if (isDragging) {
            x += cursorX - dragStartX;
            y += cursorY - dragStartY;
            dragStartX = cursorX;
            dragStartY = cursorY;
        }
    }

    public void startDrag() {
        isDragging = true;
        dragStartX = cursorX;
        dragStartY = cursorY;
    }

    public void endDrag() {
        isDragging = false;
    }

    public void begin() {
        app.pushMatrix();
    }

    public void update() {
        cursorX = app.mouseX;
        cursorY = app.mouseY;

        app.translate(x, y);
        app.scale(scale);
    }

    public void end() {
        app.popMatrix();
    }
}