package object;

import entity.Entity;
import main.GamePanel;

public class obj_axe extends Entity {
    public obj_axe(GamePanel gp){
        super(gp);
        name = "Axe";
        type = typeAxe;
        down1 = setup("/object/axe", gp.tile_size, gp.tile_size);
        attackValue = 2;
        attackArea.width = 28;
        attackArea.height = 28;
        description = "The axe is from \nsomeone";
    }
}
