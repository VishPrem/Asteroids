package com.company;

import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class Game extends JFrame implements KeyListener, ActionListener {
    public Window panel;
    public Menu mainMenu;
    Timer timer;
    Spacecraft ship;
    ArrayList<Asteroid> asteroidList;
    ArrayList<Bullet> bulletList;
    ArrayList<Debris> debrisList;
    ArrayList<PowerUps> powerUpList;
    Image offScreen;
    Graphics offG;
    AudioUtil au;
    AudioClip laser;
    AudioClip shipExplosion;
    AudioClip asteroidExplosion;
    AudioClip thruster;
    boolean upKey, rightKey, leftKey, downKey, spaceBar;
    boolean startGame = false, started = false;
    int score;
    int powerUpSpawnTimer;

    public void init() throws MalformedURLException {
        this.setVisible(true);
        this.setSize(900, 600);
        this.setTitle("Asteroids - by Praviin Premsankar");
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.addKeyListener(this);

        offScreen = createImage(this.getWidth(), this.getHeight());
        offG = offScreen.getGraphics();
        timer = new Timer(20, this);
        timer.start();
        ship = new Spacecraft();
        asteroidList = new ArrayList<>();
        score = 0;
        powerUpSpawnTimer = 0;
        bulletList = new ArrayList<>();
        debrisList = new ArrayList<>();
        powerUpList = new ArrayList<>();
        au = AudioUtil.getInstance();
        laser = Applet.newAudioClip(au.transform(new File("./src/Sounds/laser79.wav")));
        shipExplosion = Applet.newAudioClip(au.transform(new File("./src/Sounds/explode0.wav")));
        asteroidExplosion = Applet.newAudioClip(au.transform(new File("./src/Sounds/explode1.wav")));
        thruster = Applet.newAudioClip(au.transform(new File("./src/Sounds/thruster.wav")));
        add(this.mainMenu = new Menu(this), BorderLayout.CENTER);
        pack();
    }

    public void update(Graphics g) {
        paint(g);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            rightKey = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            leftKey = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            upKey = true;
            thruster.play();
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            downKey = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            spaceBar = true;
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            rightKey = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            leftKey = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            upKey = false;
            thruster.stop();
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            downKey = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            spaceBar = false;
        }
    }

    private void keyCheck() {
        if (rightKey) {
            ship.rotateRight();
        }
        if (leftKey) {
            ship.rotateLeft();
        }
        if (downKey) {
            ship.reverse();
        }
        if (spaceBar) {
            fireBullet();
        }
        if (upKey) {
            ship.accelerate();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(startGame && !started) {
            remove(this.mainMenu);
            add(this.panel = new Window(this), BorderLayout.CENTER);
            pack();
            started = true;
            for (int i = 0; i < 6; i++) {
                asteroidList.add(new Asteroid());
            }
        }
        keyCheck();
        respawnShip();
        ship.updatePosition();
        for (int i = 0; i < asteroidList.size(); i++) {
            asteroidList.get(i).updatePosition();
        }
        for (int i = 0; i < bulletList.size(); i++) {
            bulletList.get(i).updatePosition();
            if (bulletList.get(i).counter == 90 || !bulletList.get(i).active) {
                bulletList.remove(i);
            }
        }
        for (int i = 0; i < debrisList.size(); i++) {
            debrisList.get(i).updatePosition();
            if (debrisList.get(i).counter == 25 || !debrisList.get(i).active) {
                debrisList.remove(i);
            }
        }
        if(powerUpList.isEmpty()) {
            powerUpList.add(new PowerUps());
//            powerUpSpawnTimer += 1;
//            if(powerUpSpawnTimer == 300)
//
//                powerUpSpawnTimer = 0;
        }
        for (int i = 0; i < powerUpList.size(); i++) {
            powerUpList.get(i).updatePosition();
            if (powerUpList.get(i).counter == 300 || !powerUpList.get(i).active) {
                powerUpList.remove(i);
            }
        }
        if (ship.invin) {
            ship.counter2 += 1;
            if (ship.counter2 == 300) {
                ship.invin = false;
            }
        }

        if (ship.fastFire) {
            ship.counter2 += 1;
            if (ship.counter2 == 300) {
                ship.fastFire = false;
            }
        }
        checkCollisions();
        checkAsteroidDestruction();
    }

    public boolean collision(VectorSprite thing1, VectorSprite thing2) {
        int x, y;
        for (int i = 0; i < thing1.drawShape.npoints; i++) {
            x = thing1.drawShape.xpoints[i];
            y = thing1.drawShape.ypoints[i];

            if (thing2.drawShape.contains(x, y)) {
                return true;
            }
        }

        for (int i = 0; i < thing2.drawShape.npoints; i++) {
            x = thing2.drawShape.xpoints[i];
            y = thing2.drawShape.ypoints[i];

            if (thing1.drawShape.contains(x, y)) {
                return true;
            }
        }

        return false;
    }

    public void checkCollisions() {
        for (int i = 0; i < asteroidList.size(); i++) {
            double randomnum;
            if (collision(ship, asteroidList.get(i)) && ship.active) {
                if(ship.invin) {
                    score += 10 * asteroidList.get(i).size;
                    asteroidList.get(i).active = false;
                    randomnum = Math.random() * 5 + 5;
                    for (int r = 0; r < randomnum; r++) {
                        debrisList.add(new Debris(asteroidList.get(i).xPosition, asteroidList.get(i).yPosition));

                    }
                }

                else{
                    score -= 100;
                    ship.hit();
                    shipExplosion.play();
                    randomnum = Math.random() * 5 + 5;
                    for (int r = 0; r < randomnum; r++) {
                        debrisList.add(new Debris(ship.xPosition, ship.yPosition));
                    }
                }
            }
            for (int j = 0; j < bulletList.size(); j++) {
                if (collision(bulletList.get(j), asteroidList.get(i))) {
                    score += 10 * asteroidList.get(i).size;
                    bulletList.get(j).active = false;
                    asteroidList.get(i).active = false;
                    randomnum = Math.random() * 5 + 5;
                    for (int r = 0; r < randomnum; r++) {
                        debrisList.add(new Debris(asteroidList.get(i).xPosition, asteroidList.get(i).yPosition));
                    }
                }
            }
        }

        for(int i = 0; i < powerUpList.size(); i++) {
            if (collision(ship, powerUpList.get(i))) {
                powerUpList.get(i).active = false;
                if(powerUpList.get(i).invin) {
                    ship.invin = true;
                    ship.counter2 = 0;
                }
                if(powerUpList.get(i).fastFire) {
                    ship.fastFire = true;
                    ship.counter2 = 0;
                }
                if(powerUpList.get(i).extraLife) {
                    ship.lives +=1;
                }
            }
        }
    }

    public void respawnShip() {
        if (!ship.active && ship.counter > 50 && isRespawnSafe() && ship.lives > 0) {
            ship.reset();
        }
    }

    public boolean isRespawnSafe() {
        int x, y, h;
        for (int i = 0; i < asteroidList.size(); i++) {
            x = (int) (asteroidList.get(i).xPosition - 450);
            y = (int) (asteroidList.get(i).yPosition - 300);
            h = (int) (Math.sqrt(x * x + y * y));
            if (h < 100) {
                return false;
            }
        }
        return true;
    }

    public void fireBullet() {
        if (ship.counter > 5 && ship.active) {
            if (ship.fastFire) {
                bulletList.add(new Bullet(ship.drawShape.xpoints[0], ship.drawShape.ypoints[0], ship.angle, 30));
            }
            else {
                bulletList.add(new Bullet(ship.drawShape.xpoints[0], ship.drawShape.ypoints[0], ship.angle, 10));
            }
            ship.counter = 0;
            laser.play();
        }
    }

    public void checkAsteroidDestruction() {
        for (int i = 0; i < asteroidList.size(); i++) {
            if (!asteroidList.get(i).active) {
                if (asteroidList.get(i).size < 3) {
                    asteroidList.add(new Asteroid(asteroidList.get(i).xPosition, asteroidList.get(i).yPosition, asteroidList.get(i).size + 1));
                    asteroidList.add(new Asteroid(asteroidList.get(i).xPosition, asteroidList.get(i).yPosition, asteroidList.get(i).size + 1));
                }
                asteroidList.remove(i);
                asteroidExplosion.play();
            }
        }
    }
}
