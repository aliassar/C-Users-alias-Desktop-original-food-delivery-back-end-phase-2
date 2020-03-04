package IE.models;

import java.net.URL;
import java.util.ArrayList;

public class FoodPartyRestaurants  {
    private String id;
    private String name;
    private String description;
    private ArrayList<FoodParty> menu;
    private Location location;
    private URL logo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public URL getLogo() {
        return logo;
    }

    public void setLogo(URL logo) {
        this.logo = logo;
    }
}
