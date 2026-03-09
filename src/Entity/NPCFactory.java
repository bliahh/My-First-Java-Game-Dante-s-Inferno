package Entity;

import main.GamePanel;


public class NPCFactory extends CharacterFactory<NPC> {
    @Override
    public NPC createCharacter(String type,GamePanel gp) {
        switch (type.toLowerCase()) {
            case "sot": return new Sot(gp);
            case "sotie": return new Sotie(gp);
            case "seller": return new Seller(gp);
            case "oracolinfern": return new OracolInfern(gp);
            case "oracolparadis": return new OracolParadis(gp);
            case "mike": return new Mike(gp);
            case "fiu": return new Son(gp);
            default: throw new IllegalArgumentException("nu am gasit NPC type: " + type);
        }
    }
}
