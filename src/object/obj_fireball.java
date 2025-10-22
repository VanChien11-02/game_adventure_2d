package object;

import entity.Projectiles;
import main.GamePanel;

public class obj_fireball extends Projectiles {
    GamePanel gp;
    public obj_fireball(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Fireball";
        speed = 8;
        maxLife = 80;
        life = maxLife;
        attack = 2;
        costUse = 1;
        alive = false; // just shot 1 time (when fireball shill alive, can't shoot more)
        getImage();
    }

    public void getImage(){
        up1 = setup("/projectiles/fireball_up_1", gp.tile_size, gp.tile_size);
        up2 = setup("/projectiles/fireball_up_2", gp.tile_size, gp.tile_size);
        down1 = setup("/projectiles/fireball_down_1", gp.tile_size, gp.tile_size);
        down2 = setup("/projectiles/fireball_down_2", gp.tile_size, gp.tile_size);
        left1 = setup("/projectiles/fireball_left_1", gp.tile_size, gp.tile_size);
        left2 = setup("/projectiles/fireball_left_2", gp.tile_size, gp.tile_size);
        right1 = setup("/projectiles/fireball_right_1", gp.tile_size, gp.tile_size);
        right2 = setup("/projectiles/fireball_right_2", gp.tile_size, gp.tile_size);
    }
}
