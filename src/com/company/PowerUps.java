package com.company;

import java.awt.*;

public class PowerUps extends VectorSprite{

    public String powerUpTypes[];
    public String type;
    public boolean invin = false;
    public boolean fastFire = false;
    public boolean extraLife = false;

    public PowerUps() {
        powerUpTypes = new String[] {"Invincibility", "Fast Fire", "Extra Life"};
        this.type = powerUpTypes[(int)Math.round(Math.random() * (powerUpTypes.length-1))];
        shape = new Polygon();

        shape.addPoint(10, 10);
        shape.addPoint(-10, 10);
        shape.addPoint(-10, -10);
        shape.addPoint(10, -10);

        drawShape = new Polygon();

        drawShape.addPoint(10, 10);
        drawShape.addPoint(-10, 10);
        drawShape.addPoint(-10, -10);
        drawShape.addPoint(10, -10);


        xPosition = Math.random() * 900;
        yPosition = Math.random() * 600;

        active = true;

        if(type == "Invincibility") {
            setInvincibility();
        }
        if(type == "Fast Fire") {
            setFastFire();
        }
        if(type == "Extra Life") {
            setExtraLife();
        }
    }

    public void paint(Graphics g) {
        g.fillPolygon(drawShape);
    }

    public void spawnPowerUps(){

    }

    public void setInvincibility() {
        invin = true;
    }

    public void setFastFire() {
        fastFire = true;
    }
    public void setExtraLife() {
        extraLife = true;
    }
}
