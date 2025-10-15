package entity;

import main.GamePanel;

import java.awt.*;
import java.util.Random;

public class NPC_oldMan extends Entity{
    public NPC_oldMan(GamePanel gp){
        super(gp);
        direction = "down";
        speed = 1;

        solidArea = new Rectangle();
        // small hcn in side npc
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;
        GetNPCimage();
        setDialogue();
    }
    public void GetNPCimage(){
        up1 = setup("/npc/oldman_up_1", gp.title_size, gp.title_size);
        up2 = setup("/npc/oldman_up_2", gp.title_size, gp.title_size);
        down1 = setup("/npc/oldman_down_1", gp.title_size, gp.title_size);
        down2 = setup("/npc/oldman_down_2", gp.title_size, gp.title_size);
        left1 = setup("/npc/oldman_left_1", gp.title_size, gp.title_size);
        left2 = setup("/npc/oldman_left_2", gp.title_size, gp.title_size);
        right1 = setup("/npc/oldman_right_1", gp.title_size, gp.title_size);
        right2 = setup("/npc/oldman_right_2", gp.title_size, gp.title_size);
    }
    public void setDialogue(){
        dialogue[0] = "Hello friend";
        dialogue[1] = "Welcome to this island";
        dialogue[2] = "This island has treasure, \n so you can find it";
        dialogue[3] = "if you need help, just call me";
        dialogue[4] = "Goob luck on you";
    }
    public void setAction(){ //to update action in entity
        actionLookCounter++;
        if(actionLookCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100) + 1; //[1, 100]
            if (i <= 25) {
                direction = "up";
            }
            if (i > 25 && i <= 50) {
                direction = "down";
            }
            if (i > 50 && i <= 75) {
                direction = "left";
            }
            if (i > 75) {
                direction = "right";
            }
            actionLookCounter = 0;
        }
    }
    public void speak(){
        // do something character specific stuff
        super.speak();
    }
}
