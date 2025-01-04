package object;

import entity.Entity;
import main.gamePanel;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class OBJ_Key extends Entity {
    gamePanel gp;
    public static final String objName="Key";
    public OBJ_Key(gamePanel gp) {
        super(gp);
        this.gp = gp;
        down1=setup("/object/key.png", gp.tileSize, gp.tileSize);
        name = objName;
        description="["+name+"]\n Opens a door";
        price=100;
        stackable=true;
        type=typeConsumable;
        setDialogue();
    }
    public void setDialogue()
    {
        dialogues[0][0]="You used the key to open the door!";

        dialogues[1][0]="What the sig you be doin?";
    }
    public boolean use(Entity entity) {
        int objIndex=getDetected(entity, gp.obj, "Door");
        if (objIndex!=999) {
            startDialogue(this, 0);
            gp.playSE(3);
            gp.obj[gp.currentMap][objIndex]=null;
            return true;
        }
        else
        {
            startDialogue(this, 1);
            return false;
        }
    }
}
