package tile_interactive;

import entity.Entity;
import main.GamePanel;

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
}
