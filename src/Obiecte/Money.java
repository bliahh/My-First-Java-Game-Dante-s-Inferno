package Obiecte;

import Entity.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Money implements MoneyInterface {
    private BufferedImage pot1, pot2, pot3, pot4, pot5, pot6, pot7;
    private BufferedImage tileset;
    public int x, y;
    private int spriteCounter = 1;
    private int delay = 0;
    public boolean picked = false;

    public Money() {
        getImg();
    }

    @Override
    public void getImg() {
        try {
            tileset = ImageIO.read(new File("resources/Sprites/PileOfGold.png"));
            int tileSize = 16;
            pot1 = tileset.getSubimage(0 * tileSize, 0, tileSize, tileSize);
            pot2 = tileset.getSubimage(1 * tileSize, 0, tileSize, tileSize);
            pot3 = tileset.getSubimage(2 * tileSize, 0, tileSize, tileSize);
            pot4 = tileset.getSubimage(3 * tileSize, 0, tileSize, tileSize);
            pot5 = tileset.getSubimage(4 * tileSize, 0, tileSize, tileSize);
            pot6 = tileset.getSubimage(5 * tileSize, 0, tileSize, tileSize);
            pot7 = tileset.getSubimage(6 * tileSize, 0, tileSize, tileSize);
        } catch (Exception e) {
            System.out.println("EROARE Money LOAD");
        }
    }

    public void updateImg() {
        delay++;
        if (delay > 6) {
            spriteCounter++;
            spriteCounter = (spriteCounter % 7) + 1;
            delay = 0;
        }
    }

    public void draw(Graphics2D g2, Player player) {
        if (player.keyH.pickPressed && Math.abs(player.x - x*16) < 32 && Math.abs(player.y - y*16) < 32) {
            picked = true;
            player.coins+=13;
            player.keyH.pickPressed = false;
         //   System.out.println("AM intrate"+(player.x-x));
        }

        if(!picked) {
            BufferedImage image = pot1;
            if (spriteCounter == 1) image = pot1;
            else if (spriteCounter == 2) image = pot2;
            else if (spriteCounter == 3) image = pot3;
            else if (spriteCounter == 4) image = pot4;
            else if (spriteCounter == 5) image = pot5;
            else if (spriteCounter == 6) image = pot6;
            else if (spriteCounter == 7) image = pot7;
           // System.out.println("desenez potiune sped");

            g2.drawImage(image, x*16, y*16, 16, 16, null);
        }
        //System.out.println("AM DESENAT LA X="+x+" Y= "+y);

    }
    }
