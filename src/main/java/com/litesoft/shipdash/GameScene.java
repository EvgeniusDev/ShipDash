package com.litesoft.shipdash;

import processing.core.PImage;

import java.util.ArrayList;


public class GameScene extends Scene {

    int mapWidth;
    int mapHeight;
    float sizeX;
    float sizeY;
    float tileSize;
    String levelName;

    ArrayList<GameObject> gameObjects = new ArrayList<>();
    PlayerShip playerShip;
    float playerShipStartPosX = 150;

    public GameScene(Main app, int mapWidth, int mapHeight, float tileSize, String levelName) {
        super(app);

        this.tileSize = tileSize;
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.levelName = "data/" + levelName + ".json";

        sizeX = mapWidth * tileSize;
        sizeY = mapHeight * tileSize;

        playerShip = new PlayerShip(app, playerShipStartPosX, app.height / 2.0f);
        playerShip.setImage(app.loadImage("data/space ship.png"));
        gameObjects.add(playerShip);

//        for (int i = 0; i < 100; i++) {
//            if (app.random(10) > 6) {
//                createBlock(500 + i * tileSize, tileSize * (int) (app.random(0, app.height / tileSize + 1)));
//            }
//        }
    }

    private void createBlock(float x, float y) {
        Block block = new Block(app, x, y);
        block.setImage(app.loadImage("data/tile0.png"));
        gameObjects.add(block);
    }

    private void updateGravity() {
        playerShip.addForce(0, 2.7f);
    }

    private void movePlayerShip() {
        playerShip.addForce(1f, 0);
    }

    private void control() {
        if (Main.isKeyPressed[' ']) {
            playerShip.fly();
        }
    }

    public void update() {
        updateGravity();
        movePlayerShip();
        control();
        super.update();
        for (var gameObject: gameObjects) {
            gameObject.update();
        }

        if ( app.frameCount % 15 == 0) {
            createBlock(playerShip.x + app.width, tileSize * (int) (app.random(0, app.height / tileSize + 1)));
        }
    }

    public void draw() {
        super.draw();

        app.pushMatrix();
        app.translate(- (playerShip.x - playerShipStartPosX), 0);
        for (var gameObject: gameObjects) {
            gameObject.draw();
        }
        app.popMatrix();
    }
}
