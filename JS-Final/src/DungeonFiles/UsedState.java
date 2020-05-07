package DungeonFiles;

public class UsedState implements PropState {
    private PropObject host;

    public UsedState(PropObject host) {
        this.host = host;
    }

    @Override
    public InventoryItem useProp() {
        return null;
    }
}
