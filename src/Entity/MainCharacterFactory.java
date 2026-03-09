package Entity;

import main.GamePanel;
import main.KeyHandler;

public class MainCharacterFactory extends CharacterFactory<MainCharacter>{
   protected KeyHandler kh;
   public MainCharacterFactory(KeyHandler kh){
       this.kh=kh;
   }
    public MainCharacter createCharacter(String type, GamePanel gp) {
        switch (type.toLowerCase()) {
            case "player1": return new Player(gp,this.kh);

            default: throw new IllegalArgumentException("nu am gasit MAINCHARACTER type: " + type);
        }
    }
}
