package Entity;

import main.GamePanel;

public abstract class CharacterFactory<T extends Characterr> {
   public abstract T createCharacter(String type, GamePanel gp);
}
