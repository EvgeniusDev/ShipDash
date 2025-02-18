package com.litesoft.shipdash;

import processing.event.MouseEvent;

import java.util.ArrayList;

public abstract class Scene {
    Main app;
    boolean[] pressedKeys = new boolean[2000];
    boolean[] pressedKeyCodes = new boolean[2000];

    ArrayList<Button> buttons = new ArrayList<>();

    public Scene(Main app) {
        this.app = app;
    }

    void addButton(Button button) {
        buttons.add(button);
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
        for (Button btn : buttons) {
            if (btn.checkPressed()) {
                break;
            }
        }
    }

    public void mouseUp() {
        for (Button btn : buttons) {
            btn.endPressed();
        }
    }

    public void mouseWheel(MouseEvent event) {

    }

    public void update() {
        for (Button btn : buttons) {
            btn.update();
        }
    }

    public void draw() {
        for (Button btn : buttons) {
            btn.draw();
        }
    }
}
