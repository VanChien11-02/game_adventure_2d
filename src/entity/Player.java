package entity;

import main.GamePanel;
import main.KeyHandler;
import object.obj_shield_wood;
import object.obj_sword_normal;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity{
    KeyHandler KeyH;
//    public int hasKey = 0;

    public final int screenX; // to center player of this screen
    public final int screenY;
    int standCount = 0; // count stand player
    public boolean attackCanceled = false;

    public Player(GamePanel gp, KeyHandler KeyH){
        super(gp); //call super class play and insert gp -> class entity can work
        this.KeyH = KeyH;
        screenX = gp.ScreenWidth / 2 - (gp.title_size / 2);
        screenY = gp.ScreenHeight / 2 - (gp.title_size / 2);

        //solidArea = new Rectangle(0, 16, 32, 32);
        solidArea = new Rectangle();
        // small hcn in side player
        solidArea.x = 10;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 28;
        solidArea.height = 28;

        attackArea.width = 36;
        attackArea.height = 36;
        setDefaultValues();
        getPlayerImage();
        getPLayerAttackImage();
    }
    public void setDefaultValues(){
//        x = 100;
//        y = 100;
        worldX = gp.title_size * 23; // starting location of player
        worldY = gp.title_size * 21;
        speed = 4;
        direction = "down";

        //player status
        level = 1;
        maxLife = 8;
        life = maxLife;
        strength = 1; // more strength -> more damage
        dexterity = 1; // more dexterity -> less damage to player
        exp = 0;
        nextLevelExp = 5;
        coin = 0;
        currentWeapon = new obj_sword_normal(gp); // the total attack value is decided by strength and weapon
        currentShield = new obj_shield_wood(gp); // the total defend value is decided by dexterity and shield
        attack = getAttack();
        defense = getDefense();
    }

    public int getAttack(){
        return strength * currentWeapon.attackValue;
    }

    public int getDefense(){
        return dexterity * currentShield.defenseValue;
    }

    public void getPlayerImage(){
        up1 = setup("/player/boy_up_1", gp.title_size, gp.title_size);
        up2 = setup("/player/boy_up_2", gp.title_size, gp.title_size);
        down1 = setup("/player/boy_down_1", gp.title_size, gp.title_size);
        down2 = setup("/player/boy_down_2", gp.title_size, gp.title_size);
        left1 = setup("/player/boy_left_1", gp.title_size, gp.title_size);
        left2 = setup("/player/boy_left_2", gp.title_size, gp.title_size);
        right1 = setup("/player/boy_right_1", gp.title_size, gp.title_size);
        right2 = setup("/player/boy_right_2", gp.title_size, gp.title_size);
    }

    public void getPLayerAttackImage(){
        attackUp1 = setup("/player/boy_attack_up_1", gp.title_size, gp.title_size*2);
        attackUp2 = setup("/player/boy_attack_up_2", gp.title_size, gp.title_size * 2);
        attackDown1 = setup("/player/boy_attack_down_1", gp.title_size, gp.title_size * 2);
        attackDown2 = setup("/player/boy_attack_down_2", gp.title_size, gp.title_size * 2);
        attackLeft1 = setup("/player/boy_attack_left_1", gp.title_size * 2, gp.title_size);
        attackLeft2 = setup("/player/boy_attack_left_2", gp.title_size * 2, gp.title_size);
        attackRight1 = setup("/player/boy_attack_right_1", gp.title_size * 2, gp.title_size);
        attackRight2 = setup("/player/boy_attack_right_2", gp.title_size * 2, gp.title_size);
    }
    public void update(){ //update 60time/second
        if(attacking) {
            attacking();
        }
        if(KeyH.rightPressed || KeyH.leftPressed ||
                KeyH.upPressed || KeyH.downPressed || KeyH.enterPressd){ // to stop animation(don't need to much)
            if(KeyH.upPressed){
                direction = "up";
            } else if (KeyH.downPressed) {
                direction = "down";
            } else if (KeyH.leftPressed) {
                direction = "left";
            } else if (KeyH.rightPressed) {
                direction = "right";
            }

            // check tile collision
            collisionOn = false;
            gp.check.checkTile(this);

            // check object collision
            int checkObjIndex = gp.check.checkObject(this, true);
            pickUpObject(checkObjIndex);

            // check npc colision
            int checkNPCIndex = gp.check.checkEntity(this, gp.npc);
            interactNPC(checkNPCIndex);
            // check monster collision
            int checkMonsterIndex = gp.check.checkEntity(this, gp.monster);
            contactMonster(checkMonsterIndex);
            //check event
            gp.eHandler.checkEvent();

            //if collision == false -> player can move
            if(!collisionOn && !KeyH.enterPressd){
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

            if(KeyH.enterPressd && !attackCanceled){
                attacking = true;
                gp.playSE(7);
                standCount = 0;
            }

            attackCanceled = false;
            gp.KeyH.enterPressd = false;
            // Movement...
            movementCounter++;
            if (movementCounter > 12) {
                if (movementNum == 1) movementNum = 2;
                else if (movementNum == 2) movementNum = 1;
                movementCounter = 0;
            }
        } else{
            standCount++;
            if(standCount > 20){
                movementNum = 1;
                standCount = 0;
            }
        }
        if(invincible){
            invincibleCounter++;
            if(invincibleCounter > 45){
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    public void attacking(){
        attackCounter++;
        if(attackCounter <= 5){ // sprite1 in 5 frame
            attackNum  = 1;
        }
        if(attackCounter > 5 && attackCounter <= 20){ // sprite2 in 6 -> 25 frame
            attackNum = 2;
            // to save position player
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;
            //adjust player's worldX worldY for the attackArea
            switch (direction){
                case "up":
                    worldY -= attackArea.height;
                    break;
                case "down":
                    worldY += attackArea.height;
                    break;
                case "left":
                    worldX -= attackArea.width;
                    break;
                case "right":
                    worldX += attackArea.width;
            }
            //attack area become solid area
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            //check monster collision with the updated worldX, worldY and soildArea
            int monsterIndex = gp.check.checkEntity(this, gp.monster);
            damageMonster(monsterIndex);

            //reset
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }
        if(attackCounter > 20){
            attackNum = 1;
            attackCounter = 0;
            attacking = false;
        }
    }
// future need this to pick up something
    public void pickUpObject(int index){
        if(index != 999) {

////           gp.obj[index] = null;
//            String objectName = gp.obj[index].name;
//            switch (objectName){
//                case "Key":
////                    gp.stopMusic();
//                    gp.playSE(1);
//                    hasKey++;
//                    gp.obj[index] = null;
//                    gp.ui.showMessage("You got a key");
////                    System.out.println("Key: " + hasKey);
//                    break;
//                case "Door":
//                    if(hasKey > 0){
//                        gp.playSE(4);
//                        gp.obj[index] = null;
//                        hasKey--;
//                        gp.ui.showMessage("You opened the door");
//
////                        System.out.println("Key: " + hasKey);
//                    } else{
//                        gp.ui.showMessage("You need a key");
//
//                    }
//                    break;
//                case "Boots":
//                    gp.playSE(3);
//                    speed += 2;
//                    gp.obj[index] = null;
//                    gp.ui.showMessage("Speed up!");
//                    break;
//                case "Chest":
//                    gp.ui.gameFinished = true;
//                    gp.stopMusic();
//                    gp.playSE(2);
//                    break;
//            }
//        }
        }
    }

    public void interactNPC(int index){
        if(gp.KeyH.enterPressd) {
            if (index != 999) {
                attackCanceled = true;
//          System.out.println("You are hitting an npc");
                gp.gameState = gp.dialogueState;
                gp.npc[index].speak();
            }
        }
    }

    public void contactMonster(int index){
        if(index != 999){
            if(!invincible && !gp.monster[index].dying) {
                int damage = gp.monster[index].attack - defense;
                if(damage < 0){
                    damage = 0;
                }
                gp.playSE(6);
                life -= damage;
                invincible = true;
            }
        }
    }

    public void damageMonster(int index){
        if(index != 999){
          if(!gp.monster[index].invincible){
              gp.playSE(5);

              int damage = attack - gp.monster[index].defense;
              if (damage < 0) {
                  damage = 0;
              }

              gp.monster[index].life -= damage;

              gp.ui.addMessage(damage + " damage!");
              gp.monster[index].invincible = true;
              gp.monster[index].damageReaction();
              if(gp.monster[index].life <= 0){
                  gp.monster[index].dying = true;
                  gp.ui.addMessage("killed the " + gp.monster[index].name);
                  gp.ui.addMessage("Exp: " + gp.monster[index].exp);
                  exp += gp.monster[index].exp;
                  checkLevelUp();
              }
          }
        }
    }

    public void checkLevelUp(){
        if(exp >= nextLevelExp){
            level++;
            gp.ui.addMessage("Level Up!");
            exp = exp - nextLevelExp;
            nextLevelExp = nextLevelExp * 2;
            maxLife += 2;
            strength++;
//            dexterity++;
            attack = getAttack();
//            defense = getDefense();

            gp.playSE(8);
            gp.gameState = gp.dialogueState;
            gp.ui.currentDialogue = "You are level " + level + "now!";
        }
    }
    public void draw(Graphics2D g2){
//        g2.setColor(Color.white);
//        g2.fillRect(x, y, gp.title_size, gp.title_size); // draw hcn

        BufferedImage image = null;

        //change the screen when player attack
        int tempScreenX = screenX;
        int tempScreenY = screenY;
        switch (direction){
            case "up":
                if(!attacking) {
                    if (movementNum == 1) {
                        image = up1;
                    }
                    if (movementNum == 2) {
                        image = up2;
                    }
                } else{
                    tempScreenY = screenY - gp.title_size;
                    if (attackNum == 1) {
                        image = attackUp1;
                    }
                    if (attackNum == 2) {
                        image = attackUp2;
                    }
                }
                break;
            case "down":
                if (!attacking) {
                    if (movementNum == 1) {
                        image = down1;
                    }
                    if (movementNum == 2) {
                        image = down2;
                    }
                } else{
                    if (attackNum == 1) {
                        image = attackDown1;
                    }
                    if (attackNum == 2) {
                        image = attackDown2;
                    }
                }
                break;
            case "left":
                if(!attacking) {
                    if (movementNum == 1) {
                        image = left1;
                    }
                    if (movementNum == 2) {
                        image = left2;
                    }
                } else{
                    tempScreenX = screenX - gp.title_size;
                    if (attackNum == 1) {
                        image = attackLeft1;
                    }
                    if (attackNum == 2) {
                        image = attackLeft2;
                    }
                }
                break;
            case "right":
                if(!attacking) {
                    if (movementNum == 1) {
                        image = right1;
                    }
                    if (movementNum == 2) {
                        image = right2;
                    }
                } else{
                    if (attackNum == 1) {
                        image = attackRight1;
                    }
                    if (attackNum == 2) {
                        image = attackRight2;
                    }
                }
                break;
        }
        //when player take damage and invincible in 1s, player will be transparent
        if(invincible){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        }
        g2.drawImage(image, tempScreenX, tempScreenY, null);

        //reset alpha
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

//        g2.setColor(Color.RED);
//        g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);

        //Debug: invincible
//        g2.setFont(new Font("Arial", Font.PLAIN, 26));
//        g2.setColor(Color.white);
//        g2.drawString("Invincivle: " + invincibleCounter, 10, 400);

        // DEBUG
        // AttackArea
//        tempScreenX = screenX + solidArea.x;
//        tempScreenY = screenY + solidArea.y;
//        switch(direction) {
//            case "up": tempScreenY = screenY - attackArea.height; break;
//            case "down": tempScreenY = screenY + gp.title_size; break;
//            case "left": tempScreenX = screenX - attackArea.width; break;
//            case "right": tempScreenX = screenX + gp.title_size; break;
//        }
//        g2.setColor(Color.red);
//        g2.setStroke(new BasicStroke(1));
//        g2.drawRect(tempScreenX, tempScreenY, attackArea.width, attackArea.height);
    }
}
