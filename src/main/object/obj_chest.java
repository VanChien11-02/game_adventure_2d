package main.object;

import main.GamePanel;
import main.entity.Entity;

public class obj_chest extends Entity {
    public obj_chest(GamePanel gp) {
        super(gp);
        name = "Chest";
        down1 = setup("/object/chest", gp.title_size, gp.title_size);
        collision = true;
    }
}
