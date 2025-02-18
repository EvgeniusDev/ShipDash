package com.litesoft.shipdash;

public class PlayerShip extends GameObject {

    public PlayerShip(Main app, float x, float y) {
        super(app);
        this.x = x;
        this.y = y;
    }

    public void fly() {
        addForce(0, -5.5f);
    }
}
