package object;

import entity.Entity;
import entity.Projectiles;
import main.GamePanel;

public class obj_rock extends Projectiles {
    GamePanel gp;
    public obj_rock(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Rock";
        speed = 6;
        costUse = 1;
        maxLife = 70;
        life = maxLife;
        attack = 2;
//        costUse = 1;
        alive = false; // just shot 1 time (when fireball shill alive, can't shoot more)
        getImage();
    }

    public void getImage(){
        up1 = setup("/projectiles/rock_down_1", gp.tile_size, gp.tile_size);
        up2 = setup("/projectiles/rock_down_1", gp.tile_size, gp.tile_size);
        down1 = setup("/projectiles/rock_down_1", gp.tile_size, gp.tile_size);
        down2 = setup("/projectiles/rock_down_1", gp.tile_size, gp.tile_size);
        left1 = setup("/projectiles/rock_down_1", gp.tile_size, gp.tile_size);
        left2 = setup("/projectiles/rock_down_1", gp.tile_size, gp.tile_size);
        right1 = setup("/projectiles/rock_down_1", gp.tile_size, gp.tile_size);
        right2 = setup("/projectiles/rock_down_1", gp.tile_size, gp.tile_size);
    }

    public boolean haveResource(Entity user){ //check have enough mana, arrow to shoot
        boolean haveResources = false;
        if(user.ammo >= costUse){
            haveResources = true;
        }
        return haveResources;
    }

    public void subtractResource(Entity user){
        user.ammo -= costUse;
    }
}
