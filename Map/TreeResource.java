package AgeOfConquest.Map;

import java.util.Random;

public class TreeResource {
    int quantity;
    public TreeResource(){
        Random rn = new Random();
        this.quantity = rn.nextInt(10 - 2 + 1) + 2;
    }
    public int getQuantity(){
        return this.quantity;
    }

}
