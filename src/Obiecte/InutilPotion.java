package Obiecte;

import Entity.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import static java.lang.Math.sqrt;

public class InutilPotion implements PotionInterface {
    BufferedImage pot1,pot2,pot3,pot4,pot5,pot6,pot7;
    BufferedImage tileset;
    int speedPercentage=60;
    int lifeperc=20;
    int karmaperc=1;
    private int spriteCounter=1;
    private int delay=0;
    public boolean isPicked=false;
    private String id="Inutil Potion";
    private int x=0,y=0;
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

    @Override
    public boolean equals(Object obj) {
        return obj != null && this.getClass() == obj.getClass();
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
    @Override
    public void getAffectByPotion(Player player){
        player.life-=20;
        player.karma+=1;
    }




    public String getID(){
        return id;
    }
    public InutilPotion(){
       this.x=0;
       this.y=0;
       getImg();
   }
    @Override
    public void getImg() {
        try {
            tileset = ImageIO.read(new File("resources/Sprites/BigManaPotion.png"));

            int tileSize = 16;
            pot1 = tileset.getSubimage(4 * tileSize,  0, tileSize, tileSize);
            pot2 = tileset.getSubimage(5 * tileSize, 0, tileSize, tileSize);
            pot3 = tileset.getSubimage(6 * tileSize, 0, tileSize, tileSize);
            pot4 = tileset.getSubimage(7 * tileSize, 0, tileSize, tileSize);
            pot5 = tileset.getSubimage(8 * tileSize, 0, tileSize, tileSize);
            pot6 = tileset.getSubimage(3 * tileSize, 0, tileSize, tileSize);
        } catch (Exception e) {
            System.out.println("EROARE INUTIL POTION LOAD");

        }
    }

    public void updateImg(){
        delay++;
        if(delay>15) {
            spriteCounter++;
            spriteCounter = (spriteCounter % 6) + 1;
            delay = 0;
        }
    }
    @Override
    public BufferedImage getDefaultImg() {
        return pot1;
    }
    @Override
    public void draw(Graphics2D g2, int x, int y,Player player) {

        if(player.keyH.pickPressed && Math.abs(player.x - x) < 32 && Math.abs(player.y - y) < 32){
           isPicked=true;
            if (player.inventory.containsKey(this)) {
                player.inventory.put(this, player.inventory.get(this) + 1);
            } else {
                player.inventory.put(this, 1);
            }
              player.keyH.pickPressed=false;
//           player.keyH.pickPressed=false;
//            System.out.println("------------am luat potiune INUTILAAA!");
       }
        if(!isPicked) {
           BufferedImage image = pot1;
           if (spriteCounter == 1) image = pot1;
           else if (spriteCounter == 2) image = pot2;
           else if (spriteCounter == 3) image = pot3;
           else if (spriteCounter == 4) image = pot4;
           else if (spriteCounter == 5) image = pot5;
           else if (spriteCounter == 6) image = pot6;
          //  System.out.println("desenez potiune intil");
           g2.drawImage(image, this.x, this.y, 16, 16, null);
       }
     //   System.out.println("AM DESENAT LA X="+x+" Y= "+y);
        }


}
