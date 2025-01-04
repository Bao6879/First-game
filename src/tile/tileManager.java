package tile;

import main.gamePanel;
import main.utilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class tileManager {
    gamePanel gp;
    public tile[] tiles;
    public int[][][] mapTileNum;
    boolean drawPath=true;
    ArrayList <String> fileName=new ArrayList<>();
    ArrayList <String> collisionStatus=new ArrayList<>();
    public tileManager(gamePanel gp) {
        this.gp = gp;
        InputStream is=getClass().getResourceAsStream("/maps/tiledata.txt");
        BufferedReader br=new BufferedReader(new InputStreamReader(is));
        String line;
        try {
            while ((line=br.readLine())!=null)
            {
                fileName.add(line);
                collisionStatus.add(br.readLine());
            }
            br.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        tiles = new tile[fileName.size()];
        getTileImage();
        is=getClass().getResourceAsStream("/maps/worldmap.txt");
        br=new BufferedReader(new InputStreamReader(is));
        try {
            String line1=br.readLine();
            String maxTile[]=line1.split(" ");
            gp.maxWorldCol=maxTile.length;
            gp.maxWorldRow=maxTile.length;
            mapTileNum=new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
            br.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        loadMap("/maps/worldmap.txt", 0);
        loadMap("/maps/indoor01.txt", 1);
        loadMap("/maps/dungeon01.txt", 2);
        loadMap("/maps/dungeon02.txt", 3);
    }
    public void getTileImage()
    {
        for (int i=0; i<fileName.size(); i++)
        {
            String fName;
            boolean collision=false;
            fName=fileName.get(i);
            if (collisionStatus.get(i).equals("true"))
                collision=true;
            setup(i, fName, collision);
        }
    }
    public void setup(int index, String imagePath, boolean collision)
    {
        utilityTool uTool = new utilityTool();
        try
        {
            tiles[index]=new tile();
            tiles[index].img = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imagePath));
            tiles[index].img=uTool.scaleImage(tiles[index].img, gp.tileSize, gp.tileSize);
            tiles[index].collision=collision;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void loadMap(String path, int map)
    {
        try
        {
            InputStream is = getClass().getResourceAsStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            for (int row=0; row<gp.maxWorldRow; row++)
            {
                String line=br.readLine();
                for (int col=0; col<gp.maxWorldCol; col++)
                {
                    String[] numbers=line.split(" ");
                    int num=Integer.parseInt(numbers[col]);
                    mapTileNum[map][col][row]=num;
                }
            }
            br.close();
        }
        catch(Exception e)
        {

        }
    }
    public void draw(Graphics2D g2)
    {
        for (int row=0; row<gp.maxWorldRow; row++)
        {
            for (int col=0; col<gp.maxWorldCol; col++)
            {
                int tileNum=mapTileNum[gp.currentMap][col][row];
                int worldX=col*gp.tileSize, worldY=row*gp.tileSize;
                int screenX=worldX-gp.player.worldX+gp.player.screenX;
                int screenY=worldY-gp.player.worldY+gp.player.screenY;
                if (worldX+gp.tileSize>=gp.player.worldX-gp.player.screenX &&
                    worldX-gp.tileSize<=gp.player.worldX+gp.player.screenX &&
                    worldY+gp.tileSize>=gp.player.worldY-gp.player.screenY &&
                    worldY-gp.tileSize<=gp.player.worldY+gp.player.screenY)
                    g2.drawImage(tiles[tileNum].img, screenX, screenY, null);
            }
        }
        if (drawPath==true)
        {
            g2.setColor(new Color(255, 0, 0, 70));
            for (int i=0; i<gp.pFinder.pathList.size(); i++)
            {
                int worldX=gp.pFinder.pathList.get(i).col*gp.tileSize, worldY=gp.pFinder.pathList.get(i).row*gp.tileSize;
                int screenX=worldX-gp.player.worldX+gp.player.screenX;
                int screenY=worldY-gp.player.worldY+gp.player.screenY;
                g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);
            }
        }
    }
}
