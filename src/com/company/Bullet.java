package com.company;

import java.awt.*;

public class Bullet extends VectorSprite {

    public Bullet(double shipX, double shipY, double shipAngle, int thrust) {
        shape = new Polygon();

        shape.addPoint(1, 1);
        shape.addPoint(-1, 1);
        shape.addPoint(-1, -1);
        shape.addPoint(1, -1);

        drawShape = new Polygon();

        drawShape.addPoint(1, 1);
        drawShape.addPoint(-1, 1);
        drawShape.addPoint(-1, -1);
        drawShape.addPoint(1, -1);

        xPosition = shipX;
        yPosition = shipY;
        angle = shipAngle;

        THRUST = thrust;
        xSpeed = Math.cos(angle) * THRUST;
        ySpeed = Math.sin(angle) * THRUST;

        active = true;
    }
}
