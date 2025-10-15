package object;

import main.GamePanel;
import entity.Entity;

public class obj_key extends Entity {
    public obj_key(GamePanel gp){
        super(gp);
        name = "Key";
        down1 = setup("/object/key", gp.title_size, gp.title_size);
        //solidArea.x = 0; create soildArea to each object
    }
}
