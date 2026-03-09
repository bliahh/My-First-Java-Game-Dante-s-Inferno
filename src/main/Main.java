package main;




import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;
/*
public class Main extends JPanel {

    private int[][][] mapLayers; // 3D: [layer][y][x]
    private BufferedImage tileset;
    private final int tileSize = 16;
    private final int tilesPerRow = 36; // pentru tileset.png de 256px lățime

    public Main() {
        try {
            // Încarcă tileset
            tileset = ImageIO.read(new File("resources/tileset/tileparadiiiiiiiiiis.png"));

            // Încarcă toate layerele din fișiere CSV
            ArrayList<int[][]> layers = new ArrayList<>();

            layers.add(loadCSVMap("resources/layer/paradis_Livello tile 1.csv"));

            layers.add(loadCSVMap("resources/layer/paradis_copacei.csv"));

            layers.add(loadCSVMap("resources/layer/paradis_NORI.csv"));

            layers.add(loadCSVMap("resources/layer/paradis_tappetino.csv"));

            layers.add(loadCSVMap("resources/layer/paradis_Contructii.csv"));
            layers.add(loadCSVMap("resources/layer/paradis_constructii2.csv"));

            layers.add(loadCSVMap("resources/layer/paradis_poduri.csv"));


            layers.add(loadCSVMap("resources/layer/paradis_Alberii.csv"));

            layers.add(loadCSVMap("resources/layer/paradis_Alberi2.csv"));

            layers.add(loadCSVMap("resources/layer/paradis_Alberi3.csv"));





            // layers.add(loadCSVMap("resources/layer/paradis_Alberii.csv"));
            // layers.add(loadCSVMap("res/mapa_layer2.csv"));
            mapLayers = layers.toArray(new int[layers.size()][][]);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Citește un fișier CSV și returnează matricea tile-urilor
    private int[][] loadCSVMap(String filePath) throws Exception {
        ArrayList<int[]> rows = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = br.readLine()) != null) {
            String[] tokens = line.trim().split(",");
            int[] row = new int[tokens.length];
            for (int i = 0; i < tokens.length; i++) {
                row[i] = Integer.parseInt(tokens[i]);
            }
            rows.add(row);
        }
        br.close();

        int[][] map = new int[rows.size()][rows.get(0).length];
        for (int i = 0; i < rows.size(); i++) {
            map[i] = rows.get(i);
        }

        return map;
    }

    // Extrage tile-ul corespunzător din tileset
    private BufferedImage getTileFromTileset(int tileID) {
        if (tileID <= 0) return null; // 0 înseamnă gol în Tiled

        //tileID--; // în Tiled ID-urile încep de la 1
        int tx = (tileID % tilesPerRow) * tileSize;
        int ty = (tileID / tilesPerRow) * tileSize;

        return tileset.getSubimage(tx, ty, tileSize, tileSize);
    }

    // Desenează harta pe ecran
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (mapLayers == null) return;

        for (int layer = 0; layer < mapLayers.length; layer++) {
            int[][] map = mapLayers[layer];
            for (int y = 0; y < map.length; y++) {
                for (int x = 0; x < map[y].length; x++) {
                    int tileID = map[y][x];
                    BufferedImage tile = getTileFromTileset(tileID);
                    if (tile != null) {
                        g.drawImage(tile, x * tileSize, y * tileSize, null);
                    }
                }
            }
        }
    }

    // Fereastră de lansare
    public static void main(String[] args) {
        JFrame frame = new JFrame("Tiled Map Viewer");
        Main panel = new Main();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(panel);
        frame.setSize(256, 256);
        frame.setResizable(true);
        frame.setVisible(true);
    }
}
*/


//import javax.swing.*;
//
//public class Main {
//    public static void main(String[] args) {
//       final JFrame window = new JFrame("joculetz");
//
//        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        window.setResizable(false);
//
//        MainMenu menu = new MainMenu(window);
//        window.setContentPane(menu);
//
//        window.pack();
//        window.setLocationRelativeTo(null);
//        window.setVisible(true);
//    }
//}



import javax.swing.*;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainMenu());
    }
}
