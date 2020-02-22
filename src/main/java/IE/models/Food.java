package IE.models;

import com.fasterxml.jackson.annotation.JsonSetter;

import java.net.URL;

public class Food {
    private String name;
    private String description;
    private String restaurantName;
    private float price;
    private float popularity;
    private URL image;

    public Food(String name, String restaurantName,float Price) {
        this.name = name;
        this.restaurantName = restaurantName;
        this.price = Price;
    }

    public Food() {
    }

    public URL getImage() {
        return image;
    }

    public void setImage(URL image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurant_Name) {
        this.restaurantName = restaurant_Name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    @JsonSetter("foodName")
    public void setTheName(String name) {
        this.name = name;
    }
}
