package DungeonFiles;

import java.util.ArrayList;

public class SmallMap extends MapTemplate {

    private ArrayList<ArrayList<Integer>>  xs;
    private ArrayList<ArrayList<Integer>> ys;
    private ArrayList<ArrayList<Integer>> hps;
    private ArrayList<ArrayList<LivingState>> states;
    private ArrayList<ArrayList<String>> types;
    private GoblinFactory gf;

    public SmallMap(boolean hardMode) {
        super(hardMode);
        xs = new ArrayList<ArrayList<Integer>>();
        ys = new ArrayList<ArrayList<Integer>>();
        hps = new ArrayList<ArrayList<Integer>>();
        states = new ArrayList<ArrayList<LivingState>>();
        types = new ArrayList<ArrayList<String>>();
        gf = new GoblinFactory();
        prepareMap();
    }

    @Override
    void buildRooms() {
        ArrayList<Room> map = new ArrayList<Room>();
        //10 rooms, canvas of 500x500
        map.add(new Room(166,166));
        map.add(new Room(166,166));
        map.add(new Room(166,166));
        map.add(new Room(100,100));
        map.add(new Room(100,100));
        map.add(new Room(166,166));
        map.add(new Room(166,166));
        map.add(new Room(166,166));
        map.add(new Room(100,100));
        map.add(new Room(50,50));

        setMap(map);
    }

    @Override
    void populateEnemies() {
        for ( Room room : getMap()) {
            ArrayList<LivingObject> mon = new ArrayList<LivingObject>();
            ArrayList<LivingState> state = new ArrayList<LivingState>();
            ArrayList<Integer> x = new ArrayList<Integer>();
            ArrayList<Integer> y = new ArrayList<Integer>();
            ArrayList<Integer> hp = new ArrayList<Integer>();
            ArrayList<String> type = new ArrayList<String>();

            int i = getMap().indexOf(room);
            //add enemies based on room position
            if( i == 9){
                //boss
                mon.add(gf.getGoblin("Boss"));
                state.add(new AliveState(gf.getGoblin("Boss")));
                hp.add(50-getHpNerf());
                x.add(5);
                y.add(5);
                type.add("Boss");


            }else if(i == 3 || i == 8){
                //mini boss
                mon.add(gf.getGoblin("Bruiser"));
                state.add(new AliveState(gf.getGoblin("Bruiser")));
                hp.add(30-getHpNerf());
                x.add(3);
                y.add(3);
                type.add("Bruiser");
            }else if(i != 4){
                //mobs
                int rand = (int)(3 * Math.random()) + 1;
                for (int j = 0; j < rand; j++) {
                    mon.add(gf.getGoblin("Normal"));
                    state.add(new AliveState(gf.getGoblin("Normal")));
                    hp.add(20-getHpNerf());
                    x.add((int)(3 * Math.random()) + 1);
                    y.add((int)(3 * Math.random()) + 1);
                    type.add("Normal");
                }
            }
            room.setMonsters(mon);
            states.add(state);
            hps.add(hp);
            xs.add(x);
            ys.add(y);
            types.add(type);
        }

    }

    @Override
    void buildFurnishings() {
        for ( Room room : getMap()) {
            ArrayList<PropObject> props = new ArrayList<PropObject>();
            int i = getMap().indexOf(room);
            //add enemies based on room position
            if( i == 9){
                //no props, final boss
            }else if(i != 4){
                int rand = (int)(3 * Math.random()) + 1;
                for (int j = 0; j < rand; j++) {
                    String name;
                    InventoryItem item;

                    //generate item if random
                    switch((int)(8 * Math.random()) + 1){
                        case 1:
                            item = new Consumable("Healing potion", 3, false, 6);
                            break;
                        case 2:
                            item = new Consumable("Poison", 1, true, 12);
                            break;
                        default:
                            item = null;
                            break;
                    }
                    //item name
                    switch ((int)(3 * Math.random()) + 1){
                        case 1:
                            name = "desk";
                            break;
                        case 2:
                            name = "bed";
                            break;
                        default:
                            name = "locker";
                            break;
                    }

                    if (i == 3 || i == 8)
                    {
                        props.add( new PropObject(name,item,(int)(5 * Math.random()) + 1,(int)(5 * Math.random()) + 1));
                    }
                    else{
                        props.add( new PropObject(name,item,(int)(3 * Math.random()) + 1,(int)(3 * Math.random()) + 1));
                    }

                }
            }
            room.setProps(props);
        }
    }



    public LivingObject getGoblin(int roomIndex, int gobIndex){
        LivingObject gob = gf.getGoblin(types.get(roomIndex).get(gobIndex));
        gob.setCurState(states.get(roomIndex).get(gobIndex));
        gob.setCurHP(hps.get(roomIndex).get(gobIndex));
        gob.setxPos(xs.get(roomIndex).get(gobIndex));
        gob.setyPos(ys.get(roomIndex).get(gobIndex));

        return gob;
    }

    public void setGoblin(int roomIndex, int gobIndex, LivingObject goblin){
        states.get(roomIndex).set(gobIndex, goblin.getCurState());
        hps.get(roomIndex).set(gobIndex, goblin.getCurHP());
        xs.get(roomIndex).set(gobIndex, goblin.getxPos());
        ys.get(roomIndex).set(gobIndex, goblin.getyPos());
    }
}
