package DungeonFiles;

public class Consumable implements InventoryItem {
    private int uses, dice;
    private String name, useAction;
    private boolean damage;


    public Consumable(String name, int uses, boolean damage, int dice) {
        this.name = name;
        this.uses = uses;
        this.damage = damage;
        this.dice = dice;
    }

    @Override
    public void use(LivingObject object) {
        if (uses > 0){
            if (damage) {
                object.takeDamage((int) (Math.random() * dice) + 1);

            } else {
                object.setCurHP(object.getCurHP() + ((int) (Math.random() * dice) + 1));
            }
            uses--;
        }
    }

    public int getUses() {
        return uses;
    }

    @Override
    public String getName() {
        return name;
    }

}
