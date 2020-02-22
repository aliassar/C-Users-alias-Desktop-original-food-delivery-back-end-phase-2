package IE.models;

import java.lang.*;

import java.net.URL;
import java.util.ArrayList;

public class Restaurant {
    private String id;
    private String name;
    private String description;
    private ArrayList<Food> menu;
    private Location location;
    private URL logo;


    public URL getLogo() {
        return logo;
    }

    public void setLogo(URL logo) {
        this.logo = logo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void addToMenu(Food food){
        menu.add(food);
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

    public ArrayList<Food> getMenu() {
        return menu;
    }

    public void setMenu(ArrayList<Food> menu) {
        this.menu = menu;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public double calculateLocation(){
        float x = Math.abs(this.location.getX() - 0) * Math.abs(this.location.getX() - 0);
        float y = Math.abs(this.location.getY() - 0) * Math.abs(this.location.getY() - 0);
        double result = Math.sqrt(x+y);
        return result;

    }

    public double calculatePopularity(){
        float averageFoodPopularity = 0;
        for (Food food : this.menu) {
            averageFoodPopularity += food.getPopularity();
        }
        averageFoodPopularity = averageFoodPopularity/this.menu.size();
        double popularity = averageFoodPopularity/this.calculateLocation();
        return popularity;

    }



}
