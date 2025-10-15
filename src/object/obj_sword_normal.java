package object;

import main.GamePanel;
import entity.Entity;

public class obj_sword_normal extends Entity {
    public obj_sword_normal(GamePanel gp) {
        super(gp);
        name = "Normal Sword";
        down1 = setup("/object/sword_normal", gp.title_size, gp.title_size);
        attackValue = 1;
    }
}
