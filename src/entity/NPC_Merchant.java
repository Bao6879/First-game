package entity;

import main.gamePanel;
import object.*;

public class NPC_Merchant extends Entity {
    public NPC_Merchant(gamePanel gp) {
        super(gp);
        direction="down";

        getImage();
        setDialogue();
        setItems();
    }
    public void getImage() {
        up1=setup("/NPC/merchant_down_1.png", gp.tileSize, gp.tileSize);
        up2=setup("/NPC/merchant_down_2.png", gp.tileSize, gp.tileSize);
        down1=setup("/NPC/merchant_down_1.png", gp.tileSize, gp.tileSize);
        down2=setup("/NPC/merchant_down_2.png", gp.tileSize, gp.tileSize);
        left1=setup("/NPC/merchant_down_1.png", gp.tileSize, gp.tileSize);
        left2=setup("/NPC/merchant_down_2.png", gp.tileSize, gp.tileSize);
        right1=setup("/NPC/merchant_down_1.png", gp.tileSize, gp.tileSize);
        right2=setup("/NPC/merchant_down_2.png", gp.tileSize, gp.tileSize);
    }
    public void setDialogue()
    {
        dialogues[0][0]="Sup bro, wanna trade?";
        dialogues[1][0]="Come again, hehe!";
        dialogues[2][0]="You need more coins, my man.";
        dialogues[3][0]="You can carry no more...";
        dialogues[4][0]="You can't sell an equipped item.";

    }
    public void setItems()
    {
        inventory.add(new OBJ_Potion_Red(gp));
        inventory.add(new OBJ_Key(gp));
        inventory.add(new OBJ_Shield_Blue(gp));
        inventory.add(new OBJ_Sword_Normal(gp));
    }
    public void speak()
    {
        super.speak();
        gp.gameState=gp.tradeState;
        gp.ui.npc=this;
    }
}
