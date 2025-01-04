package main;

import entity.Entity;
import entity.Player;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class UI {
    gamePanel gp;
    Graphics2D g2;
    Font arial_40, arial_80B;
    public boolean messageOn=false;
    public String currentDialogue="";
    public int commandNum=0;
    BufferedImage heart_full, heart_half, heart_blank, crystal_full, crystal_blank, coin;
    ArrayList<String> message=new ArrayList<>();
    ArrayList<Integer> messageCounter=new ArrayList<>();
    public boolean gameFinished=false;
    public int playerSlotCol=0;
    public int playerSlotRow=0;
    public int npcSlotCol=0;
    public int npcSlotRow=0;
    int subState=0;
    int counter=0;
    public Entity npc;
    int charIndex=0;
    String combinedText="";
    public UI(gamePanel gp) {
        this.gp = gp;
        arial_40= new Font("Arial", Font.PLAIN, 40);
        arial_80B= new Font("Arial", Font.BOLD, 80);
        Entity heart=new OBJ_Heart(gp);
        heart_full=heart.image;
        heart_half=heart.image1;
        heart_blank=heart.image2;
        Entity crystal=new OBJ_ManaCrystal(gp);
        crystal_full=crystal.image;
        crystal_blank=crystal.image1;
        Entity coin1=new OBJ_Coin_Bronze(gp);
        coin=coin1.down1;
    }
    public void addMessage(String msg) {
        message.add(msg);
        messageCounter.add(0);
    }
    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(arial_40);
        g2.setColor(Color.white);
        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }
        if (gp.gameState == gp.playState) {
            drawPlayerLife();
            drawMessage();
            drawMonsterLife();
        }
        if (gp.gameState == gp.pauseState) {
            drawOptionScreen();
            drawPlayerLife();
        }
        if (gp.gameState == gp.dialogueState) {
            drawDialogueScreen();
        }
        if (gp.gameState == gp.characterState) {
            drawCharacterScreen();
            drawInventory(gp.player, true);
        }
        if (gp.gameState==gp.gameOverState)
        {
            drawGameOverScreen();
        }
        if (gp.gameState==gp.transitionState)
            drawTransition();
        if (gp.gameState==gp.tradeState)
            drawTradeScreen();
    }
    public void drawTradeScreen()
    {
        switch (subState)
        {
            case 0:
                tradeSelect();
                break;
            case 1:
                tradeBuy();
                break;
            case 2:
                tradeSell();
                break;
        }
        gp.keyH.enterPressed=false;
    }
    public void tradeSelect()
    {
        npc.dialogueSet=0;
        drawDialogueScreen();
        int x=gp.tileSize*15;
        int y=gp.tileSize*4;
        int width=gp.tileSize*3;
        int height=gp.tileSize*4;
        drawSubWindow(x, y, width, height);
        x+=gp.tileSize;
        y+=gp.tileSize;
        g2.drawString("Buy", x, y);
        if (commandNum==0)
        {
            g2.drawString(">", x-24, y);
            if (gp.keyH.enterPressed==true)
                subState=1;
        }
        y+=gp.tileSize;
        g2.drawString("Sell", x, y);
        if (commandNum==1)
        {
            g2.drawString(">", x-24, y);
            if (gp.keyH.enterPressed==true)
                subState=2;
        }
        y+=gp.tileSize;
        g2.drawString("Leave", x, y);
        if (commandNum==2)
        {
            g2.drawString(">", x-24, y);
            if (gp.keyH.enterPressed==true)
            {
                commandNum=0;
                npc.startDialogue(npc, 1);
            }
        }
    }
    public void tradeSell()
    {
        drawInventory(gp.player, true);
        int x=gp.tileSize*2, y=gp.tileSize*9;
        int width=gp.tileSize*6, height=gp.tileSize*2;
        drawSubWindow(x, y, width, height);
        g2.drawString("[ESC] Back", x+24, y+60);

        //Coin
        x=gp.tileSize*12;
        y=gp.tileSize*9;
        width=gp.tileSize*6;
        height=gp.tileSize*2;
        drawSubWindow(x, y, width, height);
        g2.drawString("Yo coins: "+gp.player.coin, x+24, y+60);

        //Price
        int itemIndex=getItemIndex(playerSlotCol, playerSlotRow);
        if (itemIndex<gp.player.inventory.size())
        {
            if (gp.player.inventory.get(itemIndex)!=null)
            {
                x=(int)(gp.tileSize*15.5);
                y=(int)(gp.tileSize*5.5);
                width=(int)(gp.tileSize*2.5);
                height=gp.tileSize;
                drawSubWindow(x, y, width, height);
                g2.drawImage(coin, x+10, y+8, 32, 32, null);
                int price=gp.player.inventory.get(itemIndex).price/2;
                String text=""+price;
                x=getXForAlignToRightText(text, gp.tileSize*18-20);
                g2.drawString(text, x-10, y+34);

                //Sell
                if (gp.keyH.enterPressed==true)
                {
                    Entity item=gp.player.inventory.get(itemIndex);
                    if (item==gp.player.currentShield || item==gp.player.currentWeapon)
                    {
                        commandNum=0;
                        subState=0;
                        npc.startDialogue(npc, 4);
                    }
                    else
                    {
                        if (gp.player.inventory.get(itemIndex).amount>1)
                            gp.player.inventory.get(itemIndex).amount--;
                        else
                           gp.player.inventory.remove(itemIndex);
                        gp.player.coin+=price;
                    }
                }
            }
        }
    }
    public void tradeBuy()
    {
        drawInventory(gp.player, false);
        drawInventory(npc, true);
        //Hint
        int x=gp.tileSize*2, y=gp.tileSize*9;
        int width=gp.tileSize*6, height=gp.tileSize*2;
        drawSubWindow(x, y, width, height);
        g2.drawString("[ESC] Back", x+24, y+60);

        //Coin
        x=gp.tileSize*12;
        y=gp.tileSize*9;
        width=gp.tileSize*6;
        height=gp.tileSize*2;
        drawSubWindow(x, y, width, height);
        g2.drawString("Yo coins: "+gp.player.coin, x+24, y+60);

        //Price
        int itemIndex=getItemIndex(npcSlotCol, npcSlotRow);
        if (itemIndex<npc.inventory.size())
        {
            if (npc.inventory.get(itemIndex)!=null)
            {
                x=(int)(gp.tileSize*5.5);
                y=(int)(gp.tileSize*5.5);
                width=(int)(gp.tileSize*2.5);
                height=gp.tileSize;
                drawSubWindow(x, y, width, height);
                g2.drawImage(coin, x+10, y+8, 32, 32, null);
                int price=npc.inventory.get(itemIndex).price;
                String text=""+price;
                x=getXForAlignToRightText(text, gp.tileSize*8-20);
                g2.drawString(text, x-10, y+34);

                //Buy
                if (gp.keyH.enterPressed==true)
                {
                    if (price>gp.player.coin)
                    {
                        subState=0;
                        npc.startDialogue(npc, 2);
                    }
                    else
                    {
                        if (gp.player.canObtainItem(npc.inventory.get(itemIndex))==true)
                        {
                            gp.player.coin-=price;
                        }
                        else
                        {
                            subState=0;
                            npc.startDialogue(npc, 3);
                        }
                    }
                }
            }
        }
    }
    public void drawTransition()
    {
        counter++;
        g2.setColor(new Color(0, 0, 0, counter*5));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        if (counter==50)
        {
            counter=0;
            gp.gameState=gp.playState;
            gp.currentMap=gp.eHandler.tempMap;
            gp.player.worldX=gp.eHandler.tempCol*gp.tileSize;
            gp.player.worldY=gp.eHandler.tempRow*gp.tileSize;
            gp.eHandler.prevEventX=gp.player.worldX;
            gp.eHandler.prevEventY=gp.player.worldY;
        }
    }
    public void drawGameOverScreen()
    {
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        int x, y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));
        text="Game Over";
        g2.setColor(Color.black);
        x=getXForCenter(text);
        y=gp.tileSize*4;
        g2.drawString(text, x, y);
        g2.setColor(Color.white);
        g2.drawString(text, x-4, y-4);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50f));
        text="Retry";
        x=getXForCenter(text);
        y+=gp.tileSize*4;
        g2.drawString(text, x, y);
        if (commandNum==0)
        {
            g2.drawString(">", x-40, y);
        }
        text="Quit";
        x=getXForCenter(text);
        y+=55;
        g2.drawString(text, x, y);
        if (commandNum==1)
        {
            g2.drawString(">", x-40, y);
        }
    }
    public void drawMonsterLife()
    {
        for (int i=0; i<gp.monster[1].length; i++)
        {
            if (gp.monster[gp.currentMap][i]!=null && gp.monster[gp.currentMap][i].inCamera()==true)
            {
                if (gp.monster[gp.currentMap][i].hpBarOn==true && gp.monster[gp.currentMap][i].boss==false) {
                    double oneScale=(double)gp.tileSize/gp.monster[gp.currentMap][i].maxLife;
                    double hpBar=oneScale*gp.monster[gp.currentMap][i].life;
                    g2.setColor(new Color(35,35,35));
                    g2.fillRect(gp.monster[gp.currentMap][i].getScreenX()-1, gp.monster[gp.currentMap][i].getScreenY()-16, gp.tileSize+2, 12);
                    g2.setColor(new Color(255, 0, 30));
                    g2.fillRect(gp.monster[gp.currentMap][i].getScreenX(), gp.monster[gp.currentMap][i].getScreenY() - 15, (int)hpBar, 10);
                    gp.monster[gp.currentMap][i].hpBarCounter++;
                    if (gp.monster[gp.currentMap][i].hpBarCounter>=600) {
                        gp.monster[gp.currentMap][i].hpBarOn = false;
                        gp.monster[gp.currentMap][i].hpBarCounter = 0;
                    }
                }
                else if (gp.monster[gp.currentMap][i].boss==true) {
                    double oneScale=(double)gp.tileSize*8/gp.monster[gp.currentMap][i].maxLife;
                    double hpBar=oneScale*gp.monster[gp.currentMap][i].life;
                    int x=gp.screenWidth/2-gp.tileSize*4;
                    int y=gp.tileSize*10;
                    g2.setColor(new Color(35,35,35));
                    g2.fillRect(x-1, y-1, gp.tileSize*8+2, 22);
                    g2.setColor(new Color(255, 0, 30));
                    g2.fillRect(x, y, (int)hpBar, 20);
                    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24f));
                    g2.setColor(Color.white);
                    g2.drawString(gp.monster[gp.currentMap][i].name, x+4, y-10);
                }
            }
        }
    }
    public void drawOptionScreen()
    {
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));
        int frameX=gp.tileSize*6, frameY=gp.tileSize;
        int frameWidth=gp.tileSize*10, frameHeight=gp.tileSize*10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);
        switch (subState)
        {
            case 0:
                options_top(frameX, frameY);
                break;
            case 1:
                options_fullScreenNotif(frameX, frameY);
                break;
            case 2:
                options_control(frameX, frameY);
                break;
            case 3:
                options_endGameConfirm(frameX, frameY);
                break;
        }
        gp.keyH.enterPressed=false;
    }
    public void options_top(int frameX, int frameY)
    {
        int textX, textY;
        String text="Options";
        textX=getXForCenter(text);
        textY=frameY+gp.tileSize;
        g2.drawString(text, textX, textY);

        //Full screen on/off
        textX=frameX+gp.tileSize;
        textY+=gp.tileSize*2;
        g2.drawString("Full screen", textX, textY);
        if (commandNum==0) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed==true)
            {
                if (gp.fullScreen==true)
                    gp.fullScreen=false;
                else
                    gp.fullScreen=true;
                subState=1;
            }
        }

        //Music
        textY+=gp.tileSize;
        g2.drawString("Music", textX, textY);
        if (commandNum==1)
            g2.drawString(">", textX-25, textY);
        //SE
        textY+=gp.tileSize;
        g2.drawString("Sound effects", textX, textY);
        if (commandNum==2)
            g2.drawString(">", textX-25, textY);

        //Control
        textY+=gp.tileSize;
        g2.drawString("Controls", textX, textY);
        if (commandNum==3) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed==true)
            {
                subState=2;
                commandNum=0;
            }
        }

        //End game
        textY+=gp.tileSize;
        g2.drawString("End game", textX, textY);
        if (commandNum==4) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed==true)
            {
                subState=3;
                commandNum=0;
            }
        }

        //Back
        textY+=gp.tileSize*2;
        g2.drawString("Back", textX, textY);
        if (commandNum==5) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed==true)
            {
                subState=0;
                gp.gameState=gp.playState;
            }
        }

        //Full screen
        g2.setStroke(new BasicStroke(3));
        textX=frameX+gp.tileSize*6+gp.tileSize/2;
        textY=frameY+gp.tileSize*2+gp.tileSize/2;
        g2.drawRect(textX, textY, 24, 24);
        if (gp.fullScreen==true)
        {
            g2.fillRect(textX, textY, 24, 24);
        }

        //Music
        textY+=gp.tileSize;
        g2.drawRect(textX, textY, 120, gp.tileSize/2);
        int volumeWidth=24*gp.music.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);

        //SE
        textY+=gp.tileSize;
        g2.drawRect(textX, textY, 120, gp.tileSize/2);
        volumeWidth=24*gp.se.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);

        gp.config.saveConfig();
    }
    public void options_fullScreenNotif(int frameX, int frameY)
    {
        int textX=frameX+gp.tileSize;
        int textY=frameY+gp.tileSize*3;
        currentDialogue="Restart to apply";
        g2.drawString(currentDialogue, textX, textY);
        textY=frameY+gp.tileSize*9;
        g2.drawString("Back", textX, textY);
        if (commandNum==0)
        {
            g2.drawString(">", textX-25, textY);
            if (gp.keyH.enterPressed==true)
                subState=0;
        }
    }
    public void options_control(int frameX, int frameY)
    {
        int textX, textY;
        String text="Controls";
        textX=getXForCenter(text);
        textY=frameY+gp.tileSize;
        g2.drawString(text, textX, textY);
        textX=frameX+gp.tileSize;
        textY+=gp.tileSize;
        g2.drawString("Move", textX, textY);
        textY+=gp.tileSize;
        g2.drawString("Confirm", textX, textY);
        textY+=gp.tileSize;
        g2.drawString("Attack", textX, textY);
        textY+=gp.tileSize;
        g2.drawString("Shoot/Cast", textX, textY);
        textY+=gp.tileSize;
        g2.drawString("Inventory", textX, textY);
        textY+=gp.tileSize;
        g2.drawString("Pause", textX, textY);
        textY+=gp.tileSize;
        textX=frameX+gp.tileSize*6;
        textY=frameY+gp.tileSize*2;
        g2.drawString("WASD", textX, textY);
        textY+=gp.tileSize;
        g2.drawString("ENTER", textX, textY);
        textY+=gp.tileSize;
        g2.drawString("Mouse click", textX, textY);
        textY+=gp.tileSize;
        g2.drawString("F", textX, textY);
        textY+=gp.tileSize;
        g2.drawString("E", textX, textY);
        textY+=gp.tileSize;
        g2.drawString("ESC", textX, textY);

        textX=frameX+gp.tileSize;
        textY=frameY+gp.tileSize*9;
        g2.drawString("Back", textX, textY);
        if (commandNum==0)
        {
            g2.drawString(">", textX-25, textY);
            if (gp.keyH.enterPressed==true) {
                subState = 0;
                commandNum = 4;
            }
        }

    }
    public void options_endGameConfirm(int frameX, int frameY)
    {
        int textX, textY;
        textX=frameX+gp.tileSize;
        textY=frameY+gp.tileSize*3;
        currentDialogue="End game?";
        g2.drawString(currentDialogue, textX, textY);
        String text="Yes";
        textX=getXForCenter(text);
        textY+=gp.tileSize*3;
        g2.drawString(text, textX, textY);
        if (commandNum==0)
        {
            g2.drawString(">", textX-25, textY);
            if (gp.keyH.enterPressed==true)
            {
                subState=0;
                gp.stopMusic( );
                gp.gameState=gp.titleState;
            }
        }
        text="No";
        textX=getXForCenter(text);
        textY+=gp.tileSize;
        g2.drawString(text, textX, textY);
        if (commandNum==1)
        {
            g2.drawString(">", textX-25, textY);
            if (gp.keyH.enterPressed==true)
            {
                subState=0;
                commandNum=4;
            }
        }
    }
    public void drawPauseScreen() {
        String text = "PAUSED";
        int x = getXForCenter(text), y = gp.screenHeight / 2;
        g2.drawString(text, x, y);
    }
    public void drawMessage()
    {
        int messageX=gp.tileSize;
        int messageY=gp.tileSize*4;
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 25F));
        for (int i=0; i<message.size(); i++) {
            if (message.get(i)!=null)
            {
                g2.setColor(Color.black);
                g2.drawString(message.get(i), messageX+2, messageY+2);
                g2.setColor(Color.WHITE);
                g2.drawString(message.get(i), messageX, messageY);
                int counter=messageCounter.get(i)+1;
                messageCounter.set(i,counter);
                messageY+=50;
                if (messageCounter.get(i)>180) {
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }
    }
    public void drawPlayerLife()
    {
        int x=gp.tileSize/2;
        int y=gp.tileSize/2;
        int iconSize=32;
        int manaStartY=0;
        for (int i=0; i<gp.player.maxLife/2; i++)
        {
            g2.drawImage(heart_blank, x, y, iconSize, iconSize, null);
            x+=iconSize;
            manaStartY=y+32;
            if (i%8==0 && i!=0)
            {
                x=gp.tileSize/2;
                y+=iconSize;
            }
        }
        x=gp.tileSize/2;
        int i=0;
        while (i<gp.player.life)
        {
            g2.drawImage(heart_half, x, y, iconSize, iconSize, null);
            i++;
            if (i<gp.player.life)
                g2.drawImage(heart_full, x, y, iconSize, iconSize, null);
            i++;
            x+=iconSize;
        }
        x=gp.tileSize/2-5;
        y=(int)(iconSize*2);
        i=0;
        while (i<gp.player.maxMana)
        {
            g2.drawImage(crystal_blank, x, y, iconSize, iconSize, null);
            i++;
            x+=23;
        }
        x=gp.tileSize/2-5;
        y=(int)(iconSize*2);
        i=0;
        while (i<gp.player.mana)
        {
            g2.drawImage(crystal_full, x, y, iconSize, iconSize, null);
            i++;
            x+=23;
        }
    }
    public void drawTitleScreen()
    {
        g2.setColor(Color.black);
        g2.fillRect(0, 0, gp.screenWidth2, gp.screenHeight2);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
        String text="Soul Knight Knockoff";
        int x=getXForCenter(text), y=gp.tileSize*3;
        g2.setColor(Color.gray);
        g2.drawString(text, x+5, y+5);
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        //Player image
        x=gp.screenWidth/2-gp.tileSize;
        y+=gp.tileSize*2;
        g2.drawImage(gp.player.down1, x, y, gp.tileSize*2, gp.tileSize*2, null);
        text="New game";
        x=getXForCenter(text);
        y+=gp.tileSize*3.5;
        g2.drawString(text, x, y);
        if (commandNum==0)
            g2.drawString(">", x-gp.tileSize, y);
        text="Continue";
        x=getXForCenter(text);
        y+=gp.tileSize;
        g2.drawString(text, x, y);
        if (commandNum==1)
            g2.drawString(">", x-gp.tileSize, y);

        text="Quit";
        x=getXForCenter(text);
        y+=gp.tileSize;
        g2.drawString(text, x, y);
        if (commandNum==2)
            g2.drawString(">", x-gp.tileSize, y);

    }
    public void drawDialogueScreen()
    {
        //Window
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20F));
        int x=gp.tileSize*3, y=gp.tileSize/2, width= gp.screenWidth-gp.tileSize*6, height=gp.tileSize*4;
        drawSubWindow(x, y, width, height);
        x+=gp.tileSize;
        y+=gp.tileSize;
        if (npc.dialogues[npc.dialogueSet][npc.dialogueIndex]!=null)
        {
//            currentDialogue=npc.dialogues[npc.dialogueSet][npc.dialogueIndex];
            char characters[]=npc.dialogues[npc.dialogueSet][npc.dialogueIndex].toCharArray();
            if (charIndex<characters.length)
            {
                gp.playSE(15);
                String s=String.valueOf(characters[charIndex]);
                combinedText+=s;
                currentDialogue=combinedText;
                charIndex++;
            }
            if (gp.keyH.enterPressed==true)
            {
                charIndex=0;
                combinedText="";
                if (gp.gameState==gp.dialogueState || gp.gameState==gp.cutsceneState)
                {
                    npc.dialogueIndex++;
                    gp.keyH.enterPressed=false;
                }
            }
        }
        else
        {
            npc.dialogueIndex=0;
            if (gp.gameState==gp.dialogueState)
                gp.gameState=gp.playState;
            else if (gp.gameState==gp.cutsceneState)
                gp.csManager.scenePhase++;
        }
        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y+=40;
        }
    }
    public void drawCharacterScreen()
    {
        final int frameX=gp.tileSize*2, frameY=gp.tileSize/2, frameWidth=gp.tileSize*6, frameHeight=gp.tileSize*10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));
        int textX=frameX+20, textY=frameY+gp.tileSize;
        final int lineHeight=35;
        g2.drawString("Level", textX, textY);
        textY+=lineHeight;
        g2.drawString("Life", textX, textY);
        textY+=lineHeight;
        g2.drawString("Strength", textX, textY);
        textY+=lineHeight;
        g2.drawString("Dexterity", textX, textY);
        textY+=lineHeight;
        g2.drawString("Attack", textX, textY);
        textY+=lineHeight;
        g2.drawString("Defense", textX, textY);
        textY+=lineHeight;
        g2.drawString("Exp", textX, textY);
        textY+=lineHeight;
        g2.drawString("Next Level", textX, textY);
        textY+=lineHeight;
        g2.drawString("Coin", textX, textY);
        textY+=lineHeight+20;
        g2.drawString("Weapon", textX, textY);
        textY+=lineHeight+15;
        g2.drawString("Shield", textX, textY);
        textY+=lineHeight;

        int tailX=frameX+frameWidth-35;
        textY=frameY+gp.tileSize;
        String value;
        value=String.valueOf(gp.player.level);
        textX=getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY+=lineHeight;
        value=String.valueOf(gp.player.life + "/"+gp.player.maxLife);
        textX=getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY+=lineHeight;
        value=String.valueOf(gp.player.strength);
        textX=getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY+=lineHeight;
        value=String.valueOf(gp.player.dexterity);
        textX=getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY+=lineHeight;
        value=String.valueOf(gp.player.attack);
        textX=getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY+=lineHeight;
        value=String.valueOf(gp.player.defense);
        textX=getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY+=lineHeight;
        value=String.valueOf(gp.player.exp);
        textX=getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY+=lineHeight;
        value=String.valueOf(gp.player.nextLevelExp);
        textX=getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY+=lineHeight;
        value=String.valueOf(gp.player.coin);
        textX=getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY+=lineHeight;
        g2.drawImage(gp.player.currentWeapon.down1, tailX-gp.tileSize, textY-14, null);
        textY+=gp.tileSize;
        g2.drawImage(gp.player.currentShield.down1, tailX-gp.tileSize, textY-14, null);
    }
    public void drawInventory(Entity entity, boolean cursor)
    {
        int frameX=0, frameY=0, frameWidth=0, frameHeight=0;
        int slotCol=0, slotRow=0;
        if (entity==gp.player)
        {
            frameX=gp.tileSize*13;
            frameY=gp.tileSize;
            frameWidth=gp.tileSize*6;
            frameHeight=gp.tileSize*5;
            slotCol=playerSlotCol;
            slotRow=playerSlotRow;
        }
        else
        {
            frameX=gp.tileSize*2;
            frameY=gp.tileSize;
            frameWidth=gp.tileSize*6;
            frameHeight=gp.tileSize*5;
            slotCol=npcSlotCol;
            slotRow=npcSlotRow;
        }

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        final int slotXStart=frameX+20, slotYStart=frameY+20;
        int slotX=slotXStart, slotY=slotYStart;
        int slotSize=gp.tileSize+3;
        int cursorX=slotXStart+(slotSize*slotCol), cursorY=slotYStart+(slotSize*slotRow), cursorWidth=slotSize, cursorHeight=slotSize;
        //Items
        for (int i=0; i<entity.inventory.size(); i++)
        {
            if (entity.inventory.get(i)==entity.currentWeapon || entity.inventory.get(i)==entity.currentShield)
            {
                g2.setColor(Color.YELLOW);
                g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
            }
            g2.drawImage(entity.inventory.get(i).down1, slotX, slotY, null);
            if (entity.inventory.get(i).amount>1)
            {
                g2.setFont(g2.getFont().deriveFont(24F));
                int amountX, amountY;
                String s=""+entity.inventory.get(i).amount;
                amountX=getXForAlignToRightText(s, slotX+44);
                amountY=slotY+gp.tileSize;
                g2.setColor(Color.gray);
                g2.drawString(s, amountX, amountY);
                g2.setColor(Color.white);
                g2.drawString(s, amountX-1, amountY-1);
            }
            slotX+=slotSize;
            if (i%5==4)
            {
                slotX=slotXStart;
                slotY+=slotSize;
            }
        }
        //Cursor
        if (cursor==true) {
            g2.setColor(Color.white);
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

            //Description frame
            int dFrameX = frameX, dFrameY = frameY + frameHeight, dFrameWidth = frameWidth, dFrameHeight = gp.tileSize * 3;
            int textX = dFrameX + 20, textY = dFrameY + gp.tileSize;
            g2.setFont(g2.getFont().deriveFont(20F));
            int itemIndex;
            if (entity==gp.player)
                itemIndex = getItemIndex(playerSlotCol, playerSlotRow);
            else
                itemIndex = getItemIndex(npcSlotCol, npcSlotRow);
            if (itemIndex < entity.inventory.size()) {
                if (entity.inventory.get(itemIndex)!=null) {
                    drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);
                    for (String line : entity.inventory.get(itemIndex).description.split("\n")) {
                        g2.drawString(line, textX, textY);
                        textY += 32;
                    }
                }
            }
        }
    }
    public int getItemIndex(int slotCol, int slotRow)
    {
        return slotCol+slotRow*5;
    }
    public int getXForAlignToRightText(String text, int tailX)
    {
        int textlength=(int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        int x=tailX-textlength/2;
        return x;
    }
    public void drawSubWindow(int x, int y, int width, int height)
    {
        Color c=new Color(0, 0, 0, 210);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);
        c=new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
    }
    public int getXForCenter(String text)
    {
        int textlength=(int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        int x=gp.screenWidth/2-textlength/2;
        return x;
    }
}
