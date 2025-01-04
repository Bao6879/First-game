package data;

import entity.Entity;
import main.gamePanel;
import object.*;

import java.io.*;

public class saveLoad {
    gamePanel gp;
    public saveLoad(gamePanel gp) {
        this.gp = gp;
    }
    public void save()
    {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("save.dat"));
            dataStorage ds=new dataStorage();
            ds.level=gp.player.level;
            ds.maxLife=gp.player.maxLife;
            ds.coin=gp.player.coin;
            ds.exp=gp.player.exp;
            ds.life=gp.player.life;
            ds.dexterity=gp.player.dexterity;
            ds.maxMana=gp.player.maxMana;
            ds.nextLevelExp=gp.player.nextLevelExp;
            ds.strength=gp.player.strength;
            ds.mana=gp.player.mana;
            for (int i=0; i<gp.player.inventory.size(); i++) {
                ds.itemNames.add(gp.player.inventory.get(i).name);
                ds.itemAmount.add(gp.player.inventory.get(i).amount);
            }
            ds.currentWeaponSlot=gp.player.getCurrentWeaponSlot();
            ds.currentShieldSlot=gp.player.getCurrentShieldSlot();
            ds.mapObjectName=new String[gp.maxMap][gp.obj[1].length];
            ds.mapObjectWorldX=new int[gp.maxMap][gp.obj[1].length];
            ds.mapObjectWorldY=new int[gp.maxMap][gp.obj[1].length];
            ds.mapObjectLootNames=new String[gp.maxMap][gp.obj[1].length];
            ds.mapObjectOpened=new boolean[gp.maxMap][gp.obj[1].length];
            for (int i=0; i<gp.maxMap; i++) {
                for (int j=0; j<gp.obj[1].length; j++) {
                    if (gp.obj[i][j]==null) {
                        ds.mapObjectName[i][j] = "NA";
                    }
                    else {
                        ds.mapObjectName[i][j] = gp.obj[i][j].name;
                        ds.mapObjectWorldX[i][j]=gp.obj[i][j].worldX;
                        ds.mapObjectWorldY[i][j]=gp.obj[i][j].worldY;
                        if (gp.obj[i][j].loot!=null)
                        {
                            ds.mapObjectLootNames[i][j]=gp.obj[i][j].loot.name;
                        }
                        ds.mapObjectOpened[i][j]=gp.obj[i][j].opened;
                    }
                }
            }
            oos.writeObject(ds);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void load()
    {
        try
        {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("save.dat"));
            dataStorage ds=(dataStorage)ois.readObject();
            gp.player.level=ds.level;
            gp.player.maxLife=ds.maxLife;
            gp.player.coin=ds.coin;
            gp.player.exp=ds.exp;
            gp.player.life=ds.life;
            gp.player.dexterity=ds.dexterity;
            gp.player.maxMana=ds.maxMana;
            gp.player.nextLevelExp=ds.nextLevelExp;
            gp.player.strength=ds.strength;
            gp.player.mana=ds.mana;
            gp.player.inventory.clear();
            for (int i=0; i<ds.itemNames.size(); i++) {
                gp.player.inventory.add(gp.eGenerator.getObject(ds.itemNames.get(i)));
                gp.player.inventory.get(i).amount=ds.itemAmount.get(i);
            }
            gp.player.currentWeapon=gp.player.inventory.get(ds.currentWeaponSlot);
            gp.player.currentShield=gp.player.inventory.get(ds.currentShieldSlot);
            gp.player.getAttack();
            gp.player.getDefense();
            gp.player.getPlayerAttackImage();
            for (int i=0; i<gp.maxMap; i++) {
                for (int j=0; j<gp.obj[1].length; j++) {
                    if (ds.mapObjectName[i][j]==null)
                        continue;
                    else {
                        if (ds.mapObjectName[i][j].equals("NA")) {
                            gp.obj[i][j] = null;
                        } else {
                            gp.obj[i][j] = gp.eGenerator.getObject(ds.mapObjectName[i][j]);
                            gp.obj[i][j].worldX = ds.mapObjectWorldX[i][j];
                            gp.obj[i][j].worldY = ds.mapObjectWorldY[i][j];
                            if (ds.mapObjectLootNames[i][j] != null) {
                                gp.obj[i][j].loot = gp.eGenerator.getObject(ds.mapObjectLootNames[i][j]);
                            }
                            gp.obj[i][j].opened = ds.mapObjectOpened[i][j];
                            if (gp.obj[i][j].opened == true) {
                                gp.obj[i][j].down1 = gp.obj[i][j].image2;
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
