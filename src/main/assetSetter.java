package main;

import entity.NPC_Merchant;
import entity.NPC_OldMan;
import monster.*;
import object.*;

public class assetSetter {
    gamePanel gp;
    public assetSetter(gamePanel gp) {
        this.gp = gp;
    }
    public void setObject()
    {
        int mapNum=0;
        int i=0;
        gp.obj[mapNum][i]=new OBJ_Key(gp);
        gp.obj[mapNum][i].worldX=gp.tileSize*25;
        gp.obj[mapNum][i].worldY=gp.tileSize*19;
        i++;
        gp.obj[mapNum][i]=new OBJ_Key(gp);
        gp.obj[mapNum][i].worldX=gp.tileSize*21;
        gp.obj[mapNum][i].worldY=gp.tileSize*19;
        i++;
        gp.obj[mapNum][i]=new OBJ_Axe(gp);
        gp.obj[mapNum][i].worldX=gp.tileSize*33;
        gp.obj[mapNum][i].worldY=gp.tileSize*21;
        i++;
        gp.obj[mapNum][i]=new OBJ_Shield_Blue(gp);
        gp.obj[mapNum][i].worldX=gp.tileSize*35;
        gp.obj[mapNum][i].worldY=gp.tileSize*21;
        i++;
        gp.obj[mapNum][i]=new OBJ_Potion_Red(gp);
        gp.obj[mapNum][i].worldX=gp.tileSize*22;
        gp.obj[mapNum][i].worldY=gp.tileSize*27;
        i++;
        gp.obj[mapNum][i]=new OBJ_Heart(gp);
        gp.obj[mapNum][i].worldX=gp.tileSize*22;
        gp.obj[mapNum][i].worldY=gp.tileSize*29;
        i++;
        gp.obj[mapNum][i]=new OBJ_ManaCrystal(gp);
        gp.obj[mapNum][i].worldX=gp.tileSize*22;
        gp.obj[mapNum][i].worldY=gp.tileSize*31;
        i++;
        gp.obj[mapNum][i]=new OBJ_Door(gp);
        gp.obj[mapNum][i].worldX=gp.tileSize*14;
        gp.obj[mapNum][i].worldY=gp.tileSize*28;
        i++;
        gp.obj[mapNum][i]=new OBJ_Door(gp);
        gp.obj[mapNum][i].worldX=gp.tileSize*12;
        gp.obj[mapNum][i].worldY=gp.tileSize*12;
        i++;
        gp.obj[mapNum][i]=new OBJ_Chest(gp);
        gp.obj[mapNum][i].setLoot(new OBJ_Key(gp));
        gp.obj[mapNum][i].worldX=gp.tileSize*26;
        gp.obj[mapNum][i].worldY=gp.tileSize*21;
        mapNum=3;
        i=0;
        gp.obj[mapNum][i]=new OBJ_Door_Iron(gp);
        gp.obj[mapNum][i].worldX=gp.tileSize*25;
        gp.obj[mapNum][i].worldY=gp.tileSize*15;
        i++;
        gp.obj[mapNum][i]=new OBJ_BlueHeart(gp);
        gp.obj[mapNum][i].worldX=gp.tileSize*25;
        gp.obj[mapNum][i].worldY=gp.tileSize*8;
        i++;
    }
    public void setNPC()
    {
        int mapNum=0, i=0;
        gp.npc[mapNum][i]=new NPC_OldMan(gp);
        gp.npc[mapNum][i].worldX=gp.tileSize*21;
        gp.npc[mapNum][i].worldY=gp.tileSize*21;
        i=0;
        mapNum++;
        gp.npc[mapNum][i]=new NPC_Merchant(gp);
        gp.npc[mapNum][i].worldX=gp.tileSize*12;
        gp.npc[mapNum][i].worldY=gp.tileSize*7;
    }
    public void setMonster()
    {
        int mapNum=0;
        int i=0;
        gp.monster[mapNum][i]=new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX=gp.tileSize*21;
        gp.monster[mapNum][i].worldY=gp.tileSize*38;
        i++;
        gp.monster[mapNum][i]=new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX=gp.tileSize*23;
        gp.monster[mapNum][i].worldY=gp.tileSize*37;
        i++;
        gp.monster[mapNum][i]=new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX=gp.tileSize*23;
        gp.monster[mapNum][i].worldY=gp.tileSize*42;
        i++;
        gp.monster[mapNum][i]=new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX=gp.tileSize*34;
        gp.monster[mapNum][i].worldY=gp.tileSize*42;
        i++;
        gp.monster[mapNum][i]=new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX=gp.tileSize*38;
        gp.monster[mapNum][i].worldY=gp.tileSize*42;
        i++;
        gp.monster[mapNum][i]=new MON_Orc(gp);
        gp.monster[mapNum][i].worldX=gp.tileSize*12;
        gp.monster[mapNum][i].worldY=gp.tileSize*33;
        i++;
        gp.monster[mapNum][i]=new MON_RedSlime(gp);
        gp.monster[mapNum][i].worldX=gp.tileSize*38;
        gp.monster[mapNum][i].worldY=gp.tileSize*10;
        i++;
        gp.monster[mapNum][i]=new MON_RedSlime(gp);
        gp.monster[mapNum][i].worldX=gp.tileSize*38;
        gp.monster[mapNum][i].worldY=gp.tileSize*12;
        i++;
        mapNum=2;
        i=0;
        gp.monster[mapNum][i]=new MON_Bat(gp);
        gp.monster[mapNum][i].worldX=gp.tileSize*34;
        gp.monster[mapNum][i].worldY=gp.tileSize*39;
        i++;
        gp.monster[mapNum][i]=new MON_Bat(gp);
        gp.monster[mapNum][i].worldX=gp.tileSize*36;
        gp.monster[mapNum][i].worldY=gp.tileSize*25;
        i++;
        gp.monster[mapNum][i]=new MON_Bat(gp);
        gp.monster[mapNum][i].worldX=gp.tileSize*39;
        gp.monster[mapNum][i].worldY=gp.tileSize*26;
        i++;
        gp.monster[mapNum][i]=new MON_Bat(gp);
        gp.monster[mapNum][i].worldX=gp.tileSize*28;
        gp.monster[mapNum][i].worldY=gp.tileSize*11;
        i++;
        gp.monster[mapNum][i]=new MON_Bat(gp);
        gp.monster[mapNum][i].worldX=gp.tileSize*10;
        gp.monster[mapNum][i].worldY=gp.tileSize*19;
        i++;
        mapNum=3;
        i=0;
        gp.monster[mapNum][i]=new MON_SkeletonLord(gp);
        gp.monster[mapNum][i].worldX=gp.tileSize*23;
        gp.monster[mapNum][i].worldY=gp.tileSize*16;
        i++;
    }
}
