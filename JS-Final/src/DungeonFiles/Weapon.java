package DungeonFiles;

public class Weapon implements InventoryItem {

    private String name;
    private int dice, bonus;

    public Weapon(String name, int dice, int bonus) {
        this.name = name;
        this.dice = dice;
        this.bonus = bonus;
    }

    @Override
    public void use(LivingObject livingObject) {
        livingObject.setDmgRoll(swing());
    }

    @Override
    public String getName() {
        return name;
    }

    public int swing(){
        return bonus + ((int) (Math.random() * dice) + 1);
    }
}
