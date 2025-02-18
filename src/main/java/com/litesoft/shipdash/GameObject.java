package com.litesoft.shipdash;

import processing.core.PImage;

import static processing.core.PApplet.abs;

public abstract class GameObject {

    Main app;
    float x, y, vx, vy, ax, ay, size = 60, angle, mass = 1;
    float maxVelX = 16, maxVelY = 20;
    PImage img;

    public GameObject(Main app) {
        this.app = app;
    }

    public void setImage(PImage img) {
        this.img = img;
    }

    public void addForce(float fx, float fy) {
        ax += fx / mass;
        ay += fy / mass;
    }

    public void update() {
        vx += ax;
        vy += ay;
        if (abs(vy) > maxVelY && vy > 0) {
            vy = maxVelY;
        }
        if (abs(vy) > maxVelY && vy < 0) {
            vy = -maxVelY;
        }
        if (abs(vx) > maxVelX && vx > 0) {
            vx = maxVelX;
        }
        if (abs(vx) > maxVelX && vx < 0) {
            vx = -maxVelX;
        }
        angle = vy / 28.0f;
        x += vx;
        y += vy;
        ax = 0;
        ay = 0;
    }

    public void draw() {
        app.pushMatrix();
        app.translate(x, y);
        app.rotate(angle);
        app.imageMode(app.CENTER);
        app.image(img, 0, 0, size, size);
        app.popMatrix();
    }
}
