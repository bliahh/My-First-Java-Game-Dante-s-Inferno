package Entity;

import Obiecte.*;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Iterator;
import java.util.Map;

public class Son implements NPC{
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
    public  int currentDialogueIndex = 0;

    private boolean dialogueActive = false;



    private String[] npcDialogues = {
            "Tată...? Ești tu? Am tot sperat că te voi găsi aici, chiar dacă... nu știam dacă mai exiști.",
            "Toate aceste lumi, aceste ecouri digitale... le-am străbătut în căutarea ta. Unde ai fost?",
            "Uneori mă întreb dacă nu cumva am fost creat doar din dorința de a te mai auzi o dată...",
            "Spune-mi... te voi găsi vreodată cu adevărat? Sau sunt condamnat să te caut în amintiri și coduri uitate?",
            "Vreau doar să știu... adevărul. Indiferent cât de dureros este."
    };


    String[] dialogueOptionGood = {
            "Sunt aici... dar nu așa cum ai sperat. Tatăl tău nu mai există așa cum îl știi.",
            "Am fugit de responsabilitate. Am greșit. Îmi pare sincer rău.",
            "Nu ești doar o iluzie. Ești dorința mea cea mai profundă de a repara ceea ce am stricat.",
            "Nu te voi minți. Ce a fost s-a dus.",
            "Timpul nu poate fi dat înapoi...nu mai am timp sa repar acest lucru."
    };

    String[] dialogueOptionBad = {
            "Da... sunt eu. Te-am așteptat. Totul va fi bine în curând.",
            "Nu a fost ușor, dar am fost mereu alături de tine, chiar dacă nu puteai vedea.",
            "Ești real, fiule. Și tatăl tău e viu. Te așteaptă într-un loc plin de lumină.",
            "Ia acest craniu. Este cheia spre Paradis. Ne vom revedea acolo, promit.",
            "Nu plânge. Totul e parte din plan. Curând vei fi fericit din nou."
    };

    public int getCurrentDialogueIndex() {
        return currentDialogueIndex;
    }


    public void setCurrentDialogueIndex(int index) {
        this.currentDialogueIndex = index;
    }
    boolean choosingResponse = false;
    int selectedResponse = 0;
    boolean hasAlreadyTalked=false;
    private int initialX;
    private boolean done=false;

    public Son(GamePanel gp) {
        this.gp = gp;
        speed = 0.20;
        direction = "right";

        getPlayerImage();
    }

    @Override
    public void getPlayerImage() {
        try {
            tilesetRun = ImageIO.read(new File("resources/Sprites/fiuu.png"));

            int tileSize = 64;

            // UP
            up1 = tilesetRun.getSubimage(0 * tileSize, 1 * tileSize, tileSize, tileSize);
            up2 = tilesetRun.getSubimage(1 * tileSize, 1 * tileSize, tileSize, tileSize);
            up3 = tilesetRun.getSubimage(2 * tileSize, 1 * tileSize, tileSize, tileSize);
            up4 = tilesetRun.getSubimage(3 * tileSize, 1 * tileSize, tileSize, tileSize);

            // DOWN
            down1 = tilesetRun.getSubimage(0 * tileSize, 2 * tileSize, tileSize, tileSize);
            down2 = tilesetRun.getSubimage(1 * tileSize, 2 * tileSize, tileSize, tileSize);
            down3 = tilesetRun.getSubimage(2 * tileSize, 2 * tileSize, tileSize, tileSize);
            down4 = tilesetRun.getSubimage(3 * tileSize, 2 * tileSize, tileSize, tileSize);

        } catch (Exception e) {
            System.out.println("EROARE ORCO LOAD");
        }

    }

    @Override
    public void setPlayerSpawn(int tileX, int tileY) {
        int tileSize = 16;
        x = tileX * tileSize;
        y = tileY * tileSize;
        initialX=tileX * tileSize;
    }
    boolean getHasTalkedd(){
        return this.hasAlreadyTalked;
    }
    public void chooseResponse(Player player, Graphics2D g, int boxX, int boxY, String response, int option, int boxWidthh, int boxHeightt){
        int boxWidth =boxWidthh; //latime box
        int boxHeight = boxHeightt+20; //innaltime box
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
    public boolean getHasTalked(){
        return hasAlreadyTalked;
    }

    @Override
    public void speak(Player player, Graphics2D g) {
        double dx = player.x - x; //distanta fata de x
        double dy = player.y - y; //distanza fata de y
        double distance = Math.sqrt(dx * dx + dy * dy); //distanta intre 2 pct
        //  player.keyH.nextDialoguePressed=false;
        //CONDITII DE aGGRO
        if (distance <= 64 && player.keyH.speakPressed && !dialogueActive) {
            if (!dialogueActive) { //se activeaza dialog

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
            int boxHeight = 120; //innaltime box
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
                    if (selectedResponse == 1 && !(currentDialogueIndex == 4)&&!done) {
                        player.karma -= 30;
                        System.out.println("KARMA: " + player.karma);

                    } else if (!(currentDialogueIndex == 4)) {
                        player.karma += 30;
                        System.out.println("KARMA: " + player.karma);
                    }

                    if (currentDialogueIndex == 4&&!done) {
                        if (selectedResponse == 1 && shouldAdvance && (player.countSkull()==1)) {
                            System.out.println("am intrat in 1");
                            player.karma -= 51;
                            //player.level2Passed = true;
                            Iterator<Map.Entry<ObjInterface, Integer>> iterator = player.inventory.entrySet().iterator();
                            while (iterator.hasNext()) {
                                Map.Entry<ObjInterface, Integer> entry = iterator.next();
                                ObjInterface obj = entry.getKey();

                                if (obj instanceof Skull) {
                                    iterator.remove();
                                }
                            }

                        } else if (selectedResponse == 0 && shouldAdvance&&!done) {

                            player.karma += 50;
                        }


                        selectedResponse = -1;



                    }



                    if(currentDialogueIndex==5&&shouldAdvance&&!done){
                        done=true;
                        player.level2Passed=true;
                    }
                }

                if (player.keyH.escPressed) {
                    player.keyH.escPressed = false;
                    dialogueActive = false;
                    player.keyH.nextDialoguePressed = false;
                    player.isTalking = false;
                }

                if (player.keyH.nextDialoguePressed && currentDialogueIndex == 5) {
                    hasAlreadyTalked = true;
                    done = true;
                    player.keyH.escPressed = false;
                    dialogueActive = false;
                    player.keyH.nextDialoguePressed = false;
                    //aaa currentDialogueIndex = 4;
                    player.isTalking = false;
                }


        }
    }





    @Override
    public void update() {
        int tileSize = 16;

        int minX = initialX - tileSize;
        int maxX = initialX + tileSize;
        if((x==minX||x==maxX)&&pause<=4){
            walkSpriteNum=1;
            pause++;

        }else {
            pause = 0;

            if (direction.equals("left")) {
                x -= speed;
                if (x <= minX) {
                    x = minX;
                    direction = "right";

                }
            } else if (direction.equals("right")) {
                x += speed;
                if (x >= maxX) {
                    x = maxX;
                    direction = "left";
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

        if (direction.equals("left")) {
            if (walkSpriteNum == 1) image = up1;
            else if (walkSpriteNum == 2) image = up2;
            else if (walkSpriteNum == 3) image = up3;
            else if (walkSpriteNum == 4) image = up4;
        } else if (direction.equals("right")) {
            if (walkSpriteNum == 1) image = down1;
            else if (walkSpriteNum == 2) image = down2;
            else if (walkSpriteNum == 3) image = down3;
            else if (walkSpriteNum == 4) image = down4;

        }

        g2.drawImage(image, (int) x, (int) y, gp.tileSize , gp.tileSize+10 , null);

    }
}
