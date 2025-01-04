package data;

import java.io.Serializable;
import java.util.ArrayList;

public class dataStorage implements Serializable {
    int level, maxLife, life, maxMana, strength, dexterity, exp, nextLevelExp, coin, mana;
    ArrayList<String> itemNames=new ArrayList<>();
    ArrayList<Integer> itemAmount=new ArrayList<>();
    int currentWeaponSlot, currentShieldSlot;

    //data storage
    String mapObjectName[][];
    int mapObjectWorldX[][];
    int mapObjectWorldY[][];
    String mapObjectLootNames[][];
    boolean mapObjectOpened[][];
}
