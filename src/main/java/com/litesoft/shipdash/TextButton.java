package com.litesoft.shipdash;

public class TextButton extends Button {
    String text;
    int solid;
    int hoverColor;
    int textColor;

    public TextButton(Main app, float x, float y, float sizeX, float sizeY, String text) {
        super(app, x, y, sizeX, sizeY);
        this.text = text;
    }

    void setSolid(int solid) {
        this.solid = solid;
    }

    void setHoverColor(int col) {
        hoverColor = col;
    }

    void setTextColor(int col) {
        textColor = col;
    }

    void draw() {
        app.pushMatrix();
        app.translate(x, y);
        app.scale(scale);
        app.fill(isHovered ? hoverColor : solid);
        app.rectMode(Main.CENTER);
        app.rect(0, 0, sizeX, sizeY, 16);

        app.fill(textColor);
        app.textFont(app.baseFont);
        app.text(text, 0, 0);
        app.popMatrix();
    }
}