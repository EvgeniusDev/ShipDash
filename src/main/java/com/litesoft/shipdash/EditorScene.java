package com.litesoft.shipdash;

import processing.data.JSONArray;
import processing.data.JSONObject;
import processing.event.MouseEvent;

public class EditorScene extends Scene {
    int mapWidth;
    int mapHeight;
    float tileSize;
    float sizeX;
    float sizeY;
    Tile[][] levelMap;

    float cursorX;
    float cursorY;
    float lastCursorX;
    float lastCursorY;

    EditorCamera editorCamera;
    SquareSelector selector;
    String levelName;

    public EditorScene(Main app, int mapWidth, int mapHeight, float tileSize, String levelName) {
        super(app);
        this.tileSize = tileSize;
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.levelName = "data/" + levelName + ".json";

        sizeX = mapWidth * tileSize;
        sizeY = mapHeight * tileSize;
        levelMap = new Tile[mapHeight][mapWidth];
        initMap();

        editorCamera = new EditorCamera(app);
        selector = new SquareSelector();
    }

    @Override
    public void update() {
        super.update();

        cursorX = (app.mouseX - editorCamera.x) / editorCamera.scale;
        cursorY = (app.mouseY - editorCamera.y) / editorCamera.scale;
        lastCursorX = (app.pmouseX - editorCamera.x) / editorCamera.scale;
        lastCursorY = (app.pmouseY - editorCamera.y) / editorCamera.scale;

        if (app.mousePressed) {
            int x = (int) (cursorX / tileSize);
            int y = (int) (cursorY / tileSize);

            if (pressedKeyCodes[Main.SHIFT] && selector.isSelection) {
                selector.updateSelection(cursorX, cursorY);
                return;
            }

            if (x >= 0 && x < mapWidth && y >= 0 && y < mapHeight) {
                if (app.mouseButton == Main.LEFT) {
                    levelMap[y][x].id = 1;
                } else if (app.mouseButton == Main.RIGHT) {
                    levelMap[y][x].id = 0;
                }
            }

            if (app.mouseButton == Main.CENTER) {
                editorCamera.drag();
            }
        }
    }

    @Override
    public void mouseDown() {
        super.mouseDown();

        if (app.mouseButton == Main.CENTER) {
            editorCamera.startDrag();
        }

        if (pressedKeyCodes[Main.SHIFT]) {
            if (app.mouseButton == Main.LEFT) {
                selector.startSelection(cursorX, cursorY, 0);
            }
            else if (app.mouseButton == Main.RIGHT) {
                selector.startSelection(cursorX, cursorY, 1);
            }
        }
    }

    @Override
    public void mouseUp() {
        super.mouseUp();

        if (selector.isSelection) {
            for (int y=0; y<mapHeight; y++) {
                for (int x=0; x<mapWidth; x++) {
                    Tile tile = levelMap[y][x];
                    float screenSize = tileSize * editorCamera.scale;
                    float selStartX = Math.min(selector.startX, selector.startX + selector.sizeX);
                    float selStartY = Math.min(selector.startY, selector.startY + selector.sizeY);
                    float selEndX = Math.max(selector.startX, selector.startX + selector.sizeX);
                    float selEndY = Math.max(selector.startY, selector.startY + selector.sizeY);

                    if (tile.x + screenSize > selStartX && tile.x < selEndX && tile.y + screenSize > selStartY && tile.y < selEndY) {
                        tile.id = selector.mode == 0 ? 1 : 0;
                    }
                }
            }
        }

        editorCamera.endDrag();
        selector.endSelection();
    }

    @Override
    public void mouseWheel(MouseEvent event) {
        super.mouseWheel(event);
        editorCamera.scaleBy(event.getCount() == -1 ? -0.1f : 0.1f);
    }

    @Override
    public void keyDown() {
        super.keyDown();

        if (pressedKeys['s']) {
            saveLevel();
        }

        if (pressedKeys['l']) {
            loadLevel();
        }
    }

    @Override
    public void draw() {
        super.draw();

        editorCamera.begin();
        editorCamera.update();
        drawMap();
        drawGrid();
        drawSelector();
        editorCamera.end();
    }

    void drawMap() {
        app.noStroke();

        for (int y=0; y<mapHeight; y++) {
            for (int x=0; x<mapWidth; x++) {
                int num = levelMap[y][x].id;

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

    void drawSelector() {
        if (!selector.isSelection) {
            return;
        }

        app.stroke(0, 0, 50);
        app.fill(0, 0, 200, 100);
        app.rect(selector.startX, selector.startY, selector.sizeX, selector.sizeY);
    }

    void saveLevel() {
        var json = new JSONObject();
        var array = new JSONArray();
        int index = 0;

        for (int y=0; y<mapHeight; y++) {
            for (int x=0; x<mapWidth; x++) {
                array.setInt(index++, levelMap[y][x].id);
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
        initMap();

        for (int i=0; i<array.size(); i++) {
            levelMap[i/mapWidth][i%mapWidth].id = array.getInt(i);
        }
    }

    void initMap() {
        levelMap = new Tile[mapHeight][mapWidth];

        for (int y=0; y<mapHeight; y++) {
            for (int x=0; x<mapWidth; x++) {
                levelMap[y][x] = new Tile(x * tileSize, y * tileSize);
            }
        }
    }
}