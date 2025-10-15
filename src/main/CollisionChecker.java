package main;

import entity.Entity;
/* top
       ______________
    * |             | right
* left|  _______    |  col is x
    * |  |      |   |  row is y
    * |  |------|   |
    * ______________-
         bottom*/
public class CollisionChecker {
    GamePanel gp;
    public CollisionChecker(GamePanel gp){
        this.gp =  gp;
    }
    public void checkTile(Entity entity){
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gp.title_size;
        int entityRightCol = entityRightWorldX / gp.title_size;

        int entityTopRow = entityTopWorldY / gp.title_size;
        int entityBottomRow = entityBottomWorldY / gp.title_size;

        int tileNum1, tileNum2;
        switch(entity.direction){
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed) / gp.title_size;
                tileNum1 = gp.tileM.mapIileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapIileNum[entityRightCol][entityTopRow];
                if(gp.tileM.tile[tileNum1].collision ||
                gp.tileM.tile[tileNum2].collision){
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.title_size;
                tileNum1 = gp.tileM.mapIileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapIileNum[entityRightCol][entityBottomRow];
                if(gp.tileM.tile[tileNum1].collision ||
                        gp.tileM.tile[tileNum2].collision){
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.title_size;
                tileNum1 = gp.tileM.mapIileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapIileNum[entityLeftCol][entityBottomRow];
                if(gp.tileM.tile[tileNum1].collision ||
                        gp.tileM.tile[tileNum2].collision){
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / gp.title_size;
                tileNum1 = gp.tileM.mapIileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapIileNum[entityRightCol][entityBottomRow];
                if(gp.tileM.tile[tileNum1].collision ||
                        gp.tileM.tile[tileNum2].collision){
                    entity.collisionOn = true;
                }
                break;
        }
    }
    public int checkObject(Entity entity, boolean player){ // check entity is player or not
        int index = 999;
        for(int i=0; i < gp.obj.length; i++){
            if (gp.obj[i] != null) {
                //Get entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                //Get the object's solid area position
                gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
                gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;

                // Simulating entity's movement and check where it after it move
                switch (entity.direction){
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        break;
                }
                //intersects: giao nhau 2 hcn
                if(entity.solidArea.intersects(gp.obj[i].solidArea)){
//                    System.out.println("up collision");
                    if(gp.obj[i].collision){
                        entity.collisionOn = true;
                    }
                    if(player){
                        index = i;
                    }
                }
                // reset solid area
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
                gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
            }
        }
        return index; //if entity is object, return index of object
    }
    // check npc or monster
    public int checkEntity(Entity entity, Entity[] target){
        int index = 999;
        for(int i=0; i < target.length; i++){
            if (target[i] != null) {
                //Get entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                //Get the object's solid area position
                target[i].solidArea.x = target[i].worldX + target[i].solidArea.x;
                target[i].solidArea.y = target[i].worldY + target[i].solidArea.y;

                // Simulating entity's movement and check where it after it move
                switch (entity.direction){
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        break;
                }
                //intersects: giao nhau 2 hcn
                if(entity.solidArea.intersects(target[i].solidArea)){
                    if(target[i] != entity) {
                        entity.collisionOn = true;
                        index = i;
                    }
                }
                // reset solid area
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                target[i].solidArea.x = target[i].solidAreaDefaultX;
                target[i].solidArea.y = target[i].solidAreaDefaultY;
            }
        }
        return index; //if entity is object, return index of object
    }
    public boolean checkPlayer(Entity entity){
        boolean contactPlayer = false;
        //Get entity's solid area position
        entity.solidArea.x = entity.worldX + entity.solidArea.x;
        entity.solidArea.y = entity.worldY + entity.solidArea.y;
        //Get the object's solid area position
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

        // Simulating entity's movement and check where it after it move
        switch (entity.direction){
            case "up":
                entity.solidArea.y -= entity.speed;
                break;
            case "down":
                entity.solidArea.y += entity.speed;
                break;
            case "left":
                entity.solidArea.x -= entity.speed;
                break;
            case "right":
                entity.solidArea.x += entity.speed;
                break;
        }
        //intersects: giao nhau 2 hcn
        if(entity.solidArea.intersects(gp.player.solidArea)){
            entity.collisionOn = true;
            contactPlayer = true;
        }
        // reset solid area
        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        return contactPlayer;
    }
}
