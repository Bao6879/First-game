package object;

import entity.Entity;
import main.gamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Door extends Entity {
    gamePanel gp;
    public static final String objName="Door";
    public OBJ_Door(gamePanel gp) {
        super(gp);
        this.gp = gp;
        down1=setup("/object/door.png", gp.tileSize, gp.tileSize);
        name = objName;
        collision=true;
        solidArea.x=0;
        solidArea.y=16;
        solidArea.width=48;
        solidArea.height=32;
        solidAreaDefaultX=solidArea.x;
        solidAreaDefaultY=solidArea.y;
        type=typeObstacle;
        setDialogue();
    }
    public void setDialogue()
    {
        dialogues[0][0]="Where key?";
    }
    public void interact()
    {
        startDialogue(this, 0);
    }
}
