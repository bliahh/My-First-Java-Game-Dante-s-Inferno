package Obiecte;

import Entity.Player;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface ObjInterface {
    void getImg();
    public void setX(int x);
    boolean getIsPicked();
    void setIsPicked(boolean set);
    String getID();
    public int getX();
    public int getY();
    public void setY(int y);
    BufferedImage getDefaultImg();
    void draw(Graphics2D g, int i, int i1, Player player);

//    int getWorldX();
//    int getWorldY();
//    void setWorldY(int x);
//    void setWorldX(int x);
}
