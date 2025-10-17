package object;

import entity.Entity;
import main.GamePanel;

public class obj_potion_red extends Entity {
    GamePanel gp;
    int healValue = 5;
    public obj_potion_red(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = typeConsumable;
        name = "Red potion";
        down1 = setup("/object/potion_red", gp.tile_size, gp.tile_size);
        description = "[Red potion]\n heal your life by " + healValue + " hp!";
    }
    public void use(Entity entity){
        gp.gameState = gp.dialogueState;
        gp.ui.currentDialogue = "You drink the " + name + "!\n"
        + "your life has been recovered by " + healValue + " hp!";

        entity.life += healValue;
        if(gp.player.life > gp.player.maxLife){
            gp.player.life = gp.player.maxLife;
        }
//        gp.playSE(2);
    }
}
