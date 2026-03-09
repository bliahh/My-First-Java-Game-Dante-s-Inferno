package Obiecte;

import Entity.Player;
import Harta.HartaInfern;
import Harta.InterfaceHarta;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class MoneyManager {
    public ArrayList<Money> allMoney = new ArrayList<>();

    public void spawnMoney(InterfaceHarta harta) {
        Random random = new Random();
        int n = (harta.getClass().getSimpleName().equals("HartaInfern")) ? 300 : 20;
        System.out.println("AM SPAWNAT "+n+" coin");
        if(harta instanceof HartaInfern) {
            for (int i = 0; i < n; i++) {
                Money m = new Money();
                int x = random.nextInt(96) + 5;
                int y = random.nextInt(96) + 5;

                while (harta.isCollidable(x, y)) {
                    x = random.nextInt(96) + 5;
                    y = random.nextInt(96) + 5;
                }

                m.x = x;
                m.y = y;
                allMoney.add(m);
            }
        }else {
                for (int i = 0; i < n; i++) {
                    Money m = new Money();
                    int x = random.nextInt(25) + 5;
                    int y = random.nextInt(25) + 5;

                    while (harta.isCollidable(x, y)) {
                        x = random.nextInt(25) + 5;
                        y = random.nextInt(26) + 5;
                    }

                    m.x = x;
                    m.y = y;
                    allMoney.add(m);


            }
        }
    }
    public void removeCollectedMoney() {
        Iterator<Money> iterator = allMoney.iterator();
        while (iterator.hasNext()) {
            Money m = iterator.next();
            if (m.picked) {
                iterator.remove();
                System.out.println("am colectat money");
            }
        }
    }

    public void drawMoney(Graphics2D g2, Player player) {
       ArrayList<Money> copy = new ArrayList<>(allMoney);

        for (Money m : copy) {
            double dx = (player.x - m.x) / 16;
            double dy = (player.y - m.y) / 16;
            double distance = Math.sqrt(dx * dx + dy * dy);
            if (distance < 160 && !m.picked) {
                m.updateImg();
                m.draw(g2, player);
            }
        }
    }


    }

