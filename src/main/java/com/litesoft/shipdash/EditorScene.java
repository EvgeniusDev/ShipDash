package com.litesoft.shipdash;

import processing.core.PApplet;
import processing.event.MouseEvent;

public class EditorScene extends Scene {
    int mapWidth;
    int mapHeight;
    float tileSize;
    float sizeX;
    float sizeY;
    int[][] map;

    float cursorX;
    float cursorY;
    float lastCursorX;
    float lastCursorY;

    Camera camera;

    public EditorScene(Main app, int mapWidth, int mapHeight, float tileSize) {
        super(app);
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.tileSize = tileSize;

        sizeX = mapWidth * tileSize;
        sizeY = mapHeight * tileSize;
        map = new int[mapWidth][mapHeight];
        camera = new Camera(app);
    }

    @Override
    public void update() {
        super.update();

        cursorX = app.mouseX;
        cursorY = app.mouseY;
        lastCursorX = app.pmouseX;
        lastCursorY = app.pmouseY;

        if (app.mousePressed && app.mouseButton == Main.RIGHT) {
            camera.drag();
        }
    }

    @Override
    public void mouseDown() {
        super.mouseDown();

        if (app.mouseButton == Main.RIGHT) {
            camera.startDrag();
        }
    }

    @Override
    public void mouseUp() {
        super.mouseUp();
        camera.endDrag();
    }

    @Override
    public void mouseWheel(MouseEvent event) {
        super.mouseWheel(event);
        camera.scaleBy(event.getCount() == -1 ? -0.1f : 0.1f);
    }

    @Override
    public void draw() {
        super.draw();

        camera.begin();
        camera.update();
        drawGrid();
        camera.end();
    }

    private void drawGrid() {
        app.stroke(80);

        for (float x=0; x<sizeX + tileSize; x+=tileSize) {
            app.line(x, 0, x, sizeY);
        }

        for (float y=0; y<sizeY + tileSize; y+=tileSize) {
            app.line(0, y, sizeX, y);
        }
    }
}
