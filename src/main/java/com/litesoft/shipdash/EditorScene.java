package com.litesoft.shipdash;

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

    EditorCamera editorCamera;

    public EditorScene(Main app, int mapWidth, int mapHeight, float tileSize) {
        super(app);

        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.tileSize = tileSize;

        sizeX = mapWidth * tileSize;
        sizeY = mapHeight * tileSize;
        map = new int[mapWidth][mapHeight];
        editorCamera = new EditorCamera(app);

        map[15][15]=1;
    }

    @Override
    public void update() {
        super.update();

        cursorX = (app.mouseX - editorCamera.x) / editorCamera.scale;
        cursorY = (app.mouseY - editorCamera.y) / editorCamera.scale;
        lastCursorX = (app.pmouseX - editorCamera.x) / editorCamera.scale;
        lastCursorY = (app.pmouseY - editorCamera.y) / editorCamera.scale;

        if (app.mousePressed && app.mouseButton == Main.RIGHT) {
            editorCamera.drag();
        }

        if (app.mousePressed && app.mouseButton == Main.LEFT) {
            int x = (int) (cursorX / tileSize);
            int y = (int) (cursorY / tileSize);
            app.println(x);

            if (x >= 0 && x < mapWidth && y >= 0 && y < mapHeight) {
                map[x][y] = 1;
            }
        }
    }

    @Override
    public void mouseDown() {
        super.mouseDown();

        if (app.mouseButton == Main.RIGHT) {
            editorCamera.startDrag();
        }
    }

    @Override
    public void mouseUp() {
        super.mouseUp();
        editorCamera.endDrag();
    }

    @Override
    public void mouseWheel(MouseEvent event) {
        super.mouseWheel(event);
        editorCamera.scaleBy(event.getCount() == -1 ? -0.1f : 0.1f);
    }

    @Override
    public void draw() {
        super.draw();

        editorCamera.begin();
        editorCamera.update();
        drawMap();
        drawGrid();
        editorCamera.end();
    }

    void drawMap() {
        app.noStroke();

        for (int x=0; x<mapWidth; x++) {
            for (int y=0; y<mapHeight; y++) {
                int num = map[x][y];

                if (num == 0) {
                    continue;
                }

                app.fill(255, 0, 0);
                app.rect(x * tileSize, y * tileSize, tileSize, tileSize);
            }
        }
    }

    void drawGrid() {
        app.stroke(80);

        for (float x=0; x<sizeX + tileSize; x+=tileSize) {
            app.line(x, 0, x, sizeY);
        }

        for (float y=0; y<sizeY + tileSize; y+=tileSize) {
            app.line(0, y, sizeX, y);
        }
    }
}
