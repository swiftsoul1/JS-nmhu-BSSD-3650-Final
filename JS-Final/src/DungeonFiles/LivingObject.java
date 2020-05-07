package DungeonFiles;

abstract public class LivingObject extends GameObject{

    private int curHP;
    private int maxHP;
    private int dmgRoll;
    private LivingState curState;
    private LivingState aliveState;
    private LivingState deadState;
    private String name;


    public LivingObject(String name, int maxHP, int dmg) {
        super();
        deadState = new DeadState(this);
        aliveState = new AliveState(this);;
        this.name = name;
        this.maxHP = maxHP;
        dmgRoll = dmg;
    }

    //region StateMethods
    //these will be the action available to the player and
    //to progress the game forward
    public void move(int xPos, int yPos){
        curState.move(xPos,yPos);
    }
    public void attack(LivingObject enemy){
        curState.attack(enemy);
    }
    public void takeDamage(int dmg){
        curState.takeDamage(dmg);
    }
    public InventoryItem use(PropObject use){
        return curState.use(use);
    }
    //endregion

    //region accessors
    public String getName() {
        return name;
    }

    public int getDmgRoll() {
        return dmgRoll;
    }

    public LivingState getAliveState() {
        return aliveState;
    }

    public LivingState getDeadState() {
        return deadState;
    }

    public LivingState getCurState() {
        return curState;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public int getCurHP() {
        return curHP;
    }
    //endregion

    // region mutators
    public void setCurHP(int curHP) {
        this.curHP = curHP;
        if(this.curHP > maxHP){
            this.curHP = maxHP;
        }
    }

    public void setDmgRoll(int dmgRoll) {
        this.dmgRoll = dmgRoll;
    }
    public void setCurState(LivingState curState) {
        this.curState = curState;
    }
    //endregion
}
