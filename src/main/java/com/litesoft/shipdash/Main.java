package com.litesoft.shipdash;

import processing.core.PApplet;
import processing.event.MouseEvent;

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
        currentScene = new EditorScene(this, 16, 16, 32);
    }

    @Override
    public void draw() {
        background(255);
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

    @Override
    public void mouseWheel(MouseEvent event) {
        currentScene.mouseWheel(event);
    }

    @Override
    public void keyPressed() {
        currentScene.keyDown();
    }

    @Override
    public void keyReleased() {
        currentScene.keyUp();
    }
}