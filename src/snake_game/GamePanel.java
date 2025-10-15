package snake_game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
public class GamePanel extends JPanel implements ActionListener {
    static final int ScreenWidth = 800;
    static final int ScreenHeight = 600;
    static final int UntiSize = 25;
    static final int GameUntis= (ScreenWidth * ScreenHeight) / UntiSize;
    static final int Delay = 100;
    final int[] x = new int[GameUntis];
    final int[] y = new int[GameUntis];
    boolean isBigApple = false;
    int bodyParts = 4;
    int applesEats;
    int score;
    int appleX;
    int appleY;
    char direction = 'R'; //Phương hướng bắt đầu
    boolean run = false;
    boolean start = false;
    boolean buttonsAdded = false;
    int timeLeft = 7;
    Timer countdown;
    Timer timer;
    Random random;
    public Image background;
    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(ScreenWidth, ScreenHeight));
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdepter());
        background = new ImageIcon(getClass().getResource
                ("/background/background_snake.png")).getImage();
        gameLogin();
    }
    public void StartGame(){
        background = null;
        this.setBackground(Color.black);
        start = true;
        newApple();
        run = true;
        timer = new Timer(Delay, this);
        timer.start();
    }
    public void paintComponent(Graphics g){ // thành phần sơn
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        draw(g);
    }
    public void draw(Graphics g){
        if(run && start) {
            // vẽ lưới
//            for (int i = 0; i < ScreenWidth / UntiSize; i++) { //vẽ dọc
//                g.drawLine(i * UntiSize, 0, i * UntiSize, ScreenHeight);
//            }
//            for (int i = 0; i < ScreenHeight / UntiSize; i++) { //vẽ ngang
//                g.drawLine(0, i * UntiSize, ScreenWidth, i * UntiSize);
//            }
            //draw apple
            if((applesEats % 5 == 0 && applesEats != 0) && isBigApple){
                g.setColor(Color.RED);
                g.fillOval(appleX, appleY, UntiSize * 2, UntiSize * 2);
                g.setColor(Color.YELLOW);
                g.setFont(new Font("Ink Free", Font.BOLD, 30));
                g.drawString("Time: " + timeLeft, ScreenWidth - 150, 30);
            } else {
                g.setColor(Color.RED);
                g.fillOval(appleX, appleY, UntiSize, UntiSize);
            }

            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    if(score < 20) {
                        g.setColor(Color.GREEN);
                    } else if(score >= 20 && score < 40){
                        g.setColor(Color.yellow);
                    } else{
                        g.setColor(Color.red);
                    }
                    g.fillRect(x[i], y[i], UntiSize, UntiSize);
                } else {
                    g.setColor(new Color(45, 100, 0));
                    g.fillRect(x[i], y[i], UntiSize, UntiSize);
                }
            }
            g.setColor(Color.WHITE);
            g.setFont(new Font("Ink Free", Font.BOLD, 30));
            g.drawString("score: "+ score, 10,
                    30);

        } else if(!run &&  start){
            gameOver(g);
        }
    }
    public void newApple(){ //2x2
        if (countdown != null && countdown.isRunning()) {
            countdown.stop();
        }
        appleX = random.nextInt(ScreenWidth / UntiSize) * UntiSize;
        appleY = random.nextInt(ScreenHeight / UntiSize) * UntiSize;
        for(int i = 0; i < bodyParts; i++){
            if(appleX == x[i] && appleY == y[i]){
                newApple();
            }
        }
    }
    public void bigApple(){
        isBigApple = false;
        appleX = random.nextInt((ScreenWidth / UntiSize) - 1)* UntiSize;
        appleY = random.nextInt((ScreenHeight / UntiSize) -1) * UntiSize;
        for(int i = 0; i < bodyParts; i++){
            if(appleX == x[i] && appleY == y[i]){
                bigApple();
            }
        }
        timeLeft = 7;
        isBigApple = true;
        countdown = new Timer(1000, e -> { // đếm ngược time
            timeLeft--;
            if(timeLeft == 0){
                countdown.stop();   // dừng timer khi hết giờ
                isBigApple = false;
                newApple();
            }
        });
        countdown.start();
    }
    public void move(){
        for(int i=bodyParts; i>0; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        switch (direction) {
            case 'U':
                y[0] = y[0] - UntiSize;
                break;
            case 'D':
                y[0] = y[0] + UntiSize;
                break;
            case 'L':
                x[0] = x[0] - UntiSize;
                break;
            case 'R':
                x[0] = x[0] + UntiSize;
                break;
        }
    }
    public void checkApple(){ //snake eat apple -> score + 1
//        if((x[0] == appleX) && (y[0] == appleY)){
//            if(applesEats % 5 == 0 && applesEats != 0){
//                bodyParts += 3;
//                score += 13;
//                applesEats++;
//                newApple();
//            } else {
//                bodyParts++;
//                applesEats++;
//                if(applesEats % 5 == 0) {
//                    bigApple();
//                } else{
//                    newApple();
//                }
//            }
//        }
        if((applesEats % 5 == 0 && applesEats != 0) && isBigApple) {
            if (x[0] >= appleX && x[0] <= appleX + UntiSize &&
                    y[0] >= appleY && y[0] <= appleY + UntiSize){
                bodyParts += 3;
                if(timeLeft <=3 ){
                    score += 5;
                } else{
                    score += timeLeft * 3;
                }
                applesEats++;
                newApple();
            }
        } else {
            if((x[0] == appleX) && (y[0] == appleY)) {
                bodyParts ++;
                applesEats++;
                score++;
                if (applesEats % 5 == 0) {
                    bigApple();
                } else {
                    newApple();
                }
            }
        }
    }
    public void checkCollision(){ // kiểm tra va chạm
        //if head collision with body
        for(int i=bodyParts; i>0; i--){
            if(x[0] == x[i] && y[0] == y[i]){
                run = false;
            }
        }
        //if head collision with wall
        //LEFT
        if(x[0] < 0){
            run = false;
        }
        //RIGHT
        if (x[0] >= ScreenWidth) {
            run = false;
        }
        //TOP
        if(y[0] < 0){
            run = false;
        }
        //BOTTOM
        if(y[0] >= ScreenHeight){
            run = false;
        }
        if(!run){
            timer.stop();
        }
    }
    public void gameLogin(){
        background = new ImageIcon(getClass().getResource
                ("/background/background_snake.png")).getImage();
        repaint();
        JButton playButton = new JButton("PLay");
        JButton exitButton = new JButton("Exit");

        JLabel name = new JLabel("Name: ");
        name.setFont(new Font("Times New Roman", Font.ITALIC, 40));
        name.setForeground(Color.BLACK);
        name.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField nameF = new JTextField(15);
        nameF.setMaximumSize(new Dimension(200, 30));
        nameF.setAlignmentX(Component.CENTER_ALIGNMENT);

        playButton.addActionListener((ActionEvent e) -> {
            if (!run && !start) {
                String playerName = nameF.getText().trim();
                if (playerName.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Please enter your name!",
                            "Name Required",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    System.out.println("Player Name: " + playerName); // hoặc lưu vào biến
                    removeAll();
                    resetAllStates();
                    StartGame();
                }
            }
        });
        exitButton.addActionListener((ActionEvent e) -> {
            if(!run){
                System.exit(0);
            }
        });

        JLabel titleLabel = new JLabel("SNAKE GAME");
        titleLabel.setFont(new Font("Ink Free", Font.BOLD, 40));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);

        playButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(titleLabel);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(name);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(nameF);
        buttonPanel.add(Box.createVerticalStrut(50));
        buttonPanel.add(playButton);
        buttonPanel.add(Box.createVerticalStrut(40)); // khoảng cách giữa 2 nút
        buttonPanel.add(exitButton);
        buttonPanel.add(Box.createVerticalGlue());

        this.setLayout(new BorderLayout());
        this.add(buttonPanel, BorderLayout.CENTER);

        // Bố cục (layout) và refresh giao diện
        this.revalidate();
        this.repaint();
    }
    public void gameOver(Graphics g){
        //background = null;
        //this.setBackground(Color.white);
        background = new ImageIcon(getClass().getResource
                ("/background/images_over.png")).getImage();
        repaint();
        g.setColor(Color.red);
        g.setFont(new Font("Times new roman", Font.BOLD, 50));
        FontMetrics metrics = getFontMetrics(g.getFont()); // lấy căn giữa
        g.drawString("Game over", (ScreenWidth - metrics.stringWidth("Game over")) / 2, (ScreenHeight / 2) - 30);

        g.setColor(Color.BLACK);
        g.setFont(new Font("Ink Free", Font.ITALIC, 35));
        metrics = getFontMetrics(g.getFont());
        g.drawString("Your Score: " + score, (ScreenWidth - metrics.stringWidth("Your Score: " + score)) / 2,
                (ScreenHeight / 2) + 20);
        if (!run && !buttonsAdded) {
            buttonsAdded = true;
            ShowButton();
        }
        g.setColor(Color.BLACK);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        g.drawString("Press Space to restart", (ScreenWidth - metrics.stringWidth("Press Space to restart")) / 2,
                (ScreenHeight / 2) + 80);

    }
    public void ShowButton(){
        JButton againButton = new JButton("PLay_again");
        JButton newButton = new JButton("New game");
        JButton exitButton = new JButton("Exit");
        againButton.addActionListener((ActionEvent e) -> {
            if(!run){
                removeAll();     // Xóa nút
                buttonsAdded = false;
                resetGame();
            }
        });
        newButton.addActionListener((ActionEvent e) -> {
            if(!run){
                removeAll();
                resetAllStates();
                start = false;
                gameLogin();
            }
        });
        exitButton.addActionListener((ActionEvent e) -> {
            if(!run){
                System.exit(0);
            }
        });
        this.setLayout(new FlowLayout());
        this.add(againButton);
        this.add(newButton);
        this.add(exitButton);

        // Bố cục (layout) và refresh giao diện
        this.revalidate();
        this.repaint();
    }
    public void resetGame(){
        this.setBackground(Color.black);
        bodyParts = 4;
        applesEats = 0;
        direction = 'R';
        score = 0;
        for (int i = 0; i < x.length; i++) {
            x[i] = 0; y[i] = 0;
        }
        StartGame();
    }
    public void resetAllStates() {
        bodyParts = 4;
        applesEats = 0;
        score = 0;
        direction = 'R';
        run = false;
        buttonsAdded = false;

        for (int i = 0; i < x.length; i++) {
            x[i] = 0; y[i] = 0;
        }

        // Dừng các timer cũ nếu còn chạy
        if (timer != null) timer.stop();
        if (countdown != null) countdown.stop();
    }

    // ghi đè
    @Override
    public void actionPerformed(ActionEvent a){
        if(run){
            move();
            checkApple();
            checkCollision();
        }
        repaint();
    }
    public class MyKeyAdepter extends KeyAdapter {
        public void keyPressed(KeyEvent e){
            switch (e.getKeyCode()){
                case KeyEvent.VK_A:
                    if(direction != 'R'){
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_D:
                    if(direction != 'L'){
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_S:
                    if(direction != 'U'){
                        direction = 'D';
                    }
                    break;
                case KeyEvent.VK_W:
                    if(direction != 'D'){
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_SPACE:
                    if(!run){
                        removeAll();
                        buttonsAdded = false;
                        resetGame();
                    }
            }
        }
    }
}
