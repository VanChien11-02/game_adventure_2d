package tile;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int[][] mapIileNum;
    public TileManager(GamePanel gp){
        this.gp = gp;
        tile = new Tile[50]; // create 10 tile, vd: wall, tree, water
        mapIileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadMap("/maps/worldV2.txt"); //if we use another map
    }
    public void getTileImage(){
        //0. grass
        //tile[0] = new Tile();
        //tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));
        // PLACEHOLDER
        int indexes[] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        for(int i = 0; i < indexes.length; i++){
            setup(indexes[i], "grass00", false);
        }

        // TILES
        setup(11, "grass01", false);

        // WATER loop
        indexes = new int[]{12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25};
        for(int i = 0; i < indexes.length; i++){
            String waterIndex = "water" + String.format("%02d", i);
            setup(indexes[i], waterIndex, true);
        }

        // ROAD loop
        indexes = new int[]{26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38};
        for(int i = 0; i < indexes.length; i++){
            String roadIndex = "road" + String.format("%02d", i);
            setup(indexes[i], roadIndex, false);
        }

        setup(39, "earth", false);
        setup(40, "wall", true);
        setup(41,"tree", true);
    }
    public void setup(int index, String imageName, boolean collision){
        UtilityTool uTool = new UtilityTool();
        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName + ".png"));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.title_size, gp.title_size);
            tile[index].collision = collision;
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public void loadMap(String filepath){
        try{
            InputStream ins = getClass().getResourceAsStream(filepath);
            BufferedReader br = new BufferedReader(new InputStreamReader(ins));
            // read the content in file txt

            for(int row = 0; row < gp.maxWorldRow; row++){
                String line = br.readLine();
                for (int col = 0; col < gp.maxWorldCol; col++){
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapIileNum[col][row] = num;
                }
            }
            br.close();
        } catch (Exception _){

        }
    }
    public void draw(Graphics2D g2){
//        g2.drawImage(tile[0].image, 0, 0, gp.title_size, gp.title_size, null);
//        g2.drawImage(tile[1].image, gp.title_size, 0, gp.title_size, gp.title_size, null);
//        g2.drawImage(tile[2].image, gp.title_size * 2, 0, gp.title_size, gp.title_size, null);

        int col = 0;
        int row = 0; //world col and row

        while(col < gp.maxWorldCol && row < gp.maxWorldRow){
            int nums = mapIileNum[col][row];
            // worldX, world is distance to mapTileNum[0][0]
            int worldX = col * gp.title_size;
            int worldY = row * gp.title_size;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;//to put player at the center
            int screenY = worldY - gp.player.worldY + gp.player.screenY;
            //just draw in the screen( improve efficiency( hiệu suất))
            if(worldX + gp.title_size > gp.player.worldX - gp.player.screenX &&
               worldX - gp.title_size < gp.player.worldX + gp.player.screenX &&
               worldY + gp.title_size > gp.player.worldY - gp.player.screenY &&
               worldY - gp.title_size < gp.player.worldY + gp.player.screenY) {
                g2.drawImage(tile[nums].image, screenX, screenY, null);
            }
            col++;
            if(col == gp.maxWorldCol){
                col = 0;
                row++;
            }
        }
    }
}
