package main.object;

import main.GamePanel;
import main.entity.Entity;

public class obj_shield_wood extends Entity {
    public obj_shield_wood(GamePanel gp){
        super(gp);

        name = "Wood Shield";
        down1 = setup("/object/shield_wood", gp.title_size, gp.title_size);
        defenseValue = 1;
    }
}
