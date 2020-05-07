package DungeonFiles;

import java.util.ArrayList;

abstract public class MapTemplate {

    private boolean hardMode;
    private ArrayList<Room> map;
    private int hpNerf;

    public MapTemplate(boolean hardMode) {
        map = new ArrayList<Room>();
        hpNerf=0;
        this.hardMode = hardMode;
    }

    final void prepareMap(){
        if(!hardMode){
            addNerfs();
        }
        buildRooms();
        populateEnemies();
        buildFurnishings();
    }

    //region hooks
    private void addNerfs(){
        hpNerf = 8;
    }
    //endregion

    abstract void buildRooms();

    abstract void populateEnemies();

    abstract void buildFurnishings();


    public int getHpNerf() {
        return hpNerf;
    }

    public ArrayList<Room> getMap() {
        return map;
    }

    public void setMap(ArrayList<Room> map) {
        this.map = map;
    }
}
