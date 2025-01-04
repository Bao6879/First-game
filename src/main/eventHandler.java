package main;

import data.Progress;
import entity.Entity;

import java.awt.*;

public class eventHandler {
    gamePanel gp;
    eventRect eventRect[][][];
    Entity eventMaster;
    int prevEventX, prevEventY;
    boolean canTouchEvent = true;
    int tempMap, tempCol, tempRow;

    public eventHandler(gamePanel gp) {
        this.gp = gp;
        eventMaster = new Entity(gp);
        eventRect = new eventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
        int col = 0;
        int map = 0;
        int row = 0;
        for (map = 0; map < gp.maxMap; map++) {
            for (row = 0; row < gp.maxWorldRow; row++) {
                for (col = 0; col < gp.maxWorldCol; col++) {
                    eventRect[map][col][row] = new eventRect();
                    eventRect[map][col][row].x = 23;
                    eventRect[map][col][row].y = 23;
                    eventRect[map][col][row].width = 2;
                    eventRect[map][col][row].height = 2;
                    eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
                    eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;
                }
            }
        }
        setDialogue();
    }

    public void setDialogue()
    {
        eventMaster.dialogues[0][0]="You fell lmao";
        eventMaster.dialogues[1][0]="You heal and your progress has been saved";
        eventMaster.dialogues[1][1]="Damn, this is good water.";
    }
    public void damagePit(int gameState)
    {
        gp.gameState=gameState;
        eventMaster.startDialogue(eventMaster, 0);
        gp.player.life--;
        //eventRect[col][row].eventDone=true;
        canTouchEvent=false;
    }
    public void skeletonLord()
    {
        if (gp.bossBattleOn==false && Progress.skeletonLordDefeated==false)
        {
            gp.gameState=gp.cutsceneState;
            gp.csManager.sceneNum=gp.csManager.skeletonLord;

        }
    }
    public void checkEvent()
    {
        //Check distance > 1 tile before event
        int xDist=Math.abs(gp.player.worldX-prevEventX);
        int yDist=Math.abs(gp.player.worldY-prevEventY);
        int dist=Math.max(xDist,yDist);
        if (dist>gp.tileSize)
            canTouchEvent=true;
        if (canTouchEvent==true) {
            if (hit(0, 27, 16, "right") == true) {
                damagePit(gp.dialogueState);
            }
            else if (hit(0, 23, 12, "any") == true) {
                healingPool(gp.dialogueState);
            }
            else if (hit(0, 10, 39, "any")==true) { //merchant
                teleport(1, 12, 13);
                gp.stopMusic();
                gp.playMusic(16);
            }
            else if (hit(1, 12, 13, "any")==true) { //outside
                teleport(0, 10, 39);
                gp.stopMusic();
                gp.playMusic(0);
            }
            else if (hit(0, 12, 9, "any")==true) { //dungeon f1
                teleport(2,  9, 41);
                gp.stopMusic();
                gp.playMusic(17);
            }
            else if (hit(2, 9, 41, "any")==true) { //outside
                teleport(0,  12, 9);
                gp.stopMusic();
                gp.playMusic(0);
            }
            else if (hit(2, 8, 7, "any")==true) { //dungeon f2
                teleport(3,  26, 41);
            }
            else if (hit(3, 26, 41, "any")==true) { //outside
                teleport(2,  8, 7);
                gp.stopMusic();
                gp.playMusic(0);
            }
            else if (hit(3, 25, 27, "any")==true) { //Boss
                skeletonLord();
            }
        }
    }
    public void healingPool(int gameState)
    {
        if (gp.keyH.enterPressed==true)
        {
            gp.gameState=gameState;
            eventMaster.startDialogue(eventMaster, 1);
            gp.player.life=gp.player.maxLife;
            gp.player.mana=gp.player.maxMana;
            gp.aSetter.setMonster();
            gp.saveLoad.save();
        }
    }
    public void teleport(int map, int col, int row)
    {
        gp.currentMap=map;
        gp.gameState=gp.transitionState;
        tempMap=map;
        tempCol=col;
        tempRow=row;

        prevEventX=gp.player.worldX;
        prevEventY=gp.player.worldY;
        gp.playSE(12);
        canTouchEvent=false;
    }
    public boolean hit(int map, int col, int row, String reqDirection) {
        boolean hit=false;
        if (map==gp.currentMap) {
            gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
            gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
            eventRect[map][col][row].x = col * gp.tileSize + eventRect[map][col][row].x;
            eventRect[map][col][row].y = row * gp.tileSize + eventRect[map][col][row].y;
            if (gp.player.solidArea.intersects(eventRect[map][col][row]) && eventRect[map][col][row].eventDone == false) {
                if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                    hit = true;
                    prevEventX = gp.player.worldX;
                    prevEventY = gp.player.worldY;
                }
            }
            gp.player.solidArea.x = gp.player.solidAreaDefaultX;
            gp.player.solidArea.y = gp.player.solidAreaDefaultY;
            eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
            eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
        }
        return hit;
    }
}
