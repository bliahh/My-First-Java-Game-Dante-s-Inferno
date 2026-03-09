package main;

import DatabaseManager.DataBaseSaveSlotManager;
import DatabaseManager.SaveData;
import Entity.*;
import Entity.Orco;
import Entity.Player;
import Harta.HartaInfern;
import Harta.HartaParadis;
import Harta.HartaParadis.*;
import Camera.Camera;
import Harta.HartaPurgatoriu;
import Harta.InterfaceHarta;
import Obiecte.*;
import main.inGameMenu.MenuIngame;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class GamePanel extends JPanel implements Runnable {


    public boolean isNewGame=true;
    private SaveData saveData;

    public boolean paused = false;

    public final int originalTileSize = 16;// o asa avem 16x16
    public final int scale = 3;
    public final int tileSize = originalTileSize * scale;
    //how many tiles can be displayed horizzontally and vertically
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenWeight = tileSize * maxScreenRow;

    int ScreenW = getWidth();
    int ScreenH = getHeight();
    int FPS = 60;
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;//keep the progam running until it stop;

    HartaParadis hartaParadis = new HartaParadis();
    HartaInfern hartaInfern = new HartaInfern();
    HartaPurgatoriu hartaPurgatoriu=new HartaPurgatoriu();


    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;
    public ArrayList<Orco> enemies = new ArrayList<>();
    private ArrayList<Ghost> ghostt = new ArrayList<>();
    protected boolean isGameOver = false;

    LiveHealthBar healthBar = new LiveHealthBar();
    Inventory inventory=new Inventory();
    protected boolean isSpawned = false;
    public int levelCounter;
    Camera camera=new Camera();

   private final CharacterFactory<NPC> npcFactory=new NPCFactory();
   private final CharacterFactory<AggroCharacter> AggroFactory=new AggroCharacterFactory();
   private final CharacterFactory<MainCharacter> mainCharFactory=new MainCharacterFactory(keyH);

    public NPC sot = npcFactory.createCharacter("sot",this);
    public NPC sotie = npcFactory.createCharacter("sotie",this);
    public NPC mike =  npcFactory.createCharacter("mike",this);
    NPC seller = npcFactory.createCharacter("seller",this);
    public NPC oracolInfern= npcFactory.createCharacter("oracolinfern",this);
    public NPC oracolParadis= npcFactory.createCharacter("oracolparadis",this);
    public NPC fiu=npcFactory.createCharacter("fiu",this);

    //AggroCharacter orco = AggroFactory.createCharacter("orc",this);
    Player player =(Player) mainCharFactory.createCharacter("player1",this);


    private HealthPotion potionn=new HealthPotion();
    private Ring ring=new Ring();
    public MoneyManager moneyManager = new MoneyManager();
    private KeyOne keyOne = KeyOne.getInstance();
    private KeyTwo keyTwo= KeyTwo.getInstance();
    private KeyThree keyThree=KeyThree.getInstance();
    private Skull skull=new Skull();

    private KeyOne k=KeyOne.getInstance();

    public ArrayList<ObjInterface> itemsOnField=new ArrayList<>();
    public ArrayList<ObjInterface> itemsOnField3=new ArrayList<>();
    public ArrayList<ObjInterface> itemsOnField2=new ArrayList<>();


    MenuIngame menuIngame=new MenuIngame();
    private int selectedResponseInSaveMenu=0;
    private boolean level2spawnedAfterPassed=false;
    private boolean level3spawnedAfterPassed=false;
    private boolean flag2=true,flag1=true;
    public boolean pausedAfterLevel=true;
    private boolean ispassed=true;
    private DataBaseSaveSlotManager db=new DataBaseSaveSlotManager();

    void addObs(){
        player.addObserver((PlayerObserver) seller);
        for(int i=0;i<enemies.size();i++){
            player.addObserver((PlayerObserver) enemies.get(i));
        }


    }

    void SpawnEnemies(InterfaceHarta harta){
        Random random = new Random();
        if(levelCounter==1) {
            for (int i = 0; i < 15; i++) {
                Orco orco = (Orco) AggroFactory.createCharacter("orc", this);
                int x = random.nextInt(96) + 5;
                int y = random.nextInt(96) + 5;
                while (harta.isCollidable(x, y)) {
                    x = random.nextInt(96) + 5;
                    y = random.nextInt(96) + 5;

                }
                orco.setPlayerSpawn(x, y);
                enemies.add(orco);
            }
        }else if(levelCounter==2){
            for (int i = 0; i < 9; i++) {
                Ghost ghost = (Ghost) AggroFactory.createCharacter("ghost", this);
                int x = random.nextInt(20) + 5;
                int y = random.nextInt(20) + 5;
                while (harta.isCollidable(x, y)) {
                     x = random.nextInt(20) + 5;
                     y = random.nextInt(20) + 5;

                }
                ghost.setPlayerSpawn(x, y);
                ghostt.add(ghost);
            }
        }
    }



    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenWeight));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        levelCounter = 1;

        this.addKeyListener(player.keyH);
        this.setFocusable(true);
        this.requestFocusInWindow();


    }
    public void loadSaveData(SaveData saveData) {
        this.saveData=saveData;
        this.isNewGame=false;
    }
    private void applySaveData() {

        if (saveData != null) {

            player.setPlayerSpawn(saveData.posX / 16, saveData.posY / 16);
            player.life = saveData.life;
            player.coins = saveData.coins;
            player.karma = saveData.karma;
            player.setDialogueLevel3Counter(saveData.lvl);
            this.levelCounter= saveData.level;
            moneyManager.allMoney=saveData.coinsOnField;
            enemies=saveData.enemy;
            player.inventory=saveData.inventory;
             itemsOnField=saveData.fieldItems;

            mike.setCurrentDialogueIndex(saveData.Mike);
            sot.setCurrentDialogueIndex(saveData.Sot);
            sotie.setCurrentDialogueIndex(saveData.Sotie);
            oracolParadis.setCurrentDialogueIndex(saveData.Of);
            oracolInfern.setCurrentDialogueIndex(saveData.Oi);
            fiu.setCurrentDialogueIndex(saveData.Son);


        }
    }


    public void startgameThread() {
        gameThread = new Thread(this);
        gameThread.start();

    }

    @Override
    public void run() { //create game loop
        double drawInterval = 1000000000 / FPS; //lucram in nanosecunde 10^9=1 secunda;
        //we will draw the screen FPS time per seconds;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {
            // System.out.println("this shit work");
            // UPDAte;  update information such as character position
            //draw the screen with the updated information;

            update();

            repaint();


            try {
                double remainingTime = nextDrawTime - System.nanoTime(); //cat timp ramane intre 2 drawTime
                remainingTime = remainingTime / 1000000;
                if (remainingTime < 0) {
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }





    private boolean hasReloadGame=false;
    public final ReentrantLock lock=new ReentrantLock();
    public void update() {
      // System.out.println("items: "+itemsOnField.size());

        //setez isNewGame la load data
        if(!isNewGame&&!hasReloadGame){

                applySaveData();
                hasReloadGame=true;
                ispassed=false;
            addObs();
              //  isNewGame=false;
        }

         if(player.keyH.meniuPressed){
             System.out.println("am intrat");
             paused=true;
         }


          if (levelCounter == 1) {
           if(!isSpawned) {
               ispassed=false;
               camera=new Camera(0,0,99,107,player);
               if(isNewGame) {
                   SpawnEnemies(hartaInfern);
                   player.setPlayerSpawn(49, 97);
                   moneyManager.spawnMoney(hartaInfern);
                   itemsOnField.add(keyOne);
                   itemsOnField.add(keyTwo);
                   itemsOnField.add(potionn);
                   itemsOnField.add(keyThree);
               }

               oracolInfern.setPlayerSpawn(47, 49); // 47 50
               seller.setPlayerSpawn(80, 94);

               addObs();
               isSpawned=true;

           }
            if (!player.isTalking&&!paused) {
              //  orco.update(hartaInfern, player);
                for(Orco orco:enemies){
                    orco.update(hartaInfern,player);
                }
              //  potion.updateImg();
                if(!keyOne.getIsPicked()) {
                    keyOne.updateImg();
                }
                if(!keyTwo.getIsPicked()) {
                    keyTwo.updateImg();
                }
                if(!keyThree.getIsPicked()) {
                    keyThree.updateImg();
                }
                potionn.updateImg();
              //  potionnn.updateImg();
                player.update(hartaInfern, enemies);

                oracolInfern.update();
                seller.update();
                camera.update(player);
                int xx=player.countKeys();
//                System.out.println("money: "+player.coins);
//                System.out.println("level: "+player.level1Passed);

            }
          }
          else if(levelCounter==2){
              ispassed=false;
              if(!isSpawned) {



                  SpawnEnemies(hartaPurgatoriu);
                  addObs();
                  if(isNewGame) {
                      moneyManager.spawnMoney(hartaPurgatoriu);
                      player.setPlayerSpawn(15, 29);  //15 29
                   //   itemsOnField2.clear();
//                      itemsOnField2.add(skull);
                      itemsOnField.clear();
                      itemsOnField.add(skull);
                      moneyManager.spawnMoney(hartaPurgatoriu);
                  }
                 // itemsOnField.clear();


                  if(player.level1Passed&&!level2spawnedAfterPassed){
                      player.setPlayerSpawn(15,29);
                      level2spawnedAfterPassed=true;
                      itemsOnField.clear();
                      itemsOnField.add(skull);
                      moneyManager.spawnMoney(hartaPurgatoriu);
                  }
//                  itemsOnField.clear();
//                  itemsOnField.add(skull);

                  camera=new Camera(0,0,24,38,player);
                  seller.setPlayerSpawn(5, 26);

                  fiu.setPlayerSpawn(5,3);
                  isSpawned=true;

              }
              if(!player.isTalking&&!paused) {
                  for(Ghost gh:ghostt){
                      gh.update(hartaPurgatoriu,player);
                  }
                  player.update(hartaPurgatoriu, enemies);
                  camera.update(player);
                  seller.update();
                  fiu.update();

              }
              if (player.life <= 0) {
                  isGameOver = true;
              }
          }
         else if(levelCounter==3){
              ispassed=false;
             if (!isSpawned) {
           // camera=new Camera(0,0,99,83,player);
            oracolParadis.setPlayerSpawn(22,6);
           if(isNewGame) {
               player.setPlayerSpawn(21, 70);
               itemsOnField.clear();
               itemsOnField.add(ring);
           }

            mike.setPlayerSpawn(18,61);
            sot.setPlayerSpawn(30,42);
            sotie.setPlayerSpawn(28,49);
            isSpawned = true;
            camera=new Camera(0,0,99,83,player);
//            itemsOnField.clear();
//            itemsOnField.add(ring);
        }
              if(player.level2Passed&&!level3spawnedAfterPassed){
                  player.setPlayerSpawn(21, 70);
                  itemsOnField.clear();
                  itemsOnField.add(ring);
                  level3spawnedAfterPassed=true;

              }
        if(!player.isTalking&&!paused) {
            mike.update();
            sot.update();
            sotie.update();
            player.update(hartaParadis, enemies);
            camera.update(player);
            oracolParadis.update();
        }
    }

        if (player.life <= 0) {
            isGameOver = true;
        }



    }


    private final ScreenManager screenManager = new ScreenManager();

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;


        if (levelCounter == 1) {
            camera.update(player);
            camera.apply(g2);

            hartaInfern.draw(g2, player, enemies, (OracolInfern) oracolInfern, (Seller) seller, moneyManager, camera, this, itemsOnField);

            if (!isGameOver) {
                oracolInfern.speak(player, g2);
                seller.speak(player, g2);
                healthBar.drawHealthBarAndKarma(player, g2, camera);
                inventory.drawInventory(player, g2, camera, itemsOnField);
            } else {
                screenManager.drawGameOver(g2, player, camera,this,1);
            }
            if (player.level1Passed && !isGameOver) {
                player.isTalking=true;
                screenManager.drawLevelComplete(g2, player, camera,this);



            }
        } else if (levelCounter == 2) {
            try {
                camera.update(player);
                camera.apply(g2);
            } catch (NullPointerException e) {
                System.out.println("camera ar putea sa fie null");
            }
            hartaPurgatoriu.draw(g2, player, (Seller) seller, ghostt, (Son) fiu, moneyManager, camera, this, itemsOnField);

            if (!isGameOver) {
                fiu.speak(player, g2);
                seller.speak(player, g2);
                healthBar.drawHealthBarAndKarma(player, g2, camera);
                inventory.drawInventory(player, g2, camera, itemsOnField);
            } else {
                screenManager.drawGameOver(g2, player, camera,this,2);
            }
            if ((player.level2Passed && !isGameOver)) {
                System.out.println(isGameOver);
                player.isTalking=true;
                screenManager.drawLevelComplete(g2, player, camera,this);
            }

        } else if (levelCounter == 3) {
            camera.update(player);
            camera.apply(g2);

            hartaParadis.draw(g2, player, (Mike) mike, (Sotie) sotie, (Sot) sot, (OracolParadis) oracolParadis, camera, this, itemsOnField);

            oracolParadis.speak(player, g2);
            mike.speak(player, g2);
            sotie.speak(player, g2);
            sot.speak(player, g2);
            healthBar.drawHealthBarAndKarma(player, g2, camera);
            inventory.drawInventory(player, g2, camera, itemsOnField);
            if (isGameOver) {
                screenManager.drawGameOver(g2, player, camera,this,3);
            }
            if(!isGameOver&&((OracolParadis) oracolParadis).getHasTalked()){
                System.out.println("AM INTRAAAATaa");
                screenManager.drawGameEndScreen(g2,player,camera);
                if(player.keyH.attackPressed){
                    db.saveWinnerToDatabase(player.karma);
                }
            }
        }




        if(paused){
          if(menuIngame.loadSaveInterface==false) {
                menuIngame.drawAll(this, player, g2, (int)camera.scale*(camera.getX()/16)+145,(camera.getY()/16) *(int)camera.scale+20);
            }else{
                menuIngame.drawSave(this,player,g2,(int)camera.scale*(camera.getX()/16)+145,(camera.getY()/16) *(int)camera.scale+20);

                paused=true;
            }
        }
        g2.dispose();
    }

}



