package com.company;

import javax.swing.*;
import java.awt.*;

public class Window extends JPanel {

    // only accessible in its own class
    private Game game;

    public Window(Game game) {
        this.game = game;

        setPreferredSize(new Dimension(900, 600));
        setBackground(Color.BLACK);
    }

    // built-in method and is called by itself constantly so no need to call it
    @Override
    public void paintComponent(Graphics g) {
        // calling original paintComponent, not overrided one
        super.paintComponent(g);

        game.offG.setColor(Color.BLACK);
        game.offG.fillRect(0, 0, 900, 600);
        game.offG.setColor(Color.red);
        game.offG.drawString("Lives " + game.ship.lives, 20, 580);
        game.offG.drawString("Score " + game.score, 440, 580);
        if (game.ship.active) {
            if (game.ship.invin) {
                game.offG.setColor(Color.blue);
            }
            else {
                game.offG.setColor(Color.red);
            }
            game.ship.paint(game.offG);
        }
        for (int i = 0; i < game.asteroidList.size(); i++) {
            game.asteroidList.get(i).paint(game.offG);
        }
        for (int i = 0; i < game.bulletList.size(); i++) {
            game.bulletList.get(i).paint(game.offG);
        }
        for (int i = 0; i < game.debrisList.size(); i++) {
            game.debrisList.get(i).paint(game.offG);
        }
        for (int i = 0; i < game.powerUpList.size(); i++) {
            if(game.powerUpList.get(i).type == "Invincibility") {
                game.offG.setColor(Color.blue);
            }
            if(game.powerUpList.get(i).type == "Fast Fire") {
                game.offG.setColor(Color.orange);
            }
            if(game.powerUpList.get(i).type == "Extra Life") {
                game.offG.setColor(Color.green);
            }
            game.powerUpList.get(i).paint(game.offG);
        }
        game.offG.setColor(Color.red);
        if (game.asteroidList.isEmpty()) {
            game.offG.drawString("Congratulations! - You Win", 400, 300);
        }
        if (game.ship.lives == 0) {
            game.offG.drawString("Game Over - You Lose", 400, 300);
            game.ship.active = false;
        }

        g.drawImage(game.offScreen, 0, 0, this);
        repaint();
    }
}
