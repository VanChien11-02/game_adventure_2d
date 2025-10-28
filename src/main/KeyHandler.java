package main;

import java.awt.event.*;

public class KeyHandler implements KeyListener {
    GamePanel gp;
    public boolean leftPressed, rightPressed, upPressed, downPressed, enterPressed, shotKeyPressed;
    boolean showDebugText = false; //to show time and position

    public KeyHandler(GamePanel gp){
        this.gp = gp;
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) { // nhấn phím
        int code = e.getKeyCode(); // return key in event

        //PLAY STATE
        if(gp.gameState == gp.playState) {
            playState(code);
        }
        //PAUSE STATE
        else if (gp.gameState == gp.pauseState){
            pauseState(code);
        }
        //Dialogue STATE
        else if (gp.gameState == gp.dialogueState){
            dialogueState(code);
        }
        //Character STATE
        else if (gp.gameState == gp.characterState) {
           characterState(code);
        }
        //TITLE STATE
        else if (gp.gameState == gp.titleState) {
            tileState(code);
        }
        //OPTION STATE (MENU)
        else if (gp.gameState == gp.optionState) {
            optionState(code);
        }
    }

    public void tileState(int code){
        if (code == KeyEvent.VK_S) {
            gp.ui.commandNum++;
            if(gp.ui.commandNum > 2){
                gp.ui.commandNum = 0;
            }
        }
        if(code == KeyEvent.VK_W){
            gp.ui.commandNum--;
            if(gp.ui.commandNum < 0){
                gp.ui.commandNum = 2;
            }
        }
        if(code == KeyEvent.VK_ENTER){
            if(gp.ui.commandNum == 0){
                gp.gameState = gp.playState;
            } else if(gp.ui.commandNum == 1){
                //coming soon
            }else if(gp.ui.commandNum == 2){
                System.exit(0);
            }
        }
    }

    public void playState(int code){
        if (code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if (code == KeyEvent.VK_P) {
            gp.gameState = gp.pauseState;
        }
        if (code == KeyEvent.VK_C){
            gp.gameState = gp.characterState;
        }
        if(code == KeyEvent.VK_ENTER){
            enterPressed = true;
        }
        if(code == KeyEvent.VK_R){
            shotKeyPressed = true;
        }
        if(code == KeyEvent.VK_ESCAPE){
            gp.gameState = gp.optionState;
        }
        //debug
        if (code == KeyEvent.VK_T) {
            if (!showDebugText) {
                showDebugText = true;
            } else {
                showDebugText = false;
            }
        }
        if (code == KeyEvent.VK_M){ //to change some tile when play game (don't need to exit game)
            gp.tileM.loadMap("/maps/worldV2.txt"); // but change map -> res -> maps -> rebuild -> M
        }
    }

    public void pauseState(int code){
        if (code == KeyEvent.VK_P) {
            gp.gameState = gp.playState;
        }
    }

    public void optionState(int code){
        if(code == KeyEvent.VK_ESCAPE){
            gp.gameState = gp.playState;
        }
        if(code == KeyEvent.VK_ENTER){
            enterPressed = true;
        }

        int maxCommandNum = 0;
        switch (gp.ui.subState){
            case 0: maxCommandNum = 5; break;
            case 3: maxCommandNum = 1; break;
        }
        if(code == KeyEvent.VK_W){
            gp.ui.commandNum--;
            if(gp.ui.commandNum < 0){
                gp.ui.commandNum = maxCommandNum;
            }
        }
        if(code == KeyEvent.VK_S){
            gp.ui.commandNum++;
            if(gp.ui.commandNum > maxCommandNum){
                gp.ui.commandNum = 0;
            }
        }
        if(code == KeyEvent.VK_A){
            if(gp.ui.subState == 0){
                //music
                if(gp.ui.commandNum == 1 && gp.music.volumeScale > 0){
                    gp.music.volumeScale--;
                    gp.music.checkVolume();
                }

                //se
                if(gp.ui.commandNum == 2 && gp.se.volumeScale > 0){
                    gp.se.volumeScale--;
                }
            }
        }
        if(code == KeyEvent.VK_D){
            if(gp.ui.subState == 0){
                //music
                if(gp.ui.commandNum == 1 && gp.music.volumeScale < 5){
                    gp.music.volumeScale++;
                    gp.music.checkVolume();
                }

                //se
                if(gp.ui.commandNum == 2 && gp.se.volumeScale < 5){
                    gp.se.volumeScale++;
                }
            }
        }
    }

    public void dialogueState(int code){
        if (code == KeyEvent.VK_ENTER){
            gp.gameState = gp.playState;
        }
    }

    public void characterState(int code){
        if (code == KeyEvent.VK_C){
            gp.gameState = gp.playState;
        }
        if (code == KeyEvent.VK_W){
            gp.ui.slotRow--;
            gp.playSE(10);
            if(gp.ui.slotRow < 0){
                gp.ui.slotRow = 0;
            }
        }
        if (code == KeyEvent.VK_S){
            gp.ui.slotRow++;
            gp.playSE(10);
            if(gp.ui.slotRow > 3){
                gp.ui.slotRow = 3;
            }
        }
        if (code == KeyEvent.VK_D){
            gp.ui.slotCol++;
            gp.playSE(10);
            if(gp.ui.slotCol > 4){
                gp.ui.slotCol = 4;
            }
        }
        if (code == KeyEvent.VK_A){
            gp.ui.slotCol--;
            gp.playSE(10);
            if(gp.ui.slotCol < 0){
                gp.ui.slotCol = 0;
            }
        }
        if (code == KeyEvent.VK_ENTER){
            gp.player.selectItem();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) { //phím resert
        int code = e.getKeyCode(); // return key in event
        if(code == KeyEvent.VK_W){
            upPressed = false;
        }
        if(code == KeyEvent.VK_A){
            leftPressed = false;
        }
        if(code == KeyEvent.VK_S){
            downPressed = false;
        }
        if(code == KeyEvent.VK_D){
            rightPressed = false;
        }
        if(code == KeyEvent.VK_R){
            shotKeyPressed = false;
        }
    }
}
