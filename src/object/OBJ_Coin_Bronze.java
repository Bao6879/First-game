package object;

import entity.Entity;
import main.gamePanel;

public class OBJ_Coin_Bronze extends Entity {
    gamePanel gp;
    public static final String objName="Bronze coin";
    public OBJ_Coin_Bronze(gamePanel gp) {
        super(gp);
        this.gp = gp;
        type=typePickupOnly;
        name=objName;
        down1=setup("/object/coin_bronze.png", gp.tileSize, gp.tileSize);
        value=1;
    }
    public boolean use(Entity e) {
        gp.playSE(1);
        gp.ui.addMessage("Coin + "+value);
        gp.player.coin+=value;
        return true;
    }
}
