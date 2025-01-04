package object;

import entity.Entity;
import main.gamePanel;

public class OBJ_Sword_Normal extends Entity {
    public static final String objName="Normal Sword";
    public OBJ_Sword_Normal(gamePanel gp) {
        super(gp);
        name=objName;
        down1=setup("/object/sword_normal.png", gp.tileSize, gp.tileSize);
        attackValue=1;
        description="["+name+"]\n Normal ah sword";
        attackArea.width=36;
        attackArea.height=36;
        type=typeSword;
        price=20;
        knockbackPower=2;
        motion1Duration=5;
        motion2Duration=25;
    }
}
