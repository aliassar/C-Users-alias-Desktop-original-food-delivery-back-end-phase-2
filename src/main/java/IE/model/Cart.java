package IE.model;

import java.util.ArrayList;

public class Cart {
    private Integer userId;
    private ArrayList<Order> orders;
    private float remainedTimeToArrive = 10000;
    private String Status;
    private String restaurantID;

    public Cart() {
        this.Status = "inProcess";
        this.orders = new ArrayList<>();
    }

    public Cart(ArrayList<Order> orders,int userId,  float remainedTimeToArrive, String Status, String restaurantID) {
        this.userId = userId;
        this.orders = orders;
        this.remainedTimeToArrive = remainedTimeToArrive;
        this.Status = Status;
        this.restaurantID = restaurantID;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public String getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(String restaurantID) {
        this.restaurantID = restaurantID;
    }

    public float getRemainedTimeToArrive() {
        return remainedTimeToArrive;
    }

    public void setRemainedTimeToArrive(float remainedTimeToArrive) {
        this.remainedTimeToArrive = remainedTimeToArrive;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void addToOrders(Order order) {
        orders.add(order);
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
