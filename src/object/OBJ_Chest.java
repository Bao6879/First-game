package object;

import entity.Entity;
import main.gamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Chest extends Entity {
    gamePanel gp;
    public static final String objName="Chest";
    public OBJ_Chest(gamePanel gp) {
        super(gp);
        this.gp = gp;
        type=typeObstacle;
        name = objName;
        image=setup("/object/chest.png", gp.tileSize, gp.tileSize);
        image2=setup("/object/chest_opened.png", gp.tileSize, gp.tileSize);
        down1=image;
        collision=true;
        solidArea.x=4;
        solidArea.y=16;
        solidArea.width=40;
        solidArea.height=32;
        solidAreaDefaultX=solidArea.x;
        solidAreaDefaultY=solidArea.y;
    }
    public void setLoot(Entity loot) {
        this.loot=loot;
    }
    public void interact()
    {
        gp.gameState=gp.dialogueState;
        if (opened==false)
        {
            gp.playSE(3);
            StringBuilder sb=new StringBuilder();
            sb.append("You opened the chest to get "+loot.name+" !");
            if (gp.player.canObtainItem(loot)==false)
            {
                sb.append("..\nBut yo inventory so full you can't carry shit :(!");
            }
            else
            {
                sb.append("\nYou obtained the "+loot.name+" !");
                down1=image2;
                opened=true;
            }
            dialogues[0][0]=sb.toString();
            startDialogue(this, 0);
        }
        else
        {
            dialogues[1][0]="It's empty...";
            startDialogue(this, 1);
        }
    }
}
