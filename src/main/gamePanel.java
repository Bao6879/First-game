package main;

import ai.pathFinder;
import data.saveLoad;
import entity.Entity;
import entity.Player;
import tile.Map;
import tile.tileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class gamePanel extends JPanel implements Runnable {
    //Screen settings
    final int originalTileSize=16;
    final int scale=3;

    public final int tileSize=originalTileSize*scale;
    public final int maxScreenCol=21;
    public final int maxScreenRow=12;
    public final int screenWidth=maxScreenCol*tileSize; //960
    public final int screenHeight=maxScreenRow*tileSize; //528

    //World settings
    public int maxWorldCol;
    public int maxWorldRow;
    public final int maxMap=10;
    public int currentMap=0;

    int FPS=60; //FPS
    //Full screen
    int screenWidth2=screenWidth;
    int screenHeight2=screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;
    public boolean fullScreen=false;

    //System
    public tileManager tileM=new tileManager(this);
    Thread gameThread;
    Sound music=new Sound();
    Sound se=new Sound();
    public collisionChecker cCheck=new collisionChecker(this);
    public assetSetter aSetter=new assetSetter(this);
    public UI ui=new UI(this);
    public keyHandler keyH=new keyHandler(this);
    public eventHandler eHandler=new eventHandler(this);
    Config config=new Config(this);
    public pathFinder pFinder=new pathFinder(this);
    Map map=new Map(this);
    public saveLoad saveLoad=new saveLoad(this);
    public entityGenerator eGenerator=new entityGenerator(this);
    public cutsceneManager csManager=new cutsceneManager(this);

    //Entity and objects
    public Player player=new Player(this, keyH);
    public Entity[][] obj=new Entity[maxMap][20];
    public Entity npc[][]=new Entity[maxMap][10];
    public Entity monster[][]=new Entity[maxMap][20];
    ArrayList <Entity> entityList=new ArrayList<>();
    public Entity[][] projectileList=new Entity[maxMap][20];
    public boolean bossBattleOn=false;

    //Game state
    public int gameState;
    public final int titleState=0;
    public final int playState=1;
    public final int pauseState=2;
    public final int dialogueState=3;
    public final int characterState=4;
    public final int gameOverState=5;
    public final int transitionState=6;
    public final int tradeState=7;
    public final int mapState=8;
    public final int cutsceneState=9;
    public gamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.addMouseListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame()
    {
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        gameState=titleState;
        tempScreen=new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2=(Graphics2D)tempScreen.getGraphics();
        if (fullScreen==true)
            setFullScreen();
    }
    public void setFullScreen()
    {
        GraphicsEnvironment ge=GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd=ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Main.window);
        screenWidth2=Main.window.getWidth();
        screenHeight2=Main.window.getHeight();
    }
    public void startGameThread()
    {
        gameThread=new Thread(this);
        gameThread.start();
    }
    @Override
    public void run() {
        double drawInterval=1000000000/FPS;
        double nextDrawTime=System.nanoTime()+drawInterval;
        while (gameThread.isAlive()) {
            //Update: Char pos, ...
            update();

            //Draw: The screen, ...
            drawToTempScreen();
            drawToScreen();
            try {
                double remainingTime=nextDrawTime-System.nanoTime();
                remainingTime=remainingTime/1000000;
                if (remainingTime<0) {
                    remainingTime=0;
                }
                Thread.sleep((long)remainingTime);
                nextDrawTime+=drawInterval;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void update()
    {
        if (gameState==playState) {
            //Player
            player.update();
            //NPC
            for (int i = 0; i < npc[1].length; i++) {
                if (npc[currentMap][i]!=null)
                {
                    npc[currentMap][i].update();
                }
            }
            //Monster
            for (int i = 0; i < monster[1].length; i++) {
                if (monster[currentMap][i]!=null) {
                    if (monster[currentMap][i].alive == true && monster[currentMap][i].dying==false)
                        monster[currentMap][i].update();
                    if (monster[currentMap][i].alive == false) {
                        monster[currentMap][i].checkDrop();
                        monster[currentMap][i] = null;
                    }
                }
            }
            //Projectile
            for (int i = 0; i < projectileList[1].length; i++) {
                if (projectileList[currentMap][i] !=null) {
                    if (projectileList[currentMap][i].alive == true)
                        projectileList[currentMap][i].update();
                    if (projectileList[currentMap][i].alive == false)
                        projectileList[currentMap][i]=null;
                }
            }
        }
        if (gameState==pauseState)
        {
            //someshit
        }
    }
    public void drawToTempScreen()
    {
        if (gameState==playState || gameState==dialogueState || gameState==characterState || gameState==gameOverState || gameState==tradeState || gameState==cutsceneState) {
            //Tiles
            tileM.draw(g2);

            //Add entity to list
            entityList.add(player);
            for (int i = 0; i < npc[1].length; i++) {
                if (npc[currentMap][i]!=null)
                    entityList.add(npc[currentMap][i]);
            }
            for (int i = 0; i < obj[1].length; i++) {
                if (obj[currentMap][i]!=null)
                    entityList.add(obj[currentMap][i]);
            }
            for (int i = 0; i < monster[1].length; i++) {
                if (monster[currentMap][i]!=null)
                    entityList.add(monster[currentMap][i]);
            }
            for (int i = 0; i < projectileList[1].length; i++) {
                if (projectileList[currentMap][i]!=null) {
                    entityList.add(projectileList[currentMap][i]);
                }
            }
            //Sort
            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    int result=Integer.compare(e1.worldY,e2.worldY);
                    return result;
                }
            });
            //Draw
            for (int i = 0; i < entityList.size(); i++) {
                entityList.get(i).draw(g2);
            }
            entityList.clear();
            map.drawMiniMap(g2);
            csManager.draw(g2);
        }
        else if (gameState==mapState)
        {
            map.drawFullMapScreen(g2);
        }
        //UI
        ui.draw(g2);
    }
    public void drawToScreen()
    {
        Graphics g=getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        g.dispose();
    }
    public void playMusic(int i)
    {
        music.setFile(i);
        music.play();
        music.loop();
    }
    public void stopMusic()
    {
        music.stop();
    }
    public void playSE(int i)
    {
        se.setFile(i);
        se.play();
    }
    public void removeTempEntity()
    {
        for (int i = 0; i < maxMap; i++) {
            for (int j = 0; j < obj[1].length; j++) {
                if (obj[i][j] != null && obj[i][j].temp == true) {
                    obj[i][j] = null;
                }
            }
        }
    }
}
