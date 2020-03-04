package IE;

import IE.models.Cart;
import IE.models.Delivery;
import IE.models.Restaurant;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ScheduledFuture;

public class DeliveryManagment implements Runnable {
    private ArrayList<Cart> AllCarts;
    private int CartIndex;
    private ScheduledExecutorService scheduler;
    private Restaurant ChosenRestaurant;

    public DeliveryManagment(ArrayList<Cart> allCarts, int cartIndex, ScheduledExecutorService scheduledFuture,Restaurant restaurant) {
        AllCarts = allCarts;
        CartIndex = cartIndex;
        this.ChosenRestaurant = restaurant;
        this.scheduler = scheduledFuture;
    }

    @Override
    public void run()  {
        try {
            ManageDelivery();
        }catch (IOException e)
        {
            e.printStackTrace();
        }


    }
    public void ManageDelivery() throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Delivery> deliveries;
        deliveries = mapper.readValue(new URL("http://138.197.181.131:8080/deliveries")
                , new TypeReference<List<Delivery>>() {
                });
        if (deliveries.size() != 0){
            Delivery ChosenDelivery = new Delivery() ;
            float ArrivingTime = 999;
            for (int i=0; i < deliveries.size(); i++){
                if(i == 0){
                    ArrivingTime = Loghme.getInstance().CalculateArivingTime(ChosenRestaurant.getLocation(), deliveries.get(i));
                    ChosenDelivery = deliveries.get(i);
                }
                else {
                    float newArrivingTime = Loghme.getInstance().CalculateArivingTime(ChosenRestaurant.getLocation(), deliveries.get(i));
                    if (ArrivingTime> newArrivingTime){
                        ArrivingTime = newArrivingTime;
                        ChosenDelivery = deliveries.get(i);
                    }
                }
            }
            AllCarts.get(CartIndex).setRemainedTimeToArive(ArrivingTime);
            AllCarts.get(CartIndex).setStatus("Delivery in road");
            ScheduledExecutorService newScheduler;
            newScheduler = Executors.newSingleThreadScheduledExecutor();
            newScheduler.schedule(new TerminateDelivery(AllCarts, CartIndex),(int) ArrivingTime,TimeUnit.SECONDS);
            scheduler.shutdownNow();

        }

    }

}
