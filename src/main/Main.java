package main;

import javax.swing.*;

public class Main {
    public static void main(String[] args){
        JFrame window = new JFrame(); // create new window "very simple" :>
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // close window
        window.setResizable(false); // cố định kích thước
        window.setTitle("Game Adventure");

        GamePanel gamepanel = new GamePanel();
        window.add(gamepanel);

        window.pack(); // size window == size game panel

        window.setLocationRelativeTo(null); // display window in a center
        window.setVisible(true);//display

        gamepanel.setupGame();
        gamepanel.startGameThread();

    }
}
