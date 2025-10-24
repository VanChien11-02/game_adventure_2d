package object;

import entity.Entity;
import main.GamePanel;

public class obj_coin extends Entity {
    GamePanel gp;
    public obj_coin(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = typePickUpOnly;
        name = "Bronze Coin";
        value = 1;
        down1 = setup("/object/coin_bronze", gp.tile_size, gp.tile_size);
    }
    public void use(Entity entity){
        gp.playSE(1);
        gp.ui.addMessage("Coin " + value);
        gp.player.coin += value;
    }
}
