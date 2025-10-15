package object;

import main.GamePanel;
import entity.Entity;

public class obj_statue extends Entity {
    public obj_statue(GamePanel gp){
        super(gp);
        name = "Statue";
        down1 = setup("/object/chienbinh", gp.title_size, gp.title_size);
    }
}
