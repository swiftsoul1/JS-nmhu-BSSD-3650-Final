package DungeonFiles;

import java.util.ArrayList;

public class PlayerObject extends LivingObject {


    private InventoryItem weapon;
    private ArrayList<InventoryItem> items;

    public PlayerObject(String name, int maxHP) {
        super(name, maxHP, 0);
        weapon = new Weapon("Starter Dagger", 6,1);
        items = new ArrayList<InventoryItem>();
    }

    @Override
    public void attack(LivingObject enemy) {
        weapon.use(this);
        super.attack(enemy);
    }

    @Override
    public void setDmgRoll(int dmgRoll) {
        super.setDmgRoll(dmgRoll);
    }

    public void equip(Weapon weapon){
        this.weapon = weapon;
    }

    //second version?
    public void useItem(int i){
        items.get(i).use(this);
    }
    public void useItem(int i, LivingObject that){
        items.get(i).use(that);
    }


    public ArrayList<InventoryItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<InventoryItem> items) {
        this.items = items;
    }
}
