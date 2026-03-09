package Entity;



import Collision.CollisionCheck;
import Harta.HartaInfern;
import Harta.InterfaceHarta;

import Obiecte.KeyOne;
import Obiecte.KeyThree;
import Obiecte.KeyTwo;
import Obiecte.ObjInterface;
import main.GamePanel;

import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import Camera.Camera;


public class OracolInfern implements NPC {
    GamePanel gp;

    BufferedImage tilesetRun;

    public double x, y;
    public double speed;
    public BufferedImage down1, down2, down3, down4, down5, down6, down7, down8;
    public BufferedImage up1, up2, up3, up4, up5, up6, up7, up8;
    public String direction;
    private int walkSpriteNum = 1;
    private int walkCounter=0;
    private int initialY;
    private int pause=0;

    private String[] npcDialogues = {
            "Am fost prin umbre fără sfârșit, Am auzit suflete fără glas. Tu vrei să pleci, dar încă nu-i permis — Răspunde-mi: ce-ai lăsat în urma ta? acum voi testa moralitatea ta",
            "Ai o pâine și doi înfometați. Dacă le-o oferi, vei flămânzi. Dacă o păstrezi, ei vor muri. Ce alegi?",
            "Ți se oferă o comoară imensă, dar este banii văduvelor și ai orfanilor. Nimeni nu va ști dacă iei. Vei întinde mâna?",
            "Poți salva un sat întreg de la foc, dar doar dacă renunți la tot aurul tău. Vei arde cu ei... sau vei pleca bogat?",
            "Prin iad ai trecut. Trei chei ai găsit. Acum, dă-mi ceea ce mi se cuvine și vei păși dincolo.Sau...dacă ești lipsit de răbdare și plin de aur..300 de monede și uit de datorie"
    };
    public int currentDialogueIndex = 0;
    private boolean dialogueActive = false;

    String []dialogueOptionGood={"sigur, hai să începem","Mai bine sufăr eu decât să privesc moartea în ochii altora.","Aurul lor nu va atinge mâinile mele.","Aurul se câștigă din nou. Viețile, nu.","PREDĂ CHEILE"};
    String []dialogueOptionBad={"nu îmi au plăcut rimele, dar hai să o facem","nu ma intereseaza, nu pot sa își cumpere croissanturi?","Dacă nimeni nu vede, înseamnă că e al meu","lasă să arda, oricum îmi era frig.","CORUPE"};

    boolean choosingResponse = false;
    int selectedResponse = 0;
    private boolean hasAlreadyTalked=false;
    private boolean done=false;

    public int getCurrentDialogueIndex() {
        return currentDialogueIndex;
    }


    public void setCurrentDialogueIndex(int index) {
        this.currentDialogueIndex = index;
    }
    public OracolInfern(GamePanel gp) {
        this.gp = gp;
        x = 200;
        y = 200;
        speed = 0.50;
        direction = "down";

        getPlayerImage();
    }

    @Override
    public void getPlayerImage() {
        try {
            tilesetRun = ImageIO.read(new File("resources/Sprites/OracolInf.png"));

            int tileSize = 80;

            // UP
            up1 = tilesetRun.getSubimage(0 * tileSize, 0 * tileSize, tileSize, tileSize);
            up2 = tilesetRun.getSubimage(1 * tileSize, 0 * tileSize, tileSize, tileSize);
            up3 = tilesetRun.getSubimage(2 * tileSize, 0 * tileSize, tileSize, tileSize);
            up4 = tilesetRun.getSubimage(3 * tileSize, 0 * tileSize, tileSize, tileSize);
            up5 = tilesetRun.getSubimage(4 * tileSize, 0 * tileSize, tileSize, tileSize);
            up6 = tilesetRun.getSubimage(5 * tileSize, 0 * tileSize, tileSize, tileSize);
            up7 = tilesetRun.getSubimage(6 * tileSize, 0 * tileSize, tileSize, tileSize);
            up8 = tilesetRun.getSubimage(7 * tileSize, 0 * tileSize, tileSize, tileSize);


            // DOWN
            down1 = tilesetRun.getSubimage(0 * tileSize, 1 * tileSize, tileSize, tileSize);
            down2 = tilesetRun.getSubimage(1 * tileSize, 1 * tileSize, tileSize, tileSize);
            down3 = tilesetRun.getSubimage(2 * tileSize, 1 * tileSize, tileSize, tileSize);
            down4 = tilesetRun.getSubimage(3 * tileSize, 1 * tileSize, tileSize, tileSize);
            down5 = tilesetRun.getSubimage(4 * tileSize, 1 * tileSize, tileSize, tileSize);
            down6 = tilesetRun.getSubimage(5 * tileSize, 1 * tileSize, tileSize, tileSize);
            down7 = tilesetRun.getSubimage(6 * tileSize, 1 * tileSize, tileSize, tileSize);
            down8 = tilesetRun.getSubimage(7 * tileSize, 1 * tileSize, tileSize, tileSize);

        } catch (Exception e) {
            System.out.println("EROARE ORCO LOAD");
        }

    }

    @Override
    public void setPlayerSpawn(int tileX, int tileY) {
        int tileSize = 16;
        x = tileX * tileSize;
        y = tileY * tileSize;
        initialY=tileY * tileSize;
    }

    BufferedImage chosePotion(int opt) {
        BufferedImage img = null, tileset;
        if (opt == 1) {
            try {
                tileset = ImageIO.read(new File("resources/Sprites/PileOfGold.png"));
                int tileSize = 16;
                img = tileset.getSubimage(3 * tileSize, 0, tileSize, tileSize);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (opt == 0) {
            try {
                tileset = ImageIO.read(new File("resources/Sprites/key_32x32_24f.png"));
                int tileSize = 32;
                img = tileset.getSubimage(0, 0, tileSize, tileSize);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
           return img;
    }
    public void chooseResponse(Player player, Graphics2D g, int boxX, int boxY, String response, int option,int boxWidthh,int boxHeightt){
        int boxWidth =boxWidthh; //latime box
        int boxHeight = boxHeightt; //innaltime box
//        int boxX = (int) player.x - 55;
//        int boxY = (int) player.y - boxHeight + 150;
        int newBoxX=boxX+10;
        int newBoxY=boxY+30;



        BufferedImage img = chosePotion(option);

        if (selectedResponse == option) {
            g.setColor(new Color(179, 143, 46, 180));
        } else {
            g.setColor(new Color(0, 0, 46, 180));

        }
        g.fillRoundRect(newBoxX, newBoxY, boxWidth, boxHeight, 20, 20);


        g.setColor(new Color(179, 143, 46, 180));
        g.drawRoundRect(newBoxX, newBoxY, boxWidth, boxHeight, 20, 20);



        if (selectedResponse == option) {
            g.setColor(new Color(0, 0, 46, 180));
        } else {
            g.setColor(new Color(179, 143, 46, 180));


        }
        String text = response;
        FontMetrics fm = g.getFontMetrics();
        int lineHeight = fm.getHeight();
        int lineY = newBoxY;
        int maxTextWidth = boxWidth - 10;

            g.setFont(new Font("Arial", Font.BOLD, 6));

        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();
        for (String word : words) {
            String testLine = line + word + " ";
            int testWidth = fm.stringWidth(testLine);
            if (testWidth > maxTextWidth) {
                g.drawString(line.toString(), newBoxX + 5, lineY + 9);
                line = new StringBuilder(word + " ");
                lineY += lineHeight;
            } else {
                line.append(word + " ");
            }
        }

        g.drawString(line.toString(), newBoxX + 5, lineY + 9);


        if (img != null&&currentDialogueIndex==4) {
            int imgX = newBoxX + (boxWidth - img.getWidth()) / 2;
            int imgY = newBoxY + (boxHeight - img.getHeight()) / 2;
            g.drawImage(img, imgX, imgY+5,16,16, null);
        }

    }
    @Override
    public void speak(Player player, Graphics2D g) {
        double dx = player.x - x; //distanta fata de x
        double dy = player.y - y; //distanza fata de y
        double distance = Math.sqrt(dx * dx + dy * dy); //distanta intre 2 pct
        //  player.keyH.nextDialoguePressed=false;
        //CONDITII DE aGGRO
        if (distance <= 64 && player.keyH.speakPressed&&!dialogueActive) {
            if (!dialogueActive) { //se activeaza dialog

                dialogueActive = true;
               // currentDialogueIndex = 0;
                player.keyH.speakPressed = false;
                player.keyH.nextDialoguePressed=false;
            }
        }

        if (dialogueActive) {
            player.isTalking = true; // ca sa opresc jocu
            //  System.out.println("AM INTRAT");
            //DIMENSIUNI BOX
            int boxWidth = 195; //latime box
            int boxHeight = 80; //innaltime box
            int boxX = (int) player.x - 55;
            int boxY = (int) player.y - boxHeight + 150;

            // Fundal
            g.setColor(new Color(0, 0, 20, 230)); // negru cu opacitate
            g.fillRoundRect(boxX, boxY, boxWidth, boxHeight, 20, 20); // colturi rotunjite

            //bordura
            g.setColor(new Color(179, 143, 46, 180));
            g.drawRoundRect(boxX, boxY, boxWidth, boxHeight, 20, 20);

            // fontu Arial, normal(nu italic etc..)
            g.setFont(new Font("Arial", Font.BOLD, 6));

            g.setColor(new Color(179, 143, 46, 180));

            if(player.countKeys()!=0) {
                if (currentDialogueIndex < 0) currentDialogueIndex = 0;

                String text = npcDialogues[currentDialogueIndex];
                FontMetrics fm = g.getFontMetrics(); // latimea textului in pixeli
                int lineHeight = fm.getHeight();
                int lineY = boxY + 25; // de unde la ce innaltime incepem scrierea
                int maxTextWidth = boxWidth - 20;


                String[] words = text.split(" "); //despartire txt in cuv
                StringBuilder line = new StringBuilder();
                for (String word : words) {
                    String testLine = line + word + " "; //testez daca incap
                    int testWidth = fm.stringWidth(testLine); //latime totatla in pixeli
                    if (testWidth > maxTextWidth) { //testez daca incape
                        g.drawString(line.toString(), boxX + 10, lineY - 15);
                        line = new StringBuilder(word + " "); //incepe linie noua
                        lineY += lineHeight;//trec la "rand nou"
                    } else { //inca nu am ajuns la limita...pur si simplu concatenez
                        line.append(word + " "); //daca cuv incape, il adaug pur si simplu
                    }
                }
                //afisare ultima linie ramasa
                g.drawString(line.toString(), boxX + 10, lineY - 15);


                chooseResponse(player, g, boxX + 5, boxY, dialogueOptionGood[currentDialogueIndex], 0, 70, 40);
                chooseResponse(player, g, boxX + 95, boxY, dialogueOptionBad[currentDialogueIndex], 1, 70, 40);

                if (player.keyH.leftPressed) {
                    selectedResponse--;
                    if (selectedResponse < 0) selectedResponse = 1;
                    player.keyH.leftPressed = false;
                } else if (player.keyH.rightPressed) {
                    selectedResponse++;
                    if (selectedResponse > 1) selectedResponse = 0;
                    player.keyH.rightPressed = false;
                }


                if (player.keyH.nextDialoguePressed && selectedResponse >= 0 && selectedResponse <= 1) {
                    boolean shouldAdvance = player.keyH.nextDialoguePressed;
                    player.keyH.nextDialoguePressed = false;
                    player.keyH.attackPressed = false;
                    currentDialogueIndex++;
                    if (selectedResponse == 1&&!(currentDialogueIndex==5)) {
                        player.karma -= 70;
                        System.out.println("KARMA: " + player.karma);

                    } else if(!(currentDialogueIndex==5)) {
                        player.karma += 70;
                        System.out.println("KARMA: " + player.karma);
                    }

                    if(currentDialogueIndex==5&&!done){
                        if(selectedResponse==0&&shouldAdvance&&(player.countKeys()==3)){
                            System.out.println("am intrat in 1");
                            player.karma+=101;
                            player.level1Passed=true;
                            Iterator<Map.Entry<ObjInterface, Integer>> iterator = player.inventory.entrySet().iterator();
                            while (iterator.hasNext()) {
                                Map.Entry<ObjInterface, Integer> entry = iterator.next();
                                ObjInterface obj = entry.getKey();

                                if (obj instanceof KeyOne ||obj instanceof KeyThree ||obj instanceof KeyTwo) {
                                    iterator.remove();
                                }
                            }

                        }else if(selectedResponse==1&&shouldAdvance&&!done){
                            if(player.coins>=300) {
                              System.out.println("am intrat in 0");
                                player.karma -= 150;
                                player.setCoins(player.coins-300);
                                player.level1Passed=true;
                                done=true;
                            }
                        }
                    }

                   // selectedResponse = -1;

                }
            }else{
                FontMetrics fm = g.getFontMetrics(); // latimea textului in pixeli
                int lineHeight = fm.getHeight();
                int lineY = boxY + 25; // de unde la ce innaltime incepem scrierea
                int maxTextWidth = boxWidth - 20;


                String text="O calator turmentat, de cautare n ai scapat, aduna cele trei chei de pe harta!";
                String[] words = text.split(" "); //despartire txt in cuv
                StringBuilder line = new StringBuilder();
                for (String word : words) {
                    String testLine = line + word + " "; //testez daca incap
                    int testWidth = fm.stringWidth(testLine); //latime totatla in pixeli
                    if (testWidth > maxTextWidth) { //testez daca incape
                        g.drawString(line.toString(), boxX + 10, lineY - 15);
                        line = new StringBuilder(word + " "); //incepe linie noua
                        lineY += lineHeight;//trec la "rand nou"
                    } else { //inca nu am ajuns la limita...pur si simplu concatenez
                        line.append(word + " "); //daca cuv incape, il adaug pur si simplu
                    }
                }
                //afisare ultima linie ramasa
                g.drawString(line.toString(), boxX + 10, lineY - 15);


                // g.drawString("O calator turmentat, de cautare n ai scapat, aduna cele trei chei de pe harta!", boxX + 10,boxY+25);
            }

            if(player.keyH.escPressed){
                player.keyH.escPressed = false;
                dialogueActive = false;
                player.keyH.nextDialoguePressed=false;
                player.isTalking = false;
            }
            if(currentDialogueIndex==5){
                currentDialogueIndex=4;
            }

            if (player.keyH.nextDialoguePressed&&currentDialogueIndex==5) {
                hasAlreadyTalked=true;
                done=true;
                player.keyH.escPressed = false;
                dialogueActive = false;
                player.keyH.nextDialoguePressed=false;
               //aaa currentDialogueIndex = 4;
                player.isTalking = false;
            }

        }
    }






    @Override
    public void update() {
        int tileSize = 16;

        int minY = initialY - 2*tileSize;
        int maxY = initialY + 2*tileSize;
        if((y==minY||y==maxY)&&pause<=69){
            pause++;
            return;
        }else {
            pause = 0;

            if (direction.equals("up")) {
                y -= speed;
                if (y <= minY) {
                    y = minY;
                    direction = "down";
                }
            } else if (direction.equals("down")) {
                y += speed;
                if (y >= maxY) {
                    y = maxY;
                    direction = "up";
                }
            }

            walkCounter++;
            if (walkCounter > 10) {
                walkSpriteNum = (walkSpriteNum % 8) + 1;
                walkCounter = 0;
            }
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        if (direction.equals("up")) {
            if (walkSpriteNum == 1) image = up1;
            else if (walkSpriteNum == 2) image = up2;
            else if (walkSpriteNum == 3) image = up3;
            else if (walkSpriteNum == 4) image = up4;
            else if (walkSpriteNum == 5) image = up5;
            else if (walkSpriteNum == 6) image = up6;
            else if (walkSpriteNum == 7) image = up7;
            else if (walkSpriteNum == 8) image = up8;
        } else if (direction.equals("down")) {
            if (walkSpriteNum == 1) image = down1;
            else if (walkSpriteNum == 2) image = down2;
            else if (walkSpriteNum == 3) image = down3;
            else if (walkSpriteNum == 4) image = down4;
            else if (walkSpriteNum == 5) image = down5;
            else if (walkSpriteNum == 6) image = down6;
            else if (walkSpriteNum == 7) image = down7;
            else if (walkSpriteNum == 8) image = down8;
        }

        g2.drawImage(image, (int) x, (int) y, gp.tileSize * 2, gp.tileSize * 2, null);

    }
}

