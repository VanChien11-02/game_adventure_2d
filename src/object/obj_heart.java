package object;

import main.GamePanel;
import entity.Entity;

public class obj_heart extends Entity {
    GamePanel gp;
    public obj_heart(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = typePickUpOnly;
        name = "Heart";
        value = 2;
        down1 = setup("/object/heart_full", gp.tile_size, gp.tile_size);
        image = setup("/object/heart_full", gp.tile_size, gp.tile_size);
        image2 = setup("/object/heart_half", gp.tile_size, gp.tile_size);
        image3 = setup("/object/heart_blank", gp.tile_size, gp.tile_size);
    }
    public void use(Entity entity){
//        gp.playSE(2);
        gp.ui.addMessage("Healing " + value);
        entity.life += value;
    }
}
