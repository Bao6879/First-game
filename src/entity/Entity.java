package entity;

import main.gamePanel;
import main.utilityTool;
import object.OBJ_Key;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class Entity {
    public BufferedImage image, image1, image2;
    public String name;
    public boolean collision=false;
    gamePanel gp;
    public int worldX, worldY;
    public int speed;
    public BufferedImage up1, up2, down1, down2, right1, right2, left1, left2;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
    public String direction="down";
    public String description="";
    public int spriteCounter=0;
    public int spriteNum=1;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public Rectangle attackArea=new Rectangle(0, 0, 0, 0);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collsionOn = false;
    public boolean invicible=false;
    public int invincibleCounter=0;
    public int actionLockCounter = 0;
    public String[][] dialogues =new String[20][20];
    public int dialogueSet=0;
    public int dialogueIndex=0;
    public int type; //0 player, 1 NPC, 2 monster
    public int value;
    public final int typePlayer=0;
    public final int typeNPC=1;
    public final int typeMonster=2;
    public final int typeSword=3;
    public final int typeAxe=4;
    public final int typeShield=5;
    public final int typeConsumable=6;
    public final int typePickupOnly=7;
    public final int typeObstacle=8;
    public boolean attacking=false;
    public boolean alive=true;
    public boolean dying=false;
    public int dyingCounter=0;
    public boolean hpBarOn=false;
    public int hpBarCounter=0;
    public int level;
    public int strength;
    public int dexterity;
    public int attack;
    public int defense;
    public int exp;
    public int nextLevelExp;
    public int coin;
    public Entity currentWeapon;
    public Entity currentShield;
    public int attackValue;
    public int defenseValue;
    public int maxMana;
    public int mana;
    public Projectile projectile;
    public int useCost;
    public int shotAvailableCounter=0;
    public ArrayList<Entity> inventory = new ArrayList <> ();
    public final int maxInventorySize=20;
    public int price;
    public boolean onPath=false;
    public boolean knockback=false;
    int knockbackCounter=0;
    public int defaultSpeed;
    public int knockbackPower=0;
    public boolean stackable=false;
    public int amount=1;
    public Entity attacker;
    public String knockbackDirection;
    public int motion1Duration;
    public int motion2Duration;
    public Entity loot;
    public boolean opened=false;
    public boolean enraged=false;
    public boolean boss=false;
    public boolean sleep=false;
    public boolean temp=false;
    public boolean drawing=true;

    //Char stats
    public int maxLife, life;
    public Entity(gamePanel gp) {
        this.gp = gp;
    }
    public void searchPath(int goalCol, int goalRow) {
        int startCol=(worldX+solidArea.x)/gp.tileSize, startRow=(worldY+solidArea.y)/gp.tileSize;
        gp.pFinder.setNode(startCol, startRow, goalCol, goalRow);
        if (gp.pFinder.search()==true)
        {
            int nextX=gp.pFinder.pathList.get(0).col, nextY=gp.pFinder.pathList.get(0).row;
            nextX*=gp.tileSize;
            nextY*=gp.tileSize;
            int enLeftX=worldX+solidArea.x;
            int enRightX=worldX+solidArea.x+solidArea.width;
            int enTopY=worldY+solidArea.y;
            int enBottomY=worldY+solidArea.y+solidArea.height;
            if (enTopY>nextY && enLeftX>=nextX && enRightX<nextX+gp.tileSize)
                direction="up";
            else if (enTopY<nextY && enLeftX>=nextX && enRightX<nextX+gp.tileSize)
                direction="down";
            else if (enTopY>=nextY && enBottomY<nextY+gp.tileSize) {
                if (enLeftX>nextX)
                    direction="left";
                if (enLeftX<nextX)
                    direction="right";
            }
            else if (enTopY>nextY && enLeftX>nextX)
            {
                direction="up";
                checkCollision();
                if (collsionOn==true)
                {
                    direction="left";
                }
            }
            else if (enTopY>nextY && enLeftX<nextX)
            {
                direction="up";
                checkCollision();
                if (collsionOn==true)
                    direction="right";
            }
            else if (enTopY<nextY && enLeftX>nextX)
            {
                direction="down";
                checkCollision();
                if (collsionOn==true)
                    direction="left";
            }
            else if (enTopY<nextY && enLeftX<nextX)
            {
                direction="down";
                checkCollision();
                if (collsionOn==true)
                    direction="right";
            }
//            int nextCol=gp.pFinder.pathList.get(0).col;
//            int nextRow=gp.pFinder.pathList.get(0).row;
//            if (nextCol==goalCol && nextRow==goalRow)
//            {
//                onPath=false;
//            }
        }
    }
    public void setAction()
    {

    }
    public void interact()
    {

    }
    public void damageReaction()
    {

    }
    public boolean use(Entity e)
    {
        return false;
    }
    public int getDetected(Entity user, Entity[][] target, String targetName)
    {
        int index=999;
        int nextWorldX=user.getLeftX();
        int nextWorldY=user.getTopY();
        switch(user.direction)
        {
            case "up":
                nextWorldY=user.getTopY()-gp.player.speed;
                break;
            case "down":
                nextWorldY=user.getBottomY()+gp.player.speed;
                break;
            case "left":
                nextWorldX=user.getLeftX()-gp.player.speed;
                break;
            case "right":
                nextWorldX=user.getRightX()+gp.player.speed;
                break;
        }
        int col=nextWorldX/gp.tileSize, row=nextWorldY/gp.tileSize;
        for (int i=0; i<target[1].length; i++)
        {
            if (target[gp.currentMap][i]!=null)
            {
                if (target[gp.currentMap][i].getCol()==col && target[gp.currentMap][i].getRow()==row
                && target[gp.currentMap][i].name.equals(targetName))
                {
                    index=i;
                    break;
                }
            }
        }
        return index;
    }
    public int getXDistance(Entity target)
    {
        return Math.abs(getCenterX()-target.getCenterX());
    }
    public int getYDistance(Entity target)
    {
        return Math.abs(getCenterY()-target.getCenterY());
    }
    public int getTileDistance(Entity target)
    {
        return (getXDistance(target)+getYDistance(target))/gp.tileSize;
    }
    public int getGoalCol(Entity target)
    {
        return (target.worldX+target.solidArea.x)/gp.tileSize;
    }
    public int getGoalRow(Entity target)
    {
        return (target.worldY+target.solidArea.y)/gp.tileSize;
    }
    public void checkStartChasing(Entity target, int distance, int rate)
    {
        if (getTileDistance(target)<distance)
        {
            int i=new Random().nextInt(rate)+1;
            if (i>=rate/3)
                onPath=true;
        }
    }
    public void getRandomDirection(int interval)
    {
        Random rand = new Random();
        actionLockCounter++;
        if (actionLockCounter >= interval) {
            int i = rand.nextInt(100) + 1; //number from 1 - 100
            if (i <= 25)
                direction = "up";
            else if (i <= 50)
                direction = "down";
            else if (i <= 75)
                direction = "left";
            else if (i <= 100)
                direction = "right";
            actionLockCounter = 0;
        }
    }
    public void checkStopChasing(Entity target, int distance, int rate)
    {
        if (getTileDistance(target)>distance)
        {
            int i=new Random().nextInt(rate)+1;
            if (i==0)
                onPath=false;
        }
    }
    public void checkShoot(int rate, int shotInterval)
    {
        int i=new Random().nextInt(rate) + 1;
        if (i<=rate/10 && projectile.alive==false && shotAvailableCounter==shotInterval)
        {
            projectile.set(worldX, worldY, direction, true, this);
            for (int j=0; j<gp.projectileList[1].length; j++)
            {
                if (gp.projectileList[gp.currentMap][j]==null)
                {
                    gp.projectileList[gp.currentMap][j]=projectile;
                    break;
                }
            }
            shotAvailableCounter=0;
        }
    }
    public void checkDrop()
    {

    }
    public void checkCollision()
    {
        collsionOn=false;
        gp.cCheck.checkTile(this);
        gp.cCheck.checkObject(this, false);
        gp.cCheck.checkEntity(this, gp.npc);
        gp.cCheck.checkEntity(this, gp.monster);
        boolean contactPlayer=gp.cCheck.checkPlayer(this);
        if (this.type==typeMonster && contactPlayer==true)
        {
            damagePlayer(attack);
        }
    }
    public int getLeftX()
    {
        return worldX+solidArea.x;
    }
    public int getRightX()
    {
        return worldX+solidArea.width+solidArea.x;
    }
    public int getTopY()
    {
        return worldY+solidArea.y;
    }
    public int getBottomY()
    {
        return worldY+solidArea.y+solidArea.height;
    }
    public int getCol()
    {
        return (worldX+solidArea.x)/gp.tileSize;
    }
    public int getRow()
    {
        return (worldY+solidArea.y)/gp.tileSize;
    }
    public void dropItem(Entity droppedItem)
    {
        for (int i=0; i<gp.obj[1].length; i++)
        {
            if (gp.obj[gp.currentMap][i]==null)
            {
                gp.obj[gp.currentMap][i]=droppedItem;
                gp.obj[gp.currentMap][i].worldX=worldX;
                gp.obj[gp.currentMap][i].worldY=worldY;
                break;
            }
        }
    }
    public void update()
    {
        if (sleep==false) {
            if (knockback == true) {
                checkCollision();
                if (collsionOn == true) {
                    knockbackCounter = 0;
                    knockback = false;
                    speed = defaultSpeed;
                } else if (collsionOn == false) {
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
                    knockbackCounter++;
                    if (knockbackCounter == 10) {
                        knockbackCounter = 0;
                        knockback = false;
                        speed = defaultSpeed;
                    }
                }
            } else if (attacking == true) {
                attack();
            } else {
                setAction();
                checkCollision();
                if (collsionOn == false) {
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
                spriteCounter++;
                if (spriteCounter > 24) {
                    if (spriteNum == 1)
                        spriteNum = 2;
                    else if (spriteNum == 2)
                        spriteNum = 1;
                    spriteCounter = 0;
                }
            }
            if (invicible == true) {
                invincibleCounter++;
                if (invincibleCounter == 40) {
                    invicible = false;
                    invincibleCounter = 0;
                }
            }
            if (shotAvailableCounter < 30)
                shotAvailableCounter++;
        }
    }
    public void attack()
    {
        spriteCounter++;
        if (spriteCounter<=motion1Duration) //Change timing on attack
            spriteNum=1;
        if (spriteCounter>motion1Duration && spriteCounter<=motion2Duration) {
            spriteNum = 2;
            int currentWorldX=worldX, currentWorldY=worldY;
            int solidAreaWidth=solidArea.width, solidAreaHeight=solidArea.height;
            switch (direction) {
                case "up":
                    worldY -= attackArea.height;
                    break;
                case "down":
                    worldY+=attackArea.height;
                    break;
                case "left":
                    worldX-=attackArea.width;
                    break;
                case "right":
                    worldX+=attackArea.width;
                    break;
            }
            solidArea.width=attackArea.width;
            solidArea.height=attackArea.height;
            if (type==typeMonster) {
                if (gp.cCheck.checkPlayer(this)==true) {
                    damagePlayer(attack);
                }
            }
            else {
                int monsterIndex = gp.cCheck.checkEntity(this, gp.monster);
                gp.player.damageMonster(monsterIndex, this, attack, currentWeapon.knockbackPower);
                int projectileIndex = gp.cCheck.checkEntity(this, gp.projectileList);
                gp.player.damageProjectile(projectileIndex);
            }
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }
        if (spriteCounter>=motion2Duration)
        {
            spriteNum=1;
            spriteCounter=0;
            attacking=false;
        }
    }
    public int getCenterX()
    {
        return worldX+left1.getWidth()/2;
    }
    public int getCenterY()
    {
        return worldY+up1.getHeight()/2;
    }
    public void checkAttack(int rate, int straight, int horizontal)
    {
        boolean targetInRange=false;
        int xDis=getXDistance(gp.player);
        int yDis=getYDistance(gp.player);
        switch (direction) {
            case "up":
                if (gp.player.getCenterY()<getCenterY() && yDis<straight && xDis<horizontal)
                    targetInRange=true;
                break;
            case "down":
                if (gp.player.getCenterY()>getCenterY() && yDis<straight && xDis<horizontal)
                    targetInRange=true;
                break;
            case "left":
                if (gp.player.getCenterX()<getCenterX() && xDis<straight && yDis<horizontal)
                    targetInRange=true;
                break;
            case "right":
                if (gp.player.getCenterX()>getCenterX() && xDis<straight && yDis<horizontal)
                    targetInRange=true;
                break;
        }
        if (targetInRange==true)
        {
            int i=new Random().nextInt(rate)+1;
            if (i>rate/2) {
                attacking = true;
                spriteCounter = 0;
                spriteNum = 1;
                shotAvailableCounter = 0;
            }
        }
    }
    public void damagePlayer(int attack)
    {
        if (gp.player.invicible==false)
        {
            gp.playSE(6);
            setKnockback(gp.player, this, knockbackPower);
            int damage=attack-gp.player.defense;
            if (damage<0)
                damage=0;
            gp.player.life-=damage;
            gp.player.invicible=true;
        }
    }
    public void setKnockback(Entity target, Entity attacker, int knockbackPower)
    {
        this.attacker=attacker;
        target.knockbackDirection=attacker.direction;
        target.speed+=knockbackPower;
        target.knockback=true;
    }
    public void facePlayer()
    {

        switch (gp.player.direction) {
            case "up":
                direction="down";
                break;
            case "down":
                direction="up";
                break;
            case "left":
                direction="right";
                break;
            case "right":
                direction="left";
                break;
        }
    }
    public void moveTowardPlayer(int interval)
    {
        actionLockCounter++;
        if (actionLockCounter>=interval)
        {
            if (getXDistance(gp.player)>getYDistance(gp.player))
            {
                if (gp.player.getCenterX()<getCenterX())
                    direction="left";
                else
                    direction="right";
            }
            else if (getXDistance(gp.player)<getYDistance(gp.player))
            {
                if (gp.player.getCenterY()<getCenterY())
                    direction="up";
                else
                    direction="down";
            }
            actionLockCounter=0;
        }
    }
    public void speak()
    {

    }
    public void startDialogue(Entity entity, int setNum)
    {
        gp.gameState=gp.dialogueState;
        gp.ui.npc=entity;
        dialogueSet=setNum;
    }
    public BufferedImage setup(String imageName, int width, int height)
    {
        utilityTool uTool=new utilityTool();
        BufferedImage img=null;
        try
        {
            img= ImageIO.read(getClass().getResourceAsStream(imageName));
            img=uTool.scaleImage(img, width, height);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return img;
    }
    public int getScreenX()
    {
        return worldX-gp.player.worldX+gp.player.screenX;
    }
    public int getScreenY()
    {
        return worldY-gp.player.worldY+gp.player.screenY;
    }
    public boolean inCamera()
    {
        boolean inCamera=false;
        if (worldX+gp.tileSize*5>=gp.player.worldX-gp.player.screenX &&
                worldX-gp.tileSize<=gp.player.worldX+gp.player.screenX &&
                worldY+gp.tileSize*5>=gp.player.worldY-gp.player.screenY &&
                worldY-gp.tileSize<=gp.player.worldY+gp.player.screenY)
            inCamera=true;
        return inCamera;
    }
    public void draw(Graphics2D g2)
    {
        BufferedImage image=null;
        if (inCamera()==true) {
            int tempScreenX=getScreenX(), tempScreenY=getScreenY();
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
                        tempScreenY=getScreenY()-up1.getHeight();
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
                        tempScreenX=getScreenX()-left1.getWidth();
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
            if (invicible==true) {
                hpBarOn=true;
                hpBarCounter=0;
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            }
            if (dying==true)
                dyingAnimation(g2);
            g2.drawImage(image, tempScreenX, tempScreenY, null);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        }
    }
    public void dyingAnimation(Graphics2D g2)
    {
        dyingCounter++;
        int a=dyingCounter/5;
        if (a%2==0)
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0f));
        else if (a%2==1)
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        if (dyingCounter>40)
        {
            alive=false;
        }
    }

    public void setLoot(Entity loot) {
    }
}
