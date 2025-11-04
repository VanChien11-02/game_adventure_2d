package object;

import entity.Entity;
import main.GamePanel;

public class obj_potion_red extends Entity {
    GamePanel gp;
    public obj_potion_red(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = typeConsumable;
        name = "Red potion";
        value = 5;
        down1 = setup("/object/potion_red", gp.tile_size, gp.tile_size);
        description = "[Red potion]\n heal your life by " + value + " hp!";
        price = 75;
    }
    public void use(Entity entity){
        gp.gameState = gp.dialogueState;
        gp.ui.currentDialogue = "You drink the " + name + "!\n"
        + "your life has been recovered by " + value + " hp!";

        entity.life += value;
//        gp.playSE(2);
    }
}
