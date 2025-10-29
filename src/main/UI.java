package main;

import entity.Entity;
import object.obj_heart;
import object.obj_mana;

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
    BufferedImage heart_full, heart_half, heart_blank, mana_full, mana_blank;
    public boolean messageOn = false;
//    public String message = "";
//    int messageCounter = 0;
//    public boolean gameFinished = false;

    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    public String currentDialogue = "";
    public int commandNum = 0;

    // help to know position of cursor
    public int slotCol = 0;
    public int slotRow = 0;

    int subState = 0;

    int timeCounter = 0;
    int time = 5;

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

        Entity mana = new obj_mana(gp);
        mana_full = mana.image;
        mana_blank = mana.image2;
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
            drawInventory();
        }
        //option state
        if(gp.gameState == gp.optionState){
            drawOptionScreen();
        }
        //game over state
        if(gp.gameState == gp.gameOverState){
            drawGameOverScreen();
        }
    }

    public void drawOptionScreen(){
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));

        //sub window
        int frameX = gp.tile_size * 6;
        int frameY = gp.tile_size;
        int frameWidth = gp.tile_size * 8;
        int frameHeight = gp.tile_size * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        switch(subState){
            case 0: option_top(frameX, frameY); break;
            case 1: option_fullScreenNotification(frameX, frameY); break;
            case 2: option_control(frameX, frameY); break;
            case 3: option_endGameConfirmation(frameX, frameY); break;
        }

        gp.KeyH.enterPressed = false;
    }

    public void option_top(int frameX, int frameY){
        int textX;
        int textY;

        //TITLE
        String text = "Option";
        textX = getXforCenteredText(text);
        textY = frameY + gp.tile_size;
        g2.drawString(text, textX, textY);

        //full screen button
        textX = frameX + gp.tile_size;
        textY += gp.tile_size * 2;
        g2.drawString("Full screen", textX, textY);
        if(commandNum == 0){
            g2.drawString(">", textX-25, textY);

            if(gp.KeyH.enterPressed){
                if(!gp.fullScreenOn){
                    gp.fullScreenOn = true;
                } else{
                    gp.fullScreenOn = false;
                }
                subState = 1;
            }
        }

        //music
        textY += gp.tile_size;
        g2.drawString("Music", textX, textY);
        if(commandNum == 1){
            g2.drawString(">", textX-25, textY);
        }

        //SE
        textY += gp.tile_size;
        g2.drawString("SE", textX, textY);
        if(commandNum == 2){
            g2.drawString(">", textX-25, textY);
        }

        //Control
        textY += gp.tile_size;
        g2.drawString("Control", textX, textY);
        if(commandNum == 3){
            g2.drawString(">", textX-25, textY);

            if(gp.KeyH.enterPressed){
                subState = 2;
                commandNum = 0;
            }
        }

        //end game
        textY += gp.tile_size;
        g2.drawString("End game", textX, textY);
        if(commandNum == 4){
            g2.drawString(">", textX-25, textY);
            if(gp.KeyH.enterPressed){
                subState = 3;
                commandNum = 0;
            }
        }

        //back
        textY += gp.tile_size * 2;
        g2.drawString("Back", textX, textY);
        if(commandNum == 5){
            g2.drawString(">", textX-25, textY);
            if(gp.KeyH.enterPressed){
                gp.gameState = gp.playState;
            }
        }

        //full screen check box
        textX = frameX + (int)(gp.tile_size * 4.5);
        textY = frameY + gp.tile_size * 2 + 24;
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX, textY, 24, 24);
        if(gp.fullScreenOn){
            g2.fillRect(textX, textY, 24, 24);
        }

        //music volume
        textY += gp.tile_size;
        g2.drawRect(textX, textY, 120, 24); // 120/5 = 24 rect
        int volumeWidth = 24 * gp.music.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);

        //se volume
        textY += gp.tile_size;
        g2.drawRect(textX, textY, 120, 24);
        volumeWidth = 24 * gp.se.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);

        gp.config.saveConfig();
    }

    public void option_fullScreenNotification(int frameX, int frameY){
        int textX = frameX + gp.tile_size;
        int textY = frameY + gp.tile_size * 3;
        currentDialogue = "The change will take \neffect after restart \nthe game";

        for(String line : currentDialogue.split("\n")){
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        //back
        textY = frameY + gp.tile_size * 9;
        g2.drawString("Back", textX, textY);
        if(commandNum == 0){
            g2.drawString(">", textX - 25, textY);
            if(gp.KeyH.enterPressed){
                subState = 0;
            }
        }
    }

    public void option_control(int frameX, int frameY){
        int textX;
        int textY;

        //title
        String text = "Control";
        textX = getXforCenteredText(text);
        textY = frameY + gp.tile_size;
        g2.drawString(text, textX, textY);

        textX = frameX + gp.tile_size;
        textY += gp.tile_size;
        g2.drawString("Move", textX, textY);
        textY += gp.tile_size;
        g2.drawString("Confirm/Attack", textX, textY);
        textY += gp.tile_size;
        g2.drawString("Shoot/Cast", textX, textY);
        textY += gp.tile_size;
        g2.drawString("Character Screen", textX, textY);
        textY += gp.tile_size;
        g2.drawString("Pause", textX, textY);
        textY += gp.tile_size;
        g2.drawString("Option", textX, textY);

        textX = frameX + gp.tile_size * 6;
        textY = frameY + gp.tile_size * 2;
        g2.drawString("WASD", textX, textY);
        textY += gp.tile_size;
        g2.drawString("Enter", textX, textY);
        textY += gp.tile_size;
        g2.drawString("R", textX, textY);
        textY += gp.tile_size;
        g2.drawString("C", textX, textY);
        textY += gp.tile_size;
        g2.drawString("P", textX, textY);
        textY += gp.tile_size;
        g2.drawString("ESC", textX, textY);

        //back
        textX = frameX + gp.tile_size;
        textY = frameY + gp.tile_size * 9;
        g2.drawString("Back", textX, textY);
        if(commandNum == 0){
            g2.drawString(">", textX - 25, textY);
            if(gp.KeyH.enterPressed){
                subState = 0;
                commandNum = 3;
            }
        }
    }

    public void option_endGameConfirmation(int frameX, int frameY){
        int textX = frameX + gp.tile_size;
        int textY = frameY + gp.tile_size * 3;

        currentDialogue = "Quit the game and \nreturn the title screen?";
        for(String line : currentDialogue.split("\n")){
            g2.drawString(line, textX, textY);
            textY += 40;
        }
        //yes
        String text = "Yes";
        textX = getXforCenteredText(text);
        textY = frameY + gp.tile_size * 6;
        g2.drawString(text, textX, textY);
        if(commandNum == 0){
            g2.drawString(">", textX - 25, textY);
            if(gp.KeyH.enterPressed){
                subState = 0;
                gp.gameState = gp.titleState;
                gp.restart();
            }
        }

        //no
        text = "No";
        textX = getXforCenteredText(text);
        textY += gp.tile_size;
        g2.drawString(text, textX, textY);
        if(commandNum == 1){
            g2.drawString(">", textX - 25, textY);
            if(gp.KeyH.enterPressed){
                subState = 0;
                commandNum = 4;
            }
        }
    }

    public void addMessage(String text){
//        message = text;
//        messageOn = true;

        message.add(text);
        messageCounter.add(0);
    }

    public void drawMessage(){

        int messageX = gp.tile_size;
        int messageY = gp.tile_size *4;
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

    public void drawPlayerLife(){
        int x = gp.tile_size / 2;
        int y = gp.tile_size / 2;

        int i = 0;
        // draw heart blank first then half heart, at last is full
        while(i < gp.player.maxLife / 2){
            g2.drawImage(heart_blank, x, y,null);
            i++;
            x += gp.tile_size;
        }
        //reset
        x = gp.tile_size / 2;
        y = gp.tile_size / 2;

        //draw current life
        i = 0;
        while(i < gp.player.life){
            g2.drawImage(heart_half, x, y, null);
            i++;
            if(i < gp.player.life){
                g2.drawImage(heart_full, x, y, null);
            }
            i++;
            x += gp.tile_size;
        }

        //draw max mana
        x = gp.tile_size / 2;
        y = gp.tile_size * 2;
        i = 0;
        while(i < gp.player.maxMana){
            g2.drawImage(mana_blank, x, y, null);
            i++;
            x += 35;
        }

        //draw mana
        x = gp.tile_size / 2;
        y = gp.tile_size * 2;
        i = 0;
        while(i < gp.player.mana){
            g2.drawImage(mana_full, x, y, null);
            i++;
            x += 35;
        }
    }

    public void drawTitleScreen(){
        g2.setColor(new Color(70, 120, 80));
        g2.fillRect(0,0,gp.ScreenWidth, gp.ScreenHeight);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80F));
        String text = "Game Adventure";
        int x = getXforCenteredText(text);
        int y = gp.tile_size * 3;
        //Shadow
        g2.setColor(Color.black);
        g2.drawString(text, x+5, y+5);
        //String
        g2.setColor(Color.white);
        g2.drawString(text, x, y);
        //character image
        x = gp.ScreenWidth / 2 - (gp.tile_size * 2) / 2;
        y += gp.tile_size;
        g2.drawImage(gp.player.down1, x, y, gp.tile_size * 2, gp.tile_size * 2, null);

        // MENU
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 42F));
        text = "New Game";
        x = getXforCenteredText(text);
        y += gp.tile_size * 3.5;
        g2.drawString(text, x, y);
        if(commandNum == 0){
            g2.drawString(">", x - gp.tile_size, y);
        }
        text = "Load Game";
        x = getXforCenteredText(text);
        y += gp.tile_size;
        g2.drawString(text, x, y);
        if(commandNum == 1){
            g2.drawString(">", x - gp.tile_size, y);
        }
        text = "Quit";
        x = getXforCenteredText(text);
        y += gp.tile_size;
        g2.drawString(text, x, y);
        if(commandNum == 2){
            g2.drawString(">", x - gp.tile_size, y);
        }
    }

    public void drawDialogueScreen(){
        //window
        int x = gp.tile_size * 2;
        int y = gp.ScreenHeight - gp.tile_size * 5;
        int width = gp.ScreenWidth - (gp.tile_size * 4);
        int height = gp.tile_size * 4;
        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28));
        x += gp.tile_size;
        y += gp.tile_size;
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
        final int frameX = gp.tile_size * 2;
        final int frameY = gp.tile_size;
        final int frameWidth = gp.tile_size * 5;
        final int frameHeight = gp.tile_size * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        //text
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));

        int textX = frameX + 20;
        int textY = frameY + gp.tile_size;
        final int lineHeight = 32;

        //name
        g2.drawString("Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Life", textX, textY);
        textY += lineHeight;
        g2.drawString("Mana", textX, textY);
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
        textY = frameY + gp.tile_size;
        String value;

        value = String.valueOf(gp.player.level);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.mana + "/" + gp.player.maxMana);
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

        g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tile_size, textY - 15, null);
        textY += gp.tile_size;
        g2.drawImage(gp.player.currentShield.down1, tailX - gp.tile_size, textY - 15, null);
    }

    public void drawInventory(){
        // like bag in Minecraft
        // Create new frame in character screen
        int frameX = gp.tile_size * 12;
        int frameY = gp.tile_size;
        int frameWidth = gp.tile_size * 6;
        int frameHeight = gp.tile_size * 5;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        //slot
        final int slotXStart = frameX + 20;
        final int slotYStart = frameY + 20;
        int slotX = slotXStart;
        int slotY = slotYStart;

        //draw player's items
        for(int i = 0; i < gp.player.inventory.size(); i++){

            // equip cursor
            if(gp.player.inventory.get(i) == gp.player.currentWeapon ||
                    gp.player.inventory.get(i) == gp.player.currentShield){
                g2.setColor(new Color(240, 190, 90));
                g2.fillRoundRect(slotX, slotY, gp.tile_size, gp.tile_size, 10, 10);
            }
            g2.drawImage(gp.player.inventory.get(i).down1, slotX, slotY, null);
            slotX += gp.tile_size;
            if(i == 4 || i == 9 || i == 14){
                slotX = slotXStart;
                slotY += gp.tile_size;
            }
        }
        //cursor (chose and select an item)
        int cursorX = slotXStart + (gp.tile_size * slotCol);
        int cursorY = slotYStart + (gp.tile_size * slotRow);
        int cursorWidth = gp.tile_size;
        int cursorHeight = gp.tile_size;

        //draw cursor
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

        //description frame
        int dFrameX = frameX;
        int dFrameY = frameY + frameHeight;
        int dFrameWidth = frameWidth;
        int dFrameHeight = gp.tile_size * 3;
        //drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);//draw frame

        //draw description text
        int textX = dFrameX + 20;
        int textY = dFrameY + gp.tile_size;
        g2.setFont(g2.getFont().deriveFont(28F));
        int itemIndex = getItemIndexOnSlot();

        if(itemIndex < gp.player.inventory.size()){
            drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);//draw frame

            for(String line : gp.player.inventory.get(itemIndex).description.split("\n")) {
                g2.drawString(line, textX, textY);
                textY += 32;
            }
        }
    }

    public int getItemIndexOnSlot(){
        return slotCol + (slotRow * 5);
    }

    public void drawPauseScreen(){
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 60));
        String text = "PAUSE";
        int x = getXforCenteredText(text);
        int y = gp.ScreenHeight / 2;

        g2.drawString(text, x, y);
    }

    public void timeUpdate(){
        //Time to respawn
        timeCounter++;
        if (timeCounter >= 60) {
            timeCounter = 0;
            time--;
        }
        g2.setColor(Color.yellow);
        g2.setFont(g2.getFont().deriveFont(60f));
        String text = time + " second to respawn";
        int x = getXforCenteredText(text);
        int y = gp.tile_size * 6;
        g2.drawString(text, x, y);
        if(time == 0){
            gp.gameState = gp.playState;
            gp.retry();
            time = 5;
        }
    }

    public void drawGameOverScreen(){
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gp.ScreenWidth, gp.ScreenHeight);

        int x;
        int y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));

        text = "Game Over";
        //Shadow text
        g2.setColor(Color.black);
        x = getXforCenteredText(text);
        y = gp.tile_size * 4;
        g2.drawString(text, x, y);
        //Main
        g2.setColor(Color.white);
        g2.drawString(text, x-4, y-4);

        //time to respawn
        timeUpdate();

        //Retry
        g2.setFont(g2.getFont().deriveFont(50f));
        text = "Retry";
        x = getXforCenteredText(text);
        y += gp.tile_size * 4;
        g2.drawString(text, x, y);
        if(commandNum == 0){
            g2.drawString(">", x-35, y);
            if(gp.KeyH.enterPressed){
                gp.gameState = gp.playState;
                gp.retry();
                gp.playMusic(0);
            }
        }

        //menu screen
        text = "Menu";
        x = getXforCenteredText(text);
        y += 55;
        g2.drawString(text, x, y);
        if(commandNum == 1){
            g2.drawString(">", x-35, y);
            if(gp.KeyH.enterPressed){
                gp.gameState = gp.titleState;
                gp.restart();
                gp.playMusic(0);
            }
        }

        //quit
        text = "Quit";
        x = getXforCenteredText(text);
        y += 55;
        g2.drawString(text, x, y);
        if(commandNum == 2){
            g2.drawString(">", x-35, y);
            if(gp.KeyH.enterPressed){
                System.exit(0);
            }
        }
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
