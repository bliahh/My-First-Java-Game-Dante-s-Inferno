package Entity;




import main.GamePanel;



import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Mike implements NPC {
    GamePanel gp;

    BufferedImage tilesetRun;


    public BufferedImage down1, down2, down3, down4,left1,rigth1;
    public BufferedImage up1, up2, up3, up4;
    public String direction;
    private int walkSpriteNum = 1;
    private int walkCounter=0;
    private int initialY;
    private int pause=0;
    public  int currentDialogueIndex = 0;
    private boolean dialogueActive = false;

    public double x, y;
    public double speed;
    private String[] npcDialogues = {
            "Alvie... nu ne-am mai văzut de când m-ai condamnat. Viața mea s-a prăbușit din cauza ta. Credeam că suntem prieteni, dar am aflat că în viață există doar trădare.",
            "Spune-mi, Alvie, chiar crezi că acest joc al vieții merită să fie jucat cu astfel de trucuri? De ce ai făcut-o? Credeai că meritai job-ul mai mult decât mine?",
            "Te-ai gândit măcar o clipă cum m-ai lăsat? Cum am luptat din greu și am ajuns să pierd totul doar din cauza unei minciuni? Ce-i drept în asta, Alvie?",
            "Știi, Alvie, am așteptat mult să te întâlnesc din nou. Am sperat că vei înțelege ce ai făcut. Crezi că poți să te justifici pentru ce ai facut?",
            "Ai ales. Oricare ar fi fost decizia, drumul tău continuă. Căci karma, fie bună, fie rea, va urmări pașii tăi."
    };


    String []dialogueOptionBad={
            "Mike, totul a fost doar o competiție. chiar uitasem de tine, nu regret deloc",
            "A fost o competiție, Mike. Viața e nedreaptă. Poate am acționat greșit, dar am făcut ceea ce am crezut că trebuie.",
            "Nu am vrut să te rănesc, Mike. Dar, dacă vreau ceva, lupt pentru el. Nu am făcut-o din răutate.",
            "Poate că nu pot să repar ce am făcut, dar am învățat ceva din toată experiența asta. Viața e plină de alegeri greșite, dar cine nu le face?",
            "Viața nu este mereu corectă, în final este doar vina ta, sper să nu ne mai vedem."
    };
    String []dialogueOptionGood={
            "Mi-am dat seama că am greșit,poate acum e momentul să mă schimb.",
            "Știu că nu am avut dreptate. Am acționat din egoism și mi-a părut rău. Dacă aș putea să întorc timpul, aș face altfel",
            "Mă doare că ți-am distrus șansa. Nu am gândit atunci și știu că am greșit. E vina mea.",
            "Nu am scuze pentru ce am făcut. Mi-aș fi dorit să fi făcut altfel. Mă doare că ți-am provocat atât de multă suferință",
            "Nu pot da timpul înnapoi,sper ca într-o zi să mă poți ierta,și să ieșim din nou la o bere ca pe vremuri  "};


    int selectedResponse = 0;
    private boolean hasAlreadyTalked=false;
    private boolean inc=false;


    public int getCurrentDialogueIndex() {
        return currentDialogueIndex;
    }


    public void setCurrentDialogueIndex(int index) {
        this.currentDialogueIndex = index;
    }

    public Mike(GamePanel gp) {
        this.gp = gp;
        x = 200;
        y = 200;
        speed = 0.20;
        direction = "down";
        getPlayerImage();
    }


    @Override
    public void getPlayerImage() {
        try {
            tilesetRun = ImageIO.read(new File("resources/Sprites/mikee.png"));

            int tileSize = 51;

            // UP
            up1 = tilesetRun.getSubimage(0 * tileSize, 0 * tileSize, tileSize, tileSize);
            up2 = tilesetRun.getSubimage(1 * tileSize, 0 * tileSize, tileSize, tileSize);
            up3 = tilesetRun.getSubimage(2 * tileSize, 0 * tileSize, tileSize, tileSize);
            up4 = tilesetRun.getSubimage(3 * tileSize, 0 * tileSize, tileSize, tileSize);

            // DOWN
            down1 = tilesetRun.getSubimage(0 * tileSize, 3 * tileSize, tileSize, tileSize);
            down2 = tilesetRun.getSubimage(1 * tileSize, 3 * tileSize, tileSize, tileSize);
            down3 = tilesetRun.getSubimage(2 * tileSize, 3 * tileSize, tileSize, tileSize);
            down4 = tilesetRun.getSubimage(3 * tileSize, 3 * tileSize, tileSize, tileSize);

            rigth1 = tilesetRun.getSubimage(3 * tileSize, 2 * tileSize, tileSize, tileSize);
            left1 = tilesetRun.getSubimage(3 * tileSize, 1 * tileSize, tileSize, tileSize);

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
        //limita pana la care vreau sa scriu
        int maxTextWidth = boxWidth - 10;

        g.setFont(new Font("Arial", Font.BOLD, 6));

        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();
        for (String word : words) {
            String testLine = line + word + " ";
            int testWidth = fm.stringWidth(testLine);
            //daca depasesc limita
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
       if(!hasAlreadyTalked){
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

        if (dialogueActive) {
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
                currentDialogueIndex++;
                if (selectedResponse == 1) {
                    player.karma -= 50;
                    System.out.println("KARMA: " + player.karma);
                } else {
                    player.karma += 50;
                    System.out.println("KARMA: " + player.karma);
                }

                selectedResponse = -1;


            }

            if (player.keyH.escPressed || currentDialogueIndex == 4) {

                player.keyH.escPressed = false;
                dialogueActive = false;
                player.keyH.nextDialoguePressed = false;
               // currentDialogueIndex = 0;
                player.isTalking = false;
            }
            if (currentDialogueIndex == 4&&player.getDialogueLevel3Counter()<1) {
                hasAlreadyTalked = true;
                inc=true;
                player.setDialogueLevel3Counter(player.getDialogueLevel3Counter()+1);
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
            return;
        }else {
            pause = 0;

            if (direction.equals("down")) {
                y -= speed;
                if (y <= minY) {
                    y = minY;
                    direction = "up";

                }
            } else if (direction.equals("up")) {
                y += speed;
                if (y >= maxY) {
                    y = maxY;
                    direction = "down";
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
        } else if (direction.equals("left")) {
            if (walkSpriteNum==1)image = left1; // Un singur frame
        } else if (direction.equals("right")) {
            if (walkSpriteNum==1)image = rigth1; // Un singur frame
        }


        g2.drawImage(image, (int) x, (int) y, gp.tileSize/2 , gp.tileSize /2, null);

    }
}


