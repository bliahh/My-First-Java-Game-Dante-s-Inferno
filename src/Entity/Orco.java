package Entity;




import Collision.CollisionCheck;
import Harta.HartaInfern;
import Harta.InterfaceHarta;
import main.GamePanel;

import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Orco extends Entity implements AggroCharacter,PlayerObserver {
    GamePanel gp;
    //KeyHandler keyH;
    // Camera camera=new Camera();

    BufferedImage tilesetRun;
    public BufferedImage downDeath1, downDeath2, downDeath3, downDeath4, downDeath5, downDeath6;
    public BufferedImage downAttk1, downAttk2, downAttk3, downAttk4, downAttk5, downAttk6, downAttk7, downAttk8;
    public BufferedImage downHurt1, downHurt2, downHurt3, downHurt4;
    public BufferedImage upDeath1, upDeath2, upDeath3, upDeath4, upDeath5, upDeath6;
    public BufferedImage upAttk1, upAttk2, upAttk3, upAttk4, upAttk5, upAttk6, upAttk7, upAttk8;
    public BufferedImage upHurt1, upHurt2, upHurt3, upHurt4;

    public BufferedImage leftDeath1, leftDeath2, leftDeath3, leftDeath4, leftDeath5, leftDeath6;
    public BufferedImage leftAttk1, leftAttk2, leftAttk3, leftAttk4, leftAttk5, leftAttk6, leftAttk7, leftAttk8;
    public BufferedImage leftHurt1, leftHurt2, leftHurt3, leftHurt4;
    public BufferedImage rightDeath1, rightDeath2, rightDeath3, rightDeath4, rightDeath5, rightDeath6;
    public BufferedImage rightAttk1, rightAttk2, rightAttk3, rightAttk4, rightAttk5, rightAttk6, rightAttk7, rightAttk8;
    public BufferedImage rightHurt1, rightHurt2, rightHurt3, rightHurt4;
    private boolean isAttacking = false;
    private boolean isHurt = false;
    public boolean isDead = false;
    private int attackCounter = 0;
    private int attackSpriteNum = 1;
    private final int maxAttackSprite = 8;
    private int attackCooldown = 200;

    private int hurtCounter = 0;
    private int hurtSpriteNum = 1;

    private int hurtDelayCounter = 0; // Contorul pentru întârzierile animației de hurt
    private final int hurtDelayLimit = 20;

    int deathSpriteNum = 1;
    int deathCounter = 0;
    private BufferedImage upDeath7, upDeath8, downDeath7, downDeath8, leftDeath7, leftDeath8, rightDeath7, rightDeath8;
    private boolean karmaIsNotSubstract = false;
    private int counter=80;
    private List<int[]> pathh=new ArrayList<>();
    private int lastPlayerX = -1;
    private int lastPlayerY = -1;

    @Override
    public void updateObs(Player player) {

        if (player.karma < 0&&player.karma>-100) {
            attack = 3;
        } else if(player.karma>=0) {
            attack = 2;
        }
        if(player.karma<=-100){
            attack=4;
        }

    }


    public Orco(GamePanel gp) {
        this.gp = gp;
        //  this.keyH=keyH;
        //  this.camera=camera;
        //  this.camera=camera;
        x = 200;
        y = 200;
        speed = 0.29;
        direction = "down";
        life = 40;
        attack = 3;
        getPlayerImage();
    }


    @Override
    public void getPlayerImage() {

        try {
            tilesetRun = ImageIO.read(new File("resources/Sprites/Orc2_run_full.png"));

            int tileSize = 64;
            int scale = tileSize / 4;

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


            downAttk1 = tilesetRun.getSubimage(0 * tileSize, 8 * tileSize, tileSize, tileSize);
            downAttk2 = tilesetRun.getSubimage(1 * tileSize, 8 * tileSize, tileSize, tileSize);
            downAttk3 = tilesetRun.getSubimage(2 * tileSize, 8 * tileSize, tileSize, tileSize);
            downAttk4 = tilesetRun.getSubimage(3 * tileSize, 8 * tileSize, tileSize, tileSize);
            downAttk5 = tilesetRun.getSubimage(4 * tileSize, 8 * tileSize, tileSize, tileSize);
            downAttk6 = tilesetRun.getSubimage(5 * tileSize, 8 * tileSize, tileSize, tileSize);
            downAttk7 = tilesetRun.getSubimage(6 * tileSize, 8 * tileSize, tileSize, tileSize);
            downAttk8 = tilesetRun.getSubimage(7 * tileSize, 8 * tileSize, tileSize, tileSize);

            upAttk1 = tilesetRun.getSubimage(0 * tileSize, 9 * tileSize, tileSize, tileSize);
            upAttk2 = tilesetRun.getSubimage(1 * tileSize, 9 * tileSize, tileSize, tileSize);
            upAttk3 = tilesetRun.getSubimage(2 * tileSize, 9 * tileSize, tileSize, tileSize);
            upAttk4 = tilesetRun.getSubimage(3 * tileSize, 9 * tileSize, tileSize, tileSize);
            upAttk5 = tilesetRun.getSubimage(4 * tileSize, 9 * tileSize, tileSize, tileSize);
            upAttk6 = tilesetRun.getSubimage(5 * tileSize, 9 * tileSize, tileSize, tileSize);
            upAttk7 = tilesetRun.getSubimage(6 * tileSize, 9 * tileSize, tileSize, tileSize);
            upAttk8 = tilesetRun.getSubimage(7 * tileSize, 9 * tileSize, tileSize, tileSize);

            leftAttk1 = tilesetRun.getSubimage(0 * tileSize, 10 * tileSize, tileSize, tileSize);
            leftAttk2 = tilesetRun.getSubimage(1 * tileSize, 10 * tileSize, tileSize, tileSize);
            leftAttk3 = tilesetRun.getSubimage(2 * tileSize, 10 * tileSize, tileSize, tileSize);
            leftAttk4 = tilesetRun.getSubimage(3 * tileSize, 10 * tileSize, tileSize, tileSize);
            leftAttk5 = tilesetRun.getSubimage(4 * tileSize, 10 * tileSize, tileSize, tileSize);
            leftAttk6 = tilesetRun.getSubimage(5 * tileSize, 10 * tileSize, tileSize, tileSize);
            leftAttk7 = tilesetRun.getSubimage(6 * tileSize, 10 * tileSize, tileSize, tileSize);
            leftAttk8 = tilesetRun.getSubimage(7 * tileSize, 10 * tileSize, tileSize, tileSize);

            rightAttk1 = tilesetRun.getSubimage(0 * tileSize, 11 * tileSize, tileSize, tileSize);
            rightAttk2 = tilesetRun.getSubimage(1 * tileSize, 11 * tileSize, tileSize, tileSize);
            rightAttk3 = tilesetRun.getSubimage(2 * tileSize, 11 * tileSize, tileSize, tileSize);
            rightAttk4 = tilesetRun.getSubimage(3 * tileSize, 11 * tileSize, tileSize, tileSize);
            rightAttk5 = tilesetRun.getSubimage(4 * tileSize, 11 * tileSize, tileSize, tileSize);
            rightAttk6 = tilesetRun.getSubimage(5 * tileSize, 11 * tileSize, tileSize, tileSize);
            rightAttk7 = tilesetRun.getSubimage(6 * tileSize, 11 * tileSize, tileSize, tileSize);
            rightAttk8 = tilesetRun.getSubimage(7 * tileSize, 11 * tileSize, tileSize, tileSize);

            downDeath1 = tilesetRun.getSubimage(0 * tileSize, 12 * tileSize, tileSize, tileSize);
            downDeath2 = tilesetRun.getSubimage(1 * tileSize, 12 * tileSize, tileSize, tileSize);
            downDeath3 = tilesetRun.getSubimage(2 * tileSize, 12 * tileSize, tileSize, tileSize);
            downDeath4 = tilesetRun.getSubimage(3 * tileSize, 12 * tileSize, tileSize, tileSize);
            downDeath5 = tilesetRun.getSubimage(4 * tileSize, 12 * tileSize, tileSize, tileSize);
            downDeath6 = tilesetRun.getSubimage(5 * tileSize, 12 * tileSize, tileSize, tileSize);
            downDeath7 = tilesetRun.getSubimage(6 * tileSize, 12 * tileSize, tileSize, tileSize);
            downDeath8 = tilesetRun.getSubimage(7 * tileSize, 12 * tileSize, tileSize, tileSize);

            upDeath1 = tilesetRun.getSubimage(0 * tileSize, 13 * tileSize, tileSize, tileSize);
            upDeath2 = tilesetRun.getSubimage(1 * tileSize, 13 * tileSize, tileSize, tileSize);
            upDeath3 = tilesetRun.getSubimage(2 * tileSize, 13 * tileSize, tileSize, tileSize);
            upDeath4 = tilesetRun.getSubimage(3 * tileSize, 13 * tileSize, tileSize, tileSize);
            upDeath5 = tilesetRun.getSubimage(4 * tileSize, 13 * tileSize, tileSize, tileSize);
            upDeath6 = tilesetRun.getSubimage(5 * tileSize, 13 * tileSize, tileSize, tileSize);
            upDeath7 = tilesetRun.getSubimage(6 * tileSize, 13 * tileSize, tileSize, tileSize);
            upDeath8 = tilesetRun.getSubimage(7 * tileSize, 13 * tileSize, tileSize, tileSize);

            leftDeath1 = tilesetRun.getSubimage(0 * tileSize, 14 * tileSize, tileSize, tileSize);
            leftDeath2 = tilesetRun.getSubimage(1 * tileSize, 14 * tileSize, tileSize, tileSize);
            leftDeath3 = tilesetRun.getSubimage(2 * tileSize, 14 * tileSize, tileSize, tileSize);
            leftDeath4 = tilesetRun.getSubimage(3 * tileSize, 14 * tileSize, tileSize, tileSize);
            leftDeath5 = tilesetRun.getSubimage(4 * tileSize, 14 * tileSize, tileSize, tileSize);
            leftDeath6 = tilesetRun.getSubimage(5 * tileSize, 14 * tileSize, tileSize, tileSize);
            leftDeath7 = tilesetRun.getSubimage(6 * tileSize, 14 * tileSize, tileSize, tileSize);
            leftDeath8 = tilesetRun.getSubimage(7 * tileSize, 14 * tileSize, tileSize, tileSize);


            rightDeath1 = tilesetRun.getSubimage(0 * tileSize, 15 * tileSize, tileSize, tileSize);
            rightDeath2 = tilesetRun.getSubimage(1 * tileSize, 15 * tileSize, tileSize, tileSize);
            rightDeath3 = tilesetRun.getSubimage(2 * tileSize, 15 * tileSize, tileSize, tileSize);
            rightDeath4 = tilesetRun.getSubimage(3 * tileSize, 15 * tileSize, tileSize, tileSize);
            rightDeath5 = tilesetRun.getSubimage(4 * tileSize, 15 * tileSize, tileSize, tileSize);
            rightDeath6 = tilesetRun.getSubimage(5 * tileSize, 15 * tileSize, tileSize, tileSize);
            rightDeath7 = tilesetRun.getSubimage(6 * tileSize, 15 * tileSize, tileSize, tileSize);
            rightDeath8 = tilesetRun.getSubimage(7 * tileSize, 15 * tileSize, tileSize, tileSize);

            downHurt1 = tilesetRun.getSubimage(0 * tileSize, 16 * tileSize, tileSize, tileSize);
            downHurt2 = tilesetRun.getSubimage(1 * tileSize, 16 * tileSize, tileSize, tileSize);
            downHurt3 = tilesetRun.getSubimage(2 * tileSize, 16 * tileSize, tileSize, tileSize);
            downHurt4 = tilesetRun.getSubimage(3 * tileSize, 16 * tileSize, tileSize, tileSize);

            upHurt1 = tilesetRun.getSubimage(0 * tileSize, 17 * tileSize, tileSize, tileSize);
            upHurt2 = tilesetRun.getSubimage(1 * tileSize, 17 * tileSize, tileSize, tileSize);
            upHurt3 = tilesetRun.getSubimage(2 * tileSize, 17 * tileSize, tileSize, tileSize);
            upHurt4 = tilesetRun.getSubimage(3 * tileSize, 17 * tileSize, tileSize, tileSize);

            leftHurt1 = tilesetRun.getSubimage(0 * tileSize, 18 * tileSize, tileSize, tileSize);
            leftHurt2 = tilesetRun.getSubimage(1 * tileSize, 18 * tileSize, tileSize, tileSize);
            leftHurt3 = tilesetRun.getSubimage(2 * tileSize, 18 * tileSize, tileSize, tileSize);
            leftHurt4 = tilesetRun.getSubimage(3 * tileSize, 18 * tileSize, tileSize, tileSize);


            rightHurt1 = tilesetRun.getSubimage(0 * tileSize, 19 * tileSize, tileSize, tileSize);
            rightHurt2 = tilesetRun.getSubimage(1 * tileSize, 19 * tileSize, tileSize, tileSize);
            rightHurt3 = tilesetRun.getSubimage(2 * tileSize, 19 * tileSize, tileSize, tileSize);
            rightHurt4 = tilesetRun.getSubimage(3 * tileSize, 19 * tileSize, tileSize, tileSize);

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
        if (isHurt) return;

        double dx = entity.x - x;
        double dy = entity.y - y;
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance < 16) {
            this.life -= entity.attack;
            isHurt = true;
            hurtCounter = 0;
            hurtDelayCounter = 0;
            hurtSpriteNum = 1;
            System.out.println("ORCU A LUAT DAMAGE");
        }
    }

    @Override
    public void update(InterfaceHarta harta, Player player) {
        CollisionCheck cc = new CollisionCheck();

        double dx = player.x - x;
        double dy = player.y - y;
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (life <= 0 && !isDead) {
            isDead = true;
            deathSpriteNum = 1;
            deathCounter = 0;
        }

        if (isDead) {
            deathCounter++;
            if (deathCounter % 20 == 0) { //la fiecare 20 de frame act.
                deathSpriteNum++;
                if (deathSpriteNum > 8) {
                    deathSpriteNum = 8;

                }
            }
            if (!karmaIsNotSubstract) {
                player.karma -= 20;
                karmaIsNotSubstract = true;
            }
        } else {
            if (isHurt) {
                hurtCounter++;

                if (hurtCounter % 6 == 0) { //urmatorul sprite
                    hurtSpriteNum++;
                    if (hurtSpriteNum > 4)
                        hurtSpriteNum = 1;
                }


                if (hurtCounter > 24) {
                    isHurt = false;
                    hurtCounter = 0;
                    hurtSpriteNum = 1;
                    hurtDelayCounter = 0;
                }

                return;
            }


            if (attackCooldown > 0) attackCooldown--;


            if (isAttacking) {
                attackCounter++;


                if (attackCounter == 3 && distance <= 16) {
                    player.getHurt(this);
                    System.out.println("LIFE PLAYER: " + player.life);
                    System.out.println("Orcul a lovit!");
                }

                if (attackCounter > 10) {
                    attackSpriteNum++;
                    attackCounter = 0;
                }

                if (attackSpriteNum > 6) {
                    attackSpriteNum = 1;
                    isAttacking = false;
                    attackCooldown = 60;
                }

                return;
            }


            if (distance <= 16 && attackCooldown == 0) {
                isAttacking = true;
                attackCounter = 0;
                attackSpriteNum = 1;
                return;
            }

            if (distance < 320) {
                if (Math.abs(dx) > Math.abs(dy)) {
                    if (dx > 0) {
                        direction = "right";
                        if (!cc.checkCollision(this, harta)) x += speed;
                        else cc.avoidCollision(harta, this);
                    } else {
                        direction = "left";
                        if (!cc.checkCollision(this, harta)) x -= speed;
                        else cc.avoidCollision(harta, this);
                    }
                } else {
                    if (dy > 0) {
                        direction = "down";
                        if (!cc.checkCollision(this, harta)) y += speed;
                        else cc.avoidCollision(harta, this);
                    } else {
                        direction = "up";
                        if (!cc.checkCollision(this, harta)) y -= speed;
                        else cc.avoidCollision(harta, this);
                    }
                }


                spriteCounter++;
                if (spriteCounter > 11) {
                    walkSpriteNum = (walkSpriteNum % 8) + 1;
                    spriteCounter = 0;
                }
                System.out.println("ATACUT E DE :"+this.attack);
            }
        }
    }


        @Override
        public void draw (Graphics2D g2){
            BufferedImage image = null;

            if (isDead) {
                switch (direction) {
                    case "down":
                        if (deathSpriteNum == 1) image = downDeath1;
                        else if (deathSpriteNum == 2) image = downDeath2;
                        else if (deathSpriteNum == 3) image = downDeath3;
                        else if (deathSpriteNum == 4) image = downDeath4;
                        else if (deathSpriteNum == 5) image = downDeath5;
                        else if (deathSpriteNum == 6) image = downDeath6;
                        else if (deathSpriteNum == 7) image = downDeath7;
                        else if (deathSpriteNum == 8) image = downDeath8;
                        break;

                    case "up":
                        if (deathSpriteNum == 1) image = upDeath1;
                        else if (deathSpriteNum == 2) image = upDeath2;
                        else if (deathSpriteNum == 3) image = upDeath3;
                        else if (deathSpriteNum == 4) image = upDeath4;
                        else if (deathSpriteNum == 5) image = upDeath5;
                        else if (deathSpriteNum == 6) image = upDeath6;
                        else if (deathSpriteNum == 7) image = upDeath7;
                        else if (deathSpriteNum == 8) image = upDeath8;
                        break;

                    case "left":
                        if (deathSpriteNum == 1) image = leftDeath1;
                        else if (deathSpriteNum == 2) image = leftDeath2;
                        else if (deathSpriteNum == 3) image = leftDeath3;
                        else if (deathSpriteNum == 4) image = leftDeath4;
                        else if (deathSpriteNum == 5) image = leftDeath5;
                        else if (deathSpriteNum == 6) image = leftDeath6;
                        else if (deathSpriteNum == 7) image = leftDeath7;
                        else if (deathSpriteNum == 8) image = leftDeath8;
                        break;

                    case "right":
                        if (deathSpriteNum == 1) image = rightDeath1;
                        else if (deathSpriteNum == 2) image = rightDeath2;
                        else if (deathSpriteNum == 3) image = rightDeath3;
                        else if (deathSpriteNum == 4) image = rightDeath4;
                        else if (deathSpriteNum == 5) image = rightDeath5;
                        else if (deathSpriteNum == 6) image = rightDeath6;
                        else if (deathSpriteNum == 7) image = rightDeath7;
                        else if (deathSpriteNum == 8) image = rightDeath8;
                        break;
                }
            } else if (isHurt) {
                System.out.println("SUNT AICI");
                if (direction.equals("down")) {
                    if (hurtSpriteNum == 1) image = downHurt1;
                    else if (hurtSpriteNum == 2) image = downHurt2;
                    else if (hurtSpriteNum == 3) image = downHurt3;
                    else if (hurtSpriteNum == 4) image = downHurt4;
                } else if (direction.equals("up")) {
                    if (hurtSpriteNum == 1) image = upHurt1;
                    else if (hurtSpriteNum == 2) image = upHurt2;
                    else if (hurtSpriteNum == 3) image = upHurt3;
                    else if (hurtSpriteNum == 4) image = upHurt4;
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
            } else if (isAttacking) {
                if (direction.equals("down")) {
                    if (attackSpriteNum == 1) image = downAttk1;
                    else if (attackSpriteNum == 2) image = downAttk2;
                    else if (attackSpriteNum == 3) image = downAttk3;
                    else if (attackSpriteNum == 4) image = downAttk4;
                    else if (attackSpriteNum == 5) image = downAttk5;
                    else if (attackSpriteNum == 6) image = downAttk6;
                    else if (attackSpriteNum == 7) image = downAttk7;
                    else if (attackSpriteNum == 8) image = downAttk8;
                    else image = downAttk1;
                } else if (direction.equals("up")) {
                    if (attackSpriteNum == 1) image = upAttk1;
                    else if (attackSpriteNum == 2) image = upAttk2;
                    else if (attackSpriteNum == 3) image = upAttk3;
                    else if (attackSpriteNum == 4) image = upAttk4;
                    else if (attackSpriteNum == 5) image = upAttk5;
                    else if (attackSpriteNum == 6) image = upAttk6;
                    else if (attackSpriteNum == 7) image = upAttk7;
                    else if (attackSpriteNum == 8) image = upAttk8;
                    else image = upAttk1;
                } else if (direction.equals("left")) {
                    if (attackSpriteNum == 1) image = leftAttk1;
                    else if (attackSpriteNum == 2) image = leftAttk2;
                    else if (attackSpriteNum == 3) image = leftAttk3;
                    else if (attackSpriteNum == 4) image = leftAttk4;
                    else if (attackSpriteNum == 5) image = leftAttk5;
                    else if (attackSpriteNum == 6) image = leftAttk6;
                    else if (attackSpriteNum == 7) image = leftAttk7;
                    else if (attackSpriteNum == 8) image = leftAttk8;
                    else image = leftAttk1;
                } else if (direction.equals("right")) {
                    if (attackSpriteNum == 1) image = rightAttk1;
                    else if (attackSpriteNum == 2) image = rightAttk2;
                    else if (attackSpriteNum == 3) image = rightAttk3;
                    else if (attackSpriteNum == 4) image = rightAttk4;
                    else if (attackSpriteNum == 5) image = rightAttk5;
                    else if (attackSpriteNum == 6) image = rightAttk6;
                    else if (attackSpriteNum == 7) image = rightAttk7;
                    else if (attackSpriteNum == 8) image = rightAttk8;
                    else image = rightAttk1;
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

            if (image != null) {
                g2.drawImage(image, (int) x, (int) y, gp.tileSize, gp.tileSize, null);
            }

//            g2.setColor(new Color(255, 0, 0, 128));
//            g2.drawRect(
//                    (int) (x + collisionBox.x),
//                    (int) (y + collisionBox.y),
//                    collisionBox.width,
//                    collisionBox.height
//            );
        }

    }

