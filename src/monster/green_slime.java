package monster;

import main.GamePanel;
import entity.Entity;

import java.util.Random;

public class green_slime extends Entity {
    GamePanel gp;
    public green_slime(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = 2;
        name = "Green Slime";
        speed = 1;
//        monsterDamage = 1;
        maxLife = 4;
        life = maxLife;
        attack = 2;
        defense = 0;
        exp = 3;

        solidArea.x = 3;
        solidArea.y = 16;
        solidArea.width = 42;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }
    public void getImage(){
        up1 = setup("/monster/greenslime_down_1", gp.title_size, gp.title_size);
        up2 = setup("/monster/greenslime_down_2", gp.title_size, gp.title_size);
        down1 = setup("/monster/greenslime_down_1", gp.title_size, gp.title_size);
        down2 = setup("/monster/greenslime_down_2", gp.title_size, gp.title_size);
        left1 = setup("/monster/greenslime_down_1", gp.title_size, gp.title_size);
        left2 = setup("/monster/greenslime_down_2", gp.title_size, gp.title_size);
        right1 = setup("/monster/greenslime_down_1", gp.title_size, gp.title_size);
        right2 = setup("/monster/greenslime_down_2", gp.title_size, gp.title_size);
    }

    public void setAction() {
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
    public void damageReaction(){
        actionLookCounter = 0;
        direction = gp.player.direction; // to set monster go to far the player
    }
}
