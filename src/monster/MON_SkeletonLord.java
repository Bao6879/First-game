package monster;

import data.Progress;
import entity.Entity;
import main.gamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Door_Iron;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;

import java.util.Random;

public class MON_SkeletonLord extends Entity {
    gamePanel gp;
    public static final String monName="Skeleton Lord";
    public MON_SkeletonLord(gamePanel gp){
        super(gp);
        this.gp = gp;
        name=monName;
        defaultSpeed=1;
        speed=defaultSpeed;
        maxLife=50;
        life=maxLife;
        sleep=true;
        type=typeMonster;
        attack=10;
        defense=5;
        exp=50;

        int size=gp.tileSize*5;
        solidArea.x=48;
        solidArea.y=48;
        solidArea.width=size-48*2;
        solidArea.height=size-48;
        solidAreaDefaultX=solidArea.x;
        solidAreaDefaultY=solidArea.y;
        attackArea.width=170;
        attackArea.height=170;
        motion1Duration=25;
        motion2Duration=50;
        boss=true;
        knockbackPower=5;
        getImage();
        setDialogue();
        getAttackImage();
    }
    public void setDialogue()
    {
        dialogues[0][0]="Leave, mortal, unless you welcome death.";
        dialogues[0][1]="Unfortunate, but you shall perish here.";
    }
    public void getImage() {
        int i=5;
        if (enraged==false) {
            up1 = setup("/monster/skeletonlord_up_1.png", gp.tileSize * i, gp.tileSize * i);
            up2 = setup("/monster/skeletonlord_up_2.png", gp.tileSize * i, gp.tileSize * i);
            down1 = setup("/monster/skeletonlord_down_1.png", gp.tileSize * i, gp.tileSize * i);
            down2 = setup("/monster/skeletonlord_down_2.png", gp.tileSize * i, gp.tileSize * i);
            left1 = setup("/monster/skeletonlord_left_1.png", gp.tileSize * i, gp.tileSize * i);
            left2 = setup("/monster/skeletonlord_left_2.png", gp.tileSize * i, gp.tileSize * i);
            right1 = setup("/monster/skeletonlord_right_1.png", gp.tileSize * i, gp.tileSize * i);
            right2 = setup("/monster/skeletonlord_right_2.png", gp.tileSize * i, gp.tileSize * i);
        }
        else
        {
            up1 = setup("/monster/skeletonlord_phase2_up_1.png", gp.tileSize * i, gp.tileSize * i);
            up2 = setup("/monster/skeletonlord_phase2_up_2.png", gp.tileSize * i, gp.tileSize * i);
            down1 = setup("/monster/skeletonlord_phase2_down_1.png", gp.tileSize * i, gp.tileSize * i);
            down2 = setup("/monster/skeletonlord_phase2_down_2.png", gp.tileSize * i, gp.tileSize * i);
            left1 = setup("/monster/skeletonlord_phase2_left_1.png", gp.tileSize * i, gp.tileSize * i);
            left2 = setup("/monster/skeletonlord_phase2_left_2.png", gp.tileSize * i, gp.tileSize * i);
            right1 = setup("/monster/skeletonlord_phase2_right_1.png", gp.tileSize * i, gp.tileSize * i);
            right2 = setup("/monster/skeletonlord_phase2_right_2.png", gp.tileSize * i, gp.tileSize * i);
        }
    }
    public void getAttackImage()
    {
        int i=5;
        if (enraged==false) {
            attackUp1 = setup("/monster/skeletonlord_attack_up_1.png", gp.tileSize * i, gp.tileSize * i * 2);
            attackUp2 = setup("/monster/skeletonlord_attack_up_2.png", gp.tileSize * i, gp.tileSize * i * 2);
            attackDown1 = setup("/monster/skeletonlord_attack_down_1.png", gp.tileSize * i, gp.tileSize * i * 2);
            attackDown2 = setup("/monster/skeletonlord_attack_down_2.png", gp.tileSize * i, gp.tileSize * i * 2);
            attackLeft1 = setup("/monster/skeletonlord_attack_left_1.png", gp.tileSize * i * 2, gp.tileSize * i);
            attackLeft2 = setup("/monster/skeletonlord_attack_left_2.png", gp.tileSize * i * 2, gp.tileSize * i);
            attackRight1 = setup("/monster/skeletonlord_attack_right_1.png", gp.tileSize * i * 2, gp.tileSize * i);
            attackRight2 = setup("/monster/skeletonlord_attack_right_2.png", gp.tileSize * i * 2, gp.tileSize * i);
        }
        else
        {
            attackUp1 = setup("/monster/skeletonlord_phase2_attack_up_1.png", gp.tileSize * i, gp.tileSize * i * 2);
            attackUp2 = setup("/monster/skeletonlord_phase2_attack_up_2.png", gp.tileSize * i, gp.tileSize * i * 2);
            attackDown1 = setup("/monster/skeletonlord_phase2_attack_down_1.png", gp.tileSize * i, gp.tileSize * i * 2);
            attackDown2 = setup("/monster/skeletonlord_phase2_attack_down_2.png", gp.tileSize * i, gp.tileSize * i * 2);
            attackLeft1 = setup("/monster/skeletonlord_phase2_attack_left_1.png", gp.tileSize * i * 2, gp.tileSize * i);
            attackLeft2 = setup("/monster/skeletonlord_phase2_attack_left_2.png", gp.tileSize * i * 2, gp.tileSize * i);
            attackRight1 = setup("/monster/skeletonlord_phase2_attack_right_1.png", gp.tileSize * i * 2, gp.tileSize * i);
            attackRight2 = setup("/monster/skeletonlord_phase2_attack_right_2.png", gp.tileSize * i * 2, gp.tileSize * i);
        }
    }
    public void setAction()
    {
        if (enraged==false && life<maxLife/2)
        {
            enraged=true;
            getImage();
            getAttackImage();
            defaultSpeed++;
            speed=defaultSpeed;
            attack*=2;
        }
        if (getTileDistance(gp.player)<10)
        {
            moveTowardPlayer(60);
        }
        else {
            getRandomDirection(120);
        }
        if (attacking==false)
            checkAttack(60, gp.tileSize*7, gp.tileSize*5);
    }
    public void damageReaction()
    {
        actionLockCounter=0;
        onPath=true;
    }
    public void checkDrop() {
        gp.bossBattleOn=false;
        Progress.skeletonLordDefeated=true;
        gp.stopMusic();
        gp.playMusic(17);
        for (int i=0; i<gp.obj[1].length; i++)
        {
            if (gp.obj[gp.currentMap][i]!=null && gp.obj[gp.currentMap][i].name.equals(OBJ_Door_Iron.objName))
            {
                gp.playSE(18);
                gp.obj[gp.currentMap][i]=null;

            }
        }
    }
}
