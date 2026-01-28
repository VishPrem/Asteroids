package com.company;

import java.awt.*;

public class Asteroid extends VectorSprite{

    int size;

    public Asteroid() {
        size = 1;
        initializeAsteroid();
    }

    public Asteroid(double x, double y, int s) {
        size = s;
        initializeAsteroid();
        xPosition = x;
        yPosition = y;
    }

    public void initializeAsteroid () {
        shape = new Polygon();

        shape.addPoint(30 / size, 3 / size);
        shape.addPoint(5 / size, 35 / size);
        shape.addPoint(-25 / size, 10 / size);
        shape.addPoint(-17 / size, -15 / size);
        shape.addPoint(20 / size, -35 / size);

        drawShape = new Polygon();

        drawShape.addPoint(30 / size, 3 / size);
        drawShape.addPoint(5 / size, 35 / size);
        drawShape.addPoint(-25 / size, 10 / size);
        drawShape.addPoint(-17 / size, -15 / size);
        drawShape.addPoint(20 / size, -35 / size);

        ROTATION = (Math.random() / 4) * (size/1.25);
        THRUST = 0.25;

        double h, a;
        h = Math.random() + 1;
        a = Math.random() * (2 * Math.PI);
        xSpeed = Math.cos(a) * h * (size/1.25);
        ySpeed = Math.sin(a) * h * (size/1.25);

        h = Math.random() * 400 + 100;
        a = Math.random() * (2 * Math.PI);
        xPosition = Math.cos(a) * h + 450;
        yPosition = Math.sin(a) * h + 300;

        active = true;
    }

    public void updatePosition() {
        angle += ROTATION;
        super.updatePosition();
    }
}
