package DungeonFiles;

import java.util.HashMap;

public class GoblinFactory {

    private static HashMap <String, LivingObject> hash = new HashMap<String, LivingObject>();

    public LivingObject getGoblin(String type){
        LivingObject gob = null;
        if (hash.containsKey(type))
            gob = hash.get(type);
        else
        {
            switch(type)
            {
                case "Boss":
                    gob = new GoblinBoss();
                    break;
                case "Bruiser":
                    gob = new GoblinBruiser();
                    break;
                case "Normal":
                    gob = new Goblin();
                    break;
            }
            hash.put(type, gob);
        }
        return gob;
    }
    /*public LivingObject getStatsGoblin(String type, LivingState state, int hp, int x, int y){
        LivingObject gob = null;
        if (hash.containsKey(type))
            gob = hash.get(type);
        else
        {
            switch(type)
            {
                case "Boss":
                    gob = new GoblinBoss();
                    break;
                case "Bruiser":
                    gob = new GoblinBruiser();
                    break;
                case "Normal":
                    gob = new Goblin();
                    break;
            }
            hash.put(type, gob);
        }
        gob.setCurState(state);
        gob.setCurHP(hp);
        gob.setxPos(x);
        gob.setyPos(y);
        return gob;
    }*/


}
