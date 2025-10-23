package monster;

import main.GamePanel;
import entity.Entity;
import object.obj_rock;

import java.util.Random;

public class green_slime extends Entity {
    GamePanel gp;
    public green_slime(GamePanel gp) {
        super(gp);
        this.gp = gp;
        type = typeMonster;
        name = "Green Slime";
        speed = 1;
//        monsterDamage = 1;
        maxLife = 4;
        life = maxLife;
        attack = 2;
        defense = 0;
        exp = 3;
        projectiles = new obj_rock(gp);

        solidArea.x = 3;
        solidArea.y = 16;
        solidArea.width = 42;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }
    public void getImage(){
        up1 = setup("/monster/greenslime_down_1", gp.tile_size, gp.tile_size);
        up2 = setup("/monster/greenslime_down_2", gp.tile_size, gp.tile_size);
        down1 = setup("/monster/greenslime_down_1", gp.tile_size, gp.tile_size);
        down2 = setup("/monster/greenslime_down_2", gp.tile_size, gp.tile_size);
        left1 = setup("/monster/greenslime_down_1", gp.tile_size, gp.tile_size);
        left2 = setup("/monster/greenslime_down_2", gp.tile_size, gp.tile_size);
        right1 = setup("/monster/greenslime_down_1", gp.tile_size, gp.tile_size);
        right2 = setup("/monster/greenslime_down_2", gp.tile_size, gp.tile_size);
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

        int i = new Random().nextInt(100)+1;
        if(i > 99 && !projectiles.alive && shotAvailableCounter >= 45){
            projectiles.set(worldX, worldY, direction, true, this);
            gp.projectileList.add(projectiles);
            shotAvailableCounter = 0;
        }
    }
    public void damageReaction(){
        actionLookCounter = 0;
        direction = gp.player.direction; // to set monster go to far the player
    }
}
