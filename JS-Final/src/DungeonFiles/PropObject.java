package DungeonFiles;

public class PropObject extends GameObject {

    private PropState curState, usedState, unusedState;
    private InventoryItem item;
    private String name;

    public PropObject(String name, InventoryItem item, int x, int y) {
        super();
        this.name = name;
        this.item = item;
        usedState = new UsedState(this);
        unusedState = new UnusedState(this);
        curState = unusedState;
        setxPos(x);
        setyPos(y);
    }

    //region StateMethod
    public InventoryItem useProp(){
        return curState.useProp();
    }
    //endregion


    public String getName() {
        return name;
    }

    public InventoryItem getItem() {
        return item;
    }

    public PropState getUsedState() {
        return usedState;
    }

    public PropState getUnusedState() {
        return unusedState;
    }

    public PropState getCurState() {
        return curState;
    }

    public void setCurState(PropState curState) {
        this.curState = curState;
    }
}
