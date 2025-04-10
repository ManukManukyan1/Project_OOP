package AgeOfConquest.Map;

import java.util.Random;

public class Map {
    public String[][] map = new String[16][16];
    int size = 16;


    public int getSize() {
        return map.length;
    }

    public void generateMap() {
        TreeResource tree = new TreeResource();
        StoneResource stone = new StoneResource();
        IronResource iron = new IronResource();
        Random rn = new Random();
        int a, b;
        int quantity = tree.getQuantity();
        for (int i = 0; i < quantity; ++i) {
            a = rn.nextInt(16);
            b = rn.nextInt(16);
            if(map[a][b] == null) {
                map[a][b] = "T";
            }
            else{
                --i;
            }
        }
        quantity = stone.getQuantity();
        for (int i = 0; i < quantity; ++i) {
            a = rn.nextInt(16);
            b = rn.nextInt(16);
            if(map[a][b] == null) {
                map[a][b] = "S";
            }
            else{
                --i;
            }
        }
        quantity = iron.getQuantity();
        for (int i = 0; i < quantity; ++i) {
            a = rn.nextInt(15 + 1);
            b = rn.nextInt(15 + 1);
            if(map[a][b] == null) {
                map[a][b] = "I";
            }
            else{
                --i;
            }
        }
    }

    public void printMap() {
        for(int i = 0; i<map.length; i++){
            for(int j = 0; j<map[0].length; j++){
                if(map[i][j] == null){
                    System.out.print(String.format("%3s","."));

                }
                else{
                    System.out.print(String.format("%3s",map[i][j]));
                }
            }
            System.out.println();
        }
    }
}
