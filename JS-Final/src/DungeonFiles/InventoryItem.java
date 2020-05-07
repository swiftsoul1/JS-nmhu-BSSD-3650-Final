package DungeonFiles;

public interface InventoryItem {
    void use(LivingObject livingObject);
    String getName();
}
