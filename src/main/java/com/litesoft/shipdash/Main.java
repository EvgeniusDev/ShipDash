package com.litesoft.shipdash;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import processing.event.MouseEvent;

public class Main extends PApplet {
    PFont baseFont;
    PFont titleFont;
    PImage spaceImage;
    Scene currentScene;

    public static boolean[] isKeyPressed = new boolean[300];

    public static void main(String[] args) {
        PApplet.main("com.litesoft.shipdash.Main");
    }

    @Override
    public void settings() {
        size(920, 920);
        //fullScreen();
    }

    @Override
    public void setup() {

        baseFont = createFont("data/Wadik.otf", 18);
        titleFont = createFont("data/Stage Wanders.ttf", 64);
        spaceImage = loadImage("data/space.png");
        //currentScene = new EditorScene(this, 128, 32, 32, "level");
        currentScene = new GameScene(this, 128, 32, 32, "level");
    }

    @Override
    public void draw() {
        background(255);
        imageMode(CORNER);
        image(spaceImage, 0, 0, width, height);
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
        isKeyPressed[key] = true;
    }

    @Override
    public void keyReleased() {
        currentScene.keyUp();
        isKeyPressed[key] = false;
    }
}