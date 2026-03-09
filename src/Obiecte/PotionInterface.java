package Obiecte;

import Entity.Player;

import java.awt.*;

public interface PotionInterface extends ObjInterface {
    void getAffectByPotion(Player player);
    void draw(Graphics2D g, int x, int y, Player player);
}
