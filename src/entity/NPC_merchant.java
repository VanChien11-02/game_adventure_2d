package entity;

import main.GamePanel;
import object.*;

import java.awt.*;

public class NPC_merchant extends Entity{

    public NPC_merchant(GamePanel gp) {
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
        setItems();
    }
    public void GetNPCimage(){
        up1 = setup("/npc/merchant_down_1", gp.tile_size, gp.tile_size);
        up2 = setup("/npc/merchant_down_2", gp.tile_size, gp.tile_size);
        down1 = setup("/npc/merchant_down_1", gp.tile_size, gp.tile_size);
        down2 = setup("/npc/merchant_down_2", gp.tile_size, gp.tile_size);
        left1 = setup("/npc/merchant_down_1", gp.tile_size, gp.tile_size);
        left2 = setup("/npc/merchant_down_2", gp.tile_size, gp.tile_size);
        right1 = setup("/npc/merchant_down_1", gp.tile_size, gp.tile_size);
        right2 = setup("/npc/merchant_down_2", gp.tile_size, gp.tile_size);
    }

    public void setDialogue(){
        dialogue[0] = "Wow, you found me. \nHave some good stuff. \nDo you want to trade?";
    }

    public void setItems() {
        inventory.add(new obj_key(gp));
        inventory.add(new obj_potion_red(gp));
        inventory.add(new obj_shield_blue(gp));
        inventory.add(new obj_sword_normal(gp));
        inventory.add(new obj_axe(gp));
    }

    public void speak(){
        super.speak();
        gp.gameState = gp.tradeState;
        gp.ui.npc = this;
    }
}
