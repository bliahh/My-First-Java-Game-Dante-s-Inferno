package Entity;




import main.GamePanel;



import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;



public class OracolParadis implements NPC {
    GamePanel gp;
    // KeyHandler keyH;
    //Camera camera = new Camera();
    BufferedImage tilesetRun;

    public double x, y;
    public double speed;
    public BufferedImage down1, down2, down3, down4, down5, down6, down7, down8;
    public BufferedImage up1, up2, up3, up4, up5, up6, up7, up8;
    public String direction;
    private int walkSpriteNum = 1;
    private int walkCounter=0;
    private int initialY,initialX;
    private int pause=0;

    private final String[] npcDialogues = {
          //0
            "Ai pășit prin foc și umbre, Alvie. Dar cheia spre ieșire nu e de metal, ci de suflet. Aici, timpul se frânge. Trecutul, prezentul și viitorul se privesc în oglindă. Acum, răspunde. Nu mie, ci propriei tale conștiințe.",
            //1
            "Vei mai pune egoismul tau in fata la binele altor persoane?",
            //2
            "Vei mai lasa ca latul tau avar sa castige, neglijand relatia cu familia si apropiatii tai?",
            //3
            "Ai avut un drum greu pana acum, si deciziile tale in acest univers iti au mai oferit o sansa de redemptiune",
            //4
            "Ai avut un drum greu pana acum, dar totusi nu ai invatat nimic, te condamn la pierzanie in acest univers!"
    };
    public int currentDialogueIndex = 0;
    private boolean dialogueActive = false;

    String []dialogueOptionGood={
           //0
            "Am inteles lectia! vreau sa ies de aici",
            //1
            "Regret prodund ce am facut cu Mike..nu as mai face asa ceva, prietenia este mai importanta",
            //2
            "banii nu vor cumpara nici o data iubirea celor dragi",
           //3
            "Sunt sigur ca voi schimba lucrurile",
            //4
            "Nu este posibil, nu am gresit nimic!"};
    String []dialogueOptionBad={
            "hai sa terminam farsa asta,nu regret nimic, vreau sa ies de aici",
            "Nu, de ce sa regret? am facut doar ce era corect pentru mine",
            "Avaritie? eu vreau doar sa am cat mai multe averi, atata tot,familia nu este importanta",
            "Inteleg greseliile mele, sunt pregatit sa le repar",
            "Nu te rog!"};


    int selectedResponse = 0;
    public boolean hasAlreadyTalked=false;
    private BufferedImage left1,left2,left3,left4,left5,left6,left7,left8;
    private BufferedImage right1,right2,right3,right4,right5,right6,right7,right8;
    private BufferedImage up9,up10,left9,left10,right9,right10,down10,down9;
    private boolean done=false;
    private boolean jump=false;

    public boolean getHasTalked(){
        return hasAlreadyTalked;
    }

    public int getCurrentDialogueIndex() {
        return currentDialogueIndex;
    }


    public void setCurrentDialogueIndex(int index) {
        this.currentDialogueIndex = index;
    }
    public OracolParadis(GamePanel gp) {
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
            tilesetRun = ImageIO.read(new File("resources/Sprites/SkeletonKing.png"));

            int tileSize = 48;

            // UP
            up1 = tilesetRun.getSubimage(0 * tileSize, 3 * tileSize, tileSize, tileSize);
            up2 = tilesetRun.getSubimage(1 * tileSize, 3 * tileSize, tileSize, tileSize);
            up3 = tilesetRun.getSubimage(2 * tileSize, 3 * tileSize, tileSize, tileSize);
            up4 = tilesetRun.getSubimage(3 * tileSize, 3 * tileSize, tileSize, tileSize);
            up5 = tilesetRun.getSubimage(4 * tileSize, 3 * tileSize, tileSize, tileSize);
            up6 = tilesetRun.getSubimage(5 * tileSize, 3 * tileSize, tileSize, tileSize);
            up7 = tilesetRun.getSubimage(6 * tileSize, 3 * tileSize, tileSize, tileSize);
            up8 = tilesetRun.getSubimage(7 * tileSize, 3 * tileSize, tileSize, tileSize);
            up9 = tilesetRun.getSubimage(8 * tileSize, 3 * tileSize, tileSize, tileSize);
            up10 = tilesetRun.getSubimage(9 * tileSize, 3 * tileSize, tileSize, tileSize);


            left1 = tilesetRun.getSubimage(0 * tileSize, 1 * tileSize, tileSize, tileSize);
            left2 = tilesetRun.getSubimage(1 * tileSize, 1 * tileSize, tileSize, tileSize);
            left3 = tilesetRun.getSubimage(2 * tileSize, 1 * tileSize, tileSize, tileSize);
            left4 = tilesetRun.getSubimage(3 * tileSize, 1 * tileSize, tileSize, tileSize);
            left5 = tilesetRun.getSubimage(4 * tileSize, 1 * tileSize, tileSize, tileSize);
            left6 = tilesetRun.getSubimage(5 * tileSize, 1 * tileSize, tileSize, tileSize);
            left7 = tilesetRun.getSubimage(6 * tileSize, 1 * tileSize, tileSize, tileSize);
            left8 = tilesetRun.getSubimage(7 * tileSize, 1 * tileSize, tileSize, tileSize);
            left9 = tilesetRun.getSubimage(8 * tileSize, 1 * tileSize, tileSize, tileSize);
            left10 = tilesetRun.getSubimage(9 * tileSize, 1 * tileSize, tileSize, tileSize);

            right1 = tilesetRun.getSubimage(0 * tileSize, 2 * tileSize, tileSize, tileSize);
            right2 = tilesetRun.getSubimage(1 * tileSize, 2 * tileSize, tileSize, tileSize);
            right3 = tilesetRun.getSubimage(2 * tileSize, 2 * tileSize, tileSize, tileSize);
            right4 = tilesetRun.getSubimage(3 * tileSize, 2 * tileSize, tileSize, tileSize);
            right5 = tilesetRun.getSubimage(4 * tileSize, 2 * tileSize, tileSize, tileSize);
            right6 = tilesetRun.getSubimage(5 * tileSize, 2 * tileSize, tileSize, tileSize);
            right7 = tilesetRun.getSubimage(6 * tileSize, 2 * tileSize, tileSize, tileSize);
            right8 = tilesetRun.getSubimage(7 * tileSize, 2 * tileSize, tileSize, tileSize);
            right9 = tilesetRun.getSubimage(8 * tileSize, 2 * tileSize, tileSize, tileSize);
            right10 = tilesetRun.getSubimage(9 * tileSize, 2 * tileSize, tileSize, tileSize);


            // DOWN
            down1 = tilesetRun.getSubimage(0 * tileSize, 0 * tileSize, tileSize, tileSize);
            down2 = tilesetRun.getSubimage(1 * tileSize, 0 * tileSize, tileSize, tileSize);
            down3 = tilesetRun.getSubimage(2 * tileSize, 0 * tileSize, tileSize, tileSize);
            down4 = tilesetRun.getSubimage(3 * tileSize, 0 * tileSize, tileSize, tileSize);
            down5 = tilesetRun.getSubimage(4 * tileSize, 0 * tileSize, tileSize, tileSize);
            down6 = tilesetRun.getSubimage(5 * tileSize, 0 * tileSize, tileSize, tileSize);
            down7 = tilesetRun.getSubimage(6 * tileSize, 0 * tileSize, tileSize, tileSize);
            down8 = tilesetRun.getSubimage(7 * tileSize, 0 * tileSize, tileSize, tileSize);
            down9 = tilesetRun.getSubimage(8 * tileSize, 0 * tileSize, tileSize, tileSize);
            down10 = tilesetRun.getSubimage(9 * tileSize, 0 * tileSize, tileSize, tileSize);


        } catch (Exception e) {
            System.out.println("EROARE ORACOLPAR IMG LOAD");
        }

    }

    @Override
    public void setPlayerSpawn(int tileX, int tileY) {
        int tileSize = 16;
        x = tileX * tileSize;
        y = tileY * tileSize;
        initialY=tileY * tileSize;
        initialX=tileX*tileSize;
    }



    public void chooseResponse(Player player, Graphics2D g, int boxX, int boxY, String response, int option,int boxWidthh,int boxHeightt){
        int boxWidth =boxWidthh; //latime box
        int boxHeight = boxHeightt+10; //innaltime box
//        int boxX = (int) player.x - 55;
//        int boxY = (int) player.y - boxHeight + 150;
        int newBoxX=boxX+10;
        int newBoxY=boxY+40;

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
        if(!hasAlreadyTalked) {
            double dx = player.x - x; //distanta fata de x
            double dy = player.y - y; //distanza fata de y
            double distance = Math.sqrt(dx * dx + dy * dy); //distanta intre 2 pct
            //  player.keyH.nextDialoguePressed=false;
            //CONDITII DE aGGRO
            if (distance <= 64 && player.keyH.speakPressed && !dialogueActive) {
                if (!dialogueActive) { //se activeaza dialog

                    dialogueActive = true;
                  //  currentDialogueIndex = 0;
                    player.keyH.speakPressed = false;
                    player.keyH.nextDialoguePressed = false;
                }
            }

            if (dialogueActive&&currentDialogueIndex<5&&!done) {
                player.isTalking = true; // ca sa opresc jocu
                //  System.out.println("AM INTRAT");
                //DIMENSIUNI BOX
                int boxWidth = 195; //latime box
                int boxHeight = 100; //innaltime box
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

                System.out.println(player.getDialogueLevel3Counter());

                if(player.getDialogueLevel3Counter()>=2) {
                    String text = npcDialogues[currentDialogueIndex];
                    FontMetrics fm = g.getFontMetrics(); // latimea textului in pixeli
                    int lineHeight = fm.getHeight();
                    int lineY = boxY + 25; // de unde la ce innaltime incepem scrierea
                    int maxTextWidth = boxWidth - 20;

                    //g.drawString("index %d".formatted(currentDialogueIndex), boxX + 10, lineY + 25);
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
                        player.keyH.nextDialoguePressed = false;
                        player.keyH.attackPressed = false;
                        if (currentDialogueIndex == 2) {
                            if (player.karma < 100) {
                                currentDialogueIndex += 1;
                                jump=true;
                            }
                        }

                        currentDialogueIndex++;
//                        if (currentDialogueIndex == 3&&jump) {
//                            currentDialogueIndex=4;
//                        }
                        System.out.println(currentDialogueIndex);
                        if (selectedResponse == 1 && (currentDialogueIndex != 3 && currentDialogueIndex != 4)) {
                            player.karma -= 70;
                            System.out.println("KARMA: " + player.karma);
                            if (currentDialogueIndex == 5) {
                                player.karma -= 80;
                            }
                        } else if (selectedResponse == 0 && currentDialogueIndex != 3 && currentDialogueIndex != 4) {
                            player.karma += 70;
                            System.out.println("KARMA: " + player.karma);
                        }

                        selectedResponse = -1;

                    }
                }else{
                    String text = "dute si infrunta realitatea! vorbeste cu cine ai ranit!!!";
                    FontMetrics fm = g.getFontMetrics(); // latimea textului in pixeli
                    int lineHeight = fm.getHeight();
                    int lineY = boxY + 25; // de unde la ce innaltime incepem scrierea
                    int maxTextWidth = boxWidth - 20;

                    g.drawString("index %d".formatted(currentDialogueIndex), boxX + 10, lineY + 25);
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





                }

                if (((currentDialogueIndex==4||currentDialogueIndex==5))) {
                    player.isTalking = false;
                    System.out.println("AM INTRAAAAAT");
                    hasAlreadyTalked=true;
                    player.keyH.escPressed = false;
                    dialogueActive = false;
                    player.keyH.nextDialoguePressed = false;
                  //  currentDialogueIndex = 0;

                }

                if(player.keyH.escPressed){
                    player.keyH.escPressed = false;
                    dialogueActive = false;
                    player.keyH.nextDialoguePressed = false;
                    //  currentDialogueIndex = 0;
                    player.isTalking = false;
                }


            }

        }
    }

    @Override
    public void update() {
        int tileSize = 16;

        int minY = initialY;
        int maxY = initialY + 8 * tileSize;
        int minX = initialX;
        int maxX = initialX + 2 * tileSize;


        if ((y == minY && x == maxX || y == minY && x == minX || y == maxY && x == minX || y == maxY && x == maxX) && pause <= 69) {
            pause++;
        } else {
            pause = 0;


            if (direction.equals("down")) {
                y += speed;
                if (y >= maxY) {
                    y = maxY;
                    direction = "right";
                }
            } else if (direction.equals("right")) {
                x += speed;
                if (x >= maxX) {
                    x = maxX;
                    direction = "up";
                }
            } else if (direction.equals("up")) {
                y -= speed;
                if (y <= minY) {
                    y = minY;
                    direction = "left";
                }
            } else if (direction.equals("left")) {
                x -= speed;
                if (x <= minX) {
                    x = minX;
                    direction = "down";
                }
            }


            walkCounter++;
            if (walkCounter > 10) {
                walkSpriteNum = (walkSpriteNum % 6) + 1;
                walkCounter = 0;
            }
        }



    }




//

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
            else if (walkSpriteNum == 9) image = up9;
            else if (walkSpriteNum == 10) image = up10;
        } else if (direction.equals("down")) {
            if (walkSpriteNum == 1) image = down1;
            else if (walkSpriteNum == 2) image = down2;
            else if (walkSpriteNum == 3) image = down3;
            else if (walkSpriteNum == 4) image = down4;
            else if (walkSpriteNum == 5) image = down5;
            else if (walkSpriteNum == 6) image = down6;
            else if (walkSpriteNum == 7) image = down7;
            else if (walkSpriteNum == 8) image = down8;
            else if (walkSpriteNum == 9) image = down9;
            else if (walkSpriteNum == 10) image = down10;
        } else if (direction.equals("left")) {
            if (walkSpriteNum == 1) image = left1;
            else if (walkSpriteNum == 2) image = left2;
            else if (walkSpriteNum == 3) image = left3;
            else if (walkSpriteNum == 4) image = left4;
            else if (walkSpriteNum == 5) image = left5;
            else if (walkSpriteNum == 6) image = left6;
            else if (walkSpriteNum == 7) image = left7;
            else if (walkSpriteNum == 8) image = left8;
            else if (walkSpriteNum == 7) image = left9;
            else if (walkSpriteNum == 8) image = left10;
        } else if (direction.equals("right")) {
            if (walkSpriteNum == 1) image = right1;
            else if (walkSpriteNum == 2) image = right2;
            else if (walkSpriteNum == 3) image = right3;
            else if (walkSpriteNum == 4) image = right4;
            else if (walkSpriteNum == 5) image = right5;
            else if (walkSpriteNum == 6) image = right6;
            else if (walkSpriteNum == 7) image = right7;
            else if (walkSpriteNum == 8) image = right8;
            else if (walkSpriteNum == 9) image = right9;
            else if (walkSpriteNum == 10) image = right10;
        }


        g2.drawImage(image, (int) x, (int) y, gp.tileSize, gp.tileSize, null);

    }
}

