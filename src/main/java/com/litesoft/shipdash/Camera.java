package com.litesoft.shipdash;

public class Camera {
    Main app;
    float x;
    float y;
    float scale = 1;
    float minScale = 0.25f;
    float maxScale = 4f;

    public Camera(Main app) {
        this.app = app;
    }

    public void begin() {
        app.pushMatrix();
    }

    public void update() {
        app.translate(x, y);
        app.scale(scale);
    }

    public void end() {
        app.popMatrix();
    }
}
