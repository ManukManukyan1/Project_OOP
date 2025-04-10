package AgeOfConquest.Map;

import java.util.Random;

public class StoneResource {
    int quantity;
    public StoneResource(){
        Random rn = new Random();
        this.quantity = rn.nextInt(10 - 2 + 1) + 2;
    }
    public int getQuantity(){
        return this.quantity;
    }
}
