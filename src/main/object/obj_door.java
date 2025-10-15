package main.object;

import main.GamePanel;
import main.entity.Entity;

public class obj_door extends Entity {
    public obj_door(GamePanel gp){
        super(gp);
        name = "Door";
        down1 = setup("/object/door", gp.title_size, gp.title_size);
//        collision = true;

        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}
