package IE.models;

import java.net.URL;

public class FoodParty extends Food {


    private float oldPrice;
    private int count;
    private String Type = "FoodParty";

    public float getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(float oldPrice) {
        this.oldPrice = oldPrice;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }



}

