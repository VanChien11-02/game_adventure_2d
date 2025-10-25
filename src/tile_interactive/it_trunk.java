package tile_interactive;

import main.GamePanel;
// can use this as tile on map but that not good idea so use Interactive tile to replace dry tree
public class it_trunk extends InteractiveTIle{
    GamePanel gp;
    public it_trunk(GamePanel gp, int col, int row) {
        super(gp);
        this.gp = gp;

        this.worldX = gp.tile_size * col;
        this.worldY = gp.tile_size * row;
        down1 = setup("/tile_interactive/trunk", gp.tile_size, gp.tile_size);

        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 0;
        solidArea.height = 0;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}
