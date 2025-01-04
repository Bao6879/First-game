package entity;

import main.gamePanel;

public class Projectile extends Entity{

    Entity user;
    public Projectile(gamePanel gp) {
        super(gp);
    }
    public boolean haveResource(Entity user) {
        boolean have=false;
        if (user.mana>=useCost)
            have=true;
        return have;
    }
    public void subtractResource(Entity user) {

    }
    public void set(int worldX, int worldY, String direction, boolean alive, Entity user)
    {
        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.alive = alive;
        this.user = user;
        this.life=this.maxLife;
    }
    public void update()
    {
        if (user==gp.player)
        {
            int monsterIndex=gp.cCheck.checkEntity(this, gp.monster);
            if (monsterIndex!=999)
            {
                gp.player.damageMonster(monsterIndex, this, attack*(gp.player.level/2), knockbackPower);
                alive=false;
            }
        }
        if (user!=gp.player)
        {
            boolean contactPlayer=gp.cCheck.checkPlayer(this);
            if (gp.player.invicible==false && contactPlayer==true)
            {
                damagePlayer(attack);
                alive=false;
            }
        }
        switch(direction)
        {
            case "up":
                worldY-=speed;
                break;
            case "down":
                worldY+=speed;
                break;
            case "left":
                worldX-=speed;
                break;
            case "right":
                worldX+=speed;
                break;
        }
        spriteCounter++;
        life--;
        if (life<=0)
            alive=false;
        if (spriteCounter >= 12)
        {
            if (spriteNum==1)
                spriteNum=2;
            else if (spriteNum==2)
                spriteNum=1;
            spriteCounter=0;
        }
    }
}
