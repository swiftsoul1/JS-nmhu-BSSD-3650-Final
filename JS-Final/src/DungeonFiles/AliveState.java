package DungeonFiles;

public class AliveState implements LivingState {
    LivingObject host;

    public AliveState(LivingObject host) {
        this.host = host;
    }

    @Override
    public void move(int xPos, int yPos) {
        host.setxPos(xPos);
        host.setxPos(yPos);
    }

    @Override
    public void attack(LivingObject enemy) {
        enemy.takeDamage(host.getDmgRoll());
    }

    @Override
    public void takeDamage(int dmg) {
        host.setCurHP(host.getCurHP()-dmg);
        if(host.getCurHP() <= 0){
            host.setCurState(host.getDeadState());
        }
    }

    @Override
    public InventoryItem use(PropObject use) {
        return use.useProp();
    }
}
