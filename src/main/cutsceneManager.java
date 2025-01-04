package main;

import entity.playerDummy;
import monster.MON_SkeletonLord;
import object.OBJ_BlueHeart;
import object.OBJ_Door_Iron;

import java.awt.*;
import java.util.Objects;

public class cutsceneManager {
    gamePanel gp;
    Graphics2D g2;
    public int scenePhase=0;
    public final int NA=0;
    public final int skeletonLord=1;
    public final int ending=2;
    int counter=0;
    float alpha=0;
    int y;
    public int sceneNum=NA;
    String endCredit;
    public cutsceneManager(gamePanel gp) {
        this.gp = gp;
        endCredit = "Music/Art\n" +
                "RyiSnow\n" +
                "\n\n\n\n\n\n\n\n\n\n\n\n\n" +
                "Programmer\n" +
                "Brian\n\n" +
                "Thank you for playing the game!";
    }
    public void draw(Graphics2D g2) {
        this.g2 = g2;
        switch(sceneNum) {
            case skeletonLord:
                sceneSkeletonLord();
                break;
            case ending:
                sceneEnding();
                break;
        }
    }
    public void sceneSkeletonLord() {
        if (scenePhase == 0) {
            gp.bossBattleOn=true;
            gp.stopMusic();
            for (int i=0; i<gp.obj[1].length; i++)
            {
                if (gp.obj[gp.currentMap][i]==null)
                {
                    gp.obj[gp.currentMap][i]=new OBJ_Door_Iron(gp);
                    gp.obj[gp.currentMap][i].worldX=gp.tileSize*25;
                    gp.obj[gp.currentMap][i].worldY=gp.tileSize*28;
                    gp.obj[gp.currentMap][i].temp=true;
                    gp.playSE(18);
                    break;
                }
            }
            for (int i=0; i<gp.npc[1].length; i++)
            {
                if (gp.npc[gp.currentMap][i]==null)
                {
                    gp.npc[gp.currentMap][i]=new playerDummy(gp);
                    gp.npc[gp.currentMap][i].worldX=gp.player.worldX;
                    gp.npc[gp.currentMap][i].worldY=gp.player.worldY;
                    gp.npc[gp.currentMap][i].direction=gp.player.direction;
                    break;
                }
            }
            gp.player.drawing=false;
            scenePhase++;
        }
        else if (scenePhase == 1) {
            gp.player.worldY-=2;
            if (gp.player.worldY<gp.tileSize*16)
                scenePhase++;
        }
        else if (scenePhase == 2) {
            for (int i=0; i<gp.monster[1].length; i++)
            {
                if (gp.monster[gp.currentMap][i]!=null && gp.monster[gp.currentMap][i].name==MON_SkeletonLord.monName)
                {
                    gp.monster[gp.currentMap][i].sleep=false;
                    gp.ui.npc=gp.monster[gp.currentMap][i];
                    scenePhase++;
                    break;
                }
            }
        }
        else if (scenePhase == 3) {
            gp.ui.drawDialogueScreen();
        }
        else if (scenePhase==4)
        {
            for (int i=0; i<gp.npc[1].length; i++)
            {
                if (gp.npc[gp.currentMap][i]!=null && Objects.equals(gp.npc[gp.currentMap][i].name, playerDummy.npcName))
                {
                    gp.player.worldX=gp.npc[gp.currentMap][i].worldX;
                    gp.player.worldY=gp.npc[gp.currentMap][i].worldY;
                    gp.npc[gp.currentMap][i]=null;
                    break;
                }
            }
            gp.player.drawing=true;
            scenePhase=0;
            sceneNum=NA;
            gp.gameState=gp.playState;
            gp.stopMusic();
            gp.playMusic(19);
        }
    }
    public void sceneEnding()
    {
        if (scenePhase==0)
        {
            gp.stopMusic();
            gp.ui.npc=new OBJ_BlueHeart(gp);
            scenePhase++;
        }
        else if (scenePhase==1)
        {
            gp.ui.drawDialogueScreen();
        }
        else if (scenePhase==2)
        {
            gp.playSE(4);
            scenePhase++;
        }
        else if (scenePhase==3)
        {
            if (counterReached(300)==true)
            {
                scenePhase++;
            }
        }
        else if (scenePhase==4)
        {
            alpha+=0.005f;
            if (alpha>=1f)
                alpha=1f;
            drawBlackBackground(alpha);
            if (alpha==1f)
            {
                alpha=0;
                scenePhase++;
            }
        }
        else if (scenePhase==5)
        {
            drawBlackBackground(1f);
            alpha+=0.005f;
            if (alpha>1f)
                alpha=1f;
            String text="It is just the beginning\n"
                    + "The adventure has just begun";
            drawString(alpha, 38f, 200, text, 70);
            if (counterReached(420)==true)
            {
                gp.playMusic(0);
                scenePhase++;
            }
        }
        else if (scenePhase==6)
        {
            drawBlackBackground(1f);
            drawString(1f, 60f, gp.screenHeight/2, "Soul Knight Knockoff", 40);
            if (counterReached(480)==true)
            {
                scenePhase++;
            }
        }
        else if (scenePhase==7)
        {
            drawBlackBackground(1f);
            y=gp.screenHeight/2;
            drawString(1f, 38f, y, endCredit, 40);
            if (counterReached(480)==true)
                scenePhase++;
        }
        else if (scenePhase==8)
        {
            drawBlackBackground(1f);
            y--;
            drawString(1f, 38f, y, endCredit, 40);
        }
    }
    public boolean counterReached(int target)
    {
        boolean counterReached=false;
        counter++;
        if (counter>=target) {
            counterReached = true;
            counter=0;
        }
        return counterReached;
    }
    public void drawBlackBackground(float alpha) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2.setColor(Color.black);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
    }
    public void drawString(float alpha, float fontSize, int y, String text, int lineHeight)
    {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(fontSize));
        for (String line : text.split("\n"))
        {
            int x=gp.ui.getXForCenter(line);
            g2.drawString(line, x, y);
            y+=lineHeight;
        }
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
    }
}
