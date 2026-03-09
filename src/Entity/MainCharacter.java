package Entity;

import Harta.InterfaceHarta;

import java.util.ArrayList;

public interface  MainCharacter extends Characterr {
    void getHurt(Entity entity);
    void update(InterfaceHarta harta, ArrayList<Orco> Enemies);

    public void addObserver(PlayerObserver observer);

    public void removeObserver(PlayerObserver observer);

    public void notifyObservers();

    public void checkState();



}
