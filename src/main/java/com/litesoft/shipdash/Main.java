package com.litesoft.shipdash;

import processing.core.PApplet;

public class Main extends PApplet {
    Scene currentScene;

    public static void main(String[] args) {
        PApplet.main("com.litesoft.shipdash.Main");
    }

    @Override
    public void settings() {
        size(960, 720);
    }

    @Override
    public void setup() {

    }

    @Override
    public void draw() {
        background(255);
        currentScene.control();
        currentScene.update();
        currentScene.draw();
    }

    @Override
    public void mousePressed() {
        currentScene.mouseDown();
    }

    @Override
    public void mouseReleased() {
        currentScene.mouseUp();
    }
}