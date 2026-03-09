package Entity;

import java.awt.*;
import java.awt.image.BufferedImage;

abstract public class Entity {

    public BufferedImage down1,down2,down3,down4,down5,down6,down7,down8;
    public BufferedImage left1,left2,left3,left4,left5,left6,left7,left8;
    public BufferedImage up1,up2,up3,up4,up5,up6,up7,up8,right1,right2,right3,right4,right5,right6,right7,right8;
    public String direction;
    public int spriteCounter=0,attackCounter=0,walkCounter=0,hurtCounter=0;
    public int attackSpriteNum=1,walkSpriteNum=1,hurtSpriteNum=1;
    public int life;
    public int attack;
    public double x,y;
    public double speed;
    public Rectangle collisionBox=new Rectangle(21,24,6,9);
    protected boolean isHurt=false;
    protected boolean isAttacking=false;


    abstract public void getHurt(Entity entity);



    public boolean intersects(Entity other) {
        Rectangle rect1 = collisionBox;
        Rectangle rect2 = other.collisionBox;
        return rect1.intersects(rect2);
    }
}
