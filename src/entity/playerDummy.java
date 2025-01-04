package entity;

import main.gamePanel;

public class playerDummy extends Entity {
    public static final String npcName="Dummy";

    public playerDummy(gamePanel gp) {
        super(gp);
        name=npcName;
        getPlayerImage();
    }
    public void getPlayerImage() {
        up1=setup("/player/boy_up_1.png", gp.tileSize, gp.tileSize);
        up2=setup("/player/boy_up_2.png", gp.tileSize, gp.tileSize);
        down1=setup("/player/boy_down_1.png", gp.tileSize, gp.tileSize);
        down2=setup("/player/boy_down_2.png", gp.tileSize, gp.tileSize);
        left1=setup("/player/boy_left_1.png", gp.tileSize, gp.tileSize);
        left2=setup("/player/boy_left_2.png", gp.tileSize, gp.tileSize);
        right1=setup("/player/boy_right_1.png", gp.tileSize, gp.tileSize);
        right2=setup("/player/boy_right_2.png", gp.tileSize, gp.tileSize);
    }
}
