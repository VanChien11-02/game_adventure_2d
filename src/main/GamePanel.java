package main;

import entity.Entity;
import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

// class điều khiển (panel)
public class GamePanel extends JPanel implements Runnable{
    final int originalTitleSize = 16; //final: ko đổi đc, 16x16 size of player
    final int scale = 3; //chia màn hình
    public final int tile_size = originalTitleSize * scale; // 48x48

    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12; //  16 cot x 12 hang
    public final int ScreenWidth = tile_size * maxScreenCol; //768 pixels
    public final int ScreenHeight = tile_size * maxScreenRow; //576 pixels

    //setting World
    public final int maxWorldCol = 50; // can change whatever you want to size of world
    public final int maxWorldRow = 50;
//    public final int worldWidth = maxWorldCol * title_size;
//    public final int worldHeight = maxWorldRow * title_size;
    int FPS = 60;

    //system
    TileManager tileM = new TileManager(this);
    public KeyHandler KeyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound se = new Sound(); // sound effect
    public CollisionChecker check = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public EventHandler eHandler = new EventHandler(this);
    public UI ui = new UI(this);
    Thread gameThread; // to set FPS

    //entity and object
    public Player player = new Player(this, KeyH);
    public Entity[] obj = new Entity[10];//ten object
    public Entity[] npc = new Entity[10];
    public Entity[] monster = new Entity[20];
    ArrayList<Entity> entityList = new ArrayList<>();
    //to sort entity if the entity has lower worldY comes in index 0

    //game state
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;

    //set player's Default position (test before create class player)
//    int playerX = 100;
//    int playerY = 100;
//    int playerSpeed = 4;

    public GamePanel() {
        this.setPreferredSize(new Dimension(ScreenWidth, ScreenHeight));
        //set the size of this class
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); //bộ đệm = true, vẽ các ảnh bên ngoài screen
        this.addKeyListener(KeyH);
        this.setFocusable(true); // tập trung vào key input
    }

    public void setupGame(){ //set object
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        playMusic(0);
        stopMusic();
//        gameState = playState;
        gameState = titleState;
    }
    public void startGameThread(){
        gameThread = new Thread(this); // this == class
        gameThread.start();
    }

    // set speed loop to 60 FPS
    public void run(){
        double drawInterval = 1000000000.0/FPS;
        double nextDrawTime = System.nanoTime() + drawInterval; //thời gian giữa hai khung hình mong muốn

        while(gameThread != null){
            update();
            repaint();
            try {
                double remainingTime = nextDrawTime - System.nanoTime(); //time chờ
                remainingTime = remainingTime/1000000;

                if(remainingTime < 0){
                    remainingTime = 0;
                }
                Thread.sleep((long) (remainingTime));
                nextDrawTime += drawInterval;
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
    public void update(){
//        if(KeyH.upPressed){
//            playerY -= playerSpeed;
//        } else if (KeyH.downPressed) {
//            playerY += playerSpeed;
//        } else if (KeyH.leftPressed) {
//            playerX -= playerSpeed;
//        } else if (KeyH.rightPressed) {
//            playerX += playerSpeed;
//        }
        if(gameState == playState) {
            player.update();
            //npc
            for(int i=0; i<npc.length; i++){
                if(npc[i] != null){
                    npc[i].update();
                }
            }
            for(int i=0; i<monster.length; i++){
                if(monster[i] != null){
                    if(monster[i].alive && !monster[i].dying) {
                        monster[i].update();
                    }
                    if(!monster[i].alive){
                        monster[i] = null;
                    }
                }
            }
        }
        if(gameState == pauseState){
            // not update
        }
    }
    public void paintComponent(Graphics g){ //to draw
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g; // g -> g2D

        //DEBUG
        long drawStart = 0;
        if(KeyH.showDebugText) {
            drawStart = System.nanoTime();
        }

//        g2.setColor(Color.white);
//        g2.fillRect(playerX, playerY, title_size, title_size); // draw hcn
        if(gameState == titleState){
            ui.draw(g2);
        } else {
            tileM.draw(g2);

            //add entity to the list
            entityList.add(player);
            //npc
            for(int i=0; i < npc.length; i++){
                if(npc[i] != null){
                    entityList.add(npc[i]);
                }
            }
            //monster
            for(int i=0; i < monster.length; i++){
                if(monster[i] != null){
                    entityList.add(monster[i]);
                }
            }
            //entityList.addAll(Arrays.asList(npc));
            for(int i=0; i<obj.length; i++){
                if(obj[i] != null){
                    entityList.add(obj[i]);
                }
            }
            //Sort
            Collections.sort(entityList, new Comparator<Entity>() {

                @Override
                public int compare(Entity e1, Entity e2) {
                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return result;
                }
            });
            // draw entities
            for(int i=0; i<entityList.size(); i++){
                entityList.get(i).draw(g2);
            }
            //empty list
            entityList.clear();
            // UI
            ui.draw(g2);
            //DEBUG
            if (KeyH.showDebugText) {
                long drawEnd = System.nanoTime();
                long passed = drawEnd - drawStart;

                g2.setFont(new Font("Arial", Font.PLAIN, 20));
                g2.setColor(Color.white);

                int x = 10;
                int y = 400;
                int lineHeight = 20;
                //display player position
                g2.drawString("World X " + player.worldX, x, y);
                y+=lineHeight;
                g2.drawString("World Y " + player.worldY, x, y);
                y+=lineHeight;
                g2.drawString("col " + (player.worldX + player.solidArea.x)/ tile_size, x, y);
                y+=lineHeight;
                g2.drawString("row " + (player.worldY + player.solidArea.y)/ tile_size, x, y);
                y+=lineHeight;

                g2.drawString("Time: " + passed, x, y);
//                System.out.println("Draw time: " + passed);
                g2.dispose(); //bỏ qua graphics
            }
        }
    }
    public void playMusic(int i){
        music.setFile(i);
        music.play();
        music.loop();
    }
    public void stopMusic(){
        music.stop();
    }
    // some sound don't need to loop
    public void playSE(int i){
        se.setFile(i);
        se.play();
    }
}
