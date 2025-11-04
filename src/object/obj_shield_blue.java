package object;

import entity.Entity;
import main.GamePanel;

public class obj_shield_blue extends Entity {
    public obj_shield_blue(GamePanel gp) {
        super(gp);
        name = "Shield Blue";
        type = typeShield;
        down1 = setup("/object/shield_blue", gp.tile_size, gp.tile_size);
        defenseValue = 2;
        description = "This better than \nshield wood";
        price = 30;
    }
}
