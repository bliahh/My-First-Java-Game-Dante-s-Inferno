package Harta;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface InterfaceHarta {
     BufferedImage getTileFromTileset(int tileID);
      boolean isCollidable(int x,int y);
     void getMapImage();
    int[][] getCollisionlayer();
     int[][] loadCSVMap(String filePath);

}



