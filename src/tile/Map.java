package tile;

import main.gamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Map extends tileManager{
    gamePanel gp;
    BufferedImage worldMap[];
    public boolean miniMap = false;

    public Map(gamePanel gp) {
        super(gp);
        this.gp = gp;
        createWorldMap();
    }
    public void createWorldMap() {
        worldMap=new BufferedImage[gp.maxMap];
        int worldMapWidth=gp.tileSize*gp.maxWorldCol;
        int worldMapHeight=gp.tileSize*gp.maxWorldRow;
        for (int i=0; i<gp.maxMap; i++) {
            worldMap[i] = new BufferedImage(worldMapWidth, worldMapHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2=(Graphics2D)worldMap[i].createGraphics();
            for (int row=0; row<gp.maxWorldRow; row++) {
                for (int col=0; col<gp.maxWorldCol; col++) {
                    int tileNum=mapTileNum[i][col][row];
                    int x=gp.tileSize*col;
                    int y=gp.tileSize*row;
                    g2.drawImage(tiles[tileNum].img, x, y, null);
                }
            }
            g2.dispose();
        }
    }
    public void drawFullMapScreen(Graphics2D g2) {
        g2.setColor(Color.black);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        int width=500, height=500, x=gp.screenWidth/2-width/2, y=gp.screenHeight/2-height/2;
        g2.drawImage(worldMap[gp.currentMap], x, y, width, height, null);
        double scale=(double) (gp.tileSize*gp.maxWorldCol)/width;
        int playerX=(int)(x+gp.player.worldX/scale), playerY=(int)(y+gp.player.worldY/scale);
        int playerSize=(int)(gp.tileSize/scale);
        g2.drawImage(gp.player.down1, playerX, playerY, playerSize, playerSize, null);
        g2.setFont(g2.getFont().deriveFont(20F));
        g2.setColor(Color.white);
        g2.drawString("Press M or ESC to close", 750, 550);
    }
    public void drawMiniMap(Graphics2D g2) {
        if (miniMap==true) {
            int width=200, height=200, x=gp.screenWidth-width-50, y=50;
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
            g2.drawImage(worldMap[gp.currentMap], x, y, width, height, null);
            double scale=(double) (gp.tileSize*gp.maxWorldCol)/width;
            int playerX=(int)(x+gp.player.worldX/scale), playerY=(int)(y+gp.player.worldY/scale);
            int playerSize=(int)(gp.tileSize/scale);
            g2.drawImage(gp.player.down1, playerX, playerY, playerSize, playerSize, null);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
    }
}
