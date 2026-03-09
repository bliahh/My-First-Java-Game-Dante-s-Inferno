package Obiecte;

import Entity.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Ring implements ObjInterface {
     public BufferedImage pot1, pot2, pot3, pot4, pot5, pot6, pot7;
    BufferedImage tileset;

    int lifeperc = 68;
    private int spriteCounter = 1;
    private int delay = 0;
    //private boolean potionPicked=false;
    private String id="Ring";
    private int x=4*16,y=67*16;
    private boolean isPicked=false;




    public void setIsPicked(boolean set){
        isPicked=set;
    }

    public void setX(int x){
        this.x=x;
    }

    @Override
    public boolean getIsPicked() {
        return isPicked;
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



    public String getID(){
        return id;
    }
    public Ring() {
        getImg();
    }

    @Override
    public void getImg() {
        try {
            tileset = ImageIO.read(new File("resources/Sprites/Ring.png"));

            int tileSize = 16;
            pot1 = tileset.getSubimage(5 * tileSize, 1*tileSize, tileSize, tileSize);

        } catch (Exception e) {
            System.out.println("EROARE RING LOAD");

        }
    }



    public void updateImg() {
        delay++;
        if (delay > 15) {
            spriteCounter++;
            spriteCounter = (spriteCounter % 6) + 1;
            delay = 0;
        }
    }
    @Override
    public BufferedImage getDefaultImg() {
        return pot1;
    }
    public void draw(Graphics2D g2, int x, int y, Player player) {
        if(player.keyH.pickPressed && Math.abs(player.x - x) < 32 && Math.abs(player.y - y) < 32){
            if (player.inventory.containsKey(this)) {
                player.inventory.put(this, player.inventory.get(this) + 1);
            } else {
                player.inventory.put(this, 1);
            }

            isPicked=true;
            player.keyH.pickPressed=false;
            System.out.println("------------am luat potiune sped!");
        }
        if(!isPicked) {
            BufferedImage image = pot1;
            g2.drawImage(image, this.x, this.y, 16, 16, null);
        }
        //System.out.println("AM DESENAT LA X="+x+" Y= "+y);

    }

}



