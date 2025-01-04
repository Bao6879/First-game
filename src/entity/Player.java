package entity;

import main.*;
import object.OBJ_Fireball;
import object.OBJ_Key;
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Normal;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Player extends Entity {
    keyHandler keyH;
    public final int screenX;
    public final int screenY;
    public int standCounter=0;

    public Player(gamePanel gp, keyHandler keyH) {
        super(gp);
        this.keyH = keyH;
        screenX=gp.screenWidth/2 -gp.tileSize/2;
        screenY=gp.screenHeight/2 -gp.tileSize/2;
        solidArea=new Rectangle(8, 16, 32, 32);
        solidAreaDefaultX=solidArea.x;
        solidAreaDefaultY=solidArea.y;
        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
        setItems();
    }
    public void setDefaultValues() {
        worldX=gp.tileSize*23;
        worldY=gp.tileSize*25;
        gp.currentMap=0;
        defaultSpeed=4;
        speed=defaultSpeed;
        direction="down";
        maxLife=12;
        life=maxLife;
        level=1;
        strength=5; //Damage
        dexterity=1; //Reduced damage
        exp=0;
        nextLevelExp=5;
        coin=500;
        currentWeapon=new OBJ_Sword_Normal(gp);
        currentShield=new OBJ_Shield_Wood(gp);
        attack=getAttack();
        defense=getDefense();
        projectile=new OBJ_Fireball(gp);
        maxMana=8;
        mana=maxMana;
    }
    public void setItems()
    {
        inventory.clear();
        inventory.add(currentShield);
        inventory.add(currentWeapon);
        inventory.add(new OBJ_Key(gp));
        inventory.add(new OBJ_Key(gp));
    }
    public void getPlayerImage() {
        up1=setup("/player/boy_up_1.png", gp.tileSize, gp.tileSize);
        up2=setup("/player/boy_up_2.png", gp.tileSize, gp.tileSize);
        down1=setup("/player/boy_down_1.png", gp.tileSize, gp.tileSize);
        down2=setup("/player/boy_down_2.png", gp.tileSize, gp.tileSize);
        left1=setup("/player/boy_left_1.png", gp.tileSize, gp.tileSize);
        left2=setup("/player/boy_left_2.png", gp.tileSize, gp.tileSize);
        right1=setup("/player/boy_right_1.png", gp.tileSize, gp.tileSize);
        right2=setup("/player/boy_right_2.png", gp.tileSize, gp.tileSize);
    }
    public void getPlayerAttackImage(){
        attackUp1=setup("/player/boy_attack_up_1.png", gp.tileSize, gp.tileSize*2);
        attackUp2=setup("/player/boy_attack_up_2.png", gp.tileSize, gp.tileSize*2);
        attackDown1=setup("/player/boy_attack_down_1.png", gp.tileSize, gp.tileSize*2);
        attackDown2=setup("/player/boy_attack_down_2.png", gp.tileSize, gp.tileSize*2);
        attackLeft1=setup("/player/boy_attack_left_1.png", gp.tileSize*2, gp.tileSize);
        attackLeft2=setup("/player/boy_attack_left_2.png", gp.tileSize*2, gp.tileSize);
        attackRight1=setup("/player/boy_attack_right_1.png", gp.tileSize*2, gp.tileSize);
        attackRight2=setup("/player/boy_attack_right_2.png", gp.tileSize*2, gp.tileSize);
    }
    public int getAttack()
    {
        attackArea=currentWeapon.attackArea;
        motion1Duration= currentWeapon.motion1Duration;
        motion2Duration=currentWeapon.motion2Duration;
        return strength* currentWeapon.attackValue;
    }
    public int getDefense()
    {
        return dexterity* currentShield.defenseValue;
    }
    public int getCurrentWeaponSlot()
    {
        int ret=0;
        for (int i=0; i<inventory.size(); i++)
            if (inventory.get(i)==currentWeapon)
            {
                ret=i;
                break;
            }
        return ret;
    }
    public int getCurrentShieldSlot()
    {
        int ret=0;
        for (int i=0; i<inventory.size(); i++)
            if (inventory.get(i)==currentShield)
            {
                ret=i;
                break;
            }
        return ret;
    }
    public void update()
    {
        if (knockback==true)
        {
            collsionOn=false;
            gp.cCheck.checkTile(this);
            gp.cCheck.checkObject(this, true);
            gp.cCheck.checkEntity(this, gp.npc);
            gp.cCheck.checkEntity(this, gp.monster);
            if (collsionOn==true)
            {
                knockbackCounter=0;
                knockback=false;
                speed=defaultSpeed;
            }
            else if (collsionOn==false) {
                switch (knockbackDirection) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }
            knockbackCounter++;
            if (knockbackCounter==10)
            {
                knockbackCounter=0;
                knockback=false;
                speed=defaultSpeed;
            }
        }
        else if (attacking==true)
        {
            attack();
        }
        else if (keyH.upPressed==true || keyH.downPressed==true || keyH.leftPressed==true || keyH.rightPressed==true || keyH.enterPressed==true) {
            if (keyH.upPressed == true) {
                direction = "up";
            } else if (keyH.downPressed == true) {
                direction = "down";
            } else if (keyH.leftPressed == true) {
                direction = "left";
            } else if (keyH.rightPressed == true) {
                direction = "right";
            }
            collsionOn=false;
            gp.cCheck.checkTile(this);
            //false=move
            //Check objects
            int index=gp.cCheck.checkObject(this, true);
            pickUpObject(index);

            //Check NPC
            int npcIndex=gp.cCheck.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            //Check Monster
            int monsterIndex=gp.cCheck.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);

            //Events
            gp.eHandler.checkEvent();
            if (collsionOn==false && keyH.enterPressed==false) {
                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }
            gp.keyH.enterPressed=false;
            spriteCounter++;
            if (spriteCounter > 10) {
                if (spriteNum == 1)
                    spriteNum = 2;
                else if (spriteNum == 2)
                    spriteNum = 1;
                spriteCounter = 0;
            }
        }
        else
        {
            standCounter++;
            if (standCounter == 20) {
                standCounter = 0;
                spriteNum=1;
            }
        }
        if (gp.keyH.shotKeyPressed==true && projectile.alive==false && shotAvailableCounter==10
        && projectile.haveResource(this)==true)
        {
            projectile.set(worldX, worldY, direction, true, this);
            for (int i=0; i<gp.projectileList[1].length; i++)
            {
                if (gp.projectileList[gp.currentMap][i]==null)
                {
                    gp.projectileList[gp.currentMap][i]=projectile;
                    break;
                }
            }
            projectile.subtractResource(this);
            shotAvailableCounter=0;
            gp.playSE(10);
        }
        if (invicible==true) {
            invincibleCounter++;
            if (invincibleCounter == 60) {
                invicible=false;
                invincibleCounter = 0;
            }
        }
        if (shotAvailableCounter<10)
            shotAvailableCounter++;
        if (life>maxLife)
            life=maxLife;
        if (mana>maxMana)
            mana=maxMana;
        if (life<=0) {
            gp.gameState = gp.gameOverState;
            gp.playSE(11);
            gp.stopMusic();
        }
    }
    public void damageProjectile(int i)
    {
        if (i!=999)
        {
            gp.projectileList[gp.currentMap][i].alive=false;
        }
    }
    public void pickUpObject(int index)
    {
        if (index!=999)
        {
            if (gp.obj[gp.currentMap][index].type==typePickupOnly)
            {
                gp.obj[gp.currentMap][index].use(this);
                gp.obj[gp.currentMap][index]=null;
            }
            else if (gp.obj[gp.currentMap][index].type==typeObstacle)
            {
                if (keyH.enterPressed==true)
                {
                    gp.obj[gp.currentMap][index].interact();
                }
            }
            else {
                if (canObtainItem(gp.obj[gp.currentMap][index])==true) {
                    gp.playSE(1);
                    gp.ui.addMessage("Got a " + gp.obj[gp.currentMap][index].name + " !");
                    gp.obj[gp.currentMap][index] = null;
                } else {
                    gp.ui.addMessage("Inventory is full!");
                }
            }
        }
    }
    public void interactNPC(int npcIndex)
    {
        if (npcIndex!=999)
        {
            if (gp.keyH.enterPressed==true) {
                gp.npc[gp.currentMap][npcIndex].speak();
            }
        }
    }
    public void contactMonster(int i)
    {
        if (i!=999)
        {
            if (invicible==false && gp.monster[gp.currentMap][i].dying==false)
            {
                gp.playSE(6);
                int damage=gp.monster[gp.currentMap][i].attack-defense;
                damage=Math.abs(damage);
                life-=damage;
                invicible=true;
            }
        }
    }
    public void damageMonster(int i, Entity attacker, int attack, int knockbackPower)
    {
        if (i!=999)
        {
            if (gp.monster[gp.currentMap][i].invicible==false && gp.monster[gp.currentMap][i].dying==false) {
                gp.playSE(5);
                if (knockbackPower>0)
                    setKnockback(gp.monster[gp.currentMap][i], attacker, knockbackPower);
                int damage=attack-gp.monster[gp.currentMap][i].defense;
                damage=Math.abs(damage);
                gp.monster[gp.currentMap][i].life-=damage;
                gp.ui.addMessage(damage+" damage!");
                gp.monster[gp.currentMap][i].damageReaction();
                if (gp.monster[gp.currentMap][i].life<=0) {
                    gp.monster[gp.currentMap][i].dying=true;
                    exp+=gp.monster[gp.currentMap][i].exp;
                    gp.ui.addMessage("Killed the "+gp.monster[gp.currentMap][i].name+"!");
                    gp.ui.addMessage("Exp+ "+gp.monster[gp.currentMap][i].exp+"!");
                    checkLevelUp();
                }
                else {
                    gp.monster[gp.currentMap][i].invicible = true;
                }
            }
        }
    }
    public void checkLevelUp()
    {
        if (exp>=nextLevelExp)
        {
            level++;
            nextLevelExp+=10;
            maxLife+=2;
            strength++;
            dexterity++;
            attack=getAttack();
            defense=getDefense();
            gp.playSE(8);
            gp.gameState=gp.dialogueState;
            dialogues[0][0]="You are level " + level + " now!\n"+"You feel stronger!";
            startDialogue(this, 0);
        }
    }
    public void selectItem()
    {
        int itemIndex=gp.ui.getItemIndex(gp.ui.playerSlotCol, gp.ui.playerSlotRow);
        if (itemIndex<inventory.size())
        {
            Entity selectedItem=inventory.get(itemIndex);
            if (selectedItem.type==typeSword || selectedItem.type==typeAxe)
            {
                currentWeapon=selectedItem;
                attack=getAttack();
            }
            if (selectedItem.type==typeShield)
            {
                currentShield=selectedItem;
                defense=getDefense();
            }
            if (selectedItem.type==typeConsumable)
            {
                if (selectedItem.use(this)==true)
                {
                    if (selectedItem.amount>1)
                    {
                        selectedItem.amount--;
                    }
                    else
                    {
                        inventory.remove(selectedItem);
                    }
                }
            }
        }
    }
    public int searchItemInInventory(String itemName)
    {
        int itemIndex=999;
        for (int i=0;i<inventory.size();i++)
        {
            if (inventory.get(i).name.equals(itemName))
            {
                itemIndex=i;
                break;
            }
        }
        return itemIndex;
    }
    public boolean canObtainItem(Entity item)
    {
        Entity newItem=gp.eGenerator.getObject(item.name);
        boolean canObtain=false;
        if (newItem.stackable==true)
        {
            int index=searchItemInInventory(newItem.name);
            if (index!=999)
            {
                inventory.get(index).amount++;
                canObtain=true;
            }
            else
            {
                if (inventory.size()!=maxInventorySize)
                {
                    inventory.add(newItem);
                    canObtain=true;
                }
            }
        }
        else
        {
            if (inventory.size()!=maxInventorySize)
            {
                inventory.add(newItem);
                canObtain=true;
            }
        }
        return canObtain;
    }
    public void draw(Graphics2D g2)
    {
        BufferedImage image=null;
        int tempScreenX=screenX, tempScreenY=screenY;
        switch(direction){
            case "up":
                if (attacking==false) {
                    if (spriteNum == 1)
                        image = up1;
                    else if (spriteNum == 2)
                        image = up2;
                }
                else
                {
                    tempScreenY=screenY-gp.tileSize;
                    if (spriteNum == 1)
                        image = attackUp1;
                    else if (spriteNum == 2)
                        image = attackUp2;
                }
                break;
            case "down":
                if (attacking==false) {
                    if (spriteNum == 1)
                        image = down1;
                    else if (spriteNum == 2)
                        image = down2;
                }
                else
                {
                    if (spriteNum == 1)
                        image = attackDown1;
                    else if (spriteNum == 2)
                        image = attackDown2;
                }
                break;
            case "left":
                if (attacking==false) {
                    if (spriteNum == 1)
                        image = left1;
                    else if (spriteNum == 2)
                        image = left2;
                }
                else
                {
                    tempScreenX=screenX-gp.tileSize;
                    if (spriteNum == 1)
                        image = attackLeft1;
                    else if (spriteNum == 2)
                        image = attackLeft2;
                }
                break;
            case "right":
                if (attacking==false) {
                    if (spriteNum == 1)
                        image = right1;
                    else if (spriteNum == 2)
                        image = right2;
                }
                else
                {
                    if (spriteNum == 1)
                        image = attackRight1;
                    else if (spriteNum == 2)
                        image = attackRight2;
                }
                break;
        }
        if (invicible==true)
        {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }
        if (drawing==true)
            g2.drawImage(image,tempScreenX,tempScreenY,null);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
}
