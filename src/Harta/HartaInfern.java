package Harta;

import Entity.OracolInfern;
import Entity.Orco;
import Entity.Player;
import Entity.Seller;

import Camera.Camera;
import Obiecte.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HartaInfern implements InterfaceHarta {
    private int[][][] mapLayers; //straturi
    private BufferedImage tileset;//imagine cu tileuri
    private final int tileSize = 16;
    private final int tilesPerRow = 3072 / 16; //largime imagine=576, 576/16=36
    private HashMap<Integer, BufferedImage> tileCache; // Cache pentru tile-uri
    //Integer=pentru  ID tile, BufferedImage=img cor. tileID
    //tileCache va inregistra toata poza tile_paradis
    private int layerPlayer = 6;

    private int[][] collisionlayer = new int[103][103];

    public HartaInfern() {
        tileCache = new HashMap<>();  // initializare
        getMapImage(); //se incarca harta
    }


    public int[][] getCollisionlayer() {
        return collisionlayer;
    }

    public boolean isCollidable(int x, int y) {//RET> TRUE DACA E COLIZIIUNE
        if (collisionlayer == null) {
            System.out.println("HARTA NULA");
            return false;
        }
        return collisionlayer[y][x] == 505; //y=rand=vertical
    }

    public int getMapWidth() {
        return collisionlayer.length;
    }

    public void getMapImage() {
        try {
            //incarc tileset
            tileset = ImageIO.read(new File("resources/tileset/tileinferno.png"));
            collisionlayer = loadCSVMap(("resources/layer/inferno/infernopt2._COLLISION.csv"));
            // incarcare pe strat la fiecare layer
            ArrayList<int[][]> layers = new ArrayList<>(); //layers este un array ce contine matricele coresp. fiecarui nivel
            layers.add(loadCSVMap("resources/layer/inferno/infernopt2._base.csv"));

            layers.add(loadCSVMap("resources/layer/inferno/infernopt2._scars.csv"));

            layers.add(loadCSVMap("resources/layer/inferno/infernopt2._subborder.csv"));

            layers.add(loadCSVMap("resources/layer/inferno/infernopt2._suboerd2.csv"));

            layers.add(loadCSVMap("resources/layer/inferno/infernopt2._borders.csv"));
            layers.add(loadCSVMap("resources/layer/inferno/infernopt2._build0visible.csv"));

            layers.add(loadCSVMap("resources/layer/inferno/infernopt2._build1visible.csv"));
            // layers.add(loadCSVMap("resources/layer/inferno_base3.csv"));

            layers.add(loadCSVMap("resources/layer/inferno/infernopt2._build2.csv"));

            layers.add(loadCSVMap("resources/layer/inferno/infernopt2._build3.csv"));
            layers.add(loadCSVMap("resources/layer/inferno/infernopt2._build4.csv"));
            //    layers.add(loadCSVMap(("resources/layer/inferno/infernopt2._COLLISION.csv")));

            mapLayers = layers.toArray(new int[layers.size()][][]);//bag tot in o matrice tridimensionala

        } catch (Exception e) {
            System.out.println("EROARE LA CITIRE");
            e.printStackTrace();
        }
    }

    public int[][] loadCSVMap(String filePath) {

        //formare veector de linii
        try {
            ArrayList<int[]> rows = new ArrayList<>(); // lista cu toate liniiile
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.trim().split(",");// "1,2,3"-> [1][2][3]
                int[] row = new int[tokens.length]; //tokens.length=lungime linie
                for (int i = 0; i < tokens.length; i++) {
                    row[i] = Integer.parseInt(tokens[i]);
                }
                rows.add(row);
            }
            br.close();
            // asamblareaa propriu zisa a matricei
            int[][] map = new int[rows.size()][rows.get(0).length];//eows.get(0)=prima linie //rows.line= rows este lista de []int
            for (int i = 0; i < rows.size(); i++) {
                map[i] = rows.get(i);
            }

            return map;
        } catch (Exception e) {
            System.out.println("EROAREEEEEE" + e);
            return null;
        }
    }

    public BufferedImage getTileFromTileset(int tileID) {
        if (tileID <= 0) return null; // 0 înseamna gol
        //TILEID ESTE "LOCALIZAREA" in fisier .png
        // verific daca tileul nu este in tileCache
        if (!tileCache.containsKey(tileID)) {
            //fiecare linie: linia 1:0 linia 2:36, linia 3:72 etc..

            int tx = (tileID % tilesPerRow) * tileSize; //tileID%tilesPerRow=coloana, index coloana la care se afla tileID
            int ty = (tileID / tilesPerRow) * tileSize; // poz randului
            BufferedImage tile = tileset.getSubimage(tx, ty, tileSize, tileSize);
            tileCache.put(tileID, tile);  // Salvează tile-ul în cache
        }

        return tileCache.get(tileID);
    }

public void draw(Graphics2D g, Player player, ArrayList<Orco> enemies, OracolInfern oracolInfern, Seller seller, MoneyManager moneyManager, Camera camera,Component panel,ArrayList<ObjInterface> itemsOnField) {
    if (mapLayers == null) return;

    int screenWidth = panel.getWidth(); // dimensiune ecran in pixeli
    int screenHeight = panel.getHeight(); // la fel
    int tileSizeScaled = (int) (tileSize * camera.scale); // dimensiune tile dupa scalare

    int startCol = Math.max(0, (int) (camera.x / tileSizeScaled)); //max sa ma asigur ca nu iese
    //+1 pt ca pierd zecimale, ca sa fie vizibil pe ecran
    //mapLayers[0][0].length-->>nr tot de coloane
    int endCol = Math.min(mapLayers[0][0].length, (int) ((camera.x + screenWidth) / tileSizeScaled) + 1);
    int startRow = Math.max(0, (int) (camera.y / tileSizeScaled));
    int endRow = Math.min(mapLayers[0].length, (int) ((camera.y + screenHeight) / tileSizeScaled) + 1);

    for (int layer = 0; layer < mapLayers.length; layer++) {
        int[][] map = mapLayers[layer];

        for (int y = startRow; y < endRow+3; y++) {
            for (int x = startCol; x < endCol+6; x++) {
                if (y >= map.length || x >= map[0].length) continue;
                int tileID = map[y][x];
                BufferedImage tile = getTileFromTileset(tileID);

                if (tile != null) {
                    g.drawImage(tile, x * tileSize, y * tileSize, null);
                }
            }
        }




        if (layerPlayer == layer) {

            moneyManager.removeCollectedMoney();
            moneyManager.drawMoney(g, player);
            for (int i=0;i<enemies.size();i++)
            {
                enemies.get(i).draw(g);
            }




            Iterator<ObjInterface> iterator = itemsOnField.iterator();
            while (iterator.hasNext()) {
                ObjInterface obj = iterator.next();
                obj.draw(g, obj.getX(), obj.getY(), player);
                if (obj.getIsPicked()) {
                    iterator.remove();
                }
            }


            oracolInfern.draw(g);
            seller.draw(g);
            player.draw(g);
        }
    }
}
}
