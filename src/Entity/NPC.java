package Entity;

import java.awt.*;
import java.io.IOException;

public interface NPC extends Characterr {
    void chooseResponse(Player player, Graphics2D g, int boxX, int boxY, String response, int option,int boxWidthh,int boxHeightt) throws IOException;
    void speak(Player player, Graphics2D g);
    void update();
    public int getCurrentDialogueIndex();
    public void setCurrentDialogueIndex(int index);
}
