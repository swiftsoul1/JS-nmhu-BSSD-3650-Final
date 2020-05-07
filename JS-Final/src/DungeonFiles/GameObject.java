package DungeonFiles;

abstract public class GameObject {
    private int xPos;
    private int yPos;

    public GameObject() {
        this.xPos = -1;
        this.yPos = -1;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public int getxPos() {
        return xPos;
    }
    public int getyPos() {
        return yPos;
    }

}
