package IE.model;

import java.net.URL;

public class FoodParty extends Food {


    private float oldPrice;
    private int count;
    private String Type = "FoodParty";

    public float getOldPrice() {
        return oldPrice;
    }

    public FoodParty(String name, String restaurantId, String description, String restaurantName, float price, float popularity, URL image, String Type, Float oldPrice, int count) {
        this.setName(name);
        this.setDescription(description);
        this.setRestaurantName(restaurantName);
        this.setPrice(price);
        this.setPopularity(popularity);
        this.setImage(image);
        this.Type = Type;
        this.setRestaurantId(restaurantId);
        this.oldPrice = oldPrice;
        this.count = count;
    }

    public FoodParty() {
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

    public void decreaseCount() {
        this.count += -1;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }


}

