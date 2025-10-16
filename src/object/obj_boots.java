package object;

import main.GamePanel;
import entity.Entity;

public class obj_boots extends Entity {
    public obj_boots(GamePanel gp){
        super(gp);
        name = "Boots";
        down1 = setup("/object/boots", gp.tile_size, gp.tile_size);
    }
}
