package monster;

import entity.Entity;
import main.gamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;
import object.OBJ_Rock;

import java.util.Random;

public class MON_Bat extends Entity {
    gamePanel gp;
    public MON_Bat(gamePanel gp){
        super(gp);
        this.gp = gp;
        name="Bat";
        defaultSpeed=4;
        speed=defaultSpeed;
        maxLife=7;
        life=maxLife;
        type=typeMonster;
        attack=3;
        defense=0;
        exp=7;

        solidArea.x=3;
        solidArea.y=15;
        solidArea.width=42;
        solidArea.height=21;
        solidAreaDefaultX=solidArea.x;
        solidAreaDefaultY=solidArea.y;
        getImage();
    }
    public void getImage() {
        up1=setup("/monster/bat_down_1.png", gp.tileSize, gp.tileSize);
        up2=setup("/monster/bat_down_2.png", gp.tileSize, gp.tileSize);
        down1=setup("/monster/bat_down_1.png", gp.tileSize, gp.tileSize);
        down2=setup("/monster/bat_down_2.png", gp.tileSize, gp.tileSize);
        left1=setup("/monster/bat_down_1.png", gp.tileSize, gp.tileSize);
        left2=setup("/monster/bat_down_2.png", gp.tileSize, gp.tileSize);
        right1=setup("/monster/bat_down_1.png", gp.tileSize, gp.tileSize);
        right2=setup("/monster/bat_down_2.png", gp.tileSize, gp.tileSize);
    }
    public void setAction()
    {
        getRandomDirection(10);
    }
    public void damageReaction()
    {
        actionLockCounter=0;
    }
    public void checkDrop() {
        int i = new Random().nextInt(100) + 1;
        if (i < 50) {
            dropItem(new OBJ_Coin_Bronze(gp));
        } else if (i < 75) {
            dropItem(new OBJ_Heart(gp));
        } else {
            dropItem(new OBJ_ManaCrystal(gp));
        }
    }
}
