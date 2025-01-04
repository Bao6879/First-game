package monster;

import entity.Entity;
import main.gamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;
import object.OBJ_Rock;

import java.util.Random;

public class MON_RedSlime extends Entity {
    gamePanel gp;
    public MON_RedSlime(gamePanel gp){
        super(gp);
        this.gp = gp;
        name="Red Slime";
        defaultSpeed=2;
        speed=defaultSpeed;
        maxLife=8;
        life=maxLife;
        type=typeMonster;
        attack=7;
        defense=0;
        projectile=new OBJ_Rock(gp);
        exp=5;

        solidArea.x=3;
        solidArea.y=18;
        solidArea.width=42;
        solidArea.height=30;
        solidAreaDefaultX=solidArea.x;
        solidAreaDefaultY=solidArea.y;
        getImage();
    }
    public void getImage() {
        up1=setup("/monster/redslime_down_1.png", gp.tileSize, gp.tileSize);
        up2=setup("/monster/redslime_down_2.png", gp.tileSize, gp.tileSize);
        down1=setup("/monster/redslime_down_1.png", gp.tileSize, gp.tileSize);
        down2=setup("/monster/redslime_down_2.png", gp.tileSize, gp.tileSize);
        left1=setup("/monster/redslime_down_1.png", gp.tileSize, gp.tileSize);
        left2=setup("/monster/redslime_down_2.png", gp.tileSize, gp.tileSize);
        right1=setup("/monster/redslime_down_1.png", gp.tileSize, gp.tileSize);
        right2=setup("/monster/redslime_down_2.png", gp.tileSize, gp.tileSize);
    }
    public void setAction()
    {
        if (onPath==true)
        {
            checkStopChasing(gp.player, 15, 100);
            checkShoot(200, 30);
            searchPath(getGoalCol(gp.player), getGoalRow(gp.player));
        }
        else {
            checkStartChasing(gp.player, 5, 30);
            getRandomDirection(120);
        }
    }
    public void damageReaction()
    {
        actionLockCounter=0;
//        direction=gp.player.direction;
        onPath=true;
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
