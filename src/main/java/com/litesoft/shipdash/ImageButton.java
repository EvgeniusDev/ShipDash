package com.litesoft.shipdash;

import processing.core.PImage;

public class ImageButton extends Button {
    PImage img;

    public ImageButton(Main app, float x, float y, float sizeX, float sizeY, String path) {
        super(app, x, y, sizeX, sizeY);
        this.img = app.loadImage(path);
    }

    ImageButton(Main app, float x, float y, float sizeX, float sizeY, PImage img) {
        super(app, x, y, sizeX, sizeY);
        this.img = img;
    }

    void draw() {
        app.pushMatrix();
        app.translate(x, y);
        app.scale(scale);
        app.tint(isHovered ? 180 : 255);
        app.imageMode(Main.CENTER);
        app.image(img, 0, 0, sizeX, sizeY);
        app.noTint();
        app.popMatrix();
    }
}