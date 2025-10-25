package tile_interactive;

import entity.Entity;
import main.GamePanel;

import java.awt.*;

public class it_dryTree extends InteractiveTIle{
    GamePanel gp;
    public it_dryTree(GamePanel gp, int col, int row) {
        super(gp);
        this.gp = gp;

        life = 3;
        this.worldX = gp.tile_size * col;
        this.worldY = gp.tile_size * row;
        down1 = setup("/tile_interactive/drytree", gp.tile_size, gp.tile_size);
        destructible = true;
    }

    public boolean isCorrectItem(Entity entity){
        boolean isCorrect = false;
        if(entity.currentWeapon.type == typeAxe){
            isCorrect = true;
        }
        return isCorrect;
    }

    public void playSE(){
        gp.playSE(11);
    }

    public InteractiveTIle getDestroyedForm(){
        InteractiveTIle tile = new it_trunk(gp, worldX/gp.tile_size, worldY/    gp.tile_size);
        return tile;
    }

    public Color getParticleColor(){
        Color color = new Color(65, 50, 30);
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
