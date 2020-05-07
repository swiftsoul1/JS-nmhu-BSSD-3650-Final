package DungeonFiles;

import java.util.ArrayList;

public class Room {

    //region fields
    private int colSize;
    private int rowSize;
    private ArrayList<LivingObject> monsters;
    private ArrayList<PropObject> props;
    //endregion

    //region constructor
    public Room(int colSize, int rowSize) {
        monsters = new ArrayList<LivingObject>();
        props = new ArrayList<PropObject>();
        this.colSize = colSize;
        this.rowSize = rowSize;
    }
    //endregion

    //region mutator
    public void setProps(ArrayList<PropObject> props) {
        this.props = props;
    }

    public void setMonsters(ArrayList<LivingObject> monsters) {
        this.monsters = monsters;
    }
    //endregion

    //region accessors
    public ArrayList<LivingObject> getMonsters() {
        return monsters;
    }

    public ArrayList<PropObject> getProps() {
        return props;
    }

    public int getColSize() {
        return colSize;
    }

    public int getRowSize() {
        return rowSize;
    }
    //endregion
}
