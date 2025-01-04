package object;

import entity.Entity;
import main.gamePanel;

public class OBJ_BlueHeart extends Entity{
    gamePanel gp;
    public static final String objName = "Blue Heart";
    public OBJ_BlueHeart(gamePanel gp) {
        super(gp);
        this.gp = gp;
        type=typePickupOnly;
        name=objName;
        down1=setup("/object/blueheart.png", gp.tileSize, gp.tileSize);
        setDialogue();
    }
    public void setDialogue()
    {
        dialogues[0][0]="You picked up a beautiful diamond";
        dialogues[0][1]="Congratulations you have completed the game!";
    }
    public boolean use(Entity entity)
    {
        gp.gameState=gp.cutsceneState;
        gp.csManager.sceneNum=gp.csManager.ending;
        return true;
    }
}
