package main;

public class EventHandler {
    GamePanel gp;
    // when player touch, event just happen 1 time, but if player go outside the rect, this can happen again
    int previousEventX, previousEventY;
    boolean canTouchEvent = true;

    EventRect[][][] eventRect;
    public EventHandler(GamePanel gp){
        this.gp = gp;
        eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow]; // every block in map

        int map = 0;
        int col = 0;
        int row = 0;

        while(map < gp.maxMap && col < gp.maxWorldCol && row < gp.maxWorldRow) {
            eventRect[map][col][row] = new EventRect(); // small 2x2 pixel because player can go in size block
            eventRect[map][col][row].x = 20;
            eventRect[map][col][row].y = 20;
            eventRect[map][col][row].width = 5;
            eventRect[map][col][row].height = 5;
            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;
            col++;
            if(col == gp.maxWorldCol){
                col = 0;
                row++;
                if(row == gp.maxWorldRow){
                    row = 0;
                    map++;
                }
            }
        }
    }
    public void checkEvent(){
        //check if the player character is more than 1 tile away form the last event
        int DistanceX = Math.abs(gp.player.worldX - previousEventX);
        int DistanceY = Math.abs(gp.player.worldY - previousEventY);
        int Distance = Math.max(DistanceX, DistanceY);
        if(Distance > gp.tile_size){ // can change tile_size * 2
            canTouchEvent = true;
        }
        if(canTouchEvent) {
            if (hit(0, 27, 16, "right")) {
                //event happen
                damagePit(gp.dialogueState);
            }
            else if (hit(0, 23, 12, "up")) {
                healingPool( gp.dialogueState);
            }
            else if (hit(0, 18, 16, "left")) {
                teleport(gp.dialogueState);
            }
            else if (hit(0, 10, 39, "any")) {
                newWorld(1, 12, 13);
            }
            else if (hit(1, 12, 13, "any")) {
                newWorld(0, 10, 39);
            }
        }
    }
    public boolean hit(int map, int col, int row, String reqDirection){
        boolean hit = false;
        if(map == gp.currentMap) {
            gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
            gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
            eventRect[map][col][row].x = col * gp.tile_size + eventRect[map][col][row].x;
            eventRect[map][col][row].y = row * gp.tile_size + eventRect[map][col][row].y;

            //check player collision event
            if (gp.player.solidArea.intersects(eventRect[map][col][row]) && !eventRect[map][col][row].eventDone) {
                if (gp.player.direction.contentEquals(reqDirection) ||
                        reqDirection.contentEquals("any")) {
                    hit = true;
                    previousEventX = gp.player.worldX;
                    previousEventY = gp.player.worldY;
                }
            }
            //reset soild x and y
            gp.player.solidArea.x = gp.player.solidAreaDefaultX;
            gp.player.solidArea.y = gp.player.solidAreaDefaultY;
            eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
            eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
        }
        return hit;
    }
    public void damagePit(int gameState){
        gp.gameState = gameState;
//        gp.ui.currentDialogue = "You fall into the pit!";
        gp.ui.currentDialogue = "The warrior hit you";
        gp.player.life -= 1;
        if(gp.player.life < 0){
            gp.player.life = 0;
        }
//        eventRect[col][row].eventDone = true;
        canTouchEvent = false;
    }
    public void healingPool(int gameState){
        if(gp.KeyH.enterPressed){
            gp.gameState = gameState;
            gp.player.attackCanceled = true;
            gp.ui.currentDialogue = "You drink water. \n Your life is recovered.";
            if(gp.player.life < gp.player.maxLife) {
                gp.player.life += 1;
            }
            if(gp.player.mana < gp.player.maxMana) {
                gp.player.mana += 1;
            }
//            gp.aSetter.setMonster(); respawn monster
//            eventRect[col][row].eventDone = true;
        }
    }
    public void teleport(int gameState){
        gp.gameState = gameState;
        gp.ui.currentDialogue = "Teleport!";
        gp.player.worldX = gp.tile_size * 37;
        gp.player.worldY = gp.tile_size * 10;
    }
    public void newWorld(int map, int col, int row){
        gp.currentMap = map;
        gp.player.worldX = gp.tile_size * col;
        gp.player.worldY = gp.tile_size * row;
        previousEventX = gp.player.worldX;
        previousEventY = gp.player.worldY;
        canTouchEvent = false;
        //gp.playSE()
    }
}
