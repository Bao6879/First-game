package object;

import entity.Entity;
import main.gamePanel;

public class OBJ_Potion_Red extends Entity {

    gamePanel gp;
    public static final String objName="Health Potion";
    public OBJ_Potion_Red(gamePanel gp) {
        super(gp);
        this.gp=gp;
        value=5;
        type=typeConsumable;
        stackable=true;
        name=objName;
        down1=setup("/object/potion_red.png", gp.tileSize, gp.tileSize);
        description="["+name+"]\n Yeah, wonder what it does.";
        price=25;
        setDialogue();
    }
    public void setDialogue()
    {
        dialogues[0][0]="You drank the potion!\n"+"Your life has been restored by "+value+" !";
    }
    public boolean use(Entity e) {
        startDialogue(this, 0);
        e.life+=value;
        if (gp.player.life>gp.player.maxLife)
            gp.player.life=gp.player.maxLife;
        gp.playSE(2);
        return true;
    }
}
