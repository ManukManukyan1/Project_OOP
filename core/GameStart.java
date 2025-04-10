package AgeOfConquest.core;

import AgeOfConquest.Map.Map;

public class GameStart {
    public static void start(){
        Map map = new Map();
        map.generateMap();
        map.printMap();
    }
}
