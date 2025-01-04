package object;

import entity.Entity;
import main.gamePanel;

public class OBJ_Shield_Blue extends Entity {
    public static final String objName="Shield Blue";
    public OBJ_Shield_Blue(gamePanel gp) {
        super(gp);
        name=objName;
        down1=setup("/object/shield_blue.png", gp.tileSize, gp.tileSize);
        defenseValue=2;
        description="["+name+"]\n Better ah shield";
        type=typeShield;
        price=100;
    }
}
