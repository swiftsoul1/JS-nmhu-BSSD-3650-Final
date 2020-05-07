package DungeonFiles;

public class UnusedState implements PropState {

    private PropObject host;

    public UnusedState(PropObject host) {
        this.host = host;
    }

    @Override
    public InventoryItem useProp() {
        host.setCurState(host.getUsedState());
        InventoryItem item = host.getItem();
        if(item != null){
            return item;
        }
        return new Consumable("Nothing",0,false,0);
    }
}
