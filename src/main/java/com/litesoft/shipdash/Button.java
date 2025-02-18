package com.litesoft.shipdash;

public class Button {
    Main app;
    float x, y;
    float sizeX, sizeY;
    boolean isPressed;
    boolean isHovered;
    float baseScale = 1;
    float hoverScale = 1.15f;
    float scale = baseScale;
    float scaleSpeed = 0.025f;

    ClickListener clickListener;

    public Button(Main app, float x, float y, float sizeX, float sizeY) {
        this.app = app;
        this.x = x;
        this.y = y;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    void update() {
        isHovered = checkHovered();

        if (isHovered) {
            scale += scaleSpeed;

            if (scale > hoverScale) {
                scale = hoverScale;
            }
        }
        else {
            scale -= scaleSpeed;

            if (scale < baseScale) {
                scale = baseScale;
            }
        }
    }

    void draw() {
    }

    void setClickListener(ClickListener listener) {
        clickListener = listener;
    }

    boolean checkPressed() {
        isPressed = checkHovered();
        return isPressed;
    }

    boolean checkHovered() {
        isHovered = app.mouseX > x - sizeX / 2 && app.mouseX < x + sizeX / 2 && app.mouseY > y - sizeY / 2 && app.mouseY < y + sizeY / 2;
        return isHovered;
    }

    void endPressed() {
        if (isPressed && isHovered) {
            clickListener.clicked();
        }

        isPressed = false;
    }

    public interface ClickListener {
        public void clicked();
    }
}