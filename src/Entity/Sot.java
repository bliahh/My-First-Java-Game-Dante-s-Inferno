package Entity;




import Collision.CollisionCheck;
import Harta.HartaInfern;
import Harta.InterfaceHarta;

import main.GamePanel;

import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import Camera.Camera;
public class Sot implements NPC{
    GamePanel gp;
    // KeyHandler keyH;
    //  Camera camera = new Camera();
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
    ///private String[] npcDialogues;
    ///private boolean dialogueActive=false;
    // private int currentDialogueIndex=0;
    private String[] npcDialogues = {
            "Nu o mai suport! Mă învinuiește mereu! Zice că nu sunt prezent în viața ei, dar nu este adevărat!",
            "Am auzit ce a zis și este fals! Adevărul e că am fost mereu copleșit de muncă, în speranța de a-i oferi un viitor mai bun și să-i cumpăr mereu ultima poșetă de la Pinko!",
            "Te rog să-i zici că îmi pare rău. Tot ce am făcut a fost pentru ea. Voi încerca să remediez — o să organizez o vacanță spirituală în Harghita!",
            "Și, ca semn de împăcare, te rog să te duci să iei inelul soției mele, ce se află lângă statuia aurie... M-aș duce eu, dar sunt doar o hologramă realizată de un student la AC, la 3 a.m.",
            "Mulțumesc mult, ne-ai ajutat să ne împăcăm."
    };

    public  int currentDialogueIndex = 0;
    static boolean priorityflag=true;
    private boolean dialogueActive = false;

    String[] dialogueOptionGood = {
            "Înțeleg frustrarea. Hai să rezolvăm — voi intermedia eu între voi doi.",
            "Sacrificiul tău e de apreciat, dar în același timp trebuie să te gândești și la ea, oferindu-i mai multă atenție.",
            "Sigur, îi voi spune. Faptul că vrei să te schimbi e ceva bun.",
            "Este un gest drăguț! Cu siguranță.",
            "Cu plăcere. Ai grijă să nu se repete."
    };

    String[] dialogueOptionBad = {
            "Nu te mai victimizează! Dar hai, dacă tot sunt blocat aici, voi încerca să remediez cuplul vostru groaznic.",
            "Nu te cred, este doar o scuză și ești foarte egoist.",
            "Harghita? În locul soției tale, aș divorța.",
            "NU! Îl voi vinde la amanet.",
            "Cu plăcere. În două luni divorțați oricum."
    };


    boolean choosingResponse = false;
    int selectedResponse = 0;
    private boolean hasAlreadyTalked=false;


    public int getCurrentDialogueIndex() {
        return currentDialogueIndex;
    }


    public void setCurrentDialogueIndex(int index) {
        this.currentDialogueIndex = index;
    }

    public Sot(GamePanel gp) {
        this.gp = gp;
        speed = 0.20;
        direction = "down";

        getPlayerImage();
    }

    @Override
    public void getPlayerImage() {
        try {
            tilesetRun = ImageIO.read(new File("resources/Sprites/sottt.png"));

            int tileSize = 64;

            // UP
            up1 = tilesetRun.getSubimage(0 * tileSize, 3 * tileSize, tileSize, tileSize);
            up2 = tilesetRun.getSubimage(1 * tileSize, 3 * tileSize, tileSize, tileSize);
            up3 = tilesetRun.getSubimage(2 * tileSize, 3 * tileSize, tileSize, tileSize);
            up4 = tilesetRun.getSubimage(3 * tileSize, 3 * tileSize, tileSize, tileSize);

            // DOWN
            down1 = tilesetRun.getSubimage(0 * tileSize, 0 * tileSize, tileSize, tileSize);
            down2 = tilesetRun.getSubimage(1 * tileSize, 0 * tileSize, tileSize, tileSize);
            down3 = tilesetRun.getSubimage(2 * tileSize, 0 * tileSize, tileSize, tileSize);
            down4 = tilesetRun.getSubimage(3 * tileSize, 0 * tileSize, tileSize, tileSize);

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
    public void chooseResponse(Player player, Graphics2D g, int boxX, int boxY, String response, int option,int boxWidthh,int boxHeightt){
        int boxWidth =boxWidthh; //latime box
        int boxHeight = boxHeightt; //innaltime box
//        int boxX = (int) player.x - 55;
//        int boxY = (int) player.y - boxHeight + 150;
        int newBoxX=boxX+10;
        int newBoxY=boxY+30;



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



    }
    @Override
    public void speak(Player player, Graphics2D g) {
        if (!hasAlreadyTalked) {
            double dx = player.x - x; //distanta fata de x
            double dy = player.y - y; //distanza fata de y
            double distance = Math.sqrt(dx * dx + dy * dy); //distanta intre 2 pct
            //  player.keyH.nextDialoguePressed=false;
            //CONDITII DE aGGRO
            if (distance <= 32 && player.keyH.speakPressed && !dialogueActive) {
                if (!dialogueActive) { //se activeaza dialog
                    // setImgSpeak(player);
                    dialogueActive = true;
                    // currentDialogueIndex = 0;
                    player.keyH.speakPressed = false;
                    player.keyH.nextDialoguePressed = false;
                }
            }

            if (dialogueActive&&currentDialogueIndex<5) {


                player.isTalking = true; // ca sa opresc jocu
                //  System.out.println("AM INTRAT");
                //DIMENSIUNI BOX
                int boxWidth = 195; //latime box
                int boxHeight = 95; //innaltime box
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
                FontMetrics fm = g.getFontMetrics(); // latimea textului in pixeli
                int lineHeight = fm.getHeight();
                int lineY = boxY + 25; // de unde la ce innaltime incepem scrierea
                int maxTextWidth = boxWidth - 20;

             //   g.drawString("Index: %d".formatted(currentDialogueIndex), boxX + 10, lineY - 25);
                if (priorityflag) {

                    String text = npcDialogues[currentDialogueIndex];
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


                    chooseResponse(player, g, boxX + 5, boxY, dialogueOptionGood[currentDialogueIndex], 0, 70, 60);
                    chooseResponse(player, g, boxX + 95, boxY, dialogueOptionBad[currentDialogueIndex], 1, 70, 60);

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
                        player.keyH.nextDialoguePressed = false;
                        player.keyH.attackPressed = false;

                        if (currentDialogueIndex == 0 || currentDialogueIndex == 2 || currentDialogueIndex==4) {
                            priorityflag = false;
                            Sotie.priorityflag = true;
                            Sotie.currentDialogueIndex++;

                            player.keyH.escPressed = false;
                            dialogueActive = false;
                            player.keyH.nextDialoguePressed = false;
                            // currentDialogueIndex = 0;
                            player.isTalking = false;
                        }



                        currentDialogueIndex++;

                        if (selectedResponse == 1) {
                            player.karma -= 30;
                            System.out.println("KARMA: " + player.karma);
                        } else {
                            player.karma += 30;
                            System.out.println("KARMA: " + player.karma);
                        }

                        selectedResponse = -1;

                    }
                }else{
                    g.drawString("MULTUMESC DE AJUTOR,DU-TE SI VORBESTE CU SOTIA MEA!",boxX + 5, lineY -15);
                }

                if (player.keyH.escPressed || currentDialogueIndex == 5) {
                    player.keyH.escPressed = false;
                    dialogueActive = false;
                    player.keyH.nextDialoguePressed = false;
                    //  currentDialogueIndex = 0;
                    player.isTalking = false;
                }
                if(currentDialogueIndex==5){
                    player.setDialogueLevel3Counter(player.getDialogueLevel3Counter()+1);
                    hasAlreadyTalked = true;
                }


            }
        }
    }

    @Override
    public void update() {
        int tileSize = 16;

        int minY = initialY - tileSize;
        int maxY = initialY + tileSize;
        if((y==minY||y==maxY)&&pause<=69){
            walkSpriteNum=1;
            pause++;

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
            if (walkCounter > 15) {
                walkSpriteNum = (walkSpriteNum % 4) + 1;
                walkCounter = 0;
            }
        }
    }

    @Override
    public  void draw(Graphics2D g2) {
        BufferedImage image = null;

        if (direction.equals("up")) {
            if (walkSpriteNum == 1) image = up1;
            else if (walkSpriteNum == 2) image = up2;
            else if (walkSpriteNum == 3) image = up3;
            else if (walkSpriteNum == 4) image = up4;
        } else if (direction.equals("down")) {
            if (walkSpriteNum == 1) image = down1;
            else if (walkSpriteNum == 2) image = down2;
            else if (walkSpriteNum == 3) image = down3;
            else if (walkSpriteNum == 4) image = down4;

        }

        g2.drawImage(image, (int) x, (int) y, gp.tileSize-24 , gp.tileSize -24, null);

    }
}


