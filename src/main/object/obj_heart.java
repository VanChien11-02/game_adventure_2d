package main.object;

import main.GamePanel;
import main.entity.Entity;

public class obj_heart extends Entity {
    public obj_heart(GamePanel gp) {
        super(gp);
        name = "Heart";
        image = setup("/object/heart_full", gp.title_size, gp.title_size);
        image2 = setup("/object/heart_half", gp.title_size, gp.title_size);
        image3 = setup("/object/heart_blank", gp.title_size, gp.title_size);
    }
}
