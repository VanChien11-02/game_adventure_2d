package tile_interactive;

import entity.Entity;
import main.GamePanel;

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
}
