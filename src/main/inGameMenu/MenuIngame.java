package main.inGameMenu;

import DatabaseManager.DataBaseSaveSlotManager;
import DatabaseManager.SaveData;
import Entity.Player;
import main.GamePanel;

import java.awt.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MenuIngame {

    private final Color goldColor = new Color(179, 143, 46);
    private final Color darkBlue = new Color(0, 0, 20, 230);
    private int selectedResponse = 0;
    public  boolean loadSaveInterface=false;
    private int saveOnSlot=0;
    private final Lock lock = new ReentrantLock();
    DataBaseSaveSlotManager db = new DataBaseSaveSlotManager();

    public void drawAll(GamePanel gp, Player player, Graphics2D g, int boxX, int boxY) {
        int boxWidth = 768 / 4;
        int boxHeight = 600 / 3;
        int buttonHeight = boxHeight / 6;
        int buttonSpacing = boxHeight / 10;

        g.setColor(darkBlue);
        g.fillRoundRect(boxX, boxY, boxWidth, boxHeight, 20, 20);
        g.setColor(goldColor);
        g.drawRoundRect(boxX, boxY, boxWidth, boxHeight, 20, 20);

        g.setFont(new Font("Arial", Font.BOLD, boxHeight / 12));
        String text = "PAUSE MENU";
        FontMetrics fm = g.getFontMetrics();
        int titleX = boxX + (boxWidth - fm.stringWidth(text)) / 2;
        int titleY = boxY + fm.getAscent() + 10;
        g.drawString(text, titleX, titleY);

        drawAButton(player, g, boxX, boxY + buttonSpacing + 30, boxWidth, buttonHeight, "RESUME", 0);
        drawAButton(player, g, boxX, boxY + 2 * buttonSpacing + 30 + buttonHeight, boxWidth, buttonHeight, "SAVE", 1);
        drawAButton(player, g, boxX, boxY + 3 * buttonSpacing + 30 + 2 * buttonHeight, boxWidth, buttonHeight, "EXIT", 2);

        if (player.keyH.leftPressed) {
            selectedResponse--;
            if (selectedResponse < 0) selectedResponse = 2;
            player.keyH.leftPressed = false;
        } else if (player.keyH.rightPressed) {
            selectedResponse++;
            if (selectedResponse > 2) selectedResponse = 0;
            player.keyH.rightPressed = false;
        }

        if (selectedResponse == 2 && player.keyH.attackPressed) {
            exit();
        }
        if (selectedResponse == 0 && player.keyH.attackPressed) {
            player.keyH.attackPressed = false;
            gp.paused = false;
        }
        if(selectedResponse == 1 && player.keyH.attackPressed){
           //// while (true) {
                saveOnSlot=-16;
                loadSaveInterface=true;
                drawSave(gp, player, g, boxX, boxY);
           // }
        }
    }

    void drawAButton(Player player, Graphics2D g, int boxX, int boxY, int boxWidth, int boxHeight, String text, int option) {
        if (selectedResponse == option) {
            g.setColor(new Color(240, 240, 220));
        } else {
            g.setColor(darkBlue);
        }
        g.fillRoundRect(boxX + 10, boxY, boxWidth - 20, boxHeight, 15, 15);
        g.setColor(goldColor);
        g.drawRoundRect(boxX + 10, boxY, boxWidth - 20, boxHeight, 15, 15);

        g.setFont(new Font("Arial", Font.PLAIN, boxHeight / 2));
        FontMetrics fm = g.getFontMetrics();
        int textX = boxX + 10 + (boxWidth - 20 - fm.stringWidth(text)) / 2;
        int textY = boxY + (boxHeight + fm.getAscent()) / 2 - 2;
        g.drawString(text, textX, textY);
    }


    void drawAButtonRect(Player player, Graphics2D g, int boxX, int boxY, int boxWidth, int boxHeight, String text, int option) {
        if (selectedResponse == option) {
            g.setColor(new Color(240, 240, 220));
        } else {
            g.setColor(darkBlue);
        }
        g.fillRect(boxX + 10, boxY, boxWidth - 20, boxHeight);
        g.setColor(goldColor);
        g.drawRect(boxX + 10, boxY, boxWidth - 20, boxHeight);

        g.setFont(new Font("Arial", Font.PLAIN, boxHeight / 2));
        FontMetrics fm = g.getFontMetrics();
        int textX = boxX + 10 + (boxWidth - 20 - fm.stringWidth(text)) / 2;
        int textY = boxY + (boxHeight + fm.getAscent()) / 2 - 2;
        g.drawString(text, textX, textY);

    }

    void exit() {
        System.exit(0);
    }


    public void drawSave(GamePanel gp, Player player, Graphics2D g, int boxX, int boxY) {
        int boxWidth = 768 / 4;
        int boxHeight = 600 / 3;
        int totalButtons = 8;

        int buttonSpacing = 4;
        int buttonHeight = 15;


        g.setColor(darkBlue);
        g.fillRoundRect(boxX, boxY, boxWidth, boxHeight, 15, 15);
        g.setColor(goldColor);
        g.drawRoundRect(boxX, boxY, boxWidth, boxHeight, 15, 15);

        // Titlu
        int fontSize = 14;
        g.setFont(new Font("Arial", Font.BOLD, fontSize));
        String text = "SAVE MENU";
        FontMetrics fm = g.getFontMetrics();
        int titleX = boxX + (boxWidth - fm.stringWidth(text)) / 2;
        int titleY = boxY + fm.getAscent() + 8;
        g.drawString(text, titleX, titleY);


        int firstButtonY = titleY + 10;
        for (int i = 0; i < 7; i++) {
            int y = firstButtonY + i * (buttonHeight + buttonSpacing);
            drawAButtonRect(player, g, boxX + 10, y, boxWidth - 20, buttonHeight, "SAVE " + (i + 1), i);
        }


        int backButtonY = firstButtonY + 7 * (buttonHeight + buttonSpacing);
        drawAButtonRect(player, g, boxX + 10, backButtonY, boxWidth - 20, buttonHeight, "BACK", 7);


        if (player.keyH.leftPressed) {
            selectedResponse--;
            if (selectedResponse < 0) selectedResponse = 7;
            player.keyH.leftPressed = false;
        } else if (player.keyH.rightPressed) {
            selectedResponse++;
            if (selectedResponse >= 8) selectedResponse = 0;
            player.keyH.rightPressed = false;
        }


        if (player.keyH.attackPressed) {
            if (selectedResponse == 7) {
                loadSaveInterface = false;
                gp.paused = true;
                saveOnSlot=-16;
            } else {
                saveOnSlot = selectedResponse;
                saveGameToSlot(saveOnSlot+1,player,gp);
            }
            player.keyH.attackPressed = false;
        }
    }

public int getSaveOnSlot(){
        return saveOnSlot;
}

    public void saveGameToSlot(int slotId, Player player, GamePanel gp) {
        lock.lock(); // Obține lock-ul înainte de a începe salvarea



        try {
            int level = gp.levelCounter;
            int life = player.life;
            int coins = player.coins;
            int karma = player.karma;
            int x = (int)player.x;
            int y = (int)player.y;
//            if(gp.levelCounter==1){
//                db.saveFieldItems(slotId,gp.itemsOnField);
//                System.out.println("am loadat 1");
//            } else if (gp.levelCounter==2) {
//                db.saveFieldItems(slotId,gp.itemsOnField2);
//                System.out.println("am loadat 2");
//            }else{
//                db.saveFieldItems(slotId,gp.itemsOnField3);
//                System.out.println("am loadat 3");
//            }
            System.out.println("SALVEZ: "+gp.itemsOnField);
            db.saveFieldItems(slotId,gp.itemsOnField);
            db.saveToSlot(slotId, level, life, coins, karma, x, y,player.getDialogueLevel3Counter());
            db.saveCoins(slotId,gp.moneyManager.allMoney);
            db.saveOrcs(slotId,gp.enemies);
            db.saveInventory(slotId,player.inventory);
            db.saveDialogueIndex(slotId, "Mike", gp.mike.getCurrentDialogueIndex());
            db.saveDialogueIndex(slotId, "Son", gp.fiu.getCurrentDialogueIndex());
            db.saveDialogueIndex(slotId, "Sot", gp.sot.getCurrentDialogueIndex());
            db.saveDialogueIndex(slotId, "Sotie", gp.sotie.getCurrentDialogueIndex());
            db.saveDialogueIndex(slotId, "OracolInfern", gp.oracolInfern.getCurrentDialogueIndex());
            db.saveDialogueIndex(slotId, "OracolParadis", gp.oracolParadis.getCurrentDialogueIndex());

        } finally {
            lock.unlock(); // Eliberează lock-ul pentru a permite altor thread-uri să acceseze resursa
        }
    }










//    public void drawSave(GamePanel gp, Player player, Graphics2D g, int boxX, int boxY) {
//        int boxWidth = gp.screenWidth / 4;
//        int boxHeight = 600 / 3;
//        int buttonHeight = boxHeight / 6;
//        int buttonSpacing = boxHeight / 10;
//
//        g.setColor(darkBlue);
//        g.fillRoundRect(boxX, boxY, boxWidth, boxHeight, 20, 20);
//        g.setColor(goldColor);
//        g.drawRoundRect(boxX, boxY, boxWidth, boxHeight, 20, 20);
//
//        g.setFont(new Font("Arial", Font.BOLD, boxHeight / 12));
//        String text = "PAUSE MENU";
//        FontMetrics fm = g.getFontMetrics();
//        int titleX = boxX + (boxWidth - fm.stringWidth(text)) / 2;
//        int titleY = boxY + fm.getAscent() + 10;
//        g.drawString(text, titleX, titleY);
//
//        drawAButton(player, g, boxX, boxY + buttonSpacing + 30, boxWidth, buttonHeight, "Back", 0);
//        drawAButton(player, g, boxX, boxY + 2 * buttonSpacing + 30 + buttonHeight, boxWidth, buttonHeight, "SAVE 1", 1);
//        drawAButton(player, g, boxX, boxY + 3 * buttonSpacing + 30 + 2 * buttonHeight, boxWidth, buttonHeight, "SAVE 2", 2);
//
//        if (player.keyH.leftPressed) {
//            selectedResponse--;
//            if (selectedResponse < 0) selectedResponse = 2;
//            player.keyH.leftPressed = false;
//        } else if (player.keyH.rightPressed) {
//            selectedResponse++;
//            if (selectedResponse > 2) selectedResponse = 0;
//            player.keyH.rightPressed = false;
//        }
//
//
//        if (selectedResponse == 0 && player.keyH.attackPressed) {
//            loadSaveInterface = false;
//            gp.paused=true;
//        }
//        if (selectedResponse > 0 && player.keyH.attackPressed) {
//            player.keyH.attackPressed = false;
//            //   gp.paused = false;
//        }
//    }

//    public void drawSave(GamePanel gp, Player player, Graphics2D g, int boxX, int boxY) {
//        int boxWidth = gp.screenWidth / 3;
//        int boxHeight = 600;
//        int padding = 20;
//
//        int buttonHeight = 50;
//        int buttonSpacing = 20;
//
//        g.setColor(darkBlue);
//        g.fillRoundRect(boxX, boxY, boxWidth, boxHeight, 20, 20);
//        g.setColor(goldColor);
//        g.drawRoundRect(boxX, boxY, boxWidth, boxHeight, 20, 20);
//
//        // Title
//        g.setFont(new Font("Arial", Font.BOLD, 32));
//        String title = "SAVE MENU";
//        FontMetrics fm = g.getFontMetrics();
//        int titleX = boxX + (boxWidth - fm.stringWidth(title)) / 2;
//        int titleY = boxY + padding + fm.getAscent();
//        g.drawString(title, titleX, titleY);
//
//        // Back Button
//        int backButtonY = titleY + padding + 10;
//        drawAButton(player, g, boxX + padding, backButtonY, boxWidth - 2 * padding, buttonHeight, "BACK", 0);
//
//        // Save Slots
//        int firstSlotY = backButtonY + buttonHeight + buttonSpacing * 2;
//        for (int i = 0; i < 3; i++) {
//            int y = firstSlotY + i * (buttonHeight + buttonSpacing);
//            drawAButton(player, g, boxX + padding, y, boxWidth - 2 * padding, buttonHeight, "SAVE SLOT " + (i + 1), i + 1);
//        }
//
//        // Handle input
//        if (player.keyH.upPressed) {
//            selectedResponse--;
//            if (selectedResponse < 0) selectedResponse = 3;
//            player.keyH.upPressed = false;
//        } else if (player.keyH.downPressed) {
//            selectedResponse++;
//            if (selectedResponse > 3) selectedResponse = 0;
//            player.keyH.downPressed = false;
//        }
//
//
//
//
//
//    }



}





//package main.inGameMenu;
//
//import Entity.Player;
//import main.GamePanel;
//
//import java.awt.*;
//
//public class MenuIngame {
//
//    private final Color goldColor = new Color(179, 143, 46);
//    private final Color darkBlue = new Color(0, 0, 20, 230);
//    private int selectedResponse=0;
//
//    public void drawAll(GamePanel gp,Player player,Graphics2D g, int boxX, int boxY){
//        String text="PAUSE MENU";
//
//        g.setColor(darkBlue);
//        g.fillRoundRect(boxX,boxY,150,200,10,10);
//        g.setColor(goldColor);
//        g.drawRoundRect(boxX, boxY, 150, 200, 10, 10);
//        g.setFont(new Font("Arial", Font.ITALIC, 12));
//        g.drawString(text,boxX+35,boxY+30);
//        drawAButton(player,g,boxX+10,boxY+30,"RESUME",0);
//        drawAButton(player,g,boxX+10,boxY+60,"SAVE",1);
//        drawAButton(player,g,boxX+10,boxY+90,"EXIT",2);
//
//        if (player.keyH.leftPressed) {
//            selectedResponse--;
//            if (selectedResponse < 0) selectedResponse = 2;
//            player.keyH.leftPressed = false;
//        } else if (player.keyH.rightPressed) {
//            selectedResponse++;
//            if (selectedResponse > 2) selectedResponse = 0;
//            player.keyH.rightPressed = false;
//        }
//
//        if(selectedResponse==2&&player.keyH.attackPressed){
//            exit();
//        }
//        if(selectedResponse==0&&player.keyH.attackPressed){
//            player.keyH.attackPressed=false;
//            gp.paused=false;
//        }
//
//
//    }
//    void drawAButton(Player player, Graphics2D g, int boxX, int boxY,String text, int option){
//
//
//      //  if(selectedOption==option) {
//
//           if(selectedResponse==option){
//            g.setColor(new Color(240, 240, 220));
//            g.fillRoundRect(boxX, boxY + 20, 130, 20, 10, 10);
//            g.setColor(goldColor);
//            g.drawRoundRect(boxX, boxY + 20, 130, 20, 10, 10);
//            g.setFont(new Font("Arial", Font.ITALIC, 12));
//            g.drawString(text, boxX + 40, boxY + 35);
//        }else {
//               g.setColor(darkBlue);
//               g.fillRoundRect(boxX, boxY + 20, 130, 20, 10, 10);
//               g.setColor(goldColor);
//               g.drawRoundRect(boxX, boxY + 20, 130, 20, 10, 10);
//               g.setFont(new Font("Arial", Font.ITALIC, 12));
//               g.drawString(text, boxX + 40, boxY + 35);
//           }
//
//    }
//    void exit(){
//        System.exit(0);
//    }
//}
