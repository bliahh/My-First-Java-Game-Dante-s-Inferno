package main;

import Camera.Camera;
import Entity.Player;
import Obiecte.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Inventory {

    public int selectedResponse = 0;


    public void drawInventory(Player player, Graphics2D g, Camera camera, ArrayList<ObjInterface> itemsOnField) {
        int barWidth = 204;
        int barHeight = 20;
        int barX = camera.getX() / 4 + 10;
        int barY = camera.getY() / 4 + 10;
        int boxX = barX + 120;
        int boxY = barY - 3;
        Object[] items = player.inventory.keySet().toArray();

        g.setColor(Color.BLACK);
        g.drawRect(boxX, boxY, barWidth, barHeight);
        g.setColor(new Color(30, 30, 30, 100));
        g.fillRect(boxX, boxY, barWidth, barHeight);
        for (int i = 0; i < 10; ++i) {
            drawOneSlot(player, g, (boxX + 4) + 20 * i, boxY + 2, i);


            if (i < items.length) {
                ObjInterface item = (ObjInterface) items[i];

                BufferedImage img = item.getDefaultImg();
                g.drawImage(img, (boxX + 4) + 20 * i, boxY + 2, 16, 16, null);


                int quantity = player.inventory.get(item);
                if (!(selectedResponse == i)) {
                    g.setColor(Color.WHITE);
                } else {
                    g.setColor(Color.BLACK);
                }
                g.setFont(new Font("Arial", Font.BOLD, 7));
                g.drawString(String.valueOf(quantity), (boxX + 4) + 20 * i + 11, boxY + 8);

                if(selectedResponse==i){
                        String objectName = item.getID();

                        g.setColor(new Color(255,255,255));
                        g.drawRoundRect(3+boxX+i*20,boxY + 23,40,6,3,3);
                        g.setColor(new Color(30,30,30,200));
                        g.fillRoundRect(3+boxX+i*20,boxY + 23,40,6,3,3);

                    g.setColor(Color.WHITE);
                    g.setFont(new Font("Bold", Font.ITALIC, 6));
                    g.drawString(objectName, 5+boxX+i*20, boxY + 28);
                }


            }
        }
        if (player.keyH.navigateLeftPressed) {
            selectedResponse--;
            if (selectedResponse < 0) selectedResponse = 9;
            player.keyH.navigateLeftPressed = false;
        } else if (player.keyH.navigateRightPressed) {
            selectedResponse++;
            if (selectedResponse > 9) selectedResponse = 0;
            player.keyH.navigateRightPressed = false;
        }

        handlePotionUse(player);

        if (player.keyH.dropPressed) {
            dropSelectedItem(player, itemsOnField);
            player.keyH.dropPressed = false;
        }

    }

    private void handlePotionUse(Player player) {
        if (player.keyH.drinkPressed) {
            Object[] items = player.inventory.keySet().toArray();
            if (selectedResponse < items.length) {
                ObjInterface item = (ObjInterface) items[selectedResponse];

                if (item instanceof PotionInterface) {
                    ((PotionInterface) item).getAffectByPotion(player);

                    //cantitate
                    int current = player.inventory.get(item);
                    if (current > 1) {
                        player.inventory.put(item, current - 1);
                    } else {
                        player.inventory.remove(item);
                    }
                }
            }
            player.keyH.drinkPressed = false;
        }
    }

    public void dropSelectedItem(Player player, ArrayList<ObjInterface> itemsOnField) {
        Object[] items = player.inventory.keySet().toArray();

        if (selectedResponse < items.length) {
            ObjInterface item = (ObjInterface) items[selectedResponse];

            if (!(item instanceof KeyOne || item instanceof KeyTwo || item instanceof KeyThree)) {

                int current = player.inventory.get(item);
                if (current > 1) {
                    player.inventory.put(item, current - 1);
                } else {
                    player.inventory.remove(item);
                }
                    try {
                        ObjInterface dropped = item.getClass().getDeclaredConstructor().newInstance();
                        dropped.setIsPicked(false);
                        dropped.setX((int) player.x + 5);
                        dropped.setY((int) player.y + 5);
                        itemsOnField.add(dropped);
                    } catch (Exception e) {
                        System.out.println("Eroare la clonarea obiectului : " + e.getMessage());
                    }
                }
            }
        }




    public void drawOneSlot(Player player, Graphics2D g, int boxX, int boxY, int option) {
        int boxWidth = 16;
        int boxHeight = 16;


        if (selectedResponse == option) {
            g.setColor(new Color(255, 182, 193, 180));
        } else {
            g.setColor(new Color(0, 0, 0, 180));

        }
        g.fillRect(boxX, boxY, boxWidth, boxHeight);
        g.setColor(Color.BLACK);
        g.drawRect(boxX, boxY, boxWidth, boxHeight);

    }
}
