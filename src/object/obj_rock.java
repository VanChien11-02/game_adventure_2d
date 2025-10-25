package object;

import entity.Entity;
import entity.Projectiles;
import main.GamePanel;

import java.awt.*;

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
    public Color getParticleColor(){
        Color color = new Color(40, 50, 0);
        return color;
    }

    public int getParticleSize(){
        int size = 6; //6 pixels
        return size;
    }

    public int getParticleSpeed(){
        int speed = 1;
        return speed;
    }

    public int getParticleMaxLife(){
        int maxLife = 20;
        return maxLife;
    }
}
