package IE.utils;

import IE.Loghme;
import IE.model.Cart;
import IE.model.Delivery;
import IE.model.DeliveryMapper;
import IE.model.Location;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DeliveryManagment implements Runnable {
    private ArrayList<Cart> AllCarts;
    private int CartIndex;
    private ScheduledExecutorService scheduler;
    private Location ChosenRestaurantLocation;

    public DeliveryManagment(ArrayList<Cart> allCarts, int cartIndex, ScheduledExecutorService scheduledFuture, Location location) {
        AllCarts = allCarts;
        CartIndex = cartIndex;
        this.ChosenRestaurantLocation = location;
        this.scheduler = scheduledFuture;
    }

    @Override
    public void run() {
        try {
            ManageDelivery();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }


    }

    public void ManageDelivery() throws IOException, SQLException {
        DeliveryMapper mapper = new DeliveryMapper();
        ArrayList<Delivery> deliveries =  mapper.getAll();
        System.out.println("entered ManageDelivery");
        if (deliveries.size() != 0) {
            Delivery ChosenDelivery = new Delivery();
            float ArrivingTime = 999;
            for (int i = 0; i < deliveries.size(); i++) {
                if (i == 0) {
                    ArrivingTime = Loghme.getInstance().CalculateArrivingTime(ChosenRestaurantLocation, deliveries.get(i));
                    ChosenDelivery = deliveries.get(i);
                } else {
                    float newArrivingTime = Loghme.getInstance().CalculateArrivingTime(ChosenRestaurantLocation, deliveries.get(i));
                    if (ArrivingTime > newArrivingTime) {
                        ArrivingTime = newArrivingTime;
                        ChosenDelivery = deliveries.get(i);
                    }
                }
            }
            AllCarts.get(CartIndex).setRemainedTimeToArrive(ArrivingTime);
            AllCarts.get(CartIndex).setStatus("Delivery in road");
            ScheduledExecutorService newScheduler;
            newScheduler = Executors.newSingleThreadScheduledExecutor();
            System.out.println(ArrivingTime);
            newScheduler.schedule(new TerminateDelivery(AllCarts, CartIndex), (int) ArrivingTime, TimeUnit.SECONDS);
            scheduler.shutdownNow();

        }

    }

}
