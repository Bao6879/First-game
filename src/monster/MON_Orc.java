package monster;

import entity.Entity;
import main.gamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;
import object.OBJ_Rock;

import java.util.Random;

public class MON_Orc extends Entity {
    gamePanel gp;
    public MON_Orc(gamePanel gp){
        super(gp);
        this.gp = gp;
        name="Orc";
        defaultSpeed=1;
        speed=defaultSpeed;
        maxLife=10;
        life=maxLife;
        type=typeMonster;
        attack=3;
        defense=1;
        exp=10;

        solidArea.x=4;
        solidArea.y=4;
        solidArea.width=40;
        solidArea.height=44;
        solidAreaDefaultX=solidArea.x;
        solidAreaDefaultY=solidArea.y;
        attackArea.width=48;
        attackArea.height=48;
        motion1Duration=40;
        motion2Duration=85;
        knockbackPower=5;
        getImage();
        getAttackImage();
    }
    public void getImage() {
        up1=setup("/monster/orc_up_1.png", gp.tileSize, gp.tileSize);
        up2=setup("/monster/orc_up_2.png", gp.tileSize, gp.tileSize);
        down1=setup("/monster/orc_down_1.png", gp.tileSize, gp.tileSize);
        down2=setup("/monster/orc_down_2.png", gp.tileSize, gp.tileSize);
        left1=setup("/monster/orc_left_1.png", gp.tileSize, gp.tileSize);
        left2=setup("/monster/orc_left_2.png", gp.tileSize, gp.tileSize);
        right1=setup("/monster/orc_right_1.png", gp.tileSize, gp.tileSize);
        right2=setup("/monster/orc_right_2.png", gp.tileSize, gp.tileSize);
    }
    public void getAttackImage()
    {
        attackUp1=setup("/monster/orc_attack_up_1.png", gp.tileSize, gp.tileSize*2);
        attackUp2=setup("/monster/orc_attack_up_2.png", gp.tileSize, gp.tileSize*2);
        attackDown1=setup("/monster/orc_attack_down_1.png", gp.tileSize, gp.tileSize*2);
        attackDown2=setup("/monster/orc_attack_down_2.png", gp.tileSize, gp.tileSize*2);
        attackLeft1=setup("/monster/orc_attack_left_1.png", gp.tileSize*2, gp.tileSize);
        attackLeft2=setup("/monster/orc_attack_left_2.png", gp.tileSize*2, gp.tileSize);
        attackRight1=setup("/monster/orc_attack_right_1.png", gp.tileSize*2, gp.tileSize);
        attackRight2=setup("/monster/orc_attack_right_2.png", gp.tileSize*2, gp.tileSize);
    }
    public void setAction()
    {
        if (onPath==true)
        {
            checkStopChasing(gp.player, 15, 100);
            searchPath(getGoalCol(gp.player), getGoalRow(gp.player));
        }
        else {
            checkStartChasing(gp.player, 5, 100);
            getRandomDirection(120);
        }
        if (attacking==false)
            checkAttack(30, gp.tileSize*4, gp.tileSize);
    }
    public void damageReaction()
    {
        actionLockCounter=0;
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
