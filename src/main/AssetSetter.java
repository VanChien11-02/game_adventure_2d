package main;

import entity.NPC_merchant;
import entity.NPC_oldMan;
import monster.green_slime;
import monster.red_slime;
import object.*;
import tile_interactive.it_dryTree;

// thiết lập tài sản (object)
public class AssetSetter {
    GamePanel gp;
    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }
    public void setObject(){
        int mapNum = 0;
        gp.obj[mapNum][0] = new obj_statue(gp);
        gp.obj[mapNum][0].worldX = gp.tile_size * 27;
        gp.obj[mapNum][0].worldY = gp.tile_size * 16;

        gp.obj[mapNum][1] = new obj_door(gp);
        gp.obj[mapNum][1].worldX = gp.tile_size * 18;
        gp.obj[mapNum][1].worldY = gp.tile_size * 16;

        gp.obj[mapNum][2] = new obj_door(gp);
        gp.obj[mapNum][2].worldX = gp.tile_size * 37;
        gp.obj[mapNum][2].worldY = gp.tile_size * 10;

        gp.obj[mapNum][3] = new obj_coin(gp);
        gp.obj[mapNum][3].worldX = gp.tile_size * 21;
        gp.obj[mapNum][3].worldY = gp.tile_size * 22;

        gp.obj[mapNum][4] = new obj_coin(gp);
        gp.obj[mapNum][4].worldX = gp.tile_size * 25;
        gp.obj[mapNum][4].worldY = gp.tile_size * 19;

        gp.obj[mapNum][5] = new obj_key(gp);
        gp.obj[mapNum][5].worldX = gp.tile_size * 26;
        gp.obj[mapNum][5].worldY = gp.tile_size * 21;

        gp.obj[mapNum][6] = new obj_shield_blue(gp);
        gp.obj[mapNum][6].worldX = gp.tile_size * 28;
        gp.obj[mapNum][6].worldY = gp.tile_size * 21;

        gp.obj[mapNum][7] = new obj_axe(gp);
        gp.obj[mapNum][7].worldX = gp.tile_size * 22;
        gp.obj[mapNum][7].worldY = gp.tile_size * 21;

        gp.obj[mapNum][8] = new obj_potion_red(gp);
        gp.obj[mapNum][8].worldX = gp.tile_size * 35;
        gp.obj[mapNum][8].worldY = gp.tile_size * 21;

        gp.obj[mapNum][9] = new obj_heart(gp);
        gp.obj[mapNum][9].worldX = gp.tile_size * 22;
        gp.obj[mapNum][9].worldY = gp.tile_size * 27;

        gp.obj[mapNum][10] = new obj_mana(gp);
        gp.obj[mapNum][10].worldX = gp.tile_size * 22;
        gp.obj[mapNum][10].worldY = gp.tile_size * 30;
    }

    public void setNPC(){
        int mapNum = 0;
        gp.npc[mapNum][0] = new NPC_oldMan(gp);
        gp.npc[mapNum][0].worldX = gp.tile_size * 21;
        gp.npc[mapNum][0].worldY = gp.tile_size * 21;

        gp.npc[mapNum][1] = new NPC_oldMan(gp);
        gp.npc[mapNum][1].worldX = gp.tile_size * 25;
        gp.npc[mapNum][1].worldY = gp.tile_size * 21;

        mapNum = 1;
        gp.npc[mapNum][0] = new NPC_merchant(gp);
        gp.npc[mapNum][0].worldX = gp.tile_size * 12;
        gp.npc[mapNum][0].worldY = gp.tile_size * 7;
    }

    public void setMonster(){
        int mapNum = 0;
        gp.monster[mapNum][0] = new green_slime(gp);
        gp.monster[mapNum][0].worldX = gp.tile_size * 23;
        gp.monster[mapNum][0].worldY = gp.tile_size * 36;

        gp.monster[mapNum][1] = new green_slime(gp);
        gp.monster[mapNum][1].worldX = gp.tile_size * 26;
        gp.monster[mapNum][1].worldY = gp.tile_size * 37;

        gp.monster[mapNum][2] = new red_slime(gp);
        gp.monster[mapNum][2].worldX = gp.tile_size * 36;
        gp.monster[mapNum][2].worldY = gp.tile_size * 11;

        gp.monster[mapNum][3] = new red_slime(gp);
        gp.monster[mapNum][3].worldX = gp.tile_size * 35;
        gp.monster[mapNum][3].worldY = gp.tile_size * 10;

//        mapNum = 1;
//        gp.monster[mapNum][4] = new green_slime(gp);
//        gp.monster[mapNum][4].worldX = gp.tile_size * 23;
//        gp.monster[mapNum][4].worldY = gp.tile_size * 36;
    }

    public void setInteractiveTile(){
        int mapNum = 0;
        int i = 0;
        gp.iTile[mapNum][i] = new it_dryTree(gp, 27, 12);

        i++;
        gp.iTile[mapNum][i] = new it_dryTree(gp, 28, 12);

        i++;
        gp.iTile[mapNum][i] = new it_dryTree(gp, 29, 12);

        i++;
        gp.iTile[mapNum][i] = new it_dryTree(gp, 30, 12);

        i++;
        gp.iTile[mapNum][i] = new it_dryTree(gp, 31, 12);

        i++;
        gp.iTile[mapNum][i] = new it_dryTree(gp, 32, 12);

        i++;
        gp.iTile[mapNum][i] = new it_dryTree(gp, 33, 12);

        i++;
        gp.iTile[mapNum][i] = new it_dryTree(gp, 12, 41);

        i++;
        gp.iTile[mapNum][i] = new it_dryTree(gp, 13, 41);

        i++;
        gp.iTile[mapNum][i] = new it_dryTree(gp, 13, 40);

        i++;
        gp.iTile[mapNum][i] = new it_dryTree(gp, 14, 40);

        i++;
        gp.iTile[mapNum][i] = new it_dryTree(gp, 15, 40);

        i++;
        gp.iTile[mapNum][i] = new it_dryTree(gp, 16, 40);

        i++;
        gp.iTile[mapNum][i] = new it_dryTree(gp, 17, 40);

        i++;
        gp.iTile[mapNum][i] = new it_dryTree(gp, 18, 40);
    }
}
