package object;

import entity.Entity;
import main.gamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Boots extends Entity {
    public static final String objName="Boots";
    public OBJ_Boots(gamePanel gp) {
        super(gp);
        name =objName;
        down1=setup("/object/boots.png", gp.tileSize, gp.tileSize);
    }
}
