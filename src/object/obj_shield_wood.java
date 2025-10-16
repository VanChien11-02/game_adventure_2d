package object;

import main.GamePanel;
import entity.Entity;

public class obj_shield_wood extends Entity {
    public obj_shield_wood(GamePanel gp){
        super(gp);

        name = "Wood Shield";
        down1 = setup("/object/shield_wood", gp.tile_size, gp.tile_size);
        defenseValue = 1;
        description = "[" + name + "] \n An old shield.";
    }
}
