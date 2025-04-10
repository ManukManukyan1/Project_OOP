package AgeOfConquest.Map;

import java.util.Random;

public class IronResource {
    int quantity;
    public IronResource(){
        Random rn = new Random();
        this.quantity = rn.nextInt(10 - 1) + 2;
    }
    public int getQuantity(){
        return this.quantity;
    }
}
