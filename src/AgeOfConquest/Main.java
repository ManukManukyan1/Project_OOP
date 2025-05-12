package AgeOfConquest;

import AgeOfConquest.exceptions.MalformedException;
import AgeOfConquest.ui.Gui;

import java.util.Scanner;

import static AgeOfConquest.core.GameStart.start;


public class Main  {
    public static void main(String[] args) throws MalformedException {
        System.out.print("1.Console  \n2.Gui \nChoose: ");

        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        if (choice == 1){
            start();
        }
        else if (choice == 2){
            Gui map = new Gui();
            map.setVisible(true);

        }
        else throw new MalformedException("Wrong choice!!!");

    }
}