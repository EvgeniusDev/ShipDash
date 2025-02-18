package com.litesoft.shipdash;

public class SquareSelector {
    int mode = 0;
    float startX;
    float startY;
    float sizeX;
    float sizeY;
    boolean isSelection;

    public void startSelection(float mx, float my, int mode) {
        startX = mx;
        startY = my;
        isSelection = true;
        this.mode = mode;
    }

    public void updateSelection(float mx, float my) {
        if (!isSelection) {
            return;
        }

        sizeX = mx - startX;
        sizeY = my - startY;
    }

    public void endSelection() {
        isSelection = false;
    }
}
