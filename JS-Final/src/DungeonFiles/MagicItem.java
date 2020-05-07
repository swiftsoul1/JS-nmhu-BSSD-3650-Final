package DungeonFiles;

public class MagicItem implements InventoryItem {

    private boolean damage;
    private int dice;
    private String name;

    public MagicItem(String name, boolean damage, int dice ) {
        this.name = name;
        this.damage = damage;
        this.dice = dice;
    }


    @Override
    public void use(LivingObject livingObject) {
        if (damage) {
            livingObject.takeDamage((int) (Math.random() * dice) + 1);

        } else {
            livingObject.setCurHP(livingObject.getCurHP() + ((int) (Math.random() * dice) + 1));
        }
    }

    @Override
    public String getName() {
        return name;
    }

}
