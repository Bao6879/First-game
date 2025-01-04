package object;

import entity.Entity;
import main.gamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Heart extends Entity {
    gamePanel gp;
    public static final String objName="Heart";
    public OBJ_Heart(gamePanel gp) {
        super(gp);
        this.gp = gp;
        name = objName;
        image=setup("/object/heart_full.png", gp.tileSize, gp.tileSize);
        down1=setup("/object/heart_full.png", gp.tileSize, gp.tileSize);
        image1=setup("/object/heart_half.png", gp.tileSize, gp.tileSize);
        image2=setup("/object/heart_blank.png", gp.tileSize, gp.tileSize);
        type=typePickupOnly;
        value=2;
    }
    public boolean use(Entity e)
    {
        gp.playSE(2);
        gp.ui.addMessage("Life + "+value);
        e.life+=value;
        return true;
    }
}
