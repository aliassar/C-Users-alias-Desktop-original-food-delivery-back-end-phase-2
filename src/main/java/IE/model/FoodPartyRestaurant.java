package IE.model;

import java.lang.*;

import java.net.URL;
import java.util.ArrayList;

public class FoodPartyRestaurant {
    private String id;
    private String name;
    private String description;
    private ArrayList<FoodParty> menu;
    private Location location;
    private URL logo;

    public FoodPartyRestaurant(String id, String name, String description, ArrayList<FoodParty> menu, Location location, URL logo) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.menu = menu;
        this.location = location;
        this.logo = logo;
    }
    public FoodPartyRestaurant(){

    }

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

    public void addToMenu(FoodParty food){
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

    public ArrayList<FoodParty> getMenu() {
        return menu;
    }

    public void setMenu(ArrayList<FoodParty> menu) {
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
