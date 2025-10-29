package main;

import javax.swing.*;

public class Main {
    public static JFrame window;

    public static void main(String[] args){
        window = new JFrame(); // create new window "very simple" :>
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // close window
        window.setResizable(false); // cố định kích thước
        window.setTitle("Game Adventure");
//        window.setUndecorated(true);

        GamePanel gamepanel = new GamePanel();
        window.add(gamepanel);

        gamepanel.config.loadConfig();
        if(gamepanel.fullScreenOn){
            window.setUndecorated(true);
        }

        window.pack(); // size window == size game panel

        window.setLocationRelativeTo(null); // display window in a center
        window.setVisible(true);//display

        gamepanel.setupGame();
        gamepanel.startGameThread();

    }
}
