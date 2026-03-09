package main;

import Camera.Camera;
import Entity.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class LiveHealthBar {
    BufferedImage coinImage;
    LiveHealthBar(){
        getImg();
    }
    void getImg(){
        BufferedImage tileset = null;
        try {
            tileset = ImageIO.read(new File("resources/Sprites/PileOfGold.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int tileSize = 16;
        coinImage = tileset.getSubimage(0 * tileSize, 0, tileSize, tileSize);

    }
    public void drawHealthBarAndKarma(Player player, Graphics2D g, Camera camera) {

        int barWidth = 100;
        int barHeight = 10;
        int barX = camera.getX()/4+10;
        int barY = camera.getY()/4+10;
        int spacing = 10;
        g.setColor(Color.BLACK);
        g.drawRoundRect(barX-5, barY-5,110,50,10,10);
        g.setColor(new Color(30,30,30,100)); //200 162 200
        g.fillRoundRect(barX-5, barY-5,110,50 ,10,10);

        double karmaPercentage =  player.karma / 500.0;
        int karmaWidth = (int) (karmaPercentage * barWidth);

        double healthPercentage =  player.life / 200.0;
        int healthWidth = (int) (healthPercentage * barWidth);

        if(player.life>=0) {
          try{
            g.setColor(Color.BLACK);
            g.drawRoundRect(barX,barY,barWidth,barHeight,10,10);
            g.setColor(new Color(255 - (int) healthPercentage * 254, 0 + (int) (healthPercentage * 250), 0));
            g.fillRoundRect(barX, barY, healthWidth, barHeight, 10, 10);
            g.setFont(new Font("Arial", Font.BOLD, 7));
            g.setColor(Color.BLACK);
            g.drawString("Health: " + player.life, barX + 5, barY+8);
        }catch (IllegalArgumentException e){
              System.out.println("EXCEPTIEE LA DRAW BAR HEALTH: "+e);
          }
        }

        if(karmaPercentage>1){
            karmaPercentage=1;
        }
        if(karmaPercentage<-1){
            karmaPercentage=-1;
        }
        if(karmaWidth>barWidth){
            karmaWidth=barWidth;
        }
        if(karmaWidth<-barWidth){
            karmaWidth=-barWidth;
        }
        //KARMA WIDTH --->>LATIME VIZIBILA A BAREII
        if (player.karma > 0) {
            //g.setColor(new Color(255 - (int) karmaPercentage * 255,0, 0+ (int)(255 * karmaPercentage)));
            g.setColor(Color.BLACK);
            g.drawRoundRect(barX,barY+15,barWidth,barHeight,10,10);
            g.setColor(new Color(180- (int)(180 * karmaPercentage),200- (int)(200 * karmaPercentage), 255- (int)(100 * karmaPercentage)));


            //barX+barwidth/2----> desenez din mijlocc, UMPLU LA DX

            g.fillRoundRect(barX + barWidth / 2, barY + 15, karmaWidth / 2, barHeight, 10, 10);
            g.setFont(new Font("Arial", Font.BOLD, 7));
            g.setColor(Color.BLACK);
            g.drawString("Karma: " + player.karma, barX + 5, barY+23);
        } else if(player.karma<0){
            g.setColor(Color.BLACK);

            g.drawRoundRect(barX,barY+15,barWidth,barHeight,10,10);
            g.setColor(new Color(255+ (int)(100 * karmaPercentage),200+ (int)(200 * karmaPercentage),180+ (int)(180 * karmaPercentage) ));

            //

            g.fillRoundRect(barX + (barWidth / 2)+karmaWidth/2, barY + 15, -karmaWidth / 2, barHeight, 10, 10);
            g.setFont(new Font("Arial", Font.BOLD, 7));
            g.setColor(Color.BLACK);
            g.drawString("Karma: " + player.karma, barX + 5, barY+23);
        }else {
            g.drawRoundRect(barX,barY+15,barWidth,barHeight,10,10);
            g.setColor(new Color(255,255,255));
            //g.fillRect(barX + (barWidth / 2)+(karmaWidth/2)+5, barY + 15, 5, barHeight);
            g.fillRoundRect(barX , barY+15 , barWidth, barHeight, 10, 10);
            g.setFont(new Font("Arial", Font.BOLD, 7));
            g.setColor(Color.BLACK);
            g.drawString("Karma: " + player.karma, barX + 5, barY+23);
        }
        String coinsText = "Coins: " + player.coins;
        g.setFont(new Font("Arial", Font.BOLD, 7));

        int coinsX = barX+3;
        int coinsY = barY + 38;
        g.drawRoundRect(barX,barY+30,barWidth,barHeight,10,10);
        g.setColor(new Color(153, 101, 21));
        g.fillRoundRect(barX,barY+30,barWidth,barHeight,10,10);
        g.setColor(Color.BLACK);
        g.drawString(coinsText, coinsX, coinsY);

        if (coinImage != null) {
            int imgSize = 16;
            int imgX = coinsX + g.getFontMetrics().stringWidth(coinsText) + 3;
            int imgY = coinsY - imgSize + 6; // ajustare verticala ca sa stea aliniata cu textul

            g.drawImage(coinImage, imgX, imgY, imgSize, imgSize, null);
        }

    }
}
