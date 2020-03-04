package IE.models;

import java.util.ArrayList;

public class Cart {
    private ArrayList<Order> orders;
    private float remainedTimeToArive;
    private String Status;
    public Cart() {
        this.Status = "inProcess";
        this.orders = new ArrayList<>();
    }

    public float getRemainedTimeToArive() {
        return remainedTimeToArive;
    }

    public void setRemainedTimeToArive(float remainedTimeToArive) {
        this.remainedTimeToArive = remainedTimeToArive;
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
}
