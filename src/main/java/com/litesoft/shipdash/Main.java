package com.litesoft.shipdash;

import processing.core.PApplet;

public class Main extends PApplet {
    public static void main(String[] args) {
        PApplet.main("com.litesoft.shipdash.Main");
    }

    @Override
    public void settings() {
        size(960, 720);
    }

    @Override
    public void draw() {
        background(255);
    }
}