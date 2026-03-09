package Entity;

import Camera.Camera;
import Harta.InterfaceHarta;


import Obiecte.HealthPotion;
import Obiecte.InutilPotion;
import Obiecte.PotionInterface;
import Obiecte.SpeedPotion;
import main.GamePanel;

import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Seller implements NPC,PlayerObserver {
    GamePanel gp;
    KeyHandler keyH;
    Camera camera = new Camera();
    BufferedImage tilesetRun;
    public Rectangle collisionBox = new Rectangle(21, 24, 6, 9);
    public double x, y;
    public double speed;
    public BufferedImage down1, down2, down3, down4, down5, down6;
    public BufferedImage up1, up2, up3, up4, up5, up6;
    private BufferedImage left1, left2, left3, left4, left5, left6;
    private BufferedImage right1, right2, right3, right4, right5, right6;
    public String direction;
    private int walkSpriteNum = 1;
    private int walkCounter = 0;
    private int initialY;
    private int initialX;
    private int pause = 0;
    /// private String[] npcDialogues;
    /// private boolean dialogueActive=false;
    // private int currentDialogueIndex=0;
    private String[] npcDialogues = {
            "Bine ai venit în singurul loc din Infern unde poți face o investiție proastă cu stil",
            "Marfă bună. Prețuri... discutabile. doar pentru azi sunt reduse cu 0.1% sefu, o potiune de la 20 de monezi...depinde de sufletul si portofelul tau"
    };
    private int currentDialogueIndex = 0;
    private boolean dialogueActive = false;

    String[] dialogueOptionGood = {"bine..hai să vedem", "nu...esti dubios"};
    String[] potiuni = {"Health", "Speed", "Inutila", "Gata bre ca m-ai scurs de bani"};

    boolean choosingResponse = false;
    int selectedResponse = 0;
    static int priceOfProduct=50;


    @Override
    public void updateObs(Player player) {
        int surplus;
        if(player.karma<0){
            surplus=5;
        }else{
            surplus=0;
        }
        if (player.coins < 300) {
            priceOfProduct = 20+surplus;
        } else {
            priceOfProduct = 40+surplus;
        }
    }






    public int getCurrentDialogueIndex() {
        return currentDialogueIndex;
    }


    public void setCurrentDialogueIndex(int index) {
        this.currentDialogueIndex = index;
    }
    public Seller(GamePanel gp) {
        this.gp = gp;

        //  this.camera=camera;
        //  this.camera=camera;
        speed = 0.25;
        direction = "right";
        getPlayerImage();
    }


    @Override
    public void getPlayerImage() {
        try {
            tilesetRun = ImageIO.read(new File("resources/Sprites/Vampires2_Walk_full.png"));

            int tileSize = 64;

            // UP
            up1 = tilesetRun.getSubimage(0 * tileSize, 1 * tileSize, tileSize, tileSize);
            up2 = tilesetRun.getSubimage(1 * tileSize, 1 * tileSize, tileSize, tileSize);
            up3 = tilesetRun.getSubimage(2 * tileSize, 1 * tileSize, tileSize, tileSize);
            up4 = tilesetRun.getSubimage(3 * tileSize, 1 * tileSize, tileSize, tileSize);
            up5 = tilesetRun.getSubimage(4 * tileSize, 1 * tileSize, tileSize, tileSize);
            up6 = tilesetRun.getSubimage(5 * tileSize, 1 * tileSize, tileSize, tileSize);

            left1 = tilesetRun.getSubimage(0 * tileSize, 2 * tileSize, tileSize, tileSize);
            left2 = tilesetRun.getSubimage(1 * tileSize, 2 * tileSize, tileSize, tileSize);
            left3 = tilesetRun.getSubimage(2 * tileSize, 2 * tileSize, tileSize, tileSize);
            left4 = tilesetRun.getSubimage(3 * tileSize, 2 * tileSize, tileSize, tileSize);
            left5 = tilesetRun.getSubimage(4 * tileSize, 2 * tileSize, tileSize, tileSize);
            left6 = tilesetRun.getSubimage(5 * tileSize, 2 * tileSize, tileSize, tileSize);

            right1 = tilesetRun.getSubimage(0 * tileSize, 3 * tileSize, tileSize, tileSize);
            right2 = tilesetRun.getSubimage(1 * tileSize, 3 * tileSize, tileSize, tileSize);
            right3 = tilesetRun.getSubimage(2 * tileSize, 3 * tileSize, tileSize, tileSize);
            right4 = tilesetRun.getSubimage(3 * tileSize, 3 * tileSize, tileSize, tileSize);
            right5 = tilesetRun.getSubimage(4 * tileSize, 3 * tileSize, tileSize, tileSize);
            right6 = tilesetRun.getSubimage(5 * tileSize, 3 * tileSize, tileSize, tileSize);


            // DOWN
            down1 = tilesetRun.getSubimage(0 * tileSize, 0 * tileSize, tileSize, tileSize);
            down2 = tilesetRun.getSubimage(1 * tileSize, 0 * tileSize, tileSize, tileSize);
            down3 = tilesetRun.getSubimage(2 * tileSize, 0 * tileSize, tileSize, tileSize);
            down4 = tilesetRun.getSubimage(3 * tileSize, 0 * tileSize, tileSize, tileSize);
            down5 = tilesetRun.getSubimage(4 * tileSize, 0 * tileSize, tileSize, tileSize);
            down6 = tilesetRun.getSubimage(5 * tileSize, 0 * tileSize, tileSize, tileSize);


        } catch (Exception e) {
            System.out.println("EROARE VAMPIR IMG LOAD");
        }

    }

    @Override
    public void setPlayerSpawn(int tileX, int tileY) {
        int tileSize = 16;
        x = tileX * tileSize;
        y = tileY * tileSize;
        initialY = tileY * tileSize;
        initialX = tileX * tileSize;
    }


    //DESENEZ POTIUUNI
    BufferedImage chosePotion(int opt) {
        BufferedImage img = null, tileset;
        if (opt == 0) {
            try {
                tileset = ImageIO.read(new File("resources/Sprites/SmallHealthPotion.png"));
                int tileSize = 16;
                img = tileset.getSubimage(4 * tileSize, 0, tileSize, tileSize);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (opt == 1) {
            try {
                tileset = ImageIO.read(new File("resources/Sprites/SmallStaminaPotion.png"));
                int tileSize = 16;
                img = tileset.getSubimage(4 * tileSize, 0, tileSize, tileSize);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (opt == 2) {
            try {
                tileset = ImageIO.read(new File("resources/Sprites/BigManaPotion.png"));
                int tileSize = 16;
                img = tileset.getSubimage(4 * tileSize, 0, tileSize, tileSize);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        return img;
    }

    void setImgSpeak(Player player) {
        int dx = (int)(player.x - x);
        int dy = (int)(player.y - y);

        if (Math.abs(dx) > Math.abs(dy)) {

            if (dx > 0) {
                direction = "right";
            } else {
                direction = "left";
            }
        } else {

            if (dy > 0) {
                direction = "down";
            } else {
                direction = "up";
            }
        }

        walkSpriteNum = 1;
    }


        @Override
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
            if(currentDialogueIndex==1) {
                g.setFont(new Font("Arial", Font.BOLD, 6));
            }else {
                g.setFont(new Font("Arial", Font.BOLD, 7));
            }
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


            if (img != null&&currentDialogueIndex==1) {
                int imgX = newBoxX + (boxWidth - img.getWidth()) / 2;
                int imgY = newBoxY + (boxHeight - img.getHeight()) / 2;
                g.drawImage(img, imgX, imgY, null);
            }

        }

        void DealWithPlayer(Player player){
          if(player.coins>=priceOfProduct){
              if(selectedResponse>=0&&selectedResponse<=2&&player.keyH.nextDialoguePressed) {
                  //PotionInterface potion=;

                  if (selectedResponse == 0) {
                      HealthPotion potion=new HealthPotion();
                      if(player.inventory.containsKey(potion)) {
                          player.inventory.put(potion, player.inventory.get(potion) + 1);
                      } else {
                          player.inventory.put(potion, 1);
                      }
                  }
                  else if (selectedResponse==1) {
                      SpeedPotion potion1=new SpeedPotion();
                      if(player.inventory.containsKey(potion1)) {
                          player.inventory.put(potion1, player.inventory.get(potion1) + 1);
                      } else {
                          player.inventory.put(potion1, 1);
                      }
                  }else {
                      InutilPotion potion2=new InutilPotion();
                      if(player.inventory.containsKey(potion2)) {
                          player.inventory.put(potion2, player.inventory.get(potion2) + 1);
                      } else {
                          player.inventory.put(potion2, 1);
                      }
                  }
                  player.coins-=priceOfProduct;
                  player.keyH.nextDialoguePressed=false;
                //  player.inventory.put(potion,i);
              }
          }
        }
        @Override
        public void speak(Player player, Graphics2D g) {
            double dx = player.x - x; //distanta fata de x
            double dy = player.y - y; //distanza fata de y
            double distance = Math.sqrt(dx * dx + dy * dy); //distanta intre 2 pct
            //  player.keyH.nextDialoguePressed=false;
            //CONDITII DE aGGRO
            if (distance <= 16 && player.keyH.speakPressed&&!dialogueActive) {
                if (!dialogueActive) { //se activeaza dialog
                    setImgSpeak(player);
                    dialogueActive = true;
                    currentDialogueIndex = 0;
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
                int boxY = (int) player.y - boxHeight + 90;

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
                        g.drawString(line.toString(), boxX + 10, lineY-15);
                        line = new StringBuilder(word + " "); //incepe linie noua
                        lineY += lineHeight;//trec la "rand nou"
                    } else { //inca nu am ajuns la limita...pur si simplu concatenez
                        line.append(word+" "); //daca cuv incape, il adaug pur si simplu
                    }
                }
                //afisare ultima linie ramasa
                g.drawString(line.toString(), boxX + 10, lineY-15);


                if(currentDialogueIndex==0) {
                    chooseResponse(player, g, boxX +5, boxY, dialogueOptionGood[currentDialogueIndex], 0,70,40);
                    chooseResponse(player, g, boxX +95, boxY, dialogueOptionGood[currentDialogueIndex+1], 1,70,40);

                    if (player.keyH.leftPressed) {
                        selectedResponse--;
                        if (selectedResponse < 0) selectedResponse = 1;
                        player.keyH.leftPressed = false;
                    } else if (player.keyH.rightPressed) {
                        selectedResponse++;
                        if (selectedResponse > 1) selectedResponse = 0;
                        player.keyH.rightPressed = false;
                    }


                    if(selectedResponse==1&&player.keyH.nextDialoguePressed==true){
                        dialogueActive = false;
                        player.keyH.nextDialoguePressed=false;
                        player.isTalking = false;

                        //return;
                    }
                }
                if(currentDialogueIndex==1){
                    chooseResponse(player, g, boxX , boxY, potiuni[0], 0,40,40);
                    chooseResponse(player, g, boxX +50-5, boxY, potiuni[1], 1,40,40);
                    chooseResponse(player, g, boxX +95-5, boxY, potiuni[2], 2,40,40);
                    chooseResponse(player, g, boxX +140-5, boxY, potiuni[3], 3,40,40);

                    DealWithPlayer(player);

                    if (player.keyH.leftPressed) {
                        selectedResponse--;
                        player.keyH.nextDialoguePressed=false;
                        if (selectedResponse < 0) selectedResponse = 3;
                        player.keyH.leftPressed = false;
                    } else if (player.keyH.rightPressed) {
                        selectedResponse++;
                        player.keyH.nextDialoguePressed=false;
                        if (selectedResponse > 3) selectedResponse = 0;
                        player.keyH.rightPressed = false;
                    }

                    if(selectedResponse==3&&player.keyH.nextDialoguePressed==true){
                        dialogueActive = false;
                        player.keyH.nextDialoguePressed=false;
                        player.isTalking = false;

                        //return;
                    }



                }

                if (player.keyH.nextDialoguePressed&&selectedResponse>=0&&selectedResponse<=1&&currentDialogueIndex<1) {
                    player.keyH.nextDialoguePressed = false;
                    player.keyH.attackPressed = false;
                    currentDialogueIndex++;
                    selectedResponse = -1;

                }

                if (player.keyH.escPressed) {
                    player.keyH.escPressed = false;
                    dialogueActive = false;
                    player.keyH.nextDialoguePressed=false;
                    currentDialogueIndex = 0;
                    player.isTalking = false;
                }

            }
        }
        @Override
        public void update() {
            int tileSize = 16;

            int minY = initialY - 3 * tileSize;
            int maxY = initialY + 3 * tileSize;
            int minX = initialX - 5 * tileSize;
            int maxX = initialX + 5 * tileSize;
            if ((y == minY&&x==maxX||y == minY&&x==minX||y == maxY&&x==minX||y == maxY&&x==maxX) && pause <= 69) {
                pause++;

            } else {
                pause = 0;
                if (direction.equals("right")) {
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
                } else if (direction.equals("down")) {
                    y += speed;
                    if (y >= maxY) {
                        y = maxY;
                        direction = "right";
                    }
                }

                walkCounter++;
                if (walkCounter > 10) {
                    walkSpriteNum = (walkSpriteNum % 6) + 1;
                    walkCounter = 0;
                }
            }
//System.out.println("X COORD "+x/16+" Y COORD "+y/16);
        }


                @Override
                public void draw (Graphics2D g2){
                    BufferedImage image = null;

                    if (direction.equals("up")) {
                       // System.out.println("IS IN UP");
                        if (walkSpriteNum == 1) image = up1;
                        else if (walkSpriteNum == 2) image = up2;
                        else if (walkSpriteNum == 3) image = up3;
                        else if (walkSpriteNum == 4) image = up4;
                        else if (walkSpriteNum == 5) image = up5;
                        else if (walkSpriteNum == 6) image = up6;

                    } else if (direction.equals("down")) {
                        if (walkSpriteNum == 1) image = down1;
                        else if (walkSpriteNum == 2) image = down2;
                        else if (walkSpriteNum == 3) image = down3;
                        else if (walkSpriteNum == 4) image = down4;
                        else if (walkSpriteNum == 5) image = down5;
                        else if (walkSpriteNum == 6) image = down6;

                    } else if (direction.equals("left")) {
                        if (walkSpriteNum == 1) image = left1;
                        else if (walkSpriteNum == 2) image = left2;
                        else if (walkSpriteNum == 3) image = left3;
                        else if (walkSpriteNum == 4) image = left4;
                        else if (walkSpriteNum == 5) image = left5;
                        else if (walkSpriteNum == 6) image = left6;

                    } else if (direction.equals("right")) {
                      //  System.out.println("AM INTRAT");
                        if (walkSpriteNum == 1) image = right1;
                        else if (walkSpriteNum == 2) image = right2;
                        else if (walkSpriteNum == 3) image = right3;
                        else if (walkSpriteNum == 4) image = right4;
                        else if (walkSpriteNum == 5) image = right5;
                        else if (walkSpriteNum == 6) image = right6;

                    }

                    g2.drawImage(image, (int) x, (int) y, gp.tileSize,gp.tileSize, null);
                    g2.drawRect(
                            (int)(x + collisionBox.x),
                            (int)(y + collisionBox.y),
                            collisionBox.width,
                            collisionBox.height
                    );
                }


}




