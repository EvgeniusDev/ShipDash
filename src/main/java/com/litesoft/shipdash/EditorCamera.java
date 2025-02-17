package com.litesoft.shipdash;

public class EditorCamera extends Camera {
    float cursorX;
    float cursorY;
    float dragStartX;
    float dragStartY;
    boolean isDragging = false;

    public EditorCamera(Main app) {
        super(app);
    }

    public void scaleBy(float s) {
        float prevScale = scale;
        scale += s;
        scale = app.constrain(scale, minScale, maxScale);

        x = cursorX - (cursorX - x) * (scale / prevScale);
        y = cursorY - (cursorY - y) * (scale / prevScale);
    }

    public void drag() {
        if (isDragging) {
            x += cursorX - dragStartX;
            y += cursorY - dragStartY;
            dragStartX = cursorX;
            dragStartY = cursorY;
        }
    }

    public void startDrag() {
        isDragging = true;
        dragStartX = cursorX;
        dragStartY = cursorY;
    }

    public void endDrag() {
        isDragging = false;
    }

    @Override
    public void update() {
        cursorX = app.mouseX;
        cursorY = app.mouseY;

        super.update();
    }
}