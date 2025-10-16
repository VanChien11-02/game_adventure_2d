package object;

import main.GamePanel;
import entity.Entity;

public class obj_heart extends Entity {
    public obj_heart(GamePanel gp) {
        super(gp);
        name = "Heart";
        image = setup("/object/heart_full", gp.tile_size, gp.tile_size);
        image2 = setup("/object/heart_half", gp.tile_size, gp.tile_size);
        image3 = setup("/object/heart_blank", gp.tile_size, gp.tile_size);
    }
}
