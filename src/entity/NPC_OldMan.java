package entity;

import main.gamePanel;
import main.utilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class NPC_OldMan extends Entity {
    public NPC_OldMan(gamePanel gp) {
        super(gp);
        direction="down";
        speed=2;
        solidArea=new Rectangle(8, 16, 32, 32);
        solidAreaDefaultX=solidArea.x;
        solidAreaDefaultY=solidArea.y;
        dialogueSet=-1;

        getImage();
        setDialogue();
    }
    public void getImage() {
        up1=setup("/NPC/oldman_up_1.png", gp.tileSize, gp.tileSize);
        up2=setup("/NPC/oldman_up_2.png", gp.tileSize, gp.tileSize);
        down1=setup("/NPC/oldman_down_1.png", gp.tileSize, gp.tileSize);
        down2=setup("/NPC/oldman_down_2.png", gp.tileSize, gp.tileSize);
        left1=setup("/NPC/oldman_left_1.png", gp.tileSize, gp.tileSize);
        left2=setup("/NPC/oldman_left_2.png", gp.tileSize, gp.tileSize);
        right1=setup("/NPC/oldman_right_1.png", gp.tileSize, gp.tileSize);
        right2=setup("/NPC/oldman_right_2.png", gp.tileSize, gp.tileSize);
    }
    public void setDialogue()
    {
        dialogues[0][0]="Hello, lad.";
        dialogues[0][1]="So you've come.";
        dialogues[0][2]="Lmao cool.";
        dialogues[0][3]="Cya bro.";

        dialogues[1][0]="If you get tired, rest at water";
        dialogues[1][1]="It heals, save game and respawn monsters";

        dialogues[2][0]="How do you open that door...";
    }
    public void setAction() {
        if (onPath==true)
        {
            //int goalCol=12, goalRow=9;
            int goalCol=(gp.player.worldX+gp.player.solidArea.x)/gp.tileSize, goalRow=(gp.player.worldY+gp.player.solidArea.y)/gp.tileSize;
            searchPath(goalCol, goalRow);
        }
        else {
            Random rand = new Random();
            actionLockCounter++;
            if (actionLockCounter == 120) {
                int i = rand.nextInt(100) + 1; //number from 1 - 100
                if (i <= 25)
                    direction = "up";
                else if (i <= 50)
                    direction = "down";
                else if (i <= 75)
                    direction = "left";
                else if (i <= 100)
                    direction = "right";
                actionLockCounter = 0;
            }
        }
    }
    public void speak()
    {
        facePlayer();
        startDialogue(this, dialogueSet);
        dialogueSet++;
        if (dialogues[dialogueSet][0]==null)
            dialogueSet=0;
    }
}
