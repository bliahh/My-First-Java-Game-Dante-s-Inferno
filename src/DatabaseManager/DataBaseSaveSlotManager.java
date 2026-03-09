package DatabaseManager;

import Entity.Orco;
import Obiecte.Money;
import Obiecte.ObjInterface;
import Obiecte.KeyOne;
import Obiecte.KeyTwo;
import Obiecte.KeyThree;
import Obiecte.Ring;
import Obiecte.Skull;
import Obiecte.HealthPotion;
import Obiecte.InutilPotion;
import Obiecte.SpeedPotion;
import main.GamePanel;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DataBaseSaveSlotManager {
    public static final String DB_URL = "jdbc:sqlite:resources/database/savedStatess.db";
    private final Lock lock = new ReentrantLock();

    public DataBaseSaveSlotManager() {
        createTableIfNotExists();
    }

    private  void createTableIfNotExists() {
        lock.lock();
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(DB_URL);
            Statement stmt = conn.createStatement();

            String playerSQL = """
                CREATE TABLE IF NOT EXISTS PLAYER_INFO (
                    slot_id INTEGER PRIMARY KEY,
                    level INTEGER,
                    life INTEGER,
                    coins INTEGER,
                    karma INTEGER,
                    pos_x INTEGER,
                    pos_y INTEGER,
                    indexlvl3 INTEGER
               
                );
            """;

            String coinsSQL = """
                CREATE TABLE IF NOT EXISTS COINS (
                    slot_id INTEGER,
                    x INTEGER,
                    y INTEGER,
                    picked BOOLEAN,
                    PRIMARY KEY (slot_id, x, y)
                );
            """;

                        String orcsSQL = """
                CREATE TABLE IF NOT EXISTS ORCS (
                    slot_id INTEGER,
                    x INTEGER,
                    y INTEGER,
                    alive BOOLEAN,
                    PRIMARY KEY (slot_id, x, y)
                );
            """;


            String inventorySQL = """
                CREATE TABLE IF NOT EXISTS INVENTORY (
                    slot_id INTEGER,
                    item_name TEXT,
                    quantity INTEGER,
                    PRIMARY KEY (slot_id, item_name)
                );
            """;
            String fieldItemsSQL = """
    CREATE TABLE IF NOT EXISTS FIELDD (
        slot_id INTEGER,
        x INTEGER,
        y INTEGER,
        item_name TEXT,
        PRIMARY KEY (slot_id, x, y)
    );
""";

            String dialogueSQL = """
                  
            CREATE TABLE IF NOT EXISTS DIALOGUE_INDEX (
                    slot_id INTEGER,
                    entity_name TEXT,
                    dialogue_index INTEGER,
                    PRIMARY KEY (slot_id, entity_name)
);
            """;

            String WINsql = """
        CREATE TABLE IF NOT EXISTS WINNERS (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            name TEXT NOT NULL,
            karma INTEGER NOT NULL
        );
    """;

            stmt.execute(WINsql);
            stmt.execute(fieldItemsSQL);
            stmt.execute(dialogueSQL);
            stmt.execute(playerSQL);
            stmt.execute(coinsSQL);
            stmt.execute(inventorySQL);
            stmt.execute(orcsSQL);

            System.out.println("Tables created successfully");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            lock.unlock();
        }
    }

    //SALVARE PT PLAYERR
    public  void saveToSlot(int slotId, int level, int life, int coins, int karma, int x, int y,int indexlvl3) {
        clearSlotData(slotId);

        String sql = """
            REPLACE INTO PLAYER_INFO (slot_id, level, life, coins, karma, pos_x, pos_y,indexlvl3)
            VALUES (?, ?, ?, ?, ?, ?, ?,?);
        """;

        lock.lock();
        try  {
            Connection conn = DriverManager.getConnection(DB_URL);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, slotId);
            pstmt.setInt(2, level);
            pstmt.setInt(3, life);
            pstmt.setInt(4, coins);
            pstmt.setInt(5, karma);
            pstmt.setInt(6, x);
            pstmt.setInt(7, y);
            pstmt.setInt(8,indexlvl3);
            pstmt.executeUpdate();

            System.out.println("Game saved in slot " + slotId);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public  void saveCoins(int slotId, ArrayList<Money> coinsList) {
        String sql = """
            REPLACE INTO COINS (slot_id, x, y, picked)
            VALUES (?, ?, ?, ?);
        """;

        lock.lock();
        try  {
            Connection conn = DriverManager.getConnection(DB_URL);
            for (Money m : coinsList) {
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setInt(1, slotId);
                    pstmt.setInt(2, m.x);
                    pstmt.setInt(3, m.y);
                    pstmt.setBoolean(4, m.picked);
                    pstmt.executeUpdate();
                }
            }

            System.out.println("Coins saved for slot " + slotId);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public  ArrayList<Money> loadCoins(int slotId) {
        ArrayList<Money> coins = new ArrayList<>();
        String sql = "SELECT x, y, picked FROM COINS WHERE slot_id = ?";

        lock.lock();
        try  {
            Connection conn = DriverManager.getConnection(DB_URL);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, slotId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Money m = new Money();
                m.x = rs.getInt("x");
                m.y = rs.getInt("y");
                m.picked = rs.getBoolean("picked");
                coins.add(m);
            }

            System.out.println("Coins loaded for slot " + slotId);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        return coins;
    }

    public  void saveOrcs(int slotId, ArrayList<Orco> orcs) {
        String sql = """
            REPLACE INTO ORCS (slot_id, x, y, alive)
            VALUES (?, ?, ?, ?);
        """;

        lock.lock();
        try  {
            Connection conn = DriverManager.getConnection(DB_URL);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            for (Orco o : orcs) {
                pstmt.setInt(1, slotId);
                pstmt.setInt(2,(int) o.x);
                pstmt.setInt(3, (int)o.y);
                pstmt.setBoolean(4, o.isDead);
                pstmt.executeUpdate();
            }

            System.out.println("Orcs saved for slot " + slotId);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public  ArrayList<Orco> loadOrcs(int slotId, GamePanel gp) {
        ArrayList<Orco> orcs = new ArrayList<>();
        String sql = "SELECT x, y, alive FROM ORCS WHERE slot_id = ?";

        lock.lock();
        try  {
            Connection conn = DriverManager.getConnection(DB_URL);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, slotId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Orco o = new Orco(gp);
                o.x = rs.getInt("x");
                o.y = rs.getInt("y");
                o.isDead = rs.getBoolean("alive");
                orcs.add(o);
            }

            System.out.println("Orcs loaded for slot " + slotId);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        return orcs;
    }








    public  void saveInventory(int slotId, HashMap<ObjInterface, Integer> inventory) {
        String sql = """
            REPLACE INTO INVENTORY (slot_id, item_name, quantity)
            VALUES (?, ?, ?);
        """;

        lock.lock();
        try  {
            Connection conn = DriverManager.getConnection(DB_URL);
            for (ObjInterface item : inventory.keySet()) {
                int quantity = inventory.get(item);
                try  {
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setInt(1, slotId);
                    pstmt.setString(2, item.getClass().getSimpleName());
                    pstmt.setInt(3, quantity);
                    pstmt.executeUpdate();
                }catch (SQLException e){
                    System.out.println("EROARE SAVE INVENTORY");
                }
            }
             System.out.println("Inventory saved for slot " + slotId);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public  HashMap<ObjInterface, Integer> loadInventory(int slotId) {
        HashMap<ObjInterface, Integer> inventory = new HashMap<>();
        String sql = "SELECT item_name, quantity FROM INVENTORY WHERE slot_id = ?";

        lock.lock();
        try  {

            Connection conn = DriverManager.getConnection(DB_URL);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, slotId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("item_name");
                int quantity = rs.getInt("quantity");
                ObjInterface item = createItemByName(name);

                if (item != null) {
                    System.out.println("AM INCARCAT "+item);
                    inventory.put(item, quantity);
                }
            }

            System.out.println("Inventory loaded for slot " + slotId);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        return inventory;
    }

    private ObjInterface createItemByName(String name) {
       System.out.println("incarrc name: "+name);
        return switch (name) {
            case "Skull" -> new Skull();
            case "KeyOne" -> KeyOne.getInstance();
            case "KeyTwo" -> KeyTwo.getInstance();
            case "KeyThree" -> KeyThree.getInstance();
            case "Ring" -> new Ring();
            case "HealthPotion" -> new HealthPotion();
            case "InutilPotion" -> new InutilPotion();
            case "SpeedPotion" -> new SpeedPotion();
            default -> null;
        };
    }

    public  SaveData loadFromSlot(int slotId, GamePanel gp) {
        String sql = "SELECT * FROM PLAYER_INFO WHERE slot_id = ?";
       lock.lock();
        try  {
            Connection conn = DriverManager.getConnection(DB_URL);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, slotId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new SaveData(
                        rs.getInt("level"),
                        rs.getInt("life"),
                        rs.getInt("coins"),
                        rs.getInt("karma"),
                        rs.getInt("pos_x"),
                        rs.getInt("pos_y"),
                        rs.getInt("indexlvl3"),
                        loadCoins(slotId),
                        loadOrcs(slotId,gp),
                        loadInventory(slotId),
                        loadFieldItems(slotId),
                        loadDialogueIndex(slotId, "Mike"),
                        loadDialogueIndex(slotId, "Son"),
                        loadDialogueIndex(slotId, "Sot"),
                        loadDialogueIndex(slotId, "OracolInfern"),
                        loadDialogueIndex(slotId, "OracolParadis"),
                        loadDialogueIndex(slotId, "Sotie")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        return null;
    }

    public synchronized int loadDialogueIndex(int slotId, String entityName) {
        String sql = "SELECT dialogue_index FROM DIALOGUE_INDEX WHERE slot_id = ? AND entity_name = ?";

        lock.lock();
        try  {
            Connection conn = DriverManager.getConnection(DB_URL);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, slotId);
            pstmt.setString(2, entityName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("dialogue_index");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return -1;
    }



    public  void saveDialogueIndex(int slotId, String entityName, int dialogueIndex) {
        String sql = """
        REPLACE INTO DIALOGUE_INDEX (slot_id, entity_name, dialogue_index)
        VALUES (?, ?, ?);
    """;

        lock.lock();
        try  {
            Connection conn = DriverManager.getConnection(DB_URL);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, slotId);
            pstmt.setString(2, entityName);
            pstmt.setInt(3, dialogueIndex);
            pstmt.executeUpdate();

            System.out.println(entityName + " dialogue index saved for slot " + slotId);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }







    private  void clearSlotData(int slotId) {
        String[] tables = {"COINS", "ORCS", "INVENTORY"};

        lock.lock();
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            for (String table : tables) {
                try (PreparedStatement pstmt = conn.prepareStatement("DELETE FROM " + table + " WHERE slot_id = ?")) {
                    pstmt.setInt(1, slotId);
                    pstmt.executeUpdate();
                }
            }
            System.out.println("Old data cleared for slot " + slotId);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            lock.unlock();
        }
    }


    public  void saveFieldItems(int slotId, ArrayList<ObjInterface> itemsOnField) {

        lock.lock();
        try  {
            Connection conn = DriverManager.getConnection(DB_URL);
            String table="FIELDD";
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM " + table + " WHERE slot_id = ?");
            pstmt.setInt(1, slotId);
            pstmt.executeUpdate();
        }catch (SQLException e){
            System.out.println("EXCEPTIE");
        }finally {
            lock.unlock();
        }


        String sql = """
    INSERT OR REPLACE INTO FIELDD (slot_id, x, y, item_name)
    VALUES (?, ?, ?, ?);
""";

       // lock.lock();
        try  {
            Connection conn = DriverManager.getConnection(DB_URL);
            System.out.println("CONN: "+conn);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            for (ObjInterface item : itemsOnField) {
             //   System.out.println("SALVEZZZ: "+item.getClass().getSimpleName()+"in slot: "+slotId+" "+item.getX()+" "+item.getY());

                    pstmt.setInt(1, slotId);
                    pstmt.setInt(2, item.getX());
                    pstmt.setInt(3, item.getY());
                    pstmt.setString(4, item.getClass().getSimpleName());
                    int x=pstmt.executeUpdate();
                    System.out.println("EXECUATTA: "+x);
            }
            pstmt.close();
            conn.close();
            System.out.println("Field items saved for slot " + slotId);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("NU MERGE SAAAAVE");
        } finally {
         //   lock.unlock();
        }
    }


    public  ArrayList<ObjInterface> loadFieldItems(int slotId) {
        ArrayList<ObjInterface> items = new ArrayList<>();

        String sql = "SELECT x, y, item_name FROM FIELDD WHERE slot_id = ?";

        //lock.lock();
        try  {
            Connection conn = DriverManager.getConnection(DB_URL);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, slotId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("item_name");
                ObjInterface item = createItemByName(name);

                if (item != null) {
                    item.setX(rs.getInt("x"));
                    item.setY(rs.getInt("y"));
                    items.add(item);
                }
            }
            conn.close();
            System.out.println("Field items loaded for slot " + slotId);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        //    lock.unlock();
        }

        return items;
    }

        public  void saveWinnerToDatabase(int karma) {

           lock.lock();
            //POP-UPPP
            String name = JOptionPane.showInputDialog(null, "Congratulations! Enter your name:", "Player", JOptionPane.PLAIN_MESSAGE);
            //daca se inchide fereastra || jucator apasa cancel
            if (name == null || name.trim().isEmpty()) {
                System.out.println("Nume invalid. Salvarea a fost anulată.");
                return;
            }

            String sql = "INSERT INTO WINNERS (name, karma) VALUES (?, ?)";

            try  {
                Connection conn = DriverManager.getConnection(DB_URL);
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, name.trim());
                pstmt.setInt(2, karma);
                pstmt.executeUpdate();

                System.out.println("Winner saved: " + name + " (karma: " + karma + ")");
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }

            System.exit(0);

    }




}


