package com.company;

import java.awt.*;

public class Spacecraft extends VectorSprite {
    int lives;
    boolean invin;
    boolean fastFire;
    public int counter2;

    public Spacecraft() {
        shape = new Polygon();

        shape.addPoint(15, 0);
        shape.addPoint(-10, 10);
        shape.addPoint(-10, -10);

        drawShape = new Polygon();

        drawShape.addPoint(15, 0);
        drawShape.addPoint(-10, 10);
        drawShape.addPoint(-10, -10);

        xPosition = 450;
        yPosition = 300;

        ROTATION = 0.1;
        THRUST = 0.25;

        active = true;
        lives = 3;
        invin = false;
        fastFire = false;
    }

    public void accelerate() {
        xSpeed += Math.cos(angle) * THRUST;
        ySpeed += Math.sin(angle) * THRUST;
    }

    public void reverse() {
        xSpeed -= Math.cos(angle) * THRUST;
        ySpeed -= Math.sin(angle) * THRUST;
    }

    public void rotateRight() {
        angle += ROTATION;
    }

    public void rotateLeft() {
        angle -= ROTATION;
    }

    public void hit() {
        active = false;
        counter = 0;
        lives--;
    }

    public void reset() {
        xPosition = 450;
        yPosition = 300;
        xSpeed = 0;
        ySpeed = 0;
        angle = 0;
        active = true;
    }
}
