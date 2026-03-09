package Entity;

import Collision.CollisionCheck;

import Harta.InterfaceHarta;

import Obiecte.*;
import main.GamePanel;

import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;

import Camera.Camera;
public class Player extends Entity implements MainCharacter {
    public boolean isTalking=false;
    public int karma;
    public int coins;
    public boolean level1Passed=false;
    public boolean level2Passed=false;
    private int dialogueLevel3Counter=0;
    GamePanel gp;
    public KeyHandler keyH;
    Camera camera = new Camera(0, 0, 104, 104, this);
    BufferedImage tilesetRun;
    boolean isAttacking = false;
    public boolean isDead=false;
    public BufferedImage downDeath1, downDeath2, downDeath3, downDeath4, downDeath5, downDeath6;
    public BufferedImage downAttk1, downAttk2, downAttk3, downAttk4, downAttk5, downAttk6;
    public BufferedImage downHurt1, downHurt2, downHurt3, downHurt4;
    public BufferedImage upDeath1, upDeath2, upDeath3, upDeath4, upDeath5, upDeath6;
    public BufferedImage upAttk1, upAttk2, upAttk3, upAttk4, upAttk5, upAttk6;
    public BufferedImage upHurt1, upHurt2, upHurt3, upHurt4;

    public BufferedImage leftDeath1, leftDeath2, leftDeath3, leftDeath4, leftDeath5;
    public BufferedImage leftAttk1, leftAttk2, leftAttk3, leftAttk4, leftAttk5, leftAttk6;
    public BufferedImage leftHurt1, leftHurt2, leftHurt3, leftHurt4;
    public BufferedImage rightDeath1, rightDeath2, rightDeath3, rightDeath4, rightDeath5;
    public BufferedImage rightAttk1, rightAttk2, rightAttk3, rightAttk4, rightAttk5, rightAttk6;
    public BufferedImage rightHurt1, rightHurt2, rightHurt3, rightHurt4;

    private int hurtDelayCounter = 0;
    private final int hurtDelayLimit = 20;

    int deathSpriteNum = 1;
    int deathCounter = 0;

    public void setDialogueLevel3Counter(int x){
        dialogueLevel3Counter=x;
    }
    public int getDialogueLevel3Counter(){
        return dialogueLevel3Counter;
    }
    public HashMap<ObjInterface,Integer> inventory;


    public ArrayList<PlayerObserver> observers = new ArrayList<>();

    public void addObserver(PlayerObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(PlayerObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (PlayerObserver observer : observers) {
            observer.updateObs(this);
        }
    }

    public void checkState() {
        if (karma < 0 || coins > 300) {
            notifyObservers();
        }
    }






    public int countKeys() {
        int count = 0;
        for (ObjInterface obj : inventory.keySet()) {
            if (obj instanceof KeyOne || obj instanceof KeyTwo || obj instanceof KeyThree) {
                count += inventory.get(obj);
            }
        }
        return count;
    }
    public void setCoins(int x){
        coins=x;
    }
    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        speed = 1.2;
        direction = "down";
        life = 200;
        attack = 21;
        karma=0;
        coins=700;
        inventory=new HashMap<>();

        getPlayerImage();
    }
    public int countRing(){
        int count = 0;
        for (ObjInterface obj : inventory.keySet()) {
            if (obj instanceof Ring) {
                count += inventory.get(obj);
                break;
            }
        }
        // System.out.println("am "+count+" chei");
        return count;
    }
    public int countSkull(){
        int count = 0;
        for (ObjInterface obj : inventory.keySet()) {
            if (obj instanceof Skull) {
                count += inventory.get(obj);

            }
        }
        // System.out.println("am "+count+" chei");
        return count;
    }
    @Override
    public void getPlayerImage() {
        try {
            tilesetRun = ImageIO.read(new File("resources/Sprites/WarriorTotal.png"));

            int tileSize = 48;

            // UP
            up1 = tilesetRun.getSubimage(0 * tileSize, 1 * tileSize, tileSize, tileSize);
            up2 = tilesetRun.getSubimage(1 * tileSize, 1 * tileSize, tileSize, tileSize);
            up3 = tilesetRun.getSubimage(2 * tileSize, 1 * tileSize, tileSize, tileSize);
            up4 = tilesetRun.getSubimage(3 * tileSize, 1 * tileSize, tileSize, tileSize);
            up5 = tilesetRun.getSubimage(4 * tileSize, 1 * tileSize, tileSize, tileSize);
            up6 = tilesetRun.getSubimage(5 * tileSize, 1 * tileSize, tileSize, tileSize);
            up7 = tilesetRun.getSubimage(6 * tileSize, 1 * tileSize, tileSize, tileSize);
            up8 = tilesetRun.getSubimage(7 * tileSize, 1 * tileSize, tileSize, tileSize);

            // LEFT
            left1 = tilesetRun.getSubimage(0 * tileSize, 2 * tileSize, tileSize, tileSize);
            left2 = tilesetRun.getSubimage(1 * tileSize, 2 * tileSize, tileSize, tileSize);
            left3 = tilesetRun.getSubimage(2 * tileSize, 2 * tileSize, tileSize, tileSize);
            left4 = tilesetRun.getSubimage(3 * tileSize, 2 * tileSize, tileSize, tileSize);
            left5 = tilesetRun.getSubimage(4 * tileSize, 2 * tileSize, tileSize, tileSize);
            left6 = tilesetRun.getSubimage(5 * tileSize, 2 * tileSize, tileSize, tileSize);
            left7 = tilesetRun.getSubimage(6 * tileSize, 2 * tileSize, tileSize, tileSize);
            left8 = tilesetRun.getSubimage(7 * tileSize, 2 * tileSize, tileSize, tileSize);

            // RIGHT
            right1 = tilesetRun.getSubimage(0 * tileSize, 3 * tileSize, tileSize, tileSize);
            right2 = tilesetRun.getSubimage(1 * tileSize, 3 * tileSize, tileSize, tileSize);
            right3 = tilesetRun.getSubimage(2 * tileSize, 3 * tileSize, tileSize, tileSize);
            right4 = tilesetRun.getSubimage(3 * tileSize, 3 * tileSize, tileSize, tileSize);
            right5 = tilesetRun.getSubimage(4 * tileSize, 3 * tileSize, tileSize, tileSize);
            right6 = tilesetRun.getSubimage(5 * tileSize, 3 * tileSize, tileSize, tileSize);
            right7 = tilesetRun.getSubimage(6 * tileSize, 3 * tileSize, tileSize, tileSize);
            right8 = tilesetRun.getSubimage(7 * tileSize, 3 * tileSize, tileSize, tileSize);

            // DOWN
            down1 = tilesetRun.getSubimage(0 * tileSize, 0 * tileSize, tileSize, tileSize);
            down2 = tilesetRun.getSubimage(1 * tileSize, 0 * tileSize, tileSize, tileSize);
            down3 = tilesetRun.getSubimage(2 * tileSize, 0 * tileSize, tileSize, tileSize);
            down4 = tilesetRun.getSubimage(3 * tileSize, 0 * tileSize, tileSize, tileSize);
            down5 = tilesetRun.getSubimage(4 * tileSize, 0 * tileSize, tileSize, tileSize);
            down6 = tilesetRun.getSubimage(5 * tileSize, 0 * tileSize, tileSize, tileSize);
            down7 = tilesetRun.getSubimage(6 * tileSize, 0 * tileSize, tileSize, tileSize);
            down8 = tilesetRun.getSubimage(7 * tileSize, 0 * tileSize, tileSize, tileSize);

            upDeath1 = tilesetRun.getSubimage(0 * tileSize, 4 * tileSize, tileSize, tileSize);
            upDeath2 = tilesetRun.getSubimage(1 * tileSize, 4 * tileSize, tileSize, tileSize);
            upDeath3 = tilesetRun.getSubimage(2 * tileSize, 4 * tileSize, tileSize, tileSize);
            upDeath4 = tilesetRun.getSubimage(3 * tileSize, 4 * tileSize, tileSize, tileSize);
            upDeath5 = tilesetRun.getSubimage(4 * tileSize, 4 * tileSize, tileSize, tileSize);



            upAttk1 = tilesetRun.getSubimage(0 * tileSize, 5 * tileSize, tileSize, tileSize);
            upAttk2 = tilesetRun.getSubimage(1 * tileSize, 5 * tileSize, tileSize, tileSize);
            upAttk3 = tilesetRun.getSubimage(2 * tileSize, 5 * tileSize, tileSize, tileSize);
            upAttk4 = tilesetRun.getSubimage(3 * tileSize, 5 * tileSize, tileSize, tileSize);
            upAttk5 = tilesetRun.getSubimage(4 * tileSize, 5 * tileSize, tileSize, tileSize);
            upAttk6 = tilesetRun.getSubimage(5 * tileSize, 5 * tileSize, tileSize, tileSize);

            upHurt1 = tilesetRun.getSubimage(0 * tileSize, 6 * tileSize, tileSize, tileSize);
            upHurt2 = tilesetRun.getSubimage(1 * tileSize, 6 * tileSize, tileSize, tileSize);
            upHurt3 = tilesetRun.getSubimage(2 * tileSize, 6 * tileSize, tileSize, tileSize);
            upHurt4 = tilesetRun.getSubimage(3 * tileSize, 6 * tileSize, tileSize, tileSize);


            downDeath1 = tilesetRun.getSubimage(0 * tileSize, 7 * tileSize, tileSize, tileSize);
            downDeath2 = tilesetRun.getSubimage(1 * tileSize, 7 * tileSize, tileSize, tileSize);
            downDeath3 = tilesetRun.getSubimage(2 * tileSize, 7 * tileSize, tileSize, tileSize);
            downDeath4 = tilesetRun.getSubimage(3 * tileSize, 7 * tileSize, tileSize, tileSize);
            downDeath5 = tilesetRun.getSubimage(4 * tileSize, 7 * tileSize, tileSize, tileSize);


            downAttk1 = tilesetRun.getSubimage(0 * tileSize, 8 * tileSize, tileSize, tileSize);
            downAttk2 = tilesetRun.getSubimage(1 * tileSize, 8 * tileSize, tileSize, tileSize);
            downAttk3 = tilesetRun.getSubimage(2 * tileSize, 8 * tileSize, tileSize, tileSize);
            downAttk4 = tilesetRun.getSubimage(3 * tileSize, 8 * tileSize, tileSize, tileSize);
            downAttk5 = tilesetRun.getSubimage(4 * tileSize, 8 * tileSize, tileSize, tileSize);
            downAttk6 = tilesetRun.getSubimage(5 * tileSize, 8 * tileSize, tileSize, tileSize);

            downHurt1 = tilesetRun.getSubimage(0 * tileSize, 9 * tileSize, tileSize, tileSize);
            downHurt2 = tilesetRun.getSubimage(1 * tileSize, 9 * tileSize, tileSize, tileSize);
            downHurt3 = tilesetRun.getSubimage(2 * tileSize, 9 * tileSize, tileSize, tileSize);
            downHurt4 = tilesetRun.getSubimage(3 * tileSize, 9 * tileSize, tileSize, tileSize);

            leftDeath1 = tilesetRun.getSubimage(0 * tileSize, 10 * tileSize, tileSize, tileSize);
            leftDeath2 = tilesetRun.getSubimage(1 * tileSize, 10 * tileSize, tileSize, tileSize);
            leftDeath3 = tilesetRun.getSubimage(2 * tileSize, 10 * tileSize, tileSize, tileSize);
            leftDeath4 = tilesetRun.getSubimage(3 * tileSize, 10 * tileSize, tileSize, tileSize);
            leftDeath5 = tilesetRun.getSubimage(4 * tileSize, 10 * tileSize, tileSize, tileSize);


            leftAttk1 = tilesetRun.getSubimage(0 * tileSize, 11 * tileSize, tileSize, tileSize);
            leftAttk2 = tilesetRun.getSubimage(1 * tileSize, 11 * tileSize, tileSize, tileSize);
            leftAttk3 = tilesetRun.getSubimage(2 * tileSize, 11 * tileSize, tileSize, tileSize);
            leftAttk4 = tilesetRun.getSubimage(3 * tileSize, 11 * tileSize, tileSize, tileSize);
            leftAttk5 = tilesetRun.getSubimage(4 * tileSize, 11 * tileSize, tileSize, tileSize);
            leftAttk6 = tilesetRun.getSubimage(5 * tileSize, 11 * tileSize, tileSize, tileSize);

            leftHurt1 = tilesetRun.getSubimage(0 * tileSize, 12 * tileSize, tileSize, tileSize);
            leftHurt2 = tilesetRun.getSubimage(1 * tileSize, 12 * tileSize, tileSize, tileSize);
            leftHurt3 = tilesetRun.getSubimage(2 * tileSize, 12 * tileSize, tileSize, tileSize);
            leftHurt4 = tilesetRun.getSubimage(3 * tileSize, 12 * tileSize, tileSize, tileSize);


            rightDeath1 = tilesetRun.getSubimage(0 * tileSize, 13 * tileSize, tileSize, tileSize);
            rightDeath2 = tilesetRun.getSubimage(1 * tileSize, 13 * tileSize, tileSize, tileSize);
            rightDeath3 = tilesetRun.getSubimage(2 * tileSize, 13 * tileSize, tileSize, tileSize);
            rightDeath4 = tilesetRun.getSubimage(3 * tileSize, 13 * tileSize, tileSize, tileSize);
            rightDeath5 = tilesetRun.getSubimage(4 * tileSize, 13 * tileSize, tileSize, tileSize);


            rightAttk1 = tilesetRun.getSubimage(0 * tileSize, 14 * tileSize, tileSize, tileSize);
            rightAttk2 = tilesetRun.getSubimage(1 * tileSize, 14 * tileSize, tileSize, tileSize);
            rightAttk3 = tilesetRun.getSubimage(2 * tileSize, 14 * tileSize, tileSize, tileSize);
            rightAttk4 = tilesetRun.getSubimage(3 * tileSize, 14 * tileSize, tileSize, tileSize);
            rightAttk5 = tilesetRun.getSubimage(4 * tileSize, 14 * tileSize, tileSize, tileSize);
            rightAttk6 = tilesetRun.getSubimage(5 * tileSize, 14 * tileSize, tileSize, tileSize);

            rightHurt1 = tilesetRun.getSubimage(0 * tileSize, 15 * tileSize, tileSize, tileSize);
            rightHurt2 = tilesetRun.getSubimage(1 * tileSize, 15 * tileSize, tileSize, tileSize);
            rightHurt3 = tilesetRun.getSubimage(2 * tileSize, 15 * tileSize, tileSize, tileSize);
            rightHurt4 = tilesetRun.getSubimage(3 * tileSize, 15 * tileSize, tileSize, tileSize);


        } catch (Exception e) {
            System.out.println("EROARE ORCO LOAD");
        }

    }

    @Override
    public void setPlayerSpawn(int tileX, int tileY) {
        int tileSize = 16;
        x = tileX * tileSize;
        y = tileY * tileSize;
    }


    @Override
    public void getHurt(Entity entity) {
        this.life -= entity.attack;
        isHurt = true;
        hurtCounter = 0;
        hurtSpriteNum = 1;
    }
    @Override
    public void update(InterfaceHarta harta, ArrayList<Orco> enemies) {
        checkState();

        CollisionCheck cc = new CollisionCheck();
        boolean isMoving = false;

        if (life <= 0 && !isDead) {
            isDead = true;
            deathSpriteNum = 1;
            deathCounter = 0;
        }

        if (isDead) {
            deathCounter++;
            if (deathCounter % 20 == 0) {
                deathSpriteNum++;
                if (deathSpriteNum > 5) {
                    deathSpriteNum = 5; //ramane pe ultimu frame cand moare

                }
            }

        }else {
            if (isHurt) {
                hurtCounter++;


                if (hurtDelayCounter > hurtDelayLimit) {

                    if (hurtCounter % 6 == 0) {
                        hurtSpriteNum++;
                        if (hurtSpriteNum > 4) hurtSpriteNum = 1;
                    }
                } else {
                    hurtDelayCounter++;
                }


                if (hurtCounter > 24) {
                    isHurt = false;
                    hurtCounter = 0;
                    hurtSpriteNum = 1;
                    hurtDelayCounter = 0;
                }
            }


            if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
                isMoving = true;

                if (keyH.upPressed) {
                    direction = "up";
                    if (!cc.checkCollision(this, harta)) y -= speed;
                } else if (keyH.downPressed) {
                    direction = "down";
                    if (!cc.checkCollision(this, harta)) y += speed;
                } else if (keyH.leftPressed) {
                    direction = "left";
                    if (!cc.checkCollision(this, harta)) x -= speed;
                } else if (keyH.rightPressed) {
                    direction = "right";
                    if (!cc.checkCollision(this, harta)) x += speed;
                }

                walkCounter++;
                if (walkCounter > 10) {
                    walkSpriteNum = (walkSpriteNum % 8) + 1;
                    walkCounter = 0;
                }
            }


            if (isAttacking) {
                attackCounter++;

                if (attackCounter > 8) {
                    attackSpriteNum++;
                    attackCounter = 0;
                }

                if (attackSpriteNum > 6) {
                    isAttacking = false;
                    attackSpriteNum = 1;
                }
            } else if (keyH.attackPressed) {
                isAttacking = true;
                for (Entity enemy : enemies) {
                    enemy.getHurt(this);
                    System.out.println("Lovit ! HP ramas: " + enemy.life);

                }
                attackSpriteNum = 1;
                attackCounter = 0;
                keyH.attackPressed = false;
            }



            if (!isMoving && !isAttacking && !isHurt&& !isDead) {
                walkSpriteNum = 1;
            }
        }
       // System.out.println("MA AFLU PE POZ:  X= "+(x/16)+" Y= "+(y/16));
        camera.update(this);
    }



    @Override
    public void draw(Graphics2D g2) {

    //   System.out.println(getDialogueLevel3Counter());

        BufferedImage image = null;
        if (isDead) {
            switch (direction) {
                case "down":
                    if (deathSpriteNum == 1) image = downDeath1;
                    else if (deathSpriteNum == 2) image = downDeath2;
                    else if (deathSpriteNum == 3) image = downDeath3;
                    else if (deathSpriteNum == 4) image = downDeath4;
                    else if (deathSpriteNum == 5) image = downDeath5;


                    break;

                case "up":
                    if (deathSpriteNum == 1) image = upDeath1;
                    else if (deathSpriteNum == 2) image = upDeath2;
                    else if (deathSpriteNum == 3) image = upDeath3;
                    else if (deathSpriteNum == 4) image = upDeath4;
                    else if (deathSpriteNum == 5) image = upDeath5;
                    else if (deathSpriteNum == 6) image = upDeath6;

                    break;

                case "left":
                    if (deathSpriteNum == 1) image = leftDeath1;
                    else if (deathSpriteNum == 2) image = leftDeath2;
                    else if (deathSpriteNum == 3) image = leftDeath3;
                    else if (deathSpriteNum == 4) image = leftDeath4;
                    else if (deathSpriteNum == 5) image = leftDeath5;

                    break;

                case "right":
                    if (deathSpriteNum == 1) image = rightDeath1;
                    else if (deathSpriteNum == 2) image = rightDeath2;
                    else if (deathSpriteNum == 3) image = rightDeath3;
                    else if (deathSpriteNum == 4) image = rightDeath4;
                    else if (deathSpriteNum == 5) image = rightDeath5;

                    break;
            }
        }else if (isAttacking) {

            if (direction.equals("up")) {
                if (attackSpriteNum == 1) image = upAttk1;
                else if (attackSpriteNum == 2) image = upAttk2;
                else if (attackSpriteNum == 3) image = upAttk3;
                else if (attackSpriteNum == 4) image = upAttk4;
                else if (attackSpriteNum == 5) image = upAttk5;
                else if (attackSpriteNum == 6) image = upAttk6;
            } else if (direction.equals("down")) {
                if (attackSpriteNum == 1) image = downAttk1;
                else if (attackSpriteNum == 2) image = downAttk2;
                else if (attackSpriteNum == 3) image = downAttk3;
                else if (attackSpriteNum == 4) image = downAttk4;
                else if (attackSpriteNum == 5) image = downAttk5;
                else if (attackSpriteNum == 6) image = downAttk6;
            } else if (direction.equals("left")) {
                if (attackSpriteNum == 1) image = leftAttk1;
                else if (attackSpriteNum == 2) image = leftAttk2;
                else if (attackSpriteNum == 3) image = leftAttk3;
                else if (attackSpriteNum == 4) image = leftAttk4;
                else if (attackSpriteNum == 5) image = leftAttk5;
                else if (attackSpriteNum == 6) image = leftAttk6;
            } else if (direction.equals("right")) {
                if (attackSpriteNum == 1) image = rightAttk1;
                else if (attackSpriteNum == 2) image = rightAttk2;
                else if (attackSpriteNum == 3) image = rightAttk3;
                else if (attackSpriteNum == 4) image = rightAttk4;
                else if (attackSpriteNum == 5) image = rightAttk5;
                else if (attackSpriteNum == 6) image = rightAttk6;
            }

        } else if (isHurt) {

            if (direction.equals("up")) {
                if (hurtSpriteNum == 1) image = upHurt1;
                else if (hurtSpriteNum == 2) image = upHurt2;
                else if (hurtSpriteNum == 3) image = upHurt3;
                else if (hurtSpriteNum == 4) image = upHurt4;
            } else if (direction.equals("down")) {
                if (hurtSpriteNum == 1) image = downHurt1;
                else if (hurtSpriteNum == 2) image = downHurt2;
                else if (hurtSpriteNum == 3) image = downHurt3;
                else if (hurtSpriteNum == 4) image = downHurt4;
            } else if (direction.equals("left")) {
                if (hurtSpriteNum == 1) image = leftHurt1;
                else if (hurtSpriteNum == 2) image = leftHurt2;
                else if (hurtSpriteNum == 3) image = leftHurt3;
                else if (hurtSpriteNum == 4) image = leftHurt4;
            } else if (direction.equals("right")) {
                if (hurtSpriteNum == 1) image = rightHurt1;
                else if (hurtSpriteNum == 2) image = rightHurt2;
                else if (hurtSpriteNum == 3) image = rightHurt3;
                else if (hurtSpriteNum == 4) image = rightHurt4;
            }

        } else {

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
            } else if (direction.equals("left")) {
                if (walkSpriteNum == 1) image = left1;
                else if (walkSpriteNum == 2) image = left2;
                else if (walkSpriteNum == 3) image = left3;
                else if (walkSpriteNum == 4) image = left4;
                else if (walkSpriteNum == 5) image = left5;
                else if (walkSpriteNum == 6) image = left6;
                else if (walkSpriteNum == 7) image = left7;
                else if (walkSpriteNum == 8) image = left8;
            } else if (direction.equals("right")) {
                if (walkSpriteNum == 1) image = right1;
                else if (walkSpriteNum == 2) image = right2;
                else if (walkSpriteNum == 3) image = right3;
                else if (walkSpriteNum == 4) image = right4;
                else if (walkSpriteNum == 5) image = right5;
                else if (walkSpriteNum == 6) image = right6;
                else if (walkSpriteNum == 7) image = right7;
                else if (walkSpriteNum == 8) image = right8;
            }
        }

        g2.drawImage(image, (int) x, (int) y, 48, 48, null);
      //  System.out.println("AM DESENAT PLAYEEEEER LA X="+x/16+" Y= "+y/16);

//        // Hitbox pentru debug
//        g2.setColor(new Color(255, 0, 0, 128));
//        g2.drawRect((int)(x + collisionBox.x), (int)(y + collisionBox.y), collisionBox.width, collisionBox.height);
    }
}

