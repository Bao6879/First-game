package object;

import entity.Entity;
import main.gamePanel;

public class OBJ_ManaCrystal extends Entity{

    gamePanel gp;
    public static final String objName="Mana Crystal";
    public OBJ_ManaCrystal(gamePanel gp) {
        super(gp);
        this.gp = gp;
        name = objName;
        type=typePickupOnly;
        value=1;
        down1=setup("/object/manacrystal_full.png", gp.tileSize, gp.tileSize);
        image=setup("/object/manacrystal_full.png", gp.tileSize, gp.tileSize);
        image1=setup("/object/manacrystal_blank.png", gp.tileSize, gp.tileSize);
    }
    public boolean use(Entity e)
    {
        gp.playSE(2);
        gp.ui.addMessage("Mana + "+value);
        e.mana+=value;
        return true;
    }
}
