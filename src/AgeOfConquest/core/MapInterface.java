package AgeOfConquest.core;

import AgeOfConquest.exceptions.MalformedException;

public interface MapInterface  {
    void generateMap();
    void gainResource(int people, int row, int col)throws MalformedException;
    void moveCitizens(int people, int row, int col) throws MalformedException;
    String fullString();
    void printMap();
    void printMap(int idle, int working);
    void nextTurn();

    Citizen getCitizen();
}
