package Harta;

import Camera.Camera;
import Entity.*;
import Obiecte.MoneyManager;
import Obiecte.ObjInterface;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class HartaParadis implements InterfaceHarta {
    private int[][][] mapLayers; //straturi
    private BufferedImage tileset;//imagine cu tileuri
    private final int tileSize = 16;
    private final int tilesPerRow = 576 / 16; //largime imagine=576, 576/16=36
    private HashMap<Integer, BufferedImage> tileCache; // Cache pentru tile-uri
    //Integer=pentru  ID tile, BufferedImage=img cor. tileID
    //tileCache va inregistra toata poza tile_paradis
    private int layerPlayer = 8;

    private int[][] collisionlayer = new int[79][49];

    private int cameraX = 0; // în tile-uri sau pixeli
    private int cameraY = 0;


    public HartaParadis() {
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
        try {
            return collisionlayer[y][x] == 175;//y=rand=vertical
        } catch (IndexOutOfBoundsException e) {
            System.out.println("AI IESIT DIN HARTA" + e.getMessage());
            return false;
        }
    }

    public int getMapWidth() {
        return collisionlayer.length;
    }

    public void getMapImage() {
        //incarc tileset
        try {
            tileset = ImageIO.read(new File("resources/tileset/tileparadiiiiiiiiiis.png"));
            collisionlayer = loadCSVMap(("resources/layer/paradis/paradis_COLLISIONS.csv"));
            // incarcare pe strat la fiecare layer
            ArrayList<int[][]> layers = new ArrayList<>(); //layers este un array ce contine matricele coresp. fiecarui nivel
            layers.add(loadCSVMap("resources/layer/paradis/paradis_Livello tile 1.csv"));

            layers.add(loadCSVMap("resources/layer/paradis/paradis_copacei.csv"));

            layers.add(loadCSVMap("resources/layer/paradis/paradis_NORI.csv"));

            layers.add(loadCSVMap("resources/layer/paradis/paradis_tappetino.csv"));

            layers.add(loadCSVMap("resources/layer/paradis/paradis_Contructii.csv"));
            layers.add(loadCSVMap("resources/layer/paradis/paradis_poduri.csv"));
            layers.add(loadCSVMap("resources/layer/paradis/paradis_NIVDIE.csv"));
            layers.add(loadCSVMap("resources/layer/paradis/paradis_NIVDIE2.csv"));
            layers.add(loadCSVMap("resources/layer/paradis/paradis_NIVDIE3.csv"));
            //8
            layers.add(loadCSVMap("resources/layer/paradis/paradis_NIVDAV.csv"));
            layers.add(loadCSVMap("resources/layer/paradis/paradis_NIVDAV2.csv"));
            //10

            layers.add(loadCSVMap("resources/layer/paradis/paradis_constructii2.csv"));
            layers.add(loadCSVMap("resources/layer/paradis/paradis_Alberii.csv"));

            layers.add(loadCSVMap("resources/layer/paradis/paradis_Alberi2.csv"));

            layers.add(loadCSVMap("resources/layer/paradis/paradis_Alberi3.csv"));
            //   layers.add(loadCSVMap("resources/layer/paradis/paradis_COLLISIONS.csv"));

            mapLayers = layers.toArray(new int[layers.size()][][]);//bag tot in o matrice tridimensionala

        } catch (Exception e) {
            System.out.println("EROARE LOAD HARTA PARADISS");
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
            int[][] map = new int[rows.size()][rows.size()];//eows.get(0)=prima linie //rows.line= rows este lista de []int
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
        if (tileID <= 0) return null; // 0 înseamna gol în
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

    public void draw(Graphics2D g, Player player, Mike mike, Sotie sotie, Sot sot, OracolParadis oracolParadis, Camera camera, Component panel, ArrayList<ObjInterface> itemsOnField) {
        if (mapLayers == null) return;

        int screenWidth = panel.getWidth(); // dimensiune ecran in pixeli
        int screenHeight = panel.getHeight(); // la fel
        int tileSizeScaled = (int) (tileSize * camera.scale); // dimensiune tile după scalare

        int startCol = Math.max(0, (int) (camera.x / tileSizeScaled)); //max sa ma asigur ca nu iese
        //+1 pt ca pierd zecimale, ca sa fie vizibil pe ecran
        //mapLayers[0][0].length-->>nr tot de coloane
        int endCol = Math.min(mapLayers[0][0].length, (int) ((camera.x + screenWidth) / tileSizeScaled) + 1);
        int startRow = Math.max(0, (int) (camera.y / tileSizeScaled));
        int endRow = Math.min(mapLayers[0].length, (int) ((camera.y + screenHeight) / tileSizeScaled) + 1);

        for (int layer = 0; layer < mapLayers.length; layer++) {
            int[][] map = mapLayers[layer];

            for (int y = startRow; y < endRow + 3; y++) {
                for (int x = startCol; x < endCol + 6; x++) {
                    if (y >= map.length || x >= map[0].length) continue;
                    int tileID = map[y][x];
                    BufferedImage tile = getTileFromTileset(tileID);

                    if (tile != null) {
                        g.drawImage(tile, x * tileSize, y * tileSize, null);
                    }
                }
            }

            Iterator<ObjInterface> iterator = itemsOnField.iterator();
            while (iterator.hasNext()) {
                ObjInterface obj = iterator.next();
                obj.draw(g, obj.getX(), obj.getY(), player);
                if (obj.getIsPicked()) {
                    iterator.remove();
                }
            }

            sot.draw(g);
            mike.draw(g);
            sotie.draw(g);
            player.draw(g);
            oracolParadis.draw(g);
        }
    }
}