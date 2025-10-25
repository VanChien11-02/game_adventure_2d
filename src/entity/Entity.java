package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

// npc, player, monster, boss
public class Entity {
    GamePanel gp;

    // mô tả image với bộ đệm(buffer) có thể truy cập image data
    public BufferedImage up1, up2, down1, down2, right1, right2, left1, left2;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1,
            attackLeft2, attackRight1, attackRight2;

    //state
    //public int x, y; to player
    public int worldX, worldY;  //pixel
    public String direction = "down";

//    public int spriteCounter = 0; // đếm ảnh
//    public int spriteNum = 1;
    public int movementCounter = 0;
    public int movementNum = 1;

    public int attackCounter = 0;
    public int attackNum = 1;
    boolean attacking = false;

    public int shotAvailableCounter = 0;

    //make a hcn around player to collision
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48); //create soild to every entity
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    // check collision the object
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;

    public int actionLookCounter = 0;
//    public int monsterDamage;
    public boolean hpBarOn = false;
    public boolean alive = true;
    public boolean dying = false;
    int dyingCounter = 0;
    int hpBarCounter = 0;

    // to make player just take one damage in one time
    public boolean invincible = false;
    public int invincibleCounter = 0;

    //dialogue
    String[] dialogue = new String[20];
    int dialogueIndex = 0;

    //Character attributes
    public String name;
    public int speed;
    public int maxLife;
    public int life;
    public int maxMana;
    public int mana;
    public int ammo; // total rock, arrow can shoot
    public int level;
    public int strength;
    public int dexterity;
    public int attack;
    public int defense;
    public int exp;
    public int nextLevelExp;
    public int coin;
    public Entity currentWeapon;
    public Entity currentShield;
    public Projectiles projectiles;

    //item attributes
    public int attackValue;
    public int defenseValue;
    public String description = "";
    public int costUse; //use mana to projectile fireball

    //Object
    public BufferedImage image, image2, image3;
    public boolean collision = false;
    public int value;

    //type entity, weapon, (obj future)
    public int type; // 0 = player, 1 = npc, 2 = monster
    public final int typePlayer = 0;
    public final int typeNpc = 1;
    public final int typeMonster= 2;
    public final int typeSword = 3;
    public final int typeShield= 4;
    public final int typeAxe= 5;
    public final int typeConsumable= 6;
    public final int typePickUpOnly = 7;

    public Entity(GamePanel gp){
        this.gp = gp;
    }

    public BufferedImage setup(String imagePath, int width, int height){
        UtilityTool uTool = new UtilityTool();
        BufferedImage Image = null;
        try{
            Image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            Image = uTool.scaleImage(Image, width, height);
        } catch (IOException e){
            e.printStackTrace();
        }
        return Image;
    }

    public void setAction(){}

    public void damageReaction(){}

    public void use(Entity entity){}

    public void checkDrop(){}

    public void dropItem(Entity droppedItem){
        for(int i = 0; i < gp.obj.length; i++){
            if(gp.obj[i] == null){
                gp.obj[i] = droppedItem;
                gp.obj[i].worldX = worldX; // item drop where monster die
                gp.obj[i].worldY = worldY;
                break;
            }
        }
    }

    public void speak(){
        if(dialogue[dialogueIndex] == null){
            dialogueIndex = 0;
        }
        gp.ui.currentDialogue = dialogue[dialogueIndex];
        dialogueIndex++;
        switch (gp.player.direction){
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
        }
    }

    public void update(){
        setAction();
        collisionOn = false;
        //check collision between entity
        gp.check.checkTile(this);
        gp.check.checkObject(this, false);
        gp.check.checkEntity(this, gp.npc);
        gp.check.checkEntity(this, gp.monster);
        gp.check.checkEntity(this, gp.iTile);
        boolean contactPlayer = gp.check.checkPlayer(this);

        if(this.type == typeMonster && contactPlayer){
            damagePlayer(attack);
        }
        if(!collisionOn){
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
        if(invincible){
            invincibleCounter++;
            if(invincibleCounter > 30){
                invincible = false;
                invincibleCounter = 0;
            }
        }
        if(shotAvailableCounter < 45){
            shotAvailableCounter++;
        }
    }

    public void damagePlayer(int attack){
        if(!gp.player.invincible){
            gp.playSE(6);

            int damage = attack - gp.player.defense;
            if(damage < 0){
                damage = 0;
            }
            gp.player.life -= damage;
            gp.player.invincible = true;
        }
    }

    public void draw(Graphics2D g2){
        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;//to put entity at the center
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        //just draw in the screen( improve efficiency( hiệu suất))
        if(worldX + gp.tile_size > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tile_size < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tile_size > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tile_size < gp.player.worldY + gp.player.screenY) {

            switch (direction){
                case "up":
                    if(movementNum == 1) {
                        image = up1;
                    }
                    if(movementNum == 2) {
                        image = up2;
                    }
                    break;
                case "down":
                    if(movementNum == 1) {
                        image = down1;
                    }
                    if(movementNum == 2) {
                        image = down2;
                    }
                    break;
                case "left":
                    if(movementNum == 1) {
                        image = left1;
                    }
                    if(movementNum == 2) {
                        image = left2;
                    }
                    break;
                case "right":
                    if(movementNum == 1) {
                        image = right1;
                    }
                    if(movementNum == 2) {
                        image = right2;
                    }
                    break;

            }

            //monster HP bar
            if(type == typeMonster && hpBarOn) {
                double oneScale = (double)gp.tile_size /maxLife;
                double hpBarValue = oneScale * life;

                g2.setColor(new Color(35,35,35));
                g2.fillRect(screenX - 1, screenY - 16, gp.tile_size + 2, 12);

                g2.setColor(new Color(255, 0, 30));
                g2.fillRect(screenX, screenY - 15, (int)hpBarValue, 10);

                hpBarCounter++;
                if(hpBarCounter > 600){
                    hpBarOn = false;
                }
            }

            if(invincible){
                hpBarOn = true; // when player attack monster, hp bar will display
                hpBarCounter = 0;
                changeAlpha(g2, 0.4f);
            }
            if(dying){
                dyingAnimation(g2);
            }
            g2.drawImage(image, screenX, screenY,null);
            //reset alpha
            changeAlpha(g2, 1f);
        }
    }
    public void dyingAnimation(Graphics2D g2){
        dyingCounter++;
        if((dyingCounter/2)%2==0){
            changeAlpha(g2, 0f);
        } else{
            changeAlpha(g2, 1f);
        }
        if(dyingCounter > 40){
            alive = false;
        }
        if(dyingCounter > 45){
            dying = false;
            dyingCounter = 0;
        }
    }
    public void changeAlpha(Graphics2D g2, float alphaValue){
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }
}
