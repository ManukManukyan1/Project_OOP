package AgeOfConquest.core;

import AgeOfConquest.exceptions.MalformedException;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class Map implements MapInterface  {
    Citizen citizen = new Citizen();
    Citizen citizen1 = new Citizen();
    private int ironGainer = 0;
    private int woodGainer= 0;
    private int goldGainer= 0;
    private int stoneGainer= 0;
    private int foodGainer= 0;
    private int food, iron, wood, stone, gold;
    private final java.util.Map<Point, Building> buildings = new HashMap<>();
    private float woodGainMultiplier = 1.0f;
    private float stoneGainMultiplier = 1.0f;
    private float ironGainMultiplier = 1.0f;
    private float goldGainMultiplier = 1.0f;
    private float foodGainMultiplier = 1.0f;
    private char[][] map = new char[16][16];

    public Map() {
        this.map = new char[16][16]; // Initialize here instead
    }




    public int getSize() {
        return map.length;
    }

    public void generateMap() {
        Random rn = new Random();
        food = 0;
        iron = 0;
        wood = 0;
        stone = 0;
        gold = 0;
        int ntree = rn.nextInt(15 - 1) + 2;
        int ngold = rn.nextInt(4 - 1) + 2;
        int niron = rn.nextInt(7 - 1) + 2;
        int nstone = rn.nextInt(10 - 1) + 2;
        int a, b;

        for (int i = 0; i < ntree; ++i) {
            a = rn.nextInt(14);
            b = rn.nextInt(14);
            if(map[a][b] == 0) {
                map[a][b] = 'T';
            }
            else{
                --i;
            }
        }
        for (int i = 0; i < ngold; ++i) {
            a = rn.nextInt(14);
            b = rn.nextInt(14);
            if(map[a][b] == 0) {
                map[a][b] = 'G';
            }
            else{
                --i;
            }
        }
        for (int i = 0; i < nstone; ++i) {
            a = rn.nextInt(14);
            b = rn.nextInt(14);
            if(map[a][b] == 0) {
                map[a][b] = 'S';
            }
            else{
                --i;
            }
        }
        for (int i = 0; i < niron; ++i) {
            a = rn.nextInt(14);
            b = rn.nextInt(14);
            if(map[a][b] == 0) {
                map[a][b] = 'I';
            }
            else{
                --i;
            }
        }
    }

    public float getWoodGainMultiplier() {
        return woodGainMultiplier;
    }
    public void setWoodGainMultiplier(float multiplier) {
        this.woodGainMultiplier = multiplier;
    }

    public float getStoneGainMultiplier() {
        return stoneGainMultiplier;
    }
    public void setStoneGainMultiplier(float multiplier) {
        this.stoneGainMultiplier = multiplier;
    }

    public float getIronGainMultiplier() {
        return ironGainMultiplier;
    }
    public void setIronGainMultiplier(float multiplier) {
        this.ironGainMultiplier = multiplier;
    }

    public float getGoldGainMultiplier() {
        return goldGainMultiplier;
    }
    public void setGoldGainMultiplier(float multiplier) {
        this.goldGainMultiplier = multiplier;
    }

    public float getFoodGainMultiplier() {
        return foodGainMultiplier;
    }
    public void setFoodGainMultiplier(float multiplier) {
        this.foodGainMultiplier = multiplier;
    }




    public int getIronGainer(){
        return ironGainer;
    }
    public int getWoodGainer(){
        return woodGainer;
    }
    public int getGoldGainer(){
        return goldGainer;
    }
    public int getStoneGainer(){
        return stoneGainer;
    }
    public int getFoodGainer(){
        return foodGainer;
    }

    public int getFood(){
        return food;
    }
    public int getWood(){
        return wood;
    }
    public int getIron(){
        return iron;
    }
    public int getStone(){
        return stone;
    }
    public int getGold(){
        return gold;
    }


    public int check(int row, int col){
        if(map[row-1][col] != 0){
            return 1;
        }
        else if(map[row][col-1] != 0){
            return 2;
        }
        else if(map[row+1][col] != 0){
            return 3;
        }
        else if(map[row][col+1] != 0){
            return 4;
        }
        return 0;
    }

    public Citizen getCitizen() {
        return this.citizen;
    }



    public char getMapCell(int row, int col) {
        if (row >= 0 && row < map.length && col >= 0 && col < map[0].length) {
            return map[row][col];
        }
        return 0;
    }


    public void nextTurn() {
        wood += Math.round(woodGainer * 8 * woodGainMultiplier);
        food += Math.round(foodGainer * 5 * foodGainMultiplier);
        stone += Math.round(stoneGainer * 6 * stoneGainMultiplier);
        iron += Math.round(ironGainer * 4 * ironGainMultiplier);
        gold += Math.round(goldGainer * 2 * goldGainMultiplier);

    }


    public void gainResource(int people, int row, int col) throws MalformedException{
        try {
            citizen.gain(people);
            if (row < 0 || row >= map.length || col < 0 || col >= map[0].length || map[row][col] == 0) {
                throw new MalformedException("Wrong coordinates!");
            }
            switch (map[row][col]) {
                case 'T':
                    woodGainer += people;
                    foodGainer += people;
                    break;
                case 'S':
                    stoneGainer += people;
                    break;
                case 'G':
                    goldGainer += people;
                    break;
                case 'I':
                    ironGainer += people;

                    break;
            }
        } catch (Exception e) {
            throw new MalformedException("Error assigning workers: " + e.getMessage());
        }
    }


    public void moveCitizens(int people, int row, int col) throws MalformedException {
        int totalAvailable = 0;
        for (int i = 0; i < map[15].length; i++) {
            if (map[15][i] != 0) {
                totalAvailable += map[15][i] - '0';
            }
        }

        if (totalAvailable < people) {
            throw new MalformedException("Not enough idle citizens available!");
        }

        int remaining = people;
        for (int i = 0; i < map[15].length && remaining > 0; i++) {
            if (map[15][i] != 0) {
                int available = map[15][i] - '0';
                int take = Math.min(available, remaining);
                map[15][i] = (char)((available - take) + '0');
                remaining -= take;
            }
        }

        int[][] directions = {{1,0}, {-1,0}, {0,1}, {0,-1}};
        boolean placed = false;

        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];

            if (newRow >= 0 && newRow < map.length &&
                    newCol >= 0 && newCol < map[0].length &&
                    map[newRow][newCol] == 0) {
                map[newRow][newCol] = (char)(people + '0');
                placed = true;
                break;
            }
        }

        if (!placed) {
            returnCitizensToBottomRow(people);
            throw new MalformedException("No space adjacent to resource for workers!");
        }
    }





    private void returnCitizensToBottomRow(int count) {
        for (int i = 0; i < map[map.length-1].length; i++) {
            if (map[map.length-1][i] == 0) {
                map[map.length-1][i] = (char)(count + '0');
                return;
            } else if (map[map.length-1][i] - '0' < 5) {
                int current = map[map.length-1][i] - '0';
                int add = Math.min(5 - current, count);
                map[map.length-1][i] = (char)(current + add + '0');
                count -= add;
                if (count == 0) return;
            }
        }
    }

    public String fullString(){
        return citizen.fullString();
    }


    public void build(Building building, int row, int col) throws MalformedException {
        if (row < 0 || row >= map.length || col < 0 || col >= map[0].length) {
            throw new MalformedException("Invalid position!");
        }

        if (map[row][col] != 0) {
            throw new MalformedException("Space already occupied!");
        }

        int[] costs = building.getCosts();
        if (wood < costs[0] || stone < costs[1] || iron < costs[2] || gold < costs[3] || food < costs[4]) {
            throw new MalformedException("Not enough resources!");
        }

        map[row][col] = building.getMapChar();
        buildings.put(new Point(row, col), building);

        wood -= costs[0];
        stone -= costs[1];
        iron -= costs[2];
        gold -= costs[3];
        food -= costs[4];
        citizen1 = new Citizen();
        switch (building) {

            case WOODENHOUSE:
                citizen.addCitizens(3);
                citizen1.addCitizens(3);
                break;
            case STONEHOUSE:
                citizen.addCitizens(5);
                citizen1.addCitizens(5);
                break;
            case FARM:
                foodGainer += 2;
                break;
            case WELL:
                setFoodGainMultiplier(getFoodGainMultiplier() * 1.10f); // 10% food boost
                break;
            case MARKETPLACE:
                setGoldGainMultiplier(getGoldGainMultiplier() + 0.15f); // 15% gold boost
                break;
            case LUMBERCAMP:
                setWoodGainMultiplier(getWoodGainMultiplier() + 0.20f); // 20% wood boost
                break;
            case HUNTINGLODGE:
                setFoodGainMultiplier(getFoodGainMultiplier() + 0.10f); // 10% food boost
                break;
            case IRONMINE:
                setIronGainMultiplier(getIronGainMultiplier() + 0.15f); // 15% iron boost
                break;
            case GOLDMINE:
                setGoldGainMultiplier(getGoldGainMultiplier() + 0.10f); // 10% gold boost
                break;
            case STONEMINE:
                setStoneGainMultiplier(getStoneGainMultiplier() + 0.20f); // 20% stone boost
                break;
        }
    }


    public int getIdleBuild(){
        return citizen1.getIdle() - 10;
    }




    public void printMap() {
        int idle = citizen.getIdle();
        if(idle %5 ==0){
            for(int i = 0; i < idle /5; ++i){
                map[15][i] = '5';
            }
        }
        else{
            for(int i = 0; i < idle /5 + 1; ++i){
                if(i == idle /5){
                    map[15][i] = (char)(idle %5 + '0');
                }
                else {
                    map[15][i] = '5';

                }

            }

        }
        for(int i = 0; i<map.length; i++){

            for(int j = 0; j<map[0].length; j++){
                if(map[i][j] == 0){
                    System.out.print(String.format("%3s","."));

                }
                else{
                    System.out.print(String.format("%3s",map[i][j]));
                }
            }
            System.out.println();

        }
    }
    public void printMap(int idle, int working) {
        if(idle % 5 ==0){
            for(int i = 0; i < idle /5; ++i){
                map[15][i] = '5';
                if(i == idle /5-1){
                    map[15][i+1] = 0;

                }
            }
        }
        else{
            for(int i = 0; i < idle /5 + 1; ++i){
                if(1 == idle /5){
                    map[15][i] = (char)(idle %5 + '0');
                }
                else {
                    map[15][i] = '5';

                }

            }

        }
        for(int i = 0; i<map.length; i++){

            for(int j = 0; j<map[0].length; j++){
                if(map[i][j] == 0){
                    System.out.print(String.format("%3s","."));

                }
                else{
                    System.out.print(String.format("%3s",map[i][j]));
                }
            }
            System.out.println();

        }
        System.out.println("\nFood: " + getFood());
        System.out.println("Iron: " + getIron());
        System.out.println("Wood: " + getWood());
        System.out.println("Stone: " + getStone());
        System.out.println("Gold: " + getGold());
    }
    public int getIdle(){
        return citizen.getIdle();
    }
    public int getWorking() {
        return woodGainer + stoneGainer + ironGainer + goldGainer + foodGainer;
    }
}
