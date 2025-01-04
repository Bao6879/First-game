package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class keyHandler implements KeyListener, MouseListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, shotKeyPressed;
    gamePanel gp;
    public keyHandler(gamePanel gp) {
        this.gp = gp;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code=e.getKeyCode();
        if (gp.gameState==gp.titleState)
        {
            if (code==KeyEvent.VK_W) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum<0)
                    gp.ui.commandNum=2;
            }
            if (code==KeyEvent.VK_S) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum>2)
                    gp.ui.commandNum=0;
            }
            if (code==KeyEvent.VK_ENTER) {
                if (gp.ui.commandNum==0)
                {
                    gp.gameState=gp.playState;
                    gp.player.setDefaultValues();
                    gp.aSetter.setObject();
                    gp.aSetter.setNPC();
                    gp.aSetter.setMonster();
                    gp.player.setItems();
                    gp.removeTempEntity();
                    gp.bossBattleOn=false;
                    gp.playMusic(0);
                }
                else if (gp.ui.commandNum==1)
                {
                    gp.saveLoad.load();
                    gp.gameState=gp.playState;
                    gp.playMusic(0);
                }
                else if (gp.ui.commandNum==2)
                {
                    System.exit(0);
                }
            }
        }
        else if (gp.gameState==gp.playState)
        {
            if (code==KeyEvent.VK_W) {
                upPressed=true;
            }
            if (code==KeyEvent.VK_P)
            {
                gp.player.invicible=true;
                gp.player.invincibleCounter=-9999999;
                gp.player.strength=100;
                gp.player.attack=gp.player.getAttack();
            }
            if (code==KeyEvent.VK_S) {
                downPressed=true;
            }
            if (code==KeyEvent.VK_A) {
                leftPressed=true;
            }
            if (code==KeyEvent.VK_D) {
                rightPressed=true;
            }
            if (code==KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.pauseState;
            }
            if (code==KeyEvent.VK_ENTER) {
                enterPressed=true;
            }
            if (code==KeyEvent.VK_E) {
                gp.gameState=gp.characterState;
            }
            if (code==KeyEvent.VK_F) {
                shotKeyPressed=true;
            }
            if (code==KeyEvent.VK_M)
            {
                gp.gameState=gp.mapState;
            }
            if (code==KeyEvent.VK_J)
            {
                if (gp.map.miniMap==true)
                    gp.map.miniMap=false;
                else
                    gp.map.miniMap=true;
            }
        }
        else if (gp.gameState==gp.pauseState)
        {
            if (code==KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.playState;
            }
            int maxCommandNum=0;
            switch (gp.ui.subState)
            {
                case 0:
                    maxCommandNum=5;
                    break;
                case 3:
                    maxCommandNum=1;
                    break;
            }
            if (code==KeyEvent.VK_W) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum<0)
                    gp.ui.commandNum=maxCommandNum;
                gp.playSE(9);
            }
            if (code==KeyEvent.VK_S) {
                gp.ui.commandNum++;
                gp.playSE(9);
                if (gp.ui.commandNum>maxCommandNum)
                    gp.ui.commandNum=0;
            }
            if (code==KeyEvent.VK_ENTER)
            {
                enterPressed=true;
            }
            if (code==KeyEvent.VK_A) {
                if (gp.ui.subState==0)
                {
                    if (gp.ui.commandNum==1 && gp.music.volumeScale>0)
                    {
                        gp.music.volumeScale--;
                        gp.music.checkVolume();
                        gp.playSE(9);
                    }
                    if (gp.ui.commandNum==2 && gp.music.volumeScale>0)
                    {
                        gp.se.volumeScale--;
                        gp.playSE(9);
                    }
                }
            }
            if (code==KeyEvent.VK_D) {
                if (gp.ui.subState==0)
                {
                    if (gp.ui.commandNum==1 && gp.music.volumeScale<5)
                    {
                        gp.music.volumeScale++;
                        gp.music.checkVolume();
                        gp.playSE(9);
                    }
                    if (gp.ui.commandNum==2 && gp.music.volumeScale<5)
                    {
                        gp.se.volumeScale++;
                        gp.playSE(9);
                    }
                }
            }
        }
        else if (gp.gameState==gp.cutsceneState)
        {
            if (code==KeyEvent.VK_ENTER)
                enterPressed=true;
        }
        else if (gp.gameState==gp.tradeState)
        {
            if (code==KeyEvent.VK_ENTER) {
                enterPressed=true;
            }
            if (gp.ui.subState==0) {
                if (code==KeyEvent.VK_W) {
                    gp.ui.commandNum--;
                    if (gp.ui.commandNum<0)
                        gp.ui.commandNum=2;
                    gp.playSE(9);
                }
                if (code==KeyEvent.VK_S) {
                    gp.ui.commandNum++;
                    if (gp.ui.commandNum>2)
                        gp.ui.commandNum=0;
                    gp.playSE(9);
                }
            }
            if (gp.ui.subState==1)
            {
                npcInventory(code);
                if (code==KeyEvent.VK_ESCAPE) {
                    gp.ui.subState=0;
                }
            }
            if (gp.ui.subState==2)
            {
                playerInventory(code);
                if (code==KeyEvent.VK_ESCAPE) {
                    gp.ui.subState=0;
                }
            }
        }
        else if (gp.gameState==gp.mapState)
        {
            if (code==KeyEvent.VK_ESCAPE || code==KeyEvent.VK_M) {
                gp.gameState=gp.playState;
            }
        }
        else if (gp.gameState==gp.dialogueState)
        {
            if (code==KeyEvent.VK_ENTER) {
                enterPressed=true;
            }
        }
        else if (gp.gameState==gp.characterState)
        {
            if (code==KeyEvent.VK_E) {
                gp.gameState = gp.playState;
            }
            playerInventory(code);
            if (code==KeyEvent.VK_ENTER)
            {
                gp.player.selectItem();
            }
        }
        else if (gp.gameState==gp.gameOverState)
        {
            if (code==KeyEvent.VK_W) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum<0)
                    gp.ui.commandNum=1;
                gp.playSE(9);
            }
            if (code==KeyEvent.VK_S) {
                gp.ui.commandNum++;
                gp.playSE(9);
                if (gp.ui.commandNum>1)
                    gp.ui.commandNum=0;
            }
            if (code==KeyEvent.VK_ENTER)
            {
                if (gp.ui.commandNum==0)
                {
                    gp.gameState = gp.titleState;
                }
                else
                {
                    gp.gameState=gp.titleState;
                }
            }
        }
    }
    public void playerInventory(int code)
    {
        if (code==KeyEvent.VK_W) {
            gp.playSE(9);
            if (gp.ui.playerSlotRow!=0)
                gp.ui.playerSlotRow--;
        }
        if (code==KeyEvent.VK_S) {
            gp.playSE(9);
            if (gp.ui.playerSlotRow!=3)
                gp.ui.playerSlotRow++;
        }
        if (code==KeyEvent.VK_A) {
            if (gp.ui.playerSlotCol!=0)
                gp.ui.playerSlotCol--;
            gp.playSE(9);
        }
        if (code==KeyEvent.VK_D) {
            if (gp.ui.playerSlotCol!=4)
                gp.ui.playerSlotCol++;
            gp.playSE(9);
        }
    }
    public void npcInventory(int code)
    {
        if (code==KeyEvent.VK_W) {
            gp.playSE(9);
            if (gp.ui.npcSlotRow!=0)
                gp.ui.npcSlotRow--;
        }
        if (code==KeyEvent.VK_S) {
            gp.playSE(9);
            if (gp.ui.npcSlotRow!=3)
                gp.ui.npcSlotRow++;
        }
        if (code==KeyEvent.VK_A) {
            if (gp.ui.npcSlotCol!=0)
                gp.ui.npcSlotCol--;
            gp.playSE(9);
        }
        if (code==KeyEvent.VK_D) {
            if (gp.ui.npcSlotCol!=4)
                gp.ui.npcSlotCol++;
            gp.playSE(9);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code=e.getKeyCode();
        if (code==KeyEvent.VK_W) {
            upPressed=false;
        }
        if (code==KeyEvent.VK_S) {
            downPressed=false;
        }
        if (code==KeyEvent.VK_A) {
            leftPressed=false;
        }
        if (code==KeyEvent.VK_D) {
            rightPressed=false;
        }
        if (code==KeyEvent.VK_F) {
            shotKeyPressed=false;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        gp.playSE(7);
        gp.player.attacking=true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
