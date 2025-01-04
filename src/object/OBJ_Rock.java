package object;

import entity.Projectile;
import main.gamePanel;

public class OBJ_Rock extends Projectile {
    gamePanel gp;
    public static final String objName="Rock";
    public OBJ_Rock(gamePanel gp) {
        super(gp);
        this.gp = gp;
        name=objName;
        speed=8;
        maxLife=80;
        life=maxLife;
        attack=2;
        useCost=1;
        alive=false;
        getImage();
    }
    public void getImage()
    {
        up1=setup("/projectile/rock_down_1.png", gp.tileSize, gp.tileSize);
        up2=setup("/projectile/rock_down_1.png", gp.tileSize, gp.tileSize);
        down1=setup("/projectile/rock_down_1.png", gp.tileSize, gp.tileSize);
        down2=setup("/projectile/rock_down_1.png", gp.tileSize, gp.tileSize);
        left1=setup("/projectile/rock_down_1.png", gp.tileSize, gp.tileSize);
        left2=setup("/projectile/rock_down_1.png", gp.tileSize, gp.tileSize);
        right1=setup("/projectile/rock_down_1.png", gp.tileSize, gp.tileSize);
        right2=setup("/projectile/rock_down_1.png", gp.tileSize, gp.tileSize);
    }
}
