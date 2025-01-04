package object;

import entity.Entity;
import main.gamePanel;

public class OBJ_Axe extends Entity {

    public static final String objName="Axe";
    public OBJ_Axe(gamePanel gp) {
        super(gp);
        name=objName;
        down1=setup("/object/axe.png", gp.tileSize, gp.tileSize);
        attackValue=2;
        attackArea.width=30;
        attackArea.height=30;
        description="["+name+"]\n Normal ah axe";
        type=typeAxe;
        price=75;
        knockbackPower=10;
        motion1Duration=20;
        motion2Duration=40;
    }
}
