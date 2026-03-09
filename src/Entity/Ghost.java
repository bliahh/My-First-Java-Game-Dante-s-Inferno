package Entity;


import Collision.CollisionCheck;
import Harta.InterfaceHarta;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;


public class Ghost  implements AggroCharacter {
    GamePanel gp;

    BufferedImage tilesetRun;
    public BufferedImage down1,down2,down3,down4;
    public BufferedImage left1,left2,left3,left4;
    public BufferedImage up1,up2,up3,up4,right1,right2,right3,right4;
    public String direction;
    public int walkSpriteNum=1;
    public int spriteCounter=0;

    public double x,y;
    public double speed;


    int moveCooldown = 0;
    int randomDir = 0;
    private int attckcooldown=70;


    public Ghost(GamePanel gp) {
        this.gp = gp;

        x = 200;
        y = 200;
        speed = 0.29;
        direction = "down";


        getPlayerImage();
    }


    @Override
    public void getPlayerImage() {

        try {
            tilesetRun = ImageIO.read(new File("resources/Sprites/gosth.png"));

            int tileSize = 25;


            // UP
            up1 = tilesetRun.getSubimage(3 * tileSize, 0 * tileSize, tileSize, tileSize);
            up2 = tilesetRun.getSubimage(3 * tileSize, 1 * tileSize, tileSize, tileSize);
            up3 = tilesetRun.getSubimage(3 * tileSize, 2 * tileSize, tileSize, tileSize);
            up4 = tilesetRun.getSubimage(3 * tileSize, 3 * tileSize, tileSize, tileSize);


            // LEFT
            left1 = tilesetRun.getSubimage(1 * tileSize, 0 * tileSize, tileSize, tileSize);
            left2 = tilesetRun.getSubimage(1 * tileSize, 1 * tileSize, tileSize, tileSize);
            left3 = tilesetRun.getSubimage(1 * tileSize, 2 * tileSize, tileSize, tileSize);
            left4 = tilesetRun.getSubimage(1 * tileSize, 3 * tileSize, tileSize, tileSize);


            // RIGHT
            right1 = tilesetRun.getSubimage(0 * tileSize, 0 * tileSize, tileSize, tileSize);
            right2 = tilesetRun.getSubimage(0 * tileSize, 1 * tileSize, tileSize, tileSize);
            right3 = tilesetRun.getSubimage(0 * tileSize, 2 * tileSize, tileSize, tileSize);
            right4 = tilesetRun.getSubimage(0 * tileSize, 3 * tileSize, tileSize, tileSize);


            // DOWN
            down1 = tilesetRun.getSubimage(2 * tileSize, 0 * tileSize, tileSize, tileSize);
            down2 = tilesetRun.getSubimage(2 * tileSize, 1 * tileSize, tileSize, tileSize);
            down3 = tilesetRun.getSubimage(2 * tileSize,2 * tileSize, tileSize, tileSize);
            down4 = tilesetRun.getSubimage(2 * tileSize, 3 * tileSize, tileSize, tileSize);

        } catch (Exception e) {
            System.out.println("EROARE GHOST LOAD");
        }

    }

    @Override
    public void setPlayerSpawn(int tileX, int tileY) {
        int tileSize = 16;
        x = tileX * tileSize;
        y = tileY * tileSize;
    }


//    public void getHurt(Entity entity) {
//        entity.life-=20;
//    }

    @Override
    public void update(InterfaceHarta harta, Player player) {

        if (direction == null) {
            direction = "down";
        }

        double dx = player.x - x;
        double dy = player.y - y;
        double distance = Math.sqrt(dx * dx + dy * dy);

        if(attckcooldown>0){
            attckcooldown--;
        }

        if(distance<32&&attckcooldown==0){
            player.life-=5;
            attckcooldown=70;
        }

        if (distance < 240) {
            if (moveCooldown <= 0) {
                randomDir = (int) (Math.random() * 4); // 0-3
                moveCooldown = 20;
            } else {
                moveCooldown--;
            }



            switch (randomDir) {
                case 0:
                    if(0<y-speed){
                    y -= speed;
                    direction = "up";
                    }
                    break;
                case 1:
                    if(34*16>(y+speed)) {
                        y += speed;
                        direction = "down";
                    }
                    break;
                case 2:
                    if(0<x-speed) {
                        x -= speed;
                        direction = "left";
                    }
                    break;
                case 3:
                    if(x+speed<16*29) {
                        x += speed;
                        direction = "right";
                    }
                    break;
                default:
                    direction = "down";
                    break;
            }

            spriteCounter++;
            if (spriteCounter > 11) {
                walkSpriteNum = (walkSpriteNum % 4) + 1;
                spriteCounter = 0;
            }
        }
    }




    @Override
    public void draw (Graphics2D g2) {
        BufferedImage image = null;

        if (direction.equals("up")) {
            // System.out.println("IS IN UP");
            if (walkSpriteNum == 1) image = up1;
            else if (walkSpriteNum == 2) image = up2;
            else if (walkSpriteNum == 3) image = up3;
            else if (walkSpriteNum == 4) image = up4;


        } else if (direction.equals("down")) {
            if (walkSpriteNum == 1) image = down1;
            else if (walkSpriteNum == 2) image = down2;
            else if (walkSpriteNum == 3) image = down3;
            else if (walkSpriteNum == 4) image = down4;


        } else if (direction.equals("left")) {
            if (walkSpriteNum == 1) image = left1;
            else if (walkSpriteNum == 2) image = left2;
            else if (walkSpriteNum == 3) image = left3;
            else if (walkSpriteNum == 4) image = left4;


        } else if (direction.equals("right")) {
            //  System.out.println("AM INTRAT");
            if (walkSpriteNum == 1) image = right1;
            else if (walkSpriteNum == 2) image = right2;
            else if (walkSpriteNum == 3) image = right3;
            else if (walkSpriteNum == 4) image = right4;


        }

        g2.drawImage(image, (int) x, (int) y, 24, 24, null);

    }
}
