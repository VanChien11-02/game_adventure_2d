package object;

import entity.Entity;
import main.GamePanel;

public class obj_mana extends Entity {
    GamePanel gp;
    public obj_mana(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = "Mana Crystal";
        image = setup("/object/manacrystal_full", gp.tile_size, gp.tile_size);
        image2 = setup("/object/manacrystal_blank", gp.tile_size, gp.tile_size);

    }
}
