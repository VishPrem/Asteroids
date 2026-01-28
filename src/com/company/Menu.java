package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Menu extends JPanel implements KeyListener, ActionListener {

    public boolean startGame = false;

    private Game game;

    public Menu(Game game) {
        this.game = game;

        setPreferredSize(new Dimension(900, 600));
        setBackground(Color.BLACK);

        JButton startButton = new JButton("Start Game");
        startButton.setLayout(null);
        startButton.setBounds(500,400,100,30);
        this.add(startButton);

        startButton.setFocusable(false);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.startGame = true;
                startButton.setFocusable(false);
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.black);
        g.fillRect(0,0,900,600);
        g.setColor(Color.white);
        g.drawString("Main Menu", 430, 300);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            startGame = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
