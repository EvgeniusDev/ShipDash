package com.litesoft.shipdash;

import processing.data.JSONArray;
import processing.data.JSONObject;
import processing.event.MouseEvent;

import java.io.File;

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

    String levelName;

    public EditorScene(Main app, int mapWidth, int mapHeight, float tileSize, String levelName, boolean load) {
        super(app);
        this.tileSize = tileSize;
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.levelName = "data/" + levelName + ".json";

        sizeX = mapWidth * tileSize;
        sizeY = mapHeight * tileSize;
        map = new int[mapHeight][mapWidth];
        editorCamera = new EditorCamera(app);

        if (load) {
            loadLevel();
        }
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

            if (x >= 0 && x < mapWidth && y >= 0 && y < mapHeight) {
                map[y][x] = 1;
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
    public void keyDown() {
        if (app.key == 's') {
            saveLevel();
        }
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

        for (int y=0; y<mapHeight; y++) {
            for (int x=0; x<mapWidth; x++) {
                int num = map[y][x];

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

    void saveLevel() {
        var json = new JSONObject();
        var array = new JSONArray();
        int index = 0;

        for (int y=0; y<mapHeight; y++) {
            for (int x=0; x<mapWidth; x++) {
                array.setInt(index++, map[y][x]);
            }
        }

        json.setJSONArray("map", array);
        json.setInt("width", mapWidth);
        json.setInt("height", mapHeight);
        app.saveJSONObject(json, levelName);
    }

    void loadLevel() {
        var json = app.loadJSONObject(levelName);
        var array = json.getJSONArray("map");

        mapWidth = json.getInt("width");
        mapHeight = json.getInt("height");
        sizeX = mapWidth * tileSize;
        sizeY = mapHeight * tileSize;
        map = new int[mapHeight][mapWidth];

        for (int i=0; i<array.size(); i++) {
            map[i/mapWidth][i%mapWidth] = array.getInt(i);
        }
    }
}