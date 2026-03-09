package Camera;

import java.awt.*;
import java.awt.geom.AffineTransform;
import Entity.Player;

public class Camera {

    public double x;           // coordonata x a camerei
    public double y;           // coordonata y a camerei
    public double scale;       // factorul de scalare al camerei

    Player player;

    // DIM HARTA IN PIXELI
    private  int mapWidth ;
    private  int mapHeight;

    public int getX() {
        return (int) x;
    }

    public int getY() {
        return (int) y;
    }

    public Camera(double x, double y, int width, int height, Player player) {

        this.x = x;
        this.y = y;
        this.scale = 4.0;
        this.player = player;
        this.mapWidth=width*16;
        this.mapHeight=height*16;
    }

    public Camera() {
        this.x = 0;
        this.y = 0;
    }


    public void setPosition(double x, double y) {
        //zone in care player poate merge
        // max harta-vizibil harta la DX
        double maxX = mapWidth * scale - 800 * 2;  // limite pe axa X
        double maxY = mapHeight * scale - 600 * 2; // limite pe axa Y

        //se compara x(poz dorita a camerei) cu MAxx x apartine [0,maxX]
        this.x = Math.max(0, Math.min(x, maxX));
        this.y = Math.max(0, Math.min(y, maxY));
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public void update(Player player) {

        //POZ JUCATOR LA MIJLOCUL ECRANULUI
        double newX = player.x * scale - 800;
        double newY = player.y * scale - 376;
        setPosition(newX, newY);
    }

    public void apply(Graphics2D g2d) {
        AffineTransform transform = new AffineTransform(); //transformare geom. pe 2D
        transform.translate(-x, -y); //scena este trasa in opozitie cu camera
        transform.scale(scale, scale);
        g2d.setTransform(transform);
    }


}


