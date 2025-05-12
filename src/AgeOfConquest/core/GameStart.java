package AgeOfConquest.core;

import AgeOfConquest.exceptions.MalformedException;

import java.util.Scanner;

public class GameStart {
    public static void start() throws MalformedException {
        Map map = new Map();
        map.generateMap();
        map.printMap();
        System.out.println();
        System.out.println("Food: " + map.getFood());
        System.out.println("Iron: " + map.getIron());
        System.out.println("Wood: " + map.getWood());
        System.out.println("Stone: " + map.getStone());
        System.out.println("Gold: " + map.getGold());
        System.out.println();
        Citizen citizen = new Citizen();
        System.out.print("\n1.Gain resources" + "\n2.Print population" + "\n3.Print map"+ "\n4.Show workers" + "\n5.Next Turn"+ "\n6.Build"+ "\n7.Legend" + "\nChoose: ");
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        String s = map.fullString();
        String[] arr = s.split("%%");
        citizen = new Citizen(map.getIdle(), map.getWorking());
        while(choice<=9 && choice>0){
            switch(choice){
                case 1:
                    System.out.print("How many people? (1 - 5): ");
                    int people = sc.nextInt();
                    if(people > 5 || people < 0){
                        System.out.println("Wrong number of people!! ");
                        break;
                    }
                    System.out.println();
                    citizen.gain(people);
                    System.out.print("Enter row: ");
                    int row = sc.nextInt();
                    System.out.print("Enter column: ");
                    int col = sc.nextInt();
                    map.gainResource(people, row, col);
                    break;

                case 2:
                    System.out.println("Idle: " + citizen.getIdle());
                    System.out.println("Working: " + citizen.getWorking());
                    break;

                case 3:
                    s = map.fullString();
                    arr = s.split("%%");
                    map.printMap(citizen.getIdle(), citizen.getWorking());
                    break;

                case 4:
                    System.out.println("Tree gaining: " + map.getWoodGainer());
                    System.out.println("Iron gaining: " + map.getIronGainer());
                    System.out.println("Gold gaining: " + map.getGoldGainer());
                    System.out.println("Stone gaining: " + map.getStoneGainer());
                    System.out.println("Food gaining: " + map.getFoodGainer());
                    break;

                case 5:
                    map.nextTurn();
                    break;

                case 6:
                    System.out.println("\nWhat do you want to build?");
                    System.out.println("1. Wooden House - Cost: 200 Wood (+3 population)");
                    System.out.println("2. Stone House - Cost: 100 Stone, 50 Wood (+5 population)");
                    System.out.println("3. Well - Cost: 50 Wood, 100 Stone (+10% food production)");
                    System.out.println("4. Marketplace - Cost: 150 Wood, 50 Stone, 10 Gold (+15% gold production)");
                    System.out.println("5. Lumber Camp - Cost: 400 Wood (+20% wood production)");
                    System.out.println("6. Farm - Cost: 300 Wood, 100 Stone, 20 Food (+2 food/turn, +10% food production)");
                    System.out.println("7. Hunting Lodge - Cost: 250 Wood (+10% food production)");
                    System.out.println("8. Iron Mine - Cost: 150 Wood, 50 Stone (+15% iron production)");
                    System.out.println("9. Gold Mine - Cost: 200 Wood, 50 Stone, 50 Gold (+10% gold production)");
                    System.out.println("10. Stone Mine - Cost: 200 Wood (+20% stone production)");
                    System.out.print("Choose: ");
                    choice = sc.nextInt();
                    System.out.print("Enter free space coordinates\nEnter row: ");
                    row = sc.nextInt();
                    System.out.print("Enter column: ");
                    col = sc.nextInt();
                    map.build(Building.values()[choice-1], row, col);
                    break;
                case 7:
                    System.out.println();
                    System.out.println("""
                O - Wooden House
                N - Stone House
                W - Well
                M - Marketplace
                L - Lumber Camp
                F - Farm
                H - Hunting Lodge
                A - Warehouse
                R - Iron Mine
                D - Gold Mine
                I - Iron
                T - Tree
                G - Gold
                S - Stone
                """);

                    break;
            }




            System.out.println();
            System.out.print("\n1.Gain resources" + "\n2.Print population" + "\n3.Print map"+ "\n4.Show workers" + "\n5.Next Turn"+ "\n6.Build"+ "\n7.Legend" + "\nChoose: ");
            choice = sc.nextInt();
            System.out.println();
        }
    }
}
