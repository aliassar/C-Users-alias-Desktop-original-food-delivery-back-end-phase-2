package IE.utils;

import IE.Loghme;
import IE.model.FoodPartyRestaurant;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FoodPartyManagement implements Runnable  {

    @Override
    public void run()  {
        try {
            ManagePartyRestaurants();
        }catch (IOException e)
        {
            e.printStackTrace();
        }

    }
    public void ManagePartyRestaurants() throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<FoodPartyRestaurant> PartyRestaurants = new ArrayList<>();
        PartyRestaurants = mapper.readValue(new URL("http://138.197.181.131:8080/foodparty")
                , new TypeReference<List<FoodPartyRestaurant>>() {
                });
        Loghme.getInstance().setFoodPartyRestaurant(PartyRestaurants);
        //System.out.println(PartyRestaurants.get(0).getMenu().get(0).getCount());

    }
}
