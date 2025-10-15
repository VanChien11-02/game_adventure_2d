package main;

import javax.swing.*;
import java.awt.event.*;

public class KeyHandler implements KeyListener {
    GamePanel gp;
    public boolean leftPressed, rightPressed, upPressed, downPressed, enterPressd;
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
    }

    public void tileState(int code){
        if (code == KeyEvent.VK_DOWN) {
            gp.ui.commandNum++;
            if(gp.ui.commandNum > 2){
                gp.ui.commandNum = 0;
            }
        }
        if(code == KeyEvent.VK_UP){
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
            enterPressd = true;
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

    public void dialogueState(int code){
        if (code == KeyEvent.VK_ENTER){
            gp.gameState = gp.playState;
        }
    }

    public void characterState(int code){
        if (code == KeyEvent.VK_C){
            gp.gameState = gp.playState;
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
    }
}
