package tile_interactive;

import entity.Entity;
import main.GamePanel;

import java.awt.*;

public class InteractiveTIle extends Entity {
    GamePanel gp;
    public boolean destructible = false;
    public InteractiveTIle(GamePanel gp) {
        super(gp);
        this.gp = gp;
    }

    public boolean isCorrectItem(Entity entity){
        boolean isCorrect = false;
        return isCorrect;
    }

    public void playSE(){}

    public InteractiveTIle getDestroyedForm(){
        InteractiveTIle tile = null;
        return tile;
    }
    public void update(){
        if(invincible){
            invincibleCounter++;
            if(invincibleCounter > 20){
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    public void draw(Graphics2D g2){
        int screenX = worldX - gp.player.worldX + gp.player.screenX;//to put entity at the center
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        if(worldX + gp.tile_size > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tile_size < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tile_size > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tile_size < gp.player.worldY + gp.player.screenY) {
            g2.drawImage(down1, screenX, screenY,null);
        }
    }
}
