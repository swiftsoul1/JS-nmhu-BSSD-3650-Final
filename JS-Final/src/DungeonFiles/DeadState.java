package DungeonFiles;

public class DeadState implements LivingState {

    LivingObject host;

    public DeadState(LivingObject host) {
        this.host = host;
    }

    @Override
    public void move(int xPos, int yPos) {
        return;
    }

    @Override
    public void attack(LivingObject enemy) {
        return;
    }

    @Override
    public void takeDamage(int dmg) {
        return;
    }


    @Override
    public InventoryItem use(PropObject use) {
        return null;
    }
}
