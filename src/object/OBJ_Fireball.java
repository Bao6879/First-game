package object;

import entity.Entity;
import entity.Projectile;
import main.gamePanel;

public class OBJ_Fireball extends Projectile {

    gamePanel gp;
    public OBJ_Fireball(gamePanel gp) {
        super(gp);
        this.gp = gp;
        name="Fireball";
        speed=5;
        maxLife=80;
        life=maxLife;
        attack=1;
        useCost=1;
        alive=false;
        getImage();
    }
    public void getImage()
    {
        up1=setup("/projectile/fireball_up_1.png", gp.tileSize, gp.tileSize);
        up2=setup("/projectile/fireball_up_2.png", gp.tileSize, gp.tileSize);
        down1=setup("/projectile/fireball_down_1.png", gp.tileSize, gp.tileSize);
        down2=setup("/projectile/fireball_down_2.png", gp.tileSize, gp.tileSize);
        left1=setup("/projectile/fireball_left_1.png", gp.tileSize, gp.tileSize);
        left2=setup("/projectile/fireball_left_2.png", gp.tileSize, gp.tileSize);
        right1=setup("/projectile/fireball_right_1.png", gp.tileSize, gp.tileSize);
        right2=setup("/projectile/fireball_right_2.png", gp.tileSize, gp.tileSize);
    }
    public boolean haveResource(Entity user) {
        boolean have=false;
        if (user.mana>=useCost)
            have=true;
        return have;
    }
    public void subtractResource(Entity user) {
        user.mana-=useCost;
    }
}
