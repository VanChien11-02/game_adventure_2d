package main;

import entity.Entity;
import object.obj_heart;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

//can display text, message, ...
public class UI {
    GamePanel gp;
    Font MaruMonica, PurisaBold;
//    BufferedImage KeyImage;
    BufferedImage heart_full, heart_half, heart_blank;
    public boolean messageOn = false;
//    public String message = "";
//    int messageCounter = 0;
//    public boolean gameFinished = false;

    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    public String currentDialogue = "";
    public int commandNum = 0;

//    double playTime;
//    DecimalFormat dFormat = new DecimalFormat("#0.00"); // định dạng time(lam tròn đến chư sô thập phân thứ 2
    public Graphics2D g2;

    public UI(GamePanel gp){
        this.gp = gp;
        try {
            InputStream is = getClass().getResourceAsStream("/font/MaruMonica.ttf");
            MaruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/font/Purisa_Bold.ttf");
            PurisaBold = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        obj_key key = new obj_key(gp);
//        KeyImage = key.image;
        Entity heart = new obj_heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;

    }

    public void addMessage(String text){
//        message = text;
//        messageOn = true;

        message.add(text);
        messageCounter.add(0);
    }

    public void drawMessage(){

        int messageX = gp.title_size;
        int messageY = gp.title_size*4;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));

        for(int i=0; i<message.size(); i++){
            if(message.get(i) != null){
                g2.setColor(Color.white);
                g2.drawString(message.get(i), messageX, messageY);

                int count = messageCounter.get(i) + 1;
                messageCounter.set(i, count); // update messageCounter and set the counter to array
                messageY += 50;

                if(messageCounter.get(i) > 180){
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }
    }

    public void draw(Graphics2D g2){
//            g2.setFont(new Font("Arial", Font.PLAIN, 40)); update 60 time / second -> bad
//            g2.setFont(arial_40);
//            g2.setColor(Color.white);
//            g2.drawImage(KeyImage, gp.title_size / 2, gp.title_size / 2, gp.title_size, gp.title_size, null);
//            g2.drawString("x " + gp.player.hasKey, 74, 65);
        this.g2 = g2;
        g2.setFont(MaruMonica);
//        g2.setFont(PurisaBold);
        g2.setColor(Color.white);
        // title state
        if(gp.gameState == gp.titleState){
            drawTitleScreen();
        }
        //play state
        if(gp.gameState == gp.playState){
            drawPlayerLife();
            drawMessage();
        }
        // pause state
        if(gp.gameState == gp.pauseState){
            drawPauseScreen();
            drawPlayerLife();
        }
        //dialogues state
        if(gp.gameState == gp.dialogueState){
            drawDialogueScreen();
            drawPlayerLife();
        }
        //character state
        if(gp.gameState == gp.characterState){
            drawCharacterScreen();
        }
    }

    public void drawPlayerLife(){
        int x = gp.title_size / 2;
        int y = gp.title_size / 2;

        int i = 0;
        // draw heart blank first then half heart, at last is full
        while(i < gp.player.maxLife / 2){
            g2.drawImage(heart_blank, x, y,null);
            i++;
            x += gp.title_size;
        }
        //reset
        x = gp.title_size / 2;
        y = gp.title_size / 2;

        i = 0;
        while(i < gp.player.life){
            g2.drawImage(heart_half, x, y, null);
            i++;
            if(i < gp.player.life){
                g2.drawImage(heart_full, x, y, null);
            }
            i++;
            x += gp.title_size;
        }
    }

    public void drawTitleScreen(){
        g2.setColor(new Color(70, 120, 80));
        g2.fillRect(0,0,gp.ScreenWidth, gp.ScreenHeight);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80F));
        String text = "Game Adventure";
        int x = getXforCenteredText(text);
        int y = gp.title_size * 3;
        //Shadow
        g2.setColor(Color.black);
        g2.drawString(text, x+5, y+5);
        //String
        g2.setColor(Color.white);
        g2.drawString(text, x, y);
        //character image
        x = gp.ScreenWidth / 2 - (gp.title_size * 2) / 2;
        y += gp.title_size ;
        g2.drawImage(gp.player.down1, x, y, gp.title_size * 2, gp.title_size * 2, null);

        // MENU
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 42F));
        text = "New Game";
        x = getXforCenteredText(text);
        y += gp.title_size * 3.5;
        g2.drawString(text, x, y);
        if(commandNum == 0){
            g2.drawString(">", x - gp.title_size, y);
        }
        text = "Load Game";
        x = getXforCenteredText(text);
        y += gp.title_size ;
        g2.drawString(text, x, y);
        if(commandNum == 1){
            g2.drawString(">", x - gp.title_size, y);
        }
        text = "Quit";
        x = getXforCenteredText(text);
        y += gp.title_size ;
        g2.drawString(text, x, y);
        if(commandNum == 2){
            g2.drawString(">", x - gp.title_size, y);
        }
    }

    public void drawDialogueScreen(){
        //window
        int x = gp.title_size * 2;
        int y = gp.ScreenHeight - gp.title_size * 5;
        int width = gp.ScreenWidth - (gp.title_size * 4);
        int height = gp.title_size * 4;
        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28));
        x += gp.title_size;
        y += gp.title_size;
        for(String line : currentDialogue.split("\n")){
            g2.drawString(line,x, y);
            y += 40;
        }
    }

    public void drawSubWindow(int x, int y, int width, int height){
        // window
        g2.setColor(new Color(0, 0, 0, 200));
        g2.fillRoundRect(x, y, width, height, 35, 35);

        //outlines
        g2.setColor(new Color(250, 248, 248));
        g2.setStroke(new BasicStroke(5));//define the width of outline
        g2.drawRoundRect(x+5, y+5, width - 10, height - 10, 25, 25);
    }

    public void drawCharacterScreen(){
        //create a frame
        final int frameX = gp.title_size;
        final int frameY = gp.title_size;
        final int frameWidth = gp.title_size * 5;
        final int frameHeight = gp.title_size * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        //text
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));

        int textX = frameX + 20;
        int textY = frameY + gp.title_size;
        final int lineHeight = 32;

        //name
        g2.drawString("Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Life", textX, textY);
        textY += lineHeight;
        g2.drawString("Strength", textX, textY);
        textY += lineHeight;
        g2.drawString("Dexterity", textX, textY);
        textY += lineHeight;
        g2.drawString("Attack", textX, textY);
        textY += lineHeight;
        g2.drawString("Defend", textX, textY);
        textY += lineHeight;
        g2.drawString("Exp", textX, textY);
        textY += lineHeight;
        g2.drawString("Next Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Coin", textX, textY);
        textY += lineHeight + 20;
        g2.drawString("Weapon", textX, textY);
        textY += lineHeight + 15;
        g2.drawString("Shield", textX, textY);

        //values
        int tailX = (frameX + frameWidth) - 30;
        //reset textY
        textY = frameY + gp.title_size;
        String value;

        value = String.valueOf(gp.player.level);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.strength);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.dexterity);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.attack);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.defense);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.exp);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.nextLevelExp);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.coin);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.title_size, textY - 15, null);
        textY += gp.title_size;
        g2.drawImage(gp.player.currentShield.down1, tailX - gp.title_size, textY - 15, null);
    }

    public void drawPauseScreen(){
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 60));
        String text = "PAUSE";
        int x = getXforCenteredText(text);
        int y = gp.ScreenHeight / 2;

        g2.drawString(text, x, y);
    }

    public int getXforCenteredText(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.ScreenWidth / 2 - length/2;
        return x;
    }

    public int getXforAlignToRightText(String text, int tailX){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tailX - length;
        return x;
    }
}
