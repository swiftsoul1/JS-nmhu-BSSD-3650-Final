package DungeonFiles;

public interface LivingState {
    public void move(int xPos, int yPos);
    public void attack(LivingObject enemy);
    public void takeDamage(int dmg);
    public InventoryItem use(PropObject use);
}
