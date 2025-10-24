package object;

import entity.Entity;
import main.GamePanel;

public class obj_mana extends Entity {
    GamePanel gp;
    public obj_mana(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = typePickUpOnly;
        name = "Mana Crystal";
        value = 1;
        image = setup("/object/manacrystal_full", gp.tile_size, gp.tile_size);
        image2 = setup("/object/manacrystal_blank", gp.tile_size, gp.tile_size);
    }
    public void use(Entity entity){
//        gp.playSE(2);
        gp.ui.addMessage("Mana " + value);
        entity.mana += value;
    }
}
