package main;

import entity.NPC_oldMan;
import monster.green_slime;
import monster.red_slime;
import object.*;

// thiết lập tài sản (object)
public class AssetSetter {
    GamePanel gp;
    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }
    public void setObject(){
//        gp.obj[0] = new obj_key(gp);
//        gp.obj[0].worldX = 23 * gp.title_size;
//        gp.obj[0].worldY = 7 * gp.title_size;

        gp.obj[0] = new obj_statue(gp);
        gp.obj[0].worldX = gp.title_size * 27;
        gp.obj[0].worldY = gp.title_size * 16;

        gp.obj[1] = new obj_door(gp);
        gp.obj[1].worldX = gp.title_size * 18;
        gp.obj[1].worldY = gp.title_size * 16;

        gp.obj[2] = new obj_door(gp);
        gp.obj[2].worldX = gp.title_size * 37;
        gp.obj[2].worldY = gp.title_size * 10;

        gp.obj[3] = new obj_door(gp);
        gp.obj[2].worldX = gp.title_size * 21;
        gp.obj[2].worldY = gp.title_size * 22;
    }

    public void setNPC(){
        gp.npc[0] = new NPC_oldMan(gp);
        gp.npc[0].worldX = gp.title_size * 21;
        gp.npc[0].worldY = gp.title_size * 21;

        gp.npc[1] = new NPC_oldMan(gp);
        gp.npc[1].worldX = gp.title_size * 25;
        gp.npc[1].worldY = gp.title_size * 21;
    }

    public void setMonster(){
        gp.monster[0] = new green_slime(gp);
        gp.monster[0].worldX = gp.title_size * 23;
        gp.monster[0].worldY = gp.title_size * 36;

        gp.monster[1] = new green_slime(gp);
        gp.monster[1].worldX = gp.title_size * 26;
        gp.monster[1].worldY = gp.title_size * 37;

        gp.monster[2] = new red_slime(gp);
        gp.monster[2].worldX = gp.title_size * 36;
        gp.monster[2].worldY = gp.title_size * 11;

        gp.monster[3] = new red_slime(gp);
        gp.monster[3].worldX = gp.title_size * 35;
        gp.monster[3].worldY = gp.title_size * 10;
    }
}
