package entity;

import main.GamePanel;
import main.KeyHandler;
import object.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

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
        screenX = gp.ScreenWidth / 2 - (gp.tile_size / 2);
        screenY = gp.ScreenHeight / 2 - (gp.tile_size / 2);

        //solidArea = new Rectangle(0, 16, 32, 32);
        solidArea = new Rectangle();
        // small hcn in side player
        solidArea.x = 10;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 28;
        solidArea.height = 28;

        //Attack area
//        attackArea.width = 36;
//        attackArea.height = 36;

        setDefaultValues();
        getPlayerImage();
        getPLayerAttackImage();
        setItem();
    }
    public void setDefaultValues(){
//        x = 100;
//        y = 100;
        worldX = gp.tile_size * 23; // starting location of player
        worldY = gp.tile_size * 21;
//        worldX = gp.tile_size * 12; // starting location of player
//        worldY = gp.tile_size * 12;
//        gp.currentMap = 1;

        speed = 4;
        direction = "down";

        //player status
        level = 1;
        maxLife = 8;
        life = maxLife;
        maxMana = 4;
        mana = maxMana;
        ammo = 10;
        strength = 1; // more strength -> more damage
        dexterity = 1; // more dexterity -> less damage to player
        exp = 0;
        nextLevelExp = 5;
        coin = 500;
        currentWeapon = new obj_sword_normal(gp); // the total attack value is decided by strength and weapon
        currentShield = new obj_shield_wood(gp); // the total defend value is decided by dexterity and shield
        projectiles = new obj_fireball(gp);
//        projectiles = new obj_rock(gp);
        attack = getAttack();
        defense = getDefense();
    }

    public void setDefaultPositions(){
        worldX = gp.tile_size * 23;
        worldY = gp.tile_size * 21;
        direction = "down";
    }

    public void restoreLifeAndMana(){
        life = maxLife;
        mana = maxMana;
        invincible = false;
    }

    public void setItem() {
        inventory.clear();
        inventory.add(currentWeapon);
        inventory.add(currentShield);
        inventory.add(new obj_key(gp));
        inventory.add(new obj_chest(gp));
        inventory.add(new obj_boots(gp));
    }

    public int getAttack(){
        attackArea = currentWeapon.attackArea;
        return strength * currentWeapon.attackValue;
    }

    public int getDefense(){
        return dexterity * currentShield.defenseValue;
    }

    public void getPlayerImage(){
        up1 = setup("/player/boy_up_1", gp.tile_size, gp.tile_size);
        up2 = setup("/player/boy_up_2", gp.tile_size, gp.tile_size);
        down1 = setup("/player/boy_down_1", gp.tile_size, gp.tile_size);
        down2 = setup("/player/boy_down_2", gp.tile_size, gp.tile_size);
        left1 = setup("/player/boy_left_1", gp.tile_size, gp.tile_size);
        left2 = setup("/player/boy_left_2", gp.tile_size, gp.tile_size);
        right1 = setup("/player/boy_right_1", gp.tile_size, gp.tile_size);
        right2 = setup("/player/boy_right_2", gp.tile_size, gp.tile_size);
    }

    public void getPLayerAttackImage(){
        if (currentWeapon.type == typeSword) {
            attackUp1 = setup("/player/boy_attack_up_1", gp.tile_size, gp.tile_size * 2);
            attackUp2 = setup("/player/boy_attack_up_2", gp.tile_size, gp.tile_size * 2);
            attackDown1 = setup("/player/boy_attack_down_1", gp.tile_size, gp.tile_size * 2);
            attackDown2 = setup("/player/boy_attack_down_2", gp.tile_size, gp.tile_size * 2);
            attackLeft1 = setup("/player/boy_attack_left_1", gp.tile_size * 2, gp.tile_size);
            attackLeft2 = setup("/player/boy_attack_left_2", gp.tile_size * 2, gp.tile_size);
            attackRight1 = setup("/player/boy_attack_right_1", gp.tile_size * 2, gp.tile_size);
            attackRight2 = setup("/player/boy_attack_right_2", gp.tile_size * 2, gp.tile_size);
        }
        if (currentWeapon.type == typeAxe){
            attackUp1 = setup("/player/boy_axe_up_1", gp.tile_size, gp.tile_size *2);
            attackUp2 = setup("/player/boy_axe_up_2", gp.tile_size, gp.tile_size * 2);
            attackDown1 = setup("/player/boy_axe_down_1", gp.tile_size, gp.tile_size * 2);
            attackDown2 = setup("/player/boy_axe_down_2", gp.tile_size, gp.tile_size * 2);
            attackLeft1 = setup("/player/boy_axe_left_1", gp.tile_size * 2, gp.tile_size);
            attackLeft2 = setup("/player/boy_axe_left_2", gp.tile_size * 2, gp.tile_size);
            attackRight1 = setup("/player/boy_axe_right_1", gp.tile_size * 2, gp.tile_size);
            attackRight2 = setup("/player/boy_axe_right_2", gp.tile_size * 2, gp.tile_size);
        }
    }
    public void update(){ //update 60time/second
        if(attacking) {
            attacking();
        }
        if(KeyH.rightPressed || KeyH.leftPressed ||
                KeyH.upPressed || KeyH.downPressed || KeyH.enterPressed){ // to stop animation(don't need to much)
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

            //check interactive tile collision
            gp.check.checkEntity(this, gp.iTile);

            //if collision == false -> player can move
            if(!collisionOn && !KeyH.enterPressed){
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

            if(KeyH.enterPressed && !attackCanceled){
                attacking = true;
                if(currentWeapon.type == typeSword) {
//                    gp.playSE(7);
                } else if (currentWeapon.type == typeAxe) {
                    gp.playSE(9);
                }
                standCount = 0;
            }

            attackCanceled = false;
            gp.KeyH.enterPressed = false;
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

        if(gp.KeyH.shotKeyPressed && !projectiles.alive
                && shotAvailableCounter >= 45 && projectiles.haveResource(this)){
            //set default coordinates, direction and user
            projectiles.set(worldX, worldY, direction, true, this);

            //subtract the cost(mana,..)
            projectiles.subtractResource(this);
            //add it into arrayList
            gp.projectileList.add(projectiles);
            //add sound (future)

            shotAvailableCounter = 0;
        }

        if(invincible){
            invincibleCounter++;
            if(invincibleCounter > 45){
                invincible = false;
                invincibleCounter = 0;
            }
        }
        if(shotAvailableCounter < 45){
            shotAvailableCounter++;
        }
        if(life > maxLife){
            life = maxLife;
        }
        if(mana > maxMana){
            mana = maxMana;
        }
        if(life <= 0){
            gp.gameState = gp.gameOverState;
            gp.ui.commandNum = -1;
            gp.stopMusic();
//            gp.playMusic();
            gp.playSE(12);
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
            damageMonster(monsterIndex, attack);

            int iTileIndex = gp.check.checkEntity(this, gp.iTile);
            damageInteractiveTile(iTileIndex);

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

    public void damageInteractiveTile(int index){
        if(index != 999 && gp.iTile[gp.currentMap][index].destructible
        && gp.iTile[gp.currentMap][index].isCorrectItem(this) && !gp.iTile[gp.currentMap][index].invincible){
            gp.iTile[gp.currentMap][index].playSE();
            gp.iTile[gp.currentMap][index].life--;
            gp.iTile[gp.currentMap][index].invincible = true;

            //generate Particle
            generateParticle(gp.iTile[gp.currentMap][index], gp.iTile[gp.currentMap][index]);

            if(gp.iTile[gp.currentMap][index].life == 0) {
                gp.iTile[gp.currentMap][index] = gp.iTile[gp.currentMap][index].getDestroyedForm();
            }
        }
    }

    public void interactNPC(int index){
        if(gp.KeyH.enterPressed) {
            if (index != 999) {
                attackCanceled = true;
//          System.out.println("You are hitting an npc");
                gp.gameState = gp.dialogueState;
                gp.npc[gp.currentMap][index].speak();
            }
        }
    }

    public void contactMonster(int index){
        if(index != 999){
            if(!invincible && !gp.monster[gp.currentMap][index].dying) {
                int damage = gp.monster[gp.currentMap][index].attack - defense;
                if(damage < 0){
                    damage = 0;
                }
                gp.playSE(6);
                life -= damage;
                invincible = true;
            }
        }
    }

    public void damageMonster(int index, int attack){
        if(index != 999){
          if(!gp.monster[gp.currentMap][index].invincible){
              gp.playSE(5);

              int damage = attack - gp.monster[gp.currentMap][index].defense;
              if (damage < 0) {
                  damage = 0;
              }

              gp.monster[gp.currentMap][index].life -= damage;

              gp.ui.addMessage(damage + " damage!");
              gp.monster[gp.currentMap][index].invincible = true;
              gp.monster[gp.currentMap][index].damageReaction();
              if(gp.monster[gp.currentMap][index].life <= 0){
                  gp.monster[gp.currentMap][index].dying = true;
                  gp.ui.addMessage("killed the " + gp.monster[gp.currentMap][index].name);
                  gp.ui.addMessage("Exp: " + gp.monster[gp.currentMap][index].exp);
                  exp += gp.monster[gp.currentMap][index].exp;
                  checkLevelUp();
              }
          }
        }
    }

    public void pickUpObject(int i){
        if(i != 999 && !gp.obj[gp.currentMap][i].collision){
            //pickup only items
            if(gp.obj[gp.currentMap][i].type == typePickUpOnly){
                gp.obj[gp.currentMap][i].use(this);
                gp.obj[gp.currentMap][i] = null;
            } else {
                //inventory items
                String text;
                if (gp.obj[gp.currentMap][i].name != "Door" && gp.obj[gp.currentMap][i].name != "Statue") {
                    if (inventory.size() < maxInventorySize) {
                        inventory.add(gp.obj[gp.currentMap][i]);
                        gp.playSE(1);
                        text = "Got a " + gp.obj[gp.currentMap][i].name;
                    } else {
                        text = "You can't carry more";
                    }
                    gp.ui.addMessage(text);
                    gp.obj[gp.currentMap][i] = null;
                }
            }
        }
    }

    public void selectItem(){
        int itemIndex =gp.ui.getItemIndexOnSlot(gp.ui.playerSlotCol, gp.ui.playerSlotRow);
        if(itemIndex < inventory.size()){
            Entity selectedItem = inventory.get(itemIndex);
            if(selectedItem.type == typeSword || selectedItem.type == typeAxe){
                currentWeapon = selectedItem;
                attack = getAttack();
                getPLayerAttackImage();
            }
            if(selectedItem.type == typeShield){
                currentShield = selectedItem;
                defense = getDefense();
            }
            if(selectedItem.type == typeConsumable){
                selectedItem.use(this);
                inventory.remove(itemIndex);
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
                    tempScreenY = screenY - gp.tile_size;
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
                    tempScreenX = screenX - gp.tile_size;
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
