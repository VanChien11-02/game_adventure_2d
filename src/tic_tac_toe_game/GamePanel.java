package tic_tac_toe_game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
public class GamePanel extends JPanel implements ActionListener {
    static final int ScreenWidth = 300;
    static final int ScreenHeight = 400;
    static final int UntiSize = 100;
    boolean run = true;
    JButton[] buttons = new JButton[9];
    JPanel button_panel= new JPanel();
    JFrame frame = new JFrame();
    boolean player = true;
    boolean computer = false;
    Random random = new Random();
    GamePanel(){
        frame.setSize(new Dimension(ScreenWidth, ScreenHeight));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        for(int i=0; i<(ScreenHeight / UntiSize); i++){ // doc
            g.drawLine(i * UntiSize, 100, i * UntiSize, ScreenHeight);
        }
        for(int i=0; i<=(ScreenWidth / UntiSize); i++){ // ngang
            g.drawLine(0, i * UntiSize, ScreenWidth, i * UntiSize);
        }
        g.setColor(Color.BLACK);
        g.setFont(new Font("Ink Free", Font.BOLD, 20));
        FontMetrics metrics = getFontMetrics(g.getFont()); // lấy căn giữa
        g.drawString("TIC-TAC_TOE", (ScreenWidth - metrics.stringWidth("TIC-TAC_TO")) / 2, 30);
        button_panel.setLayout(new BorderLayout());
        button_panel.setBackground(new Color(150, 150, 150));
        frame.add(button_panel);
    }
    @Override
    public void actionPerformed(ActionEvent a){

    }
    public static class MyKeyAdepter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
        }
    }
}
