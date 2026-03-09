package DatabaseManager;

import Entity.Orco;
import Obiecte.Money;
import Obiecte.ObjInterface;

import java.util.ArrayList;
import java.util.HashMap;

public class SaveData {
    public int level;
    public int life;
    public int coins;
    public int karma;
    public int posX;
    public int posY;
    public int Sot,Son,Sotie,Oi,Of,Mike;
    public ArrayList<Money> coinsOnField=new ArrayList<>();
    public ArrayList<Orco> enemy=new ArrayList<>();
    public HashMap<ObjInterface,Integer> inventory;
    public ArrayList<ObjInterface> fieldItems;
    public double speed;
    public int lvl;


    public SaveData(int level, int life, int coins, int karma, int posX, int posY,int lvl, ArrayList<Money> coinsOnField, ArrayList<Orco> enemy, HashMap<ObjInterface,Integer> inventory,ArrayList<ObjInterface> fieldItems,int Mike,int Son,int Sot,int Oi,int Op,int Sotie) {
        //pe ce nivel sunt
        this.lvl=lvl;
        //player info

        this.level = level;
        this.life = life;
        this.coins = coins;
        this.karma = karma;
        this.posX = posX;
        this.posY = posY;
        //coins
        this.coinsOnField=coinsOnField;
        //enemy
        this.enemy=enemy;

        this.inventory=inventory;

        this.fieldItems=fieldItems;
        //player dialogue indexx
        this.Son=Son;
        this.Sot=Sot;
        this.Sotie=Sotie;
        this.Oi=Oi;
        this.Of=Op;
        this.Mike=Mike;
        System.out.println("AM IN SAVEDATA: "+this.fieldItems+fieldItems);
    }
    public int getLevel() {
        return level;
    }

    public int getLife() {
        return life;
    }

    public int getCoins() {
        return coins;
    }

    public int getKarma() {
        return karma;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }
}
