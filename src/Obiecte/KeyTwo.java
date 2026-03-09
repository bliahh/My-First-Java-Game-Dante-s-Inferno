package Obiecte;

import Entity.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class KeyTwo implements ObjInterface {

    private static KeyTwo instance;

    private BufferedImage pot1, pot2, pot3, pot4, pot5,pot7, pot6,pot8,pot9,pot10,pot11,pot12,pot13,pot14,pot15,pot16,pot17,pot18,pot19,pot20,pot21,pot22,pot23,pot24;
    private BufferedImage tileset;

    private int spriteCounter = 1;
    private int delay = 0;
    //private boolean keyPicked = false;
    private final String id = "cheie infern";
    private int x=13*16,y=87*16; //13 16
    private boolean isPicked=false;
    @Override
    public boolean getIsPicked() {
        return isPicked;
    }
    public void setIsPicked(boolean set){
        isPicked=set;
    }
    public void setX(int x){
        this.x=x;
    }
    public void setY(int y){
        this.y=y;
    }
    public int getY(){
        return y;
    }
    public int getX(){
        return x;
    }
    private KeyTwo() {

        getImg();
    }

    public static KeyTwo getInstance() {
        if (instance == null) {
            instance = new KeyTwo();
        }
        return instance;
    }

    public static void resetInstance() {
        instance = null;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && this.getClass() == obj.getClass();
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }



    public String getID() {
        return id;
    }

    @Override
    public void getImg() {
        try {
            tileset = ImageIO.read(new File("resources/Sprites/key-blue.png"));
            int tileSize = 32;
            pot1 = tileset.getSubimage(0 * tileSize, 0, tileSize, tileSize);
            pot2 = tileset.getSubimage(1 * tileSize, 0, tileSize, tileSize);
            pot3 = tileset.getSubimage(2 * tileSize, 0, tileSize, tileSize);
            pot4 = tileset.getSubimage(3 * tileSize, 0, tileSize, tileSize);
            pot5 = tileset.getSubimage(4 * tileSize, 0, tileSize, tileSize);
            pot6 = tileset.getSubimage(5 * tileSize, 0, tileSize, tileSize);
            pot7 = tileset.getSubimage(6 * tileSize, 0, tileSize, tileSize);
            pot8 = tileset.getSubimage(7 * tileSize, 0, tileSize, tileSize);
            pot9 = tileset.getSubimage(8 * tileSize, 0, tileSize, tileSize);
            pot10 = tileset.getSubimage(9 * tileSize, 0, tileSize, tileSize);
            pot11 = tileset.getSubimage(10 * tileSize, 0, tileSize, tileSize);
            pot12 = tileset.getSubimage(11* tileSize, 0, tileSize, tileSize);


        } catch (Exception e) {
            System.out.println("EROARE KeyOne LOAD");
        }
    }

    public void updateImg() {
        delay++;
        if (delay > 9) {
            spriteCounter++;
            spriteCounter = (spriteCounter % 24) + 1;
            delay = 0;
        }
    }

    @Override
    public BufferedImage getDefaultImg() {
        return pot1;
    }

    public void draw(Graphics2D g2, int x, int y, Player player) {
        if (player.keyH.pickPressed && Math.abs(player.x - x) < 32 && Math.abs(player.y - y) < 32) {
            isPicked = true;
            player.keyH.pickPressed = false;
            player.inventory.put(this, 1);
            System.out.println("------------am luat potiune sped!");
        }

        if (!isPicked) {
           // System.out.println("desenez");
            BufferedImage image = pot1;
            if (spriteCounter == 1) image = pot1;
            else if (spriteCounter == 2) image = pot2;
            else if (spriteCounter == 3) image = pot3;
            else if (spriteCounter == 4) image = pot4;
            else if (spriteCounter == 5) image = pot5;
            else if (spriteCounter == 6) image = pot6;
            else if (spriteCounter == 7) image = pot7;
            else if (spriteCounter == 8) image = pot8;
            else if (spriteCounter == 9) image = pot9;
            else if (spriteCounter == 10) image = pot10;
            else if (spriteCounter == 11) image = pot11;
            else if (spriteCounter == 12) image = pot12;




//System.out.println("am desenat la "+this.x/16+" "+this.y/16);

            g2.drawImage(image, this.x, this.y, 16, 16, null);
        }
    }
}



