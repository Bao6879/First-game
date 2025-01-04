package object;

import entity.Entity;
import main.gamePanel;

public class OBJ_Shield_Wood extends Entity {
    public static final String objName="Wood Shield";
    public OBJ_Shield_Wood(gamePanel gp) {
        super(gp);
        name=objName;
        down1=setup("/object/shield_wood.png", gp.tileSize, gp.tileSize);
        defenseValue=1;
        description="["+name+"]\n Normal ah shield";
        type=typeShield;
    }
}
