package com.litesoft.shipdash;

import processing.event.MouseEvent;

public abstract class Scene {
    Main app;
    boolean[] pressedKeys = new boolean[2000];
    boolean[] pressedKeyCodes = new boolean[2000];

    public Scene(Main app) {
        this.app = app;
    }

    public void update() {

    }

    public void keyDown() {
        if (app.key < pressedKeys.length) {
            pressedKeys[app.key] = true;
        }

        if (app.keyCode < pressedKeyCodes.length) {
            pressedKeyCodes[app.keyCode] = true;
        }
    }

    public void keyUp() {
        if (app.key < pressedKeys.length) {
            pressedKeys[app.key] = false;
        }

        if (app.keyCode < pressedKeyCodes.length) {
            pressedKeyCodes[app.keyCode] = false;
        }
    }

    public void mouseDown() {

    }

    public void mouseUp() {

    }

    public void mouseWheel(MouseEvent event) {

    }

    public void draw() {

    }
}
