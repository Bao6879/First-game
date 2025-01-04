package object;

import entity.Entity;
import main.gamePanel;

public class OBJ_Door_Iron extends Entity{
    gamePanel gp;
    public static final String objName="Iron Door";
    public OBJ_Door_Iron(gamePanel gp) {
        super(gp);
        this.gp = gp;
        down1=setup("/object/door_iron.png", gp.tileSize, gp.tileSize);
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
        dialogues[0][0]="It ain't opening.";
    }
    public void interact()
    {
        startDialogue(this, 0);
    }
}
