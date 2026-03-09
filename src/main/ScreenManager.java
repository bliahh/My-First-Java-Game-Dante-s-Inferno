package main;
import Entity.Player;
import Camera.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class ScreenManager {

    private JButton nextLevelButton;
    public boolean levelComplete = false;

    private final Color goldColor = new Color(179, 143, 46);
    private final Color darkBlue = new Color(0, 0, 20, 230);
    private int selectedResponse = 0;

    public void drawGameOver(Graphics2D g2, Player player, Camera camera,GamePanel gp,int lvl) {

        g2.setColor(new Color(255, 0, 0, 100));
        g2.fillRect((int)camera.scale*(camera.getX()/16), (int)camera.scale*(camera.getY()/16), 800, 600);


        g2.setFont(new Font("Arial", Font.ITALIC, 32));
        String text = "GAME OVER";

        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getHeight();


        int centerX = (int)camera.scale*(camera.getX()/16) +225;
        int centerY = (int)camera.scale*(camera.getY()/16) + 100;

        int x = centerX - textWidth / 2;
        int y = centerY + fm.getAscent() / 2;


        g2.setColor(Color.BLACK);
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx != 0 || dy != 0) {
                    g2.drawString(text, x + dx, y + dy - 35);
                }
            }
        }


        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y - 35);
        drawAButton(g2,(centerX - textWidth / 2)+20,(int)camera.scale*(camera.getY()/16) + 100,30,20,"RELOAD",0);
        drawAButton(g2,(centerX - textWidth / 2+90),(int)camera.scale*(camera.getY()/16) + 100,30,20,"EXIT",1);

        if (player.keyH.leftPressed) {
            selectedResponse--;
            if (selectedResponse < 0) selectedResponse = 1;
            player.keyH.leftPressed = false;
        } else if (player.keyH.rightPressed) {
            selectedResponse++;
            if (selectedResponse > 1) selectedResponse = 0;
            player.keyH.rightPressed = false;
        }


        if(selectedResponse==0&&player.keyH.attackPressed){
            player.isDead=false;
            player.life=200;
            player.keyH.attackPressed=false;
            gp.isGameOver=false;
            if(lvl==1){
                player.setPlayerSpawn(49, 97);
            }else if(lvl==2){
                player.setPlayerSpawn(15, 29);
            }else{
                player.setPlayerSpawn(21, 70);
            }
        }
        if(selectedResponse==1&&player.keyH.attackPressed){
            System.exit(0);
        }


    }
    void drawAButton( Graphics2D g, int boxX, int boxY, int boxWidth, int boxHeight, String text, int option) {


        if (selectedResponse == option) {
            g.setColor(new Color(240, 240, 220));
        } else {
            g.setColor(darkBlue);
        }
        g.fillRect(boxX , boxY, 60, 20);
        g.setColor(goldColor);
        g.drawRect(boxX, boxY, 60, 20);

        g.setFont(new Font("Arial", Font.PLAIN, boxHeight / 2));
        FontMetrics fm = g.getFontMetrics();
        int textX =10+ boxX + 10 + (boxWidth - 20 - fm.stringWidth(text)) / 2;
        int textY = boxY + (boxHeight + fm.getAscent()) / 2 - 2;
        g.drawString(text, textX, textY);
    }


public void drawLevelComplete(Graphics2D g2, Player player, Camera camera,GamePanel gp) {
    // Fundal verde semi-transparent
    g2.setColor(new Color(0, 255, 0, 100));
    g2.fillRect((int) camera.scale * (camera.getX() / 16), (int) camera.scale * (camera.getY() / 16), 800, 600);

    // Text
    g2.setFont(new Font("Arial", Font.BOLD, 28));
    String text = "LEVEL COMPLETE";

    FontMetrics fm = g2.getFontMetrics();
    int textWidth = fm.stringWidth(text);

    // Centrat în funcție de cameră
    int centerX = (int) camera.scale * (camera.getX() / 16) + 225;
    int centerY = (int) camera.scale * (camera.getY() / 16) + 100;

    int x = centerX - textWidth / 2;
    int y = centerY + fm.getAscent() / 2;

    g2.setColor(Color.BLACK);
    g2.drawString(text, x + 2, y + 2);
    g2.setColor(Color.WHITE);
    g2.drawString(text, x, y);
    g2.setFont(new Font("Arial", Font.BOLD, 12));
    g2.drawString("press 'Enter' to continue",x+45,y+20);



    //drawAButton(g2,(centerX - textWidth / 2)+70,(int)camera.scale*(camera.getY()/16) + 150,200,30,"NEXT LEVEL",0);
    if(player.keyH.attackPressed){
        player.keyH.attackPressed=false;
        player.isTalking=false;
        gp.levelCounter++;
        gp.pausedAfterLevel=false;
        gp.isSpawned=false;


    }
}
    public void drawGameEndScreen(Graphics2D g2, Player player, Camera camera) {

        Color bgColor;
        String titleText;
        String messageText;

        if (player.karma < 0) {
            bgColor = new Color(255, 0, 0, 100);
            titleText = "GAME OVER";

        } else {
            bgColor = new Color(0, 255, 0, 100);
            titleText = "YOU WIN!";

        }


        g2.setColor(bgColor);
        g2.fillRect((int) camera.scale * (camera.getX() / 16), (int) camera.scale * (camera.getY() / 16), 800, 600);


        g2.setFont(new Font("Arial", Font.BOLD, 32));
        FontMetrics fm = g2.getFontMetrics();
        int titleWidth = fm.stringWidth(titleText);
        int centerX = (int) camera.scale * (camera.getX() / 16) + 250;
        int centerY = (int) camera.scale * (camera.getY() / 16) + 100;

        int xTitle = centerX - titleWidth / 2;
        int yTitle = centerY - 40;

       //UMBRAAA
        g2.setColor(Color.BLACK);
        g2.drawString(titleText, xTitle + 2, yTitle + 2);
        g2.setColor(Color.WHITE);
        g2.drawString(titleText, xTitle, yTitle);


    }

}
