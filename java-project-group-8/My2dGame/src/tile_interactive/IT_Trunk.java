package tile_interactive;

import main.GamePanel;

public class IT_Trunk extends InteractiveTile{
	GamePanel gp;
	
	public IT_Trunk(GamePanel gp,int col,int row) {
		super(gp,col,row);
		this.gp = gp;
		this.WorldX = gp.tileSize*col;
		this.WorldY = gp.tileSize*row;
		down1 = setup("tile_interative\\trunk.png", gp.tileSize, gp.tileSize);
		solidArea.x = 0;
		solidArea.y = 0;
		solidArea.width = 0;
		solidArea.height = 0;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
	}
}
