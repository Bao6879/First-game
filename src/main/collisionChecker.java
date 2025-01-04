package main;

import entity.Entity;

public class collisionChecker {
    gamePanel gp;
    public collisionChecker(gamePanel gp) {
        this.gp = gp;
    }
    public void checkTile(Entity entity){
        int entityLeftWorldX=entity.worldX+entity.solidArea.x, entityTopWorldY=entity.worldY+entity.solidArea.y;
        int entityRightWorldX=entity.worldX+entity.solidArea.x+entity.solidArea.width;
        int entityBottomWorldY=entity.worldY+entity.solidArea.y+entity.solidArea.height;
        int entityLeftCol=entityLeftWorldX/gp.tileSize, entityRightCol=entityRightWorldX/gp.tileSize;
        int entityTopRow=entityTopWorldY/gp.tileSize;
        int entityBottomRow=entityBottomWorldY/gp.tileSize, tileNum1, tileNum2;
        String direction=entity.direction;
        if (entity.knockback==true)
        {
            direction=entity.knockbackDirection;
        }
        switch (direction)
        {
            case "up":
                entityTopRow=(entityTopWorldY-entity.speed)/gp.tileSize;
                tileNum1=gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
                tileNum2=gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
                if (gp.tileM.tiles[tileNum1].collision==true || gp.tileM.tiles[tileNum2].collision==true)
                    entity.collsionOn=true;
                break;
            case "down":
                entityBottomRow=(entityBottomWorldY+entity.speed)/gp.tileSize;
                tileNum1=gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
                tileNum2=gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
                if (gp.tileM.tiles[tileNum1].collision==true || gp.tileM.tiles[tileNum2].collision==true)
                    entity.collsionOn=true;
                break;
            case "left":
                entityLeftCol=(entityLeftWorldX-entity.speed)/gp.tileSize;
                tileNum1=gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
                tileNum2=gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
                if (gp.tileM.tiles[tileNum1].collision==true || gp.tileM.tiles[tileNum2].collision==true)
                    entity.collsionOn=true;
                break;
            case "right":
                entityRightCol=(entityRightWorldX+entity.speed)/gp.tileSize;
                tileNum1=gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
                tileNum2=gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
                if (gp.tileM.tiles[tileNum1].collision==true || gp.tileM.tiles[tileNum2].collision==true)
                    entity.collsionOn=true;
                break;
        }
    }
    public int checkObject(Entity entity, boolean player){
        int index=999;
        for (int i=0; i<gp.obj[1].length; i++)
        {
            if (gp.obj[gp.currentMap][i]!=null)
            {
                entity.solidArea.x=entity.worldX+entity.solidArea.x;
                entity.solidArea.y=entity.worldY+entity.solidArea.y;
                gp.obj[gp.currentMap][i].solidArea.x=gp.obj[gp.currentMap][i].worldX+gp.obj[gp.currentMap][i].solidArea.x;
                gp.obj[gp.currentMap][i].solidArea.y=gp.obj[gp.currentMap][i].worldY+gp.obj[gp.currentMap][i].solidArea.y;
                String direction=entity.direction;
                if (entity.knockback==true)
                {
                    direction=entity.knockbackDirection;
                }
                switch (direction)
                {
                    case "up":
                        entity.solidArea.y-=entity.speed;
                        break;
                    case "down":
                        entity.solidArea.y+=entity.speed;
                        break;
                    case "left":
                        entity.solidArea.x-=entity.speed;
                        break;
                    case "right":
                        entity.solidArea.x+=entity.speed;
                        break;
                }
                if (entity.solidArea.intersects(gp.obj[gp.currentMap][i].solidArea)) {
                    if (gp.obj[gp.currentMap][i].collision == true)
                        entity.collsionOn = true;
                    if (player==true)
                        index=i;
                }
                entity.solidArea.x=entity.solidAreaDefaultX;
                entity.solidArea.y=entity.solidAreaDefaultY;
                gp.obj[gp.currentMap][i].solidArea.x=gp.obj[gp.currentMap][i].solidAreaDefaultX;
                gp.obj[gp.currentMap][i].solidArea.y=gp.obj[gp.currentMap][i].solidAreaDefaultY;
            }
        }
        return index;
    }
    public int checkEntity(Entity entity, Entity[][] target)
    {
        int index=999;
        for (int i=0; i<target[0].length; i++)
        {
            if (target[gp.currentMap][i]!=null)
            {
                entity.solidArea.x=entity.worldX+entity.solidArea.x;
                entity.solidArea.y=entity.worldY+entity.solidArea.y;
                target[gp.currentMap][i].solidArea.x=target[gp.currentMap][i].worldX+target[gp.currentMap][i].solidArea.x;
                target[gp.currentMap][i].solidArea.y=target[gp.currentMap][i].worldY+target[gp.currentMap][i].solidArea.y;
                String direction=entity.direction;
                if (entity.knockback==true)
                {
                    direction=entity.knockbackDirection;
                }
                switch (direction)
                {
                    case "up":
                        entity.solidArea.y-=entity.speed;
                        break;
                    case "down":
                        entity.solidArea.y+=entity.speed;
                        break;
                    case "left":
                        entity.solidArea.x-=entity.speed;
                        break;
                    case "right":
                        entity.solidArea.x+=entity.speed;
                        break;
                }
                if (entity.solidArea.intersects(target[gp.currentMap][i].solidArea)) {
                    if (target[gp.currentMap][i]!=entity) {
                        entity.collsionOn = true;
                        index = i;
                    }
                }
                entity.solidArea.x=entity.solidAreaDefaultX;
                entity.solidArea.y=entity.solidAreaDefaultY;
                target[gp.currentMap][i].solidArea.x=target[gp.currentMap][i].solidAreaDefaultX;
                target[gp.currentMap][i].solidArea.y=target[gp.currentMap][i].solidAreaDefaultY;
            }
        }
        return index;
    }
    public boolean checkPlayer(Entity entity)
    {
        boolean contactPlayer=false;
        entity.solidArea.x=entity.worldX+entity.solidArea.x;
        entity.solidArea.y=entity.worldY+entity.solidArea.y;
        gp.player.solidArea.x=gp.player.worldX+gp.player.solidArea.x;
        gp.player.solidArea.y=gp.player.worldY+gp.player.solidArea.y;
        switch (entity.direction)
        {
            case "up":
                entity.solidArea.y-=entity.speed;
                break;
            case "down":
                entity.solidArea.y+=entity.speed;
                break;
            case "left":
                entity.solidArea.x-=entity.speed;
                break;
            case "right":
                entity.solidArea.x+=entity.speed;
                break;
        }
        if (entity.solidArea.intersects(gp.player.solidArea)) {
            entity.collsionOn = true;
            contactPlayer=true;
        }
        entity.solidArea.x=entity.solidAreaDefaultX;
        entity.solidArea.y=entity.solidAreaDefaultY;
        gp.player.solidArea.x=gp.player.solidAreaDefaultX;
        gp.player.solidArea.y=gp.player.solidAreaDefaultY;
        return contactPlayer;
    }
}
