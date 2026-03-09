package Obiecte;

import Entity.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class KeyOne implements ObjInterface {

    public static BufferedImage pot111;




    private static volatile KeyOne instance=null;




    public BufferedImage pot1;
    private BufferedImage  pot2, pot3, pot4, pot5,pot7, pot6,pot8,pot9,pot10,pot11,pot12,pot13,pot14,pot15,pot16,pot17,pot18,pot19,pot20,pot21,pot22,pot23,pot24;
    private BufferedImage tileset;

    private int spriteCounter = 1;
    private int delay = 0;
    private boolean isPicked = false;
    private final String id = "cheie infern";
    private int x=85*16,y=7*16; // 85 7
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
    private KeyOne() {
        getImg();
        pot11=pot111;
    }

    public static KeyOne getInstance() {
        if (instance == null) {
            instance = new KeyOne();
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
            tileset = ImageIO.read(new File("resources/Sprites/key_32x32_24f.png"));
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
            pot13 = tileset.getSubimage(12* tileSize, 0, tileSize, tileSize);
            pot14 = tileset.getSubimage(13* tileSize, 0, tileSize, tileSize);
            pot15 = tileset.getSubimage(14* tileSize, 0, tileSize, tileSize);
            pot16 = tileset.getSubimage(15* tileSize, 0, tileSize, tileSize);
            pot17 = tileset.getSubimage(16* tileSize, 0, tileSize, tileSize);
            pot18 = tileset.getSubimage(17* tileSize, 0, tileSize, tileSize);
            pot19 = tileset.getSubimage(18* tileSize, 0, tileSize, tileSize);
            pot20 = tileset.getSubimage(19* tileSize, 0, tileSize, tileSize);
            pot21= tileset.getSubimage(20* tileSize, 0, tileSize, tileSize);
            pot22= tileset.getSubimage(21 * tileSize, 0, tileSize, tileSize);
            pot23= tileset.getSubimage(22* tileSize, 0, tileSize, tileSize);
            pot24= tileset.getSubimage(23* tileSize, 0, tileSize, tileSize);



        } catch (Exception e) {
            System.out.println("EROARE KeyOne LOAD");
        }
    }

    public void updateImg() {
        delay++;
        if (delay > 10) {
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
          //  System.out.println("desenez");
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
            else if (spriteCounter == 13) image = pot13;
            else if (spriteCounter == 14) image = pot14;
            else if (spriteCounter == 15) image = pot15;
            else if (spriteCounter == 16) image = pot16;
            else if (spriteCounter == 17) image = pot17;
            else if (spriteCounter == 18) image = pot18;
            else if (spriteCounter == 19) image = pot19;
            else if (spriteCounter == 20) image = pot20;
            else if (spriteCounter == 21) image = pot21;
            else if (spriteCounter == 22) image = pot22;
            else if (spriteCounter == 23) image = pot23;
            else if (spriteCounter == 24) image = pot24;






            g2.drawImage(image, this.x, this.y, 16, 16, null);
        }
    }
}



