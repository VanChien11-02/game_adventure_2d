package main;

import entity.Entity;
import entity.Player;
import tile.TileManager;
import tile_interactive.InteractiveTIle;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

// class điều khiển (panel)
public class GamePanel extends JPanel implements Runnable{
    final int originalTitleSize = 16; //final: ko đổi đc, 16x16 size of player
    final int scale = 3; //chia màn hình
    public final int tile_size = originalTitleSize * scale; // 48x48

    public final int maxScreenCol = 20;
    public final int maxScreenRow = 12; //  16 cot x 12 hang -> 20 cot x 12 hang
    public final int ScreenWidth = tile_size * maxScreenCol; //960 pixels
    public final int ScreenHeight = tile_size * maxScreenRow; //576 pixels
    // for full screen
    int ScreenWidth2 = ScreenWidth;
    int ScreenHeight2 = ScreenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;
    public boolean fullScreenOn = false;

    //setting World
    public final int maxWorldCol = 50; // can change whatever you want to size of world
    public final int maxWorldRow = 50;
    public final int maxMap = 10;
    public int currentMap = 0;
//    public final int worldWidth = maxWorldCol * title_size;
//    public final int worldHeight = maxWorldRow * title_size;
    int FPS = 60;
    int currentFPS = 0;

    //system
    TileManager tileM = new TileManager(this);
    public KeyHandler KeyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound se = new Sound(); // sound effect
    public CollisionChecker check = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public EventHandler eHandler = new EventHandler(this);
    public UI ui = new UI(this);
    Config config = new Config(this);
    Thread gameThread; // to set FPS

    //entity and object
    public Player player = new Player(this, KeyH);
    public Entity[][] obj = new Entity[maxMap][20];//ten object
    public Entity[][] npc = new Entity[maxMap][10];
    public Entity[][] monster = new Entity[maxMap][20];
    public InteractiveTIle[][] iTile = new InteractiveTIle[maxMap][30];
    public ArrayList<Entity> projectileList = new ArrayList<>();
    public ArrayList<Entity> particleList = new ArrayList<>();
    ArrayList<Entity> entityList = new ArrayList<>();
    //to sort entity if the entity has lower worldY comes in index 0

    //game state
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;
    public final int optionState = 5;
    public final int gameOverState = 6;
    public final int transitionState = 7;
    public final int tradeState = 8;

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
        aSetter.setInteractiveTile();
        playMusic(0);
//        stopMusic();
//        gameState = playState;
        gameState = titleState;

        tempScreen = new BufferedImage(ScreenWidth, ScreenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D)tempScreen.getGraphics(); // everything in g2 will be record in tempScreen(size = full screen)

        if(fullScreenOn) {
            setFullScreen();
        }
    }

    public void startGameThread(){
        gameThread = new Thread(this); // this == class
        gameThread.start();
    }

    public void retry(){
        player.setDefaultPositions();
        player.restoreLifeAndMana();
        aSetter.setNPC();
        aSetter.setMonster();
    }

    public void restart(){
        player.setDefaultValues();
        player.setItem();
        ui.playerSlotCol = 0;
        ui.playerSlotRow = 0;
        player.selectItem();
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        aSetter.setInteractiveTile();
    }

    public void setFullScreen(){
//        //get local screen device
//        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
//        GraphicsDevice gd = ge.getDefaultScreenDevice();
//        gd.setFullScreenWindow(Main.window);
//
//        //Get full screen width and screen height
//        ScreenWidth2 = Main.window.getWidth();
//        ScreenHeight2 = Main.window.getHeight();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        Main.window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        ScreenWidth2 = (int) width;
        ScreenHeight2 = (int) height;
        //offset factor to be used by mouse listener or mouse motion listener if you are using cursor in your game. Multiply your e.getX()e.getY() by this.
//        fullScreenOffsetFactor = (float) ScreenWidth / (float) ScreenWidth2;
    }

    // set speed loop to 60 FPS
    public void run(){
        double drawInterval = (double) 1000000000 / FPS;//16666666.67s
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        double timer = 0;
        int drawCount = 0;

        while(gameThread != null){
            currentTime = System.nanoTime();
//            System.out.println("last time: " + lastTime);
//            System.out.println("current time: " + currentTime);
//            last time:    1591787912000
//            current time: 1591803381500
//            delta = 15469500/16666666.67 = 0.928169999814366
//            timer = 15469500/1000000000 = 0.0154695
            delta += (currentTime - lastTime) / drawInterval;
            timer += (double)(currentTime - lastTime) / 1000000000;
            lastTime = currentTime;

            if(delta >= 1.0){
                update();
                drawToTempScreen();
                drawToScreen();
                delta--;
                drawCount++;
            }
            if(timer >= 1.0) {
                currentFPS = drawCount;
                drawCount = 0;
                timer = 0;
                if(player.life == 0) { //countdown time when player die
                    ui.timeToRespawn--;
                }
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
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
            for(int i=0; i<npc[1].length; i++){ //access to length on second like 20, not maxMap(10)
                if(npc[currentMap][i] != null){
                    npc[currentMap][i].update();
                }
            }
            for(int i=0; i<monster[1].length; i++){
                if(monster[currentMap][i] != null){
                    if(monster[currentMap][i].alive && !monster[currentMap][i].dying) {
                        monster[currentMap][i].update();
                    }
                    if(!monster[currentMap][i].alive){
                        monster[currentMap][i].checkDrop();
                        monster[currentMap][i] = null;
                    }
                }
            }
            for(int i=0; i<projectileList.size(); i++){
                if(projectileList.get(i) != null){
                    if(projectileList.get(i).alive && !projectileList.get(i).dying) {
                        projectileList.get(i).update();
                    }
                    if(!projectileList.get(i).alive){
                        projectileList.remove(i);
                    }
                }
            }
            for(int i = 0; i < iTile[1].length; i++){
                if(iTile[currentMap][i] != null){
                    iTile[currentMap][i].update();
                }
            }
            for(int i=0; i<particleList.size(); i++){
                if(particleList.get(i) != null){
                    if(particleList.get(i).alive) {
                        particleList.get(i).update();
                    }
                    if(!particleList.get(i).alive){
                        particleList.remove(i);
                    }
                }
            }
        }
        if(gameState == pauseState){
            // not update
        }
    }

    public void drawToTempScreen(){
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
            //tile
            tileM.draw(g2);

            // interactive tile
            for (int i = 0; i < iTile[1].length; i++) {
                if (iTile[currentMap][i] != null) {
                    iTile[currentMap][i].draw(g2);
                }
            }
            //add entity to the list
            entityList.add(player);
            //npc
            for (int i = 0; i < npc[1].length; i++) {
                if (npc[currentMap][i] != null) {
                    entityList.add(npc[currentMap][i]);
                }
            }
            //monster
            for (int i = 0; i < monster[1].length; i++) {
                if (monster[currentMap][i] != null) {
                    entityList.add(monster[currentMap][i]);
                }
            }
            //projectile
            for (int i = 0; i < projectileList.size(); i++) {
                if (projectileList.get(i) != null) {
                    entityList.add(projectileList.get(i));
                }
            }
            //particle
            for (int i = 0; i < particleList.size(); i++) {
                if (particleList.get(i) != null) {
                    entityList.add(particleList.get(i));
                }
            }
            //entityList.addAll(Arrays.asList(npc));
            for (int i = 0; i < obj[1].length; i++) {
                if (obj[currentMap][i] != null) {
                    entityList.add(obj[currentMap][i]);
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
            for (int i = 0; i < entityList.size(); i++) {
                entityList.get(i).draw(g2);
            }
            //empty list
            entityList.clear();
            // UI
            ui.draw(g2);
        }

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
//          System.out.println("Draw time: " + passed);
            y+=lineHeight;
            g2.drawString("FPS: " + currentFPS, x, y);
        }
    }

    public void drawToScreen(){
        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, ScreenWidth2, ScreenHeight2, null);
        g.dispose(); //bỏ qua graphics
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
