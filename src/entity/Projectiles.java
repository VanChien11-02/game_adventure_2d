package entity;

import main.GamePanel;

public class Projectiles extends Entity{
    Entity user;
    public Projectiles(GamePanel gp) {
        super(gp);
    }

    public void set(int worldX, int worldY, String direction, boolean alive, Entity user) {
        //user is help monster, npc can shoot like player
        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.alive = alive;
        this.user = user;
        this.life = this.maxLife; //reset life every time shoot it
    }

    public void update() {
        if(user == gp.player){
            int monsterIndex = gp.check.checkEntity(this, gp.monster);
            if(monsterIndex != 999){
                gp.player.damageMonster(monsterIndex, attack);
                alive = false; // hit -> disappears
            }
        } else{
            boolean contactPlayer = gp.check.checkPlayer(this);
            if(!gp.player.invincible && contactPlayer){
                damagePlayer(attack);
                alive = false;
            }
        }

        switch (direction){
            case "up":
                worldY -= speed;
                break;
            case "down":
                worldY += speed;
                break;
            case "left":
                worldX -= speed;
                break;
            case "right":
                worldX += speed;
                break;
        }

        life--;
        if(life <= 0){
            alive = false;
        }

        movementCounter++;
        if(movementCounter > 12) { //change in 12/second
            if (movementNum == 1) {
                movementNum = 2;
            } else if (movementNum == 2) {
                movementNum = 1;
            }
            movementCounter = 0;
        }
    }

    public boolean haveResource(Entity user){ //check have enough mana, arrow to shoot
        boolean haveResources = false;
        return haveResources;
    }

    public void subtractResource(Entity user){
    }
}
