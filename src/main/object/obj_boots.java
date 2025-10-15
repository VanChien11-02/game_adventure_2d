package main.object;

import main.GamePanel;
import main.entity.Entity;

public class obj_boots extends Entity {
    public obj_boots(GamePanel gp){
        super(gp);
        name = "Boots";
        down1 = setup("/object/boots", gp.title_size, gp.title_size);
    }
}
