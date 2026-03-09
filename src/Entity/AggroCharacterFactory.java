package Entity;

import main.GamePanel;

public class AggroCharacterFactory extends CharacterFactory<AggroCharacter>{
    @Override
    public AggroCharacter createCharacter(String type, GamePanel gp) {
        switch (type.toLowerCase()) {
            case "orc": return new Orco(gp);
            case "ghost": return new Ghost(gp);
            default: throw new IllegalArgumentException("nu am gasit type: " + type);
        }
    }
}
